package com.example.managersystem.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * @Author: wusen
 * @Date: 2024/5/1 10:33
 * @Description: 自定义响应传递
 */
public class ResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream capture;
    private ServletOutputStream output;
    private PrintWriter writer;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        capture = new ByteArrayOutputStream(response.getBufferSize());
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (output == null) {
            output = new ServletOutputStream() {
                @Override
                public void write(int b) {
                    capture.write(b);
                }

                @Override
                public void flush() throws IOException {
                    capture.flush();
                }

                @Override
                public void close() throws IOException {
                    capture.close();
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                    // Implement as needed for asynchronous IO
                }
            };
        }
        return output;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (output != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(capture, getCharacterEncoding()), true);
        }
        return writer;
    }

    public byte[] getCaptureAsBytes() {
        return capture.toByteArray();
    }

    public String getCaptureAsString(HttpServletResponse response) throws IOException {
        // 确保响应内容被写回到原始的响应对象中
        ServletOutputStream out = response.getOutputStream();
        out.write(getCaptureAsBytes());
        out.flush();
        out.close();
        return capture.toString(getCharacterEncoding());
    }
}
