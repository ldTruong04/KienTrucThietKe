## PLAN: Travel Booking System (Orchestration-Driven SOA)

### 1. Mục tiêu

Xây dựng hệ thống đặt tour theo kiến trúc Orchestration-Driven SOA.

Flow chính:
Frontend → Orchestrator → User Service → Tour Service → Booking Service → Payment Service → trả kết quả

Nguyên tắc:

* Service KHÔNG gọi nhau trực tiếp
* Chỉ Orchestrator điều phối
* Frontend chỉ gọi Orchestrator

---

### 2. Cấu hình hệ thống (LAN)

* Orchestrator: http://192.168.1.10:8080
* User Service: http://192.168.1.11:8081
* Tour Service: http://192.168.1.12:8082
* Booking Service: http://192.168.1.13:8083
* Payment Service: http://192.168.1.14:8084
* Frontend: http://192.168.1.15:3000

(Giai đoạn dev có thể dùng localhost, nhưng demo phải dùng IP LAN)

---

### 3. Orchestrator Service (Core)

API:

* POST /book-tour

Flow xử lý:

1. Gọi User Service → validate user
2. Gọi Tour Service → lấy thông tin tour
3. Gọi Booking Service → tạo booking
4. Gọi Payment Service → thanh toán
5. Trả kết quả về frontend

Yêu cầu:

* Dùng axios để gọi REST
* Log từng bước
* Handle lỗi rõ ràng (fail step nào trả step đó)

---

### 4. Frontend (ReactJS)

Chức năng:

* Login
* Xem danh sách tour
* Xem chi tiết tour
* Đặt tour

API:

* Chỉ gọi Orchestrator: http://192.168.1.10:8080

Yêu cầu:

* Dùng axios
* Có loading + error handling
* Khi đặt tour → gọi POST /book-tour

---

### 5. User Service

API:

* POST /login
* GET /users/:id

Yêu cầu:

* Dữ liệu giả (in-memory)
* Không gọi service khác

---

### 6. Tour Service

API:

* GET /tours
* GET /tours/:id

Yêu cầu:

* Mock data (ít nhất 3 tour)
* Không gọi service khác

---

### 7. Booking Service

API:

* POST /bookings

Yêu cầu:

* Tạo booking ID random
* Trả dữ liệu booking

---

### 8. Payment Service

API:

* POST /payments

Yêu cầu:

* Random success / fail (~30% fail)
* Trả:

  * success → { status: "paid" }
  * fail → { status: "failed" }

---

### 9. Ràng buộc kiến trúc (BẮT BUỘC)

* Frontend → chỉ gọi Orchestrator
* Orchestrator → gọi tất cả service
* Service → KHÔNG gọi nhau

---

### 10. Kiểm tra hệ thống

1. Chạy tất cả service
2. Chạy frontend
3. Thực hiện đặt tour

Kiểm tra:

* Frontend chỉ gọi port 8080
* Orchestrator log đầy đủ:
  → User → Tour → Booking → Payment
* Payment có thể fail (test error flow)

---

### 11. Gợi ý nâng cao (optional)

* Dùng .env cho config IP
* Thêm logging (morgan)
* Thêm retry khi payment fail
---
