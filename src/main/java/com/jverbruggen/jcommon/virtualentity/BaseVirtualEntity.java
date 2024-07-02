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
import com.jverbruggen.jcommon.serviceprovider.CommonServiceProvider;
import com.jverbruggen.jcommon.virtualentity.render.Viewer;
import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.packet.sender.PacketSender;
import com.jverbruggen.jcommon.virtualentity.render.manager.ViewportManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseVirtualEntity implements VirtualEntity, ViewedByPlayer {
    // Services
    protected final PacketSender packetSender;
    protected final ViewportManager viewportManager;

    // Properties
    private UUID uuid;
    private final int entityId;

    // Players
    private Player seatedPlayer;
    private boolean allowsSeatedPlayer;
    private boolean passengerSyncCounterActive;
    private int passengerSyncCounter;

    // Rendering
    private boolean spawned;
    private boolean rendered;
    private Vector3 location;
    private double yawRotation;
    protected List<Viewer> viewers;
    private Model model;
    private int teleportSyncCountdownState; // If entity isn't teleported every few frames, it starts drifting due to only relative updates

    public BaseVirtualEntity(CommonServiceProvider serviceProvider, Vector3 location, double yawRotation, int entityId) {
        this.packetSender = serviceProvider._getSingleton(PacketSender.class);
        this.viewportManager = serviceProvider._getSingleton(ViewportManager.class);

        this.uuid = UUID.randomUUID();
        this.entityId = entityId;

        this.seatedPlayer = null;
        this.allowsSeatedPlayer = false;
        this.passengerSyncCounterActive = false;
        this.passengerSyncCounter = 0;

        this.spawned = true;
        this.rendered = true;
        this.location = location;
        this.yawRotation = yawRotation;
        this.viewers = new ArrayList<>();
        this.teleportSyncCountdownState = 0;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public Player getSeatedPlayer() {
        return seatedPlayer;
    }

    @Override
    public boolean allowsSeatedPlayer() {
        return this.allowsSeatedPlayer;
    }

    @Override
    public boolean hasSeatedPlayer() {
        return seatedPlayer != null;
    }

    @Override
    public void setSeatedPlayer(Player seatedPlayer) {
        this.seatedPlayer = seatedPlayer;

        packetSender.sendSeatedPlayerPacket(viewers, seatedPlayer, this);

        if(seatedPlayer != null){
            this.passengerSyncCounterActive = true;
            this.passengerSyncCounter = 0;
        }else{
            this.passengerSyncCounterActive = false;
        }
    }

    @Override
    public Vector3 getLocation() {
        return location;
    }

    @Override
    public void setLocation(Vector3 newLocation) {
        if(newLocation == null) return;

        final int chunkSize = viewportManager.getRenderChunkSize();

        if(Vector3.chunkRotated(this.location, newLocation, chunkSize)){
            viewportManager.updateForEntity(this);
        }

        double distanceSquared = newLocation.distanceSquared(this.location);

        if(distanceSquared > 49 || teleportSyncCountdownState > 60) {
            Vector3 blockLocation = newLocation.toBlock();
            teleportEntity(blockLocation);
            teleportSyncCountdownState = 0;

            Vector3 delta = Vector3.subtract(newLocation, newLocation.toBlock());
            moveEntity(delta, 0);
        }
        else{
            Vector3 delta = Vector3.subtract(newLocation, this.location);
            moveEntity(delta, 0);
        }

        this.location = newLocation;

        teleportSyncCountdownState++;
    }

    @Override
    public double getYaw() {
        return yawRotation;
    }

    @Override
    public boolean isAlive() {
        return spawned;
    }

    @Override
    public List<Viewer> getViewers() {
        return viewers;
    }

    @Override
    public void addViewer(Viewer viewer) {
        if(viewers.contains(viewer)) return;

        viewers.add(viewer);
    }

    @Override
    public void removeViewer(Viewer viewer) {
        viewers.remove(viewer);
    }

    @Override
    public boolean isViewer(Viewer viewer) {
        return viewers.contains(viewer);
    }

    @Override
    public void spawnFor(Viewer viewer) {
        spawnFor(viewer, packetSender);
    }

    @Override
    public void spawnForAll(List<Viewer> viewers) {
        spawnForAll(viewers, false);
    }

    @Override
    public void spawnForAll(List<Viewer> viewers, boolean hard) {
        viewers.forEach(this::spawnFor);
    }

    @Override
    public void despawn() {
        spawned = false;
        packetSender.sendDestroyVirtualEntityForPacket(getViewers(), this);
    }

    @Override
    public void despawnFor(Viewer viewer, boolean removeAsViewer) {
        if(removeAsViewer)
            removeViewer(viewer);

        packetSender.sendDestroyVirtualEntityForPacket(viewer, this);
    }

    @Override
    public void despawnForAll(List<Viewer> viewers, boolean removeAsViewer) {
        for(Viewer viewer : List.copyOf(viewers)){
            if(!isViewer(viewer)) continue;
            despawnFor(viewer, removeAsViewer);
        }
    }

    @Override
    public boolean isRendered() {
        return rendered;
    }

    @Override
    public void setRendered(boolean render) {
        if(render && !rendered){
            rendered = true;
            spawnForAll(getViewers(), true);
        }else if(!render && rendered){
            rendered = false;
            despawnForAll(getViewers(), false);
        }
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    protected void syncSeatedPlayer(Vector3 position){
        if(passengerSyncCounterActive){
            if(passengerSyncCounter > 20){
                passengerSyncCounter = 0;

                this.seatedPlayer.setPositionWithoutTeleport(position);
            }else passengerSyncCounter++;
        }
    }

    public abstract void spawnFor(Viewer viewer, PacketSender packetSender);

    protected abstract void moveEntity(Vector3 delta, double yawRotation);

    protected abstract void teleportEntity(Vector3 newLocation);
}
