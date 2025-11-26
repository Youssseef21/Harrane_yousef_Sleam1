package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void saveDepartment_returnsSavedEntity() {
        Department d = new Department();
        d.setName("IT");
        when(departmentRepository.save(d)).thenReturn(d);

        Department saved = departmentService.saveDepartment(d);

        assertNotNull(saved);
        assertEquals("IT", saved.getName());
        verify(departmentRepository).save(d);
    }

    @Test
    void getDepartmentById_notFound_returnsNull() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());
        Department res = departmentService.getDepartmentById(99L);
        assertNull(res);
        verify(departmentRepository).findById(99L);
    }
}
