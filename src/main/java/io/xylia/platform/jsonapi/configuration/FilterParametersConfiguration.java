package io.xylia.platform.jsonapi.configuration;

import java.util.List;

import io.xylia.platform.jsonapi.configuration.parameters.RequestParamFilterParametersMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterParametersConfiguration implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new RequestParamFilterParametersMethodArgumentResolver());
  }

}
