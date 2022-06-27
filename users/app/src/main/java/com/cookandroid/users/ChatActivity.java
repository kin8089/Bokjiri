package com.cookandroid.users;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    final int PERMISSION = 1;
    private long backpressedTime = 0;
    LinearLayout btn_home, TTSBtn;
    RecyclerView recyclerView;
    EditText et_msg;
    ImageView imageView, imageView2;
    ArrayList<Chatsmodal> chatsmodalArrayList;
    ChatAdapter chatAdapter;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    Context mContext;
    Intent i;
    SpeechRecognizer mRecognizer;
    private TextToSpeech tts;
    //    RecyclerView.LayoutManager layoutManager;
//    PreferenceManager preferenceManager;
    String url, mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mContext = this;

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        recyclerView = findViewById(R.id.chat_recycler);
        et_msg = findViewById(R.id.et_msg);
        imageView = findViewById(R.id.send_btn);
        imageView2 = findViewById(R.id.sst_btn);
        chatsmodalArrayList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatsmodalArrayList, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(chatAdapter);

        recyclerView.setItemViewCacheSize(100);

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        }
                    });
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_msg.getText().toString().isEmpty()) {
                    Toast.makeText(ChatActivity.this, "메시지를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (et_msg.getText().toString().equals("1")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("2")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("3")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("1번")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("2번")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("3번")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("첫 번째")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("두 번째")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("세 번째")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("처음")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("중간")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("마지막")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("국민취업제도")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("장애아동수당")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("네")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("응")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("아니오")) {
                    chat5();
                } else if (et_msg.getText().toString().equals("아니요")) {
                    chat5();
                } else if (et_msg.getText().toString().equals("아니")) {
                    chat5();
                } else if (et_msg.getText().toString().equals("양로지원")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("마음건강지원ㅅ아버")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("학대아동쉼터")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("이동하기")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chatsmodalArrayList.add(new Chatsmodal("이동하기", USER_KEY));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                        }
                    }, 1000); //딜레이 타임 조절
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        }
                    });
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    et_msg.setText(null);

                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chatsmodalArrayList.add(new Chatsmodal("저희 서비스에 만족하셨나요?\n챗봇을 종료하시려면 \"종료\"를 입력해주세요", BOT_KEY));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                        }
                    }, 2000); //딜레이 타임 조절
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        }
                    });
                } else if (et_msg.getText().toString().equals("신청대상 알려주세요")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("신청대상")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("대상")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("어디에")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("어디서")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("접수방법")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("신청방법")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("어디에 신청하면 돼요?")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("어디에 신청하는 거야?")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("할만한거 아무거나")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("나도 신청할 수 있어?")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("어디서 더 확인할 수 있어?")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("사이트 있으면 보여주세요")) {
                    chat7();
                } else if (et_msg.getText().toString().equals("사이트")) {
                    chat7();
                } else if (et_msg.getText().toString().equals("사이트 있어?")) {
                    chat7();
                } else if (et_msg.getText().toString().equals("종료")) {
                    Intent intent = new Intent(ChatActivity.this, Main2Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                } else {
                    chat1();
                }
//                et_msg.setText(null);
                imm.hideSoftInputFromWindow(et_msg.getWindowToken(), 0);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                });
            }
        });

        btn_home = findViewById(R.id.homeBtn);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, Main2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatsmodalArrayList.add(new Chatsmodal("무슨 도움이 필요하신가요?", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("무슨 도움이 필요하신가요?", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }, 1000); //딜레이 타임 조절

//        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getContext().getPackageName());
//        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
//        mRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
//        mRecognizer.setRecognitionListener(listener);

        // 퍼미션 체크
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        // xml의 버튼과 텍스트 뷰 연결

        // RecognizerIntent 객체 생성
        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        // 버튼을 클릭 이벤트 - 객체에 Context와 listener를 할당한 후 실행
        imageView2.setOnClickListener(v -> {
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(i);
        });

//        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status != android.speech.tts.TextToSpeech.ERROR) {
//                    tts.setLanguage(Locale.KOREAN);
//                }
//            }
//        });

        TTSBtn = findViewById(R.id.TTSBtn);
        TTSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tts != null) {
                    tts.stop();
                    tts.shutdown();
                    tts = null;
                    Toast.makeText(ChatActivity.this, "음성모드를 종료 합니다", Toast.LENGTH_SHORT).show();
                } else {
                    tts = new TextToSpeech(ChatActivity.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != android.speech.tts.TextToSpeech.ERROR) {
                                tts.setLanguage(Locale.KOREAN);
                            }
                        }
                    });
                    Toast.makeText(ChatActivity.this, "음성모드를 활성화 합니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void getResponse(ChatRequest chatRequest) {
        chatAdapter.notifyDataSetChanged();
        String token = PreferenceManager.getString(mContext, "key");
        String authToken = "Bearer " + token;
//        System.out.println("Token 저장 확인용: " + authToken);
//        System.out.println("message 저장 확인용: " + chatRequest.getMy_chat());
        Call<ChatResponse> call = ApiClient.getService2(authToken).chatbot(chatRequest);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful()) {
                    chatsmodalArrayList.add(new Chatsmodal(response.body().getMessage(), BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);

                    if (tts != null) {
                        tts.setPitch(1.0f);
                        tts.setSpeechRate(1.0f);
                        tts.speak(response.body().message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        }
                    });

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chatsmodalArrayList.add(new Chatsmodal("원하시는 서비스를 골라주세요 없으시다면 \"아니\"를 입력해주세요", BOT_KEY));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
//                            if (tts != null) {
//                                tts.setPitch(1.0f);
//                                tts.setSpeechRate(1.0f);
//                                tts.speak("원하시는 내용의 서비스 번호를 입력해주세요", TextToSpeech.QUEUE_FLUSH, null);
//                            }
                        }
                    }, 1000); //딜레이 타임 조절
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        }
                    });
                }
            }

            //            private void getResponse(String message) {
