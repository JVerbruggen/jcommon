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

import com.jverbruggen.jcommon.bukkit.Player;
import com.jverbruggen.jcommon.virtualentity.Model;
import com.jverbruggen.jcommon.virtualentity.VirtualArmorStand;
import com.jverbruggen.jcommon.virtualentity.VirtualEntity;

import java.util.List;

public interface PacketSender {
    void sendSpawnVirtualArmorstandForPacket(Player player, VirtualArmorStand virtualArmorStand);
    void sendAddModelToEntityPacket(Player player, VirtualEntity virtualEntity, ModelSlot modelSlot, Model model);
    void sendSeatedPlayerPacket(List<Player> players, Player seatedPlayer, VirtualEntity virtualEntity);

    void sendDestroyVirtualEntityForPacket(Player player, VirtualEntity virtualEntity);
    void sendDestroyVirtualEntityForPacket(List<Player> players, VirtualEntity virtualEntity);

    String getSenderVersion();
}
