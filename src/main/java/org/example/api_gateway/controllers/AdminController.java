package org.example.api_gateway.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.example.api_gateway.models.dto.AccountDTO;
import org.example.api_gateway.models.dto.FullUserRegisterRequest;
import org.example.api_gateway.models.dto.UserDTO;
import org.example.api_gateway.models.enums.Gender;
import org.example.api_gateway.models.enums.HairColor;
import org.example.api_gateway.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "Admin operations")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Register full user with profile",
            responses = @ApiResponse(responseCode = "200", description = "User created",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))))
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerFullUser(@RequestBody FullUserRegisterRequest request,
                                                    @RequestHeader("Authorization") String token) {
        String cleanToken = token.replace("Bearer ", "");
        UserDTO createdUser = adminService.registerFullUser(request, cleanToken);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Get list of users with optional filtering")
    @GetMapping("/users")
    public ResponseEntity<UserDTO[]> getUsersFilter(@RequestHeader("Authorization") String token,
                                                    @RequestParam(required = false) Gender gender,
                                                    @RequestParam(required = false) HairColor hairColor) {
        String cleanToken = token.replace("Bearer ", "");
        UserDTO[] users = adminService.getUsers(cleanToken, gender, hairColor);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@RequestHeader("Authorization") String token,
                                               @PathVariable Long id) {
        String cleanToken = token.replace("Bearer ", "");
        UserDTO user = adminService.getUserById(cleanToken, id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all accounts")
    @GetMapping("/accounts")
    public ResponseEntity<AccountDTO[]> getAllAccounts(@RequestHeader("Authorization") String token) {
        String cleanToken = token.replace("Bearer ", "");
        AccountDTO[] accounts = adminService.getAllAccounts(cleanToken);
        return ResponseEntity.ok(accounts);
    }

    @Operation(summary = "Get all accounts of a user by user ID")
    @GetMapping("/users/{id}/accounts")
    public ResponseEntity<AccountDTO[]> getUserAccounts(@RequestHeader("Authorization") String token,
                                                        @PathVariable Long id) {
        String cleanToken = token.replace("Bearer ", "");
        AccountDTO[] accounts = adminService.getUserAccounts(cleanToken, id);
        return ResponseEntity.ok(accounts);
    }

    @Operation(summary = "Get account details by account ID")
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccountDetails(@RequestHeader("Authorization") String token,
                                                        @PathVariable Long id) {
        String cleanToken = token.replace("Bearer ", "");
        AccountDTO account = adminService.getAccountDetails(cleanToken, id);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<String> createAccount(@RequestHeader("Authorization") String token,
                                                @PathVariable("id") Long ownerId) {
        String response = adminService.createAccount(token, ownerId);
        return ResponseEntity.ok(response);
    }
}
