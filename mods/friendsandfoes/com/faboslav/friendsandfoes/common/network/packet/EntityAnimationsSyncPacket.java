package com.faboslav.friendsandfoes.common.network.packet;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.AnimationDefinition;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationLoader;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationParser;
import com.faboslav.friendsandfoes.common.events.lifecycle.DatapackSyncEvent;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.mojang.serialization.DataResult;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record EntityAnimationsSyncPacket(Map<ResourceLocation, AnimationDefinition> entityAnimations) implements Packet<EntityAnimationsSyncPacket> {
   public static final ResourceLocation ID = FriendsAndFoes.makeID("entity_animations_sync_packet");
   public static final ClientboundPacketType<EntityAnimationsSyncPacket> TYPE = new EntityAnimationsSyncPacket.Handler();

   public static void sendToClient(DatapackSyncEvent event) {
      Map<ResourceLocation, AnimationDefinition> entityAnimations = AnimationLoader.INSTANCE
         .getAnimations()
         .entrySet()
         .stream()
         .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().get()));
      MessageHandler.DEFAULT_CHANNEL.sendToPlayer(new EntityAnimationsSyncPacket(entityAnimations), event.player());
   }

   public PacketType<EntityAnimationsSyncPacket> type() {
      return TYPE;
   }

   public static class Handler implements ClientboundPacketType<EntityAnimationsSyncPacket> {
      public ResourceLocation id() {
         return EntityAnimationsSyncPacket.ID;
      }

      public Runnable handle(EntityAnimationsSyncPacket packet) {
         return () -> AnimationLoader.INSTANCE.apply(packet.entityAnimations());
      }

      public EntityAnimationsSyncPacket decode(RegistryFriendlyByteBuf buf) {
         Map<ResourceLocation, AnimationDefinition> parsedEntityAnimations = new HashMap<>();
         CompoundTag data = buf.readNbt();
         if (data == null) {
            FriendsAndFoes.getLogger().error("Entity Animation packet is empty");
            return new EntityAnimationsSyncPacket(parsedEntityAnimations);
         }

         ListTag entityAnimations = data.getList("entity_animations", 10);

         for (int i = 0; i < entityAnimations.size(); i++) {
            CompoundTag entityAnimation = entityAnimations.getCompound(i);
            Tag resourceLocationTag = entityAnimation.get("resource_location");
            Tag animationDefinitionTag = entityAnimation.get("animation_definition");
            if (resourceLocationTag != null && animationDefinitionTag != null) {
               DataResult<ResourceLocation> parsedResourceLocation = ResourceLocation.CODEC.parse(NbtOps.INSTANCE, resourceLocationTag);
               parsedResourceLocation.error()
                  .ifPresent(error -> FriendsAndFoes.getLogger().error("Failed to parse Resource Location packet entry: {}", error.message()));
               DataResult<AnimationDefinition> parsedAnimationDefinition = AnimationParser.CODEC.parse(NbtOps.INSTANCE, animationDefinitionTag);
               parsedAnimationDefinition.error()
                  .ifPresent(error -> FriendsAndFoes.getLogger().error("Failed to parse Animation Definition packet entry: {}", error.message()));
               parsedResourceLocation.result()
                  .ifPresent(
                     validResourceLocation -> parsedAnimationDefinition.result()
                        .ifPresent(validAnimationDefinition -> parsedEntityAnimations.put(validResourceLocation, validAnimationDefinition))
                  );
            } else {
               FriendsAndFoes.getLogger().error("Entity Animation packet is invalid");
            }
         }

         return new EntityAnimationsSyncPacket(parsedEntityAnimations);
      }

      public void encode(EntityAnimationsSyncPacket packet, RegistryFriendlyByteBuf buf) {
         CompoundTag data = new CompoundTag();
         ListTag entityAnimationsList = new ListTag();

         for (Entry<ResourceLocation, AnimationDefinition> entry : packet.entityAnimations().entrySet()) {
            CompoundTag animationEntry = new CompoundTag();
            animationEntry.putString("resource_location", entry.getKey().toString());
            DataResult<Tag> encodedDefinition = AnimationParser.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue());
            encodedDefinition.error()
               .ifPresent(error -> FriendsAndFoes.getLogger().error("Failed to encode Animation Definition for {}: {}", entry.getKey(), error.message()));
            encodedDefinition.result().ifPresent(tag -> animationEntry.put("animation_definition", tag));
            entityAnimationsList.add(animationEntry);
         }

         data.put("entity_animations", entityAnimationsList);
         buf.writeNbt(data);
      }
   }
}
