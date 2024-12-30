package com.example.simple.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.simple.entity.Users;
import com.example.simple.model.UsersDetails;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/v1")
public interface SimpleControllerAPI {

	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "get all Users Details", 
					content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ArrayList.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content), 
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
	@GetMapping(value = "/getUsers", produces = "application/json")
	public List<UsersDetails> getAllUserDetails();

	@PostMapping(value = "/addUser", produces = "application/json")
	public Users addUserDetails(@RequestBody UsersDetails usersDetails);

}
