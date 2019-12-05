package io.xylia.platform.jsonapi.configuration.model;

import com.github.jasminb.jsonapi.Link;
import com.github.jasminb.jsonapi.annotations.Meta;

import java.util.HashMap;
import java.util.Map;

public class BaseAggregate {

    private static final Map<String, Link> linkMap = new HashMap<>();

    @Meta
    private io.xylia.platform.jsonapi.configuration.model.Meta meta;
}
