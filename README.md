# 学习系统项目 (Study System)

这是一个前后端分离的学习系统项目，包含学习文档、学习视频、练习和考试功能。

## 项目结构

```
xty_pro/
├── frontend/          # Vue.js 前端项目
├── backend/           # Spring Boot 后端项目
└── docs/              # 项目文档
```

## 技术栈

### 前端 (Frontend)
- **框架**: Vue.js 2.6.11
- **路由**: Vue Router 3.2.0
- **状态管理**: Vuex 3.4.0
- **构建工具**: Vue CLI 4.5.19
- **测试**: Jest

### 后端 (Backend)
- **框架**: Spring Boot 2.7.18
- **安全**: Spring Security
- **数据访问**: Spring Data JPA
- **数据库**: MySQL 8.0
- **认证**: JWT (JSON Web Token)
- **构建工具**: Maven

## 快速开始

### 前端开发
```bash
cd frontend
npm install
npm run serve
```

### 后端开发
```bash
cd backend
mvn spring-boot:run
```

## 开发指南

### 前端开发 (你的朋友负责)
- 前端代码位于 `frontend/` 目录
- 使用 Vue.js 2.x 版本
- 支持热重载开发
- 包含完整的测试配置

### 后端开发 (你负责)
- 后端代码位于 `backend/` 目录
- 使用 Spring Boot 框架
- 包含完整的数据库配置
- 支持 JWT 认证

## 部署说明

### 开发环境
- 前端: `http://localhost:8080`
- 后端: `http://localhost:8081`

### 生产环境
- 前端构建: `npm run build`
- 后端打包: `mvn clean package`

## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情
