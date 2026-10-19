package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.IBreathAttacker;
import twilightforest.entity.ai.goal.BreathAttackGoal;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFSounds;

public class FireBeetle extends Monster implements IBreathAttacker {
   private static final EntityDataAccessor<Boolean> BREATHING = SynchedEntityData.defineId(FireBeetle.class, EntityDataSerializers.BOOLEAN);
   private static final int BREATH_DURATION = 10;
   private static final int BREATH_DAMAGE = 2;

   public FireBeetle(EntityType<? extends FireBeetle> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(2, new BreathAttackGoal(this, 5.0F, 30, 0.1F));
      this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BREATHING, false);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder registerAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 25.0).add(Attributes.MOVEMENT_SPEED, 0.23).add(Attributes.ATTACK_DAMAGE, 4.0);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.FIRE_BEETLE_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.FIRE_BEETLE_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.FIRE_BEETLE_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState block) {
      this.playSound((SoundEvent)TFSounds.FIRE_BEETLE_STEP.get(), 0.15F, 1.0F);
   }

   @Override
   public boolean isBreathing() {
      return (Boolean)this.getEntityData().get(BREATHING);
   }

   @Override
   public void setBreathing(boolean flag) {
      this.getEntityData().set(BREATHING, flag);
   }

   public void aiStep() {
      super.aiStep();
      if (this.isBreathing()) {
         Vec3 look = this.getLookAngle();
         double dist = 0.9;
         double px = this.getX() + look.x() * dist;
         double py = this.getY() + 0.25 + look.y() * dist;
         double pz = this.getZ() + look.z() * dist;

         for (int i = 0; i < 2; i++) {
            double dx = look.x();
            double dy = look.y();
            double dz = look.z();
            double spread = 5.0 + this.getRandom().nextDouble() * 2.5;
            double velocity = 0.15 + this.getRandom().nextDouble() * 0.15;
            dx += this.getRandom().nextGaussian() * 0.0075 * spread;
            dy += this.getRandom().nextGaussian() * 0.0075 * spread;
            dz += this.getRandom().nextGaussian() * 0.0075 * spread;
            dx *= velocity;
            dy *= velocity;
            dz *= velocity;
            this.level().addParticle(ParticleTypes.FLAME, px, py, pz, dx, dy, dz);
         }

         this.playSound((SoundEvent)TFSounds.FIRE_BEETLE_SHOOT.get(), this.getRandom().nextFloat() * 0.5F, this.getRandom().nextFloat() * 0.5F);
         this.gameEvent(GameEvent.PROJECTILE_SHOOT);
      }
   }

   public int getMaxHeadXRot() {
      return 500;
   }

   @Override
   public void doBreathAttack(Entity target) {
      if (!target.fireImmune() && target.hurt(TFDamageTypes.getEntityDamageSource(this.level(), TFDamageTypes.SCORCHED, this), 2.0F)) {
         target.igniteForSeconds(10.0F);
      }
   }

   public boolean doHurtTarget(Entity entity) {
      return this.isBreathing()
         ? entity.hurt(TFDamageTypes.getEntityDamageSource(this.level(), TFDamageTypes.SCORCHED, this), 2.0F)
         : super.doHurtTarget(entity);
   }
}
