#!/usr/bin/env bash
set -euo pipefail

APP_DIR="/opt/tongzhou"
SQL_DIR="/root"

echo "[1/5] Checking files..."
test -f "${APP_DIR}/ruoyi-admin.jar"
test -d "${APP_DIR}/dist"
test -f "${SQL_DIR}/ry_20260417.sql"
test -f "${SQL_DIR}/tongzhou_qa.sql"
test -f /etc/nginx/sites-available/tongzhou.conf
test -f /etc/systemd/system/tongzhou.service

echo "[2/5] Importing database..."
mysql ry_vue < "${SQL_DIR}/ry_20260417.sql"
mysql ry_vue < "${SQL_DIR}/tongzhou_qa.sql"

echo "[3/5] Restarting nginx..."
nginx -t
systemctl restart nginx

echo "[4/5] Restarting app..."
systemctl daemon-reload
systemctl restart tongzhou || systemctl start tongzhou

echo "[5/5] Status..."
systemctl --no-pager --full status tongzhou | sed -n '1,20p'
echo
echo "App deployed. Open: http://47.83.4.136/portal"
