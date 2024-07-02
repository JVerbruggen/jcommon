/************************************************************************************************************
 * GPLv3 License                                                                                            *
 *                                                                                                          *
 * Copyright (c) 2024-2024 JVerbruggen                                                                      *
 * https://github.com/JVerbruggen/jcommon                                                                   *
 *                                                                                                          *
 * This software is protected under the GPLv3 license,                                                      *
 * that can be found in the project's LICENSE file.                                                         *
 *                                                                                                          *
 * In short, permission is hereby granted that anyone can copy, modify and distribute this software.        *
 * You have to include the license and copyright notice with each and every distribution. You can use       *
 * this software privately or commercially. Modifications to the code have to be indicated, and             *
 * distributions of this code must be distributed with the same license, GPLv3. The software is provided    *
 * without warranty. The software author or license can not be held liable for any damages                  *
 * inflicted by the software.                                                                               *
 ************************************************************************************************************/

package com.jverbruggen.jcommon.serviceprovider;

import java.util.HashMap;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class HashMapCommonServiceProvider implements CommonServiceProvider {

    private HashMap<Class, Object> instances;

    public HashMapCommonServiceProvider() {
        this.instances = new HashMap<>();
    }

    @Override
    public <T> T _getSingleton(Class<T> type) {
        if(!instances.containsKey(type)) throw new RuntimeException("Type " + type.getTypeName() + " was not registered to the ServiceProvider");

        Object object = instances.get(type);
        return type.cast(object);
    }

    @Override
    public <T> T _register(Class<T> type, T instance) {
        instances.put(type, instance);

        return instance;
    }

    @Override
    public <T extends CommonServiceProvider, R> void _register(Class<R> type, Function<T, R> function) {
        function.apply((T) this);
    }
}
