package com.example.booking.repository;

import com.example.booking.entity.Doctor;
import com.example.booking.enums.Specialization;
import com.example.booking.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Doctor entity
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Find doctor by user ID
     */
    Optional<Doctor> findByUserId(Long userId);

    /**
     * Find doctor by license number
     */
    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    /**
     * Find doctors by specialization
     */
    List<Doctor> findBySpecialization(Specialization specialization);

    /**
     * Find doctors by status
     */
    List<Doctor> findByStatus(UserStatus status);

    /**
     * Find doctors by specialization and status
     */
    List<Doctor> findBySpecializationAndStatus(Specialization specialization, UserStatus status);

    /**
     * Check if license number exists
     */
    boolean existsByLicenseNumber(String licenseNumber);

    /**
     * Find all active doctors
     */
    @Query("SELECT d FROM Doctor d WHERE d.status = 'ACTIVE' ORDER BY d.user.firstName")
    List<Doctor> findAllActiveDoctors();

    /**
     * Search doctors by name
     */
    @Query("SELECT d FROM Doctor d WHERE " +
           "LOWER(d.user.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.user.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Doctor> searchByName(@Param("searchTerm") String searchTerm);
}

