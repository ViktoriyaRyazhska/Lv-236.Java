package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.Form;
import com.inva.hipstertest.domain.Pupil;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Pupil entity.
 */
@SuppressWarnings("unused")
public interface PupilRepository extends JpaRepository<Pupil, Long> {

    @Query("select pupil from Pupil pupil left join pupil.form form where form.id =:formId")
    List<Pupil> findAllByFormId(@Param("formId") Long formId);

    @Query("select pupil from Pupil pupil where pupil.user.login = ?#{principal.username}")
    Pupil findPupilByCurrentUser();

    @Query("select pupil from Parent parent join parent.pupils pupil where parent.id = :parentId")
    List<Pupil> findAllByParentId(@Param("parentId") Long parentId);

    @Query("select p from Pupil p where  p.form.id is null")
    List<Pupil> findAllUnassignedPupilsByCurrentSchool();

}
