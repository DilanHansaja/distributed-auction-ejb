package com.dilanhansaja.ee.ejb.remote;

import jakarta.ejb.Remote;

@Remote
public interface AdminService {
    boolean validate(String username, String password);
}
