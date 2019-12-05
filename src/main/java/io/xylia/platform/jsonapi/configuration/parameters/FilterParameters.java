package io.xylia.platform.jsonapi.configuration.parameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * A request param bean which contains the filter parameters.
 */
@Getter
@Setter
public class FilterParameters {

    private Map<String, Map<FilterOperator, Set<String>>> filters = new HashMap<>();

    @Override
    public String toString() {
        return filters.toString();
    }

}
