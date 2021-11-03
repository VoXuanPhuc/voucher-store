package com.emclab.voucher.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.emclab.voucher.domain.VoucherCode} entity.
 */
public class VoucherCodeDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private VoucherDTO voucher;

    private MyOrderDTO order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public VoucherDTO getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }

    public MyOrderDTO getOrder() {
        return order;
    }

    public void setOrder(MyOrderDTO order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoucherCodeDTO)) {
            return false;
        }

        VoucherCodeDTO voucherCodeDTO = (VoucherCodeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voucherCodeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherCodeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", voucher=" + getVoucher() +
            ", order=" + getOrder() +
            "}";
    }
}
