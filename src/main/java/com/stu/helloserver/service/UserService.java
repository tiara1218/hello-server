package com.stu.helloserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.helloserver.common.Result;
import com.stu.helloserver.dto.UserDTO;
import com.stu.helloserver.entity.User;
import com.stu.helloserver.vo.UserDetailVO;

public interface UserService {
    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
    Result<String> getUserById(Long id);

    // 新增：分页查询用户
    Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize);

    // 新增：多表联查用户详细信息（带缓存）
    Result<UserDetailVO> getUserDetail(Long userId);

    // 新增：更新用户信息
    Result<String> updateUserInfo(Long userId, String realName, String phone, String address);

    // 新增：删除用户
    Result<String> deleteUser(Long userId);
}