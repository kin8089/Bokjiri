package com.cookandroid.users;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private long backpressedTime = 0;
    private GpsTracker gpsTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 구글에서 등록한 api와 엮어주기

        // 시작위치를 서울 시청으로 변경
        gpsTracker = new GpsTracker(MapActivity.this);
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        LatLng currnt = new LatLng(latitude, longitude);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currnt));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(currnt);

        markerOptions.title("내 위치");

        markerOptions.snippet("내 위치");

        googleMap.addMarker(markerOptions);


        LatLng cityHall = new LatLng(37.505263, 127.054858); // 서울시청 위도와 경도

        // 시작시 마커 생성하기

        markerOptions.position(cityHall);

        markerOptions.title("서울고용복지센터");

        markerOptions.snippet("서울고용복지센터");


        // 생성된 마커 옵션을 지도에 표시

        googleMap.addMarker(markerOptions);


        // 서울광장마커

        // 회사 DB에 데이터를 가지고 있어야 된다.

        LatLng plaza = new LatLng(37.503008, 127.054160);

        markerOptions.position(plaza);

        markerOptions.title("대치노인복지센터");

        markerOptions.snippet("대치노인복지센터");

        googleMap.addMarker(markerOptions);


        LatLng plaza1 = new LatLng(37.509398, 127.041949);

        markerOptions.position(plaza1);

        markerOptions.title("강남복지재단");

        markerOptions.snippet("강남복지재단");

        googleMap.addMarker(markerOptions);



        LatLng plaza2 = new LatLng(37.491868, 127.073535);

        markerOptions.position(plaza2);

        markerOptions.title("강남장애인복지관");

        markerOptions.snippet("강남장애인복지관");

        googleMap.addMarker(markerOptions);


        LatLng plaza3 = new LatLng(37.505062, 127.052205);

        markerOptions.position(plaza3);

        markerOptions.title("근로복지공단");

        markerOptions.snippet("근로복지공단");

        googleMap.addMarker(markerOptions);


        LatLng plaza4 = new LatLng(37.491743, 127.025391);

        markerOptions.position(plaza4);

        markerOptions.title("서초2동 주민센터");

        markerOptions.snippet("서초2동 주민센터");

        googleMap.addMarker(markerOptions);


        LatLng plaza5 = new LatLng(37.492044, 127.024946);

        markerOptions.position(plaza5);

        markerOptions.title("서초1동 주민센터");

        markerOptions.snippet("서초1동 주민센터");

        googleMap.addMarker(markerOptions);


        LatLng plaza6 = new LatLng(37.502716, 127.023964);

        markerOptions.position(plaza6);

        markerOptions.title("서초4동 주민센터");

        markerOptions.snippet("서초4동 주민센터");

        googleMap.addMarker(markerOptions);

        LatLng plaza7 = new LatLng(37.511482, 127.028527);

        markerOptions.position(plaza7);

        markerOptions.title("논현1동 행정복지센터");

        markerOptions.snippet("논현1동 행정복지센터");

        googleMap.addMarker(markerOptions);


        LatLng old1 = new LatLng(37.494481, 127.041825);

        markerOptions.position(old1);

        markerOptions.title("역삼노인센터");

        markerOptions.snippet("역삼노인센터");

        googleMap.addMarker(markerOptions);


        LatLng old2 = new LatLng(37.575978, 126.985864);

        markerOptions.position(old2);

        markerOptions.title("서울노인복지센터");

        markerOptions.snippet("서울노인복지센터");

        googleMap.addMarker(markerOptions);


        LatLng young = new LatLng(37.567388, 126.3990580);

        markerOptions.position(young);

        markerOptions.title("서울시청소년상담복지센터");

        markerOptions.snippet("서울시청소년상담복지센터");

        googleMap.addMarker(markerOptions);


//        //맵 로드 된 이후
//
//        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//
//            @Override
//
//            public void onMapLoaded() {
//
//                Toast.makeText(MapActivity.this, "Map로딩성공", Toast.LENGTH_SHORT).show();
//
//
//            }
//
//        });

        //카메라 이동 시작

        googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {

            @Override

            public void onCameraMoveStarted(int i) {

                Log.d("set>>", "start");

            }

        });

        // 카메라 이동 중

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override

            public void onCameraMove() {

                Log.d("set>>", "move");

            }

        });

        // 지도를 클릭하면 호출되는 이벤트

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override

            public void onMapClick(LatLng latLng) {

                // 기존 마커 정리

//                googleMap.clear();


                // 클릭한 위치로 지도 이동하기

                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


                // 신규 마커 추가

//                MarkerOptions newMarker = new MarkerOptions();
//
//                newMarker.position(latLng);
//
//                googleMap.addMarker(newMarker);

            }

        });

    }


    @TargetApi(Build.VERSION_CODES.M)

    private void checkPermission() {

        String[] permissions = {

                // Manifest는 android를 import

                android.Manifest.permission.ACCESS_COARSE_LOCATION,

                android.Manifest.permission.ACCESS_FINE_LOCATION

        };


        int permissionCheck = PackageManager.PERMISSION_GRANTED;

        for (String permission : permissions) {

            permissionCheck = this.checkSelfPermission(permission);

            if (permissionCheck == PackageManager.PERMISSION_DENIED) {

                break;

            }

        }


        if (permissionCheck == PackageManager.PERMISSION_DENIED) {

            this.requestPermissions(permissions, 1);

        }

    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            Intent intent = new Intent(MapActivity.this, Main2Activity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }
    }

}