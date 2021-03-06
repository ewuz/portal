package com.xcm.service;

import com.xcm.model.SysUser;
import com.xcm.model.vo.SysUserVo;
import com.xcm.page.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户业务
 * created by lq at 2018-04-11 17:46
 **/
public interface SysUserService extends BaseService<SysUser> {
    /**
     * 用户登陆
     *
     * @param userName 用户名
     * @param password 密码
     * @param system   系统标志
     * @return
     */
    SysUserVo login(String userName, String password, String system);

    /**
     * 退出
     *
     * @param system    业务系统标志
     * @param sysUserId 用户id
     */
    void logout(String system, Integer sysUserId);

    /**
     * 新增用户(绑定角色)
     *
     * @param sysUser 新增的用戶对象
     * @param roleIds 角色id(多个以英文的逗号隔开)
     * @return
     */
    void save(SysUser sysUser, String roleIds);

    /**
     * 更新用户
     *
     * @param sysUser 更新的用户对象
     * @param roleIds 角色id(多个以英文的逗号隔开)
     */
    void update(SysUser sysUser, String roleIds);

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return
     */
    SysUser getByUsername(String userName);

    /**
     * 用户列表分页
     *
     * @param paramMap 参数
     * @param pageNum  第几页
     * @param pageSize 每页几条
     * @return
     */
    PageInfo<SysUserVo> listPage(Map<String, Object> paramMap, Integer pageNum, Integer pageSize);

    /**
     * 根据用户id查询VO类
     *
     * @param userId 用户id
     * @return
     */
    SysUserVo getByIdVo(Integer userId);

    /**
     * 查询集合
     *
     * @param paramMap 参数map
     * @return
     */
    List<SysUserVo> list(Map<String, Object> paramMap);

    /**
     * 设置用户启用状态
     *
     * @param paramMap
     */
    void setEnbleOrDisable(Map<String, Object> paramMap);
}
