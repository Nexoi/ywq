package com.seeu.ywq.release.model;

import io.swagger.annotations.ApiParam;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 点赞列表
 */
@Entity
@IdClass(PublishLikedUserPKeys.class)
@Table(name = "publish_liked_users")
public class PublishLikedUser implements Serializable {

    @ApiParam(hidden = true)
    @Id
    private Long uid;

    @ApiParam(hidden = true)
    private String headIconUrl;// 点赞时候的头像

    @Id
    @Column(name = "publish_id")
    private Long publishId;

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

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl;
    }
}