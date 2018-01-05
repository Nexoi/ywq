package com.seeu.ywq.page.service;

import com.seeu.ywq.page.dvo.PublishLiteVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublishLiteService {
    Page<PublishLiteVO> findAllByTagIds(Long visitorUid, Pageable pageable, Long... ids);

    Page<PublishLiteVO> findAllByFollowedUids(Long visitorUid, Pageable pageable, Long... uids);

    Page<PublishLiteVO> findAllByUid(Long visitorUid, Long uid, Pageable pageable);
}
