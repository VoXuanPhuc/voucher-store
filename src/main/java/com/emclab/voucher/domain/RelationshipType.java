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
 * A RelationshipType.
 */
@Entity
@Table(name = "relationship_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RelationshipType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "type", "user", "store" }, allowSetters = true)
    private Set<StoreUser> storeUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationshipType id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public RelationshipType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<StoreUser> getStoreUsers() {
        return this.storeUsers;
    }

    public RelationshipType storeUsers(Set<StoreUser> storeUsers) {
        this.setStoreUsers(storeUsers);
        return this;
    }

    public RelationshipType addStoreUser(StoreUser storeUser) {
        this.storeUsers.add(storeUser);
        storeUser.setType(this);
        return this;
    }

    public RelationshipType removeStoreUser(StoreUser storeUser) {
        this.storeUsers.remove(storeUser);
        storeUser.setType(null);
        return this;
    }

    public void setStoreUsers(Set<StoreUser> storeUsers) {
        if (this.storeUsers != null) {
            this.storeUsers.forEach(i -> i.setType(null));
        }
        if (storeUsers != null) {
            storeUsers.forEach(i -> i.setType(this));
        }
        this.storeUsers = storeUsers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelationshipType)) {
            return false;
        }
        return id != null && id.equals(((RelationshipType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RelationshipType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
