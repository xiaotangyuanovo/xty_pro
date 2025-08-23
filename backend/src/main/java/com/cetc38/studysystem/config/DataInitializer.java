package com.cetc38.studysystem.config;

import com.cetc38.studysystem.entity.Student;
import com.cetc38.studysystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 数据初始化类
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 检查是否已有测试数据
        if (studentRepository.count() == 0) {
            createTestStudent();
        }
    }

    /**
     * 创建测试学生账户
     */
    private void createTestStudent() {
        Student student = new Student();
        student.setUsername("student001");
        student.setPassword(passwordEncoder.encode("123456"));
        student.setRealName("张三");
        student.setEmail("zhangsan@example.com");
        student.setStudentNumber("2024001");
        student.setPhone("13800138000");
        student.setStatus(Student.AccountStatus.ACTIVE);
        
        studentRepository.save(student);
        System.out.println("测试学生账户创建成功: username=student001, password=123456");
    }
}

