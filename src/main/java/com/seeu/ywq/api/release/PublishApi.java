package com.seeu.ywq.api.release;

import com.seeu.core.R;
import com.seeu.ywq.release.dvo.PublishVO;
import com.seeu.ywq.release.model.Picture;
import com.seeu.ywq.release.model.Publish;
import com.seeu.ywq.release.repository.PublishLikedUserRepository;
import com.seeu.ywq.release.repository.PublishRepository;
import com.seeu.ywq.release.service.PublishService;
import com.seeu.ywq.release.service.UserPictureService;
import com.seeu.ywq.userlogin.model.UserLogin;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Api(tags = {"动态"}, description = "发布新动态/查看动态")
@RestController
@RequestMapping("/api/v1/publish")
public class PublishApi {
    @Resource
    private UserPictureService userPictureService;
    @Autowired
    private PublishService publishService;
    @Resource
    private PublishRepository publishRepository;

    @ApiOperation(value = "获取某一条动态", notes = "根据发布动态ID获取动态内容")
    @ApiResponse(code = 404, message = "找不到该动态")
    @GetMapping("/{publishId}")
    public ResponseEntity get(@AuthenticationPrincipal UserLogin authUser, // 如果未登陆依然可以查看动态内容，但是内容可能会被限制
                              @PathVariable("publishId") Long publishId) {
        if (authUser == null) {
            PublishVO vo = publishService.viewIt(publishId);
            return (vo == null) ? ResponseEntity.status(404).body(R.code(404).message("找不到该动态").build()) : ResponseEntity.ok(vo);
        } else {
            PublishVO vo = publishService.viewIt(publishId, authUser.getUid());
            return (vo == null) ? ResponseEntity.status(404).body(R.code(404).message("找不到该动态").build()) : ResponseEntity.ok(vo);
        }
    }


    /**
     * 发布新动态
     *
     * @param authUser
     * @param publish
     * @param pictureType
     * @param images
     * @return
     */
    @ApiOperation(value = "发布新动态", notes = "发布新动态，根据不同的动态类型（type字段）传入不同的数据")
    @ApiResponses({
            @ApiResponse(code = 201, message = "发布成功"),
            @ApiResponse(code = 400, message = "400 数据错误"),
            @ApiResponse(code = 500, message = "500 服务器异常，文件传输失败"),
    })
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity add(@AuthenticationPrincipal UserLogin authUser,
                              Publish publish,
                              @ApiParam(value = "照片类型，数组，用逗号隔开，可选值：open、close，分别表示：公开、私密。如：open,close,close", example = "open,close,open")
                                      Picture.ALBUM_TYPE[] pictureType,// 照片类型（公开1/私密0）
                              MultipartFile[] images) {
        if (publish.getType() != null && publish.getType() == Publish.PUBLISH_TYPE.picture) {
            if (images == null || images.length == 0)
                return ResponseEntity.badRequest().body(R.code(4001).message("请传入至少一张图片").build());
            if (images.length != pictureType.length)
                return ResponseEntity.badRequest().body(R.code(4002).message("参数错误，pictureType 长度需要和 images 一致").build());
        }
        // 初始化判断
        if (publish.getTitle() == null || publish.getTitle().trim().length() == 0)
            return ResponseEntity.badRequest().body(R.code(4003).message("标题内容不能为空").build());
        if (publish.getText() == null && Publish.PUBLISH_TYPE.word == publish.getType())
            return ResponseEntity.badRequest().body(R.code(4004).message("文本内容不能为空").build());

        // 数据规整
        publish.setId(null);
        publish.setUid(authUser.getUid());
        publish.setCommentNum(0);
        publish.setLikedUsers(null);
        publish.setLikeNum(0);
        publish.setViewNum(0);
        publish.setState(Publish.PUBLIC_STATUS.normal); // 初始化为正常
        publish.setUnlockPrice(publish.getUnlockPrice() == null ? BigDecimal.ZERO : publish.getUnlockPrice());
        publish.setCreateTime(new Date());
        publish.setType(publish.getType() == null ? Publish.PUBLISH_TYPE.word : publish.getType());
        try {
            switch (publish.getType()) {
                case word:
                    publish.setPictures(null);
                    publish.setUnlockPrice(BigDecimal.ZERO);
                    publish.setVideoUrls(null);
                    publish.setCoverVideoUrl(null);
                    break;
                case video:
                    publish.setPictures(null);
                    break;
                case picture:
                    publish.setPictures(userPictureService.getPictureWithOutSave(authUser.getUid(), publish.getId(), pictureType, images));  // 图片信息
                    publish.setVideoUrls(null);
                    publish.setCoverVideoUrl(null);
                    break;
                default:
                    publish.setPictures(userPictureService.getPictureWithOutSave(authUser.getUid(), publish.getId(), pictureType, images));  // 图片信息
                    break;
            }
            // 发布信息持久化
            return ResponseEntity.status(201).body(publishService.transferToVO(publishRepository.save(publish)));
        } catch (Exception e) {
            // 注意回滚（如果异常，阿里云可能会存储部分图片，但本地可能无对应图片信息）
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.code(500).message("服务器异常，文件传输失败").build());
        }
    }

    @ApiOperation(value = "删除某一条动态【本人】", notes = "根据发布动态ID删除动态")
    @DeleteMapping("/{publishId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity delete(@AuthenticationPrincipal UserLogin authUser,
                                 @PathVariable("publishId") Long publishId) {
        Publish publish = publishRepository.findByIdAndUid(publishId, authUser.getUid());
        if (publish == null) {
            return ResponseEntity.status(404).body(R.code(404).message("您无此动态信息").build());
        }
        if (!publish.getUid().equals(authUser.getUid())) {
            return ResponseEntity.badRequest().body(R.code(400).message("不能删除非本人的动态信息").build());
        }
        // 会一并清除点赞、评论等信息
        publishService.deletePublish(publishId);
        return ResponseEntity.ok().build();
    }
}