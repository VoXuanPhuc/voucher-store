package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.Address;
import com.emclab.voucher.service.dto.AddressDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:42+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Autowired
    private VillageMapper villageMapper;

    @Override
    public Address toEntity(AddressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Address address = new Address();

        address.id( dto.getId() );
        address.setStreet( dto.getStreet() );
        address.setZipCode( dto.getZipCode() );
        address.setVillage( villageMapper.toEntity( dto.getVillage() ) );

        return address;
    }

    @Override
    public List<Address> toEntity(List<AddressDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Address> list = new ArrayList<Address>( dtoList.size() );
        for ( AddressDTO addressDTO : dtoList ) {
            list.add( toEntity( addressDTO ) );
        }

        return list;
    }

    @Override
    public List<AddressDTO> toDto(List<Address> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>( entityList.size() );
        for ( Address address : entityList ) {
            list.add( toDto( address ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Address entity, AddressDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getStreet() != null ) {
            entity.setStreet( dto.getStreet() );
        }
        if ( dto.getZipCode() != null ) {
            entity.setZipCode( dto.getZipCode() );
        }
        if ( dto.getVillage() != null ) {
            entity.setVillage( villageMapper.toEntity( dto.getVillage() ) );
        }
    }

    @Override
    public AddressDTO toDto(Address s) {
        if ( s == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setVillage( villageMapper.toDtoId( s.getVillage() ) );
        addressDTO.setId( s.getId() );
        addressDTO.setStreet( s.getStreet() );
        addressDTO.setZipCode( s.getZipCode() );

        return addressDTO;
    }

    @Override
    public AddressDTO toDtoId(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId( address.getId() );

        return addressDTO;
    }
}
