package com.inva.hipstertest.web.rest;

import com.inva.hipstertest.SchoolNetApp;

import com.inva.hipstertest.domain.LessonType;
import com.inva.hipstertest.repository.LessonTypeRepository;
import com.inva.hipstertest.service.LessonTypeService;
import com.inva.hipstertest.service.dto.LessonTypeDTO;
import com.inva.hipstertest.service.mapper.LessonTypeMapper;
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
 * Test class for the LessonTypeResource REST controller.
 *
 * @see LessonTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolNetApp.class)
public class LessonTypeResourceIntTest {

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private LessonTypeRepository lessonTypeRepository;

    @Autowired
    private LessonTypeMapper lessonTypeMapper;

    @Autowired
    private LessonTypeService lessonTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLessonTypeMockMvc;

    private LessonType lessonType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LessonTypeResource lessonTypeResource = new LessonTypeResource(lessonTypeService);
        this.restLessonTypeMockMvc = MockMvcBuilders.standaloneSetup(lessonTypeResource)
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
    public static LessonType createEntity(EntityManager em) {
        LessonType lessonType = new LessonType()
            .enabled(DEFAULT_ENABLED)
            .name(DEFAULT_NAME);
        return lessonType;
    }

    @Before
    public void initTest() {
        lessonType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLessonType() throws Exception {
        int databaseSizeBeforeCreate = lessonTypeRepository.findAll().size();

        // Create the LessonType
        LessonTypeDTO lessonTypeDTO = lessonTypeMapper.lessonTypeToLessonTypeDTO(lessonType);
        restLessonTypeMockMvc.perform(post("/api/lesson-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LessonType in the database
        List<LessonType> lessonTypeList = lessonTypeRepository.findAll();
        assertThat(lessonTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LessonType testLessonType = lessonTypeList.get(lessonTypeList.size() - 1);
        assertThat(testLessonType.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testLessonType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLessonTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lessonTypeRepository.findAll().size();

        // Create the LessonType with an existing ID
        lessonType.setId(1L);
        LessonTypeDTO lessonTypeDTO = lessonTypeMapper.lessonTypeToLessonTypeDTO(lessonType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLessonTypeMockMvc.perform(post("/api/lesson-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LessonType> lessonTypeList = lessonTypeRepository.findAll();
        assertThat(lessonTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTypeRepository.findAll().size();
        // set the field null
        lessonType.setEnabled(null);

        // Create the LessonType, which fails.
        LessonTypeDTO lessonTypeDTO = lessonTypeMapper.lessonTypeToLessonTypeDTO(lessonType);

        restLessonTypeMockMvc.perform(post("/api/lesson-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LessonType> lessonTypeList = lessonTypeRepository.findAll();
        assertThat(lessonTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTypeRepository.findAll().size();
        // set the field null
        lessonType.setName(null);

        // Create the LessonType, which fails.
        LessonTypeDTO lessonTypeDTO = lessonTypeMapper.lessonTypeToLessonTypeDTO(lessonType);

        restLessonTypeMockMvc.perform(post("/api/lesson-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LessonType> lessonTypeList = lessonTypeRepository.findAll();
        assertThat(lessonTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLessonTypes() throws Exception {
        // Initialize the database
        lessonTypeRepository.saveAndFlush(lessonType);

        // Get all the lessonTypeList
        restLessonTypeMockMvc.perform(get("/api/lesson-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lessonType.getId().intValue())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLessonType() throws Exception {
        // Initialize the database
        lessonTypeRepository.saveAndFlush(lessonType);

        // Get the lessonType
        restLessonTypeMockMvc.perform(get("/api/lesson-types/{id}", lessonType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lessonType.getId().intValue()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLessonType() throws Exception {
        // Get the lessonType
        restLessonTypeMockMvc.perform(get("/api/lesson-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLessonType() throws Exception {
        // Initialize the database
        lessonTypeRepository.saveAndFlush(lessonType);
        int databaseSizeBeforeUpdate = lessonTypeRepository.findAll().size();

        // Update the lessonType
        LessonType updatedLessonType = lessonTypeRepository.findOne(lessonType.getId());
        updatedLessonType
            .enabled(UPDATED_ENABLED)
            .name(UPDATED_NAME);
        LessonTypeDTO lessonTypeDTO = lessonTypeMapper.lessonTypeToLessonTypeDTO(updatedLessonType);

        restLessonTypeMockMvc.perform(put("/api/lesson-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTypeDTO)))
            .andExpect(status().isOk());

        // Validate the LessonType in the database
        List<LessonType> lessonTypeList = lessonTypeRepository.findAll();
        assertThat(lessonTypeList).hasSize(databaseSizeBeforeUpdate);
        LessonType testLessonType = lessonTypeList.get(lessonTypeList.size() - 1);
        assertThat(testLessonType.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testLessonType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLessonType() throws Exception {
        int databaseSizeBeforeUpdate = lessonTypeRepository.findAll().size();

        // Create the LessonType
        LessonTypeDTO lessonTypeDTO = lessonTypeMapper.lessonTypeToLessonTypeDTO(lessonType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLessonTypeMockMvc.perform(put("/api/lesson-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LessonType in the database
        List<LessonType> lessonTypeList = lessonTypeRepository.findAll();
        assertThat(lessonTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLessonType() throws Exception {
        // Initialize the database
        lessonTypeRepository.saveAndFlush(lessonType);
        int databaseSizeBeforeDelete = lessonTypeRepository.findAll().size();

        // Get the lessonType
        restLessonTypeMockMvc.perform(delete("/api/lesson-types/{id}", lessonType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LessonType> lessonTypeList = lessonTypeRepository.findAll();
        assertThat(lessonTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LessonType.class);
    }
}
