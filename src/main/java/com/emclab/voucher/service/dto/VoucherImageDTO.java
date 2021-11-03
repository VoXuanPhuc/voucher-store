package com.emclab.voucher.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.emclab.voucher.domain.VoucherImage} entity.
 */
public class VoucherImageDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private VoucherDTO voucher;

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

    public VoucherDTO getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoucherImageDTO)) {
            return false;
        }

        VoucherImageDTO voucherImageDTO = (VoucherImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voucherImageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherImageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", voucher=" + getVoucher() +
            "}";
    }
}
