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
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Event extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "voucherImages", "voucherCodes", "feedbacks", "products", "event", "type", "status" },
        allowSetters = true
    )
    private Set<Voucher> vouchers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "address", "storeUsers", "categories", "events", "benifit" }, allowSetters = true)
    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Event title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Voucher> getVouchers() {
        return this.vouchers;
    }

    public Event vouchers(Set<Voucher> vouchers) {
        this.setVouchers(vouchers);
        return this;
    }

    public Event addVoucher(Voucher voucher) {
        this.vouchers.add(voucher);
        voucher.setEvent(this);
        return this;
    }

    public Event removeVoucher(Voucher voucher) {
        this.vouchers.remove(voucher);
        voucher.setEvent(null);
        return this;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        if (this.vouchers != null) {
            this.vouchers.forEach(i -> i.setEvent(null));
        }
        if (vouchers != null) {
            vouchers.forEach(i -> i.setEvent(this));
        }
        this.vouchers = vouchers;
    }

    public Store getStore() {
        return this.store;
    }

    public Event store(Store store) {
        this.setStore(store);
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
