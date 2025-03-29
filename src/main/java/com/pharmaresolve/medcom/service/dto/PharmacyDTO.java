package com.pharmaresolve.medcom.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.pharmaresolve.medcom.domain.Pharmacy} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PharmacyDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String address;

    private String email;

    private String phone;

    private String timeZone;

    private String website;

    private Boolean active;

    private ZonedDateTime created;

    private String activatedBy;

    private Boolean deleted;

    private PharmacySubscriptionDTO subscription;

    private UserDTO admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getActivatedBy() {
        return activatedBy;
    }

    public void setActivatedBy(String activatedBy) {
        this.activatedBy = activatedBy;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public PharmacySubscriptionDTO getSubscription() {
        return subscription;
    }

    public void setSubscription(PharmacySubscriptionDTO subscription) {
        this.subscription = subscription;
    }

    public UserDTO getAdmin() {
        return admin;
    }

    public void setAdmin(UserDTO admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PharmacyDTO)) {
            return false;
        }

        PharmacyDTO pharmacyDTO = (PharmacyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pharmacyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PharmacyDTO{" +
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
            ", subscription=" + getSubscription() +
            ", admin=" + getAdmin() +
            "}";
    }
}
