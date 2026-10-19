package noobanidus.mods.lootr.neoforge.impl;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import noobanidus.mods.lootr.common.api.client.ClientTextureType;
import noobanidus.mods.lootr.common.api.config.SaveMode;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.impl.DefaultLootrAPIImpl;
import noobanidus.mods.lootr.neoforge.config.ConfigManager;
import org.jetbrains.annotations.Nullable;

public class LootrAPIImpl extends DefaultLootrAPIImpl {
   @Nullable
   @Override
   public MinecraftServer getServer() {
      return ServerLifecycleHooks.getCurrentServer();
   }

   @Override
   public boolean isFakePlayer(Player player) {
      return player instanceof ServerPlayer sPlayer && sPlayer.connection == null ? true : player instanceof FakePlayer;
   }

   @Override
   public long getLootSeed(long seed) {
      return !ConfigManager.RANDOMISE_SEED.get() && seed != -1L && seed != 0L ? seed : ThreadLocalRandom.current().nextLong();
   }

   @Override
   public float getExplosionResistance(Block block, float defaultResistance) {
      if ((Boolean)ConfigManager.BLAST_IMMUNE.get()) {
         return Float.MAX_VALUE;
      } else {
         return ConfigManager.BLAST_RESISTANT.get() ? 16.0F : defaultResistance;
      }
   }

   @Override
   public boolean isBlastResistant() {
      return (Boolean)ConfigManager.BLAST_RESISTANT.get();
   }

   @Override
   public boolean isBlastImmune() {
      return (Boolean)ConfigManager.BLAST_IMMUNE.get();
   }

