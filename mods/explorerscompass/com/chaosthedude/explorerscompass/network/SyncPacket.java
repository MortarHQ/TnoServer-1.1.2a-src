package com.chaosthedude.explorerscompass.network;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncPacket(
   boolean canTeleport,
   int maxNextSearches,
   boolean infiniteXp,
   List<ResourceLocation> allowedStructureKeys,
   Map<ResourceLocation, Integer> xpLevelsForAllowedStructureKeys,
   ListMultimap<ResourceLocation, ResourceLocation> dimensionKeysForAllowedStructureKeys,
   Map<ResourceLocation, ResourceLocation> structureKeysToTypeKeys,
   ListMultimap<ResourceLocation, ResourceLocation> typeKeysToStructureKeys
) implements CustomPacketPayload {
   public static final Type<SyncPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("explorerscompass", "sync"));
   public static final StreamCodec<FriendlyByteBuf, SyncPacket> CODEC = StreamCodec.ofMember(SyncPacket::write, SyncPacket::read);

   public static SyncPacket read(FriendlyByteBuf buf) {
      boolean canTeleport = buf.readBoolean();
      int maxNextSearches = buf.readInt();
      boolean infiniteXp = buf.readBoolean();
      List<ResourceLocation> allowedStructureKeys = new ArrayList<>();
      Map<ResourceLocation, Integer> xpLevelsForAllowedStructureKeys = new HashMap<>();
      ListMultimap<ResourceLocation, ResourceLocation> dimensionKeysForAllowedStructureKeys = ArrayListMultimap.create();
      Map<ResourceLocation, ResourceLocation> structureKeysToTypeKeys = new HashMap<>();
      ListMultimap<ResourceLocation, ResourceLocation> typeKeysToStructureKeys = ArrayListMultimap.create();
      int numStructures = buf.readInt();

      for (int i = 0; i < numStructures; i++) {
         ResourceLocation structureKey = buf.readResourceLocation();
         int numDimensions = buf.readInt();
         List<ResourceLocation> dimensions = new ArrayList<>();

         for (int j = 0; j < numDimensions; j++) {
            dimensions.add(buf.readResourceLocation());
         }

         ResourceLocation typeKey = buf.readResourceLocation();
         int xpLevels = buf.readInt();
         if (structureKey != null) {
            allowedStructureKeys.add(structureKey);
            dimensionKeysForAllowedStructureKeys.putAll(structureKey, dimensions);
            structureKeysToTypeKeys.put(structureKey, typeKey);
            xpLevelsForAllowedStructureKeys.put(structureKey, xpLevels);
         }
      }

      int numTypes = buf.readInt();

      for (int i = 0; i < numTypes; i++) {
         ResourceLocation typeKey = buf.readResourceLocation();
         int numStructuresToAdd = buf.readInt();

         for (int j = 0; j < numStructuresToAdd; j++) {
            ResourceLocation structureKey = buf.readResourceLocation();
            typeKeysToStructureKeys.put(typeKey, structureKey);
         }
      }

      return new SyncPacket(
         canTeleport,
         maxNextSearches,
         infiniteXp,
         allowedStructureKeys,
         xpLevelsForAllowedStructureKeys,
         dimensionKeysForAllowedStructureKeys,
         structureKeysToTypeKeys,
         typeKeysToStructureKeys
      );
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeBoolean(this.canTeleport);
      buf.writeInt(this.maxNextSearches);
      buf.writeBoolean(this.infiniteXp);
      buf.writeInt(this.allowedStructureKeys.size());

      for (ResourceLocation structureKey : this.allowedStructureKeys) {
         buf.writeResourceLocation(structureKey);
         List<ResourceLocation> dimensions = this.dimensionKeysForAllowedStructureKeys.get(structureKey);
         buf.writeInt(dimensions.size());

         for (ResourceLocation dimensionKey : dimensions) {
            buf.writeResourceLocation(dimensionKey);
         }

         ResourceLocation typeKey = this.structureKeysToTypeKeys.get(structureKey);
         buf.writeResourceLocation(typeKey);
         int xpLevels = this.xpLevelsForAllowedStructureKeys.get(structureKey);
         buf.writeInt(xpLevels);
      }

      buf.writeInt(this.typeKeysToStructureKeys.keySet().size());

      for (ResourceLocation typeKey : this.typeKeysToStructureKeys.keySet()) {
         buf.writeResourceLocation(typeKey);
         List<ResourceLocation> structureKeys = this.typeKeysToStructureKeys.get(typeKey);
         buf.writeInt(structureKeys.size());

         for (ResourceLocation structureKey : structureKeys) {
            buf.writeResourceLocation(structureKey);
         }
      }
   }

   public static void handle(SyncPacket packet, IPayloadContext context) {
      if (context.flow().isClientbound()) {
         context.enqueueWork(() -> {
            ExplorersCompass.synced = true;
            canTeleport = packet.canTeleport;
            maxNextSearches = packet.maxNextSearches;
            infiniteXp = packet.infiniteXp;
            allowedStructureKeys = packet.allowedStructureKeys;
            xpLevelsForAllowedStructureKeys = packet.xpLevelsForAllowedStructureKeys;
            dimensionKeysForAllowedStructureKeys = packet.dimensionKeysForAllowedStructureKeys;
            structureKeysToTypeKeys = packet.structureKeysToTypeKeys;
            typeKeysToStructureKeys = packet.typeKeysToStructureKeys;
         });
      }
   }

   public Type<SyncPacket> type() {
      return TYPE;
   }
}
