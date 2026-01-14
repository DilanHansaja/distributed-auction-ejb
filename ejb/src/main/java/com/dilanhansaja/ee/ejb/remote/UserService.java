package com.dilanhansaja.ee.ejb.remote;

import jakarta.ejb.Remote;
import lk.jiat.ee.core.model.User;

@Remote
public interface UserService {
    String register(String name, String email, String password,String confirmPassword);
    String login(String email, String password);
}
