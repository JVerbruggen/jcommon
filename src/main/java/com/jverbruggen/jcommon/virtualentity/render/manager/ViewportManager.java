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

package com.jverbruggen.jcommon.virtualentity.render.manager;

import com.jverbruggen.jcommon.player.Player;
import com.jverbruggen.jcommon.math.Quaternion;
import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.virtualentity.Model;
import com.jverbruggen.jcommon.virtualentity.VirtualArmorStand;
import com.jverbruggen.jcommon.virtualentity.VirtualArmorstandConfiguration;
import com.jverbruggen.jcommon.virtualentity.VirtualEntity;
import org.bukkit.entity.EntityType;

public interface ViewportManager {
    void updateVisuals(Player player);
    void updateVisuals(Player player, Vector3 playerLocation);
    void updateForEntity(VirtualEntity virtualEntity);
    VirtualEntity spawnModelEntity(Vector3 location, Model model);
    VirtualEntity spawnModelEntity(Vector3 location, Quaternion rotation, Model model, String customName);
    VirtualEntity spawnVirtualBukkitEntity(Vector3 location, EntityType entityType);
    VirtualEntity spawnVirtualBukkitEntity(Vector3 location, EntityType entityType, double yawRotation);


    VirtualArmorStand spawnVirtualArmorstand(Vector3 location);
    VirtualArmorStand spawnVirtualArmorstand(Vector3 location, double yawRotation);
    VirtualArmorStand spawnVirtualArmorstand(Vector3 location, Quaternion rotation, Model model, VirtualArmorstandConfiguration configuration);
    VirtualArmorStand spawnVirtualArmorstand(Vector3 location, Quaternion rotation, double yawRotation, Model model, VirtualArmorstandConfiguration configuration);
    void despawnAll();
    VirtualEntity getEntity(int entityId);
    int getRenderChunkSize();
    int getRenderDistance();
}
