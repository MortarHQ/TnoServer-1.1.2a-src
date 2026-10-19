package it.hurts.sskirillss.relics.entities;

import it.hurts.octostudios.octolib.module.particle.trail.EntityTrailProvider;
import it.hurts.sskirillss.relics.entities.misc.ITargetableEntity;
import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.sync.S2CEntityTargetPacket;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ShadowGlaiveEntity extends ThrowableProjectile implements ITargetableEntity {
   private static final EntityDataAccessor<Integer> MAX_BOUNCES = SynchedEntityData.defineId(ShadowGlaiveEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.defineId(ShadowGlaiveEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(ShadowGlaiveEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> CHANCE = SynchedEntityData.defineId(ShadowGlaiveEntity.class, EntityDataSerializers.FLOAT);
   private Set<String> bouncedTargets = new HashSet<>();
   private List<String> blacklistedTargets = new ArrayList<>();
   @Nullable
   private LivingEntity currentTarget = null;
   @Nullable
   private LivingEntity lastTarget = null;

   public ShadowGlaiveEntity(EntityType<? extends ShadowGlaiveEntity> type, Level level) {
      super(type, level);
   }

   public void setMaxBounces(int maxBounces) {
      this.getEntityData().set(MAX_BOUNCES, maxBounces);
   }

   public int getMaxBounces() {
      return (Integer)this.getEntityData().get(MAX_BOUNCES);
   }

   public void setBounces(int bounces) {
      this.getEntityData().set(BOUNCES, bounces);
   }

   public int getBounces() {
      return (Integer)this.getEntityData().get(BOUNCES);
   }

   public void addBounces(int bounces) {
      this.setBounces(Math.clamp(this.getBounces() + bounces, 0, this.getMaxBounces()));
   }

   public void setDamage(float damage) {
      this.getEntityData().set(DAMAGE, damage);
   }

   public float getDamage() {
      return (Float)this.getEntityData().get(DAMAGE);
   }

   public void setChance(float chance) {
      this.getEntityData().set(CHANCE, chance);
   }

   public float getChance() {
      return (Float)this.getEntityData().get(CHANCE);
   }

   public List<LivingEntity> locateNearestTargets() {
      return EntityUtils.gatherPotentialTargets(this, LivingEntity.class, 16.0)
         .filter(
            entity -> (this.lastTarget == null || !this.lastTarget.getStringUUID().equals(entity.getStringUUID()))
               && !(this.getOwner() instanceof Player player && EntityUtils.isAlliedTo(player, entity))
         )
         .collect(Collectors.toList());
   }

   public void tick() {
      super.tick();
      Level level = this.getCommandSenderWorld();
      Vec3 particleCenter = this.position().add(this.getDeltaMovement().scale(-1.0));

      for (int i = 0; i < 5; i++) {
         level.addParticle(
            ParticleUtils.constructSimpleSpark(
               new Color(50 + this.random.nextInt(100), 0, 150 + this.random.nextInt(100)),
               0.1F + this.random.nextFloat() * 0.15F,
               5 + this.random.nextInt(10),
               0.85F
            ),
            particleCenter.x() + MathUtils.randomFloat(this.random) * 0.25F,
            particleCenter.y(),
            particleCenter.z() + MathUtils.randomFloat(this.random) * 0.25F,
            0.0,
            0.0,
            0.0
         );
      }

      LivingEntity currentTarget = this.getTarget();
      if (currentTarget != null && (this.position().distanceTo(currentTarget.position()) >= 16.0 || currentTarget.isDeadOrDying())) {
         currentTarget = null;
      }

      if (!level.isClientSide()) {
         LivingEntity potentialTarget = null;
         List<LivingEntity> candidateEntities = this.locateNearestTargets();
         candidateEntities.removeIf(entityx -> this.blacklistedTargets.contains(entityx.getStringUUID()));
         List<LivingEntity> targetEntities = candidateEntities.stream().filter(entityx -> {
            String uuid = entityx.getStringUUID();
            return !this.bouncedTargets.contains(uuid) && !this.blacklistedTargets.contains(uuid);
         }).toList();
         if (!targetEntities.isEmpty()) {
            potentialTarget = targetEntities.getFirst();
         } else if (!candidateEntities.isEmpty()) {
            this.bouncedTargets.clear();
            potentialTarget = candidateEntities.getFirst();
         }

         if (potentialTarget != null && (currentTarget == null || !currentTarget.getStringUUID().equals(potentialTarget.getStringUUID()))) {
            NetworkHandler.sendToClientsTrackingEntity(new S2CEntityTargetPacket(this.getId(), potentialTarget.getId()), this);
            this.setTarget(potentialTarget);
            currentTarget = potentialTarget;
         }
      }

      if (currentTarget != null && !currentTarget.isDeadOrDying() && this.tickCount < 250 && this.getBounces() < this.getMaxBounces()) {
         if (this.getEyePosition().distanceTo(currentTarget.getEyePosition()) <= 1.5) {
            currentTarget.invulnerableTime = 0;
            if (currentTarget.hurt(level.damageSources().thrown(this, this.getOwner()), this.getDamage())) {
               this.bouncedTargets.add(currentTarget.getStringUUID());
               this.lastTarget = currentTarget;
               this.setTarget(null);
               this.addBounces(1);
               if (this.random.nextDouble() <= this.getChance()) {
                  ShadowGlaiveEntity entity = new ShadowGlaiveEntity((EntityType<? extends ShadowGlaiveEntity>)EntityRegistry.SHADOW_GLAIVE.get(), level);
                  entity.setMaxBounces(this.getMaxBounces());
                  entity.setBounces(this.getBounces());
                  entity.setPos(this.getEyePosition());
                  entity.setDamage(this.getDamage());
                  entity.setOwner(this.getOwner());
                  level.addFreshEntity(entity);
               }
            } else {
               this.blacklistedTargets.add(currentTarget.getStringUUID());
               this.setTarget(null);
            }
         } else {
            this.setDeltaMovement(currentTarget.getEyePosition().subtract(this.getEyePosition()).normalize());
         }
      } else {
         if (!level.isClientSide()) {
            this.discard();
         }
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(MAX_BOUNCES, 10);
      builder.define(BOUNCES, 0);
      builder.define(DAMAGE, 1.0F);
      builder.define(CHANCE, -1.0F);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("max_bounces", this.getMaxBounces());
      tag.putInt("bounces", this.getBounces());
      tag.putFloat("damage", this.getDamage());
      tag.putFloat("chance", this.getChance());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setMaxBounces(tag.getInt("max_bounces"));
      this.setBounces(tag.getInt("bounces"));
      this.setDamage(tag.getFloat("damage"));
      this.setChance(tag.getFloat("chance"));
   }

   public boolean isPushedByFluid() {
      return false;
   }

   protected double getDefaultGravity() {
      return 0.0;
   }

   @Nullable
   @Override
   public LivingEntity getTarget() {
      return this.currentTarget;
   }

   @Override
   public void setTarget(LivingEntity target) {
      this.currentTarget = target;
   }

   public Set<String> getBouncedTargets() {
      return this.bouncedTargets;
   }

   @OnlyIn(Dist.CLIENT)
   public static class TrailProvider extends EntityTrailProvider<ShadowGlaiveEntity> {
      public TrailProvider(ShadowGlaiveEntity entity) {
         super(entity);
      }

      public Vec3 getTrailPosition(float partialTicks) {
         return ((ShadowGlaiveEntity)this.entity).getPosition(partialTicks).add(((ShadowGlaiveEntity)this.entity).getDeltaMovement().scale(-1.0));
      }

      public int getTrailUpdateFrequency() {
         return 1;
      }

      public boolean isTrailAlive() {
         return ((ShadowGlaiveEntity)this.entity).isAlive();
      }

      public boolean isTrailGrowing() {
         return ((ShadowGlaiveEntity)this.entity).getKnownMovement().length() >= 0.1F;
      }

      public int getTrailMaxLength() {
         return 5;
      }

      public int getTrailFadeInColor() {
         return -65281;
      }

      public int getTrailFadeOutColor() {
         return -2147483393;
      }

      public double getTrailScale() {
         return 0.15F;
      }
   }
}
