package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.registry.block.ModBlocks;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;
import org.joml.Vector3f;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class BlackBloodEcologyHandler {
   private static final String ROOT = "tensuramoreskills:black_blood_ecology";
   private static final String CONVERTED = "Converted";
   private static final String FUSION_TIER = "FusionTier";
   private static final String CORRUPTION = "Corruption";
   private static final String CORRUPTION_TIME = "CorruptionTime";
   private static final String HOME_X = "HomeX";
   private static final String HOME_Y = "HomeY";
   private static final String HOME_Z = "HomeZ";
   private static final String ALTAR_X = "AltarX";
   private static final String ALTAR_Y = "AltarY";
   private static final String ALTAR_Z = "AltarZ";
   private static final String LAST_ATTACK = "LastAttack";
   private static final String LAST_KILL = "LastKill";
   private static final String TEAM_NAME = "bb_minions";
   private static final ResourceLocation HEALTH = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "black_blood_minion_health");
   private static final ResourceLocation ATTACK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "black_blood_minion_attack");
   private static final ResourceLocation ARMOR = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "black_blood_minion_armor");
   private static final ResourceLocation SPEED = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "black_blood_minion_speed");
   private static final ResourceLocation SCALE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "black_blood_minion_scale");
   private static final DustParticleOptions BLACK = new DustParticleOptions(new Vector3f(0.015F, 0.0F, 0.01F), 1.35F);
   private static final DustParticleOptions RED = new DustParticleOptions(new Vector3f(0.55F, 0.0F, 0.015F), 1.2F);
   private static final DustParticleOptions BRIGHT_RED = new DustParticleOptions(new Vector3f(0.95F, 0.02F, 0.02F), 1.05F);
   private static final int MAX_STRUCTURES_PROCESSED = 6;
   private static final int MAX_PENDING_PROCESSED = 2;
   private static final int MAX_MATERIALIZATIONS = 2;
   private static final int MAX_TARGET_SCAN = 20;
   private static final int MAX_VIRTUAL_SOURCE_SEEDS = 12;
   private static final int MAX_VIRTUAL_STEPS = 8;
   private static final int MAX_ROOT_LAUNCHES = 10;
   private static final int VIRTUAL_INFECTED_SCAN_RADIUS = 2;
   private static final int HEMATIC_EXTRACTOR_INTERVAL = 6000;
   private static final int CARRION_ORCHARD_INTERVAL = 1200;
   private static final int MARROW_PUMP_INTERVAL = 1200;
   private static final int SANGUINE_COMPOSTER_INTERVAL = 100;
   private static final int GENERATED_STRUCTURE_TRANSFER = 240;
   private static final int STRUCTURE_REQUIRED_CHUNK_VALUE = 1;
   private static final int STRUCTURE_MIN_SPACING = 24;
   private static final int STRUCTURE_SEARCH_CHUNK_RADIUS = 6;
   private static final int STRUCTURE_SEARCH_SAMPLES = 384;
   private static final int STRUCTURE_EVOLUTION_ONE_AGE = 24000;
   private static final int STRUCTURE_EVOLUTION_TWO_AGE = 72000;
   private static final int STRUCTURE_EVOLUTION_ONE_POWER = 50;
   private static final int STRUCTURE_EVOLUTION_TWO_POWER = 200;
   private static final int BLOOD_TURBINE_INTERVAL = 1800;
   private static final int SCARLET_LATTICE_INTERVAL = 2400;
   private static final int PREDATOR_GARDEN_INTERVAL = 2400;
   private static final int CONQUEST_MONOLITH_INTERVAL = 3600;
   private static final Map<ServerLevel, BlackBloodEcologyHandler.InfectionPowerCache> INFECTION_POWER_CACHE = new WeakHashMap<>();

   private BlackBloodEcologyHandler() {
   }

   @SubscribeEvent
   public static void onRegisterCommands(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)Commands.literal("blackblood")
               .then(
                  ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ecology")
                                 .executes(context -> showEcologyStatus((CommandSourceStack)context.getSource())))
                              .then(
                                 ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("force").requires(source -> source.hasPermission(2)))
                                       .executes(context -> forceEcologyBuild((CommandSourceStack)context.getSource(), 1)))
                                    .then(
                                       Commands.argument("count", IntegerArgumentType.integer(1, 12))
                                          .executes(
                                             context -> forceEcologyBuild(
                                                (CommandSourceStack)context.getSource(), IntegerArgumentType.getInteger(context, "count")
                                             )
                                          )
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)Commands.literal("spawn").requires(source -> source.hasPermission(2)))
                                 .executes(context -> forceEcologyBuild((CommandSourceStack)context.getSource(), 1))
                           ))
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("evolve").requires(source -> source.hasPermission(2)))
                              .executes(context -> forceEcologyEvolution((CommandSourceStack)context.getSource()))
                        ))
                     .then(Commands.literal("list").executes(context -> listEcologyStructures((CommandSourceStack)context.getSource())))
               )
         );
   }

   private static int showEcologyStatus(CommandSourceStack source) {
      ServerLevel level = source.getLevel();
      MoreskillsRaceConfig.BlackCommunion cfg = cfg();
      BlackBloodDomainData domain = BlackBloodDomainData.get(level);
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
      int totalFuel = 0;
      int fundedAltars = 0;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : domain.altars()) {
         totalFuel = safeAdd(totalFuel, Math.max(0, entry.getValue().fuelBlood()));
         if (entry.getValue().fuelBlood() >= Math.max(1, (Integer)cfg.ecologyBuildBloodCost.get())) {
            fundedAltars++;
         }
      }

      BlockPos heart = conquest.worldHeartPos();
      source.sendSuccess(() -> Component.literal("Black Blood Ecology").withStyle(ChatFormatting.DARK_RED), false);
      source.sendSuccess(
         () -> Component.literal(
               "Enabled: "
                  + enabled(cfg)
                  + " | World Heart: "
                  + (heart == null ? "missing" : heart.toShortString())
                  + " | Loaded: "
                  + (heart != null && level.isLoaded(heart))
            )
            .withStyle(ChatFormatting.RED),
         false
      );
      source.sendSuccess(
         () -> Component.literal(
               "Structures: "
                  + ecology.structureCount()
                  + " | Pending builds: "
                  + ecology.pendingBuildCount()
                  + " | Pending roots: "
                  + ecology.pendingRootCount()
            )
            .withStyle(ChatFormatting.RED),
         false
      );
      int finalFundedAltars = fundedAltars;
      int finalTotalFuel = totalFuel;
      source.sendSuccess(
         () -> Component.literal(
               "Altars with build fuel: "
                  + finalFundedAltars
                  + " | Total altar fuel: "
                  + finalTotalFuel
                  + " | Build cost: "
                  + Math.max(1, (Integer)cfg.ecologyBuildBloodCost.get())
            )
            .withStyle(ChatFormatting.RED),
         false
      );
      source.sendSuccess(() -> Component.literal("Last build state: " + ecology.lastBuildStatus()).withStyle(ChatFormatting.DARK_RED), false);
      source.sendSuccess(() -> Component.literal("Pending reservation: " + ecology.firstPendingSummary()).withStyle(ChatFormatting.DARK_RED), false);
      source.sendSuccess(
         () -> Component.literal(
               "Last queued: "
                  + formatTime(level.getGameTime(), ecology.lastBuildQueuedTime())
                  + " | Last formed: "
                  + formatTime(level.getGameTime(), ecology.lastBuildSuccessTime())
            )
            .withStyle(ChatFormatting.DARK_RED),
         false
      );
      return 1;
   }

   private static int forceEcologyBuild(CommandSourceStack source, int count) {
      ServerLevel level = source.getLevel();
      MoreskillsRaceConfig.BlackCommunion cfg = cfg();
      if (enabled(cfg) && BlackBloodConquestData.get(level).worldHeartPos() != null) {
         BlackBloodDomainData domain = BlackBloodDomainData.get(level);
         BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
         int queued = 0;

         for (int i = 0; i < Math.max(1, count); i++) {
            if (queueBuildAttempt(level, domain, ecology, cfg, true)) {
               queued++;
            }
         }

         processPendingBuilds(level, domain, ecology, cfg);
         int result = queued;
         source.sendSuccess(
            () -> Component.literal("Ecology force pass queued " + result + " build(s). " + ecology.lastBuildStatus()).withStyle(ChatFormatting.RED), true
         );
         return queued > 0 ? 1 : 0;
      } else {
         source.sendFailure(Component.literal("Ecology requires an enabled Black Blood system and an existing World Heart."));
         return 0;
      }
   }

   private static int forceEcologyEvolution(CommandSourceStack source) {
      ServerLevel level = source.getLevel();
      BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
      BlockPos origin = source.getEntity() == null ? BlackBloodConquestData.get(level).worldHeartPos() : source.getEntity().blockPosition();
      if (origin == null) {
         source.sendFailure(Component.literal("No World Heart or command position is available."));
         return 0;
      } else {
         BlockPos pos = ecology.findNearestStructure(origin, 256);
         BlackBloodEcologyHandler.StructureData structure = pos == null ? null : ecology.getStructure(pos);
         if (structure == null) {
            source.sendFailure(Component.literal("No ecology structure exists within 256 blocks."));
            return 0;
         } else {
            int next = Math.min(2, structure.evolution() + 1);
            if (next <= structure.evolution()) {
               source.sendFailure(Component.literal("The nearest structure is already fully evolved."));
               return 0;
            } else {
               evolveStructureForm(level, pos, structure.type(), next);
               ecology.setStructure(pos, structure.withEvolution(next));
               ecology.setLastBuildStatus("EVOLVED: " + structure.type().name() + " E" + next + " at " + pos.toShortString());
               level.sendParticles(BRIGHT_RED, pos.getX() + 0.5, pos.getY() + 2.0 + next, pos.getZ() + 0.5, 48, 2.3, 2.0, 2.3, 0.04);
               level.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 0.9F, 0.4F + next * 0.08F);
               source.sendSuccess(
                  () -> Component.literal("Evolved " + structure.type().name() + " to E" + next + " at " + pos.toShortString())
                     .withStyle(ChatFormatting.DARK_RED),
                  true
               );
               return 1;
            }
         }
      }
   }

   private static int listEcologyStructures(CommandSourceStack source) {
      BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(source.getLevel());
      source.sendSuccess(() -> Component.literal("Ecology structures: " + ecology.structureCount()).withStyle(ChatFormatting.DARK_RED), false);
      int shown = 0;

      for (Entry<BlockPos, BlackBloodEcologyHandler.StructureData> entry : ecology.structureEntries()) {
         if (shown++ >= 16) {
            break;
         }

         BlockPos pos = entry.getKey();
         BlackBloodEcologyHandler.StructureData data = entry.getValue();
         source.sendSuccess(
            () -> Component.literal(data.type().name() + " E" + data.evolution() + " at " + pos.toShortString() + " blood " + data.storedBlood())
               .withStyle(ChatFormatting.RED),
            false
         );
      }

      if (ecology.structureCount() > 16) {
         source.sendSuccess(() -> Component.literal("Only the first 16 are shown.").withStyle(ChatFormatting.DARK_RED), false);
      }

      return 1;
   }

   private static String formatTime(long now, long then) {
      if (then <= 0L) {
         return "never";
      }

      long seconds = Math.max(0L, now - then) / 20L;
      return seconds + "s ago";
   }

   private static MoreskillsRaceConfig.BlackCommunion cfg() {
      return MoreskillsRaceConfig.get().BlackCommunion;
   }

   private static boolean enabled(MoreskillsRaceConfig.BlackCommunion cfg) {
      return (Boolean)cfg.enabled.get()
         && (Boolean)cfg.blockInfectionEnabled.get()
         && (Boolean)cfg.bloodAltarEnabled.get()
         && (Boolean)cfg.conquestEnabled.get()
         && (Boolean)cfg.ecologyEnabled.get();
   }

   @SubscribeEvent
   public static void onLevelTick(Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         MoreskillsRaceConfig.BlackCommunion cfg = cfg();
         if (enabled(cfg)) {
            long time = level.getGameTime();
            BlackBloodDomainData domain = BlackBloodDomainData.get(level);
            BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
            BlockPos heart = conquest.worldHeartPos();
            if (heart != null) {
               BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
               ensureVirtualSeed(domain, conquest, ecology, cfg);
               if (time % Math.max(200, Math.min(1200, (Integer)cfg.ecologyVirtualStepInterval.get())) == 0L) {
                  syncVirtualSources(level, domain, ecology, cfg);
                  int steps = Math.min(8, Math.max(1, (Integer)cfg.ecologyVirtualStepsPerPulse.get()) + ecology.structureCount() / 12);
                  advanceVirtualFrontier(level, domain, ecology, cfg, steps);
               }

               if (time % Math.max(100, (Integer)cfg.ecologyMaterializeInterval.get()) == 0L) {
                  materializeNearPlayers(level, domain, ecology, cfg);
               }

               if (ecology.structureCount() <= 0 && ecology.pendingBuildCount() <= 0 && time % 200L == 0L) {
                  queueBuildAttempt(level, domain, ecology, cfg, true);
               } else if (time % Math.max(1200, Math.min(6000, (Integer)cfg.ecologyBuildInterval.get())) == 0L) {
                  queueBuildAttempt(level, domain, ecology, cfg);
               }

               if (time % Math.max(40, (Integer)cfg.ecologyPendingProcessInterval.get()) == 0L) {
                  processPendingBuilds(level, domain, ecology, cfg);
                  processPendingRoots(level, domain, conquest, ecology, cfg);
               }

               if (time % Math.max(1200, (Integer)cfg.ecologyWorldHeartRootInterval.get()) == 0L) {
                  maintainWorldHeartRoots(level, domain, conquest, ecology, cfg);
               }

               if (time % Math.max(40, (Integer)cfg.ecologyTickInterval.get()) == 0L) {
                  tickStructures(level, domain, ecology, cfg);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityTick(net.neoforged.neoforge.event.tick.EntityTickEvent.Post event) {
      if (event.getEntity() instanceof Mob mob && mob.level() instanceof ServerLevel level) {
         MoreskillsRaceConfig.BlackCommunion cfg = cfg();
         if (enabled(cfg) && Math.floorMod(mob.tickCount - mob.getId(), 10) == 0) {
            CompoundTag tag = ecologyTagOptional(mob);
            if (tag != null && tag.getBoolean("Converted")) {
               tickConvertedMob(level, mob, cfg);
            } else if (tag != null && tag.getInt("Corruption") > 0) {
               long now = level.getGameTime();
               if (now - tag.getLong("CorruptionTime") > Math.max(400, (Integer)cfg.ecologyCorruptionDecayTicks.get())) {
                  tag.remove("Corruption");
                  tag.remove("CorruptionTime");
                  clearEcologyTagIfEmpty(mob, tag);
               } else {
                  tryConvertMarkedMob(level, mob, cfg);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      LivingEntity target = event.getEntity();
      if (target.level() instanceof ServerLevel level) {
         Entity var7 = event.getSource().getEntity();
         if (isConvertedBloodMinion(target)) {
            if (var7 instanceof LivingEntity attacker && isBloodFaction(attacker)) {
               event.setAmount(0.0F);
            }
         } else if (target instanceof Mob mob && var7 instanceof LivingEntity attacker && isConvertedBloodMinion(attacker)) {
            MoreskillsRaceConfig.BlackCommunion cfg = cfg();
            if (enabled(cfg) && !BloodfiendHandler.isBloodfiend(target)) {
               addCorruption(mob, 1, level.getGameTime());
            } else {
               event.setAmount(0.0F);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity killed = event.getEntity();
      if (killed.level() instanceof ServerLevel level) {
         if (isConverted(killed)) {
            removeFromMinionTeam(level, killed);
         }

         MoreskillsRaceConfig.BlackCommunion cfg = cfg();
         if (enabled(cfg) && BlackBloodConquestData.get(level).worldHeartPos() != null && !isBloodFaction(killed)) {
            feedCarrionOrchard(level, killed, cfg);
         }

         if (event.getSource().getEntity() instanceof LivingEntity attacker && isConverted(attacker) && !isConverted(killed)) {
            if (enabled(cfg)) {
               CompoundTag attackerTag = ecologyTag(attacker);
               long now = level.getGameTime();
               if (now - attackerTag.getLong("LastKill") >= 10L) {
                  attackerTag.putLong("LastKill", now);
                  int gain = Mth.clamp(
                     Mth.ceil(killed.getMaxHealth() / Math.max(1.0F, ((Double)cfg.ecologyBloodHealthDivisor.get()).floatValue())),
                     1,
                     Math.max(1, (Integer)cfg.ecologyMaxBloodPerKill.get())
                  );
                  BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
                  BlockPos structure = ecology.findNearestStructure(killed.blockPosition(), Math.max(32, (Integer)cfg.ecologyBloodCollectionRadius.get()));
                  if (structure != null) {
                     ecology.addStructureBlood(structure, gain, Math.max(100, (Integer)cfg.ecologyStructureBloodCapacity.get()));
                  } else {
                     BlockPos altarPos = findNearestAltar(
                        BlackBloodDomainData.get(level), killed.blockPosition(), Math.max(64, (Integer)cfg.ecologyBloodCollectionRadius.get() * 2)
                     );
                     if (altarPos != null) {
                        addAltarFuel(BlackBloodDomainData.get(level), altarPos, gain, cfg);
                     }
                  }

                  level.sendParticles(
                     RED,
                     killed.getX(),
                     killed.getY() + killed.getBbHeight() * 0.5,
                     killed.getZ(),
                     Math.min(10, 2 + gain),
                     killed.getBbWidth() * 0.3,
                     killed.getBbHeight() * 0.25,
                     killed.getBbWidth() * 0.3,
                     0.02
                  );
               }
            }
         }
      }
   }

   private static void ensureVirtualSeed(
      BlackBloodDomainData domain, BlackBloodConquestData conquest, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (!ecology.hasVirtualChunks()) {
         int claimSteps = Math.max(2, (Integer)cfg.ecologyVirtualClaimSteps.get());
         BlockPos heart = conquest.worldHeartPos();
         BlockPos nearest = heart == null ? null : findNearestPersistedInfection(domain, ecology, new ChunkPos(heart), Set.of());
         if (nearest != null) {
            ecology.seedVirtual(ChunkPos.asLong(nearest), claimSteps);
         } else if (heart != null) {
            ecology.seedVirtual(ChunkPos.asLong(heart), claimSteps);
         }
      }
   }

   private static void syncVirtualSources(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int claimSteps = Math.max(2, (Integer)cfg.ecologyVirtualClaimSteps.get());
      int remaining = 12;
      remaining -= seedVirtualFromPersistedNodes(domain, ecology, claimSteps, remaining);
      if (remaining > 0) {
         seedVirtualFromNearbyInfectedChunks(level, domain, ecology, claimSteps, remaining);
      }
   }

   private static int seedVirtualFromPersistedNodes(BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, int claimSteps, int limit) {
      if (limit <= 0) {
         return 0;
      }

      int start = ecology.nodeSourceCursor();
      int index = 0;
      int seeded = 0;

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : domain.nodes()) {
         if (index++ >= start) {
            BlackBloodDomainData.NodeData node = entry.getValue();
            if (node != null && domain.getAltar(node.altarPos()) != null) {
               ecology.seedVirtual(ChunkPos.asLong(entry.getKey()), claimSteps);
               if (++seeded >= limit) {
                  ecology.setNodeSourceCursor(index);
                  return seeded;
               }
            }
         }
      }

      ecology.setNodeSourceCursor(0);
      return seeded;
   }

   private static int seedVirtualFromNearbyInfectedChunks(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, int claimSteps, int limit
   ) {
      if (limit <= 0) {
         return 0;
      }

      int seeded = 0;
      int diameter = 5;
      int area = diameter * diameter;

      for (ServerPlayer player : level.players()) {
         ChunkPos center = player.chunkPosition();
         int offset = Math.floorMod((int)(level.getGameTime() / 200L) + player.getId(), area);

         for (int step = 0; step < area && seeded < limit; step++) {
            int index = Math.floorMod(offset + step, area);
            int chunkX = center.x + index % diameter - 2;
            int chunkZ = center.z + index / diameter - 2;
            BlockPos sample = new BlockPos((chunkX << 4) + 8, level.getSeaLevel(), (chunkZ << 4) + 8);
            if (domain.getChunkValue(sample) > 0) {
               ecology.seedVirtual(ChunkPos.asLong(chunkX, chunkZ), claimSteps);
               seeded++;
            }
         }

         if (seeded >= limit) {
            break;
         }
      }

      return seeded;
   }

   private static void advanceVirtualFrontier(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg, int steps
   ) {
      int count = Math.min(8, Math.max(1, steps));
      int i = 0;

      while (i < count && advanceOneVirtualFrontier(level, domain, ecology, cfg, null, true)) {
         i++;
      }
   }

   private static boolean advanceOneVirtualFrontier(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      MoreskillsRaceConfig.BlackCommunion cfg,
      BlockPos forcedAltar,
      boolean chargeFuel
   ) {
      Long target = ecology.pollFrontier();
      if (target == null) {
         return false;
      }

      int claimSteps = Math.max(2, (Integer)cfg.ecologyVirtualClaimSteps.get());
      int oldPressure = ecology.getVirtualPressure(target);
      if (oldPressure >= claimSteps) {
         ecology.expandFrontierFrom(target);
         return true;
      }

      int cost = Math.max(1, (Integer)cfg.ecologyVirtualStepBloodCost.get());
      BlockPos altarPos = forcedAltar;
      if (altarPos == null || domain.getAltar(altarPos) == null || chargeFuel && domain.getAltar(altarPos).fuelBlood() < cost) {
         altarPos = findNearestFundingAltar(domain, new ChunkPos(target), chargeFuel ? cost : 0);
      }

      if (altarPos == null) {
         ecology.requeueFrontier(target);
         return false;
      }

      if (chargeFuel && !consumeAltarFuel(domain, altarPos, cost)) {
         ecology.requeueFrontier(target);
         return false;
      }

      int nextPressure = oldPressure + 1;
      ecology.setVirtualPressure(target, nextPressure);
      if (nextPressure >= claimSteps) {
         ChunkPos claimed = new ChunkPos(target);
         BlockPos memoryPos = new BlockPos(claimed.getMiddleBlockX(), level.getSeaLevel(), claimed.getMiddleBlockZ());
         BlackBloodConquestData.get(level)
            .addInfectionPressure(
               memoryPos, Math.max(1, (Integer)cfg.conquestConqueredThreshold.get()), Math.max(1, (Integer)cfg.conquestConqueredThreshold.get())
            );
         rewardMarrowPump(level, ecology, memoryPos, 90);
         ecology.expandFrontierFrom(target);
      } else {
         ecology.requeueFrontier(target);
      }

      return true;
   }

   private static void materializeNearPlayers(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int completed = 0;
      int claimSteps = Math.max(2, (Integer)cfg.ecologyVirtualClaimSteps.get());
      int diameter = 5;
      int area = diameter * diameter;

      for (ServerPlayer player : level.players()) {
         if (completed >= 2) {
            break;
         }

         ChunkPos center = player.chunkPosition();
         int offset = Math.floorMod((int)(level.getGameTime() / Math.max(1, (Integer)cfg.ecologyMaterializeInterval.get())) + player.getId(), area);

         for (int step = 0; step < area && completed < 2; step++) {
            int index = Math.floorMod(offset + step, area);
            int chunkX = center.x + index % diameter - 2;
            int chunkZ = center.z + index / diameter - 2;
            long key = ChunkPos.asLong(chunkX, chunkZ);
            if (ecology.getVirtualPressure(key) >= claimSteps && !ecology.isMaterialized(key) && level.hasChunk(chunkX, chunkZ)) {
               BlockPos seed = materializeChunk(level, domain, ecology, key, cfg);
               if (seed != null) {
                  ecology.markMaterialized(key);
                  BlackBloodDomainHandler.trySlowNaturalSpread(level, seed, 1);
                  completed++;
               }
            }
         }
      }
   }

   private static BlockPos materializeChunk(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, long chunkKey, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      ChunkPos chunk = new ChunkPos(chunkKey);
      BlockPos existingNode = findLoadedNodeSeed(level, domain, chunkKey);
      if (existingNode != null) {
         domain.addChunkValue(existingNode, Math.max(1, (Integer)cfg.domainChunkValuePerBlock.get() * 4), (Integer)cfg.domainChunkMaxValue.get());
         return existingNode;
      }

      BlockPos altarPos = findMaterializationAltar(domain, chunk);
      BlackBloodDomainData.AltarData altar = altarPos == null ? null : domain.getAltar(altarPos);
      if (altar == null) {
         return null;
      }

      int start = Math.floorMod((int)(chunkKey ^ level.getGameTime()), 16);

      for (int i = 0; i < 8; i++) {
         int localX = Math.floorMod(start + i * 5, 16);
         int localZ = Math.floorMod(start * 3 + i * 7, 16);
         int x = chunk.getMinBlockX() + localX;
         int z = chunk.getMinBlockZ() + localZ;
         int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;
         BlockPos ground = new BlockPos(x, y, z);
         BlockPos above = ground.above();
         if (level.isLoaded(ground)) {
            BlockState groundState = level.getBlockState(ground);
            BlockState aboveState = level.getBlockState(above);
            if (!isProtected(level, ground, groundState)) {
               if ((aboveState.isAir() || aboveState.canBeReplaced()) && groundState.isFaceSturdy(level, ground, Direction.UP)) {
                  level.setBlock(above, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3);
                  domain.addChunkValue(above, Math.max(1, (Integer)cfg.domainChunkValuePerBlock.get() * 4), (Integer)cfg.domainChunkMaxValue.get());
                  domain.addNode(above, altar.owner(), altarPos, 1);
                  level.sendParticles(BLACK, above.getX() + 0.5, above.getY() + 0.5, above.getZ() + 0.5, 8, 0.25, 0.25, 0.25, 0.01);
                  return above;
               }

               if (canReplaceNaturalGround(groundState)) {
                  BlockState replacement = groundState.is(Blocks.GRASS_BLOCK)
                     ? ((Block)ModBlocks.INFECTED_GRASS.get()).defaultBlockState()
                     : ((Block)ModBlocks.INFECTED_DIRT.get()).defaultBlockState();
                  level.setBlock(ground, replacement, 3);
                  domain.addChunkValue(ground, Math.max(1, (Integer)cfg.domainChunkValuePerBlock.get() * 4), (Integer)cfg.domainChunkMaxValue.get());
                  domain.addNode(ground, altar.owner(), altarPos, 1);
                  level.sendParticles(BLACK, ground.getX() + 0.5, ground.getY() + 0.7, ground.getZ() + 0.5, 8, 0.25, 0.25, 0.25, 0.01);
                  return ground;
               }
            }
         }
      }

      return null;
   }

   private static BlockPos findLoadedNodeSeed(ServerLevel level, BlackBloodDomainData domain, long chunkKey) {
      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : domain.nodes()) {
         BlockPos pos = entry.getKey();
         BlackBloodDomainData.NodeData node = entry.getValue();
         if (ChunkPos.asLong(pos) == chunkKey
            && node != null
            && domain.getAltar(node.altarPos()) != null
            && level.isLoaded(pos)
            && isEcologyInfectionSource(level.getBlockState(pos))) {
            return pos;
         }
      }

      return null;
   }

   private static BlockPos findMaterializationAltar(BlackBloodDomainData domain, ChunkPos targetChunk) {
      BlockPos best = null;
      int bestDistance = Integer.MAX_VALUE;

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : domain.nodes()) {
         BlackBloodDomainData.NodeData node = entry.getValue();
         if (node != null && domain.getAltar(node.altarPos()) != null) {
            ChunkPos nodeChunk = new ChunkPos(entry.getKey());
            int distance = Math.max(Math.abs(nodeChunk.x - targetChunk.x), Math.abs(nodeChunk.z - targetChunk.z));
            if (distance < bestDistance) {
               bestDistance = distance;
               best = node.altarPos();
               if (distance == 0) {
                  return best;
               }
            }
         }
      }

      if (best != null) {
         return best;
      }

      Entry<BlockPos, BlackBloodDomainData.AltarData> fallback = findBestAltar(domain, 0);
      return fallback == null ? null : fallback.getKey();
   }

   private static boolean isEcologyInfectionSource(BlockState state) {
      return state.is((Block)ModBlocks.INFECTED_GRASS.get())
         || state.is((Block)ModBlocks.INFECTED_DIRT.get())
         || state.is((Block)ModBlocks.BLOOD_FLOWER_ROOT.get())
         || state.is((Block)ModBlocks.BLOOD_FLOWER_PETAL.get())
         || state.is((Block)ModBlocks.BLOOD_FLOWER_CORE.get())
         || state.is((Block)ModBlocks.BLOOD_ALTAR.get())
         || state.is((Block)ModBlocks.BLOOD_WORLD_HEART_CORE.get());
   }

   private static BlockPos findNearestPersistedInfection(
      BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, ChunkPos target, Set<BlockPos> excluded
   ) {
      BlockPos best = null;
      int bestDistance = Integer.MAX_VALUE;
      int checked = 0;

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : domain.nodes()) {
         if (checked++ >= 512) {
            break;
         }

         BlockPos pos = entry.getKey();
         BlackBloodDomainData.NodeData node = entry.getValue();
         if (node != null && !excluded.contains(pos) && domain.getAltar(node.altarPos()) != null) {
            ChunkPos chunk = new ChunkPos(pos);
            int distance = Math.max(Math.abs(chunk.x - target.x), Math.abs(chunk.z - target.z));
            if (distance < bestDistance) {
               bestDistance = distance;
               best = pos;
            }
         }
      }

      for (BlockPos pos : ecology.structurePositions()) {
         if (!excluded.contains(pos)) {
            ChunkPos chunk = new ChunkPos(pos);
            int distance = Math.max(Math.abs(chunk.x - target.x), Math.abs(chunk.z - target.z));
            if (distance < bestDistance) {
               bestDistance = distance;
               best = pos;
            }
         }
      }

      return best;
   }

   private static BlockPos findNearestFundingAltar(BlackBloodDomainData domain, ChunkPos target, int requiredFuel) {
      BlockPos best = null;
      int bestDistance = Integer.MAX_VALUE;
      int bestFuel = -1;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : domain.altars()) {
         BlackBloodDomainData.AltarData altar = entry.getValue();
         if (altar != null && altar.fuelBlood() >= requiredFuel) {
            ChunkPos chunk = new ChunkPos(entry.getKey());
            int distance = Math.max(Math.abs(chunk.x - target.x), Math.abs(chunk.z - target.z));
            if (distance < bestDistance || distance == bestDistance && altar.fuelBlood() > bestFuel) {
               bestDistance = distance;
               bestFuel = altar.fuelBlood();
               best = entry.getKey();
            }
         }
      }

      return best;
   }

   private static void maintainWorldHeartRoots(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodConquestData conquest,
      BlackBloodEcologyHandler.EcologyData ecology,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      BlockPos heart = conquest.worldHeartPos();
      if (heart != null) {
         int desired = Math.min(10, Math.max(1, (Integer)cfg.ecologyWorldHeartRootCount.get()));
         ecology.pruneActiveRootTargets(conquest);
         int existing = ecology.activeRootCount() + ecology.pendingRootCount();
         if (existing < desired) {
            BlackBloodConquestData.TargetChunk target = conquest.findBestPlayerBaseTarget(
               level.getGameTime(),
               Math.max(1, (Integer)cfg.conquestMinPlayerHeatTarget.get() / 2),
               Math.max(1, (Integer)cfg.conquestMinBaseHeatTarget.get() / 2)
            );
            if (target != null) {
               ChunkPos baseTarget = new ChunkPos(target.chunk());
               Set<BlockPos> usedSources = new HashSet<>();
               int needed = desired - existing;

               for (int i = 0; i < needed; i++) {
                  int ring = i / 4 + 1;
                  int direction = i & 3;
                  int offsetX = direction == 0 ? ring : (direction == 1 ? -ring : 0);
                  int offsetZ = direction == 2 ? ring : (direction == 3 ? -ring : 0);
                  long targetChunk = ChunkPos.asLong(baseTarget.x + offsetX, baseTarget.z + offsetZ);
                  if (!conquest.hasRootTargeting(targetChunk) && !ecology.hasPendingRootTarget(targetChunk)) {
                     BlockPos source = findNearestPersistedInfection(domain, ecology, new ChunkPos(targetChunk), usedSources);
                     if (source == null) {
                        break;
                     }

                     BlockPos altarPos = findSourceAltar(domain, ecology, source);
                     if (altarPos == null) {
                        usedSources.add(source);
                     } else {
                        ecology.queuePendingRoot(
                           new BlackBloodEcologyHandler.PendingRoot(source, altarPos, targetChunk, target.baseLike(), level.getGameTime())
                        );
                        usedSources.add(source);
                     }
                  }
               }
            }
         }
      }
   }

   private static void processPendingRoots(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodConquestData conquest,
      BlackBloodEcologyHandler.EcologyData ecology,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int processed = 0;

      for (Entry<Long, BlackBloodEcologyHandler.PendingRoot> entry : ecology.pendingRoots()) {
         if (processed++ >= 10) {
            break;
         }

         long targetChunk = entry.getKey();
         BlackBloodEcologyHandler.PendingRoot pending = ecology.getPendingRoot(targetChunk);
         if (pending != null) {
            if (conquest.hasRootTargeting(targetChunk)) {
               ecology.removePendingRoot(targetChunk);
            } else {
               BlockPos source = pending.source();
               BlockPos altarPos = pending.altarPos();
               BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
               if (altar == null) {
                  source = findNearestPersistedInfection(domain, ecology, new ChunkPos(targetChunk), Set.of());
                  if (source == null) {
                     continue;
                  }

                  altarPos = findSourceAltar(domain, ecology, source);
                  if (altarPos == null) {
                     continue;
                  }

                  pending = new BlackBloodEcologyHandler.PendingRoot(source, altarPos, targetChunk, pending.baseLike(), pending.queuedAt());
                  ecology.setPendingRoot(targetChunk, pending);
                  altar = domain.getAltar(altarPos);
               }

               int reserve = Math.max(1, (Integer)cfg.conquestRootStartCost.get());
               if (altar != null && altar.fuelBlood() >= reserve && level.isLoaded(source)) {
                  BlockState state = level.getBlockState(source);
                  if (!isEcologyInfectionSource(state)) {
                     BlockPos replacement = findNearestLoadedInfection(level, domain, ecology, new ChunkPos(targetChunk), source);
                     if (replacement == null) {
                        continue;
                     }

                     source = replacement;
                     altarPos = findSourceAltar(domain, ecology, source);
                     if (altarPos == null) {
                        continue;
                     }

                     pending = new BlackBloodEcologyHandler.PendingRoot(source, altarPos, targetChunk, pending.baseLike(), pending.queuedAt());
                     ecology.setPendingRoot(targetChunk, pending);
                     altar = domain.getAltar(altarPos);
                     if (altar == null || altar.fuelBlood() < reserve) {
                        continue;
                     }
                  }

                  BlockPos start = openRootAtInfection(level, source);
                  if (start != null && consumeAltarFuel(domain, altarPos, reserve)) {
                     conquest.addRoot(start, altarPos, targetChunk, pending.baseLike(), true);
                     ecology.markActiveRootTarget(targetChunk);
                     BlackBloodDomainData.AltarData ownerAltar = domain.getAltar(altarPos);
                     if (ownerAltar != null && domain.getNode(start) == null) {
                        domain.addNode(start, ownerAltar.owner(), altarPos, 1);
                     }

                     ecology.removePendingRoot(targetChunk);
                     level.sendParticles(BLACK, start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5, 22, 0.45, 0.25, 0.45, 0.025);
                     level.playSound(null, start, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 0.8F, 0.4F);
                  }
               }
            }
         }
      }
   }

   private static BlockPos findSourceAltar(BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, BlockPos source) {
      BlackBloodDomainData.NodeData node = domain.getNode(source);
      if (node != null && domain.getAltar(node.altarPos()) != null) {
         return node.altarPos();
      }

      BlackBloodEcologyHandler.StructureData structure = ecology.getStructure(source);
      return structure != null && domain.getAltar(structure.altarPos()) != null
         ? structure.altarPos()
         : findNearestFundingAltar(domain, new ChunkPos(source), 0);
   }

   private static BlockPos findNearestLoadedInfection(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, ChunkPos target, BlockPos excluded
   ) {
      BlockPos best = null;
      int bestDistance = Integer.MAX_VALUE;
      int checked = 0;

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : domain.nodes()) {
         if (checked++ >= 256) {
            break;
         }

         BlockPos pos = entry.getKey();
         BlackBloodDomainData.NodeData node = entry.getValue();
         if (!pos.equals(excluded)
            && node != null
            && domain.getAltar(node.altarPos()) != null
            && level.isLoaded(pos)
            && isEcologyInfectionSource(level.getBlockState(pos))) {
            ChunkPos chunk = new ChunkPos(pos);
            int distance = Math.max(Math.abs(chunk.x - target.x), Math.abs(chunk.z - target.z));
            if (distance < bestDistance) {
               bestDistance = distance;
               best = pos;
            }
         }
      }

      for (BlockPos pos : ecology.structurePositions()) {
         if (!pos.equals(excluded) && level.isLoaded(pos) && isEcologyInfectionSource(level.getBlockState(pos))) {
            ChunkPos chunk = new ChunkPos(pos);
            int distance = Math.max(Math.abs(chunk.x - target.x), Math.abs(chunk.z - target.z));
            if (distance < bestDistance) {
               bestDistance = distance;
               best = pos;
            }
         }
      }

      return best;
   }

   private static BlockPos openRootAtInfection(ServerLevel level, BlockPos source) {
      if (!level.isLoaded(source)) {
         return null;
      }

      if (level.getBlockState(source).is((Block)ModBlocks.BLOOD_FLOWER_ROOT.get())) {
         return source;
      }

      for (Direction direction : Direction.values()) {
         BlockPos candidate = source.relative(direction);
         BlockState state = level.getBlockState(candidate);
         if ((state.isAir() || state.canBeReplaced()) && level.setBlock(candidate, ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState(), 3)) {
            return candidate;
         }
      }

      return isEcologyInfectionSource(level.getBlockState(source)) ? source : null;
   }

   private static boolean queueBuildAttempt(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      return queueBuildAttempt(level, domain, ecology, cfg, false);
   }

   private static boolean queueBuildAttempt(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg, boolean forced
   ) {
      ecology.markBuildAttempt(level.getGameTime());
      if (ecology.pendingBuildCount() >= Math.max(1, (Integer)cfg.ecologyMaxPendingBuilds.get())) {
         ecology.setLastBuildStatus("WAITING: pending build limit " + ecology.pendingBuildCount());
         return false;
      }

      int maxStructures = Math.max(128, (Integer)cfg.ecologyMaxStructures.get());
      if (ecology.structureCount() + ecology.pendingStructureCount() >= maxStructures) {
         ecology.setLastBuildStatus("STOPPED: structure limit " + maxStructures);
         return false;
      }

      BlockPos heart = BlackBloodConquestData.get(level).worldHeartPos();
      if (heart == null) {
         ecology.setLastBuildStatus("WAITING: World Heart missing");
         return false;
      }

      if (level.isLoaded(heart) && domain.getChunkValue(heart) < 1) {
         domain.addChunkValue(heart, 1, (Integer)cfg.domainChunkMaxValue.get());
      }

      int attempt = ecology.nextBuildAttempt();
      boolean remote = !forced
         && (Boolean)cfg.ecologyRemoteAltarsEnabled.get()
         && attempt % Math.max(1, (Integer)cfg.ecologyRemoteAltarAttemptEvery.get()) == 0;
      BlockPos ground = remote ? findRemoteAltarGround(level, domain, ecology, cfg) : null;
      if (ground == null) {
         remote = false;
         ground = findStructureGround(level, domain, ecology, cfg);
      }

      if (ground == null) {
         ecology.setLastBuildStatus(
            level.isLoaded(heart) ? "WAITING: no safe loaded infected column near the World Heart" : "WAITING: World Heart and infected anchors are unloaded"
         );
         return false;
      } else {
         BlockPos center = ground.above();
         BlockPos altarPos = findNearestFundingAltar(domain, new ChunkPos(center), 0);
         if (altarPos == null) {
            ecology.setLastBuildStatus("WAITING: no registered Blood Altar");
            return false;
         } else {
            BlackBloodEcologyHandler.StructureType type = ecology.nextStructureType();
            int cost = remote ? Math.max(1, (Integer)cfg.ecologyRemoteAltarBloodCost.get()) : Math.max(1, (Integer)cfg.ecologyBuildBloodCost.get());
            ecology.queuePendingBuild(new BlackBloodEcologyHandler.PendingBuild(center, altarPos, type, remote, cost, 0, level.getGameTime()));
            ecology.markBuildQueued(level.getGameTime(), type, center);
            level.sendParticles(BLACK, center.getX() + 0.5, center.getY() + 0.2, center.getZ() + 0.5, 5, 0.35, 0.08, 0.35, 0.004);
            return true;
         }
      }
   }

   private static void processPendingBuilds(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      for (Entry<Long, BlackBloodEcologyHandler.PendingBuild> entry : ecology.nextPendingBuilds(2)) {
         long id = entry.getKey();
         BlackBloodEcologyHandler.PendingBuild pending = ecology.getPendingBuild(id);
         if (pending != null) {
            BlockPos center = pending.center();
            BlockPos ground = center.below();
            boolean loaded = level.isLoaded(center);
            boolean valid = loaded
               && (
                  pending.remoteAltar()
                     ? isRemoteAltarSite(domain, ecology, ground, cfg) && canBuildAt(level, ground)
                     : !ecology.hasStructureNear(center, 24) && canBuildAt(level, ground)
               );
            if (!valid) {
               BlockPos replacement = pending.remoteAltar()
                  ? findRemoteAltarGround(level, domain, ecology, cfg)
                  : findStructureGround(level, domain, ecology, cfg);
               if (replacement == null && pending.remoteAltar()) {
                  replacement = findStructureGround(level, domain, ecology, cfg);
                  if (replacement != null) {
                     pending = new BlackBloodEcologyHandler.PendingBuild(
                        replacement.above(),
                        pending.sourceAltar(),
                        pending.type(),
                        false,
                        Math.max(1, (Integer)cfg.ecologyBuildBloodCost.get()),
                        pending.reservedBlood(),
                        pending.queuedAt()
                     );
                  }
               }

               if (replacement == null) {
                  ecology.setLastBuildStatus("WAITING: pending " + pending.type().name() + " has no loaded infected site");
                  continue;
               }

               center = replacement.above();
               if (!pending.center().equals(center)) {
                  pending = pending.withCenter(center);
               }

               ecology.setPendingBuild(id, pending);
            }

            BlockPos funding = pending.sourceAltar();
            BlackBloodDomainData.AltarData altar = domain.getAltar(funding);
            if (altar == null) {
               funding = findNearestFundingAltar(domain, new ChunkPos(center), 0);
               if (funding == null) {
                  ecology.setLastBuildStatus("WAITING: " + pending.type().name() + " has no registered altar");
                  continue;
               }

               pending = pending.withSourceAltar(funding);
               ecology.setPendingBuild(id, pending);
               altar = domain.getAltar(funding);
            }

            int needed = Math.max(0, pending.cost() - pending.reservedBlood());
            if (needed > 0 && altar != null && altar.fuelBlood() > 0) {
               int reservedNow = Math.min(needed, altar.fuelBlood());
               if (consumeAltarFuel(domain, funding, reservedNow)) {
                  pending = pending.withReservedBlood(pending.reservedBlood() + reservedNow);
                  ecology.setPendingBuild(id, pending);
                  altar = domain.getAltar(funding);
               }
            }

            if (pending.reservedBlood() < pending.cost()) {
               ecology.setLastBuildStatus("RESERVING: " + pending.type().name() + " " + pending.reservedBlood() + "/" + pending.cost());
            } else {
               boolean formed;
               if (pending.remoteAltar()) {
                  formed = placeRemoteAltar(level, domain, ecology, center, funding, altar.owner(), pending.cost(), cfg);
               } else {
                  formed = prepareStructureSite(level, center) && placeStructure(level, domain, center, funding, altar.owner(), pending.type(), cfg);
                  if (formed) {
                     ecology.addStructure(center, new BlackBloodEcologyHandler.StructureData(funding, pending.type(), 0, 0L, level.getGameTime(), 0));
                     ecology.seedVirtual(ChunkPos.asLong(center), Math.max(2, (Integer)cfg.ecologyVirtualClaimSteps.get()));
                  }
               }

               if (!formed) {
                  ecology.setLastBuildStatus("RETRYING: funded " + pending.type().name() + " terrain blocked at " + center.toShortString());
                  BlockPos replacement = findStructureGround(level, domain, ecology, cfg);
                  if (replacement != null) {
                     ecology.setPendingBuild(id, pending.withCenter(replacement.above()));
                  }
               } else {
                  ecology.removePendingBuild(id);
                  ecology.markBuildSuccess(level.getGameTime(), pending.remoteAltar() ? null : pending.type(), center, pending.remoteAltar());
                  level.playSound(
                     null,
                     center,
                     SoundEvents.WITHER_SPAWN,
                     SoundSource.BLOCKS,
                     0.65F,
                     pending.remoteAltar() ? 0.34F : 0.45F + pending.type().ordinal() * 0.025F
                  );
                  level.sendParticles(RED, center.getX() + 0.5, center.getY() + 1.5, center.getZ() + 0.5, pending.remoteAltar() ? 48 : 28, 1.8, 1.4, 1.8, 0.025);
               }
            }
         }
      }
   }

   private static BlockPos findStructureGround(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      List<BlockPos> centers = collectLoadedInfectionCenters(level, domain, 1, 48);
      if (centers.isEmpty()) {
         return null;
      }

      int startCenter = Math.floorMod(ecology.siteSourceCursor(), centers.size());
      int sampled = 0;

      for (int centerOffset = 0; centerOffset < centers.size() && sampled < 384; centerOffset++) {
         BlockPos source = centers.get(Math.floorMod(startCenter + centerOffset, centers.size()));
         ChunkPos sourceChunk = new ChunkPos(source);
         int startRing = Math.floorMod(ecology.siteSourceCursor() + centerOffset, 7);

         for (int ringOffset = 0; ringOffset <= 6 && sampled < 384; ringOffset++) {
            int ring = Math.floorMod(startRing + ringOffset, 7);
            int diameter = ring * 2 + 1;
            int perimeter = ring == 0 ? 1 : diameter * 4 - 4;
            int startEdge = Math.floorMod(ecology.siteSourceCursor() * 7 + centerOffset * 13 + ring * 5, perimeter);

            for (int edgeOffset = 0; edgeOffset < perimeter && sampled < 384; edgeOffset++) {
               int edge = Math.floorMod(startEdge + edgeOffset, perimeter);
               int[] offset = chunkRingOffset(edge, ring);
               int chunkX = sourceChunk.x + offset[0];
               int chunkZ = sourceChunk.z + offset[1];
               if (level.hasChunk(chunkX, chunkZ)) {
                  BlockPos chunkSample = new BlockPos((chunkX << 4) + 8, level.getSeaLevel(), (chunkZ << 4) + 8);
                  if (domain.getChunkValue(chunkSample) >= 1) {
                     int localStart = Math.floorMod(ecology.siteSourceCursor() + chunkX * 31 + chunkZ * 17, 16);

                     for (int local = 0; local < 8 && sampled < 384; local++) {
                        int localX = Math.floorMod(localStart + local * 5, 16);
                        int localZ = Math.floorMod(localStart * 3 + local * 7, 16);
                        BlockPos ground = surfaceGround(level, (chunkX << 4) + localX, (chunkZ << 4) + localZ);
                        sampled++;
                        if (ground != null && isStructureSiteAvailable(level, ecology, ground)) {
                           ecology.setSiteSourceCursor(safeAdd(ecology.siteSourceCursor(), sampled + 1));
                           return ground;
                        }
                     }
                  }
               }
            }
         }
      }

      for (BlockPos source : centers) {
         BlockPos emergency = findEmergencyStructureGround(level, domain, ecology, source);
         if (emergency != null) {
            ecology.setSiteSourceCursor(safeAdd(ecology.siteSourceCursor(), sampled + 1));
            return emergency;
         }
      }

      ecology.setSiteSourceCursor(safeAdd(ecology.siteSourceCursor(), Math.max(1, sampled)));
      return null;
   }

   private static int[] chunkRingOffset(int index, int ring) {
      if (ring <= 0) {
         return new int[]{0, 0};
      } else {
         int side = ring * 2;
         int perimeter = side * 4;
         int value = Math.floorMod(index, perimeter);
         if (value < side) {
            return new int[]{-ring + value, -ring};
         } else if (value < side * 2) {
            return new int[]{ring, -ring + value - side};
         } else {
            return value < side * 3 ? new int[]{ring - (value - side * 2), ring} : new int[]{-ring, ring - (value - side * 3)};
         }
      }
   }

   private static boolean isStructureSiteAvailable(ServerLevel level, BlackBloodEcologyHandler.EcologyData ecology, BlockPos ground) {
      return level.isLoaded(ground)
         && !ecology.hasStructureNear(ground.above(), 24)
         && !ecology.hasPendingBuildNear(ground.above(), 24)
         && canBuildAt(level, ground);
   }

   private static BlockPos findEmergencyStructureGround(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, BlockPos source
   ) {
      ChunkPos chunk = new ChunkPos(source);
      if (level.hasChunk(chunk.x, chunk.z) && domain.getChunkValue(source) >= 1) {
         int start = Math.floorMod(ecology.siteSourceCursor() + chunk.x * 19 + chunk.z * 23, 64);

         for (int i = 0; i < 64; i++) {
            int value = Math.floorMod(start + i * 29, 256);
            int localX = value & 15;
            int localZ = value >> 4;
            BlockPos ground = surfaceGround(level, chunk.getMinBlockX() + localX, chunk.getMinBlockZ() + localZ);
            if (ground != null && isStructureSiteAvailable(level, ecology, ground)) {
               return ground;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static BlockPos findRemoteAltarGround(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int required = Math.max(1, (Integer)cfg.ecologyRemoteAltarRequiredChunkValue.get());
      int minDistance = Math.max(128, (Integer)cfg.ecologyRemoteAltarMinDistance.get());

      for (BlockPos source : collectLoadedInfectionCenters(level, domain, required, 24)) {
         if (!domain.hasAltarNear(source, minDistance) && !ecology.hasPendingRemoteAltarNear(source, minDistance)) {
            for (int attempt = 0; attempt < 8; attempt++) {
               int radius = level.random.nextInt(25);
               double angle = level.random.nextDouble() * Math.PI * 2.0;
               int x = Mth.floor(source.getX() + Math.cos(angle) * radius);
               int z = Mth.floor(source.getZ() + Math.sin(angle) * radius);
               BlockPos ground = surfaceGround(level, x, z);
               if (ground != null && isRemoteAltarSite(domain, ecology, ground, cfg) && canBuildAt(level, ground)) {
                  return ground;
               }
            }
         }
      }

      return null;
   }

   private static boolean isRemoteAltarSite(
      BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, BlockPos ground, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int required = Math.max(1, (Integer)cfg.ecologyRemoteAltarRequiredChunkValue.get());
      int minDistance = Math.max(128, (Integer)cfg.ecologyRemoteAltarMinDistance.get());
      return domain.getChunkValue(ground) >= required && !domain.hasAltarNear(ground, minDistance) && !ecology.hasPendingRemoteAltarNear(ground, minDistance);
   }

   private static List<BlockPos> collectLoadedInfectionCenters(ServerLevel level, BlackBloodDomainData domain, int requiredChunkValue, int max) {
      List<BlockPos> centers = new ArrayList<>();
      Set<Long> chunks = new HashSet<>();
      BlockPos heart = BlackBloodConquestData.get(level).worldHeartPos();
      if (heart != null && level.isLoaded(heart)) {
         addLoadedInfectionCenter(level, domain, heart, requiredChunkValue, centers, chunks, max, true);
         ChunkPos heartChunk = new ChunkPos(heart);

         for (int ring = 0; ring <= 4 && centers.size() < max; ring++) {
            int perimeter = ring == 0 ? 1 : ring * 8;

            for (int i = 0; i < perimeter && centers.size() < max; i++) {
               int[] offset = chunkRingOffset(i, ring);
               int chunkX = heartChunk.x + offset[0];
               int chunkZ = heartChunk.z + offset[1];
               if (level.hasChunk(chunkX, chunkZ)) {
                  BlockPos sample = new BlockPos((chunkX << 4) + 8, level.getSeaLevel(), (chunkZ << 4) + 8);
                  addLoadedInfectionCenter(level, domain, sample, requiredChunkValue, centers, chunks, max, false);
               }
            }
         }
      }

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : domain.altars()) {
         if (centers.size() >= max) {
            return centers;
         }

         addLoadedInfectionCenter(level, domain, entry.getKey(), requiredChunkValue, centers, chunks, max, false);
      }

      for (Entry<BlockPos, BlackBloodEcologyHandler.StructureData> entry : BlackBloodEcologyHandler.EcologyData.get(level).structureEntries()) {
         if (centers.size() >= max) {
            return centers;
         }

         addLoadedInfectionCenter(level, domain, entry.getKey(), requiredChunkValue, centers, chunks, max, false);
      }

      int checked = 0;

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : domain.nodes()) {
         if (checked++ >= 512 || centers.size() >= max) {
            break;
         }

         BlockPos pos = entry.getKey();
         BlackBloodDomainData.NodeData node = entry.getValue();
         if (node != null && domain.getAltar(node.altarPos()) != null) {
            addLoadedInfectionCenter(level, domain, pos, requiredChunkValue, centers, chunks, max, false);
         }
      }

      for (ServerPlayer player : level.players()) {
         if (centers.size() >= max) {
            break;
         }

         addLoadedInfectionCenter(level, domain, player.blockPosition(), requiredChunkValue, centers, chunks, max, false);
      }

      return centers;
   }

   private static void addLoadedInfectionCenter(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlockPos pos,
      int requiredChunkValue,
      List<BlockPos> centers,
      Set<Long> chunks,
      int max,
      boolean heartAnchor
   ) {
      if (centers.size() < max && level.isLoaded(pos)) {
         long chunk = ChunkPos.asLong(pos);
         if (!chunks.contains(chunk)) {
            if (heartAnchor || domain.getChunkValue(pos) >= Math.max(1, requiredChunkValue)) {
               ChunkPos chunkPos = new ChunkPos(pos);
               BlockPos center = new BlockPos(chunkPos.getMiddleBlockX(), pos.getY(), chunkPos.getMiddleBlockZ());
               chunks.add(chunk);
               centers.add(center);
            }
         }
      }
   }

   private static BlockPos surfaceGround(ServerLevel level, int x, int z) {
      int chunkX = x >> 4;
      int chunkZ = z >> 4;
      if (!level.hasChunk(chunkX, chunkZ)) {
         return null;
      }

      int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;
      BlockPos ground = new BlockPos(x, y, z);
      return level.isLoaded(ground) ? ground : null;
   }

   private static boolean canBuildAt(ServerLevel level, BlockPos ground) {
      if (!level.isLoaded(ground)) {
         return false;
      }

      BlockState groundState = level.getBlockState(ground);
      if (!isProtected(level, ground, groundState) && groundState.isFaceSturdy(level, ground, Direction.UP)) {
         int[][] samples = new int[][]{{0, 0}, {3, 0}, {-3, 0}, {0, 3}, {0, -3}, {3, 3}, {3, -3}, {-3, 3}, {-3, -3}};

         for (int[] sample : samples) {
            for (int y = 1; y <= 10; y++) {
               BlockPos pos = ground.offset(sample[0], y, sample[1]);
               if (!level.isLoaded(pos)) {
                  return false;
               }

               BlockState state = level.getBlockState(pos);
               if (state.hasBlockEntity()
                  || state.is((Block)ModBlocks.BLOOD_ALTAR.get())
                  || state.is((Block)ModBlocks.BLOOD_WORLD_HEART_CORE.get())
                  || state.is((Block)ModBlocks.BLOOD_FLOWER_CORE.get())
                  || state.getDestroySpeed(level, pos) < 0.0F) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean prepareStructureSite(ServerLevel level, BlockPos center) {
      if (level.isLoaded(center) && level.isLoaded(center.below())) {
         for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
               for (int y = 0; y <= 11; y++) {
                  BlockPos pos = center.offset(x, y, z);
                  BlockState state = level.getBlockState(pos);
                  if (!state.isAir()) {
                     if (state.hasBlockEntity()
                        || state.is((Block)ModBlocks.BLOOD_ALTAR.get())
                        || state.is((Block)ModBlocks.BLOOD_WORLD_HEART_CORE.get())
                        || state.is((Block)ModBlocks.BLOOD_FLOWER_CORE.get())
                        || state.getDestroySpeed(level, pos) < 0.0F) {
                        return false;
                     }

                     if (state.canBeReplaced()
                        || state.is(BlockTags.LEAVES)
                        || state.is(BlockTags.LOGS)
                        || state.is((Block)ModBlocks.BLOOD_FLOWER_ROOT.get())
                        || state.is((Block)ModBlocks.BLOOD_FLOWER_PETAL.get())) {
                        level.removeBlock(pos, false);
                     }
                  }
               }
            }
         }

         BlockPos ground = center.below();
         BlockState groundState = level.getBlockState(ground);
         if (groundState.isAir() || !groundState.isFaceSturdy(level, ground, Direction.UP)) {
            level.setBlock(ground, ((Block)ModBlocks.INFECTED_DIRT.get()).defaultBlockState(), 3);
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean placeRemoteAltar(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos center,
      BlockPos sourceAltar,
      UUID owner,
      int paidCost,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (level.isLoaded(center) && isRemoteAltarSite(domain, ecology, center.below(), cfg)) {
         BlockState old = level.getBlockState(center);
         if (!old.isAir() && !old.canBeReplaced()) {
            return false;
         } else if (!level.setBlock(center, ((Block)ModBlocks.BLOOD_ALTAR.get()).defaultBlockState(), 3)) {
            return false;
         } else if (!domain.addAltar(center, owner)) {
            level.removeBlock(center, false);
            return false;
         } else {
            int seedFuel = Math.min(Math.max(0, (Integer)cfg.ecologyRemoteAltarSeedFuel.get()), Math.max(0, paidCost));
            int levelValue = getLocalAltarLevel(seedFuel, cfg);
            domain.setAltar(center, new BlackBloodDomainData.AltarData(owner, seedFuel, seedFuel, levelValue, seedFuel, 0));
            domain.addNode(center, owner, center, 1);
            domain.addChunkValue(
               center,
               Math.max((Integer)cfg.domainBiomeChunkValueRequired.get(), (Integer)cfg.ecologyRemoteAltarRequiredChunkValue.get()),
               (Integer)cfg.domainChunkMaxValue.get()
            );
            ecology.seedVirtual(ChunkPos.asLong(center), Math.max(2, (Integer)cfg.ecologyVirtualClaimSteps.get()));
            BlackBloodDomainHandler.trySlowNaturalSpread(level, center, 2);
            return true;
         }
      } else {
         return false;
      }
   }

   private static int getLocalAltarLevel(int blood, MoreskillsRaceConfig.BlackCommunion cfg) {
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

   private static boolean placeStructure(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlockPos center,
      BlockPos altarPos,
      UUID owner,
      BlackBloodEcologyHandler.StructureType type,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int placed = switch (type) {
         case VEIN_SPIRE -> placeVeinSpire(level, center);
         case CONVERSION_NEST -> placeConversionNest(level, center);
         case BLOOD_RESERVOIR -> placeBloodReservoir(level, center);
         case FUSION_PIT -> placeFusionPit(level, center);
         case SUMMONER_BLOOM -> placeSummonerBloom(level, center);
         case ROOT_RELAY -> placeRootRelay(level, center);
         case BLOOD_SANCTUARY -> placeBloodSanctuary(level, center);
         case HUNTING_BEACON -> placeHuntingBeacon(level, center);
         case HEMATIC_EXTRACTOR -> placeHematicExtractor(level, center);
         case CARRION_ORCHARD -> placeCarrionOrchard(level, center);
         case MARROW_PUMP -> placeMarrowPump(level, center);
         case SANGUINE_COMPOSTER -> placeSanguineComposter(level, center);
         case BLOOD_TURBINE -> placeBloodTurbine(level, center);
         case SCARLET_LATTICE -> placeScarletLattice(level, center);
         case PREDATOR_GARDEN -> placePredatorGarden(level, center);
         case CONQUEST_MONOLITH -> placeConquestMonolith(level, center);
      };
      if (placed < 8) {
         return false;
      }

      domain.addNode(center, owner, altarPos, 1);
      domain.addChunkValue(center, Math.max(1, placed * (Integer)cfg.domainChunkValuePerBlock.get()), (Integer)cfg.domainChunkMaxValue.get());
      return true;
   }

   private static int placeVeinSpire(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 2);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int y = 1; y < 7; y++) {
         placed += placeIfReplaceable(level, center.above(y), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
      }

      for (Direction direction : Plane.HORIZONTAL) {
         placed += placeIfReplaceable(level, center.above(4).relative(direction), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
         placed += placeIfReplaceable(level, center.above(2).relative(direction, 2), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeConversionNest(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 3);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int x = -3; x <= 3; x++) {
         for (int z = -3; z <= 3; z++) {
            int distance = Math.abs(x) + Math.abs(z);
            if (distance == 3 || distance == 4) {
               placed += placeIfReplaceable(
                  level,
                  center.offset(x, distance == 3 ? 0 : 1, z),
                  distance == 3
                     ? ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
                     : ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState()
               );
            }
         }
      }

      for (Direction direction : Plane.HORIZONTAL) {
         placed += placeIfReplaceable(level, center.relative(direction, 2).above(), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeBloodReservoir(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 3);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int x = -2; x <= 2; x++) {
         for (int z = -2; z <= 2; z++) {
            if (Math.max(Math.abs(x), Math.abs(z)) == 2) {
               placed += placeIfReplaceable(level, center.offset(x, 0, z), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
            }
         }
      }

      int[][] corners = new int[][]{{-2, -2}, {-2, 2}, {2, -2}, {2, 2}};

      for (int[] corner : corners) {
         placed += placeIfReplaceable(level, center.offset(corner[0], 1, corner[1]), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
         placed += placeIfReplaceable(level, center.offset(corner[0], 2, corner[1]), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeFusionPit(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 4);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int x = -3; x <= 3; x++) {
         for (int z = -3; z <= 3; z++) {
            double distance = Math.sqrt(x * x + z * z);
            if (distance >= 2.5 && distance <= 3.5) {
               placed += placeIfReplaceable(level, center.offset(x, 0, z), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
               if ((x + z & 1) == 0) {
                  placed += placeIfReplaceable(level, center.offset(x, 1, z), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
               }
            }
         }
      }

      return placed;
   }

   private static int placeSummonerBloom(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 3);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int y = 1; y <= 3; y++) {
         placed += placeIfReplaceable(level, center.above(y), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
      }

      for (Direction direction : Plane.HORIZONTAL) {
         placed += placeIfReplaceable(level, center.above(2).relative(direction, 2), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
         placed += placeIfReplaceable(level, center.relative(direction, 3), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeRootRelay(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 2);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int y = 1; y <= 8; y++) {
         placed += placeIfReplaceable(
            level,
            center.above(y),
            y % 3 == 0 ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState() : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
         );
      }

      for (Direction direction : Plane.HORIZONTAL) {
         placed += placeIfReplaceable(level, center.above(6).relative(direction, 2), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeBloodSanctuary(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 4);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int x = -4; x <= 4; x++) {
         for (int z = -4; z <= 4; z++) {
            double distance = Math.sqrt(x * x + z * z);
            if (distance >= 3.4 && distance <= 4.5) {
               placed += placeIfReplaceable(
                  level, center.offset(x, (x + z & 1) == 0 ? 1 : 0, z), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState()
               );
            }
         }
      }

      return placed;
   }

   private static int placeHuntingBeacon(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 2);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int y = 1; y <= 6; y++) {
         placed += placeIfReplaceable(level, center.above(y), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
      }

      for (Direction direction : Plane.HORIZONTAL) {
         placed += placeIfReplaceable(level, center.above(6).relative(direction), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
         placed += placeIfReplaceable(level, center.above(5).relative(direction, 2), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeHematicExtractor(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 3);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int y = 1; y <= 5; y++) {
         placed += placeIfReplaceable(
            level,
            center.above(y),
            y == 3 ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState() : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
         );
      }

      for (Direction direction : Plane.HORIZONTAL) {
         for (int i = 1; i <= 3; i++) {
            placed += placeIfReplaceable(
               level, center.relative(direction, i).below(i == 3 ? 1 : 0), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
            );
         }

         placed += placeIfReplaceable(level, center.above(4).relative(direction, 2), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeCarrionOrchard(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 4);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());
      int[][] trunks = new int[][]{{-3, -3}, {-3, 3}, {3, -3}, {3, 3}};

      for (int[] trunk : trunks) {
         for (int y = 0; y <= 3; y++) {
            placed += placeIfReplaceable(level, center.offset(trunk[0], y, trunk[1]), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
         }

         for (Direction direction : Plane.HORIZONTAL) {
            placed += placeIfReplaceable(
               level, center.offset(trunk[0], 3, trunk[1]).relative(direction), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState()
            );
         }
      }

      return placed;
   }

   private static int placeMarrowPump(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 3);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int y = 1; y <= 9; y++) {
         placed += placeIfReplaceable(
            level,
            center.above(y),
            y % 4 == 0 ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState() : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
         );
      }

      for (Direction direction : Plane.HORIZONTAL) {
         placed += placeIfReplaceable(level, center.above(2).relative(direction, 2), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
         placed += placeIfReplaceable(level, center.above(7).relative(direction, 2), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeSanguineComposter(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 3);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int x = -2; x <= 2; x++) {
         for (int z = -2; z <= 2; z++) {
            if (Math.max(Math.abs(x), Math.abs(z)) == 2) {
               placed += placeIfReplaceable(level, center.offset(x, 0, z), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
               if ((x + z & 1) == 0) {
                  placed += placeIfReplaceable(level, center.offset(x, 1, z), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
               }
            }
         }
      }

      for (Direction direction : Plane.HORIZONTAL) {
         placed += placeIfReplaceable(level, center.relative(direction, 3).below(), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeBloodTurbine(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 3);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int y = 1; y <= 7; y++) {
         placed += placeIfReplaceable(
            level,
            center.above(y),
            y == 4 ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState() : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
         );
      }

      for (Direction direction : Plane.HORIZONTAL) {
         for (int i = 1; i <= 3; i++) {
            placed += placeIfReplaceable(
               level,
               center.above(5).relative(direction, i),
               i == 3 ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState() : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
            );
         }
      }

      return placed;
   }

   private static int placeScarletLattice(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 5);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int x = -5; x <= 5; x++) {
         for (int z = -5; z <= 5; z++) {
            if (x == 0 || z == 0 || Math.abs(x) == Math.abs(z)) {
               int distance = Math.max(Math.abs(x), Math.abs(z));
               if (distance > 0 && distance <= 5) {
                  placed += placeIfReplaceable(
                     level,
                     center.offset(x, distance % 3 == 0 ? 1 : 0, z),
                     distance == 5
                        ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState()
                        : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
                  );
               }
            }
         }
      }

      return placed;
   }

   private static int placePredatorGarden(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 4);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (Direction direction : Plane.HORIZONTAL) {
         BlockPos stalk = center.relative(direction, 3);

         for (int y = 0; y <= 4; y++) {
            placed += placeIfReplaceable(
               level,
               stalk.above(y),
               y == 4 ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState() : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
            );
         }

         placed += placeIfReplaceable(level, stalk.above(3).relative(direction.getOpposite()), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
         placed += placeIfReplaceable(level, stalk.above(3).relative(direction.getClockWise()), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
         placed += placeIfReplaceable(
            level, stalk.above(3).relative(direction.getCounterClockWise()), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState()
         );
      }

      return placed;
   }

   private static int placeConquestMonolith(ServerLevel level, BlockPos center) {
      int placed = placeGroundPatch(level, center.below(), 3);
      placed += placeIfReplaceable(level, center, ((Block)ModBlocks.BLOOD_FLOWER_CORE.get()).defaultBlockState());

      for (int y = 1; y <= 11; y++) {
         placed += placeIfReplaceable(
            level,
            center.above(y),
            y % 3 == 0 ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState() : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
         );
      }

      for (Direction direction : Plane.HORIZONTAL) {
         for (int i = 1; i <= 4; i++) {
            placed += placeIfReplaceable(
               level, center.relative(direction, i).below(Math.min(2, i / 2)), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
            );
         }

         placed += placeIfReplaceable(level, center.above(9).relative(direction, 2), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
      }

      return placed;
   }

   private static int placeGroundPatch(ServerLevel level, BlockPos center, int radius) {
      int placed = 0;

      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            if (x * x + z * z <= radius * radius + 1) {
               BlockPos pos = center.offset(x, 0, z);
               BlockState state = level.getBlockState(pos);
               if (!isProtected(level, pos, state) && canReplaceNaturalGround(state)) {
                  level.setBlock(
                     pos,
                     state.is(Blocks.GRASS_BLOCK)
                        ? ((Block)ModBlocks.INFECTED_GRASS.get()).defaultBlockState()
                        : ((Block)ModBlocks.INFECTED_DIRT.get()).defaultBlockState(),
                     3
                  );
                  placed++;
               }
            }
         }
      }

      return placed;
   }

   private static int placeIfReplaceable(ServerLevel level, BlockPos pos, BlockState state) {
      if (!level.isLoaded(pos)) {
         return 0;
      }

      BlockState old = level.getBlockState(pos);
      if (!old.isAir() && !old.canBeReplaced()) {
         return 0;
      }

      level.setBlock(pos, state, 3);
      return 1;
   }

   private static void tickStructures(
      ServerLevel level, BlackBloodDomainData domain, BlackBloodEcologyHandler.EcologyData ecology, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      long now = level.getGameTime();
      double infectionPower = getInfectionPower(level, cfg);

      for (Entry<BlockPos, BlackBloodEcologyHandler.StructureData> entry : ecology.nextStructures(6)) {
         BlockPos pos = entry.getKey();
         BlackBloodEcologyHandler.StructureData structure = ecology.getStructure(pos);
         if (structure != null && level.isLoaded(pos)) {
            if (!level.getBlockState(pos).is((Block)ModBlocks.BLOOD_FLOWER_CORE.get())) {
               ecology.removeStructure(pos);
            } else {
               long elapsed = Math.max(1L, Math.min(24000L, now - structure.lastGameTime()));
               long oldAge = structure.age();
               long newAge = oldAge + elapsed;
               int targetEvolution = getStructureEvolutionTarget(newAge, infectionPower);
               if (targetEvolution > structure.evolution()) {
                  for (int stage = structure.evolution() + 1; stage <= targetEvolution; stage++) {
                     evolveStructureForm(level, pos, structure.type(), stage);
                  }

                  structure = structure.withEvolution(targetEvolution);
                  ecology.setStructure(pos, structure);
                  level.sendParticles(
                     BRIGHT_RED,
                     pos.getX() + 0.5,
                     pos.getY() + 2.0 + targetEvolution,
                     pos.getZ() + 0.5,
                     28 + targetEvolution * 16,
                     1.8 + targetEvolution,
                     1.4 + targetEvolution,
                     1.8 + targetEvolution,
                     0.035
                  );
                  level.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 0.75F, 0.52F + targetEvolution * 0.08F);
               }

               switch (structure.type()) {
                  case VEIN_SPIRE:
                     tickVeinSpire(level, pos, structure, oldAge, newAge, cfg);
                     break;
                  case CONVERSION_NEST:
                     tickConversionNest(level, pos, structure, oldAge, newAge, cfg);
                     break;
                  case BLOOD_RESERVOIR:
                     tickBloodReservoir(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case FUSION_PIT:
                     tickFusionPit(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case SUMMONER_BLOOM:
                     tickSummonerBloom(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case ROOT_RELAY:
                     tickRootRelay(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case BLOOD_SANCTUARY:
                     tickBloodSanctuary(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case HUNTING_BEACON:
                     tickHuntingBeacon(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case HEMATIC_EXTRACTOR:
                     tickHematicExtractor(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case CARRION_ORCHARD:
                     tickCarrionOrchard(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case MARROW_PUMP:
                     tickMarrowPump(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case SANGUINE_COMPOSTER:
                     tickSanguineComposter(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case BLOOD_TURBINE:
                     tickBloodTurbine(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case SCARLET_LATTICE:
                     tickScarletLattice(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case PREDATOR_GARDEN:
                     tickPredatorGarden(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
                     break;
                  case CONQUEST_MONOLITH:
                     tickConquestMonolith(level, domain, ecology, pos, structure, oldAge, newAge, cfg);
               }

               BlackBloodEcologyHandler.StructureData updated = ecology.getStructure(pos);
               if (updated != null) {
                  ecology.setStructure(
                     pos,
                     new BlackBloodEcologyHandler.StructureData(updated.altarPos(), updated.type(), updated.storedBlood(), newAge, now, updated.evolution())
                  );
               }

               spawnStructureVfx(level, pos, structure.type(), structure.evolution());
            }
         }
      }
   }

   private static int getStructureEvolutionTarget(long age, double infectionPower) {
      if (age >= 72000L && infectionPower >= 200.0) {
         return 2;
      } else {
         return age >= 24000L && infectionPower >= 50.0 ? 1 : 0;
      }
   }

   private static void tickVeinSpire(
      ServerLevel level, BlockPos pos, BlackBloodEcologyHandler.StructureData structure, long oldAge, long newAge, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int interval = Math.max(1200, (Integer)cfg.ecologySpireSpreadInterval.get());
      if (crossed(oldAge, newAge, interval)) {
         BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
         BlackBloodEcologyHandler.StructureData current = ecology.getStructure(pos);
         int cost = Math.max(1, (Integer)cfg.altarFuelPerBlock.get());
         if (current != null && consumeStructureOrAltarBlood(ecology, pos, BlackBloodDomainData.get(level), current.altarPos(), cost)) {
            BlackBloodDomainHandler.trySlowNaturalSpread(
               level, pos, Math.max(1, Math.min(4, (Integer)cfg.ecologySpireSpreadBlocks.get() + structure.evolution()))
            );
            level.sendParticles(BLACK, pos.getX() + 0.5, pos.getY() + 3.5, pos.getZ() + 0.5, 9, 0.45, 1.4, 0.45, 0.015);
         }
      }
   }

   private static void tickConversionNest(
      ServerLevel level, BlockPos pos, BlackBloodEcologyHandler.StructureData structure, long oldAge, long newAge, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int interval = Math.max(1200, (Integer)cfg.ecologyNestPulseInterval.get());
      if (crossed(oldAge, newAge, interval)) {
         int nestRadius = Math.min(64, Math.max(8, (Integer)cfg.ecologyNestRadius.get()) + structure.evolution() * 4);
         List<Mob> nearby = level.getEntitiesOfClass(Mob.class, new AABB(pos).inflate(nestRadius), mob -> mob.isAlive() && !isBloodFaction(mob));
         Mob target = null;
         double best = Double.MAX_VALUE;
         int checked = 0;

         for (Mob mob : nearby) {
            if (checked++ >= 20) {
               break;
            }

            double distance = mob.distanceToSqr(
               BlackBloodEcologyHandler.VecCenter.x(pos), BlackBloodEcologyHandler.VecCenter.y(pos), BlackBloodEcologyHandler.VecCenter.z(pos)
            );
            if (distance < best) {
               best = distance;
               target = mob;
            }
         }

         if (target != null) {
            BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
            BlackBloodEcologyHandler.StructureData current = ecology.getStructure(pos);
            if (current != null && consumeStructureOrAltarBlood(ecology, pos, BlackBloodDomainData.get(level), current.altarPos(), 1)) {
               addCorruption(target, 1 + structure.evolution(), level.getGameTime());
               float damage = Math.max(0.25F, ((Double)cfg.ecologyNestPulseDamage.get()).floatValue()) * (1.0F + structure.evolution() * 0.75F);
               target.hurt(TensuraDamageTypes.getDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC), damage);
               if (target.isAlive()) {
                  tryConvertMarkedMob(level, target, cfg);
               }

               level.sendParticles(
                  RED,
                  target.getX(),
                  target.getY() + target.getBbHeight() * 0.5,
                  target.getZ(),
                  6,
                  target.getBbWidth() * 0.4,
                  target.getBbHeight() * 0.3,
                  target.getBbWidth() * 0.4,
                  0.01
               );
            }
         }
      }
   }

   private static void tickBloodReservoir(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int interval = Math.max(1200, (Integer)cfg.ecologyReservoirFeedInterval.get());
      if (crossed(oldAge, newAge, interval)) {
         int feedLimit = Math.max(1, (Integer)cfg.ecologyReservoirFeedAmount.get()) * (1 + structure.evolution());
         int pulled = ecology.pullBloodFromNearbyStructures(pos, Math.max(32, (Integer)cfg.ecologyBloodCollectionRadius.get()), feedLimit);
         BlackBloodEcologyHandler.StructureData current = ecology.getStructure(pos);
         if (current != null) {
            int available = Math.min(feedLimit, current.storedBlood() + pulled);
            if (available > 0) {
               int accepted = addAltarFuel(domain, current.altarPos(), available, cfg);
               if (accepted > 0) {
                  int nextStored = Math.max(0, current.storedBlood() + pulled - accepted);
                  ecology.setStructure(pos, current.withStoredBlood(nextStored));
                  level.sendParticles(RED, pos.getX() + 0.5, pos.getY() + 1.3, pos.getZ() + 0.5, 10, 0.7, 0.6, 0.7, 0.02);
                  level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 0.4F, 0.55F);
               }
            }
         }
      }
   }

   private static void tickFusionPit(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int interval = Math.max(2400, (Integer)cfg.ecologyFusionInterval.get());
      if (crossed(oldAge, newAge, interval)) {
         int radius = Math.max(6, (Integer)cfg.ecologyFusionRadius.get());
         List<Mob> mobs = level.getEntitiesOfClass(
            Mob.class,
            new AABB(pos).inflate(radius),
            mob -> mob.isAlive() && isConverted(mob) && getFusionTier(mob) < Math.max(2, (Integer)cfg.ecologyMaxFusionTier.get())
         );
         if (mobs.size() >= 3) {
            for (int tier = 1; tier < Math.max(2, (Integer)cfg.ecologyMaxFusionTier.get()); tier++) {
               List<Mob> sameTier = new ArrayList<>();

               for (Mob mob : mobs) {
                  if (getFusionTier(mob) == tier) {
                     sameTier.add(mob);
                     if (sameTier.size() >= 3) {
                        break;
                     }
                  }
               }

               if (sameTier.size() >= 3) {
                  BlackBloodEcologyHandler.StructureData current = ecology.getStructure(pos);
                  if (current == null) {
                     return;
                  }

                  int cost = Math.max(1, (Integer)cfg.ecologyFusionBloodCost.get());
                  if (!consumeStructureOrAltarBlood(ecology, pos, domain, current.altarPos(), cost)) {
                     return;
                  }

                  fuse(level, sameTier.get(0), sameTier.get(1), sameTier.get(2), tier + 1, cfg);
                  return;
               }
            }
         }
      }
   }

   private static void tickSummonerBloom(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int interval = Math.max(1200, (Integer)cfg.ecologySummonerInterval.get());
      if (crossed(oldAge, newAge, interval)) {
         int radius = Math.max(12, (Integer)cfg.ecologyNestRadius.get() * 2);
         int active = level.getEntitiesOfClass(Mob.class, new AABB(pos).inflate(radius), BlackBloodEcologyHandler::isConvertedBloodMinion).size();
         if (active < Math.max(1, (Integer)cfg.ecologySummonerCap.get())) {
            int cost = Math.max(1, (Integer)cfg.ecologySummonerBloodCost.get());
            if (consumeStructureOrAltarBlood(ecology, pos, domain, structure.altarPos(), cost)) {
               Mob summoned = (Mob)(switch (level.random.nextInt(3)) {
                  case 0 -> (Zombie)EntityType.ZOMBIE.create(level);
                  case 1 -> (Skeleton)EntityType.SKELETON.create(level);
                  default -> (Spider)EntityType.SPIDER.create(level);
               });
               if (summoned == null) {
                  refundStructureOrAltarBlood(ecology, pos, domain, structure.altarPos(), cost, cfg);
               } else {
                  double angle = level.random.nextDouble() * Math.PI * 2.0;
                  double distance = 2.0 + level.random.nextDouble() * 2.5;
                  summoned.moveTo(
                     pos.getX() + 0.5 + Math.cos(angle) * distance,
                     pos.getY() + 1.0,
                     pos.getZ() + 0.5 + Math.sin(angle) * distance,
                     level.random.nextFloat() * 360.0F,
                     0.0F
                  );
                  if (!level.addFreshEntity(summoned)) {
                     refundStructureOrAltarBlood(ecology, pos, domain, structure.altarPos(), cost, cfg);
                  } else {
                     convertMob(level, summoned, pos, structure.altarPos());
                     if (structure.evolution() > 0) {
                        ecologyTag(summoned).putInt("FusionTier", 1 + structure.evolution());
                        applyFusionAttributes(summoned);
                        summoned.setHealth(summoned.getMaxHealth());
                     }

                     level.sendParticles(RED, summoned.getX(), summoned.getY() + 0.8, summoned.getZ(), 18, 0.6, 0.7, 0.6, 0.025);
                  }
               }
            }
         }
      }
   }

   private static void tickRootRelay(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int interval = Math.max(600, (Integer)cfg.ecologyRelayInterval.get());
      if (crossed(oldAge, newAge, interval)) {
         int cost = Math.max(1, (Integer)cfg.ecologyRelayBloodCost.get());
         if (consumeStructureOrAltarBlood(ecology, pos, domain, structure.altarPos(), cost)) {
            ecology.seedVirtual(ChunkPos.asLong(pos), Math.max(2, (Integer)cfg.ecologyVirtualClaimSteps.get()));
            int steps = Math.min(8, Math.max(1, (Integer)cfg.ecologyRelaySteps.get()) + structure.evolution());
            boolean progressed = false;

            for (int i = 0; i < steps; i++) {
               progressed |= advanceOneVirtualFrontier(level, domain, ecology, cfg, structure.altarPos(), false);
            }

            if (!progressed) {
               refundStructureOrAltarBlood(ecology, pos, domain, structure.altarPos(), cost, cfg);
            } else {
               level.sendParticles(BLACK, pos.getX() + 0.5, pos.getY() + 5.0, pos.getZ() + 0.5, 14, 0.7, 2.1, 0.7, 0.018);
            }
         }
      }
   }

   private static void tickBloodSanctuary(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int interval = Math.max(100, (Integer)cfg.ecologySanctuaryInterval.get());
      if (crossed(oldAge, newAge, interval)) {
         int cost = 5;
         if (consumeStructureOrAltarBlood(ecology, pos, domain, structure.altarPos(), cost)) {
            int radius = Math.min(64, Math.max(6, (Integer)cfg.ecologySanctuaryRadius.get()) + structure.evolution() * 6);
            float heal = Math.max(0.0F, ((Double)cfg.ecologySanctuaryHeal.get()).floatValue()) * (1.0F + structure.evolution());
            int affected = 0;

            for (LivingEntity entity : level.getEntitiesOfClass(
               LivingEntity.class, new AABB(pos).inflate(radius), entityx -> entityx.isAlive() && isBloodFaction(entityx)
            )) {
               if (affected++ >= 24) {
                  break;
               }

               if (heal > 0.0F && entity.getHealth() < entity.getMaxHealth()) {
                  entity.heal(heal);
               }
            }

            if (affected <= 0) {
               refundStructureOrAltarBlood(ecology, pos, domain, structure.altarPos(), cost, cfg);
            } else {
               level.sendParticles(RED, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 8, 1.2, 0.5, 1.2, 0.01);
            }
         }
      }
   }

   private static void tickHuntingBeacon(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      int interval = Math.max(100, (Integer)cfg.ecologyHuntingBeaconInterval.get());
      if (crossed(oldAge, newAge, interval)) {
         int radius = Math.min(64, Math.max(8, (Integer)cfg.ecologyHuntingBeaconRadius.get()) + structure.evolution() * 8);
         List<Mob> enemies = level.getEntitiesOfClass(Mob.class, new AABB(pos).inflate(radius), mob -> mob.isAlive() && !isBloodFaction(mob));
         if (!enemies.isEmpty()) {
            Mob target = null;
            double best = Double.MAX_VALUE;
            int checked = 0;

            for (Mob enemy : enemies) {
               if (checked++ >= 20) {
                  break;
               }

               double distance = enemy.distanceToSqr(
                  BlackBloodEcologyHandler.VecCenter.x(pos), BlackBloodEcologyHandler.VecCenter.y(pos), BlackBloodEcologyHandler.VecCenter.z(pos)
               );
               if (distance < best) {
                  best = distance;
                  target = enemy;
               }
            }

            if (target != null && consumeStructureOrAltarBlood(ecology, pos, domain, structure.altarPos(), 5)) {
               int assigned = 0;

               for (Mob minion : level.getEntitiesOfClass(Mob.class, new AABB(pos).inflate(radius), BlackBloodEcologyHandler::isConvertedBloodMinion)) {
                  if (assigned++ >= 8 + structure.evolution() * 4) {
                     break;
                  }

                  minion.setTarget(target);
                  minion.getNavigation().moveTo(target, Math.max(0.75, (Double)cfg.ecologyMinionMoveSpeed.get()));
               }

               if (assigned <= 0) {
                  refundStructureOrAltarBlood(ecology, pos, domain, structure.altarPos(), 5, cfg);
               }
            }
         }
      }
   }

   private static void tickHematicExtractor(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (crossed(oldAge, newAge, 6000L)) {
         int required = Math.max(1, (Integer)cfg.domainBiomeChunkValueRequired.get());
         int generated = 0;

         for (int chunkX = -1; chunkX <= 1; chunkX++) {
            for (int chunkZ = -1; chunkZ <= 1; chunkZ++) {
               BlockPos sample = pos.offset(chunkX * 16, 0, chunkZ * 16);
               int value = domain.getChunkValue(sample);
               int excess = Math.max(0, value - required * 2);
               if (excess > 0) {
                  int drained = Math.min(24, excess);
                  domain.subtractChunkValue(sample, drained);
                  generated += drained;
               }
            }
         }

         if (generated > 0) {
            generated *= 1 + structure.evolution();
            storeGeneratedStructureBlood(ecology, pos, generated, cfg);
            flushStructureBloodToAltar(domain, ecology, pos, structure.altarPos(), 240 * (1 + structure.evolution()), cfg);
            level.sendParticles(BLACK, pos.getX() + 0.5, pos.getY() + 2.8, pos.getZ() + 0.5, 18, 1.4, 0.9, 1.4, 0.02);
            level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 0.55F, 0.38F);
         }
      }
   }

   private static void tickCarrionOrchard(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (crossed(oldAge, newAge, 1200L)) {
         int moved = flushStructureBloodToAltar(domain, ecology, pos, structure.altarPos(), 240 * (1 + structure.evolution()), cfg);
         if (moved > 0) {
            level.sendParticles(RED, pos.getX() + 0.5, pos.getY() + 2.0, pos.getZ() + 0.5, 14, 2.1, 1.1, 2.1, 0.018);
            level.playSound(null, pos, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 0.55F, 0.48F);
         }
      }
   }

   private static void tickMarrowPump(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (crossed(oldAge, newAge, 1200L)) {
         int moved = flushStructureBloodToAltar(domain, ecology, pos, structure.altarPos(), 240 * (2 + structure.evolution()), cfg);
         if (moved > 0) {
            level.sendParticles(BRIGHT_RED, pos.getX() + 0.5, pos.getY() + 5.0, pos.getZ() + 0.5, 20, 0.8, 2.2, 0.8, 0.025);
            level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 0.65F, 0.42F);
         }
      }
   }

   private static void tickSanguineComposter(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (crossed(oldAge, newAge, 100L)) {
         int remaining = 12 + structure.evolution() * 6;
         int generated = 0;

         for (ItemEntity itemEntity : level.getEntitiesOfClass(
            ItemEntity.class, new AABB(pos).inflate(7.0), item -> item.isAlive() && !item.getItem().isEmpty()
         )) {
            if (remaining <= 0) {
               break;
            }

            ItemStack stack = itemEntity.getItem();
            int value = organicBloodValue(stack);
            if (value > 0) {
               int consumed = Math.min(remaining, stack.getCount());
               generated += consumed * value;
               remaining -= consumed;
               stack.shrink(consumed);
               if (stack.isEmpty()) {
                  itemEntity.discard();
               } else {
                  itemEntity.setItem(stack);
               }
            }
         }

         if (generated > 0) {
            storeGeneratedStructureBlood(ecology, pos, generated, cfg);
            flushStructureBloodToAltar(domain, ecology, pos, structure.altarPos(), 160 + structure.evolution() * 100, cfg);
            level.sendParticles(RED, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 12, 0.9, 0.35, 0.9, 0.018);
            level.playSound(null, pos, SoundEvents.COMPOSTER_FILL_SUCCESS, SoundSource.BLOCKS, 0.65F, 0.5F);
         }
      }
   }

   private static void tickBloodTurbine(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (crossed(oldAge, newAge, 1800L)) {
         int centerValue = domain.getChunkValue(pos);
         int saturation = 0;
         int gradient = 0;

         for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
               int value = domain.getChunkValue(pos.offset(x * 16, 0, z * 16));
               saturation += Math.min(1000, Math.max(0, value));
               gradient += Math.min(1000, Math.abs(centerValue - value));
            }
         }

         int generated = 12 + saturation / 250 + gradient / 500 + structure.evolution() * 22;
         if (level.isRaining()) {
            generated += 18 + structure.evolution() * 8;
         }

         generated = Mth.clamp(generated, 12, 180);
         storeGeneratedStructureBlood(ecology, pos, generated, cfg);
         flushStructureBloodToAltar(domain, ecology, pos, structure.altarPos(), 240 + structure.evolution() * 120, cfg);
         level.sendParticles(
            level.isRaining() ? BRIGHT_RED : RED, pos.getX() + 0.5, pos.getY() + 5.5, pos.getZ() + 0.5, 14 + structure.evolution() * 6, 2.0, 1.4, 2.0, 0.025
         );
      }
   }

   private static void tickScarletLattice(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (crossed(oldAge, newAge, 2400L)) {
         Set<BlackBloodEcologyHandler.StructureType> types = new HashSet<>();
         int evolved = 0;
         int checked = 0;
         int radius = 80 + structure.evolution() * 32;
         double radiusSqr = (double)radius * radius;

         for (Entry<BlockPos, BlackBloodEcologyHandler.StructureData> entry : ecology.structureEntries()) {
            if (checked++ >= 128) {
               break;
            }

            if (!(entry.getKey().distSqr(pos) > radiusSqr)) {
               types.add(entry.getValue().type());
               evolved += entry.getValue().evolution();
            }
         }

         int generated = types.size() * (6 + structure.evolution() * 2) + Math.min(60, evolved * 3);
         if (generated > 0) {
            storeGeneratedStructureBlood(ecology, pos, generated, cfg);
            flushStructureBloodToAltar(domain, ecology, pos, structure.altarPos(), 240 + structure.evolution() * 120, cfg);
            level.sendParticles(
               BRIGHT_RED,
               pos.getX() + 0.5,
               pos.getY() + 1.0,
               pos.getZ() + 0.5,
               12 + types.size(),
               3.0 + structure.evolution(),
               0.8,
               3.0 + structure.evolution(),
               0.018
            );
         }
      }
   }

   private static void tickPredatorGarden(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (crossed(oldAge, newAge, 2400L)) {
         int radius = 20 + structure.evolution() * 6;
         int limit = 4 + structure.evolution() * 2;
         float damage = 4.0F + structure.evolution() * 4.0F;
         int generated = 0;
         int attacked = 0;
         DamageSource source = TensuraDamageTypes.getDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC);

         for (Mob mob : level.getEntitiesOfClass(Mob.class, new AABB(pos).inflate(radius), mobx -> mobx.isAlive() && !isBloodFaction(mobx))) {
            if (attacked++ >= limit) {
               break;
            }

            float before = mob.getHealth();
            mob.hurt(source, damage);
            float dealt = Math.max(0.0F, before - mob.getHealth());
            generated += Math.max(1, Mth.ceil(dealt / 2.0F));
            if (!mob.isAlive()) {
               generated += 8 + structure.evolution() * 6;
            }

            level.sendParticles(
               BLACK,
               mob.getX(),
               mob.getY() + mob.getBbHeight() * 0.5,
               mob.getZ(),
               5,
               mob.getBbWidth() * 0.35,
               mob.getBbHeight() * 0.25,
               mob.getBbWidth() * 0.35,
               0.012
            );
         }

         if (generated > 0) {
            storeGeneratedStructureBlood(ecology, pos, Math.min(200, generated), cfg);
            flushStructureBloodToAltar(domain, ecology, pos, structure.altarPos(), 240 + structure.evolution() * 80, cfg);
            level.playSound(null, pos, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 0.6F, 0.38F + structure.evolution() * 0.08F);
         }
      }
   }

   private static void tickConquestMonolith(
      ServerLevel level,
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos pos,
      BlackBloodEcologyHandler.StructureData structure,
      long oldAge,
      long newAge,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (crossed(oldAge, newAge, 3600L)) {
         ecology.seedVirtual(ChunkPos.asLong(pos), Math.max(2, (Integer)cfg.ecologyVirtualClaimSteps.get()));
         int attempts = 1 + structure.evolution();
         int progressed = 0;

         for (int i = 0; i < attempts; i++) {
            if (advanceOneVirtualFrontier(level, domain, ecology, cfg, structure.altarPos(), false)) {
               progressed++;
            }
         }

         if (progressed > 0) {
            int generated = progressed * (30 + structure.evolution() * 15);
            storeGeneratedStructureBlood(ecology, pos, generated, cfg);
            flushStructureBloodToAltar(domain, ecology, pos, structure.altarPos(), 240 + structure.evolution() * 120, cfg);
            level.sendParticles(BLACK, pos.getX() + 0.5, pos.getY() + 8.0, pos.getZ() + 0.5, 20 + structure.evolution() * 10, 1.1, 3.4, 1.1, 0.025);
         }
      }
   }

   private static void evolveStructureForm(ServerLevel level, BlockPos center, BlackBloodEcologyHandler.StructureType type, int stage) {
      int safeStage = Mth.clamp(stage, 1, 2);
      switch (type) {
         case VEIN_SPIRE:
         case ROOT_RELAY:
         case HUNTING_BEACON:
         case HEMATIC_EXTRACTOR:
         case MARROW_PUMP:
         case BLOOD_TURBINE:
         case CONQUEST_MONOLITH:
            evolveTowerStructure(level, center, safeStage);
            break;
         case CONVERSION_NEST:
         case FUSION_PIT:
         case BLOOD_SANCTUARY:
         case SANGUINE_COMPOSTER:
         case PREDATOR_GARDEN:
            evolveRingStructure(level, center, safeStage);
            break;
         case BLOOD_RESERVOIR:
         case SCARLET_LATTICE:
            evolveNetworkStructure(level, center, safeStage);
            break;
         case SUMMONER_BLOOM:
         case CARRION_ORCHARD:
            evolveGroveStructure(level, center, safeStage);
      }
   }

   private static void evolveTowerStructure(ServerLevel level, BlockPos center, int stage) {
      int radius = 2 + stage;
      int height = 7 + stage * 3;

      for (Direction direction : Plane.HORIZONTAL) {
         BlockPos support = center.relative(direction, radius);

         for (int y = 0; y <= 2 + stage; y++) {
            placeIfReplaceable(level, support.above(y), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
         }

         placeIfReplaceable(level, support.above(3 + stage), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
         placeIfReplaceable(level, center.above(height).relative(direction, radius), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
      }

      for (int y = 1; y <= height; y++) {
         placeIfReplaceable(
            level,
            center.above(y),
            y % 4 == 0 ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState() : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
         );
      }
   }

   private static void evolveRingStructure(ServerLevel level, BlockPos center, int stage) {
      int radius = 5 + stage;

      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            double distance = Math.sqrt(x * x + z * z);
            if (distance >= radius - 0.7 && distance <= radius + 0.4) {
               int y = Math.floorMod(x + z, 3) == 0 ? stage : stage - 1;
               placeIfReplaceable(
                  level,
                  center.offset(x, y, z),
                  Math.floorMod(x * 31 + z * 17, 4) == 0
                     ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState()
                     : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
               );
            }
         }
      }
   }

   private static void evolveGroveStructure(ServerLevel level, BlockPos center, int stage) {
      int radius = 4 + stage * 2;
      Direction[] directions = new Direction[]{
         Direction.NORTH, Direction.NORTH, Direction.EAST, Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.WEST, Direction.WEST
      };
      int[][] offsets = new int[][]{
         {0, -radius}, {radius, -radius}, {radius, 0}, {radius, radius}, {0, radius}, {-radius, radius}, {-radius, 0}, {-radius, -radius}
      };

      for (int i = 0; i < offsets.length; i++) {
         BlockPos stalk = center.offset(offsets[i][0], 0, offsets[i][1]);
         int height = 3 + stage;

         for (int y = 0; y <= height; y++) {
            placeIfReplaceable(level, stalk.above(y), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState());
         }

         placeIfReplaceable(level, stalk.above(height).relative(directions[i]), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
         placeIfReplaceable(level, stalk.above(height).relative(directions[i].getClockWise()), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState());
         placeIfReplaceable(
            level, stalk.above(height).relative(directions[i].getCounterClockWise()), ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState()
         );
      }
   }

   private static void evolveNetworkStructure(ServerLevel level, BlockPos center, int stage) {
      int radius = 5 + stage * 2;

      for (Direction direction : Plane.HORIZONTAL) {
         for (int i = 1; i <= radius; i++) {
            placeIfReplaceable(
               level, center.relative(direction, i).above(i % 4 == 0 ? stage : 0), ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
            );
         }

         BlockPos pylon = center.relative(direction, radius);

         for (int y = 1; y <= 3 + stage; y++) {
            placeIfReplaceable(
               level,
               pylon.above(y),
               y == 3 + stage
                  ? ((Block)ModBlocks.BLOOD_FLOWER_PETAL.get()).defaultBlockState()
                  : ((Block)ModBlocks.BLOOD_FLOWER_ROOT.get()).defaultBlockState()
            );
         }
      }
   }

   private static int organicBloodValue(ItemStack stack) {
      if (stack.is(Items.ROTTEN_FLESH)) {
         return 4;
      } else if (stack.is(Items.BEEF) || stack.is(Items.PORKCHOP) || stack.is(Items.CHICKEN) || stack.is(Items.MUTTON) || stack.is(Items.RABBIT)) {
         return 7;
      } else if (stack.is(Items.COD) || stack.is(Items.SALMON) || stack.is(Items.TROPICAL_FISH) || stack.is(Items.PUFFERFISH)) {
         return 5;
      } else if (stack.is(Items.SPIDER_EYE) || stack.is(Items.FERMENTED_SPIDER_EYE)) {
         return 6;
      } else if (stack.is(TensuraMobDropItems.MONSTER_LEATHER_D) || stack.is(TensuraMobDropItems.MONSTER_LEATHER_C)) {
         return 6;
      } else if (stack.is(TensuraMobDropItems.MONSTER_LEATHER_B) || stack.is(TensuraMobDropItems.MONSTER_LEATHER_A)) {
         return 10;
      } else {
         return !stack.is(Items.BONE) && !stack.is(Items.BONE_MEAL) && !stack.is(Items.LEATHER) ? 0 : 3;
      }
   }

   private static void feedCarrionOrchard(ServerLevel level, LivingEntity killed, MoreskillsRaceConfig.BlackCommunion cfg) {
      BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
      BlockPos orchard = ecology.findNearestStructureOfType(killed.blockPosition(), 96, BlackBloodEcologyHandler.StructureType.CARRION_ORCHARD);
      if (orchard != null) {
         int generated = Mth.clamp(Mth.ceil(killed.getMaxHealth() / 6.0F), 2, 80);
         ecology.addStructureBlood(orchard, generated, Math.max(100, (Integer)cfg.ecologyStructureBloodCapacity.get()));
         level.sendParticles(
            BLACK,
            killed.getX(),
            killed.getY() + killed.getBbHeight() * 0.45,
            killed.getZ(),
            Math.min(12, 3 + generated / 8),
            killed.getBbWidth() * 0.4,
            killed.getBbHeight() * 0.25,
            killed.getBbWidth() * 0.4,
            0.012
         );
      }
   }

   public static void onWorldRootCompleted(ServerLevel level, BlockPos arrival, boolean baseTarget) {
      MoreskillsRaceConfig.BlackCommunion cfg = cfg();
      if (enabled(cfg) && BlackBloodConquestData.get(level).worldHeartPos() != null) {
         rewardMarrowPump(level, BlackBloodEcologyHandler.EcologyData.get(level), arrival, baseTarget ? 600 : 260);
      }
   }

   private static void rewardMarrowPump(ServerLevel level, BlackBloodEcologyHandler.EcologyData ecology, BlockPos origin, int amount) {
      BlockPos pump = ecology.findNearestStructureOfType(origin, 8192, BlackBloodEcologyHandler.StructureType.MARROW_PUMP);
      if (pump != null && amount > 0) {
         MoreskillsRaceConfig.BlackCommunion cfg = cfg();
         ecology.addStructureBlood(pump, amount, Math.max(100, (Integer)cfg.ecologyStructureBloodCapacity.get()));
         if (level.isLoaded(pump)) {
            level.sendParticles(BRIGHT_RED, pump.getX() + 0.5, pump.getY() + 4.5, pump.getZ() + 0.5, 12, 0.55, 1.5, 0.55, 0.018);
         }
      }
   }

   private static void storeGeneratedStructureBlood(
      BlackBloodEcologyHandler.EcologyData ecology, BlockPos pos, int amount, MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      ecology.addStructureBlood(pos, amount, Math.max(100, (Integer)cfg.ecologyStructureBloodCapacity.get()));
   }

   private static int flushStructureBloodToAltar(
      BlackBloodDomainData domain,
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos structurePos,
      BlockPos altarPos,
      int maxTransfer,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      BlackBloodEcologyHandler.StructureData current = ecology.getStructure(structurePos);
      if (current != null && current.storedBlood() > 0 && maxTransfer > 0) {
         int offered = Math.min(maxTransfer, current.storedBlood());
         int accepted = addAltarFuel(domain, altarPos, offered, cfg);
         if (accepted > 0) {
            ecology.setStructure(structurePos, current.withStoredBlood(current.storedBlood() - accepted));
         }

         return accepted;
      } else {
         return 0;
      }
   }

   private static void fuse(ServerLevel level, Mob first, Mob second, Mob third, int newTier, MoreskillsRaceConfig.BlackCommunion cfg) {
      Mob survivor = first;
      if (second.getMaxHealth() > survivor.getMaxHealth()) {
         survivor = second;
      }

      if (third.getMaxHealth() > survivor.getMaxHealth()) {
         survivor = third;
      }

      for (Mob mob : List.of(first, second, third)) {
         if (mob != survivor) {
            removeFromMinionTeam(level, mob);
            mob.discard();
         }
      }

      CompoundTag tag = ecologyTag(survivor);
      tag.putInt("FusionTier", Math.min(Math.max(2, (Integer)cfg.ecologyMaxFusionTier.get()), newTier));
      applyFusionAttributes(survivor);
      survivor.setHealth(survivor.getMaxHealth());
      level.sendParticles(
         BRIGHT_RED,
         survivor.getX(),
         survivor.getY() + survivor.getBbHeight() * 0.55,
         survivor.getZ(),
         34,
         survivor.getBbWidth() * 0.8,
         survivor.getBbHeight() * 0.45,
         survivor.getBbWidth() * 0.8,
         0.035
      );
      level.sendParticles(
         BLACK,
         survivor.getX(),
         survivor.getY() + survivor.getBbHeight() * 0.55,
         survivor.getZ(),
         24,
         survivor.getBbWidth() * 0.7,
         survivor.getBbHeight() * 0.4,
         survivor.getBbWidth() * 0.7,
         0.025
      );
      level.playSound(null, survivor.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 0.75F, 0.68F + newTier * 0.06F);
   }

   private static void tickConvertedMob(ServerLevel level, Mob mob, MoreskillsRaceConfig.BlackCommunion cfg) {
      if (Math.floorMod(mob.tickCount - mob.getId(), 200) < 10) {
         ensureMinionTeam(level, mob);
         applyFusionAttributes(mob);
      }

      if (Math.floorMod(mob.tickCount - mob.getId(), 40) < 10) {
         int tier = getFusionTier(mob);
         level.sendParticles(
            tier >= 3 ? BRIGHT_RED : RED,
            mob.getX(),
            mob.getY() + mob.getBbHeight() * 0.5,
            mob.getZ(),
            Math.min(7, 2 + tier),
            mob.getBbWidth() * 0.35,
            mob.getBbHeight() * 0.3,
            mob.getBbWidth() * 0.35,
            0.006
         );
      }

      int interval = Math.max(20, (Integer)cfg.ecologyMinionThinkInterval.get());
      if (Math.floorMod(mob.tickCount - mob.getId(), interval) < 10) {
         LivingEntity target = mob.getTarget();
         if (!isValidMinionTarget(target)) {
            target = findMinionTarget(level, mob, Math.max(8, (Integer)cfg.ecologyMinionTargetRadius.get()));
            mob.setTarget(target);
         }

         if (target == null) {
            returnHome(mob);
         } else {
            double distance = mob.distanceToSqr(target);
            if (distance > 5.5) {
               mob.getNavigation().moveTo(target, Math.max(0.75, (Double)cfg.ecologyMinionMoveSpeed.get()));
            } else {
               CompoundTag tag = ecologyTag(mob);
               long now = level.getGameTime();
               int attackInterval = Math.max(20, (Integer)cfg.ecologyMinionAttackInterval.get());
               if (now - tag.getLong("LastAttack") >= attackInterval) {
                  tag.putLong("LastAttack", now);
                  int tier = getFusionTier(mob);
                  double power = getInfectionPower(level, cfg);
                  double powerScale = 1.0 + Math.min(9.0, Math.sqrt(Math.max(0.0, power)) * 0.035 * Math.max(0, tier - 1));
                  float damage = (float)(Math.max(0.5F, ((Double)cfg.ecologyMinionDamage.get()).floatValue()) * (1.0F + (tier - 1) * 0.25F) * powerScale);
                  DamageSource source = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, mob);
                  boolean hurt = target.hurt(source, damage);
                  if (hurt && target instanceof Mob targetMob && target.isAlive()) {
                     addCorruption(targetMob, 1, now);
                     tryConvertMarkedMob(level, targetMob, cfg);
                  }
               }
            }
         }
      }
   }

   private static LivingEntity findMinionTarget(ServerLevel level, Mob mob, int radius) {
      List<Mob> candidates = level.getEntitiesOfClass(Mob.class, mob.getBoundingBox().inflate(radius), BlackBloodEcologyHandler::isValidMinionTarget);
      LivingEntity best = null;
      double bestDistance = Double.MAX_VALUE;
      int checked = 0;

      for (Mob candidate : candidates) {
         if (checked++ >= 20) {
            break;
         }

         double distance = mob.distanceToSqr(candidate);
         if (distance < bestDistance) {
            bestDistance = distance;
            best = candidate;
         }
      }

      return best;
   }

   private static boolean isValidMinionTarget(LivingEntity target) {
      return target instanceof Mob && target.isAlive() && !isBloodFaction(target);
   }

   private static void returnHome(Mob mob) {
      CompoundTag tag = ecologyTagOptional(mob);
      if (tag != null && tag.contains("HomeX", 3)) {
         BlockPos home = new BlockPos(tag.getInt("HomeX"), tag.getInt("HomeY"), tag.getInt("HomeZ"));
         if (mob.blockPosition().distSqr(home) > 1024.0) {
            mob.getNavigation().moveTo(home.getX() + 0.5, home.getY() + 0.5, home.getZ() + 0.5, 0.9);
         }
      }
   }

   private static void tryConvertMarkedMob(ServerLevel level, Mob target, MoreskillsRaceConfig.BlackCommunion cfg) {
      if (target.isAlive() && !isConverted(target) && !BloodfiendHandler.isBloodfiend(target)) {
         CompoundTag tag = ecologyTagOptional(target);
         if (tag != null && tag.getInt("Corruption") >= Math.max(1, (Integer)cfg.ecologyConversionMarks.get())) {
            double ratio = target.getHealth() / Math.max(1.0F, target.getMaxHealth());
            if (!(ratio > Mth.clamp((Double)cfg.ecologyConversionHealthRatio.get(), 0.01, 1.0))) {
               BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
               BlockPos nest = ecology.findNearestStructureOfType(
                  target.blockPosition(), Math.max(12, (Integer)cfg.ecologyNestRadius.get() * 2), BlackBloodEcologyHandler.StructureType.CONVERSION_NEST
               );
               if (nest != null) {
                  int cap = Math.max(1, (Integer)cfg.ecologyMinionCapPerNest.get());
                  int active = level.getEntitiesOfClass(
                        Mob.class, new AABB(nest).inflate(Math.max(16, (Integer)cfg.ecologyNestRadius.get() * 2)), BlackBloodEcologyHandler::isConverted
                     )
                     .size();
                  if (active < cap) {
                     BlackBloodEcologyHandler.StructureData structure = ecology.getStructure(nest);
                     if (structure != null) {
                        int cost = Math.max(1, (Integer)cfg.ecologyConversionBloodCost.get());
                        if (consumeStructureOrAltarBlood(ecology, nest, BlackBloodDomainData.get(level), structure.altarPos(), cost)) {
                           convertMob(level, target, nest, structure.altarPos());
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void convertMob(ServerLevel level, Mob mob, BlockPos home, BlockPos altarPos) {
      CompoundTag tag = ecologyTag(mob);
      tag.putBoolean("Converted", true);
      tag.putInt("FusionTier", 1);
      tag.remove("Corruption");
      tag.remove("CorruptionTime");
      tag.putInt("HomeX", home.getX());
      tag.putInt("HomeY", home.getY());
      tag.putInt("HomeZ", home.getZ());
      tag.putInt("AltarX", altarPos.getX());
      tag.putInt("AltarY", altarPos.getY());
      tag.putInt("AltarZ", altarPos.getZ());
      mob.setTarget(null);
      applyFusionAttributes(mob);
      ensureMinionTeam(level, mob);
      mob.setHealth(Math.max(mob.getHealth(), mob.getMaxHealth() * 0.4F));
      level.sendParticles(
         BRIGHT_RED,
         mob.getX(),
         mob.getY() + mob.getBbHeight() * 0.5,
         mob.getZ(),
         24,
         mob.getBbWidth() * 0.65,
         mob.getBbHeight() * 0.45,
         mob.getBbWidth() * 0.65,
         0.03
      );
      level.sendParticles(
         BLACK,
         mob.getX(),
         mob.getY() + mob.getBbHeight() * 0.5,
         mob.getZ(),
         16,
         mob.getBbWidth() * 0.55,
         mob.getBbHeight() * 0.4,
         mob.getBbWidth() * 0.55,
         0.02
      );
      level.playSound(null, mob.blockPosition(), SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.HOSTILE, 0.8F, 0.62F);
   }

   private static void addCorruption(Mob target, int amount, long time) {
      CompoundTag tag = ecologyTag(target);
      tag.putInt("Corruption", Mth.clamp(tag.getInt("Corruption") + Math.max(1, amount), 0, 100));
      tag.putLong("CorruptionTime", time);
   }

   public static boolean isConvertedBloodMinion(Entity entity) {
      CompoundTag tag = ecologyTagOptional(entity);
      return tag != null && tag.getBoolean("Converted");
   }

   public static boolean isBloodFaction(Entity entity) {
      return isConvertedBloodMinion(entity) || entity instanceof LivingEntity living && BloodfiendHandler.isBloodfiend(living);
   }

   private static boolean isConverted(Entity entity) {
      return isConvertedBloodMinion(entity);
   }

   private static int getFusionTier(Entity entity) {
      CompoundTag tag = ecologyTagOptional(entity);
      return tag == null ? 1 : Mth.clamp(tag.getInt("FusionTier"), 1, 16);
   }

   private static void applyFusionAttributes(Mob mob) {
      int tier = getFusionTier(mob);
      MoreskillsRaceConfig.BlackCommunion cfg = cfg();
      int maxTier = Math.max(2, (Integer)cfg.ecologyMaxFusionTier.get());
      double tierFactor = Mth.clamp((tier - 1.0) / Math.max(1.0, maxTier - 1.0), 0.0, 1.0);
      double power = mob.level() instanceof ServerLevel level ? getInfectionPower(level, cfg) : 0.0;
      double infectionHealth = Math.min((Double)cfg.ecologyFusionMaxHealthMultiplier.get(), power * (Double)cfg.ecologyFusionHealthPerPower.get() * tierFactor);
      double infectionAttack = Math.min((Double)cfg.ecologyFusionMaxAttackMultiplier.get(), power * (Double)cfg.ecologyFusionAttackPerPower.get() * tierFactor);

      double health = switch (tier) {
         case 1 -> 0.1;
         case 2 -> 0.35;
         default -> 0.75 + (tier - 3) * 0.15;
      } + infectionHealth;

      double attack = switch (tier) {
         case 1 -> 0.05;
         case 2 -> 0.2;
         default -> 0.45 + (tier - 3) * 0.1;
      } + infectionAttack;

      double armor = switch (tier) {
         case 1 -> 0.0;
         case 2 -> 2.0;
         default -> 5.0 + (tier - 3) * 1.5;
      } + Math.min((Double)cfg.ecologyFusionMaxArmor.get(), power * 0.02 * tierFactor);

      double speed = switch (tier) {
         case 1 -> 0.0;
         case 2 -> 0.03;
         default -> 0.06 + (tier - 3) * 0.01;
      } + Math.min(0.2, power * 3.0E-5 * tierFactor);

      double scale = switch (tier) {
         case 1 -> 0.02;
         case 2 -> 0.1;
         default -> 0.22 + (tier - 3) * 0.05;
      } + Math.min(0.55, power * 8.0E-5 * tierFactor);
      setModifier(mob, Attributes.MAX_HEALTH, HEALTH, health, Operation.ADD_MULTIPLIED_TOTAL);
      setModifier(mob, Attributes.ATTACK_DAMAGE, ATTACK, attack, Operation.ADD_MULTIPLIED_TOTAL);
      setModifier(mob, Attributes.ARMOR, ARMOR, armor, Operation.ADD_VALUE);
      setModifier(mob, Attributes.MOVEMENT_SPEED, SPEED, speed, Operation.ADD_MULTIPLIED_TOTAL);
      setModifier(mob, Attributes.SCALE, SCALE, scale, Operation.ADD_MULTIPLIED_TOTAL);
   }

   private static double getInfectionPower(ServerLevel level, MoreskillsRaceConfig.BlackCommunion cfg) {
      long now = level.getGameTime();
      BlackBloodEcologyHandler.InfectionPowerCache cached = INFECTION_POWER_CACHE.get(level);
      if (cached != null && now < cached.expiresAt()) {
         return cached.power();
      }

      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      BlackBloodEcologyHandler.EcologyData ecology = BlackBloodEcologyHandler.EcologyData.get(level);
      long power = (long)conquest.conqueredChunks() * Math.max(0, (Integer)cfg.ecologyFusionPowerPerConqueredChunk.get());
      power += conquest.createdBlooms() * 8L;
      power += conquest.completedRoots() * 5L;
      power += ecology.claimedVirtualCount(Math.max(2, (Integer)cfg.ecologyVirtualClaimSteps.get())) / 4L;
      power += ecology.structureCount() * 2L;
      double result = Math.min(1000000.0, Math.max(0L, power));
      INFECTION_POWER_CACHE.put(level, new BlackBloodEcologyHandler.InfectionPowerCache(now + 1200L, result));
      return result;
   }

   private static void setModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id, double amount, Operation operation) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         instance.addOrReplacePermanentModifier(new AttributeModifier(id, amount, operation));
      }
   }

   private static void ensureMinionTeam(ServerLevel level, LivingEntity entity) {
      Scoreboard scoreboard = level.getScoreboard();
      PlayerTeam team = scoreboard.getPlayerTeam("bb_minions");
      if (team == null) {
         team = scoreboard.addPlayerTeam("bb_minions");
         team.setColor(ChatFormatting.DARK_RED);
         team.setAllowFriendlyFire(false);
      }

      if (entity.getTeam() != team) {
         scoreboard.addPlayerToTeam(entity.getScoreboardName(), team);
      }

      entity.setGlowingTag(true);
   }

   private static void removeFromMinionTeam(ServerLevel level, LivingEntity entity) {
      PlayerTeam team = level.getScoreboard().getPlayerTeam("bb_minions");
      if (team != null) {
         level.getScoreboard().removePlayerFromTeam(entity.getScoreboardName(), team);
      }
   }

   private static boolean consumeStructureOrAltarBlood(
      BlackBloodEcologyHandler.EcologyData ecology, BlockPos structurePos, BlackBloodDomainData domain, BlockPos altarPos, int amount
   ) {
      BlackBloodEcologyHandler.StructureData structure = ecology.getStructure(structurePos);
      if (structure == null) {
         return false;
      }

      int fromStructure = Math.min(amount, structure.storedBlood());
      if (fromStructure > 0) {
         ecology.setStructure(structurePos, structure.withStoredBlood(structure.storedBlood() - fromStructure));
      }

      int remaining = amount - fromStructure;
      if (remaining <= 0) {
         return true;
      }

      if (consumeAltarFuel(domain, altarPos, remaining)) {
         return true;
      }

      if (fromStructure > 0) {
         BlackBloodEcologyHandler.StructureData current = ecology.getStructure(structurePos);
         if (current != null) {
            ecology.setStructure(structurePos, current.withStoredBlood(current.storedBlood() + fromStructure));
         }
      }

      return false;
   }

   private static void refundStructureOrAltarBlood(
      BlackBloodEcologyHandler.EcologyData ecology,
      BlockPos structurePos,
      BlackBloodDomainData domain,
      BlockPos altarPos,
      int amount,
      MoreskillsRaceConfig.BlackCommunion cfg
   ) {
      if (amount > 0) {
         BlackBloodEcologyHandler.StructureData structure = ecology.getStructure(structurePos);
         if (structure != null) {
            int capacity = Math.max(100, (Integer)cfg.ecologyStructureBloodCapacity.get());
            int room = Math.max(0, capacity - structure.storedBlood());
            int stored = Math.min(room, amount);
            if (stored > 0) {
               ecology.setStructure(structurePos, structure.withStoredBlood(structure.storedBlood() + stored));
               amount -= stored;
            }
         }

         if (amount > 0) {
            addAltarFuel(domain, altarPos, amount, cfg);
         }
      }
   }

   private static boolean consumeAltarFuel(BlackBloodDomainData domain, BlockPos altarPos, int amount) {
      BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
      return altar != null && altar.fuelBlood() >= amount
         ? domain.setAltar(
            altarPos,
            new BlackBloodDomainData.AltarData(
               altar.owner(), altar.fuelBlood() - amount, altar.totalBlood(), altar.level(), altar.generatedBlood(), altar.domainKills()
            )
         )
         : false;
   }

   private static int addAltarFuel(BlackBloodDomainData domain, BlockPos altarPos, int amount, MoreskillsRaceConfig.BlackCommunion cfg) {
      BlackBloodDomainData.AltarData altar = domain.getAltar(altarPos);
      if (altar != null && amount > 0) {
         int room = Math.max(0, (Integer)cfg.altarMaxStoredBlood.get() - altar.fuelBlood());
         int accepted = Math.min(room, amount);
         if (accepted <= 0) {
            return 0;
         }

         domain.setAltar(
            altarPos,
            new BlackBloodDomainData.AltarData(
               altar.owner(),
               altar.fuelBlood() + accepted,
               safeAdd(altar.totalBlood(), accepted),
               altar.level(),
               safeAdd(altar.generatedBlood(), accepted),
               altar.domainKills()
            )
         );
         return accepted;
      } else {
         return 0;
      }
   }

   private static Entry<BlockPos, BlackBloodDomainData.AltarData> findBestAltar(BlackBloodDomainData domain, int requiredFuel) {
      Entry<BlockPos, BlackBloodDomainData.AltarData> best = null;
      int bestFuel = Integer.MIN_VALUE;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : domain.altars()) {
         if (entry.getValue().fuelBlood() >= requiredFuel && entry.getValue().fuelBlood() > bestFuel) {
            bestFuel = entry.getValue().fuelBlood();
            best = entry;
         }
      }

      return best;
   }

   private static BlockPos findNearestAltar(BlackBloodDomainData domain, BlockPos center, int radius) {
      BlockPos best = null;
      double bestDistance = (double)radius * radius;

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : domain.altars()) {
         double distance = entry.getKey().distSqr(center);
         if (distance <= bestDistance) {
            bestDistance = distance;
            best = entry.getKey();
         }
      }

      return best;
   }

   private static void spawnStructureVfx(ServerLevel level, BlockPos pos, BlackBloodEcologyHandler.StructureType type, int evolution) {
      int count = (type == BlackBloodEcologyHandler.StructureType.FUSION_PIT ? 4 : 2) + evolution;
      DustParticleOptions particle = evolution >= 2
         ? BRIGHT_RED
         : (type != BlackBloodEcologyHandler.StructureType.VEIN_SPIRE && type != BlackBloodEcologyHandler.StructureType.CONQUEST_MONOLITH ? RED : BLACK);
      level.sendParticles(
         particle,
         pos.getX() + 0.5,
         pos.getY() + 1.1 + evolution,
         pos.getZ() + 0.5,
         count,
         0.55 + evolution * 0.25,
         0.55 + evolution * 0.35,
         0.55 + evolution * 0.25,
         0.004
      );
   }

   private static boolean crossed(long oldAge, long newAge, long interval) {
      return oldAge / interval != newAge / interval;
   }

   private static boolean canReplaceNaturalGround(BlockState state) {
      return state.is(BlockTags.DIRT)
         || state.is(BlockTags.BASE_STONE_OVERWORLD)
         || state.is(BlockTags.BASE_STONE_NETHER)
         || state.is(Blocks.GRASS_BLOCK)
         || state.is(Blocks.MYCELIUM)
         || state.is(Blocks.PODZOL)
         || state.is(Blocks.COARSE_DIRT)
         || state.is(Blocks.ROOTED_DIRT);
   }

   private static boolean isProtected(ServerLevel level, BlockPos pos, BlockState state) {
      if (!state.isAir() && !state.hasBlockEntity() && !state.is(BlockTags.WITHER_IMMUNE)) {
         BlockEntity blockEntity = level.getBlockEntity(pos);
         return blockEntity != null
            || state.getDestroySpeed(level, pos) < 0.0F
            || state.is((Block)ModBlocks.BLOOD_ALTAR.get())
            || state.is((Block)ModBlocks.BLOOD_WORLD_HEART_CORE.get());
      } else {
         return true;
      }
   }

   private static CompoundTag ecologyTag(Entity entity) {
      CompoundTag persistent = entity.getPersistentData();
      if (!persistent.contains("tensuramoreskills:black_blood_ecology", 10)) {
         persistent.put("tensuramoreskills:black_blood_ecology", new CompoundTag());
      }

      return persistent.getCompound("tensuramoreskills:black_blood_ecology");
   }

   private static CompoundTag ecologyTagOptional(Entity entity) {
      CompoundTag persistent = entity.getPersistentData();
      return persistent.contains("tensuramoreskills:black_blood_ecology", 10) ? persistent.getCompound("tensuramoreskills:black_blood_ecology") : null;
   }

   private static void clearEcologyTagIfEmpty(Entity entity, CompoundTag tag) {
      if (tag.isEmpty()) {
         entity.getPersistentData().remove("tensuramoreskills:black_blood_ecology");
      }
   }

   private static int safeAdd(int current, int add) {
      long value = (long)current + add;
      return value >= 2147483647L ? Integer.MAX_VALUE : Math.max(0, (int)value);
   }

   private static final class EcologyData extends SavedData {
      private static final String DATA_NAME = "tensuramoreskills_black_blood_ecology";
      private final LinkedHashMap<BlockPos, BlackBloodEcologyHandler.StructureData> structures = new LinkedHashMap<>();
      private final LinkedHashMap<Long, BlackBloodEcologyHandler.PendingBuild> pendingBuilds = new LinkedHashMap<>();
      private final LinkedHashMap<Long, BlackBloodEcologyHandler.PendingRoot> pendingRoots = new LinkedHashMap<>();
      private final LinkedHashMap<Long, Byte> virtualPressure = new LinkedHashMap<>();
      private final LinkedHashSet<Long> frontier = new LinkedHashSet<>();
      private final Set<Long> materialized = new HashSet<>();
      private final Set<Long> activeRootTargets = new HashSet<>();
      private int structureCursor;
      private int pendingBuildCursor;
      private int nodeSourceCursor;
      private int siteSourceCursor;
      private int buildAttemptCounter;
      private long nextPendingBuildId = 1L;
      private String lastBuildStatus = "never";
      private long lastBuildAttemptTime;
      private long lastBuildQueuedTime;
      private long lastBuildSuccessTime;

      private static Factory<BlackBloodEcologyHandler.EcologyData> factory() {
         return new Factory(BlackBloodEcologyHandler.EcologyData::new, BlackBloodEcologyHandler.EcologyData::load);
      }

      private static BlackBloodEcologyHandler.EcologyData get(ServerLevel level) {
         return (BlackBloodEcologyHandler.EcologyData)level.getDataStorage().computeIfAbsent(factory(), "tensuramoreskills_black_blood_ecology");
      }

      private static BlackBloodEcologyHandler.EcologyData load(CompoundTag tag, Provider provider) {
         BlackBloodEcologyHandler.EcologyData data = new BlackBloodEcologyHandler.EcologyData();
         ListTag structures = tag.getList("Structures", 10);

         for (int i = 0; i < structures.size(); i++) {
            CompoundTag entry = structures.getCompound(i);
            BlockPos pos = new BlockPos(entry.getInt("X"), entry.getInt("Y"), entry.getInt("Z"));
            BlockPos altar = new BlockPos(entry.getInt("AltarX"), entry.getInt("AltarY"), entry.getInt("AltarZ"));
            BlackBloodEcologyHandler.StructureType type = BlackBloodEcologyHandler.StructureType.byId(entry.getInt("Type"));
            data.structures
               .put(
                  pos.immutable(),
                  new BlackBloodEcologyHandler.StructureData(
                     altar,
                     type,
                     entry.getInt("StoredBlood"),
                     entry.getLong("Age"),
                     entry.getLong("LastGameTime"),
                     entry.contains("Evolution", 3) ? entry.getInt("Evolution") : 0
                  )
               );
         }

         ListTag builds = tag.getList("PendingBuilds", 10);

         for (int i = 0; i < builds.size(); i++) {
            CompoundTag entry = builds.getCompound(i);
            long id = Math.max(1L, entry.getLong("Id"));
            BlockPos center = new BlockPos(entry.getInt("X"), entry.getInt("Y"), entry.getInt("Z"));
            BlockPos altar = new BlockPos(entry.getInt("AltarX"), entry.getInt("AltarY"), entry.getInt("AltarZ"));
            BlackBloodEcologyHandler.PendingBuild build = new BlackBloodEcologyHandler.PendingBuild(
               center,
               altar,
               BlackBloodEcologyHandler.StructureType.byId(entry.getInt("Type")),
               entry.getBoolean("Remote"),
               entry.getInt("Cost"),
               entry.contains("ReservedBlood", 3) ? entry.getInt("ReservedBlood") : 0,
               entry.getLong("QueuedAt")
            );
            data.pendingBuilds.put(id, build);
            data.nextPendingBuildId = Math.max(data.nextPendingBuildId, id + 1L);
         }

         ListTag roots = tag.getList("PendingRoots", 10);

         for (int i = 0; i < roots.size(); i++) {
            CompoundTag entry = roots.getCompound(i);
            long target = entry.getLong("TargetChunk");
            BlockPos source = new BlockPos(entry.getInt("X"), entry.getInt("Y"), entry.getInt("Z"));
            BlockPos altar = new BlockPos(entry.getInt("AltarX"), entry.getInt("AltarY"), entry.getInt("AltarZ"));
            data.pendingRoots
               .put(target, new BlackBloodEcologyHandler.PendingRoot(source, altar, target, entry.getBoolean("BaseLike"), entry.getLong("QueuedAt")));
         }

         ListTag virtual = tag.getList("VirtualChunks", 10);

         for (int i = 0; i < virtual.size(); i++) {
            CompoundTag entry = virtual.getCompound(i);
            int pressure = Mth.clamp(entry.getInt("Pressure"), 0, 127);
            if (pressure > 0) {
               data.virtualPressure.put(entry.getLong("Chunk"), (byte)pressure);
            }
         }

         for (long chunk : tag.getLongArray("Frontier")) {
            data.frontier.add(chunk);
         }

         for (long chunk : tag.getLongArray("Materialized")) {
            data.materialized.add(chunk);
         }

         for (long chunk : tag.getLongArray("ActiveEcologyRoots")) {
            data.activeRootTargets.add(chunk);
         }

         data.structureCursor = Math.max(0, tag.getInt("StructureCursor"));
         data.pendingBuildCursor = Math.max(0, tag.getInt("PendingBuildCursor"));
         data.nodeSourceCursor = Math.max(0, tag.getInt("NodeSourceCursor"));
         data.siteSourceCursor = Math.max(0, tag.getInt("SiteSourceCursor"));
         data.buildAttemptCounter = Math.max(0, tag.getInt("BuildAttemptCounter"));
         data.nextPendingBuildId = Math.max(data.nextPendingBuildId, Math.max(1L, tag.getLong("NextPendingBuildId")));
         data.lastBuildStatus = tag.contains("LastBuildStatus", 8) ? tag.getString("LastBuildStatus") : "never";
         data.lastBuildAttemptTime = Math.max(0L, tag.getLong("LastBuildAttemptTime"));
         data.lastBuildQueuedTime = Math.max(0L, tag.getLong("LastBuildQueuedTime"));
         data.lastBuildSuccessTime = Math.max(0L, tag.getLong("LastBuildSuccessTime"));
         return data;
      }

      public CompoundTag save(CompoundTag tag, Provider provider) {
         ListTag structures = new ListTag();

         for (Entry<BlockPos, BlackBloodEcologyHandler.StructureData> entry : this.structures.entrySet()) {
            BlockPos pos = entry.getKey();
            BlackBloodEcologyHandler.StructureData structure = entry.getValue();
            CompoundTag value = new CompoundTag();
            value.putInt("X", pos.getX());
            value.putInt("Y", pos.getY());
            value.putInt("Z", pos.getZ());
            value.putInt("AltarX", structure.altarPos().getX());
            value.putInt("AltarY", structure.altarPos().getY());
            value.putInt("AltarZ", structure.altarPos().getZ());
            value.putInt("Type", structure.type().ordinal());
            value.putInt("StoredBlood", structure.storedBlood());
            value.putLong("Age", structure.age());
            value.putLong("LastGameTime", structure.lastGameTime());
            value.putInt("Evolution", structure.evolution());
            structures.add(value);
         }

         tag.put("Structures", structures);
         ListTag builds = new ListTag();

         for (Entry<Long, BlackBloodEcologyHandler.PendingBuild> entry : this.pendingBuilds.entrySet()) {
            BlackBloodEcologyHandler.PendingBuild build = entry.getValue();
            CompoundTag value = new CompoundTag();
            value.putLong("Id", entry.getKey());
            value.putInt("X", build.center().getX());
            value.putInt("Y", build.center().getY());
            value.putInt("Z", build.center().getZ());
            value.putInt("AltarX", build.sourceAltar().getX());
            value.putInt("AltarY", build.sourceAltar().getY());
            value.putInt("AltarZ", build.sourceAltar().getZ());
            value.putInt("Type", build.type().ordinal());
            value.putBoolean("Remote", build.remoteAltar());
            value.putInt("Cost", build.cost());
            value.putInt("ReservedBlood", build.reservedBlood());
            value.putLong("QueuedAt", build.queuedAt());
            builds.add(value);
         }

         tag.put("PendingBuilds", builds);
         ListTag roots = new ListTag();

         for (BlackBloodEcologyHandler.PendingRoot root : this.pendingRoots.values()) {
            CompoundTag value = new CompoundTag();
            value.putLong("TargetChunk", root.targetChunk());
            value.putInt("X", root.source().getX());
            value.putInt("Y", root.source().getY());
            value.putInt("Z", root.source().getZ());
            value.putInt("AltarX", root.altarPos().getX());
            value.putInt("AltarY", root.altarPos().getY());
            value.putInt("AltarZ", root.altarPos().getZ());
            value.putBoolean("BaseLike", root.baseLike());
            value.putLong("QueuedAt", root.queuedAt());
            roots.add(value);
         }

         tag.put("PendingRoots", roots);
         ListTag virtual = new ListTag();

         for (Entry<Long, Byte> entry : this.virtualPressure.entrySet()) {
            CompoundTag value = new CompoundTag();
            value.putLong("Chunk", entry.getKey());
            value.putInt("Pressure", Byte.toUnsignedInt(entry.getValue()));
            virtual.add(value);
         }

         tag.put("VirtualChunks", virtual);
         tag.putLongArray("Frontier", this.frontier.stream().mapToLong(Long::longValue).toArray());
         tag.putLongArray("Materialized", this.materialized.stream().mapToLong(Long::longValue).toArray());
         tag.putLongArray("ActiveEcologyRoots", this.activeRootTargets.stream().mapToLong(Long::longValue).toArray());
         tag.putInt("StructureCursor", this.structureCursor);
         tag.putInt("PendingBuildCursor", this.pendingBuildCursor);
         tag.putInt("NodeSourceCursor", this.nodeSourceCursor);
         tag.putInt("SiteSourceCursor", this.siteSourceCursor);
         tag.putInt("BuildAttemptCounter", this.buildAttemptCounter);
         tag.putLong("NextPendingBuildId", this.nextPendingBuildId);
         tag.putString("LastBuildStatus", this.lastBuildStatus);
         tag.putLong("LastBuildAttemptTime", this.lastBuildAttemptTime);
         tag.putLong("LastBuildQueuedTime", this.lastBuildQueuedTime);
         tag.putLong("LastBuildSuccessTime", this.lastBuildSuccessTime);
         return tag;
      }

      private String lastBuildStatus() {
         return this.lastBuildStatus;
      }

      private long lastBuildQueuedTime() {
         return this.lastBuildQueuedTime;
      }

      private long lastBuildSuccessTime() {
         return this.lastBuildSuccessTime;
      }

      private void setLastBuildStatus(String status) {
         String value = status != null && !status.isBlank() ? status : "unknown";
         if (!Objects.equals(this.lastBuildStatus, value)) {
            this.lastBuildStatus = value;
            this.setDirty();
         }
      }

      private void markBuildAttempt(long time) {
         this.lastBuildAttemptTime = Math.max(0L, time);
         this.setDirty();
      }

      private void markBuildQueued(long time, BlackBloodEcologyHandler.StructureType type, BlockPos pos) {
         this.lastBuildQueuedTime = Math.max(0L, time);
         this.lastBuildStatus = "QUEUED: " + type.name() + " at " + pos.toShortString();
         this.setDirty();
      }

      private void markBuildSuccess(long time, BlackBloodEcologyHandler.StructureType type, BlockPos pos, boolean remoteAltar) {
         this.lastBuildSuccessTime = Math.max(0L, time);
         this.lastBuildStatus = remoteAltar ? "FORMED: REMOTE_ALTAR at " + pos.toShortString() : "FORMED: " + type.name() + " at " + pos.toShortString();
         this.setDirty();
      }

      private Iterable<Entry<BlockPos, BlackBloodEcologyHandler.StructureData>> structureEntries() {
         return new ArrayList<>(this.structures.entrySet());
      }

      private String firstPendingSummary() {
         Iterator var1 = this.pendingBuilds.entrySet().iterator();
         if (var1.hasNext()) {
            Entry<Long, BlackBloodEcologyHandler.PendingBuild> entry = (Entry<Long, BlackBloodEcologyHandler.PendingBuild>)var1.next();
            BlackBloodEcologyHandler.PendingBuild build = entry.getValue();
            return "#"
               + entry.getKey()
               + " "
               + build.type().name()
               + " "
               + build.reservedBlood()
               + "/"
               + build.cost()
               + " at "
               + build.center().toShortString();
         } else {
            return "none";
         }
      }

      private int nodeSourceCursor() {
         return this.nodeSourceCursor;
      }

      private void setNodeSourceCursor(int cursor) {
         this.nodeSourceCursor = Math.max(0, cursor);
         this.setDirty();
      }

      private int siteSourceCursor() {
         return this.siteSourceCursor;
      }

      private void setSiteSourceCursor(int cursor) {
         this.siteSourceCursor = Math.max(0, cursor);
         this.setDirty();
      }

      private int nextBuildAttempt() {
         this.buildAttemptCounter = BlackBloodEcologyHandler.safeAdd(this.buildAttemptCounter, 1);
         this.setDirty();
         return this.buildAttemptCounter;
      }

      private int structureCount() {
         return this.structures.size();
      }

      private Iterable<BlockPos> structurePositions() {
         return new ArrayList<>(this.structures.keySet());
      }

      private boolean addStructure(BlockPos pos, BlackBloodEcologyHandler.StructureData structure) {
         if (this.structures.containsKey(pos)) {
            return false;
         }

         this.structures.put(pos.immutable(), structure);
         this.setDirty();
         return true;
      }

      private BlackBloodEcologyHandler.StructureData getStructure(BlockPos pos) {
         return this.structures.get(pos);
      }

      private void setStructure(BlockPos pos, BlackBloodEcologyHandler.StructureData structure) {
         BlackBloodEcologyHandler.StructureData old = this.structures.get(pos);
         if (!Objects.equals(old, structure)) {
            this.structures.put(pos.immutable(), structure);
            this.setDirty();
         }
      }

      private void removeStructure(BlockPos pos) {
         if (this.structures.remove(pos) != null) {
            this.setDirty();
         }
      }

      private boolean hasStructureNear(BlockPos pos, int radius) {
         int radiusSqr = radius * radius;

         for (BlockPos structure : this.structures.keySet()) {
            if (structure.distSqr(pos) <= radiusSqr) {
               return true;
            }
         }

         return false;
      }

      private List<Entry<BlockPos, BlackBloodEcologyHandler.StructureData>> nextStructures(int max) {
         List<Entry<BlockPos, BlackBloodEcologyHandler.StructureData>> entries = new ArrayList<>(this.structures.entrySet());
         List<Entry<BlockPos, BlackBloodEcologyHandler.StructureData>> result = new ArrayList<>();
         if (!entries.isEmpty() && max > 0) {
            int start = Math.floorMod(this.structureCursor, entries.size());
            int count = Math.min(max, entries.size());

            for (int i = 0; i < count; i++) {
               Entry<BlockPos, BlackBloodEcologyHandler.StructureData> entry = entries.get(Math.floorMod(start + i, entries.size()));
               result.add(Map.entry(entry.getKey(), entry.getValue()));
            }

            this.structureCursor = Math.floorMod(start + count, entries.size());
            return result;
         } else {
            return result;
         }
      }

      private BlackBloodEcologyHandler.StructureType nextStructureType() {
         BlackBloodEcologyHandler.StructureType[] cycle = new BlackBloodEcologyHandler.StructureType[]{
            BlackBloodEcologyHandler.StructureType.VEIN_SPIRE,
            BlackBloodEcologyHandler.StructureType.BLOOD_TURBINE,
            BlackBloodEcologyHandler.StructureType.CONVERSION_NEST,
            BlackBloodEcologyHandler.StructureType.PREDATOR_GARDEN,
            BlackBloodEcologyHandler.StructureType.BLOOD_RESERVOIR,
            BlackBloodEcologyHandler.StructureType.SCARLET_LATTICE,
            BlackBloodEcologyHandler.StructureType.FUSION_PIT,
            BlackBloodEcologyHandler.StructureType.CONQUEST_MONOLITH,
            BlackBloodEcologyHandler.StructureType.SUMMONER_BLOOM,
            BlackBloodEcologyHandler.StructureType.ROOT_RELAY,
            BlackBloodEcologyHandler.StructureType.BLOOD_SANCTUARY,
            BlackBloodEcologyHandler.StructureType.HUNTING_BEACON,
            BlackBloodEcologyHandler.StructureType.HEMATIC_EXTRACTOR,
            BlackBloodEcologyHandler.StructureType.CARRION_ORCHARD,
            BlackBloodEcologyHandler.StructureType.MARROW_PUMP,
            BlackBloodEcologyHandler.StructureType.SANGUINE_COMPOSTER
         };
         int index = this.structures.size() + this.pendingStructureCount();
         return cycle[Math.floorMod(index, cycle.length)];
      }

      private int pendingBuildCount() {
         return this.pendingBuilds.size();
      }

      private int pendingStructureCount() {
         int count = 0;

         for (BlackBloodEcologyHandler.PendingBuild build : this.pendingBuilds.values()) {
            if (!build.remoteAltar()) {
               count++;
            }
         }

         return count;
      }

      private void queuePendingBuild(BlackBloodEcologyHandler.PendingBuild build) {
         this.pendingBuilds.put(this.nextPendingBuildId++, build);
         this.setDirty();
      }

      private BlackBloodEcologyHandler.PendingBuild getPendingBuild(long id) {
         return this.pendingBuilds.get(id);
      }

      private void setPendingBuild(long id, BlackBloodEcologyHandler.PendingBuild build) {
         if (this.pendingBuilds.containsKey(id) && !Objects.equals(this.pendingBuilds.get(id), build)) {
            this.pendingBuilds.put(id, build);
            this.setDirty();
         }
      }

      private void removePendingBuild(long id) {
         if (this.pendingBuilds.remove(id) != null) {
            this.setDirty();
         }
      }

      private List<Entry<Long, BlackBloodEcologyHandler.PendingBuild>> nextPendingBuilds(int max) {
         List<Entry<Long, BlackBloodEcologyHandler.PendingBuild>> entries = new ArrayList<>(this.pendingBuilds.entrySet());
         List<Entry<Long, BlackBloodEcologyHandler.PendingBuild>> result = new ArrayList<>();
         if (!entries.isEmpty() && max > 0) {
            int start = Math.floorMod(this.pendingBuildCursor, entries.size());
            int count = Math.min(max, entries.size());

            for (int i = 0; i < count; i++) {
               Entry<Long, BlackBloodEcologyHandler.PendingBuild> entry = entries.get(Math.floorMod(start + i, entries.size()));
               result.add(Map.entry(entry.getKey(), entry.getValue()));
            }

            this.pendingBuildCursor = Math.floorMod(start + count, entries.size());
            return result;
         } else {
            return result;
         }
      }

      private boolean hasPendingBuildNear(BlockPos pos, int radius) {
         int radiusSqr = radius * radius;

         for (BlackBloodEcologyHandler.PendingBuild build : this.pendingBuilds.values()) {
            if (!build.remoteAltar() && build.center().distSqr(pos) <= radiusSqr) {
               return true;
            }
         }

         return false;
      }

      private boolean hasPendingRemoteAltarNear(BlockPos pos, int radius) {
         int radiusSqr = radius * radius;

         for (BlackBloodEcologyHandler.PendingBuild build : this.pendingBuilds.values()) {
            if (build.remoteAltar() && build.center().distSqr(pos) <= radiusSqr) {
               return true;
            }
         }

         return false;
      }

      private int pendingRootCount() {
         return this.pendingRoots.size();
      }

      private boolean hasPendingRootTarget(long targetChunk) {
         return this.pendingRoots.containsKey(targetChunk);
      }

      private void queuePendingRoot(BlackBloodEcologyHandler.PendingRoot root) {
         if (!this.pendingRoots.containsKey(root.targetChunk())) {
            this.pendingRoots.put(root.targetChunk(), root);
            this.setDirty();
         }
      }

      private BlackBloodEcologyHandler.PendingRoot getPendingRoot(long targetChunk) {
         return this.pendingRoots.get(targetChunk);
      }

      private void setPendingRoot(long targetChunk, BlackBloodEcologyHandler.PendingRoot root) {
         if (this.pendingRoots.containsKey(targetChunk) && !Objects.equals(this.pendingRoots.get(targetChunk), root)) {
            this.pendingRoots.put(targetChunk, root);
            this.setDirty();
         }
      }

      private void removePendingRoot(long targetChunk) {
         if (this.pendingRoots.remove(targetChunk) != null) {
            this.setDirty();
         }
      }

      private Iterable<Entry<Long, BlackBloodEcologyHandler.PendingRoot>> pendingRoots() {
         return new ArrayList<>(this.pendingRoots.entrySet());
      }

      private int activeRootCount() {
         return this.activeRootTargets.size();
      }

      private void markActiveRootTarget(long targetChunk) {
         if (this.activeRootTargets.add(targetChunk)) {
            this.setDirty();
         }
      }

      private void pruneActiveRootTargets(BlackBloodConquestData conquest) {
         if (this.activeRootTargets.removeIf(target -> !conquest.hasRootTargeting(target))) {
            this.setDirty();
         }
      }

      private void addStructureBlood(BlockPos pos, int amount, int capacity) {
         BlackBloodEcologyHandler.StructureData structure = this.structures.get(pos);
         if (structure != null && amount > 0) {
            int next = Math.min(capacity, BlackBloodEcologyHandler.safeAdd(structure.storedBlood(), amount));
            this.setStructure(pos, structure.withStoredBlood(next));
         }
      }

      private int pullBloodFromNearbyStructures(BlockPos reservoir, int radius, int max) {
         if (max <= 0) {
            return 0;
         }

         int radiusSqr = radius * radius;
         int pulled = 0;

         for (Entry<BlockPos, BlackBloodEcologyHandler.StructureData> entry : new ArrayList<>(this.structures.entrySet())) {
            if (pulled < max && !entry.getKey().equals(reservoir) && !(entry.getKey().distSqr(reservoir) > radiusSqr)) {
               BlackBloodEcologyHandler.StructureData structure = this.structures.get(entry.getKey());
               if (structure != null && structure.storedBlood() > 0) {
                  int take = Math.min(max - pulled, Math.min(8, structure.storedBlood()));
                  this.setStructure(entry.getKey(), structure.withStoredBlood(structure.storedBlood() - take));
                  pulled += take;
               }
            }
         }

         return pulled;
      }

      private BlockPos findNearestStructure(BlockPos center, int radius) {
         BlockPos best = null;
         double bestDistance = (double)radius * radius;

         for (BlockPos pos : this.structures.keySet()) {
            double distance = pos.distSqr(center);
            if (distance <= bestDistance) {
               bestDistance = distance;
               best = pos;
            }
         }

         return best;
      }

      private BlockPos findNearestStructureOfType(BlockPos center, int radius, BlackBloodEcologyHandler.StructureType type) {
         BlockPos best = null;
         double bestDistance = (double)radius * radius;

         for (Entry<BlockPos, BlackBloodEcologyHandler.StructureData> entry : this.structures.entrySet()) {
            if (entry.getValue().type() == type) {
               double distance = entry.getKey().distSqr(center);
               if (distance <= bestDistance) {
                  bestDistance = distance;
                  best = entry.getKey();
               }
            }
         }

         return best;
      }

      private boolean hasVirtualChunks() {
         return !this.virtualPressure.isEmpty();
      }

      private int claimedVirtualCount(int claimSteps) {
         int count = 0;

         for (byte pressure : this.virtualPressure.values()) {
            if (Byte.toUnsignedInt(pressure) >= claimSteps) {
               count++;
            }
         }

         return count;
      }

      private int getVirtualPressure(long chunk) {
         return Byte.toUnsignedInt(this.virtualPressure.getOrDefault(chunk, Byte.valueOf((byte)0)));
      }

      private void setVirtualPressure(long chunk, int pressure) {
         int value = Mth.clamp(pressure, 0, 127);
         if (value <= 0) {
            this.virtualPressure.remove(chunk);
         } else {
            this.virtualPressure.put(chunk, (byte)value);
         }

         this.setDirty();
      }

      private void seedVirtual(long chunk, int claimSteps) {
         if (this.getVirtualPressure(chunk) < claimSteps) {
            this.virtualPressure.put(chunk, (byte)Math.min(127, claimSteps));
         }

         this.expandFrontierFrom(chunk);
         this.setDirty();
      }

      private Long pollFrontier() {
         Iterator<Long> iterator = this.frontier.iterator();
         if (!iterator.hasNext()) {
            return null;
         }

         Long next = iterator.next();
         iterator.remove();
         this.setDirty();
         return next;
      }

      private void requeueFrontier(long chunk) {
         if (this.frontier.add(chunk)) {
            this.setDirty();
         }
      }

      private void expandFrontierFrom(long chunk) {
         ChunkPos pos = new ChunkPos(chunk);
         long[] neighbors = new long[]{
            ChunkPos.asLong(pos.x + 1, pos.z), ChunkPos.asLong(pos.x - 1, pos.z), ChunkPos.asLong(pos.x, pos.z + 1), ChunkPos.asLong(pos.x, pos.z - 1)
         };

         for (long neighbor : neighbors) {
            if (this.getVirtualPressure(neighbor) <= 0) {
               this.frontier.add(neighbor);
            }
         }

         this.setDirty();
      }

      private boolean isMaterialized(long chunk) {
         return this.materialized.contains(chunk);
      }

      private void markMaterialized(long chunk) {
         if (this.materialized.add(chunk)) {
            this.setDirty();
         }
      }
   }

   private record InfectionPowerCache(long expiresAt, double power) {
   }

   private record PendingBuild(
      BlockPos center, BlockPos sourceAltar, BlackBloodEcologyHandler.StructureType type, boolean remoteAltar, int cost, int reservedBlood, long queuedAt
   ) {
      private PendingBuild {
         center = center.immutable();
         sourceAltar = sourceAltar.immutable();
         cost = Math.max(1, cost);
         reservedBlood = Mth.clamp(reservedBlood, 0, cost);
         queuedAt = Math.max(0L, queuedAt);
      }

      private BlackBloodEcologyHandler.PendingBuild withCenter(BlockPos center) {
         return new BlackBloodEcologyHandler.PendingBuild(center, this.sourceAltar, this.type, this.remoteAltar, this.cost, this.reservedBlood, this.queuedAt);
      }

      private BlackBloodEcologyHandler.PendingBuild withSourceAltar(BlockPos sourceAltar) {
         return new BlackBloodEcologyHandler.PendingBuild(this.center, sourceAltar, this.type, this.remoteAltar, this.cost, this.reservedBlood, this.queuedAt);
      }

      private BlackBloodEcologyHandler.PendingBuild withReservedBlood(int reservedBlood) {
         return new BlackBloodEcologyHandler.PendingBuild(this.center, this.sourceAltar, this.type, this.remoteAltar, this.cost, reservedBlood, this.queuedAt);
      }
   }

   private record PendingRoot(BlockPos source, BlockPos altarPos, long targetChunk, boolean baseLike, long queuedAt) {
      private PendingRoot {
         source = source.immutable();
         altarPos = altarPos.immutable();
         queuedAt = Math.max(0L, queuedAt);
      }
   }

   private record StructureData(BlockPos altarPos, BlackBloodEcologyHandler.StructureType type, int storedBlood, long age, long lastGameTime, int evolution) {
      private StructureData {
         altarPos = altarPos.immutable();
         storedBlood = Math.max(0, storedBlood);
         age = Math.max(0L, age);
         lastGameTime = Math.max(0L, lastGameTime);
         evolution = Mth.clamp(evolution, 0, 2);
      }

      private BlackBloodEcologyHandler.StructureData withStoredBlood(int storedBlood) {
         return new BlackBloodEcologyHandler.StructureData(this.altarPos, this.type, storedBlood, this.age, this.lastGameTime, this.evolution);
      }

      private BlackBloodEcologyHandler.StructureData withEvolution(int evolution) {
         return new BlackBloodEcologyHandler.StructureData(this.altarPos, this.type, this.storedBlood, this.age, this.lastGameTime, evolution);
      }
   }

   private enum StructureType {
      VEIN_SPIRE,
      CONVERSION_NEST,
      BLOOD_RESERVOIR,
      FUSION_PIT,
      SUMMONER_BLOOM,
      ROOT_RELAY,
      BLOOD_SANCTUARY,
      HUNTING_BEACON,
      HEMATIC_EXTRACTOR,
      CARRION_ORCHARD,
      MARROW_PUMP,
      SANGUINE_COMPOSTER,
      BLOOD_TURBINE,
      SCARLET_LATTICE,
      PREDATOR_GARDEN,
      CONQUEST_MONOLITH;

      private static BlackBloodEcologyHandler.StructureType byId(int id) {
         BlackBloodEcologyHandler.StructureType[] values = values();
         return values[Math.floorMod(id, values.length)];
      }
   }

   private static final class VecCenter {
      private static double x(BlockPos pos) {
         return pos.getX() + 0.5;
      }

      private static double y(BlockPos pos) {
         return pos.getY() + 0.5;
      }

      private static double z(BlockPos pos) {
         return pos.getZ() + 0.5;
      }
   }
}
