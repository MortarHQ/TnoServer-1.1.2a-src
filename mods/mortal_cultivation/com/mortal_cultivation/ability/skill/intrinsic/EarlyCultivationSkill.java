package com.mortal_cultivation.ability.skill.intrinsic;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class EarlyCultivationSkill extends LegacySkill {
   private static final double MAGICULE_COST = 50.0;
   private static final double FIREBALL_SPEED = 1.5;
   private static final float EXPLOSION_POWER = 2.0F;
   private static final long MAX_FIREBALL_LIFETIME = 5000L;
   private static final double MAX_FLIGHT_DISTANCE = 50.0;
   private static final Map<UUID, EarlyCultivationSkill.FireballInfo> SKILL_FIREBALLS = new HashMap<>();
   private static final Map<UUID, SmallFireball> FIREBALL_ENTITIES = new HashMap<>();

   public EarlyCultivationSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/intrinsic/early.png");
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 50.0;
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         Level level = player.level();
         if (EnergyHelper.isOutOfMagiculeConsuming(player, 50.0) <= 0.0) {
            instance.setCoolDown(8, 0);
            Vec3 lookVec = player.getLookAngle();
            Vec3 spawnPos = new Vec3(player.getX() + lookVec.x * 0.5, player.getEyeY() - 0.1, player.getZ() + lookVec.z * 0.5);
            SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
            Vec3 velocity = new Vec3(lookVec.x * 1.5, lookVec.y * 1.5, lookVec.z * 1.5);
            fireball.setDeltaMovement(velocity);
            fireball.setPos(spawnPos);
            fireball.setNoGravity(true);
            fireball.setCustomName(Component.translatable("mortal_cultivation.skill.early_cultivation.fireball"));
            fireball.setCustomNameVisible(false);
            fireball.setOwner(player);
            level.addFreshEntity(fireball);
            UUID id = fireball.getUUID();
            fireball.getPersistentData().putBoolean("mortal_cultivation.is_skill_fireball", true);
            SKILL_FIREBALLS.put(id, new EarlyCultivationSkill.FireballInfo(System.currentTimeMillis(), velocity.normalize(), spawnPos));
            FIREBALL_ENTITIES.put(id, fireball);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.addMasteryPoint(instance, player);
         }
      }
   }

   @SubscribeEvent
   public static void onProjectileImpact(ProjectileImpactEvent event) {
      Entity projectile = event.getProjectile();
      if (projectile instanceof SmallFireball && SKILL_FIREBALLS.containsKey(projectile.getUUID())) {
         HitResult hitResult = event.getRayTraceResult();
         Level level = projectile.level();
         if (level.isClientSide) {
            return;
         }

         Vec3 hitPos;
         if (hitResult.getType() == Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult)hitResult;
            Entity target = entityHit.getEntity();
            hitPos = target.position();
            if (target instanceof LivingEntity) {
               target.hurt(target.level().damageSources().inFire(), 5.0F);
            }
         } else {
            hitPos = hitResult.getLocation();
         }

         createExplosion(level, projectile, hitPos.x, hitPos.y, hitPos.z);
         UUID fireballId = projectile.getUUID();
         SKILL_FIREBALLS.remove(fireballId);
         FIREBALL_ENTITIES.remove(fireballId);
         projectile.remove(RemovalReason.KILLED);
      }
   }

   private static void createExplosion(Level level, Entity entity, double x, double y, double z) {
      if (!level.isClientSide) {
         level.explode(entity, x, y, z, 2.0F, false, ExplosionInteraction.BLOCK);
      }
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      if (event.getServer().getTickCount() % 10 == 0) {
         long currentTime = System.currentTimeMillis();
         Iterator<Entry<UUID, EarlyCultivationSkill.FireballInfo>> it = SKILL_FIREBALLS.entrySet().iterator();

         while (it.hasNext()) {
            Entry<UUID, EarlyCultivationSkill.FireballInfo> entry = it.next();
            EarlyCultivationSkill.FireballInfo info = entry.getValue();
            if (currentTime - info.creationTime > 5000L) {
               FIREBALL_ENTITIES.remove(entry.getKey());
               it.remove();
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityTick(net.neoforged.neoforge.event.tick.LevelTickEvent.Post event) {
      if (!event.getLevel().isClientSide() && event.getLevel().getGameTime() % 20L == 0L) {
         long currentTime = System.currentTimeMillis();
         Iterator<Entry<UUID, SmallFireball>> it = FIREBALL_ENTITIES.entrySet().iterator();

         while (it.hasNext()) {
            Entry<UUID, SmallFireball> entry = it.next();
            UUID id = entry.getKey();
            SmallFireball fireball = entry.getValue();
            EarlyCultivationSkill.FireballInfo info = SKILL_FIREBALLS.get(id);
            if (info != null && !fireball.isRemoved()) {
               double distanceSq = fireball.distanceToSqr(info.startPosition.x, info.startPosition.y, info.startPosition.z);
               if (distanceSq > 2500.0) {
                  createExplosion(fireball.level(), fireball, fireball.getX(), fireball.getY(), fireball.getZ());
                  SKILL_FIREBALLS.remove(id);
                  fireball.remove(RemovalReason.KILLED);
                  it.remove();
               } else if (currentTime - info.creationTime > 5000L) {
                  createExplosion(fireball.level(), fireball, fireball.getX(), fireball.getY(), fireball.getZ());
                  SKILL_FIREBALLS.remove(id);
                  fireball.remove(RemovalReason.KILLED);
                  it.remove();
               } else {
                  fireball.setDeltaMovement(info.direction.x * 1.5, info.direction.y * 1.5, info.direction.z * 1.5);
                  fireball.hasImpulse = true;
               }
            } else {
               it.remove();
               SKILL_FIREBALLS.remove(id);
            }
         }
      }
   }

   private static class FireballInfo {
      final long creationTime;
      final Vec3 direction;
      final Vec3 startPosition;

      FireballInfo(long time, Vec3 dir, Vec3 pos) {
         this.creationTime = time;
         this.direction = dir;
         this.startPosition = pos;
      }
   }
}
