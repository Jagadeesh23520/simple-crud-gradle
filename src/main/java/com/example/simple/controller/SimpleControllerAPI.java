package com.example.simple.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.simple.entity.Users;
import com.example.simple.model.UsersDetails;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/v1")
public interface SimpleControllerAPI {

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "get all Users Details", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ArrayList.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content) })
	@GetMapping(value = "/getUsers", produces = "application/json")
	public List<UsersDetails> getAllUserDetails(HttpServletRequest request);

	@PostMapping(value = "/addUser", produces = "application/json")
	public Users addUserDetails(@RequestBody UsersDetails usersDetails);

	@GetMapping(value = "/search")
	public ResponseEntity<Object> searchByName(@RequestParam(required = true, value = "name") String userName);

	@PutMapping(value = "/update")
	public ResponseEntity<Object> updateUserDetails(@RequestParam(required = true, value = "userUID") String useruid,
			@RequestBody UsersDetails usersDetails);

	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteUserDetails(@RequestParam(required = true, value = "userUID") String useruid);

	@GetMapping(value = "/userlogin")
	public ResponseEntity<String> loginByUserName(HttpServletRequest request);

}
