# CETC38 学习系统

这是一个学习文档、学习视频、练习、考试的平台，分为学生端和管理端。

## 项目结构

```
src/main/java/com/cetc38/studysystem/
├── config/           # 配置类
├── controller/       # 控制器
├── dto/             # 数据传输对象
├── entity/          # 实体类
├── repository/      # 数据访问层
├── service/         # 业务逻辑层
└── util/            # 工具类
```

## 功能特性

### 学生端登录功能

#### 1. 后端验证
- ✅ 用户名存在性验证
- ✅ 明文密码验证（不加密）
- ✅ 密码匹配验证（明文比较）
- ✅ 账户状态检查（正常、锁定、禁用）

#### 2. 安全特性
- ✅ JWT Token认证
- ✅ 密码加密存储
- ✅ 登录失败次数限制（5次失败后锁定30分钟）
- ✅ 账户状态管理

#### 3. 登录流程
- ✅ 登录成功：生成JWT Token，返回学生信息
- ✅ 登录失败：错误信息提示，记录失败次数

## 技术栈

- **后端框架**: Spring Boot 2.7.18
- **安全框架**: Spring Security
- **数据访问**: Spring Data JPA
- **数据库**: MySQL 8.0
- **认证方式**: JWT (JSON Web Token)
- **密码加密**: BCrypt
- **构建工具**: Maven

## 快速开始

### 1. 环境要求
- JDK 8+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库配置
创建数据库：
```sql
CREATE DATABASE study_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

修改 `src/main/resources/application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/study_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 3. 运行项目
```bash
mvn spring-boot:run
```

### 4. 测试登录
项目启动后会自动创建测试账户：
- 用户名: `student001`
- 密码: `123456`

登录接口：`POST /api/student/login`

## API接口

### 学生登录
```
POST /api/student/login
Content-Type: application/json

{
  "username": "student001",
  "password": "123456"
}
```

### 获取学生信息
```
GET /api/student/profile
Authorization: Bearer {token}
```

## 数据库表结构

### students表
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | BIGINT | 主键ID |
| username | VARCHAR(20) | 用户名（唯一） |
| password | VARCHAR(100) | 加密后的密码 |
| real_name | VARCHAR(50) | 真实姓名 |
| email | VARCHAR(100) | 邮箱 |
| phone | VARCHAR(20) | 手机号 |
| student_number | VARCHAR(20) | 学号（唯一） |
| status | ENUM | 账户状态（ACTIVE/LOCKED/DISABLED） |
| login_attempts | INT | 登录失败次数 |
| locked_until | DATETIME | 锁定截止时间 |
| last_login_time | DATETIME | 最后登录时间 |
| created_time | DATETIME | 创建时间 |
| updated_time | DATETIME | 更新时间 |

## 安全机制

1. **密码安全**: 使用BCrypt算法加密存储
2. **账户锁定**: 连续5次登录失败后锁定30分钟
3. **JWT认证**: 无状态认证，支持跨域
4. **输入验证**: 使用Bean Validation进行参数校验

## 下一步计划

- [ ] 学生注册功能
- [ ] 密码重置功能
- [ ] 管理员端功能
- [ ] 学习资源管理
- [ ] 在线考试系统
- [ ] 学习进度跟踪

## 贡献指南

欢迎提交Issue和Pull Request来改进这个项目。

## 许可证

MIT License


