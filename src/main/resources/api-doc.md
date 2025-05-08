# 宠物之家后端接口文档 v1.0

## 1. 用户模块
### 1.1 用户注册
```
POST /api/user/register
Request:
{
    "phone": string,     // 手机号
    "password": string,  // 密码(加密后)
    "code": string      // 验证码
}
Response:
{
    "code": 0,          // 0成功,非0失败
    "message": string,  // 响应信息
    "data": {}
}
```

### 1.2 用户登录
```
POST /api/user/login
Request:
{
    "loginType": string,  // phone-手机号,password-密码
    "phone": string,      // 手机号
    "password": string,   // 密码(加密后,密码登录时必填)
    "code": string       // 验证码(手机号登录时必填)
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "token": string,
        "userId": number,
        "userName": string,
        "avatar": string
    }
}
```

### 1.3 发送验证码
```
POST /api/user/sendCode
Request:
{
    "phone": string,    // 手机号
    "type": string     // register-注册,login-登录
}
Response:
{
    "code": 0,
    "message": string
}
```

### 1.4 退出登录
```
POST /api/user/logout
Request:
{
    "token": string    // 当前登录的token
}
Response:
{
    "code": 0,
    "message": string
}
```

## 2. 宠物管理模块
### 2.1 获取我的宠物列表
```
GET /api/pets
Request:
{
    "userId": number    // 用户ID
}
Response:
{
    "code": 0,
    "message": string,
    "data": [{
        "id": number,            // 宠物ID
        "name": string,          // 宠物名称
        "breed": string,         // 品种("cat"-猫,"dog"-狗,"other"-其他)
        "age": number,           // 年龄
        "gender": string,        // 性别("male"-公,"female"-母)
        "status": string,        // 状态("home"-家养,"stray"-流浪,"adoption"-待领养,"adopted"-已领养)
        "image": string,         // 图片URL
        "isLost": boolean,       // 是否丢失
        "createTime": string,    // 创建时间
        "healthRecords": [{
            "id": number,
            "type": string,
            "date": string,
            "title": string,
            "description": string,
            "hospital": string,
            "cost": number
        }]
    }]
}
```

### 2.2 添加宠物
```
POST /api/pets
Request:
{
    "userId": number,    // 用户ID
    "name": string,      // 宠物名称
    "breed": string,     // 品种("cat"-猫,"dog"-狗,"other"-其他)
    "age": number,       // 年龄
    "gender": string,    // 性别("male"-公,"female"-母)
    "status": string,    // 状态("home"-家养,"stray"-流浪,"adoption"-待领养,"adopted"-已领养)
    "image": string      // 图片URL
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "id": number     // 新增宠物ID
    }
}
```

### 2.3 更新宠物信息
```
PUT /api/pets/{petId}
Request:
{
    "name": string,      // 宠物名称
    "breed": string,     // 品种("cat"-猫,"dog"-狗,"other"-其他)
    "age": number,       // 年龄
    "gender": string,    // 性别("male"-公,"female"-母)
    "status": string,    // 状态("home"-家养,"stray"-流浪,"adoption"-待领养,"adopted"-已领养)
    "image": string      // 图片URL
}
Response:
{
    "code": 0,
    "message": string
}
```

### 2.4 删除宠物
```
DELETE /api/pets/{petId}
Response:
{
    "code": 0,
    "message": string
}
```

## 3. 领养模块
### 3.1 获取领养信息列表
```
GET /api/adoptions
Request:
{
    "page": number,         // 页码，默认为1
    "pageSize": number,     // 每页数量，默认为10
    "breed": string,        // 可选，按品种筛选("cat"-猫,"dog"-狗,"other"-其他)
    "health": string,       // 可选，按健康状况筛选
    "province": string,     // 可选，按省份筛选
    "city": string,         // 可选，按城市筛选
    "userId": number,       // 可选，按发布者ID筛选
    "status": string        // 可选，按状态筛选("publish"-发布领养,"waiting"-待领养,"success"-领养成功,"fail"-领养失败)
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "total": number,    // 总记录数
        "list": [{
            "id": number,                // 领养信息ID
            "petId": number,             // 宠物ID
            "userId": number,            // 发布者ID
            "petInfo": {
                "name": string,          // 宠物名称
                "breed": string,         // 品种("cat"-猫,"dog"-狗,"other"-其他)
                "age": number,           // 年龄
                "gender": string,        // 性别("male"-公,"female"-母)
                "image": string          // 图片URL
            },
            "health": string,            // 健康状况
            "province": string,          // 省份
            "city": string,              // 城市
            "description": string,       // 详细描述
            "image": string,             // 图片URL (可能含多个URL，逗号分隔)
            "requirements": string,      // 领养要求
            "status": string,            // 状态("publish"-发布领养,"waiting"-待领养,"success"-领养成功,"fail"-领养失败)
            "createTime": string,        // 创建时间
            "updateTime": string         // 更新时间
        }]
    }
}
```

