package com.pharmaresolve.medcom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pharmaresolve.medcom.domain.enumeration.ConnectionType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AppConnectionLog.
 */
@Entity
@Table(name = "app_connection_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppConnectionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ConnectionType type;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "session_duration")
    private Duration sessionDuration;

    @Column(name = "previous_login_time")
    private ZonedDateTime previousLoginTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "pharmacy", "notificationPreference" }, allowSetters = true)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subscription", "admin", "watchList" }, allowSetters = true)
    private Pharmacy pharmacy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppConnectionLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConnectionType getType() {
        return this.type;
    }

    public AppConnectionLog type(ConnectionType type) {
        this.setType(type);
        return this;
    }

    public void setType(ConnectionType type) {
        this.type = type;
    }

    public ZonedDateTime getTime() {
        return this.time;
    }

    public AppConnectionLog time(ZonedDateTime time) {
        this.setTime(time);
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public AppConnectionLog ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public AppConnectionLog userAgent(String userAgent) {
        this.setUserAgent(userAgent);
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public AppConnectionLog latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public AppConnectionLog longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Duration getSessionDuration() {
        return this.sessionDuration;
    }

    public AppConnectionLog sessionDuration(Duration sessionDuration) {
        this.setSessionDuration(sessionDuration);
        return this;
    }

    public void setSessionDuration(Duration sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public ZonedDateTime getPreviousLoginTime() {
        return this.previousLoginTime;
    }

    public AppConnectionLog previousLoginTime(ZonedDateTime previousLoginTime) {
        this.setPreviousLoginTime(previousLoginTime);
        return this;
    }

    public void setPreviousLoginTime(ZonedDateTime previousLoginTime) {
        this.previousLoginTime = previousLoginTime;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public AppConnectionLog appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    public Pharmacy getPharmacy() {
        return this.pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public AppConnectionLog pharmacy(Pharmacy pharmacy) {
        this.setPharmacy(pharmacy);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppConnectionLog)) {
            return false;
        }
        return getId() != null && getId().equals(((AppConnectionLog) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppConnectionLog{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", time='" + getTime() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", sessionDuration='" + getSessionDuration() + "'" +
            ", previousLoginTime='" + getPreviousLoginTime() + "'" +
            "}";
    }
}
