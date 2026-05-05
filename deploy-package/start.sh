#!/usr/bin/env bash
set -e

cd /opt/tongzhou

exec /usr/bin/java -jar /opt/tongzhou/ruoyi-admin.jar \
  --spring.datasource.druid.master.url="jdbc:mysql://127.0.0.1:3306/ry_vue?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8" \
  --spring.datasource.druid.master.username=ruoyi \
  --spring.datasource.druid.master.password="123456."
