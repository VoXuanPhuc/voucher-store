package com.emclab.voucher.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.emclab.voucher.domain.Gift} entity.
 */
public class GiftDTO implements Serializable {

    private Long id;

    private String message;

    private MyUserDTO giver;

    private VoucherCodeDTO voucher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyUserDTO getGiver() {
        return giver;
    }

    public void setGiver(MyUserDTO giver) {
        this.giver = giver;
    }

    public VoucherCodeDTO getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherCodeDTO voucher) {
        this.voucher = voucher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GiftDTO)) {
            return false;
        }

        GiftDTO giftDTO = (GiftDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, giftDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GiftDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", giver=" + getGiver() +
            ", voucher=" + getVoucher() +
            "}";
    }
}
