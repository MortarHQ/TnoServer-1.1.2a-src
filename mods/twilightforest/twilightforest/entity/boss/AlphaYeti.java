package twilightforest.entity.boss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.IHostileMount;
import twilightforest.entity.ai.goal.ThrowRiderGoal;
import twilightforest.entity.ai.goal.YetiRampageGoal;
import twilightforest.entity.ai.goal.YetiTiredGoal;
import twilightforest.entity.projectile.FallingIce;
import twilightforest.entity.projectile.IceBomb;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.util.WorldUtil;
import twilightforest.util.entities.EntityUtil;

public class AlphaYeti extends BaseTFBoss implements RangedAttackMob, IHostileMount {
   private static final EntityDataAccessor<Boolean> RAMPAGE_FLAG = SynchedEntityData.defineId(AlphaYeti.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> TIRED_FLAG = SynchedEntityData.defineId(AlphaYeti.class, EntityDataSerializers.BOOLEAN);
   private int collisionCounter;
   private boolean canRampage;

   public AlphaYeti(EntityType<? extends AlphaYeti> type, Level level) {
      super(type, level);
      this.xpReward = 317;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new YetiTiredGoal(this, 100));
      this.goalSelector.addGoal(3, new YetiRampageGoal(this, 10, 180));
      this.goalSelector
         .addGoal(
            4,
            new RangedAttackGoal(this, 1.0, 40, 40, 40.0F) {
               public boolean canUse() {
                  return AlphaYeti.this.getRandom().nextInt(50) > 0
                     && AlphaYeti.this.getTarget() != null
                     && AlphaYeti.this.distanceToSqr(AlphaYeti.this.getTarget()) >= 16.0
                     && super.canUse();
               }
            }
         );
      this.goalSelector.addGoal(4, new ThrowRiderGoal(this, 1.0, false) {
         @Override
         protected void checkAndPerformAttack(LivingEntity victim) {
            super.checkAndPerformAttack(victim);
            if (!AlphaYeti.this.getPassengers().isEmpty()) {
               AlphaYeti.this.playSound((SoundEvent)TFSounds.ALPHA_YETI_GRAB.get(), 4.0F, 0.75F + AlphaYeti.this.getRandom().nextFloat() * 0.25F);
            }
         }

         @Override
         public void stop() {
            if (!AlphaYeti.this.getPassengers().isEmpty()) {
               AlphaYeti.this.playSound((SoundEvent)TFSounds.ALPHA_YETI_THROW.get(), 4.0F, 0.75F + AlphaYeti.this.getRandom().nextFloat() * 0.25F);
            }

            super.stop();
         }
      });
      this.addRestrictionGoals(this, this.goalSelector);
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 2.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(RAMPAGE_FLAG, false);
      builder.define(TIRED_FLAG, false);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 200.0)
         .add(Attributes.MOVEMENT_SPEED, 0.38)
         .add(Attributes.ATTACK_DAMAGE, 1.0)
         .add(Attributes.FOLLOW_RANGE, 40.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
   }

   public void aiStep() {
      super.aiStep();
      if (this.isVehicle()) {
         this.getLookControl().setLookAt((Entity)this.getPassengers().get(0), 100.0F, 100.0F);
      }

      if (this.level().isClientSide()) {
         if (this.isRampaging()) {
            float rotation = this.tickCount / 10.0F;

            for (int i = 0; i < 20; i++) {
               this.addSnowEffect(rotation + i * 50, i + rotation);
            }

            this.walkAnimation.setSpeed(this.walkAnimation.speed() + 0.6F);
         }

         if (this.isTired()) {
            for (int i = 0; i < 20; i++) {
               this.level()
                  .addParticle(
                     ParticleTypes.SPLASH,
                     this.getX() + (this.random.nextDouble() - 0.5) * this.getBbWidth() * 0.5,
                     this.getY() + this.getEyeHeight(),
                     this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth() * 0.5,
                     (this.getRandom().nextFloat() - 0.5F) * 0.75F,
                     0.0,
                     (this.getRandom().nextFloat() - 0.5F) * 0.75F
                  );
            }
         }
      }
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.isRampaging() && (this.horizontalCollision || this.verticalCollision)) {
         this.collisionCounter++;
      }

