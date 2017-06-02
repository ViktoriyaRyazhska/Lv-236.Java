package com.inva.hipstertest.service;

import com.inva.hipstertest.SchoolNetApp;
import com.inva.hipstertest.service.dto.PupilDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by slavkosoltys on 21.05.17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolNetApp.class)
@Transactional
public class PupilServiceIntTest {

    @Autowired
    private PupilService pupilService;

    @Test
    public void shouldSave() throws Exception {
    }

    @Test
    public void shouldFindAll() throws Exception {
        List<PupilDTO> pupils = pupilService.findAll();
        assertNotNull(pupils);
        assertTrue(!pupils.isEmpty());
    }

    @Test
    public void shouldFindOne() throws Exception {
        long pupilId = 3L;
        PupilDTO pupil = pupilService.findOne(pupilId);
        assertNotNull(pupil);
        //  assertEquals(pupilId), pupil.getId());

    }

    @Test
    public void shouldDelete() throws Exception {
        long pupilId = 3L;
        PupilDTO pupil = pupilService.findOne(pupilId);
        assertNotNull(pupil);
        pupilService.delete(pupilId);
        PupilDTO maybePupil = pupilService.findOne(pupilId);
        assertNull(maybePupil);
    }

//    @Test
//    public void shouldFindAllByFormId() throws Exception {
//        // prepare
//        Long formId = 4L;
//
//        // act
//        List<PupilDTO> pupils = pupilService.findAllByFormId(formId);
//
//        // verify
//        assertNotNull(pupils);
//        assertTrue(!pupils.isEmpty());
//
//        PupilDTO pupilDTO = pupils.get(0);
//        assertNotNull(pupilDTO);
//
//        assertTrue(formId.equals(pupilDTO.getFormId()));
//    }

//    private Pupil generatePupil(){
//        return new Pupil();
//    }

    // TODO create pupil test data generator

}
