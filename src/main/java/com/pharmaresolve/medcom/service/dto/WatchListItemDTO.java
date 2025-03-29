package com.pharmaresolve.medcom.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.pharmaresolve.medcom.domain.WatchListItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WatchListItemDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateAdded;

    private Integer priority;

    private Boolean alertEnabled;

    private WatchListDTO watchList;

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

    public Boolean getAlertEnabled() {
        return alertEnabled;
    }

    public void setAlertEnabled(Boolean alertEnabled) {
        this.alertEnabled = alertEnabled;
    }

    public WatchListDTO getWatchList() {
        return watchList;
    }

    public void setWatchList(WatchListDTO watchList) {
        this.watchList = watchList;
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
        if (!(o instanceof WatchListItemDTO)) {
            return false;
        }

        WatchListItemDTO watchListItemDTO = (WatchListItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, watchListItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchListItemDTO{" +
            "id=" + getId() +
            ", dateAdded='" + getDateAdded() + "'" +
            ", priority=" + getPriority() +
            ", alertEnabled='" + getAlertEnabled() + "'" +
            ", watchList=" + getWatchList() +
            ", product=" + getProduct() +
            "}";
    }
}
