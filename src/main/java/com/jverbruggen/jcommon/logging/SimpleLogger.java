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

package com.jverbruggen.jcommon.logging;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class SimpleLogger implements Logger{
    private final java.util.logging.Logger bukkitPluginLogger;
    private final String prefix;
    private final boolean broadcastMode;
    private final boolean debugMode;

    public SimpleLogger(java.util.logging.Logger bukkitPluginLogger, String prefix, boolean broadcastMode, boolean debugMode) {
        this.bukkitPluginLogger = bukkitPluginLogger;
        this.prefix = prefix;
        this.broadcastMode = broadcastMode;
        this.debugMode = debugMode;
    }

    @Override
    public void debug(String message) {
        if(debugMode)
            log(message, Level.INFO);
    }

    @Override
    public void info(String message) {
        log(message, Level.INFO);
    }

    @Override
    public void warning(String message) {
        log(message, Level.WARNING);
    }

    @Override
    public void severe(String message) {
        log(message, Level.SEVERE);
    }

    private void log(String message, Level level){
        if(broadcastMode){
            ChatColor color;
            if(level.equals(Level.INFO))
                color = ChatColor.GRAY;
            else if(level.equals(Level.WARNING))
                color = ChatColor.YELLOW;
            else if(level.equals(Level.SEVERE))
                color = ChatColor.DARK_RED;
            else
                color = ChatColor.GRAY;

            Bukkit.broadcastMessage(color + prefix + " " + message);
        }else{
            bukkitPluginLogger.log(level, message);
        }
    }
}
