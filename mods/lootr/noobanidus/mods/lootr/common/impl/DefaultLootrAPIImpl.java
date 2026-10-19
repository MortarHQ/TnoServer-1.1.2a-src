package noobanidus.mods.lootr.common.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import noobanidus.mods.lootr.common.api.ILootrAPI;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.LootrConstants;
import noobanidus.mods.lootr.common.api.MenuBuilder;
import noobanidus.mods.lootr.common.api.PotDecorationsAdapter;
import noobanidus.mods.lootr.common.api.adapter.ILootrDataAdapter;
import noobanidus.mods.lootr.common.api.adapter.ILootrItemFrameAdapter;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.ILootrSavedData;
import noobanidus.mods.lootr.common.api.data.LootFiller;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.entity.ILootrEntity;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import noobanidus.mods.lootr.common.api.filter.ILootrFilter;
import noobanidus.mods.lootr.common.api.processor.ILootrBlockEntityProcessor;
import noobanidus.mods.lootr.common.api.processor.ILootrEntityProcessor;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.common.client.ClientHooks;
import noobanidus.mods.lootr.common.data.DataStorage;
import noobanidus.mods.lootr.common.integration.sherdsapi.SherdsIntegration;
import org.jetbrains.annotations.Nullable;

public abstract class DefaultLootrAPIImpl implements ILootrAPI {
   private static final BoundingBox DESERT_PYRAMID_ADDITIONAL = new BoundingBox(-5, -30, -5, 5, 4, 4);
   private boolean sherdsChecked = false;
   private DataComponentType<?> sherdsType = null;

   @Override
   public final void handleProviderSneak(@Nullable ILootrInfoProvider provider, ServerPlayer player) {
      if (provider != null) {
         if (provider.canBeMarkedUnopened()) {
            if (provider.removeVisualOpener(player)) {
               provider.performClose(player);
               provider.performUpdate(player);
            }
         }
      }
   }

   @Override
   public final void handleProviderOpen(@Nullable ILootrInfoProvider provider, ServerPlayer player) {
      this.handleProviderOpen(provider, player, null);
   }

   @Override
   public final void handleProviderOpen(@Nullable ILootrInfoProvider provider, ServerPlayer player, @Nullable MenuBuilder menuBuilder) {
      if (provider != null) {
         if (player.isSpectator()) {
            player.openMenu(null);
         } else if (provider.getInfoLevel() != null && !provider.getInfoLevel().isClientSide()) {
            if (provider.canPlayerOpen(player)) {
               if (LootrAPI.isDecayed(provider) && provider.canDecay()) {
                  provider.performDecay();
                  player.displayClientMessage(Component.translatable("lootr.message.decayed").setStyle(LootrAPI.getDecayStyle()), true);
               } else {
                  if (provider.canDecay()) {
                     int decayValue = LootrAPI.getRemainingDecayValue(provider);
                     if (decayValue > 0 && LootrAPI.shouldNotify(decayValue)) {
                        player.displayClientMessage(
                           Component.translatable("lootr.message.decay_in", new Object[]{decayValue / 20}).setStyle(LootrAPI.getDecayStyle()), true
                        );
                     } else if (decayValue == -1 && LootrAPI.isDecaying(provider)) {
                        LootrAPI.setDecaying(provider);
                        player.displayClientMessage(
                           Component.translatable("lootr.message.decay_start", new Object[]{LootrAPI.getDecayValue() / 20}).setStyle(LootrAPI.getDecayStyle()),
                           true
                        );
                     }
                  }

                  provider.performTrigger(player);
                  boolean shouldUpdate = false;
                  if (LootrAPI.isRefreshed(provider) && provider.canRefresh()) {
                     provider.performRefresh();
                     provider.performClose();
                     player.displayClientMessage(Component.translatable("lootr.message.refreshed").setStyle(LootrAPI.getRefreshStyle()), true);
                     shouldUpdate = true;
                  }

                  if (provider.canRefresh()) {
                     int refreshValue = LootrAPI.getRemainingRefreshValue(provider);
                     if (refreshValue > 0 && LootrAPI.shouldNotify(refreshValue)) {
                        player.displayClientMessage(
                           Component.translatable("lootr.message.refresh_in", new Object[]{refreshValue / 20}).setStyle(LootrAPI.getRefreshStyle()), true
                        );
                     } else if (refreshValue == -1 && LootrAPI.isRefreshing(provider)) {
                        LootrAPI.setRefreshing(provider);
                        player.displayClientMessage(
                           Component.translatable("lootr.message.refresh_start", new Object[]{LootrAPI.getRefreshValue() / 20})
                              .setStyle(LootrAPI.getRefreshStyle()),
                           true
                        );
                     }
                  }

                  MenuProvider menuProvider = LootrAPI.getInventory(provider, player, menuBuilder);
                  if (menuProvider != null) {
                     if (!provider.hasServerOpened(player)) {
                        player.awardStat(LootrRegistry.getLootedStat());
                        LootrRegistry.getStatTrigger().trigger(player);
                     }

                     if (provider.addOpener(player)) {
                        provider.performOpen(player);
                        shouldUpdate = true;
                     }

                     if (shouldUpdate) {
                        provider.performUpdate(player);
                     }

                     player.openMenu(menuProvider);
                     PiglinAi.angerNearbyPiglins(player, true);
                  }
               }
            }
         }
      }
   }