      if (this.collisionCounter >= 15) {
         this.destroyBlocksInAABB(this.getBoundingBox());
         this.collisionCounter = 0;
      }
   }

   private void addSnowEffect(float rotation, float hgt) {
      double px = 3.0 * Math.cos(rotation);
      double py = hgt % 5.0F;
      double pz = 3.0 * Math.sin(rotation);
      this.level().addParticle((ParticleOptions)TFParticleType.SNOW.get(), this.xOld + px, this.yOld + py, this.zOld + pz, 0.0, 0.0, 0.0);
   }

   public void setTarget(@Nullable LivingEntity entity) {
      if (entity != null && entity != this.getTarget()) {
         this.playSound((SoundEvent)TFSounds.ALPHA_YETI_ALERT.get(), 4.0F, 0.5F + this.getRandom().nextFloat() * 0.5F);
      }

      super.setTarget(entity);
   }

   public boolean hurt(DamageSource source, float amount) {
      if (!this.canRampage() && !this.isTired() && source.is(DamageTypeTags.IS_PROJECTILE)) {
         return false;
      }

      boolean flag = super.hurt(source, amount);
      if (flag) {
         this.canRampage = true;
      }

      return flag;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.ALPHA_YETI_GROWL.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.ALPHA_YETI_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.ALPHA_YETI_DEATH.get();
   }

   public float getVoicePitch() {
      return 0.5F + this.getRandom().nextFloat() * 0.5F;
   }

   protected float getSoundVolume() {
      return 4.0F;
   }

   protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float yRot) {
      return new Vec3(0.0, dimensions.height(), 0.4F);
   }

   public boolean canRiderInteract() {
      return true;
   }

   public void destroyBlocksInAABB(AABB box) {
      if (EventHooks.canEntityGrief(this.level(), this)) {
         for (BlockPos pos : WorldUtil.getAllInBB(box)) {
            if (EntityUtil.canDestroyBlock(this.level(), pos, this)) {
               this.level().destroyBlock(pos, false);
            }
         }
      }
   }

   public void makeRandomBlockFall(int range, int hangTime) {
      if (EventHooks.canEntityGrief(this.level(), this)) {
         int bx = Mth.floor(this.getX()) + this.getRandom().nextInt(range) - this.getRandom().nextInt(range);
         int bz = Mth.floor(this.getZ()) + this.getRandom().nextInt(range) - this.getRandom().nextInt(range);
         int by = Mth.floor(this.getY() + this.getEyeHeight());
         this.makeBlockFallAbove(new BlockPos(bx, by, bz), hangTime);
      }
   }

   private void makeBlockFallAbove(BlockPos pos, int hangTime) {
      for (int i = 1; i < 25; i++) {
         BlockPos up = pos.above(i);
         if (this.level().getBlockState(up).is(BlockTags.ICE) && this.level().getBlockState(up.below()).isAir()) {
            this.makeBlockFall(up, hangTime);
            break;
         }
      }
   }

   public void makeBlockAboveTargetFall() {
      if (this.getTarget() != null) {
         this.makeBlockFallAbove(this.getTarget().blockPosition(), 40);
      }
   }

   private void makeBlockFall(BlockPos pos, int hangTime) {
      FallingIce ice = new FallingIce(this.level(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, this.level().getBlockState(pos), hangTime);
      this.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
      this.level().addFreshEntity(ice);
   }

   public void performRangedAttack(LivingEntity target, float distanceFactor) {
      if (!this.canRampage()) {
         IceBomb ice = new IceBomb((EntityType<? extends IceBomb>)TFEntities.THROWN_ICE.get(), this.level(), this);
         double d0 = target.getX() - this.getX();
         double d1 = target.getBoundingBox().minY + target.getBbHeight() / 3.0F - ice.getY();
         double d2 = target.getZ() - this.getZ();
         double d3 = Mth.sqrt((float)(d0 * d0 + d2 * d2));
         ice.shoot(d0, d1 + d3 * 0.2, d2, 1.6F, 14 - this.level().getDifficulty().getId() * 4);
         this.playSound((SoundEvent)TFSounds.ALPHA_YETI_ICE.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
         this.gameEvent(GameEvent.PROJECTILE_SHOOT);
         this.level().addFreshEntity(ice);
      }
   }

   public boolean canRampage() {
      return this.canRampage;
   }

   public void setRampaging(boolean rampaging) {
      this.getEntityData().set(RAMPAGE_FLAG, rampaging);
   }

   public boolean isRampaging() {
      return (Boolean)this.getEntityData().get(RAMPAGE_FLAG);
   }

   public void setTired(boolean tired) {
      this.getEntityData().set(TIRED_FLAG, tired);
      this.canRampage = false;
   }

   public boolean isTired() {
      return (Boolean)this.getEntityData().get(TIRED_FLAG);
   }

   public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
      if (!this.level().isClientSide() && this.isRampaging()) {
         this.playSound((SoundEvent)TFSounds.ALPHA_YETI_ICE.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
         this.hitNearbyEntities();
      }

      return super.causeFallDamage(distance, multiplier, source);
   }

   private void hitNearbyEntities() {
      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.0, 0.0, 5.0))) {
         if (entity != this && entity.hurt(this.damageSources().mobAttack(this), 5.0F)) {
            entity.push(0.0, 0.4, 0.0);
         }
      }
   }

   @Override
   public int getHomeRadius() {
      return 30;
   }

   @Override
   public ResourceKey<Structure> getHomeStructure() {
      return TFStructures.YETI_CAVE;
   }

   @Override
   public Block getDeathContainer(RandomSource random) {
      return (Block)TFBlocks.CANOPY_CHEST.get();
   }

   @Override
   public Block getBossSpawner() {
      return (Block)TFBlocks.ALPHA_YETI_BOSS_SPAWNER.get();
   }

   @Override
   public int getBossBarColor() {
      return 11858160;
   }
}
