package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.joml.Vector3f;
import twilightforest.init.TFSounds;

public class CarminiteGolem extends Monster {
   private int attackTimer;

   public CarminiteGolem(EntityType<? extends CarminiteGolem> type, Level world) {
      super(type, world);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 40.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.ATTACK_DAMAGE, 9.0)
         .add(Attributes.ARMOR, 2.0);
   }

   public boolean doHurtTarget(Entity entity) {
      boolean attackSuccess = super.doHurtTarget(entity);
      if (attackSuccess) {
         this.attackTimer = 10;
         this.level().broadcastEntityEvent(this, (byte)4);
         entity.push(0.0, 0.4, 0.0);
      }

      return attackSuccess;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.CARMINITE_GOLEM_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.CARMINITE_GOLEM_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound((SoundEvent)TFSounds.CARMINITE_GOLEM_STEP.get(), 1.0F, 1.0F);
   }

   public void aiStep() {
      super.aiStep();
      if (this.attackTimer > 0) {
         this.attackTimer--;
      }

      if (this.getRandom().nextBoolean()) {
         this.level()
            .addParticle(
               new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), 1.0F),
               this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(),
               this.getY() + this.getRandom().nextDouble() * this.getBbHeight() - 0.25,
               this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(),
               0.0,
               0.0,
               0.0
            );
      }
   }

   public void handleEntityEvent(byte id) {
      if (id == 4) {
         this.attackTimer = 10;
         this.playSound((SoundEvent)TFSounds.CARMINITE_GOLEM_ATTACK.get(), 1.0F, 1.0F);
      } else {
         super.handleEntityEvent(id);
      }
   }

   public int getAttackTimer() {
      return this.attackTimer;
   }

   public int getMaxSpawnClusterSize() {
      return 2;
   }

   public boolean canSpawnSprintParticle() {
      return this.getDeltaMovement().horizontalDistanceSqr() > 2.5000003E-7F && this.getRandom().nextInt(5) == 0;
   }
}
