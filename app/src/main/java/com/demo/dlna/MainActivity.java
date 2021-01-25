package com.demo.dlna;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wasu.dlna.listener.BrowseRegistryListener;
import com.wasu.dlna.core.IWasuDlnaController;
import com.wasu.dlna.WasuDlnaManager;
import com.wasu.dlna.utils.Constant;

import org.fourthline.cling.model.meta.Device;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private MyAdapter adapter;
    String playUrl = "http://ali.appvod.wasumedia.cn/71602ca1688743ad9306e380e2bd2276/3e533913e1b84d5aa60411cc6acf246c-a7cde29247899719251f5f3571f94ebf-od-S00000001-100000.m3u8?isHD=0&isIpqam=0&token=E3975A5A482DA9CD4B22E146CD77166CFF59F16D982A304A6F55902EFE06B789B0631EE547C39D21061676153DA966493C3A00602F1C7FF21431D4DF82F01D615489ADD3BC4FCC773111303C73F696794421215463D721EC2C3926C6252C87AF4625FB105C750264409D5167A18424187A8EB2956C44EBBEF5C7E99117D928A8E4E0EA34ADF2B9BC9CF2CA4CBF69F6C87D8AACF564F04EEC538638D7A876CB8FB198BFFF4681DA70AFA79B25BA9FCA3D0E50B9927AC032A9681C2682&isHD=0&isIpqam=0&Contentid=CP23010020201231029311&duration=0_0";
    //"http://125.210.81.33:5543/lbvod_ip/CP23010020200528062538.m3u8?isHD=0&isIpqam=0&token=585C2CD7B6DBC7F7894E2832B6615722CB43139D0B64061A81FFE20A679BA2D89CAE59C222EDBDD2E19D15D538C92D859A503B244A14E0E810DAA8FE64BEF32A58FA217039FADD3405DFE62C9CF7C40C35A9E219AD1A0F67A959B2C831969C3083D9274D00DBE7B6424FB31B9C30549B246899B3F37E9B16399DE34C123FFB02FA177BCF55E3B889E0B649DCF7F2301AF778DA18653FC2562EC7A409&isHD=0&isIpqam=0&Contentid=CP23010020200528062538&duration=0_0&udid=a0f87acfe2b55584";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ListView listView = findViewById(R.id.list_view);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        WasuDlnaManager.getInstance().init(getApplication(), browseRegistryListener);
        WasuDlnaManager.getInstance().prepare();
    }

    private final BrowseRegistryListener browseRegistryListener = new BrowseRegistryListener() {

        @Override
        protected void onDeviceAdded(Device device) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(Constant.TAG, "onDeviceAdded: " + device.getDetails().getFriendlyName() + ", isFullyHydrated: " + device.isFullyHydrated());
                    int position = adapter.getItemIndex(device);
                    if (position >= 0) {
                        // Device already in the list, re-set new value at same position
                        adapter.remove(device);
                        adapter.add(device, position);
                    } else {
                        adapter.add(device);
                    }
                }
            });
        }

        @Override
        protected void onDeviceRemoved(Device device) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.remove(device);
                }
            });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "搜索").setIcon(android.R.drawable.ic_menu_search);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                adapter.clear();
                WasuDlnaManager.getInstance().searchDevices();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IWasuDlnaController wasuDlnaController = WasuDlnaManager.getInstance().getWasuDlnaController();
        wasuDlnaController.selectDevice(adapter.getItem(position));
        wasuDlnaController.setPlayItem(playUrl, "蜘蛛侠", null);
        Intent intent = new Intent(this, ControlActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WasuDlnaManager.getInstance().unInit();
    }

    class MyAdapter extends BaseAdapter {
        List<Device> data = new ArrayList<>();

        public MyAdapter() {

        }

        public void setData(List<Device> list) {
            data.addAll(list);
        }

        public void clear() {
            if (data != null) {
                data.clear();
            }
        }

        public void remove(Device device) {
            if (data != null) {
                data.remove(device);
            }
            notifyDataSetChanged();
        }

        public void add(Device device) {
            if (data != null) {
                data.add(device);
            }
            notifyDataSetChanged();
        }

        public void add(Device device, int pos) {
            data.add(pos, device);
        }

        public int getItemIndex(Device device) {
            return data.indexOf(device);
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Device getItem(int position) {
            return data == null ? null : data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.device_item, parent, false);
                holder = new ViewHolder();
                holder.textView = convertView.findViewById(R.id.device_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(getItem(position).getDetails().getFriendlyName());
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}