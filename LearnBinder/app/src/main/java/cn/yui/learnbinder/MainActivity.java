package cn.yui.learnbinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.yui.ipclib.core.BinderIPC;
import cn.yui.learnbinder.data.UserInfo;
import cn.yui.learnbinder.ipc.UserInfoSingleton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BinderIPC.getInstance().open(this);
        BinderIPC.getInstance().register(UserInfoSingleton.class);
    }

    public void gotoSecondActivity(View view) {
        UserInfoSingleton.getInstance().setUserInfo(new UserInfo("password", "name"));
        startActivity(new Intent(this, SecondActivity.class));
    }
}