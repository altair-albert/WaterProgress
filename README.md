# WaterProgress
Android 水波进度条
使用方法
```xml
    <net.hivebc.waterprogress.view.WaterProgress
        android:id="@+id/progress"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        app:speed="0.4"
        app:wave="0.3" />
```

```java
WaterProgress waterProgress = (WaterProgress) findViewById(R.id.progress);
waterProgress.setProgress(10);   //设置进度
waterProgress.setSpeed(0.5);   // 设置波纹速度
waterProgress.setWave(0.3);  // 设置波纹高度 
```
## 属性

名称  |   作用    | 取值
-----|----------|-------
speed| 波纹速度  |  float（0-1）
wave |波纹幅度|float（0-1）
max|最大进度|int
color|波纹颜色|color
backgroundColor|背景颜色|color
showProgress|是否显示进度|boolean
diam|球形半径大小|dimension
textColor|进度文字颜色|color
textSize|进度文字大小|dimension
