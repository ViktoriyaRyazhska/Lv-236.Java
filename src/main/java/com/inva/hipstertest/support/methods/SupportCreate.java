package com.inva.hipstertest.support.methods;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.repository.UserRepository;
import com.inva.hipstertest.service.dto.ParentDTO;
import com.inva.hipstertest.service.dto.PupilDTO;
import com.inva.hipstertest.service.dto.TeacherDTO;
import com.inva.hipstertest.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by Sensei on 23.05.2017.
 */
//@Component
abstract public class SupportCreate {


    private final Logger log = LoggerFactory.getLogger(SupportCreate.class);
    @Autowired
    private PasswordEncoder encode;
    @Autowired
    private UserRepository userRepository;

    /**
     * @param teacherDTO must have:
     *                   firstName, lastName, email.
     * @param role       ROLE_ENUM [HEAD_TEACHER, PARENT, PUPIL, TEACHER]
     */
    public Map<String, Object> saveUserWithRole(TeacherDTO teacherDTO, ROLE_ENUM role) {
        log.debug("Request to save user", teacherDTO, role);
        User user = new User();
        Map<String, Object> map = new HashMap<>();
        String login = RandomUtil.generateLogin(teacherDTO.getFirstName(),
            teacherDTO.getLastName(), teacherDTO.getSchoolId());

        if (!userRepository.findOneByLogin(login).isPresent() &&
            !userRepository.findOneByEmail(teacherDTO.getEmail()).isPresent()) {
            user.setLogin(login);
            user.setEmail(teacherDTO.getEmail());
        } else {
            if (userRepository.findOneByEmail(teacherDTO.getEmail()).isPresent()) {
                map.put("error", teacherDTO);
                return map;
            } else {
                String anotherLogin = RandomUtil.generateLogin(teacherDTO.getFirstName(),
                    teacherDTO.getLastName(), teacherDTO.getSchoolId());
                user.setLogin(anotherLogin);
                user.setEmail(teacherDTO.getEmail());
            }
        }
        user.setFirstName(teacherDTO.getFirstName());
        user.setLastName(teacherDTO.getLastName());
        user.setLangKey("en");
        /* For user what we need use true */
        user.setActivated(true);
        Set<Authority> auto = new HashSet<>();
        Authority authority = new Authority();
        if (role.equals(ROLE_ENUM.HEAD_TEACHER)) {
            authority.setName("ROLE_HEAD_TEACHER");
            auto.add(authority);
        } else if (role.equals(ROLE_ENUM.TEACHER)) {
            authority.setName("ROLE_TEACHER");
            auto.add(authority);
        } else if (role.equals(ROLE_ENUM.PUPIL)) {
            authority.setName("ROLE_PUPIL");
            auto.add(authority);
            user.setActivated(false);
            user.setActivationKey(RandomUtil.generateActivationKey());
        } else if (role.equals(ROLE_ENUM.PARENT)) {
            authority.setName("ROLE_PARENT");
            auto.add(authority);
            user.setActivated(false);
            user.setActivationKey(RandomUtil.generateActivationKey());
        } else {
            System.out.println("BAD PARAM(role) IN METHOD saveUserWithRole");
            authority.setName("ROLE_USER");
            auto.add(authority);
            user.setActivated(false);
            user.setActivationKey(RandomUtil.generateActivationKey());
        }
        user.setAuthorities(auto);
        String noEncryptedPassword = RandomUtil.generatePassword();
        String encryptedPassword = encode.encode(noEncryptedPassword);
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(ZonedDateTime.now());
        user = userRepository.save(user);
        String content = "Your login: (" + login + "). And password: (" + noEncryptedPassword + ").";
        map.put("userObject", user);
        map.put("content", content);
        return map;
    }


