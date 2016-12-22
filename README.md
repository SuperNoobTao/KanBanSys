# springboot

![主页面](http://obe2uu2sg.bkt.clouddn.com/R%5DI2F40%29IZ_79%25W382VHD%5DW.png)

![设备管理](http://obe2uu2sg.bkt.clouddn.com/%7DZI~JDSLD74JD%7B4F@BMZ2%60Y.png)

### 用户登录
url: http://localhost:8080/login
POST
payload: account=admin&pwd=admin

responese：
```markdown
{
code: "200"
operationStatus: "Successfully"
}
```

### 欢迎
url: http://localhost:8080/resources/v1/welcome
GET

responese：
```markdown
{
id: 2
content: "Hello, admin!"
}
```

### 访问用户列表(show all)
url: http://localhost:8080/resources/v1/users
GET

responese：
```markdown
[4]
-0:  {
id: 2
name: "admin"
account: "admin"
description: null
}
-1:  {
id: 3
name: "111"
account: "111"
description: null
}
-2:  {
id: 7
name: "2222"
account: "22222"
description: null
}
-3:  {
id: 8
name: "11111"
account: "11111"
description: null
}
```

### 访问用户(by id)
url: http://localhost:8080/resources/v1/users/9
GET

responese：
```markdown
{
id: 9
name: "测试新建后改名"
account: "ceshi"
description: null
}
```

### 访问用户(过滤条件)
url: http://localhost:8080/resources/v1/users?pageNo=1&pageSize=20&name=admin&sortBy=id:asc,name:desc
GET

responese：
```markdown
{
    "content": [
        {
            "id": 2,
            "name": "admin",
            "account": "admin",
            "description": null
        }
    ],
    "last": true,
    "totalElements": 1,
    "totalPages": 1,
    "number": 0,
    "size": 20,
    "sort": [
        {
            "direction": "ASC",
            "property": "id",
            "ignoreCase": false,
            "nullHandling": "NATIVE",
            "ascending": true
        },
        {
            "direction": "DESC",
            "property": "name",
            "ignoreCase": false,
            "nullHandling": "NATIVE",
            "ascending": false
        }
    ],
    "first": true,
    "numberOfElements": 1
}
```



### 更新用户
url: http://localhost:8080/resources/v1/users/3
PUT
payload: name=测试更新
responese：
```markdown
{
id: 3
name: "测试更新"
account: "111"
description: null
}
```

### 更新用户(添加角色)
url: http://localhost:8080/resources/v1/users/10
PUT
payload: roleIds=1,2
responese：
```markdown
{
id: 10
name: "create"
account: "create"
description: null
}
```

### 创建用户
url: http://localhost:8080/resources/v1/users
POST
payload: name=create&account=create&pwd=admin

responese：
```markdown
{
id: 10
name: "create"
account: "create"
description: null
}
```

### 删除用户
url: http://localhost:8080/resources/v1/users/9
DELETE

responese：
```markdown
204 No Content
```




































