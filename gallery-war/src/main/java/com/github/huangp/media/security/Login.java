package com.github.huangp.media.security;

import java.io.Serializable;
import java.util.Optional;
//import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.huangp.media.model.User;
import com.github.huangp.media.service.UsersService;

/**
 * @author Patrick Huang
 *         <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@Named
@SessionScoped
public class Login implements Serializable {
    @Inject
    private Credentials credentials;

//    @EJB
    private UsersService usersManager;

    private User currentUser;

    public void login() {
        Optional<User> user = usersManager.getUser(credentials.getUsername(),
                credentials.getPassword());
        if (user.isPresent()) {
            currentUser = user.get();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Welcome, " + currentUser.getName()));
        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("invalid username and password"));
        }
    }

    public void logout() {
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Goodbye, " + currentUser.getName()));
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    @Produces
    @LoggedIn
    public User getCurrentUser() {
        return currentUser;
    }
}
