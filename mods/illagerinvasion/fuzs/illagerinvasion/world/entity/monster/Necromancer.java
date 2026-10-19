package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.entity.monster.SpellcasterIllager.IllagerSpell;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterCastingSpellGoal;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterUseSpellGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

public class Necromancer extends SpellcasterIllager implements PowerableMob {
   private static final EntityDataAccessor<Boolean> DATA_IS_SHIELDED = SynchedEntityData.defineId(Necromancer.class, EntityDataSerializers.BOOLEAN);
   private int conjureSkullCooldown;

   public Necromancer(EntityType<? extends Necromancer> entityType, Level world) {
      super(entityType, world);
      this.xpReward = 15;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Necromancer.LookAtTargetOrWololoTarget());
      this.goalSelector.addGoal(4, new Necromancer.ConjureSkullGoal());
      this.goalSelector.addGoal(3, new Necromancer.SummonUndeadGoal());
      this.goalSelector.addGoal(5, new AvoidEntityGoal(this, Player.class, 8.0F, 0.6, 1.0));
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_IS_SHIELDED, false);
   }

   public boolean getShieldedState() {
      return (Boolean)this.entityData.get(DATA_IS_SHIELDED);
   }

   public void setShieldedState(boolean isShielded) {
      this.entityData.set(DATA_IS_SHIELDED, isShielded);
   }

   public SoundEvent getCelebrateSound() {
      return SoundEvents.ILLUSIONER_AMBIENT;
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.conjureSkullCooldown--;
      List<Mob> mobs = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(10.0), mob -> mob.getType().is(EntityTypeTags.UNDEAD));
      if (!mobs.isEmpty()) {
         mobs.forEach(this::doUndeadLinkLogic);
         if (this.tickCount % 10 == 0) {
            mobs.forEach(this::doUndeadLinkParticle);
         }
      }

      if (this.tickCount % 20 == 0) {
         this.setShieldedState(!mobs.isEmpty());
      }

      if (this.getTarget() != null) {
         mobs = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(30.0), mob -> mob.getType().is(EntityTypeTags.UNDEAD));
         mobs.forEach(this::setUndeadTarget);
      }
   }

   public boolean canAttack(LivingEntity target) {
      return !target.getType().is(EntityTypeTags.UNDEAD) && super.canAttack(target);
   }

   public void doUndeadLinkLogic(LivingEntity entity) {
      entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10, 0));
      entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0));
      entity.clearFire();
      if (entity instanceof Mob mob) {
         mob.setTarget(this.getTarget());
      }
   }

   public void setUndeadTarget(LivingEntity entity) {
      if (this.getTarget() != null && entity instanceof Mob) {
         ((Mob)entity).setTarget(this.getTarget());
      }
   }

   public void doUndeadLinkParticle(LivingEntity entity) {
      if (!this.level().isClientSide) {
         double x = entity.getX();
         double y = entity.getY();
         double z = entity.getZ();
         ((ServerLevel)this.level())
            .sendParticles((SimpleParticleType)ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value(), x, y + 1.0, z, 1, 0.4, 0.5, 0.4, 0.015);
         ((ServerLevel)this.level())
            .sendParticles(
               (SimpleParticleType)ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value(), this.getX(), this.getY() + 1.0, this.getZ(), 1, 0.4, 0.5, 0.4, 0.015
            );
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      return super.hurt(source, this.getShieldedState() ? amount / 2.0F : amount);
   }

   public boolean isAlliedTo(Entity other) {
      if (other == null) {
         return false;
      } else if (other == this) {
         return true;
      } else if (super.isAlliedTo(other)) {
         return true;
      } else {
         return other instanceof Vex
            ? this.isAlliedTo(((Vex)other).getOwner())
            : other instanceof LivingEntity livingEntity
               && livingEntity.getType().is(EntityTypeTags.ILLAGER_FRIENDS)
               && this.getTeam() == null
               && other.getTeam() == null;
      }
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ILLUSIONER_AMBIENT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ILLUSIONER_DEATH;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ILLUSIONER_HURT;
   }

   protected SoundEvent getCastingSoundEvent() {
      return SoundEvents.EVOKER_CAST_SPELL;
   }

   public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
   }

   public IllagerArmPose getArmPose() {
      return this.isCastingSpell() ? IllagerArmPose.SPELLCASTING : IllagerArmPose.CROSSED;
   }

   public boolean isPowered() {
      return this.getShieldedState();
   }

   public class ConjureSkullGoal extends SpellcasterUseSpellGoal {
      public ConjureSkullGoal() {
         super(Necromancer.this);
      }

      public boolean canUse() {
         if (Necromancer.this.getTarget() == null) {
            return false;
         } else if (Necromancer.this.conjureSkullCooldown > 0) {
            return false;
         } else {
            return Necromancer.this.conjureSkullCooldown <= 0 ? true : true;
         }
      }

      public void stop() {
         super.stop();
      }

      private void shootSkullAt(LivingEntity target) {
         this.shootSkullAt(target.getX(), target.getY() + target.getEyeHeight() * 0.5, target.getZ());
      }

      private void shootSkullAt(double targetX, double targetY, double targetZ) {
         double d = Necromancer.this.getX();
         double e = Necromancer.this.getY() + 2.5;
         double f = Necromancer.this.getZ();
         double g = targetX - d;
         double h = targetY - e;
         double i = targetZ - f;
         SkullBolt skullbolt = new SkullBolt(Necromancer.this.level(), Necromancer.this, g, h, i);
         skullbolt.setOwner(Necromancer.this);
         skullbolt.setPosRaw(d, e, f);
         Necromancer.this.level().addFreshEntity(skullbolt);
      }

      protected void performSpellCasting() {
         this.shootSkullAt(Necromancer.this.getTarget());
         if (!Necromancer.this.level().isClientSide) {
            double x = Necromancer.this.getX();
            double y = Necromancer.this.getY() + 2.5;
            double z = Necromancer.this.getZ();
            ((ServerLevel)Necromancer.this.level()).sendParticles(ParticleTypes.SMOKE, x, y, z, 40, 0.4, 0.4, 0.4, 0.15);
         }

         Necromancer.this.conjureSkullCooldown = 100;
      }

      protected int getCastWarmupTime() {
         return 40;
      }

      protected int getCastingTime() {
         return 60;
      }

      protected int getCastingInterval() {
         return 140;
      }

      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.EVOKER_PREPARE_ATTACK;
      }

      protected IllagerSpell getSpell() {
         return ModRegistry.CONJURE_SKULL_BOLT_ILLAGER_SPELL;
      }
   }

   class LookAtTargetOrWololoTarget extends SpellcasterCastingSpellGoal {
      LookAtTargetOrWololoTarget() {
         super(Necromancer.this);
      }

      public void tick() {
         if (Necromancer.this.getTarget() != null) {
            Necromancer.this.getLookControl().setLookAt(Necromancer.this.getTarget(), Necromancer.this.getMaxHeadYRot(), Necromancer.this.getMaxHeadXRot());
         }
      }
   }

   class SummonUndeadGoal extends SpellcasterUseSpellGoal {
      private final TargetingConditions closeVexPredicate = TargetingConditions.forNonCombat().range(16.0).ignoreLineOfSight().ignoreInvisibilityTesting();
      private int spellcount;

      SummonUndeadGoal() {
         super(Necromancer.this);
      }

      public boolean canUse() {
         if (!super.canUse()) {
            return false;
         }

         if (this.spellcount >= 4) {
            return false;
         }

         int i = Necromancer.this.level()
            .getNearbyEntities(Zombie.class, this.closeVexPredicate, Necromancer.this, Necromancer.this.getBoundingBox().inflate(16.0))
            .size();
         return Necromancer.this.random.nextInt(5) + 1 > i;
      }

      protected int getCastingTime() {
         return 100;
      }

      protected int getCastingInterval() {
         return 340;
      }

      protected void performSpellCasting() {
         int spawnAmount = Necromancer.this.level().isNight() ? 4 : 2;
         ServerLevel serverWorld = (ServerLevel)Necromancer.this.level();

         for (int i = 0; i < spawnAmount; i++) {
            BlockPos blockPos = Necromancer.this.blockPosition().offset(-2 + Necromancer.this.random.nextInt(5), 1, -2 + Necromancer.this.random.nextInt(5));
            Zombie zombieEntity = (Zombie)EntityType.ZOMBIE.create(Necromancer.this.level());
            zombieEntity.moveTo(blockPos, 0.0F, 0.0F);
            zombieEntity.finalizeSpawn(serverWorld, Necromancer.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, null);
            serverWorld.addFreshEntityWithPassengers(zombieEntity);
         }

         BlockPos blockPos = Necromancer.this.blockPosition().offset(-2 + Necromancer.this.random.nextInt(5), 1, -2 + Necromancer.this.random.nextInt(5));
         Skeleton skeletonEntity = (Skeleton)EntityType.SKELETON.create(Necromancer.this.level());
         skeletonEntity.moveTo(blockPos, 0.0F, 0.0F);
         skeletonEntity.finalizeSpawn(serverWorld, Necromancer.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, null);
         serverWorld.addFreshEntityWithPassengers(skeletonEntity);
         this.spellcount++;
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)ModSoundEvents.NECROMANCER_SUMMON_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return ModRegistry.NECRO_RAISE_ILLAGER_SPELL;
      }
   }
}
