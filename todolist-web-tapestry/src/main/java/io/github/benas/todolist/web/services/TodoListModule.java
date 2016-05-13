/*
 * The MIT License
 *
 * Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package io.github.benas.todolist.web.services;

import io.github.todolist.core.domain.Priority;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.util.StringToEnumCoercion;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;

/**
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class TodoListModule {
    
    private TodoListModule() {}
    
    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.add(SymbolConstants.PRODUCTION_MODE, false);
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, "secret");
        configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "true");
    }

    public static void contributeTypeCoercer(Configuration<CoercionTuple> configuration) {
        add(configuration, Priority.class);
    }

    private static <T extends Enum> void add(Configuration<CoercionTuple> configuration, Class<T> enumType) {
        configuration.add(CoercionTuple.create(String.class, enumType, StringToEnumCoercion.create(enumType)));
    }

}
