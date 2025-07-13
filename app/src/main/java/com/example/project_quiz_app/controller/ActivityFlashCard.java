package com.example.project_quiz_app.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_quiz_app.R;

import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.Flashcard;
import com.example.project_quiz_app.model.FlashcardItem;

import com.example.project_quiz_app.view.adapters.CreateFlashcardAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class ActivityFlashCard extends AppCompatActivity {
    private AppDatabase db;
    private RecyclerView rcFlashcard;
    private Button btnAI;
    private List<FlashcardItem> flashcardItems = new ArrayList<>();
    private CreateFlashcardAdapter adapter;
    private String categoryName, categoryDescription;
    private int userId;

    private TextView textAI; // Thêm vào khai báo biến

    private String apiKey = "your_api_key";
    private void bindingView() {
        rcFlashcard = findViewById(R.id.recyclerView);
        btnAI = findViewById(R.id.btnAI);
        db = AppDatabase.getInstance(this);
        // Lấy thông tin từ sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userIdStr = sharedPreferences.getString("user_id", null);
        if (userIdStr == null) {
            return;
        }
        userId = Integer.parseInt(userIdStr);
    }

    private void bindingAction() {
        getDataFromIntent();
        btnAI.setOnClickListener(this::showAIBottomSheet);
    }

    private void initRecyclerView() {
        adapter = new CreateFlashcardAdapter(flashcardItems);
        rcFlashcard.setAdapter(adapter);
        rcFlashcard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
    // Implement callback methods
//    @Override
//    public void onFlashcardFlipped(int flashcardId) {
//        // Lưu bản ghi review khi flip thẻ
//        insertFlashcardReview(flashcardId, "viewed", false);
//    }
//
//    @Override
//    public void onFlashcardStarred(int flashcardId) {
//        // Lưu bản ghi review khi star thẻ
//        insertFlashcardReview(flashcardId, "starred", true);
//    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("category_id", -1);
        categoryName= intent.getStringExtra("category_name");
        categoryDescription= intent.getStringExtra("category_description");


        new Thread(() -> {
            // Lấy dữ liệu từ DB
            List<Flashcard> flashcards = db.flashcardDao().getFlashcardsByCategoryId(id);

            // Chuyển đổi sang FlashcardItem
            List<FlashcardItem> items = new ArrayList<>();
            for (Flashcard flashcard : flashcards) {
                items.add(new FlashcardItem( flashcard.getId(), flashcard.getTerm(), flashcard.getDefinition()));
            }

            // Cập nhật UI
            runOnUiThread(() -> {
                flashcardItems.clear();
                flashcardItems.addAll(items);
                initRecyclerView();
            });
        }).start();
    }

//    private void showAIBottomSheet(View v) {
//        BottomSheetDialog dialog = new BottomSheetDialog(this);
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_ai_bottom_sheet, null);
//        TextView textAI = view.findViewById(R.id.textAI);
//        dialog.setContentView(view);
//
//        // lay data tu recyclerview
//        // Lấy vị trí đầu tiên đang hiển thị
//        LinearLayoutManager layoutManager = (LinearLayoutManager) rcFlashcard.getLayoutManager();
//        int position = layoutManager.findFirstVisibleItemPosition();  // hoặc lastVisibleItemPosition()
//
//        // Lấy data từ adapter
//        FlashcardItem item = adapter.getItemByPosition(position);
//        // Ví dụ: hiển thị log
//        Log.d("FlashcardData", "Term: " + item.getTerm() + ", Definition: " + item.getDefinition() + "\n Title: " + categoryName + "description: " + categoryDescription);
//        String meessageforAI = "Đây là 1 flashcard với term: "
//                + item.getTerm() + " definition: " + item.getDefinition()
//                + "\n Title: " + categoryName
//                + "\n description: " + categoryDescription;
//
//        callGeminiAPI(meessageforAI);
//        // Thiết lập chiều cao 70% màn hình
//        View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
//        if (bottomSheet != null) {
//            bottomSheet.getLayoutParams().height = (int) (getResources().getDisplayMetrics().heightPixels * 0.7);
//        }
//        String res =  callGeminiAPI(meessageforAI).toString();
//        textAI.setText(res);
//
//        dialog.show();
//    }
//    // call API ai
//    public void callGeminiAPI(String message) {
//        OkHttpClient client = new OkHttpClient();
//
//        String apiKey = "YOUR_API_KEY";
//        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;
//
//        // Body JSON
//        String json = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + message + "\" }] }] }";
//
//        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String responseData = response.body().string();
//                    Log.d("GeminiAPI", responseData);
//                    // TODO: Parse JSON và cập nhật UI
//                } else {
//                    Log.e("GeminiAPI", "Error: " + response.code());
//                }
//            }
//        });
//    }

    // Sửa lại hàm showAIBottomSheet
    private void showAIBottomSheet(View v) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_ai_bottom_sheet, null);
        dialog.setContentView(view);

        // Binding TextView từ dialog layout
        textAI = view.findViewById(R.id.textAI); // Thay R.id.textAI bằng ID thực tế

        // lay data tu recyclerview
        LinearLayoutManager layoutManager = (LinearLayoutManager) rcFlashcard.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();

        // Kiểm tra position hợp lệ
        if (position != RecyclerView.NO_POSITION && position < flashcardItems.size()) {
            FlashcardItem item = adapter.getItemByPosition(position);
            Log.d("FlashcardData", "Term: " + item.getTerm() + ", Definition: " + item.getDefinition() +
                    "\n Title: " + categoryName + " description: " + categoryDescription);

            String messageForAI = "Đây là 1 flashcard với term: "
                    + item.getTerm() + " definition: " + item.getDefinition()
                    + "\n Title: " + categoryName
                    + "\n description: " + categoryDescription
                    + "\n Hãy tạo ra 1 câu hỏi trắc nghiệm với 4 đáp án A, B, C, D để kiểm tra hiểu biết về flashcard này.";

            // Hiển thị loading text
            textAI.setText("Đang tạo câu hỏi...");

            // Gọi API (chỉ 1 lần)
            callGeminiAPI(messageForAI, textAI);
        }

        // Thiết lập chiều cao 70% màn hình
        View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            bottomSheet.getLayoutParams().height = (int) (getResources().getDisplayMetrics().heightPixels * 0.7);
        }

        dialog.show();
    }
    // Hàm callGeminiAPI đã sửa với URL đúng
    // Hàm parse JSON response từ Gemini API - ĐÃ SỬA
    private String parseGeminiResponse(String jsonResponse) {
        try {
            Log.d("GeminiAPI", "Parsing response: " + jsonResponse);

            // Gemini API trả về format:
            // {"candidates":[{"content":{"parts":[{"text":"response text here"}]}}]}

            // Tìm "candidates" array
            int candidatesStart = jsonResponse.indexOf("\"candidates\":");
            if (candidatesStart == -1) {
                Log.e("GeminiAPI", "No candidates found in response");
                return "Không tìm thấy candidates trong response";
            }

            // Tìm "content" object
            int contentStart = jsonResponse.indexOf("\"content\":", candidatesStart);
            if (contentStart == -1) {
                Log.e("GeminiAPI", "No content found in response");
                return "Không tìm thấy content trong response";
            }

            // Tìm "parts" array
            int partsStart = jsonResponse.indexOf("\"parts\":", contentStart);
            if (partsStart == -1) {
                Log.e("GeminiAPI", "No parts found in response");
                return "Không tìm thấy parts trong response";
            }

            // Tìm "text" field
            int textStart = jsonResponse.indexOf("\"text\":", partsStart);
            if (textStart == -1) {
                Log.e("GeminiAPI", "No text found in response");
                return "Không tìm thấy text trong response";
            }

            // Tìm vị trí bắt đầu của text content
            int textContentStart = jsonResponse.indexOf("\"", textStart + 7) + 1;

            // Tìm vị trí kết thúc của text content (dấu " kế tiếp, không phải escaped)
            int textContentEnd = textContentStart;
            while (textContentEnd < jsonResponse.length()) {
                char c = jsonResponse.charAt(textContentEnd);
                if (c == '"' && jsonResponse.charAt(textContentEnd - 1) != '\\') {
                    break;
                }
                textContentEnd++;
            }

            if (textContentStart > 0 && textContentEnd > textContentStart) {
                String text = jsonResponse.substring(textContentStart, textContentEnd);

                // Unescape JSON characters
                text = text.replace("\\n", "\n")
                        .replace("\\r", "\r")
                        .replace("\\t", "\t")
                        .replace("\\\"", "\"")
                        .replace("\\\\", "\\");

                Log.d("GeminiAPI", "Extracted text: " + text);
                return text;
            }

            return "Không thể trích xuất text từ response";

        } catch (Exception e) {
            Log.e("GeminiAPI", "Error parsing response", e);
            return "Lỗi khi xử lý response: " + e.getMessage();
        }
    }

    // Phiên bản đơn giản hơn sử dụng regex (nếu cần)
    private String parseGeminiResponseSimple(String jsonResponse) {
        try {
            Log.d("GeminiAPI", "Parsing response: " + jsonResponse);

            // Sử dụng regex để tìm text
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\"text\"\\s*:\\s*\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"");
            java.util.regex.Matcher matcher = pattern.matcher(jsonResponse);

            if (matcher.find()) {
                String text = matcher.group(1);
                // Unescape JSON characters
                text = text.replace("\\n", "\n")
                        .replace("\\r", "\r")
                        .replace("\\t", "\t")
                        .replace("\\\"", "\"")
                        .replace("\\\\", "\\");

                Log.d("GeminiAPI", "Extracted text: " + text);
                return text;
            }

            return "Không tìm thấy text trong response";

        } catch (Exception e) {
            Log.e("GeminiAPI", "Error parsing response", e);
            return "Lỗi khi xử lý response: " + e.getMessage();
        }
    }

    // Phiên bản sử dụng JSONObject (khuyến nghị nhất)
    private String parseGeminiResponseWithJSON(String jsonResponse) {
        try {
            Log.d("GeminiAPI", "Parsing response: " + jsonResponse);

            // Sử dụng JSONObject có sẵn trong Android
            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse);

            // Lấy candidates array
            org.json.JSONArray candidates = jsonObject.getJSONArray("candidates");
            if (candidates.length() == 0) {
                return "Không có candidates trong response";
            }

            // Lấy candidate đầu tiên
            org.json.JSONObject candidate = candidates.getJSONObject(0);

            // Lấy content
            org.json.JSONObject content = candidate.getJSONObject("content");

            // Lấy parts array
            org.json.JSONArray parts = content.getJSONArray("parts");
            if (parts.length() == 0) {
                return "Không có parts trong response";
            }

            // Lấy text từ part đầu tiên
            org.json.JSONObject part = parts.getJSONObject(0);
            String text = part.getString("text");

            Log.d("GeminiAPI", "Extracted text: " + text);
            return text;

        } catch (Exception e) {
            Log.e("GeminiAPI", "Error parsing JSON response", e);
            return "Lỗi khi parse JSON: " + e.getMessage();
        }
    }

    // Cập nhật hàm callGeminiAPI để sử dụng parser tốt hơn
    public void callGeminiAPI(String message, TextView textView) {
        OkHttpClient client = new OkHttpClient();


        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        String escapedMessage = message.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

        String json = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + escapedMessage + "\" }] }] }";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("GeminiAPI", "Request failed", e);
                runOnUiThread(() -> {
                    textView.setText("Lỗi kết nối: " + e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    Log.d("GeminiAPI", "Response Code: " + response.code());
                    Log.d("GeminiAPI", "Response Body: " + responseData);

                    if (response.isSuccessful()) {
                        // Thử parser JSONObject trước (tốt nhất)
                        String aiResponse = parseGeminiResponseWithJSON(responseData);

                        // Nếu lỗi thì thử parser đơn giản
                        if (aiResponse.startsWith("Lỗi") || aiResponse.startsWith("Không")) {
                            aiResponse = parseGeminiResponse(responseData);
                        }

                        final String finalResponse = aiResponse;
                        runOnUiThread(() -> {
                            textView.setText(finalResponse);
                        });
                    } else {
                        Log.e("GeminiAPI", "Error: " + response.code() + " - " + response.message());
                        runOnUiThread(() -> {
                            textView.setText("Lỗi API " + response.code() + ": " + responseData);
                        });
                    }
                } finally {
                    if (response.body() != null) {
                        response.body().close();
                    }
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flash_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}
