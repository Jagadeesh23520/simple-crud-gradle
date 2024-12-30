package com.example.simple.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simple.entity.Users;
import com.example.simple.model.UsersDetails;
import com.example.simple.repository.UserRepository;

@Service
public class SimpleServiceImpl implements SimpleService {

	@Autowired
	UserRepository userRepo;

	@Override
	public List<UsersDetails> getAllUsersDetails() {
		List<UsersDetails> usersDetailsList = new ArrayList<>();

		List<Users> usersList = userRepo.findAll();

		usersList.stream().forEach(users -> {
			UsersDetails userDetails = new UsersDetails();
			userDetails.setId(users.getId());
			userDetails.setAddress(users.getAddress());
			userDetails.setUserUID(users.getUserUID());
			userDetails.setPhoneNo(users.getPhoneNo());
			userDetails.setUserName(users.getUserName());

			usersDetailsList.add(userDetails);
		});
		return usersDetailsList;
	}

	@Override
	public Users addUsersDetails(UsersDetails usersDetails) {
		Users users = new Users();

		users.setUserName(usersDetails.getUserName());
		users.setAddress(usersDetails.getAddress());
		users.setPhoneNo(usersDetails.getPhoneNo());

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
		String uuid = LocalDateTime.now().format(format);
		users.setUserUID(uuid);

		Users saveResponse = userRepo.save(users);

		return saveResponse;

	}

}
