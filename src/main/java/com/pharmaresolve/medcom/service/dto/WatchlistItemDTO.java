package com.pharmaresolve.medcom.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.pharmaresolve.medcom.domain.WatchlistItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WatchlistItemDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateAdded;

    private Integer priority;

    private String addedBy;

    private Boolean alertEnabled;

    private WatchlistDTO watchlist;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Boolean getAlertEnabled() {
        return alertEnabled;
    }

    public void setAlertEnabled(Boolean alertEnabled) {
        this.alertEnabled = alertEnabled;
    }

    public WatchlistDTO getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(WatchlistDTO watchlist) {
        this.watchlist = watchlist;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchlistItemDTO)) {
            return false;
        }

        WatchlistItemDTO watchlistItemDTO = (WatchlistItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, watchlistItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchlistItemDTO{" +
            "id=" + getId() +
            ", dateAdded='" + getDateAdded() + "'" +
            ", priority=" + getPriority() +
            ", addedBy='" + getAddedBy() + "'" +
            ", alertEnabled='" + getAlertEnabled() + "'" +
            ", watchlist=" + getWatchlist() +
            ", product=" + getProduct() +
            "}";
    }
}
