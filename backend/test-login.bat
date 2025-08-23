@echo off
echo 测试学生登录接口...
echo.

echo 1. 测试正确用户名和密码
curl -X POST http://localhost:8080/api/student/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"student001\",\"password\":\"123456\"}"
echo.
echo.

echo 2. 测试错误密码
curl -X POST http://localhost:8080/api/student/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"student001\",\"password\":\"wrongpassword\"}"
echo.
echo.

echo 3. 测试不存在的用户名
curl -X POST http://localhost:8080/api/student/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"nonexistent\",\"password\":\"123456\"}"
echo.
echo.

pause


