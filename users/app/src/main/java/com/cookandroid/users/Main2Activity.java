package com.cookandroid.users;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    LinearLayout logoutBtn, homeBtn, profileBtn, supportBtn;
    FloatingActionButton floatingActionButton;
    private final long FINISH_INTERVAL_TIME = 2000;
    // 구글api클라이언트
    private GoogleSignInClient googleSignInClient;
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private RecyclerAdapter adapter;
    //    String city = "";
//    ArrayList<String> citys = new ArrayList<>();
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        floatingActionButton = findViewById(R.id.chatBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Splash.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
                finish();
            }
        });


        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLocationServicesStatus()) {
                    checkRunTimePermission();
                } else {
                    showDialogForLocationServiceSetting();
                }
            }
        });


        profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, UserActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        supportBtn = findViewById(R.id.supportBtn);
        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, EtcActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();
        // Google 로그인을 앱에 통합
        // GoogleSignInOptions 개체를 구성할 때 requestIdToken을 호출
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("249861716913-e2196ledkjf9oasdnkiqns44a19e03f7.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        init();

        getData();

    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
        builder.setMessage("앱을 종료하시겠습니까?");
        builder.setPositiveButton("아니오", ((dialogInterface, which) -> {
            dialogInterface.cancel();
        }));
        builder.setNegativeButton("예", (dialogInterface, which) -> {
            finish();
            logout();
            googleSignInClient.signOut();
        });
        builder.show();
    }


    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerDecoration(30));
    }

    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList(
                "독립유공생활지원수당 지원",
                "친환경 무상급식비 및 친환경 쌀 구매 차액 지원",
                "자원봉사자 통합 교육",
                "1인가구 생활불편해소 서비스",
                "저소득 어르신 무료급식사업",
                "정신건강증진사업",
                "따듯한 중랑 산후조리 지원",
                "출산장려(출산축하금)",
                "사랑의 수어교실 운영",
                "청년마음건강지원사업",
                "재외국민긴급지원비",
                "통합공공임대",
                "보훈대상자 생계지원금 지급",
                "고령자 고용지원금",
                "장애인 신규고용장려금",
                "과학문화 바우처 지원",
                "영아수당 지원",
                "첫만남이용권");
        List<String> listContent = Arrays.asList(
                " 지원주기 : 월\n\n 제공유형 : 현금지급\n\n 신청방법 : 방문\n\n 지자체 정보 : 서울특별시",
                " 지원주기 : 반기\n\n 제공유형 : 현금지급\n\n 신청방법 : 인터넷,모바일\n\n 지자체 정보 : 서울특별시 성북구",
                " 지원주기 : 수시\n\n 제공유형 : 기타,자원봉사\n\n 신청방법 : 인터넷,모바일\n\n 지자체 정보 : 서울특별시 강서구",
                " 지원주기 : 수시\n\n 제공유형 : 현금지급\n\n 신청방법 : 방문,인터넷\n\n 지자체 정보 : 서울특별시 서초구",
                " 지원주기 : 수시\n\n 제공유형 : 기타\n\n 신청방법 : 방문,전화\n\n 지자체 정보 : 서울특별시 마포구",
                " 지원주기 : 수시\n\n 제공유형 : 현금지급\n\n 신청방법 : 인터넷,우편,방문\n\n 지자체 정보 : 서울특별시",
                " 지원주기 : 월\n\n 제공유형 : 현금지급\n\n 신청방법 : 전화,인터넷\n\n 지자체 정보 : 서울특별시 중랑구",
                " 지원주기 : 1회성\n\n 제공유형 : 현금지급\n\n 신청방법 : 방문\n\n 지자체 정보 : 서울특별시 노원구",
                " 지원주기 : 년\n\n 제공유형 : 기타,프로그램/서비스\n\n 신청방법 : 전화,방문,인터넷\n\n 지자체 정보 : 서울특별시 강북구",
                " 문의처 : 129\n\n 지원주기 : 분기\n\n 제공유형 : 전자바우처(바우처)\n\n 담당부처 : 보건복지부",
                " 문의처 : \n\n 지원주기 : \n\n 제공유형 : 기타\n\n 담당부처 : 외교부",
                " 문의처 : 1600-0777\n\n 지원주기 : 수시\n\n 제공유형 : 현물대여\n\n 담당부처 : 국토교통부",
                " 문의처 : 1577-0606\n\n 지원주기 : 월\n\n 제공유형 : 현금지급\n\n 담당부처 : 국가보훈처",
                " 문의처 : 1350\n\n 지원주기 : 분기\n\n 제공유형 : 현금지급\n\n 담당부처 : 고용노동부",
                " 문의처 : 1588-1519\n\n 지원주기 : 수시\n\n 제공유형 : 현금지급\n\n 담당부처 : 고용노동부",
                " 문의처 : 02-540-0222\n\n 지원주기 : 1회성\n\n 제공유형 : 전자바우처(바우처)\n\n 담당부처 : 과학기술정보통신부",
                " 문의처 : 129\n\n 지원주기 : 월\n\n 제공유형 : 현금지급\n\n 담당부처 : 보건복지부",
                " 문의처 : 129\n\n 지원주기 : 1회성\n\n 제공유형 : 전자바우처(바우처)\n\n 담당부처 : 보건복지부"
        );
        List<Integer> listResId = Arrays.asList(
                R.drawable.img2,
                R.drawable.rice,
                R.drawable.img3_1,
                R.drawable.img4,
                R.drawable.img5,
                R.drawable.img6,
                R.drawable.img7,
                R.drawable.img8,
                R.drawable.img9_1,
                R.drawable.mind,
                R.drawable.world,
                R.drawable.home,
                R.drawable.bohun,
                R.drawable.old,
                R.drawable.seak,
                R.drawable.sience,
                R.drawable.kid,
                R.drawable.born
        );
        List<String> listUri = Arrays.asList(
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004629&wlfareInfoReldBztpCd=02",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004626&wlfareInfoReldBztpCd=02",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004607&wlfareInfoReldBztpCd=02",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004602&wlfareInfoReldBztpCd=02",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004588&wlfareInfoReldBztpCd=02",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004543&wlfareInfoReldBztpCd=02",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004480&wlfareInfoReldBztpCd=02",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004379&wlfareInfoReldBztpCd=02",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004370&wlfareInfoReldBztpCd=02",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004671&wlfareInfoReldBztpCd=01",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004665&wlfareInfoReldBztpCd=01",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004663&wlfareInfoReldBztpCd=01",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004662&wlfareInfoReldBztpCd=01",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004660&wlfareInfoReldBztpCd=01",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004659&wlfareInfoReldBztpCd=01",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004658&wlfareInfoReldBztpCd=01",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004657&wlfareInfoReldBztpCd=01",
                "https://www.bokjiro.go.kr/ssis-teu/twataa/wlfareInfo/moveTWAT52011M.do?wlfareInfoId=WLF00004656&wlfareInfoReldBztpCd=01"
        );
        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setResId(listResId.get(i));
            data.setUri(listUri.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(Main2Activity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(Main2Activity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(Main2Activity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Main2Activity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
            Intent intent = new Intent(Main2Activity.this, MapActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(Main2Activity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(Main2Activity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(Main2Activity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("지도를 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
