# Backend 设计

- 说明：这是一个会议系统，两个后台

  - Administrator 后台
  - User 后台

- 四种角色( Administrator + 3 Users )

  - 管理员 Administrator：审核会议申请
  - 发起会议者 Chair：User 发起会议后成为 Chair
  - 程序委员会委员 PC member：由 Chair 邀请成为
  - 投稿人 Author：User 投稿后成为 Author

- 进入系统逻辑

  - User 注册
  - User 登录
  - User 进入操作系统

- 会议申请逻辑

  - User 发起会议，成为 Chair ，等待审核
  - Administrator 审核

    1. 审核不通过，结束
    2. 审核通过

       a. Chair 开启投稿

       b. Chair 邀请 PC members

       c. Author 投稿

- 一些方法
  - 由 token 获取用户名： `userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()))` （因为用户名是唯一的，而 token 在每次登录的时候都会有一映射，所以可以获得）
