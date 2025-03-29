package com.pharmaresolve.medcom.service.dto;

import com.pharmaresolve.medcom.domain.enumeration.ConnectionType;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.pharmaresolve.medcom.domain.AppConnectionLog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppConnectionLogDTO implements Serializable {

    private Long id;

    private ConnectionType type;

    private ZonedDateTime time;

    private String ipAddress;

    private String userAgent;

    private String latitude;

    private String longitude;

    private Duration sessionDuration;

    private ZonedDateTime previousLoginTime;

    private AppUserDTO appUser;

    private PharmacyDTO pharmacy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConnectionType getType() {
        return type;
    }

    public void setType(ConnectionType type) {
        this.type = type;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Duration getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Duration sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public ZonedDateTime getPreviousLoginTime() {
        return previousLoginTime;
    }

    public void setPreviousLoginTime(ZonedDateTime previousLoginTime) {
        this.previousLoginTime = previousLoginTime;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
    }

    public PharmacyDTO getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(PharmacyDTO pharmacy) {
        this.pharmacy = pharmacy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppConnectionLogDTO)) {
            return false;
        }

        AppConnectionLogDTO appConnectionLogDTO = (AppConnectionLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appConnectionLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppConnectionLogDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", time='" + getTime() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", sessionDuration='" + getSessionDuration() + "'" +
            ", previousLoginTime='" + getPreviousLoginTime() + "'" +
            ", appUser=" + getAppUser() +
            ", pharmacy=" + getPharmacy() +
            "}";
    }
}
