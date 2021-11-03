package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.RelationshipType;
import com.emclab.voucher.repository.RelationshipTypeRepository;
import com.emclab.voucher.service.RelationshipTypeService;
import com.emclab.voucher.service.dto.RelationshipTypeDTO;
import com.emclab.voucher.service.mapper.RelationshipTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RelationshipType}.
 */
@Service
@Transactional
public class RelationshipTypeServiceImpl implements RelationshipTypeService {

    private final Logger log = LoggerFactory.getLogger(RelationshipTypeServiceImpl.class);

    private final RelationshipTypeRepository relationshipTypeRepository;

    private final RelationshipTypeMapper relationshipTypeMapper;

    public RelationshipTypeServiceImpl(
        RelationshipTypeRepository relationshipTypeRepository,
        RelationshipTypeMapper relationshipTypeMapper
    ) {
        this.relationshipTypeRepository = relationshipTypeRepository;
        this.relationshipTypeMapper = relationshipTypeMapper;
    }

    @Override
    public RelationshipTypeDTO save(RelationshipTypeDTO relationshipTypeDTO) {
        log.debug("Request to save RelationshipType : {}", relationshipTypeDTO);
        RelationshipType relationshipType = relationshipTypeMapper.toEntity(relationshipTypeDTO);
        relationshipType = relationshipTypeRepository.save(relationshipType);
        return relationshipTypeMapper.toDto(relationshipType);
    }

    @Override
    public Optional<RelationshipTypeDTO> partialUpdate(RelationshipTypeDTO relationshipTypeDTO) {
        log.debug("Request to partially update RelationshipType : {}", relationshipTypeDTO);

        return relationshipTypeRepository
            .findById(relationshipTypeDTO.getId())
            .map(
                existingRelationshipType -> {
                    relationshipTypeMapper.partialUpdate(existingRelationshipType, relationshipTypeDTO);

                    return existingRelationshipType;
                }
            )
            .map(relationshipTypeRepository::save)
            .map(relationshipTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationshipTypeDTO> findAll() {
        log.debug("Request to get all RelationshipTypes");
        return relationshipTypeRepository
            .findAll()
            .stream()
            .map(relationshipTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RelationshipTypeDTO> findOne(Long id) {
        log.debug("Request to get RelationshipType : {}", id);
        return relationshipTypeRepository.findById(id).map(relationshipTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RelationshipType : {}", id);
        relationshipTypeRepository.deleteById(id);
    }
}