### 3.2 获取领养信息详情
```
GET /api/adoptions/{id}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "id": number,                // 领养信息ID
        "petId": number,             // 宠物ID
        "userId": number,            // 发布者ID
        "petInfo": {
            "name": string,          // 宠物名称
            "breed": string,         // 品种("cat"-猫,"dog"-狗,"other"-其他)
            "age": number,           // 年龄
            "gender": string,        // 性别("male"-公,"female"-母)
            "image": string          // 图片URL
        },
        "userInfo": {
            "username": string,
            "avatar": string
        },
        "health": string,            // 健康状况
        "province": string,          // 省份
        "city": string,              // 城市
        "description": string,       // 详细描述
        "image": string,             // 图片URL (可能含多个URL，逗号分隔)
        "requirements": string,      // 领养要求
        "status": string,            // 状态("publish"-发布领养,"waiting"-待领养,"success"-领养成功,"fail"-领养失败)
        "createTime": string,        // 创建时间
        "updateTime": string         // 更新时间
    }
}
```

### 3.3 发布领养信息
```
POST /api/adoptions
Request:
{
    "petId": number,         // 宠物ID（可选，若不存在则创建新宠物）
    "petInfo": {
        "name": string,      // 宠物名称
        "breed": string,     // 品种("cat"-猫,"dog"-狗,"other"-其他)
        "age": number,       // 年龄
        "gender": string,    // 性别("male"-公,"female"-母)
        "image": string      // 图片URL
    },
    "health": string,        // 健康状况
    "province": string,      // 省份
    "city": string,          // 城市
    "description": string,   // 详细描述
    "image": string,         // 图片URL (可能含多个URL，逗号分隔)
    "requirements": string   // 领养要求
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "id": number         // 领养信息ID
    }
}
```

### 3.4 申请领养
```
POST /api/adoptions/{adoptionId}/apply
Request:
{
    "phone": string,      // 联系电话
    "address": string,    // 联系地址
    "reason": string      // 申请理由
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "applicationId": number  // 申请ID
    }
}
```

### 3.5 更新领养信息
```
PUT /api/adoptions/{id}
Request:
{
    "health": string,        // 健康状况
    "province": string,      // 省份
    "city": string,          // 城市
    "description": string,   // 详细描述
    "image": string,         // 图片URL (可能含多个URL，逗号分隔)
    "requirements": string,  // 领养要求
    "status": string         // 状态("publish"-发布领养,"waiting"-待领养,"success"-领养成功,"fail"-领养失败)
}
Response:
{
    "code": 0,
    "message": string
}
```

### 3.6 删除领养信息
```
DELETE /api/adoptions/{id}
Response:
{
    "code": 0,
    "message": string
}
```

### 3.7 获取我发布的领养信息
```
GET /api/adoptions/my
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "total": number,
        "list": [{
            "id": number,                // 领养信息ID
            "petId": number,             // 宠物ID
            "petInfo": {
                "name": string,          // 宠物名称
                "breed": string,         // 品种("cat"-猫,"dog"-狗,"other"-其他)
                "age": number,           // 年龄
                "gender": string,        // 性别("male"-公,"female"-母)
                "image": string          // 图片URL
            },
            "health": string,            // 健康状况
            "province": string,          // 省份
            "city": string,              // 城市
            "description": string,       // 详细描述
            "image": string,             // 图片URL (可能含多个URL，逗号分隔)
            "requirements": string,      // 领养要求
            "status": string,            // 状态("publish"-发布领养,"waiting"-待领养,"success"-领养成功,"fail"-领养失败)
            "applications": number,      // 申请人数
            "createTime": string         // 创建时间
        }]
    }
}
```

## 4. 流浪动物模块
### 4.1 获取流浪动物列表
```
GET /api/pets/stray
Request:
{
    "page": number,         // 页码，默认为1
    "pageSize": number,     // 每页条数，默认为10
    "breed": string,        // 可选，品种("cat"-猫,"dog"-狗,"other"-其他)
    "age": number,          // 可选，年龄筛选
    "gender": string,       // 可选，性别("male"-公,"female"-母)
    "status": string,       // 可选，状态("stray"-流浪,"adoption"-待领养)
    "province": string,     // 可选，省份
    "city": string          // 可选，城市
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "total": number,    // 总记录数
        "list": [
            {
                "id": number,            // 宠物ID
                "name": string,          // 宠物名称
                "breed": string,         // 品种("cat"-猫,"dog"-狗,"other"-其他)
                "age": number,           // 年龄
                "gender": string,        // 性别("male"-公,"female"-母)
                "status": string,        // 状态("stray"-流浪,"adoption"-待领养)
                "image": string,         // 图片URL
                "province": string,      // 省份
                "city": string,          // 城市
                "description": string,   // 描述信息
                "isLost": boolean,       // 是否丢失
                "createTime": string     // 创建时间
            }
        ]
    }
}
```

