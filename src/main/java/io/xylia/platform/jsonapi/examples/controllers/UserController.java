package io.xylia.platform.jsonapi.examples.controllers;

import io.xylia.platform.jsonapi.configuration.parameters.FilterParameters;
import io.xylia.platform.jsonapi.examples.model.User;
import io.xylia.platform.jsonapi.exceptions.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static Map<Long, User> users = new ConcurrentHashMap<>();

    @PostMapping
    public User create(@RequestBody @Valid User user) {

        log.info("Create user [{}, {}]", user.getUsername(), user.getEmail());
        if (user.getId() == null)
            user.setId(ID_GENERATOR.getAndIncrement());

        if (users.values().stream().map(User::getUsername).anyMatch(username -> username.equals(user.getUsername()))) {
            throw new UnprocessableEntityException("name", "Username " + user.getUsername() + " already exists!");
        }

        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public List<User> filter(FilterParameters filterParameters) {
//        Set<String> usernames = filterParameters.getFilters().getOrDefault("username", Collections.emptyMap().getOrDefault(FilterOperator.EQ, Collections.emptySet()));
        Set<String> usernames = Collections.emptySet();

        if (!usernames.isEmpty())
            return users.values().stream().filter(user -> usernames.contains(user.getUsername())).collect(Collectors.toList());

        return users.values().stream().collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public User findUserById(@PathVariable("id") long id) {
        return users.get(id);
    }

}
