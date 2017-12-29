package com.seeu.ywq.release.service.apppage.impl;

import com.seeu.ywq.release.dvo.apppage.*;
import com.seeu.ywq.release.model.Image;
import com.seeu.ywq.release.model.Publish;
import com.seeu.ywq.release.model.PublishVideo;
import com.seeu.ywq.release.model.Video;
import com.seeu.ywq.release.model.apppage.HomePageVideo;
import com.seeu.ywq.release.model.apppage.PublishLite;
import com.seeu.ywq.release.service.apppage.AppVOService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AppVOServiceImpl implements AppVOService {

    @Override
    public HomePageVOUser formUserVO(Object[] objects) {
        if (objects == null || objects.length != 13) return null;// 长度必须是 13 个
        HomePageVOUser vo = new HomePageVOUser();
        vo.setUid(parseLong(objects[0]));
        vo.setNickname(parseString(objects[1]));
        vo.setLikeNum(parseLong(objects[2]));
        vo.setHeadIconUrl(parseString(objects[3]));
        vo.setIdentifications(parseBytesToLongList(objects[4]));
        Image image = new Image();
        image.setId(parseLong(objects[5]));
        image.setHeight(parseInt(objects[6]));
        image.setWidth(parseInt(objects[7]));
        image.setImageUrl(parseString(objects[8]));
        image.setThumbImage100pxUrl(parseString(objects[9]));
        image.setThumbImage200pxUrl(parseString(objects[10]));
        image.setThumbImage300pxUrl(parseString(objects[11]));
        image.setThumbImage500pxUrl(parseString(objects[12]));
        vo.setCoverImage(image);
        return vo;
    }

    @Override
    public List<HomePageVOUser> formUserVO(List<Object[]> objects) {
        if (objects == null || objects.size() == 0) return new ArrayList<>();
        List<HomePageVOUser> vos = new ArrayList<>();
        for (Object[] object : objects) {
            vos.add(formUserVO(object));
        }
        return vos;
    }

    @Override
    public HomePageVOVideo formVideoVO(Object[] objects) {
        if (objects == null || objects.length != 19) return null;// 长度必须是 19 个
        HomePageVOVideo vo = new HomePageVOVideo();
        vo.setId(parseLong(objects[0]));
        vo.setCategory(parseCATEGORY(objects[1]));
        vo.setTitle(parseString(objects[2]));
        vo.setUid(parseLong(objects[3]));
        vo.setTitle(parseString(objects[4]));
        vo.setHeadIconUrl(parseString(objects[5]));
        vo.setViewNum(parseLong(objects[6]));
        vo.setCreateTime(parseDate(objects[7]));
        Image image = new Image();
        image.setId(parseLong(objects[8]));
        image.setHeight(parseInt(objects[9]));
        image.setWidth(parseInt(objects[10]));
        image.setImageUrl(parseString(objects[11]));
        image.setThumbImage100pxUrl(parseString(objects[12]));
        image.setThumbImage200pxUrl(parseString(objects[13]));
        image.setThumbImage300pxUrl(parseString(objects[14]));
        image.setThumbImage500pxUrl(parseString(objects[15]));
        Video video = new Video();
        video.setId(parseLong(objects[16]));
        video.setCoverUrl(parseString(objects[17]));
        video.setSrcUrl(parseString(objects[18]));

        vo.setCoverImage(image);
        vo.setVideo(video);
        return vo;
    }

    @Override
    public List<HomePageVOVideo> formVideoVO(List<Object[]> objects) {
        if (objects == null || objects.size() == 0) return new ArrayList<>();
        List<HomePageVOVideo> vos = new ArrayList<>();
        for (Object[] object : objects) {
            vos.add(formVideoVO(object));
        }
        return vos;
    }

    @Override
    public PositionUserVO formPositionUserVO(Object[] objects, BigDecimal longitude, BigDecimal latitude) {
        if (objects == null || objects.length != 6) return null;// 长度必须是 6 个
        PositionUserVO vo = new PositionUserVO();
        vo.setUid(parseLong(objects[0]));
        vo.setNickname(parseString(objects[1]));
        vo.setHeadIconUrl(parseString(objects[2]));
        vo.setIdentifications(parseBytesToLongList(objects[3]));
        if (objects[4] == null || objects[5] == null)
            return vo;
        Double longitudeDlt = parseDouble(objects[4]) - longitude.doubleValue();
        Double latitudeDlt = parseDouble(objects[5]) - latitude.doubleValue();
        Double radius = Math.sqrt(longitudeDlt * longitudeDlt + latitudeDlt * latitudeDlt);
        vo.setDistance(BigDecimal.valueOf(radius * 100000).setScale(2, BigDecimal.ROUND_UP)); // 转换成米
        return vo;
    }

    @Override
    public List<PositionUserVO> formPositionUserVO(List<Object[]> objects, BigDecimal longitude, BigDecimal latitude) {
        if (objects == null || objects.size() == 0) return new ArrayList<>();
        List<PositionUserVO> vos = new ArrayList<>();
        for (Object[] object : objects) {
            vos.add(formPositionUserVO(object, longitude, latitude));
        }
        return vos;
    }

    @Override
    public PositionUserVO formPositionDistanceUserVO(Object[] objects) {
        if (objects == null || objects.length != 5) return null;// 长度必须是 5 个
        PositionUserVO vo = new PositionUserVO();
        vo.setUid(parseLong(objects[0]));
        vo.setNickname(parseString(objects[1]));
        vo.setHeadIconUrl(parseString(objects[2]));
        vo.setIdentifications(parseBytesToLongList(objects[3]));
        if (objects[4] == null)
            return vo;
        vo.setDistance(BigDecimal.valueOf(parseDouble(objects[4])).setScale(2, BigDecimal.ROUND_UP)); // 千米
        return vo;
    }

    @Override
    public List<PositionUserVO> formPositionDistanceUserVO(List<Object[]> objects) {
        if (objects == null || objects.size() == 0) return new ArrayList<>();
        List<PositionUserVO> vos = new ArrayList<>();
        for (Object[] object : objects) {
            vos.add(formPositionDistanceUserVO(object));
        }
        return vos;
    }

    @Override
    public PublishLite formPublishLite(Object[] objects) {
        if (objects == null || objects.length != 15) return null;// 长度必须是 15 个
        PublishLite vo = new PublishLite();
        vo.setId(parseLong(objects[0]));
        vo.setUid(parseLong(objects[1]));
        vo.setWeight(parseInt(objects[2]));
        vo.setType(paresPUBLISH_TYPE(objects[3]));
        vo.setTitle(parseString(objects[4]));
        vo.setCreateTime(parseDate(objects[5]));
        vo.setUnlockPrice((parseDouble(objects[6])).longValue());
        vo.setViewNum(parseInt(objects[7]));
        vo.setCommentNum(parseInt(objects[8]));
        vo.setLikeNum(parseInt(objects[9]));
        vo.setLabels(parseString(objects[10]));
        vo.setText(parseString(objects[11]));
        // 视频更多详情信息可在此补足
        Video video = new Video();
        video.setId(parseLong(objects[12]));
        video.setCoverUrl(parseString(objects[13]));
        video.setSrcUrl(parseString(objects[14]));
        PublishVideo publishVideo = new PublishVideo();
        publishVideo.setVideo(video);
        vo.setVideo(publishVideo);
        vo.setPictures(new ArrayList<>());
        return vo;
    }

    @Override
    public List<PublishLite> formPublishLite(List<Object[]> objects) {
        if (objects == null || objects.size() == 0) return new ArrayList<>();
        List<PublishLite> list = new ArrayList();
        for (Object[] object : objects) {
            list.add(formPublishLite(object));
        }
        return list;
    }

    @Override
    public PageBalance formPageBalance(Object[] objects) {
        if (objects == null || objects.length != 4) return null;// 长度必须是 4 个
        PageBalance pb = new PageBalance();
        pb.setUid(parseLong(objects[0]));
        pb.setBalance(parseLong(objects[1]));
        pb.setNickname(parseString(objects[2]));
        pb.setHeadIconUrl(parseString(objects[3]));
        return pb;
    }

    @Override
    public List<PageBalance> formPageBalance(List<Object[]> objects) {
        if (objects == null || objects.size() == 0) return new ArrayList<>();
        List<PageBalance> list = new ArrayList();
        for (Object[] object : objects) {
            list.add(formPageBalance(object));
        }
        return list;
    }

    private List<Long> parseBytesToLongList(Object object) {
        if (object == null) return new ArrayList<>();
        byte[] bytes = (byte[]) object;
        if (bytes.length == 0) return new ArrayList<>();
        List<Long> longs = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < bytes.length; i++) {
            byte id = bytes[i];
            if (id == 44) {
                // 这是逗号
                longs.add(parseLong(temp)); // 转成对应的 int 值
                temp = "";
            } else if (i + 1 == bytes.length) {
                // 这是句末
                temp += Byte.toUnsignedLong(id) - 48;
                longs.add(parseLong(temp));
                temp = "";
            } else {
                temp += Byte.toUnsignedLong(id) - 48;
            }
        }
        return longs;
    }

    private Integer parseInt(Object object) {
        if (object == null) return 0;
        return Integer.parseInt(object.toString());
    }


    private Long parseLong(Object object) {
        if (object == null) return 0l;
        return Long.parseLong(object.toString());
    }

    private String parseString(Object object) {
        if (object == null) return null;
        return object.toString();
    }

    private Double parseDouble(Object object) {
        if (object == null) return null;
        return Double.parseDouble(object.toString());
    }

    private Date parseDate(Object object) {
        if (object == null) return null;
        // TODO
//        return new Date(object.toString());
        return null;
    }

    private HomePageVideo.CATEGORY parseCATEGORY(Object object) {
        if (object == null) return null;
        int categoryIndex = Integer.parseInt(object.toString());
        return (categoryIndex == 0) ? HomePageVideo.CATEGORY.hd : HomePageVideo.CATEGORY.vr;
    }


    private Publish.PUBLISH_TYPE paresPUBLISH_TYPE(Object object) {
        if (object == null) return null;
        int categoryIndex = Integer.parseInt(object.toString());
        switch (categoryIndex) {
            case 0:
                return Publish.PUBLISH_TYPE.word;
            case 1:
                return Publish.PUBLISH_TYPE.picture;
            case 2:
                return Publish.PUBLISH_TYPE.video;
            default:
                return Publish.PUBLISH_TYPE.word;
        }
    }
}
