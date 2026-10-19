package it.hurts.octostudios.rarcompat.entities;

import it.hurts.octostudios.octolib.module.particle.trail.EntityTrailProvider;
import it.hurts.sskirillss.relics.entities.misc.ITargetableEntity;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class SparkEntity extends ThrowableProjectile implements ITargetableEntity {
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(SparkEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<ItemStack> RELIC_STACK = SynchedEntityData.defineId(SparkEntity.class, EntityDataSerializers.ITEM_STACK);
   private LivingEntity target;

   public SparkEntity(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
      super(type, worldIn);
   }

   public void tick() {
      super.tick();
      Level level = this.getCommandSenderWorld();
      Vec3 particleCenter = this.getPosition(1.0F).add(0.0, 0.5, 0.0);
      level.addParticle(
         ParticleUtils.constructSimpleSpark(
            new Color(200 + this.random.nextInt(56), 100 + this.random.nextInt(156), 0),
            0.01F + this.random.nextFloat() * 0.1F,
            5 + this.random.nextInt(3),
            0.9F
         ),
         particleCenter.x() + MathUtils.randomFloat(this.random) * 0.05F,
         particleCenter.y() + MathUtils.randomFloat(this.random) * 0.05F,
         particleCenter.z() + MathUtils.randomFloat(this.random) * 0.05F,
         0.0,
         0.0,
         0.0
      );
      if (!level.isClientSide()) {
         if (this.target != null && !this.target.isDeadOrDying() && this.tickCount < 120) {
            if (this.distanceTo(this.target) <= 1.0F) {
               this.hitEntity(this.target);
            }

            Vec3 targetPos = new Vec3(this.target.getX(), this.target.getY() + this.target.getBbHeight() / 2.0F, this.target.getZ());
            Vec3 direction = targetPos.subtract(this.position()).normalize();
            Vec3 motion = this.getDeltaMovement();
            float factor = Math.clamp(this.tickCount * 0.05F, 0.0F, 1.0F);
            double deltaX = motion.x + (direction.x * factor - motion.x) * factor;
            double deltaZ = motion.z + (direction.z * factor - motion.z) * factor;
            this.setDeltaMovement(new Vec3(deltaX, direction.scale(this.position().distanceTo(targetPos) * (this.tickCount * 0.01F)).y, deltaZ));
         } else {
            this.discard();
         }
      }
   }

   protected void onHitEntity(@NotNull EntityHitResult result) {
      super.onHitEntity(result);
      if (result instanceof EntityHitResult entityResult && entityResult.getEntity() instanceof LivingEntity entity) {
         this.hitEntity(entity);
      }
   }

   public void hitEntity(LivingEntity target) {
      if (this.getOwner() instanceof Player player && !target.getStringUUID().equals(player.getStringUUID())) {
         target.invulnerableTime = 0;
         if (target.hurt(this.getCommandSenderWorld().damageSources().onFire(), this.getDamage()) && this.getRelicStack().getItem() instanceof IRelicItem relic
            )
          {
            target.setRemainingFireTicks((int)relic.getStatValue(this.getRelicStack(), "caster", "duration"));
            relic.spreadRelicExperience(player, this.getRelicStack(), 1);
         }
      }

      this.discard();
   }

   public void setDamage(float damage) {
      this.getEntityData().set(DAMAGE, damage);
   }

   public float getDamage() {
      return (Float)this.getEntityData().get(DAMAGE);
   }

   public boolean hurt(DamageSource p_19946_, float p_19947_) {
      return false;
   }

   public boolean isNoGravity() {
      return true;
   }

   public void setRelicStack(ItemStack stack) {
      this.getEntityData().set(RELIC_STACK, stack);
   }

   public ItemStack getRelicStack() {
      return (ItemStack)this.getEntityData().get(RELIC_STACK);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.put("relic_stack", this.getRelicStack().save(this.registryAccess()));
      tag.putFloat("damage", this.getDamage());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setRelicStack(ItemStack.parseOptional(this.registryAccess(), tag.getCompound("relic_stack")));
      this.setDamage(tag.getFloat("damage"));
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DAMAGE, 1.0F);
      builder.define(RELIC_STACK, ItemStack.EMPTY);
   }

   public LivingEntity getTarget() {
      return this.target;
   }

   public void setTarget(LivingEntity target) {
      this.target = target;
   }

   @OnlyIn(Dist.CLIENT)
   public static class TrailProvider extends EntityTrailProvider<SparkEntity> {
      public TrailProvider(SparkEntity entity) {
         super(entity);
      }

      public Vec3 getTrailPosition(float partialTicks) {
         return ((SparkEntity)this.entity).getPosition(partialTicks).add(0.0, 0.5, 0.0);
      }

      public int getTrailUpdateFrequency() {
         return 1;
      }

      public boolean isTrailAlive() {
         return ((SparkEntity)this.entity).isAlive();
      }

      public boolean isTrailGrowing() {
         return ((SparkEntity)this.entity).tickCount > 2;
      }

      public int getTrailMaxLength() {
         return 4;
      }

      public int getTrailFadeInColor() {
         return -5103070;
      }

      public int getTrailFadeOutColor() {
         return -2130736128;
      }

      public double getTrailScale() {
         return 0.05F;
      }
   }
}
