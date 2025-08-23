# CETC38 学习系统 API 接口文档

## 基础信息
- **基础URL**: `http://localhost:8080`
- **API版本**: v1.0
- **认证方式**: JWT Token
- **数据格式**: JSON

## 通用响应格式

### 成功响应
```json
{
  "success": true,
  "message": "操作成功",
  "data": {},
  "timestamp": 1755927589360
}
```

### 错误响应
```json
{
  "success": false,
  "message": "错误信息",
  "data": null,
  "timestamp": 1755927589360
}
```

## 接口列表

### 1. 学生登录
- **接口**: `POST /api/student/login`
- **参数**: `{"username": "student001", "password": "123456"}`
- **返回**: JWT令牌 + 学生信息

### 2. 获取学生信息
- **接口**: `GET /api/student/profile`
- **认证**: Bearer Token
- **返回**: 学生详细信息

## 测试账号
- `student001` / `123456`
- `student002` / `123456`
- `student003` / `123456`

## 安全机制
- 明文密码存储
- 5次失败锁定30分钟
- JWT认证（24小时有效期）
- 账户状态管理
