package com.dilanhansaja.ee.ejb.bean;

import jakarta.ejb.Stateless;
import com.dilanhansaja.ee.ejb.remote.AdminService;

@Stateless
public class AdminManagerBean implements AdminService {

    @Override
    public boolean validate(String username, String password) {
        return "admin".equals(username) && "1234".equals(password);
    }
}
