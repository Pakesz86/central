package com.pakesz.central.controller;

import com.pakesz.central.dto.PasswordChangeReq;
import com.pakesz.central.dto.SuccessResp;
import com.pakesz.central.entity.User;
import com.pakesz.central.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/user")
@Tag(name="User", description="the endpoints for all authenticated users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary="Details of current user", description="Returns with id, name and email of current user.")
    @GetMapping("/me")
    public ResponseEntity<User> getDetails() {
        var currentUser = userService.loadCurrentUser();

        return ResponseEntity.ok(currentUser);
    }

    @Operation(summary="Change password", description="If the old password is correct, updates the users password to " +
            "the new one.")
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description="Password changed successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "string"),
                    examples = @ExampleObject(value = "Password changed successfully")
            )),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"errors\":[\"Current password is incorrect.\"]}")
            ))
    })
    @PutMapping("/me/change-password")
    public ResponseEntity<SuccessResp> changePassword(@Valid @RequestBody PasswordChangeReq passwordChangeRequest) {
        var currentUser = userService.loadCurrentUser();

        userService.changePassword(currentUser, passwordChangeRequest);

        return ResponseEntity.ok(new SuccessResp(true, "Password changed successfully"));
    }
}
