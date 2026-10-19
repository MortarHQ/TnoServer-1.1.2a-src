package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModSoundEvents;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.entity.monster.SpellcasterIllager.IllagerSpell;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterCastingSpellGoal;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterUseSpellGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Provoker extends SpellcasterIllager implements RangedAttackMob {
   private int buffAllyCooldown;

   public Provoker(EntityType<? extends Provoker> entityType, Level world) {
      super(entityType, world);
      this.xpReward = 10;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Provoker.LookAtTargetOrWololoTarget());
      this.goalSelector.addGoal(3, new Provoker.BuffAllyGoal());
      this.goalSelector.addGoal(4, new RangedBowAttackGoal(this, 0.5, 20, 15.0F));
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
      return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
   }

   public void performRangedAttack(LivingEntity target, float pullProgress) {
      ItemStack itemInHand = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
      ItemStack itemStack = this.getProjectile(itemInHand);
      AbstractArrow persistentProjectileEntity = ProjectileUtil.getMobArrow(this, itemStack, pullProgress, itemInHand);
      double d = target.getX() - this.getX();
      double e = target.getY(0.3333333333333333) - persistentProjectileEntity.getY();
      double f = target.getZ() - this.getZ();
      double g = Math.sqrt(d * d + f * f);
      persistentProjectileEntity.shoot(d, e + g * 0.2F, f, 1.6F, 14 - this.level().getDifficulty().getId() * 4);
      this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      this.level().addFreshEntity(persistentProjectileEntity);
   }

   public float getWalkTargetValue(BlockPos pos, LevelReader level) {
      return 0.0F;
   }

   public int getMaxSpawnClusterSize() {
      return 1;
   }

   public SoundEvent getCelebrateSound() {
      return (SoundEvent)ModSoundEvents.PROVOKER_CELEBRATE_SOUND_EVENT.value();
   }

   protected void customServerAiStep() {
      this.buffAllyCooldown--;
      super.customServerAiStep();
   }

   public boolean isAlliedTo(Entity other) {
      if (other == null) {
         return false;
      } else if (other == this) {
         return true;
      } else if (super.isAlliedTo(other)) {
         return true;
      } else if (other instanceof Vex) {
         return this.isAlliedTo(((Vex)other).getOwner());
      } else {
         return other instanceof LivingEntity livingEntity && livingEntity.getType().is(EntityTypeTags.ILLAGER_FRIENDS)
            ? this.getTeam() == null && other.getTeam() == null
            : false;
      }
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSoundEvents.PROVOKER_AMBIENT_SOUND_EVENT.value();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSoundEvents.PROVOKER_DEATH_SOUND_EVENT.value();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)ModSoundEvents.PROVOKER_HURT_SOUND_EVENT.value();
   }

   protected SoundEvent getCastingSoundEvent() {
      return SoundEvents.EVOKER_CAST_SPELL;
   }

   public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
   }

   public IllagerArmPose getArmPose() {
      if (this.isCastingSpell()) {
         return IllagerArmPose.SPELLCASTING;
      } else {
         return this.isAggressive() ? IllagerArmPose.BOW_AND_ARROW : IllagerArmPose.CROSSED;
      }
   }

   public class BuffAllyGoal extends SpellcasterUseSpellGoal {
      public BuffAllyGoal() {
         super(Provoker.this);
      }

      public boolean canUse() {
         return Provoker.this.getTarget() == null ? false : Provoker.this.buffAllyCooldown < 0;
      }

      private List<LivingEntity> getTargets() {
         return Provoker.this.level()
            .getEntitiesOfClass(LivingEntity.class, Provoker.this.getBoundingBox().inflate(12.0), entity -> entity instanceof AbstractIllager);
      }

      public void stop() {
         super.stop();
      }

      private void buff(LivingEntity entity) {
         entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 0));
         entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 120, 0));
         double x = entity.getX();
         double y = entity.getY() + 1.0;
         double z = entity.getZ();
         ((ServerLevel)Provoker.this.level()).sendParticles(ParticleTypes.ANGRY_VILLAGER, x, y, z, 10, 0.4, 0.4, 0.4, 0.15);
      }

      protected void performSpellCasting() {
         Provoker.this.buffAllyCooldown = 300;
         this.getTargets().forEach(this::buff);
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
         return SoundEvents.ILLUSIONER_PREPARE_BLINDNESS;
      }

      protected IllagerSpell getSpell() {
         return ModRegistry.PROVOKE_ILLAGER_SPELL;
      }
   }

   class LookAtTargetOrWololoTarget extends SpellcasterCastingSpellGoal {
      LookAtTargetOrWololoTarget() {
         super(Provoker.this);
      }

      public void tick() {
         if (Provoker.this.getTarget() != null) {
            Provoker.this.getLookControl().setLookAt(Provoker.this.getTarget(), Provoker.this.getMaxHeadYRot(), Provoker.this.getMaxHeadXRot());
         }
      }
   }
}
