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

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.jverbruggen.jcommon.logging.Logger;
import com.jverbruggen.jcommon.logging.SimpleLogger;
import com.jverbruggen.jcommon.nms.NMSHandler;
import com.jverbruggen.jcommon.nms.NMSHandlerFactory;
import com.jverbruggen.jcommon.packet.sender.PacketSender;
import com.jverbruggen.jcommon.packet.sender.impl.PacketSender_1_20_4;
import com.jverbruggen.jcommon.player.PlayerManager;
import com.jverbruggen.jcommon.virtualentity.id.EntityIdFactory;
import com.jverbruggen.jcommon.virtualentity.render.manager.ViewportManagerFactory;
import org.bukkit.plugin.java.JavaPlugin;

public class CommonServiceProviderFactory {
    public static CommonServiceProvider createServiceProvider(JavaPlugin javaPlugin){
        return createServiceProvider(javaPlugin, Settings.defaultValues());
    }

    public static CommonServiceProvider createServiceProvider(JavaPlugin javaPlugin, Settings settings){
        CommonServiceProvider commonServiceProvider = new HashMapCommonServiceProvider();
        java.util.logging.Logger parentLogger = javaPlugin.getLogger();

        configureServiceProvider(commonServiceProvider, parentLogger, settings);

        return commonServiceProvider;
    }

    private static void configureServiceProvider(CommonServiceProvider commonServiceProvider, java.util.logging.Logger parentLogger, Settings settings){
        ProtocolManager protocolManager                 = commonServiceProvider._register(ProtocolManager.class,        ProtocolLibrary.getProtocolManager());
        Logger logger                                   = commonServiceProvider._register(Logger.class,                 new SimpleLogger(parentLogger, settings.prefix(), settings.broadcastMode(), settings.debugMode()));
        PacketSender packetSender                       = commonServiceProvider._register(PacketSender.class,           new PacketSender_1_20_4(protocolManager, logger));
        EntityIdFactory entityIdFactory                 = commonServiceProvider._register(EntityIdFactory.class,        new EntityIdFactory(1_500_000, Integer.MAX_VALUE));
        ViewportManagerFactory viewportManagerFactory   = commonServiceProvider._register(ViewportManagerFactory.class, new ViewportManagerFactory(packetSender, entityIdFactory));
        NMSHandler nmsHandler                           = commonServiceProvider._register(NMSHandler.class,             NMSHandlerFactory.getNMSHandler());
        PlayerManager playerManager                     = commonServiceProvider._register(PlayerManager.class,          new PlayerManager(nmsHandler));
    }
}
