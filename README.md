2020-hackweek backend



###### 返回字段

> | 返回字段 | 字段类型 | 说明                             |
> | :------- | :------- | :------------------------------- |
> | status   | int      | 返回结果状态。0：正常；1：错误。 |
> | message  | string   | 结果                             |
> | data     | object   | 得到的数据                       |





### 1\. 获取注册的邮箱验证码

#### 接口功能
> 获取注册的邮箱验证码

#### URL
> /account/code

#### HTTP请求方式
> GET

#### 请求参数
> | 参数  | 必选 | 类型   | 说明       |
> | :---- | :--- | :----- | ---------- |
> | email | ture | string | 用户的邮箱 |

#### 接口示例
> 地址：/account/code?email=111111@111.com 
##### 请求
``` json

```
##### 响应
``` json
{
    "message": "发送成功",
    "data": null,
    "status": 1
}
```



### 2\. 注册

#### 接口功能
> 注册

#### URL
> /account/user

#### HTTP请求方式
> POST

#### 请求体
> | 参数     | 必选 | 类型         | 说明                  |
> | :------- | :--- | :----------- | --------------------- |
> | email    | true | string       | 用户的邮箱            |
> | username | true | string(3-20) | 用户名                |
> | password | true | string(6-20) | 用户的密码            |
> | code     | true | string(6)    | 邮箱验证码,十分钟有效 |

#### 接口示例
> 地址：/account/user
##### 请求
``` json
{
    "username":"sww",
    "email":"13979062948@163.com",
    "password":"111111",
    "code":"KMGIPF"
}
```
##### 响应
``` json
{
    "message": "注册成功",
    "data": null,
    "status": 1
}
```



### 3\. 登录

#### 接口功能

> 登录,获取token

#### URL

> /account/token

#### HTTP请求方式

> POST

#### 请求体

> | 参数     | 必选 | 类型         | 说明       |
> | :------- | :--- | :----------- | ---------- |
> | username | true | string(3-20) | 用户名     |
> | password | true | string(6-20) | 用户的密码 |

#### 接口示例

> 地址：/account/token

##### 请求

``` json
{
    "username":"sww",
    "password":"111111"
}
```

##### 响应

``` json
{
    "message": "登录成功",
    "data": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODY0OTM2OTEsImp0aSI6IjEyNDg0MzgxODQ3OTExNzkyNjUiLCJzdWIiOiJ1c2VyIiwiaWF0IjoxNTg2NDg2NDkxfQ.CDdkfWzaJeBMtmCjCZD9z1MBaNpb0HcN-XqAMcvXgpNXiKhf2YMDWR1uY81LHntn9AiNzeY6hEyJGyNGZkb4vw",
    "status": 1
}
```



### 4\. 获取忘记密码的邮箱验证码

#### 接口功能

> 忘记密码,向用户的注册邮箱发送邮箱验证码

#### URL

> /account/password/code

#### HTTP请求方式

> GET

#### 请求参数

> | 参数     | 必选 | 类型   | 说明   |
> | :------- | :--- | :----- | ------ |
> | username | true | string | 用户名 |

#### 接口示例

> 地址：/account/password/code?username=sww

##### 请求

``` json

```

##### 响应

``` json
{
    "message": "已向13****@163.com发送邮件",
    "data": null,
    "status": 1
}
```



### 5\. 通过邮箱验证码修改密码

#### 接口功能

> 通过邮箱验证码修改密码

#### URL

> /account/password

#### HTTP请求方式

> PUT

#### 请求体

> | 参数     | 必选 | 类型         | 说明       |
> | :------- | :--- | :----------- | ---------- |
> | username | true | string(3-20) | 用户名     |
> | password | true | string(6-20) | 密码       |
> | code     | true | string(6)    | 邮箱验证码 |

#### 接口示例

> 地址：/account/password

##### 请求

``` json
{
    "username":"sww",
    "password":"123456",
    "code":"0L55U7"
}
```

##### 响应

``` json
{
    "message": "修改成功",
    "data": null,
    "status": 1
}
```

### 6\. 获取用户粉丝列表

#### 接口功能

> 获取用户的粉丝列表

#### URL

> /self/follower/{userId}

#### HTTP请求方式

> GET

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> 

#### 接口示例

> 地址：/page/follower/1248438184791179265

##### 请求

``` json

```

##### 响应

``` json
{
    "message": "没有被任何人关注",
    "data": null,
    "status": 1
}
```

### 7\. 获取用户关注的人列表

#### 接口功能

> 获取用户的关注的人列表

#### URL

> /self/follows/{userId}

#### HTTP请求方式

> GET

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> 

#### 接口示例

> 地址：/page/follows/1248438184791179265

##### 请求

``` json

```

##### 响应

``` json
{
    "message": "没有关注任何人",
    "data": null,
    "status": 1
}
```

### 8\. 获取用户个人界面的信息

#### 接口功能

> 获取用户的个人界面的信息

#### URL

> /self/main

#### HTTP请求方式

> GET

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> 

