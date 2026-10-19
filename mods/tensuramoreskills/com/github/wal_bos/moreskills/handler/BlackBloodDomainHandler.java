package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.registry.block.ModBlocks;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import org.joml.Vector3f;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class BlackBloodDomainHandler {
   private static final DustParticleOptions BLACK_BLOOD = new DustParticleOptions(new Vector3f(0.02F, 0.0F, 0.015F), 1.25F);
   private static final DustParticleOptions DARK_RED_BLOOD = new DustParticleOptions(new Vector3f(0.5F, 0.0F, 0.02F), 1.1F);
   private static final Direction[] CURSOR_DIRECTIONS = new Direction[]{
      Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN
   };
   private static final Direction[][] BALANCED_CURSOR_DIRECTIONS = new Direction[][]{
      {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN},
      {Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.UP, Direction.DOWN},
      {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST, Direction.UP, Direction.DOWN},
      {Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.UP, Direction.DOWN},
      {Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN},
      {Direction.WEST, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.UP, Direction.DOWN},
      {Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.NORTH, Direction.UP, Direction.DOWN},
      {Direction.NORTH, Direction.WEST, Direction.EAST, Direction.SOUTH, Direction.UP, Direction.DOWN}
   };
   private static final Direction[] HORIZONTAL_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   private static final int MAX_DOWNWARD_SPREAD = 3;
   private static final int MAX_NODES_PER_SOURCE_PULSE = 4;
   private static final int NODE_DIRECTION_SECTORS = 8;
   private static final int MAX_SOURCES_PER_PULSE = 64;
   private static final int RARE_FORMATION_MAX_BLOCKS = 42;
   private static final int MAX_INVALID_NODE_CHECKS_PER_PASS = 128;
   private static final int MAX_NODE_TICKS_PER_ALTAR_PASS = 48;
   private static final int MAX_SOURCE_CANDIDATES_PER_PULSE = 192;
   private static final List<BlackBloodDomainHandler.DomainFeature> DOMAIN_FEATURES = new ArrayList<>();
   private static final Map<ServerLevel, BlackBloodDomainHandler.DomainLookupCache> DOMAIN_LOOKUP_CACHES = new WeakHashMap<>();
   private static final String DOMAIN_KILL_PAID_TAG = "tensuramoreskills:black_blood_domain_kill_paid";
   private static final String DOMAIN_DAMAGE_TIME_TAG = "tensuramoreskills:black_blood_domain_damage_time";

   private BlackBloodDomainHandler() {
   }

   public static void registerDomainFeature(BlackBloodDomainHandler.DomainFeature feature) {
      if (feature != null && !DOMAIN_FEATURES.contains(feature)) {
         DOMAIN_FEATURES.add(feature);
      }
   }

   @SubscribeEvent
   public static void onEntityTick(Post event) {
      if (event.getEntity() instanceof LivingEntity entity) {
         if (entity.level() instanceof ServerLevel level) {
            MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
            if ((Boolean)cfg.blockInfectionEnabled.get() && (Boolean)cfg.domainBiomeBuffsEnabled.get()) {
               if (entity.tickCount % Math.max(1, (Integer)cfg.domainBiomeBuffInterval.get()) == 0) {
                  BlackBloodDomainData data = BlackBloodDomainData.get(level);
                  if (isCachedDomainChunk(level, data, entity.blockPosition(), (Integer)cfg.domainBiomeChunkValueRequired.get())) {
                     if (BloodfiendHandler.isBloodfiend(entity)) {
                        applyBloodfiendDomainBuff(
                           level, entity, getCachedNearestAltarLevel(level, data, entity.blockPosition(), (Integer)cfg.domainBiomeAltarLevelSearchRadius.get())
                        );
                     } else if (BlackBloodEcologyHandler.isConvertedBloodMinion(entity)) {
                        applyBloodMinionDomainBuff(
                           level, entity, getCachedNearestAltarLevel(level, data, entity.blockPosition(), (Integer)cfg.domainBiomeAltarLevelSearchRadius.get())
                        );
                     } else if ((Boolean)cfg.domainBiomeDamagesEnemies.get()) {
                        applyEnemyDomainPunish(level, entity);
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity.level() instanceof ServerLevel level) {
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         if ((Boolean)cfg.blockInfectionEnabled.get() && (Boolean)cfg.domainBiomeDamagesEnemies.get()) {
            if (!BlackBloodEcologyHandler.isBloodFaction(entity)) {
               long lastDomainDamage = entity.getPersistentData().getLong("tensuramoreskills:black_blood_domain_damage_time");
               boolean recentDomainDamage = lastDomainDamage > 0L && level.getGameTime() - lastDomainDamage <= 200L;
               boolean insideDomain = BlackBloodDomainData.get(level).isDomainChunk(entity.blockPosition(), (Integer)cfg.domainBiomeChunkValueRequired.get());
               if (recentDomainDamage || insideDomain) {
                  convertDomainKillToAltarBlood(level, entity);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLevelTick(net.neoforged.neoforge.event.tick.LevelTickEvent.Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         if (level.getGameTime() % 20L == 0L) {
            MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
            if ((Boolean)cfg.blockInfectionEnabled.get() && (Boolean)cfg.bloodAltarEnabled.get()) {
               BlackBloodDomainData data = BlackBloodDomainData.get(level);
               if (level.getGameTime() % 100L == 0L) {
                  data.removeMissingAltars(level, (Block)ModBlocks.BLOOD_ALTAR.get());
                  data.removeNodesForMissingAltars();
               }

               removeInvalidNodes(level, data, 128);
               if ((Boolean)cfg.altarPassiveSpreadEnabled.get()) {
                  tickAltars(level, data, cfg);
                  tickNodes(level, data, cfg);
               }
            }
         }
      }
   }

   private static void tickAltars(ServerLevel level, BlackBloodDomainData data, MoreskillsRaceConfig.BlackCommunion cfg) {
      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : copyAltars(data)) {
         BlockPos pos = entry.getKey();
         BlackBloodDomainData.AltarData altar = data.getAltar(pos);
         if (altar != null && level.isLoaded(pos) && level.getBlockState(pos).is((Block)ModBlocks.BLOOD_ALTAR.get())) {
            int fuelBlood = Math.max(0, altar.fuelBlood());
            if (fuelBlood > 0) {
               int levelValue = BlackBloodConquestHandler.getDomainLevel(cfg, altar, BlackBloodConquestData.get(level));
               int interval = levelValue <= 0 ? Math.max(1, (Integer)cfg.dormantSpreadInterval.get()) : Math.max(1, (Integer)cfg.altarSpreadInterval.get());
               if (level.getGameTime() % interval == 0L) {
                  BlackBloodDomainHandler.SpendPulseResult result = spendAllFuelThisPulse(level, data, pos, pos, 0, cfg, levelValue, false);
                  BlackBloodDomainData.AltarData updated = data.getAltar(pos);
                  if (updated != null && updated.fuelBlood() <= 0 && result.spentBlood() > 0) {
                     level.sendParticles(BLACK_BLOOD, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 12, 0.35, 0.25, 0.35, 0.01);
                     level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 0.7F, 0.45F);
                  }
               }
            }
         }
      }
   }

   private static void tickNodes(ServerLevel level, BlackBloodDomainData data, MoreskillsRaceConfig.BlackCommunion cfg) {
      long time = level.getGameTime();
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> altarEntry : data.altars()) {
         BlockPos altarPos = altarEntry.getKey();
         BlackBloodDomainData.AltarData altar = data.getAltar(altarPos);
         if (altar != null && altar.fuelBlood() > 0) {
            int nodeCount = data.countNodesForAltar(altarPos);
            if (nodeCount > 0) {
               int budget = Math.min(nodeCount, Math.max(48, Math.max(1, (Integer)cfg.altarSpreadAttempts.get())));
               int levelValue = BlackBloodConquestHandler.getDomainLevel(cfg, altar, conquest);

               for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : data.nextNodesForAltar(altarPos, budget)) {
                  BlockPos nodePos = entry.getKey();
                  BlackBloodDomainData.NodeData node = data.getNode(nodePos);
                  if (node != null && level.isLoaded(nodePos)) {
                     if (!isInfectionSource(level.getBlockState(nodePos))) {
                        data.removeNode(nodePos);
                     } else {
                        int interval = Math.max(1, (Integer)cfg.altarSpreadInterval.get() * 2 + Math.max(0, node.generation() - 1) * 20);
                        if (time % interval == 0L) {
                           spendAllFuelThisPulse(level, data, altarPos, nodePos, node.generation(), cfg, levelValue, false);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static BlackBloodDomainHandler.SpendPulseResult spendAllFuelThisPulse(
      ServerLevel level,
      BlackBloodDomainData data,
      BlockPos altarPos,
      BlockPos primarySource,
      int primaryGeneration,
      MoreskillsRaceConfig.BlackCommunion cfg,
      int altarLevel,
      boolean fast
   ) {
      int totalChanged = 0;
      int totalNodes = 0;
      int totalSpent = 0;
      int failedRounds = 0;
      int rounds = 0;

      while (rounds < 16) {
         rounds++;
         BlackBloodDomainData.AltarData altar = data.getAltar(altarPos);
         if (altar == null) {
            break;
         }

         int blockCost = getBlockInfectionFuelCost(cfg);
         int currentFuel = Math.max(0, altar.fuelBlood());
         if (currentFuel < blockCost) {
            break;
         }

         List<BlackBloodDomainHandler.SpreadSource> sources = collectPulseSources(level, data, altarPos, primarySource, primaryGeneration);
         if (sources.isEmpty()) {
            break;
         }

         boolean progressed = false;

         for (int i = 0; i < sources.size(); i++) {
            BlackBloodDomainHandler.SpreadSource source = sources.get(i);
            altar = data.getAltar(altarPos);
            if (altar == null) {
               return new BlackBloodDomainHandler.SpendPulseResult(totalChanged, totalNodes, totalSpent);
            }

            currentFuel = Math.max(0, altar.fuelBlood());
            if (currentFuel < blockCost) {
               return new BlackBloodDomainHandler.SpendPulseResult(totalChanged, totalNodes, totalSpent);
            }

            if (level.isLoaded(source.pos()) && isInfectionSource(level.getBlockState(source.pos()))) {
               int remainingSources = Math.max(1, sources.size() - i);
               int fuelLimitedBlocks = Math.max(1, currentFuel / blockCost);
               int sourceBlockBudget = Math.max(1, fuelLimitedBlocks / remainingSources);
               int sourceRadius = getSourceSpreadRadius(cfg, altarLevel, source.generation());
               int maxSearch = getAllFuelPulseSearchBudget(cfg, sourceBlockBudget, sourceRadius, fast);
               BlackBloodDomainHandler.SpreadResult spread = spreadUsingAltarFuel(
                  level,
                  data,
                  altarPos,
                  source.pos(),
                  source.generation(),
                  sourceBlockBudget,
                  maxSearch,
                  (Integer)cfg.domainChunkValuePerBlock.get(),
                  fast,
                  false
               );
               if (spread.spentBlood() > 0) {
                  progressed = true;
                  totalChanged += spread.changed();
                  totalNodes += spread.nodesCreated();
                  totalSpent += spread.spentBlood();
               }

               int formationSpent = tryGenerateRareDomainFormation(level, data, altarPos, source.pos(), source.generation(), cfg, altarLevel, fast);
               if (formationSpent > 0) {
                  progressed = true;
                  totalSpent += formationSpent;
               }

               if (spread.changed() <= 0 && spread.nodesCreated() <= 0 && formationSpent <= 0 && source.generation() > 0 && !source.pos().equals(primarySource)
                  )
                {
                  data.removeNode(source.pos());
               }
            }
         }

         altar = data.getAltar(altarPos);
         if (altar == null || altar.fuelBlood() < getBlockInfectionFuelCost(cfg)) {
            break;
         }

         if (progressed) {
            failedRounds = 0;
         } else {
            boolean createdRelay = false;

            for (BlackBloodDomainHandler.SpreadSource source : sources) {
               altar = data.getAltar(altarPos);
               if (altar == null || altar.fuelBlood() < getNodeCreationFuelCost(cfg, altarLevel)) {
                  break;
               }

               int beforeFuel = altar.fuelBlood();
               int beforeNodes = data.countNodesForAltar(altarPos);
               ensureRelayNodesFromSource(level, data, altarPos, source.pos(), cfg, altarLevel, source.generation());
               altar = data.getAltar(altarPos);
               int afterFuel = altar == null ? beforeFuel : altar.fuelBlood();
               int afterNodes = data.countNodesForAltar(altarPos);
               if (afterNodes > beforeNodes && afterFuel < beforeFuel) {
                  createdRelay = true;
                  totalNodes += afterNodes - beforeNodes;
                  totalSpent += beforeFuel - afterFuel;
               }
            }

            if (!createdRelay) {
               if (++failedRounds >= 2) {
                  break;
               }
            } else {
               failedRounds = 0;
            }
         }
      }

      return new BlackBloodDomainHandler.SpendPulseResult(totalChanged, totalNodes, totalSpent);
   }

   private static List<BlackBloodDomainHandler.SpreadSource> collectPulseSources(
      ServerLevel level, BlackBloodDomainData data, BlockPos altarPos, BlockPos primarySource, int primaryGeneration
   ) {
      List<List<BlackBloodDomainHandler.SpreadSource>> buckets = new ArrayList<>();

      for (int i = 0; i < 8; i++) {
         buckets.add(new ArrayList<>());
      }

      Set<Long> used = new HashSet<>();
      if (level.isLoaded(primarySource) && isInfectionSource(level.getBlockState(primarySource))) {
         int sector = getSector(primarySource.getX() - altarPos.getX(), primarySource.getZ() - altarPos.getZ());
         buckets.get(sector).add(new BlackBloodDomainHandler.SpreadSource(primarySource, Math.max(0, primaryGeneration), sector));
         used.add(primarySource.asLong());
      }

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : data.nextNodesForAltar(altarPos, 192)) {
         BlockPos nodePos = entry.getKey();
         BlackBloodDomainData.NodeData node = entry.getValue();
         if (!used.contains(nodePos.asLong()) && level.isLoaded(nodePos)) {
            if (!isInfectionSource(level.getBlockState(nodePos))) {
               data.removeNode(nodePos);
            } else {
               int sector = getSector(nodePos.getX() - altarPos.getX(), nodePos.getZ() - altarPos.getZ());
               buckets.get(sector).add(new BlackBloodDomainHandler.SpreadSource(nodePos, Math.max(1, node.generation()), sector));
               used.add(nodePos.asLong());
            }
         }
      }

      for (List<BlackBloodDomainHandler.SpreadSource> bucket : buckets) {
         bucket.sort((left, right) -> Double.compare(right.pos().distSqr(altarPos), left.pos().distSqr(altarPos)));
      }

      List<BlackBloodDomainHandler.SpreadSource> sources = new ArrayList<>();
      int startSector = Math.floorMod((int)(level.getGameTime() / 20L), 8);

      for (int depth = 0; sources.size() < 64; depth++) {
         boolean added = false;

         for (int offset = 0; offset < 8 && sources.size() < 64; offset++) {
            int sector = Math.floorMod(startSector + offset, 8);
            List<BlackBloodDomainHandler.SpreadSource> bucket = buckets.get(sector);
            if (depth < bucket.size()) {
               sources.add(bucket.get(depth));
               added = true;
            }
         }

         if (!added) {
            break;
         }
      }

      return sources;
   }

   private static int getAllFuelPulseSearchBudget(MoreskillsRaceConfig.BlackCommunion cfg, int fuelLimitedBlocks, int sourceRadius, boolean fast) {
      long byFuel = Math.max(1, fuelLimitedBlocks) * (fast ? 96L : 64L);
      long byConfig = Math.max(8, (Integer)cfg.altarSpreadAttempts.get()) * (fast ? 64L : 48L);
      long byRadius = Math.max(8, sourceRadius) * (fast ? 96L : 64L);
      long budget = Math.max(Math.max(byFuel, byConfig), byRadius);
      return (int)Math.min(1000000L, Math.max(64L, budget));
   }

   public static void onCommunionPulse(LivingEntity caster, List<LivingEntity> linked, double radius, double power) {
      if (caster.level() instanceof ServerLevel level) {
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         if ((Boolean)cfg.blockInfectionEnabled.get()) {
            if ((Boolean)cfg.bloodAltarEnabled.get()) {
               BlackBloodDomainData data = BlackBloodDomainData.get(level);
               BlockPos altarPos = getNearestAltarPos(data, caster.blockPosition(), (Integer)cfg.altarNoSpawnNearRadius.get());
               if (altarPos == null) {
                  altarPos = trySpawnAltar(level, data, caster);
               }

               if (altarPos != null) {
                  if (level.isLoaded(altarPos)) {
                     if (level.getBlockState(altarPos).is((Block)ModBlocks.BLOOD_ALTAR.get())) {
                        int seedBlocks = Math.max(1, Math.min((Integer)cfg.blockInfectionBlocksPerPulse.get(), (Integer)cfg.altarSacrificeBurstBlocks.get()));
                        int seedSearch = Math.max(16, Math.min((Integer)cfg.blockInfectionAttempts.get(), (Integer)cfg.altarSacrificeBurstAttempts.get()));
                        spreadUsingAltarFuel(
                           level, data, altarPos, altarPos, 0, seedBlocks, seedSearch, (Integer)cfg.domainChunkValuePerBlock.get(), true, true
                        );
                     }
                  }
               }
            }
         }
      }
   }

   private static int drainBloodIntoAltar(ServerPlayer player, int maxAmount) {
      if (maxAmount <= 0) {
         return 0;
      } else {
         int storedBlood = Math.max(0, BloodfiendData.getBlood(player));
         if (storedBlood <= 0) {
            return 0;
         } else {
            int drained = Math.min(storedBlood, maxAmount);
            if (drained <= 0) {
               return 0;
            } else {
               return !BloodfiendData.consumeBlood(player, drained) ? 0 : drained;
            }
         }
      }
   }

   public static boolean sacrificeToAltar(ServerPlayer player, BlockPos pos) {
      ServerLevel level = player.serverLevel();
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if (!(Boolean)cfg.blockInfectionEnabled.get() || !(Boolean)cfg.bloodAltarEnabled.get() || !(Boolean)cfg.altarSacrificeEnabled.get()) {
         return false;
      }

      if (!level.isLoaded(pos)) {
         return false;
      }

      if (!level.getBlockState(pos).is((Block)ModBlocks.BLOOD_ALTAR.get())) {
         return false;
      }

      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.message.black_communion.altar_bloodfiend_only").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      BlackBloodDomainData data = BlackBloodDomainData.get(level);
      BlackBloodDomainData.AltarData altar = data.getAltar(pos);
      if (altar == null) {
         data.addAltar(pos, player.getUUID());
         altar = data.getAltar(pos);
      }

      int freeSpace = Math.max(0, (Integer)cfg.altarMaxStoredBlood.get() - Math.max(0, altar.fuelBlood()));
      if (freeSpace <= 0) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.message.black_communion.altar_full").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      } else {
         int offeredBlood = drainBloodIntoAltar(player, freeSpace);
         if (offeredBlood <= 0) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.message.black_communion.altar_not_enough_blood").withStyle(ChatFormatting.DARK_RED), true
            );
            return false;
         } else {
            int fuelBlood = Math.min((Integer)cfg.altarMaxStoredBlood.get(), safeAdd(altar.fuelBlood(), offeredBlood));
            int totalBlood = safeAdd(altar.totalBlood(), offeredBlood);
            int unlockedLevel = Math.max(altar.level(), getAltarLevel(cfg, totalBlood));
            altar = new BlackBloodDomainData.AltarData(altar.owner(), fuelBlood, totalBlood, unlockedLevel, altar.generatedBlood(), altar.domainKills());
            data.setAltar(pos, altar);
            data.addChunkValue(pos, (Integer)cfg.domainChunkValueSacrificeBonus.get(), (Integer)cfg.domainChunkMaxValue.get());
            int domainLevel = BlackBloodConquestHandler.getDomainLevel(cfg, altar, BlackBloodConquestData.get(level));
            int burstBlocks = Math.max(1, (Integer)cfg.altarSacrificeBurstBlocks.get() + domainLevel * (Integer)cfg.altarSacrificeBurstBlocksPerLevel.get());
            int burstSearch = Math.max(16, (Integer)cfg.altarSacrificeBurstAttempts.get() + domainLevel * 24);
            BlackBloodDomainHandler.SpreadResult burstResult = spreadUsingAltarFuel(
               level, data, pos, pos, 0, burstBlocks, burstSearch, (Integer)cfg.domainChunkValuePerBlock.get(), true, true
            );
            altar = data.getAltar(pos);
            fuelBlood = altar == null ? 0 : Math.max(0, altar.fuelBlood());
            BloodfiendHandler.syncBlood(player);
            level.sendParticles(DARK_RED_BLOOD, pos.getX() + 0.5, pos.getY() + 1.15, pos.getZ() + 0.5, 45, 0.55, 0.45, 0.55, 0.04);
            level.sendParticles(ParticleTypes.SCULK_SOUL, pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5, 12, 0.45, 0.35, 0.45, 0.02);
            level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.25F, 0.55F);
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.message.black_communion.altar_sacrifice", new Object[]{fuelBlood, totalBlood, unlockedLevel})
                  .withStyle(ChatFormatting.DARK_RED),
               true
            );
            return true;
         }
      }
   }

   public static boolean showAltarInfo(ServerPlayer player, BlockPos pos) {
      ServerLevel level = player.serverLevel();
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if (!(Boolean)cfg.blockInfectionEnabled.get() || !(Boolean)cfg.bloodAltarEnabled.get()) {
         return false;
      }

      if (!level.isLoaded(pos)) {
         return false;
      }

      if (!level.getBlockState(pos).is((Block)ModBlocks.BLOOD_ALTAR.get())) {
         return false;
      }

      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.message.black_communion.altar_bloodfiend_only").withStyle(ChatFormatting.DARK_RED), true
         );
         return true;
      }

      BlackBloodDomainData data = BlackBloodDomainData.get(level);
      BlackBloodDomainData.AltarData altar = data.getAltar(pos);
      if (altar == null) {
         data.addAltar(pos, player.getUUID());
         altar = data.getAltar(pos);
      }

      if (altar == null) {
         return false;
      }

      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      int domainLevel = BlackBloodConquestHandler.getDomainLevel(cfg, altar, conquest);
      int domainXp = BlackBloodConquestHandler.getDomainXp(cfg, altar, conquest);
      int nextDomainXp = BlackBloodConquestHandler.getNextDomainLevelXp(cfg, domainLevel);
      int fuelBlood = Math.max(0, altar.fuelBlood());
      int totalBlood = Math.max(0, altar.totalBlood());
      int radius = Math.max(1, (int)Math.ceil((Double)cfg.altarBaseRadius.get() + domainLevel * (Double)cfg.altarRadiusPerLevel.get()));
      int passiveBlocks = domainLevel <= 0
         ? (Integer)cfg.dormantSpreadBlocksPerPulse.get()
         : (Integer)cfg.altarSpreadBlocksPerPulse.get() + domainLevel * (Integer)cfg.altarSpreadBlocksPerLevel.get();
      int interval = domainLevel <= 0 ? (Integer)cfg.dormantSpreadInterval.get() : (Integer)cfg.altarSpreadInterval.get();
      int chunkValue = data.getChunkValue(pos);
      int totalNodes = data.countNodesForAltar(pos);
      int activeNodes = countActiveNodesForAltar(level, data, pos);
      int generatedBlood = Math.max(0, altar.generatedBlood());
      int domainKills = Math.max(0, altar.domainKills());
      int targetDistance = BlackBloodConquestHandler.getBestKnownTargetDistanceBlocks(level, pos, conquest);
      player.sendSystemMessage(Component.literal("Black Blood Altar").withStyle(ChatFormatting.DARK_RED));
      player.sendSystemMessage(Component.literal("Domain Level: " + domainLevel + " / " + cfg.domainMaxLevel.get()).withStyle(ChatFormatting.RED));
      player.sendSystemMessage(
         Component.literal("Domain XP: " + domainXp + (nextDomainXp > 0 ? " / " + nextDomainXp + " next" : " / max")).withStyle(ChatFormatting.RED)
      );
      player.sendSystemMessage(Component.literal("Fuel Blood: " + fuelBlood + " / " + cfg.altarMaxStoredBlood.get()).withStyle(ChatFormatting.RED));
      player.sendSystemMessage(Component.literal("Lifetime Blood: " + totalBlood + " + conquest growth").withStyle(ChatFormatting.RED));
      player.sendSystemMessage(
         Component.literal("Spread: " + Math.max(0, passiveBlocks) + " blocks every " + interval + " ticks, radius " + radius)
            .withStyle(ChatFormatting.DARK_RED)
      );
      player.sendSystemMessage(
         Component.literal("Domain Chunk Value: " + chunkValue + " / " + cfg.domainBiomeChunkValueRequired.get()).withStyle(ChatFormatting.DARK_RED)
      );
      player.sendSystemMessage(Component.literal("Active Nodes: " + activeNodes + " / " + totalNodes + " relay anchors").withStyle(ChatFormatting.DARK_RED));
      player.sendSystemMessage(
         Component.literal("Generated Blood: " + generatedBlood + " from " + domainKills + " domain kills").withStyle(ChatFormatting.DARK_RED)
      );
      player.sendSystemMessage(
         Component.literal(
               "Conquest: stage "
                  + conquest.worldStage()
                  + ", roots "
                  + conquest.rootCount()
                  + ", blooms "
                  + conquest.createdBlooms()
                  + ", breached bases "
                  + conquest.baseBreaches()
                  + ", conquered chunks "
                  + conquest.conqueredChunks()
            )
            .withStyle(ChatFormatting.DARK_RED)
      );
      player.sendSystemMessage(
         Component.literal("World Heart: " + (conquest.worldHeartPos() == null ? "not formed" : conquest.worldHeartPos().toShortString()))
            .withStyle(ChatFormatting.RED)
      );
      player.sendSystemMessage(
         Component.literal(
               "Longest Known Tunnel Target: "
                  + (targetDistance <= 0 ? "none" : targetDistance + " blocks")
                  + " / "
                  + cfg.conquestRootMaxTargetDistanceBlocks.get()
                  + " allowed"
            )
            .withStyle(ChatFormatting.DARK_RED)
      );
      player.sendSystemMessage(
         Component.literal(
               "Fuel Use: " + getBlockInfectionFuelCost(cfg) + " fuel per infected block, " + getNodeCreationFuelCost(cfg, domainLevel) + " per relay node"
            )
            .withStyle(ChatFormatting.DARK_RED)
      );
      player.sendSystemMessage(
         Component.literal("Buff Multiplier: x" + String.format(Locale.ROOT, "%.2f", 1.0 + domainLevel * (Double)cfg.domainBiomeBuffPerAltarLevel.get()))
            .withStyle(ChatFormatting.RED)
      );
      player.sendSystemMessage(
         Component.literal(
               "Commands Available In Code: commandMarkConquestTarget, commandRootTunnel, commandBloodTide, commandBloomEruption, commandWorldHeartPulse, commandHarvestBloomNetwork"
            )
            .withStyle(ChatFormatting.DARK_RED)
      );
      return true;
   }

   public static boolean isBlackBloodDomain(ServerLevel level, BlockPos pos) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      return cfg.blockInfectionEnabled.get() && cfg.domainBiomeBuffsEnabled.get()
         ? BlackBloodDomainData.get(level).isDomainChunk(pos, (Integer)cfg.domainBiomeChunkValueRequired.get())
         : false;
   }

   public static void trySlowNaturalSpread(ServerLevel level, BlockPos anchor, int blocks) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if ((Boolean)cfg.blockInfectionEnabled.get() && (Boolean)cfg.altarPassiveSpreadEnabled.get()) {
         if (level.isLoaded(anchor)) {
            if (isInfectedBlock(level.getBlockState(anchor)) || level.getBlockState(anchor).is((Block)ModBlocks.BLOOD_ALTAR.get())) {
               BlackBloodDomainData data = BlackBloodDomainData.get(level);
               BlockPos altarPos = getNearestAltarPos(
                  data, anchor, Math.max((Integer)cfg.domainBiomeAltarLevelSearchRadius.get(), (Integer)cfg.altarNoSpawnNearRadius.get())
               );
               if (altarPos != null) {
                  spreadUsingAltarFuel(
                     level,
                     data,
                     altarPos,
                     anchor,
                     1,
                     Math.max(1, blocks),
                     Math.max(8, (Integer)cfg.altarSpreadAttempts.get() / 4),
                     (Integer)cfg.domainChunkValuePerBlock.get(),
                     false,
                     true
                  );
               }
            }
         }
      }
   }

   public static void damageEnemyOnMire(ServerLevel level, LivingEntity entity) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if ((Boolean)cfg.blockInfectionEnabled.get() && (Boolean)cfg.domainBiomeDamagesEnemies.get()) {
         if (!BlackBloodEcologyHandler.isBloodFaction(entity)) {
            if ((Double)cfg.domainBiomeEnemyDamage.get() > 0.0) {
               hurtWithDomainBloodConversion(level, entity, ((Double)cfg.domainBiomeEnemyDamage.get()).floatValue());
            }
         }
      }
   }

   public static boolean isNaturalSpreadEdge(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return false;
      }

      for (Direction direction : Direction.values()) {
         BlockPos check = pos.relative(direction);
         if (level.isLoaded(check)) {
            BlockState state = level.getBlockState(check);
            if (!state.isAir()) {
               if (isClearablePlant(state) && level.isLoaded(check.below()) && canInfect(level, check.below())) {
                  return true;
               }

               if (!isInfectedBlock(state) && canInfect(level, check)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private static int cursorSpreadFromAnchor(ServerLevel level, BlackBloodDomainData data, BlockPos anchor, int maxBlocks, int maxSearch, int chunkValue) {
      if (maxBlocks > 0 && maxSearch > 0) {
         ArrayDeque<BlockPos> queue = new ArrayDeque<>();
         Set<Long> visited = new HashSet<>();
         int changed = 0;
         int searched = 0;
         queue.add(anchor);
         visited.add(anchor.asLong());

         while (!queue.isEmpty() && changed < maxBlocks && searched < maxSearch) {
            BlockPos current = queue.poll();
            searched++;

            for (Direction direction : balancedCursorDirections(current, anchor)) {
               if (changed >= maxBlocks || searched >= maxSearch) {
                  break;
               }

               BlockPos next = getSurfaceBiasedNext(level, current, direction, anchor.getY() - 3);
               if (next != null && level.isLoaded(next)) {
                  long key = next.asLong();
                  if (!visited.contains(key)) {
                     visited.add(key);
                     BlockState nextState = level.getBlockState(next);
                     if (canInfectFrom(level, current, next)) {
                        clearCoveringVegetation(level, next);
                        level.setBlock(next, getInfectedState(nextState), 3);
                        data.addChunkValue(next, chunkValue, (Integer)MoreskillsRaceConfig.get().BlackCommunion.domainChunkMaxValue.get());
                        changed++;
                        queue.add(next);
                        if (level.random.nextInt(4) == 0) {
                           level.sendParticles(BLACK_BLOOD, next.getX() + 0.5, next.getY() + 1.05, next.getZ() + 0.5, 2, 0.35, 0.15, 0.35, 0.01);
                        }
                     } else if (canCursorTravelThrough(level, next, nextState)) {
                        queue.add(next);
                     }
                  }
               }
            }
         }

         return changed;
      } else {
         return 0;
      }
   }

   private static BlockPos trySpawnAltar(ServerLevel level, BlackBloodDomainData data, LivingEntity caster) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if (!(Boolean)cfg.bloodAltarEnabled.get()) {
         return null;
      }

      if (!(Boolean)cfg.allowMultipleBloodAltars.get() && data.hasAnyAltar()) {
         return null;
      }

      if (data.hasAltarNear(caster.blockPosition(), (Integer)cfg.altarNoSpawnNearRadius.get())) {
         return null;
      }

      BlockPos pos = findAltarPos(level, caster.blockPosition(), (Integer)cfg.altarSpawnSearchRadius.get());
      if (pos == null) {
         return null;
      }

      level.setBlock(pos, ((Block)ModBlocks.BLOOD_ALTAR.get()).defaultBlockState(), 3);
      data.addAltar(pos, caster.getUUID());
      data.addChunkValue(pos, (Integer)cfg.domainChunkValueSacrificeBonus.get(), (Integer)cfg.domainChunkMaxValue.get());
      level.sendParticles(DARK_RED_BLOOD, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 80, 0.65, 0.55, 0.65, 0.05);
      level.sendParticles(ParticleTypes.SCULK_SOUL, pos.getX() + 0.5, pos.getY() + 1.3, pos.getZ() + 0.5, 20, 0.55, 0.45, 0.55, 0.03);
      level.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 1.1F, 0.45F);
      return pos;
   }

   private static BlockPos getNearestAltarPos(BlackBloodDomainData data, BlockPos center, int radius) {
      int safeRadius = Math.max(1, radius);
      int radiusSqr = safeRadius * safeRadius;
      BlockPos best = null;
      double bestDistance = Double.MAX_VALUE;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : data.altars()) {
         double distance = entry.getKey().distSqr(center);
         if (distance <= radiusSqr && distance < bestDistance) {
            best = entry.getKey();
            bestDistance = distance;
         }
      }

      return best;
   }

   private static int getAltarLevel(MoreskillsRaceConfig.BlackCommunion cfg, int blood) {
      int level = 0;
      if (blood >= (Integer)cfg.altarLevel1Blood.get()) {
         level = 1;
      }

      if (blood >= (Integer)cfg.altarLevel2Blood.get()) {
         level = 2;
      }

      if (blood >= (Integer)cfg.altarLevel3Blood.get()) {
         level = 3;
      }

      if (blood >= (Integer)cfg.altarLevel4Blood.get()) {
         level = 4;
      }

      if (blood >= (Integer)cfg.altarLevel5Blood.get()) {
         level = 5;
      }

      return Math.min((Integer)cfg.altarMaxLevel.get(), level);
   }

   private static int getNextAltarLevelBlood(MoreskillsRaceConfig.BlackCommunion cfg, int level) {
      if (level < 1) {
         return (Integer)cfg.altarLevel1Blood.get();
      } else if (level < 2) {
         return (Integer)cfg.altarLevel2Blood.get();
      } else if (level < 3) {
         return (Integer)cfg.altarLevel3Blood.get();
      } else if (level < 4) {
         return (Integer)cfg.altarLevel4Blood.get();
      } else {
         return level < 5 && cfg.altarMaxLevel.get() >= 5 ? (Integer)cfg.altarLevel5Blood.get() : -1;
      }
   }

   private static int safeAdd(int current, int add) {
      if (add <= 0) {
         return current;
      } else {
         return current >= Integer.MAX_VALUE - add ? Integer.MAX_VALUE : current + add;
      }
   }

   private static BlockPos findAltarPos(ServerLevel level, BlockPos center, int radius) {
      BlockPos direct = findDirectAltarPos(level, center);
      if (direct != null) {
         return direct;
      }

      int realRadius = Math.max(1, radius);

      for (int r = 1; r <= realRadius; r++) {
         for (int x = -r; x <= r; x++) {
            for (int z = -r; z <= r; z++) {
               if (Math.abs(x) == r || Math.abs(z) == r) {
                  for (int y = -3; y <= 3; y++) {
                     BlockPos base = center.offset(x, y, z);
                     BlockPos ground = findGround(level, base);
                     if (ground != null) {
                        BlockPos altar = ground.above();
                        if (canPlaceAltarAt(level, ground, altar)) {
                           return altar;
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private static BlockPos findDirectAltarPos(ServerLevel level, BlockPos center) {
      for (int y = 0; y <= 4; y++) {
         BlockPos ground = center.below(y);
         BlockPos altar = ground.above();
         if (canPlaceAltarAt(level, ground, altar)) {
            return altar;
         }
      }

      for (int y = 1; y <= 4; y++) {
         BlockPos ground = center.above(y);
         BlockPos altar = ground.above();
         if (canPlaceAltarAt(level, ground, altar)) {
            return altar;
         }
      }

      return null;
   }

   private static boolean canPlaceAltarAt(ServerLevel level, BlockPos ground, BlockPos altar) {
      if (!level.isLoaded(ground) || !level.isLoaded(altar)) {
         return false;
      } else if (!canHoldAltar(level, ground)) {
         return false;
      } else {
         return !level.getBlockState(altar).isAir() ? false : level.getBlockState(altar.above()).isAir();
      }
   }

   private static boolean canHoldAltar(ServerLevel level, BlockPos ground) {
      if (!level.isLoaded(ground)) {
         return false;
      } else {
         BlockState state = level.getBlockState(ground);
         if (state.isAir()) {
            return false;
         } else if (state.hasBlockEntity()) {
            return false;
         } else {
            return !state.is(Blocks.BEDROCK)
                  && !state.is(Blocks.END_PORTAL_FRAME)
                  && !state.is(Blocks.END_PORTAL)
                  && !state.is(Blocks.NETHER_PORTAL)
                  && !state.is(Blocks.COMMAND_BLOCK)
                  && !state.is(Blocks.CHAIN_COMMAND_BLOCK)
                  && !state.is(Blocks.REPEATING_COMMAND_BLOCK)
                  && !state.is(Blocks.BARRIER)
                  && !state.is(Blocks.STRUCTURE_BLOCK)
                  && !state.is(Blocks.JIGSAW)
               ? state.isFaceSturdy(level, ground, Direction.UP)
               : false;
         }
      }
   }

   private static BlockPos findGround(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return null;
      }

      MutableBlockPos mutable = pos.mutable();

      for (int i = 0; i < 12; i++) {
         if (!level.isLoaded(mutable)) {
            return null;
         }

         BlockState state = level.getBlockState(mutable);
         if (!state.isAir() && level.getBlockState(mutable.above()).isAir()) {
            return mutable.immutable();
         }

         mutable.move(Direction.DOWN);
      }

      mutable.set(pos);

      for (int i = 0; i < 12; i++) {
         if (!level.isLoaded(mutable)) {
            return null;
         }

         BlockState state = level.getBlockState(mutable);
         if (!state.isAir() && level.getBlockState(mutable.above()).isAir()) {
            return mutable.immutable();
         }

         mutable.move(Direction.UP);
      }

      return null;
   }

   private static BlackBloodDomainHandler.SpreadResult spreadUsingAltarFuel(
      ServerLevel level,
      BlackBloodDomainData data,
      BlockPos altarPos,
      BlockPos sourcePos,
      int sourceGeneration,
      int requestedBlocks,
      int maxSearch,
      int chunkValue,
      boolean fast,
      boolean allowNodeCreation
   ) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      BlackBloodDomainData.AltarData altar = data.getAltar(altarPos);
      if (altar == null) {
         return BlackBloodDomainHandler.SpreadResult.EMPTY;
      }

      int fuelBlood = Math.max(0, altar.fuelBlood());
      if (fuelBlood <= 0) {
         return BlackBloodDomainHandler.SpreadResult.EMPTY;
      }

      int blockCost = getBlockInfectionFuelCost(cfg);
      int nodeCost = getNodeCreationFuelCost(cfg, BlackBloodConquestHandler.getDomainLevel(cfg, altar, BlackBloodConquestData.get(level)));
      int maxBlocks = Math.max(0, Math.min(Math.max(0, requestedBlocks), fuelBlood / blockCost));
      int maxNodes = allowNodeCreation && nodeCost > 0 ? Math.max(0, Math.min(4, fuelBlood / nodeCost)) : 0;
      if (maxBlocks <= 0 && maxNodes <= 0) {
         return BlackBloodDomainHandler.SpreadResult.EMPTY;
      }

      BlackBloodDomainHandler.SpreadResult result = cursorSpreadFromSource(
         level, data, altarPos, sourcePos, Math.max(0, sourceGeneration), maxBlocks, maxSearch, chunkValue, fast, maxNodes, 0, fuelBlood, blockCost, nodeCost
      );
      int spent = Math.min(fuelBlood, result.actualFuelCost(blockCost, nodeCost));
      if (spent <= 0) {
         return result.withSpentBlood(0);
      }

      BlackBloodDomainData.AltarData latest = data.getAltar(altarPos);
      if (latest != null) {
         data.setAltar(altarPos, latest.withFuelBlood(Math.max(0, latest.fuelBlood() - spent)));
      }

      return result.withSpentBlood(spent);
   }

   private static BlackBloodDomainHandler.SpreadResult cursorSpreadFromSource(
      ServerLevel level,
      BlackBloodDomainData data,
      BlockPos altarPos,
      BlockPos sourcePos,
      int sourceGeneration,
      int maxBlocks,
      int maxSearch,
      int chunkValue,
      boolean fast,
      int maxNodeCreations,
      int maxFeatures,
      int fuelBudget,
      int blockCost,
      int nodeCost
   ) {
      if ((maxBlocks > 0 || maxNodeCreations > 0 || maxFeatures > 0) && maxSearch > 0 && fuelBudget > 0) {
         if (!level.isLoaded(sourcePos)) {
            return BlackBloodDomainHandler.SpreadResult.EMPTY;
         }

         if (!isInfectionSource(level.getBlockState(sourcePos))) {
            return BlackBloodDomainHandler.SpreadResult.EMPTY;
         }

         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         ArrayDeque<BlockPos> queue = new ArrayDeque<>();
         Set<Long> visited = new HashSet<>();
         int changed = 0;
         int searched = 0;
         int nodesCreated = 0;
         int fuelSpent = 0;
         BlackBloodDomainData.AltarData altarData = data.getAltar(altarPos);
         int altarLevel = altarData == null ? 0 : altarData.level();
         int maxRange = getSourceSpreadRadius(cfg, altarLevel, sourceGeneration);
         int minY = sourcePos.getY() - 3;
         queue.add(sourcePos);
         visited.add(sourcePos.asLong());

         while (!queue.isEmpty() && fuelSpent < fuelBudget && (changed < maxBlocks || nodesCreated < maxNodeCreations) && searched < maxSearch) {
            BlockPos current = queue.poll();
            searched++;

            for (Direction direction : balancedCursorDirections(current, sourcePos)) {
               if (fuelSpent >= fuelBudget || changed >= maxBlocks && nodesCreated >= maxNodeCreations || searched >= maxSearch) {
                  break;
               }

               BlockPos next = getSurfaceBiasedNext(level, current, direction, minY);
               if (next != null && level.isLoaded(next)) {
                  if (!next.closerThan(sourcePos, maxRange)) {
                     if (nodesCreated < maxNodeCreations
                        && fuelSpent + nodeCost <= fuelBudget
                        && tryCreateEdgeNode(level, data, current, altarPos, altarData, sourceGeneration + 1)) {
                        nodesCreated++;
                        fuelSpent += nodeCost;
                     }
                  } else {
                     long key = next.asLong();
                     if (!visited.contains(key)) {
                        visited.add(key);
                        BlockState nextState = level.getBlockState(next);
                        if (changed < maxBlocks && fuelSpent + blockCost <= fuelBudget && canInfectFrom(level, current, next)) {
                           clearCoveringVegetation(level, next);
                           level.setBlock(next, getInfectedState(nextState), 3);
                           data.addChunkValue(next, chunkValue, (Integer)cfg.domainChunkMaxValue.get());
                           changed++;
                           fuelSpent += blockCost;
                           queue.add(next);
                           if (fast || level.random.nextInt(4) == 0) {
                              level.sendParticles(BLACK_BLOOD, next.getX() + 0.5, next.getY() + 1.05, next.getZ() + 0.5, fast ? 5 : 2, 0.35, 0.15, 0.35, 0.01);
                           }

                           if (isNodeEdgeCandidate(next, sourcePos, maxRange)
                              && nodesCreated < maxNodeCreations
                              && fuelSpent + nodeCost <= fuelBudget
                              && tryCreateEdgeNode(level, data, next, altarPos, altarData, sourceGeneration + 1)) {
                              nodesCreated++;
                              fuelSpent += nodeCost;
                           }
                        } else if (canCursorTravelThrough(level, next, nextState)) {
                           queue.add(next);
                        }
                     }
                  }
               }
            }
         }

         return new BlackBloodDomainHandler.SpreadResult(changed, nodesCreated, 0, fuelSpent);
      } else {
         return BlackBloodDomainHandler.SpreadResult.EMPTY;
      }
   }

   private static boolean isNodeEdgeCandidate(BlockPos pos, BlockPos sourcePos, int maxRange) {
      if (maxRange <= 8) {
         return pos.distSqr(sourcePos) >= (maxRange - 2) * (maxRange - 2);
      }

      int edgeRange = Math.max(6, (int)Math.floor(maxRange * 0.55));
      return pos.distSqr(sourcePos) >= edgeRange * edgeRange;
   }

   private static int getRunnableFeatureBudget(
      ServerLevel level,
      BlackBloodDomainData data,
      BlockPos altarPos,
      BlockPos sourcePos,
      BlockPos origin,
      BlackBloodDomainData.AltarData altar,
      int generation,
      boolean fast,
      int availableFuel
   ) {
      if (availableFuel > 0 && !DOMAIN_FEATURES.isEmpty()) {
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;

         for (BlackBloodDomainHandler.DomainFeature feature : DOMAIN_FEATURES) {
            int cost = Math.max(1, feature.fuelCost(cfg, altar, generation, fast));
            if (availableFuel >= cost && feature.canRun(level, data, altarPos, sourcePos, origin, generation, fast)) {
               return 1;
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   private static int tryRunDomainFeature(
      ServerLevel level,
      BlackBloodDomainData data,
      BlockPos altarPos,
      BlockPos sourcePos,
      BlockPos origin,
      BlackBloodDomainData.AltarData altar,
      int generation,
      boolean fast
   ) {
      if (altar != null && !DOMAIN_FEATURES.isEmpty()) {
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;

         for (BlackBloodDomainHandler.DomainFeature feature : DOMAIN_FEATURES) {
            int cost = Math.max(1, feature.fuelCost(cfg, altar, generation, fast));
            if (altar.fuelBlood() >= cost
               && feature.canRun(level, data, altarPos, sourcePos, origin, generation, fast)
               && feature.run(level, data, altarPos, sourcePos, origin, generation, fast)) {
               return cost;
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   private static boolean tryCreateEdgeNode(
      ServerLevel level, BlackBloodDomainData data, BlockPos nodePos, BlockPos altarPos, BlackBloodDomainData.AltarData altarData, int generation
   ) {
      if (altarData == null) {
         return false;
      }

      if (!level.isLoaded(nodePos)) {
         return false;
      }

      if (!isInfectionSource(level.getBlockState(nodePos))) {
         return false;
      }

      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      int spacing = getNodeSpacing(cfg, Math.max(0, altarData.level()));
      if (data.hasNodeNear(nodePos, spacing)) {
         return false;
      }

      if (!data.addNode(nodePos, altarData.owner(), altarPos, Math.max(1, generation))) {
         return false;
      }

      level.sendParticles(DARK_RED_BLOOD, nodePos.getX() + 0.5, nodePos.getY() + 1.12, nodePos.getZ() + 0.5, 18, 0.35, 0.2, 0.35, 0.02);
      level.sendParticles(ParticleTypes.SCULK_SOUL, nodePos.getX() + 0.5, nodePos.getY() + 1.25, nodePos.getZ() + 0.5, 5, 0.25, 0.2, 0.25, 0.01);
      level.playSound(null, nodePos, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 0.65F, 0.45F);
      return true;
   }

   private static int getBlockInfectionFuelCost(MoreskillsRaceConfig.BlackCommunion cfg) {
      return Math.max(1, (Integer)cfg.altarFuelPerBlock.get());
   }

   private static int getNodeCreationFuelCost(MoreskillsRaceConfig.BlackCommunion cfg, int altarLevel) {
      int blockCost = getBlockInfectionFuelCost(cfg);
      return Math.max(blockCost, blockCost * 2);
   }

   private static int getSourceSpreadRadius(MoreskillsRaceConfig.BlackCommunion cfg, int altarLevel, int sourceGeneration) {
      int base = Math.max(1, (int)Math.ceil((Double)cfg.altarBaseRadius.get() + Math.max(0, altarLevel) * (Double)cfg.altarRadiusPerLevel.get()));
      return sourceGeneration <= 0 ? base : Math.max(8, (int)Math.ceil(base * 0.75));
   }

   private static int getNodeSpacing(MoreskillsRaceConfig.BlackCommunion cfg, int altarLevel) {
      return Math.max(8, (int)Math.ceil(((Double)cfg.altarBaseRadius.get() + Math.max(0, altarLevel) * (Double)cfg.altarRadiusPerLevel.get()) * 0.25));
   }

   private static void ensureRelayNodesFromSource(
      ServerLevel level,
      BlackBloodDomainData data,
      BlockPos altarPos,
      BlockPos sourcePos,
      MoreskillsRaceConfig.BlackCommunion cfg,
      int altarLevel,
      int sourceGeneration
   ) {
      BlackBloodDomainData.AltarData altar = data.getAltar(altarPos);
      if (altar != null && altar.fuelBlood() > 0) {
         if (level.isLoaded(sourcePos) && isInfectionSource(level.getBlockState(sourcePos))) {
            int nodeCost = getNodeCreationFuelCost(cfg, altarLevel);
            int created = 0;
            int maxCreate = 4;

            while (created < maxCreate) {
               altar = data.getAltar(altarPos);
               if (altar == null || altar.fuelBlood() < nodeCost) {
                  return;
               }

               BlockPos candidate = findForcedRelayNodeCandidate(level, data, altarPos, sourcePos, altarLevel, Math.max(0, sourceGeneration), created);
               if (candidate == null) {
                  return;
               }

               if (!data.addNode(candidate, altar.owner(), altarPos, Math.max(1, sourceGeneration + 1))) {
                  return;
               }

               data.setAltar(altarPos, altar.withFuelBlood(altar.fuelBlood() - nodeCost));
               created++;
               level.sendParticles(DARK_RED_BLOOD, candidate.getX() + 0.5, candidate.getY() + 1.12, candidate.getZ() + 0.5, 24, 0.35, 0.2, 0.35, 0.02);
               level.sendParticles(ParticleTypes.SCULK_SOUL, candidate.getX() + 0.5, candidate.getY() + 1.25, candidate.getZ() + 0.5, 8, 0.25, 0.2, 0.25, 0.01);
               level.playSound(null, candidate, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 0.7F, 0.5F);
            }
         }
      }
   }

   private static BlockPos findForcedRelayNodeCandidate(
      ServerLevel level, BlackBloodDomainData data, BlockPos altarPos, BlockPos sourcePos, int altarLevel, int sourceGeneration, int createIndex
   ) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      int radius = Math.max(8, getSourceSpreadRadius(cfg, altarLevel, sourceGeneration));
      int spacing = getNodeSpacing(cfg, altarLevel);
      int preferredSector = getBalancedNodeSector(level, data, altarPos, sourcePos, sourceGeneration, createIndex);
      BlockPos bestPreferredEdge = null;
      BlockPos bestAnyEdge = null;
      BlockPos bestPreferredSource = null;
      BlockPos bestAnySource = null;
      double bestPreferredEdgeScore = Double.NEGATIVE_INFINITY;
      double bestAnyEdgeScore = Double.NEGATIVE_INFINITY;
      double bestPreferredSourceScore = Double.NEGATIVE_INFINITY;
      double bestAnySourceScore = Double.NEGATIVE_INFINITY;
      int minRing = Math.max(3, (int)Math.floor(radius * 0.4));
      int idealRing = Math.max(minRing + 1, (int)Math.floor(radius * 0.78));

      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            int horizontal = x * x + z * z;
            if (horizontal <= radius * radius && horizontal >= minRing * minRing) {
               for (int y = -3; y <= 7; y++) {
                  BlockPos candidate = sourcePos.offset(x, y, z);
                  if (level.isLoaded(candidate) && data.getNode(candidate) == null && !data.hasNodeNear(candidate, spacing)) {
                     BlockState candidateState = level.getBlockState(candidate);
                     if (isInfectionSource(candidateState) && !candidateState.is((Block)ModBlocks.BLOOD_ALTAR.get())) {
                        int globalSector = getSector(candidate.getX() - altarPos.getX(), candidate.getZ() - altarPos.getZ());
                        int sourceSector = getSector(sourcePos.getX() - altarPos.getX(), sourcePos.getZ() - altarPos.getZ());
                        int sectorDistance = getSectorDistance(globalSector, preferredSector);
                        int sourceSectorDistance = sourcePos.equals(altarPos) ? 0 : getSectorDistance(globalSector, sourceSector);
                        double horizontalDistance = Math.sqrt(horizontal);
                        double ringScore = 10000.0 - Math.abs(horizontalDistance - idealRing) * 250.0;
                        double globalDistance = Math.sqrt(candidate.distSqr(altarPos));
                        double outwardScore = globalDistance * 35.0;
                        double sectorScore = -sectorDistance * 4500.0 - sourceSectorDistance * 700.0;
                        double heightScore = -Math.abs(y) * 35.0;
                        double randomTie = Math.floorMod(candidate.getX() * 73428767 ^ candidate.getY() * 912931 ^ candidate.getZ() * 42317861, 997) * 0.001;
                        double score = ringScore + outwardScore + sectorScore + heightScore + randomTie;
                        boolean edge = isNaturalSpreadEdge(level, candidate);
                        if (edge) {
                           if (globalSector == preferredSector && score > bestPreferredEdgeScore) {
                              bestPreferredEdgeScore = score;
                              bestPreferredEdge = candidate.immutable();
                           }

                           if (score > bestAnyEdgeScore) {
                              bestAnyEdgeScore = score;
                              bestAnyEdge = candidate.immutable();
                           }
                        }

                        if (globalSector == preferredSector && score > bestPreferredSourceScore) {
                           bestPreferredSourceScore = score;
                           bestPreferredSource = candidate.immutable();
                        }

                        if (score > bestAnySourceScore) {
                           bestAnySourceScore = score;
                           bestAnySource = candidate.immutable();
                        }
                     }
                  }
               }
            }
         }
      }

      if (bestPreferredEdge != null) {
         return bestPreferredEdge;
      } else if (bestAnyEdge != null) {
         return bestAnyEdge;
      } else {
         return bestPreferredSource != null ? bestPreferredSource : bestAnySource;
      }
   }

   private static int getBalancedNodeSector(
      ServerLevel level, BlackBloodDomainData data, BlockPos altarPos, BlockPos sourcePos, int sourceGeneration, int createIndex
   ) {
      int[] counts = new int[8];

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : data.nodes()) {
         if (entry.getValue().altarPos().equals(altarPos)) {
            BlockPos nodePos = entry.getKey();
            int sector = getSector(nodePos.getX() - altarPos.getX(), nodePos.getZ() - altarPos.getZ());
            counts[sector]++;
         }
      }

      int sourceSector = sourcePos.equals(altarPos)
         ? Math.floorMod(
            (int)(level.getGameTime() / Math.max(1L, ((Integer)MoreskillsRaceConfig.get().BlackCommunion.altarSpreadInterval.get()).intValue()))
               + createIndex * 2,
            8
         )
         : getSector(sourcePos.getX() - altarPos.getX(), sourcePos.getZ() - altarPos.getZ());
      int bestSector = sourceSector;
      int bestScore = Integer.MAX_VALUE;

      for (int offset = 0; offset < 8; offset++) {
         int sector = Math.floorMod(sourceSector + offset, 8);
         int distancePenalty = sourcePos.equals(altarPos) ? 0 : getSectorDistance(sector, sourceSector) * 3;
         int rotationPenalty = Math.floorMod(sector - sourceSector - createIndex, 8);
         int score = counts[sector] * 100 + distancePenalty + rotationPenalty;
         if (score < bestScore) {
            bestScore = score;
            bestSector = sector;
         }
      }

      return bestSector;
   }

   private static int getSector(int x, int z) {
      if (x == 0 && z == 0) {
         return 0;
      }

      double angle = Math.atan2(z, x);
      double normalized = (angle + Math.PI) / (Math.PI * 2);
      return Math.floorMod((int)Math.floor(normalized * 8.0), 8);
   }

   private static int getSectorDistance(int a, int b) {
      int diff = Math.abs(a - b);
      return Math.min(diff, 8 - diff);
   }

   private static int tryGenerateRareDomainFormation(
      ServerLevel level,
      BlackBloodDomainData data,
      BlockPos altarPos,
      BlockPos sourcePos,
      int sourceGeneration,
      MoreskillsRaceConfig.BlackCommunion cfg,
      int altarLevel,
      boolean fast
   ) {
      BlackBloodDomainData.AltarData altar = data.getAltar(altarPos);
      if (altar == null) {
         return 0;
      }

      int blockCost = getBlockInfectionFuelCost(cfg);
      int fuelBlood = Math.max(0, altar.fuelBlood());
      if (fuelBlood < blockCost * 16) {
         return 0;
      }

      int highBloodRequired = Math.max((Integer)cfg.altarLevel5Blood.get() * 8, blockCost * 750);
      if (altar.totalBlood() < highBloodRequired && fuelBlood < highBloodRequired) {
         return 0;
      }

      int rarity = altar.totalBlood() >= highBloodRequired * 2 ? 260 : 520;
      if (!fast && level.random.nextInt(rarity) != 0) {
         return 0;
      }

      BlockPos origin = findFormationOrigin(level, data, sourcePos, altarLevel, sourceGeneration);
      if (origin == null) {
         return 0;
      }

      int type = Math.floorMod(origin.getX() * 13 + origin.getY() * 7 + origin.getZ() * 19 + (int)level.getGameTime(), 3);
      int maxBlocks = Math.min(42, Math.max(8, fuelBlood / blockCost));
      int placed;
      if (type == 0) {
         placed = placeBloodLump(level, data, origin, maxBlocks, cfg);
      } else if (type == 1) {
         placed = placeBloodSpire(level, data, origin, maxBlocks, cfg);
      } else {
         placed = placeBloodOrb(level, data, origin, maxBlocks, cfg);
      }

      if (placed <= 0) {
         return 0;
      }

      int spent = Math.min(fuelBlood, placed * blockCost);
      BlackBloodDomainData.AltarData latest = data.getAltar(altarPos);
      if (latest != null) {
         data.setAltar(altarPos, latest.withFuelBlood(Math.max(0, latest.fuelBlood() - spent)));
      }

      level.sendParticles(DARK_RED_BLOOD, origin.getX() + 0.5, origin.getY() + 1.25, origin.getZ() + 0.5, 36, 0.75, 0.45, 0.75, 0.035);
      level.sendParticles(ParticleTypes.SCULK_SOUL, origin.getX() + 0.5, origin.getY() + 1.4, origin.getZ() + 0.5, 10, 0.55, 0.35, 0.55, 0.02);
      level.playSound(null, origin, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 0.9F, 0.38F);
      return spent;
   }

   private static BlockPos findFormationOrigin(ServerLevel level, BlackBloodDomainData data, BlockPos sourcePos, int altarLevel, int sourceGeneration) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      int radius = Math.max(8, getSourceSpreadRadius(cfg, altarLevel, sourceGeneration));
      BlockPos best = null;
      double bestScore = Double.NEGATIVE_INFINITY;

      for (int x = -radius; x <= radius; x += 2) {
         for (int z = -radius; z <= radius; z += 2) {
            int horizontal = x * x + z * z;
            if (horizontal <= radius * radius && horizontal >= 16) {
               for (int y = -3; y <= 6; y++) {
                  BlockPos candidate = sourcePos.offset(x, y, z);
                  if (level.isLoaded(candidate)
                     && isInfectionSource(level.getBlockState(candidate))
                     && isSurfaceExposed(level, candidate)
                     && data.getNode(candidate) == null) {
                     double score = candidate.distSqr(sourcePos);
                     if (isNaturalSpreadEdge(level, candidate)) {
                        score += 5000.0;
                     }

                     score += Math.floorMod(candidate.getX() * 92821 ^ candidate.getY() * 68917 ^ candidate.getZ() * 31337, 1000) * 0.001;
                     if (score > bestScore) {
                        bestScore = score;
                        best = candidate.immutable();
                     }
                  }
               }
            }
         }
      }

      return best;
   }

   private static int placeBloodLump(ServerLevel level, BlackBloodDomainData data, BlockPos origin, int maxBlocks, MoreskillsRaceConfig.BlackCommunion cfg) {
      int placed = 0;

      for (int y = 0; y <= 2 && placed < maxBlocks; y++) {
         int radius = 2 - y;

         for (int x = -radius; x <= radius && placed < maxBlocks; x++) {
            for (int z = -radius; z <= radius && placed < maxBlocks; z++) {
               if (x * x + z * z <= radius * radius + 1) {
                  BlockPos pos = origin.offset(x, y, z);
                  if (placeFormationBlock(level, data, pos, cfg, true)) {
                     placed++;
                  }
               }
            }
         }
      }

      return placed;
   }

   private static int placeBloodSpire(ServerLevel level, BlackBloodDomainData data, BlockPos origin, int maxBlocks, MoreskillsRaceConfig.BlackCommunion cfg) {
      int placed = 0;
      int height = Math.min(6, Math.max(3, maxBlocks / 4));

      for (int y = 1; y <= height && placed < maxBlocks; y++) {
         BlockPos center = origin.above(y);
         if (placeFormationBlock(level, data, center, cfg, true)) {
            placed++;
         }

         if (y <= 3) {
            for (Direction direction : HORIZONTAL_DIRECTIONS) {
               if (placed >= maxBlocks) {
                  break;
               }

               if (placeFormationBlock(level, data, center.relative(direction), cfg, false)) {
                  placed++;
               }
            }
         }
      }

      return placed;
   }

   private static int placeBloodOrb(ServerLevel level, BlackBloodDomainData data, BlockPos origin, int maxBlocks, MoreskillsRaceConfig.BlackCommunion cfg) {
      int placed = 0;
      BlockPos center = origin.above(3);
      int radius = 2;

      for (int x = -radius; x <= radius && placed < maxBlocks; x++) {
         for (int y = -radius; y <= radius && placed < maxBlocks; y++) {
            for (int z = -radius; z <= radius && placed < maxBlocks; z++) {
               int distance = x * x + y * y + z * z;
               if (distance <= radius * radius + 1 && distance >= 2) {
                  BlockPos pos = center.offset(x, y, z);
                  if (placeFormationBlock(level, data, pos, cfg, true)) {
                     placed++;
                  }
               }
            }
         }
      }

      if (placed <= 0) {
         placed += placeBloodLump(level, data, origin, maxBlocks, cfg);
      }

      return placed;
   }

   private static boolean placeFormationBlock(
      ServerLevel level, BlackBloodDomainData data, BlockPos pos, MoreskillsRaceConfig.BlackCommunion cfg, boolean allowAir
   ) {
      if (!level.isLoaded(pos)) {
         return false;
      }

      BlockState state = level.getBlockState(pos);
      if (isInfectedBlock(state) || state.is((Block)ModBlocks.BLOOD_ALTAR.get())) {
         return false;
      }

      if (state.hasBlockEntity() || level.getBlockEntity(pos) != null) {
         return false;
      }

      if (!state.is(Blocks.BEDROCK)
         && !state.is(Blocks.END_PORTAL_FRAME)
         && !state.is(Blocks.END_PORTAL)
         && !state.is(Blocks.NETHER_PORTAL)
         && !state.is(Blocks.COMMAND_BLOCK)
         && !state.is(Blocks.CHAIN_COMMAND_BLOCK)
         && !state.is(Blocks.REPEATING_COMMAND_BLOCK)
         && !state.is(Blocks.BARRIER)
         && !state.is(Blocks.STRUCTURE_BLOCK)
         && !state.is(Blocks.JIGSAW)) {
         if (isClearablePlant(state)) {
            level.destroyBlock(pos, false);
            state = level.getBlockState(pos);
         }

         if (!allowAir && state.isAir()) {
            return false;
         }

         if (!state.isAir() && !canInfect(level, pos)) {
            return false;
         }

         level.setBlock(pos, ((Block)ModBlocks.BLACK_BLOOD_MIRE.get()).defaultBlockState(), 3);
         data.addChunkValue(pos, (Integer)cfg.domainChunkValuePerBlock.get(), (Integer)cfg.domainChunkMaxValue.get());
         return true;
      } else {
         return false;
      }
   }

   private static void removeInvalidNodes(ServerLevel level, BlackBloodDomainData data, int maxChecks) {
      if (maxChecks > 0) {
         int checked = 0;

         for (Entry<BlockPos, BlackBloodDomainData.AltarData> altarEntry : data.altars()) {
            if (checked >= maxChecks) {
               return;
            }

            BlockPos altarPos = altarEntry.getKey();
            int budget = Math.max(1, Math.min(maxChecks - checked, 32));

            for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : data.nextNodesForAltar(altarPos, budget)) {
               if (checked >= maxChecks) {
                  return;
               }

               checked++;
               BlockPos pos = entry.getKey();
               BlackBloodDomainData.NodeData node = data.getNode(pos);
               if (node != null) {
                  if (data.getAltar(node.altarPos()) == null) {
                     data.removeNode(pos);
                  } else if (level.isLoaded(pos) && !isInfectionSource(level.getBlockState(pos))) {
                     data.removeNode(pos);
                  }
               }
            }
         }
      }
   }

   private static Direction[] balancedCursorDirections(BlockPos current, BlockPos origin) {
      int dx = current.getX() - origin.getX();
      int dz = current.getZ() - origin.getZ();
      if (Math.abs(dx) > Math.abs(dz)) {
         return dx >= 0 ? BALANCED_CURSOR_DIRECTIONS[4] : BALANCED_CURSOR_DIRECTIONS[5];
      } else if (Math.abs(dz) > 0) {
         return dz >= 0 ? BALANCED_CURSOR_DIRECTIONS[6] : BALANCED_CURSOR_DIRECTIONS[7];
      } else {
         return BALANCED_CURSOR_DIRECTIONS[Math.floorMod(current.getX() * 31 + current.getY() * 17 + current.getZ() * 13, 4)];
      }
   }

   private static List<Entry<BlockPos, BlackBloodDomainData.AltarData>> copyAltars(BlackBloodDomainData data) {
      List<Entry<BlockPos, BlackBloodDomainData.AltarData>> copy = new ArrayList<>();

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : data.altars()) {
         copy.add(entry);
      }

      return copy;
   }

   private static List<Entry<BlockPos, BlackBloodDomainData.NodeData>> copyNodes(BlackBloodDomainData data) {
      List<Entry<BlockPos, BlackBloodDomainData.NodeData>> copy = new ArrayList<>();

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : data.nodes()) {
         copy.add(entry);
      }

      return copy;
   }

   private static BlockPos getSurfaceBiasedNext(ServerLevel level, BlockPos current, Direction direction, int minY) {
      BlockPos direct = current.relative(direction);
      if (!level.isLoaded(direct)) {
         return null;
      }

      if (direction == Direction.DOWN && direct.getY() < minY) {
         return null;
      }

      if (direction == Direction.DOWN) {
         return canVerticalInfect(level, current, direct) ? direct : null;
      }

      if (direction == Direction.UP) {
         return canVerticalInfect(level, current, direct) ? direct : null;
      }

      BlockPos surface = findNearestSurfaceCandidate(level, direct, current.getY(), minY);
      return surface == null ? direct : surface;
   }

   private static BlockPos findNearestSurfaceCandidate(ServerLevel level, BlockPos pos, int preferredY, int minY) {
      if (!level.isLoaded(pos)) {
         return null;
      }

      if (isClearablePlant(level.getBlockState(pos)) && level.isLoaded(pos.below()) && canInfect(level, pos.below()) && isSurfaceExposed(level, pos.below())) {
         return pos.below();
      }

      if (canInfect(level, pos) && isSurfaceExposed(level, pos)) {
         return pos;
      }

      for (int down = 1; down <= 3; down++) {
         BlockPos check = pos.below(down);
         if (check.getY() < minY || !level.isLoaded(check)) {
            break;
         }

         if (isClearablePlant(level.getBlockState(check))
            && level.isLoaded(check.below())
            && canInfect(level, check.below())
            && isSurfaceExposed(level, check.below())) {
            return check.below();
         }

         if (canInfect(level, check) && isSurfaceExposed(level, check)) {
            return check;
         }
      }

      for (int up = 1; up <= 3; up++) {
         BlockPos check = pos.above(up);
         if (!level.isLoaded(check) || Math.abs(check.getY() - preferredY) > 5) {
            break;
         }

         if (isClearablePlant(level.getBlockState(check))
            && level.isLoaded(check.below())
            && canInfect(level, check.below())
            && isSurfaceExposed(level, check.below())) {
            return check.below();
         }

         if (canInfect(level, check) && isSurfaceExposed(level, check)) {
            return check;
         }
      }

      return null;
   }

   private static boolean canInfectFrom(ServerLevel level, BlockPos from, BlockPos target) {
      if (!canInfect(level, target)) {
         return false;
      } else {
         return isSurfaceExposed(level, target) ? true : isVerticalConnectedToInfection(level, from, target);
      }
   }

   private static boolean canVerticalInfect(ServerLevel level, BlockPos from, BlockPos target) {
      if (!canInfect(level, target)) {
         return false;
      } else {
         return target.getY() < from.getY() - 3 ? false : isSurfaceExposed(level, target) || isVerticalConnectedToInfection(level, from, target);
      }
   }

   private static boolean isSurfaceExposed(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return false;
      }

      BlockPos above = pos.above();
      if (!level.isLoaded(above)) {
         return false;
      }

      BlockState aboveState = level.getBlockState(above);
      return aboveState.isAir() || isClearablePlant(aboveState);
   }

   private static void clearCoveringVegetation(ServerLevel level, BlockPos infectedGround) {
      BlockPos above = infectedGround.above();
      if (level.isLoaded(above)) {
         BlockState state = level.getBlockState(above);
         if (isClearablePlant(state)) {
            level.destroyBlock(above, false);
         }
      }
   }

   private static boolean isClearablePlant(BlockState state) {
      Block block = state.getBlock();
      return state.is(BlockTags.FLOWERS)
         || block == Blocks.SHORT_GRASS
         || block == Blocks.TALL_GRASS
         || block == Blocks.FERN
         || block == Blocks.LARGE_FERN
         || block == Blocks.DEAD_BUSH
         || block == Blocks.VINE
         || block == Blocks.SNOW
         || block == Blocks.SEAGRASS
         || block == Blocks.TALL_SEAGRASS;
   }

   private static boolean isVerticalConnectedToInfection(ServerLevel level, BlockPos from, BlockPos target) {
      if (Math.abs(target.getY() - from.getY()) > 5) {
         return false;
      }

      if (isInfectionSource(level.getBlockState(from))) {
         return true;
      }

      for (Direction direction : HORIZONTAL_DIRECTIONS) {
         BlockPos side = target.relative(direction);
         if (level.isLoaded(side) && isInfectionSource(level.getBlockState(side))) {
            return true;
         }
      }

      BlockPos below = target.below();
      return level.isLoaded(below) && isInfectionSource(level.getBlockState(below));
   }

   private static boolean isInfectionSource(BlockState state) {
      return isInfectedBlock(state) || state.is((Block)ModBlocks.BLOOD_ALTAR.get());
   }

   private static boolean canCursorTravelThrough(ServerLevel level, BlockPos pos, BlockState state) {
      if (state.is((Block)ModBlocks.BLOOD_ALTAR.get())) {
         return true;
      }

      if (isInfectedBlock(state)) {
         return true;
      }

      if (!state.isAir()) {
         return false;
      }

      BlockState below = level.getBlockState(pos.below());
      return isInfectedBlock(below) || below.is((Block)ModBlocks.BLOOD_ALTAR.get());
   }

   private static boolean canInfect(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return false;
      }

      BlockState state = level.getBlockState(pos);
      if (state.isAir()) {
         return false;
      }

      if (isInfectedBlock(state) || state.is((Block)ModBlocks.BLOOD_ALTAR.get())) {
         return false;
      }

      if (state.is(Blocks.BEDROCK)
         || state.is(Blocks.END_PORTAL_FRAME)
         || state.is(Blocks.END_PORTAL)
         || state.is(Blocks.NETHER_PORTAL)
         || state.is(Blocks.COMMAND_BLOCK)
         || state.is(Blocks.CHAIN_COMMAND_BLOCK)
         || state.is(Blocks.REPEATING_COMMAND_BLOCK)
         || state.is(Blocks.BARRIER)
         || state.is(Blocks.STRUCTURE_BLOCK)
         || state.is(Blocks.JIGSAW)) {
         return false;
      }

      if (state.hasBlockEntity()) {
         return false;
      }

      BlockEntity blockEntity = level.getBlockEntity(pos);
      if (blockEntity != null) {
         return false;
      }

      Block block = state.getBlock();
      return state.is(BlockTags.LOGS)
         || state.is(BlockTags.LEAVES)
         || state.is(BlockTags.DIRT)
         || state.is(BlockTags.SAND)
         || state.is(BlockTags.BASE_STONE_OVERWORLD)
         || state.is(BlockTags.BASE_STONE_NETHER)
         || state.is(Blocks.GRASS_BLOCK)
         || block == Blocks.GRAVEL
         || block == Blocks.CLAY
         || block == Blocks.MUD
         || block == Blocks.MOSS_BLOCK
         || block == Blocks.NETHERRACK
         || block == Blocks.SOUL_SAND
         || block == Blocks.SOUL_SOIL;
   }

   private static BlockState getInfectedState(BlockState oldState) {
      if (oldState.is(BlockTags.LEAVES)) {
         return ((Block)ModBlocks.INFECTED_LEAVES.get()).defaultBlockState();
      } else if (oldState.is(BlockTags.LOGS)) {
         return ((Block)ModBlocks.INFECTED_WOOD.get()).defaultBlockState();
      } else if (oldState.is(Blocks.GRASS_BLOCK)) {
         return ((Block)ModBlocks.INFECTED_GRASS.get()).defaultBlockState();
      } else {
         return oldState.is(BlockTags.DIRT)
            ? ((Block)ModBlocks.INFECTED_DIRT.get()).defaultBlockState()
            : ((Block)ModBlocks.BLACK_BLOOD_MIRE.get()).defaultBlockState();
      }
   }

   private static boolean isInfectedBlock(BlockState state) {
      return state.is((Block)ModBlocks.BLACK_BLOOD_MIRE.get())
         || state.is((Block)ModBlocks.INFECTED_GRASS.get())
         || state.is((Block)ModBlocks.INFECTED_DIRT.get())
         || state.is((Block)ModBlocks.INFECTED_WOOD.get())
         || state.is((Block)ModBlocks.INFECTED_LEAVES.get())
         || state.is((Block)ModBlocks.BLOOD_FLOWER_ROOT.get())
         || state.is((Block)ModBlocks.BLOOD_FLOWER_PETAL.get())
         || state.is((Block)ModBlocks.BLOOD_FLOWER_CORE.get())
         || state.is((Block)ModBlocks.BLOOD_WORLD_HEART_CORE.get());
   }

   private static int countActiveNodesForAltar(ServerLevel level, BlackBloodDomainData data, BlockPos altarPos) {
      if (data.getAltar(altarPos) == null) {
         return 0;
      }

      int active = 0;

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : data.nodes()) {
         if (entry.getValue().altarPos().equals(altarPos)) {
            BlockPos nodePos = entry.getKey();
            if (level.isLoaded(nodePos) && isInfectionSource(level.getBlockState(nodePos))) {
               active++;
            }
         }
      }

      return active;
   }

   private static void hurtWithDomainBloodConversion(ServerLevel level, LivingEntity entity, float damage) {
      if (!(damage <= 0.0F) && entity.isAlive()) {
         entity.getPersistentData().putLong("tensuramoreskills:black_blood_domain_damage_time", level.getGameTime());
         boolean hurt = entity.hurt(TensuraDamageTypes.getDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC), damage);
         if (hurt) {
            entity.getPersistentData().putLong("tensuramoreskills:black_blood_domain_damage_time", level.getGameTime());
            if (!entity.isAlive() || !(entity.getHealth() > 0.0F)) {
               convertDomainKillToAltarBlood(level, entity);
            }
         }
      }
   }

   private static void convertDomainKillToAltarBlood(ServerLevel level, LivingEntity killed) {
      if (!killed.getPersistentData().getBoolean("tensuramoreskills:black_blood_domain_kill_paid")) {
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         int bloodGain = Math.max(0, (Integer)cfg.domainBiomeKillBloodGain.get());
         if (bloodGain > 0) {
            BlackBloodDomainData data = BlackBloodDomainData.get(level);
            BlockPos altarPos = getNearestAltarPosAny(data, killed.blockPosition());
            if (altarPos != null) {
               BlackBloodDomainData.AltarData altar = data.getAltar(altarPos);
               if (altar != null) {
                  int nextTotal = safeAdd(altar.totalBlood(), bloodGain);
                  int nextLevel = Math.max(altar.level(), getAltarLevel(cfg, nextTotal));
                  data.setAltar(altarPos, altar.withGeneratedBlood(bloodGain, (Integer)cfg.altarMaxStoredBlood.get(), nextLevel));
                  data.addChunkValue(killed.blockPosition(), (Integer)cfg.domainChunkValueSacrificeBonus.get(), (Integer)cfg.domainChunkMaxValue.get());
                  killed.getPersistentData().putBoolean("tensuramoreskills:black_blood_domain_kill_paid", true);
                  level.sendParticles(
                     DARK_RED_BLOOD,
                     killed.getX(),
                     killed.getY() + killed.getBbHeight() * 0.5,
                     killed.getZ(),
                     10,
                     killed.getBbWidth() * 0.35,
                     killed.getBbHeight() * 0.25,
                     killed.getBbWidth() * 0.35,
                     0.025
                  );
               }
            }
         }
      }
   }

   private static BlockPos getNearestAltarPosAny(BlackBloodDomainData data, BlockPos center) {
      BlockPos best = null;
      double bestDistance = Double.MAX_VALUE;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : data.altars()) {
         double distance = entry.getKey().distSqr(center);
         if (distance < bestDistance) {
            best = entry.getKey();
            bestDistance = distance;
         }
      }

      return best;
   }

   private static void applyBloodfiendDomainBuff(ServerLevel level, LivingEntity entity, int altarLevel) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      double multiplier = 1.0 + altarLevel * (Double)cfg.domainBiomeBuffPerAltarLevel.get();
      restore(
         entity,
         (Double)cfg.domainBiomeBloodfiendHeal.get() * multiplier,
         (Double)cfg.domainBiomeBloodfiendSpiritualRestore.get() * multiplier,
         (Double)cfg.domainBiomeBloodfiendMagiculeRestore.get() * multiplier,
         (Integer)cfg.domainBiomeBloodfiendBloodGain.get() + altarLevel * (Integer)cfg.domainBiomeBloodfiendBloodGainPerAltarLevel.get()
      );
      if (entity instanceof ServerPlayer serverPlayer) {
         BloodfiendHandler.syncBlood(serverPlayer);
      }

      level.sendParticles(
         DARK_RED_BLOOD,
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.5,
         entity.getZ(),
         3,
         entity.getBbWidth() * 0.4,
         entity.getBbHeight() * 0.25,
         entity.getBbWidth() * 0.4,
         0.01
      );
   }

   private static void applyBloodMinionDomainBuff(ServerLevel level, LivingEntity entity, int altarLevel) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      double multiplier = 0.25 + Math.min(0.75, altarLevel * (Double)cfg.domainBiomeBuffPerAltarLevel.get() * 0.25);
      double heal = (Double)cfg.domainBiomeBloodfiendHeal.get() * multiplier;
      if (heal > 0.0 && entity.getHealth() < entity.getMaxHealth()) {
         entity.heal((float)heal);
      }

      level.sendParticles(
         DARK_RED_BLOOD,
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.5,
         entity.getZ(),
         2,
         entity.getBbWidth() * 0.3,
         entity.getBbHeight() * 0.2,
         entity.getBbWidth() * 0.3,
         0.006
      );
   }

   private static void applyEnemyDomainPunish(ServerLevel level, LivingEntity entity) {
      if (!BlackBloodEcologyHandler.isBloodFaction(entity)) {
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         if ((Double)cfg.domainBiomeEnemyDamage.get() > 0.0) {
            hurtWithDomainBloodConversion(level, entity, ((Double)cfg.domainBiomeEnemyDamage.get()).floatValue());
         }
      }
   }

   private static BlackBloodDomainHandler.DomainLookupCache getDomainLookupCache(ServerLevel level, BlackBloodDomainData data) {
      BlackBloodDomainHandler.DomainLookupCache cache = DOMAIN_LOOKUP_CACHES.computeIfAbsent(level, ignored -> new BlackBloodDomainHandler.DomainLookupCache());
      cache.refresh(level.getGameTime(), data.getChangeVersion());
      return cache;
   }

   private static boolean isCachedDomainChunk(ServerLevel level, BlackBloodDomainData data, BlockPos pos, int required) {
      long key = ChunkPos.asLong(pos);
      BlackBloodDomainHandler.DomainLookupCache cache = getDomainLookupCache(level, data);
      Boolean cached = cache.domainChunks.get(key);
      if (cached != null) {
         return cached;
      }

      boolean value = data.isDomainChunk(pos, required);
      cache.domainChunks.put(key, value);
      return value;
   }

   private static int getCachedNearestAltarLevel(ServerLevel level, BlackBloodDomainData data, BlockPos pos, int radius) {
      long key = ChunkPos.asLong(pos);
      BlackBloodDomainHandler.DomainLookupCache cache = getDomainLookupCache(level, data);
      Integer cached = cache.altarLevels.get(key);
      if (cached != null) {
         return cached;
      }

      int value = getNearestAltarLevel(data, pos, radius);
      cache.altarLevels.put(key, value);
      return value;
   }

   private static int getNearestAltarLevel(BlackBloodDomainData data, BlockPos pos, int radius) {
      int radiusSqr = radius * radius;
      int best = 0;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : data.altars()) {
         if (entry.getKey().distSqr(pos) <= radiusSqr) {
            best = Math.max(best, entry.getValue().level());
         }
      }

      return best;
   }

   private static void restore(LivingEntity entity, double health, double spiritual, double magicule, int blood) {
      if (health > 0.0) {
         entity.heal((float)health);
      }

      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      if (spiritual > 0.0) {
         existence.setSpiritualHealth(Math.min(entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH), existence.getSpiritualHealth() + spiritual));
      }

      if (magicule > 0.0) {
         existence.setMagicule(Math.min(EnergyHelper.getMaxMagicule(entity), existence.getMagicule() + magicule));
      }

      existence.markDirty();
      if (blood > 0) {
         BloodfiendData.addBlood(entity, blood, BloodfiendHandler.getMaxBlood(entity));
      }
   }

   public interface DomainFeature {
      boolean canRun(ServerLevel var1, BlackBloodDomainData var2, BlockPos var3, BlockPos var4, BlockPos var5, int var6, boolean var7);

      int fuelCost(MoreskillsRaceConfig.BlackCommunion var1, BlackBloodDomainData.AltarData var2, int var3, boolean var4);

      boolean run(ServerLevel var1, BlackBloodDomainData var2, BlockPos var3, BlockPos var4, BlockPos var5, int var6, boolean var7);
   }

   private static final class DomainLookupCache {
      private long gameTime = Long.MIN_VALUE;
      private int dataVersion = Integer.MIN_VALUE;
      private final Map<Long, Boolean> domainChunks = new HashMap<>();
      private final Map<Long, Integer> altarLevels = new HashMap<>();

      private void refresh(long gameTime, int dataVersion) {
         if (this.gameTime != gameTime || this.dataVersion != dataVersion) {
            this.gameTime = gameTime;
            this.dataVersion = dataVersion;
            this.domainChunks.clear();
            this.altarLevels.clear();
         }
      }
   }

   private record SpendPulseResult(int changedBlocks, int nodesCreated, int spentBlood) {
   }

   private record SpreadResult(int changed, int nodesCreated, int featureFuelSpent, int spentBlood) {
      private static final BlackBloodDomainHandler.SpreadResult EMPTY = new BlackBloodDomainHandler.SpreadResult(0, 0, 0, 0);

      private int actualFuelCost(int blockCost, int nodeCost) {
         long blockSpent = (long)Math.max(0, this.changed) * Math.max(1, blockCost);
         long nodeSpent = (long)Math.max(0, this.nodesCreated) * Math.max(1, nodeCost);
         long featureSpent = Math.max(0, this.featureFuelSpent);
         long total = blockSpent + nodeSpent + featureSpent;
         return total >= 2147483647L ? Integer.MAX_VALUE : (int)total;
      }

      private BlackBloodDomainHandler.SpreadResult withSpentBlood(int spentBlood) {
         return new BlackBloodDomainHandler.SpreadResult(this.changed, this.nodesCreated, this.featureFuelSpent, Math.max(0, spentBlood));
      }
   }

   private record SpreadSource(BlockPos pos, int generation, int sector) {
   }
}
