package com.pharmaresolve.medcom.service.dto;

import com.pharmaresolve.medcom.domain.enumeration.WatchListUpdateType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.pharmaresolve.medcom.domain.WatchListLog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WatchListLogDTO implements Serializable {

    private Long id;

    private WatchListUpdateType updateType;

    private ZonedDateTime updateTime;

    private String userAgent;

    private WatchListItemDTO watchListItem;

    private AppUserDTO appUser;

    private PharmacyDTO pharmacy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WatchListUpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(WatchListUpdateType updateType) {
        this.updateType = updateType;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public WatchListItemDTO getWatchListItem() {
        return watchListItem;
    }

    public void setWatchListItem(WatchListItemDTO watchListItem) {
        this.watchListItem = watchListItem;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
    }

    public PharmacyDTO getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(PharmacyDTO pharmacy) {
        this.pharmacy = pharmacy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchListLogDTO)) {
            return false;
        }

        WatchListLogDTO watchListLogDTO = (WatchListLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, watchListLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchListLogDTO{" +
            "id=" + getId() +
            ", updateType='" + getUpdateType() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", watchListItem=" + getWatchListItem() +
            ", appUser=" + getAppUser() +
            ", pharmacy=" + getPharmacy() +
            "}";
    }
}
