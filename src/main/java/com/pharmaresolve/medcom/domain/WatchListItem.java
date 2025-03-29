package com.pharmaresolve.medcom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WatchListItem.
 */
@Entity
@Table(name = "watch_list_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WatchListItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_added")
    private ZonedDateTime dateAdded;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "alert_enabled")
    private Boolean alertEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "pharmacy", "watchListItems" }, allowSetters = true)
    private WatchList watchList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "watchListItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "watchListItem", "notifications" }, allowSetters = true)
    private Set<Alert> alerts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WatchListItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateAdded() {
        return this.dateAdded;
    }

    public WatchListItem dateAdded(ZonedDateTime dateAdded) {
        this.setDateAdded(dateAdded);
        return this;
    }

    public void setDateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public WatchListItem priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getAlertEnabled() {
        return this.alertEnabled;
    }

    public WatchListItem alertEnabled(Boolean alertEnabled) {
        this.setAlertEnabled(alertEnabled);
        return this;
    }

    public void setAlertEnabled(Boolean alertEnabled) {
        this.alertEnabled = alertEnabled;
    }

    public WatchList getWatchList() {
        return this.watchList;
    }

    public void setWatchList(WatchList watchList) {
        this.watchList = watchList;
    }

    public WatchListItem watchList(WatchList watchList) {
        this.setWatchList(watchList);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public WatchListItem product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Set<Alert> getAlerts() {
        return this.alerts;
    }

    public void setAlerts(Set<Alert> alerts) {
        if (this.alerts != null) {
            this.alerts.forEach(i -> i.setWatchListItem(null));
        }
        if (alerts != null) {
            alerts.forEach(i -> i.setWatchListItem(this));
        }
        this.alerts = alerts;
    }

    public WatchListItem alerts(Set<Alert> alerts) {
        this.setAlerts(alerts);
        return this;
    }

    public WatchListItem addAlert(Alert alert) {
        this.alerts.add(alert);
        alert.setWatchListItem(this);
        return this;
    }

    public WatchListItem removeAlert(Alert alert) {
        this.alerts.remove(alert);
        alert.setWatchListItem(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchListItem)) {
            return false;
        }
        return getId() != null && getId().equals(((WatchListItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchListItem{" +
            "id=" + getId() +
            ", dateAdded='" + getDateAdded() + "'" +
            ", priority=" + getPriority() +
            ", alertEnabled='" + getAlertEnabled() + "'" +
            "}";
    }
}
