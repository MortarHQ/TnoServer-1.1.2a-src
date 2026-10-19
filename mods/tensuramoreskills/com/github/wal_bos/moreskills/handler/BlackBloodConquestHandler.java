package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.registry.block.ModBlocks;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import org.joml.Vector3f;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class BlackBloodConquestHandler {
   private static final DustParticleOptions BLACK_BLOOD = new DustParticleOptions(new Vector3f(0.02F, 0.0F, 0.015F), 1.35F);
   private static final DustParticleOptions RED_BLOOD = new DustParticleOptions(new Vector3f(0.55F, 0.0F, 0.02F), 1.2F);
   private static final ResourceLocation GROWTH_HEALTH = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "black_blood_growth_health");
   private static final ResourceLocation GROWTH_ATTACK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "black_blood_growth_attack");
   private static final ResourceLocation GROWTH_SPEED = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "black_blood_growth_speed");
   private static final String GROWTH_TAG = "tensuramoreskills:black_blood_infinite_growth";
   private static final Map<ServerLevel, BlackBloodConquestHandler.DomainPowerCache> DOMAIN_POWER_CACHE = new WeakHashMap<>();
   private static final int HARD_MAX_ACTIVE_ROOTS = 1000;
   private static final int ROOT_FUEL_STRIDE_BLOCKS = 16;
   private static final int ROOT_VISIBLE_MARK_INTERVAL = 10;
   private static final int BLOOM_CORE_COLLAPSE_RADIUS = 18;
   private static final int BLOOM_CORE_COLLAPSE_BUDGET = 220;
   private static final int COMMAND_SEED_NODE_SPACING = 10;
   private static final int COMMAND_MAX_SEEDED_NODES = 16;

   private BlackBloodConquestHandler() {
   }

   @SubscribeEvent
   public static void onRegisterCommands(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("blackblood")
                  .then(Commands.literal("roots").executes(context -> showRootsCommand((CommandSourceStack)context.getSource()))))
               .then(
                  Commands.literal("cancelroot")
                     .then(
                        Commands.argument("index", IntegerArgumentType.integer(1))
                           .executes(context -> cancelRootCommand((CommandSourceStack)context.getSource(), IntegerArgumentType.getInteger(context, "index")))
                     )
               )
         );
   }

   private static int showRootsCommand(CommandSourceStack source) {
      ServerLevel level = source.getLevel();
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      ServerPlayer player = source.getEntity() instanceof ServerPlayer serverPlayer ? serverPlayer : null;
      if (player != null) {
         sendRootWorkList(player, conquest, true);
      } else {
         source.sendSuccess(() -> Component.literal("Black Blood roots: " + conquest.totalRootWorkCount()), false);
      }

      return 1;
   }

   private static int cancelRootCommand(CommandSourceStack source, int index) {
      ServerLevel level = source.getLevel();
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      BlackBloodConquestHandler.RootWorkEntry target = getRootWorkEntry(conquest, index);
      if (target == null) {
         source.sendFailure(Component.literal("No Black Blood root exists with number " + index + "."));
         return 0;
      } else {
         boolean removed = target.active() ? conquest.removeRoot(target.pos()) : conquest.removePendingRootLaunch(target.targetChunk());
         if (!removed) {
            source.sendFailure(Component.literal("That Black Blood root could not be cancelled."));
            return 0;
         } else {
            source.sendSuccess(
               () -> Component.literal(
                  "Cancelled Black Blood " + target.kind() + " #" + index + " targeting chunk " + target.targetX() + ", " + target.targetZ() + "."
               ),
               true
            );
            return 1;
         }
      }
   }

   public static int markTargetCooldownTicks() {
      return 60;
   }

   public static int rootCommandCooldownTicks() {
      return 200;
   }

   public static int bloodTideCooldownTicks() {
      return 160;
   }

   public static int bloomSiegeCooldownTicks() {
      return 300;
   }

   public static int worldHeartCommandCooldownTicks() {
      return 600;
   }

   public static int harvestCommandCooldownTicks() {
      return 240;
   }

   private static boolean conquestEnabled(MoreskillsRaceConfig.BlackCommunion cfg) {
      return (Boolean)cfg.enabled.get()
         && (Boolean)cfg.blockInfectionEnabled.get()
         && (Boolean)cfg.bloodAltarEnabled.get()
         && (Boolean)cfg.altarPassiveSpreadEnabled.get()
         && (Boolean)cfg.conquestEnabled.get();
   }

   public static int getDomainXp(MoreskillsRaceConfig.BlackCommunion cfg, BlackBloodDomainData.AltarData altar, BlackBloodConquestData conquest) {
      if (altar == null) {
         return 0;
      }

      int xp = Math.max(0, altar.totalBlood());
      if (conquest != null) {
         xp = safeAdd(xp, conquest.createdBlooms() * Math.max(0, (Integer)cfg.domainXpPerBloom.get()));
         xp = safeAdd(xp, conquest.completedRoots() * Math.max(0, (Integer)cfg.domainXpPerCompletedRoot.get()));
         xp = safeAdd(xp, conquest.baseBreaches() * Math.max(0, (Integer)cfg.domainXpPerBaseBreach.get()));
         xp = safeAdd(xp, conquest.conqueredChunks() * Math.max(0, (Integer)cfg.domainXpPerConqueredChunk.get()));
      }

      return xp;
   }

   public static int getDomainLevel(MoreskillsRaceConfig.BlackCommunion cfg, BlackBloodDomainData.AltarData altar, BlackBloodConquestData conquest) {
      return getDomainLevelFromXp(cfg, getDomainXp(cfg, altar, conquest));
   }

   public static int getDomainLevelFromXp(MoreskillsRaceConfig.BlackCommunion cfg, int xp) {
      int level = 0;
      if (xp >= (Integer)cfg.altarLevel1Blood.get()) {
         level = 1;
      }

      if (xp >= (Integer)cfg.altarLevel2Blood.get()) {
         level = 2;
      }

      if (xp >= (Integer)cfg.altarLevel3Blood.get()) {
         level = 3;
      }

      if (xp >= (Integer)cfg.altarLevel4Blood.get()) {
         level = 4;
      }

      if (xp >= (Integer)cfg.altarLevel5Blood.get()) {
         level = 5;
      }

      if (xp >= (Integer)cfg.domainLevel6Xp.get()) {
         level = 6;
      }

      if (xp >= (Integer)cfg.domainLevel7Xp.get()) {
         level = 7;
      }

      if (xp >= (Integer)cfg.domainLevel8Xp.get()) {
         level = 8;
      }

      if (xp >= (Integer)cfg.domainLevel9Xp.get()) {
         level = 9;
      }

      if (xp >= (Integer)cfg.domainLevel10Xp.get()) {
         level = 10;
      }

      return Math.min(Math.max(0, (Integer)cfg.domainMaxLevel.get()), level);
   }

   public static int getNextDomainLevelXp(MoreskillsRaceConfig.BlackCommunion cfg, int level) {
      if (level < 1) {
         return (Integer)cfg.altarLevel1Blood.get();
      } else if (level < 2) {
         return (Integer)cfg.altarLevel2Blood.get();
      } else if (level < 3) {
         return (Integer)cfg.altarLevel3Blood.get();
      } else if (level < 4) {
         return (Integer)cfg.altarLevel4Blood.get();
      } else if (level < 5) {
         return (Integer)cfg.altarLevel5Blood.get();
      } else if (level < 6) {
         return (Integer)cfg.domainLevel6Xp.get();
      } else if (level < 7) {
         return (Integer)cfg.domainLevel7Xp.get();
      } else if (level < 8) {
         return (Integer)cfg.domainLevel8Xp.get();
      } else if (level < 9) {
         return (Integer)cfg.domainLevel9Xp.get();
      } else {
         return level < 10 ? (Integer)cfg.domainLevel10Xp.get() : -1;
      }
   }

   public static int getBestKnownTargetDistanceBlocks(ServerLevel level, BlockPos from, BlackBloodConquestData conquest) {
      int best = 0;

      for (Entry<BlockPos, BlackBloodConquestData.RootData> entry : conquest.roots()) {
         ChunkPos target = new ChunkPos(entry.getValue().targetChunk());
         int dx = target.getMiddleBlockX() - from.getX();
         int dz = target.getMiddleBlockZ() - from.getZ();
         best = Math.max(best, (int)Math.sqrt(dx * dx + dz * dz));
      }

      return best;
   }

   private static BlackBloodConquestHandler.DomainPowerCache getDomainPower(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      long time = level.getGameTime();
      BlackBloodConquestHandler.DomainPowerCache cached = DOMAIN_POWER_CACHE.get(level);
      if (cached != null
         && cached.gameTime() == time
         && cached.domainVersion() == domain.getChangeVersion()
         && cached.conquestVersion() == conquest.getChangeVersion()) {
         return cached;
      }

      int bestLevel = 0;
      int bestXp = 0;
      BlockPos bestPos = null;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : domain.altars()) {
         BlockPos pos = entry.getKey();
         if (level.isLoaded(pos) && level.getBlockState(pos).is((Block)ModBlocks.BLOOD_ALTAR.get())) {
            int xp = getDomainXp(cfg, entry.getValue(), conquest);
            int domainLevel = getDomainLevelFromXp(cfg, xp);
            if (domainLevel > bestLevel || domainLevel == bestLevel && xp > bestXp) {
               bestLevel = domainLevel;
               bestXp = xp;
               bestPos = pos;
            }
         }
      }

      BlackBloodConquestHandler.DomainPowerCache next = new BlackBloodConquestHandler.DomainPowerCache(
         time, domain.getChangeVersion(), conquest.getChangeVersion(), bestLevel, bestXp, bestPos
      );
      DOMAIN_POWER_CACHE.put(level, next);
      return next;
   }

   private static int getBestDomainLevel(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      return getDomainPower(level, domain, conquest, cfg).level();
   }

   private static BlockPos getMainActiveAltar(ServerLevel level, BlackBloodDomainData data, MoreskillsRaceConfig.BlackCommunion cfg) {
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      int requiredLevel = Math.max(0, (Integer)cfg.conquestRequiredDomainLevel.get());
      BlackBloodConquestHandler.DomainPowerCache power = getDomainPower(level, data, conquest, cfg);
      if (power.altarPos() != null && power.level() >= requiredLevel) {
         BlackBloodDomainData.AltarData altar = data.getAltar(power.altarPos());
         return altar != null ? power.altarPos() : null;
      } else {
         return null;
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel level) {
            MoreskillsRaceConfig.BlackCommunion var9 = MoreskillsRaceConfig.get().BlackCommunion;
            if (BloodfiendHandler.isBloodfiend(player) && player.tickCount % 100 == 0) {
               applyStoredGrowth(player);
            }

            if (conquestEnabled(var9)) {
               if (player.tickCount % Math.max(20, (Integer)var9.conquestPlayerMemoryInterval.get()) == 0) {
                  BlackBloodDomainData domain = BlackBloodDomainData.get(level);
                  BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
                  if (getMainActiveAltar(level, domain, var9) != null) {
                     BlockPos playerPos = player.blockPosition();
                     if (shouldSamplePlayerMemory(level, domain, conquest, playerPos, var9)) {
                        int baseScore = fastArtificialBaseScore(level, playerPos, Math.max(1, (Integer)var9.conquestBaseScanRadius.get()));
                        conquest.recordPlayerSample(player.getUUID(), playerPos, baseScore, level.getGameTime());
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLevelTick(net.neoforged.neoforge.event.tick.LevelTickEvent.Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         long var8 = level.getGameTime();
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         if (conquestEnabled(cfg)) {
            BlackBloodDomainData domain = BlackBloodDomainData.get(level);
            BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
            if (var8 % Math.max(1, (Integer)cfg.conquestRootTickInterval.get()) == 0L) {
               processPendingRootLaunches(level, domain, conquest, cfg);
            }

            BlockPos activeAltar = getMainActiveAltar(level, domain, cfg);
            if (activeAltar != null) {
               if (var8 % Math.max(1, (Integer)cfg.conquestRootTickInterval.get()) == 0L) {
                  startWorldRoots(level, domain, conquest, cfg);
                  tickWorldRoots(level, domain, conquest, cfg);
               }

               if (var8 % Math.max(1, (Integer)cfg.conquestBloomTickInterval.get()) == 0L) {
                  tickBlooms(level, domain, conquest, cfg);
               }

               if (var8 % Math.max(1, (Integer)cfg.conquestHeartTickInterval.get()) == 0L) {
                  tickWorldEvolution(level, domain, conquest, cfg);
                  tickWorldHeart(level, domain, conquest, cfg);
                  conquest.prune(var8, Math.max(20L, ((Integer)cfg.conquestMemoryPruneTicks.get()).intValue()));
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onBlockBreak(BreakEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         BlockState state = event.getState();
         if (isConquestBlock(state)) {
            MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
            if (conquestEnabled(cfg)) {
               BlackBloodDomainData domain = BlackBloodDomainData.get(level);
               if (getMainActiveAltar(level, domain, cfg) != null) {
                  BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
                  if (state.is((Block)ModBlocks.BLOOD_FLOWER_CORE.get())) {
                     collapseBloomCore(level, domain, conquest, event.getPos(), cfg);
                  } else {
                     conquest.addInfectionPressure(
                        event.getPos(), Math.max(0, (Integer)cfg.conquestBlockBreakPressure.get()), Math.max(1, (Integer)cfg.conquestConqueredThreshold.get())
                     );
                  }
               }
            }
         }
      }
   }

   public static boolean drainBloomPower(ServerPlayer player, BlockPos pos) {
      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(Component.literal("Only Bloodfiends can drink from the Blood Flower.").withStyle(ChatFormatting.DARK_RED), true);
         return true;
      } else {
         ServerLevel level = player.serverLevel();
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
         BlackBloodConquestData.BloomData bloom = conquest.getBloom(pos);
         if (bloom == null) {
            player.displayClientMessage(Component.literal("This flower has no stored conquest blood.").withStyle(ChatFormatting.DARK_RED), true);
            return true;
         } else {
            int amount = Math.min(Math.max(1, (Integer)cfg.conquestBloomDrainMaxBlood.get()), Math.max(0, bloom.storedBlood()));
            if (amount <= 0) {
               player.displayClientMessage(Component.literal("The flower is hollow.").withStyle(ChatFormatting.DARK_RED), true);
               return true;
            } else {
               conquest.setBloom(pos, bloom.withStoredBlood(bloom.storedBlood() - amount));
               addInfiniteGrowth(player, Math.max(1, amount / Math.max(1, (Integer)cfg.conquestBloomGrowthDivisor.get())));
               BloodfiendData.addBlood(
                  player, Math.max(1, amount / Math.max(1, (Integer)cfg.conquestBloomBloodDivisor.get())), BloodfiendHandler.getMaxBlood(player)
               );
               BloodfiendHandler.syncBlood(player);
               restore(
                  player,
                  amount * (Double)cfg.conquestBloomHealMultiplier.get(),
                  amount * (Double)cfg.conquestBloomSpiritualMultiplier.get(),
                  amount * (Double)cfg.conquestBloomMagiculeMultiplier.get()
               );
               level.sendParticles(RED_BLOOD, player.getX(), player.getY() + player.getBbHeight() * 0.5, player.getZ(), 30, 0.45, 0.35, 0.45, 0.03);
               level.playSound(null, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 0.9F, 0.55F);
               player.displayClientMessage(
                  Component.literal("Black Blood Growth: " + player.getPersistentData().getInt("tensuramoreskills:black_blood_infinite_growth"))
                     .withStyle(ChatFormatting.DARK_RED),
                  true
               );
               return true;
            }
         }
      }
   }

   public static boolean drainWorldHeartPower(ServerPlayer player, BlockPos pos) {
      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(Component.literal("Only Bloodfiends can survive the World Heart.").withStyle(ChatFormatting.DARK_RED), true);
         return true;
      } else {
         ServerLevel level = player.serverLevel();
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
         if (conquest.worldHeartPos() != null && conquest.worldHeartPos().equals(pos)) {
            int gain = Math.max(
               (Integer)cfg.conquestWorldHeartMinGrowth.get(), conquest.conqueredChunks() / 2 + conquest.createdBlooms() * 6 + conquest.baseBreaches() * 40
            );
            addInfiniteGrowth(player, gain);
            restore(
               player,
               gain * (Double)cfg.conquestWorldHeartHealMultiplier.get(),
               gain * (Double)cfg.conquestWorldHeartSpiritualMultiplier.get(),
               gain * (Double)cfg.conquestWorldHeartMagiculeMultiplier.get()
            );
            level.sendParticles(RED_BLOOD, player.getX(), player.getY() + player.getBbHeight() * 0.5, player.getZ(), 90, 0.7, 0.6, 0.7, 0.05);
            level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 0.6F, 0.45F);
            player.displayClientMessage(
               Component.literal("World Heart Growth: " + player.getPersistentData().getInt("tensuramoreskills:black_blood_infinite_growth"))
                  .withStyle(ChatFormatting.DARK_RED),
               true
            );
            return true;
         } else {
            player.displayClientMessage(Component.literal("This is not the active World Heart.").withStyle(ChatFormatting.DARK_RED), true);
            return true;
         }
      }
   }

   private static void startWorldRoots(ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg) {
      int bestDomainLevel = getBestDomainLevel(level, domain, conquest, cfg);
      if (bestDomainLevel >= (Integer)cfg.conquestRequiredDomainLevelForRoots.get()) {
         int maxRoots = 1000;
         if (conquest.totalRootWorkCount() < maxRoots) {
            BlackBloodConquestData.TargetChunk target = conquest.findBestPlayerBaseTarget(
               level.getGameTime(), (Integer)cfg.conquestMinPlayerHeatTarget.get(), (Integer)cfg.conquestMinBaseHeatTarget.get()
            );
            if (target != null && !conquest.hasRootTargeting(target.chunk()) && conquest.totalRootWorkCount() < 1000) {
               BlockPos altarPos = getBestWorldRootAltar(level, domain, conquest, cfg);
               if (altarPos != null) {
                  ChunkPos targetChunk = new ChunkPos(target.chunk());
                  int dx = targetChunk.getMiddleBlockX() - altarPos.getX();
                  int dz = targetChunk.getMiddleBlockZ() - altarPos.getZ();
                  int distance = (int)Math.sqrt(dx * dx + dz * dz);
                  if (distance <= Math.max(16, (Integer)cfg.conquestRootMaxTargetDistanceBlocks.get())) {
                     BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
                     if (altar != null && getDomainLevel(cfg, altar, conquest) >= Math.max(1, (Integer)cfg.conquestRequiredDomainLevelForRoots.get())) {
                        BlockPos start = getOrOpenRootStart(level, altarPos);
                        if (start != null) {
                           if (level.setBlock(start, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3)) {
                              conquest.addRoot(start, altarPos, target.chunk(), target.baseLike(), true);
                              registerDomainNode(level, domain, altarPos, start, 1);
                              level.sendParticles(BLACK_BLOOD, start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5, 35, 0.45, 0.25, 0.45, 0.03);
                              level.playSound(null, start, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 1.0F, 0.35F);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void processPendingRootLaunches(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int processed = 0;
      int maxProcessed = Math.max(1, Math.min(4, 1000));

      for (Entry<Long, BlackBloodConquestData.PendingRootLaunch> entry : conquest.nextPendingRootLaunches(maxProcessed)) {
         if (processed >= maxProcessed) {
            break;
         }

         long targetChunk = entry.getKey();
         BlackBloodConquestData.PendingRootLaunch pending = conquest.getPendingRootLaunch(targetChunk);
         if (pending != null) {
            if (pending.age() > (Integer)cfg.conquestRootMaxAge.get()) {
               conquest.removePendingRootLaunch(targetChunk);
            } else {
               BlockPos altarPos = pending.altarPos();
               if (level.isLoaded(altarPos)) {
                  if (!level.getBlockState(altarPos).is((Block)ModBlocks.BLOOD_ALTAR.get())) {
                     conquest.removePendingRootLaunch(targetChunk);
                  } else {
                     BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
                     if (altar != null && getDomainLevel(cfg, altar, conquest) >= Math.max(1, (Integer)cfg.conquestRequiredDomainLevelForRoots.get())) {
                        BlockPos start = getOrOpenRootStart(level, altarPos);
                        if (start != null) {
                           conquest.removePendingRootLaunch(targetChunk);
                           if (level.setBlock(start, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3)) {
                              conquest.addRoot(start, altarPos, pending.targetChunk(), pending.playerBaseTarget(), pending.bloomOnArrival());
                              registerDomainNode(level, domain, altarPos, start, 1);
                              decorateRootStep(level, conquest, start, 0, cfg);
                              level.sendParticles(
                                 BLACK_BLOOD,
                                 start.getX() + 0.5,
                                 start.getY() + 0.5,
                                 start.getZ() + 0.5,
                                 pending.bloomOnArrival() ? 54 : 36,
                                 0.6,
                                 0.35,
                                 0.6,
                                 0.04
                              );
                              level.playSound(
                                 null,
                                 start,
                                 pending.bloomOnArrival() ? SoundEvents.WITHER_SPAWN : SoundEvents.SCULK_CATALYST_BLOOM,
                                 SoundSource.BLOCKS,
                                 0.8F,
                                 0.35F
                              );
                              processed++;
                           }
                        }
                     } else {
                        conquest.removePendingRootLaunch(targetChunk);
                     }
                  }
               }
            }
         }
      }
   }

   private static void tickWorldRoots(ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg) {
      int processed = 0;
      int maxProcessed = Math.max(1, (Integer)cfg.conquestMaxRootsProcessedPerTick.get());
      int blocksPerRoot = Math.max(1, (Integer)cfg.conquestRootBlocksPerTick.get());
      List<Entry<BlockPos, BlackBloodConquestData.RootData>> roots = conquest.nextRoots(Math.max(maxProcessed, 1000));
      roots.sort(Comparator.comparingInt(entryx -> ((BlackBloodConquestData.RootData)entryx.getValue()).progress()));

      for (Entry<BlockPos, BlackBloodConquestData.RootData> entry : roots) {
         if (processed >= maxProcessed) {
            break;
         }

         processed++;
         BlockPos pos = entry.getKey();
         BlackBloodConquestData.RootData root = conquest.getRoot(pos);
         if (root != null) {
            root = root.tickAge(root.altarPos());
            conquest.setRoot(pos, pos, root);
            if (root.age() > (Integer)cfg.conquestRootMaxAge.get()) {
               conquest.removeRoot(pos);
            } else {
               BlockPos current = pos;

               for (int moved = 0; moved < blocksPerRoot; moved++) {
                  root = conquest.getRoot(current);
                  if (root == null) {
                     break;
                  }

                  BlackBloodDomainData.AltarData altar = domain.getAltar(root.altarPos());
                  if (altar == null) {
                     conquest.removeRoot(current);
                     break;
                  }

                  ChunkPos target = new ChunkPos(root.targetChunk());
                  int currentChunkX = current.getX() >> 4;
                  int currentChunkZ = current.getZ() >> 4;
                  if (Math.abs(currentChunkX - target.x) <= (Integer)cfg.conquestRootReachDistanceChunks.get()
                     && Math.abs(currentChunkZ - target.z) <= (Integer)cfg.conquestRootReachDistanceChunks.get()) {
                     boolean completed = false;
                     if (root.bloomOnArrival()) {
                        BlockPos burst = findBloomBurstPos(level, current);
                        if (burst != null) {
                           placeBloodFlowerBloom(level, domain, conquest, root.altarPos(), burst, root.playerBaseTarget(), cfg);
                           completed = true;
                        }
                     } else {
                        completed = rootArrivalNetwork(level, conquest, current, root.playerBaseTarget(), cfg) > 0;
                     }

                     if (completed) {
                        conquest.incrementCompletedRoots(root.playerBaseTarget());
                        conquest.markChunkBreached(root.targetChunk());
                        BlackBloodEcologyHandler.onWorldRootCompleted(level, current, root.playerBaseTarget());
                        conquest.removeRoot(current);
                     }
                     break;
                  }

                  BlockPos next = findNextRootStep(level, current, target);
                  if (next == null) {
                     next = current.relative(randomHorizontal(level));
                  }

                  if (next == null || !level.isLoaded(next)) {
                     break;
                  }

                  if (!canReplaceForRoot(level, next)) {
                     next = findNearbyRootReplace(level, next);
                  }

                  if (next == null) {
                     break;
                  }

                  boolean paidStep = root.progress() % 16 != 0;
                  int stepCost = Math.max(1, (Integer)cfg.conquestRootStepCost.get());
                  if (!paidStep) {
                     altar = ensureRootFuel(level, domain, conquest, root.altarPos(), stepCost, cfg);
                     if (altar == null || altar.fuelBlood() < stepCost) {
                        break;
                     }
                  }

                  if (!level.setBlock(next, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3)) {
                     break;
                  }

                  if (!paidStep) {
                     BlackBloodDomainData.AltarData latest = domain.getAltar(root.altarPos());
                     if (latest != null) {
                        domain.setAltar(root.altarPos(), latest.withFuelBlood(Math.max(0, latest.fuelBlood() - stepCost)));
                        altar = domain.getAltar(root.altarPos());
                     }
                  }

                  int domainLevel = altar == null ? 0 : getDomainLevel(cfg, altar, conquest);
                  conquest.addInfectionPressure(next, 8 + domainLevel, (Integer)cfg.conquestConqueredThreshold.get());
                  if (root.progress() % 20 == 0) {
                     registerDomainNode(level, domain, root.altarPos(), next, 1 + root.progress() / Math.max(1, 20));
                  }

                  conquest.setRoot(current, next, root.advance(root.altarPos()));
                  decorateRootStep(level, conquest, next, root.progress(), cfg);
                  current = next;
                  if (level.random.nextInt(3) == 0) {
                     level.sendParticles(BLACK_BLOOD, next.getX() + 0.5, next.getY() + 0.5, next.getZ() + 0.5, 4, 0.25, 0.15, 0.25, 0.01);
                  }
               }
            }
         }
      }
   }

   private static void tickBlooms(ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg) {
      int bestDomainLevel = getBestDomainLevel(level, domain, conquest, cfg);
      int maxProcessed = Math.max(1, Math.min(6 + bestDomainLevel / 2, conquest.bloomCount()));
      int processed = 0;

      for (Entry<BlockPos, BlackBloodConquestData.BloomData> entry : conquest.nextBlooms(maxProcessed)) {
         if (processed >= maxProcessed) {
            break;
         }

         BlockPos pos = entry.getKey();
         BlackBloodConquestData.BloomData bloom = conquest.getBloom(pos);
         if (bloom != null && level.isLoaded(pos)) {
            processed++;
            if (!level.getBlockState(pos).is((Block)ModBlocks.BLOOD_FLOWER_CORE.get())) {
               conquest.removeBloom(pos);
            } else {
               int bloodGain = 55 + bloom.tier() * 35 + bestDomainLevel * 18 + conquest.getChunkBaseHeat(pos) / 250;
               bloom = bloom.tick(bloodGain, (Integer)cfg.conquestBloomMaxStoredBlood.get());
               if (bloom.age() > 1200 && bloom.tier() < 2 && bestDomainLevel >= 7) {
                  bloom = bloom.withTier(2);
                  reinforceBloomVeins(level, conquest, pos, 10, cfg);
               }

               if (bloom.age() > 2400 && bloom.tier() < 3) {
                  bloom = bloom.withTier(3);
                  reinforceBloomVeins(level, conquest, pos, 14, cfg);
               }

               conquest.setBloom(pos, bloom);
               if (bloom.age() % 4 == 0) {
                  generateBloomCoreBlood(level, domain, conquest, pos, bloom, bestDomainLevel, cfg);
               }

               conquest.addInfectionPressure(pos, 25 + bloom.tier() * 14 + bestDomainLevel * 3, (Integer)cfg.conquestConqueredThreshold.get());
               if (bloom.age() % Math.max(1, (Integer)cfg.conquestBloomFeedInterval.get()) == 0) {
                  feedBloomToMainAltar(level, domain, conquest, pos, bloom, cfg);
               }

               if (bloom.age() % 200 == 0) {
                  seedDomainNodesAround(
                     level,
                     domain,
                     conquest,
                     bloom.mainAltarPos(),
                     pos,
                     18 + bloom.tier() * 4,
                     Math.min(16, 3 + bloom.tier() * 2 + bestDomainLevel / 3),
                     2 + bloom.tier()
                  );
               }

               if (bloom.age() % 600 == 0) {
                  int budget = Math.min(64 + bestDomainLevel * 6, (Integer)cfg.conquestBloomSpreadBlocks.get() + bloom.tier() * 16 + bestDomainLevel * 6);
                  spreadBloomArea(level, conquest, pos, budget, cfg);
                  seedDomainNodesAround(
                     level,
                     domain,
                     conquest,
                     bloom.mainAltarPos(),
                     pos,
                     22 + bloom.tier() * 5,
                     Math.min(16, 5 + bloom.tier() * 3 + bestDomainLevel / 2),
                     2 + bloom.tier()
                  );
                  growBloodThorns(level, conquest, pos, Math.min(5, 1 + bloom.tier() + bestDomainLevel / 4), cfg);
               }
            }
         }
      }
   }

   private static void tickWorldEvolution(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int domainLevel = getBestDomainLevel(level, domain, conquest, cfg);
      BlockPos bestAltar = getBestWorldRootAltar(level, domain, conquest, cfg);
      if (bestAltar != null) {
         int stage = Math.max(0, conquest.worldStage());
         int targetStage = Math.min(5, domainLevel / 2);
         if (targetStage > stage) {
            conquest.setWorldStage(targetStage);
         }

         if (domainLevel >= (Integer)cfg.conquestWorldHeartRequiredDomainLevel.get() && conquest.worldHeartPos() == null) {
            BlockPos heart = findWorldHeartPos(level, bestAltar);
            if (heart != null) {
               placeWorldHeart(level, conquest, heart, cfg);
               conquest.setWorldStage(Math.max((Integer)cfg.conquestWorldHeartStage.get(), 6));
            }
         }
      }
   }

   private static void tickWorldHeart(ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg) {
      BlockPos heart = conquest.worldHeartPos();
      if (heart != null) {
         if (level.isLoaded(heart)) {
            if (!level.getBlockState(heart).is((Block)ModBlocks.BLOOD_WORLD_HEART_CORE.get())) {
               conquest.setWorldHeartPos(null);
               conquest.setWorldStage(Math.min(conquest.worldStage(), 5));
            } else {
               int domainLevel = getBestDomainLevel(level, domain, conquest, cfg);
               int heartBudget = Math.min((Integer)cfg.conquestWorldHeartSpreadBlocks.get(), 40 + domainLevel * 6);
               spreadBloomArea(level, conquest, heart, heartBudget, cfg);
               BlockPos heartAltar = getNearestAltarAny(domain, heart);
               if (heartAltar != null) {
                  registerDomainNode(level, domain, heartAltar, heart, 4);
                  seedDomainNodesAround(level, domain, conquest, heartAltar, heart, 28 + domainLevel * 2, Math.min(16, 6 + domainLevel), 4);
                  generateWorldHeartBlood(level, domain, conquest, heart, heartAltar, domainLevel, cfg);
               }

               growBloodThorns(level, conquest, heart, Math.min(6, 2 + domainLevel / 3), cfg);
               spawnSanguinePods(level, conquest, heart, Math.min(4, 1 + domainLevel / 4), cfg);
               BlackBloodConquestData.TargetChunk target = conquest.findBestPlayerBaseTarget(
                  level.getGameTime(),
                  Math.max(1, (Integer)cfg.conquestMinPlayerHeatTarget.get() / 2),
                  Math.max(1, (Integer)cfg.conquestMinBaseHeatTarget.get() / 2)
               );
               int maxRoots = 1000;
               if (target != null && conquest.totalRootWorkCount() < maxRoots && !conquest.hasRootTargeting(target.chunk())) {
                  BlockPos start = getOrOpenRootStart(level, heart);
                  BlockPos altar = getNearestAltarAny(domain, heart);
                  if (start != null && altar != null && level.setBlock(start, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3)) {
                     conquest.addRoot(start, altar, target.chunk(), target.baseLike());
                     registerDomainNode(level, domain, altar, start, 2);
                  }
               }

               level.sendParticles(RED_BLOOD, heart.getX() + 0.5, heart.getY() + 4.0, heart.getZ() + 0.5, 35 + domainLevel * 4, 2.6, 1.8, 2.6, 0.035);
               if (level.random.nextInt(3) == 0) {
                  level.playSound(null, heart, SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 0.8F, 0.35F);
               }
            }
         }
      }
   }

   private static void generateWorldHeartBlood(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodConquestData conquest,
      BlockPos heart,
      BlockPos altarPos,
      int domainLevel,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int networkYield = conquest.conqueredChunks() / 5 + conquest.createdBlooms() * 2 + conquest.completedRoots() * 2 + conquest.baseBreaches() * 8;
      int amount = 24 + domainLevel * 7 + Math.min(180, Math.max(0, networkYield));
      int accepted = addGeneratedAltarBlood(domain, altarPos, amount, cfg);
      if (accepted > 0) {
         level.sendParticles(RED_BLOOD, heart.getX() + 0.5, heart.getY() + 5.0, heart.getZ() + 0.5, 12 + domainLevel, 1.6, 1.2, 1.6, 0.018);
      }
   }

   private static void generateBloomCoreBlood(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodConquestData conquest,
      BlockPos bloomPos,
      BlackBloodConquestData.BloomData bloom,
      int domainLevel,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int localPressure = conquest.getChunkInfectionPressure(bloomPos);
      int localHeat = conquest.getChunkBaseHeat(bloomPos);
      int amount = 8 + bloom.tier() * 7 + domainLevel * 2 + Math.min(24, localPressure / 300 + localHeat / 500);
      int accepted = addGeneratedAltarBlood(domain, bloom.mainAltarPos(), amount, cfg);
      if (accepted > 0) {
         level.sendParticles(RED_BLOOD, bloomPos.getX() + 0.5, bloomPos.getY() + 1.2, bloomPos.getZ() + 0.5, 6 + bloom.tier() * 2, 0.5, 0.45, 0.5, 0.012);
      }
   }

   private static int addGeneratedAltarBlood(BlackBloodDomainData domain, BlockPos altarPos, int amount, MoreskillsRaceConfig.BlackCommunion cfg) {
      BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
      if (altar != null && amount > 0) {
         int room = Math.max(0, (Integer)cfg.altarMaxStoredBlood.get() - altar.fuelBlood());
         int accepted = Math.min(room, amount);
         if (accepted <= 0) {
            return 0;
         }

         int nextFuel = safeAdd(altar.fuelBlood(), accepted);
         int nextTotal = safeAdd(altar.totalBlood(), accepted);
         int nextLevel = Math.max(altar.level(), getAltarLevel(cfg, nextTotal));
         domain.setAltar(
            altarPos,
            new BlackBloodDomainData.AltarData(altar.owner(), nextFuel, nextTotal, nextLevel, safeAdd(altar.generatedBlood(), accepted), altar.domainKills())
         );
         return accepted;
      } else {
         return 0;
      }
   }

   private static void feedBloomToMainAltar(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodConquestData conquest,
      BlockPos bloomPos,
      BlackBloodConquestData.BloomData bloom,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      BlackBloodDomainData.AltarData altar = domain.getAltar(bloom.mainAltarPos());
      if (altar != null) {
         int feed = Math.min(bloom.storedBlood(), (Integer)cfg.conquestBloomFeedAmount.get() * Math.max(1, bloom.tier()));
         if (feed > 0) {
            int nextFuel = Math.min((Integer)cfg.altarMaxStoredBlood.get(), altar.fuelBlood() + feed);
            int nextTotal = safeAdd(altar.totalBlood(), feed);
            int nextLevel = Math.max(altar.level(), getAltarLevel(cfg, nextTotal));
            domain.setAltar(
               bloom.mainAltarPos(),
               new BlackBloodDomainData.AltarData(altar.owner(), nextFuel, nextTotal, nextLevel, safeAdd(altar.generatedBlood(), feed), altar.domainKills())
            );
            conquest.setBloom(bloomPos, bloom.withStoredBlood(bloom.storedBlood() - feed));
            level.sendParticles(RED_BLOOD, bloomPos.getX() + 0.5, bloomPos.getY() + 1.5, bloomPos.getZ() + 0.5, 20, 0.6, 0.45, 0.6, 0.025);
         }
      }
   }

   private static void placeBloodFlowerBloom(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodConquestData conquest,
      BlockPos mainAltar,
      BlockPos center,
      boolean playerBaseTarget,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int domainLevel = getDomainLevel(cfg, domain.getAltar(mainAltar), conquest);
      clearSmallArea(level, center, playerBaseTarget ? 5 : 4, playerBaseTarget ? 8 : 6);
      level.setBlock(center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState(), 3);
      conquest.addBloom(
         center,
         mainAltar,
         playerBaseTarget,
         (Integer)cfg.conquestBloomStartBlood.get() * (playerBaseTarget ? Math.max(1, (Integer)cfg.conquestBaseBloomBloodMultiplier.get()) : 1)
      );
      growBloomCrown(level, conquest, center, playerBaseTarget ? 7 + domainLevel / 2 : 5 + domainLevel / 3, playerBaseTarget, cfg);
      spawnSanguinePods(level, conquest, center, playerBaseTarget ? 8 + domainLevel : 4 + domainLevel / 2, cfg);
      growBloodThorns(level, conquest, center, playerBaseTarget ? 8 + domainLevel : 4 + domainLevel / 2, cfg);
      spreadBloomArea(
         level,
         conquest,
         center,
         playerBaseTarget ? (Integer)cfg.conquestBaseBloomBurstSpreadBlocks.get() : (Integer)cfg.conquestBloomBurstSpreadBlocks.get() + domainLevel * 20,
         cfg
      );
      registerDomainNode(level, domain, mainAltar, center, playerBaseTarget ? 3 : 2);
      seedDomainNodesAround(
         level,
         domain,
         conquest,
         mainAltar,
         center,
         playerBaseTarget ? 28 : 20,
         Math.min(16, playerBaseTarget ? 12 + domainLevel : 8 + domainLevel / 2),
         playerBaseTarget ? 3 : 2
      );
      level.sendParticles(RED_BLOOD, center.getX() + 0.5, center.getY() + 2.0, center.getZ() + 0.5, playerBaseTarget ? 220 : 130, 3.0, 1.8, 3.0, 0.08);
      level.sendParticles(ParticleTypes.SCULK_SOUL, center.getX() + 0.5, center.getY() + 2.2, center.getZ() + 0.5, 65, 2.4, 1.2, 2.4, 0.04);
      level.playSound(null, center, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 1.0F, 0.35F);
   }

   private static void growBloomCrown(
      ServerLevel level, BlackBloodConquestData conquest, BlockPos center, int radius, boolean violent, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int height = violent ? 10 : 6;

      for (int y = 1; y <= height; y++) {
         BlockPos stem = center.above(y);
         if (canReplaceForFlower(level, stem)) {
            level.setBlock(stem, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
            conquest.addInfectionPressure(stem, 14, (Integer)cfg.conquestConqueredThreshold.get());
         }
      }

      BlockPos crown = center.above(height);

      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            int dist = x * x + z * z;
            if (dist <= radius * radius && dist >= Math.max(1, radius / 2)) {
               int y = Math.abs(x) + Math.abs(z) <= radius ? 0 : -1;
               BlockPos petal = crown.offset(x, y, z);
               if (canReplaceForFlower(level, petal)) {
                  level.setBlock(petal, ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState(), 3);
                  conquest.addInfectionPressure(petal, 18, (Integer)cfg.conquestConqueredThreshold.get());
               }
            }
         }
      }

      for (Direction direction : Plane.HORIZONTAL) {
         for (int i = 1; i <= radius + 7; i++) {
            BlockPos root = center.relative(direction, i).below(Math.min(4, i / 3));
            if (canReplaceForRoot(level, root)) {
               level.setBlock(root, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
               conquest.addInfectionPressure(root, 10, (Integer)cfg.conquestConqueredThreshold.get());
            }
         }
      }

      for (int i = 0; i < radius * 2; i++) {
         Direction direction = randomHorizontal(level);
         int length = 3 + level.random.nextInt(Math.max(2, radius));
         BlockPos root = center.relative(direction, 2 + level.random.nextInt(Math.max(2, radius))).below(1 + level.random.nextInt(4));

         for (int j = 0; j < length; j++) {
            root = root.relative(direction).below(level.random.nextInt(3) == 0 ? 1 : 0);
            if (canReplaceForRoot(level, root)) {
               level.setBlock(root, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
               conquest.addInfectionPressure(root, 12, (Integer)cfg.conquestConqueredThreshold.get());
            }
         }
      }
   }

   private static void placeWorldHeart(ServerLevel level, BlackBloodConquestData conquest, BlockPos center, MoreskillsRaceConfig.BlackCommunion cfg) {
      clearSmallArea(level, center, 10, 18);
      level.setBlock(center, ((Block)ModBlocks.BLOOD_WORLD_HEART_CORE.get()).defaultBlockState(), 3);
      conquest.setWorldHeartPos(center);

      for (int y = 1; y <= 18; y++) {
         int radius = y < 5 ? 5 : (y < 11 ? 8 : (y < 15 ? 6 : 3));

         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               int dist = x * x + z * z;
               if (dist <= radius * radius && (dist >= radius * radius - 10 || y % 3 == 0)) {
                  BlockPos pos = center.offset(x, y, z);
                  if (canReplaceForFlower(level, pos)) {
                     level.setBlock(
                        pos,
                        y % 3 != 0 && dist >= 5
                           ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState()
                           : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(),
                        3
                     );
                     conquest.addInfectionPressure(pos, 90, (Integer)cfg.conquestConqueredThreshold.get());
                  }
               }
            }
         }
      }

      for (Direction direction : Plane.HORIZONTAL) {
         for (int i = 1; i <= 40; i++) {
            BlockPos root = center.relative(direction, i).below(Math.min(10, i / 3));
            if (canReplaceForRoot(level, root)) {
               level.setBlock(root, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
               conquest.addInfectionPressure(root, 60, (Integer)cfg.conquestConqueredThreshold.get());
            }
         }
      }

      spawnSanguinePods(level, conquest, center, 12, cfg);
      growBloodThorns(level, conquest, center, 32, cfg);
      level.sendParticles(RED_BLOOD, center.getX() + 0.5, center.getY() + 8.0, center.getZ() + 0.5, 800, 8.0, 6.0, 8.0, 0.12);
      level.playSound(null, center, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 1.5F, 0.28F);
   }

   private static void spawnSanguinePods(
      ServerLevel level, BlackBloodConquestData conquest, BlockPos center, int attempts, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int safeAttempts = Math.max(0, Math.min(12, attempts));

      for (int i = 0; i < safeAttempts; i++) {
         int radius = 3 + level.random.nextInt(10);
         int x = level.random.nextInt(radius * 2 + 1) - radius;
         int z = level.random.nextInt(radius * 2 + 1) - radius;
         BlockPos ground = findGroundNear(level, center.offset(x, 0, z), 8);
         if (ground != null) {
            BlockPos pod = ground.above();
            if (canReplaceForFlower(level, pod)) {
               level.setBlock(pod, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
               conquest.addInfectionPressure(pod, 20, (Integer)cfg.conquestConqueredThreshold.get());
            }

            BlockPos cap = pod.above();
            if (canReplaceForFlower(level, cap)) {
               level.setBlock(cap, ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState(), 3);
               conquest.addInfectionPressure(cap, 18, (Integer)cfg.conquestConqueredThreshold.get());
            }

            for (Direction direction : Plane.HORIZONTAL) {
               BlockPos root = pod.relative(direction);
               if (canReplaceForRoot(level, root)) {
                  level.setBlock(root, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
                  conquest.addInfectionPressure(root, 12, (Integer)cfg.conquestConqueredThreshold.get());
               }
            }
         }
      }
   }

   private static void growBloodThorns(
      ServerLevel level, BlackBloodConquestData conquest, BlockPos center, int attempts, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      for (int i = 0; i < attempts; i++) {
         int radius = 4 + level.random.nextInt(14);
         int x = level.random.nextInt(radius * 2 + 1) - radius;
         int z = level.random.nextInt(radius * 2 + 1) - radius;
         BlockPos ground = findGroundNear(level, center.offset(x, 0, z), 10);
         if (ground != null) {
            int height = 2 + level.random.nextInt(5);

            for (int y = 1; y <= height; y++) {
               BlockPos thorn = ground.above(y);
               if (canReplaceForFlower(level, thorn)) {
                  level.setBlock(thorn, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
                  conquest.addInfectionPressure(thorn, 18, (Integer)cfg.conquestConqueredThreshold.get());
               }
            }

            BlockPos tip = ground.above(height + 1);
            if (canReplaceForFlower(level, tip)) {
               level.setBlock(tip, ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState(), 3);
               conquest.addInfectionPressure(tip, 24, (Integer)cfg.conquestConqueredThreshold.get());
            }
         }
      }
   }

   private static BlockPos findGroundNear(ServerLevel level, BlockPos start, int verticalRange) {
      for (int y = verticalRange; y >= -verticalRange; y--) {
         BlockPos pos = start.offset(0, y, 0);
         if (level.isLoaded(pos) && canHoldFlower(level, pos) && canReplaceForFlower(level, pos.above())) {
            return pos;
         }
      }

      return null;
   }

   private static void spreadBloomArea(ServerLevel level, BlackBloodConquestData conquest, BlockPos center, int blocks, MoreskillsRaceConfig.BlackCommunion cfg) {
      int budget = Math.max(0, Math.min(96, blocks));
      if (budget > 0) {
         int radius = Math.max(6, Math.min(28, (int)Math.sqrt(Math.max(1, budget)) + 8));
         int placed = 0;
         int rings = Math.max(1, radius);
         int startSector = Math.floorMod((int)(level.getGameTime() / 20L) + center.getX() * 31 + center.getZ() * 17, 16);

         for (int ring = 1; ring <= rings && placed < budget; ring++) {
            int sectors = Math.max(8, ring * 8);

            for (int i = 0; i < sectors && placed < budget; i++) {
               int sector = Math.floorMod(i + startSector, sectors);
               double angle = (Math.PI * 2) * sector / sectors;
               int x = (int)Math.round(Math.cos(angle) * ring);
               int z = (int)Math.round(Math.sin(angle) * ring);

               for (int y = -3; y <= 3 && placed < budget; y++) {
                  BlockPos ground = findInfectableGroundNear(level, center.offset(x, y, z));
                  if (ground != null && infectConquestBlock(level, ground)) {
                     conquest.addInfectionPressure(ground, 12, (Integer)cfg.conquestConqueredThreshold.get());
                     placed++;
                  }
               }
            }
         }
      }
   }

   private static int rootArrivalNetwork(
      ServerLevel level, BlackBloodConquestData conquest, BlockPos center, boolean baseTarget, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int placed = 0;
      int radius = baseTarget ? 8 : 5;
      int budget = baseTarget ? 48 : 28;

      for (int ring = 0; ring <= radius && placed < budget; ring++) {
         int sectors = ring == 0 ? 1 : Math.max(8, ring * 8);

         for (int i = 0; i < sectors && placed < budget; i++) {
            double angle = sectors == 1 ? 0.0 : (Math.PI * 2) * i / sectors;
            int x = sectors == 1 ? 0 : (int)Math.round(Math.cos(angle) * ring);
            int z = sectors == 1 ? 0 : (int)Math.round(Math.sin(angle) * ring);

            for (int y = -2; y <= 2 && placed < budget; y++) {
               BlockPos pos = center.offset(x, y, z);
               BlockPos ground = findInfectableGroundNear(level, pos);
               if (ground != null) {
                  if (ring == 0 && canReplaceForRoot(level, ground.above())) {
                     level.setBlock(ground.above(), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
                     conquest.addInfectionPressure(ground.above(), 24, (Integer)cfg.conquestConqueredThreshold.get());
                     placed++;
                  } else if (infectConquestBlock(level, ground)) {
                     conquest.addInfectionPressure(ground, baseTarget ? 18 : 12, (Integer)cfg.conquestConqueredThreshold.get());
                     placed++;
                  }
               }
            }
         }
      }

      return placed;
   }

   private static boolean shouldSamplePlayerMemory(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, BlockPos pos, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (domain.isDomainChunk(pos, (Integer)cfg.domainBiomeChunkValueRequired.get())) {
         return true;
      }

      if (conquest.getChunkInfectionPressure(pos) > 0) {
         return true;
      }

      BlockPos heart = conquest.worldHeartPos();
      return heart != null && heart.distSqr(pos) <= 16384.0 ? true : conquest.hasConquestAnchorNear(pos, 96);
   }

   private static int fastArtificialBaseScore(ServerLevel level, BlockPos center, int radius) {
      int score = 0;
      int clamped = Math.max(2, Math.min(8, radius));
      int[][] offsets = new int[][]{
         {0, 0}, {clamped, 0}, {-clamped, 0}, {0, clamped}, {0, -clamped}, {clamped, clamped}, {clamped, -clamped}, {-clamped, clamped}, {-clamped, -clamped}
      };

      for (int[] offset : offsets) {
         for (int y = -2; y <= 2; y += 2) {
            BlockPos pos = center.offset(offset[0], y, offset[1]);
            if (level.isLoaded(pos)) {
               score += artificialBlockScore(level, pos, level.getBlockState(pos));
               if (score >= 240) {
                  return score;
               }
            }
         }
      }

      return score;
   }

   private static int artificialBlockScore(ServerLevel level, BlockPos pos, BlockState state) {
      if (state.isAir()) {
         return 0;
      } else {
         BlockEntity blockEntity = level.getBlockEntity(pos);
         if (blockEntity != null) {
            return 18;
         } else {
            Block block = state.getBlock();
            if (state.is(BlockTags.BEDS)) {
               return 40;
            } else if (state.is(BlockTags.DOORS) || state.is(BlockTags.TRAPDOORS) || state.is(BlockTags.FENCES) || state.is(BlockTags.WALLS)) {
               return 12;
            } else if (state.is(BlockTags.PLANKS) || state.is(BlockTags.WOODEN_STAIRS) || state.is(BlockTags.WOODEN_SLABS)) {
               return 8;
            } else if (block == Blocks.CHEST
               || block == Blocks.TRAPPED_CHEST
               || block == Blocks.BARREL
               || block == Blocks.FURNACE
               || block == Blocks.BLAST_FURNACE
               || block == Blocks.SMOKER
               || block == Blocks.CRAFTING_TABLE
               || block == Blocks.ENCHANTING_TABLE
               || block == Blocks.ANVIL
               || block == Blocks.CHIPPED_ANVIL
               || block == Blocks.DAMAGED_ANVIL
               || block == Blocks.BREWING_STAND
               || block == Blocks.CAULDRON) {
               return 35;
            } else if (block == Blocks.TORCH
               || block == Blocks.SOUL_TORCH
               || block == Blocks.LANTERN
               || block == Blocks.SOUL_LANTERN
               || block == Blocks.REDSTONE_TORCH
               || block == Blocks.GLOWSTONE
               || block == Blocks.SEA_LANTERN) {
               return 14;
            } else if (block == Blocks.GLASS
               || block == Blocks.GLASS_PANE
               || block == Blocks.IRON_DOOR
               || block == Blocks.IRON_TRAPDOOR
               || block == Blocks.IRON_BARS) {
               return 12;
            } else {
               return block != Blocks.COBBLESTONE
                     && block != Blocks.STONE_BRICKS
                     && block != Blocks.BRICKS
                     && block != Blocks.DEEPSLATE_BRICKS
                     && block != Blocks.POLISHED_DEEPSLATE
                     && block != Blocks.SMOOTH_STONE
                     && block != Blocks.OAK_STAIRS
                     && block != Blocks.COBBLESTONE_STAIRS
                     && block != Blocks.STONE_BRICK_STAIRS
                  ? 0
                  : 6;
            }
         }
      }
   }

   private static BlockPos findNextRootStep(ServerLevel level, BlockPos current, ChunkPos target) {
      int targetX = target.getMiddleBlockX();
      int targetZ = target.getMiddleBlockZ();
      int stepX = Integer.compare(targetX, current.getX());
      int stepZ = Integer.compare(targetZ, current.getZ());
      List<BlockPos> candidates = new ArrayList<>();
      candidates.add(current.offset(stepX, 0, stepZ));
      candidates.add(current.offset(stepX, 0, 0));
      candidates.add(current.offset(0, 0, stepZ));
      candidates.add(current.offset(stepX, level.random.nextBoolean() ? 1 : -1, stepZ));
      candidates.add(current.below());
      candidates.add(current.above());
      candidates.sort(Comparator.comparingDouble(pos -> {
         double diffX = pos.getX() - targetX;
         double diffZ = pos.getZ() - targetZ;
         return diffX * diffX + diffZ * diffZ;
      }));

      for (BlockPos candidate : candidates) {
         BlockPos adjusted = adjustRootDepth(level, candidate);
         if (adjusted != null && canReplaceForRoot(level, adjusted)) {
            return adjusted;
         }
      }

      return null;
   }

   private static BlockPos adjustRootDepth(ServerLevel level, BlockPos pos) {
      int minY = level.getMinBuildHeight() + 6;
      int maxY = Math.min(level.getMaxBuildHeight() - 12, 54);
      int y = Math.max(minY, Math.min(maxY, pos.getY()));
      BlockPos base = new BlockPos(pos.getX(), y, pos.getZ());

      for (int i = 0; i <= 6; i++) {
         BlockPos down = base.below(i);
         if (level.isLoaded(down) && canReplaceForRoot(level, down)) {
            return down;
         }

         BlockPos up = base.above(i);
         if (level.isLoaded(up) && canReplaceForRoot(level, up)) {
            return up;
         }
      }

      return null;
   }

   private static BlockPos findRootStart(ServerLevel level, BlockPos altarPos) {
      for (int y = 1; y <= 18; y++) {
         for (int r = 0; r <= 5; r++) {
            for (int x = -r; x <= r; x++) {
               for (int z = -r; z <= r; z++) {
                  if (Math.abs(x) == r || Math.abs(z) == r) {
                     BlockPos pos = altarPos.offset(x, -y, z);
                     if (level.isLoaded(pos) && canReplaceForRoot(level, pos)) {
                        return pos;
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private static BlockPos getOrOpenRootStart(ServerLevel level, BlockPos altarPos) {
      if (!level.isLoaded(altarPos)) {
         return null;
      }

      BlockPos start = findRootStart(level, altarPos);
      if (start != null) {
         return start;
      }

      for (int y = 2; y <= 20; y++) {
         for (int r = 0; r <= 7; r++) {
            for (int x = -r; x <= r; x++) {
               for (int z = -r; z <= r; z++) {
                  if (Math.abs(x) == r || Math.abs(z) == r) {
                     BlockPos pos = altarPos.offset(x, -y, z);
                     if (level.isLoaded(pos) && canReplaceForRoot(level, pos)) {
                        return pos;
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private static BlockPos getOrOpenBloodTideStart(ServerLevel level, BlockPos center) {
      BlockPos ground = findInfectableGroundNear(level, center);
      if (ground != null && level.isLoaded(ground.above()) && canReplaceForRoot(level, ground.above())) {
         return ground.above();
      }

      for (int r = 0; r <= 6; r++) {
         for (int x = -r; x <= r; x++) {
            for (int z = -r; z <= r; z++) {
               if (Math.abs(x) == r || Math.abs(z) == r) {
                  for (int y = -4; y <= 4; y++) {
                     BlockPos pos = center.offset(x, y, z);
                     if (level.isLoaded(pos) && canReplaceForRoot(level, pos)) {
                        return pos;
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private static BlockPos findBloomBurstPos(ServerLevel level, BlockPos rootPos) {
      for (int y = 0; y < 80; y++) {
         BlockPos check = rootPos.above(y);
         if (!level.isLoaded(check)) {
            return null;
         }

         if (level.getBlockState(check).isAir()) {
            BlockPos ground = check.below();
            if (canHoldFlower(level, ground) && level.getBlockState(check.above()).isAir()) {
               return check;
            }
         }
      }

      return findGroundNear(level, rootPos);
   }

   private static BlockPos findWorldHeartPos(ServerLevel level, BlockPos altar) {
      for (int radius = 8; radius <= 32; radius += 4) {
         for (int x = -radius; x <= radius; x += 4) {
            for (int z = -radius; z <= radius; z += 4) {
               BlockPos base = altar.offset(x, 0, z);
               BlockPos ground = findGroundNear(level, base);
               if (ground != null) {
                  BlockPos heart = ground.above();
                  if (level.isLoaded(heart) && level.getBlockState(heart).isAir() && level.getBlockState(heart.above()).isAir()) {
                     return heart;
                  }
               }
            }
         }
      }

      return null;
   }

   private static BlockPos findInfectableGroundNear(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return null;
      }

      for (int y = 0; y <= 12; y++) {
         BlockPos down = pos.below(y);
         if (level.isLoaded(down) && canReplaceInfection(level, down, level.getBlockState(down))) {
            return down;
         }

         BlockPos up = pos.above(y);
         if (level.isLoaded(up) && canReplaceInfection(level, up, level.getBlockState(up))) {
            return up;
         }
      }

      return null;
   }

   private static BlockPos findGroundNear(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return null;
      }

      for (int y = 0; y <= 12; y++) {
         BlockPos down = pos.below(y);
         if (canHoldFlower(level, down) && level.getBlockState(down.above()).isAir()) {
            return down.above();
         }

         BlockPos up = pos.above(y);
         if (canHoldFlower(level, up) && level.getBlockState(up.above()).isAir()) {
            return up.above();
         }
      }

      return null;
   }

   private static BlockPos findNearbyRootReplace(ServerLevel level, BlockPos center) {
      for (Direction direction : Direction.values()) {
         BlockPos pos = center.relative(direction);
         if (level.isLoaded(pos) && canReplaceForRoot(level, pos)) {
            return pos;
         }
      }

      return null;
   }

   private static boolean infectConquestBlock(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return false;
      }

      BlockState state = level.getBlockState(pos);
      if (isConquestBlock(state) || state.is((Block)ModBlocks.BLOOD_ALTAR.get())) {
         return false;
      }

      if (!canReplaceInfection(level, pos, state)) {
         return false;
      }

      level.setBlock(pos, getInfectedState(state), 3);
      return true;
   }

   private static boolean canReplaceForRoot(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return false;
      } else {
         BlockState state = level.getBlockState(pos);
         if (isConquestBlock(state) || state.is((Block)ModBlocks.BLOOD_ALTAR.get())) {
            return false;
         } else {
            return state.isAir() ? true : canReplaceInfection(level, pos, state);
         }
      }
   }

   private static boolean canReplaceForFlower(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return false;
      } else {
         BlockState state = level.getBlockState(pos);
         if (isConquestBlock(state) || state.is((Block)ModBlocks.BLOOD_ALTAR.get())) {
            return false;
         } else if (state.isAir()) {
            return true;
         } else {
            return isClearablePlant(state) ? true : canReplaceInfection(level, pos, state) && artificialBlockScore(level, pos, state) <= 0;
         }
      }
   }

   private static boolean canReplaceInfection(ServerLevel level, BlockPos pos, BlockState state) {
      if (state.hasBlockEntity() || level.getBlockEntity(pos) != null) {
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

      if (isClearablePlant(state)) {
         return true;
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

   private static boolean canHoldFlower(ServerLevel level, BlockPos ground) {
      if (!level.isLoaded(ground)) {
         return false;
      }

      BlockState state = level.getBlockState(ground);
      return !state.isAir() && !state.hasBlockEntity() && state.isFaceSturdy(level, ground, Direction.UP);
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

   private static boolean isConquestBlock(BlockState state) {
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

   private static void clearSmallArea(ServerLevel level, BlockPos center, int radius, int height) {
      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            for (int y = 0; y <= height; y++) {
               BlockPos pos = center.offset(x, y, z);
               if (level.isLoaded(pos)) {
                  BlockState state = level.getBlockState(pos);
                  if (!state.hasBlockEntity() && artificialBlockScore(level, pos, state) <= 0 && (isClearablePlant(state) || state.isAir())) {
                     level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                  }
               }
            }
         }
      }
   }

   public static boolean commandMarkConquestTarget(ServerPlayer player, BlockPos target) {
      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(
            Component.literal("Command refused: only Bloodfiends can command the Black Blood.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      } else {
         ServerLevel level = player.serverLevel();
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         if (!conquestEnabled(cfg)) {
            player.displayClientMessage(
               Component.literal("Command refused: conquest is disabled or one required infection setting is off.").withStyle(ChatFormatting.DARK_RED), true
            );
            return false;
         } else {
            BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
            int baseScore = fastArtificialBaseScore(level, target, Math.max(1, (Integer)cfg.conquestBaseScanRadius.get()));
            conquest.recordCommandTarget(target, 120, Math.max(400, baseScore + 600), level.getGameTime());
            level.sendParticles(BLACK_BLOOD, target.getX() + 0.5, target.getY() + 1.0, target.getZ() + 0.5, 24, 1.0, 0.6, 1.0, 0.03);
            player.displayClientMessage(
               Component.literal("Command accepted: conquest target marked at chunk " + (new ChunkPos(target)).x + ", " + (new ChunkPos(target)).z)
                  .withStyle(ChatFormatting.DARK_RED),
               true
            );
            return true;
         }
      }
   }

   public static boolean commandRootTunnel(ServerPlayer player, BlockPos target) {
      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(Component.literal("Command refused: only Bloodfiends can command World Roots.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      }

      ServerLevel level = player.serverLevel();
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if (!conquestEnabled(cfg)) {
         player.displayClientMessage(
            Component.literal("Command refused: conquest is disabled or one required infection setting is off.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      BlackBloodDomainData domain = BlackBloodDomainData.get(level);
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      if (conquest.totalRootWorkCount() >= 1000) {
         sendRootCapacityMessage(player, conquest);
         return false;
      }

      BlockPos altarPos = getBestWorldRootAltarForCommand(level, domain, conquest, cfg);
      if (altarPos == null) {
         player.displayClientMessage(
            Component.literal("Command refused: no strong enough altar can hear the World Root command. Raise the domain level first.")
               .withStyle(ChatFormatting.DARK_RED),
            true
         );
         return false;
      }

      int dx = target.getX() - altarPos.getX();
      int dz = target.getZ() - altarPos.getZ();
      int distance = (int)Math.sqrt(dx * dx + dz * dz);
      if (distance > Math.max(16, (Integer)cfg.conquestRootMaxTargetDistanceBlocks.get())) {
         player.displayClientMessage(
            Component.literal("Command refused: target is beyond root command range: " + distance + " blocks.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      long targetChunk = ChunkPos.asLong(target);
      if (conquest.hasRootTargeting(targetChunk)) {
         player.displayClientMessage(Component.literal("Command refused: a World Root is already hunting that chunk.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      }

      BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
      if (altar == null) {
         player.displayClientMessage(
            Component.literal("Command refused: the chosen altar vanished before the World Root could answer.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      conquest.recordCommandTarget(target, 80, 800, level.getGameTime());
      if (!level.isLoaded(altarPos)) {
         if (!conquest.addPendingRootLaunch(altarPos, targetChunk, true, false)) {
            player.displayClientMessage(
               Component.literal("Command refused: a World Root is already queued for that chunk.").withStyle(ChatFormatting.DARK_RED), true
            );
            return false;
         } else {
            player.displayClientMessage(
               Component.literal("Command accepted: World Root queued for free. It will open when the altar chunk loads, then spend blood only while crawling.")
                  .withStyle(ChatFormatting.DARK_RED),
               true
            );
            return true;
         }
      } else {
         BlockPos start = getOrOpenRootStart(level, altarPos);
         if (start == null) {
            if (!conquest.addPendingRootLaunch(altarPos, targetChunk, true, false)) {
               player.displayClientMessage(
                  Component.literal("Command refused: a World Root is already queued for that chunk.").withStyle(ChatFormatting.DARK_RED), true
               );
               return false;
            } else {
               player.displayClientMessage(
                  Component.literal(
                        "Command accepted: World Root queued for free. The altar area is too blocked right now, so it will retry when the chunk updates."
                     )
                     .withStyle(ChatFormatting.DARK_RED),
                  true
               );
               return true;
            }
         } else if (!level.setBlock(start, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3)) {
            player.displayClientMessage(
               Component.literal("Command refused: the World Root start could not be placed.").withStyle(ChatFormatting.DARK_RED), true
            );
            return false;
         } else {
            conquest.addRoot(start, altarPos, targetChunk, true, false);
            registerDomainNode(level, domain, altarPos, start, 1);
            level.sendParticles(BLACK_BLOOD, start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5, 36, 0.5, 0.3, 0.5, 0.035);
            level.playSound(null, start, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 0.9F, 0.35F);
            player.displayClientMessage(
               Component.literal("Command accepted: World Root opened for free. It will spend altar or bloom blood only while crawling.")
                  .withStyle(ChatFormatting.DARK_RED),
               true
            );
            return true;
         }
      }
   }

   public static boolean commandBloodTide(ServerPlayer player, int blocks) {
      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(Component.literal("Command refused: only Bloodfiends can command a Blood Tide.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      }

      ServerLevel level = player.serverLevel();
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if (!conquestEnabled(cfg)) {
         player.displayClientMessage(
            Component.literal("Command refused: conquest is disabled or one required infection setting is off.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      BlackBloodDomainData domain = BlackBloodDomainData.get(level);
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      BlockPos altarPos = getBestWorldRootAltarForCommand(level, domain, conquest, cfg);
      if (altarPos == null) {
         altarPos = getNearestAltarAny(domain, player.blockPosition());
      }

      if (altarPos != null && domain.getAltar(altarPos) != null) {
         BlockPos start = getOrOpenBloodTideStart(level, player.blockPosition());
         if (start == null) {
            player.displayClientMessage(
               Component.literal("Command refused: no ground near you can open a Blood Tide node.").withStyle(ChatFormatting.DARK_RED), true
            );
            return false;
         } else {
            int budget = Math.max(16, Math.min(128, blocks * 2));
            if (!level.setBlock(start, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3)) {
               player.displayClientMessage(
                  Component.literal("Command refused: the Blood Tide node could not be placed.").withStyle(ChatFormatting.DARK_RED), true
               );
               return false;
            } else {
               conquest.addInfectionPressure(start, budget * 20, (Integer)cfg.conquestConqueredThreshold.get());
               registerDomainNode(level, domain, altarPos, start, 1);
               spreadBloomArea(level, conquest, start, budget, cfg);
               int seeded = seedDomainNodesAround(level, domain, conquest, altarPos, start, 24, Math.min(16, 6 + Math.max(1, budget / 16)), 1);
               level.sendParticles(RED_BLOOD, start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5, 60, 2.2, 0.45, 2.2, 0.045);
               level.playSound(null, start, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.PLAYERS, 0.9F, 0.42F);
               player.displayClientMessage(
                  Component.literal("Command accepted: Blood Tide opened a relay node and seeded " + seeded + " spread anchors.")
                     .withStyle(ChatFormatting.DARK_RED),
                  true
               );
               return true;
            }
         }
      } else {
         player.displayClientMessage(
            Component.literal("Command refused: Blood Tide needs at least one registered altar to bind relay nodes.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }
   }

   public static boolean commandBloomEruption(ServerPlayer player, BlockPos target) {
      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(Component.literal("Command refused: only Bloodfiends can command a Siege Bloom.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      }

      ServerLevel level = player.serverLevel();
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if (!conquestEnabled(cfg)) {
         player.displayClientMessage(
            Component.literal("Command refused: conquest is disabled or one required infection setting is off.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      BlackBloodDomainData domain = BlackBloodDomainData.get(level);
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      if (conquest.totalRootWorkCount() >= 1000) {
         sendRootCapacityMessage(player, conquest);
         return false;
      }

      BlockPos altarPos = getBestWorldRootAltarForCommand(level, domain, conquest, cfg);
      if (altarPos == null) {
         player.displayClientMessage(
            Component.literal("Command refused: no strong enough altar can hear the Siege Bloom command. Raise the domain level first.")
               .withStyle(ChatFormatting.DARK_RED),
            true
         );
         return false;
      }

      int dx = target.getX() - altarPos.getX();
      int dz = target.getZ() - altarPos.getZ();
      int distance = (int)Math.sqrt(dx * dx + dz * dz);
      if (distance > Math.max(16, (Integer)cfg.conquestRootMaxTargetDistanceBlocks.get())) {
         player.displayClientMessage(
            Component.literal("Command refused: target is beyond root command range: " + distance + " blocks.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      long targetChunk = ChunkPos.asLong(target);
      if (conquest.hasRootTargeting(targetChunk)) {
         player.displayClientMessage(Component.literal("Command refused: a World Root is already hunting that chunk.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      }

      BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
      if (altar == null) {
         player.displayClientMessage(
            Component.literal("Command refused: the chosen altar vanished before the Siege Bloom could answer.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      conquest.recordCommandTarget(target, 160, 1600, level.getGameTime());
      if (!level.isLoaded(altarPos)) {
         if (!conquest.addPendingRootLaunch(altarPos, targetChunk, true, true)) {
            player.displayClientMessage(
               Component.literal("Command refused: a Siege Bloom is already queued for that chunk.").withStyle(ChatFormatting.DARK_RED), true
            );
            return false;
         } else {
            player.displayClientMessage(
               Component.literal(
                     "Command accepted: Siege Bloom queued for free. It will open when the altar chunk loads, then spend blood only while crawling."
                  )
                  .withStyle(ChatFormatting.DARK_RED),
               true
            );
            return true;
         }
      } else {
         BlockPos start = getOrOpenRootStart(level, altarPos);
         if (start == null) {
            if (!conquest.addPendingRootLaunch(altarPos, targetChunk, true, true)) {
               player.displayClientMessage(
                  Component.literal("Command refused: a Siege Bloom is already queued for that chunk.").withStyle(ChatFormatting.DARK_RED), true
               );
               return false;
            } else {
               player.displayClientMessage(
                  Component.literal(
                        "Command accepted: Siege Bloom queued for free. The altar area is too blocked right now, so it will retry when the chunk updates."
                     )
                     .withStyle(ChatFormatting.DARK_RED),
                  true
               );
               return true;
            }
         } else if (!level.setBlock(start, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3)) {
            player.displayClientMessage(
               Component.literal("Command refused: the Siege Bloom root start could not be placed.").withStyle(ChatFormatting.DARK_RED), true
            );
            return false;
         } else {
            conquest.addRoot(start, altarPos, targetChunk, true, true);
            registerDomainNode(level, domain, altarPos, start, 1);
            decorateRootStep(level, conquest, start, 0, cfg);
            level.sendParticles(BLACK_BLOOD, start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5, 54, 0.7, 0.35, 0.7, 0.04);
            level.playSound(null, start, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 0.7F, 0.32F);
            player.displayClientMessage(
               Component.literal(
                     "Command accepted: Siege Bloom seeded for free. It will spend altar or bloom blood only while crawling, then flower at the target."
                  )
                  .withStyle(ChatFormatting.DARK_RED),
               true
            );
            return true;
         }
      }
   }

   public static boolean commandWorldHeartPulse(ServerPlayer player) {
      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(
            Component.literal("Command refused: only Bloodfiends can command the World Heart.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      ServerLevel level = player.serverLevel();
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if (!conquestEnabled(cfg)) {
         player.displayClientMessage(
            Component.literal("Command refused: conquest is disabled or one required infection setting is off.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      BlackBloodDomainData domain = BlackBloodDomainData.get(level);
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      BlockPos heart = conquest.worldHeartPos();
      if (heart != null && level.isLoaded(heart) && level.getBlockState(heart).is((Block)ModBlocks.BLOOD_WORLD_HEART_CORE.get())) {
         int domainLevel = getBestDomainLevel(level, domain, conquest, cfg);
         int budget = Math.min(128, 48 + domainLevel * 8);
         spreadBloomArea(level, conquest, heart, budget, cfg);
         BlockPos heartAltar = getNearestAltarAny(domain, heart);
         int seeded = 0;
         if (heartAltar != null) {
            registerDomainNode(level, domain, heartAltar, heart, 4);
            seeded = seedDomainNodesAround(level, domain, conquest, heartAltar, heart, 32 + domainLevel * 2, Math.min(16, 8 + domainLevel), 4);
         }

         growBloodThorns(level, conquest, heart, Math.min(8, 2 + domainLevel / 2), cfg);
         spawnSanguinePods(level, conquest, heart, Math.min(5, 1 + domainLevel / 3), cfg);
         level.sendParticles(RED_BLOOD, heart.getX() + 0.5, heart.getY() + 4.0, heart.getZ() + 0.5, 90, 3.5, 2.0, 3.5, 0.05);
         level.playSound(null, heart, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 0.9F, 0.32F);
         player.displayClientMessage(
            Component.literal("Command accepted: World Heart pulse commanded and seeded " + seeded + " spread anchors.").withStyle(ChatFormatting.DARK_RED),
            true
         );
         return true;
      } else {
         player.displayClientMessage(Component.literal("Command refused: no World Heart answers.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      }
   }

   public static boolean commandHarvestBloomNetwork(ServerPlayer player) {
      if (!BloodfiendHandler.isBloodfiend(player)) {
         player.displayClientMessage(
            Component.literal("Command refused: only Bloodfiends can harvest the bloom network.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      ServerLevel level = player.serverLevel();
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if (!conquestEnabled(cfg)) {
         player.displayClientMessage(
            Component.literal("Command refused: conquest is disabled or one required infection setting is off.").withStyle(ChatFormatting.DARK_RED), true
         );
         return false;
      }

      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      int harvested = 0;
      int blooms = 0;

      for (Entry<BlockPos, BlackBloodConquestData.BloomData> entry : conquest.blooms()) {
         if (blooms >= 12) {
            break;
         }

         BlockPos pos = entry.getKey();
         BlackBloodConquestData.BloomData bloom = conquest.getBloom(pos);
         if (bloom != null && level.isLoaded(pos) && !(pos.distSqr(player.blockPosition()) > 65536.0)) {
            int take = Math.min(400, bloom.storedBlood() / 8);
            if (take > 0) {
               conquest.setBloom(pos, bloom.withStoredBlood(bloom.storedBlood() - take));
               harvested += take;
               blooms++;
            }
         }
      }

      if (harvested <= 0) {
         player.displayClientMessage(Component.literal("Command refused: no nearby blooms have harvestable blood.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      } else {
         addInfiniteGrowth(player, Math.max(1, harvested / Math.max(1, (Integer)cfg.conquestBloomGrowthDivisor.get() * 2)));
         BloodfiendData.addBlood(
            player, Math.max(1, harvested / Math.max(1, (Integer)cfg.conquestBloomBloodDivisor.get())), BloodfiendHandler.getMaxBlood(player)
         );
         BloodfiendHandler.syncBlood(player);
         restore(
            player,
            harvested * (Double)cfg.conquestBloomHealMultiplier.get(),
            harvested * (Double)cfg.conquestBloomSpiritualMultiplier.get(),
            harvested * (Double)cfg.conquestBloomMagiculeMultiplier.get()
         );
         level.sendParticles(RED_BLOOD, player.getX(), player.getY() + 1.0, player.getZ(), 55, 1.3, 0.8, 1.3, 0.04);
         player.displayClientMessage(
            Component.literal("Command accepted: harvested " + harvested + " blood from " + blooms + " blooms.").withStyle(ChatFormatting.DARK_RED), true
         );
         return true;
      }
   }

   private static int getRootLaunchReserveCost(MoreskillsRaceConfig.BlackCommunion cfg) {
      return Math.max(25, Math.min(Math.max(1, (Integer)cfg.conquestRootStartCost.get()), 250));
   }

   private static BlackBloodDomainData.AltarData ensureRootFuel(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, BlockPos altarPos, int needed, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
      if (altar != null && altar.fuelBlood() < needed) {
         int missing = needed - altar.fuelBlood();
         int taken = siphonNearbyBloomBlood(level, conquest, altarPos, missing);
         if (taken <= 0) {
            return altar;
         }

         BlackBloodDomainData.AltarData boosted = new BlackBloodDomainData.AltarData(
            altar.owner(), altar.fuelBlood() + taken, altar.totalBlood(), altar.level(), altar.generatedBlood(), altar.domainKills()
         );
         domain.setAltar(altarPos, boosted);
         return boosted;
      } else {
         return altar;
      }
   }

   private static int siphonNearbyBloomBlood(ServerLevel level, BlackBloodConquestData conquest, BlockPos altarPos, int needed) {
      int taken = 0;
      int checked = 0;

      for (Entry<BlockPos, BlackBloodConquestData.BloomData> entry : conquest.blooms()) {
         if (checked++ >= 8 || taken >= needed) {
            break;
         }

         BlockPos pos = entry.getKey();
         if (level.isLoaded(pos) && !(pos.distSqr(altarPos) > 1048576.0)) {
            BlackBloodConquestData.BloomData bloom = conquest.getBloom(pos);
            if (bloom != null && bloom.storedBlood() > 0) {
               int drain = Math.min(needed - taken, Math.min(64, bloom.storedBlood()));
               if (drain > 0) {
                  conquest.setBloom(pos, bloom.withStoredBlood(bloom.storedBlood() - drain));
                  taken += drain;
               }
            }
         }
      }

      return taken;
   }

   private static void decorateRootStep(
      ServerLevel level, BlackBloodConquestData conquest, BlockPos rootPos, int progress, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (progress % 10 == 0) {
         BlockPos ground = findGroundNear(level, rootPos, 18);
         if (ground != null) {
            BlockPos marker = ground.above();
            if (canReplaceForFlower(level, marker)) {
               level.setBlock(marker, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
               conquest.addInfectionPressure(marker, 10, (Integer)cfg.conquestConqueredThreshold.get());
            }

            if (progress % 30 == 0) {
               BlockPos cap = marker.above();
               if (canReplaceForFlower(level, cap)) {
                  level.setBlock(cap, ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState(), 3);
                  conquest.addInfectionPressure(cap, 12, (Integer)cfg.conquestConqueredThreshold.get());
               }
            }
         }
      }
   }

   private static boolean registerDomainNode(ServerLevel level, BlackBloodDomainData domain, BlockPos altarPos, BlockPos nodePos, int generation) {
      BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
      if (altar != null && nodePos != null && level.isLoaded(nodePos)) {
         BlockState state = level.getBlockState(nodePos);
         if (!isConquestBlock(state) && !state.is((Block)ModBlocks.BLOOD_ALTAR.get())) {
            return false;
         } else {
            return domain.hasNodeNear(nodePos, 10) ? false : domain.addNode(nodePos, altar.owner(), altarPos, Math.max(1, generation));
         }
      } else {
         return false;
      }
   }

   private static int seedDomainNodesAround(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodConquestData conquest,
      BlockPos altarPos,
      BlockPos center,
      int radius,
      int maxNodes,
      int generation
   ) {
      if (altarPos != null && center != null && maxNodes > 0) {
         int placed = 0;
         int safeRadius = Math.max(4, Math.min(64, radius));
         int startSector = Math.floorMod((int)(level.getGameTime() / 20L) + center.getX() * 31 + center.getZ() * 17, 16);
         if (registerDomainNode(level, domain, altarPos, center, generation)) {
            placed++;
         }

         for (int ring = 2; ring <= safeRadius && placed < maxNodes; ring += 2) {
            int sectors = Math.max(8, ring * 4);

            for (int i = 0; i < sectors && placed < maxNodes; i++) {
               int sector = Math.floorMod(i + startSector, sectors);
               double angle = (Math.PI * 2) * sector / sectors;
               int x = (int)Math.round(Math.cos(angle) * ring);
               int z = (int)Math.round(Math.sin(angle) * ring);

               for (int y = -3; y <= 4 && placed < maxNodes; y++) {
                  BlockPos check = center.offset(x, y, z);
                  if (level.isLoaded(check)) {
                     BlockState state = level.getBlockState(check);
                     if (isConquestBlock(state) && registerDomainNode(level, domain, altarPos, check, generation)) {
                        conquest.addInfectionPressure(
                           check, 18 + generation * 4, (Integer)MoreskillsRaceConfig.get().BlackCommunion.conquestConqueredThreshold.get()
                        );
                        placed++;
                        break;
                     }

                     BlockPos ground = findInfectableGroundNear(level, check);
                     if (ground != null && infectConquestBlock(level, ground) && registerDomainNode(level, domain, altarPos, ground, generation)) {
                        conquest.addInfectionPressure(
                           ground, 18 + generation * 4, (Integer)MoreskillsRaceConfig.get().BlackCommunion.conquestConqueredThreshold.get()
                        );
                        placed++;
                        break;
                     }
                  }
               }
            }
         }

         return placed;
      } else {
         return 0;
      }
   }

   private static void reinforceBloomVeins(
      ServerLevel level, BlackBloodConquestData conquest, BlockPos center, int budget, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int placed = 0;
      int attempts = Math.max(8, Math.min(48, budget * 3));

      for (int i = 0; i < attempts && placed < budget; i++) {
         Direction direction = randomHorizontal(level);
         int distance = 2 + level.random.nextInt(8);
         BlockPos ground = findGroundNear(level, center.relative(direction, distance).offset(level.random.nextInt(5) - 2, 0, level.random.nextInt(5) - 2), 8);
         if (ground != null) {
            BlockPos vein = ground.above();
            if (canReplaceForFlower(level, vein)) {
               level.setBlock(vein, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
               conquest.addInfectionPressure(vein, 16, (Integer)cfg.conquestConqueredThreshold.get());
               placed++;
            }
         }
      }
   }

   private static void collapseBloomCore(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodConquestData conquest, BlockPos core, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      BlackBloodConquestData.BloomData bloom = conquest.getBloom(core);
      if (bloom != null) {
         conquest.removeBloom(core);
         int removed = 0;
         int radius = 18;
         int attempts = 220;

         for (int i = 0; i < attempts && removed < 110; i++) {
            BlockPos pos = core.offset(
               level.random.nextInt(radius * 2 + 1) - radius, level.random.nextInt(13) - 4, level.random.nextInt(radius * 2 + 1) - radius
            );
            if (level.isLoaded(pos)) {
               BlockState state = level.getBlockState(pos);
               if (isConquestBlock(state)
                  && !state.is((Block)ModBlocks.BLOOD_ALTAR.get())
                  && !state.is((Block)ModBlocks.BLOOD_WORLD_HEART_CORE.get())
                  && (!state.is((Block)ModBlocks.BLOOD_FLOWER_CORE.get()) || conquest.getBloom(pos) == null)) {
                  level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                  domain.subtractChunkValue(pos, Math.max(1, (Integer)cfg.domainChunkValuePerBlock.get() * 2));
                  removed++;
               }
            }
         }

         for (Entry<BlockPos, BlackBloodConquestData.RootData> entry : new ArrayList<>(toRootList(conquest))) {
            if (entry.getKey().distSqr(core) <= 4096.0) {
               conquest.removeRoot(entry.getKey());
            }
         }

         level.sendParticles(ParticleTypes.SMOKE, core.getX() + 0.5, core.getY() + 1.0, core.getZ() + 0.5, 50, 1.4, 0.7, 1.4, 0.04);
         level.playSound(null, core, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 0.8F, 0.6F);
      }
   }

   private static List<Entry<BlockPos, BlackBloodConquestData.RootData>> toRootList(BlackBloodConquestData conquest) {
      List<Entry<BlockPos, BlackBloodConquestData.RootData>> roots = new ArrayList<>();

      for (Entry<BlockPos, BlackBloodConquestData.RootData> entry : conquest.roots()) {
         roots.add(entry);
      }

      return roots;
   }

   private static void sendRootCapacityMessage(ServerPlayer player, BlackBloodConquestData conquest) {
      player.sendSystemMessage(
         Component.literal("Command refused: root capacity is full: " + conquest.totalRootWorkCount() + " / 1000. Cancel one below or run /blackblood roots.")
            .withStyle(ChatFormatting.DARK_RED)
      );
      sendRootWorkList(player, conquest, true);
   }

   private static void sendRootWorkList(ServerPlayer player, BlackBloodConquestData conquest, boolean buttons) {
      List<BlackBloodConquestHandler.RootWorkEntry> entries = getRootWorkEntries(conquest);
      if (entries.isEmpty()) {
         player.sendSystemMessage(Component.literal("No active or pending Black Blood roots.").withStyle(ChatFormatting.DARK_RED));
      } else {
         player.sendSystemMessage(Component.literal("Black Blood roots: " + entries.size() + " / 1000").withStyle(ChatFormatting.DARK_RED));

         for (BlackBloodConquestHandler.RootWorkEntry entry : entries) {
            MutableComponent line = Component.literal(
                  "#"
                     + entry.index()
                     + " "
                     + entry.kind()
                     + " "
                     + entry.state()
                     + " at "
                     + entry.positionText()
                     + " -> chunk "
                     + entry.targetX()
                     + ", "
                     + entry.targetZ()
                     + " age "
                     + entry.age()
                     + " progress "
                     + entry.progress()
               )
               .withStyle(ChatFormatting.RED);
            if (buttons) {
               line.append(Component.literal(" "));
               line.append(
                  Component.literal("[Cancel]")
                     .withStyle(
                        style -> style.withColor(ChatFormatting.GOLD)
                           .withClickEvent(new ClickEvent(Action.RUN_COMMAND, "/blackblood cancelroot " + entry.index()))
                     )
               );
            }

            player.sendSystemMessage(line);
         }
      }
   }

   private static BlackBloodConquestHandler.RootWorkEntry getRootWorkEntry(BlackBloodConquestData conquest, int index) {
      for (BlackBloodConquestHandler.RootWorkEntry entry : getRootWorkEntries(conquest)) {
         if (entry.index() == index) {
            return entry;
         }
      }

      return null;
   }

   private static List<BlackBloodConquestHandler.RootWorkEntry> getRootWorkEntries(BlackBloodConquestData conquest) {
      List<BlackBloodConquestHandler.RootWorkEntry> entries = new ArrayList<>();
      int index = 1;

      for (Entry<BlockPos, BlackBloodConquestData.RootData> entry : conquest.roots()) {
         BlackBloodConquestData.RootData root = entry.getValue();
         ChunkPos target = new ChunkPos(root.targetChunk());
         entries.add(
            new BlackBloodConquestHandler.RootWorkEntry(
               index++,
               true,
               root.bloomOnArrival() ? "Siege Bloom Root" : "World Root",
               "active",
               entry.getKey(),
               root.targetChunk(),
               target.x,
               target.z,
               root.age(),
               root.progress()
            )
         );
      }

      for (Entry<Long, BlackBloodConquestData.PendingRootLaunch> entry : conquest.pendingRootLaunches()) {
         BlackBloodConquestData.PendingRootLaunch pending = entry.getValue();
         ChunkPos target = new ChunkPos(pending.targetChunk());
         entries.add(
            new BlackBloodConquestHandler.RootWorkEntry(
               index++,
               false,
               pending.bloomOnArrival() ? "Pending Siege Bloom" : "Pending World Root",
               "pending",
               pending.altarPos(),
               pending.targetChunk(),
               target.x,
               target.z,
               pending.age(),
               0
            )
         );
      }

      return entries;
   }

   private static BlockPos getBestWorldRootAltarForCommand(
      ServerLevel level, BlackBloodDomainData data, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      BlockPos best = null;
      int bestScore = Integer.MIN_VALUE;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : data.altars()) {
         BlockPos pos = entry.getKey();
         BlackBloodDomainData.AltarData altar = entry.getValue();
         int domainLevel = getDomainLevel(cfg, altar, conquest);
         if (domainLevel >= Math.max(1, (Integer)cfg.conquestRequiredDomainLevelForRoots.get())) {
            int loadedBonus = level.isLoaded(pos) ? 1000000 : 0;
            int score = loadedBonus + domainLevel * 100000 + getDomainXp(cfg, altar, conquest) + Math.max(0, altar.fuelBlood());
            if (score > bestScore) {
               bestScore = score;
               best = pos;
            }
         }
      }

      return best;
   }

   private static BlockPos getBestWorldRootAltar(
      ServerLevel level, BlackBloodDomainData data, BlackBloodConquestData conquest, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      BlockPos best = null;
      int bestScore = Integer.MIN_VALUE;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : data.altars()) {
         BlockPos pos = entry.getKey();
         if (level.isLoaded(pos) && level.getBlockState(pos).is((Block)ModBlocks.BLOOD_ALTAR.get())) {
            BlackBloodDomainData.AltarData altar = entry.getValue();
            int domainLevel = getDomainLevel(cfg, altar, conquest);
            if (domainLevel >= Math.max(1, (Integer)cfg.conquestRequiredDomainLevelForRoots.get())) {
               int score = domainLevel * 100000 + getDomainXp(cfg, altar, conquest) + altar.fuelBlood();
               if (score > bestScore) {
                  bestScore = score;
                  best = pos;
               }
            }
         }
      }

      return best;
   }

   private static BlockPos getNearestAltarAny(BlackBloodDomainData data, BlockPos center) {
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

   private static Direction randomHorizontal(ServerLevel level) {
      return Plane.HORIZONTAL.getRandomDirection(level.random);
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

   private static int safeAdd(int current, int add) {
      if (add <= 0) {
         return current;
      } else {
         return current >= Integer.MAX_VALUE - add ? Integer.MAX_VALUE : current + add;
      }
   }

   private static void addInfiniteGrowth(ServerPlayer player, int amount) {
      int current = player.getPersistentData().getInt("tensuramoreskills:black_blood_infinite_growth");
      int next = safeAdd(current, Math.max(0, amount));
      player.getPersistentData().putInt("tensuramoreskills:black_blood_infinite_growth", next);
      applyStoredGrowth(player);
   }

   private static void applyStoredGrowth(ServerPlayer player) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      int growth = Math.max(0, player.getPersistentData().getInt("tensuramoreskills:black_blood_infinite_growth"));
      if (growth > 0) {
         applyModifier(
            player.getAttribute(Attributes.MAX_HEALTH),
            GROWTH_HEALTH,
            Math.min((Double)cfg.conquestGrowthMaxHealthCap.get(), growth * (Double)cfg.conquestGrowthHealthPerPoint.get()),
            Operation.ADD_VALUE
         );
         applyModifier(
            player.getAttribute(Attributes.ATTACK_DAMAGE),
            GROWTH_ATTACK,
            Math.min((Double)cfg.conquestGrowthMaxAttackCap.get(), growth * (Double)cfg.conquestGrowthAttackPerPoint.get()),
            Operation.ADD_VALUE
         );
         applyModifier(
            player.getAttribute(Attributes.MOVEMENT_SPEED),
            GROWTH_SPEED,
            Math.min((Double)cfg.conquestGrowthMaxSpeedCap.get(), growth * (Double)cfg.conquestGrowthSpeedPerPoint.get()),
            Operation.ADD_VALUE
         );
      }
   }

   private static void applyModifier(AttributeInstance instance, ResourceLocation id, double amount, Operation operation) {
      if (instance != null) {
         instance.removeModifier(id);
         instance.addPermanentModifier(new AttributeModifier(id, amount, operation));
      }
   }

   private static void restore(LivingEntity entity, double health, double spiritual, double magicule) {
      if (health > 0.0) {
         entity.heal((float)Math.min(Float.MAX_VALUE, health));
      }

      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      if (spiritual > 0.0) {
         existence.setSpiritualHealth(Math.min(entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH), existence.getSpiritualHealth() + spiritual));
      }

      if (magicule > 0.0) {
         existence.setMagicule(Math.min(EnergyHelper.getMaxMagicule(entity), existence.getMagicule() + magicule));
      }

      existence.markDirty();
   }

   private record DomainPowerCache(long gameTime, int domainVersion, int conquestVersion, int level, int xp, BlockPos altarPos) {
   }

   private record RootWorkEntry(
      int index, boolean active, String kind, String state, BlockPos pos, long targetChunk, int targetX, int targetZ, int age, int progress
   ) {
      private String positionText() {
         return this.pos.getX() + " " + this.pos.getY() + " " + this.pos.getZ();
      }
   }
}
