package com.pharmaresolve.medcom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "access_code_expiration_date")
    private ZonedDateTime accessCodeExpirationDate;

    @Column(name = "last_login")
    private ZonedDateTime lastLogin;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subscription", "admin", "watchList" }, allowSetters = true)
    private Pharmacy pharmacy;

    @JsonIgnoreProperties(value = { "appUser" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private NotificationPreference notificationPreference;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public AppUser firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AppUser lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public AppUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public AppUser phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccessCode() {
        return this.accessCode;
    }

    public AppUser accessCode(String accessCode) {
        this.setAccessCode(accessCode);
        return this;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public ZonedDateTime getAccessCodeExpirationDate() {
        return this.accessCodeExpirationDate;
    }

    public AppUser accessCodeExpirationDate(ZonedDateTime accessCodeExpirationDate) {
        this.setAccessCodeExpirationDate(accessCodeExpirationDate);
        return this;
    }

    public void setAccessCodeExpirationDate(ZonedDateTime accessCodeExpirationDate) {
        this.accessCodeExpirationDate = accessCodeExpirationDate;
    }

    public ZonedDateTime getLastLogin() {
        return this.lastLogin;
    }

    public AppUser lastLogin(ZonedDateTime lastLogin) {
        this.setLastLogin(lastLogin);
        return this;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getActive() {
        return this.active;
    }

    public AppUser active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Pharmacy getPharmacy() {
        return this.pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public AppUser pharmacy(Pharmacy pharmacy) {
        this.setPharmacy(pharmacy);
        return this;
    }

    public NotificationPreference getNotificationPreference() {
        return this.notificationPreference;
    }

    public void setNotificationPreference(NotificationPreference notificationPreference) {
        this.notificationPreference = notificationPreference;
    }

    public AppUser notificationPreference(NotificationPreference notificationPreference) {
        this.setNotificationPreference(notificationPreference);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return getId() != null && getId().equals(((AppUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", accessCode='" + getAccessCode() + "'" +
            ", accessCodeExpirationDate='" + getAccessCodeExpirationDate() + "'" +
            ", lastLogin='" + getLastLogin() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
