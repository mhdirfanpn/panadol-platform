package com.example.booking.service.impl;

import com.example.booking.dto.request.DoctorRequest;
import com.example.booking.dto.request.UserRequest;
import com.example.booking.dto.response.DoctorResponse;
import com.example.booking.dto.response.UserResponse;
import com.example.booking.entity.Doctor;
import com.example.booking.entity.User;
import com.example.booking.enums.UserRole;
import com.example.booking.enums.UserStatus;
import com.example.booking.repository.DoctorRepository;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.SuperAdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of SuperAdminService
 */
@Service
@Transactional
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public SuperAdminServiceImpl(UserRepository userRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest, Long superAdminId) {
        // Validate email and username uniqueness
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Create user entity
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword()); // TODO: Encrypt password
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedBy(superAdminId);

        // Save user
        User savedUser = userRepository.save(user);

        // Convert to response
        return convertToUserResponse(savedUser);
    }

    @Override
    public DoctorResponse onboardDoctor(DoctorRequest doctorRequest, Long superAdminId) {
        // Validate email and username uniqueness
        if (userRepository.existsByEmail(doctorRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(doctorRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (doctorRepository.existsByLicenseNumber(doctorRequest.getLicenseNumber())) {
            throw new RuntimeException("License number already exists");
        }

        // Create user entity for doctor
        User user = getUser(doctorRequest, superAdminId);

        // Save user
        User savedUser = userRepository.save(user);

        // Create doctor entity
        Doctor doctor = new Doctor();
        doctor.setUser(savedUser);
        doctor.setSpecialization(doctorRequest.getSpecialization());
        doctor.setLicenseNumber(doctorRequest.getLicenseNumber());
        doctor.setExperienceYears(doctorRequest.getExperienceYears());
        doctor.setQualifications(doctorRequest.getQualifications());
        doctor.setBio(doctorRequest.getBio());
        doctor.setConsultationFee(doctorRequest.getConsultationFee());
        doctor.setStatus(UserStatus.ACTIVE);
        doctor.setCreatedBy(superAdminId);

        // Save doctor
        Doctor savedDoctor = doctorRepository.save(doctor);

        // Convert to response
        return convertToDoctorResponse(savedDoctor);
    }

    private static User getUser(DoctorRequest doctorRequest, Long superAdminId) {
        User user = new User();
        user.setFirstName(doctorRequest.getFirstName());
        user.setLastName(doctorRequest.getLastName());
        user.setEmail(doctorRequest.getEmail());
        user.setUsername(doctorRequest.getUsername());
        user.setPassword(doctorRequest.getPassword()); // TODO: Encrypt password
        user.setPhoneNumber(doctorRequest.getPhoneNumber());
        user.setRole(UserRole.DOCTOR);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedBy(superAdminId);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDoctorResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return convertToUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));
        return convertToDoctorResponse(doctor);
    }

    @Override
    public UserResponse updateUserStatus(Long userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setStatus(status);
        User updatedUser = userRepository.save(user);
        return convertToUserResponse(updatedUser);
    }

    @Override
    public DoctorResponse updateDoctorStatus(Long doctorId, UserStatus status) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));
        doctor.setStatus(status);
        doctor.getUser().setStatus(status);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return convertToDoctorResponse(updatedDoctor);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));
        
        // Delete doctor and associated user
        Long userId = doctor.getUser().getId();
        doctorRepository.deleteById(doctorId);
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStats getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalDoctors = doctorRepository.count();
        long totalPatients = userRepository.findByRole(UserRole.PATIENT).size();
        long activeUsers = userRepository.findByStatus(UserStatus.ACTIVE).size();
        long activeDoctors = doctorRepository.findByStatus(UserStatus.ACTIVE).size();
        long pendingUsers = userRepository.findByStatus(UserStatus.PENDING).size();

        return new DashboardStats(totalUsers, totalDoctors, totalPatients, 
                                 activeUsers, activeDoctors, pendingUsers);
    }

    // Helper methods to convert entities to DTOs
    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setCreatedAt(user.getCreatedAt());
        response.setLastLogin(user.getLastLogin());
        return response;
    }

    private DoctorResponse convertToDoctorResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setUserId(doctor.getUser().getId());
        response.setFirstName(doctor.getUser().getFirstName());
        response.setLastName(doctor.getUser().getLastName());
        response.setEmail(doctor.getUser().getEmail());
        response.setPhoneNumber(doctor.getUser().getPhoneNumber());
        response.setSpecialization(doctor.getSpecialization());
        response.setLicenseNumber(doctor.getLicenseNumber());
        response.setExperienceYears(doctor.getExperienceYears());
        response.setQualifications(doctor.getQualifications());
        response.setBio(doctor.getBio());
        response.setConsultationFee(doctor.getConsultationFee());
        response.setStatus(doctor.getStatus());
        response.setCreatedAt(doctor.getCreatedAt());
        return response;
    }
}