### 4.2 获取流浪动物详情
```
GET /api/pets/stray/{id}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "id": number,            // 宠物ID
        "name": string,          // 宠物名称
        "breed": string,         // 品种("cat"-猫,"dog"-狗,"other"-其他)
        "age": number,           // 年龄
        "gender": string,        // 性别("male"-公,"female"-母)
        "status": string,        // 状态("stray"-流浪,"adoption"-待领养)
        "image": string,         // 图片URL
        "province": string,      // 省份
        "city": string,          // 城市
        "description": string,   // 描述信息
        "foundLocation": string, // 发现地点
        "foundDate": string,     // 发现日期
        "isLost": boolean,       // 是否丢失
        "createTime": string     // 创建时间
    }
}
```

### 4.3 为流浪动物发布领养信息
```
POST /api/pets/stray/{id}/adoption
Request:
{
    "health": string,        // 健康状况
    "province": string,      // 省份
    "city": string,          // 城市
    "description": string,   // 详细描述
    "image": string,         // 图片URL (可能含多个URL，逗号分隔)
    "requirements": string   // 领养要求
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "adoptionId": number // 领养信息ID
    }
}
```

### 4.4 申请领养流浪动物
```
POST /api/pets/stray/{id}/apply
Request:
{
    "phone": string,      // 联系电话
    "address": string,    // 联系地址
    "reason": string      // 申请理由
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "applicationId": number  // 申请ID
    }
}
```

## 5. 健康档案模块
### 5.1 添加健康记录
```
POST /api/pets/{petId}/health-records
Request:
{
    "type": string,      // 体检/疫苗/驱虫/治疗/手术
    "date": string,      // YYYY-MM-DD
    "title": string,
    "description": string,
    "hospital": string,
    "cost": number
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "id": number    // 新增记录ID
    }
}
```

### 5.2 获取健康记录列表
```
GET /api/pets/{petId}/health-records
Request:
{
    "type": string,     // 可选,记录类型筛选
    "year": number      // 可选,年份筛选
}
Response:
{
    "code": 0,
    "message": string,
    "data": [{
        "id": number,
        "type": string,
        "date": string,
        "title": string,
        "description": string,
        "hospital": string,
        "cost": number
    }]
}
```

## 6. 宠物挂失模块
### 6.1 发布挂失
```
POST /api/pets/{petId}/lost-report
Request:
{
    "lostTime": string,     // YYYY-MM-DD HH:mm
    "location": string,
    "description": string,
    "contact": string,
    "reward": number
}
Response:
{
    "code": 0,
    "message": string
}
```

### 6.2 取消挂失
```
DELETE /api/pets/{petId}/lost-report
Response:
{
    "code": 0,
    "message": string
}
```

## 7. 文件上传
### 7.1 上传图片
```
POST /api/upload/image
Request:
{
    "file": File,          // 图片文件
    "type": string        // pet-宠物图片
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "url": string     // 图片访问URL
    }
}
```

## 8. 消息通知模块
### 8.1 获取消息列表
```
GET /api/messages
Request:
{
    "userId": number,     // 用户ID
    "type": string,      // 可选,消息类型(system/adoption/comment)
    "status": string,    // 可选,消息状态(read/unread)
    "page": number,      // 页码
    "pageSize": number   // 每页数量
}
Response:
{
    "code": 0,
    "message": string,
    "data": {
        "total": number,    // 总消息数
        "list": [{
            "id": number,
            "type": string,    // system-系统通知,adoption-领养相关,comment-评论回复
            "title": string,
            "content": string,
            "time": string,    // YYYY-MM-DD HH:mm:ss
            "isRead": boolean,
            "icon": string     // 消息图标
        }]
    }
}
```

### 8.2 标记消息已读
```
PUT /api/messages/read
Request:
{
    "messageIds": number[]    // 消息ID数组
}
Response:
{
    "code": 0,
    "message": string
}
```

### 8.3 标记全部已读
```
PUT /api/messages/read-all
Request:
{
    "userId": number     // 用户ID
}
Response:
{
    "code": 0,
    "message": string
}
```

### 8.4 删除消息
```
DELETE /api/messages
Request:
{
    "messageIds": number[]    // 消息ID数组
}
Response:
{
    "code": 0,
    "message": string
}
```