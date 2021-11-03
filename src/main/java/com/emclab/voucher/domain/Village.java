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
 * A Village.
 */
@Entity
@Table(name = "village")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Village implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "village")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "village" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "villages", "province" }, allowSetters = true)
    private District district;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Village id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Village name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public Village addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Village addAddress(Address address) {
        this.addresses.add(address);
        address.setVillage(this);
        return this;
    }

    public Village removeAddress(Address address) {
        this.addresses.remove(address);
        address.setVillage(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setVillage(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setVillage(this));
        }
        this.addresses = addresses;
    }

    public District getDistrict() {
        return this.district;
    }

    public Village district(District district) {
        this.setDistrict(district);
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Village)) {
            return false;
        }
        return id != null && id.equals(((Village) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Village{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
