package com.dilanhansaja.ee.ejb.bean;


import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lk.jiat.ee.core.model.User;
import com.dilanhansaja.ee.ejb.remote.UserAccountService;
import com.dilanhansaja.ee.ejb.remote.UserService;

@Stateless
public class UserSessionManagerBean implements UserService {

    @EJB
    private UserAccountService userAccountService;

    @Override
    public String register(String name, String email, String password, String confirmPassword) {

        if (name == null || name.trim().isEmpty()) {
            return "Name is required.";
        }

        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            return "Invalid email address.";
        }

        if (password == null || password.length() < 6) {
            return "Password must be at least 6 characters.";
        }

        if (!confirmPassword.equals(password)) {
            return "Passwords do not match.";
        }

        User newUser = new User(name, email, password);

        if(userAccountService.saveUser(newUser)){
            return "Success";
        }

        return "Email already exists. Please use different email or try login";

    }

    @Override
    public String login(String email, String password) {

        if(userAccountService.validateUser(email, password)) {
            return "Success";
        }else{
            return "Invalid email or password.";
        }

    }

}
