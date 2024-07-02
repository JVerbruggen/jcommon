/************************************************************************************************************
 * GPLv3 License                                                                                            *
 *                                                                                                          *
 * Copyright (c) 2024-2024 JVerbruggen                                                                      *
 * https://github.com/JVerbruggen/jrides                                                                    *
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

import com.jverbruggen.jcommon.math.Quaternion;
import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.packet.sender.PacketSender;
import com.jverbruggen.jcommon.virtualentity.render.manager.ViewportManager;

import javax.annotation.Nonnull;

public class YawRotatedVirtualArmorstand extends VirtualArmorStand {
    public YawRotatedVirtualArmorstand(PacketSender packetSender, ViewportManager viewportManager, Vector3 location, Quaternion orientation, double yawRotation, int entityId, @Nonnull VirtualArmorstandConfiguration configuration) {
        super(packetSender, viewportManager, location, orientation, yawRotation, entityId);
    }

    @Override
    public void setRotation(Quaternion orientation) {
        if(orientation == null) return;

        Vector3 headPose = ArmorStandPose.getArmorStandPose(orientation);
        headPose.y = 0;

        setHeadPose(headPose);
        setYaw(packetSender.toPacketYaw(orientation.getYaw() - 90));
        moveEntity(Vector3.zero(), getYaw());
    }
}
