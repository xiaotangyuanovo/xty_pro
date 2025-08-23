-- 修复密码问题的SQL脚本
-- 请以root用户身份执行此脚本

USE study_system;

-- 删除现有的测试数据
DELETE FROM students WHERE username IN ('student001', 'student002', 'student003');

-- 插入新的测试数据，使用明文密码
INSERT INTO students (username, password, real_name, email, student_number, phone, status) VALUES 
('student001', '123456', '张三', 'zhangsan@example.com', '2024001', '13800138000', 'ACTIVE'),
('student002', '123456', '李四', 'lisi@example.com', '2024002', '13800138001', 'ACTIVE'),
('student003', '123456', '王五', 'wangwu@example.com', '2024003', '13800138002', 'ACTIVE');

-- 验证数据是否插入成功
SELECT id, username, real_name, email, student_number, status, created_time FROM students;

-- 说明：现在使用明文密码 "123456" 存储
-- 登录时直接比较明文密码，不进行加密
