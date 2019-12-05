package io.xylia.platform.jsonapi.configuration;

import static io.xylia.platform.jsonapi.configuration.JsonApiHttpMessageConverter.APPLICATION_JSON_API;
import static org.assertj.core.api.Assertions.assertThat;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import io.xylia.platform.jsonapi.dummy.app.controllers.DummyUserController;
import io.xylia.platform.jsonapi.dummy.app.data.DummyUserData;

/**
 * Unit test of {@link JsonApiHttpMessageConverter}.
 * 
 *
 */
public class JsonApiHttpMessageConverterUnitTest {

  @Test
  public void canReadUserDataTest() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    Class<?> type = DummyUserData.class;
    Class<?> contextClass = DummyUserController.class;

    // When
    boolean canRead = jsonApiHttpMessageConverter.canRead(type, contextClass, null);

    // Then
    assertThat(canRead).isTrue();
  }

  @Test
  public void canReadIterableOfUserDataTests() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    ParameterizedType type = TypeUtils.parameterize(Iterable.class, DummyUserData.class);
    Class<?> contextClass = ArrayList.class;

    // When
    boolean canRead = jsonApiHttpMessageConverter.canRead(type, contextClass, null);

    // Then
    assertThat(canRead).isTrue();
  }

  @Test
  public void canReadCollectionOfUserDataTests() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    ParameterizedType type = TypeUtils.parameterize(Collection.class, DummyUserData.class);
    Class<?> contextClass = ArrayList.class;

    // When
    boolean canRead = jsonApiHttpMessageConverter.canRead(type, contextClass, null);

    // Then
    assertThat(canRead).isTrue();
  }

  @Test
  public void canReadJsonApiMediaType() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    Class<?> clazz = DummyUserData.class;
    MediaType jsonApiMediaType = APPLICATION_JSON_API;

    // When
    boolean canRead = jsonApiHttpMessageConverter.canRead(clazz, clazz, jsonApiMediaType);

    // Then
    assertThat(canRead).isTrue();
  }

  @Test
  public void cannotReadObject() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    Class<?> type = Object.class;
    Class<?> contextClass = DummyUserController.class;

    // When
    boolean canRead = jsonApiHttpMessageConverter.canRead(type, contextClass, null);

    // Then
    assertThat(canRead).isFalse();
  }

  @Test
  public void cannotReadIterableOfObjects() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    ParameterizedType type = TypeUtils.parameterize(Iterable.class, Object.class);
    Class<?> contextClass = ArrayList.class;

    // When
    boolean canRead = jsonApiHttpMessageConverter.canRead(type, contextClass, null);

    // Then
    assertThat(canRead).isFalse();
  }

  @Test
  public void cannotReadCollectionOfObjects() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    ParameterizedType type = TypeUtils.parameterize(Collection.class, Object.class);
    Class<?> contextClass = ArrayList.class;

    // When
    boolean canRead = jsonApiHttpMessageConverter.canRead(type, contextClass, null);

    // Then
    assertThat(canRead).isFalse();
  }

  @Test
  public void cannotReadJsonMediaType() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    Class<?> clazz = DummyUserData.class;
    MediaType jsonMediaType = MediaType.APPLICATION_JSON;

    // When
    boolean canRead = jsonApiHttpMessageConverter.canRead(clazz, clazz, jsonMediaType);

    // Then
    assertThat(canRead).isFalse();
  }

  @Test
  public void canWriteUserDataTest() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    Class<?> clazz = DummyUserData.class;

    // When
    boolean canWrite = jsonApiHttpMessageConverter.canWrite(clazz, clazz, null);

    // Then
    assertThat(canWrite).isTrue();
  }

  @Test
  public void canWriteIterableOfUserDataTests() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    ParameterizedType type = TypeUtils.parameterize(Iterable.class, DummyUserData.class);
    Class<?> contextClass = ArrayList.class;

    // When
    boolean canWrite = jsonApiHttpMessageConverter.canWrite(type, contextClass, null);

    // Then
    assertThat(canWrite).isTrue();
  }

  @Test
  public void canWriteCollectionOfUserDataTests() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    ParameterizedType type = TypeUtils.parameterize(Collection.class, DummyUserData.class);
    Class<?> contextClass = ArrayList.class;

    // When
    boolean canWrite = jsonApiHttpMessageConverter.canWrite(type, contextClass, null);

    // Then
    assertThat(canWrite).isTrue();
  }

  public void canWriteJsonApiMediaType() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    Class<?> clazz = DummyUserData.class;
    MediaType jsonApiMediaType = APPLICATION_JSON_API;

    // When
    boolean canWrite = jsonApiHttpMessageConverter.canWrite(clazz, clazz, jsonApiMediaType);

    // Then
    assertThat(canWrite).isTrue();
  }

  @Test
  public void cannotWriteObject() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    Class<?> clazz = Object.class;

    // When
    boolean canWrite = jsonApiHttpMessageConverter.canWrite(clazz, clazz, null);

    // Then
    assertThat(canWrite).isFalse();
  }

  @Test
  public void cannotWriteIterableOfObjects() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    ParameterizedType type = TypeUtils.parameterize(Iterable.class, Object.class);
    Class<?> contextClass = ArrayList.class;

    // When
    boolean canWrite = jsonApiHttpMessageConverter.canWrite(type, contextClass, null);

    // Then
    assertThat(canWrite).isFalse();
  }

  @Test
  public void cannotWriteCollectionOfObjects() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    ParameterizedType type = TypeUtils.parameterize(Collection.class, Object.class);
    Class<?> contextClass = ArrayList.class;

    // When
    boolean canWrite = jsonApiHttpMessageConverter.canWrite(type, contextClass, null);

    // Then
    assertThat(canWrite).isFalse();
  }

  @Test
  public void cannotWriteJsonMediaType() {
    // Given
    JsonApiHttpMessageConverter jsonApiHttpMessageConverter = new JsonApiHttpMessageConverter();
    Class<?> clazz = DummyUserData.class;
    MediaType jsonMediaType = MediaType.APPLICATION_JSON;

    // When
    boolean canWrite = jsonApiHttpMessageConverter.canWrite(clazz, clazz, jsonMediaType);

    // Then
    assertThat(canWrite).isFalse();
  }
}
