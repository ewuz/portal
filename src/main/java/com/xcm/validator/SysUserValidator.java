package com.xcm.validator;

import com.xcm.config.MyConfig;
import com.xcm.constant.business.SysUserConstants;
import com.xcm.model.SysUser;
import com.xcm.util.IdcardUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 用户相关属性验证
 * created by lq at 2018-04-13 9:41
 **/
public class SysUserValidator {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{4,32}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,32}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 判断系统标志是否正确
     *
     * @param system
     * @return　校验通过返回true，否则返回false
     */
    public static boolean isSystem(String system) {
        if (StringUtils.isBlank(system)) {
            return false;
        }
        if (!MyConfig.systemSignSet.contains(system)) {
            return false;
        }
        return true;
    }

    /**
     * 检查用户表单数据
     *
     * @param sysUser
     * @return 成功返回空字符，失败返回对应的提示语句
     */
    public static String validateFormSysUser(SysUser sysUser) {
        if (null == sysUser) {
            return SysUserConstants.VALIDATE_NO_SYS_USER;
        }
        if (null == sysUser.getUserName()) {
            return SysUserConstants.LOGIN_NO_USER_NAME;
        }
        if (!isUsername(sysUser.getUserName())) {
            return SysUserConstants.VALIDATE_USERNAME_ERROR;
        }
        if (null == sysUser.getPassword() || !isPassword(sysUser.getPassword())) {
            return SysUserConstants.VALIDATE_PASSWORD_ERROR;
        }
        if (null == sysUser.getDepartmentId() || StringUtils.isBlank(sysUser.getDepartmentId())) {
            return SysUserConstants.VALIDATE_DEPARTMENT_ERROR;
        }
        if (StringUtils.isNotBlank(sysUser.getIdCard()) && !IdcardUtils.isIDCard(sysUser.getIdCard())) {
            return SysUserConstants.VALIDATE_ID_CARD_ERROR;
        }
        if (StringUtils.isNotBlank(sysUser.getEmail()) && !isEmail(sysUser.getEmail())) {
            return SysUserConstants.VALIDATE_EMAIL_ERROR;
        }
        if (StringUtils.isNotBlank(sysUser.getTelephone()) && !isMobile(sysUser.getTelephone())) {
            return SysUserConstants.VALIDATE_TELEPHONE_ERROR;
        }
        return "";
    }


    /**
     * 检查登录用户参数
     *
     * @param userName     用户名
     * @param password     密码
     * @param system       系统标志
     * @param verification 验证码
     * @return
     */
    public static String validateLoginSysUser(String userName, String password, String system, String verification) {
        if (StringUtils.isBlank(userName)) {
            return SysUserConstants.LOGIN_NO_USER_NAME;
        }
        if (StringUtils.isBlank(password)) {
            return SysUserConstants.LOGIN_NO_PASSWORD;
        }
        if (StringUtils.isBlank(system)) {
            return SysUserConstants.LOGIN_NO_SYSTEM;
        }
        if (!isUsername(userName)) {
            return SysUserConstants.LOGIN_USER_NAME_ERROR;
        }
        // TODO 解密后验证暂未实现

        if (!isPassword(password)) {
            return SysUserConstants.LOGIN_PASSWORD_ERROR;
        }
        if (!MyConfig.systemSignSet.contains(system)) {
            return SysUserConstants.LOGIN_SYSTEM_ERROR;
        }
        if (StringUtils.isNotBlank(verification)) {
            // TODO 验证码验证
        }
        return "";
    }
}
