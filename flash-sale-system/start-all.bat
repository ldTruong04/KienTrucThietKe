@echo off
echo ==========================================
echo    HE THONG FLASH SALE KHOI DONG
echo ==========================================
echo.

echo [1/4] Dang khoi dong Redis...
docker-compose up -d
echo.

echo [2/4] Dang cai dat cac thu vien (Vui long doi)...
cd shared
call npm install
cd ..\scripts
call npm install
cd ..\services\product-service
call npm install
cd ..\cart-service
call npm install
cd ..\order-service
call npm install
cd ..\inventory-service
call npm install
cd ..\..\frontend
call npm install
cd ..
echo.

echo [3/4] Dang nap du lieu mau vao Redis...
cd scripts
node seed.js
cd ..
echo.

echo [4/4] Dang mo cac cua so cho tung he thong...

start "Product Service (Port 8081)" cmd /k "color 0A && cd services\product-service && npm start"
start "Cart Service (Port 8082)" cmd /k "color 0B && cd services\cart-service && npm start"
start "Order Service (Port 8083)" cmd /k "color 0D && cd services\order-service && npm start"
start "Inventory Service (Port 8084)" cmd /k "color 0E && cd services\inventory-service && npm start"
start "Frontend React (Port 3000)" cmd /k "color 0C && cd frontend && npm run dev"

echo.
echo ==========================================
echo HOAN TAT!
echo 5 cua so den (Terminal) da duoc mo len.
echo Ban hay mo trinh duyet web (Chrome/Edge) va truy cap:
echo http://localhost:3000
echo ==========================================
pause
