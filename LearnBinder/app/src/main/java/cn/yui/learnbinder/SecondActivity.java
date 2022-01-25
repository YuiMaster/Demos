package cn.yui.learnbinder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.yui.ipclib.core.BinderIPC;
import cn.yui.learnbinder.data.UserInfo;
import cn.yui.learnbinder.ipc.IUserInfoSingleton;

/**
 * @Description: java类作用描述
 * @Author: Yui Master
 * @CreateDate: 2022/1/25 3:48 下午
 */
public class SecondActivity extends AppCompatActivity {
    private TextView textView;
    private IUserInfoSingleton userInfoSingleton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        BinderIPC.getInstance().open(SecondActivity.this);
        textView = findViewById(R.id.text_view);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoSingleton = BinderIPC.getInstance().getInstance(IUserInfoSingleton.class);
                UserInfo userInfo = userInfoSingleton.getUserInfo();
                textView.setText("" + userInfo.getName() + " " + userInfo.getPassword());
            }
        });
    }
}
