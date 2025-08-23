public class TestJwtKey {
    public static void main(String[] args) {
        // 测试旧的密钥
        String oldSecret = "cetc38StudySystemSecretKey2024";
        System.out.println("=== 旧密钥信息 ===");
        System.out.println("密钥: " + oldSecret);
        System.out.println("字符长度: " + oldSecret.length());
        System.out.println("字节长度: " + oldSecret.getBytes().length + " 字节");
        System.out.println("位数: " + (oldSecret.getBytes().length * 8) + " 位");
        System.out.println("是否满足JWT要求(>=256位): " + (oldSecret.getBytes().length * 8 >= 256));
        
        System.out.println();
        
        // 测试新的密钥
        String newSecret = "cetc38StudySystemSecretKey2024ForJWTTokenGenerationAndValidationWithSecureLength";
        System.out.println("=== 新密钥信息 ===");
        System.out.println("密钥: " + newSecret);
        System.out.println("字符长度: " + newSecret.length());
        System.out.println("字节长度: " + newSecret.getBytes().length + " 字节");
        System.out.println("位数: " + (newSecret.getBytes().length * 8) + " 位");
        System.out.println("是否满足JWT要求(>=256位): " + (newSecret.getBytes().length * 8 >= 256));
        
        System.out.println();
        
        // 推荐的最小密钥长度
        System.out.println("=== JWT密钥要求 ===");
        System.out.println("HMAC-SHA算法要求: >= 256位 (32字节)");
        System.out.println("推荐密钥长度: 64字符 (512位)");
        System.out.println("当前新密钥状态: " + (newSecret.getBytes().length * 8 >= 256 ? "✅ 安全" : "❌ 不安全"));
    }
}
