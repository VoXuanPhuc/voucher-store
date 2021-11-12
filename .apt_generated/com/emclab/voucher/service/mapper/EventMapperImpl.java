package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.Event;
import com.emclab.voucher.service.dto.EventDTO;
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
public class EventMapperImpl implements EventMapper {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Event toEntity(EventDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Event event = new Event();

        event.id( dto.getId() );
        event.setTitle( dto.getTitle() );
        event.setDescription( dto.getDescription() );
        event.setStore( storeMapper.toEntity( dto.getStore() ) );

        return event;
    }

    @Override
    public List<Event> toEntity(List<EventDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Event> list = new ArrayList<Event>( dtoList.size() );
        for ( EventDTO eventDTO : dtoList ) {
            list.add( toEntity( eventDTO ) );
        }

        return list;
    }

    @Override
    public List<EventDTO> toDto(List<Event> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EventDTO> list = new ArrayList<EventDTO>( entityList.size() );
        for ( Event event : entityList ) {
            list.add( toDto( event ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Event entity, EventDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getStore() != null ) {
            entity.setStore( storeMapper.toEntity( dto.getStore() ) );
        }
    }

    @Override
    public EventDTO toDto(Event s) {
        if ( s == null ) {
            return null;
        }

        EventDTO eventDTO = new EventDTO();

        eventDTO.setStore( storeMapper.toDtoId( s.getStore() ) );
        eventDTO.setId( s.getId() );
        eventDTO.setTitle( s.getTitle() );
        eventDTO.setDescription( s.getDescription() );

        return eventDTO;
    }

    @Override
    public EventDTO toDtoId(Event event) {
        if ( event == null ) {
            return null;
        }

        EventDTO eventDTO = new EventDTO();

        eventDTO.setId( event.getId() );

        return eventDTO;
    }
}
