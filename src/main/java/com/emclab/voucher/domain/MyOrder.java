package com.emclab.voucher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MyOrder.
 */
@Entity
@Table(name = "my_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyOrder extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @Column(name = "payment_time", nullable = true)
    private Instant paymentTime;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "gifts", "status", "voucher", "order" }, allowSetters = true)
    private Set<VoucherCode> voucherCodes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "address", "storeUsers", "myOrders", "feedbacks", "gifts", "role" }, allowSetters = true)
    private MyUser user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "myOrders" }, allowSetters = true)
    private OrderStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyOrder id(Long id) {
        this.id = id;
        return this;
    }

    public Double getTotalCost() {
        return this.totalCost;
    }

    public MyOrder totalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Instant getPaymentTime() {
        return this.paymentTime;
    }

    public MyOrder paymentTime(Instant paymentTime) {
        this.paymentTime = paymentTime;
        return this;
    }

    public void setPaymentTime(Instant paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Set<VoucherCode> getVoucherCodes() {
        return this.voucherCodes;
    }

    public MyOrder voucherCodes(Set<VoucherCode> voucherCodes) {
        this.setVoucherCodes(voucherCodes);
        return this;
    }

    public MyOrder addVoucherCode(VoucherCode voucherCode) {
        this.voucherCodes.add(voucherCode);
        voucherCode.setOrder(this);
        return this;
    }

    public MyOrder removeVoucherCode(VoucherCode voucherCode) {
        this.voucherCodes.remove(voucherCode);
        voucherCode.setOrder(null);
        return this;
    }

    public void setVoucherCodes(Set<VoucherCode> voucherCodes) {
        if (this.voucherCodes != null) {
            this.voucherCodes.forEach(i -> i.setOrder(null));
        }
        if (voucherCodes != null) {
            voucherCodes.forEach(i -> i.setOrder(this));
        }
        this.voucherCodes = voucherCodes;
    }

    public MyUser getUser() {
        return this.user;
    }

    public MyOrder user(MyUser myUser) {
        this.setUser(myUser);
        return this;
    }

    public void setUser(MyUser myUser) {
        this.user = myUser;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public MyOrder status(OrderStatus orderStatus) {
        this.setStatus(orderStatus);
        return this;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyOrder)) {
            return false;
        }
        return id != null && id.equals(((MyOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyOrder{" +
                "id=" + getId() +
                ", totalCost=" + getTotalCost() +
                ", paymentTime='" + getPaymentTime() + "'" +
                "}";
    }
}
