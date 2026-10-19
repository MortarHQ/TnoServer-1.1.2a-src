package twilightforest.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.Zombie.ZombieGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.boss.Lich;
import twilightforest.init.TFAttributes;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class LichMinion extends Zombie {
   @Nullable
   public Lich master;

   public LichMinion(EntityType<? extends LichMinion> type, Level world) {
      super(type, world);
      this.master = null;
   }

   public LichMinion(Level world, Lich entityTFLich) {
      super((EntityType)TFEntities.LICH_MINION.get(), world);
      this.master = entityTFLich;
   }

   protected void addBehaviourGoals() {
      this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Lich.class) {
         protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
            return !(potentialTarget instanceof Lich) && super.canAttack(potentialTarget, targetPredicate);
         }
      });
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public boolean hurt(DamageSource source, float amount) {
      LivingEntity prevTarget = this.getTarget();
      if (super.hurt(source, amount)) {
         if (source.getEntity() instanceof Lich) {
            this.setLastHurtByMob(prevTarget);
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2));
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1));
         }

         return true;
      } else {
         return false;
      }
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.MINION_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)TFSounds.MINION_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.MINION_DEATH.get();
   }

   protected SoundEvent getStepSound() {
      return (SoundEvent)TFSounds.MINION_STEP.get();
   }

   public void aiStep() {
      if (this.master == null) {
         this.findNewMaster();
      }

      if (this.master == null || !this.master.isAlive()) {
         this.kill();
      }

      super.aiStep();
   }

   private void findNewMaster() {
      for (Lich nearbyLich : this.level()
         .getEntitiesOfClass(
            Lich.class, new AABB(this.getX(), this.getY(), this.getZ(), this.getX() + 1.0, this.getY() + 1.0, this.getZ() + 1.0).inflate(32.0, 16.0, 32.0)
         )) {
         if (!nearbyLich.isShadowClone() && nearbyLich.wantsNewMinion()) {
            this.master = nearbyLich;
            if (!this.level().isClientSide()) {
               this.master.makeMagicTrail(this.getEyePosition(), this.master.getEyePosition(), 0.0F, 0.0F, 0.0F);
            }

            this.setTarget(this.master.getTarget());
            break;
         }
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      boolean baby = false;
      if (this.master != null && difficulty.getDifficulty() == Difficulty.HARD) {
         int babiesSummoned = this.master.getBabyMinionsSummoned();
         if (babiesSummoned < this.master.getAttributeValue(TFAttributes.MINION_COUNT) / 4.0) {
            baby = this.getRandom().nextInt(100) <= 20;
         }

         if (baby) {
            this.master.setBabyMinionsSummoned(babiesSummoned + 1);
         }
      }

      SpawnGroupData var7 = new ZombieGroupData(baby, true);
      return super.finalizeSpawn(level, difficulty, spawnType, var7);
   }

   protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
   }

   public boolean canPickUpLoot() {
      return false;
   }
}
