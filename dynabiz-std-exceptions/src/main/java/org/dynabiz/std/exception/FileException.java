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

public class FileException extends BusinessException {

    public static int BASE_CODE = 8000;
    private static int code = 1;

    public static final FileException TOO_LARGE = new FileException(code++, "File size is beyond the limit");
    public static final FileException TOO_SMALL = new FileException(code++, "File size is too small");
    public static final FileException NOT_FOUND = new FileException(code++, "File not found");
    public static final FileException CAN_NOT_CREATE = new FileException(code++, "Create file failed");
    public static final FileException UNEXPECTED_FORMAT = new FileException(code++, "Format of file is unexpected");

    public FileException(String message) {
        super(BASE_CODE, message);
    }

    private FileException(int code, String message){
        super(BASE_CODE + code, message, true);
    }

    public static BusinessException[] getAllExceptionTypes(){
        return new BusinessException[]{TOO_LARGE, TOO_SMALL, NOT_FOUND, CAN_NOT_CREATE, UNEXPECTED_FORMAT};
    }
}
