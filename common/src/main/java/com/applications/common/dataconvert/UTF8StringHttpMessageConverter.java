package com.applications.common.dataconvert;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * 重写StringHttpMessageConverter，解决@ResponseBody的乱码问题
 */
public class UTF8StringHttpMessageConverter extends StringHttpMessageConverter {
    private static final MediaType utf8 = new MediaType("text", "plain", Charset.forName("UTF-8"));
    public static final Charset DEFAULT_CHARSET = utf8.getCharSet();
    
    private boolean writeAcceptCharset = true;

    public UTF8StringHttpMessageConverter() {
        super(DEFAULT_CHARSET);
    }

    @Override
    protected MediaType getDefaultContentType(String dumy) {
        return utf8;
    }

    protected List<Charset> getAcceptedCharsets() {
        return Arrays.asList(utf8.getCharSet());
    }

    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
        if (this.writeAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
        }
        Charset charset = utf8.getCharSet();
        FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(), charset));
    }

    public boolean isWriteAcceptCharset() {
        return writeAcceptCharset;
    }

    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        this.writeAcceptCharset = writeAcceptCharset;
    }

}
