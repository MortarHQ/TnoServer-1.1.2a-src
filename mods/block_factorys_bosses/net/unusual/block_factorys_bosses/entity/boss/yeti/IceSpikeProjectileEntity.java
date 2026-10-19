package net.unusual.block_factorys_bosses.entity.boss.yeti;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.EventHooks;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceSpikeProjectileEntity extends Projectile implements GeoEntity {
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   private double baseDamage = 2.0;
   private int knockback = 0;

   public IceSpikeProjectileEntity(EntityType<? extends IceSpikeProjectileEntity> type, Level world) {
      super(type, world);
   }

   public IceSpikeProjectileEntity(EntityType<? extends IceSpikeProjectileEntity> type, LivingEntity owner, Level level) {
      this(type, level);
      this.setOwner(owner);
      this.setPos(owner.getX(), owner.getEyeY() - 0.1F, owner.getZ());
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      double bb = this.getBoundingBox().getSize() * 10.0;
      if (Double.isNaN(bb)) {
         bb = 1.0;
      }

      bb *= 64.0 * getViewScale();
      return distance < bb * bb;
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void setBaseDamage(double baseDamage) {
      this.baseDamage = baseDamage;
   }

   public void setKnockback(int knockback) {
      this.knockback = knockback;
   }

   public boolean isAttackable() {
      return false;
   }

   protected float getWaterInertia() {
      return 0.6F;
   }

   public AABB getBoundingBoxForCulling() {
      return super.getBoundingBoxForCulling().inflate(3.0);
   }

   protected void doKnockback(LivingEntity livingEntity) {
      if (this.knockback > 0.0) {
         double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
         Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(this.knockback * 0.6 * d1);
         if (vec3.lengthSqr() > 0.0) {
            livingEntity.push(vec3.x, 0.1, vec3.z);
         }
      }
   }

   public void playerTouch(Player player) {
      super.playerTouch(player);
      if (player != this.getOwner()) {
         ItemStack stack = new ItemStack((ItemLike)BossesRiseBlocks.ICE_BLOCK_PARTICULES.get()).copy();
         player.setTicksFrozen(200);
         if (this.level() instanceof ServerLevel level) {
            level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), this.getX(), this.getY(), this.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
            player.hurt(new DamageSource(level.holderOrThrow(DamageTypes.FREEZE), this, this.getOwner()), 8.0F);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            if (this.getOwner() instanceof YetiEntity yeti) {
               double distance = yeti.distanceTo(player);
               if (distance >= 10.0 && distance <= 20.0) {
                  yeti.doAttack(YetiEntity.YetiState.RANGED_CONTROL.getAttack());
               }
            }

            this.discard();
         }
      }
   }

   protected double getDefaultGravity() {
      return 0.05;
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      Entity target = result.getEntity();
      float delta = (float)this.getDeltaMovement().length();
      double damage = this.baseDamage;
      Entity owner = this.getOwner();
      DamageSource damagesource = this.damageSources().indirectMagic(this, (Entity)(owner != null ? owner : this));
      int ceil = Mth.ceil(Mth.clamp(delta * damage, 0.0, 2.147483647E9));
      if (owner instanceof LivingEntity living) {
         living.setLastHurtMob(target);
      }

      boolean enderman = target.getType() == EntityType.ENDERMAN;
      int fireTicks = target.getRemainingFireTicks();
      if (this.isOnFire() && !enderman) {
         target.igniteForSeconds(5.0F);
      }

      if (target.hurt(damagesource, ceil)) {
         if (enderman) {
            return;
         }

         if (target instanceof LivingEntity living) {
            this.doKnockback(living);
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            if (this.getOwner() instanceof YetiEntity yeti && living instanceof Player) {
               double distance = yeti.distanceTo(living);
               if (distance >= 10.0 && distance <= 20.0) {
                  yeti.doAttack(YetiEntity.YetiState.RANGED_CONTROL.getAttack());
               }
            }

            if (living != owner && living instanceof Player && owner instanceof ServerPlayer player && !this.isSilent()) {
               player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
            }
         }

         this.breakIce();
      } else {
         target.setRemainingFireTicks(fireTicks);
         this.breakIce();
      }
   }

   public void onHitBlock(BlockHitResult blockHitResult) {
      super.onHitBlock(blockHitResult);
      this.breakIce();
   }

   public void breakIce() {
      ItemStack stack = new ItemStack((ItemLike)BossesRiseBlocks.ICE_BLOCK_PARTICULES.get()).copy();
      if (this.level() instanceof ServerLevel level) {
         level.playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            SoundEvents.GLASS_BREAK,
            SoundSource.PLAYERS,
            0.9F,
            (float)Mth.nextDouble(this.getRandom(), 0.15, 0.35)
         );
         level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), this.getX(), this.getY(), this.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
         this.discard();
      }
   }

   public void tick() {
      super.tick();
      Vec3 delta = this.getDeltaMovement();
      if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
         double d0 = delta.horizontalDistance();
         this.setYRot((float)(Mth.atan2(delta.x, delta.z) * 180.0 / (float) Math.PI));
         this.setXRot((float)(Mth.atan2(delta.y, d0) * 180.0 / (float) Math.PI));
         this.yRotO = this.getYRot();
         this.xRotO = this.getXRot();
      }

      BlockPos blockpos = this.blockPosition();
      BlockState blockstate = this.level().getBlockState(blockpos);
      if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW) || this.isInFluidType((fluidType, height) -> this.canFluidExtinguish(fluidType))) {
         this.clearFire();
      }

      Vec3 pos = this.position();
      Vec3 moved = pos.add(delta);
      HitResult hitresult = this.level().clip(new ClipContext(pos, moved, Block.COLLIDER, Fluid.NONE, this));
      if (hitresult.getType() != Type.MISS) {
         moved = hitresult.getLocation();
      }

      EntityHitResult entityhitresult = this.findHitEntity(pos, moved);
      if (entityhitresult != null) {
         hitresult = entityhitresult;
      }

      if (hitresult instanceof EntityHitResult entityHit) {
         Entity entity = entityHit.getEntity();
         Entity entity1 = this.getOwner();
         if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
            hitresult = null;
         }
      }

      if (hitresult != null && hitresult.getType() != Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
         this.hitTargetOrDeflectSelf(hitresult);
         this.hasImpulse = true;
      }

      delta = this.getDeltaMovement();
      double dX = delta.x;
      double dY = delta.y;
      double dZ = delta.z;

      for (int i = 0; i < 4; i++) {
         this.level()
            .addParticle(
               (ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(),
               this.getX() + dX * i / 4.0,
               this.getY() + dY * i / 4.0,
               this.getZ() + dZ * i / 4.0,
               -dX * 0.02,
               -(dY + 0.2) * 0.02,
               -dZ * 0.02
            );
      }

      double newX = this.getX() + dX;
      double newY = this.getY() + dY;
      double newZ = this.getZ() + dZ;
      double d4 = delta.horizontalDistance();
      this.setYRot((float)(Mth.atan2(dX, dZ) * 180.0 / (float) Math.PI));
      this.setXRot((float)(Mth.atan2(dY, d4) * 180.0 / (float) Math.PI));
      this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
      this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
      float f = 0.99F;
      if (this.isInWater()) {
         for (int j = 0; j < 4; j++) {
            this.level().addParticle(ParticleTypes.BUBBLE, newX - dX * 0.25, newY - dY * 0.25, newZ - dZ * 0.25, dX, dY, dZ);
         }

         f = this.getWaterInertia();
      }

      this.setDeltaMovement(delta.scale(f));
      this.applyGravity();
      this.setPos(newX, newY, newZ);
      this.checkInsideBlocks();
   }

   public static IceSpikeProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source) {
      return shoot(world, entity, source, 1.0F, 5.0, 0);
   }

   public static IceSpikeProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
      return shoot(world, entity, source, pullingPower, 5.0, 0);
   }

   public static IceSpikeProjectileEntity shoot(Level world, LivingEntity entity, RandomSource ignoredRandom, float power, double damage, int knockback) {
      IceSpikeProjectileEntity iceSpike = new IceSpikeProjectileEntity(
         (EntityType<? extends IceSpikeProjectileEntity>)BossesRiseEntities.ICE_SPIKE_PR.get(), entity, world
      );
      iceSpike.shoot(entity.getViewVector(1.0F).x, entity.getViewVector(1.0F).y, entity.getViewVector(1.0F).z, power * 2.0F, 0.0F);
      iceSpike.setSilent(true);
      iceSpike.setBaseDamage(damage);
      iceSpike.setKnockback(knockback);
      world.addFreshEntity(iceSpike);
      return iceSpike;
   }

   public static IceSpikeProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
      IceSpikeProjectileEntity iceSpike = new IceSpikeProjectileEntity(
         (EntityType<? extends IceSpikeProjectileEntity>)BossesRiseEntities.ICE_SPIKE_PR.get(), entity, entity.level()
      );
      double dx = target.getX() - entity.getX();
      double dy = target.getY() + target.getEyeHeight() - 1.1;
      double dz = target.getZ() - entity.getZ();
      iceSpike.shoot(dx, dy - iceSpike.getY() + Math.hypot(dx, dz) * 0.2F, dz, 2.0F, 12.0F);
      iceSpike.setSilent(true);
      iceSpike.setBaseDamage(5.0);
      iceSpike.setKnockback(0);
      entity.level().addFreshEntity(iceSpike);
      return iceSpike;
   }

   @Nullable
   protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
      return ProjectileUtil.getEntityHitResult(
         this.level(), this, startVec, endVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), x$0 -> this.canHitEntity(x$0)
      );
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putDouble("damage", this.baseDamage);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("damage", 99)) {
         this.baseDamage = compound.getDouble("damage");
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }
}
