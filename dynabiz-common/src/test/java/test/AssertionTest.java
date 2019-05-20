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
import org.dynabiz.util.Assert;
import org.junit.Test;

public class AssertionTest {



    @Test
    public void testMapper() {
        System.out.println("+=========================================================+");
        System.out.println("||                     TEST ASSERTION                    ||");
        System.out.println("+=========================================================+");

        Assert.beFalse(false, new BusinessException("Assertion exception"));
        try{
            Assert.beFalse(true, new BusinessException("Assertion exception"));
        }
        catch (Exception e){
            assert e instanceof BusinessException;
        }
        System.out.println("Assert.beFalse : OK");

        Assert.beTrue(true, new BusinessException("Assertion exception"));
        try{
            Assert.beTrue(false, new BusinessException("Assertion exception"));
        }
        catch (Exception e){
            assert e instanceof BusinessException;
        }
        System.out.println("Assert.beTrue : OK");

        Assert.beNull(null, new BusinessException("Assertion exception"));
        try{
            Assert.beNull(1, new BusinessException("Assertion exception"));
        }
        catch (Exception e){
            assert e instanceof BusinessException;
        }
        System.out.println("Assert.beNull : OK");

        Assert.notNull(1, new BusinessException("Assertion exception"));
        try{
            Assert.notNull(null, new BusinessException("Assertion exception"));
        }
        catch (Exception e){
            assert e instanceof BusinessException;
        }
        System.out.println("Assert.notNull : OK");
    }

}
