package com.seeu.ywq.api.release.page;

import com.seeu.ywq.page.model.HomePageCategory;
import com.seeu.ywq.page.service.AppHomePageService;
import com.seeu.ywq.page_home.model.HomeUser;
import com.seeu.ywq.page_home.service.HomeUserService;
import com.seeu.ywq.userlogin.model.UserLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "APP页面数据尤物主页接口", description = "尤物")
@RestController
@RequestMapping("/api/v1/page")
public class HomePageYouwuApi {
    @Autowired
    private AppHomePageService appHomePageService;
    @Autowired
    private HomeUserService homeUserService;

    @ApiOperation(value = "尤物（固定长度数据）")
    @GetMapping("/youwu")
    public ResponseEntity youwu(@AuthenticationPrincipal UserLogin authUser) {
        List<HomePageCategory> categoryList = null;
        if (authUser == null)
            categoryList = appHomePageService.queryAllByPage(HomePageCategory.PAGE.youwu);
        else
            categoryList = appHomePageService.queryAllByPage(authUser.getUid(), HomePageCategory.PAGE.youwu);
        return ResponseEntity.ok(categoryList);
//        Map map = new HashMap();
//        map.put("news", appHomePageService.getYouWuPage_New());
//        map.put("suggestion", appHomePageService.getYouWuPage_Suggestion());
//        return ResponseEntity.ok(map);
    }

    @ApiOperation(value = "尤物上拉加载数据")
    @GetMapping("/youwu/more")
    public ResponseEntity listMore(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(homeUserService.queryAllByLABEL(HomeUser.LABEL.youwu, new PageRequest(page, size)));
    }
}
