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

import com.jverbruggen.jcommon.virtualentity.render.Viewer;

import java.util.List;

public interface ViewedByPlayer extends VirtualEntity, HasAliveStatus, Positioned {
    List<Viewer> getViewers();
    void addViewer(Viewer viewer);
    void removeViewer(Viewer viewer);
    boolean isViewer(Viewer viewer);

    void spawnFor(Viewer viewer);
    void spawnForAll(List<Viewer> viewers);
    void spawnForAll(List<Viewer> viewers, boolean hard);
    void despawn();
    void despawnFor(Viewer viewer, boolean removeAsViewer);
    void despawnForAll(List<Viewer> viewers, boolean removeAsViewer);
}
