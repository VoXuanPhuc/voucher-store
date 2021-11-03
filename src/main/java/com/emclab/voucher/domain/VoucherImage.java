package com.emclab.voucher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VoucherImage.
 */
@Entity
@Table(name = "voucher_image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VoucherImage extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "voucherImages", "voucherCodes", "feedbacks", "products", "event", "type", "status" },
        allowSetters = true
    )
    private Voucher voucher;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoucherImage id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public VoucherImage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Voucher getVoucher() {
        return this.voucher;
    }

    public VoucherImage voucher(Voucher voucher) {
        this.setVoucher(voucher);
        return this;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoucherImage)) {
            return false;
        }
        return id != null && id.equals(((VoucherImage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherImage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
