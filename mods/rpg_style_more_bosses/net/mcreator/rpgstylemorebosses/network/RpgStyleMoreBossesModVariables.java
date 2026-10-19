package net.mcreator.rpgstylemorebosses.network;

import java.util.function.Supplier;
import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
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
import net.minecraft.world.entity.Entity;
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
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

@EventBusSubscriber(bus = Bus.MOD)
public class RpgStyleMoreBossesModVariables {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(Keys.ATTACHMENT_TYPES, "rpg_style_more_bosses");
   public static final Supplier<AttachmentType<RpgStyleMoreBossesModVariables.PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register(
      "player_variables", () -> AttachmentType.serializable(() -> new RpgStyleMoreBossesModVariables.PlayerVariables()).build()
   );

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      RpgStyleMoreBossesMod.addNetworkMessage(
         RpgStyleMoreBossesModVariables.SavedDataSyncMessage.TYPE,
         RpgStyleMoreBossesModVariables.SavedDataSyncMessage.STREAM_CODEC,
         RpgStyleMoreBossesModVariables.SavedDataSyncMessage::handleData
      );
      RpgStyleMoreBossesMod.addNetworkMessage(
         RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage.TYPE,
         RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage.STREAM_CODEC,
         RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage::handleData
      );
   }

   @EventBusSubscriber
   public static class EventBusVariableHandlers {
      @SubscribeEvent
      public static void onPlayerLoggedInSyncPlayerVariables(PlayerLoggedInEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            ((RpgStyleMoreBossesModVariables.PlayerVariables)player.getData(RpgStyleMoreBossesModVariables.PLAYER_VARIABLES))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerRespawnedSyncPlayerVariables(PlayerRespawnEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            ((RpgStyleMoreBossesModVariables.PlayerVariables)player.getData(RpgStyleMoreBossesModVariables.PLAYER_VARIABLES))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerChangedDimensionEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            ((RpgStyleMoreBossesModVariables.PlayerVariables)player.getData(RpgStyleMoreBossesModVariables.PLAYER_VARIABLES))
               .syncPlayerVariables(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void clonePlayer(Clone event) {
         RpgStyleMoreBossesModVariables.PlayerVariables original = (RpgStyleMoreBossesModVariables.PlayerVariables)event.getOriginal()
            .getData(RpgStyleMoreBossesModVariables.PLAYER_VARIABLES);
         RpgStyleMoreBossesModVariables.PlayerVariables clone = new RpgStyleMoreBossesModVariables.PlayerVariables();
         if (!event.isWasDeath()) {
            clone.TickExplosion = original.TickExplosion;
         }

         event.getEntity().setData(RpgStyleMoreBossesModVariables.PLAYER_VARIABLES, clone);
      }

      @SubscribeEvent
      public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            SavedData mapdata = RpgStyleMoreBossesModVariables.MapVariables.get(event.getEntity().level());
            SavedData worlddata = RpgStyleMoreBossesModVariables.WorldVariables.get(event.getEntity().level());
            if (mapdata != null) {
               PacketDistributor.sendToPlayer(player, new RpgStyleMoreBossesModVariables.SavedDataSyncMessage(0, mapdata), new CustomPacketPayload[0]);
            }

            if (worlddata != null) {
               PacketDistributor.sendToPlayer(player, new RpgStyleMoreBossesModVariables.SavedDataSyncMessage(1, worlddata), new CustomPacketPayload[0]);
            }
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
         if (event.getEntity() instanceof ServerPlayer player) {
            SavedData worlddata = RpgStyleMoreBossesModVariables.WorldVariables.get(event.getEntity().level());
            if (worlddata != null) {
               PacketDistributor.sendToPlayer(player, new RpgStyleMoreBossesModVariables.SavedDataSyncMessage(1, worlddata), new CustomPacketPayload[0]);
            }
         }
      }
   }

   public static class MapVariables extends SavedData {
      public static final String DATA_NAME = "rpg_style_more_bosses_mapvars";
      public double BC = 0.0;
      public boolean ExoframeIfSpawning = false;
      public boolean ExoframeDieAnimPlay = false;
      public boolean ExoframeBossCounterSpawnDebug = false;
      public boolean ExoframeAbilityUse = false;
      static RpgStyleMoreBossesModVariables.MapVariables clientSide = new RpgStyleMoreBossesModVariables.MapVariables();

      public static RpgStyleMoreBossesModVariables.MapVariables load(CompoundTag tag, Provider lookupProvider) {
         RpgStyleMoreBossesModVariables.MapVariables data = new RpgStyleMoreBossesModVariables.MapVariables();
         data.read(tag, lookupProvider);
         return data;
      }

      public void read(CompoundTag nbt, Provider lookupProvider) {
         this.BC = nbt.getDouble("BC");
         this.ExoframeIfSpawning = nbt.getBoolean("ExoframeIfSpawning");
         this.ExoframeDieAnimPlay = nbt.getBoolean("ExoframeDieAnimPlay");
         this.ExoframeBossCounterSpawnDebug = nbt.getBoolean("ExoframeBossCounterSpawnDebug");
         this.ExoframeAbilityUse = nbt.getBoolean("ExoframeAbilityUse");
      }

      public CompoundTag save(CompoundTag nbt, Provider lookupProvider) {
         nbt.putDouble("BC", this.BC);
         nbt.putBoolean("ExoframeIfSpawning", this.ExoframeIfSpawning);
         nbt.putBoolean("ExoframeDieAnimPlay", this.ExoframeDieAnimPlay);
         nbt.putBoolean("ExoframeBossCounterSpawnDebug", this.ExoframeBossCounterSpawnDebug);
         nbt.putBoolean("ExoframeAbilityUse", this.ExoframeAbilityUse);
         return nbt;
      }

      public void syncData(LevelAccessor world) {
         this.setDirty();
         if (world instanceof Level && !world.isClientSide()) {
            PacketDistributor.sendToAllPlayers(new RpgStyleMoreBossesModVariables.SavedDataSyncMessage(0, this), new CustomPacketPayload[0]);
         }
      }

      public static RpgStyleMoreBossesModVariables.MapVariables get(LevelAccessor world) {
         return world instanceof ServerLevelAccessor serverLevelAcc
            ? (RpgStyleMoreBossesModVariables.MapVariables)serverLevelAcc.getLevel()
               .getServer()
               .getLevel(Level.OVERWORLD)
               .getDataStorage()
               .computeIfAbsent(
                  new Factory(RpgStyleMoreBossesModVariables.MapVariables::new, RpgStyleMoreBossesModVariables.MapVariables::load),
                  "rpg_style_more_bosses_mapvars"
               )
            : clientSide;
      }
   }

   public static class PlayerVariables implements INBTSerializable<CompoundTag> {
      public boolean TickExplosion = false;

      public CompoundTag serializeNBT(Provider lookupProvider) {
         CompoundTag nbt = new CompoundTag();
         nbt.putBoolean("TickExplosion", this.TickExplosion);
         return nbt;
      }

      public void deserializeNBT(Provider lookupProvider, CompoundTag nbt) {
         this.TickExplosion = nbt.getBoolean("TickExplosion");
      }

      public void syncPlayerVariables(Entity entity) {
         if (entity instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage(this), new CustomPacketPayload[0]);
         }
      }
   }

   public record PlayerVariablesSyncMessage(RpgStyleMoreBossesModVariables.PlayerVariables data) implements CustomPacketPayload {
      public static final Type<RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "player_variables_sync")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec.of(
         (buffer, message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())),
         buffer -> {
            RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage message = new RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage(
               new RpgStyleMoreBossesModVariables.PlayerVariables()
            );
            message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
            return message;
         }
      );

      public Type<RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage> type() {
         return TYPE;
      }

      public static void handleData(RpgStyleMoreBossesModVariables.PlayerVariablesSyncMessage message, IPayloadContext context) {
         if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
            context.enqueueWork(
                  () -> ((RpgStyleMoreBossesModVariables.PlayerVariables)context.player().getData(RpgStyleMoreBossesModVariables.PLAYER_VARIABLES))
                     .deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()))
               )
               .exceptionally(e -> {
                  context.connection().disconnect(Component.literal(e.getMessage()));
                  return null;
               });
         }
      }
   }

   public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload {
      public static final Type<RpgStyleMoreBossesModVariables.SavedDataSyncMessage> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "saved_data_sync")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, RpgStyleMoreBossesModVariables.SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of(
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
               data = (SavedData)(dataType == 0 ? new RpgStyleMoreBossesModVariables.MapVariables() : new RpgStyleMoreBossesModVariables.WorldVariables());
               if (data instanceof RpgStyleMoreBossesModVariables.MapVariables mapVariables) {
                  mapVariables.read(nbt, buffer.registryAccess());
               } else if (data instanceof RpgStyleMoreBossesModVariables.WorldVariables worldVariables) {
                  worldVariables.read(nbt, buffer.registryAccess());
               }
            }

            return new RpgStyleMoreBossesModVariables.SavedDataSyncMessage(dataType, data);
         }
      );

      public Type<RpgStyleMoreBossesModVariables.SavedDataSyncMessage> type() {
         return TYPE;
      }

      public static void handleData(RpgStyleMoreBossesModVariables.SavedDataSyncMessage message, IPayloadContext context) {
         if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
            context.enqueueWork(
                  () -> {
                     if (message.dataType == 0) {
                        RpgStyleMoreBossesModVariables.MapVariables.clientSide
                           .read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
                     } else {
                        RpgStyleMoreBossesModVariables.WorldVariables.clientSide
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
      public static final String DATA_NAME = "rpg_style_more_bosses_worldvars";
      static RpgStyleMoreBossesModVariables.WorldVariables clientSide = new RpgStyleMoreBossesModVariables.WorldVariables();

      public static RpgStyleMoreBossesModVariables.WorldVariables load(CompoundTag tag, Provider lookupProvider) {
         RpgStyleMoreBossesModVariables.WorldVariables data = new RpgStyleMoreBossesModVariables.WorldVariables();
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
            PacketDistributor.sendToPlayersInDimension(level, new RpgStyleMoreBossesModVariables.SavedDataSyncMessage(1, this), new CustomPacketPayload[0]);
         }
      }

      public static RpgStyleMoreBossesModVariables.WorldVariables get(LevelAccessor world) {
         return world instanceof ServerLevel level
            ? (RpgStyleMoreBossesModVariables.WorldVariables)level.getDataStorage()
               .computeIfAbsent(
                  new Factory(RpgStyleMoreBossesModVariables.WorldVariables::new, RpgStyleMoreBossesModVariables.WorldVariables::load),
                  "rpg_style_more_bosses_worldvars"
               )
            : clientSide;
      }
   }
}
