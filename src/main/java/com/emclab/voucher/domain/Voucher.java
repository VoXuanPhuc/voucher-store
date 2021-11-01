package com.emclab.voucher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Voucher.
 */
@Entity
@Table(name = "voucher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "expried_time", nullable = false)
    private Instant expriedTime;

    @OneToMany(mappedBy = "voucher")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "voucher" }, allowSetters = true)
    private Set<VoucherImage> voucherImages = new HashSet<>();

    @OneToMany(mappedBy = "voucher")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "gifts", "voucher", "order" }, allowSetters = true)
    private Set<VoucherCode> voucherCodes = new HashSet<>();

    @OneToMany(mappedBy = "voucher")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "feedbackImages", "user", "voucher" }, allowSetters = true)
    private Set<Feedback> feedbacks = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_voucher__product",
        joinColumns = @JoinColumn(name = "voucher_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnoreProperties(value = { "category", "vouchers" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "vouchers", "store" }, allowSetters = true)
    private Event event;

    @ManyToOne
    @JsonIgnoreProperties(value = { "vouchers" }, allowSetters = true)
    private ServiceType type;

    @ManyToOne
    @JsonIgnoreProperties(value = { "vouchers" }, allowSetters = true)
    private VoucherStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voucher id(Long id) {
        this.id = id;
        return this;
    }

    public Double getPrice() {
        return this.price;
    }

    public Voucher price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Voucher quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Voucher startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getExpriedTime() {
        return this.expriedTime;
    }

    public Voucher expriedTime(Instant expriedTime) {
        this.expriedTime = expriedTime;
        return this;
    }

    public void setExpriedTime(Instant expriedTime) {
        this.expriedTime = expriedTime;
    }

    public Set<VoucherImage> getVoucherImages() {
        return this.voucherImages;
    }

    public Voucher voucherImages(Set<VoucherImage> voucherImages) {
        this.setVoucherImages(voucherImages);
        return this;
    }

    public Voucher addVoucherImage(VoucherImage voucherImage) {
        this.voucherImages.add(voucherImage);
        voucherImage.setVoucher(this);
        return this;
    }

    public Voucher removeVoucherImage(VoucherImage voucherImage) {
        this.voucherImages.remove(voucherImage);
        voucherImage.setVoucher(null);
        return this;
    }

    public void setVoucherImages(Set<VoucherImage> voucherImages) {
        if (this.voucherImages != null) {
            this.voucherImages.forEach(i -> i.setVoucher(null));
        }
        if (voucherImages != null) {
            voucherImages.forEach(i -> i.setVoucher(this));
        }
        this.voucherImages = voucherImages;
    }

    public Set<VoucherCode> getVoucherCodes() {
        return this.voucherCodes;
    }

    public Voucher voucherCodes(Set<VoucherCode> voucherCodes) {
        this.setVoucherCodes(voucherCodes);
        return this;
    }

    public Voucher addVoucherCode(VoucherCode voucherCode) {
        this.voucherCodes.add(voucherCode);
        voucherCode.setVoucher(this);
        return this;
    }

    public Voucher removeVoucherCode(VoucherCode voucherCode) {
        this.voucherCodes.remove(voucherCode);
        voucherCode.setVoucher(null);
        return this;
    }

    public void setVoucherCodes(Set<VoucherCode> voucherCodes) {
        if (this.voucherCodes != null) {
            this.voucherCodes.forEach(i -> i.setVoucher(null));
        }
        if (voucherCodes != null) {
            voucherCodes.forEach(i -> i.setVoucher(this));
        }
        this.voucherCodes = voucherCodes;
    }

    public Set<Feedback> getFeedbacks() {
        return this.feedbacks;
    }

    public Voucher feedbacks(Set<Feedback> feedbacks) {
        this.setFeedbacks(feedbacks);
        return this;
    }

    public Voucher addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
        feedback.setVoucher(this);
        return this;
    }

    public Voucher removeFeedback(Feedback feedback) {
        this.feedbacks.remove(feedback);
        feedback.setVoucher(null);
        return this;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        if (this.feedbacks != null) {
            this.feedbacks.forEach(i -> i.setVoucher(null));
        }
        if (feedbacks != null) {
            feedbacks.forEach(i -> i.setVoucher(this));
        }
        this.feedbacks = feedbacks;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public Voucher products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Voucher addProduct(Product product) {
        this.products.add(product);
        product.getVouchers().add(this);
        return this;
    }

    public Voucher removeProduct(Product product) {
        this.products.remove(product);
        product.getVouchers().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Event getEvent() {
        return this.event;
    }

    public Voucher event(Event event) {
        this.setEvent(event);
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ServiceType getType() {
        return this.type;
    }

    public Voucher type(ServiceType serviceType) {
        this.setType(serviceType);
        return this;
    }

    public void setType(ServiceType serviceType) {
        this.type = serviceType;
    }

    public VoucherStatus getStatus() {
        return this.status;
    }

    public Voucher status(VoucherStatus voucherStatus) {
        this.setStatus(voucherStatus);
        return this;
    }

    public void setStatus(VoucherStatus voucherStatus) {
        this.status = voucherStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voucher)) {
            return false;
        }
        return id != null && id.equals(((Voucher) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voucher{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", startTime='" + getStartTime() + "'" +
            ", expriedTime='" + getExpriedTime() + "'" +
            "}";
    }
}
