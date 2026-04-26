# Base image
FROM python:3.9

# Thư mục làm việc
WORKDIR /app

# Copy file requirements trước
COPY requirements.txt .

# Cài thư viện
RUN pip install --no-cache-dir -r requirements.txt

# Copy toàn bộ code
COPY . .

# Mở port 5000
EXPOSE 5000

# Chạy ứng dụng
CMD ["python", "app.py"]