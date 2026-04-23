package com.stu.helloserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.helloserver.common.Result;
import com.stu.helloserver.common.ResultCode;
import com.stu.helloserver.dto.UserDTO;
import com.stu.helloserver.entity.User;
import com.stu.helloserver.mapper.UserMapper;
import com.stu.helloserver.service.UserService;
import com.stu.helloserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        // 1. 查询该用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }

        // 2. 组装实体对象
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // 生产环境需加密

        // 3. 插入数据库
        userMapper.insert(user);

        return Result.success("注册成功!");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        // 1. 根据用户名查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);

        // 2. 校验用户是否存在
        if (dbUser == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 3. 校验密码是否正确
        if (!dbUser.getPassword().equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }

        // 4. 生成 Token 并返回
        String token = JwtUtil.generateToken(dbUser.getUsername());
        return Result.success(token);
    }

    @Override
    public Result<String> getUserById(Long id) {
        // 根据id查询用户
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        // 返回用户信息（实际开发中建议使用DTO，这里简化处理）
        return Result.success("查询成功，用户名为：" + user.getUsername());
    }

    // ========== 新增：分页查询 ==========
    @Override
    public Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize) {
        // 1. 创建分页对象（参数1：当前页码，参数2：每页条数）
        Page<User> pageParam = new Page<>(pageNum, pageSize);

        // 2. 执行分页查询（第二个参数为查询条件，传null表示查询全部）
        Page<User> resultPage = userMapper.selectPage(pageParam, null);

        // 3. 返回结果
        return Result.success(resultPage);
    }
}