# Backend 设计

## 说明：这是一个会议系统的后端

## 四种角色( Administrator + 3 Users )

1. 管理员 Administrator：审核会议申请
2. 发起会议者 Chair：User 发起会议后成为 Chair
3. 程序委员会委员 PC member：由 Chair 邀请成为
4. 投稿人 Author：User 投稿后成为 Author

## 进入系统逻辑

1. User

   - User 注册
   - User 登录
   - User 进入操作系统

2. admin

   - admin 登录

## 会议申请逻辑

1. User 发起会议，成为 Chair ，等待审核
2. Administrator 审核

   - 审核不通过，结束
   - 审核通过

     a. Chair 开启投稿

     b. Chair 邀请 PC members

     c. Author 投稿

## Request 说明

- Initial Request

  - 注册：前端发送申请，由 AuthController 接受 RegisterRequest，再调用 AuthService 中 register 方法注册用户

    - 若用户名已存在——注册失败
    - 若用户名不存在——userRepository 中添加新注册的 User

  - 登录：前端发送申请，由 AuthController 接受 LoginRequest，再调用 AuthService 中 login 方法验证密码

    - 密码正确——登录成功
    - 密码错误——登陆失败，错误三次，冻结账户（待完成）

- User Request

  - 会议申请：前端发送申请，由 AuthController 接受 ConferenceRequest，再调用 AuthService 中 Conference 方法将会议加入会议仓库 ConferenceRepertories

  - 会议邀请：会议通过后 `Conference.getStatus() == Conference.Status.valueOf(PASS)`，chair 可以邀请其他用户成为 PC members

  - 查看会议：User 可查看现在通过的会议

  - 投稿：Users 查看会议列表后可以投稿

- Admin Request

  - 查看会议：Admin 可查看现在所有的会议 `{ALL, PENDING, PASS, REJECT}`

  - 会议审核（管理员）：管理员查看会议仓库 ConferenceRepertories 中的会议列表，决定每个会议的状态 `public static enum Status {PENDING, PASS, REJECT}`

- 一些方法
  - 由 token 获取用户名： `userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()))` （因为用户名是唯一的，而 token 在每次登录的时候都会有一映射，所以可以获得）

## 待做

- 引入 MySQL
- 多次错误冻结账号——管理员解冻

## 接口说明

- 方法：GET/POST
- 地址
- 发送的值是什么
- 接受的值是什么
