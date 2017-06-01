package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.Teacher;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Teacher entity.
 */
@SuppressWarnings("unused")
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    @Query("select distinct teacher from Teacher teacher left join fetch teacher.lessons")
    List<Teacher> findAllWithEagerRelationships();

    @Query("select teacher from Teacher teacher left join fetch teacher.lessons where teacher.id =:id")
    Teacher findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select teacher from Teacher teacher where teacher.user.login = ?#{principal.username}")
    Teacher findTeacherByCurrentUser();

    //@Query("select pupil from Pupil pupil where pupil.user.login = ?#{principal.username}")
    @Query("select t from Teacher t left join fetch t.school s where t.user.login = ?#{principal.username}")
    Teacher findOneWithSchool();

    //@Query("select teac.id from Teacher teac where teac.user.login =?#{principal.username}")
    //Long findTeacherId();

}
