package com.pharmaresolve.medcom.service.dto;

import com.pharmaresolve.medcom.domain.enumeration.BillingCycle;
import com.pharmaresolve.medcom.domain.enumeration.NotificationType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.pharmaresolve.medcom.domain.PharmacySubscription} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PharmacySubscriptionDTO implements Serializable {

    private Long id;

    private String plan;

    private Integer maxWatchListItems;

    private Integer maxUsers;

    private Integer maxEmailsPerMonth;

    private Integer maxSmsPerMonth;

    private BigDecimal price;

    private BillingCycle billingCycle;

    private Integer trialPeriodDays;

    private Boolean active;

    private NotificationType notificationTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Integer getMaxWatchListItems() {
        return maxWatchListItems;
    }

    public void setMaxWatchListItems(Integer maxWatchListItems) {
        this.maxWatchListItems = maxWatchListItems;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Integer getMaxEmailsPerMonth() {
        return maxEmailsPerMonth;
    }

    public void setMaxEmailsPerMonth(Integer maxEmailsPerMonth) {
        this.maxEmailsPerMonth = maxEmailsPerMonth;
    }

    public Integer getMaxSmsPerMonth() {
        return maxSmsPerMonth;
    }

    public void setMaxSmsPerMonth(Integer maxSmsPerMonth) {
        this.maxSmsPerMonth = maxSmsPerMonth;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

    public Integer getTrialPeriodDays() {
        return trialPeriodDays;
    }

    public void setTrialPeriodDays(Integer trialPeriodDays) {
        this.trialPeriodDays = trialPeriodDays;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public NotificationType getNotificationTypes() {
        return notificationTypes;
    }

    public void setNotificationTypes(NotificationType notificationTypes) {
        this.notificationTypes = notificationTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PharmacySubscriptionDTO)) {
            return false;
        }

        PharmacySubscriptionDTO pharmacySubscriptionDTO = (PharmacySubscriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pharmacySubscriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PharmacySubscriptionDTO{" +
            "id=" + getId() +
            ", plan='" + getPlan() + "'" +
            ", maxWatchListItems=" + getMaxWatchListItems() +
            ", maxUsers=" + getMaxUsers() +
            ", maxEmailsPerMonth=" + getMaxEmailsPerMonth() +
            ", maxSmsPerMonth=" + getMaxSmsPerMonth() +
            ", price=" + getPrice() +
            ", billingCycle='" + getBillingCycle() + "'" +
            ", trialPeriodDays=" + getTrialPeriodDays() +
            ", active='" + getActive() + "'" +
            ", notificationTypes='" + getNotificationTypes() + "'" +
            "}";
    }
}
