package com.pharmaresolve.medcom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NotificationPreference.
 */
@Entity
@Table(name = "notification_preference")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationPreference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "email_enabled")
    private Boolean emailEnabled;

    @Column(name = "sms_enabled")
    private Boolean smsEnabled;

    @Column(name = "push_enabled")
    private Boolean pushEnabled;

    @Column(name = "quiet_hours_start")
    private ZonedDateTime quietHoursStart;

    @Column(name = "quiet_hours_end")
    private ZonedDateTime quietHoursEnd;

    @JsonIgnoreProperties(value = { "pharmacy", "notificationPreference" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "notificationPreference")
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NotificationPreference id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEmailEnabled() {
        return this.emailEnabled;
    }

    public NotificationPreference emailEnabled(Boolean emailEnabled) {
        this.setEmailEnabled(emailEnabled);
        return this;
    }

    public void setEmailEnabled(Boolean emailEnabled) {
        this.emailEnabled = emailEnabled;
    }

    public Boolean getSmsEnabled() {
        return this.smsEnabled;
    }

    public NotificationPreference smsEnabled(Boolean smsEnabled) {
        this.setSmsEnabled(smsEnabled);
        return this;
    }

    public void setSmsEnabled(Boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public Boolean getPushEnabled() {
        return this.pushEnabled;
    }

    public NotificationPreference pushEnabled(Boolean pushEnabled) {
        this.setPushEnabled(pushEnabled);
        return this;
    }

    public void setPushEnabled(Boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }

    public ZonedDateTime getQuietHoursStart() {
        return this.quietHoursStart;
    }

    public NotificationPreference quietHoursStart(ZonedDateTime quietHoursStart) {
        this.setQuietHoursStart(quietHoursStart);
        return this;
    }

    public void setQuietHoursStart(ZonedDateTime quietHoursStart) {
        this.quietHoursStart = quietHoursStart;
    }

    public ZonedDateTime getQuietHoursEnd() {
        return this.quietHoursEnd;
    }

    public NotificationPreference quietHoursEnd(ZonedDateTime quietHoursEnd) {
        this.setQuietHoursEnd(quietHoursEnd);
        return this;
    }

    public void setQuietHoursEnd(ZonedDateTime quietHoursEnd) {
        this.quietHoursEnd = quietHoursEnd;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        if (this.appUser != null) {
            this.appUser.setNotificationPreference(null);
        }
        if (appUser != null) {
            appUser.setNotificationPreference(this);
        }
        this.appUser = appUser;
    }

    public NotificationPreference appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationPreference)) {
            return false;
        }
        return getId() != null && getId().equals(((NotificationPreference) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationPreference{" +
            "id=" + getId() +
            ", emailEnabled='" + getEmailEnabled() + "'" +
            ", smsEnabled='" + getSmsEnabled() + "'" +
            ", pushEnabled='" + getPushEnabled() + "'" +
            ", quietHoursStart='" + getQuietHoursStart() + "'" +
            ", quietHoursEnd='" + getQuietHoursEnd() + "'" +
            "}";
    }
}
