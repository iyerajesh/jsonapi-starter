package io.xylia.platform.jsonapi.configuration;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.NonNull;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.github.jasminb.jsonapi.ConverterConfiguration;
import com.github.jasminb.jsonapi.DeserializationFeature;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import org.springframework.lang.Nullable;

/**
 * Implementation of {@link org.springframework.http.converter.HttpMessageConverter} that can read
 * and write JSON using <a href="http://jsonapi.org/">json api</a>.
 * 
 * <p>
 * This converter can be used to bind to {@link com.github.jasminb.jsonapi.annotations.Type} beans,
 * {@link JSONAPIDocument} beans, or untyped {@link Iterable} instances.
 *
 * <p>
 * By default, this converter supports {@code application/vnd.api+json}.
 *
 * <p>
 * The default constructor uses the default configuration provided by
 * {@link Jackson2ObjectMapperBuilder}.
 * 
 *
 */
public class JsonApiHttpMessageConverter extends AbstractJackson2HttpMessageConverter {

  @NonNull
  public static final String APPLICATION_JSON_API_VALUE = "application/vnd.api+json";

  public static final MediaType APPLICATION_JSON_API =
      MediaType.valueOf(APPLICATION_JSON_API_VALUE);

  private final ResourceConverter resourceConverter;

  public JsonApiHttpMessageConverter() {
    this(Jackson2ObjectMapperBuilder.json().build());
  }

  private JsonApiHttpMessageConverter(ObjectMapper objectMapper) {
    super(objectMapper, APPLICATION_JSON_API);

    Objects.requireNonNull(objectMapper, "An ObjectMapper must be provided.");

    resourceConverter = new ResourceConverter(objectMapper);

    // Used for POST (create) as we don't have ID yet!
    resourceConverter.disableDeserializationOption(DeserializationFeature.REQUIRE_RESOURCE_ID);
  }

  @Override
  public boolean canRead(Type type, @Nullable Class<?> contextClass,
      @Nullable MediaType mediaType) {
    if (!canRead(mediaType)) {
      return false;
    }

    Class<?> clazz = getRawClass(type, contextClass);
    if (clazz != null && supports(clazz) && !resourceConverter.isRegisteredType(clazz)) {
      resourceConverter.registerType(clazz);
      return true;
    }

    return resourceConverter.isRegisteredType(clazz);
  }

  @Override
  public boolean canWrite(@Nullable Type type, Class<?> contextClass,
      @Nullable MediaType mediaType) {
    if (!canWrite(mediaType) || type == null) {
      return false;
    }

    Class<?> clazz = getRawClass(type, contextClass);
    if (clazz != null && supports(clazz) && !resourceConverter.isRegisteredType(clazz)) {
      resourceConverter.registerType(clazz);
      return true;
    }

    return resourceConverter.isRegisteredType(clazz);
  }

  private @Nullable Class<?> getRawClass(Type type, @Nullable Class<?> contextClass) {
    Class<?> rawClass = null;

    JavaType javaType = getJavaType(type, contextClass);
    if (javaType.isCollectionLikeType()) {
      rawClass = javaType.getContentType().getRawClass();
    } else if (SimpleType.class.isAssignableFrom(javaType.getClass())) {
      if (Iterable.class.isAssignableFrom(javaType.getRawClass())) {
        rawClass = javaType.getBindings().getBoundType(0).getRawClass();
      } else {
        rawClass = javaType.getRawClass();
      }
    }

    return rawClass;
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    return ConverterConfiguration.isEligibleType(clazz);
  }

  @Override
  public Object read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage)
      throws IOException {
    JavaType javaType = getJavaType(type, contextClass);

    if (Iterable.class.isAssignableFrom(javaType.getRawClass())) {
      JavaType itemType = javaType.getBindings().getBoundType(0);
      List<?> collection = resourceConverter
          .readDocumentCollection(inputMessage.getBody(), itemType.getRawClass()).get();
      return collection == null ? Collections.emptyList() : collection;
    } else {
      Object document =
          resourceConverter.readDocument(inputMessage.getBody(), javaType.getRawClass()).get();
      return document == null ? Optional.empty() : document;
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  protected void writeInternal(Object object, @Nullable Type type, HttpOutputMessage outputMessage)
      throws IOException {

    try {
      JSONAPIDocument jsonApiDocument;
      if (JSONAPIDocument.class.isAssignableFrom(object.getClass())) {
        jsonApiDocument = Objects.requireNonNull(JSONAPIDocument.class.cast(object));
      } else {
        jsonApiDocument = new JSONAPIDocument<>(object);
      }

      Object document = jsonApiDocument.get();
      if (document != null && Iterable.class.isAssignableFrom(document.getClass())) {
        outputMessage.getBody().write(resourceConverter.writeDocumentCollection(jsonApiDocument));
      } else {
        outputMessage.getBody().write(resourceConverter.writeDocument(jsonApiDocument));
      }
    } catch (DocumentSerializationException ex) {
      String message = ex.getMessage();
      message = message == null ? "" : message;
      throw new HttpMessageNotWritableException(message, ex);
    }
  }

}
