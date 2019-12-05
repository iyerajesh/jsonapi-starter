package io.xylia.platform.jsonapi.configuration.converters;

import com.github.jasminb.jsonapi.models.errors.Error;

/**
 * Convert an {@link Object} in a {@link Error}.
 * 
 *
 * @param <S> the source of error
 */
public interface ErrorConverter<S> {

  Error convert(S source);

}
