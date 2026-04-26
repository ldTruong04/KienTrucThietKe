# ===== Stage 1: Build =====
FROM node:18 AS builder

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

# (Nếu có build step như React/TypeScript thì chạy ở đây)
# RUN npm run build


# ===== Stage 2: Production =====
FROM node:18-alpine

WORKDIR /app

# Copy node_modules + source từ stage 1
COPY --from=builder /app /app

EXPOSE 3000

CMD ["npm", "start"]