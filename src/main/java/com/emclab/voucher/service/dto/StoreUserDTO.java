package com.emclab.voucher.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.emclab.voucher.domain.StoreUser} entity.
 */
public class StoreUserDTO implements Serializable {

    private Long id;

    private RelationshipTypeDTO type;

    private MyUserDTO user;

    private StoreDTO store;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationshipTypeDTO getType() {
        return type;
    }

    public void setType(RelationshipTypeDTO type) {
        this.type = type;
    }

    public MyUserDTO getUser() {
        return user;
    }

    public void setUser(MyUserDTO user) {
        this.user = user;
    }

    public StoreDTO getStore() {
        return store;
    }

    public void setStore(StoreDTO store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreUserDTO)) {
            return false;
        }

        StoreUserDTO storeUserDTO = (StoreUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, storeUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreUserDTO{" +
            "id=" + getId() +
            ", type=" + getType() +
            ", user=" + getUser() +
            ", store=" + getStore() +
            "}";
    }
}
