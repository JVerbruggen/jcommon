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

import com.jverbruggen.jcommon.player.Player;
import com.jverbruggen.jcommon.math.Vector3;

import java.util.UUID;

public interface VirtualEntity extends HasAliveStatus, Positioned {
    UUID getUUID();
    int getEntityId();

    Player getSeatedPlayer();
    boolean allowsSeatedPlayer();
    boolean hasSeatedPlayer();
    void setSeatedPlayer(Player player);

    void setLocation(Vector3 newLocation);

    double getYaw();

    boolean isRendered();
    void setRendered(boolean render);

    void setModel(Model model);
}
