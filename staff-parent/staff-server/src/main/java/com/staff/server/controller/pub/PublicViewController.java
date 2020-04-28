package com.staff.server.controller.pub;

import com.staff.common.dto.GetStaffDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 统一公共页面访问接口
 */
@Controller
@RequestMapping("/public/view")
public class PublicViewController {
    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/loginView")
    public String loginView() {
        return "login";
    }
}
