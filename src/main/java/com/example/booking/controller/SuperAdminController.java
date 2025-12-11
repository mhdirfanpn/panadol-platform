package com.example.booking.controller;

import com.example.booking.dto.request.DoctorRequest;
import com.example.booking.dto.request.UserRequest;
import com.example.booking.dto.response.DoctorResponse;
import com.example.booking.dto.response.UserResponse;
import com.example.booking.enums.UserRole;
import com.example.booking.enums.UserStatus;
import com.example.booking.service.SuperAdminService;
import com.example.booking.service.SuperAdminService.DashboardStats;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Super Admin operations
 * Handles user management, doctor onboarding, and system administration
 */
@RestController
@RequestMapping("/api/super-admin")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    /**
     * Create a new user (Admin, Staff, Patient)
     * POST /api/super-admin/users
     */
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest userRequest,
            @RequestHeader(value = "X-User-Id", required = false, defaultValue = "1") Long superAdminId) {
        UserResponse response = superAdminService.createUser(userRequest, superAdminId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Onboard a new doctor
     * POST /api/super-admin/doctors
     */
    @PostMapping("/doctors")
    public ResponseEntity<DoctorResponse> onboardDoctor(
            @Valid @RequestBody DoctorRequest doctorRequest,
            @RequestHeader(value = "X-User-Id", required = false, defaultValue = "1") Long superAdminId) {
        DoctorResponse response = superAdminService.onboardDoctor(doctorRequest, superAdminId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get all users
     * GET /api/super-admin/users
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = superAdminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get all doctors
     * GET /api/super-admin/doctors
     */
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        List<DoctorResponse> doctors = superAdminService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    /**
     * Get users by role
     * GET /api/super-admin/users/role/{role}
     */
    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable UserRole role) {
        List<UserResponse> users = superAdminService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by ID
     * GET /api/super-admin/users/{userId}
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse user = superAdminService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Get doctor by ID
     * GET /api/super-admin/doctors/{doctorId}
     */
    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long doctorId) {
        DoctorResponse doctor = superAdminService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctor);
    }

    /**
     * Update user status
     * PATCH /api/super-admin/users/{userId}/status
     */
    @PatchMapping("/users/{userId}/status")
    public ResponseEntity<UserResponse> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam UserStatus status) {
        UserResponse response = superAdminService.updateUserStatus(userId, status);
        return ResponseEntity.ok(response);
    }

    /**
     * Update doctor status
     * PATCH /api/super-admin/doctors/{doctorId}/status
     */
    @PatchMapping("/doctors/{doctorId}/status")
    public ResponseEntity<DoctorResponse> updateDoctorStatus(
            @PathVariable Long doctorId,
            @RequestParam UserStatus status) {
        DoctorResponse response = superAdminService.updateDoctorStatus(doctorId, status);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete user
     * DELETE /api/super-admin/users/{userId}
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        superAdminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete doctor
     * DELETE /api/super-admin/doctors/{doctorId}
     */
    @DeleteMapping("/doctors/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorId) {
        superAdminService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get dashboard statistics
     * GET /api/super-admin/dashboard/stats
     */
    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        DashboardStats stats = superAdminService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}

