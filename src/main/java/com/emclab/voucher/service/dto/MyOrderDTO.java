package com.emclab.voucher.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.emclab.voucher.domain.MyOrder} entity.
 */
public class MyOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private Double totalCost;

    private Instant paymentTime;

    private MyUserDTO user;

    private OrderStatusDTO status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Instant getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Instant paymentTime) {
        this.paymentTime = paymentTime;
    }

    public MyUserDTO getUser() {
        return user;
    }

    public void setUser(MyUserDTO user) {
        this.user = user;
    }

    public OrderStatusDTO getStatus() {
        return status;
    }

    public void setStatus(OrderStatusDTO status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyOrderDTO)) {
            return false;
        }

        MyOrderDTO myOrderDTO = (MyOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, myOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyOrderDTO{" +
                "id=" + getId() +
                ", totalCost=" + getTotalCost() +
                ", paymentTime='" + getPaymentTime() + "'" +
                ", user=" + getUser() +
                ", status=" + getStatus() +
                "}";
    }
}
