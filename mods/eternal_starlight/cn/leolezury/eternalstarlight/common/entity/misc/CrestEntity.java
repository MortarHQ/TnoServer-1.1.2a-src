package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import cn.leolezury.eternalstarlight.common.vfx.ManaCrystalParticleVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class CrestEntity extends Entity implements TrailOwner {
   private static final String TAG_AGE = "age";
   private static final String TAG_HEALTH = "health";
   private static final String TAG_CREST = "crest";
   private int age;
   private int health = 5;
   private ResourceKey<Crest> crest;
   private Player followingPlayer;

   public CrestEntity(Level level, double x, double y, double z, ResourceKey<Crest> crest) {
      this(ESEntities.CREST.get(), level);
      this.setPos(x, y, z);
      this.setYRot((float)(this.random.nextDouble() * 360.0));
      this.setDeltaMovement((this.random.nextDouble() * 0.2 - 0.1) * 2.0, this.random.nextDouble() * 0.2 * 2.0, (this.random.nextDouble() * 0.2 - 0.1) * 2.0);
      this.crest = crest;
   }

   public CrestEntity(EntityType<? extends CrestEntity> entityType, Level level) {
      super(entityType, level);
      this.setNoGravity(true);
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.NONE;
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      this.xo = this.getX();
      this.yo = this.getY();
      this.zo = this.getZ();
      if (this.level().getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
         this.setDeltaMovement((this.random.nextFloat() - this.random.nextFloat()) * 0.2F, 0.2, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
      }

      if (!this.level().noCollision(this.getBoundingBox())) {
         this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
      }

      if (this.tickCount % 20 == 0 && (this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > 64.0)) {
         this.followingPlayer = this.level().getNearestPlayer(this, 64.0);
      }

      if (this.followingPlayer != null && (this.followingPlayer.isSpectator() || this.followingPlayer.isDeadOrDying())) {
         this.followingPlayer = null;
      }

      if (this.followingPlayer != null && this.age > 60) {
         Vec3 vec3 = new Vec3(
            this.followingPlayer.getX() - this.getX(),
            this.followingPlayer.getY() + this.followingPlayer.getEyeHeight() / 2.0 - this.getY(),
            this.followingPlayer.getZ() - this.getZ()
         );
         double d = vec3.lengthSqr();
         if (d < 4096.0) {
            double e = 1.0 - Math.sqrt(d) / 64.0;
            this.setDeltaMovement(this.getDeltaMovement().add(vec3.normalize().scale(e * e * 0.1)));
         }
      } else if (!this.level().isClientSide && this.age % 10 == 0) {
         double deltaX = Mth.frac(this.getX()) + Math.cos(this.age * 200);
         double deltaZ = Mth.frac(this.getZ()) + Math.sin(this.age * 200);
         Vec3 movement = new Vec3(Mth.smoothstep(deltaX / 5.0), Mth.smoothstep(this.getRandom().nextFloat() / 2.0) / 1.8, Mth.smoothstep(deltaZ / 5.0));
         this.setDeltaMovement(ESMathUtil.lerpVec(0.1F, this.getDeltaMovement(), movement));
      }

      this.move(MoverType.SELF, this.getDeltaMovement());
      float f = 0.98F;
      if (this.onGround()) {
         f = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.98F;
      }

      this.setDeltaMovement(this.getDeltaMovement().multiply(f, 0.98, f));
      if (this.onGround()) {
         this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, -0.9, 1.0));
      }

      this.age++;
      if (this.age >= 6000) {
         this.discard();
      }

      if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
         this.registryAccess()
            .registryOrThrow(ESRegistries.CREST)
            .getOptional(this.crest)
            .ifPresent(value -> ManaCrystalParticleVfx.createInstance(value.type(), new Vec3(this.xo, this.yo, this.zo)).send(serverLevel));
         this.registryAccess()
            .registryOrThrow(ESRegistries.CREST)
            .getOptional(this.crest)
            .ifPresent(value -> ManaCrystalParticleVfx.createInstance(value.type(), this.position()).send(serverLevel));
         this.registryAccess()
            .registryOrThrow(ESRegistries.CREST)
            .getOptional(this.crest)
            .ifPresent(
               value -> ManaCrystalParticleVfx.createInstance(value.type(), this.position().add(this.position().subtract(this.xo, this.yo, this.zo)))
                  .send(serverLevel)
            );
      }
   }

   public BlockPos getBlockPosBelowThatAffectsMyMovement() {
      return this.getOnPos(0.999999F);
   }

   protected void doWaterSplashEffect() {
   }

   public boolean hurt(DamageSource damageSource, float f) {
      if (this.isInvulnerableTo(damageSource)) {
         return false;
      }

      if (this.level().isClientSide) {
         return true;
      }

      this.markHurt();
      this.health = (int)(this.health - f);
      if (this.health <= 0) {
         this.discard();
      }

      return true;
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      compoundTag.putInt("age", this.age);
      compoundTag.putInt("health", this.health);
      compoundTag.putString("crest", this.crest.location().toString());
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      this.age = compoundTag.getInt("age");
      this.health = compoundTag.getInt("health");
      this.crest = ResourceKey.create(ESRegistries.CREST, ResourceLocation.parse(compoundTag.getString("crest")));
   }

   public void playerTouch(Player player) {
      if (!this.level().isClientSide) {
         ESCrestUtil.upgradeCrest(player, this.crest);
      }

      this.discard();
   }

   public boolean isAttackable() {
      return false;
   }

   public SoundSource getSoundSource() {
      return SoundSource.AMBIENT;
   }

   @Override
   public TrailEffect createNewTrail() {
      return new TrailEffect(0.5F, 15.0F);
   }

   @Override
   public void updateTrail(TrailEffect effect) {
      Vec3 oldPos = new Vec3(this.xOld, this.yOld, this.zOld);
      effect.update(oldPos.add(0.0, this.getBbHeight() / 2.0F, 0.0));
      if (this.isRemoved()) {
         effect.setLength(Math.max(effect.getLength() - 1.5F, 0.0F));
      }
   }

   @Override
   public Vector4f getTrailColor() {
      return new Vector4f(0.27450982F, 0.039215688F, 0.40784314F, 1.0F);
   }

   @Override
   public boolean isTrailFullBright() {
      return true;
   }
}
