package com.emclab.voucher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VoucherStatus.
 */
@Entity
@Table(name = "voucher_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VoucherStatus extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "status")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "voucherImages", "voucherCodes", "feedbacks", "products", "event", "type", "status" },
        allowSetters = true
    )
    private Set<Voucher> vouchers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoucherStatus id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public VoucherStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Voucher> getVouchers() {
        return this.vouchers;
    }

    public VoucherStatus vouchers(Set<Voucher> vouchers) {
        this.setVouchers(vouchers);
        return this;
    }

    public VoucherStatus addVoucher(Voucher voucher) {
        this.vouchers.add(voucher);
        voucher.setStatus(this);
        return this;
    }

    public VoucherStatus removeVoucher(Voucher voucher) {
        this.vouchers.remove(voucher);
        voucher.setStatus(null);
        return this;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        if (this.vouchers != null) {
            this.vouchers.forEach(i -> i.setStatus(null));
        }
        if (vouchers != null) {
            vouchers.forEach(i -> i.setStatus(this));
        }
        this.vouchers = vouchers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoucherStatus)) {
            return false;
        }
        return id != null && id.equals(((VoucherStatus) o).id);
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
        return "VoucherStatus{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
    }
}
