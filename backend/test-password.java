import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // 测试密码
        String rawPassword = "123456";
        
        // 生成新的加密密码
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("原始密码: " + rawPassword);
        System.out.println("加密后密码: " + encodedPassword);
        
        // 验证密码匹配
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("密码匹配结果: " + matches);
        
        // 测试数据库中的密码
        String dbPassword = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa";
        boolean dbMatches = encoder.matches(rawPassword, dbPassword);
        System.out.println("数据库密码匹配结果: " + dbMatches);
        
        // 生成新的测试密码用于数据库
        System.out.println("\n=== 新的加密密码（用于更新数据库）===");
        System.out.println("INSERT INTO students (username, password, real_name, email, student_number, phone, status) VALUES ");
        System.out.println("('student001', '" + encoder.encode("123456") + "', '张三', 'zhangsan@example.com', '2024001', '13800138000', 'ACTIVE'),");
        System.out.println("('student002', '" + encoder.encode("123456") + "', '李四', 'lisi@example.com', '2024002', '13800138001', 'ACTIVE'),");
        System.out.println("('student003', '" + encoder.encode("123456") + "', '王五', 'wangwu@example.com', '2024003', '13800138002', 'ACTIVE');");
    }
}
