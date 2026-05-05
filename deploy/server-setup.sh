#!/usr/bin/env bash
set -euo pipefail

APP_DIR="/opt/tongzhou"
UPLOAD_DIR="/home/ruoyi/uploadPath"
DB_NAME="ry_vue"
DB_USER="ruoyi"
DB_PASSWORD="123456."

echo "[1/7] Installing packages..."
apt update
apt install -y openjdk-17-jre-headless nginx redis-server mysql-server unzip

echo "[2/7] Enabling services..."
systemctl enable nginx
systemctl enable redis-server
systemctl enable mysql
systemctl start nginx
systemctl start redis-server
systemctl start mysql

echo "[3/7] Creating directories..."
mkdir -p "${APP_DIR}"
mkdir -p "${UPLOAD_DIR}"

echo "[4/7] Configuring MySQL..."
mysql <<SQL
CREATE DATABASE IF NOT EXISTS ${DB_NAME} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';
ALTER USER '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';
GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO '${DB_USER}'@'localhost';
CREATE USER IF NOT EXISTS '${DB_USER}'@'%' IDENTIFIED BY '${DB_PASSWORD}';
ALTER USER '${DB_USER}'@'%' IDENTIFIED BY '${DB_PASSWORD}';
GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO '${DB_USER}'@'%';
FLUSH PRIVILEGES;
SQL

echo "[5/7] Opening firewall if ufw exists..."
if command -v ufw >/dev/null 2>&1; then
  ufw allow 22 || true
  ufw allow 80 || true
  ufw allow 443 || true
fi

echo "[6/7] Enabling nginx site..."
if [ -f /etc/nginx/sites-enabled/default ]; then
  rm -f /etc/nginx/sites-enabled/default
fi
ln -sf /etc/nginx/sites-available/tongzhou.conf /etc/nginx/sites-enabled/tongzhou.conf
nginx -t
systemctl restart nginx

echo "[7/7] Enabling app service..."
chmod +x "${APP_DIR}/start.sh"
systemctl daemon-reload
systemctl enable tongzhou

echo "Server setup completed."
