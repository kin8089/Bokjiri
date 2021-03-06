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
                    Toast.makeText(ChatActivity.this, "???????????? ???????????????", Toast.LENGTH_SHORT).show();
                    return;
                } else if (et_msg.getText().toString().equals("1")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("2")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("3")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("1???")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("2???")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("3???")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("??? ??????")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("??? ??????")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("??? ??????")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("??????")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("??????")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("?????????")) {
                    chat2();
                } else if (et_msg.getText().toString().equals("??????????????????")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("??????????????????")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("???")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("???")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("?????????")) {
                    chat5();
                } else if (et_msg.getText().toString().equals("?????????")) {
                    chat5();
                } else if (et_msg.getText().toString().equals("??????")) {
                    chat5();
                } else if (et_msg.getText().toString().equals("????????????")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("???????????????????????????")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("??????????????????")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("????????????")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chatsmodalArrayList.add(new Chatsmodal("????????????", USER_KEY));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                        }
                    }, 1000); //????????? ?????? ??????
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
                            chatsmodalArrayList.add(new Chatsmodal("?????? ???????????? ???????????????????\n????????? ?????????????????? \"??????\"??? ??????????????????", BOT_KEY));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                        }
                    }, 2000); //????????? ?????? ??????
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        }
                    });
                } else if (et_msg.getText().toString().equals("???????????? ???????????????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("????????????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("??????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("?????????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("?????????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("????????????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("????????????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("????????? ???????????? ???????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("????????? ???????????? ???????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("???????????? ????????????")) {
                    chat4();
                } else if (et_msg.getText().toString().equals("?????? ????????? ??? ???????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("????????? ??? ????????? ??? ???????")) {
                    chat6();
                } else if (et_msg.getText().toString().equals("????????? ????????? ???????????????")) {
                    chat7();
                } else if (et_msg.getText().toString().equals("?????????")) {
                    chat7();
                } else if (et_msg.getText().toString().equals("????????? ???????")) {
                    chat7();
                } else if (et_msg.getText().toString().equals("??????")) {
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
                chatsmodalArrayList.add(new Chatsmodal("?????? ????????? ???????????????????", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("?????? ????????? ???????????????????", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }, 1000); //????????? ?????? ??????

//        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getContext().getPackageName());
//        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
//        mRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
//        mRecognizer.setRecognitionListener(listener);

        // ????????? ??????
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        // xml??? ????????? ????????? ??? ??????

        // RecognizerIntent ?????? ??????
        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        // ????????? ?????? ????????? - ????????? Context??? listener??? ????????? ??? ??????
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
                    Toast.makeText(ChatActivity.this, "??????????????? ?????? ?????????", Toast.LENGTH_SHORT).show();
                } else {
                    tts = new TextToSpeech(ChatActivity.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != android.speech.tts.TextToSpeech.ERROR) {
                                tts.setLanguage(Locale.KOREAN);
                            }
                        }
                    });
                    Toast.makeText(ChatActivity.this, "??????????????? ????????? ?????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void getResponse(ChatRequest chatRequest) {
        chatAdapter.notifyDataSetChanged();
        String token = PreferenceManager.getString(mContext, "key");
        String authToken = "Bearer " + token;
//        System.out.println("Token ?????? ?????????: " + authToken);
//        System.out.println("message ?????? ?????????: " + chatRequest.getMy_chat());
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
                            chatsmodalArrayList.add(new Chatsmodal("???????????? ???????????? ??????????????? ??????????????? \"??????\"??? ??????????????????", BOT_KEY));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
//                            if (tts != null) {
//                                tts.setPitch(1.0f);
//                                tts.setSpeechRate(1.0f);
//                                tts.speak("???????????? ????????? ????????? ????????? ??????????????????", TextToSpeech.QUEUE_FLUSH, null);
//                            }
                        }
                    }, 1000); //????????? ?????? ??????
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
                chatsmodalArrayList.add(new Chatsmodal("????????? ????????????", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("????????? ????????????", TextToSpeech.QUEUE_FLUSH, null);
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
//        System.out.println("Token ?????? ?????????: " + authToken);
//        System.out.println("message ?????? ?????????: " + chatRequest.getMy_chat());
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

//                    if (!response.body().getMessage().equals("?????????????????? ??????????????????")) {
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                chatsmodalArrayList.add(new Chatsmodal("?????? ????????? ???????????? \"????????????\"??? ??????????????????", BOT_KEY));
//                                recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
//                                chatAdapter.notifyDataSetChanged();
////                            if (tts != null) {
////                                tts.setPitch(1.0f);
////                                tts.setSpeechRate(1.0f);
////                                tts.speak("??????????????? ?????? ????????? ???????????? ????????? ??????????????????", TextToSpeech.QUEUE_FLUSH, null);
////                            }
//                            }
//                        }, 1000); //????????? ?????? ??????
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
                chatsmodalArrayList.add(new Chatsmodal("????????? ????????????", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("????????? ????????????", TextToSpeech.QUEUE_FLUSH, null);

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
//        System.out.println("Token ?????? ?????????: " + authToken);
//        System.out.println("message ?????? ?????????: " + chatRequest.getMy_chat());
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
                chatsmodalArrayList.add(new Chatsmodal("????????? ????????????", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("????????? ????????????", TextToSpeech.QUEUE_FLUSH, null);
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
//        System.out.println("Token ?????? ?????????: " + authToken);
//        System.out.println("message ?????? ?????????: " + chatRequest.getMy_chat());
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
                chatsmodalArrayList.add(new Chatsmodal("????????? ????????????", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                if (tts != null) {
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                    tts.speak("????????? ????????????", TextToSpeech.QUEUE_FLUSH, null);
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
            Toast.makeText(this, "?????? ??? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
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
                    message = "????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "??????????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "???????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "????????? ????????????";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "???????????? ????????? ????????????";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "????????? ??????????????????";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "????????? ????????????";
                    break;
                default:
                    message = "??? ??? ?????? ??????";
                    break;
            }

            Toast.makeText(getApplicationContext(), "????????? ????????????????????? : " + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // ?????? ?????? ArrayList??? ????????? ?????? textView??? ????????? ???????????????.
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
                chatsmodalArrayList.add(new Chatsmodal("???????????? ???????????? ???????????????????\n????????? ?????????????????? \"??????\"??? ??????????????????", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
            }
        }, 500); //????????? ?????? ??????
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
                chatsmodalArrayList.add(new Chatsmodal("????????? ??????????????????? ??????????????? ???????????? \"??????\"??? ??????????????????", BOT_KEY));
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
            }
        }, 1000); //????????? ?????? ??????
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        });
    }

}
