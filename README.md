# AndroidDLNA

1.使用配置说明
1.1依赖添加

```groovy
implementation (name: 'WasuDlna_1.0', ext: 'aar')
implementation "org.fourthline.cling:cling-core:2.1.1"
implementation 'org.fourthline.cling:cling-support:2.1.1'
implementation 'org.eclipse.jetty:jetty-server:8.1.12.v20130726'
implementation 'org.eclipse.jetty:jetty-servlet:8.1.12.v20130726'
implementation 'org.eclipse.jetty:jetty-client:8.1.12.v20130726'
```



```
buildscript {
    repositories {
        google()
        jcenter()
        maven {
            url 'http://4thline.org/m2'
        }
    }
    dependencies {
        classpath "com.android.tools.build:gradle:4.1.1"
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url 'http://4thline.org/m2'
        }
    }
}
```

1.2 权限

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

1.3注册服务
<service android:name="com.wasu.dlna.core.WasuDlnaService" />

2.使用流程
初始化->准备（启动服务）->搜索设备->选择投屏设备->设置播放片源->播放->播放控制（暂停、快进快退）->停止播放->反初始化

3.API
3.1初始化

```
WasuDlnaManager.getInstance().init(getApplication(), browseRegistryListener);
```

3.2准备

```
WasuDlnaManager.getInstance().prepare();
```

3.3搜索设备

```
WasuDlnaManager.getInstance().searchDevices();
```

3.4选择投屏设备

```
WasuDlnaController wasuDlnaController = WasuDlnaManager.getInstance().getWasuDlnaController();
wasuDlnaController.selectDevice(device);
```

3.5设置播放片源

```
WasuDlnaManager.getInstance().getWasuDlnaController().setPlayItem(playUrl, "蜘蛛侠", null);
```

3.6播放

```
WasuDlnaManager.getInstance().getWasuDlnaController().play(playListener);
```

3.7暂停

```
WasuDlnaManager.getInstance().getWasuDlnaController().pause(pauseListener);
```

3.8反初始化

```
WasuDlnaManager.getInstance().unInit();
```


3.6播放
WasuDlnaManager.getInstance().getWasuDlnaController().play(playListener);
3.7暂停
WasuDlnaManager.getInstance().getWasuDlnaController().pause(pauseListener);
3.8反初始化
WasuDlnaManager.getInstance().unInit();
