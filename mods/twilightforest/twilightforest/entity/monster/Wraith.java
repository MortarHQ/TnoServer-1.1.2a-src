package twilightforest.entity.monster;

import java.util.EnumSet;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.EnforcedHomePoint;
import twilightforest.entity.ai.control.NoClipMoveControl;
import twilightforest.entity.ai.goal.SimplifiedAttackGoal;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFSounds;

public class Wraith extends FlyingMob implements Enemy, EnforcedHomePoint {
   private static final EntityDataAccessor<Optional<GlobalPos>> HOME_POINT = SynchedEntityData.defineId(Wraith.class, EntityDataSerializers.OPTIONAL_GLOBAL_POS);

   public Wraith(EntityType<? extends Wraith> type, Level level) {
      super(type, level);
      this.moveControl = new NoClipMoveControl(this);
      this.noPhysics = true;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(2, new Wraith.MoveTowardsHomeGoal(this, 0.85));
      this.goalSelector.addGoal(4, new SimplifiedAttackGoal(this));
      this.goalSelector.addGoal(5, new Wraith.FlyTowardsTargetGoal(this));
      this.goalSelector.addGoal(6, new Wraith.RandomFloatAroundGoal(this));
      this.goalSelector.addGoal(7, new Wraith.LookAroundGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false));
   }

   public static Builder registerAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.ATTACK_DAMAGE, 5.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(HOME_POINT, Optional.empty());
   }

   public boolean isSteppingCarefully() {
      return true;
   }

   protected boolean shouldDespawnInPeaceful() {
      return true;
   }

   public boolean hurt(DamageSource source, float amount) {
      if (super.hurt(source, amount)) {
         Entity entity = source.getEntity();
         if (this.getVehicle() != entity && !this.getPassengers().contains(entity)) {
            if (entity != this && entity instanceof LivingEntity && !source.isCreativePlayer()) {
               this.setTarget((LivingEntity)entity);
            }

            return true;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean doHurtTarget(Entity entity) {
      entity.hurt(TFDamageTypes.getEntityDamageSource(this.level(), TFDamageTypes.HAUNT, this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
      return super.doHurtTarget(entity);
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.WRAITH_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.WRAITH_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.WRAITH_DEATH.get();
   }

   public static boolean checkMonsterSpawnRules(
      EntityType<? extends Wraith> entity, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random
   ) {
      return world.getDifficulty() != Difficulty.PEACEFUL
         && Monster.isDarkEnoughToSpawn(world, pos, random)
         && checkMobSpawnRules(entity, world, reason, pos, random);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType type, @Nullable SpawnGroupData data) {
      if (type == MobSpawnType.STRUCTURE || type == MobSpawnType.SPAWNER) {
         this.setRestrictionPoint(GlobalPos.of(accessor.getLevel().dimension(), this.blockPosition()));
      }

      return super.finalizeSpawn(accessor, difficulty, type, data);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.saveHomePointToNbt(tag);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.loadHomePointFromNbt(tag);
   }

   @Nullable
   @Override
   public GlobalPos getRestrictionPoint() {
      return (GlobalPos)((Optional)this.getEntityData().get(HOME_POINT)).orElse(null);
   }

   @Override
   public void setRestrictionPoint(@Nullable GlobalPos pos) {
      this.getEntityData().set(HOME_POINT, Optional.ofNullable(pos));
   }

   @Override
   public int getHomeRadius() {
      return 20;
   }

   static class FlyTowardsTargetGoal extends Goal {
      private final Wraith wraith;

      FlyTowardsTargetGoal(Wraith wraith) {
         this.wraith = wraith;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return this.wraith.getTarget() != null;
      }

      public boolean canContinueToUse() {
         return false;
      }

      public void start() {
         LivingEntity target = this.wraith.getTarget();
         if (target != null) {
            this.wraith.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 0.5);
         }
      }
   }

   public static class LookAroundGoal extends Goal {
      private final Wraith wraith;

      public LookAroundGoal(Wraith wraith) {
         this.wraith = wraith;
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         return true;
      }

      public void tick() {
         if (this.wraith.getTarget() == null) {
            this.wraith.setYRot(-((float)Mth.atan2(this.wraith.getDeltaMovement().x(), this.wraith.getDeltaMovement().z())) * (180.0F / (float)Math.PI));
            this.wraith.setYBodyRot(this.wraith.getYRot());
         } else {
            LivingEntity entitylivingbase = this.wraith.getTarget();
            if (entitylivingbase.distanceToSqr(this.wraith) < 4096.0) {
               double d1 = entitylivingbase.getX() - this.wraith.getX();
               double d2 = entitylivingbase.getZ() - this.wraith.getZ();
               this.wraith.setYRot(-((float)Mth.atan2(d1, d2)) * (180.0F / (float)Math.PI));
               this.wraith.setYBodyRot(this.wraith.getYRot());
            }
         }
      }
   }

   public static class MoveTowardsHomeGoal extends Goal {
      private final Wraith mob;
      private double wantedX;
      private double wantedY;
      private double wantedZ;
      private final double speedModifier;

      public MoveTowardsHomeGoal(Wraith mob, double speedModifier) {
         this.mob = mob;
         this.speedModifier = speedModifier;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         if (!this.mob.isMobWithinHomeArea(this.mob) && this.mob.getTarget() == null) {
            BlockPos pos = this.mob
               .getRestrictionPoint()
               .pos()
               .relative(Direction.getRandom(this.mob.getRandom()))
               .offset(this.mob.getRandom().nextInt(5), this.mob.getRandom().nextInt(5), this.mob.getRandom().nextInt(5));
            if (!this.mob.level().isLoaded(pos)) {
               return false;
            }

            this.wantedX = pos.getX();
            this.wantedY = pos.getY();
            this.wantedZ = pos.getZ();
            return true;
         } else {
            return false;
         }
      }

      public boolean canContinueToUse() {
         return false;
      }

      public void start() {
         this.mob.getMoveControl().setWantedPosition(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
      }
   }

   static class RandomFloatAroundGoal extends Goal {
      private final Wraith wraith;

      public RandomFloatAroundGoal(Wraith wraith) {
         this.wraith = wraith;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         if (this.wraith.getTarget() == null && this.wraith.isMobWithinHomeArea(this.wraith)) {
            MoveControl control = this.wraith.getMoveControl();
            double d0 = control.getWantedX() - this.wraith.getX();
            double d1 = control.getWantedY() - this.wraith.getY();
            double d2 = control.getWantedZ() - this.wraith.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0 || d3 > 3600.0;
         } else {
            return false;
         }
      }

      public boolean canContinueToUse() {
         return false;
      }

      public void start() {
         RandomSource random = this.wraith.getRandom();
         double d0 = this.wraith.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
         double d1 = this.wraith.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
         double d2 = this.wraith.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
         this.wraith.getMoveControl().setWantedPosition(d0, d1, d2, 0.5);
      }
   }
}
