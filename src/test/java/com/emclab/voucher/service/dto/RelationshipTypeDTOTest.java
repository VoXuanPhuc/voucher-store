package com.emclab.voucher.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emclab.voucher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelationshipTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipTypeDTO.class);
        RelationshipTypeDTO relationshipTypeDTO1 = new RelationshipTypeDTO();
        relationshipTypeDTO1.setId(1L);
        RelationshipTypeDTO relationshipTypeDTO2 = new RelationshipTypeDTO();
        assertThat(relationshipTypeDTO1).isNotEqualTo(relationshipTypeDTO2);
        relationshipTypeDTO2.setId(relationshipTypeDTO1.getId());
        assertThat(relationshipTypeDTO1).isEqualTo(relationshipTypeDTO2);
        relationshipTypeDTO2.setId(2L);
        assertThat(relationshipTypeDTO1).isNotEqualTo(relationshipTypeDTO2);
        relationshipTypeDTO1.setId(null);
        assertThat(relationshipTypeDTO1).isNotEqualTo(relationshipTypeDTO2);
    }
}
