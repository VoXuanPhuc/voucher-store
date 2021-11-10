package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.RelationshipType;
import com.emclab.voucher.service.dto.RelationshipTypeDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:45+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class RelationshipTypeMapperImpl implements RelationshipTypeMapper {

    @Override
    public RelationshipType toEntity(RelationshipTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RelationshipType relationshipType = new RelationshipType();

        relationshipType.id( dto.getId() );
        relationshipType.setName( dto.getName() );

        return relationshipType;
    }

    @Override
    public RelationshipTypeDTO toDto(RelationshipType entity) {
        if ( entity == null ) {
            return null;
        }

        RelationshipTypeDTO relationshipTypeDTO = new RelationshipTypeDTO();

        relationshipTypeDTO.setId( entity.getId() );
        relationshipTypeDTO.setName( entity.getName() );

        return relationshipTypeDTO;
    }

    @Override
    public List<RelationshipType> toEntity(List<RelationshipTypeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RelationshipType> list = new ArrayList<RelationshipType>( dtoList.size() );
        for ( RelationshipTypeDTO relationshipTypeDTO : dtoList ) {
            list.add( toEntity( relationshipTypeDTO ) );
        }

        return list;
    }

    @Override
    public List<RelationshipTypeDTO> toDto(List<RelationshipType> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RelationshipTypeDTO> list = new ArrayList<RelationshipTypeDTO>( entityList.size() );
        for ( RelationshipType relationshipType : entityList ) {
            list.add( toDto( relationshipType ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RelationshipType entity, RelationshipTypeDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
    }

    @Override
    public RelationshipTypeDTO toDtoId(RelationshipType relationshipType) {
        if ( relationshipType == null ) {
            return null;
        }

        RelationshipTypeDTO relationshipTypeDTO = new RelationshipTypeDTO();

        relationshipTypeDTO.setId( relationshipType.getId() );

        return relationshipTypeDTO;
    }
}
