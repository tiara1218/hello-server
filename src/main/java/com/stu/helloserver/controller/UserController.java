package com.stu.helloserver.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.helloserver.common.Result;
import com.stu.helloserver.dto.UserDTO;
import com.stu.helloserver.entity.User;
import com.stu.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. 新增用户（注册）
    @PostMapping
    public Result<String> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    // 2. 用户登录
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    // 3. 根据 id 查询用户
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    // ========== 4. 新增：分页查询用户列表 ==========
    @GetMapping("/page")
    public Result<Page<User>> getUserPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return userService.getUserPage(pageNum, pageSize);
    }
}