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

import com.jverbruggen.jcommon.math.ArmorStandPose;
import com.jverbruggen.jcommon.packet.sender.ModelSlot;
import com.jverbruggen.jcommon.player.Player;
import com.jverbruggen.jcommon.interaction.PlayerInteractVirtualEntityAction;
import com.jverbruggen.jcommon.math.Quaternion;
import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.packet.sender.PacketSender;
import com.jverbruggen.jcommon.serviceprovider.CommonServiceProvider;
import com.jverbruggen.jcommon.sync.TaskScheduler;
import com.jverbruggen.jcommon.virtualentity.render.Viewer;

import java.util.List;

public class VirtualArmorStand extends BaseVirtualEntity implements VirtualEntity, HasOrientation, HasAction {
    private static Vector3 ARMORSTAND_MODEL_COMPENSATION = null;

    private final TaskScheduler taskScheduler;

    private final Quaternion orientation;
    private final VirtualArmorstandConfiguration virtualArmorstandConfiguration;
    private PlayerInteractVirtualEntityAction action;

    public VirtualArmorStand(CommonServiceProvider serviceProvider, Vector3 location, Quaternion orientation, double yawRotation, int entityId, VirtualArmorstandConfiguration virtualArmorstandConfiguration, VirtualArmorstandConfiguration virtualArmorstandConfiguration1) {
        super(serviceProvider, location, yawRotation, entityId);

        this.taskScheduler = serviceProvider._getSingleton(TaskScheduler.class);

        this.virtualArmorstandConfiguration = virtualArmorstandConfiguration1;
        this.orientation = orientation;

        this.fillArmorstandCompensationVector();
    }

    private void fillArmorstandCompensationVector(){
        if(ARMORSTAND_MODEL_COMPENSATION == null)
            ARMORSTAND_MODEL_COMPENSATION = packetSender.getArmorstandModelCompensationVector();
    }

    @Override
    public void spawnFor(Viewer viewer, PacketSender packetSender) {
        addViewer(viewer);

        if(!isRendered()) return;

        packetSender.sendSpawnVirtualArmorstandForPacket(viewer, this);

        setHeadPose(ArmorStandPose.getArmorStandPose(this.orientation));

        if(getSeatedPlayer() != null){
            taskScheduler.runTaskLater(() -> packetSender.sendSeatedPlayerPacket(List.of(viewer), getSeatedPlayer(), this), 1L);
        }
    }

    @Override
    public boolean hasAction() {
        return action != null;
    }

    @Override
    public void setAction(PlayerInteractVirtualEntityAction action) {
        this.action = action;
    }

    @Override
    public void runCustomAction(Player player) {
        if(hasAction())
            this.action.run(this, player);
    }

    @Override
    public Vector3 getLocation() {
        Vector3 actualArmorstandLocation = super.getLocation();
        return Vector3.add(actualArmorstandLocation, ARMORSTAND_MODEL_COMPENSATION);
    }

    @Override
    public void setLocation(Vector3 newLocation) {
        Vector3 actualArmorstandLocation = Vector3.subtract(newLocation, ARMORSTAND_MODEL_COMPENSATION);
        super.setLocation(actualArmorstandLocation);

        if(actualArmorstandLocation == null) return;

        syncSeatedPlayer(newLocation);
    }

    @Override
    public Quaternion getOrientation() {
        return orientation.clone();
    }

    @Override
    public void setOrientation(Quaternion orientation) {
        if(orientation == null) return;

        this.orientation.copyFrom(orientation);
        setHeadPose(ArmorStandPose.getArmorStandPose(orientation));
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);
        packetSender.sendAddModelToEntityPacket(viewers, this, ModelSlot.HEAD, model);
    }

    public VirtualArmorstandConfiguration getConfiguration() {
        return virtualArmorstandConfiguration;
    }

    protected void setHeadPose(Vector3 rotation) {
        virtualArmorstandConfiguration.rotations().setHead(rotation);
        packetSender.sendRotationPacket(viewers, this, ArmorstandRotationBone.HEAD, rotation);
    }

    @Override
    protected void moveEntity(Vector3 delta, double yawRotation) {
        packetSender.moveVirtualArmorstand(this.getViewers(), getEntityId(), delta, yawRotation);
    }

    @Override
    protected void teleportEntity(Vector3 newLocation) {
        packetSender.teleportVirtualEntity(this.getViewers(), getEntityId(), newLocation);
    }
}
