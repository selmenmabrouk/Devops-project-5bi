package tn.esprit.spring.RegistrationServiceTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.RegistrationServicesImpl;
import tn.esprit.spring.entities.Course;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class RegistrationServicesImplTest {

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRegistrationAndAssignToSkier() {
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));

        Registration registration = new Registration();
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);

        assertNotNull(result);
        assertEquals(skier, result.getSkier());
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }

    @Test
    public void testAssignRegistrationToCourse() {
        Registration registration = new Registration();
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));

        Course course = new Course();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration result = registrationServices.assignRegistrationToCourse(1L, 1L);

        assertNotNull(result);
        assertEquals(course, result.getCourse());
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }


    @Test
    public void testNumWeeksCourseOfInstructorBySupport() {
        when(registrationRepository.numWeeksCourseOfInstructorBySupport(1L, Support.SKI)).thenReturn(List.of(1, 2, 3));

        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(1L, Support.SKI);

        assertEquals(3, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
    }
}
