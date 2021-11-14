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
public class OrderStatus extends AbstractAuditingEntity {

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
    private Set<MyOrder> myOrders = new HashSet<>();

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

    public Set<MyOrder> getMyOrders() {
        return this.myOrders;
    }

    public OrderStatus myOrders(Set<MyOrder> myOrders) {
        this.setMyOrders(myOrders);
        return this;
    }

    public OrderStatus addMyOrder(MyOrder myOrder) {
        this.myOrders.add(myOrder);
        myOrder.setStatus(this);
        return this;
    }

    public OrderStatus removeMyOrder(MyOrder myOrder) {
        this.myOrders.remove(myOrder);
        myOrder.setStatus(null);
        return this;
    }

    public void setMyOrders(Set<MyOrder> myOrders) {
        if (this.myOrders != null) {
            this.myOrders.forEach(i -> i.setStatus(null));
        }
        if (myOrders != null) {
            myOrders.forEach(i -> i.setStatus(this));
        }
        this.myOrders = myOrders;
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
