package com.example.booking.service;

import com.example.booking.dto.request.DoctorRequest;
import com.example.booking.dto.request.UserRequest;
import com.example.booking.dto.response.DoctorResponse;
import com.example.booking.dto.response.UserResponse;
import com.example.booking.enums.UserRole;
import com.example.booking.enums.UserStatus;

import java.util.List;

/**
 * Service interface for Super Admin operations
 */
public interface SuperAdminService {

    /**
     * Create a new user (Admin, Staff, Patient)
     */
    UserResponse createUser(UserRequest userRequest, Long superAdminId);

    /**
     * Onboard a new doctor
     */
    DoctorResponse onboardDoctor(DoctorRequest doctorRequest, Long superAdminId);

    /**
     * Get all users
     */
    List<UserResponse> getAllUsers();

    /**
     * Get all doctors
     */
    List<DoctorResponse> getAllDoctors();

    /**
     * Get users by role
     */
    List<UserResponse> getUsersByRole(UserRole role);

    /**
     * Get user by ID
     */
    UserResponse getUserById(Long userId);

    /**
     * Get doctor by ID
     */
    DoctorResponse getDoctorById(Long doctorId);

    /**
     * Update user status
     */
    UserResponse updateUserStatus(Long userId, UserStatus status);

    /**
     * Update doctor status
     */
    DoctorResponse updateDoctorStatus(Long doctorId, UserStatus status);

    /**
     * Delete user
     */
    void deleteUser(Long userId);

    /**
     * Delete doctor
     */
    void deleteDoctor(Long doctorId);

    /**
     * Get dashboard statistics
     */
    DashboardStats getDashboardStats();

    /**
     * Inner class for dashboard statistics
     */
    class DashboardStats {
        private long totalUsers;
        private long totalDoctors;
        private long totalPatients;
        private long activeUsers;
        private long activeDoctors;
        private long pendingUsers;

        // Constructors
        public DashboardStats() {
        }

        public DashboardStats(long totalUsers, long totalDoctors, long totalPatients, 
                            long activeUsers, long activeDoctors, long pendingUsers) {
            this.totalUsers = totalUsers;
            this.totalDoctors = totalDoctors;
            this.totalPatients = totalPatients;
            this.activeUsers = activeUsers;
            this.activeDoctors = activeDoctors;
            this.pendingUsers = pendingUsers;
        }

        // Getters and Setters
        public long getTotalUsers() {
            return totalUsers;
        }

        public void setTotalUsers(long totalUsers) {
            this.totalUsers = totalUsers;
        }

        public long getTotalDoctors() {
            return totalDoctors;
        }

        public void setTotalDoctors(long totalDoctors) {
            this.totalDoctors = totalDoctors;
        }

        public long getTotalPatients() {
            return totalPatients;
        }

        public void setTotalPatients(long totalPatients) {
            this.totalPatients = totalPatients;
        }

        public long getActiveUsers() {
            return activeUsers;
        }

        public void setActiveUsers(long activeUsers) {
            this.activeUsers = activeUsers;
        }

        public long getActiveDoctors() {
            return activeDoctors;
        }

        public void setActiveDoctors(long activeDoctors) {
            this.activeDoctors = activeDoctors;
        }

        public long getPendingUsers() {
            return pendingUsers;
        }

        public void setPendingUsers(long pendingUsers) {
            this.pendingUsers = pendingUsers;
        }
    }
}

