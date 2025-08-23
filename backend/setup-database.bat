@echo off
echo ========================================
echo 学习系统数据库建立脚本
echo ========================================
echo.

echo 请确保MySQL服务已启动，并且你有root用户权限
echo.

echo 正在连接MySQL并执行数据库建立脚本...
echo.

REM 执行SQL脚本
mysql -u root -p < setup-database.sql

echo.
echo ========================================
echo 数据库建立完成！
echo ========================================
echo.
echo 数据库名称: study_system
echo 用户名: cetc38
echo 密码: 1234
echo.
echo 测试账号:
echo - student001 / 123456
echo - student002 / 123456  
echo - student003 / 123456
echo.
echo 按任意键退出...
pause > nul
