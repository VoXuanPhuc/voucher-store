package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.MyUser;
import com.emclab.voucher.service.dto.MyUserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:43+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class MyUserMapperImpl implements MyUserMapper {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public MyUser toEntity(MyUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MyUser myUser = new MyUser();

        myUser.id( dto.getId() );
        myUser.setUsername( dto.getUsername() );
        myUser.setPassword( dto.getPassword() );
        myUser.setFirstName( dto.getFirstName() );
        myUser.setLastName( dto.getLastName() );
        myUser.setGender( dto.getGender() );
        myUser.setPhone( dto.getPhone() );
        myUser.setEmail( dto.getEmail() );
        myUser.setAddress( addressMapper.toEntity( dto.getAddress() ) );
        myUser.setRole( roleMapper.toEntity( dto.getRole() ) );

        return myUser;
    }

    @Override
    public List<MyUser> toEntity(List<MyUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MyUser> list = new ArrayList<MyUser>( dtoList.size() );
        for ( MyUserDTO myUserDTO : dtoList ) {
            list.add( toEntity( myUserDTO ) );
        }

        return list;
    }

    @Override
    public List<MyUserDTO> toDto(List<MyUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MyUserDTO> list = new ArrayList<MyUserDTO>( entityList.size() );
        for ( MyUser myUser : entityList ) {
            list.add( toDto( myUser ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(MyUser entity, MyUserDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getUsername() != null ) {
            entity.setUsername( dto.getUsername() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getFirstName() != null ) {
            entity.setFirstName( dto.getFirstName() );
        }
        if ( dto.getLastName() != null ) {
            entity.setLastName( dto.getLastName() );
        }
        if ( dto.getGender() != null ) {
            entity.setGender( dto.getGender() );
        }
        if ( dto.getPhone() != null ) {
            entity.setPhone( dto.getPhone() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( addressMapper.toEntity( dto.getAddress() ) );
        }
        if ( dto.getRole() != null ) {
            entity.setRole( roleMapper.toEntity( dto.getRole() ) );
        }
    }

    @Override
    public MyUserDTO toDto(MyUser s) {
        if ( s == null ) {
            return null;
        }

        MyUserDTO myUserDTO = new MyUserDTO();

        myUserDTO.setAddress( addressMapper.toDtoId( s.getAddress() ) );
        myUserDTO.setRole( roleMapper.toDtoId( s.getRole() ) );
        myUserDTO.setId( s.getId() );
        myUserDTO.setUsername( s.getUsername() );
        myUserDTO.setPassword( s.getPassword() );
        myUserDTO.setFirstName( s.getFirstName() );
        myUserDTO.setLastName( s.getLastName() );
        myUserDTO.setGender( s.getGender() );
        myUserDTO.setPhone( s.getPhone() );
        myUserDTO.setEmail( s.getEmail() );

        return myUserDTO;
    }

    @Override
    public MyUserDTO toDtoId(MyUser myUser) {
        if ( myUser == null ) {
            return null;
        }

        MyUserDTO myUserDTO = new MyUserDTO();

        myUserDTO.setId( myUser.getId() );

        return myUserDTO;
    }
}
