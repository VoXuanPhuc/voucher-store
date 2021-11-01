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
 * A Feedback.
 */
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rate", nullable = false)
    private Integer rate;

    @Column(name = "detail")
    private String detail;

    @OneToMany(mappedBy = "feedback")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "feedback" }, allowSetters = true)
    private Set<FeedbackImage> feedbackImages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "address", "storeUsers", "orders", "feedbacks", "gifts", "role" }, allowSetters = true)
    private MyUser user;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "voucherImages", "voucherCodes", "feedbacks", "products", "event", "type", "status" },
        allowSetters = true
    )
    private Voucher voucher;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Feedback id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getRate() {
        return this.rate;
    }

    public Feedback rate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDetail() {
        return this.detail;
    }

    public Feedback detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Set<FeedbackImage> getFeedbackImages() {
        return this.feedbackImages;
    }

    public Feedback feedbackImages(Set<FeedbackImage> feedbackImages) {
        this.setFeedbackImages(feedbackImages);
        return this;
    }

    public Feedback addFeedbackImage(FeedbackImage feedbackImage) {
        this.feedbackImages.add(feedbackImage);
        feedbackImage.setFeedback(this);
        return this;
    }

    public Feedback removeFeedbackImage(FeedbackImage feedbackImage) {
        this.feedbackImages.remove(feedbackImage);
        feedbackImage.setFeedback(null);
        return this;
    }

    public void setFeedbackImages(Set<FeedbackImage> feedbackImages) {
        if (this.feedbackImages != null) {
            this.feedbackImages.forEach(i -> i.setFeedback(null));
        }
        if (feedbackImages != null) {
            feedbackImages.forEach(i -> i.setFeedback(this));
        }
        this.feedbackImages = feedbackImages;
    }

    public MyUser getUser() {
        return this.user;
    }

    public Feedback user(MyUser myUser) {
        this.setUser(myUser);
        return this;
    }

    public void setUser(MyUser myUser) {
        this.user = myUser;
    }

    public Voucher getVoucher() {
        return this.voucher;
    }

    public Feedback voucher(Voucher voucher) {
        this.setVoucher(voucher);
        return this;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feedback)) {
            return false;
        }
        return id != null && id.equals(((Feedback) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Feedback{" +
            "id=" + getId() +
            ", rate=" + getRate() +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
