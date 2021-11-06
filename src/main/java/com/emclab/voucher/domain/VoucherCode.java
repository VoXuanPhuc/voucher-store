package com.emclab.voucher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VoucherCode.
 */
@Entity
@Table(name = "voucher_code")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VoucherCode extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @OneToMany(mappedBy = "voucher")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "giver", "voucher" }, allowSetters = true)
    private Set<Gift> gifts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "voucherImages", "voucherCodes", "feedbacks", "products", "event", "type", "status" },
        allowSetters = true
    )
    private Voucher voucher;

    @ManyToOne
    @JsonIgnoreProperties(value = { "voucherCodes", "user", "status" }, allowSetters = true)
    private MyOrder order;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoucherCode id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public VoucherCode code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Gift> getGifts() {
        return this.gifts;
    }

    public VoucherCode gifts(Set<Gift> gifts) {
        this.setGifts(gifts);
        return this;
    }

    public VoucherCode addGift(Gift gift) {
        this.gifts.add(gift);
        gift.setVoucher(this);
        return this;
    }

    public VoucherCode removeGift(Gift gift) {
        this.gifts.remove(gift);
        gift.setVoucher(null);
        return this;
    }

    public void setGifts(Set<Gift> gifts) {
        if (this.gifts != null) {
            this.gifts.forEach(i -> i.setVoucher(null));
        }
        if (gifts != null) {
            gifts.forEach(i -> i.setVoucher(this));
        }
        this.gifts = gifts;
    }

    public Voucher getVoucher() {
        return this.voucher;
    }

    public VoucherCode voucher(Voucher voucher) {
        this.setVoucher(voucher);
        return this;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public MyOrder getOrder() {
        return this.order;
    }

    public VoucherCode order(MyOrder myOrder) {
        this.setOrder(myOrder);
        return this;
    }

    public void setOrder(MyOrder myOrder) {
        this.order = myOrder;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoucherCode)) {
            return false;
        }
        return id != null && id.equals(((VoucherCode) o).id);
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
        return "VoucherCode{" + "id=" + getId() + ", code='" + getCode() + "'" + "}";
    }
}
