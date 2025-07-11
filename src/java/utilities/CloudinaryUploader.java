/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.io.File;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class CloudinaryUploader {

    private static final String CLOUD_NAME = "dlevje6nq";
    private static final String API_KEY = "532963552644636";
    private static final String API_SECRET = "sBhbk34GT9XFuSiGMW-mn8OgLG8";

    public static String uploadFile(File file) throws IOException {
        String url = "https://api.cloudinary.com/v1_1/" + CLOUD_NAME + "/auto/upload";
        String boundary = Long.toHexString(System.currentTimeMillis());
        String CRLF = "\r\n";

        // Tạo thông tin bảo mật
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String folder = "EPMS"; // dùng lại sau
        String toSign = "folder=" + folder + "&timestamp=" + timestamp;
        String signature = sha1(toSign + API_SECRET);

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (
                OutputStream output = conn.getOutputStream(); PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true)) {
            // api_key
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"api_key\"").append(CRLF).append(CRLF).append(API_KEY).append(CRLF);

            // timestamp
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"timestamp\"").append(CRLF).append(CRLF).append(timestamp).append(CRLF);

            // signature
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"signature\"").append(CRLF).append(CRLF).append(signature).append(CRLF);

            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"folder\"").append(CRLF).append(CRLF);
            writer.append(folder).append(CRLF); // dùng biến đã định nghĩa

            // file
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"").append(CRLF);
            writer.append("Content-Type: application/octet-stream").append(CRLF).append(CRLF).flush();
            Files.copy(file.toPath(), output);
            output.flush();
            writer.append(CRLF).flush();

            // End
            writer.append("--").append(boundary).append("--").append(CRLF).flush();
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            String json = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            return extractUrl(json);
        } else {
            throw new IOException("Upload failed with HTTP code " + responseCode);
        }
    }

    private static String extractUrl(String json) {
        int start = json.indexOf("\"secure_url\":\"") + 14;
        int end = json.indexOf("\"", start);
        return json.substring(start, end).replace("\\/", "/");
    }

    private static String sha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] result = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA1 failed", e);
        }
    }

    public static void main(String[] args) {
        File file = new File("D:\\Demo.txt");  // Đường dẫn file bạn muốn test
        try {
            String uploadedUrl = uploadFile(file);
            System.out.println("Upload thành công:");
            System.out.println(uploadedUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
