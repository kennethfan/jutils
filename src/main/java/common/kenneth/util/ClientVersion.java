package common.kenneth.util;

import java.io.Serializable;
import java.text.ParseException;

/**
 * 表示一个客户端的版本
 * 采用“类maven”的版本定义：<major version>.<minor version>.<incremental version>-<qualifier>
 * http://books.sonatype.com/mvnref-book/reference/pom-relationships-sect-pom-syntax.html#pom-reationships-sect-versions
 * Created by wangchao23 on 2015-12-16.
 */
public final class ClientVersion implements Serializable,Comparable<ClientVersion>{
    private int major;  //主版本号
    private int minor;  //次版本号
    private int incr;   //递增版本号
    private String qualifier;   //后缀标识

    /**
     * 采用字符串构建版本类
     * @param versionString
     * @throws Exception 如果字符串不合法则抛出受检异常
     */
    public ClientVersion(String versionString) throws ParseException{
        try {
            parseVersion(versionString);
        } catch (Exception e) {
            throw new ParseException("Parsing versionString failed. target = " + versionString, 0);
        }
    }

    /**
     * 采用字符串构建版本类
     * 无法解析目标字符串时，使用默认字符串替代
     *
     * @param versionString
     * @param defaultVersionString
     */
    public ClientVersion(String versionString, String defaultVersionString){
        try {
            parseVersion(versionString);
        } catch (Exception e) {
            try {
                parseVersion(defaultVersionString);
            } catch (Exception e1) {
                //如果默认字符串也解析失败，此时该对象初始化为{0,0,0,null}，不抛出异常
            }
        }
    }

    //没有用split，自己用indexOf貌似效率比正则高一点点
    private void parseVersion(String versionString){
        versionString = versionString.trim();
        //major
        int start = 0;
        int end = versionString.indexOf('.');
        if (end == -1){
            major = Integer.parseInt(versionString);
            return;
        }
        String piece = versionString.substring(start, end);
        major = Integer.parseInt(piece);

        //minor
        start = end + 1;
        end = versionString.indexOf('.', start);
        if (end == -1){
            piece = versionString.substring(start);
            minor = Integer.parseInt(piece);
            return;
        }
        piece = versionString.substring(start, end);
        minor = Integer.parseInt(piece);

        //incr
        start = end + 1;
        end = versionString.indexOf('-', start);
        if (end == -1) {
            piece = versionString.substring(start);
            incr = Integer.parseInt(piece);
            return;
        }
        piece = versionString.substring(start, end);
        incr = Integer.parseInt(piece);

        //qualifier
        start = end + 1;
        qualifier = versionString.substring(start);
    }


    @Override
    public int compareTo(ClientVersion that) {
        int diff = major - that.major;
        if(diff != 0){
            return diff;
        }
        diff = minor - that.minor;
        if(diff != 0){
            return diff;
        }
        diff = incr - that.incr;
        if(diff != 0){
            return diff;
        }
        /**
         * qualifier比较：
         * 1 都为null则判定为相等
         * 2 一个不为null，则不为null的更大（版本更晚）
         * 3 都不为null，则进行简单的字符串比较
         */
        if(qualifier==null){
            return that.qualifier == null ? 0 : -1;
        } else {
            return that.qualifier == null ? 1 : qualifier.compareTo(that.qualifier);
        }
    }

    public boolean isLaterThan(ClientVersion that){
        return compareTo(that) > 0;
    }

    public boolean isLaterThanOrEqual(ClientVersion that){
        return compareTo(that) >= 0;
    }

    public boolean isEarlierThan(ClientVersion that){
        return compareTo(that) < 0;
    }

    public boolean isEarlierThanOrEqual(ClientVersion that){
        return compareTo(that) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientVersion that = (ClientVersion) o;

        if (major != that.major) return false;
        if (minor != that.minor) return false;
        if (incr != that.incr) return false;
        return !(qualifier != null ? !qualifier.equals(that.qualifier) : that.qualifier != null);

    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + incr;
        result = 31 * result + (qualifier != null ? qualifier.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientVersion{" +
                "major=" + major +
                ", minor=" + minor +
                ", incr=" + incr +
                ", qualifier='" + qualifier + '\'' +
                '}';
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getIncr() {
        return incr;
    }

    public String getQualifier() {
        return qualifier;
    }
}
