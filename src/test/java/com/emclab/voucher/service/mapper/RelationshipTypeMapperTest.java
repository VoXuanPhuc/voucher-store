package com.emclab.voucher.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelationshipTypeMapperTest {

    private RelationshipTypeMapper relationshipTypeMapper;

    @BeforeEach
    public void setUp() {
        relationshipTypeMapper = new RelationshipTypeMapperImpl();
    }
}