   @Override
   public final void handleProviderTick(@Nullable ILootrInfoProvider provider) {
      if (provider != null) {
         if (provider.getInfoLevel() != null && !provider.getInfoLevel().isClientSide()) {
            if (LootrAPI.shouldPerformDecayWhileTicking() && LootrAPI.isDecayed(provider) && provider.hasBeenOpened() && provider.canDecay()) {
               provider.performDecay();
            } else {
               if (LootrAPI.shouldStartDecayWhileTicking() && !LootrAPI.isDecayed(provider) && provider.hasBeenOpened() && provider.canDecay()) {
                  int decayValue = LootrAPI.getRemainingDecayValue(provider);
                  if (decayValue == -1 && LootrAPI.isDecaying(provider)) {
                     LootrAPI.setDecaying(provider);
                  }
               }

               if (LootrAPI.shouldPerformRefreshWhileTicking() && LootrAPI.isRefreshed(provider) && provider.hasBeenOpened() && provider.canRefresh()) {
                  provider.performRefresh();
                  provider.performClose();
                  provider.performUpdate();
               }

               if (LootrAPI.shouldStartRefreshWhileTicking() && !LootrAPI.isRefreshed(provider) && provider.hasBeenOpened() && provider.canRefresh()) {
                  int refreshValue = LootrAPI.getRemainingRefreshValue(provider);
                  if (refreshValue == -1 && LootrAPI.isRefreshing(provider)) {
                     LootrAPI.setRefreshing(provider);
                  }
               }
            }
         }
      }
   }

   @Override
   public final void handleProviderClientTick(@Nullable ILootrInfoProvider provider) {
      if (provider != null) {
         if (provider.getInfoLevel() != null && provider.getInfoLevel().isClientSide()) {
            if (LootrAPI.shouldDisplayUnopenedParticles()) {
               ILootrType type = provider.getInfoNewType();
               if (type != null && type.displaysUnopenedParticle()) {
                  ClientHooks.performUnopenedParticles(provider);
               }
            }
         }
      }
   }

   @Override
   public final Set<UUID> getPlayerIds() {
      MinecraftServer server = this.getServer();
      if (server == null) {
         return Set.of();
      }

      Set<UUID> result = new HashSet<>();

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         if (!this.isFakePlayer(player)) {
            UUID thisUuid = player.getUUID();
            if (thisUuid != null) {
               result.add(thisUuid);
            }
         }
      }

