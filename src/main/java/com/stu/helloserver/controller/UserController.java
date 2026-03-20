package com.stu.helloserver.controller;  // 修改这里：使用你项目的包名

import com.stu.helloserver.entity.User;  // 修改这里：使用你项目的包名
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 1. 获取用户信息（查）
    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id) {  // 去掉 "id" 字符串
        return "查询成功，正在返回 ID 为 " + id + " 的用户信息";
    }

    // 2. 新增用户（增）
    @PostMapping
    public String createUser(@RequestBody User user) {
        return "新增成功，接收到用户：" + user.getName() + "，年龄：" + user.getAge();
    }

    // 3. 全量更新用户信息（改）
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user) {  // 去掉 "id" 字符串
        return "更新成功，ID " + id + " 的用户已修改为：" + user.getName();
    }

    // 4. 删除用户（删）
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {  // 去掉 "id" 字符串
        return "删除成功，已移除 ID 为 " + id + " 的用户";
    }
}