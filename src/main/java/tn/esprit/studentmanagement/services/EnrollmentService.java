package tn.esprit.studentmanagement.services;

import org.springframework.stereotype.Service;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService implements IEnrollment {

    private final EnrollmentRepository enrollmentRepository;

    // Constructor injection
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment getEnrollmentById(Long idEnrollment) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(idEnrollment);
        return optionalEnrollment.orElse(null); // or throw a custom NotFoundException
    }

    @Override
    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteEnrollment(Long idEnrollment) {
        enrollmentRepository.deleteById(idEnrollment);
    }
}