      return result;
   }

   @Override
   public final boolean clearPlayerLoot(UUID id) {
      return DataStorage.clearInventories(id);
   }

   @Deprecated
   @Override
   public final ILootrInventory getInventory(ILootrInfoProvider provider, ServerPlayer player, LootFiller filler) {
      return DataStorage.getInventory(provider, player, filler);
   }

   @Override
   public final ILootrInventory getInventory(ILootrInfoProvider provider, ServerPlayer player, LootFiller filler, @Nullable MenuBuilder menuBuilder) {
      ILootrInventory inventory = DataStorage.getInventory(provider, player, filler);
      if (inventory != null && menuBuilder != null) {
         inventory.setMenuBuilder(menuBuilder);
      }

      return inventory;
   }

   @Nullable
   @Override
   public final ILootrSavedData getData(ILootrInfoProvider provider) {
      return DataStorage.getData(provider);
   }

   @Override
   public final boolean shouldDiscard() {
      return LootrAPI.shouldDiscardIdAndOpeners;
   }

   @Deprecated
   @Override
   public final boolean isAwarded(UUID uuid, ServerPlayer player) {
      return DataStorage.isAwarded(uuid, player);
   }

   @Deprecated
   @Override
   public final void award(UUID id, ServerPlayer player) {
      DataStorage.award(id, player);
   }

   @Override
   public final int getRemainingDecayValue(ILootrInfoProvider provider) {
      return DataStorage.getDecayValue(provider);
   }

   @Override
   public final boolean isDecayed(ILootrInfoProvider provider) {
      return DataStorage.isDecayed(provider);
   }

   @Override
   public final void setDecaying(ILootrInfoProvider provider) {
      DataStorage.setDecaying(provider);
   }

   @Deprecated
   @Override
   public final void removeDecayed(ILootrInfoProvider provider) {
   }

   @Override
   public final int getRemainingRefreshValue(ILootrInfoProvider provider) {
      return DataStorage.getRefreshValue(provider);
   }

   @Override
   public final boolean isRefreshed(ILootrInfoProvider provider) {
      return DataStorage.isRefreshed(provider);
   }

   @Override
   public final void setRefreshing(ILootrInfoProvider provider) {
      DataStorage.setRefreshing(provider);
   }

   @Deprecated
   @Override
   public final void removeRefreshed(ILootrInfoProvider provider) {
   }

   @Nullable
   @Override
   public final <T extends BlockEntity> ILootrBlockEntity resolveBlockEntity(T blockEntity) {
      return LootrServiceRegistry.convertBlockEntity(blockEntity);
   }

   @Override
   public final <T extends Entity> ILootrEntity resolveEntity(T entity) {
      return LootrServiceRegistry.convertEntity(entity);
   }

   @Override
   public boolean isTaggedStructurePresent(ServerLevel level, ChunkPos chunkPos, TagKey<Structure> tag, BlockPos pos) {
      Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
      List<StructureStart> starts = level.structureManager()
         .startsForStructure(chunkPos, o -> registry.getHolder(registry.getId(o)).map(b -> b.is(tag)).orElse(false));

      for (StructureStart start : starts) {
         BoundingBox extended = start.getBoundingBox().inflatedBy(8);
         if (extended.isInside(pos)) {
            return true;
         }

         if (start.getStructure().type().equals(StructureType.DESERT_PYRAMID)) {
            BlockPos center = start.getBoundingBox().getCenter();
            if (DESERT_PYRAMID_ADDITIONAL.moved(center.getX(), center.getY(), center.getZ()).isInside(pos)) {
               return true;
            }
         }
      }

      if (LootrAPI.performPiecewiseCheck()) {
         for (StructureStart start : starts) {
            for (StructurePiece piece : start.getPieces()) {
               if (piece.getBoundingBox().inflatedBy(8).isInside(pos)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Override
   public boolean isWorldBorderSafe(Level level, BlockPos pos) {
      return !this.shouldCheckWorldBorder() ? true : level.getWorldBorder().isWithinBounds(pos);
   }

   @Override
   public boolean isWorldBorderSafe(Level level, ChunkPos pos) {
      return !this.shouldCheckWorldBorder() ? true : level.getWorldBorder().isWithinBounds(pos);
   }

   @Override
   public void playerDestroyed(Level level, Player player, BlockPos pos, @Nullable BlockEntity blockEntity) {
      if (this.shouldDropPlayerLoot() && !level.isClientSide() && blockEntity != null) {
         if (LootrAPI.resolveBlockEntity(blockEntity) instanceof ILootrInfoProvider provider
            && player instanceof ServerPlayer serverPlayer
            && provider.canDropContentsWhenBroken()) {
            ILootrInventory inventory = this.getInventory(provider, serverPlayer, provider.getDefaultFiller(), null);
            if (inventory != null) {
               Containers.dropContents(level, pos, inventory);
            }
         }
      }
   }

   @Override
   public void refreshSections() {
      MinecraftServer server = this.getServer();
      if (server == null || server.isSingleplayer() || !server.isDedicatedServer()) {
         ClientHooks.refreshSection();
      }
   }

   @Nullable
   @Override
   public BlockState replacementBlockState(BlockState original) {
      return LootrServiceRegistry.getReplacementBlockState(original);
   }

   @Override
   public List<ILootrFilter> getFilters() {
      return LootrServiceRegistry.getFilters();
   }

   @Override
   public List<ILootrBlockEntityProcessor.Pre> getBlockEntityPreProcessors() {
      return LootrServiceRegistry.getBlockEntityPreProcessors();
   }

   @Override
   public List<ILootrBlockEntityProcessor.Post> getBlockEntityPostProcessors() {
      return LootrServiceRegistry.getBlockEntityPostProcessors();
   }

   @Override
   public List<ILootrEntityProcessor.Pre> getEntityPreProcessors() {
      return LootrServiceRegistry.getEntityPreProcessors();
   }

   @Override
   public List<ILootrEntityProcessor.Post> getEntityPostProcessors() {
      return LootrServiceRegistry.getEntityPostProcessors();
   }

   @Nullable
   @Override
   public <T> ILootrDataAdapter<T> getAdapter(T type) {
      return LootrServiceRegistry.getAdapter(type);
   }

   @Nullable
   @Override
   public <T> ILootrItemFrameAdapter<T> getItemFrameAdapter(T type) {
      return LootrServiceRegistry.getItemFrameAdapter(type);
   }

   @Override
   public ILootrType getType(String type) {
      return LootrServiceRegistry.getType(type);
   }

   @Nullable
   private DataComponentType<?> getSherdsComponent() {
      if (!this.sherdsChecked) {
         this.sherdsChecked = true;
         this.sherdsType = (DataComponentType<?>)BuiltInRegistries.DATA_COMPONENT_TYPE.get(LootrConstants.SHERDSAPI_POT_DECORATIONS);
      }

      return this.sherdsType;
   }

   @Nullable
   @Override
   public PotDecorationsAdapter getDecorationsAdapter(ItemStack stack) {
      DataComponentType<?> sherdsType = this.getSherdsComponent();
      if (sherdsType != null && stack.has(sherdsType)) {
         return SherdsIntegration.getAdapterFrom(stack);
      }

      PotDecorations output = (PotDecorations)stack.get(DataComponents.POT_DECORATIONS);
      return output == null ? null : new PotDecorationsAdapter(output);
   }

   @Nullable
   @Override
   public PotDecorationsAdapter getDecorationsAdapter(DataComponentInput stack) {
      DataComponentType<?> sherdsType = this.getSherdsComponent();
      if (sherdsType != null) {
         Object comp = stack.get(sherdsType);
         if (comp != null) {
            return SherdsIntegration.getAdapterFrom(stack);
         }
      }

      PotDecorations output = (PotDecorations)stack.get(DataComponents.POT_DECORATIONS);
      return output == null ? null : new PotDecorationsAdapter(output);
   }

   @Nullable
   @Override
   public PotDecorationsAdapter getDecorationsAdapter(BlockEntity blockEntity) {
      if (blockEntity instanceof DecoratedPotBlockEntity decoratedPotBlockEntity) {
         DataComponentType<?> sherdsType = this.getSherdsComponent();
         if (sherdsType != null) {
            return SherdsIntegration.getAdapterFrom(blockEntity);
         } else {
            return decoratedPotBlockEntity.getDecorations() != PotDecorations.EMPTY
               ? new PotDecorationsAdapter(decoratedPotBlockEntity.getDecorations())
               : null;
         }
      } else {
         return null;
      }
   }
}
