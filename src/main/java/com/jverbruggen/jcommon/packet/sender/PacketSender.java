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

package com.jverbruggen.jcommon.packet.sender;

import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.player.Player;
import com.jverbruggen.jcommon.virtualentity.ArmorstandRotationBone;
import com.jverbruggen.jcommon.virtualentity.Model;
import com.jverbruggen.jcommon.virtualentity.VirtualArmorStand;
import com.jverbruggen.jcommon.virtualentity.VirtualEntity;
import com.jverbruggen.jcommon.virtualentity.render.Viewer;

import java.util.List;

public interface PacketSender {
    void sendSpawnVirtualArmorstandForPacket(Viewer viewer, VirtualArmorStand virtualArmorStand);
    void sendAddModelToEntityPacket(Viewer viewer, VirtualEntity virtualEntity, ModelSlot modelSlot, Model model);
    void sendAddModelToEntityPacket(List<Viewer> viewers, VirtualEntity virtualEntity, ModelSlot modelSlot, Model model);
    void sendSeatedPlayerPacket(List<Viewer> viewers, Player seatedPlayer, VirtualEntity virtualEntity);
    void sendRotationPacket(List<Viewer> players, VirtualEntity virtualEntity, ArmorstandRotationBone rotationBone, Vector3 rotation);

    void sendDestroyVirtualEntityForPacket(Viewer viewer, VirtualEntity virtualEntity);
    void sendDestroyVirtualEntityForPacket(List<Viewer> viewers, VirtualEntity virtualEntity);

    Vector3 getArmorstandModelCompensationVector();
    String getSenderVersion();
    double toPacketYaw(double normalYaw);
}
