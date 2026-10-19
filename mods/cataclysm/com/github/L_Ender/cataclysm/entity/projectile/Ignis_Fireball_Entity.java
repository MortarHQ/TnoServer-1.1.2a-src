package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.util.CustomExplosion.IgnisExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class Ignis_Fireball_Entity extends CMAbstractHurtingProjectile {
   private static final EntityDataAccessor<Boolean> SOUL = SynchedEntityData.defineId(Ignis_Fireball_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> FIRED = SynchedEntityData.defineId(Ignis_Fireball_Entity.class, EntityDataSerializers.BOOLEAN);
   private int timer;
   private Vec3[] trailPositions = new Vec3[64];
   private int trailPointer = -1;

   public Ignis_Fireball_Entity(EntityType<? extends Ignis_Fireball_Entity> type, Level level) {
      super(type, level);
   }

   public Ignis_Fireball_Entity(Level level, LivingEntity entity, double x, double y, double z) {
      super((EntityType<? extends CMAbstractHurtingProjectile>)ModEntities.IGNIS_FIREBALL.get(), entity, x, y, z, level);
   }

   public Ignis_Fireball_Entity(Level worldIn, LivingEntity entity) {
      this((EntityType<? extends Ignis_Fireball_Entity>)ModEntities.IGNIS_FIREBALL.get(), worldIn);
      this.setOwner(entity);
   }

   public boolean isOnFire() {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.timer--;
         if (this.timer <= 0 && !this.getFired()) {
            this.setFired(true);
         }
      }

      if (this.timer < -160) {
         this.discard();
      }

      if (this.timer == 0 || this.timer == -40) {
         Entity entity = this.getOwner();
         if (entity instanceof Mob && ((Mob)entity).getTarget() != null) {
            LivingEntity target = ((Mob)entity).getTarget();
            if (target == null) {
               this.discard();
            }

            float speed = this.isSoul() ? 0.25F : 0.2F;
            double dx = target.getX() - this.getX();
            double dy = target.getY() + target.getBbHeight() * 0.5F - this.getY();
            double dz = target.getZ() - this.getZ();
            double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= d;
            dy /= d;
            dz /= d;
            this.xPower = dx * speed;
            this.yPower = dy * speed;
            this.zPower = dz * speed;
         }
      }

      Vec3 trailAt = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
      if (this.trailPointer == -1) {
         Vec3 backAt = trailAt;

         for (int i = 0; i < this.trailPositions.length; i++) {
            this.trailPositions[i] = backAt;
         }
      }

      if (++this.trailPointer == this.trailPositions.length) {
         this.trailPointer = 0;
      }

      this.trailPositions[this.trailPointer] = trailAt;
   }

   public void setUp(int delay) {
      this.setFired(false);
      this.timer = delay;
   }

   protected void onHitEntity(EntityHitResult p_37626_) {
      super.onHitEntity(p_37626_);
      Entity entity = p_37626_.getEntity();
      Entity shooter = this.getOwner();
      if (this.level() instanceof ServerLevel serverlevel
         && this.getFired()
         && !(entity instanceof Ignis_Fireball_Entity)
         && !(entity instanceof Ignis_Abyss_Fireball_Entity)
         && !(entity instanceof Cm_Falling_Block_Entity)
         && (!(entity instanceof Ignis_Entity) || !(shooter instanceof Ignis_Entity))) {
         boolean flag;
         if (shooter instanceof LivingEntity owner) {
            DamageSource damagesource = this.damageSources().mobProjectile(this, owner);
            float damage = this.isSoul() ? 8.0F : 6.0F;
            if (entity instanceof LivingEntity) {
               flag = entity.hurt(this.damageSources().mobProjectile(this, owner), damage + ((LivingEntity)entity).getMaxHealth() * 0.07F);
            } else {
               flag = entity.hurt(this.damageSources().mobProjectile(this, owner), damage);
            }

            if (flag) {
               if (entity.isAlive()) {
                  EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
               }

               if (owner instanceof Ignis_Entity) {
                  owner.heal(5.0F * (float)CMCommonConfig.Ignis.HealingMultiplier);
               } else {
                  owner.heal(5.0F);
               }
            }
         } else {
            flag = entity.hurt(this.damageSources().magic(), 5.0F);
         }

         IgnisExplosion explosion = new IgnisExplosion(this.level(), this, null, null, this.getX(), this.getY(), this.getZ(), 1.0F, true, BlockInteraction.KEEP);
         explosion.explode();
         explosion.finalizeExplosion(this.isSoul() ? 2 : 1, 0.35);
         this.discard();
         if (flag && entity instanceof LivingEntity) {
            MobEffectInstance effectinstance1 = ((LivingEntity)entity).getEffect(ModEffect.EFFECTBLAZING_BRAND);
            int i = 2;
            if (effectinstance1 != null) {
               i += effectinstance1.getAmplifier();
               ((LivingEntity)entity).removeEffectNoUpdate(ModEffect.EFFECTBLAZING_BRAND);
            } else {
               i--;
            }

            i = Mth.clamp(i, 0, 4);
            MobEffectInstance effectinstance = new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND, 200, i, false, false, true);
            ((LivingEntity)entity).addEffect(effectinstance);
         }
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.level().isClientSide && this.getFired()) {
         IgnisExplosion explosion = new IgnisExplosion(this.level(), this, null, null, this.getX(), this.getY(), this.getZ(), 1.0F, true, BlockInteraction.KEEP);
         explosion.explode();
         explosion.finalizeExplosion(this.isSoul() ? 2 : 1, 0.35);
         this.discard();
      }
   }

   protected void onHit(HitResult result) {
      Type hitresult$type = result.getType();
      if (hitresult$type == Type.ENTITY) {
         EntityHitResult entityhitresult = (EntityHitResult)result;
         Entity entity = entityhitresult.getEntity();
         if (entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof Projectile projectile) {
            projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this.getOwner(), this.getOwner(), true);
         }

         this.onHitEntity(entityhitresult);
         this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), Context.of(this, (BlockState)null));
      } else if (hitresult$type == Type.BLOCK) {
         BlockHitResult blockhitresult = (BlockHitResult)result;
         this.onHitBlock(blockhitresult);
         BlockPos blockpos = blockhitresult.getBlockPos();
         this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, Context.of(this, this.level().getBlockState(blockpos)));
      }
   }

   public boolean isPickable() {
      return false;
   }

   @Override
   public boolean hurt(DamageSource p_37616_, float p_37617_) {
      return false;
   }

   public Vec3 getTrailPosition(int pointer, float partialTick) {
      if (this.isRemoved()) {
         partialTick = 1.0F;
      }

      int i = this.trailPointer - pointer & 63;
      int j = this.trailPointer - pointer - 1 & 63;
      Vec3 d0 = this.trailPositions[j];
      Vec3 d1 = this.trailPositions[i].subtract(d0);
      return d0.add(d1.scale(partialTick));
   }

   public boolean hasTrail() {
      return this.trailPointer != -1;
   }

   @Override
   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(SOUL, false);
      p_326229_.define(FIRED, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("is_soul", this.isSoul());
      compound.putInt("timer", this.timer);
      compound.putBoolean("fired", this.getFired());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setSoul(compound.getBoolean("is_soul"));
      this.timer = compound.getInt("timer");
      this.setFired(compound.getBoolean("fired"));
   }

   public boolean isSoul() {
      return (Boolean)this.entityData.get(SOUL);
   }

   public void setSoul(boolean IsSoul) {
      this.entityData.set(SOUL, IsSoul);
   }

   public void setFired(boolean fired) {
      this.entityData.set(FIRED, fired);
   }

   public boolean getFired() {
      return (Boolean)this.entityData.get(FIRED);
   }
}
