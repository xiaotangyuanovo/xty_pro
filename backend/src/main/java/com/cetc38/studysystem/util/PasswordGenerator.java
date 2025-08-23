package com.cetc38.studysystem.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码生成工具类
 * 用于生成正确的BCrypt密码哈希值
 */
public class PasswordGenerator {
    
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public static void main(String[] args) {
        // 生成密码 "123456" 的BCrypt哈希值
        String password = "123456";
        String encodedPassword = encoder.encode(password);
        
        System.out.println("=== 密码生成结果 ===");
        System.out.println("原始密码: " + password);
        System.out.println("BCrypt哈希值: " + encodedPassword);
        
        // 验证密码
        boolean matches = encoder.matches(password, encodedPassword);
        System.out.println("验证结果: " + matches);
        
        System.out.println("\n=== 数据库插入语句 ===");
        System.out.println("-- 删除现有数据");
        System.out.println("DELETE FROM students WHERE username IN ('student001', 'student002', 'student003');");
        System.out.println();
        System.out.println("-- 插入新数据");
        System.out.println("INSERT INTO students (username, password, real_name, email, student_number, phone, status) VALUES ");
        System.out.println("('student001', '" + encoder.encode("123456") + "', '张三', 'zhangsan@example.com', '2024001', '13800138000', 'ACTIVE'),");
        System.out.println("('student002', '" + encoder.encode("123456") + "', '李四', 'lisi@example.com', '2024002', '13800138001', 'ACTIVE'),");
        System.out.println("('student003', '" + encoder.encode("123456") + "', '王五', 'wangwu@example.com', '2024003', '13800138002', 'ACTIVE');");
    }
}
