package com.applications.common.dataconvert;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 对于ajax请求，如果方法返回类型为byte，其方法只能用于返回json格式
 */
public class ByteArrayMessageConverter implements HttpMessageConverter<byte[]> {

    private List<MediaType> supportedMediaTypes = Collections.emptyList();
    private MediaType transferMediaType;

    public ByteArrayMessageConverter() {
        this(new MediaType("application", "octet-stream"), MediaType.ALL);
        Map<String, String> param = new HashMap<String, String>();
        param.put("charset", "utf-8");
        transferMediaType = new MediaType("application", "json", param);
    }

    protected ByteArrayMessageConverter(MediaType... supportedMediaTypes) {
        setSupportedMediaTypes(Arrays.asList(supportedMediaTypes));
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return supports(clazz) && canRead(mediaType);
    }

    protected boolean canRead(MediaType mediaType) {
        if (mediaType == null) {
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.includes(mediaType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return supports(clazz) && canWrite(mediaType);
    }

    protected boolean canWrite(MediaType mediaType) {
        if (mediaType == null || MediaType.ALL.equals(mediaType)) {
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.isCompatibleWith(mediaType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.unmodifiableList(this.supportedMediaTypes);
    }

    @Override
    public byte[] read(Class<? extends byte[]> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        long contentLength = inputMessage.getHeaders().getContentLength();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(contentLength >= 0 ? (int) contentLength : StreamUtils.BUFFER_SIZE);
        StreamUtils.copy(inputMessage.getBody(), bos);
        return bos.toByteArray();
    }

    @Override
    public void write(byte[] t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpHeaders headers = outputMessage.getHeaders();
        if (headers.getContentType() == null) {
            if (contentType == null || contentType.isWildcardType() || contentType.isWildcardSubtype()) {
                contentType = getDefaultContentType(t);
            }
            if (contentType != null && isTransferMediaType(contentType)) {
                headers.setContentType(contentType);
            } else {
                headers.setContentType(transferMediaType);
            }
        }
        if (headers.getContentLength() == -1) {
            Long contentLength = getContentLength(t, headers.getContentType());
            if (contentLength != null) {
                headers.setContentLength(contentLength);
            }
        }
        StreamUtils.copy(t, outputMessage.getBody());
        outputMessage.getBody().flush();

    }

    protected boolean isTransferMediaType(MediaType contentType) {
        if (contentType == null) {
            return true;
        }
        if ("image".equals(contentType.getType())) {
            return false;
        }
        return true;
    }

    protected MediaType getDefaultContentType(byte[] t) throws IOException {
        List<MediaType> mediaTypes = getSupportedMediaTypes();
        return (!mediaTypes.isEmpty() ? mediaTypes.get(0) : null);
    }

    protected Long getContentLength(byte[] bytes, MediaType contentType) {
        return (long) bytes.length;
    }

    protected boolean supports(Class<?> clazz) {
        return byte[].class.equals(clazz);
    }

    public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
        Assert.notEmpty(supportedMediaTypes, "'supportedMediaTypes' must not be empty");
        this.supportedMediaTypes = new ArrayList<MediaType>(supportedMediaTypes);
    }

}
