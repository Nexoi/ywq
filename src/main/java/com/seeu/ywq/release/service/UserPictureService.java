package com.seeu.ywq.release.service;

import com.seeu.ywq.release.dvo.PublishPictureVO;
import com.seeu.ywq.release.model.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserPictureService {

    /**
     * @param targetUid 目标用户【本人】，查看自己的相册时候会使用
     * @return
     */
    Page findAllMine(Long targetUid, Picture.ALBUM_TYPE albumType, PageRequest pageRequest);


    /**
     * 存储图片（公开/私密），并持久化
     *
     * @param uid
     * @param publishId
     * @param albumTypes
     * @param images
     * @return
     * @throws Exception
     */
    List<PublishPictureVO> savePictures(Long uid, Long publishId, Picture.ALBUM_TYPE[] albumTypes, MultipartFile[] images) throws Exception;

    /**
     * 发布内容时使用，不持久化 Picture，但会持久化 Image（阿里云存储后即会持久化到本地数据库）
     *
     * @param uid
     * @param publishId
     * @param albumTypes
     * @param images
     * @return
     * @throws Exception
     */
    List<Picture> getPictureWithOutSave(Long uid, Long publishId, Picture.ALBUM_TYPE[] albumTypes, MultipartFile[] images) throws Exception;

    boolean canVisit(Long uid, Long pictureId);

    /**
     * 匿名访问时
     *
     * @param picture
     * @return
     */
    PublishPictureVO transferToVO(Picture picture);

    /**
     * 实名访问时，包括【本人】访问
     *
     * @param picture
     * @param uid
     * @return
     */
    PublishPictureVO transferToVO(Picture picture, Long uid);

    /**
     * 匿名访问时
     *
     * @param pictures
     * @return
     */
    List<PublishPictureVO> transferToVO(List<Picture> pictures);

    /**
     * 实名访问时，包括【本人】访问
     *
     * @param pictures
     * @param uid
     * @return
     */
    List<PublishPictureVO> transferToVO(List<Picture> pictures, Long uid);
}