    /**
     * @param pupilDTO must have:
     *                 firstName, lastName, email.
     * @param role     ROLE_ENUM [HEAD_TEACHER, PARENT, PUPIL, TEACHER]
     */
    public Map<String, Object> savePupilWithRole(PupilDTO pupilDTO, ROLE_ENUM role) {
        log.debug("Request to save user", pupilDTO, role);
        User user = new User();
        Map<String, Object> map = new HashMap<>();
        String login = RandomUtil.generateLogin(pupilDTO.getFirstName(),
            pupilDTO.getLastName(), pupilDTO.getFormId());

        if (!userRepository.findOneByLogin(login).isPresent() &&
            !userRepository.findOneByEmail(pupilDTO.getEmail()).isPresent()) {
            user.setLogin(login);
            user.setEmail(pupilDTO.getEmail());
        } else {
            if (userRepository.findOneByEmail(pupilDTO.getEmail()).isPresent()) {
                map.put("error", pupilDTO);
                return map;
            } else {
                String anotherLogin = RandomUtil.generateLogin(pupilDTO.getFirstName(),
                    pupilDTO.getLastName(), pupilDTO.getFormId());
                user.setLogin(anotherLogin);
                user.setEmail(pupilDTO.getEmail());
            }
        }
        user.setFirstName(pupilDTO.getFirstName());
        user.setLastName(pupilDTO.getLastName());
        user.setLangKey("en");
        /* For user what we need use true */
        user.setActivated(true);
        Set<Authority> auto = new HashSet<>();
        Authority authority = new Authority();
        if (role.equals(ROLE_ENUM.HEAD_TEACHER)) {
            authority.setName("ROLE_HEAD_TEACHER");
            auto.add(authority);
        } else if (role.equals(ROLE_ENUM.TEACHER)) {
            authority.setName("ROLE_TEACHER");
            auto.add(authority);
        } else if (role.equals(ROLE_ENUM.PUPIL)) {
            authority.setName("ROLE_PUPIL");
            auto.add(authority);
            //user.setActivated(true);
            //user.setActivationKey(RandomUtil.generateActivationKey());
        } else if (role.equals(ROLE_ENUM.PARENT)) {
            authority.setName("ROLE_PARENT");
            auto.add(authority);
            user.setActivated(false);
            user.setActivationKey(RandomUtil.generateActivationKey());
        } else {
            System.out.println("BAD PARAM(role) IN METHOD saveUserWithRole");
            authority.setName("ROLE_USER");
            auto.add(authority);
            user.setActivated(false);
            user.setActivationKey(RandomUtil.generateActivationKey());
        }
        user.setAuthorities(auto);
        String noEncryptedPassword = RandomUtil.generatePassword();
        String encryptedPassword = encode.encode(noEncryptedPassword);
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(ZonedDateTime.now());
        user = userRepository.save(user);
        String content = "Your login: (" + login + "). And password: (" + noEncryptedPassword + ").";
        map.put("userObject", user);
        map.put("content", content);
        return map;
    }

    /**
     * @param parentDTO must have:
     *                  firstName, lastName, email.
     * @param role      ROLE_ENUM [HEAD_TEACHER, PARENT, PUPIL, TEACHER]
     */
    public Map<String, Object> saveParentWithRole(ParentDTO parentDTO, ROLE_ENUM role) {
        log.debug("Request to save user", parentDTO, role);
        User user = new User();

        Map<String, Object> map = new HashMap<>();


        String login = RandomUtil.generateLogin(parentDTO.getFirstName(),
            parentDTO.getLastName(), Long.MIN_VALUE);

        if (!userRepository.findOneByLogin(login).isPresent() &&
            !userRepository.findOneByEmail(parentDTO.getEmail()).isPresent()) {
            user.setLogin(login);
            user.setEmail(parentDTO.getEmail());
        } else {
            if (userRepository.findOneByEmail(parentDTO.getEmail()).isPresent()) {
                map.put("error", parentDTO);
                return map;
            } else {
                String anotherLogin = RandomUtil.generateLogin(parentDTO.getFirstName(),
                    parentDTO.getLastName(), Long.MIN_VALUE);
                user.setLogin(anotherLogin);
                user.setEmail(parentDTO.getEmail());
            }
        }
        user.setFirstName(parentDTO.getFirstName());
        user.setLastName(parentDTO.getLastName());
        user.setLangKey("en");
        /* For user what we need use true */
        user.setActivated(true);
        Set<Authority> auto = new HashSet<>();
        Authority authority = new Authority();
        if (role.equals(ROLE_ENUM.PARENT)) {
            authority.setName("ROLE_PARENT");
            auto.add(authority);
            user.setActivated(true);
        } else {
            System.out.println("BAD PARAM(role) IN METHOD saveUserWithRole");
            authority.setName("ROLE_USER");
            auto.add(authority);
            user.setActivated(false);
            user.setActivationKey(RandomUtil.generateActivationKey());
        }
        user.setAuthorities(auto);
        String noEncryptedPassword = RandomUtil.generatePassword();
        String encryptedPassword = encode.encode(noEncryptedPassword);
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(ZonedDateTime.now());
        user = userRepository.save(user);
        String content = "Your login: (" + login + "). And password: (" + noEncryptedPassword + ").";
        map.put("userObject", user);
        map.put("content", content);
        return map;
    }
}
