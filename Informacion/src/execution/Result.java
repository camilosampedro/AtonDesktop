/*
 * The MIT License
 *
 * Copyright 2015 Camilo Sampedro.
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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package execution;

import comunication.SendableObject;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Result extends SendableObject {

    private final String result;

    public Result(String result) {
        this.result = result;
    }

    @Override
    public String getBody() {
        return BODYSTART + result + BODYEND;
    }

    public static Result buildObject(String information) {
        int i = information.indexOf(BODYSTART);
        int j = information.indexOf(BODYEND);
        String info = information.substring(i, j);
        return new Result(info);
    }

    @Override
    public String getHead() {
        return HEADSTART + TYPE[RESULT] + HEADEND;
    }

    public String getResult() {
        return result;
    }
}
