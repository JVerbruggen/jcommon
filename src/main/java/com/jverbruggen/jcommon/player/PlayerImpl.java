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

package com.jverbruggen.jcommon.player;

import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.nms.NMSHandler;
import org.bukkit.Location;

public class PlayerImpl implements Player{
    private final NMSHandler nmsHandler;
    private final org.bukkit.entity.Player bukkitPlayer;

    public PlayerImpl(NMSHandler nmsHandler, org.bukkit.entity.Player bukkitPlayer) {
        this.nmsHandler = nmsHandler;
        this.bukkitPlayer = bukkitPlayer;
    }

    @Override
    public org.bukkit.entity.Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    @Override
    public void setPositionWithoutTeleport(Vector3 position) {
        nmsHandler.setPlayerLocationNoTeleport(this, position);
    }

    @Override
    public Vector3 getLocation() {
        Location location = bukkitPlayer.getLocation();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        return new Vector3(x, y, z);
    }
}
