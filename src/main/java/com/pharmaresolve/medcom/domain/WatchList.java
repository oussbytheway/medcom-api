package com.pharmaresolve.medcom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WatchList.
 */
@Entity
@Table(name = "watch_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WatchList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "jhi_limit")
    private Integer limit;

    @JsonIgnoreProperties(value = { "subscription", "admin", "watchList" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Pharmacy pharmacy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "watchList")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "watchList", "product", "alerts" }, allowSetters = true)
    private Set<WatchListItem> watchListItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WatchList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public WatchList name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public WatchList created(ZonedDateTime created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public WatchList limit(Integer limit) {
        this.setLimit(limit);
        return this;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Pharmacy getPharmacy() {
        return this.pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public WatchList pharmacy(Pharmacy pharmacy) {
        this.setPharmacy(pharmacy);
        return this;
    }

    public Set<WatchListItem> getWatchListItems() {
        return this.watchListItems;
    }

    public void setWatchListItems(Set<WatchListItem> watchListItems) {
        if (this.watchListItems != null) {
            this.watchListItems.forEach(i -> i.setWatchList(null));
        }
        if (watchListItems != null) {
            watchListItems.forEach(i -> i.setWatchList(this));
        }
        this.watchListItems = watchListItems;
    }

    public WatchList watchListItems(Set<WatchListItem> watchListItems) {
        this.setWatchListItems(watchListItems);
        return this;
    }

    public WatchList addWatchListItem(WatchListItem watchListItem) {
        this.watchListItems.add(watchListItem);
        watchListItem.setWatchList(this);
        return this;
    }

    public WatchList removeWatchListItem(WatchListItem watchListItem) {
        this.watchListItems.remove(watchListItem);
        watchListItem.setWatchList(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchList)) {
            return false;
        }
        return getId() != null && getId().equals(((WatchList) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", limit=" + getLimit() +
            "}";
    }
}
