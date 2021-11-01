package com.emclab.voucher.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.emclab.voucher.domain.ServiceType} entity.
 */
public class ServiceTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String icon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceTypeDTO)) {
            return false;
        }

        ServiceTypeDTO serviceTypeDTO = (ServiceTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
