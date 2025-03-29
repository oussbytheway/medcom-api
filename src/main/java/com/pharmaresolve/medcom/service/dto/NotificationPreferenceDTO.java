package com.pharmaresolve.medcom.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.pharmaresolve.medcom.domain.NotificationPreference} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationPreferenceDTO implements Serializable {

    private Long id;

    private Boolean emailEnabled;

    private Boolean smsEnabled;

    private Boolean pushEnabled;

    private ZonedDateTime quietHoursStart;

    private ZonedDateTime quietHoursEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEmailEnabled() {
        return emailEnabled;
    }

    public void setEmailEnabled(Boolean emailEnabled) {
        this.emailEnabled = emailEnabled;
    }

    public Boolean getSmsEnabled() {
        return smsEnabled;
    }

    public void setSmsEnabled(Boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public Boolean getPushEnabled() {
        return pushEnabled;
    }

    public void setPushEnabled(Boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }

    public ZonedDateTime getQuietHoursStart() {
        return quietHoursStart;
    }

    public void setQuietHoursStart(ZonedDateTime quietHoursStart) {
        this.quietHoursStart = quietHoursStart;
    }

    public ZonedDateTime getQuietHoursEnd() {
        return quietHoursEnd;
    }

    public void setQuietHoursEnd(ZonedDateTime quietHoursEnd) {
        this.quietHoursEnd = quietHoursEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationPreferenceDTO)) {
            return false;
        }

        NotificationPreferenceDTO notificationPreferenceDTO = (NotificationPreferenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationPreferenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationPreferenceDTO{" +
            "id=" + getId() +
            ", emailEnabled='" + getEmailEnabled() + "'" +
            ", smsEnabled='" + getSmsEnabled() + "'" +
            ", pushEnabled='" + getPushEnabled() + "'" +
            ", quietHoursStart='" + getQuietHoursStart() + "'" +
            ", quietHoursEnd='" + getQuietHoursEnd() + "'" +
            "}";
    }
}
