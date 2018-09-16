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

public class RepositoryException extends BusinessException {

    public static int BASE_CODE = 5000;
    private static int code = 1;

    public static final RepositoryException ITEM_NOT_FOUND =  new RepositoryException(code++, "Item not found");
    public static final RepositoryException ITEM_EXISTS = new RepositoryException(code++, "Specific item already existed");
    public static final RepositoryException COUNT_REACHED_MAX = new RepositoryException(code++, "The count reached maximum");
    public static final RepositoryException COUNT_REACHED_MIN = new RepositoryException(code++, "The count reached minimum");


    public RepositoryException(String message){
        super(BASE_CODE, message);
    }

    private RepositoryException(int code, String message){
        super(BASE_CODE + code, message, true);
    }

    public static BusinessException[] getAllExceptionTypes(){
        return new BusinessException[]{ITEM_NOT_FOUND, ITEM_EXISTS, COUNT_REACHED_MAX, COUNT_REACHED_MIN };
    }
}
