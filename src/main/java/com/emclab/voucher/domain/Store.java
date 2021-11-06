package com.emclab.voucher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Store.
 */
@Entity
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Store extends AbstractAuditingEntity {

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
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "avartar")
    private String avartar;

    @Column(name = "background")
    private String background;

    @JsonIgnoreProperties(value = { "village" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "type", "user", "store" }, allowSetters = true)
    private Set<StoreUser> storeUsers = new HashSet<>();

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "store" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vouchers", "store" }, allowSetters = true)
    private Set<Event> events = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "stores" }, allowSetters = true)
    private BenifitPackage benifit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Store description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return this.email;
    }

    public Store email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Store phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvartar() {
        return this.avartar;
    }

    public Store avartar(String avartar) {
        this.avartar = avartar;
        return this;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public String getBackground() {
        return this.background;
    }

    public Store background(String background) {
        this.background = background;
        return this;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Address getAddress() {
        return this.address;
    }

    public Store address(Address address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<StoreUser> getStoreUsers() {
        return this.storeUsers;
    }

    public Store storeUsers(Set<StoreUser> storeUsers) {
        this.setStoreUsers(storeUsers);
        return this;
    }

    public Store addStoreUser(StoreUser storeUser) {
        this.storeUsers.add(storeUser);
        storeUser.setStore(this);
        return this;
    }

    public Store removeStoreUser(StoreUser storeUser) {
        this.storeUsers.remove(storeUser);
        storeUser.setStore(null);
        return this;
    }

    public void setStoreUsers(Set<StoreUser> storeUsers) {
        if (this.storeUsers != null) {
            this.storeUsers.forEach(i -> i.setStore(null));
        }
        if (storeUsers != null) {
            storeUsers.forEach(i -> i.setStore(this));
        }
        this.storeUsers = storeUsers;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public Store categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public Store addCategory(Category category) {
        this.categories.add(category);
        category.setStore(this);
        return this;
    }

    public Store removeCategory(Category category) {
        this.categories.remove(category);
        category.setStore(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        if (this.categories != null) {
            this.categories.forEach(i -> i.setStore(null));
        }
        if (categories != null) {
            categories.forEach(i -> i.setStore(this));
        }
        this.categories = categories;
    }

    public Set<Event> getEvents() {
        return this.events;
    }

    public Store events(Set<Event> events) {
        this.setEvents(events);
        return this;
    }

    public Store addEvent(Event event) {
        this.events.add(event);
        event.setStore(this);
        return this;
    }

    public Store removeEvent(Event event) {
        this.events.remove(event);
        event.setStore(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        if (this.events != null) {
            this.events.forEach(i -> i.setStore(null));
        }
        if (events != null) {
            events.forEach(i -> i.setStore(this));
        }
        this.events = events;
    }

    public BenifitPackage getBenifit() {
        return this.benifit;
    }

    public Store benifit(BenifitPackage benifitPackage) {
        this.setBenifit(benifitPackage);
        return this;
    }

    public void setBenifit(BenifitPackage benifitPackage) {
        this.benifit = benifitPackage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        return id != null && id.equals(((Store) o).id);
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
        return "Store{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='" + getDescription() + "'"
                + ", email='" + getEmail() + "'" + ", phone='" + getPhone() + "'" + ", avartar='" + getAvartar() + "'"
                + ", background='" + getBackground() + "'" + "}";
    }
}
