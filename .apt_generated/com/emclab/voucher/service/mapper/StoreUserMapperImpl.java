package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.StoreUser;
import com.emclab.voucher.service.dto.StoreUserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:44+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class StoreUserMapperImpl implements StoreUserMapper {

    @Autowired
    private RelationshipTypeMapper relationshipTypeMapper;
    @Autowired
    private MyUserMapper myUserMapper;
    @Autowired
    private StoreMapper storeMapper;

    @Override
    public StoreUser toEntity(StoreUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreUser storeUser = new StoreUser();

        storeUser.id( dto.getId() );
        storeUser.setType( relationshipTypeMapper.toEntity( dto.getType() ) );
        storeUser.setUser( myUserMapper.toEntity( dto.getUser() ) );
        storeUser.setStore( storeMapper.toEntity( dto.getStore() ) );

        return storeUser;
    }

    @Override
    public List<StoreUser> toEntity(List<StoreUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreUser> list = new ArrayList<StoreUser>( dtoList.size() );
        for ( StoreUserDTO storeUserDTO : dtoList ) {
            list.add( toEntity( storeUserDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreUserDTO> toDto(List<StoreUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreUserDTO> list = new ArrayList<StoreUserDTO>( entityList.size() );
        for ( StoreUser storeUser : entityList ) {
            list.add( toDto( storeUser ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(StoreUser entity, StoreUserDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getType() != null ) {
            entity.setType( relationshipTypeMapper.toEntity( dto.getType() ) );
        }
        if ( dto.getUser() != null ) {
            entity.setUser( myUserMapper.toEntity( dto.getUser() ) );
        }
        if ( dto.getStore() != null ) {
            entity.setStore( storeMapper.toEntity( dto.getStore() ) );
        }
    }

    @Override
    public StoreUserDTO toDto(StoreUser s) {
        if ( s == null ) {
            return null;
        }

        StoreUserDTO storeUserDTO = new StoreUserDTO();

        storeUserDTO.setType( relationshipTypeMapper.toDtoId( s.getType() ) );
        storeUserDTO.setUser( myUserMapper.toDtoId( s.getUser() ) );
        storeUserDTO.setStore( storeMapper.toDtoId( s.getStore() ) );
        storeUserDTO.setId( s.getId() );

        return storeUserDTO;
    }
}
