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
 * A District.
 */
@Entity
@Table(name = "district")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "district")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "addresses", "district" }, allowSetters = true)
    private Set<Village> villages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "districts" }, allowSetters = true)
    private Province province;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public District id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public District name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Village> getVillages() {
        return this.villages;
    }

    public District villages(Set<Village> villages) {
        this.setVillages(villages);
        return this;
    }

    public District addVillage(Village village) {
        this.villages.add(village);
        village.setDistrict(this);
        return this;
    }

    public District removeVillage(Village village) {
        this.villages.remove(village);
        village.setDistrict(null);
        return this;
    }

    public void setVillages(Set<Village> villages) {
        if (this.villages != null) {
            this.villages.forEach(i -> i.setDistrict(null));
        }
        if (villages != null) {
            villages.forEach(i -> i.setDistrict(this));
        }
        this.villages = villages;
    }

    public Province getProvince() {
        return this.province;
    }

    public District province(Province province) {
        this.setProvince(province);
        return this;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        return id != null && id.equals(((District) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "District{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
