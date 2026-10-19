package fuzs.puzzleslib.impl.attachment.builder;

import fuzs.puzzleslib.api.attachment.v4.DataAttachmentRegistry;
import fuzs.puzzleslib.api.event.v1.entity.player.AfterChangeDimensionCallback;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerCopyEvents;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerNetworkEvents;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerTrackingEvents;
import fuzs.puzzleslib.api.network.v3.PlayerSet;
import fuzs.puzzleslib.impl.attachment.AttachmentTypeAdapter;
import fuzs.puzzleslib.impl.attachment.ClientboundEntityDataAttachmentMessage;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface EntityDataAttachmentBuilder<V> extends DataAttachmentRegistry.EntityBuilder<V> {
   @Nullable
   default BiConsumer<Entity, V> getSynchronizer(
      ResourceLocation resourceLocation,
      AttachmentTypeAdapter<Entity, V> attachmentType,
      @Nullable StreamCodec<? super RegistryFriendlyByteBuf, V> streamCodec,
      @Nullable Function<Entity, PlayerSet> synchronizationTargets
   ) {
      if (streamCodec == null) {
         return null;
      }

      Type<ClientboundEntityDataAttachmentMessage<V>> type = new Type(resourceLocation);
      this.registerPayloadHandlers(resourceLocation, attachmentType, type, streamCodec);
      this.registerEventHandlers(attachmentType, type, synchronizationTargets);
      return this.getDefaultSynchronizer(type, synchronizationTargets);
   }

   void registerPayloadHandlers(
      ResourceLocation var1,
      AttachmentTypeAdapter<Entity, V> var2,
      Type<ClientboundEntityDataAttachmentMessage<V>> var3,
      @Nullable StreamCodec<? super RegistryFriendlyByteBuf, V> var4
   );

   private void registerEventHandlers(
      AttachmentTypeAdapter<Entity, V> attachmentType,
      Type<ClientboundEntityDataAttachmentMessage<V>> type,
      @Nullable Function<Entity, PlayerSet> synchronizationTargets
   ) {
      PlayerNetworkEvents.LOGGED_IN.register(serverPlayer -> this.broadcast(type, serverPlayer, attachmentType));
      AfterChangeDimensionCallback.EVENT.register((serverPlayer, from, to) -> this.broadcast(type, serverPlayer, attachmentType));
      PlayerCopyEvents.RESPAWN.register((serverPlayer, originalStillAlive) -> this.broadcast(type, serverPlayer, attachmentType));
      if (synchronizationTargets != null) {
         PlayerTrackingEvents.START
            .register((trackedEntity, serverPlayer) -> this.broadcast(type, trackedEntity, PlayerSet.ofPlayer(serverPlayer), attachmentType));
      }
   }

   private BiConsumer<Entity, V> getDefaultSynchronizer(
      Type<ClientboundEntityDataAttachmentMessage<V>> type, @Nullable Function<Entity, PlayerSet> synchronizationTargets
   ) {
      return (entity, value) -> {
         PlayerSet playerSet;
         if (synchronizationTargets != null) {
            playerSet = synchronizationTargets.apply(entity);
         } else {
            playerSet = PlayerSet.ofEntity(entity);
         }

         this.broadcast(type, entity, playerSet, value);
      };
   }

   private void broadcast(Type<ClientboundEntityDataAttachmentMessage<V>> type, ServerPlayer serverPlayer, AttachmentTypeAdapter<Entity, V> attachmentType) {
      this.broadcast(type, serverPlayer, PlayerSet.ofPlayer(serverPlayer), attachmentType);
   }

   private void broadcast(
      Type<ClientboundEntityDataAttachmentMessage<V>> type, Entity entity, PlayerSet playerSet, AttachmentTypeAdapter<Entity, V> attachmentType
   ) {
      if (attachmentType.hasData(entity)) {
         this.broadcast(type, entity, playerSet, attachmentType.getData(entity));
      }
   }

   private void broadcast(Type<ClientboundEntityDataAttachmentMessage<V>> type, Entity entity, PlayerSet playerSet, @Nullable V value) {
      ClientboundEntityDataAttachmentMessage<V> message = new ClientboundEntityDataAttachmentMessage<>(type, entity.getId(), Optional.ofNullable(value));
      playerSet.broadcast(type, new ClientboundCustomPayloadPacket(message));
   }
}
