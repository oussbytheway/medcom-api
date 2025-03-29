package com.pharmaresolve.medcom.domain;

import com.pharmaresolve.medcom.domain.enumeration.BillingCycle;
import com.pharmaresolve.medcom.domain.enumeration.NotificationType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PharmacySubscription.
 */
@Entity
@Table(name = "pharmacy_subscription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PharmacySubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "plan")
    private String plan;

    @Column(name = "max_watch_list_items")
    private Integer maxWatchListItems;

    @Column(name = "max_users")
    private Integer maxUsers;

    @Column(name = "max_emails_per_month")
    private Integer maxEmailsPerMonth;

    @Column(name = "max_sms_per_month")
    private Integer maxSmsPerMonth;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_cycle")
    private BillingCycle billingCycle;

    @Column(name = "trial_period_days")
    private Integer trialPeriodDays;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_types")
    private NotificationType notificationTypes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PharmacySubscription id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlan() {
        return this.plan;
    }

    public PharmacySubscription plan(String plan) {
        this.setPlan(plan);
        return this;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Integer getMaxWatchListItems() {
        return this.maxWatchListItems;
    }

    public PharmacySubscription maxWatchListItems(Integer maxWatchListItems) {
        this.setMaxWatchListItems(maxWatchListItems);
        return this;
    }

    public void setMaxWatchListItems(Integer maxWatchListItems) {
        this.maxWatchListItems = maxWatchListItems;
    }

    public Integer getMaxUsers() {
        return this.maxUsers;
    }

    public PharmacySubscription maxUsers(Integer maxUsers) {
        this.setMaxUsers(maxUsers);
        return this;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Integer getMaxEmailsPerMonth() {
        return this.maxEmailsPerMonth;
    }

    public PharmacySubscription maxEmailsPerMonth(Integer maxEmailsPerMonth) {
        this.setMaxEmailsPerMonth(maxEmailsPerMonth);
        return this;
    }

    public void setMaxEmailsPerMonth(Integer maxEmailsPerMonth) {
        this.maxEmailsPerMonth = maxEmailsPerMonth;
    }

    public Integer getMaxSmsPerMonth() {
        return this.maxSmsPerMonth;
    }

    public PharmacySubscription maxSmsPerMonth(Integer maxSmsPerMonth) {
        this.setMaxSmsPerMonth(maxSmsPerMonth);
        return this;
    }

    public void setMaxSmsPerMonth(Integer maxSmsPerMonth) {
        this.maxSmsPerMonth = maxSmsPerMonth;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public PharmacySubscription price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BillingCycle getBillingCycle() {
        return this.billingCycle;
    }

    public PharmacySubscription billingCycle(BillingCycle billingCycle) {
        this.setBillingCycle(billingCycle);
        return this;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

    public Integer getTrialPeriodDays() {
        return this.trialPeriodDays;
    }

    public PharmacySubscription trialPeriodDays(Integer trialPeriodDays) {
        this.setTrialPeriodDays(trialPeriodDays);
        return this;
    }

    public void setTrialPeriodDays(Integer trialPeriodDays) {
        this.trialPeriodDays = trialPeriodDays;
    }

    public Boolean getActive() {
        return this.active;
    }

    public PharmacySubscription active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public NotificationType getNotificationTypes() {
        return this.notificationTypes;
    }

    public PharmacySubscription notificationTypes(NotificationType notificationTypes) {
        this.setNotificationTypes(notificationTypes);
        return this;
    }

    public void setNotificationTypes(NotificationType notificationTypes) {
        this.notificationTypes = notificationTypes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PharmacySubscription)) {
            return false;
        }
        return getId() != null && getId().equals(((PharmacySubscription) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PharmacySubscription{" +
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
