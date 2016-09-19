package com.standardproject.common;

import android.content.Context;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.standardproject.R;
import com.standardproject.util.baidu.BaiduMapUtilByRacer;
import com.standardproject.util.baidu.LocationBean;
import com.ugiant.AbActivity;

/**
 * Created by yangpeixian on 2016/7/19.
 * 百度定位
 */
public class BaiduLocationActivity extends AbActivity {

    // 搜索模块，也可去掉地图模块独立使用
    private Marker mMarker = null;

    private BaiduMap mBaiduMap;
    // 控件
    private MapView mMapView;

    // 地图需要参数
    private static Context mContext;
    private LocationBean mLocationBean; // / 地图实体类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_location);

        initViews();
        initData();
        setListeners();

    }

    @Override
    public void initViews() {
        // 初始化地图需要空间
        this.mContext = this;
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.mMapView);
        BaiduMapUtilByRacer.goneMapViewChild(mMapView, true, true);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
        mBaiduMap.getUiSettings().setZoomGesturesEnabled(true);// 缩放手势
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        locate(); // / 定位
    }

    @Override
    public void initData() {
//        //设定中心点坐标
//        LatLng cenpt = new LatLng(29.806651, 121.606983);
//        //定义地图状态
//        MapStatus mMapStatus = new MapStatus.Builder()
//                .target(cenpt)
//                .zoom(18)
//                .build();
//        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        //改变地图状态
//        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    public void setListeners() {

    }

    // 定位
    public void locate() {
        BaiduMapUtilByRacer.locateByBaiduMap(mContext, 2000,
                new BaiduMapUtilByRacer.LocateListener() {

                    @Override
                    public void onLocateSucceed(LocationBean locationBean) {
                        mLocationBean = locationBean;
                        if (mMarker != null) {
                            mMarker.remove();
                        } else {
                            mBaiduMap.clear();
                        }
                        mMarker = BaiduMapUtilByRacer.showMarkerByResource(
                                locationBean.getLatitude(),
                                locationBean.getLongitude(), R.drawable.point,
                                mBaiduMap, 0, true);
                    }

                    @Override
                    public void onLocateFiled() {
                    }

                    @Override
                    public void onLocating() {
                    }
                });
    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mLocationBean = null;
        if (mMapView != null) {
            mBaiduMap.setMyLocationEnabled(false);// 关闭定位图层
            mMapView.destroyDrawingCache();
            mMapView.onDestroy();
            mMapView = null;
        }
        super.onDestroy();
    }
}
