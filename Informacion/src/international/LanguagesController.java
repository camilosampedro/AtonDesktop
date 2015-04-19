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
package international;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author Camilo Sampedro
 */
public class LanguagesController {

    private static Map supportedLanguages;
    private static ResourceBundle translation;

    public static void initializeLanguage(String language) {
        Locale spanish = new Locale("Spanish");
        supportedLanguages = new HashMap();
        supportedLanguages.put("Spanish", spanish);
        supportedLanguages.put("English", Locale.ENGLISH);
        translation = ResourceBundle.getBundle("resources.language", (Locale) supportedLanguages.get(language));
    }

    public static String getWord(String keyword) {
        try {
            return translation.getString(keyword);
        } catch (MissingResourceException ex) {
            logs.LogCreator.addToLog(logs.LogCreator.ERROR, "Word resource not found: " + keyword);
            return "[WR NF] " + keyword;
        }
    }
}
