package com.github.huangp.service;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.github.huangp.model.User;
import com.google.common.base.Optional;

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
