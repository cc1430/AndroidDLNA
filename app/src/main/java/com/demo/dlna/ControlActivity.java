package com.demo.dlna;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wasu.dlna.WasuDlnaManager;
import com.wasu.dlna.bean.DlnaResponse;
import com.wasu.dlna.bean.PlayPositionResponse;
import com.wasu.dlna.listener.GetPlayPositionListener;
import com.wasu.dlna.listener.PauseListener;
import com.wasu.dlna.listener.PlayListener;
import com.wasu.dlna.listener.StopListener;
import com.wasu.dlna.utils.Utils;

import java.lang.ref.WeakReference;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener, Handler.Callback {

    private TextView tvDuration;
    private TextView tvPosition;
    private MyHandler myHandler;
    private Handler mainHandler;

    public static final int MSG_UPDATE_POSITION = 0x000;
    public static final int MSG_QUERY_POSITION = 0x100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        myHandler = new MyHandler(this);
        mainHandler = new Handler(getMainLooper(), this);

        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_forward).setOnClickListener(this);

        tvDuration = findViewById(R.id.tv_duration_value);
        tvPosition = findViewById(R.id.tv_pos_value);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_play) {
            WasuDlnaManager.getInstance().getWasuDlnaController().play(new PlayListener() {
                @Override
                public void onPlaySuccess(DlnaResponse response) {
                    myHandler.removeMessages(MSG_QUERY_POSITION);
                    myHandler.sendEmptyMessage(MSG_QUERY_POSITION);
                }

                @Override
                public void onPlayFailed(DlnaResponse response) {
                    myHandler.removeMessages(MSG_QUERY_POSITION);
                }
            });
        } else if (id == R.id.btn_pause) {
            WasuDlnaManager.getInstance().getWasuDlnaController().pause(new PauseListener() {
                @Override
                public void onPauseSuccess(DlnaResponse response) {
                    myHandler.removeMessages(MSG_QUERY_POSITION);
                }

                @Override
                public void onPauseFailed(DlnaResponse response) {

                }
            });
        } else if (id == R.id.btn_stop) {
            WasuDlnaManager.getInstance().getWasuDlnaController().stop(new StopListener() {
                @Override
                public void onStopSuccess(DlnaResponse response) {
                    ControlActivity.this.finish();
                }

                @Override
                public void onStopFailed(DlnaResponse response) {

                }
            });
        } else if (id == R.id.btn_back) {
            String curPos = (String) tvPosition.getText();
            int pos = Utils.getIntTime(curPos) - 5000;
            WasuDlnaManager.getInstance().getWasuDlnaController().seek(pos, null);
        } else if (id == R.id.btn_forward) {
            String curPos = (String) tvPosition.getText();
            int pos = Utils.getIntTime(curPos) + 5000;
            WasuDlnaManager.getInstance().getWasuDlnaController().seek(pos, null);
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case MSG_UPDATE_POSITION:
                PlayPositionResponse response = (PlayPositionResponse) msg.obj;
                tvDuration.setText(response.getTrackDuration());
                tvPosition.setText(response.getRelTime());
                break;
            default:
                break;
        }
        return false;
    }

    private static class MyHandler extends Handler {
        WeakReference<Activity> weakReference;

        public MyHandler(Activity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_QUERY_POSITION:
                    ((ControlActivity)weakReference.get()).queryPlayInfo();
                    break;
                default:
                    break;
            }
        }
    }

    public void queryPlayInfo() {
        WasuDlnaManager.getInstance().getWasuDlnaController().getPlayPosition(new GetPlayPositionListener() {
            @Override
            public void onGetPlayPositionSuccess(PlayPositionResponse response) {
                Message message = mainHandler.obtainMessage(MSG_UPDATE_POSITION);
                message.obj = response;
                mainHandler.sendMessage(message);

                myHandler.sendEmptyMessageDelayed(MSG_QUERY_POSITION, 1000);
            }

            @Override
            public void onReceivedPlayPosition(PlayPositionResponse response) {

            }

            @Override
            public void onGetPlayPositionFailed(PlayPositionResponse response) {
                myHandler.removeMessages(MSG_QUERY_POSITION);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
        mainHandler.removeCallbacksAndMessages(null);
    }

}