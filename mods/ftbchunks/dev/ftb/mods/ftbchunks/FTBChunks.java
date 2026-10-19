package dev.ftb.mods.ftbchunks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.ExplosionEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.hooks.level.entity.PlayerHooks;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import dev.architectury.utils.value.IntValue;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.FTBChunksProperties;
import dev.ftb.mods.ftbchunks.api.Protection;
import dev.ftb.mods.ftbchunks.api.event.CustomMinYEvent;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.data.ChunkSyncInfo;
import dev.ftb.mods.ftbchunks.data.ChunkTeamDataImpl;
import dev.ftb.mods.ftbchunks.data.ClaimExpirationManager;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkImpl;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import dev.ftb.mods.ftbchunks.data.PvPMode;
import dev.ftb.mods.ftbchunks.net.FTBChunksNet;
import dev.ftb.mods.ftbchunks.net.LoginDataPacket;
import dev.ftb.mods.ftbchunks.net.PlayerDeathPacket;
import dev.ftb.mods.ftbchunks.net.PlayerVisibilityPacket;
import dev.ftb.mods.ftbchunks.net.SendGeneralDataPacket;
import dev.ftb.mods.ftbchunks.net.SendManyChunksPacket;
import dev.ftb.mods.ftblibrary.config.manager.ConfigManager;
import dev.ftb.mods.ftblibrary.integration.stages.StageHelper;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.math.XZ;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.event.PlayerJoinedPartyTeamEvent;
import dev.ftb.mods.ftbteams.api.event.PlayerLeftPartyTeamEvent;
import dev.ftb.mods.ftbteams.api.event.PlayerLoggedInAfterTeamEvent;
import dev.ftb.mods.ftbteams.api.event.PlayerTransferredTeamOwnershipEvent;
import dev.ftb.mods.ftbteams.api.event.TeamAllyEvent;
import dev.ftb.mods.ftbteams.api.event.TeamCollectPropertiesEvent;
import dev.ftb.mods.ftbteams.api.event.TeamCreatedEvent;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamManagerEvent;
import dev.ftb.mods.ftbteams.api.event.TeamPropertiesChangedEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class FTBChunks {
   public static final String MOD_ID = "ftbchunks";
   public static final Logger LOGGER = LogManager.getLogger("FTB Chunks");
   public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setLenient().create();
   public static FTBChunks instance;
   public static final int TILES = 15;
   public static final int TILE_SIZE = 16;
   public static final int TILE_OFFSET = 7;
   public static final int MINIMAP_SIZE = 240;
   public static final XZ[] RELATIVE_SPIRAL_POSITIONS = new XZ[225];
   public static final Registrar<Block> BLOCK_REGISTRY = RegistrarManager.get("ftbchunks").get(Registries.BLOCK);

   public FTBChunks() {
      FTBChunksAPI._init(FTBChunksAPIImpl.INSTANCE);
      FTBChunksNet.init();
      ConfigManager.getInstance().registerServerConfig(FTBChunksWorldConfig.CONFIG, "ftbchunks.config.server", true, FTBChunksWorldConfig::onConfigChanged);
      ConfigManager.getInstance().registerClientConfig(FTBChunksClientConfig.CONFIG, "ftbchunks.config.client");

      for (int i = 0; i < RELATIVE_SPIRAL_POSITIONS.length; i++) {
         RELATIVE_SPIRAL_POSITIONS[i] = MathUtils.getSpiralPoint(i + 1);
      }

      LifecycleEvent.SERVER_LEVEL_LOAD.register(this::serverLevelLoad);
      TeamManagerEvent.CREATED.register(this::teamManagerCreated);
      TeamManagerEvent.DESTROYED.register(this::teamManagerDestroyed);
      TeamEvent.PLAYER_LOGGED_IN.register(this::loggedIn);
      TeamEvent.CREATED.register(this::teamCreated);
      TeamEvent.LOADED.register(this::teamLoaded);
      TeamEvent.SAVED.register(this::teamSaved);
      TeamEvent.COLLECT_PROPERTIES.register(this::teamConfig);
      TeamEvent.PLAYER_JOINED_PARTY.register(this::playerJoinedParty);
      TeamEvent.PLAYER_LEFT_PARTY.register(this::playerLeftParty);
      TeamEvent.OWNERSHIP_TRANSFERRED.register(this::teamOwnershipTransferred);
      TeamEvent.PROPERTIES_CHANGED.register(this::teamPropertiesChanged);
      TeamEvent.ADD_ALLY.register(this::playerAllianceChange);
      TeamEvent.REMOVE_ALLY.register(this::playerAllianceChange);
      InteractionEvent.LEFT_CLICK_BLOCK.register(this::blockLeftClick);
      InteractionEvent.RIGHT_CLICK_BLOCK.register(this::blockRightClick);
      InteractionEvent.RIGHT_CLICK_ITEM.register(this::itemRightClick);
      InteractionEvent.INTERACT_ENTITY.register(this::interactEntity);
      InteractionEvent.FARMLAND_TRAMPLE.register(this::farmlandTrample);
      BlockEvent.BREAK.register(this::blockBreak);
      BlockEvent.PLACE.register(this::blockPlace);
      PlayerEvent.PLAYER_QUIT.register(this::loggedOut);
      PlayerEvent.FILL_BUCKET.register(this::fillBucket);
      PlayerEvent.PLAYER_CLONE.register(this::playerCloned);
      PlayerEvent.CHANGE_DIMENSION.register(this::playerChangedDimension);
      PlayerEvent.ATTACK_ENTITY.register(this::playerAttackEntity);
      EntityEvent.ENTER_SECTION.register(this::enterSection);
      EntityEvent.LIVING_CHECK_SPAWN.register(this::checkSpawn);
      EntityEvent.LIVING_HURT.register(this::onLivingHurt);
      ExplosionEvent.DETONATE.register(this::explosionDetonate);
      CommandRegistrationEvent.EVENT.register(FTBChunksCommands::registerCommands);
      TickEvent.SERVER_POST.register(this::serverTickPost);
      TickEvent.PLAYER_POST.register(this::playerTickPost);
      EnvExecutor.runInEnv(Env.CLIENT, () -> FTBChunksClient.INSTANCE::init);
      LifecycleEvent.SETUP
         .register(
            (Runnable)() -> ((CustomMinYEvent)CustomMinYEvent.REGISTER.invoker())
               .register(CustomMinYRegistryImpl.getInstance(Platform.getEnvironment() == Env.CLIENT))
         );
   }

   private EventResult playerAttackEntity(Player player, Level level, Entity entity, InteractionHand interactionHand, @Nullable EntityHitResult entityHitResult) {
      return player instanceof ServerPlayer
            && !(entity instanceof LivingEntity)
            && ClaimedChunkManagerImpl.getInstance()
               .shouldPreventInteraction(player, interactionHand, entity.blockPosition(), Protection.ATTACK_NONLIVING_ENTITY, entity)
         ? EventResult.interruptFalse()
         : EventResult.pass();
   }

   public static boolean isNonLivingOrArmorStand(Entity e) {
      return e instanceof ArmorStand || !(e instanceof LivingEntity);
   }

   private EventResult onLivingHurt(LivingEntity living, DamageSource damageSource, float dmg) {
      if (!living.level().isClientSide() && living instanceof Player target && damageSource.getEntity() instanceof Player attacker) {
         PvPMode mode = (PvPMode)FTBChunksWorldConfig.PVP_MODE.get();
         if (mode == PvPMode.ALWAYS) {
            return EventResult.pass();
         }

         if (this.isPvPProtectedChunk(mode, attacker) || this.isPvPProtectedChunk(mode, target)) {
            PlayerNotifier.notifyWithCooldown(attacker, Component.translatable("ftbchunks.message.no_pvp").withStyle(ChatFormatting.GOLD), 3000L);
            return EventResult.interruptFalse();
         }
      }

      return EventResult.pass();
   }

   private boolean isPvPProtectedChunk(PvPMode mode, Player player) {
      ClaimedChunk cc = ClaimedChunkManagerImpl.getInstance().getChunk(new ChunkDimPos(player.level(), player.blockPosition()));
      return cc != null && (mode == PvPMode.NEVER || !cc.getTeamData().allowPVP());
   }

   private void playerTickPost(Player player) {
      if (player.level().isClientSide && player.level().getGameTime() % 20L == 0L) {
         FTBChunksClient.INSTANCE.maybeClearDeathpoint(player);
      }
   }

   private void serverLevelLoad(ServerLevel level) {
      if (ClaimedChunkManagerImpl.getInstance() != null) {
         ClaimedChunkManagerImpl.getInstance().initForceLoadedChunks(level);
      } else {
         LOGGER.warn("Level {} loaded before FTB Chunks manager was initialized! Unable to force-load chunks", level.dimension().location());
      }
   }

   private void teamManagerCreated(TeamManagerEvent event) {
      ClaimedChunkManagerImpl.init(event.getManager());
   }

   private void teamManagerDestroyed(TeamManagerEvent event) {
      ClaimedChunkManagerImpl.shutdown();
   }

   private void loggedIn(PlayerLoggedInAfterTeamEvent event) {
      ServerPlayer player = event.getPlayer();
      ChunkTeamDataImpl data = ClaimedChunkManagerImpl.getInstance().getOrCreateData(player);
      data.updateLimits();
      String playerId = event.getPlayer().getUUID().toString();
      LOGGER.debug("handling player team login: player = {}, team = {}", playerId, data.getTeamId());
      UUID managerId = FTBTeamsAPI.api().getManager().getId();
      NetworkManager.sendToPlayer(player, new LoginDataPacket(managerId));
      SendGeneralDataPacket.send(data, player);
      LOGGER.debug("team data sent to {}", playerId);
      long now = System.currentTimeMillis();
      Map<Pair<ResourceKey<Level>, UUID>, List<ChunkSyncInfo>> chunksToSend = new HashMap<>();

      for (ClaimedChunkImpl chunk : ClaimedChunkManagerImpl.getInstance().getAllClaimedChunks()) {
         chunksToSend.computeIfAbsent(Pair.of(chunk.getPos().dimension(), chunk.getTeamData().getTeamId()), s -> new ArrayList<>())
            .add(ChunkSyncInfo.create(now, chunk.getPos().x(), chunk.getPos().z(), chunk));
      }

      chunksToSend.forEach(
         (dimensionAndId, chunkPackets) -> FTBTeamsAPI.api()
            .getManager()
            .getTeamByID((UUID)dimensionAndId.getRight())
            .ifPresent(
               team -> {
                  ChunkTeamDataImpl teamData = ClaimedChunkManagerImpl.getInstance().getOrCreateData(team);
                  if (teamData.canPlayerUse(player, FTBChunksProperties.CLAIM_VISIBILITY)) {
                     SendManyChunksPacket packet = new SendManyChunksPacket(
                        (ResourceKey<Level>)dimensionAndId.getLeft(), (UUID)dimensionAndId.getRight(), (List<ChunkSyncInfo>)chunkPackets
                     );
                     NetworkManager.sendToPlayer(player, packet);
                  }
               }
            )
      );
      LOGGER.debug("claimed chunk data sent to {}", playerId);
      data.setLastLoginTime(now);
      data.setForceLoadMember(player.getUUID(), FTBChunksWorldConfig.canPlayerOfflineForceload(player));
      if (data.getTeam().getOnlineMembers().size() == 1 && !data.canDoOfflineForceLoading()) {
         data.updateChunkTickets(true);
      }

      PlayerVisibilityPacket.syncToLevel(player.level());
      LOGGER.debug("visible player list sent to {}", playerId);
   }

   public void loggedOut(ServerPlayer player) {
      if (FTBTeamsAPI.api().isManagerLoaded() && FTBChunksAPI.api().isManagerLoaded()) {
         ChunkTeamDataImpl data = ClaimedChunkManagerImpl.getInstance().getOrCreateData(player);
         if (data != null) {
            data.setForceLoadMember(player.getUUID(), FTBChunksWorldConfig.canPlayerOfflineForceload(player));
            ClaimedChunkManagerImpl.getInstance().clearForceLoadedCache();
            LongRangePlayerTracker.INSTANCE.stopTracking(player);
            if (data.getTeam().getOnlineMembers().size() == 1 && !data.canDoOfflineForceLoading()) {
               data.updateChunkTickets(false);
            }
         } else {
            LOGGER.warn("on player disconnect: player '{}' has no team?", player.getGameProfile().getName());
         }
      }
   }

   private void teamCreated(TeamCreatedEvent teamEvent) {
      ClaimedChunkManagerImpl.getInstance().getOrCreateData(teamEvent.getTeam());
   }

   private void teamLoaded(TeamEvent teamEvent) {
      ClaimedChunkManagerImpl.getInstance().getOrCreateData(teamEvent.getTeam());
   }

   private void teamSaved(TeamEvent teamEvent) {
      ClaimedChunkManagerImpl.getInstance().getOrCreateData(teamEvent.getTeam()).saveNow();
   }

   public EventResult blockLeftClick(Player player, InteractionHand hand, BlockPos pos, Direction face) {
      return player instanceof ServerPlayer
            && ClaimedChunkManagerImpl.getInstance().shouldPreventInteraction(player, hand, pos, FTBChunksExpected.getBlockBreakProtection(), null)
         ? EventResult.interruptFalse()
         : EventResult.pass();
   }

   public EventResult blockRightClick(Player player, InteractionHand hand, BlockPos pos, Direction face) {
      if (player instanceof ServerPlayer sp) {
         boolean blockItem = sp.getItemInHand(hand).getItem() instanceof BlockItem;
         ClaimedChunkManagerImpl mgr = ClaimedChunkManagerImpl.getInstance();
         if (mgr.shouldPreventInteraction(player, hand, pos, FTBChunksExpected.getBlockInteractProtection(), null)
            || blockItem && mgr.shouldPreventInteraction(player, hand, pos, FTBChunksExpected.getBlockPlaceProtection(), null)) {
            FTBCUtils.forceHeldItemSync(sp, hand);
            return EventResult.interruptFalse();
         }
      }

      return EventResult.pass();
   }

   public CompoundEventResult<ItemStack> itemRightClick(Player player, InteractionHand hand) {
      if (player instanceof ServerPlayer sp
         && ClaimedChunkManagerImpl.getInstance()
            .shouldPreventInteraction(player, hand, BlockPos.containing(player.getEyePosition(1.0F)), Protection.RIGHT_CLICK_ITEM, null)) {
         FTBCUtils.forceHeldItemSync(sp, hand);
         return CompoundEventResult.interruptFalse(player.getItemInHand(hand));
      } else {
         return CompoundEventResult.pass();
      }
   }

   private EventResult interactEntity(Player player, Entity entity, InteractionHand hand) {
      return player instanceof ServerPlayer
            && ClaimedChunkManagerImpl.getInstance().shouldPreventInteraction(player, hand, entity.blockPosition(), Protection.INTERACT_ENTITY, entity)
         ? EventResult.interruptFalse()
         : EventResult.pass();
   }

   public EventResult blockBreak(Level level, BlockPos pos, BlockState blockState, ServerPlayer player, @Nullable IntValue intValue) {
      return ClaimedChunkManagerImpl.getInstance()
            .shouldPreventInteraction(player, InteractionHand.MAIN_HAND, pos, FTBChunksExpected.getBlockBreakProtection(), null)
         ? EventResult.interruptFalse()
         : EventResult.pass();
   }

   public EventResult blockPlace(Level level, BlockPos pos, BlockState blockState, @Nullable Entity entity) {
      if (entity instanceof ServerPlayer sp
         && ClaimedChunkManagerImpl.getInstance()
            .shouldPreventInteraction(entity, InteractionHand.MAIN_HAND, pos, FTBChunksExpected.getBlockPlaceProtection(), null)) {
         FTBCUtils.forceHeldItemSync(sp, InteractionHand.MAIN_HAND);
         return EventResult.interruptFalse();
      } else {
         return EventResult.pass();
      }
   }

   public CompoundEventResult<ItemStack> fillBucket(Player player, Level level, ItemStack emptyBucket, @Nullable HitResult target) {
      if (player instanceof ServerPlayer && target instanceof BlockHitResult hitResult) {
         InteractionHand hand = player.getUsedItemHand();
         if (ClaimedChunkManagerImpl.getInstance().shouldPreventInteraction(player, hand, hitResult.getBlockPos(), Protection.EDIT_FLUID, null)) {
            return CompoundEventResult.interrupt(false, player.getItemInHand(hand));
         }
      }

      return CompoundEventResult.pass();
   }

   public EventResult farmlandTrample(Level world, BlockPos pos, BlockState blockState, float distance, Entity entity) {
      return entity instanceof ServerPlayer
            && ClaimedChunkManagerImpl.getInstance().shouldPreventInteraction(entity, InteractionHand.MAIN_HAND, pos, Protection.EDIT_BLOCK, null)
         ? EventResult.interrupt(false)
         : EventResult.pass();
   }

   public void enterSection(Entity entity, int chunkX, int chunkY, int chunkZ, int prevX, int prevY, int prevZ) {
      if ((chunkX != prevX || chunkZ != prevZ || chunkY == prevY)
         && entity instanceof ServerPlayer player
         && !PlayerHooks.isFake(player)
         && FTBTeamsAPI.api().isManagerLoaded()
         && FTBChunksAPI.api().isManagerLoaded()) {
         FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID()).ifPresent(team -> {
            ChunkTeamDataImpl data = ClaimedChunkManagerImpl.getInstance().getOrCreateData(team);
            data.checkForChunkChange(player, chunkX, chunkZ);
         });
      }
   }

   public EventResult checkSpawn(
      LivingEntity entity, LevelAccessor levelAccessor, double x, double y, double z, MobSpawnType type, @Nullable BaseSpawner spawner
   ) {
      if (!levelAccessor.isClientSide() && !(entity instanceof Player) && levelAccessor instanceof Level level) {
         switch (type) {
            case NATURAL:
            case CHUNK_GENERATION:
            case SPAWNER:
            case STRUCTURE:
            case JOCKEY:
            case PATROL:
               ClaimedChunkImpl chunk = ClaimedChunkManagerImpl.getInstance().getChunk(new ChunkDimPos(level, BlockPos.containing(x, y, z)));
               if (chunk != null && !chunk.canEntitySpawn(entity)) {
                  return EventResult.interrupt(false);
               }
         }
      }

      return EventResult.pass();
   }

   private boolean ignoreExplosion(Level level, Explosion explosion) {
      return !level.isClientSide() && !explosion.getToBlow().isEmpty()
         ? explosion.source == null && !(Boolean)FTBChunksWorldConfig.PROTECT_UNKNOWN_EXPLOSIONS.get()
         : true;
   }

   public void explosionDetonate(Level level, Explosion explosion, List<Entity> affectedEntities) {
      if (!(Boolean)FTBChunksWorldConfig.DISABLE_PROTECTION.get() && !this.ignoreExplosion(level, explosion)) {
         List<BlockPos> list = new ArrayList<>(explosion.getToBlow());
         explosion.clearToBlow();
         Map<ChunkDimPos, Boolean> map = new HashMap<>();

         for (BlockPos pos : list) {
            if (map.computeIfAbsent(new ChunkDimPos(level, pos), cpos -> {
               ClaimedChunkImpl chunk = ClaimedChunkManagerImpl.getInstance().getChunk(cpos);
               return chunk == null || chunk.allowExplosions();
            })) {
               explosion.getToBlow().add(pos);
            }
         }
      }
   }

   private void playerCloned(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean wonGame) {
      if (!wonGame) {
         newPlayer.getLastDeathLocation().ifPresent(loc -> {
            int num = newPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS));
            NetworkManager.sendToPlayer(newPlayer, new PlayerDeathPacket(loc, num));
         });
      }
   }

   private void playerChangedDimension(ServerPlayer serverPlayer, ResourceKey<Level> oldLevel, ResourceKey<Level> newLevel) {
      LongRangePlayerTracker.INSTANCE.stopTracking(serverPlayer);
      StageHelper.INSTANCE.getProvider().sync(serverPlayer);
   }

   private void teamConfig(TeamCollectPropertiesEvent event) {
      event.add(FTBChunksProperties.ALLOW_EXPLOSIONS);
      event.add(FTBChunksProperties.ALLOW_MOB_GRIEFING);
      event.add(FTBChunksProperties.ALLOW_ALL_FAKE_PLAYERS);
      event.add(FTBChunksProperties.ALLOW_NAMED_FAKE_PLAYERS);
      event.add(FTBChunksProperties.ALLOW_FAKE_PLAYERS_BY_ID);
      event.add(FTBChunksProperties.ALLOW_PVP);
      FTBChunksExpected.getPlatformSpecificProperties(event);
      event.add(FTBChunksProperties.ENTITY_INTERACT_MODE);
      event.add(FTBChunksProperties.NONLIVING_ENTITY_ATTACK_MODE);
      event.add(FTBChunksProperties.CLAIM_VISIBILITY);
      event.add(FTBChunksProperties.LOCATION_MODE);
   }

   private void playerJoinedParty(PlayerJoinedPartyTeamEvent event) {
      ChunkTeamDataImpl playerData = ClaimedChunkManagerImpl.getInstance().getOrCreateData(event.getPreviousTeam());
      ChunkTeamDataImpl partyData = ClaimedChunkManagerImpl.getInstance().getOrCreateData(event.getTeam());
      partyData.addMemberData(event.getPlayer(), playerData);
      partyData.updateLimits();
      this.transferClaims(playerData, partyData, playerData.getClaimedChunks());
      partyData.setForceLoadMember(event.getPlayer().getUUID(), FTBChunksWorldConfig.canPlayerOfflineForceload(event.getPlayer()));
      PlayerVisibilityPacket.syncToLevel(event.getPlayer().level());
      partyData.syncChunksToPlayer(event.getPlayer());
   }

   private void playerLeftParty(PlayerLeftPartyTeamEvent event) {
      ChunkTeamDataImpl partyData = ClaimedChunkManagerImpl.getInstance().getOrCreateData(event.getTeam());
      FTBTeamsAPI.api().getManager().getPlayerTeamForPlayerID(event.getPlayerId()).ifPresent(personalTeam -> {
         ChunkTeamDataImpl playerData = ClaimedChunkManagerImpl.getInstance().getOrCreateData(personalTeam);
         if (event.getTeamDeleted()) {
            this.transferClaims(partyData, playerData, partyData.getClaimedChunks());
            ClaimedChunkManagerImpl.getInstance().deleteTeam(event.getTeam());
         } else {
            this.transferClaims(partyData, playerData, partyData.getOriginalClaims(event.getPlayerId()));
         }

         partyData.deleteMemberData(event.getPlayerId());
         partyData.updateLimits();
         if (event.getPlayer() != null) {
            PlayerVisibilityPacket.syncToLevel(event.getPlayer().level());
            SendGeneralDataPacket.send(playerData, event.getPlayer());
         }
      });
   }

   private void transferClaims(ChunkTeamDataImpl transferFrom, ChunkTeamDataImpl transferTo, Collection<ClaimedChunkImpl> chunksToTransfer) {
      CommandSourceStack sourceStack = ClaimedChunkManagerImpl.getInstance().getMinecraftServer().createCommandSourceStack();
      String fromName = transferFrom.getTeam().getShortName();
      String toName = transferTo.getTeam().getShortName();
      transferFrom.clearClaimCaches();
      transferTo.clearClaimCaches();
      int nChunks = transferTo.getClaimedChunks().size();
      Map<ResourceKey<Level>, List<ChunkSyncInfo>> chunksToSend = new HashMap<>();
      Map<ResourceKey<Level>, List<ChunkSyncInfo>> chunksToUnclaim = new HashMap<>();
      int transferred = 0;
      int unclaimed = 0;
      long now = System.currentTimeMillis();
      int total = transferTo.getClaimedChunks().size();
      LOGGER.info("attempting to transfer {} chunks from {} to {}", chunksToTransfer.size(), fromName, toName);

      for (ClaimedChunkImpl chunk : chunksToTransfer) {
         ChunkDimPos cdp = chunk.getPos();
         if (total >= transferTo.getMaxClaimChunks()) {
            chunk.unclaim(sourceStack, false);
            chunksToUnclaim.computeIfAbsent(cdp.dimension(), s -> new ArrayList<>()).add(ChunkSyncInfo.create(now, cdp.x(), cdp.z(), null));
            unclaimed++;
         } else {
            chunk.setTeamData(transferTo);
            chunksToSend.computeIfAbsent(cdp.dimension(), s -> new ArrayList<>()).add(ChunkSyncInfo.create(now, cdp.x(), cdp.z(), chunk));
            transferred++;
         }

         if (chunk.isForceLoaded()) {
            ServerLevel level = ClaimedChunkManagerImpl.getInstance().getMinecraftServer().getLevel(cdp.dimension());
            if (level != null) {
               FTBChunksExpected.addChunkToForceLoaded(level, "ftbchunks", transferFrom.getTeamId(), cdp.x(), cdp.z(), false);
               if (chunk.isActuallyForceLoaded()) {
                  FTBChunksExpected.addChunkToForceLoaded(level, "ftbchunks", transferTo.getTeamId(), cdp.x(), cdp.z(), true);
               }
            }
         }

         total++;
      }

      transferFrom.updateLimits();
      transferTo.updateLimits();
      if (transferred > 0 || unclaimed > 0) {
         chunksToSend.forEach(
            (dimension, chunkPackets) -> {
               if (!chunkPackets.isEmpty()) {
                  new SendManyChunksPacket((ResourceKey<Level>)dimension, transferTo.getTeamId(), (List<ChunkSyncInfo>)chunkPackets)
                     .sendToAll(sourceStack.getServer(), transferTo);
               }
            }
         );
         chunksToUnclaim.forEach(
            (dimension, chunkPackets) -> {
               if (!chunkPackets.isEmpty()) {
                  NetworkManager.sendToPlayers(
                     sourceStack.getServer().getPlayerList().getPlayers(),
                     new SendManyChunksPacket((ResourceKey<Level>)dimension, Util.NIL_UUID, (List<ChunkSyncInfo>)chunkPackets)
                  );
               }
            }
         );
      }

      LOGGER.info("Transferred {} chunks from {} ({}) to {} ({})", transferred, transferFrom, fromName, transferTo, toName);
      LOGGER.info("Unclaimed {} chunks for {} ({}) due to claim limits", unclaimed, transferFrom, fromName);
      LOGGER.info("Team {} had {} claimed chunks, now has {}", toName, nChunks, nChunks + transferred);
   }

   private void teamOwnershipTransferred(PlayerTransferredTeamOwnershipEvent event) {
      ChunkTeamDataImpl data = ClaimedChunkManagerImpl.getInstance().getOrCreateData(event.getTeam());
      data.updateLimits();
   }

   private void teamPropertiesChanged(TeamPropertiesChangedEvent event) {
      MinecraftServer server = ClaimedChunkManagerImpl.getInstance().getMinecraftServer();
      if (server != null) {
         if (event.getPreviousProperties().get(FTBChunksProperties.LOCATION_MODE) != event.getTeam().getProperty(FTBChunksProperties.LOCATION_MODE)) {
            PlayerVisibilityPacket.syncToAll();
         }

         if (event.getPreviousProperties().get(FTBChunksProperties.CLAIM_VISIBILITY) != event.getTeam().getProperty(FTBChunksProperties.CLAIM_VISIBILITY)) {
            ChunkTeamDataImpl teamData = ClaimedChunkManagerImpl.getInstance().getOrCreateData(event.getTeam());
            teamData.syncChunksToAll(server);
         }

         ClaimedChunkManagerImpl.getInstance().getOrCreateData(event.getTeam()).clearFakePlayerNameCache();
      }
   }

   private void playerAllianceChange(TeamAllyEvent event) {
      ChunkTeamDataImpl teamData = ClaimedChunkManagerImpl.getInstance().getOrCreateData(event.getTeam());
      List<ServerPlayer> players = new ArrayList<>();
      event.getPlayers().forEach(profile -> {
         ServerPlayer p = ClaimedChunkManagerImpl.getInstance().getMinecraftServer().getPlayerList().getPlayer(profile.getId());
         if (p != null) {
            teamData.syncChunksToPlayer(p);
            players.add(p);
         }
      });
      PlayerVisibilityPacket.syncToPlayers(players);
   }

   private void serverTickPost(MinecraftServer minecraftServer) {
      ClaimExpirationManager.INSTANCE.tick(minecraftServer);
      LongRangePlayerTracker.INSTANCE.tick(minecraftServer);
   }

   public static boolean isDevMode() {
      return Platform.isDevelopmentEnvironment() || (Boolean)FTBChunksWorldConfig.DEV_COMMANDS.get();
   }
}
