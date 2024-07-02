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
import com.jverbruggen.jcommon.packet.sender.PacketSender;
import com.jverbruggen.jcommon.virtualentity.*;
import com.jverbruggen.jcommon.virtualentity.id.EntityIdFactory;
import com.jverbruggen.jcommon.virtualentity.render.GlobalViewport;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class GlobalViewportManager implements ViewportManager {
    private final GlobalViewport globalViewport;
    private final PacketSender packetSender;
    private final EntityIdFactory entityIdFactory;
    private final HashMap<Integer, VirtualEntity> entities;

    private final int renderDistance;
    private final int renderChunkSize;

    public GlobalViewportManager(GlobalViewport globalViewport, PacketSender packetSender, EntityIdFactory entityIdFactory,
                                 int renderDistance, int renderChunkSize) {
        this.globalViewport = globalViewport;
        this.packetSender = packetSender;
        this.entityIdFactory = entityIdFactory;
        this.entities = new HashMap<>();

        this.renderDistance = renderDistance;
        this.renderChunkSize = renderChunkSize;
    }

    private void addEntity(VirtualEntity entity){
        entities.put(entity.getEntityId(), entity);
    }

    private void removeEntity(int entityId){
        entities.remove(entityId);
    }

    public VirtualEntity getEntity(int entityId){
        return entities.get(entityId);
    }

    @Override
    public int getRenderChunkSize() {
        return renderChunkSize;
    }

    @Override
    public int getRenderDistance() {
        return renderDistance;
    }

    @Override
    public void updateVisuals(Player player) {
        updateVisuals(player, player.getLocation());
    }

    @Override
    public void updateVisuals(Player player, Vector3 playerLocation) {
        globalViewport.updateFor(player, playerLocation);
    }

    @Override
    public void updateForEntity(VirtualEntity virtualEntity) {
        globalViewport.updateEntityViewers(virtualEntity);
    }

    @Override
    public VirtualEntity spawnModelEntity(Vector3 location, Model model) {
        return spawnVirtualArmorstand(location, new Quaternion(), model, VirtualArmorstandConfiguration.createDefault());
    }

    @Override
    public VirtualEntity spawnModelEntity(Vector3 location, Quaternion rotation, Model model, String customName) {
        return spawnVirtualArmorstand(location, rotation, model, VirtualArmorstandConfiguration.createWithName(customName));
    }

    @Override
    public VirtualEntity spawnVirtualBukkitEntity(Vector3 location, EntityType entityType) {
        return spawnVirtualBukkitEntity(location, entityType, 0);
    }

    @Override
    public VirtualEntity spawnVirtualBukkitEntity(Vector3 location, EntityType entityType, double yawRotation) {
        int entityId = entityIdFactory.newId();
        VirtualEntity virtualEntity = new VirtualBukkitEntity(packetSender, this, location, entityType, yawRotation, entityId);

        addEntity(virtualEntity);

        updateForEntity(virtualEntity);
        return virtualEntity;
    }

    @Override
    public VirtualArmorStand spawnSeatEntity(Vector3 location, double yawRotation, Model model){
        int entityId = entityIdFactory.newId();
        Quaternion rotation = new Quaternion();
        VirtualArmorStand virtualArmorstand = new YawRotatedVirtualArmorstand(packetSender, this, location, rotation, yawRotation, entityId, VirtualArmorstandConfiguration.createDefault());
        if(model != null){
            virtualArmorstand.setModel(model);
        }

        addEntity(virtualArmorstand);

        updateForEntity(virtualArmorstand);
        return virtualArmorstand;
    }

    @Override
    public VirtualArmorStand spawnVirtualArmorstand(Vector3 location) {
        return spawnVirtualArmorstand(location, new Quaternion(), null, VirtualArmorstandConfiguration.createDefault());
    }

    @Override
    public VirtualArmorStand spawnVirtualArmorstand(Vector3 location, double yawRotation) {
        return spawnVirtualArmorstand(location, yawRotation);
    }

    @Override
    public VirtualArmorStand spawnVirtualArmorstand(Vector3 location, Quaternion rotation, Model model, VirtualArmorstandConfiguration configuration) {
        return spawnVirtualArmorstand(location, rotation, 0, model, configuration);
    }

    @Override
    public VirtualArmorStand spawnVirtualArmorstand(Vector3 location, Quaternion rotation, double yawRotation, Model model, VirtualArmorstandConfiguration configuration) {
        int entityId = entityIdFactory.newId();
        VirtualArmorStand virtualArmorstand = new VirtualArmorStand(packetSender, this, location, rotation, yawRotation, entityId, configuration);
        if(model != null){
            virtualArmorstand.setModel(model);
        }

        addEntity(virtualArmorstand);

        updateForEntity(virtualArmorstand);
        return virtualArmorstand;
    }

    @Override
    public void despawnAll() {
        for(ViewedByPlayer viewedByPlayer : globalViewport.getEntities()){
            removeEntity(viewedByPlayer.getEntityId());
            viewedByPlayer.despawn();
        }
    }

    private void flushDeadEntities(){
        globalViewport.flushDeadEntities();
    }
}
