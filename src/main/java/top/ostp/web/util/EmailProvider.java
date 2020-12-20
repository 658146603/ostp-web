package top.ostp.web.util;

import okhttp3.*;

import java.io.IOException;

public class EmailProvider {
    public static boolean resetPassword(String link, String to) {
        String url = "https://mail.otsp.top/mail/reset";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("link", link)
                .add("to", to)
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return (response.code() == 200);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
