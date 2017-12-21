package com.seeu.ywq.release.dvo.apppage;

import com.seeu.ywq.release.model.Image;
import com.seeu.ywq.release.model.Video;
import com.seeu.ywq.release.model.apppage.HomePageVideo;

import javax.persistence.*;
import java.util.Date;

public class HomePageVOVideo {
    private Long id;
    private HomePageVideo.CATEGORY category;
    private String title;
    private Long uid;
    private String nickname;
    private String headIconUrl;
    private Long viewNum;
    private Date createTime;
    private Image coverImage;
    private Video video;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HomePageVideo.CATEGORY getCategory() {
        return category;
    }

    public void setCategory(HomePageVideo.CATEGORY category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getViewNum() {
        return viewNum;
    }

    public void setViewNum(Long viewNum) {
        this.viewNum = viewNum;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl;
    }

    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
