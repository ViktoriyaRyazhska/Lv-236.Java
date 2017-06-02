package com.inva.hipstertest.support.methods;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.repository.UserRepository;
import com.inva.hipstertest.service.dto.TeacherDTO;
import com.inva.hipstertest.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
     *
     * @param teacherDTO must have:
     * firstName, lastName, email.
     * @param role ROLE_ENUM [HEAD_TEACHER, PARENT, PUPIL, TEACHER]
     * */
    public Map<String, Object> saveUserWithRole(TeacherDTO teacherDTO, ROLE_ENUM role){
        log.debug("Request to save user", teacherDTO, role);
        User user = new User();
        Map<String, Object> map = new HashMap<>();
        String login =  RandomUtil.generateLogin(teacherDTO.getFirstName(),
            teacherDTO.getLastName(),teacherDTO.getSchoolId());

        if (!userRepository.findOneByLogin(login).isPresent() &&
            !userRepository.findOneByEmail(teacherDTO.getEmail()).isPresent()){
            user.setLogin(login);
            user.setEmail(teacherDTO.getEmail());
        }else{
            if (userRepository.findOneByEmail(teacherDTO.getEmail()).isPresent()){
                map.put("error", teacherDTO);
                return map;
            }else{
                String anotherLogin = RandomUtil.generateLogin(teacherDTO.getFirstName(),
                    teacherDTO.getLastName(),teacherDTO.getSchoolId());
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
        if(role.equals(ROLE_ENUM.HEAD_TEACHER)){
            authority.setName("ROLE_HEAD_TEACHER");
            auto.add(authority);
        }else if(role.equals(ROLE_ENUM.TEACHER)){
            authority.setName("ROLE_TEACHER");
            auto.add(authority);
        }else if(role.equals(ROLE_ENUM.PUPIL)){
            authority.setName("ROLE_PUPIL");
            auto.add(authority);
            user.setActivated(false);
            user.setActivationKey(RandomUtil.generateActivationKey());
        }else if(role.equals(ROLE_ENUM.PARENT)){
            authority.setName("ROLE_PARENT");
            auto.add(authority);
            user.setActivated(false);
            user.setActivationKey(RandomUtil.generateActivationKey());
        }else{
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
