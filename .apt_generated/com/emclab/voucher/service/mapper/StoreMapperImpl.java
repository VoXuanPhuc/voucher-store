package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.Store;
import com.emclab.voucher.service.dto.StoreDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:45+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class StoreMapperImpl implements StoreMapper {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private BenifitPackageMapper benifitPackageMapper;

    @Override
    public Store toEntity(StoreDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Store store = new Store();

        store.id( dto.getId() );
        store.setName( dto.getName() );
        store.setDescription( dto.getDescription() );
        store.setEmail( dto.getEmail() );
        store.setPhone( dto.getPhone() );
        store.setAvartar( dto.getAvartar() );
        store.setBackground( dto.getBackground() );
        store.setAddress( addressMapper.toEntity( dto.getAddress() ) );
        store.setBenifit( benifitPackageMapper.toEntity( dto.getBenifit() ) );

        return store;
    }

    @Override
    public List<Store> toEntity(List<StoreDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Store> list = new ArrayList<Store>( dtoList.size() );
        for ( StoreDTO storeDTO : dtoList ) {
            list.add( toEntity( storeDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreDTO> toDto(List<Store> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreDTO> list = new ArrayList<StoreDTO>( entityList.size() );
        for ( Store store : entityList ) {
            list.add( toDto( store ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Store entity, StoreDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getPhone() != null ) {
            entity.setPhone( dto.getPhone() );
        }
        if ( dto.getAvartar() != null ) {
            entity.setAvartar( dto.getAvartar() );
        }
        if ( dto.getBackground() != null ) {
            entity.setBackground( dto.getBackground() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( addressMapper.toEntity( dto.getAddress() ) );
        }
        if ( dto.getBenifit() != null ) {
            entity.setBenifit( benifitPackageMapper.toEntity( dto.getBenifit() ) );
        }
    }

    @Override
    public StoreDTO toDto(Store s) {
        if ( s == null ) {
            return null;
        }

        StoreDTO storeDTO = new StoreDTO();

        storeDTO.setAddress( addressMapper.toDtoId( s.getAddress() ) );
        storeDTO.setBenifit( benifitPackageMapper.toDtoId( s.getBenifit() ) );
        storeDTO.setId( s.getId() );
        storeDTO.setName( s.getName() );
        storeDTO.setDescription( s.getDescription() );
        storeDTO.setEmail( s.getEmail() );
        storeDTO.setPhone( s.getPhone() );
        storeDTO.setAvartar( s.getAvartar() );
        storeDTO.setBackground( s.getBackground() );

        return storeDTO;
    }

    @Override
    public StoreDTO toDtoId(Store store) {
        if ( store == null ) {
            return null;
        }

        StoreDTO storeDTO = new StoreDTO();

        storeDTO.setId( store.getId() );

        return storeDTO;
    }
}
