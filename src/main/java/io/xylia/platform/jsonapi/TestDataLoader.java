package io.xylia.platform.jsonapi;

import io.xylia.platform.jsonapi.examples.controllers.UserController;
import io.xylia.platform.jsonapi.examples.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Configuration
public class TestDataLoader {

    @Autowired
    private UserController userController;

    @PostConstruct
    public void setup() {
        IntStream.rangeClosed(1, 2).mapToObj(i -> {
            User user = new User();
            user.setUsername("user" + i);
            user.setEmail("user" + i + "@domain.com");
            return user;
        }).forEach(userController::create);
    }
}
