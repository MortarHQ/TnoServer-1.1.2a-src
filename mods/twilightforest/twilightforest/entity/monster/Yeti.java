package twilightforest.entity.monster;

import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.entity.IHostileMount;
import twilightforest.entity.ai.goal.ThrowRiderGoal;
import twilightforest.init.TFBiomes;
import twilightforest.init.TFSounds;

public class Yeti extends Monster implements IHostileMount {
   private static final EntityDataAccessor<Boolean> ANGER_FLAG = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);
   private static final AttributeModifier ANGRY_MODIFIER = new AttributeModifier(TwilightForestMod.prefix("anger_follow_boost"), 8.0, Operation.ADD_VALUE);

   public Yeti(EntityType<? extends Yeti> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new ThrowRiderGoal(this, 1.0, false) {
         @Override
         protected void checkAndPerformAttack(LivingEntity victim) {
            super.checkAndPerformAttack(victim);
            if (!Yeti.this.getPassengers().isEmpty()) {
               Yeti.this.playSound((SoundEvent)TFSounds.YETI_GRAB.get(), 1.0F, 1.25F + Yeti.this.getRandom().nextFloat() * 0.5F);
            }
         }

         @Override
         public void stop() {
            if (!Yeti.this.getPassengers().isEmpty()) {
               Yeti.this.playSound((SoundEvent)TFSounds.YETI_THROW.get(), 1.0F, 1.25F + Yeti.this.getRandom().nextFloat() * 0.5F);
            }

            super.stop();
         }
      });
      this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.38)
         .add(Attributes.ATTACK_DAMAGE, 0.0)
         .add(Attributes.FOLLOW_RANGE, 4.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ANGER_FLAG, false);
   }

   public void aiStep() {
      super.aiStep();
      if (!this.getPassengers().isEmpty()) {
         this.getLookControl().setLookAt((Entity)this.getPassengers().get(0), 100.0F, 100.0F);
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      if (source.getEntity() != null && !source.isCreativePlayer()) {
         this.setAngry(true);
      }

      return super.hurt(source, amount);
   }

   public boolean isAngry() {
      return (Boolean)this.getEntityData().get(ANGER_FLAG);
   }

   public void setAngry(boolean anger) {
      this.getEntityData().set(ANGER_FLAG, anger);
      if (!this.level().isClientSide()) {
         if (anger) {
            if (!Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).hasModifier(ANGRY_MODIFIER.id())) {
               Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).addTransientModifier(ANGRY_MODIFIER);
            }
         } else {
            Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).removeModifier(ANGRY_MODIFIER.id());
         }
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("Angry", this.isAngry());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setAngry(compound.getBoolean("Angry"));
   }

   protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float yRot) {
      return new Vec3(0.0, dimensions.height(), 0.4F);
   }

   public boolean canRiderInteract() {
      return true;
   }

   public static boolean yetiSnowyForestSpawnHandler(
      EntityType<? extends Yeti> entityType, ServerLevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource random
   ) {
      if (accessor.getDifficulty() != Difficulty.PEACEFUL) {
         return accessor.getBiome(pos).is(TFBiomes.SNOWY_FOREST)
            ? checkMobSpawnRules(entityType, accessor, reason, pos, random)
            : normalYetiSpawnHandler(entityType, accessor, reason, pos, random);
      } else {
         return false;
      }
   }

   public static boolean normalYetiSpawnHandler(
      EntityType<? extends Monster> entity, ServerLevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource random
   ) {
      return isValidLightLevel(accessor, pos, random) && checkMobSpawnRules(entity, accessor, reason, pos, random);
   }

   public static boolean isValidLightLevel(ServerLevelAccessor accessor, BlockPos blockPos, RandomSource random) {
      Optional<ResourceKey<Biome>> key = accessor.getBiome(blockPos).unwrapKey();
      if (accessor.getBrightness(LightLayer.SKY, blockPos) > random.nextInt(32)) {
         return Objects.equals(key, Optional.of(TFBiomes.SNOWY_FOREST));
      }

      int i = accessor.getLevel().isThundering() ? accessor.getMaxLocalRawBrightness(blockPos, 10) : accessor.getMaxLocalRawBrightness(blockPos);
      return i <= random.nextInt(8) || Objects.equals(key, Optional.of(TFBiomes.SNOWY_FOREST));
   }

   public float getVoicePitch() {
      return super.getVoicePitch() + 0.55F;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.YETI_GROWL.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.YETI_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.YETI_DEATH.get();
   }
}
