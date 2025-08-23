package com.cetc38.studysystem.service;

import com.cetc38.studysystem.dto.LoginRequest;
import com.cetc38.studysystem.dto.LoginResponse;
import com.cetc38.studysystem.entity.Student;
import com.cetc38.studysystem.repository.StudentRepository;
import com.cetc38.studysystem.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 学生服务类
 */
@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 学生登录
     */
    public LoginResponse login(LoginRequest loginRequest) {
        // 1. 验证用户名存在性
        Student student = studentRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名不存在"));

        // 2. 检查账户状态
        if (student.getStatus() == Student.AccountStatus.DISABLED) {
            throw new RuntimeException("账户已被禁用，请联系管理员");
        }

        if (student.getStatus() == Student.AccountStatus.LOCKED) {
            if (student.getLockedUntil() != null && student.getLockedUntil().isAfter(LocalDateTime.now())) {
                throw new RuntimeException("账户已被锁定，请稍后再试");
            } else {
                // 锁定时间已过，恢复账户状态
                student.setStatus(Student.AccountStatus.ACTIVE);
                student.setLoginAttempts(0);
                student.setLockedUntil(null);
            }
        }

        // 3. 验证密码（直接比较明文密码）
        if (!loginRequest.getPassword().equals(student.getPassword())) {
            handleLoginFailure(student);
            throw new RuntimeException("密码错误");
        }

        // 4. 登录成功，重置登录失败次数
        student.setLoginAttempts(0);
        student.setLastLoginTime(LocalDateTime.now());
        student.setLockedUntil(null);
        studentRepository.save(student);

        // 5. 生成JWT令牌
        String token = jwtUtil.generateToken(student.getUsername(), student.getId());

        // 6. 构建响应
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setExpiresIn(jwtUtil.getTokenRemainingTime(token));

        LoginResponse.StudentInfo studentInfo = new LoginResponse.StudentInfo();
        studentInfo.setId(student.getId());
        studentInfo.setUsername(student.getUsername());
        studentInfo.setRealName(student.getRealName());
        studentInfo.setEmail(student.getEmail());
        studentInfo.setStudentNumber(student.getStudentNumber());
        response.setStudent(studentInfo);

        return response;
    }

    /**
     * 处理登录失败
     */
    private void handleLoginFailure(Student student) {
        student.setLoginAttempts(student.getLoginAttempts() + 1);
        
        // 如果失败次数达到5次，锁定账户30分钟
        if (student.getLoginAttempts() >= 5) {
            student.setStatus(Student.AccountStatus.LOCKED);
            student.setLockedUntil(LocalDateTime.now().plusMinutes(30));
        }
        
        studentRepository.save(student);
    }

    /**
     * 根据用户名查找学生
     */
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("学生不存在"));
    }

    /**
     * 根据ID查找学生
     */
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("学生不存在"));
    }
}

