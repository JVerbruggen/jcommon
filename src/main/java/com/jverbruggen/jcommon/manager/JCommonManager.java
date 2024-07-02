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

package com.jverbruggen.jcommon.manager;

import com.jverbruggen.jcommon.serviceprovider.CommonServiceProvider;
import com.jverbruggen.jcommon.virtualentity.render.manager.ViewportManager;
import org.bukkit.Bukkit;

public class JCommonManager {
    private final CommonServiceProvider serviceProvider;

    public JCommonManager(CommonServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public CommonServiceProvider getInternalServiceProvider(){
        return serviceProvider;
    }

    public ViewportManager getViewportManager(){
        return serviceProvider._getSingleton(ViewportManager.class);
    }

    public String getVersion(){
        return "jcommon-1.0-" + Bukkit.getVersion();
    }
}
