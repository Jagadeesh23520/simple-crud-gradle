package com.example.simple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.simple.entity.Users;
import com.example.simple.model.UsersDetails;
import com.example.simple.service.SimpleService;

@RestController
public class SimpleController implements SimpleControllerAPI {

	private static final String num = "\\d+"; //"[0-9]+"

	@Autowired
	SimpleService simpleService;

	@Override
	public List<UsersDetails> getAllUserDetails() {

		return simpleService.getAllUsersDetails();
	}

	@Override
	public Users addUserDetails(UsersDetails usersDetails) {

		return simpleService.addUsersDetails(usersDetails);
	}

	@Override
	public ResponseEntity<Object> searchByName(String name) {
		ResponseEntity<Object> usersDetailsResponse = null;

		try {
			List<UsersDetails> userDetailsList = simpleService.searchByUserName(name);
			if (!userDetailsList.isEmpty()) {
				usersDetailsResponse = new ResponseEntity<Object>(userDetailsList, HttpStatus.OK);
			} else {
				usersDetailsResponse = new ResponseEntity<Object>("Username Not Found : " + name, HttpStatus.OK);
			}
		} catch (Exception e) {
			usersDetailsResponse = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return usersDetailsResponse;

	}

	@Override
	public ResponseEntity<Object> updateUserDetails(String useruid, UsersDetails usersDetails) {
		ResponseEntity<Object> usersDetailsResponse = null;
		try {
			if (useruid.matches(num)) {
				Users updateUsersdetails = simpleService.updateUsersdetails(useruid, usersDetails);
				usersDetailsResponse = new ResponseEntity<Object>(updateUsersdetails, HttpStatus.OK);
			} else {
				usersDetailsResponse = new ResponseEntity<Object>("UserUID must be Integer", HttpStatus.OK);
			}

		} catch (Exception e) {
			String message = e.getMessage();
			if (message.contains("Not Found")) {
				usersDetailsResponse = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
			} else {
				usersDetailsResponse = new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		return usersDetailsResponse;
	}

	@Override
	public ResponseEntity<String> deleteUserDetails(String useruid) {
		ResponseEntity<String> usersDetailsResponse = null;
		try {
			if (useruid.matches(num)) {
				String message = simpleService.deleteByUser(useruid);
				usersDetailsResponse = new ResponseEntity<String>(message, HttpStatus.OK);
			} else {
				usersDetailsResponse = new ResponseEntity<String>("UserUID must be Integer", HttpStatus.OK);
			}

		} catch (Exception e) {
			String message = e.getMessage();
			if (message.contains("Not Found")) {
				usersDetailsResponse = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			} else {
				usersDetailsResponse = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return usersDetailsResponse;
	}

}
