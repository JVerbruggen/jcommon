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

package com.jverbruggen.jcommon.virtualentity.render.manager;

import com.jverbruggen.jcommon.packet.sender.PacketSender;
import com.jverbruggen.jcommon.virtualentity.id.EntityIdFactory;
import com.jverbruggen.jcommon.virtualentity.render.GlobalViewport;

public class ViewportManagerFactory {
    private final PacketSender packetSender;
    private final EntityIdFactory entityIdFactory;

    public ViewportManagerFactory(PacketSender packetSender, EntityIdFactory entityIdFactory) {
        this.packetSender = packetSender;
        this.entityIdFactory = entityIdFactory;
    }

    public ViewportManager createViewportManager(){
        int renderDistance = 100;
        int renderChunkSize = 8;

        return new GlobalViewportManager(new GlobalViewport(renderDistance), packetSender, entityIdFactory, renderDistance, renderChunkSize);
    }
}
