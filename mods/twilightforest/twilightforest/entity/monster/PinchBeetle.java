package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.entity.IHostileMount;
import twilightforest.entity.ai.goal.ChargeAttackGoal;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFSounds;
import twilightforest.util.entities.EntityUtil;

public class PinchBeetle extends Monster implements IHostileMount {
   public PinchBeetle(EntityType<? extends PinchBeetle> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(2, new ChargeAttackGoal(this, 1.5F, false));
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 40.0)
         .add(Attributes.MOVEMENT_SPEED, 0.23)
         .add(Attributes.ATTACK_DAMAGE, 4.0)
         .add(Attributes.ARMOR, 2.0);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.PINCH_BEETLE_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.PINCH_BEETLE_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.PINCH_BEETLE_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound((SoundEvent)TFSounds.PINCH_BEETLE_STEP.get(), 0.15F, 1.0F);
   }

   public void aiStep() {
      super.aiStep();
      this.dimensions = this.getDimensions(this.getPose());
      if (!this.getPassengers().isEmpty()) {
         Entity passenger = (Entity)this.getPassengers().getFirst();
         if (passenger.getVehicle() != this) {
            this.removePassenger(passenger);
            return;
         }

         this.getLookControl().setLookAt(passenger, 100.0F, 100.0F);
         if (passenger instanceof LivingEntity entity) {
            this.setTarget(entity);
         }

         if (passenger instanceof Player player && player.getAbilities().invulnerable) {
            player.stopRiding();
            this.setTarget(null);
         }
      }
   }

   public void die(DamageSource source) {
      if (!this.getPassengers().isEmpty()) {
         this.getPassengers().forEach(Entity::stopRiding);
      }

      super.die(source);
   }

   public void knockback(double x, double y, double z) {
      if (this.getPassengers().isEmpty()) {
         super.knockback(x, y, z);
      }
   }

   public boolean doHurtTarget(Entity entity) {
      if (this.getPassengers().isEmpty()) {
         Entity v = entity.getVehicle();
         if (v == null || !v.getType().is(EntityTagGenerator.RIDES_OBSTRUCT_SNATCHING)) {
            entity.stopRiding();
            entity.startRiding(this, true);
         }
      }

      return EntityUtil.properlyApplyCustomDamageSource(this, entity, TFDamageTypes.getEntityDamageSource(this.level(), TFDamageTypes.CLAMPED, this), null);
   }

   public boolean startRiding(Entity entity, boolean force) {
      if (!(entity instanceof Boat boat)) {
         return super.startRiding(entity, force);
      } else {
         boat.kill();
         if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            for (int i = 0; i < 3; i++) {
               this.spawnAtLocation(boat.getVariant().getPlanks());
            }

            for (int j = 0; j < 2; j++) {
               this.spawnAtLocation(Items.STICK);
            }
         }

         this.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR);
         return false;
      }
   }

   protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float yRot) {
      return new Vec3(0.0, this.getEyeHeight(), 0.75);
   }

   public boolean canRiderInteract() {
      return true;
   }

   public EntityDimensions getDefaultDimensions(Pose pose) {
      return !this.getPassengers().isEmpty() ? EntityDimensions.scalable(2.2F, 1.6F) : super.getDefaultDimensions(pose);
   }
}
