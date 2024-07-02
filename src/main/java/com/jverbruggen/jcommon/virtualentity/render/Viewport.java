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

package com.jverbruggen.jcommon.virtualentity.render;

import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.virtualentity.ViewedByPlayer;

import java.util.List;

public interface Viewport {
    List<ViewedByPlayer> getEntities();
    List<Viewer> getViewers();
    boolean isInViewport(Vector3 location);
    void addViewer(Viewer viewer);
    void removeViewer(Viewer viewer);
    void updateFor(Viewer viewer, Vector3 playerLocation);
    boolean hasViewer(Viewer viewer);
    void addEntity(ViewedByPlayer viewedByPlayer);
    void removeEntity(ViewedByPlayer viewedByPlayer);
    void updateEntityViewers(ViewedByPlayer viewedByPlayer);
    boolean hasEntity(ViewedByPlayer viewedByPlayer);
    void flushDeadEntities();
}
