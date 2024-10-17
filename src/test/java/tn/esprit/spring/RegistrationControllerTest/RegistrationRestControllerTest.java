package tn.esprit.spring.RegistrationControllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.repositories.controllers.RegistrationRestController;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.services.IRegistrationServices;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegistrationRestControllerTest {

    @Mock
    private IRegistrationServices registrationServices;

    @InjectMocks
    private RegistrationRestController registrationRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationRestController).build();
    }

    @Test
    void testAddAndAssignToSkier() throws Exception {
        Registration registration = new Registration();
        registration.setNumWeek(1);  // Set numWeek to ensure it exists in the response
        when(registrationServices.addRegistrationAndAssignToSkier(any(Registration.class), anyLong())).thenReturn(registration);

        mockMvc.perform(put("/registration/addAndAssignToSkier/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"numWeek\": 1 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numWeek").value(1)); // Check for the correct value

        verify(registrationServices, times(1)).addRegistrationAndAssignToSkier(any(Registration.class), eq(1L));
    }

    @Test
    void testAssignToCourse() throws Exception {
        Registration registration = new Registration();
        registration.setNumWeek(1);  // Set numWeek to ensure it exists in the response
        when(registrationServices.assignRegistrationToCourse(anyLong(), anyLong())).thenReturn(registration);

        mockMvc.perform(put("/registration/assignToCourse/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numWeek").value(1)); // Check for the correct value

        verify(registrationServices, times(1)).assignRegistrationToCourse(eq(1L), eq(1L));
    }

    @Test
    void testAddAndAssignToSkierAndCourse() throws Exception {
        Registration registration = new Registration();
        registration.setNumWeek(1);  // Set numWeek to ensure it exists in the response
        when(registrationServices.addRegistrationAndAssignToSkierAndCourse(any(Registration.class), anyLong(), anyLong())).thenReturn(registration);

        mockMvc.perform(put("/registration/addAndAssignToSkierAndCourse/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"numWeek\": 1 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numWeek").value(1)); // Check for the correct value

        verify(registrationServices, times(1)).addRegistrationAndAssignToSkierAndCourse(any(Registration.class), eq(1L), eq(1L));
    }

    @Test
    void testNumWeeksCourseOfInstructorBySupport() throws Exception {
        when(registrationServices.numWeeksCourseOfInstructorBySupport(anyLong(), any(Support.class)))
                .thenReturn(List.of(1, 2, 3));

        mockMvc.perform(get("/registration/numWeeks/1/SKI")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(1))
                .andExpect(jsonPath("$[1]").value(2))
                .andExpect(jsonPath("$[2]").value(3));

        verify(registrationServices, times(1)).numWeeksCourseOfInstructorBySupport(eq(1L), eq(Support.SKI));
    }
}
