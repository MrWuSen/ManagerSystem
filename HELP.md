# Getting Started
### Reference example
```text
curl --location 'localhost:8081/admin/addUser' \
--header 'userInfo: eyJhY2NvdW50TmFtZSI6Iua1i+ivlSIsInJvbGUiOiJhZG1pbiIsInVzZXJJZCI6IjEyMzQ1NiJ9' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{"userId":123457,"endpoint":["resource A","resource B","resource C","resource D"]}'
```
```text
curl --location 'localhost:8081/user/resource A' \
--header 'userInfo: eyJhY2NvdW50TmFtZSI6Iua1i+ivlSIsInJvbGUiOiJhZG1pbiIsInVzZXJJZCI6IjEyMzQ1NiJ9' \
--header 'Content-Type: application/json;charset=UTF-8'
```
