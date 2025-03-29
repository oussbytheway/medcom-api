package com.pharmaresolve.medcom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pharmacy.
 */
@Entity
@Table(name = "pharmacy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pharmacy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "website")
    private String website;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "activated_by")
    private String activatedBy;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private PharmacySubscription subscription;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User admin;

    @JsonIgnoreProperties(value = { "pharmacy", "watchListItems" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pharmacy")
    private WatchList watchList;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pharmacy id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Pharmacy name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Pharmacy address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public Pharmacy email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Pharmacy phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public Pharmacy timeZone(String timeZone) {
        this.setTimeZone(timeZone);
        return this;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getWebsite() {
        return this.website;
    }

    public Pharmacy website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Pharmacy active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public Pharmacy created(ZonedDateTime created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getActivatedBy() {
        return this.activatedBy;
    }

    public Pharmacy activatedBy(String activatedBy) {
        this.setActivatedBy(activatedBy);
        return this;
    }

    public void setActivatedBy(String activatedBy) {
        this.activatedBy = activatedBy;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Pharmacy deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public PharmacySubscription getSubscription() {
        return this.subscription;
    }

    public void setSubscription(PharmacySubscription pharmacySubscription) {
        this.subscription = pharmacySubscription;
    }

    public Pharmacy subscription(PharmacySubscription pharmacySubscription) {
        this.setSubscription(pharmacySubscription);
        return this;
    }

    public User getAdmin() {
        return this.admin;
    }

    public void setAdmin(User user) {
        this.admin = user;
    }

    public Pharmacy admin(User user) {
        this.setAdmin(user);
        return this;
    }

    public WatchList getWatchList() {
        return this.watchList;
    }

    public void setWatchList(WatchList watchList) {
        if (this.watchList != null) {
            this.watchList.setPharmacy(null);
        }
        if (watchList != null) {
            watchList.setPharmacy(this);
        }
        this.watchList = watchList;
    }

    public Pharmacy watchList(WatchList watchList) {
        this.setWatchList(watchList);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pharmacy)) {
            return false;
        }
        return getId() != null && getId().equals(((Pharmacy) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pharmacy{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", website='" + getWebsite() + "'" +
            ", active='" + getActive() + "'" +
            ", created='" + getCreated() + "'" +
            ", activatedBy='" + getActivatedBy() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
