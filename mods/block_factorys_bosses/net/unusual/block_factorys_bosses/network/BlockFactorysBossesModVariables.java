package net.unusual.block_factorys_bosses.network;

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
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;

@EventBusSubscriber
public class BlockFactorysBossesModVariables {
   public static double cinematicX = 0.0;
   public static double cinematicY = 0.0;
   public static double cinematicZ = 0.0;

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      BossesRise.addNetworkMessage(
         BlockFactorysBossesModVariables.SavedDataSyncMessage.TYPE,
         BlockFactorysBossesModVariables.SavedDataSyncMessage.STREAM_CODEC,
         BlockFactorysBossesModVariables.SavedDataSyncMessage::handleData
      );
      BossesRise.addNetworkMessage(
         BlockFactorysBossesModVariables.PlayerVariablesSyncMessage.TYPE,
         BlockFactorysBossesModVariables.PlayerVariablesSyncMessage.STREAM_CODEC,
         BlockFactorysBossesModVariables.PlayerVariablesSyncMessage::handleData
      );
   }

   @EventBusSubscriber
   public static class EventBusVariableHandlers {
      @SubscribeEvent
      public static void onPlayerLoggedInSyncPlayerVariables(PlayerLoggedInEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            ((PlayerVariables)player.getData(BossesRiseDataAttachments.PLAYER_VARIABLES)).syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerRespawnedSyncPlayerVariables(PlayerRespawnEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            ((PlayerVariables)player.getData(BossesRiseDataAttachments.PLAYER_VARIABLES)).syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerChangedDimensionEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            ((PlayerVariables)player.getData(BossesRiseDataAttachments.PLAYER_VARIABLES)).syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void clonePlayer(Clone event) {
         PlayerVariables original = (PlayerVariables)event.getOriginal().getData(BossesRiseDataAttachments.PLAYER_VARIABLES);
         PlayerVariables clone = new PlayerVariables();
         clone.random = original.random;
         clone.boss_no_hit = original.boss_no_hit;
         event.getEntity().setData(BossesRiseDataAttachments.PLAYER_VARIABLES, clone);
      }

      @SubscribeEvent
      public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            SavedData mapdata = BlockFactorysBossesModVariables.MapVariables.get(event.getEntity().level());
            SavedData worlddata = BlockFactorysBossesModVariables.WorldVariables.get(event.getEntity().level());
            if (mapdata != null) {
               PacketDistributor.sendToPlayer(player, new BlockFactorysBossesModVariables.SavedDataSyncMessage(0, mapdata), new CustomPacketPayload[0]);
            }

            if (worlddata != null) {
               PacketDistributor.sendToPlayer(player, new BlockFactorysBossesModVariables.SavedDataSyncMessage(1, worlddata), new CustomPacketPayload[0]);
            }
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            SavedData worlddata = BlockFactorysBossesModVariables.WorldVariables.get(event.getEntity().level());
            if (worlddata != null) {
               PacketDistributor.sendToPlayer(player, new BlockFactorysBossesModVariables.SavedDataSyncMessage(1, worlddata), new CustomPacketPayload[0]);
            }
         }
      }
   }

   public static class MapVariables extends SavedData {
      public static final String DATA_NAME = "block_factorys_bosses_mapvars";
      public double debugX = 0.0;
      public double debugY = 0.0;
      public double debugZ = 0.0;
      static BlockFactorysBossesModVariables.MapVariables clientSide = new BlockFactorysBossesModVariables.MapVariables();

      public static BlockFactorysBossesModVariables.MapVariables load(CompoundTag tag, Provider lookupProvider) {
         BlockFactorysBossesModVariables.MapVariables data = new BlockFactorysBossesModVariables.MapVariables();
         data.read(tag, lookupProvider);
         return data;
      }

      public void read(CompoundTag nbt, Provider lookupProvider) {
         this.debugX = nbt.getDouble("debugX");
         this.debugY = nbt.getDouble("debugY");
         this.debugZ = nbt.getDouble("debugZ");
      }

      public CompoundTag save(CompoundTag nbt, Provider lookupProvider) {
         nbt.putDouble("debugX", this.debugX);
         nbt.putDouble("debugY", this.debugY);
         nbt.putDouble("debugZ", this.debugZ);
         return nbt;
      }

      public void syncData(LevelAccessor world) {
         this.setDirty();
         if (world instanceof Level && !world.isClientSide()) {
            PacketDistributor.sendToAllPlayers(new BlockFactorysBossesModVariables.SavedDataSyncMessage(0, this), new CustomPacketPayload[0]);
         }
      }

      public static BlockFactorysBossesModVariables.MapVariables get(LevelAccessor world) {
         return world instanceof ServerLevelAccessor serverLevelAcc
            ? (BlockFactorysBossesModVariables.MapVariables)serverLevelAcc.getLevel()
               .getServer()
               .getLevel(Level.OVERWORLD)
               .getDataStorage()
               .computeIfAbsent(
                  new Factory(BlockFactorysBossesModVariables.MapVariables::new, BlockFactorysBossesModVariables.MapVariables::load),
                  "block_factorys_bosses_mapvars"
               )
            : clientSide;
      }
   }

   public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
      public static final Type<BlockFactorysBossesModVariables.PlayerVariablesSyncMessage> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "player_variables_sync")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, BlockFactorysBossesModVariables.PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec.of(
         (buffer, message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())),
         buffer -> {
            BlockFactorysBossesModVariables.PlayerVariablesSyncMessage message = new BlockFactorysBossesModVariables.PlayerVariablesSyncMessage(
               new PlayerVariables()
            );
            message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
            return message;
         }
      );

      public Type<BlockFactorysBossesModVariables.PlayerVariablesSyncMessage> type() {
         return TYPE;
      }

      public static void handleData(BlockFactorysBossesModVariables.PlayerVariablesSyncMessage message, IPayloadContext context) {
         if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
            context.enqueueWork(() -> BlockFactorysBossesModVariables.PlayerVariablesSyncMessage.ClientHandler.handle(message, context)).exceptionally(e -> {
               context.connection().disconnect(Component.literal(e.getMessage()));
               return null;
            });
         }
      }

      private interface ClientHandler {
         static void handle(BlockFactorysBossesModVariables.PlayerVariablesSyncMessage message, IPayloadContext context) {
            ((PlayerVariables)context.player().getData(BossesRiseDataAttachments.PLAYER_VARIABLES))
               .deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()));
         }
      }
   }

   public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload {
      public static final Type<BlockFactorysBossesModVariables.SavedDataSyncMessage> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "saved_data_sync")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, BlockFactorysBossesModVariables.SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of(
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
               data = (SavedData)(dataType == 0 ? new BlockFactorysBossesModVariables.MapVariables() : new BlockFactorysBossesModVariables.WorldVariables());
               if (data instanceof BlockFactorysBossesModVariables.MapVariables mapVariables) {
                  mapVariables.read(nbt, buffer.registryAccess());
               } else if (data instanceof BlockFactorysBossesModVariables.WorldVariables worldVariables) {
                  worldVariables.read(nbt, buffer.registryAccess());
               }
            }

            return new BlockFactorysBossesModVariables.SavedDataSyncMessage(dataType, data);
         }
      );

      public Type<BlockFactorysBossesModVariables.SavedDataSyncMessage> type() {
         return TYPE;
      }

      public static void handleData(BlockFactorysBossesModVariables.SavedDataSyncMessage message, IPayloadContext context) {
         if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
            context.enqueueWork(() -> BlockFactorysBossesModVariables.SavedDataSyncMessage.ClientHandler.handle(message, context)).exceptionally(e -> {
               context.connection().disconnect(Component.literal(e.getMessage()));
               return null;
            });
         }
      }

      private interface ClientHandler {
         static void handle(BlockFactorysBossesModVariables.SavedDataSyncMessage message, IPayloadContext context) {
            if (message.dataType == 0) {
               BlockFactorysBossesModVariables.MapVariables.clientSide
                  .read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
            } else {
               BlockFactorysBossesModVariables.WorldVariables.clientSide
                  .read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
            }
         }
      }
   }

   public static class WorldVariables extends SavedData {
      public static final String DATA_NAME = "block_factorys_bosses_worldvars";
      static BlockFactorysBossesModVariables.WorldVariables clientSide = new BlockFactorysBossesModVariables.WorldVariables();

      public static BlockFactorysBossesModVariables.WorldVariables load(CompoundTag tag, Provider lookupProvider) {
         BlockFactorysBossesModVariables.WorldVariables data = new BlockFactorysBossesModVariables.WorldVariables();
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
            PacketDistributor.sendToPlayersInDimension(level, new BlockFactorysBossesModVariables.SavedDataSyncMessage(1, this), new CustomPacketPayload[0]);
         }
      }

      public static BlockFactorysBossesModVariables.WorldVariables get(LevelAccessor world) {
         return world instanceof ServerLevel level
            ? (BlockFactorysBossesModVariables.WorldVariables)level.getDataStorage()
               .computeIfAbsent(
                  new Factory(BlockFactorysBossesModVariables.WorldVariables::new, BlockFactorysBossesModVariables.WorldVariables::load),
                  "block_factorys_bosses_worldvars"
               )
            : clientSide;
      }
   }
}
