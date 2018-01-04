package com.seeu.ywq.resource.service;

import com.seeu.ywq.exception.PublishNotFoundException;

import java.util.Date;

public interface ResourceAuthService {
    boolean canVisit(Long uid, Long publishId, Date currentTime);

    boolean canVisit(Long uid, Long resourceId);

    /**
     * 激活该资源
     * 按天数计算，若还在激活状态则叠加天数；若不在激活状态，则以今天为基数叠加天数
     *
     * @param uid
     * @param resourceId
     * @param day
     * @throws PublishNotFoundException
     */
    void activeResource(Long uid, Long resourceId, Integer day);
}