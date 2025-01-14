package com.example.simple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.simple.config.CacheService;
import com.example.simple.entity.Users;
import com.example.simple.model.UsersDetails;
import com.example.simple.service.SimpleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class SimpleController implements SimpleControllerAPI {

	private static final String num = "\\d+"; // "[0-9]+"

	@Autowired
	SimpleService simpleService;

	@Autowired
	CacheService cacheService;

	@Override
	public List<UsersDetails> getAllUserDetails(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String sessionID = session.getId();

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

	@Override
	public ResponseEntity<String> loginByUserName(HttpServletRequest request) {

		ResponseEntity<String> response = null;
		String userUID = request.getHeader("userUID");
		String userName = request.getHeader("userName");
		String password = request.getHeader("password");

		HttpSession session = request.getSession();

		String session_userUID = (String) session.getAttribute(session.getId() + "USERUID_CACHE");

		String userUID_cache = (String) cacheService.getCache(session.getId(), "USERUID_CACHE");

		if (userUID.equals(session_userUID) || userUID.equals(userUID_cache)) {
			response = new ResponseEntity<String>("user login successfully : " + userUID_cache, HttpStatus.ACCEPTED);
		} else {
			response = new ResponseEntity<String>("userUID not match with cache : " + userUID_cache, HttpStatus.OK);
		}
		System.out.println("userUID input : " + userUID);
		System.out.println("userUID cache : " + userUID_cache);
		System.out.println("session userUID cache : " + session_userUID);

		return response;
	}
}
