package contact;

import okhttp3.*;

import java.io.IOException;

public class HttpClient {

    private static final String base_url = "https://52kaoyan.top/";

    private static final String token = "a7c0c46641e49245b9bc828b78555057";

    private static final OkHttpClient client = new OkHttpClient();

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    // GET 请求
    public static String get(String url) {
        Request request = new Request.Builder()
                .url(base_url+url)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // POST 请求，传入 JSON 数据
    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }
}
