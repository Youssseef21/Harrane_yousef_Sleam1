package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void saveStudent_returnsSavedStudent() {
        Student s = new Student();
        s.setFirstName("Alice");

        when(studentRepository.save(s)).thenReturn(s);

        Student saved = studentService.saveStudent(s);

        assertNotNull(saved);
        assertEquals("Alice", saved.getFirstName());
        verify(studentRepository).save(s);
    }
}