//                chatsmodalArrayList.add(new Chatsmodal(message,USER_KEY));
//                chatAdapter.notifyDataSetChanged();
//                String url = "~~msg="+message;
//                String BASE_URL = "";
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                UserService userService = retrofit.create(UserService.class);
//                Call<MsgModal> call = userService.getMessage(url);
//                call.enqueue(new Callback<MsgModal>() {
//                    @Override
//                    public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
//                        if(response.isSuccessful()){
//                            MsgModal msgModal = response.body();
//                            chatsmodalArrayList.add(new Chatsmodal(msgModal.getCnt(),BOT_KEY));
//                            chatAdapter.notifyDataSetChanged();
//                            recyclerView.scrollToPosition(chatsmodalArrayList.size()-1);
//                        }
//                    }
            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                chatsmodalArrayList.add(new Chatsmodal("응답이 없습니다", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("응답이 없습니다", TextToSpeech.QUEUE_FLUSH, null);
                }
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                });
            }
        });
    }

    private void getResponse2(ChatRequest chatRequest) {
        chatAdapter.notifyDataSetChanged();
        String token = PreferenceManager.getString(mContext, "key");
        String authToken = "Bearer " + token;
//        System.out.println("Token 저장 확인용: " + authToken);
//        System.out.println("message 저장 확인용: " + chatRequest.getMy_chat());
        Call<ChatResponse> call = ApiClient.getService2(authToken).chatbot2(chatRequest);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful()) {
                    chatsmodalArrayList.add(new Chatsmodal(response.body().getMessage(), BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);

                    if (tts != null) {
                        tts.setPitch(1.0f);
                        tts.setSpeechRate(1.0f);
                        tts.speak(response.body().message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        }
                    });

//                    if (!response.body().getMessage().equals("서비스번호를 입력해주세요")) {
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                chatsmodalArrayList.add(new Chatsmodal("세부 정보를 원하시면 \"이동하기\"를 입력해주세요", BOT_KEY));
//                                recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
//                                chatAdapter.notifyDataSetChanged();
////                            if (tts != null) {
////                                tts.setPitch(1.0f);
////                                tts.setSpeechRate(1.0f);
////                                tts.speak("추가적으로 다른 정보를 원하시면 채팅을 입력해주세요", TextToSpeech.QUEUE_FLUSH, null);
////                            }
//                            }
//                        }, 1000); //딜레이 타임 조절
//                        recyclerView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
//                            }
//                        });
//                    }
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                chatsmodalArrayList.add(new Chatsmodal("응답이 없습니다", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("응답이 없습니다", TextToSpeech.QUEUE_FLUSH, null);

                }
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                });
            }
        });
    }

    private void getResponse3(ChatRequest chatRequest) {
        chatAdapter.notifyDataSetChanged();
        String token = PreferenceManager.getString(mContext, "key");
        String authToken = "Bearer " + token;
//        System.out.println("Token 저장 확인용: " + authToken);
//        System.out.println("message 저장 확인용: " + chatRequest.getMy_chat());
        Call<ChatResponse> call = ApiClient.getService2(authToken).chatbot3(chatRequest);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                url = response.body().getUrl();
                if (response.isSuccessful()) {
                    chatsmodalArrayList.add(new Chatsmodal(response.body().getMessage(), BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                    if (tts != null) {
                        tts.setPitch(1.0f);
                        tts.setSpeechRate(1.0f);
                        tts.speak(response.body().message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        }
                    });
                    question();
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                chatsmodalArrayList.add(new Chatsmodal("응답이 없습니다", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("응답이 없습니다", TextToSpeech.QUEUE_FLUSH, null);
                }
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                });
            }
        });
    }

    private void getResponse4(ChatRequest chatRequest) {
        chatAdapter.notifyDataSetChanged();
        String token = PreferenceManager.getString(mContext, "key");
        String authToken = "Bearer " + token;
//        System.out.println("Token 저장 확인용: " + authToken);
//        System.out.println("message 저장 확인용: " + chatRequest.getMy_chat());
        Call<ChatResponse> call = ApiClient.getService2(authToken).chatbot3(chatRequest);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                url = response.body().getUrl();
                Log.e("url", url);
                if (response.isSuccessful()) {
                    chatsmodalArrayList.add(new Chatsmodal(response.body().getMessage(), BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                    if (tts != null) {
                        tts.setPitch(1.0f);
                        tts.setSpeechRate(1.0f);
                        tts.speak(response.body().message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                chatsmodalArrayList.add(new Chatsmodal("응답이 없습니다", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("응답이 없습니다", TextToSpeech.QUEUE_FLUSH, null);
                }
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            Intent intent = new Intent(ChatActivity.this, Main2Activity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(), "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "일치하는 단어가 없습니다";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "천천히 말씀해주세요";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버에 에러";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다 : " + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줍니다.
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for (int i = 0; i < matches.size(); i++) {
                et_msg.setText(matches.get(i));
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    };

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        super.onDestroy();
    }

    public void chat1() {
        chatsmodalArrayList.add(new Chatsmodal(et_msg.getText().toString(), USER_KEY));
        chatAdapter.notifyDataSetChanged();
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setMy_chat(et_msg.getText().toString());
        getResponse(chatRequest);
        et_msg.setText(null);
    }

    public void chat2() {
        chatsmodalArrayList.add(new Chatsmodal(et_msg.getText().toString(), USER_KEY));
        chatAdapter.notifyDataSetChanged();
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setMy_chat(et_msg.getText().toString());
        getResponse2(chatRequest);
        et_msg.setText(null);
        question();
    }

    public void chat4() {
        chatsmodalArrayList.add(new Chatsmodal(et_msg.getText().toString(), USER_KEY));
        chatAdapter.notifyDataSetChanged();
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setMy_chat(et_msg.getText().toString());
        getResponse2(chatRequest);
        question();
        et_msg.setText(null);
    }

    public void chat5() {
        chatsmodalArrayList.add(new Chatsmodal(et_msg.getText().toString(), USER_KEY));
        chatAdapter.notifyDataSetChanged();
        et_msg.setText(null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatsmodalArrayList.add(new Chatsmodal("복지리의 서비스에 만족하셨나요?\n챗봇을 종료하시려면 \"종료\"를 입력해주세요", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
            }
        }, 500); //딜레이 타임 조절
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        });
    }

    public void chat6() {
        chatsmodalArrayList.add(new Chatsmodal(et_msg.getText().toString(), USER_KEY));
        chatAdapter.notifyDataSetChanged();
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setMy_chat(et_msg.getText().toString());
        getResponse3(chatRequest);
        et_msg.setText(null);
    }

    public void chat7() {
        chatsmodalArrayList.add(new Chatsmodal(et_msg.getText().toString(), USER_KEY));
        chatAdapter.notifyDataSetChanged();
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setMy_chat(et_msg.getText().toString());
        getResponse4(chatRequest);
        et_msg.setText(null);
    }

    public void question() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatsmodalArrayList.add(new Chatsmodal("어떤게 궁금하신가요? 궁금한점이 없으시면 \"아니\"를 입력해주세요", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
            }
        }, 1000); //딜레이 타임 조절
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        });
    }

}
