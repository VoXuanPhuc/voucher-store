package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.OrderStatus;
import com.emclab.voucher.service.dto.OrderStatusDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:46+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class OrderStatusMapperImpl implements OrderStatusMapper {

    @Override
    public OrderStatus toEntity(OrderStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OrderStatus orderStatus = new OrderStatus();

        orderStatus.id( dto.getId() );
        orderStatus.setName( dto.getName() );

        return orderStatus;
    }

    @Override
    public OrderStatusDTO toDto(OrderStatus entity) {
        if ( entity == null ) {
            return null;
        }

        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();

        orderStatusDTO.setId( entity.getId() );
        orderStatusDTO.setName( entity.getName() );

        return orderStatusDTO;
    }

    @Override
    public List<OrderStatus> toEntity(List<OrderStatusDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<OrderStatus> list = new ArrayList<OrderStatus>( dtoList.size() );
        for ( OrderStatusDTO orderStatusDTO : dtoList ) {
            list.add( toEntity( orderStatusDTO ) );
        }

        return list;
    }

    @Override
    public List<OrderStatusDTO> toDto(List<OrderStatus> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderStatusDTO> list = new ArrayList<OrderStatusDTO>( entityList.size() );
        for ( OrderStatus orderStatus : entityList ) {
            list.add( toDto( orderStatus ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(OrderStatus entity, OrderStatusDTO dto) {
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
    public OrderStatusDTO toDtoId(OrderStatus orderStatus) {
        if ( orderStatus == null ) {
            return null;
        }

        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();

        orderStatusDTO.setId( orderStatus.getId() );

        return orderStatusDTO;
    }
}
