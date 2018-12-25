package com.miki.mytestcall;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miki.mytest.IMyService;

public class MainActivity extends AppCompatActivity {
        private IMyService iMyService;
        private  ServiceConnection con=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        conService();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button =findViewById(R.id.button);
        final TextView textView =findViewById(R.id.mtest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (iMyService!=null)
                    textView.setText(String.valueOf(iMyService.youComeing(1,1)));
                    else {
                        Log.d("luol","null");
                        conService();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void  conService(){
            con=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iMyService =IMyService.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Bundle args = new Bundle();
        Intent intent = new Intent("com.miki.mytest.MainActivity");
        intent.putExtras(args);
        intent.setPackage("com.miki.mytest");
        bindService(intent, con, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iMyService != null) {
         unbindService(con);
        }
    }
}
