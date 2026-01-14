package com.dilanhansaja.ee.ejb.remote;

import lk.jiat.ee.core.model.User;

public interface UserAccountService {
    User getUserByEmail(String email);
    boolean saveUser(User user);
    boolean validateUser(String email, String password);
}
