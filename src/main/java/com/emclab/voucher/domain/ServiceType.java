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
 * A ServiceType.
 */
@Entity
@Table(name = "service_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
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

    public ServiceType id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ServiceType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Voucher> getVouchers() {
        return this.vouchers;
    }

    public ServiceType vouchers(Set<Voucher> vouchers) {
        this.setVouchers(vouchers);
        return this;
    }

    public ServiceType addVoucher(Voucher voucher) {
        this.vouchers.add(voucher);
        voucher.setType(this);
        return this;
    }

    public ServiceType removeVoucher(Voucher voucher) {
        this.vouchers.remove(voucher);
        voucher.setType(null);
        return this;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        if (this.vouchers != null) {
            this.vouchers.forEach(i -> i.setType(null));
        }
        if (vouchers != null) {
            vouchers.forEach(i -> i.setType(this));
        }
        this.vouchers = vouchers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceType)) {
            return false;
        }
        return id != null && id.equals(((ServiceType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
