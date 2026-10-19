package twilightforest.entity.monster;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.util.landmarks.LegacyLandmarkPlacements;

public class HostileWolf extends Monster implements VariantHolder<Holder<WolfVariant>> {
   private static final EntityDataAccessor<Holder<WolfVariant>> VARIANT = SynchedEntityData.defineId(HostileWolf.class, EntityDataSerializers.WOLF_VARIANT);

   public HostileWolf(EntityType<? extends HostileWolf> type, Level level) {
      super(type, level);
   }

   public static Builder registerAttributes() {
      return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 2.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new HostileWolf.LeapGoal(this, 0.4F));
      this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{HostileWolf.class}));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Animal.class, false, Wolf.PREY_SELECTOR));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, AbstractSkeleton.class, false));
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, this.registryAccess().registryOrThrow(Registries.WOLF_VARIANT).getHolderOrThrow(WolfVariants.PALE));
   }

   public ResourceLocation getTexture() {
      WolfVariant wolfvariant = (WolfVariant)this.getVariant().value();
      return this.isAggressive() ? wolfvariant.angryTexture() : wolfvariant.wildTexture();
   }

   public Holder<WolfVariant> getVariant() {
      return (Holder<WolfVariant>)this.getEntityData().get(VARIANT);
   }

   public void setVariant(Holder<WolfVariant> variant) {
      this.getEntityData().set(VARIANT, variant);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putString("variant", this.getVariant().unwrapKey().orElse(WolfVariants.PALE).location().toString());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      Optional.ofNullable(ResourceLocation.tryParse(tag.getString("variant")))
         .map(location -> ResourceKey.create(Registries.WOLF_VARIANT, location))
         .flatMap(key -> this.registryAccess().registryOrThrow(Registries.WOLF_VARIANT).getHolder(key))
         .ifPresent(this::setVariant);
   }

   public void setTarget(@Nullable LivingEntity entity) {
      if (entity != null && entity != this.getTarget()) {
         this.playSound(this.getTargetSound(), 4.0F, this.getVoicePitch());
      }

      super.setTarget(entity);
   }

   public static boolean checkWolfSpawnRules(
      EntityType<? extends HostileWolf> entity, ServerLevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource random
   ) {
      return accessor.getDifficulty() != Difficulty.PEACEFUL
         && isValidLightLevel(accessor, pos, random)
         && checkMobSpawnRules(entity, accessor, reason, pos, random);
   }

   public static boolean isValidLightLevel(ServerLevelAccessor accessor, BlockPos pos, RandomSource random) {
      int chunkX = Mth.floor(pos.getX()) >> 4;
      int chunkZ = Mth.floor(pos.getZ()) >> 4;
      return LegacyLandmarkPlacements.pickLandmarkForChunk(chunkX, chunkZ, accessor) == TFStructures.HEDGE_MAZE
         || Monster.isDarkEnoughToSpawn(accessor, pos, random);
   }

   protected SoundEvent getTargetSound() {
      return (SoundEvent)TFSounds.HOSTILE_WOLF_TARGET.get();
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.HOSTILE_WOLF_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.HOSTILE_WOLF_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.HOSTILE_WOLF_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected boolean shouldDespawnInPeaceful() {
      return true;
   }

   public float getTailAngle() {
      return this.getTarget() != null ? 1.5393804F : (float) (Math.PI / 5);
   }

   public static class LeapGoal extends LeapAtTargetGoal {
      private final Mob mob;

      public LeapGoal(Mob mob, float jump) {
         super(mob, jump);
         this.mob = mob;
      }

      public void start() {
         super.start();
         this.mob.setAggressive(true);
      }

      public void stop() {
         super.stop();
         this.mob.setAggressive(false);
      }
   }
}
