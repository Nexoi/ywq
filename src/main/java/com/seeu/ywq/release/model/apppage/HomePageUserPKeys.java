package com.seeu.ywq.release.model.apppage;

import java.io.Serializable;

public class HomePageUserPKeys implements Serializable {
    private HomePageUser.CATEGORY category;
    private Long uid;

    public HomePageUserPKeys() {
    }

    public HomePageUserPKeys(HomePageUser.CATEGORY category, Long uid) {
        this.category = category;
        this.uid = uid;
    }

    public HomePageUser.CATEGORY getCategory() {
        return category;
    }

    public void setCategory(HomePageUser.CATEGORY category) {
        this.category = category;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((uid == null) ? 0 : uid.hashCode());
        result = PRIME * result + ((category == null) ? 0 : category.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final HomePageUserPKeys other = (HomePageUserPKeys) obj;
        if (uid == null) {
            if (other.uid != null) {
                return false;
            }
        } else if (!uid.equals(other.uid)) {
            return false;
        }
        if (category == null) {
            if (other.category != null) {
                return false;
            }
        } else if (!category.equals(other.category)) {
            return false;
        }
        return true;
    }
}
