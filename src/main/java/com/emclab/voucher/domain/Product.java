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
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JsonIgnoreProperties(value = { "products", "store" }, allowSetters = true)
    private Category category;

    @ManyToMany(mappedBy = "products")
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

    public Product id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Product code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return this.image;
    }

    public Product image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return this.category;
    }

    public Product category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Voucher> getVouchers() {
        return this.vouchers;
    }

    public Product vouchers(Set<Voucher> vouchers) {
        this.setVouchers(vouchers);
        return this;
    }

    public Product addVoucher(Voucher voucher) {
        this.vouchers.add(voucher);
        voucher.getProducts().add(this);
        return this;
    }

    public Product removeVoucher(Voucher voucher) {
        this.vouchers.remove(voucher);
        voucher.getProducts().remove(this);
        return this;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        if (this.vouchers != null) {
            this.vouchers.forEach(i -> i.removeProduct(this));
        }
        if (vouchers != null) {
            vouchers.forEach(i -> i.addProduct(this));
        }
        this.vouchers = vouchers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
