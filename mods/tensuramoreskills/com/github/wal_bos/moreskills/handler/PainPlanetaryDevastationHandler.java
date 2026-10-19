package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.PainSixPathsVfxPayload;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class PainPlanetaryDevastationHandler {
   private static final MoreSkillsConfigs.PainLordOfSixPaths CONFIG = MoreSkillsConfigs.GRAND.painLordOfSixPaths;
   private static final Map<UUID, PainPlanetaryDevastationHandler.Core> CORES = new HashMap<>();
   private static final int MAX_ACTIVE_REAL_CHUNKS = 520;
   private static final int MIN_FAKE_BLOCK_RADIUS = 90;
   private static boolean initialized;

   private PainPlanetaryDevastationHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(PainPlanetaryDevastationHandler::serverTick);
      }
   }

   private static void serverTick(Post event) {
      MinecraftServer server = event.getServer();
      if (server != null && !CORES.isEmpty()) {
         Iterator<Entry<UUID, PainPlanetaryDevastationHandler.Core>> iterator = CORES.entrySet().iterator();

         while (iterator.hasNext()) {
            PainPlanetaryDevastationHandler.Core core = iterator.next().getValue();
            ServerLevel level = findLevel(server, core.dimension);
            if (level == null) {
               core.instance.getOrCreateTag().putBoolean("PlanetaryDevastationActive", false);
               core.instance.markDirty();
               discardPieces(core);
               iterator.remove();
            } else if (level.getEntity(core.owner) instanceof LivingEntity owner && owner.isAlive()) {
               tickCore(owner, core.instance, core.skill, level, core);
            } else {
               core.instance.getOrCreateTag().putBoolean("PlanetaryDevastationActive", false);
               core.instance.markDirty();
               sendPlanetStop(level, core);
               discardPieces(core);
               iterator.remove();
            }
         }
      }
   }

   private static ServerLevel findLevel(MinecraftServer server, String dimension) {
      for (ServerLevel level : server.getAllLevels()) {
         if (level.dimension().location().toString().equals(dimension)) {
            return level;
         }
      }

      return null;
   }

   public static boolean hasActive(LivingEntity owner) {
      return CORES.containsKey(owner.getUUID());
   }

   public static boolean hasPushDebris(LivingEntity owner) {
      return false;
   }

   public static void clearPushDebris(LivingEntity owner) {
   }

   public static void tickPushDebris(LivingEntity owner) {
   }

   public static void start(LivingEntity owner, ManasSkillInstance instance, Skill skill) {
      init();
      if (owner.level() instanceof ServerLevel level) {
         Vec3 var11 = owner.getLookAngle().normalize();
         Vec3 start = owner.getEyePosition().add(var11.scale(3.0));
         Vec3 sky = new Vec3(start.x + var11.x * 10.0, owner.getY() + 40.0, start.z + var11.z * 10.0);
         long seed = owner.getUUID().getMostSignificantBits() ^ owner.getUUID().getLeastSignificantBits() ^ level.getGameTime();
         PainPlanetaryDevastationHandler.Core core = new PainPlanetaryDevastationHandler.Core(
            owner.getUUID(), level.dimension().location().toString(), start, sky, owner.blockPosition(), seed, instance, skill
         );
         core.grief = canSkillGrief(level, instance, owner, sky);
         CORES.put(owner.getUUID(), core);
         sendPlanet(level, core, 0.0F);
         instance.getOrCreateTag().putBoolean("PlanetaryDevastationActive", true);
         instance.markDirty();
         level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 2.0F, 0.35F);
         level.playSound(null, start.x, start.y, start.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.4F, 0.55F);
         if (owner instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.pain_lord_of_six_paths.planet_start").withStyle(ChatFormatting.DARK_PURPLE), true
            );
         }
      }
   }

   public static void tick(LivingEntity owner, ManasSkillInstance instance, Skill skill) {
      PainPlanetaryDevastationHandler.Core core = CORES.get(owner.getUUID());
      if (core == null) {
         instance.getOrCreateTag().putBoolean("PlanetaryDevastationActive", false);
         instance.markDirty();
      } else if (owner.level() instanceof ServerLevel level && core.dimension.equals(level.dimension().location().toString())) {
         tickCore(owner, instance, skill, level, core);
      } else {
         cancel(owner, instance);
      }
   }

   private static void tickCore(LivingEntity owner, ManasSkillInstance instance, Skill skill, ServerLevel level, PainPlanetaryDevastationHandler.Core core) {
      long gameTime = level.getGameTime();
      if (core.lastTick != gameTime) {
         core.lastTick = gameTime;
         core.age++;
         if (core.phase == 0) {
            double flightTicks = Math.max(18.0, ((Integer)CONFIG.planetaryDevastationFlightTicks.get()).intValue());
            double flightProgress = Mth.clamp(core.age / flightTicks, 0.0, 1.0);
            double eased = flightProgress * flightProgress * (3.0 - 2.0 * flightProgress);
            core.center = core.start.lerp(core.sky, eased);
            sendPlanet(level, core, 0.0F);
            if (shouldScan(core)) {
               collectWorldChunks(level, owner, instance, core);
            }

            moveRealPieces(level, core, false);
            pullEntities(
               level,
               owner,
               core,
               Math.max(70.0, (Double)CONFIG.planetaryDevastationEntityPullRadius.get() * 2.15),
               Math.max(2.2, (Double)CONFIG.planetaryDevastationPullForce.get() * 7.0)
            );
            if (flightProgress >= 1.0) {
               core.phase = 1;
               core.holdStartAge = core.age;
            }
         } else if (core.phase == 1) {
            if (core.holdStartAge < 0) {
               core.holdStartAge = core.age;
            }

            sendPlanet(level, core, 0.55F);
            if (shouldScan(core)) {
               collectWorldChunks(level, owner, instance, core);
            }

            moveRealPieces(level, core, false);
            pullEntities(
               level,
               owner,
               core,
               Math.max(78.0, (Double)CONFIG.planetaryDevastationEntityPullRadius.get() * 2.35),
               Math.max(2.6, (Double)CONFIG.planetaryDevastationPullForce.get() * 8.0)
            );
            int feedTicks = Mth.clamp((Integer)CONFIG.planetaryDevastationHoldTicks.get(), 220, 1200);
            if (core.age - core.holdStartAge >= feedTicks) {
               endFeeding(level, owner, core);
            }
         } else if (core.phase == 3) {
            sendPlanet(level, core, 0.75F);
            moveRealPieces(level, core, false);
         } else {
            if (core.phase == 2) {
               core.center = core.center.add(0.0, -Math.max(2.4, (Double)CONFIG.planetaryDevastationDropSpeed.get() * 1.85), 0.0);
               sendPlanet(level, core, 1.0F);
               moveRealPieces(level, core, true);
               pullEntities(
                  level,
                  owner,
                  core,
                  Math.max(68.0, (Double)CONFIG.planetaryDevastationEntityPullRadius.get() * 2.0),
                  Math.max(2.4, (Double)CONFIG.planetaryDevastationPullForce.get() * 7.0)
               );
               if (core.center.y <= core.dropY || core.age - core.dropAge > (Integer)CONFIG.planetaryDevastationMaxDropTicks.get()) {
                  detonate(level, owner, instance, core);
               }
            }
         }
      }
   }

   private static boolean shouldScan(PainPlanetaryDevastationHandler.Core core) {
      return core.phase != 2;
   }

   private static void endFeeding(ServerLevel level, LivingEntity owner, PainPlanetaryDevastationHandler.Core core) {
      if (core.phase == 1) {
         core.phase = 3;
         moveRealPieces(level, core, false);
         sendPlanet(level, core, 0.75F);
         if (owner instanceof Player player) {
            player.displayClientMessage(Component.literal("§5Planetary Devastation has finished gathering. Shift-use it to drop the moon."), true);
         }
      }
   }

   public static void drop(LivingEntity owner, ManasSkillInstance instance, Skill skill) {
      PainPlanetaryDevastationHandler.Core core = CORES.get(owner.getUUID());
      if (core == null) {
         if (owner instanceof Player player) {
            player.displayClientMessage(Component.literal("§cNo Planetary Devastation is active."), true);
         }
      } else if (owner.level() instanceof ServerLevel level) {
         beginDrop(level, owner, instance, skill, core);
      }
   }

   private static void beginDrop(ServerLevel level, LivingEntity owner, ManasSkillInstance instance, Skill skill, PainPlanetaryDevastationHandler.Core core) {
      if (core.phase != 2) {
         core.phase = 2;
         core.dropAge = core.age;
         core.dropY = Math.max(level.getMinBuildHeight() + 3.0, owner.getY() - 8.0);
         releaseSettledMoon(level, core);
         sendPlanet(level, core, 1.0F);
         level.playSound(null, core.center.x, core.center.y, core.center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.2F, 0.45F);
         if (owner instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.pain_lord_of_six_paths.planet_drop").withStyle(ChatFormatting.RED), true
            );
         }
      }
   }

   public static void cancel(LivingEntity owner, ManasSkillInstance instance) {
      PainPlanetaryDevastationHandler.Core core = CORES.remove(owner.getUUID());
      if (core != null && owner.level() instanceof ServerLevel level) {
         sendPlanetStop(level, core);
         discardPieces(core);
      }

      instance.getOrCreateTag().putBoolean("PlanetaryDevastationActive", false);
      instance.markDirty();
   }

   public static void flingGroundBlocks(
      ServerLevel level, LivingEntity owner, ManasSkillInstance instance, Skill skill, double radius, double force, double charge
   ) {
      flingAlmightyPushBlocks(level, owner, instance, skill, true, radius, radius * 0.35, force, charge);
   }

   public static void flingAlmightyPushBlocks(
      ServerLevel level,
      LivingEntity owner,
      ManasSkillInstance instance,
      Skill skill,
      boolean aoe,
      double rangeOrRadius,
      double width,
      double force,
      double charge
   ) {
      boolean grief = canSkillGrief(level, instance, owner, owner.position());
      int max = (int)Mth.lerp(charge, Math.max(24, (Integer)CONFIG.almightyPushMinBlocks.get()), Math.max(160, (Integer)CONFIG.almightyPushMaxBlocks.get()));
      Vec3 origin = owner.position().add(0.0, owner.getBbHeight() * 0.35, 0.0);
      Vec3 look = owner.getLookAngle().normalize();
      int scanRadius = (int)Math.max(8.0, aoe ? rangeOrRadius * 0.75 : rangeOrRadius);
      List<BlockPos> positions = aoe
         ? collectGroundDisk(level, owner.blockPosition(), scanRadius, max)
         : collectLineGround(level, owner.blockPosition(), look, rangeOrRadius, width, max);
      if (!grief) {
         PainSixPathsVfxPayload.send(
            level,
            origin,
            (float)Math.max(10.0, aoe ? rangeOrRadius : Math.min(rangeOrRadius, 90.0)),
            6,
            (float)Math.max(1.0, force),
            owner.getUUID().getLeastSignificantBits() ^ level.getGameTime() ^ 102371745L
         );
      } else {
         int made = 0;

         for (BlockPos pos : positions) {
            if (made >= max) {
               break;
            }

            BlockState state = level.getBlockState(pos);
            if (validMovableState(level, pos, state)) {
               Vec3 from = Vec3.atCenterOf(pos);
               Vec3 rel = from.subtract(origin);
               double along = rel.dot(look);
               if (aoe || !(along < -2.0) && !(along > rangeOrRadius) && !(rel.subtract(look.scale(along)).length() > width * 2.2)) {
                  Vec3 dir = aoe ? rel : look.scale(1.0).add(rel.normalize().scale(0.12));
                  if (dir.lengthSqr() < 0.01) {
                     dir = look;
                  }

                  Vec3 velocity = dir.normalize().scale(Mth.lerp(charge, 1.8, 4.8)).add(0.0, Mth.lerp(charge, 0.55, 2.4), 0.0);
                  if (canSkillGrief(level, instance, owner, from)) {
                     FallingBlockEntity falling = FallingBlockEntity.fall(level, pos, state);
                     falling.setDeltaMovement(velocity);
                     falling.hurtMarked = true;
                  }

                  if (made % 3 == 0) {
                     level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), from.x, from.y, from.z, 10, 0.35, 0.35, 0.35, 0.16);
                  }

                  made++;
               }
            }
         }
      }
   }

   public static void pullUniversalBlocks(
      ServerLevel level, LivingEntity owner, ManasSkillInstance instance, Skill skill, double range, double force, boolean sphere
   ) {
      boolean grief = canSkillGrief(level, instance, owner, owner.position());
      Vec3 anchor = owner.position().add(0.0, owner.getBbHeight() * 0.55, 0.0);
      Vec3 look = owner.getLookAngle().normalize();
      int max = sphere ? 260 : 160;
      List<BlockPos> positions = sphere
         ? collectGroundDisk(level, owner.blockPosition(), (int)Math.min(72.0, range), max)
         : collectLineGround(level, owner.blockPosition(), look, range, 8.0, max);
      if (!grief) {
         PainSixPathsVfxPayload.send(
            level,
            anchor,
            (float)Math.max(10.0, Math.min(110.0, range)),
            7,
            (float)Math.max(1.0, force),
            owner.getUUID().getMostSignificantBits() ^ level.getGameTime() ^ 463121L
         );
      } else {
         int made = 0;

         for (BlockPos pos : positions) {
            if (made >= max) {
               break;
            }

            BlockState state = level.getBlockState(pos);
            if (validMovableState(level, pos, state)) {
               Vec3 from = Vec3.atCenterOf(pos);
               Vec3 to = anchor.subtract(from);
               if (!(to.lengthSqr() > range * range)) {
                  if (!sphere) {
                     Vec3 rel = from.subtract(anchor);
                     double along = rel.dot(look);
                     double side = rel.subtract(look.scale(along)).length();
                     if (along < -1.5 || along > range || side > 7.5) {
                        continue;
                     }
                  }

                  Vec3 velocity = to.normalize().scale(Math.max(0.7, force * 0.25)).add(0.0, 0.25, 0.0);
                  if (canSkillGrief(level, instance, owner, from)) {
                     FallingBlockEntity falling = FallingBlockEntity.fall(level, pos, state);
                     falling.setDeltaMovement(velocity);
                     falling.hurtMarked = true;
                  }

                  if (made % 4 == 0) {
                     level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), from.x, from.y, from.z, 6, 0.25, 0.25, 0.25, 0.1);
                  }

                  made++;
               }
            }
         }
      }
   }

   public static void carveAlmightyCrater(ServerLevel level, LivingEntity owner, ManasSkillInstance instance, Skill skill, double radius, double charge) {
      if (canSkillGrief(level, instance, owner, owner.position())) {
         float power = (float)Mth.clamp(7.0 + charge * 10.0 + radius * 0.045, 8.0, 22.0);
         level.explode(owner, owner.getX(), owner.getY() - 1.0, owner.getZ(), power, ExplosionInteraction.TNT);
      }
   }

   public static boolean canSkillGrief(ServerLevel level, ManasSkillInstance instance, LivingEntity owner, Vec3 pos) {
      return !TensuraGameRules.canSkillGrief(level)
         ? false
         : !((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker()).grief(instance, level, owner, pos.x, pos.y, pos.z).isFalse();
   }

   private static void sendPlanet(ServerLevel level, PainPlanetaryDevastationHandler.Core core, float phase) {
      float baseRadius = Math.max(14.0F, ((Double)CONFIG.planetaryDevastationPlanetRadius.get()).floatValue() * 1.55F);
      float radius = core.grief ? baseRadius : Math.max(baseRadius * 1.45F, 19.8F);
      PainSixPathsVfxPayload.send(level, core.center, radius, 4, core.grief ? phase + 10.0F : phase, core.seed);
   }

   private static void sendPlanetStop(ServerLevel level, PainPlanetaryDevastationHandler.Core core) {
      PainSixPathsVfxPayload.send(
         level, core.center, Math.max(14.0F, ((Double)CONFIG.planetaryDevastationPlanetRadius.get()).floatValue() * 1.55F), 5, 0.0F, core.seed
      );
   }

   private static void collectWorldChunks(ServerLevel level, LivingEntity owner, ManasSkillInstance instance, PainPlanetaryDevastationHandler.Core core) {
      if (core.grief) {
         int maxBlocks = Math.max(9000, (Integer)CONFIG.planetaryDevastationMaxBlocks.get() * 10);
         if (core.removedBlocks < maxBlocks && core.pieces.size() + core.settled.size() < 520) {
            int radius = Math.max(72, (Integer)CONFIG.planetaryDevastationBlockPullRadius.get() + 34);
            int chunksPerScan = Math.max(8, Math.min(18, Math.max(1, (Integer)CONFIG.planetaryDevastationBlocksPerScan.get()) / 2));

            for (int i = 0; i < chunksPerScan && core.removedBlocks < maxBlocks && core.pieces.size() + core.settled.size() < 520; i++) {
               BlockPos anchor = nextChunkAnchor(level, core, radius);
               if (anchor == null) {
                  return;
               }

               PainPlanetaryDevastationHandler.ChunkData chunk = carveChunk(level, owner, instance, core, anchor, maxBlocks);
               if (!chunk.entries.isEmpty()) {
                  FallingBlockEntity falling = FallingBlockEntity.fall(level, anchor, chunk.visualState);
                  falling.noPhysics = true;
                  falling.setNoGravity(true);
                  Vec3 anchorCenter = Vec3.atCenterOf(anchor);
                  Vec3 startMotion = core.center.subtract(anchorCenter);
                  if (startMotion.lengthSqr() < 0.001) {
                     startMotion = new Vec3(0.0, 1.0, 0.0);
                  }

                  falling.setDeltaMovement(startMotion.normalize().scale(1.2));
                  double moonRadius = Math.max(7.0, (Double)CONFIG.planetaryDevastationPlanetRadius.get() * 1.18);
                  Vec3 offset = sphereOffset(core.seed, core.chunkIndex++, moonRadius);
                  core.pieces.add(new PainPlanetaryDevastationHandler.ChunkPiece(falling, chunk.visualState, chunk.entries, offset));
               }
            }
         }
      }
   }

   private static PainPlanetaryDevastationHandler.ChunkData carveChunk(
      ServerLevel level, LivingEntity owner, ManasSkillInstance instance, PainPlanetaryDevastationHandler.Core core, BlockPos anchor, int maxBlocks
   ) {
      int sx = 3 + level.random.nextInt(4);
      int sy = 2 + level.random.nextInt(3);
      int sz = 3 + level.random.nextInt(4);
      List<PainPlanetaryDevastationHandler.BlockEntry> entries = new ArrayList<>();
      BlockState visual = level.getBlockState(anchor);
      if (!validMovableState(level, anchor, visual)) {
         visual = Blocks.DIRT.defaultBlockState();
      }

      for (BlockPos pos : BlockPos.betweenClosed(anchor.offset(-sx, -sy, -sz), anchor.offset(sx, sy, sz))) {
         if (core.removedBlocks >= maxBlocks || entries.size() >= 156) {
            break;
         }

         double shape = Math.pow((double)(pos.getX() - anchor.getX()) / Math.max(1, sx), 2.0)
            + Math.pow((double)(pos.getY() - anchor.getY()) / Math.max(1, sy), 2.0)
            + Math.pow((double)(pos.getZ() - anchor.getZ()) / Math.max(1, sz), 2.0);
         if (!(shape > 1.22) && core.collected.add(pos.immutable())) {
            BlockState state = level.getBlockState(pos);
            if (validMovableState(level, pos, state) && canSkillGrief(level, instance, owner, Vec3.atCenterOf(pos))) {
               if (entries.isEmpty() || pos.equals(anchor)) {
                  visual = state;
               }

               entries.add(new PainPlanetaryDevastationHandler.BlockEntry(relative(pos, anchor), state));
               level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
               core.removedBlocks++;
            }
         }
      }

      return new PainPlanetaryDevastationHandler.ChunkData(visual, entries);
   }

   private static BlockPos relative(BlockPos pos, BlockPos anchor) {
      return new BlockPos(pos.getX() - anchor.getX(), pos.getY() - anchor.getY(), pos.getZ() - anchor.getZ());
   }

   private static BlockPos nextChunkAnchor(ServerLevel level, PainPlanetaryDevastationHandler.Core core, int radius) {
      BlockPos center = core.groundCenter;

      for (int attempt = 0; attempt < 160; attempt++) {
         int n = core.scanCursor++;
         double angle = n * 2.399963229728653;
         double dist = Math.sqrt((double)(n % Math.max(1, radius * radius)) / Math.max(1, radius * radius)) * radius;
         int x = center.getX() + Mth.floor(Math.cos(angle) * dist);
         int z = center.getZ() + Mth.floor(Math.sin(angle) * dist);
         int yBase = center.getY() + level.random.nextInt(Math.max(12, radius / 2)) - Math.max(6, radius / 4);

         for (int dy = 10; dy >= -18; dy--) {
            BlockPos pos = new BlockPos(x, yBase + dy, z);
            if (!core.collected.contains(pos)) {
               BlockState state = level.getBlockState(pos);
               if (validMovableState(level, pos, state)) {
                  return pos.immutable();
               }
            }
         }
      }

      return null;
   }

   private static void moveRealPieces(ServerLevel level, PainPlanetaryDevastationHandler.Core core, boolean dropping) {
      Iterator<PainPlanetaryDevastationHandler.ChunkPiece> iterator = core.pieces.iterator();

      while (iterator.hasNext()) {
         PainPlanetaryDevastationHandler.ChunkPiece piece = iterator.next();
         FallingBlockEntity falling = piece.entity;
         if (falling != null && !falling.isRemoved()) {
            falling.noPhysics = true;
            falling.setNoGravity(true);
            Vec3 target = core.center.add(piece.offset);
            Vec3 to = target.subtract(falling.position());
            double distance = to.length();
            if (!dropping && core.phase == 1 && distance <= 0.95 && settleChunk(level, core, piece, BlockPos.containing(target))) {
               falling.discard();
               iterator.remove();
            } else {
               if (distance > 0.05) {
                  double speed = dropping ? 2.45 : Mth.clamp(distance * 0.18, 0.7, 3.15);
                  Vec3 motion = falling.getDeltaMovement().scale(dropping ? 0.34 : 0.48).add(to.normalize().scale(speed));
                  double cap = dropping ? 5.1 : 4.35;
                  if (motion.length() > cap) {
                     motion = motion.normalize().scale(cap);
                  }

                  falling.setDeltaMovement(motion);
               } else {
                  falling.setDeltaMovement(Vec3.ZERO);
               }

               falling.hurtMarked = true;
            }
         } else {
            iterator.remove();
         }
      }
   }

   private static boolean settleChunk(
      ServerLevel level, PainPlanetaryDevastationHandler.Core core, PainPlanetaryDevastationHandler.ChunkPiece piece, BlockPos base
   ) {
      List<BlockPos> placedPositions = new ArrayList<>();

      for (PainPlanetaryDevastationHandler.BlockEntry entry : piece.entries) {
         BlockPos pos = base.offset(entry.relative);
         if (core.settledPositions.add(pos) && level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, entry.state, 3);
            placedPositions.add(pos.immutable());
         }
      }

      if (placedPositions.isEmpty()) {
         return false;
      }

      core.settled.add(new PainPlanetaryDevastationHandler.SettledChunk(placedPositions, piece.entries, piece.offset, piece.visualState));
      return true;
   }

   private static void releaseSettledMoon(ServerLevel level, PainPlanetaryDevastationHandler.Core core) {
      if (core.grief && !core.settled.isEmpty()) {
         for (PainPlanetaryDevastationHandler.SettledChunk settled : core.settled) {
            BlockPos base = settledBase(settled);
            BlockState visual = settled.visualState;
            if (visual == null || visual.isAir()) {
               visual = Blocks.DIRT.defaultBlockState();
            }

            for (BlockPos pos : settled.positions) {
               if (!level.getBlockState(pos).isAir()) {
                  level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
               }
            }

            FallingBlockEntity falling = FallingBlockEntity.fall(level, base, visual);
            falling.noPhysics = true;
            falling.setNoGravity(true);
            falling.setDeltaMovement(new Vec3(0.0, -0.28, 0.0));
            core.pieces.add(new PainPlanetaryDevastationHandler.ChunkPiece(falling, visual, settled.entries, settled.offset));
         }

         core.settled.clear();
         core.settledPositions.clear();
      }
   }

   private static BlockPos settledBase(PainPlanetaryDevastationHandler.SettledChunk settled) {
      if (settled.positions.isEmpty()) {
         return BlockPos.ZERO;
      }

      long x = 0L;
      long y = 0L;
      long z = 0L;

      for (BlockPos pos : settled.positions) {
         x += pos.getX();
         y += pos.getY();
         z += pos.getZ();
      }

      int size = Math.max(1, settled.positions.size());
      return new BlockPos((int)(x / size), (int)(y / size), (int)(z / size));
   }

   private static void placeRealPlanet(ServerLevel level, PainPlanetaryDevastationHandler.Core core) {
      if (core.grief) {
         Set<BlockPos> placed = new HashSet<>();

         for (PainPlanetaryDevastationHandler.SettledChunk settled : core.settled) {
            for (int i = 0; i < settled.positions.size() && i < settled.entries.size(); i++) {
               BlockPos pos = settled.positions.get(i);
               if (placed.add(pos) && (level.getBlockState(pos).isAir() || level.getBlockState(pos).canBeReplaced())) {
                  level.setBlock(pos, settled.entries.get(i).state, 3);
               }
            }
         }

         for (PainPlanetaryDevastationHandler.ChunkPiece piece : core.pieces) {
            if (piece.entity != null && !piece.entity.isRemoved()) {
               BlockPos base = BlockPos.containing(core.center.add(piece.offset));

               for (PainPlanetaryDevastationHandler.BlockEntry entry : piece.entries) {
                  BlockPos pos = base.offset(entry.relative);
                  if (placed.add(pos) && level.getBlockState(pos).canBeReplaced()) {
                     level.setBlock(pos, entry.state, 3);
                  }
               }

               piece.entity.discard();
            }
         }

         core.pieces.clear();
         core.settled.clear();
         core.settledPositions.clear();
      }
   }

   private static void discardPieces(PainPlanetaryDevastationHandler.Core core) {
      for (PainPlanetaryDevastationHandler.ChunkPiece piece : core.pieces) {
         if (piece.entity != null) {
            piece.entity.discard();
         }
      }

      core.pieces.clear();
      core.settled.clear();
      core.settledPositions.clear();
   }

   private static void pullEntities(ServerLevel level, LivingEntity owner, PainPlanetaryDevastationHandler.Core core, double radius, double force) {
      AABB box = new AABB(core.center, core.center).inflate(radius);
      DamageSource source = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.GRAVITY_PRESS, owner);
      double moonRadius = Math.max(5.0, (Double)CONFIG.planetaryDevastationPlanetRadius.get() * 1.2);

      for (Entity target : level.getEntities(
         (Entity)null, box, targetx -> targetx != owner && targetx.isAlive() && !(targetx instanceof Display) && !(targetx instanceof FallingBlockEntity)
      )) {
         if (!(target instanceof Player player && player.getAbilities().invulnerable)) {
            Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            Vec3 toCenter = core.center.subtract(targetCenter);
            double distance = Math.max(0.001, toCenter.length());
            if (!(distance > radius)) {
               Vec3 direction = toCenter.normalize();
               double normalized = 1.0 - distance / radius;
               if (distance <= moonRadius) {
                  double desiredSpeed = Mth.clamp(distance * 0.1, 0.05, 0.55);
                  target.setDeltaMovement(target.getDeltaMovement().scale(0.35).add(direction.scale(desiredSpeed)));
               } else {
                  double desiredSpeed = 0.1 + normalized * normalized * Math.max(0.65, force);
                  desiredSpeed = Mth.clamp(desiredSpeed, 0.1, target instanceof Projectile ? 4.0 : 1.85);
                  target.setDeltaMovement(target.getDeltaMovement().scale(0.55).add(direction.scale(desiredSpeed)));
               }

               target.fallDistance = 0.0F;
               target.hurtMarked = true;
               target.hasImpulse = true;
               if (target instanceof Projectile projectile) {
                  projectile.hasImpulse = true;
               }

               if (target instanceof LivingEntity living
                  && distance <= moonRadius + 2.0
                  && living.tickCount % Math.max(8, (Integer)CONFIG.planetaryDevastationEntityDamageIntervalTicks.get()) == 0) {
                  living.invulnerableTime = 0;
                  living.hurt(source, ((Double)CONFIG.planetaryDevastationPullDamage.get()).floatValue());
                  living.invulnerableTime = 0;
                  living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), 80, 1, true, false, true));
               }
            }
         }
      }
   }

   private static void detonate(ServerLevel level, LivingEntity owner, ManasSkillInstance instance, PainPlanetaryDevastationHandler.Core core) {
      placeRealPlanet(level, core);
      double radius = Math.max(32.0, (Double)CONFIG.planetaryDevastationExplosionRadius.get() * 1.45);
      AABB box = new AABB(core.center, core.center).inflate(radius);
      DamageSource source = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.GRAVITY_PRESS, owner);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, targetx -> targetx != owner && targetx.isAlive())) {
         double distance = target.position().distanceTo(core.center);
         if (!(distance > radius)) {
            double multiplier = Math.max((Double)CONFIG.planetaryDevastationMinFalloff.get(), 1.0 - distance / radius);
            double health = target.getMaxHealth() * (Double)CONFIG.planetaryDevastationHealthDamageRatio.get();
            double damage = Math.min(
               (Double)CONFIG.planetaryDevastationMaxImpactDamage.get() * 1.35, Math.max((Double)CONFIG.planetaryDevastationImpactDamage.get() * 1.35, health)
            );
            target.invulnerableTime = 0;
            target.hurt(source, (float)(damage * multiplier));
            target.invulnerableTime = 0;
            Vec3 away = target.position().subtract(core.center);
            if (away.lengthSqr() < 0.01) {
               away = new Vec3(0.0, 1.0, 0.0);
            }

            target.setDeltaMovement(
               away.normalize()
                  .scale((Double)CONFIG.planetaryDevastationImpactKnockback.get() + multiplier * 4.5)
                  .add(0.0, (Double)CONFIG.planetaryDevastationImpactLift.get() + multiplier * 2.2, 0.0)
            );
            target.hurtMarked = true;
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY),
                  (Integer)CONFIG.planetaryDevastationDebuffTicks.get(),
                  (Integer)CONFIG.planetaryDevastationFragilityAmplifier.get(),
                  true,
                  false,
                  true
               )
            );
         }
      }

      ExplosionInteraction interaction = core.grief ? ExplosionInteraction.TNT : ExplosionInteraction.NONE;
      float power = (float)Math.min(42.0, Math.max(18.0, (Double)CONFIG.planetaryDevastationExplosionPower.get() * 4.15));
      level.explode(owner, core.center.x, core.center.y, core.center.z, power, interaction);
      if (core.grief) {
         vaporizeMeteorBlocks(level, owner, instance, core, Math.max(18, (int)Math.ceil((Double)CONFIG.planetaryDevastationPlanetRadius.get() * 2.4)));
         double ring = Math.max(12.0, (Double)CONFIG.planetaryDevastationPlanetRadius.get() * 2.05);

         for (int i = 0; i < 10; i++) {
            double a = (Math.PI * 2) * i / 10.0;
            level.explode(
               owner, core.center.x + Math.cos(a) * ring, core.center.y - 1.5, core.center.z + Math.sin(a) * ring, Math.max(10.0F, power * 0.52F), interaction
            );
         }

         for (int i = 0; i < 6; i++) {
            double a = (Math.PI * 2) * (i + 0.5) / 6.0;
            level.explode(
               owner,
               core.center.x + Math.cos(a) * ring * 0.55,
               core.center.y + (i % 2 == 0 ? 7.0 : -7.0),
               core.center.z + Math.sin(a) * ring * 0.55,
               Math.max(8.0F, power * 0.38F),
               interaction
            );
         }
      }

      level.playSound(null, core.center.x, core.center.y, core.center.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 4.4F, 0.34F);
      sendPlanetStop(level, core);
      CORES.remove(owner.getUUID());
      instance.getOrCreateTag().putBoolean("PlanetaryDevastationActive", false);
      instance.markDirty();
   }

   private static void vaporizeMeteorBlocks(
      ServerLevel level, LivingEntity owner, ManasSkillInstance instance, PainPlanetaryDevastationHandler.Core core, int radius
   ) {
      BlockPos center = BlockPos.containing(core.center);
      int radiusSqr = radius * radius;

      for (BlockPos pos : BlockPos.betweenClosed(center.offset(-radius, -radius, -radius), center.offset(radius, radius, radius))) {
         if (!(pos.distToCenterSqr(core.center.x, core.center.y, core.center.z) > radiusSqr)) {
            BlockState state = level.getBlockState(pos);
            if (validMovableState(level, pos, state) && canSkillGrief(level, instance, owner, Vec3.atCenterOf(pos))) {
               level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
         }
      }
   }

   private static List<BlockPos> collectLineGround(ServerLevel level, BlockPos origin, Vec3 look, double range, double width, int max) {
      Set<BlockPos> set = new LinkedHashSet<>();
      Vec3 flat = new Vec3(look.x, 0.0, look.z);
      if (flat.lengthSqr() < 0.001) {
         flat = new Vec3(0.0, 0.0, 1.0);
      }

      flat = flat.normalize();
      Vec3 side = new Vec3(-flat.z, 0.0, flat.x);
      int steps = Math.max(8, (int)(range / 2.0));
      int half = Math.max(2, (int)Math.ceil(width));

      for (int i = 1; i <= steps && set.size() < max * 5; i++) {
         double d = range * i / steps;
         Vec3 lineCenter = Vec3.atCenterOf(origin).add(flat.scale(d));

         for (int sx = -half; sx <= half && set.size() < max * 5; sx++) {
            Vec3 sample = lineCenter.add(side.scale(sx));
            BlockPos base = BlockPos.containing(sample.x, origin.getY(), sample.z);

            for (int y = 3; y >= -7; y--) {
               BlockPos pos = base.offset(0, y, 0);
               if (validMovableState(level, pos, level.getBlockState(pos)) && level.getBlockState(pos.above()).isAir()) {
                  set.add(pos.immutable());
                  break;
               }
            }
         }
      }

      List<BlockPos> positions = new ArrayList<>(set);
      positions.sort(Comparator.comparingDouble(posx -> posx.distSqr(origin)));
      return positions;
   }

   private static List<BlockPos> collectGroundDisk(ServerLevel level, BlockPos origin, int radius, int max) {
      List<BlockPos> positions = new ArrayList<>();

      for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -4, -radius), origin.offset(radius, 2, radius))) {
         if (positions.size() >= max * 4) {
            break;
         }

         double dist = Math.sqrt(pos.distToCenterSqr(origin.getX() + 0.5, pos.getY() + 0.5, origin.getZ() + 0.5));
         if (!(dist > radius) && validMovableState(level, pos, level.getBlockState(pos)) && level.getBlockState(pos.above()).isAir()) {
            positions.add(pos.immutable());
         }
      }

      positions.sort(Comparator.comparingDouble(posx -> posx.distSqr(origin)));
      return positions;
   }

   private static boolean validMovableState(ServerLevel level, BlockPos pos, BlockState state) {
      return !state.isAir() && state.getFluidState().getType() == Fluids.EMPTY && state.getDestroySpeed(level, pos) >= 0.0F;
   }

   private static Vec3 sphereOffset(long seed, int index, double radius) {
      double u = hash(seed, index + 1000) * 2.0 - 1.0;
      double a = hash(seed, index + 2000) * Math.PI * 2.0;
      double d = Math.sqrt(Math.max(0.0, 1.0 - u * u));
      double fill = Math.cbrt(0.08 + hash(seed, index + 3000) * 0.92);
      double r = radius * fill;
      return new Vec3(Math.cos(a) * d * r, u * r, Math.sin(a) * d * r);
   }

   private static double hash(long seed, int index) {
      long value = seed ^ index * 341873128712L;
      value ^= value >>> 33;
      value *= -49064778989728563L;
      value ^= value >>> 33;
      value *= -4265267296055464877L;
      value ^= value >>> 33;
      return (value & 16777215L) / 1.6777215E7;
   }

   private static final class BlockEntry {
      final BlockPos relative;
      final BlockState state;

      BlockEntry(BlockPos relative, BlockState state) {
         this.relative = relative;
         this.state = state;
      }
   }

   private static final class ChunkData {
      final BlockState visualState;
      final List<PainPlanetaryDevastationHandler.BlockEntry> entries;

      ChunkData(BlockState visualState, List<PainPlanetaryDevastationHandler.BlockEntry> entries) {
         this.visualState = visualState;
         this.entries = entries;
      }
   }

   private static final class ChunkPiece {
      final FallingBlockEntity entity;
      final BlockState visualState;
      final List<PainPlanetaryDevastationHandler.BlockEntry> entries;
      final Vec3 offset;

      ChunkPiece(FallingBlockEntity entity, BlockState visualState, List<PainPlanetaryDevastationHandler.BlockEntry> entries, Vec3 offset) {
         this.entity = entity;
         this.visualState = visualState;
         this.entries = new ArrayList<>(entries);
         this.offset = offset;
      }
   }

   private static final class Core {
      final UUID owner;
      final String dimension;
      final Vec3 start;
      final Vec3 sky;
      final BlockPos groundCenter;
      final long seed;
      final ManasSkillInstance instance;
      final Skill skill;
      final Set<BlockPos> collected = new HashSet<>();
      final List<PainPlanetaryDevastationHandler.ChunkPiece> pieces = new ArrayList<>();
      final List<PainPlanetaryDevastationHandler.SettledChunk> settled = new ArrayList<>();
      final Set<BlockPos> settledPositions = new HashSet<>();
      Vec3 center;
      boolean grief;
      int age;
      int phase;
      int dropAge;
      int holdStartAge = -1;
      double dropY;
      int scanCursor;
      int removedBlocks;
      int chunkIndex;
      long lastTick = -1L;

      Core(UUID owner, String dimension, Vec3 start, Vec3 sky, BlockPos groundCenter, long seed, ManasSkillInstance instance, Skill skill) {
         this.owner = owner;
         this.dimension = dimension;
         this.start = start;
         this.sky = sky;
         this.groundCenter = groundCenter;
         this.seed = seed;
         this.instance = instance;
         this.skill = skill;
         this.center = start;
      }
   }

   private static final class SettledChunk {
      final List<BlockPos> positions;
      final List<PainPlanetaryDevastationHandler.BlockEntry> entries;
      final Vec3 offset;
      final BlockState visualState;

      SettledChunk(List<BlockPos> positions, List<PainPlanetaryDevastationHandler.BlockEntry> entries, Vec3 offset, BlockState visualState) {
         this.positions = new ArrayList<>(positions);
         this.entries = new ArrayList<>(entries);
         this.offset = offset;
         this.visualState = visualState;
      }
   }
}
