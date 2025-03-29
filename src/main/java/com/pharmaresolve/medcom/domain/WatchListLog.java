package com.pharmaresolve.medcom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pharmaresolve.medcom.domain.enumeration.WatchListUpdateType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WatchListLog.
 */
@Entity
@Table(name = "watch_list_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WatchListLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "update_type")
    private WatchListUpdateType updateType;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "user_agent")
    private String userAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "watchList", "product", "alerts" }, allowSetters = true)
    private WatchListItem watchListItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "pharmacy" }, allowSetters = true)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subscription", "admin", "watchList" }, allowSetters = true)
    private Pharmacy pharmacy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WatchListLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WatchListUpdateType getUpdateType() {
        return this.updateType;
    }

    public WatchListLog updateType(WatchListUpdateType updateType) {
        this.setUpdateType(updateType);
        return this;
    }

    public void setUpdateType(WatchListUpdateType updateType) {
        this.updateType = updateType;
    }

    public ZonedDateTime getUpdateTime() {
        return this.updateTime;
    }

    public WatchListLog updateTime(ZonedDateTime updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public WatchListLog userAgent(String userAgent) {
        this.setUserAgent(userAgent);
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public WatchListItem getWatchListItem() {
        return this.watchListItem;
    }

    public void setWatchListItem(WatchListItem watchListItem) {
        this.watchListItem = watchListItem;
    }

    public WatchListLog watchListItem(WatchListItem watchListItem) {
        this.setWatchListItem(watchListItem);
        return this;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public WatchListLog appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    public Pharmacy getPharmacy() {
        return this.pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public WatchListLog pharmacy(Pharmacy pharmacy) {
        this.setPharmacy(pharmacy);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchListLog)) {
            return false;
        }
        return getId() != null && getId().equals(((WatchListLog) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchListLog{" +
            "id=" + getId() +
            ", updateType='" + getUpdateType() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            "}";
    }
}
