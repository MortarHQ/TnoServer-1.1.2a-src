package net.witherspawnanimation.network;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import net.witherspawnanimation.WitherSpawnAnimationMod;

@EventBusSubscriber(bus = Bus.MOD)
public class WitherSpawnAnimationModVariables {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(Keys.ATTACHMENT_TYPES, "wither_spawn_animation");

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      WitherSpawnAnimationMod.addNetworkMessage(
         WitherSpawnAnimationModVariables.SavedDataSyncMessage.TYPE,
         WitherSpawnAnimationModVariables.SavedDataSyncMessage.STREAM_CODEC,
         WitherSpawnAnimationModVariables.SavedDataSyncMessage::handleData
      );
   }

   @EventBusSubscriber
   public static class EventBusVariableHandlers {
      @SubscribeEvent
      public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            SavedData mapdata = WitherSpawnAnimationModVariables.MapVariables.get(event.getEntity().level());
            SavedData worlddata = WitherSpawnAnimationModVariables.WorldVariables.get(event.getEntity().level());
            if (mapdata != null) {
               PacketDistributor.sendToPlayer(player, new WitherSpawnAnimationModVariables.SavedDataSyncMessage(0, mapdata), new CustomPacketPayload[0]);
            }

            if (worlddata != null) {
               PacketDistributor.sendToPlayer(player, new WitherSpawnAnimationModVariables.SavedDataSyncMessage(1, worlddata), new CustomPacketPayload[0]);
            }
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            SavedData worlddata = WitherSpawnAnimationModVariables.WorldVariables.get(event.getEntity().level());
            if (worlddata != null) {
               PacketDistributor.sendToPlayer(player, new WitherSpawnAnimationModVariables.SavedDataSyncMessage(1, worlddata), new CustomPacketPayload[0]);
            }
         }
      }
   }

   public static class MapVariables extends SavedData {
      public static final String DATA_NAME = "wither_spawn_animation_mapvars";
      public boolean spawn_fix = false;
      static WitherSpawnAnimationModVariables.MapVariables clientSide = new WitherSpawnAnimationModVariables.MapVariables();

      public static WitherSpawnAnimationModVariables.MapVariables load(CompoundTag tag, Provider lookupProvider) {
         WitherSpawnAnimationModVariables.MapVariables data = new WitherSpawnAnimationModVariables.MapVariables();
         data.read(tag, lookupProvider);
         return data;
      }

      public void read(CompoundTag nbt, Provider lookupProvider) {
         this.spawn_fix = nbt.getBoolean("spawn_fix");
      }

      public CompoundTag save(CompoundTag nbt, Provider lookupProvider) {
         nbt.putBoolean("spawn_fix", this.spawn_fix);
         return nbt;
      }

      public void syncData(LevelAccessor world) {
         this.setDirty();
         if (world instanceof Level && !world.isClientSide()) {
            PacketDistributor.sendToAllPlayers(new WitherSpawnAnimationModVariables.SavedDataSyncMessage(0, this), new CustomPacketPayload[0]);
         }
      }

      public static WitherSpawnAnimationModVariables.MapVariables get(LevelAccessor world) {
         return world instanceof ServerLevelAccessor serverLevelAcc
            ? (WitherSpawnAnimationModVariables.MapVariables)serverLevelAcc.getLevel()
               .getServer()
               .getLevel(Level.OVERWORLD)
               .getDataStorage()
               .computeIfAbsent(
                  new Factory(WitherSpawnAnimationModVariables.MapVariables::new, WitherSpawnAnimationModVariables.MapVariables::load),
                  "wither_spawn_animation_mapvars"
               )
            : clientSide;
      }
   }

   public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload {
      public static final Type<WitherSpawnAnimationModVariables.SavedDataSyncMessage> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("wither_spawn_animation", "saved_data_sync")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, WitherSpawnAnimationModVariables.SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of(
         (buffer, message) -> {
            buffer.writeInt(message.dataType);
            if (message.data != null) {
               buffer.writeNbt(message.data.save(new CompoundTag(), buffer.registryAccess()));
            }
         }, buffer -> {
            int dataType = buffer.readInt();
            CompoundTag nbt = buffer.readNbt();
            SavedData data = null;
            if (nbt != null) {
               data = (SavedData)(dataType == 0 ? new WitherSpawnAnimationModVariables.MapVariables() : new WitherSpawnAnimationModVariables.WorldVariables());
               if (data instanceof WitherSpawnAnimationModVariables.MapVariables mapVariables) {
                  mapVariables.read(nbt, buffer.registryAccess());
               } else if (data instanceof WitherSpawnAnimationModVariables.WorldVariables worldVariables) {
                  worldVariables.read(nbt, buffer.registryAccess());
               }
            }

            return new WitherSpawnAnimationModVariables.SavedDataSyncMessage(dataType, data);
         }
      );

      public Type<WitherSpawnAnimationModVariables.SavedDataSyncMessage> type() {
         return TYPE;
      }

      public static void handleData(WitherSpawnAnimationModVariables.SavedDataSyncMessage message, IPayloadContext context) {
         if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
            context.enqueueWork(
                  () -> {
                     if (message.dataType == 0) {
                        WitherSpawnAnimationModVariables.MapVariables.clientSide
                           .read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
                     } else {
                        WitherSpawnAnimationModVariables.WorldVariables.clientSide
                           .read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
                     }
                  }
               )
               .exceptionally(e -> {
                  context.connection().disconnect(Component.literal(e.getMessage()));
                  return null;
               });
         }
      }
   }

   public static class WorldVariables extends SavedData {
      public static final String DATA_NAME = "wither_spawn_animation_worldvars";
      static WitherSpawnAnimationModVariables.WorldVariables clientSide = new WitherSpawnAnimationModVariables.WorldVariables();

      public static WitherSpawnAnimationModVariables.WorldVariables load(CompoundTag tag, Provider lookupProvider) {
         WitherSpawnAnimationModVariables.WorldVariables data = new WitherSpawnAnimationModVariables.WorldVariables();
         data.read(tag, lookupProvider);
         return data;
      }

      public void read(CompoundTag nbt, Provider lookupProvider) {
      }

      public CompoundTag save(CompoundTag nbt, Provider lookupProvider) {
         return nbt;
      }

      public void syncData(LevelAccessor world) {
         this.setDirty();
         if (world instanceof ServerLevel level) {
            PacketDistributor.sendToPlayersInDimension(level, new WitherSpawnAnimationModVariables.SavedDataSyncMessage(1, this), new CustomPacketPayload[0]);
         }
      }

      public static WitherSpawnAnimationModVariables.WorldVariables get(LevelAccessor world) {
         return world instanceof ServerLevel level
            ? (WitherSpawnAnimationModVariables.WorldVariables)level.getDataStorage()
               .computeIfAbsent(
                  new Factory(WitherSpawnAnimationModVariables.WorldVariables::new, WitherSpawnAnimationModVariables.WorldVariables::load),
                  "wither_spawn_animation_worldvars"
               )
            : clientSide;
      }
   }
}
