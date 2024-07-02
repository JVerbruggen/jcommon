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

package com.jverbruggen.jcommon.virtualentity.render;

import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.virtualentity.HasAliveStatus;
import com.jverbruggen.jcommon.virtualentity.ViewedByPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class VirtualEntityViewport implements Viewport{
    protected List<ViewedByPlayer> virtualEntities;
    protected List<Viewer> viewers;
    private final int maxRenderDistance;

    public VirtualEntityViewport(int maxRenderDistance) {
        this.virtualEntities = new ArrayList<>();
        this.viewers = new ArrayList<>();
        this.maxRenderDistance = maxRenderDistance;
    }

    @Override
    public List<ViewedByPlayer> getEntities() {
        return virtualEntities;
    }

    @Override
    public List<Viewer> getViewers() {
        return viewers;
    }

    @Override
    public void addViewer(Viewer viewer) {
        if(hasViewer(viewer)) return;
        viewers.add(viewer);
    }

    @Override
    public void removeViewer(Viewer viewer) {
        if(!hasViewer(viewer)) return;
        viewers.remove(viewer);

        for(ViewedByPlayer virtualEntity : virtualEntities){
            if(!virtualEntity.isViewer(viewer)) continue; // TODO: Same as this::removeEntity TODO
            virtualEntity.despawnFor(viewer, true);
        }
    }

    @Override
    public boolean hasViewer(Viewer viewer) {
        return viewers.contains(viewer);
    }

    @Override
    public void addEntity(ViewedByPlayer viewedByPlayer) {
        if(hasEntity(viewedByPlayer)) return;

        virtualEntities.add(viewedByPlayer);
        updateEntityViewers(viewedByPlayer);
//        virtualEntity.spawnForAll(viewers);
    }

    @Override
    public void removeEntity(ViewedByPlayer viewedByPlayer) {
        if(!hasEntity(viewedByPlayer)) return;

        virtualEntities.remove(viewedByPlayer);
        //TODO: Should it be despawned? What if player and viewport are in a different viewport as well?
    }

    @Override
    public boolean hasEntity(ViewedByPlayer viewedByPlayer) {
        return virtualEntities.contains(viewedByPlayer);
    }

    @Override
    public void updateFor(Viewer viewer, Vector3 playerLocation) {
        if(!hasViewer(viewer)) addViewer(viewer);

        for(ViewedByPlayer viewedByPlayer : virtualEntities){
            renderLogic(viewedByPlayer, viewer, playerLocation);
        }
    }

    @Override
    public void updateEntityViewers(ViewedByPlayer viewedByPlayer) {
        if(!hasEntity(viewedByPlayer)) addEntity(viewedByPlayer);

        for(Viewer viewer : viewers){
            renderLogic(viewedByPlayer, viewer, viewer.getLocation());
        }
    }

    @Override
    public void flushDeadEntities() {
        virtualEntities = getEntities().stream()
                .filter(HasAliveStatus::isAlive)
                .collect(Collectors.toList());
    }

    private void renderLogic(ViewedByPlayer viewedByPlayer, Viewer viewer, Vector3 playerLocation){
        double distanceSquared = viewedByPlayer.getLocation().distanceSquared(playerLocation);
        if(viewer.isViewing(viewedByPlayer)){
            if(distanceSquared > maxRenderDistance*maxRenderDistance){
                viewedByPlayer.despawnFor(viewer, true);
                viewer.removeViewing(viewedByPlayer);
            }
        }
        else{
            if(distanceSquared <= maxRenderDistance*maxRenderDistance){
                viewedByPlayer.spawnFor(viewer);
                viewer.addViewing(viewedByPlayer);
            }
        }
    }
}
