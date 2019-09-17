package com.hewentian.entity;

/**
 * <p>
 * <b>Location</b> 是 测试protostuff序列化和反序列化使用
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2019-03-11 21:49:42
 * @since JDK 1.8
 */
public class Location {
    private String x;
    private String y;

    public Location() {
    }

    public Location(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
