package com.inva.hipstertest.web.rest;

import com.inva.hipstertest.SchoolNetApp;

import com.inva.hipstertest.domain.Attendances;
import com.inva.hipstertest.domain.Pupil;
import com.inva.hipstertest.domain.Schedule;
import com.inva.hipstertest.repository.AttendancesRepository;
import com.inva.hipstertest.service.AttendancesService;
import com.inva.hipstertest.service.dto.AttendancesDTO;
import com.inva.hipstertest.service.mapper.AttendancesMapper;
import com.inva.hipstertest.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AttendancesResource REST controller.
 *
 * @see AttendancesResource
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolNetApp.class)
public class AttendancesResourceIntTest {

    private static final Integer DEFAULT_GRADE = 1;
    private static final Integer UPDATED_GRADE = 2;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private AttendancesRepository attendancesRepository;

    @Autowired
    private AttendancesMapper attendancesMapper;

    @Autowired
    private AttendancesService attendancesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAttendancesMockMvc;

    private Attendances attendances;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AttendancesResource attendancesResource = new AttendancesResource(attendancesService);
        this.restAttendancesMockMvc = MockMvcBuilders.standaloneSetup(attendancesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }


/**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */

    public static Attendances createEntity(EntityManager em) {
        Attendances attendances = new Attendances()
            .grade(DEFAULT_GRADE)
            .enabled(DEFAULT_ENABLED);
        // Add required entity
        Pupil pupil = PupilResourceIntTest.createEntity(em);
        em.persist(pupil);
        em.flush();
        attendances.setPupil(pupil);
        // Add required entity
        Schedule schedule = ScheduleResourceIntTest.createEntity(em);
        em.persist(schedule);
        em.flush();
        attendances.setSchedule(schedule);
        return attendances;
    }

    @Before
    public void initTest() {
        attendances = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendances() throws Exception {
        int databaseSizeBeforeCreate = attendancesRepository.findAll().size();

        // Create the Attendances
        AttendancesDTO attendancesDTO = attendancesMapper.attendancesToAttendancesDTO(attendances);
        restAttendancesMockMvc.perform(post("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendances in the database
        List<Attendances> attendancesList = attendancesRepository.findAll();
        assertThat(attendancesList).hasSize(databaseSizeBeforeCreate + 1);
        Attendances testAttendances = attendancesList.get(attendancesList.size() - 1);
        assertThat(testAttendances.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testAttendances.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createAttendancesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendancesRepository.findAll().size();

        // Create the Attendances with an existing ID
        attendances.setId(1L);
        AttendancesDTO attendancesDTO = attendancesMapper.attendancesToAttendancesDTO(attendances);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendancesMockMvc.perform(post("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Attendances> attendancesList = attendancesRepository.findAll();
        assertThat(attendancesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendancesRepository.findAll().size();
        // set the field null
        attendances.setEnabled(null);

        // Create the Attendances, which fails.
        AttendancesDTO attendancesDTO = attendancesMapper.attendancesToAttendancesDTO(attendances);

        restAttendancesMockMvc.perform(post("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesDTO)))
            .andExpect(status().isBadRequest());

        List<Attendances> attendancesList = attendancesRepository.findAll();
        assertThat(attendancesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttendances() throws Exception {
        // Initialize the database
        attendancesRepository.saveAndFlush(attendances);

        // Get all the attendancesList
        restAttendancesMockMvc.perform(get("/api/attendances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendances.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getAttendances() throws Exception {
        // Initialize the database
        attendancesRepository.saveAndFlush(attendances);

        // Get the attendances
        restAttendancesMockMvc.perform(get("/api/attendances/{id}", attendances.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendances.getId().intValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAttendances() throws Exception {
        // Get the attendances
        restAttendancesMockMvc.perform(get("/api/attendances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendances() throws Exception {
        // Initialize the database
        attendancesRepository.saveAndFlush(attendances);
        int databaseSizeBeforeUpdate = attendancesRepository.findAll().size();

        // Update the attendances
        Attendances updatedAttendances = attendancesRepository.findOne(attendances.getId());
        updatedAttendances
            .grade(UPDATED_GRADE)
            .enabled(UPDATED_ENABLED);
        AttendancesDTO attendancesDTO = attendancesMapper.attendancesToAttendancesDTO(updatedAttendances);

        restAttendancesMockMvc.perform(put("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesDTO)))
            .andExpect(status().isOk());

        // Validate the Attendances in the database
        List<Attendances> attendancesList = attendancesRepository.findAll();
        assertThat(attendancesList).hasSize(databaseSizeBeforeUpdate);
        Attendances testAttendances = attendancesList.get(attendancesList.size() - 1);
        assertThat(testAttendances.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testAttendances.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendances() throws Exception {
        int databaseSizeBeforeUpdate = attendancesRepository.findAll().size();

        // Create the Attendances
        AttendancesDTO attendancesDTO = attendancesMapper.attendancesToAttendancesDTO(attendances);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAttendancesMockMvc.perform(put("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendances in the database
        List<Attendances> attendancesList = attendancesRepository.findAll();
        assertThat(attendancesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAttendances() throws Exception {
        // Initialize the database
        attendancesRepository.saveAndFlush(attendances);
        int databaseSizeBeforeDelete = attendancesRepository.findAll().size();

        // Get the attendances
        restAttendancesMockMvc.perform(delete("/api/attendances/{id}", attendances.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Attendances> attendancesList = attendancesRepository.findAll();
        assertThat(attendancesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attendances.class);
    }
}
