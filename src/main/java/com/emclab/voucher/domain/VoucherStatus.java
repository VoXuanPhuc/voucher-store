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
    @JsonIgnoreProperties(value = { "gifts", "status", "voucher", "order" }, allowSetters = true)
    private Set<VoucherCode> voucherCodes = new HashSet<>();

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

    public Set<VoucherCode> getVoucherCodes() {
        return this.voucherCodes;
    }

    public VoucherStatus voucherCodes(Set<VoucherCode> voucherCodes) {
        this.setVoucherCodes(voucherCodes);
        return this;
    }

    public VoucherStatus addVoucherCode(VoucherCode voucherCode) {
        this.voucherCodes.add(voucherCode);
        voucherCode.setStatus(this);
        return this;
    }

    public VoucherStatus removeVoucherCode(VoucherCode voucherCode) {
        this.voucherCodes.remove(voucherCode);
        voucherCode.setStatus(null);
        return this;
    }

    public void setVoucherCodes(Set<VoucherCode> voucherCodes) {
        if (this.voucherCodes != null) {
            this.voucherCodes.forEach(i -> i.setStatus(null));
        }
        if (voucherCodes != null) {
            voucherCodes.forEach(i -> i.setStatus(this));
        }
        this.voucherCodes = voucherCodes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherStatus{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