   @Override
   public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos position, float defaultProgress) {
      return ConfigManager.DISABLE_BREAK.get() ? 0.0F : defaultProgress;
   }

   @Override
   public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos, int defaultSignal) {
      return ConfigManager.POWER_COMPARATORS.get() ? 1 : defaultSignal;
   }

   @Override
   public boolean shouldPowerComparators() {
      return (Boolean)ConfigManager.POWER_COMPARATORS.get();
   }

   @Override
   public boolean shouldNotify(int remaining) {
      return ConfigManager.shouldNotify(remaining);
   }

   @Override
   public int getNotificationDelay() {
      return (Integer)ConfigManager.NOTIFICATION_DELAY.get();
   }

   @Override
   public boolean isNotificationsEnabled() {
      return !(Boolean)ConfigManager.DISABLE_NOTIFICATIONS.get();
   }

   @Override
   public boolean isMessageStylesEnabled() {
      return !(Boolean)ConfigManager.DISABLE_MESSAGE_STYLES.get();
   }

   @Override
   public ClientTextureType getTextureType() {
      if (ConfigManager.isVanillaTextures()) {
         return ClientTextureType.VANILLA;
      } else {
         return ConfigManager.isNewTextures() ? ClientTextureType.NEW : ClientTextureType.OLD;
      }
   }

   @Override
   public boolean isDisabled() {
      return (Boolean)ConfigManager.DISABLE.get();
   }

   @Override
   public boolean isLootTableBlacklisted(ResourceKey<LootTable> table) {
      return ConfigManager.isLootTableBlacklisted(table);
   }

   @Override
   public boolean isDimensionBlocked(ResourceKey<Level> dimension) {
      return ConfigManager.isDimensionBlocked(dimension);
   }

   @Override
   public boolean isDimensionDecaying(ResourceKey<Level> dimension) {
      return ConfigManager.isDimensionDecaying(dimension);
   }

   @Override
   public boolean isDimensionRefreshing(ResourceKey<Level> dimension) {
      return ConfigManager.isDimensionRefreshing(dimension);
   }

   @Override
   public Set<ResourceKey<Level>> getDimensionBlacklist() {
      return ConfigManager.getDimensionBlacklist();
   }

   @Override
   public Set<ResourceKey<Level>> getDimensionWhitelist() {
      return ConfigManager.getDimensionWhitelist();
   }

   @Override
   public Set<ResourceKey<LootTable>> getLootTableBlacklist() {
      return ConfigManager.getLootBlacklist();
   }

   @Override
   public Set<String> getLootModidBlacklist() {
      return ConfigManager.getLootModids();
   }

   @Override
   public Set<String> getModidDimensionWhitelist() {
      return ConfigManager.getDimensionModidWhitelist();
   }

   @Override
   public Set<String> getModidDimensionBlacklist() {
      return ConfigManager.getDimensionModidBlacklist();
   }

   @Override
   public boolean isDecaying(ILootrInfoProvider provider) {
      return ConfigManager.isDecaying(provider);
   }

   @Override
   public boolean isRefreshing(ILootrInfoProvider provider) {
      return ConfigManager.isRefreshing(provider);
   }

   @Override
   public Set<String> getModidDecayWhitelist() {
      return ConfigManager.getDecayMods();
   }

   @Override
   public Set<ResourceKey<LootTable>> getDecayWhitelist() {
      return ConfigManager.getDecayingTables();
   }

   @Override
   public Set<ResourceKey<Level>> getDecayDimensions() {
      return ConfigManager.getDecayDimensions();
   }

   @Override
   public Set<String> getRefreshModids() {
      return ConfigManager.getRefreshMods();
   }

   @Override
   public Set<ResourceKey<LootTable>> getRefreshWhitelist() {
      return ConfigManager.getRefreshingTables();
   }

   @Override
   public Set<ResourceKey<Level>> getRefreshDimensions() {
      return ConfigManager.getRefreshDimensions();
   }

   @Override
   public boolean reportUnresolvedTables() {
      return (Boolean)ConfigManager.REPORT_UNRESOLVED_TABLES.get();
   }

   @Override
   public boolean isCustomTrapped() {
      return (Boolean)ConfigManager.TRAPPED_CUSTOM.get();
   }

   @Override
   public boolean shouldCheckWorldBorder() {
      return (Boolean)ConfigManager.CHECK_WORLD_BORDER.get();
   }

   @Deprecated
   @Override
   public boolean shouldConvertMineshafts() {
      return (Boolean)ConfigManager.CONVERT_MINESHAFTS.get();
   }

   @Deprecated
   @Override
   public boolean shouldConvertElytras() {
      return false;
   }

   @Override
   public boolean shouldConvertElytrasToChests() {
      return (Boolean)ConfigManager.CONVERT_ELYTRAS_TO_CHESTS.get();
   }

   @Override
   public boolean shouldConvertElytrasToItemFrames() {
      return (Boolean)ConfigManager.CONVERT_ELYTRAS_TO_ITEM_FRAMES.get();
   }

   @Override
   public boolean shouldConvertStructureItemFrames() {
      return (Boolean)ConfigManager.CONVERT_ITEM_FRAMES.get();
   }

   @Override
   public int getDecayValue() {
      return (Integer)ConfigManager.DECAY_VALUE.get();
   }

   @Override
   public boolean shouldDecayAll() {
      return (Boolean)ConfigManager.DECAY_ALL.get();
   }

   @Override
   public int getRefreshValue() {
      return (Integer)ConfigManager.REFRESH_VALUE.get();
   }

   @Override
   public boolean shouldRefreshAll() {
      return (Boolean)ConfigManager.REFRESH_ALL.get();
   }

   @Override
   public Style getInvalidStyle() {
      return ConfigManager.DISABLE_MESSAGE_STYLES.get() ? Style.EMPTY : Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.RED)).withBold(true);
   }

   @Override
   public Style getDecayStyle() {
      return ConfigManager.DISABLE_MESSAGE_STYLES.get() ? Style.EMPTY : Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.RED)).withBold(true);
   }

   @Override
   public Style getRefreshStyle() {
      return ConfigManager.DISABLE_MESSAGE_STYLES.get() ? Style.EMPTY : Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.BLUE)).withBold(true);
   }

   @Override
   public Style getChatStyle() {
      return ConfigManager.DISABLE_MESSAGE_STYLES.get() ? Style.EMPTY : Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.AQUA));
   }

   @Override
   public boolean canDestroyOrBreak(Player player) {
      return this.isFakePlayer(player) && (Boolean)ConfigManager.ENABLE_FAKE_PLAYER_BREAK.get() || (Boolean)ConfigManager.ENABLE_BREAK.get();
   }

   @Override
   public boolean isBreakDisabled() {
      return (Boolean)ConfigManager.DISABLE_BREAK.get();
   }

   @Override
   public boolean isBreakEnabled() {
      return (Boolean)ConfigManager.ENABLE_BREAK.get();
   }

   @Override
   public boolean isFakePlayerBreakEnabled() {
      return (Boolean)ConfigManager.ENABLE_FAKE_PLAYER_BREAK.get();
   }

   @Override
   public boolean canBrushablesSelfSupport() {
      return (Boolean)ConfigManager.BRUSHABLES_SELF_SUPPORT.get();
   }

   @Override
   public boolean canItemFramesSelfSupport() {
      return (Boolean)ConfigManager.ITEM_FRAMES_SELF_SUPPORT.get();
   }

   @Override
   public boolean shouldDropPlayerLoot() {
      return (Boolean)ConfigManager.SHOULD_DROP_PLAYER_LOOT.get();
   }

   @Override
   public boolean shouldPerformDecayWhileTicking() {
      return (Boolean)ConfigManager.PERFORM_DECAY_WHILE_TICKING.get();
   }

   @Override
   public boolean shouldPerformRefreshWhileTicking() {
      return (Boolean)ConfigManager.PERFORM_REFRESH_WHILE_TICKING.get();
   }

   @Override
   public boolean shouldStartDecayWhileTicking() {
      return (Boolean)ConfigManager.START_DECAY_WHILE_TICKING.get();
   }

   @Override
   public boolean shouldStartRefreshWhileTicking() {
      return (Boolean)ConfigManager.START_REFRESH_WHILE_TICKING.get();
   }

   @Override
   public boolean shouldWarnNoLootTables() {
      return !(Boolean)ConfigManager.SHOULD_WARN_NO_LOOT_TABLE_AT_GENERATION.get();
   }

   @Override
   public boolean performPiecewiseCheck() {
      return ConfigManager.shouldPerformPiecewiseCheck();
   }

   @Override
   public boolean shouldBypassSpawnProtection() {
      return (Boolean)ConfigManager.BYPASS_SPAWN_PROTECTION.get();
   }

   @Override
   public boolean shouldReplaceWhenDecayed() {
      return (Boolean)ConfigManager.REPLACE_WHEN_DECAYED.get();
   }

   @Override
   public SaveMode getFileSaveMode() {
      return (SaveMode)ConfigManager.SAVE_MODE.get();
   }

   @Override
   public boolean shouldDisplayUnopenedParticles() {
      return (Boolean)ConfigManager.UNOPENED_PARTICLES.get();
   }

   @Override
   public Component getInvalidTableComponent(ResourceKey<LootTable> lootTable) {
      return Component.translatable("lootr.message.invalid_table", new Object[]{lootTable.location().getNamespace(), lootTable.toString()})
         .setStyle(
            ConfigManager.DISABLE_MESSAGE_STYLES.get()
               ? Style.EMPTY
               : Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_RED)).withBold(true)
         );
   }
}
