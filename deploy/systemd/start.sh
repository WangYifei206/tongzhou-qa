#!/usr/bin/env bash
set -e

cd /opt/tongzhou

exec /usr/bin/java -jar /opt/tongzhou/ruoyi-admin.jar \
  --spring.datasource.druid.master.url="jdbc:mysql://127.0.0.1:3306/your_database?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8" \
  --spring.datasource.druid.master.username=your_db_username \
  --spring.datasource.druid.master.password="your_db_password"
