package com.stu.helloserver.controller;

import com.stu.helloserver.common.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable Long id) {
        String data = "查询成功，正在返回 ID 为 " + id + " 的用户信息";
        return Result.success(data);
    }

    @PostMapping("/{id}")
    public Result<String> updateUser(@PathVariable Long id) {
        String data = "更新用户 ID 为 " + id;
        return Result.success(data);
    }
}