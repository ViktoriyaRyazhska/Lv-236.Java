package com.inva.hipstertest.web.rest;

import com.inva.hipstertest.SchoolNetApp;

import com.inva.hipstertest.domain.AttendancesLog;
import com.inva.hipstertest.domain.Teacher;
import com.inva.hipstertest.domain.Attendances;
import com.inva.hipstertest.repository.AttendancesLogRepository;
import com.inva.hipstertest.service.AttendancesLogService;
import com.inva.hipstertest.service.dto.AttendancesLogDTO;
import com.inva.hipstertest.service.mapper.AttendancesLogMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.inva.hipstertest.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AttendancesLogResource REST controller.
 *
 * @see AttendancesLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolNetApp.class)
public class AttendancesLogResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_OLD_GRADE = 1;
    private static final Integer UPDATED_OLD_GRADE = 2;

    private static final Integer DEFAULT_NEW_GRADE = 1;
    private static final Integer UPDATED_NEW_GRADE = 2;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private AttendancesLogRepository attendancesLogRepository;

    @Autowired
    private AttendancesLogMapper attendancesLogMapper;

    @Autowired
    private AttendancesLogService attendancesLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAttendancesLogMockMvc;

    private AttendancesLog attendancesLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AttendancesLogResource attendancesLogResource = new AttendancesLogResource(attendancesLogService);
        this.restAttendancesLogMockMvc = MockMvcBuilders.standaloneSetup(attendancesLogResource)
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
    public static AttendancesLog createEntity(EntityManager em) {
        AttendancesLog attendancesLog = new AttendancesLog()
            .date(DEFAULT_DATE)
            .oldGrade(DEFAULT_OLD_GRADE)
            .newGrade(DEFAULT_NEW_GRADE)
            .reason(DEFAULT_REASON);
        // Add required entity
        Teacher teacher = TeacherResourceIntTest.createEntity(em);
        em.persist(teacher);
        em.flush();
        attendancesLog.setTeacher(teacher);
        // Add required entity
        Attendances attendances = AttendancesResourceIntTest.createEntity(em);
        em.persist(attendances);
        em.flush();
        attendancesLog.setAttendances(attendances);
        return attendancesLog;
    }

    @Before
    public void initTest() {
        attendancesLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendancesLog() throws Exception {
        int databaseSizeBeforeCreate = attendancesLogRepository.findAll().size();

        // Create the AttendancesLog
        AttendancesLogDTO attendancesLogDTO = attendancesLogMapper.attendancesLogToAttendancesLogDTO(attendancesLog);
        restAttendancesLogMockMvc.perform(post("/api/attendances-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesLogDTO)))
            .andExpect(status().isCreated());

        // Validate the AttendancesLog in the database
        List<AttendancesLog> attendancesLogList = attendancesLogRepository.findAll();
        assertThat(attendancesLogList).hasSize(databaseSizeBeforeCreate + 1);
        AttendancesLog testAttendancesLog = attendancesLogList.get(attendancesLogList.size() - 1);
        assertThat(testAttendancesLog.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAttendancesLog.getOldGrade()).isEqualTo(DEFAULT_OLD_GRADE);
        assertThat(testAttendancesLog.getNewGrade()).isEqualTo(DEFAULT_NEW_GRADE);
        assertThat(testAttendancesLog.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createAttendancesLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendancesLogRepository.findAll().size();

        // Create the AttendancesLog with an existing ID
        attendancesLog.setId(1L);
        AttendancesLogDTO attendancesLogDTO = attendancesLogMapper.attendancesLogToAttendancesLogDTO(attendancesLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendancesLogMockMvc.perform(post("/api/attendances-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AttendancesLog> attendancesLogList = attendancesLogRepository.findAll();
        assertThat(attendancesLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendancesLogRepository.findAll().size();
        // set the field null
        attendancesLog.setDate(null);

        // Create the AttendancesLog, which fails.
        AttendancesLogDTO attendancesLogDTO = attendancesLogMapper.attendancesLogToAttendancesLogDTO(attendancesLog);

        restAttendancesLogMockMvc.perform(post("/api/attendances-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesLogDTO)))
            .andExpect(status().isBadRequest());

        List<AttendancesLog> attendancesLogList = attendancesLogRepository.findAll();
        assertThat(attendancesLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendancesLogRepository.findAll().size();
        // set the field null
        attendancesLog.setReason(null);

        // Create the AttendancesLog, which fails.
        AttendancesLogDTO attendancesLogDTO = attendancesLogMapper.attendancesLogToAttendancesLogDTO(attendancesLog);

        restAttendancesLogMockMvc.perform(post("/api/attendances-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesLogDTO)))
            .andExpect(status().isBadRequest());

        List<AttendancesLog> attendancesLogList = attendancesLogRepository.findAll();
        assertThat(attendancesLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttendancesLogs() throws Exception {
        // Initialize the database
        attendancesLogRepository.saveAndFlush(attendancesLog);

        // Get all the attendancesLogList
        restAttendancesLogMockMvc.perform(get("/api/attendances-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendancesLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].oldGrade").value(hasItem(DEFAULT_OLD_GRADE)))
            .andExpect(jsonPath("$.[*].newGrade").value(hasItem(DEFAULT_NEW_GRADE)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }

    @Test
    @Transactional
    public void getAttendancesLog() throws Exception {
        // Initialize the database
        attendancesLogRepository.saveAndFlush(attendancesLog);

        // Get the attendancesLog
        restAttendancesLogMockMvc.perform(get("/api/attendances-logs/{id}", attendancesLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendancesLog.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.oldGrade").value(DEFAULT_OLD_GRADE))
            .andExpect(jsonPath("$.newGrade").value(DEFAULT_NEW_GRADE))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttendancesLog() throws Exception {
        // Get the attendancesLog
        restAttendancesLogMockMvc.perform(get("/api/attendances-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendancesLog() throws Exception {
        // Initialize the database
        attendancesLogRepository.saveAndFlush(attendancesLog);
        int databaseSizeBeforeUpdate = attendancesLogRepository.findAll().size();

        // Update the attendancesLog
        AttendancesLog updatedAttendancesLog = attendancesLogRepository.findOne(attendancesLog.getId());
        updatedAttendancesLog
            .date(UPDATED_DATE)
            .oldGrade(UPDATED_OLD_GRADE)
            .newGrade(UPDATED_NEW_GRADE)
            .reason(UPDATED_REASON);
        AttendancesLogDTO attendancesLogDTO = attendancesLogMapper.attendancesLogToAttendancesLogDTO(updatedAttendancesLog);

        restAttendancesLogMockMvc.perform(put("/api/attendances-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesLogDTO)))
            .andExpect(status().isOk());

        // Validate the AttendancesLog in the database
        List<AttendancesLog> attendancesLogList = attendancesLogRepository.findAll();
        assertThat(attendancesLogList).hasSize(databaseSizeBeforeUpdate);
        AttendancesLog testAttendancesLog = attendancesLogList.get(attendancesLogList.size() - 1);
        assertThat(testAttendancesLog.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAttendancesLog.getOldGrade()).isEqualTo(UPDATED_OLD_GRADE);
        assertThat(testAttendancesLog.getNewGrade()).isEqualTo(UPDATED_NEW_GRADE);
        assertThat(testAttendancesLog.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendancesLog() throws Exception {
        int databaseSizeBeforeUpdate = attendancesLogRepository.findAll().size();

        // Create the AttendancesLog
        AttendancesLogDTO attendancesLogDTO = attendancesLogMapper.attendancesLogToAttendancesLogDTO(attendancesLog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAttendancesLogMockMvc.perform(put("/api/attendances-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendancesLogDTO)))
            .andExpect(status().isCreated());

        // Validate the AttendancesLog in the database
        List<AttendancesLog> attendancesLogList = attendancesLogRepository.findAll();
        assertThat(attendancesLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAttendancesLog() throws Exception {
        // Initialize the database
        attendancesLogRepository.saveAndFlush(attendancesLog);
        int databaseSizeBeforeDelete = attendancesLogRepository.findAll().size();

        // Get the attendancesLog
        restAttendancesLogMockMvc.perform(delete("/api/attendances-logs/{id}", attendancesLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AttendancesLog> attendancesLogList = attendancesLogRepository.findAll();
        assertThat(attendancesLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendancesLog.class);
    }
}
