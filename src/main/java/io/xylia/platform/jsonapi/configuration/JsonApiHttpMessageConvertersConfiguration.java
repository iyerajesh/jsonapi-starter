package io.xylia.platform.jsonapi.configuration;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.github.jasminb.jsonapi.ResourceConverter;

import java.util.Map;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

/**
 * Configuration for HTTP Message converters that use json api.
 */
@Configuration
@ConditionalOnClass(ResourceConverter.class)
@ConditionalOnProperty(name = JsonApiHttpMessageConvertersConfiguration.PREFERRED_MAPPER_PROPERTY,
        havingValue = "json-api")
@Import({JsonApiResponseEntityExceptionHandler.class, FilterParametersConfiguration.class})
public class JsonApiHttpMessageConvertersConfiguration {

    @Autowired
    private ListableBeanFactory listableBeanFactory;

    static final String PREFERRED_MAPPER_PROPERTY = "spring.http.converters.preferred-json-mapper";

    @Bean
    public HttpMessageConverters jsonApiHttpMessageConverter() {
        return new HttpMessageConverters(new JsonApiHttpMessageConverter());
    }

//    @Bean
//    public HttpMessageConverters jsonApiHttpMessageConverter() {
//
//        Map<String, Object> annotatedElements = listableBeanFactory.getBeansWithAnnotation(EnableJsonApiTypes.class);
//
//        if (annotatedElements.isEmpty())
//            // do nothing, proceed with the existing default HttpMessageConverters
//            return null;
//
//        if (annotatedElements.size() > 1)
//            throw new IllegalArgumentException("More than one EnableJsonApiTypes annotation is not allowed in the classpath!");
//
//        String bean = requireNonNull(annotatedElements.keySet().iterator().next());
//        String packageName = requireNonNull(annotatedElements.values().stream().findFirst().get().getClass().getName());
//        return new HttpMessageConverters(new JsonApiHttpMessageConverter(packageName, findEnableJsonApiTypesAnnotation(bean)));
//
//    }

    private EnableJsonApiTypes findEnableJsonApiTypesAnnotation(String bean) {
        return requireNonNull(listableBeanFactory.findAnnotationOnBean(bean, EnableJsonApiTypes.class));
    }
}
