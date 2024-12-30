package com.example.simple.service;

import java.util.List;

import com.example.simple.entity.Users;
import com.example.simple.model.UsersDetails;

public interface SimpleService {

	List<UsersDetails> getAllUsersDetails();

	Users addUsersDetails(UsersDetails usersDetails);
}
