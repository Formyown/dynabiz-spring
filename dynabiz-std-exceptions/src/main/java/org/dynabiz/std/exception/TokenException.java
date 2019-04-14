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

package org.dynabiz.std.exception;

import org.dynabiz.BusinessException;

public class TokenException extends BusinessException {

    public static int BASE_CODE = 4000;
    private static int code = 1;

    public static final TokenException BAD_TOKEN = new TokenException(code++, "Bad token");
    public static final TokenException TOKEN_NOT_FOUND = new TokenException(code++, "Token not found");


    public TokenException(String message){
        super(BASE_CODE, message);
    }

    private TokenException(int code, String message){
        super(BASE_CODE + code, message, true);
    }

    public static BusinessException[] getAllExceptionTypes(){
        return new BusinessException[]{BAD_TOKEN, TOKEN_NOT_FOUND};
    }
}
