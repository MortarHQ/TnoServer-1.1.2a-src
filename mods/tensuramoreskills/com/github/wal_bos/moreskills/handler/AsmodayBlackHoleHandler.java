package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.net.msg.AsmodayBlackHolePayload;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class AsmodayBlackHoleHandler {
   private static final Map<UUID, AsmodayBlackHoleHandler.BlackHoleState> BLACK_HOLES = new ConcurrentHashMap<>();
   public static final ResourceLocation CHRONO_DIVINITY = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "chrono_divinity");
   private static final double VISUAL_DISTANCE = 1000.0;
   private static final double EFFECT_RADIUS_MULTIPLIER = 1.85;
   private static final double BASE_PULL = 0.2;
   private static final double CLOSE_PULL = 1.8;
   private static final double CORE_PULL = 4.5;
   private static final double MAX_MOTION = 6.75;
   private static final float BASE_DAMAGE = 45000.0F;
   private static final float CORE_DAMAGE = 120000.0F;
   private static final float FINAL_DAMAGE = 260000.0F;
   private static final float FORCE_MIN_DAMAGE = 5000.0F;

   private AsmodayBlackHoleHandler() {
   }

   public static void spawn(ServerLevel level, LivingEntity owner, Vec3 center, float radius, int duration) {
      spawn(level, owner, center, radius, duration, true);
   }

   public static void spawn(ServerLevel level, LivingEntity owner, Vec3 center, float radius, int duration, boolean mastered) {
      UUID id = UUID.randomUUID();
      UUID ownerId = owner == null ? null : owner.getUUID();
      AsmodayBlackHoleHandler.BlackHoleState state = new AsmodayBlackHoleHandler.BlackHoleState(
         id, level.dimension().location().toString(), ownerId, center, Math.max(8.0F, radius), Math.max(20, duration), mastered
      );
      BLACK_HOLES.put(id, state);
      if (owner != null) {
         AttributeHelper.addPermanentAttributeIfHigher(
            owner, TensuraAttributes.RESISTANCE_DEGRADATION, CHRONO_DIVINITY, mastered ? 2.25 : 1.25, Operation.ADD_VALUE
         );
         AttributeHelper.addPermanentAttributeIfHigher(
            owner, TensuraAttributes.SPACE_RESIST_DEGRADATION, CHRONO_DIVINITY, mastered ? 2.25 : 1.25, Operation.ADD_VALUE
         );
      }

      AsmodayBlackHolePayload payload = new AsmodayBlackHolePayload(id, center.x, center.y, center.z, state.radius, state.duration);

      for (ServerPlayer player : level.players()) {
         if (player.position().distanceTo(center) <= 1000.0) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }

      level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 9.0F, 0.28F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 7.0F, 0.45F);
      level.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 7.0F, 0.36F);
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      if (!BLACK_HOLES.isEmpty()) {
         Iterator<AsmodayBlackHoleHandler.BlackHoleState> iterator = BLACK_HOLES.values().iterator();

         while (iterator.hasNext()) {
            AsmodayBlackHoleHandler.BlackHoleState state = iterator.next();
            ServerLevel level = getLevel(event.getServer(), state.dimension);
            if (level == null) {
               removeOwnerDegradation(event.getServer(), state);
               iterator.remove();
            } else {
               state.age++;
               if (state.age > state.duration) {
                  finalCrush(level, state);
                  removeOwnerDegradation(event.getServer(), state);
                  iterator.remove();
               } else {
                  tickBlackHole(level, state);
               }
            }
         }
      }
   }

   private static void tickBlackHole(ServerLevel level, AsmodayBlackHoleHandler.BlackHoleState state) {
      double radius = state.radius;
      double effectRadius = radius * 1.85;
      Vec3 center = state.center;
      AABB area = new AABB(center, center).inflate(effectRadius);
      DamageSource damageSource = createSpaceDamage(level, state);

      for (Entity entity : level.getEntities((Entity)null, area, entityx -> canAffect(level, state, entityx))) {
         LivingEntity living = (LivingEntity)entity;
         Vec3 entityCenter = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         Vec3 toCenter = center.subtract(entityCenter);
         double distance = Math.max(0.001, toCenter.length());
         if (!(distance > effectRadius)) {
            double normalized = Math.max(0.0, 1.0 - distance / effectRadius);
            double core = Math.max(0.0, 1.0 - distance / Math.max(4.0, radius * 0.2));
            double pull = 0.2 + normalized * normalized * 1.8 + core * core * 4.5;
            if (living instanceof Mob) {
               pull *= 1.25;
            }

            Vec3 motion = living.getDeltaMovement().scale(0.42).add(toCenter.normalize().scale(pull));
            if (motion.length() > 6.75) {
               motion = motion.normalize().scale(6.75);
            }

            living.setDeltaMovement(motion);
            living.hurtMarked = true;
            living.fallDistance = 0.0F;
            if (distance < radius * 0.18) {
               living.setDeltaMovement(living.getDeltaMovement().scale(0.2).add(toCenter.normalize().scale(2.1)));
            }

            if (state.age % 5 == 0) {
               float damage = 45000.0F;
               damage += (float)(normalized * 30000.0);
               damage += (float)(core * 120000.0);
               if (distance < radius * 0.12) {
                  damage *= 2.25F;
               }

               hurtLiving(level, state, living, damageSource, damage);
            }
         }
      }

      if (state.age % 30 == 0) {
         level.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 6.5F, 0.36F);
      }

      if (state.age % 100 == 0) {
         level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 5.5F, 0.22F);
      }
   }

   private static void finalCrush(ServerLevel level, AsmodayBlackHoleHandler.BlackHoleState state) {
      Vec3 center = state.center;
      double radius = state.radius * 0.75;
      AABB area = new AABB(center, center).inflate(radius);
      DamageSource damageSource = createSpaceDamage(level, state);

      for (Entity entity : level.getEntities((Entity)null, area, entityx -> canAffect(level, state, entityx))) {
         LivingEntity living = (LivingEntity)entity;
         Vec3 entityCenter = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         Vec3 toCenter = center.subtract(entityCenter);
         double distance = Math.max(0.001, toCenter.length());
         double normalized = Math.max(0.0, 1.0 - distance / radius);
         living.setDeltaMovement(toCenter.normalize().scale(4.0 + normalized * 3.0));
         living.hurtMarked = true;
         living.fallDistance = 0.0F;
         float damage = 260000.0F + (float)(normalized * 260000.0 * 1.5);
         hurtLiving(level, state, living, damageSource, damage);
      }

      level.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 10.0F, 0.22F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 10.0F, 0.18F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 8.0F, 0.35F);
   }

   private static void hurtLiving(ServerLevel level, AsmodayBlackHoleHandler.BlackHoleState state, LivingEntity living, DamageSource damageSource, float damage) {
      if (canAffect(level, state, living)) {
         if (!(living instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            float before = living.getHealth();
            living.invulnerableTime = 0;
            living.hurt(damageSource, damage);
            living.invulnerableTime = 0;
            if (living.isAlive() && living.getHealth() >= before - 0.01F) {
               forceHealthDamage(level, state, living, damageSource, Math.max(5000.0F, damage * 0.35F));
            }
         }
      }
   }

   private static void forceHealthDamage(
      ServerLevel level, AsmodayBlackHoleHandler.BlackHoleState state, LivingEntity living, DamageSource damageSource, float damage
   ) {
      if (canAffect(level, state, living)) {
         if (!(living instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            float newHealth = living.getHealth() - damage;
            living.invulnerableTime = 0;
            if ((state.owner == null ? null : level.getEntity(state.owner)) instanceof LivingEntity livingOwner) {
               living.setLastHurtByMob(livingOwner);
            }

            if (newHealth <= 0.0F) {
               living.setAbsorptionAmount(0.0F);
               living.setHealth(0.0F);
               living.die(damageSource);
            } else {
               living.setHealth(newHealth);
               living.hurtMarked = true;
            }
         }
      }
   }

   private static DamageSource createSpaceDamage(ServerLevel level, AsmodayBlackHoleHandler.BlackHoleState state) {
      Entity owner = state.owner == null ? null : level.getEntity(state.owner);
      return TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.SPACE_ELEMENTAL, owner);
   }

   private static boolean canAffect(ServerLevel level, AsmodayBlackHoleHandler.BlackHoleState state, Entity entity) {
      if (entity == null || !entity.isAlive() || entity.isSpectator()) {
         return false;
      }

      if (!(entity instanceof Player) && !(entity instanceof Mob)) {
         return false;
      }

      if (!(entity instanceof LivingEntity)) {
         return false;
      }

      if (isProtectedEntity(entity)) {
         return false;
      }

      if (entity instanceof FeldwayAwakenedEntity) {
         return false;
      }

      if (entity instanceof ItemFrame) {
         return false;
      }

      if (entity instanceof ArmorStand) {
         return false;
      }

      if (state.owner != null && entity.getUUID().equals(state.owner)) {
         return false;
      }

      if (!(entity instanceof Player player && (player.isCreative() || player.isSpectator()))) {
         Entity owner = state.owner == null ? null : level.getEntity(state.owner);
         if (owner != null) {
            if (entity.isAlliedTo(owner) || owner.isAlliedTo(entity)) {
               return false;
            }

            if (entity instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null && ownable.getOwnerUUID().equals(owner.getUUID())) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean isProtectedEntity(Entity entity) {
      ResourceLocation typeId = EntityType.getKey(entity.getType());
      String namespace = typeId.getNamespace().toLowerCase(Locale.ROOT);
      String path = typeId.getPath().toLowerCase(Locale.ROOT);
      String full = typeId.toString().toLowerCase(Locale.ROOT);
      return containsProtectedTerm(namespace) || containsProtectedTerm(path) || containsProtectedTerm(full);
   }

   private static boolean containsProtectedTerm(String value) {
      return value.contains("corpse")
         || value.contains("playercorpse")
         || value.contains("player_corpse")
         || value.contains("grave")
         || value.contains("gravestone")
         || value.contains("tomb")
         || value.contains("tombstone")
         || value.contains("deathchest")
         || value.contains("death_chest")
         || value.contains("deathstorage")
         || value.contains("death_storage")
         || value.contains("body")
         || value.contains("dead_body")
         || value.contains("soulbound")
         || value.contains("yigd")
         || value.contains("corail");
   }

   public static boolean hasActiveBlackHole(LivingEntity owner) {
      if (owner == null) {
         return false;
      }

      UUID ownerId = owner.getUUID();

      for (AsmodayBlackHoleHandler.BlackHoleState state : BLACK_HOLES.values()) {
         if (state.owner != null && state.owner.equals(ownerId) && state.age <= state.duration) {
            return true;
         }
      }

      return false;
   }

   private static ServerLevel getLevel(MinecraftServer server, String dimension) {
      for (ServerLevel serverLevel : server.getAllLevels()) {
         if (serverLevel.dimension().location().toString().equals(dimension)) {
            return serverLevel;
         }
      }

      return null;
   }

   private static void removeOwnerDegradation(MinecraftServer server, AsmodayBlackHoleHandler.BlackHoleState state) {
      if (state.owner != null) {
         for (ServerLevel serverLevel : server.getAllLevels()) {
            if (serverLevel.getEntity(state.owner) instanceof LivingEntity living) {
               AttributeHelper.removeAttribute(living, TensuraAttributes.RESISTANCE_DEGRADATION, CHRONO_DIVINITY);
               AttributeHelper.removeAttribute(living, TensuraAttributes.SPACE_RESIST_DEGRADATION, CHRONO_DIVINITY);
               return;
            }
         }
      }
   }

   private static final class BlackHoleState {
      private final UUID id;
      private final String dimension;
      private final UUID owner;
      private final Vec3 center;
      private final float radius;
      private final int duration;
      private final boolean mastered;
      private int age;

      private BlackHoleState(UUID id, String dimension, UUID owner, Vec3 center, float radius, int duration, boolean mastered) {
         this.id = id;
         this.dimension = dimension;
         this.owner = owner;
         this.center = center;
         this.radius = radius;
         this.duration = duration;
         this.mastered = mastered;
      }
   }
}
