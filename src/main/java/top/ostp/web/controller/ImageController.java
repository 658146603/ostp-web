package top.ostp.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@NoAuthority
@Controller(value = "/image")
public class ImageController {
    @SuppressWarnings("ResultOfMethodCallIgnored")
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
            e.printStackTrace();
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
}
