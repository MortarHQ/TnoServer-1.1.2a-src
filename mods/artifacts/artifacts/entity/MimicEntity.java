package artifacts.entity;

import artifacts.Artifacts;
import artifacts.registry.ModSoundEvents;
import java.util.EnumSet;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public class MimicEntity extends Mob implements Enemy {
   public static final ResourceKey<LootTable> LOOT_TABLE = Artifacts.key(Registries.LOOT_TABLE, "entities/mimic");
   private static final int DORMANT_TICK_RATE = 20;
   public int ticksInAir;
   public int attackCooldown;
   public boolean isDormant;
   public Direction facing;
   private int tickRate = 1;

   public MimicEntity(EntityType<? extends MimicEntity> type, Level world) {
      super(type, world);
      this.moveControl = new MimicEntity.MimicMovementController(this);
      this.xpReward = 10;
   }

   public static Builder createMobAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 60.0)
         .add(Attributes.FOLLOW_RANGE, 16.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
         .add(Attributes.MOVEMENT_SPEED, 0.8)
         .add(Attributes.ATTACK_DAMAGE, 5.0);
   }

   public void setFacing(Direction facing) {
      this.facing = facing;
      if (facing != null && this.getMoveControl() instanceof MimicEntity.MimicMovementController controller) {
         controller.setDirection(facing.toYRot(), false);
      }
   }

   public void setDormant(boolean isDormant) {
      this.isDormant = isDormant;
      if (!isDormant) {
         this.facing = null;
      }
   }

   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   public boolean removeWhenFarAway(double distance) {
      return false;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new MimicEntity.FloatGoal(this));
      this.goalSelector.addGoal(2, new MimicEntity.AttackGoal(this));
      this.goalSelector.addGoal(3, new MimicEntity.PanicGoal());
      this.goalSelector.addGoal(4, new MimicEntity.FaceRandomGoal(this));
      this.goalSelector.addGoal(5, new MimicEntity.HopGoal(this));
      this.targetSelector.addGoal(1, new MimicEntity.NearestPlayerTargetGoal());
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("ticksInAir", this.ticksInAir);
      compound.putBoolean("isDormant", this.isDormant);
      if (this.facing != null) {
         compound.putString("facing", this.facing.name());
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.ticksInAir = compound.getInt("ticksInAir");
      if (compound.contains("facing")) {
         this.setFacing(Direction.byName(compound.getString("facing").toLowerCase()));
      } else {
         this.setFacing(null);
      }

      this.setDormant(compound.getBoolean("isDormant"));
   }

   public void tick() {
      if (this.tickCount % 20 == 0) {
         if (this.isDormant
            && !this.level().isClientSide()
            && this.onGround()
            && !this.isDeadOrDying()
            && !this.isPassenger()
            && this.getPassengers().isEmpty()
            && this.level().getNearestPlayer(this, 32.0) == null) {
            this.tickRate = 20;
         } else {
            this.tickRate = 1;
         }
      }

      if (this.tickCount % this.tickRate == 0) {
         super.tick();
         if (this.isInWater()) {
            this.ticksInAir = 0;
         } else if (!this.onGround()) {
            this.ticksInAir++;
         } else if (this.ticksInAir > 0) {
            this.playSound(this.getLandingSound(), this.getSoundVolume(), this.getVoicePitch());
            this.ticksInAir = 0;
         }

         if (this.attackCooldown > 0) {
            this.attackCooldown--;
         }
      }
   }

   public void playerTouch(Player player) {
      if (this.attackCooldown <= 0
         && player.level().getDifficulty() != Difficulty.PEACEFUL
         && this.isAlive()
         && this.isWithinMeleeAttackRange(player)
         && this.hasLineOfSight(player)) {
         this.attackCooldown = 20;
         DamageSource damageSource = this.damageSources().mobAttack(this);
         if (player.hurt(damageSource, (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue()) && this.level() instanceof ServerLevel level) {
            EnchantmentHelper.doPostAttackEffects(level, player, damageSource);
         }
      }
   }

   public void setTarget(LivingEntity entity) {
      this.setDormant(false);
      super.setTarget(entity);
   }

   public boolean isPushedByFluid() {
      return !this.isDormant;
   }

   protected int decreaseAirSupply(int i) {
      return this.isDormant ? i : super.decreaseAirSupply(i);
   }

   public boolean hurt(DamageSource source, float amount) {
      if (source.getEntity() instanceof Player player) {
         this.setTarget(player);
      }

      if (this.ticksInAir <= 0 && source.is(DamageTypeTags.IS_PROJECTILE) && !source.is(DamageTypeTags.BYPASSES_ARMOR)) {
         this.playSound((SoundEvent)ModSoundEvents.MIMIC_HURT.value(), this.getSoundVolume(), this.getVoicePitch());
         return false;
      }

      if (this.onGround() && this.getRandom().nextBoolean() && this.getMoveControl() instanceof MimicEntity.MimicMovementController controller) {
         controller.setDirection(this.getRandom().nextInt(4) * 90, true);
      }

      return super.hurt(source, amount);
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return (SoundEvent)ModSoundEvents.MIMIC_HURT.value();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSoundEvents.MIMIC_DEATH.value();
   }

   protected SoundEvent getJumpingSound() {
      return (SoundEvent)ModSoundEvents.MIMIC_OPEN.value();
   }

   protected SoundEvent getLandingSound() {
      return (SoundEvent)ModSoundEvents.MIMIC_CLOSE.value();
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LOOT_TABLE;
   }

   protected static class AttackGoal extends Goal {
      private final MimicEntity mimic;
      private int timeRemaining;

      public AttackGoal(MimicEntity mimic) {
         this.mimic = mimic;
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         return this.mimic.getTarget() instanceof Player player
            && player.isAlive()
            && player.getCommandSenderWorld().getDifficulty() != Difficulty.PEACEFUL
            && !player.getAbilities().invulnerable;
      }

      public void start() {
         this.timeRemaining = 300;
         super.start();
      }

      public boolean canContinueToUse() {
         return this.mimic.getTarget() instanceof Player player
            && player.isAlive()
            && player.getCommandSenderWorld().getDifficulty() != Difficulty.PEACEFUL
            && !player.getAbilities().invulnerable
            && --this.timeRemaining > 0;
      }

      public void tick() {
         super.tick();
         if (!this.mimic.isDormant) {
            if (this.mimic.getTarget() != null && this.mimic.getMoveControl() instanceof MimicEntity.MimicMovementController controller) {
               this.mimic.lookAt(this.mimic.getTarget(), 10.0F, 10.0F);
               controller.setDirection(this.mimic.getYRot(), true);
            }
         }
      }
   }

   protected static class FaceRandomGoal extends Goal {
      private final MimicEntity mimic;
      private int chosenDegrees;
      private int nextRandomizeTime;

      public FaceRandomGoal(MimicEntity mimic) {
         this.mimic = mimic;
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         return this.mimic.getTarget() == null
            && (this.mimic.onGround() || this.mimic.isInWater() || this.mimic.isInLava() || this.mimic.hasEffect(MobEffects.LEVITATION));
      }

      public void tick() {
         if (!this.mimic.isDormant) {
            if (--this.nextRandomizeTime <= 0) {
               this.nextRandomizeTime = 480 + this.mimic.getRandom().nextInt(320);
               this.chosenDegrees = this.mimic.getRandom().nextInt(4) * 90;
            }

            if (this.mimic.getMoveControl() instanceof MimicEntity.MimicMovementController controller) {
               controller.setDirection(this.chosenDegrees, false);
            }
         }
      }
   }

   protected static class FloatGoal extends Goal {
      private final MimicEntity mimic;

      public FloatGoal(MimicEntity mimic) {
         this.mimic = mimic;
         this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
         mimic.getNavigation().setCanFloat(true);
      }

      public boolean canUse() {
         return this.mimic.isInWater() || this.mimic.isInLava();
      }

      public void tick() {
         if (this.mimic.getRandom().nextFloat() < 0.8F) {
            this.mimic.jumpControl.jump();
         }

         if (this.mimic.getMoveControl() instanceof MimicEntity.MimicMovementController controller) {
            controller.setSpeed(1.2);
         }
      }
   }

   protected static class HopGoal extends Goal {
      private final MimicEntity mimic;

      public HopGoal(MimicEntity mimic) {
         this.mimic = mimic;
         this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
      }

      public boolean canUse() {
         return !this.mimic.isDormant && !this.mimic.isPassenger();
      }

      public void tick() {
         if (this.mimic.getMoveControl() instanceof MimicEntity.MimicMovementController controller) {
            controller.setSpeed(1.0);
         }
      }
   }

   protected static class MimicMovementController extends MoveControl {
      private final MimicEntity mimic;
      private float rotationDegrees;
      private int jumpDelay;

      public MimicMovementController(MimicEntity mimic) {
         super(mimic);
         this.mimic = mimic;
         this.rotationDegrees = 180.0F * mimic.getYRot() / (float) Math.PI;
         this.jumpDelay = mimic.random.nextInt(320) + 640;
      }

      public void setDirection(float rotation, boolean shouldJump) {
         this.rotationDegrees = rotation;
         if (shouldJump && this.jumpDelay > 10) {
            this.jumpDelay = 10;
         }
      }

      public void setSpeed(double speed) {
         this.speedModifier = speed;
         this.operation = Operation.MOVE_TO;
      }

      public void tick() {
         this.mimic.yHeadRot = this.mimic.yBodyRot = this.rotlerp(this.mimic.getYRot(), this.rotationDegrees, 90.0F);
         this.mimic.setYRot(this.mimic.yHeadRot);
         if (this.operation != Operation.MOVE_TO) {
            this.mimic.setZza(0.0F);
         } else {
            this.operation = Operation.WAIT;
            if (this.mimic.onGround()) {
               this.mimic.setSpeed((float)(this.speedModifier * this.mimic.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
               if (this.jumpDelay-- > 0) {
                  this.mimic.xxa = this.mimic.zza = 0.0F;
                  this.mimic.setSpeed(0.0F);
               } else {
                  this.jumpDelay = this.mimic.random.nextInt(320) + 640;
                  this.mimic.jumpControl.jump();
                  this.mimic.playSound(this.mimic.getJumpingSound(), this.mimic.getSoundVolume(), this.mimic.getVoicePitch());
               }
            } else {
               this.mimic.setSpeed((float)(this.speedModifier * this.mimic.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
            }
         }
      }
   }

   private class NearestPlayerTargetGoal extends NearestAttackableTargetGoal<Player> {
      public NearestPlayerTargetGoal() {
         super(MimicEntity.this, Player.class, 1, true, false, null);
         super.targetConditions = TargetingConditions.forNonCombat()
            .range(this.getFollowDistance())
            .selector(
               entity -> entity.canBeSeenAsEnemy()
                  && (
                     !MimicEntity.this.isDormant
                        || MimicEntity.this.distanceTo(entity) < MimicEntity.this.getAttribute(Attributes.FOLLOW_RANGE).getValue() / 2.5
                  )
            );
      }
   }

   protected class PanicGoal extends Goal {
      private int timeRemaining;

      public PanicGoal() {
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         return MimicEntity.this.level().getDifficulty() == Difficulty.PEACEFUL
            && MimicEntity.this.getTarget() instanceof Player player
            && player.isAlive()
            && !player.getAbilities().invulnerable;
      }

      public void start() {
         this.timeRemaining = 300;
         super.start();
      }

      public boolean canContinueToUse() {
         return MimicEntity.this.getTarget() instanceof Player player
            && player.isAlive()
            && player.getCommandSenderWorld().getDifficulty() == Difficulty.PEACEFUL
            && !player.getAbilities().invulnerable
            && --this.timeRemaining > 0;
      }

      public void tick() {
         super.tick();
         if (!MimicEntity.this.isDormant) {
            if (MimicEntity.this.onGround()
               && MimicEntity.this.getMoveControl() instanceof MimicEntity.MimicMovementController controller
               && controller.jumpDelay >= 10) {
               controller.setDirection(controller.rotationDegrees + MimicEntity.this.random.nextFloat() * 180.0F - 90.0F, true);
            }
         }
      }
   }
}
