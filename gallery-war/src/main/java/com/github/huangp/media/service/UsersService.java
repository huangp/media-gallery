package com.github.huangp.media.service;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.github.huangp.media.model.User;

public interface UsersService {

    @SuppressWarnings("unchecked")
    @Produces
    @Named("users")
    @RequestScoped
    List<User> getUsers() throws Exception;

    String addUser() throws Exception;

    User getNewUser();

    void setNewUser(User newUser);

    Optional<User> getUser(String username, String password);
}
