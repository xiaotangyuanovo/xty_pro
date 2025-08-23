package com.cetc38.studysystem.repository;

import com.cetc38.studysystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 学生数据访问层接口
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * 根据用户名查找学生
     */
    Optional<Student> findByUsername(String username);

    /**
     * 根据学号查找学生
     */
    Optional<Student> findByStudentNumber(String studentNumber);

    /**
     * 根据邮箱查找学生
     */
    Optional<Student> findByEmail(String email);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查学号是否存在
     */
    boolean existsByStudentNumber(String studentNumber);

    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 根据用户名和状态查找学生
     */
    @Query("SELECT s FROM Student s WHERE s.username = :username AND s.status = :status")
    Optional<Student> findByUsernameAndStatus(@Param("username") String username, @Param("status") Student.AccountStatus status);
}

