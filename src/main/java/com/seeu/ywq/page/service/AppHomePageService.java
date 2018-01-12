package com.seeu.ywq.page.service;

import com.seeu.ywq.exception.ResourceAddException;
import com.seeu.ywq.exception.ResourceAlreadyExistedException;
import com.seeu.ywq.exception.ResourceDeleteException;
import com.seeu.ywq.exception.ResourceNotFoundException;
import com.seeu.ywq.page.dvo.HomePageVOUser;
import com.seeu.ywq.page.dvo.HomePageVOVideo;
import com.seeu.ywq.page.model.Advertisement;
import com.seeu.ywq.page.model.HomePageCategory;
import com.seeu.ywq.page.model.HomePageUser;
import com.seeu.ywq.page.model.HomePageVideo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * APP 首页搜索、列表
 */
public interface AppHomePageService {

    void addAdvertisement(MultipartFile image, Advertisement.CATEGORY category, String url, Integer order) throws ResourceAddException;

    void deleteAdvertisement(Long advertisementId) throws ResourceNotFoundException;

    void addUserConfigurer(Integer category, Long srcId, Integer order) throws ResourceAlreadyExistedException;

    void deleteUserConfigurer(Integer category, Long uid) throws ResourceNotFoundException;

    HomePageVideo addVideo(MultipartFile videoFile, MultipartFile coverImage, Long uid, String title, HomePageVideo.CATEGORY category, Integer order) throws ResourceAddException;

    void deleteVideo(Long videoId) throws ResourceNotFoundException;

    // 查看首页广告列表
    List<Advertisement> getHomePage_Advertisements();

    // 查询该页信息
    List<HomePageCategory> queryAllByPage(HomePageCategory.PAGE page);

    List<HomePageCategory> queryAllByPage(Long visitorUid, HomePageCategory.PAGE page);

    List<HomePageVOUser> getHomePageUsers(Integer category);

    List<HomePageVOUser> getHomePageUsers(Long visitorUid, Integer category);
//    // 新晋网红
//    List<HomePageVOUser> getHomePage_NewHotsPerson();
//
//    // 新晋演员
//    List<HomePageVOUser> getHomePage_NewActors();
//
//    // 新晋尤物
//    List<HomePageVOUser> getYouWuPage_New();
//
//    // 推荐尤物
//    List<HomePageVOUser> getYouWuPage_Suggestion();
//
//    // 新晋网红
//    List<HomePageVOUser> getHotsPerson_New();
//
//    // 推荐网红
//    List<HomePageVOUser> getHotsPerson_Suggestion();

    // 广告
    List<Advertisement> getVideo_Advertisements();

    // 高清视频
    List<HomePageVOVideo> getVideo_HD();

    // 高清视频
    List<HomePageVOVideo> getVideo_HD(Long visitorUid);

    // VR 视频
    List<HomePageVOVideo> getVideo_VR();

    // VR 视频
    List<HomePageVOVideo> getVideo_VR(Long visitorUid);

}
