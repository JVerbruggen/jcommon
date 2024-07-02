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

package com.jverbruggen.jcommon.plugin;

import com.jverbruggen.jcommon.JCommon;
import com.jverbruggen.jcommon.logging.Logger;
import com.jverbruggen.jcommon.manager.JCommonManager;
import com.jverbruggen.jcommon.serviceprovider.internal.InternalServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onLoad() {
        JCommonManager jCommonManager = JCommon.getManager(this);
        String version = jCommonManager.getVersion();

        InternalServiceProvider.getSingleton(Logger.class).info("JCommon version " + version);
    }
}
