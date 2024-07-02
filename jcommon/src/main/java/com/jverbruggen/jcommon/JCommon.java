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

package com.jverbruggen.jcommon;

import com.jverbruggen.jcommon.manager.JCommonManager;
import com.jverbruggen.jcommon.serviceprovider.CommonServiceProvider;
import com.jverbruggen.jcommon.serviceprovider.CommonServiceProviderFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class JCommon {
    private static final HashMap<JavaPlugin, JCommonManager> SESSIONS = new LinkedHashMap<>();

    private static JCommonManager createNewManager(JavaPlugin javaPlugin){
        CommonServiceProvider serviceProvider = CommonServiceProviderFactory.createServiceProvider(javaPlugin.getLogger());

        return new JCommonManager(serviceProvider);
    }

    public static JCommonManager getManager(JavaPlugin javaPlugin){
        if(SESSIONS.containsKey(javaPlugin))
            return SESSIONS.get(javaPlugin);

        JCommonManager jCommonManager = createNewManager(javaPlugin);
        SESSIONS.put(javaPlugin, jCommonManager);
        return jCommonManager;
    }
}
