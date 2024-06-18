@echo off
start java -jar target\Basket_Players-0.0.1-SNAPSHOT.jar
timeout /t 10
start http://localhost:8080
