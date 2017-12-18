package com.seeu.ywq.release.model;

import java.io.Serializable;

public class Publish$UserKeys implements Serializable {
    private Long publishId;
    private Long uid;

    public Publish$UserKeys() {
    }

    public Publish$UserKeys(Long publishId, Long uid) {
        this.publishId = publishId;
        this.uid = uid;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
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
        result = PRIME * result + ((publishId == null) ? 0 : publishId.hashCode());
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

        final Publish$UserKeys other = (Publish$UserKeys) obj;
        if (uid == null) {
            if (other.uid != null) {
                return false;
            }
        } else if (!uid.equals(other.uid)) {
            return false;
        }
        if (publishId == null) {
            if (other.publishId != null) {
                return false;
            }
        } else if (!publishId.equals(other.publishId)) {
            return false;
        }
        return true;
    }
}
