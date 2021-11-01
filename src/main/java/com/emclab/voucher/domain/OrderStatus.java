package com.emclab.voucher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrderStatus.
 */
@Entity
@Table(name = "order_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "status")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "voucherCodes", "user", "status" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public OrderStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public OrderStatus orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public OrderStatus addOrder(Order order) {
        this.orders.add(order);
        order.setStatus(this);
        return this;
    }

    public OrderStatus removeOrder(Order order) {
        this.orders.remove(order);
        order.setStatus(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setStatus(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setStatus(this));
        }
        this.orders = orders;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderStatus)) {
            return false;
        }
        return id != null && id.equals(((OrderStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderStatus{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
