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

package com.jverbruggen.jcommon.packet.objects.v1_20;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.jverbruggen.jcommon.math.Vector3;
import com.jverbruggen.jcommon.packet.objects.SingularServerPacket;
import com.jverbruggen.jcommon.virtualentity.ArmorstandRotationBone;

public class ArmorstandRotationServerPacket extends SingularServerPacket {
    protected final int entityId;
    protected final ArmorstandRotationBone rotationBone;
    protected final Vector3 rotation;

    public ArmorstandRotationServerPacket(ProtocolManager protocolManager, int entityId, ArmorstandRotationBone rotationBone, Vector3 rotation) {
        super(protocolManager);
        this.entityId = entityId;
        this.rotationBone = rotationBone;
        this.rotation = rotation;
    }

    @Override
    public PacketContainer getPacket() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, this.entityId);

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.getVectorSerializer();
        watcher.setObject(getRotationType(rotationBone), serializer, rotation.toVector3F());
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        return packet;
    }

    public int getRotationType(ArmorstandRotationBone armorstandRotationBone){
        return switch (armorstandRotationBone){
            case HEAD -> 16;
            case BODY -> 17;
            case OFF_HAND -> 18;
            case MAIN_HAND -> 19;
            case LEFT_LEG -> 20;
            case RIGHT_LEG -> 21;
        };
    }
}
