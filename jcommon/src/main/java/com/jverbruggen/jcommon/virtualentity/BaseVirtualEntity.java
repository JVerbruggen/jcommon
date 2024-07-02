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

import com.jverbruggen.jcommon.bukkit.Player;
import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.packet.sender.PacketSender;
import com.jverbruggen.jcommon.serviceprovider.internal.InternalServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseVirtualEntity implements VirtualEntity, ViewedByPlayer {
    // Properties
    private UUID uuid;
    private final int entityId;

    // Players
    private Player seatedPlayer;
    private boolean allowsSeatedPlayer;

    // Rendering
    private boolean spawned;
    private boolean rendered;
    private Vector3 location;
    private double yawRotation;
    protected List<Player> viewers;
    private Model model;

    public BaseVirtualEntity(Vector3 location, double yawRotation, int entityId) {
        this.uuid = UUID.randomUUID();
        this.entityId = entityId;

        this.seatedPlayer = null;
        this.allowsSeatedPlayer = false;

        this.spawned = true;
        this.rendered = true;
        this.location = location;
        this.yawRotation = yawRotation;
        this.viewers = new ArrayList<>();
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
    }

    @Override
    public Vector3 getLocation() {
        return location;
    }

    @Override
    public void setLocation(Vector3 location) {
        this.location = location;
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
    public List<Player> getViewers() {
        return viewers;
    }

    @Override
    public void addViewer(Player player) {
        if(viewers.contains(player)) return;

        viewers.add(player);
    }

    @Override
    public void removeViewer(Player player) {
        viewers.remove(player);
    }

    @Override
    public boolean isViewer(Player player) {
        return viewers.contains(player);
    }

    public abstract void spawnFor(Player player, PacketSender packetSender);

    @Override
    public void spawnFor(Player player) {
        PacketSender packetSender = InternalServiceProvider.getSingleton(PacketSender.class);
        spawnFor(player, packetSender);
    }

    @Override
    public void spawnForAll(List<Player> players) {
        spawnForAll(players, false);
    }

    @Override
    public void spawnForAll(List<Player> players, boolean hard) {
        players.forEach(this::spawnFor);
    }

    @Override
    public void despawn() {
        spawned = false;
        InternalServiceProvider.getSingleton(PacketSender.class).sendDestroyVirtualEntityForPacket(getViewers(), this);
    }

    @Override
    public void despawnFor(Player player, boolean removeAsViewer) {
        if(removeAsViewer)
            removeViewer(player);

        InternalServiceProvider.getSingleton(PacketSender.class).sendDestroyVirtualEntityForPacket(player, this);
    }

    @Override
    public void despawnForAll(List<Player> players, boolean removeAsViewer) {
        for(Player player : List.copyOf(players)){
            if(!isViewer(player)) continue;
            despawnFor(player, removeAsViewer);
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
}
