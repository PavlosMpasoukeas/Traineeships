package com.myy803.traineeship.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.myy803.traineeship.domainmodel.User;

@Service
public interface UserService extends UserDetailsService{
	public void saveUser(User user);
    public boolean isUserPresent(User user);
    public User findById(String username);
}
