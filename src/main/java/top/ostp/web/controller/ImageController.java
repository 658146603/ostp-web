package top.ostp.web.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Component
@NoAuthority
@Controller(value = "/image")
public class ImageController {


    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            System.out.printf("IO异常：%s%n", e.getMessage());
            throw e;
        } finally {
            if (null != bos) {
                bos.close();
            }
        }
        return bos.toByteArray();
    }

    @RequestMapping(value = "/image/get/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImageById(@PathVariable String id) throws IOException {
        return getImage(id);
    }

    @RequestMapping(value = "/image/get", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(String id) throws IOException {
        id = new String(Base64.getEncoder().encode(id.getBytes(StandardCharsets.UTF_8)));
        File file = new File("image/" + id);
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            inputStream = new FileInputStream("image/404.jpg");
        }
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    @PostMapping(value = "image/put")
    @ResponseBody
    public ApiResponse<Object> putImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Responses.fail("文件为空");
        }
        UUID uuid = UUID.randomUUID();
        Path path = Paths.get("image/" + new String(Base64.getEncoder().encode(uuid.toString().getBytes(StandardCharsets.UTF_8))));
        try {
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
            return Responses.fail("文件保存失败");
        }
        return Responses.ok(uuid);
    }

    @PostConstruct
    void init() {
        File file = new File("image/404.jpg");
        if (!file.exists()) {
            File path = new File("image");
            if (!path.exists()) {
                path.mkdirs();
            }
            Request request = new Request.Builder()
                    .url("https://boot.otsp.top/404.jpg")
                    .build();

            OkHttpClient client = new OkHttpClient();
            try {
                Response response = client.newCall(request).execute();
                FileOutputStream stream = new FileOutputStream(file);
                stream.write(readInputStream(Objects.requireNonNull(response.body()).byteStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
