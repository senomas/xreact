# DemoApp

## DOCKER MYSQL
```
docker run -d --name demo-db \
   -p 3309:3306 \
   -e MYSQL_ROOT_PASSWORD=dodol123 \
   -e MYSQL_DATABASE=demo \
   -e MYSQL_USER=demo \
   -e MYSQL_PASSWORD=dodol123 \
   mysql
```
