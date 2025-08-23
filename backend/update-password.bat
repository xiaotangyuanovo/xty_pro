@echo off
echo ========================================
echo 更新密码为明文格式
echo ========================================
echo.

echo 请确保MySQL服务已启动，并且你有root用户权限
echo.

echo 正在连接MySQL并执行密码更新脚本...
echo.

REM 执行SQL脚本
mysql -u root -p < update-password-to-plaintext.sql

echo.
echo ========================================
echo 密码更新完成！
echo ========================================
echo.
echo 现在所有测试账号的密码都是明文 "123456"
echo 登录时直接比较明文密码，不进行加密
echo.
echo 测试账号:
echo - student001 / 123456
echo - student002 / 123456  
echo - student003 / 123456
echo.
echo 按任意键退出...
pause > nul
