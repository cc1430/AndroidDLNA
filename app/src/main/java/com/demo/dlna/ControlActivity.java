package com.demo.dlna;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wasu.dlna.WasuDlnaManager;
import com.wasu.dlna.bean.DlnaResponse;
import com.wasu.dlna.bean.PlayPositionResponse;
import com.wasu.dlna.bean.VolumeResponse;
import com.wasu.dlna.listener.GetPlayPositionListener;
import com.wasu.dlna.listener.GetVolumeListener;
import com.wasu.dlna.listener.PauseListener;
import com.wasu.dlna.listener.PlayListener;
import com.wasu.dlna.listener.StopListener;
import com.wasu.dlna.utils.Utils;

import org.fourthline.cling.support.model.PositionInfo;

import java.lang.ref.WeakReference;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener, Handler.Callback, SeekBar.OnSeekBarChangeListener {

    private TextView tvDuration;
    private TextView tvPosition;
    private SeekBar seekBarProgress;

    private SeekBar seekBarVolume;

    private MyHandler myHandler;
    private Handler mainHandler;

    boolean isMute;

    public static final int MSG_UPDATE_POSITION = 0x000;
    public static final int MSG_UPDATE_VOLUME = 0x001;

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
        findViewById(R.id.btn_mute).setOnClickListener(this);
        findViewById(R.id.btn_volume_minus).setOnClickListener(this);
//        findViewById(R.id.btn_volume_plus).setOnClickListener(this);

        tvDuration = findViewById(R.id.tv_duration_value);
        tvPosition = findViewById(R.id.tv_pos_value);

        seekBarProgress = findViewById(R.id.sb_progress);
        seekBarProgress.setOnSeekBarChangeListener(this);

        seekBarVolume = findViewById(R.id.sb_volume);
        seekBarVolume.setOnSeekBarChangeListener(this);
        seekBarVolume.setProgress(0);

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
        } else if (id == R.id.btn_mute) {
            WasuDlnaManager.getInstance().getWasuDlnaController().setMute(!isMute, null);
            isMute = !isMute;
        } else if (id == R.id.btn_volume_minus) {
            WasuDlnaManager.getInstance().getWasuDlnaController().getTransportInfo(null);
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case MSG_UPDATE_POSITION:
                PlayPositionResponse response = (PlayPositionResponse) msg.obj;
                PositionInfo positionInfo = response.getResponse();
                tvDuration.setText(positionInfo.getTrackDuration());
                tvPosition.setText(positionInfo.getRelTime());

                int max = Utils.getIntTime(positionInfo.getTrackDuration());
                seekBarProgress.setMax(max);
                int current = Utils.getIntTime(positionInfo.getRelTime());
                seekBarProgress.setProgress(current);
                break;
            case MSG_UPDATE_VOLUME:
                seekBarVolume.setMax((Integer) msg.obj);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        int id = seekBar.getId();
        if (id == R.id.sb_progress) {
            WasuDlnaManager.getInstance().getWasuDlnaController().seek(progress, null);
        } else if (id == R.id.sb_volume) {
            WasuDlnaManager.getInstance().getWasuDlnaController().setVolume(progress, null);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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

            }

            @Override
            public void onReceivedPlayPosition(PlayPositionResponse response) {
                Message message = mainHandler.obtainMessage(MSG_UPDATE_POSITION);
                message.obj = response;
                mainHandler.sendMessage(message);

                myHandler.sendEmptyMessageDelayed(MSG_QUERY_POSITION, 1000);
            }

            @Override
            public void onGetPlayPositionFailed(PlayPositionResponse response) {
                myHandler.removeMessages(MSG_QUERY_POSITION);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 获取当前音量的有问题拿到的都是100
         */
        WasuDlnaManager.getInstance().getWasuDlnaController().getVolume(new GetVolumeListener() {
            @Override
            public void onGetVolumeSuccess(VolumeResponse response) {

            }

            @Override
            public void onReceivedVolume(VolumeResponse response) {
                Message message = mainHandler.obtainMessage(MSG_UPDATE_VOLUME);
                message.obj = response.getResponse();
                mainHandler.sendMessage(message);
            }

            @Override
            public void onGetVolumeFailed(VolumeResponse response) {

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