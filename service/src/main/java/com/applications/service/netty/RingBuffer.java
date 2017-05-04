package com.applications.service.netty;

/**
 * @author hukaisheng
 * @date 2017/4/27.
 *
 * 以无锁的方式进行线程安全的buffer的读写操作
 * 进行读操作的时候，我们只修改head的值，而在写操作的时候我们只修改tail的值
 */
public class RingBuffer {

    private final static int bufferSize = 1024;
    private String[] buffer = new String[bufferSize];
    private int head = 0;
    private int tail = 0;

    private Boolean empty() {
        return head == tail;
    }

    private Boolean full() {
        return (tail + 1) % bufferSize == head;
    }

    /**
     * 写入内容到buffer之后才修改tail的值
     * @param v
     * @return
     */
    public Boolean put(String v) {
        if (full()) {
            return false;
        }
        buffer[tail] = v;
        tail = (tail + 1) % bufferSize;
        return true;
    }

    /**
     * 进行读操作的时候，我们会读取tail的值并将其赋值给copyTail
     * @return
     */
    public String get() {
        if (empty()) {
            return null;
        }
        String result = buffer[head];
        head = (head + 1) % bufferSize;
        return result;
    }

    public String[] getAll() {
        if (empty()) {
            return new String[0];
        }
        int copyTail = tail;
        int cnt = head < copyTail ? copyTail - head : bufferSize - head + copyTail;
        String[] result = new String[cnt];
        if (head < copyTail) {
            for (int i = head; i < copyTail; i++) {
                result[i - head] = buffer[i];
            }
        } else {
            for (int i = head; i < bufferSize; i++) {
                result[i - head] = buffer[i];
            }
            for (int i = 0; i < copyTail; i++) {
                result[bufferSize - head + i] = buffer[i];
            }
        }
        head = copyTail;
        return result;
    }
}
