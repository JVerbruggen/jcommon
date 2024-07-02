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

package com.jverbruggen.jcommon.packet.sender.impl;

import com.comphenix.protocol.ProtocolManager;
import com.jverbruggen.jcommon.bukkit.Player;
import com.jverbruggen.jcommon.logging.Logger;
import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.packet.objects.v1_20.EntityDestroyServerPacket;
import com.jverbruggen.jcommon.packet.objects.v1_20.EntityEquipmentServerPacket;
import com.jverbruggen.jcommon.packet.objects.v1_20.EntityMountServerPacket;
import com.jverbruggen.jcommon.packet.objects.v1_20.SpawnArmorstandServerPacket;
import com.jverbruggen.jcommon.packet.sender.ModelSlot;
import com.jverbruggen.jcommon.packet.sender.PacketSender;
import com.jverbruggen.jcommon.virtualentity.Model;
import com.jverbruggen.jcommon.virtualentity.VirtualArmorStand;
import com.jverbruggen.jcommon.virtualentity.VirtualEntity;

import java.util.List;
import java.util.UUID;

public class PacketSender_1_20_4 extends BasePacketSender implements PacketSender {

    public PacketSender_1_20_4(ProtocolManager protocolManager, Logger logger) {
        super(protocolManager, logger);
    }

    @Override
    public void sendSpawnVirtualArmorstandForPacket(Player player, VirtualArmorStand virtualArmorStand) {
        sendDebugLog("sendSpawnVirtualArmorstandForPacket (single)");

        Vector3 location = virtualArmorStand.getLocation();
        double locationX = location.getX();
        double locationY = location.getY();
        double locationZ = location.getZ();
        double yawRotation = virtualArmorStand.getYaw();

        UUID uuid = virtualArmorStand.getUUID();
        int entityId = virtualArmorStand.getEntityId();

        new SpawnArmorstandServerPacket(protocolManager, entityId, locationX, locationY, locationZ, yawRotation, uuid);
    }

    @Override
    public void sendAddModelToEntityPacket(Player player, VirtualEntity virtualEntity, ModelSlot modelSlot, Model model) {
        if(model == null) return;
        sendDebugLog("sendAddModelToEntityPacket (single)");
        int entityId = virtualEntity.getEntityId();

        new EntityEquipmentServerPacket(
                protocolManager, entityId, ModelSlot.toItemSlot(modelSlot), model
        ).send(player);
    }

    @Override
    public void sendSeatedPlayerPacket(List<Player> players, Player seatedPlayer, VirtualEntity virtualEntity) {
        sendDebugLog("sendSeatedPlayerPacket (multiple)");
        int entityId = virtualEntity.getEntityId();

        new EntityMountServerPacket(protocolManager, entityId, seatedPlayer).sendAll(players);
    }

    @Override
    public void sendDestroyVirtualEntityForPacket(Player player, VirtualEntity virtualEntity) {
        sendDebugLog("sendDestroyVirtualEntityForPacket (single)");
        int entityId = virtualEntity.getEntityId();

        new EntityDestroyServerPacket(
                protocolManager, entityId
        ).send(player);
    }

    @Override
    public void sendDestroyVirtualEntityForPacket(List<Player> players, VirtualEntity virtualEntity) {
        sendDebugLog("sendDestroyVirtualEntityForPacket (multiple)");
        int entityId = virtualEntity.getEntityId();

        new EntityDestroyServerPacket(
                protocolManager, entityId
        ).sendAll(players);
    }

    @Override
    public String getSenderVersion() {
        return "1.20.4";
    }
}
