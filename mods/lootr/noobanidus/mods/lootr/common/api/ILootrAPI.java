package noobanidus.mods.lootr.common.api;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.adapter.ILootrDataAdapter;
import noobanidus.mods.lootr.common.api.adapter.ILootrItemFrameAdapter;
import noobanidus.mods.lootr.common.api.client.ClientTextureType;
import noobanidus.mods.lootr.common.api.config.SaveMode;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.ILootrSavedData;
import noobanidus.mods.lootr.common.api.data.LootFiller;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.entity.ILootrEntity;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import noobanidus.mods.lootr.common.api.filter.ILootrFilter;
import noobanidus.mods.lootr.common.api.processor.ILootrBlockEntityProcessor;
import noobanidus.mods.lootr.common.api.processor.ILootrEntityProcessor;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public interface ILootrAPI {
   Set<UUID> getPlayerIds();

   @Nullable
   default ServerPlayer getPlayer(UUID playerId) {
      MinecraftServer server = this.getServer();
      return server.getPlayerList().getPlayer(playerId);
   }

   MinecraftServer getServer();

   default int getCurrentTicks() {
      MinecraftServer server = this.getServer();
      return server == null ? -1 : server.getTickCount();
   }

   boolean isFakePlayer(Player var1);

   default boolean clearPlayerLoot(ServerPlayer entity) {
      return this.clearPlayerLoot(entity.getUUID());
   }

   boolean clearPlayerLoot(UUID var1);

   @Deprecated
   @Nullable
   ILootrInventory getInventory(ILootrInfoProvider var1, ServerPlayer var2, LootFiller var3);

   @Nullable
   default ILootrInventory getInventory(ILootrInfoProvider provider, ServerPlayer player, @Nullable MenuBuilder builder) {
      return this.getInventory(provider, player, provider.getDefaultFiller(), builder);
   }

   @Nullable
   ILootrInventory getInventory(ILootrInfoProvider var1, ServerPlayer var2, LootFiller var3, @Nullable MenuBuilder var4);

   @Nullable
   ILootrSavedData getData(ILootrInfoProvider var1);

   long getLootSeed(long var1);

   boolean shouldDiscard();

   float getExplosionResistance(Block var1, float var2);

   boolean isBlastResistant();

   boolean isBlastImmune();

   float getDestroyProgress(BlockState var1, Player var2, BlockGetter var3, BlockPos var4, float var5);

   int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3, int var4);

   boolean shouldPowerComparators();

   boolean shouldNotify(int var1);

   int getNotificationDelay();

   boolean isNotificationsEnabled();

   boolean isMessageStylesEnabled();

   ClientTextureType getTextureType();

   default boolean isNewTextures() {
      return this.getTextureType() == ClientTextureType.NEW;
   }

   default boolean isOldTextures() {
      return this.getTextureType() == ClientTextureType.OLD;
   }

   default boolean isVanillaTextures() {
      return this.getTextureType() == ClientTextureType.VANILLA;
   }

   default boolean isDefaultTextures() {
      return this.getTextureType() == ClientTextureType.NEW;
   }

   boolean isDisabled();

   boolean isLootTableBlacklisted(ResourceKey<LootTable> var1);

   boolean isDimensionBlocked(ResourceKey<Level> var1);

   boolean isDimensionDecaying(ResourceKey<Level> var1);

   boolean isDimensionRefreshing(ResourceKey<Level> var1);

   Set<ResourceKey<Level>> getDimensionBlacklist();

   Set<ResourceKey<Level>> getDimensionWhitelist();

   Set<ResourceKey<LootTable>> getLootTableBlacklist();

   Set<String> getLootModidBlacklist();

   Set<String> getModidDimensionWhitelist();

   Set<String> getModidDimensionBlacklist();

   boolean isDecaying(ILootrInfoProvider var1);

   boolean isRefreshing(ILootrInfoProvider var1);

   Set<String> getModidDecayWhitelist();

   Set<ResourceKey<LootTable>> getDecayWhitelist();

   Set<ResourceKey<Level>> getDecayDimensions();

   Set<String> getRefreshModids();

   Set<ResourceKey<LootTable>> getRefreshWhitelist();

   Set<ResourceKey<Level>> getRefreshDimensions();

   boolean reportUnresolvedTables();

   boolean isCustomTrapped();

   boolean isWorldBorderSafe(Level var1, BlockPos var2);

   boolean isWorldBorderSafe(Level var1, ChunkPos var2);

   boolean shouldCheckWorldBorder();

   boolean shouldConvertMineshafts();

   @Deprecated
   boolean shouldConvertElytras();

   boolean shouldConvertElytrasToChests();

   boolean shouldConvertElytrasToItemFrames();

   boolean shouldConvertStructureItemFrames();

   int getDecayValue();

   boolean shouldDecayAll();

   int getRefreshValue();

   boolean shouldRefreshAll();

   Style getInvalidStyle();

   Style getDecayStyle();

   Style getRefreshStyle();

   Style getChatStyle();

   Component getInvalidTableComponent(ResourceKey<LootTable> var1);

   boolean canDestroyOrBreak(Player var1);

   boolean isBreakDisabled();

   boolean isBreakEnabled();

   boolean isFakePlayerBreakEnabled();

   boolean canBrushablesSelfSupport();

   boolean canItemFramesSelfSupport();

   boolean shouldDropPlayerLoot();

   boolean shouldPerformDecayWhileTicking();

   boolean shouldPerformRefreshWhileTicking();

   boolean shouldStartDecayWhileTicking();

   boolean shouldStartRefreshWhileTicking();

   boolean shouldWarnNoLootTables();

   boolean performPiecewiseCheck();

   @Deprecated
   default boolean isAwarded(ILootrInfoProvider provider, ServerPlayer player) {
      return this.isAwarded(provider.getInfoUUID(), player);
   }

   @Deprecated
   boolean isAwarded(UUID var1, ServerPlayer var2);

   @Deprecated
   default void award(ILootrInfoProvider provider, ServerPlayer player) {
      this.award(provider.getInfoUUID(), player);
   }

   @Deprecated
   void award(UUID var1, ServerPlayer var2);

   int getRemainingDecayValue(ILootrInfoProvider var1);

   boolean isDecayed(ILootrInfoProvider var1);

   void setDecaying(ILootrInfoProvider var1);

   void removeDecayed(ILootrInfoProvider var1);

   int getRemainingRefreshValue(ILootrInfoProvider var1);

   boolean isRefreshed(ILootrInfoProvider var1);

   void setRefreshing(ILootrInfoProvider var1);

   void removeRefreshed(ILootrInfoProvider var1);

   @Nullable
   BlockState replacementBlockState(BlockState var1);

   void handleProviderSneak(@Nullable ILootrInfoProvider var1, ServerPlayer var2);

   void handleProviderOpen(@Nullable ILootrInfoProvider var1, ServerPlayer var2);

   void handleProviderOpen(@Nullable ILootrInfoProvider var1, ServerPlayer var2, MenuBuilder var3);

   void handleProviderTick(@Nullable ILootrInfoProvider var1);

   void handleProviderClientTick(@Nullable ILootrInfoProvider var1);

   @Nullable
   <T extends BlockEntity> ILootrBlockEntity resolveBlockEntity(T var1);

   <T extends Entity> ILootrEntity resolveEntity(T var1);

   boolean isTaggedStructurePresent(ServerLevel var1, ChunkPos var2, TagKey<Structure> var3, BlockPos var4);

   void playerDestroyed(Level var1, Player var2, BlockPos var3, @Nullable BlockEntity var4);

   void refreshSections();

   List<ILootrFilter> getFilters();

   List<ILootrBlockEntityProcessor.Pre> getBlockEntityPreProcessors();

   List<ILootrBlockEntityProcessor.Post> getBlockEntityPostProcessors();

   List<ILootrEntityProcessor.Pre> getEntityPreProcessors();

   List<ILootrEntityProcessor.Post> getEntityPostProcessors();

   @Nullable
   <T> ILootrDataAdapter<T> getAdapter(T var1);

   @Nullable
   <T> ILootrItemFrameAdapter<T> getItemFrameAdapter(T var1);

   ILootrType getType(String var1);

   boolean shouldBypassSpawnProtection();

   boolean shouldReplaceWhenDecayed();

   PotDecorationsAdapter getDecorationsAdapter(BlockEntity var1);

   PotDecorationsAdapter getDecorationsAdapter(ItemStack var1);

   PotDecorationsAdapter getDecorationsAdapter(DataComponentInput var1);

   SaveMode getFileSaveMode();

   boolean shouldDisplayUnopenedParticles();

   default long getGameTime() {
      MinecraftServer server = LootrAPI.getServer();
      return server == null ? -1L : server.getWorldData().overworldData().getGameTime();
   }
}
