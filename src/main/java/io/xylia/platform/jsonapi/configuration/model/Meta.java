package io.xylia.platform.jsonapi.configuration.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Meta is optional, one does not have to define it or use it
 */

@Data
@NoArgsConstructor
public class Meta {
    private String copyright;
    private String author;
}
