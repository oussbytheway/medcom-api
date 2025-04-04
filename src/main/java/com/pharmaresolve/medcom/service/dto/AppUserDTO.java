package com.pharmaresolve.medcom.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.pharmaresolve.medcom.domain.AppUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserDTO implements Serializable {

    private Long id;

    private String firstName;

    @NotNull
    private String lastName;

    private String email;

    private String phone;

    private String accessCode;

    private ZonedDateTime accessCodeExpirationDate;

    private ZonedDateTime lastLogin;

    private Boolean active;

    private PharmacyDTO pharmacy;

    private NotificationPreferenceDTO notificationPreference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public ZonedDateTime getAccessCodeExpirationDate() {
        return accessCodeExpirationDate;
    }

    public void setAccessCodeExpirationDate(ZonedDateTime accessCodeExpirationDate) {
        this.accessCodeExpirationDate = accessCodeExpirationDate;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PharmacyDTO getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(PharmacyDTO pharmacy) {
        this.pharmacy = pharmacy;
    }

    public NotificationPreferenceDTO getNotificationPreference() {
        return notificationPreference;
    }

    public void setNotificationPreference(NotificationPreferenceDTO notificationPreference) {
        this.notificationPreference = notificationPreference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserDTO)) {
            return false;
        }

        AppUserDTO appUserDTO = (AppUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", accessCode='" + getAccessCode() + "'" +
            ", accessCodeExpirationDate='" + getAccessCodeExpirationDate() + "'" +
            ", lastLogin='" + getLastLogin() + "'" +
            ", active='" + getActive() + "'" +
            ", pharmacy=" + getPharmacy() +
            ", notificationPreference=" + getNotificationPreference() +
            "}";
    }
}
