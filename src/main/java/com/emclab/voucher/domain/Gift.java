package com.emclab.voucher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Gift.
 */
@Entity
@Table(name = "gift")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gift extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JsonIgnoreProperties(value = { "address", "storeUsers", "myOrders", "feedbacks", "gifts", "role" }, allowSetters = true)
    private MyUser giver;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gifts", "voucher", "order" }, allowSetters = true)
    private VoucherCode voucher;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gift id(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public Gift message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyUser getGiver() {
        return this.giver;
    }

    public Gift giver(MyUser myUser) {
        this.setGiver(myUser);
        return this;
    }

    public void setGiver(MyUser myUser) {
        this.giver = myUser;
    }

    public VoucherCode getVoucher() {
        return this.voucher;
    }

    public Gift voucher(VoucherCode voucherCode) {
        this.setVoucher(voucherCode);
        return this;
    }

    public void setVoucher(VoucherCode voucherCode) {
        this.voucher = voucherCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gift)) {
            return false;
        }
        return id != null && id.equals(((Gift) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gift{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
