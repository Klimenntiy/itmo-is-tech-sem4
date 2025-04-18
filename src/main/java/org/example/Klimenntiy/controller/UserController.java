package org.example.Klimenntiy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.Klimenntiy.dto.UserDTO;
import org.example.Klimenntiy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Add a friend to the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friend added successfully"),
            @ApiResponse(responseCode = "404", description = "User or friend not found")
    })
    @PostMapping("/{login}/friends/{friendLogin}")
    public ResponseEntity<String> addFriend(@PathVariable String login, @PathVariable String friendLogin) {
        userService.addFriend(login, friendLogin);
        return ResponseEntity.ok("Friend added successfully");
    }

    @Operation(summary = "Remove a friend from the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friend removed successfully"),
            @ApiResponse(responseCode = "404", description = "User or friend not found")
    })
    @DeleteMapping("/{login}/friends/{friendLogin}")
    public ResponseEntity<String> removeFriend(@PathVariable String login, @PathVariable String friendLogin) {
        userService.removeFriend(login, friendLogin);
        return ResponseEntity.ok("Friend removed successfully");
    }

    @Operation(summary = "Get a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get a list of all friends for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friends retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<UserDTO>> getFriendsByUserId(@PathVariable Long userId) {
        List<UserDTO> friends = userService.getFriendsByUserId(userId);
        return ResponseEntity.ok(friends);
    }

    @Operation(summary = "Get a list of all users with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<UserDTO>> getUsersWithFilters(@RequestParam(required = false) String hairColor,
                                                             @RequestParam(required = false) String gender) {
        List<UserDTO> users = userService.getUsersWithFilters(hairColor, gender);
        return ResponseEntity.ok(users);
    }
}
