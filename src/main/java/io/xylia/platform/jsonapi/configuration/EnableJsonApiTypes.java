package io.xylia.platform.jsonapi.configuration;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JsonApiHttpMessageConvertersConfiguration.class)
public @interface EnableJsonApiTypes {

    /**
     * Allows for more concise annotation declaration if no other attributes are needed
     * For example @ComponentScan(basePackages = "org.my.pkg")
     *
     * @return
     */
    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};


}
