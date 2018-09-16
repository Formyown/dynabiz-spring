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

package io.dynabiz.std.exception;


import io.dynabiz.BusinessException;

public class RegisterException extends BusinessException {

    public static int BASE_CODE = 2000;
    private static int code = 1;

    public static final RegisterException PASSWORD_ILLEGAL_FORMAT = new RegisterException(code++, "Illegal format of password");
    public static final RegisterException EMAIL_ILLEGAL_FORMAT = new RegisterException(code++, "Illegal format of email");
    public static final RegisterException PHONE_ILLEGAL_FORMAT = new RegisterException(code++, "Illegal format of phone");
    public static final RegisterException USER_NAME_ILLEGAL_FORMAT = new RegisterException(code++, "Illegal format of user name");
    public static final RegisterException EMAIL_ALREADY_EXISTS = new RegisterException(code++, "Email already exists");
    public static final RegisterException PHONE_ALREADY_EXISTS = new RegisterException(code++, "Phone already exists");
    public static final RegisterException USER_NAME_ALREADY_EXISTS = new RegisterException(code++, "User name already exists");

    public RegisterException(String message) {
        super(BASE_CODE, message);
    }

    private RegisterException(int code ,String message) {
        super(BASE_CODE + code, "RegisterException:" + message, true);
    }

    public static BusinessException[] getAllExceptionTypes(){
        return new BusinessException[]{PASSWORD_ILLEGAL_FORMAT, EMAIL_ILLEGAL_FORMAT, PHONE_ILLEGAL_FORMAT,
                USER_NAME_ILLEGAL_FORMAT, EMAIL_ALREADY_EXISTS, PHONE_ALREADY_EXISTS, USER_NAME_ALREADY_EXISTS};
    }

}
