/*
 * Copyright (c) 2018, Deyu Heng. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.dynabiz;

public class BusinessException extends RuntimeException implements Cloneable {
    private int code = -1;
    private int subCode = -1;

    private boolean finalInstance = false;

    BusinessException(){
        super();
    }

    public BusinessException(BusinessException e){
        super(e);
        this.code = e.code;
        this.subCode = e.subCode;
    }

    public BusinessException(String message){
        super(message);
    }

    protected BusinessException(int code, String message, boolean finalInstance){
        super(message);
        this.code = code;
        this.finalInstance = finalInstance;
    }

    public BusinessException(int code, String message){
        super(message);
        this.code = code;
    }

    public BusinessException(int code, int subCode, String message){
        super(message);
        this.code = code;
        this.subCode = subCode;
    }

    public BusinessException(int code , Exception e){
        super(e);
        this.code = code;
    }

    public BusinessException(int code, String message, Throwable cause){
        super(message,cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public BusinessException setCode(int code) {

        if(finalInstance){
            //deep clone new instance to modify
            BusinessException e = new BusinessException(this);
            e.code = code;
            return e;
        }
        this.code = code;
        return this;
    }

    public int getSubCode() {
        return subCode;
    }

    public BusinessException setSubCode(int subCode) {
        if(finalInstance){
            //deep clone new instance to modify
            BusinessException e = new BusinessException(this);
            e.subCode = subCode;
            return e;
        }
        this.subCode = subCode;
        return this;
    }
}

