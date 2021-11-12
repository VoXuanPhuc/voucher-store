package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.MyOrder;
import com.emclab.voucher.service.dto.MyOrderDTO;
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
public class MyOrderMapperImpl implements MyOrderMapper {

    @Autowired
    private MyUserMapper myUserMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    public MyOrder toEntity(MyOrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MyOrder myOrder = new MyOrder();

        myOrder.id( dto.getId() );
        myOrder.setTotalCost( dto.getTotalCost() );
        myOrder.setPaymentTime( dto.getPaymentTime() );
        myOrder.setUser( myUserMapper.toEntity( dto.getUser() ) );
        myOrder.setStatus( orderStatusMapper.toEntity( dto.getStatus() ) );

        return myOrder;
    }

    @Override
    public List<MyOrder> toEntity(List<MyOrderDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MyOrder> list = new ArrayList<MyOrder>( dtoList.size() );
        for ( MyOrderDTO myOrderDTO : dtoList ) {
            list.add( toEntity( myOrderDTO ) );
        }

        return list;
    }

    @Override
    public List<MyOrderDTO> toDto(List<MyOrder> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MyOrderDTO> list = new ArrayList<MyOrderDTO>( entityList.size() );
        for ( MyOrder myOrder : entityList ) {
            list.add( toDto( myOrder ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(MyOrder entity, MyOrderDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getTotalCost() != null ) {
            entity.setTotalCost( dto.getTotalCost() );
        }
        if ( dto.getPaymentTime() != null ) {
            entity.setPaymentTime( dto.getPaymentTime() );
        }
        if ( dto.getUser() != null ) {
            entity.setUser( myUserMapper.toEntity( dto.getUser() ) );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( orderStatusMapper.toEntity( dto.getStatus() ) );
        }
    }

    @Override
    public MyOrderDTO toDto(MyOrder s) {
        if ( s == null ) {
            return null;
        }

        MyOrderDTO myOrderDTO = new MyOrderDTO();

        myOrderDTO.setUser( myUserMapper.toDtoId( s.getUser() ) );
        myOrderDTO.setStatus( orderStatusMapper.toDtoId( s.getStatus() ) );
        myOrderDTO.setId( s.getId() );
        myOrderDTO.setTotalCost( s.getTotalCost() );
        myOrderDTO.setPaymentTime( s.getPaymentTime() );

        return myOrderDTO;
    }

    @Override
    public MyOrderDTO toDtoId(MyOrder myOrder) {
        if ( myOrder == null ) {
            return null;
        }

        MyOrderDTO myOrderDTO = new MyOrderDTO();

        myOrderDTO.setId( myOrder.getId() );

        return myOrderDTO;
    }
}
