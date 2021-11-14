package com.emclab.voucher.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.emclab.voucher.domain.Voucher} entity.
 */
public class VoucherDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private Integer quantity;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant expriedTime;

    private Set<ProductDTO> products = new HashSet<>();

    private EventDTO event;

    private ServiceTypeDTO type;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getExpriedTime() {
        return expriedTime;
    }

    public void setExpriedTime(Instant expriedTime) {
        this.expriedTime = expriedTime;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public ServiceTypeDTO getType() {
        return type;
    }

    public void setType(ServiceTypeDTO type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoucherDTO)) {
            return false;
        }

        VoucherDTO voucherDTO = (VoucherDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voucherDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", startTime='" + getStartTime() + "'" +
            ", expriedTime='" + getExpriedTime() + "'" +
            ", products=" + getProducts() +
            ", event=" + getEvent() +
            ", type=" + getType() +
            "}";
    }
}
