package io.hiwepy.cloud.oss.core;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SysDataFileName {

    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static AtomicInteger _staticIncrement = new AtomicInteger(new Random().nextInt());

    //8+8+8+4
    //fileId+appId+timestamp+increment
    public int increment;//
    private int timestamp;//文件存储时间秒
    private int appId;//应用Id
    private long fileId;//文件Id--app唯一

    public int getIncrement() {
        return increment;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getAppId() {
        return appId;
    }

    public long getFileId() {
        return fileId;
    }


    public SysDataFileName(byte[] bytes) {
        fromByteArray(bytes);
    }

    public SysDataFileName(int appId, long fileId) {
        this.fileId = fileId;
        this.appId = appId;
        this.timestamp = getCurTimestamp();
        this.increment = getCurIncrement();
    }

    public SysDataFileName(int timestamp, int appId, long fileId) {
        this.fileId = fileId;
        this.appId = appId;
        this.timestamp = timestamp;
        this.increment = getCurIncrement();
    }

    private void fromByteArray(byte[] bytes) {
        if (bytes == null || bytes.length < 20)
            throw new IllegalArgumentException("bytes");
        //fileId+appId+timestamp+increment
        this.fileId = toLong(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
        this.appId = toInt(bytes[8], bytes[9], bytes[10], bytes[11]);
        this.timestamp = toInt(bytes[12], bytes[13], bytes[14], bytes[15]);
        this.increment = toInt(bytes[16], bytes[17], bytes[18], bytes[19]);
    }

    private byte[] toByteArray() {
        //fileId+appId+timestamp+increment
        //8+4+4+4
        byte[] bytes = new byte[20];

        bytes[0] = long0(this.fileId);
        bytes[1] = long1(this.fileId);
        bytes[2] = long2(this.fileId);
        bytes[3] = long3(this.fileId);
        bytes[4] = long4(this.fileId);
        bytes[5] = long5(this.fileId);
        bytes[6] = long6(this.fileId);
        bytes[7] = long7(this.fileId);

        bytes[8] = int0(this.appId);
        bytes[9] = int1(this.appId);
        bytes[10] = int2(this.appId);
        bytes[11] = int3(this.appId);

        bytes[12] = int0(this.timestamp);
        bytes[13] = int1(this.timestamp);
        bytes[14] = int2(this.timestamp);
        bytes[15] = int3(this.timestamp);

        bytes[16] = int0(this.increment);
        bytes[17] = int1(this.increment);
        bytes[18] = int2(this.increment);
        bytes[19] = int3(this.increment);

        return bytes;
    }

    private static int getCurIncrement() {
        int l = _staticIncrement.incrementAndGet() & 0x00ffffff;
        return l;
    }

    private static int getCurTimestamp() {
        return (int) (new Date().getTime() / 1000);
    }

    private static int toInt(byte b, byte b1, byte b2, byte b3) {
        return (b & 0xff)
                + ((b1 & 0xff) << 8)
                + ((b2 & 0xff) << 16)
                + ((b3 & 0xff) << 24);
    }

    private static long toLong(byte b, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7) {
        return (0xffL & (long) b)
                + (0xff00L & ((long) b1 << 8))
                + (0xff0000L & ((long) b2 << 16))
                + (0xff000000L & ((long) b3 << 24))
                + (0xff00000000L & ((long) b4 << 32))
                + (0xff0000000000L & ((long) b5 << 40))
                + (0xff000000000000L & ((long) b6 << 48))
                + (0xff00000000000000L & ((long) b7 << 56));

    }

    private static byte int3(int i) {
        return (byte) ((i >> 24) & 0xff);
    }

    private static byte int2(int i) {
        return (byte) ((i >> 16) & 0xff);
    }

    private static byte int1(int i) {
        return (byte) ((i >> 8) & 0xff);
    }

    private static byte int0(int i) {
        return (byte) (i & 0xff);
    }

    private static byte long7(long i) {
        return (byte) ((i >> 56) & 0xff);
    }

    private static byte long6(long i) {
        return (byte) ((i >> 48) & 0xff);
    }

    private static byte long5(long i) {
        return (byte) ((i >> 40) & 0xff);
    }

    private static byte long4(long i) {
        return (byte) ((i >> 32) & 0xff);
    }

    private static byte long3(long i) {
        return (byte) ((i >> 24) & 0xff);
    }

    private static byte long2(long i) {
        return (byte) ((i >> 16) & 0xff);
    }

    private static byte long1(long i) {
        return (byte) ((i >> 8) & 0xff);
    }

    private static byte long0(long i) {
        return (byte) (i & 0xff);
    }

    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int len = data.length;
        char[] out = new char[len << 1];
        int i = 0;

        for (int var5 = 0; i < len; ++i) {
            out[var5++] = toDigits[(240 & data[i]) >>> 4];
            out[var5++] = toDigits[15 & data[i]];
        }

        return out;
    }

    private static String encodeHexStr(byte[] data) {
        return new String(encodeHex(data, DIGITS_LOWER));
    }

    private static byte[] decodeHex(CharSequence hexData) {
        if (hexData == null || hexData.length() == 0) {
            return null;
        } else {
            int len = hexData.length();
            if ((len & 1) != 0) {
                hexData = "0" + hexData;
                len = hexData.length();
            }

            byte[] out = new byte[len >> 1];
            int i = 0;

            for (int j = 0; j < len; ++i) {
                int f = toDigit(hexData.charAt(j), j) << 4;
                ++j;
                f |= toDigit(hexData.charAt(j), j);
                ++j;
                out[i] = (byte) (f & 255);
            }

            return out;
        }
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit < 0) {
            throw new IllegalArgumentException("Illegal hexadecimal character " + String.valueOf(ch) + " at index " + String.valueOf(index));
        } else {
            return digit;
        }
    }

    @Override
    public String toString() {
        byte[] bytes = this.toByteArray();
        return encodeHexStr(bytes);
    }

    public static SysDataFileName parse(String s) {
        byte[] bytes = decodeHex(s);
        return new SysDataFileName(bytes);
    }
}

