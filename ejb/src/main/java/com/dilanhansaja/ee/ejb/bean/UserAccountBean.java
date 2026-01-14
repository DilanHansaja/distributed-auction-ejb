package com.dilanhansaja.ee.ejb.bean;


import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import lk.jiat.ee.core.model.User;
import com.dilanhansaja.ee.ejb.remote.UserAccountService;

import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class UserAccountBean implements UserAccountService {

    private ConcurrentHashMap<String,User> userMap = new ConcurrentHashMap<>();

    @Override
    @Lock(LockType.READ)
    public User getUserByEmail(String email) {

       if(userMap.containsKey(email)){
           return userMap.get(email);
       }
       return null;
    }

    @Override
    @Lock(LockType.WRITE)
    public boolean saveUser(User user) {

        if(userMap.containsKey(user.getEmail())) {
            return false;
        }else{
            userMap.put(user.getEmail(), user);
            return true;
        }
    }

    @Override
    @Lock(LockType.READ)
    public boolean validateUser(String email, String password) {

        System.out.println("MAP Size: " + userMap.size());

        if(userMap.containsKey(email)) {
            return userMap.get(email).getPassword().equals(password);
        }else{
            System.out.println("User not found");
            return false;
        }
    }
}