#### 接口示例

> 地址：/self/main

##### 请求

``` json

```

##### 响应

``` json
{
    "message": "获取成功",
    "data": {
        "username": "sww",
        "userId": 1248438184791179265,
        "avatar": null,
        "followNum": 0,
        "followedNum": 0,
        "likedNum": 0,
        "introduction": null,
        "birth": null
    },
    "status": 1
}
```

### 9\. 修改用户个人资料

#### 接口功能

> 修改用户个人资料

#### URL

> /self/main/

#### HTTP请求方式

> PUT

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> | 参数         | 必选  | 类型          | 说明                            |
>| :----------- | :---- | :------------ | ------------------------------- |
>  | introduction | false | string(0-100) | 介绍                            |
>  | birth        | false | date          | 生日 格式为 yyyy-MM-dd HH:mm:ss |

#### 接口示例

> 地址：/self/main

##### 请求

``` json
{
    "introduction":"1jof1je13fj1f1f",
    "birth":"2010-10-10 10:10:10"
}
```

##### 响应

``` json
{
    "message": "更新成功",
    "data": null,
    "status": 1
}
```

### 10\. 修改用户个人资料

#### 接口功能

> 修改用户个人资料

#### URL

> /self/main/

#### HTTP请求方式

> PUT

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> | 参数         | 必选  | 类型          | 说明                            |
> | :----------- | :---- | :------------ | ------------------------------- |
> | introduction | false | string(0-100) | 介绍                            |
> | birth        | false | date          | 生日 格式为 yyyy-MM-dd HH:mm:ss |

#### 接口示例

> 地址：/self/main

##### 请求

``` json
{
    "introduction":"1jof1je13fj1f1f",
    "birth":"2010-10-10 10:10:10"
}
```

##### 响应

``` json
{
    "message": "更新成功",
    "data": null,
    "status": 1
}
```

### 11\. 获取某个用户的个人主页信息

#### 接口功能

> 获取某个用户的个人主页信息

#### URL

> /self/{userId}?page=

#### HTTP请求方式

> GET

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求参数

> | 参数 | 类型 | 说明                         |
> | ---- | ---- | ---------------------------- |
> | page | int  | 显示的用户发过的文章的第一页 |

#### 接口示例

> 地址：/self/1248438184791179265?page=1

##### 请求

``` json

```

##### 响应

 当page为1时会返回个人信息(userInfo)和是否已关注(isFollowed)

``` json
{
    "message": "获取成功",
    "data": {
        "userInfo": {
            "username": "sww",
            "userId": "1248438184791179265",
            "avatar": null,
            "followNum": null,
            "followedNum": null,
            "likedNum": null,
            "introduction": "1jof1je13fj1f1f",
            "birth": "2010-10-10 00:00:00"
        },
        "articles": [
            {
                "id": "1248499219426439170",
                "title": "swank1",
                "articleType": 0,
                "authorId": "1248438184791179265",
                "content": "swank content",
                "gmtCreate": "2020-04-10 06:33:05"
            },
            {
                "id": "1248499299978047489",
                "title": "story1",
                "articleType": 1,
                "authorId": "1248438184791179265",
                "content": "story content",
                "gmtCreate": "2020-04-10 06:33:24"
            }
        ],
        "isFollowed": false
    },
    "status": 1
}
```

当page大于1时,仅返回第二页文章列表

``` json
{
    "message": "获取成功",
    "data": {
        "articles": []
    },
    "status": 1
}
```

### 12\. 上传个人头像

#### 接口功能

> 上传个人头像

#### URL

> /self/avatar

#### HTTP请求方式

> POST

#### 请求头

| 参数          | 类型                | 说明                |
| ------------- | ------------------- | ------------------- |
| Content-Type  | multipart/form-data |                     |
| Authorization | token               | 在登录时获得的token |

#### 请求体

> | 参数   | 必选  | 类型          | 说明                 |
> | :----- | :---- | :------------ | -------------------- |
> | avatar | false | MultipartFile | 用户头像图片(1m以内) |

#### 接口示例

> 地址：/self/avatar

##### 请求

``` json

```

##### 响应

``` json
{
    "message": "上传成功",
    "data": "http://q89jpbw7d.bkt.clouddn.com/FsVIUUhwwO86xy4JfjBP-5suhKvP",
    "status": 1
}
```

### 13\.发布story

#### 接口功能

> 发布story

#### URL

> /article/story

#### HTTP请求方式

> POST

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> | 参数    | 必选 | 类型   | 说明     |
> | :------ | :--- | :----- | -------- |
> | title   | true | string | 文章标题 |
> | content | true | string | 文章内容 |

#### 接口示例

> 地址：/article/story

##### 请求

``` json
{
    "title":"story111",
    "content":"content1f13f1fw"
}
```

##### 响应

```json
{
    "message": "发布成功",
    "data": null,
    "status": 1
}
```

### 14\.发布swank

#### 接口功能

> 发布swank

#### URL

> /article/swank

#### HTTP请求方式

