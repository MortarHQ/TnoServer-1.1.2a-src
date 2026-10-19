package net.unusual.block_factorys_bosses.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class SoulShockwaveEntity extends AbstractArrow implements ItemSupplier {
   public static final ItemStack PROJECTILE_ITEM = new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get());
   private int knockback = 0;

   public SoulShockwaveEntity(EntityType<? extends SoulShockwaveEntity> type, Level world) {
      super(type, world);
   }

   public SoulShockwaveEntity(EntityType<? extends SoulShockwaveEntity> type, double x, double y, double z, Level world, @Nullable ItemStack firedFromWeapon) {
      super(type, x, y, z, world, PROJECTILE_ITEM, firedFromWeapon);
   }

   public SoulShockwaveEntity(EntityType<? extends SoulShockwaveEntity> type, LivingEntity entity, Level world, @Nullable ItemStack firedFromWeapon) {
      super(type, entity, world, PROJECTILE_ITEM, firedFromWeapon);
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getItem() {
      return PROJECTILE_ITEM;
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get());
   }

   protected void doPostHurtEffects(LivingEntity entity) {
      super.doPostHurtEffects(entity);
      entity.setArrowCount(entity.getArrowCount() - 1);
   }

   public void setKnockback(int knockback) {
      this.knockback = knockback;
   }

   protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
      if (this.knockback > 0.0) {
         double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
         Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(this.knockback * 0.6 * d1);
         if (vec3.lengthSqr() > 0.0) {
            livingEntity.push(vec3.x, 0.1, vec3.z);
         }
      }
   }

   public void onHitEntity(EntityHitResult entityHitResult) {
      Entity entity = entityHitResult.getEntity();
      if (entity != this.getOwner()) {
         super.onHitEntity(entityHitResult);
         this.onHitSomething(entity);
      }
   }

   public void onHitBlock(BlockHitResult blockHitResult) {
      super.onHitBlock(blockHitResult);
      this.onHitSomething(null);
   }

   private void onHitSomething(@Nullable Entity target) {
      if (this.level() instanceof ServerLevel level) {
         Entity entity = (Entity)(target != null ? target : this);
         double x = entity.getX();
         double y = entity.getY();
         double z = entity.getZ();
         level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.SOUL_CLOUD.get(), x, y + 0.1, z, 15, 0.5, 0.01, 0.5, Math.random() * 1.2);
         level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.BLACK_FALLING_DUST.get(), x, y + 0.1, z, 15, 0.5, 0.01, 0.5, Math.random() * 0.9);
         level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.MAGICAL_DOT.get(), x, y + 0.1, z, 8, 0.5, 0.01, 0.5, Math.random() * 1.5);
         int aabbSize = 6;
         level.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(this.position(), aabbSize, aabbSize, aabbSize), e -> !(e instanceof UnderworldKnightEntity))
            .forEach(hitEntity -> {
               hitEntity.hurt(new DamageSource(level.holderOrThrow(DamageTypes.EXPLOSION), this, this.getOwner()), 4.0F);
               hitEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            });
         if (target instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
         }

         this.discard();
      }
   }

   public void tick() {
      super.tick();
      if (this.inGround) {
         this.discard();
      }
   }

   public static SoulShockwaveEntity shoot(Level world, LivingEntity entity, RandomSource source) {
      return shoot(world, entity, source, 1.0F, 5.0, 0);
   }

   public static SoulShockwaveEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
      return shoot(world, entity, source, pullingPower * 1.0F, 5.0, 0);
   }

   public static SoulShockwaveEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
      SoulShockwaveEntity entityarrow = new SoulShockwaveEntity(
         (EntityType<? extends SoulShockwaveEntity>)BossesRiseEntities.SOUL_SHOCKWAVE.get(), entity, world, null
      );
      entityarrow.shoot(entity.getViewVector(1.0F).x, entity.getViewVector(1.0F).y, entity.getViewVector(1.0F).z, power * 2.0F, 0.0F);
      entityarrow.setSilent(true);
      entityarrow.setCritArrow(false);
      entityarrow.setBaseDamage(damage);
      entityarrow.setKnockback(knockback);
      world.addFreshEntity(entityarrow);
      world.playSound(
         null,
         entity.getX(),
         entity.getY(),
         entity.getZ(),
         SoundEvents.BLAZE_SHOOT,
         SoundSource.PLAYERS,
         1.0F,
         1.0F / (random.nextFloat() * 0.5F + 1.0F) + power / 2.0F
      );
      return entityarrow;
   }

   public static SoulShockwaveEntity shoot(LivingEntity entity, LivingEntity target) {
      SoulShockwaveEntity entityarrow = new SoulShockwaveEntity(
         (EntityType<? extends SoulShockwaveEntity>)BossesRiseEntities.SOUL_SHOCKWAVE.get(), entity, entity.level(), null
      );
      double dx = target.getX() - entity.getX();
      double dy = target.getY() + target.getEyeHeight() - 1.1;
      double dz = target.getZ() - entity.getZ();
      entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 2.0F, 12.0F);
      entityarrow.setSilent(true);
      entityarrow.setBaseDamage(5.0);
      entityarrow.setKnockback(0);
      entityarrow.setCritArrow(false);
      entity.level().addFreshEntity(entityarrow);
      entity.level()
         .playSound(
            null,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            SoundEvents.BLAZE_SHOOT,
            SoundSource.PLAYERS,
            1.0F,
            1.0F / (RandomSource.create().nextFloat() * 0.5F + 1.0F)
         );
      return entityarrow;
   }
}
