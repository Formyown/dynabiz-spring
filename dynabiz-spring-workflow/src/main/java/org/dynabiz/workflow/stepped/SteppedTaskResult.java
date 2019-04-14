package org.dynabiz.workflow.stepped;


import java.util.Objects;

public class SteppedTaskResult {
    private int index;
    private boolean last;

    private long expiredAt;
    private String token;
    private Object ret;

    public SteppedTaskResult() {
    }

    public SteppedTaskResult(int index, boolean last, long expiredAt, String token, Object ret) {
        this.index = index;
        this.last = last;
        this.expiredAt = expiredAt;
        this.token = token;
        this.ret = ret;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SteppedTaskResult that = (SteppedTaskResult) o;
        return index == that.index &&
                last == that.last &&
                expiredAt == that.expiredAt &&
                Objects.equals(token, that.token) &&
                Objects.equals(ret, that.ret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, last, expiredAt, token, ret);
    }

    @Override
    public String toString() {
        return "SteppedTaskResult{" +
                "index=" + index +
                ", last=" + last +
                ", expiredAt=" + expiredAt +
                ", token='" + token + '\'' +
                ", ret=" + ret +
                '}';
    }
}

