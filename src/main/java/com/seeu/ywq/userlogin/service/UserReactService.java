package com.seeu.ywq.userlogin.service;

import com.seeu.ywq.page.dvo.SimpleUserVO;
import com.seeu.ywq.userlogin.dvo.UserLoginVO;
import com.seeu.ywq.userlogin.model.UserLogin;

/**
 * 辅助设计，对用户的基本交互（规避权限安全问题）
 */
public interface UserReactService {
    STATUS likeMe(Long myUid, Long hisUid);

    STATUS cancelLikeMe(Long myUid, Long hisUid);

    Boolean exists(Long uid);

    UserLogin findOne(Long uid);

    UserLoginVO findOneWithSafety(Long uid);

    UserLogin findByPhone(String phone);

    SimpleUserVO findOneAndTransferToVO(Long uid);

    String getPhone(Long uid);

    String getWeChatID(Long uid);

    /* save **/
    UserLogin save(UserLogin userLogin);

    UserLogin saveNickName(Long uid, String nickname);

    public enum STATUS {
        success,
        exist,
        not_exist,
        contradiction // 矛盾
    }
}
