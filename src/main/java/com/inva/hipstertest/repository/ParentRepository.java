package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.Parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Parent entity.
 */
@SuppressWarnings("unused")
public interface ParentRepository extends JpaRepository<Parent,Long> {

    @Query("select distinct parent from Parent parent left join fetch parent.pupils")
    List<Parent> findAllWithEagerRelationships();

    @Query("select parent from Parent parent left join fetch parent.pupils where parent.id =:id")
    Parent findOneWithEagerRelationships(@Param("id") Long id);


    @Query(value="select parent.id from parent join parent_pupil on parent_pupil.parents_id=parent.id " +
        "join pupil on parent_pupil.pupils_id=pupil.id where pupil.id=:id",nativeQuery = true)
    List<Parent> findParentOfPupil(@Param("id") Long id);
}
