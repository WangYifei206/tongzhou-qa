package com.ruoyi.framework.web.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.constant.TongzhouConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.domain.tongzhou.TzUserProfile;
import com.ruoyi.system.domain.tongzhou.dto.PortalRegisterBody;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.mapper.tongzhou.TzCommunityMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

@Component
public class PortalAuthService
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private TzCommunityMapper communityMapper;

    public String login(String username, String password, String code, String uuid)
    {
        return loginService.loginPortal(username, password, code, uuid);
    }

    @Transactional
    public void register(PortalRegisterBody body)
    {
        String username = body.getUsername();
        String password = body.getPassword();
        if (configService.selectCaptchaEnabled())
        {
            registerService.validateCaptcha(username, body.getCode(), body.getUuid());
        }
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            throw new ServiceException("用户名和密码不能为空");
        }
        SysUser uniqueCheck = new SysUser();
        uniqueCheck.setUserName(username);
        if (!userService.checkUserNameUnique(uniqueCheck))
        {
            throw new ServiceException("用户名已存在");
        }
        if (StringUtils.isNotEmpty(body.getEmail()))
        {
            SysUser emailCheck = new SysUser();
            emailCheck.setEmail(body.getEmail());
            if (!userService.checkEmailUnique(emailCheck))
            {
                throw new ServiceException("邮箱已存在");
            }
        }
        if (StringUtils.isNotEmpty(body.getPhonenumber()))
        {
            SysUser phoneCheck = new SysUser();
            phoneCheck.setPhonenumber(body.getPhonenumber());
            if (!userService.checkPhoneUnique(phoneCheck))
            {
                throw new ServiceException("手机号已存在");
            }
        }
        SysUser user = new SysUser();
        user.setUserName(username);
        user.setNickName(StringUtils.isNotEmpty(body.getNickName()) ? body.getNickName() : username);
        user.setUserType(UserConstants.USER_TYPE_PORTAL);
        user.setEmail(body.getEmail());
        user.setPhonenumber(body.getPhonenumber());
        user.setStatus(UserConstants.NORMAL);
        user.setPwdUpdateDate(DateUtils.getNowDate());
        user.setPassword(SecurityUtils.encryptPassword(password));
        user.setCreateBy("portal-register");
        if (!userService.registerUser(user))
        {
            throw new ServiceException("注册失败，请稍后重试");
        }
        List<SysUserRole> userRoles = new ArrayList<>();
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(TongzhouConstants.PORTAL_ROLE_ID);
        userRoles.add(userRole);
        userRoleMapper.batchUserRole(userRoles);

        TzUserProfile profile = new TzUserProfile();
        profile.setUserId(user.getUserId());
        profile.setCollege(body.getCollege());
        profile.setGrade(body.getGrade());
        profile.setMajor(body.getMajor());
        profile.setIntroduction(body.getIntroduction());
        profile.setStatus(UserConstants.NORMAL);
        profile.setCreateBy("portal-register");
        communityMapper.insertUserProfile(profile);
    }
}
