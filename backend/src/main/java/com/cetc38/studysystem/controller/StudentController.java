package com.cetc38.studysystem.controller;

import com.cetc38.studysystem.dto.ApiResponse;
import com.cetc38.studysystem.dto.LoginRequest;
import com.cetc38.studysystem.dto.LoginResponse;
import com.cetc38.studysystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 学生控制器
 */
@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 学生登录
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = studentService.login(loginRequest);
            return ResponseEntity.ok(ApiResponse.success("登录成功", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取当前登录学生信息
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<LoginResponse.StudentInfo>> getProfile(@RequestHeader("Authorization") String token) {
        try {
            // 移除Bearer前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            // 这里应该从JWT中解析用户信息，暂时返回模拟数据
            // 实际项目中应该通过JWT过滤器来验证token
            return ResponseEntity.ok(ApiResponse.success("获取成功", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}

