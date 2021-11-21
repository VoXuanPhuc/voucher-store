package com.emclab.voucher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StoreUser.
 */
@Entity
@Table(name = "store_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StoreUser extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "storeUsers" }, allowSetters = true)
    private RelationshipType type;

    @ManyToOne
    @JsonIgnoreProperties(value = { "address", "storeUsers", "myOrders", "feedbacks", "gifts", "roles" }, allowSetters = true)
    private MyUser user;

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

    public StoreUser id(Long id) {
        this.id = id;
        return this;
    }

    public RelationshipType getType() {
        return this.type;
    }

    public StoreUser type(RelationshipType relationshipType) {
        this.setType(relationshipType);
        return this;
    }

    public void setType(RelationshipType relationshipType) {
        this.type = relationshipType;
    }

    public MyUser getUser() {
        return this.user;
    }

    public StoreUser user(MyUser myUser) {
        this.setUser(myUser);
        return this;
    }

    public void setUser(MyUser myUser) {
        this.user = myUser;
    }

    public Store getStore() {
        return this.store;
    }

    public StoreUser store(Store store) {
        this.setStore(store);
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreUser)) {
            return false;
        }
        return id != null && id.equals(((StoreUser) o).id);
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
        return "StoreUser{" + "id=" + getId() + "}";
    }
}
