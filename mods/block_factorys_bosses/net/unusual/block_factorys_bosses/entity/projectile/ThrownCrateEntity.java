package net.unusual.block_factorys_bosses.entity.projectile;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.EventHooks;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntityPart;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ThrownCrateEntity extends Projectile implements GeoEntity {
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

   public ThrownCrateEntity(EntityType<? extends ThrownCrateEntity> type, Level world) {
      super(type, world);
   }

   protected double getDefaultGravity() {
      return 0.05;
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
      int i = 0;

      while (i < 4) {
         i++;
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

   protected float getWaterInertia() {
      return 0.6F;
   }

   @Nullable
   protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
      return ProjectileUtil.getEntityHitResult(
         this.level(), this, startVec, endVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), x$0 -> this.canHitEntity(x$0)
      );
   }

   protected void onHitEntity(EntityHitResult result) {
      Entity entity = result.getEntity();
      if (!(entity instanceof KrakenTentacleEntityPart) && !(entity instanceof KrakenTentacleEntity)) {
         super.onHitEntity(result);
         if (this.getOwner() != entity) {
            DamageSource damagesource = this.damageSources().magic();
            if (entity.hurt(damagesource, 2.0F) && this.level() instanceof ServerLevel serverlevel) {
               EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
            }
         }

         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 1.5F, false, ExplosionInteraction.MOB);
            this.playSound((SoundEvent)BossesRiseSounds.CRATE_LAND.value(), 1.0F, 1.0F);
            this.spawnAtLocation(new ItemStack((ItemLike)BossesRiseItems.CANNONBALL.get(), 2));
            this.discard();
         }
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 1.5F, false, ExplosionInteraction.MOB);
         this.playSound((SoundEvent)BossesRiseSounds.CRATE_LAND.value(), 1.0F, 1.0F);
         this.spawnAtLocation(new ItemStack((ItemLike)BossesRiseItems.CANNONBALL.get(), 2));
         this.discard();
      }
   }

   protected void defineSynchedData(Builder builder) {
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      double d0 = this.getBoundingBox().getSize() * 10.0;
      if (Double.isNaN(d0)) {
         d0 = 0.0;
      }

      d0 *= 64.0 * getViewScale();
      return distance < d0 * d0;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }
}