> POST

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> | 参数         | 必选 | 类型   | 说明     |
> | :----------- | :--- | :----- | -------- |
> | introduction | true | string | 文章标题 |
> | content      | true | string | 文章内容 |

#### 接口示例

> 地址：/article/swank

##### 请求

``` json
{
    "title":"swank1121",
    "content":"阿额枋ijafejaefp哈俄"
}
```

##### 响应

```json
{
    "message": "发布成功",
    "data": null,
    "status": 1
}
```

### 15\.获取推荐的文章

#### 接口功能

> 获取推荐的文章

#### URL

> /most/like

#### HTTP请求方式

> GET

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> 

#### 接口示例

> 地址：/most/like

##### 请求

``` json

```

##### 响应

```json
{
    "message": "获取成功",
    "data": [
        {
            "id": "1248499219426439170",
            "title": "swank1",
            "authorUsername": "sww",
            "authorId": "1248438184791179265",
            "authorAvatarUrl": "http://q89jpbw7d.bkt.clouddn.com/FsVIUUhwwO86xy4JfjBP-5suhKvP",
            "gmtCreate": "2020-04-10 06:33:05"
        },
        {
            "id": "1248499299978047489",
            "title": "story1",
            "authorUsername": "sww",
            "authorId": "1248438184791179265",
            "authorAvatarUrl": "http://q89jpbw7d.bkt.clouddn.com/FsVIUUhwwO86xy4JfjBP-5suhKvP",
            "gmtCreate": "2020-04-10 06:33:24"
        }
    ],
    "status": 1
}
```

### 16\.获取推荐的用户

#### 接口功能

> 获取推荐的用户

#### URL

> /most/followed

#### HTTP请求方式

> GET

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> 

#### 接口示例

> 地址：/most/followed

##### 请求

``` json

```

##### 响应

```json
{
    "message": "获取成功",
    "data": [
        {
            "userId": "1248138184744479265",
            "username": "sww1",
            "avatarUrl": "http://q89jpbw7d.bkt.clouddn.com/FsVIUUhwwO86xy4JfjBP-5suhKvP"
        }
    ],
    "status": 1
}
```

### 17\.关注用户

#### 接口功能

> 关注用户或取消关注

#### URL

> /follow/{userId}

#### HTTP请求方式

> POST

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> 

#### 接口示例

> 地址：/follow/1248138184744479265

##### 请求

``` json

```

##### 响应

```json
{
    "message": "关注成功",
    "data": null,
    "status": 1
}
// 如果已经关注则返回
{
    "message": "取消关注成功",
    "data": null,
    "status": 1
}
```

### 18\.点赞或取消点赞文章

#### 接口功能

> 点赞或取消点赞文章

#### URL

> article/like/{articleId}

#### HTTP请求方式

> POST

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求体

> 

#### 接口示例

> 地址：article/like/1248499299978047489

##### 请求

``` json

```

##### 响应

```json
{
    "message": "点赞成功",
    "data": null,
    "status": 1
}
// 如果已经关注则返回
{
    "message": "取消点赞成功",
    "data": null,
    "status": 1
}
```

### 19\.获取swank

#### 接口功能

> 获取swank

#### URL

> /article/swank?page=

#### HTTP请求方式

> GET

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求参数

> | 参数名称 | 类型     | 说明 |
> | -------- | -------- | ---- |
> | page     | int(>=1) | 页数 |
>
> 

#### 接口示例

> 地址：/article/swank?page=1

##### 请求

``` json

```

##### 响应

```json
{
    "message": "获取成功",
    "data": [
        {
            "id": "1248499219426439170",
            "title": "swank1",
            "authorUsername": "sww",
            "authorId": "1248438184833122306",
            "authorAvatarUrl": "http://q89jpbw7d.bkt.clouddn.com/FsVIUUhwwO86xy4JfjBP-5suhKvP",
            "gmtCreate": "2020-04-10 06:33:05"
        }
    ],
    "status": 1
}
```

### 20\.获取story

#### 接口功能

> 获取story

#### URL

> /article/story?page=

#### HTTP请求方式

> GET

#### 请求头

| 参数          | 类型             | 说明                |
| ------------- | ---------------- | ------------------- |
| Content-Type  | application/json |                     |
| Authorization | token            | 在登录时获得的token |

#### 请求参数

> | 参数名称 | 类型     | 说明 |
> | -------- | -------- | ---- |
> | page     | int(>=1) | 页数 |
>
> 

#### 接口示例

> 地址：/article/swank?page=1

##### 请求

``` json

```

##### 响应

```json
{
    "message": "获取成功",
    "data": [
        {
            "id": "1248499299978047489",
            "title": "story1",
            "authorUsername": "sww",
            "authorId": "1248438184833122306",
            "authorAvatarUrl": "http://q89jpbw7d.bkt.clouddn.com/FsVIUUhwwO86xy4JfjBP-5suhKvP",
            "gmtCreate": "2020-04-10 06:33:24"
        }
    ],
    "status": 1
}
```

