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

import jakarta.validation.ValidationException;

@Service
public class SimpleServiceImpl implements SimpleService {

	@Autowired
	UserRepository userRepo;

	@Override
	public List<UsersDetails> getAllUsersDetails() {
		List<Users> usersList = userRepo.findAll();

		List<UsersDetails> usersDetailsList = userDetailsMapping(usersList);
		return usersDetailsList;
	}

	private List<UsersDetails> userDetailsMapping(List<Users> usersList) {
		List<UsersDetails> usersDetailsList = new ArrayList<>();

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

	@Override
	public List<UsersDetails> searchByUserName(String name) {

		if (name.length() < 3) {
			throw new ValidationException("user Name Required atleast 3 character");
		}

		List<Users> usersList = userRepo.searchByUserName(name);

		List<UsersDetails> usersDetailsList = userDetailsMapping(usersList);
		return usersDetailsList;
	}

	@Override
	public Users updateUsersdetails(String userUID, UsersDetails usersdetails) {
		Users existingUsers = null;
		existingUsers = userRepo.findByUserUID(userUID);

		if (existingUsers != null) {
			existingUsers.setUserName(
					usersdetails.getUserName() != null ? usersdetails.getUserName() : existingUsers.getUserName());
			existingUsers.setAddress(
					usersdetails.getAddress() != null ? usersdetails.getAddress() : existingUsers.getAddress());
			existingUsers.setPhoneNo(
					usersdetails.getPhoneNo() != null ? usersdetails.getPhoneNo() : existingUsers.getPhoneNo());

			return userRepo.saveAndFlush(existingUsers);

		} else {
			throw new ValidationException("UserUID Not Found : " + userUID);
		}
	}

	@Override
	public String deleteByUser(String userUID) {

		Users existingUsers = null;
		existingUsers = userRepo.findByUserUID(userUID);

		if (existingUsers != null && existingUsers.getUserUID() != null) {

			userRepo.deleteById(existingUsers.getId());

			return "successfully deleted the user : " + existingUsers.getUserName();
		} else {
			throw new ValidationException("UserUID Not Found : " + userUID);
		}
	}

}
