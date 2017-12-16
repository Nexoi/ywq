package com.seeu.ywq.api.release;

import com.seeu.core.R;
import com.seeu.ywq.release.dvo.PhotoWallVO;
import com.seeu.ywq.release.model.User;
import com.seeu.ywq.release.repository.UserRepository;
import com.seeu.ywq.release.service.UserPhotoWallService;
import com.seeu.ywq.userlogin.model.UserLogin;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户基本信息的CRUD
 */
@Api(tags = "用户信息", description = "用户信息查看/修改")
@RestController("userInfoApi")
@RequestMapping("/api/v1/user")
public class UserInfoApi {

    @Resource
    private UserRepository userRepository;
    @Autowired
    private UserPhotoWallService userPhotoWallService;

    /**
     * 查看用户所有信息【本人】
     *
     * @param authUser
     * @return
     */
    @ApiOperation(value = "查看用户所有信息【本人】", notes = "查看用户个人信息，用户需要处于已登陆状态。信息包含：基本信息（认证、技能）、相册，不包含动态")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 404, message = "无用户信息")
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMineAll(@ApiParam(hidden = true) @AuthenticationPrincipal UserLogin authUser) {
        Long uid = authUser.getUid();
        User user = userRepository.findOne(uid);
        if (user == null)
            return ResponseEntity.status(404).body(R.code(404).message("无此用户信息 [UID = " + uid + "]").build());
        // 其余信息
        List<PhotoWallVO> userAlbums = userPhotoWallService.findAllByUid(uid);
        Map map = new HashMap();
        map.put("info", user);
        map.put("albums", userAlbums);
        return ResponseEntity.ok(map);
    }

    /**
     * 查看用户所有信息【非本人】
     *
     * @param uid
     * @return
     */
    @ApiOperation(value = "查看用户所有信息", notes = "查看该用户个人信息。信息包含：基本信息（认证、技能）、相册，不包含动态")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 404, message = "无用户信息")
    })
    @GetMapping("/{uid}/all")
    public ResponseEntity getMineAll(@PathVariable("uid") Long uid) {
        User user = userRepository.findOne(uid);
        if (user == null)
            return ResponseEntity.status(404).body(R.code(404).message("无此用户信息 [UID = " + uid + "]").build());

        // 其余信息
        List<PhotoWallVO> userAlbums = userPhotoWallService.findAllByUid(uid);
        Map map = new HashMap();
        map.put("info", user);
        map.put("albums", userAlbums);
        return ResponseEntity.ok(map);
    }

    /**
     * 查看用户信息【本人】
     *
     * @param authUser
     * @return
     */
    @ApiOperation(value = "查看用户信息【本人】", notes = "查看用户个人信息，用户需要处于已登陆状态")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 404, message = "无用户信息")
    })
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMine(@ApiParam(hidden = true) @AuthenticationPrincipal UserLogin authUser) {
        User user = userRepository.findOne(authUser.getUid());
        return user == null ? ResponseEntity.status(404).body(R.code(404).message("无此用户信息 [UID = " + authUser.getUid() + "]").build()) : ResponseEntity.ok(user);
    }

    /**
     * 查看用户信息【非本人】
     *
     * @param uid
     * @return
     */
    @ApiOperation(value = "查看用户信息", notes = "查看该用户个人信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 404, message = "无此用户信息")
    })
    @GetMapping("/{uid}")
    public ResponseEntity get(@PathVariable("uid") Long uid) {
        User user = userRepository.findOne(uid);
        return user == null ? ResponseEntity.status(404).body(R.code(404).message("无此用户信息 [UID = " + uid + "]").build()) : ResponseEntity.ok(user);
    }

    /**
     * 存储/更新用户信息【本人】
     *
     * @param authUser
     * @param user
     * @return
     */
    @ApiOperation(value = "存储/更新用户信息【本人】", notes = "存储/更新用户个人信息，用户需要处于已登陆状态")
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity saveOrUpdate(@ApiParam(hidden = true) @AuthenticationPrincipal UserLogin authUser,
                                       User user) {
        User sourceUser = userRepository.findOne(authUser.getUid());
        user.setPhone(null); // 电话号码不可修改
        user.setFansNum(null);
        user.setFollowNum(null);
        user.setUid(authUser.getUid());
        BeanUtils.copyProperties(user, sourceUser);
        User savedUser = userRepository.save(sourceUser);
        return ResponseEntity.ok(savedUser);
    }
}