package com.staff.server.controller.pri;

import com.staff.common.config.CookieUtil;
import com.staff.common.pojo.User;
import com.staff.common.pojo.WorkStaffTable;
import com.staff.server.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/private/view")
public class PrivateViewController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户对应的主页
     * @return
     */
    @RequestMapping("/mainView")
    public String mainView(HttpServletRequest request) {
        String scount = CookieUtil.getCookieByName(request,"count");
        User user = userMapper.selectByCount(scount);
        if (user.getType().equals("管理员")) {
            return "index";
        } else if (user.getType().equals("学生")) {
            return "indexStudent";
        } else {
            return "indexTeacher";
        }
    }

}
