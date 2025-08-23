-- 创建数据库
CREATE DATABASE IF NOT EXISTS study_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE study_system;

-- 创建学生表
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    student_number VARCHAR(20) UNIQUE COMMENT '学号',
    status ENUM('ACTIVE', 'LOCKED', 'DISABLED') NOT NULL DEFAULT 'ACTIVE' COMMENT '账户状态',
    login_attempts INT DEFAULT 0 COMMENT '登录失败次数',
    locked_until DATETIME COMMENT '锁定截止时间',
    last_login_time DATETIME COMMENT '最后登录时间',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    INDEX idx_username (username),
    INDEX idx_student_number (student_number),
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';

-- 插入测试数据（密码：123456，明文存储）
INSERT INTO students (username, password, real_name, email, student_number, phone, status) VALUES 
('student001', '123456', '张三', 'zhangsan@example.com', '2024001', '13800138000', 'ACTIVE'),
('student002', '123456', '李四', 'lisi@example.com', '2024002', '13800138001', 'ACTIVE'),
('student003', '123456', '王五', 'wangwu@example.com', '2024003', '13800138002', 'ACTIVE');

-- 查看表结构
DESCRIBE students;

-- 查看测试数据
SELECT id, username, real_name, email, student_number, status, created_time FROM students;


