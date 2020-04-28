package com.staff.server.controller.pub;

import com.staff.common.config.*;
import com.staff.common.dto.GetUserDetailDTO;
import com.staff.common.pojo.StaffTable;
import com.staff.common.request.LoginRequest;
import com.staff.common.response.BaseResponse;
import com.staff.common.response.GetWorkResponse;
import com.staff.server.mapper.StaffTableMapper;
import com.staff.server.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/public/system")
public class PublicServerController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${server.port}")
    private String port;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = "/login",consumes = "application/json;charset=UTF-8",
                    produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request,
                              HttpServletResponse response) throws InterruptedException {
        GetUserDetailDTO getUserDetailDTO = userMapper.selectByCount(loginRequest.getCount());
         if (null == getUserDetailDTO) {
            BusinessException.throwException(ErrorCode.Status.COUNT_PASS_ERROR);
         }
         String sessionId = CookieUtil.getCookieByName(request, "comfirmSession");
         if (sessionId == null) {
             BusinessException.throwException(ErrorCode.Status.NO_LOGIN);
         }
         String confirmCode = redisTemplate.opsForValue().get(sessionId);
         // 验证验证码
         if (!loginRequest.getConfirmCode().toUpperCase().equals(confirmCode)) {
             Cookie sCookie = new Cookie("comfirmSession", null);
             sCookie.setMaxAge(0);
             sCookie.setPath("/");
             response.addCookie(sCookie);
             BusinessException.throwException(ErrorCode.Status.CONFIRM_CODE_ERROR);
         }
         // 验证密码
         String password = getUserDetailDTO.getPassword();
         if (!password.equals(MD5Utils.MD5(loginRequest.getPassword()))) {
             Cookie sCookie = new Cookie("comfirmSession", null);
             sCookie.setMaxAge(0);
             sCookie.setPath("/");
             response.addCookie(sCookie);
             BusinessException.throwException(ErrorCode.Status.COUNT_PASS_ERROR);
         }
         // 登录成功,设置用户账号可以存在的缓存时间
         redisTemplate.opsForValue().set(loginRequest.getCount(), sessionId, 30, TimeUnit.MINUTES);
         Cookie sCookie = new Cookie("comfirmSession", null);
         sCookie.setMaxAge(0);
         sCookie.setPath("/");
         response.addCookie(sCookie);
         Cookie countCookie = new Cookie("count", loginRequest.getCount());
         countCookie.setMaxAge(-1);
         countCookie.setPath("/");
         Cookie sessionCookie = new Cookie("lyhsessionId", sessionId);
         sessionCookie.setMaxAge(-1);
         sessionCookie.setPath("/");
         response.addCookie(countCookie);
         response.addCookie(sessionCookie);
         return BaseResponse.DEFAULT;
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/getConfirmCode", method = RequestMethod.GET)
    public void getConfirmCode(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        Map<String, Object> map = CodeUtil.generateCodeAndPic();
        HttpSession session = request.getSession();
        InetAddress address = InetAddress.getLocalHost();
        String sessionId = session.getId() + address.getHostAddress() + ":" +port;
        // 将验证码存入缓存，并设置缓存可以保留的时间
        redisTemplate.opsForValue().set(sessionId, map.get("code").toString(),10, TimeUnit.MINUTES);
        Cookie cookie = new Cookie("comfirmSession", sessionId);
        response.addCookie(cookie);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        Cookie countCookie = new Cookie("count", null);
        countCookie.setMaxAge(0);
        countCookie.setPath("/");
        Cookie sessionCookie = new Cookie("lyhsessionId", null);
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/");
        response.addCookie(countCookie);
        response.addCookie(sessionCookie);
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", response.getOutputStream());
    }
}
