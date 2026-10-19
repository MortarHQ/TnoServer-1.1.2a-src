package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonKingdomVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class AvalonKingdomHandler implements AvalonModeHandler {
   public static final ResourceKey<Level> AVALON = ResourceKey.create(
      Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon")
   );
   private static final Map<UUID, AvalonKingdomHandler.KingdomState> STATES = new HashMap<>();
   private static final Set<Long> BUILT_ARENAS = new HashSet<>();
   private static final int ENTRY_RETRY_TICKS = 12;
   private static boolean initialized;

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(AvalonKingdomHandler::serverTick);
         NeoForge.EVENT_BUS.addListener(AvalonKingdomHandler::livingDeath);
      }
   }

   @Override
   public String modeId() {
      return "avalon_god_of_eternal_kingdom.kingdom_of_avalon";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.avalon.kingdomCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel sourceLevel) {
         MinecraftServer var13 = sourceLevel.getServer();
         UUID ownerId = entity.getUUID();
         AvalonKingdomHandler.KingdomState previous = STATES.get(ownerId);
         if (previous != null) {
            if (entity.isShiftKeyDown()) {
               STATES.remove(ownerId);
               finish(previous, var13, true);
               instance.setCoolDown(0, mode);
               instance.markDirty();
               return;
            }

            LivingEntity currentOwner = findLiving(var13, ownerId);
            if (previous.entered && currentOwner != null && currentOwner.level().dimension().equals(AVALON)) {
               return;
            }

            STATES.remove(ownerId);
            finish(previous, var13, true);
         } else if (entity.isShiftKeyDown()) {
            instance.setCoolDown(0, mode);
            instance.markDirty();
            return;
         }

         LivingEntity target = RoyalSkillSupport.lookTarget(entity, (Double)MoreSkillsConfigs.GRAND.avalon.kingdomTargetRange.get());
         if (target == null || target == entity) {
            entity.sendSystemMessage(Component.literal("Kingdom of Avalon requires a living enemy in sight.").withStyle(ChatFormatting.RED));
         } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            ServerLevel avalon = var13.getLevel(AVALON);
            if (avalon == null) {
               entity.sendSystemMessage(
                  Component.literal("The Avalon dimension is unavailable. Check the included datapack JSON files.").withStyle(ChatFormatting.RED)
               );
            } else {
               Vec3 center = arenaCenter(ownerId);
               buildArena(avalon, center);
               AvalonKingdomHandler.KingdomState state = new AvalonKingdomHandler.KingdomState(
                  ownerId,
                  target.getUUID(),
                  AvalonKingdomHandler.ReturnPoint.of(entity),
                  AvalonKingdomHandler.ReturnPoint.of(target),
                  center,
                  var13.getTickCount() + (Integer)MoreSkillsConfigs.GRAND.avalon.kingdomDurationTicks.get(),
                  var13.getTickCount() + 12,
                  mode
               );
               STATES.put(ownerId, state);
               attemptEntry(state, var13);
               instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.avalon.kingdomCooldown.get(), mode);
               instance.addMasteryPoint(entity);
               instance.markDirty();
            }
         }
      }
   }

   private static void serverTick(Post event) {
      MinecraftServer server = event.getServer();
      int tick = server.getTickCount();
      Iterator<Entry<UUID, AvalonKingdomHandler.KingdomState>> iterator = STATES.entrySet().iterator();

      while (iterator.hasNext()) {
         AvalonKingdomHandler.KingdomState state = iterator.next().getValue();
         if (!state.entered) {
            if (tick > state.entryDeadline) {
               finish(state, server, true);
               iterator.remove();
            } else {
               attemptEntry(state, server);
            }
         } else {
            LivingEntity owner = findLiving(server, state.owner);
            LivingEntity target = findLiving(server, state.target);
            if (owner == null || !owner.isAlive() || owner.isRemoved()) {
               finish(state, server, true);
               iterator.remove();
            } else if (target != null && target.isAlive() && !target.isRemoved()) {
               if (owner.isShiftKeyDown()) {
                  clearKingdomCooldown(owner, state.mode);
               }

               if (tick >= state.expiresAt) {
                  finish(state, server, true);
                  iterator.remove();
               } else {
                  ServerLevel avalon = server.getLevel(AVALON);
                  if (avalon != null && owner.level().dimension().equals(AVALON) && target.level().dimension().equals(AVALON)) {
                     enforceArena(state, owner, target, avalon, tick);
                  } else {
                     finish(state, server, true);
                     iterator.remove();
                  }
               }
            } else {
               finish(state, server, true);
               iterator.remove();
            }
         }
      }
   }

   private static void livingDeath(LivingDeathEvent event) {
      LivingEntity dead = event.getEntity();
      if (dead.level() instanceof ServerLevel level) {
         AvalonKingdomHandler.KingdomState state = findStateByParticipant(dead.getUUID());
         if (state != null) {
            STATES.remove(state.owner);
            finish(state, level.getServer(), true);
         }
      }
   }

   private static AvalonKingdomHandler.KingdomState findStateByParticipant(UUID participant) {
      AvalonKingdomHandler.KingdomState owned = STATES.get(participant);
      if (owned != null) {
         return owned;
      }

      for (AvalonKingdomHandler.KingdomState state : STATES.values()) {
         if (state.target.equals(participant)) {
            return state;
         }
      }

      return null;
   }

   private static boolean attemptEntry(AvalonKingdomHandler.KingdomState state, MinecraftServer server) {
      ServerLevel avalon = server.getLevel(AVALON);
      if (avalon == null) {
         return false;
      }

      LivingEntity owner = findLiving(server, state.owner);
      LivingEntity target = findLiving(server, state.target);
      if (owner != null && target != null && owner.isAlive() && target.isAlive()) {
         if (!owner.level().dimension().equals(AVALON)) {
            teleport(owner, avalon, state.center.add(-11.0, 1.0, 0.0));
         }

         if (!target.level().dimension().equals(AVALON)) {
            teleport(target, avalon, state.center.add(11.0, 1.0, 0.0));
         }

         owner = findLiving(server, state.owner);
         target = findLiving(server, state.target);
         if (owner != null && target != null) {
            if (owner.level().dimension().equals(AVALON) && target.level().dimension().equals(AVALON)) {
               teleportWithin(owner, state.center.add(-11.0, 1.0, 0.0));
               teleportWithin(target, state.center.add(11.0, 1.0, 0.0));
               if (!state.entered) {
                  state.entered = true;
                  RoyalSkillSupport.markCombat(owner, target);
                  AvalonKingdomVfxPayload.send(avalon, owner, target, state.center.add(0.0, 1.0, 0.0), state.center, 68.0F, 120, 1);
                  avalon.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 8.0F, 1.35F);
               }

               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static void enforceArena(AvalonKingdomHandler.KingdomState state, LivingEntity owner, LivingEntity target, ServerLevel level, int tick) {
      clamp(owner, state.center);
      clamp(target, state.center);
      if (tick % 10 == 0) {
         AABB area = new AABB(state.center, state.center).inflate(70.0);

         for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (living != owner && !RoyalSkillSupport.isAlly(owner, living)) {
               RoyalSkillSupport.applyEffect(living, owner, TensuraMobEffects.getReference(TensuraMobEffects.SILENCE), 30, 1);
               if (!(living instanceof ServerPlayer)) {
                  RoyalSkillSupport.applyEffect(living, owner, TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 30, 1);
               }

               if (tick % 20 == 0) {
                  RoyalSkillSupport.forceUntoggleAndDelay(living, 40);
               }
            }
         }
      }
   }

   private static void clamp(LivingEntity entity, Vec3 center) {
      Vec3 delta = entity.position().subtract(center);
      double horizontal = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
      if (!(horizontal <= 62.0) || !(entity.getY() >= center.y - 4.0) || !(entity.getY() <= center.y + 48.0)) {
         Vec3 direction = horizontal <= 0.001 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(delta.x / horizontal, 0.0, delta.z / horizontal);
         teleportWithin(entity, center.add(direction.scale(48.0)).add(0.0, 1.0, 0.0));
      }
   }

   public static boolean isInsideOwnedKingdom(LivingEntity owner) {
      AvalonKingdomHandler.KingdomState state = STATES.get(owner.getUUID());
      return state != null && state.entered && owner.level().dimension().equals(AVALON) && owner.position().distanceToSqr(state.center) <= 6400.0;
   }

   public static void releaseOwnedBy(LivingEntity owner) {
      if (owner.level() instanceof ServerLevel level) {
         endKingdom(owner.getUUID(), level.getServer(), true);
      }
   }

   private static void endKingdom(UUID owner, MinecraftServer server, boolean returnEntities) {
      AvalonKingdomHandler.KingdomState state = STATES.remove(owner);
      if (state != null) {
         finish(state, server, returnEntities);
      }
   }

   private static void finish(AvalonKingdomHandler.KingdomState state, MinecraftServer server, boolean returnEntities) {
      LivingEntity owner = findLiving(server, state.owner);
      LivingEntity target = findLiving(server, state.target);
      ServerLevel avalon = server.getLevel(AVALON);
      if (state.entered && avalon != null && owner != null && owner.level().dimension().equals(AVALON)) {
         AvalonKingdomVfxPayload.send(avalon, owner, target, state.center, state.center.add(0.0, 24.0, 0.0), 68.0F, 80, 0);
      }

      if (returnEntities) {
         returnEntity(owner, state.ownerReturn, server);
         returnEntity(target, state.targetReturn, server);
      }
   }

   private static void clearKingdomCooldown(LivingEntity owner, int mode) {
      ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(owner)
         .getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get())
         .orElse(null);
      if (instance != null && instance.getCoolDown(mode) > 0) {
         instance.setCoolDown(0, mode);
         instance.markDirty();
      }
   }

   private static LivingEntity findLiving(MinecraftServer server, UUID uuid) {
      for (ServerLevel level : server.getAllLevels()) {
         if (level.getEntity(uuid) instanceof LivingEntity living) {
            return living;
         }
      }

      return null;
   }

   private static void teleport(LivingEntity entity, ServerLevel destination, Vec3 position) {
      if (entity != null && entity.isAlive() && !entity.isRemoved()) {
         entity.stopRiding();
         entity.ejectPassengers();
         entity.setDeltaMovement(Vec3.ZERO);
         entity.resetFallDistance();
         if (entity instanceof ServerPlayer player) {
            player.teleportTo(destination, position.x, position.y, position.z, Set.of(), player.getYRot(), player.getXRot());
         } else {
            entity.teleportTo(destination, position.x, position.y, position.z, Set.of(), entity.getYRot(), entity.getXRot());
         }
      }
   }

   private static void teleportWithin(LivingEntity entity, Vec3 position) {
      if (entity != null && entity.isAlive() && !entity.isRemoved()) {
         entity.teleportTo(position.x, position.y, position.z);
         entity.setDeltaMovement(Vec3.ZERO);
         entity.resetFallDistance();
      }
   }

   private static void returnEntity(LivingEntity entity, AvalonKingdomHandler.ReturnPoint point, MinecraftServer server) {
      if (entity != null && point != null && entity.isAlive() && !entity.isRemoved()) {
         ServerLevel destination = server.getLevel(point.dimension);
         if (destination == null) {
            destination = server.overworld();
         }

         entity.stopRiding();
         entity.ejectPassengers();
         entity.setDeltaMovement(Vec3.ZERO);
         entity.resetFallDistance();
         if (entity instanceof ServerPlayer player) {
            player.teleportTo(destination, point.position.x, point.position.y, point.position.z, Set.of(), point.yaw, point.pitch);
         } else {
            entity.teleportTo(destination, point.position.x, point.position.y, point.position.z, Set.of(), point.yaw, point.pitch);
         }
      }
   }

   private static Vec3 arenaCenter(UUID uuid) {
      long mixed = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
      int slot = Math.floorMod((int)(mixed ^ mixed >>> 32), 8192);
      int xSlot = slot & 127;
      int zSlot = slot >>> 7;
      return new Vec3((xSlot - 64) * 256.0, 96.0, (zSlot - 32) * 256.0);
   }

   private static void buildArena(ServerLevel level, Vec3 center) {
      long key = BlockPos.asLong((int)center.x, (int)center.y, (int)center.z);
      if (BUILT_ARENAS.add(key)) {
         BlockPos origin = BlockPos.containing(center);
         int radius = 34;

         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               double distance = Math.sqrt(x * x + z * z);
               if (!(distance > radius)) {
                  BlockPos floor = origin.offset(x, -1, z);
                  level.setBlock(floor, distance > radius - 2 ? Blocks.CHISELED_QUARTZ_BLOCK.defaultBlockState() : Blocks.SMOOTH_QUARTZ.defaultBlockState(), 2);
                  if (Math.abs(x) == 18 && Math.abs(z) <= 2 || Math.abs(z) == 18 && Math.abs(x) <= 2) {
                     level.setBlock(floor, Blocks.QUARTZ_BRICKS.defaultBlockState(), 2);
                  }
               }
            }
         }

         for (int i = 0; i < 12; i++) {
            double angle = i * Math.PI * 2.0 / 12.0;
            int x = (int)Math.round(Math.cos(angle) * 28.0);
            int z = (int)Math.round(Math.sin(angle) * 28.0);

            for (int y = 0; y < 9; y++) {
               level.setBlock(origin.offset(x, y, z), y == 8 ? Blocks.SEA_LANTERN.defaultBlockState() : Blocks.QUARTZ_PILLAR.defaultBlockState(), 2);
            }
         }
      }
   }

   @Override
   public void cleanup(LivingEntity entity) {
      releaseOwnedBy(entity);
   }

   private static final class KingdomState {
      private final UUID owner;
      private final UUID target;
      private final AvalonKingdomHandler.ReturnPoint ownerReturn;
      private final AvalonKingdomHandler.ReturnPoint targetReturn;
      private final Vec3 center;
      private final long expiresAt;
      private final long entryDeadline;
      private final int mode;
      private boolean entered;

      private KingdomState(
         UUID owner,
         UUID target,
         AvalonKingdomHandler.ReturnPoint ownerReturn,
         AvalonKingdomHandler.ReturnPoint targetReturn,
         Vec3 center,
         long expiresAt,
         long entryDeadline,
         int mode
      ) {
         this.owner = owner;
         this.target = target;
         this.ownerReturn = ownerReturn;
         this.targetReturn = targetReturn;
         this.center = center;
         this.expiresAt = expiresAt;
         this.entryDeadline = entryDeadline;
         this.mode = mode;
      }
   }

   private record ReturnPoint(ResourceKey<Level> dimension, Vec3 position, float yaw, float pitch) {
      private static AvalonKingdomHandler.ReturnPoint of(Entity entity) {
         return new AvalonKingdomHandler.ReturnPoint(entity.level().dimension(), entity.position(), entity.getYRot(), entity.getXRot());
      }
   }
}
