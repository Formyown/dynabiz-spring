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
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package test;

import org.dynabiz.BusinessException;

import org.dynabiz.std.exception.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ExceptionsTest {



    @Test
    public void printBizExceptions() {

        BusinessException fe = FileException.NOT_FOUND.setSubCode(10);
        BusinessException fe2 = FileException.NOT_FOUND.setSubCode(12);
        assert fe.getSubCode() == 10;
        assert fe2.getSubCode() == 12;
        assert FileException.NOT_FOUND.getSubCode() == -1;

        System.out.println("+=========================================================+");
        System.out.println("||                ALL BUSINESS EXCEPTIONS                ||");
        System.out.println("+=========================================================+");


        List<BusinessException> bizExceptions = new ArrayList<BusinessException>();
        bizExceptions.addAll(Arrays.asList(FileException.getAllExceptionTypes()));
        bizExceptions.addAll(Arrays.asList(FinanceException.getAllExceptionTypes()));
        bizExceptions.addAll(Arrays.asList(LoginException.getAllExceptionTypes()));
        bizExceptions.addAll(Arrays.asList(PermissionException.getAllExceptionTypes()));
        bizExceptions.addAll(Arrays.asList(RegisterException.getAllExceptionTypes()));
        bizExceptions.addAll(Arrays.asList(TokenException.getAllExceptionTypes()));
        bizExceptions.addAll(Arrays.asList(VerificationCodeException.getAllExceptionTypes()));

        bizExceptions.sort(Comparator.comparingInt(BusinessException::getCode));

        for (BusinessException be : bizExceptions){
            System.out.println(String.format("%d : [%s] %s", be.getCode(), be.getClass().getSimpleName(), be.getMessage()));
        }
        System.out.println(String.format("Total: %d", bizExceptions.size()));
    }



}
