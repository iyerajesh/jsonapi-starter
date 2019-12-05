package io.xylia.platform.jsonapi.examples.model;

import com.github.jasminb.jsonapi.Link;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;
import io.xylia.platform.jsonapi.configuration.model.BaseAggregate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@Type("user")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseAggregate implements Serializable {

    private static final Map<String, Link> linkMap = new HashMap<>();

    @Id
    private Long id;

    @NotNull
    private String username;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }
}
