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

package com.jverbruggen.jcommon.packet.objects.v1_20;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.jverbruggen.jcommon.bukkit.Player;
import com.jverbruggen.jcommon.packet.objects.SingularServerPacket;

public class EntityMountServerPacket extends SingularServerPacket {
    private final int entityId;
    private final Player mountedPlayer;

    public EntityMountServerPacket(ProtocolManager protocolManager, int entityId, Player mountedPlayer) {
        super(protocolManager);
        this.entityId = entityId;
        this.mountedPlayer = mountedPlayer;
    }

    @Override
    public PacketContainer getPacket() {
        int[] mountingEntity;
        if(mountedPlayer != null){
            mountingEntity = new int[]{mountedPlayer.getBukkitPlayer().getEntityId()};
        }else{
            mountingEntity = new int[]{};
        }

        PacketContainer packet = new PacketContainer(PacketType.Play.Server.MOUNT);
        packet.getIntegers().write(0, entityId);
        packet.getIntegerArrays().write(0, mountingEntity);

        return packet;
    }
}
