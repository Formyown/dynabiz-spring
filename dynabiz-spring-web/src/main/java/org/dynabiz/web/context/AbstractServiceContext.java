package org.dynabiz.web.context;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 此类用于存储请求或会话内容的上下文
 *
 * 若要继承此类 <b>请务必重写hashCode与equals方法</b>
 */
public abstract class AbstractServiceContext implements Serializable {
    private static final long serialVersionUID = -6091520420908900649L;


    @JsonIgnore
    private int hashCode = 0;

    /**
     *
     */
    protected void makeSnapshot(){
        hashCode = hashCode();
    }

    /**
     * 判断上下文是否已经被修改
     * @return
     */
    @JsonIgnore
    public boolean isModified(){
        return hashCode != hashCode();
    }

}
