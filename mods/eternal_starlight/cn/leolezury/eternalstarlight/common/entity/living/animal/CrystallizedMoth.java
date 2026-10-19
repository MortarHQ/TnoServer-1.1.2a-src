package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CrystallizedMoth extends TamableAnimal implements FlyingAnimal, NeutralMob {
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   private int remainingPersistentAngerTime;
   @Nullable
   private UUID persistentAngerTarget;
   private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.CRYSTALLIZED_MOTH_FOOD);
   protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(CrystallizedMoth.class, EntityDataSerializers.INT);
   public AnimationState idleAnimationState = new AnimationState();

   public int getAttackTicks() {
      return (Integer)this.getEntityData().get(ATTACK_TICKS);
   }

   public void setAttackTicks(int attackTicks) {
      this.getEntityData().set(ATTACK_TICKS, attackTicks);
   }

   public CrystallizedMoth(EntityType<? extends CrystallizedMoth> entityType, Level level) {
      super(entityType, level);
      this.moveControl = new CrystallizedMoth.MothMoveControl();
      this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, -1.0F);
      this.setNoGravity(true);
   }

   protected PathNavigation createNavigation(Level level) {
      FlyingPathNavigation pathNavigation = new FlyingPathNavigation(this, level) {
         public boolean isStableDestination(BlockPos blockPos) {
            return this.level.getBlockState(blockPos).isAir();
         }
      };
      pathNavigation.setCanOpenDoors(false);
      pathNavigation.setCanFloat(true);
      pathNavigation.setCanPassDoors(true);
      return pathNavigation;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ATTACK_TICKS, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new CrystallizedMoth.CrystallizedMothAttackGoal());
      this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
      this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, FOOD_ITEMS, false));
      this.goalSelector.addGoal(5, new RandomFlyGoal(this) {
         @Override
         public boolean canUse() {
            return super.canUse() && CrystallizedMoth.this.getAttackTicks() <= 0;
         }

         @Override
         public boolean canContinueToUse() {
            return super.canContinueToUse() && CrystallizedMoth.this.getAttackTicks() <= 0;
         }
      });
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.targetSelector.addGoal(0, new OwnerHurtByTargetGoal(this));
      this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Bat.class, false));
      this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal(this, true));
   }

   public int getRemainingPersistentAngerTime() {
      return this.remainingPersistentAngerTime;
   }

   public void setRemainingPersistentAngerTime(int i) {
      this.remainingPersistentAngerTime = i;
   }

   @Nullable
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   public void setPersistentAngerTarget(@Nullable UUID uuid) {
      this.persistentAngerTarget = uuid;
   }

   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.getRandom()));
   }

   public boolean hurt(DamageSource source, float amount) {
      return !source.is(ESDamageTypes.SONAR) && super.hurt(source, amount);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.crystallizedMoth.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.crystallizedMoth.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.crystallizedMoth.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.crystallizedMoth.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.FLYING_SPEED, 0.6);
   }

   public void aiStep() {
      super.aiStep();
      if (this.level().isClientSide) {
         this.idleAnimationState.startIfStopped(this.tickCount);
      }
   }

   public boolean isFood(ItemStack itemStack) {
      return FOOD_ITEMS.test(itemStack);
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (this.level().isClientSide && (!this.isBaby() || !this.isFood(itemStack))) {
         boolean bl = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame() && !this.isAngry();
         return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
      }

      if (this.isTame()) {
         if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
            itemStack.consume(1, player);
            FoodProperties foodProperties = (FoodProperties)itemStack.get(DataComponents.FOOD);
            float f = foodProperties != null ? foodProperties.nutrition() : 1.0F;
            this.heal(2.0F * f);
            return InteractionResult.sidedSuccess(this.level().isClientSide());
         }

         if (itemStack.is(ESItems.LUNARIS_CACTUS_GEL.get())) {
            itemStack.consume(1, player);
            if (this.random.nextInt(5) == 0) {
               ItemEntity itemEntity = this.spawnAtLocation((ItemLike)ESItems.SHIVERING_GEL.get(), 1);
               if (itemEntity != null) {
                  itemEntity.setDeltaMovement(
                     itemEntity.getDeltaMovement()
                        .add(
                           (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F,
                           this.getRandom().nextFloat() * 0.05F,
                           (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F
                        )
                  );
               }
            } else {
               this.level().broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;
         }
      } else if (this.isFood(itemStack) && !this.isAngry()) {
         itemStack.consume(1, player);
         this.tryToTame(player);
         return InteractionResult.SUCCESS;
      }

      return super.mobInteract(player, hand);
   }

   private void tryToTame(Player player) {
      if (this.random.nextInt(3) == 0) {
         this.tame(player);
         this.getNavigation().stop();
         this.setTarget(null);
         this.level().broadcastEntityEvent(this, (byte)7);
      } else {
         this.level().broadcastEntityEvent(this, (byte)6);
      }
   }

   public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
      if (!(target instanceof Creeper) && !(target instanceof Ghast)) {
         return switch (target) {
            case CrystallizedMoth moth -> !moth.isTame() || moth.getOwner() != owner;
            case Player playerTarget when owner instanceof Player playerOwner && !playerOwner.canHarmPlayer(playerTarget) -> false;
            case AbstractHorse horse when horse.isTamed() -> false;
            default -> {
               if (target instanceof TamableAnimal animal && animal.isTame()) {
                  yield false;
               } else {
                  yield true;
               }
            }
         };
      } else {
         return false;
      }
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return !this.isTame();
   }

   public boolean shouldTryTeleportToOwner() {
      LivingEntity livingEntity = this.getOwner();
      return livingEntity != null && this.distanceToSqr(this.getOwner()) >= 400.0;
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
      CrystallizedMoth moth = (CrystallizedMoth)ESEntities.CRYSTALLIZED_MOTH.get().create(level);
      if (this.isTame() && moth != null) {
         moth.setOwnerUUID(this.getOwnerUUID());
         moth.setTame(true, true);
      }

      return moth;
   }

   public boolean isFlying() {
      return !this.onGround();
   }

   public static boolean checkMothSpawnRules(
      EntityType<? extends CrystallizedMoth> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return !level.canSeeSky(pos)
         && pos.getY() < level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 20
         && ESConfig.INSTANCE.mobsConfig.crystallizedMoth.canSpawn();
   }

   private class CrystallizedMothAttackGoal extends Goal {
      public CrystallizedMothAttackGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return CrystallizedMoth.this.getTarget() != null && CrystallizedMoth.this.getAttackTicks() == 0 && CrystallizedMoth.this.random.nextInt(50) == 0;
      }

      public boolean canContinueToUse() {
         return CrystallizedMoth.this.getAttackTicks() < 100;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         CrystallizedMoth.this.setAttackTicks(CrystallizedMoth.this.getAttackTicks() + 1);
         if (CrystallizedMoth.this.getTarget() != null) {
            LivingEntity target = CrystallizedMoth.this.getTarget();
            CrystallizedMoth.this.getLookControl().setLookAt(target, 360.0F, 360.0F);
            Vec3 selfPos = CrystallizedMoth.this.position().add(0.0, CrystallizedMoth.this.getBbHeight() / 2.0F, 0.0);
            Vec3 pos = target.position().add(0.0, target.getBbHeight() / 2.0F, 0.0);
            Vec3 subtract = pos.subtract(selfPos);
            CrystallizedMoth.this.setYRot(-((float)Mth.atan2(subtract.x, subtract.z)) * (180.0F / (float)Math.PI));
            if (CrystallizedMoth.this.getAttackTicks() > 15 && CrystallizedMoth.this.hasLineOfSight(target) && CrystallizedMoth.this.distanceTo(target) < 30.0F
               )
             {
               target.hurt(
                  ESDamageTypes.getEntityDamageSource(CrystallizedMoth.this.level(), ESDamageTypes.SONAR, CrystallizedMoth.this),
                  (float)CrystallizedMoth.this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                     * (target.getType().is(ESTags.EntityTypes.VULNERABLE_TO_SONAR_BOMB) ? 4 : 1)
               );
            }

            if (CrystallizedMoth.this.level() instanceof ServerLevel serverLevel && CrystallizedMoth.this.getAttackTicks() % 5 == 0) {
               Vec3 delta = pos.subtract(selfPos).normalize().scale(0.8);
               ESPlatform.INSTANCE
                  .sendToAllClients(
                     serverLevel,
                     new ParticlePacket((ParticleOptions)ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), selfPos.x, selfPos.y, selfPos.z, delta.x, delta.y, delta.z)
                  );
               ScreenShakeVfx.createInstance(CrystallizedMoth.this.level().dimension(), CrystallizedMoth.this.position(), 30.0F, 15, 0.09F, 0.09F, 4.0F, 5.0F)
                  .send(serverLevel);
            }

            Vec3 wanted = ESMathUtil.rotationToPosition(pos, 10.0F, 0.0F, ESMathUtil.positionToYaw(pos, selfPos) + 5.0F);
            CrystallizedMoth.this.getMoveControl().setWantedPosition(wanted.x, wanted.y, wanted.z, 1.0);
         }
      }

      public void stop() {
         CrystallizedMoth.this.setAttackTicks(0);
      }
   }

   private class MothMoveControl extends MoveControl {
      public MothMoveControl() {
         super(CrystallizedMoth.this);
      }

      public void tick() {
         if (this.operation == Operation.MOVE_TO) {
            Vec3 vec3 = new Vec3(this.wantedX - this.mob.getX(), this.wantedY - this.mob.getY(), this.wantedZ - this.mob.getZ());
            double length = vec3.length();
            double size = this.mob.getBoundingBox().getSize();
            Vec3 delta = vec3.scale(this.speedModifier * 0.025 / length);
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(delta));
            if (length < size * 0.5) {
               this.operation = Operation.WAIT;
               this.mob.setDeltaMovement(this.mob.getDeltaMovement().scale(0.2));
            } else if (length >= size && CrystallizedMoth.this.getAttackTicks() <= 0) {
               this.mob.setYRot(-((float)Mth.atan2(delta.x, delta.z)) * (180.0F / (float)Math.PI));
            }
         }

         this.mob.setNoGravity(true);
      }
   }
}
