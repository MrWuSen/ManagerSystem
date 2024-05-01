package com.example.managersystem.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.managersystem.dto.UserResource;
import com.example.managersystem.exception.CustomBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: wusen
 * @Date: 2024/4/30 14:17
 * @Description: 文件操作
 */
@Slf4j
public class FileOperateUtils {
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final Path FILE_PATH = Paths.get("src/main/resources/temp.txt");

    /**
     * 逐行读取文件内容
     */
    public static UserResource readLines(String userId) {
        UserResource userResource = null;
        rwLock.readLock().lock();
        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH)) {
            String line;
            int currentLine = 0;
            while ((line = reader.readLine()) != null) {
                currentLine++;
                userResource = JSONObject.parseObject(line, UserResource.class);
                if (userId.equals(userResource.getUserId())) {
                    userResource.setUserLineNo(currentLine);
                    break;
                } else {
                    userResource = null;
                }
            }
        } catch (IOException e) {
            log.error("Error occurred while reading the file: ", e);
            throw new CustomBusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        } finally {
            rwLock.readLock().unlock();
        }
        return userResource;
    }

    /**
     * 大文件内容修改
     *
     * @param lineNumber
     * @param newContent
     */
    public static void modifyLine(int lineNumber, String newContent) {
        rwLock.writeLock().lock();
        Path tempFile = Paths.get(FILE_PATH.toString() + ".tmp");
        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH);
             BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            String line;
            int currentLine = 0;
            while ((line = reader.readLine()) != null) {
                currentLine++;
                if (currentLine == lineNumber) {
                    // 写入新内容替换旧内容
                    writer.write(newContent);
                } else {
                    // 写入原始内容
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("Error occurred while modifying the line: {}", e.getMessage());
            throw new CustomBusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        } finally {
            try {
                // 删除原文件
                Files.delete(FILE_PATH);
                // 将临时文件重命名为原文件名
                Files.move(tempFile, FILE_PATH);
            } catch (IOException e) {
                log.error("Error occurred while replacing the file: {}", e.getMessage());
            }
            rwLock.writeLock().unlock();
        }
    }

    /**
     * 向文件中追加内容
     *
     * @param lines
     */
    public static void appendLines(Iterable<String> lines) {
        rwLock.writeLock().lock();
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardOpenOption.APPEND)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("Error occurred while appending the file: {}", e.getMessage());
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
