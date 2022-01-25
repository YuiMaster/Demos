package cn.yui.learnbinder.ipc;

import cn.yui.ipclib.ClassId;
import cn.yui.learnbinder.data.UserInfo;

/**
 * @Description: java类作用描述
 * @Author: Yui Master
 * @CreateDate: 2022/1/25 3:14 下午
 */
@ClassId("cn.yui.learnbinder.ipc.UserInfoSingleton")
public interface IUserInfoSingleton {
    UserInfo getUserInfo();

    void setUserInfo(UserInfo userInfo);
}
