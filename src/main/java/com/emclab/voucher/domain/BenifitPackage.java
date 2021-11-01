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
 * A BenifitPackage.
 */
@Entity
@Table(name = "benifit_package")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BenifitPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "cost", nullable = false)
    private Long cost;

    @NotNull
    @Column(name = "time", nullable = false)
    private String time;

    @OneToMany(mappedBy = "pack")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "address", "storeUsers", "categories", "events", "pack" }, allowSetters = true)
    private Set<Store> stores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BenifitPackage id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public BenifitPackage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public BenifitPackage description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCost() {
        return this.cost;
    }

    public BenifitPackage cost(Long cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getTime() {
        return this.time;
    }

    public BenifitPackage time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Set<Store> getStores() {
        return this.stores;
    }

    public BenifitPackage stores(Set<Store> stores) {
        this.setStores(stores);
        return this;
    }

    public BenifitPackage addStore(Store store) {
        this.stores.add(store);
        store.setPack(this);
        return this;
    }

    public BenifitPackage removeStore(Store store) {
        this.stores.remove(store);
        store.setPack(null);
        return this;
    }

    public void setStores(Set<Store> stores) {
        if (this.stores != null) {
            this.stores.forEach(i -> i.setPack(null));
        }
        if (stores != null) {
            stores.forEach(i -> i.setPack(this));
        }
        this.stores = stores;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BenifitPackage)) {
            return false;
        }
        return id != null && id.equals(((BenifitPackage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BenifitPackage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", cost=" + getCost() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
