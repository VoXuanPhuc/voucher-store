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
 * A MyUser.
 */
@Entity
@Table(name = "my_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnoreProperties(value = { "village" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "type", "user", "store" }, allowSetters = true)
    private Set<StoreUser> storeUsers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "voucherCodes", "user", "status" }, allowSetters = true)
    private Set<MyOrder> myOrders = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "feedbackImages", "user", "voucher" }, allowSetters = true)
    private Set<Feedback> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "giver")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "giver", "voucher" }, allowSetters = true)
    private Set<Gift> gifts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "myUsers" }, allowSetters = true)
    private Role role;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyUser id(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public MyUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public MyUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public MyUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public MyUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public MyUser gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return this.phone;
    }

    public MyUser phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public MyUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return this.address;
    }

    public MyUser address(Address address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<StoreUser> getStoreUsers() {
        return this.storeUsers;
    }

    public MyUser storeUsers(Set<StoreUser> storeUsers) {
        this.setStoreUsers(storeUsers);
        return this;
    }

    public MyUser addStoreUser(StoreUser storeUser) {
        this.storeUsers.add(storeUser);
        storeUser.setUser(this);
        return this;
    }

    public MyUser removeStoreUser(StoreUser storeUser) {
        this.storeUsers.remove(storeUser);
        storeUser.setUser(null);
        return this;
    }

    public void setStoreUsers(Set<StoreUser> storeUsers) {
        if (this.storeUsers != null) {
            this.storeUsers.forEach(i -> i.setUser(null));
        }
        if (storeUsers != null) {
            storeUsers.forEach(i -> i.setUser(this));
        }
        this.storeUsers = storeUsers;
    }

    public Set<MyOrder> getMyOrders() {
        return this.myOrders;
    }

    public MyUser myOrders(Set<MyOrder> myOrders) {
        this.setMyOrders(myOrders);
        return this;
    }

    public MyUser addMyOrder(MyOrder myOrder) {
        this.myOrders.add(myOrder);
        myOrder.setUser(this);
        return this;
    }

    public MyUser removeMyOrder(MyOrder myOrder) {
        this.myOrders.remove(myOrder);
        myOrder.setUser(null);
        return this;
    }

    public void setMyOrders(Set<MyOrder> myOrders) {
        if (this.myOrders != null) {
            this.myOrders.forEach(i -> i.setUser(null));
        }
        if (myOrders != null) {
            myOrders.forEach(i -> i.setUser(this));
        }
        this.myOrders = myOrders;
    }

    public Set<Feedback> getFeedbacks() {
        return this.feedbacks;
    }

    public MyUser feedbacks(Set<Feedback> feedbacks) {
        this.setFeedbacks(feedbacks);
        return this;
    }

    public MyUser addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
        feedback.setUser(this);
        return this;
    }

    public MyUser removeFeedback(Feedback feedback) {
        this.feedbacks.remove(feedback);
        feedback.setUser(null);
        return this;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        if (this.feedbacks != null) {
            this.feedbacks.forEach(i -> i.setUser(null));
        }
        if (feedbacks != null) {
            feedbacks.forEach(i -> i.setUser(this));
        }
        this.feedbacks = feedbacks;
    }

    public Set<Gift> getGifts() {
        return this.gifts;
    }

    public MyUser gifts(Set<Gift> gifts) {
        this.setGifts(gifts);
        return this;
    }

    public MyUser addGift(Gift gift) {
        this.gifts.add(gift);
        gift.setGiver(this);
        return this;
    }

    public MyUser removeGift(Gift gift) {
        this.gifts.remove(gift);
        gift.setGiver(null);
        return this;
    }

    public void setGifts(Set<Gift> gifts) {
        if (this.gifts != null) {
            this.gifts.forEach(i -> i.setGiver(null));
        }
        if (gifts != null) {
            gifts.forEach(i -> i.setGiver(this));
        }
        this.gifts = gifts;
    }

    public Role getRole() {
        return this.role;
    }

    public MyUser role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyUser)) {
            return false;
        }
        return id != null && id.equals(((MyUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
