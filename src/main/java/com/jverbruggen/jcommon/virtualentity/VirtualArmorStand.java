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

package com.jverbruggen.jcommon.virtualentity;

import com.jverbruggen.jcommon.bukkit.Player;
import com.jverbruggen.jcommon.interaction.PlayerInteractVirtualEntityAction;
import com.jverbruggen.jcommon.math.Quaternion;
import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.packet.sender.PacketSender;

public class VirtualArmorStand extends BaseVirtualEntity implements VirtualEntity, HasOrientation, HasAction {
    private static Vector3 ARMORSTAND_MODEL_COMPENSATION = null;

    private PlayerInteractVirtualEntityAction action;
    private Quaternion orientation;
    private double yawRotation;

    public VirtualArmorStand(Vector3 location, double yawRotation, int entityId) {
        super(location, yawRotation, entityId);
    }

    @Override
    public void spawnFor(Player player, PacketSender packetSender) {
        packetSender.sendSpawnVirtualArmorstandForPacket(player, this);
    }

    @Override
    public boolean hasAction() {
        return action != null;
    }

    @Override
    public void setAction(PlayerInteractVirtualEntityAction action) {
        this.action = action;
    }

    @Override
    public void runCustomAction(Player player) {
        if(hasAction())
            this.action.run(this, player);
    }

    @Override
    public Quaternion getOrientation() {
        return orientation.clone();
    }

    @Override
    public void setOrientation(Quaternion orientation) {
        this.orientation.copyFrom(orientation);
    }
}
