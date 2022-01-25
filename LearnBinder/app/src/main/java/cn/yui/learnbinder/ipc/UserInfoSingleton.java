package cn.yui.learnbinder.ipc;

import androidx.annotation.Nullable;

import cn.yui.learnbinder.data.UserInfo;

/**
 * @Description: 用户信息实现类，一般不暴露给调用者
 * @Author: Yui Master
 * @CreateDate: 2022/1/25 3:15 下午
 */
public class UserInfoSingleton implements IUserInfoSingleton {
    @Nullable
    private static UserInfoSingleton instance = null;
    @Nullable
    private UserInfo userInfo;

    private UserInfoSingleton() {

    }

    public static synchronized UserInfoSingleton getInstance() {
        if (instance == null) {
            instance = new UserInfoSingleton();
        }
        return instance;
    }

    @Override
    @Nullable
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public void setUserInfo(@Nullable UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
