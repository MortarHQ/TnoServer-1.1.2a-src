package com.mongoose.royalvariations.common.entities.hostile;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.attachments.RVCapHelper;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.common.items.RVItems;
import com.mongoose.royalvariations.utils.MathHelper;
import com.mongoose.royalvariations.utils.MobUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Zombie.ZombieGroupData;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

public class RoyalZombie extends RoyalMonster {
   private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(RoyalZombie.class, EntityDataSerializers.INT);
   private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = p_34284_ -> p_34284_ == Difficulty.HARD;
   public static String IDLE = "idle";
   public static String ATTACK = "attack";
   public static String BLESS = "bless";
   private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
   private boolean canBreakDoors;
   private boolean isWalking;
   private int attackTick;
   private int toIdleTick;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState toIdleAnimationState = new AnimationState();
   public AnimationState crownAnimationState = new AnimationState();
   public AnimationState attackAnimationState = new AnimationState();
   public AnimationState blessAnimationState = new AnimationState();

   public RoyalZombie(EntityType<? extends Monster> p_33002_, Level p_33003_) {
      super(p_33002_, p_33003_);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new RestrictSunGoal(this));
      this.goalSelector.addGoal(1, new RoyalZombie.KnightingGoal(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1, false));
      this.goalSelector.addGoal(4, new RoyalZombie.ZombieAttackTurtleEggGoal(this, 1.0, 3));
      this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::canBreakDoors));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[]{ZombifiedPiglin.class}));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 60.0)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.MOVEMENT_SPEED, 0.23)
         .add(Attributes.ATTACK_DAMAGE, 6.0)
         .add(Attributes.ARMOR, 12.0)
         .add(Attributes.ATTACK_KNOCKBACK, 0.5)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.6);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ANIM_STATE, 0);
   }

   public void setAnimationState(String input) {
      this.setAnimationState(this.getAnimationState(input));
   }

   public void setAnimationState(int id) {
      this.entityData.set(ANIM_STATE, id);
   }

   public int getAnimationState(String animation) {
      if (Objects.equals(animation, "idle")) {
         return 0;
      } else if (Objects.equals(animation, "attack")) {
         return 1;
      } else {
         return Objects.equals(animation, "bless") ? 2 : 0;
      }
   }

   public void stopAllAnimation() {
      for (AnimationState state : this.getAnimations()) {
         state.stop();
      }
   }

   public int getCurrentAnimation() {
      return (Integer)this.entityData.get(ANIM_STATE);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) {
      if (ANIM_STATE.equals(p_219422_) && this.level().isClientSide) {
         switch (this.entityData.get(ANIM_STATE)) {
            case 0:
               this.stopAllAnimation();
               break;
            case 1:
               this.stopAllAnimation();
               this.attackAnimationState.start(this.tickCount);
               break;
            case 2:
               this.stopAllAnimation();
               this.blessAnimationState.start(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_219422_);
   }

   public List<AnimationState> getAnimations() {
      List<AnimationState> animationStates = new ArrayList<>();
      animationStates.add(this.attackAnimationState);
      animationStates.add(this.blessAnimationState);
      return animationStates;
   }

   public boolean isCurrentAnimation(String string) {
      return this.getCurrentAnimation() == this.getAnimationState(string);
   }

   public boolean isAlliedTo(Entity p_32665_) {
      if (p_32665_ == null) {
         return false;
      } else if (p_32665_ == this) {
         return true;
      } else {
         return !(p_32665_ instanceof Zombie) ? super.isAlliedTo(p_32665_) : this.getTeam() == null && p_32665_.getTeam() == null;
      }
   }

   public boolean canBreakDoors() {
      return this.canBreakDoors;
   }

   public void setCanBreakDoors(boolean p_34337_) {
      if (this.supportsBreakDoorGoal() && GoalUtils.hasGroundPathNavigation(this)) {
         if (this.canBreakDoors != p_34337_) {
            this.canBreakDoors = p_34337_;
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(p_34337_);
            if (p_34337_) {
               this.goalSelector.addGoal(1, this.breakDoorGoal);
            } else {
               this.goalSelector.removeGoal(this.breakDoorGoal);
            }
         }
      } else if (this.canBreakDoors) {
         this.goalSelector.removeGoal(this.breakDoorGoal);
         this.canBreakDoors = false;
      }
   }

   protected boolean supportsBreakDoorGoal() {
      return true;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ZOMBIE_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource p_34327_) {
      return SoundEvents.ZOMBIE_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ZOMBIE_DEATH;
   }

   protected SoundEvent getStepSound() {
      return SoundEvents.ZOMBIE_STEP;
   }

   protected void playStepSound(BlockPos p_34316_, BlockState p_34317_) {
      this.playSound(this.getStepSound(), 0.15F, 1.0F);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("CanBreakDoors", this.canBreakDoors());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setCanBreakDoors(compoundTag.getBoolean("CanBreakDoors"));
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_) {
      SpawnGroupData data = super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_);
      this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 2, false, false));

      for (Direction direction : Direction.values()) {
         if (direction.getAxis() == Axis.X) {
            Vec3 vec3 = this.position().relative(direction, 2.0);
            Zombie zombie = new Zombie(p_21434_.getLevel());
            zombie.finalizeSpawn(p_21434_, p_21435_, MobSpawnType.MOB_SUMMONED, p_21437_);
            zombie.setPos(vec3);
            if (p_21434_.addFreshEntity(zombie)) {
               p_21434_.getLevel().sendParticles(ParticleTypes.POOF, vec3.x, vec3.y, vec3.z, 8, 0.2, 0.3, 0.2, 0.1);
            }
         }
      }

      return data;
   }

   @Override
   protected boolean isSunSensitive() {
      return true;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         if (!this.walkAnimation.isMoving()) {
            if (this.isWalking) {
               this.isWalking = false;
               this.toIdleTick = MathHelper.secondsToTicks(0.5F);
            }
         } else {
            this.isWalking = true;
         }

         if (this.toIdleTick > 0) {
            this.toIdleTick--;
         }

         boolean flag = !this.walkAnimation.isMoving() && this.isCurrentAnimation(IDLE);
         this.toIdleAnimationState.animateWhen(this.toIdleTick > 0 && flag, this.tickCount);
         this.idleAnimationState.animateWhen(this.toIdleTick <= 0 && flag, this.tickCount);
         this.crownAnimationState.startIfStopped(this.tickCount);
      } else {
         if (this.getTarget() != null) {
            this.setAggressive(true);
            this.level().broadcastEntityEvent(this, (byte)4);
         } else {
            this.setAggressive(false);
            this.level().broadcastEntityEvent(this, (byte)5);
         }

         if (this.attackTick > 0) {
            this.attackTick--;
         } else if (this.isCurrentAnimation(ATTACK)) {
            this.setAnimationState(IDLE);
         }
      }
   }

   public void swing(InteractionHand p_21007_) {
      super.swing(p_21007_);
      this.setAnimationState(ATTACK);
      this.attackTick = MathHelper.secondsToTicks(0.75F);
   }

   public boolean doHurtTarget(Entity entity) {
      boolean flag = super.doHurtTarget(entity);
      if (flag && entity instanceof LivingEntity livingEntity) {
         livingEntity.addEffect(new MobEffectInstance(RVEffects.CHOSEN_VICTIM, 400, 0));
      }

      return flag;
   }

   public boolean killedEntity(ServerLevel p_219160_, LivingEntity p_219161_) {
      boolean flag = super.killedEntity(p_219160_, p_219161_);
      if ((p_219160_.getDifficulty() == Difficulty.NORMAL || p_219160_.getDifficulty() == Difficulty.HARD)
         && p_219161_ instanceof Villager villager
         && EventHooks.canLivingConvert(p_219161_, EntityType.ZOMBIE_VILLAGER, timer -> {})) {
         if (p_219160_.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
            return flag;
         }

         ZombieVillager zombievillager = (ZombieVillager)villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
         if (zombievillager != null) {
            zombievillager.finalizeSpawn(
               p_219160_, p_219160_.getCurrentDifficultyAt(zombievillager.blockPosition()), MobSpawnType.CONVERSION, new ZombieGroupData(false, true)
            );
            zombievillager.setVillagerData(villager.getVillagerData());
            zombievillager.setGossips((Tag)villager.getGossips().store(NbtOps.INSTANCE));
            zombievillager.setTradeOffers(villager.getOffers().copy());
            zombievillager.setVillagerXp(villager.getVillagerXp());
            EventHooks.onLivingConvert(villager, zombievillager);
            if (!this.isSilent()) {
               p_219160_.levelEvent((Player)null, 1026, this.blockPosition(), 0);
            }

            flag = false;
         }
      }

      return flag;
   }

   public void handleEntityEvent(byte p_21375_) {
      if (p_21375_ == 4) {
         this.setAggressive(true);
      } else if (p_21375_ == 5) {
         this.setAggressive(false);
      } else {
         super.handleEntityEvent(p_21375_);
      }
   }

   static class KnightingGoal extends Goal {
      public static int FLASH_TIME = 15;
      public RoyalZombie royal;
      public List<Zombie> list = new ArrayList<>();
      public int blessTick;

      public KnightingGoal(RoyalZombie zombie) {
         this.royal = zombie;
         this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
      }

      public boolean canUse() {
         if (this.royal.getTarget() != null && this.royal.knightCool <= 0) {
            List<Zombie> list = this.royal.level().getEntitiesOfClass(Zombie.class, this.royal.getBoundingBox().inflate(10.0));
            list.removeIf(zombie -> !zombie.hasLineOfSight(this.royal) && MobUtil.areAllies(zombie, this.royal.getTarget()));
            this.list = list;
            return !this.list.isEmpty();
         } else {
            return false;
         }
      }

      public boolean canContinueToUse() {
         return this.blessTick > 0;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void start() {
         super.start();
         this.royal.setAnimationState(RoyalZombie.BLESS);
         this.royal.getNavigation().stop();
         this.royal.getMoveControl().strafe(0.0F, 0.0F);
         this.blessTick = FLASH_TIME + 1;
         if (this.royal.getTarget() != null) {
            this.royal.getLookControl().setLookAt(this.royal.getTarget(), 100.0F, 100.0F);
            this.royal.lookAt(this.royal.getTarget(), 100.0F, 100.0F);
         }

         if (!this.list.isEmpty()) {
            for (Zombie zombie : this.list) {
               if (zombie.getItemBySlot(EquipmentSlot.HEAD).isEmpty() || zombie.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                  RVCapHelper.setFlashTick(zombie, FLASH_TIME);
               }
            }
         }
      }

      public void tick() {
         super.tick();
         this.blessTick--;
         this.royal.getNavigation().stop();
         this.royal.getMoveControl().strafe(0.0F, 0.0F);
         if (this.royal.getTarget() != null) {
            this.royal.getLookControl().setLookAt(this.royal.getTarget(), 100.0F, 100.0F);
            this.royal.lookAt(this.royal.getTarget(), 100.0F, 100.0F);
         }

         if (this.blessTick == 1 && !this.list.isEmpty()) {
            for (Zombie zombie : this.list) {
               if (this.royal.level() instanceof ServerLevel serverLevel) {
                  if (zombie.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                     zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike)RVItems.ROYAL_KNIGHT_HELMET.get()));
                     serverLevel.sendParticles(
                        (SimpleParticleType)RVParticleTypes.SPARKLE.get(),
                        zombie.getX(),
                        zombie.getY() + zombie.getBbHeight(),
                        zombie.getZ(),
                        serverLevel.getRandom().nextIntBetweenInclusive(5, 6),
                        0.2,
                        0.3,
                        0.2,
                        0.1
                     );
                  } else {
                     serverLevel.sendParticles(
                        (SimpleParticleType)RVParticleTypes.SHINE.get(),
                        zombie.getX(),
                        zombie.getY() + 1.3,
                        zombie.getZ(),
                        serverLevel.getRandom().nextIntBetweenInclusive(5, 6),
                        0.2,
                        0.3,
                        0.2,
                        0.1
                     );
                  }

                  if (zombie.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                     Item item = Items.IRON_SWORD;
                     if (this.royal.level().getRandom().nextFloat() <= 0.25F) {
                        item = Items.IRON_AXE;
                     }

                     zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(item));
                  }

                  zombie.addEffect(new MobEffectInstance(RVEffects.KNIGHTLY_FORTITUDE, 400, 0, false, false));
                  zombie.playSound(SoundEvents.AMETHYST_CLUSTER_FALL, 0.9F, 1.0F);
                  serverLevel.playSound(null, zombie.getX(), zombie.getY(), zombie.getZ(), SoundEvents.AMETHYST_CLUSTER_FALL, SoundSource.HOSTILE, 0.9F, 1.0F);

                  for (int i = 0; i <= serverLevel.getRandom().nextInt(10) + 10; i++) {
                     serverLevel.sendParticles(
                        (SimpleParticleType)RVParticleTypes.HOLY.get(),
                        zombie.getRandomX(1.5),
                        zombie.getRandomY(),
                        zombie.getRandomZ(1.5),
                        0,
                        0.0,
                        1.0,
                        0.0,
                        1.0
                     );
                  }

                  zombie.setTarget(this.royal.getTarget());
               }
            }
         }
      }

      public void stop() {
         super.stop();
         this.blessTick = 0;
         this.royal.knightCool = 100;
         this.royal.setAnimationState(RoyalZombie.IDLE);
         this.list.clear();
      }
   }

   class ZombieAttackTurtleEggGoal extends RemoveBlockGoal {
      ZombieAttackTurtleEggGoal(PathfinderMob p_34344_, double p_34345_, int p_34346_) {
         super(Blocks.TURTLE_EGG, p_34344_, p_34345_, p_34346_);
      }

      public void playDestroyProgressSound(LevelAccessor p_34351_, BlockPos p_34352_) {
         p_34351_.playSound(null, p_34352_, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + RoyalZombie.this.random.nextFloat() * 0.2F);
      }

      public void playBreakSound(Level p_34348_, BlockPos p_34349_) {
         p_34348_.playSound(null, p_34349_, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + p_34348_.random.nextFloat() * 0.2F);
      }

      public double acceptedDistance() {
         return 1.14;
      }
   }
}
