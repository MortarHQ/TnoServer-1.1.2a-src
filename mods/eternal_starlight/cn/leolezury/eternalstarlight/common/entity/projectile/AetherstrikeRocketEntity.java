package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.util.ESWeatherUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AetherstrikeRocketEntity extends Projectile implements ItemSupplier {
   private final String TAG_LIFE = "life";
   private final String TAG_LIFETIME = "lifetime";
   private final String TAG_ITEM = "item";
   private final String TAG_SHOT_AT_ANGLE = "shot_at_angle";
   private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(AetherstrikeRocketEntity.class, EntityDataSerializers.ITEM_STACK);
   private static final EntityDataAccessor<Boolean> SHOT_AT_ANGLE = SynchedEntityData.defineId(AetherstrikeRocketEntity.class, EntityDataSerializers.BOOLEAN);
   private int life;
   private int lifetime;

   public AetherstrikeRocketEntity(EntityType<? extends AetherstrikeRocketEntity> entityType, Level level) {
      super(entityType, level);
   }

   public AetherstrikeRocketEntity(Level level, double x, double y, double z, ItemStack stack) {
      super(ESEntities.AETHERSTRIKE_ROCKET.get(), level);
      this.life = 0;
      this.setPos(x, y, z);
      this.entityData.set(ITEM, stack.copy());
      this.setDeltaMovement(this.random.triangle(0.0, 0.002297), 0.05, this.random.triangle(0.0, 0.002297));
      this.lifetime = 25 + this.random.nextInt(6) + this.random.nextInt(7);
   }

   public AetherstrikeRocketEntity(Level level, @Nullable Entity shooter, double x, double y, double z, ItemStack stack) {
      this(level, x, y, z, stack);
      this.setOwner(shooter);
   }

   public AetherstrikeRocketEntity(Level level, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
      this(level, x, y, z, stack);
      this.entityData.set(SHOT_AT_ANGLE, shotAtAngle);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(ITEM, getDefaultItem()).define(SHOT_AT_ANGLE, false);
   }

   public void tick() {
      super.tick();
      if (!this.isShotAtAngle()) {
         double horizontal = this.horizontalCollision ? 1.0 : 1.15;
         this.setDeltaMovement(this.getDeltaMovement().multiply(horizontal, 1.0, horizontal).add(0.0, 0.04, 0.0));
      }

      Vec3 movement = this.getDeltaMovement();
      this.move(MoverType.SELF, movement);
      this.setDeltaMovement(movement);
      HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, x$0 -> this.canHitEntity(x$0));
      if (!this.noPhysics) {
         this.hitTargetOrDeflectSelf(hitResult);
         this.hasImpulse = true;
      }

      this.updateRotation();
      if (this.life == 0 && !this.isSilent()) {
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.AMBIENT, 3.0F, 1.0F);
      }

      this.life++;
      if (this.level().isClientSide && this.life % 2 == 0) {
         this.level()
            .addParticle(
               ESExplosionParticleOptions.AETHERSENT,
               this.getX() - movement.x,
               this.getY() - movement.y,
               this.getZ() - movement.z,
               this.random.nextGaussian() * 0.05,
               -this.getDeltaMovement().y * 0.5,
               this.random.nextGaussian() * 0.05
            );
      }

      if (!this.level().isClientSide && this.life > this.lifetime) {
         this.explode();
      }
   }

   private void explode() {
      this.gameEvent(GameEvent.EXPLODE, this.getOwner());
      this.level()
         .playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            SoundEvents.FIREWORK_ROCKET_LARGE_BLAST,
            SoundSource.AMBIENT,
            25.0F,
            0.95F + this.random.nextFloat() * 0.1F
         );
      if (this.level() instanceof ServerLevel serverLevel && serverLevel.dimension().location().equals(ESDimensions.STARLIGHT_KEY.location())) {
         for (int i = 0; i < 4; i++) {
            Vec3 vec3 = this.position().offsetRandom(this.getRandom(), 1.5F);

            for (int s = 0; s < serverLevel.players().size(); s++) {
               ServerPlayer serverPlayer = (ServerPlayer)serverLevel.players().get(s);
               serverLevel.sendParticles(serverPlayer, ESExplosionParticleOptions.AETHERSENT, true, vec3.x, vec3.y, vec3.z, 3, 0.0, 0.0, 0.0, 0.0);
            }
         }

         for (int i = 0; i < 30; i++) {
            Vec3 speed = new Vec3(
                  (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                  this.random.nextFloat() * 0.05F,
                  (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
               )
               .normalize();
            ESPlatform.INSTANCE
               .sendToAllClients(
                  serverLevel,
                  new ParticlePacket(
                     ExplosionShockParticleOptions.AETHERSENT,
                     this.getEyePosition().x + speed.x * 1.2,
                     this.getEyePosition().y + speed.y * 1.2,
                     this.getEyePosition().z + speed.z * 1.2,
                     speed.x,
                     speed.y,
                     speed.z
                  )
               );
         }

         if (this.getRandom().nextFloat() < 0.6 && this.level().canSeeSky(this.blockPosition())) {
            ESWeatherUtil.getOrCreateWeathers(serverLevel)
               .setActiveWeather(ESWeathers.METEOR_SHOWER.get(), ESWeathers.METEOR_SHOWER.get().weatherProperties().duration().sample(serverLevel.getRandom()));
            if (this.getOwner() instanceof Player player) {
               player.getCooldowns().addCooldown(this.getItem().getItem(), 2400);
            }
         }
      }

      this.discard();
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (!this.level().isClientSide) {
         this.explode();
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      BlockPos blockpos = new BlockPos(result.getBlockPos());
      this.level().getBlockState(blockpos).entityInside(this.level(), blockpos, this);
      if (!this.level().isClientSide()) {
         this.explode();
      }

      super.onHitBlock(result);
   }

   public boolean isShotAtAngle() {
      return (Boolean)this.entityData.get(SHOT_AT_ANGLE);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("life", this.life);
      compound.putInt("lifetime", this.lifetime);
      compound.put("item", this.getItem().save(this.registryAccess()));
      compound.putBoolean("shot_at_angle", (Boolean)this.entityData.get(SHOT_AT_ANGLE));
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.life = compound.getInt("life");
      this.lifetime = compound.getInt("lifetime");
      if (compound.contains("item", 10)) {
         this.entityData.set(ITEM, ItemStack.parse(this.registryAccess(), compound.getCompound("item")).orElseGet(AetherstrikeRocketEntity::getDefaultItem));
      } else {
         this.entityData.set(ITEM, getDefaultItem());
      }

      if (compound.contains("shot_at_angle")) {
         this.entityData.set(SHOT_AT_ANGLE, compound.getBoolean("shot_at_angle"));
      }
   }

   public ItemStack getItem() {
      return (ItemStack)this.entityData.get(ITEM);
   }

   public boolean isAttackable() {
      return false;
   }

   private static ItemStack getDefaultItem() {
      return new ItemStack((ItemLike)ESItems.AETHERSTRIKE_ROCKET.get());
   }
}
