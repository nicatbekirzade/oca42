package com.example.oca42.controller;

import com.example.oca42.model.AddressRequestDto;
import com.example.oca42.model.UserCreateRequestDto;
import com.example.oca42.model.UserResponseDto;
import com.example.oca42.model.UserUpdateRequestDto;
import com.example.oca42.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    //get all users

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userManagementService.getAllUsers();
    }

    @PatchMapping("/{userId}/address")
    public void addNewAddressToUser(
            @PathVariable Long userId,
            @RequestBody AddressRequestDto requestDto) {
        userManagementService.addNewAddressToUser(userId, requestDto);
    }

    @DeleteMapping("/{userId}/address/{addressId}")
    public void removeUserAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId) {
        userManagementService.removeUserAddress(userId, addressId);
    }

    @PutMapping("/{id}")
    public void updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userManagementService.updateUser(id, userUpdateRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userManagementService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @DeleteMapping("/contact/{id}")
    public void deleteContact(@PathVariable Long id) {
        userManagementService.deleteContact(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
    }

    @PostMapping
    public void create(@RequestBody UserCreateRequestDto requestDto) {
        userManagementService.create(requestDto);
    }
}
