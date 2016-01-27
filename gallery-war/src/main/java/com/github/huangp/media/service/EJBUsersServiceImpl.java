package com.github.huangp.service;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import com.github.huangp.model.User;
import com.google.common.base.Optional;

import static org.slf4j.LoggerFactory.*;

@Named("usersService")
@RequestScoped
@Stateful
public class EJBUsersServiceImpl implements UsersService {
    private static final Logger log =
            getLogger(EJBUsersServiceImpl.class);

    @Inject
    private EntityManager entityManager;

    private User newUser = new User();

    @Override
    @SuppressWarnings("unchecked")
    @Produces
    @Named("users")
    @RequestScoped
    public List<User> getUsers() throws Exception {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public String addUser() throws Exception {
        entityManager.persist(newUser);
        log.info("Added {}", newUser);
        return "/users?faces-redirect=true";
    }

    @Override
    public User getNewUser() {
        return newUser;
    }

    @Override
    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    @Override
    public Optional<User> getUser(String username, String password) {
        List<User> resultList = entityManager.createQuery(
                "from User u where u.username = :username and u.password = :password",
                User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
        if (resultList.isEmpty()) {
            return Optional.absent();
        }
        return Optional.of(resultList.get(0));
    }

    @Remove
    public void remove() {
    }

}
