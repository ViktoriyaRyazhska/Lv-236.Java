package com.inva.hipstertest.service.mapper;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.service.dto.ParentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Parent and its DTO ParentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PupilMapper.class, })
public interface ParentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "pupils", target = "pupils")
    ParentDTO parentToParentDTO(Parent parent);

    List<ParentDTO> parentsToParentDTOs(List<Parent> parents);

    @Mapping(source = "userId", target = "user")
    Parent parentDTOToParent(ParentDTO parentDTO);

    List<Parent> parentDTOsToParents(List<ParentDTO> parentDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Parent parentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Parent parent = new Parent();
        parent.setId(id);
        return parent;
    }


}
