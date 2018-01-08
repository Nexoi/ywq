package com.seeu.ywq.api.release.user;

import com.seeu.core.R;
import com.seeu.ywq.exception.ActionNotSupportException;
import com.seeu.ywq.pay.exception.BalanceNotEnoughException;
import com.seeu.ywq.pay.model.Balance;
import com.seeu.ywq.pay.model.OrderLog;
import com.seeu.ywq.pay.model.OrderRecharge;
import com.seeu.ywq.pay.service.BalanceService;
import com.seeu.ywq.pay.service.OrderService;
import com.seeu.ywq.userlogin.exception.NoSuchUserException;
import com.seeu.ywq.userlogin.model.UserLogin;
import com.seeu.ywq.utils.DateFormatterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户账户", description = "充值/提现/交易记录/账户信息")
@RestController
@RequestMapping("/api/v1/user")
public class UserBalanceApi {
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "查看交易记录", notes = "查看自己的余额系统收支情况")
    @GetMapping("/transactions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity listTransactions(@AuthenticationPrincipal UserLogin authUser,
                                           @RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(orderService.queryAll(authUser.getUid(), new PageRequest(page, size, new Sort(Sort.Direction.DESC, "createTime"))));
    }

    @ApiOperation(value = "查看账户各项额度记录", notes = "查看自己账户余额、相册收入、打赏收入、提现记录等")
    @GetMapping("/account")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMyBalanceDetail(@AuthenticationPrincipal UserLogin authUser) {
        Balance balance = null;
        try {
            balance = balanceService.queryDetail(authUser.getUid());
        } catch (NoSuchUserException e) {
            // 初始化账户
            balanceService.initAccount(authUser.getUid(), null);
            balance = new Balance();
            balance.setBindUid(null);
        }
        return ResponseEntity.ok(balance);
    }

    @ApiOperation(value = "查看余额", notes = "查看自己账户余额")
    @GetMapping("/balance")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMyBalance(@AuthenticationPrincipal UserLogin authUser) {
        Long diamonds = 0L;
        try {
            diamonds = balanceService.query(authUser.getUid());
        } catch (NoSuchUserException e) {
            // 初始化账户
            balanceService.initAccount(authUser.getUid(), null);
        }
        Map map = new HashMap();
        map.put("balance", diamonds);
        return ResponseEntity.ok(map);
    }


    @ApiOperation(value = "充值", notes = "给自己充值一定额度的钻石，服务器创建订单，客户端将订单信息发送到支付宝/微信进行支付，完成后服务器会自动校验支付情况。重新刷新余额即可查看结果")
    @PostMapping("/balance/recharge")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity recharge(@AuthenticationPrincipal UserLogin authUser,
                                   Long diamonds) {
        try {
            balanceService.update(dateFormatterService.getyyyyMMddHHmmssS().format(new Date()), authUser.getUid(), OrderLog.EVENT.RECHARGE, diamonds);
            return ResponseEntity.ok(R.code(200).message("充值成功！"));
        } catch (BalanceNotEnoughException e) {
            return null; // 不可能发生的事情
        } catch (ActionNotSupportException e) {
            return ResponseEntity.badRequest().body(R.code(4001).message("充值额度不能为负数！"));
        }
    }

    @ApiOperation(value = "提现", notes = "提现操作会被视作申请提现操作。管理员后台同意之后会打款至对应的账号")
    @PostMapping("/balance/withdraw")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity withdraw(@AuthenticationPrincipal UserLogin authUser,
                                   OrderRecharge.PAY_METHOD payMethod,
                                   @ApiParam(value = "用户支付宝/微信账户ID")
                                           String payId,
                                   @ApiParam(value = "用户支付宝/微信名字")
                                           String accountName,
                                   Long diamonds) {
        try {
            balanceService.update(dateFormatterService.getyyyyMMddHHmmssS().format(new Date()), authUser.getUid(), OrderLog.EVENT.WITHDRAW, diamonds);
            return ResponseEntity.ok(R.code(200).message("提现成功！"));
        } catch (BalanceNotEnoughException e) {
            return ResponseEntity.badRequest().body(R.code(4000).message("余额不足！"));
        } catch (ActionNotSupportException e) {
            return ResponseEntity.badRequest().body(R.code(4001).message("提现额度不能为负数！"));
        }
    }

    @Autowired
    DateFormatterService dateFormatterService;
}
