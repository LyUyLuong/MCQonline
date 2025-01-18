package com.javaweb.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class UploadFileUtils {

    public void writeOrUpdate(String path, byte[] bytes) {
        File file = new File(StringUtils.substringBeforeLast(path, "/"));
        if (!file.exists() && !file.mkdirs()) {
            throw new RuntimeException("Không thể tạo thư mục: " + file.getPath());
        }

        try (FileOutputStream fop = new FileOutputStream(path)) {
            fop.write(bytes);
            System.out.println("Ghi file thành công: " + path);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi file: " + path, e);
        }
    }

}
