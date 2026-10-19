package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.illagerinvasion.util.SetMagicFireUtil;
import fuzs.illagerinvasion.util.TeleportUtil;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.entity.monster.SpellcasterIllager.IllagerSpell;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterCastingSpellGoal;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterUseSpellGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

public class Sorcerer extends SpellcasterIllager {
   private int castTeleportCooldown;
   private int conjureFlamesCooldown;

   public Sorcerer(EntityType<? extends Sorcerer> entityType, Level world) {
      super(entityType, world);
      this.xpReward = 10;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Sorcerer.LookAtTargetOrWololoTarget());
      this.goalSelector.addGoal(4, new Sorcerer.CastTeleportGoal());
      this.goalSelector.addGoal(3, new Sorcerer.ConjureFlamesGoal());
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(5, new AvoidEntityGoal(this, Player.class, 8.0F, 0.6, 1.0));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
   }

   public SoundEvent getCelebrateSound() {
      return (SoundEvent)ModSoundEvents.SORCERER_CELEBRATE_SOUND_EVENT.value();
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.castTeleportCooldown--;
      this.conjureFlamesCooldown--;
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
      return (SoundEvent)ModSoundEvents.SORCERER_AMBIENT_SOUND_EVENT.value();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSoundEvents.SORCERER_DEATH_SOUND_EVENT.value();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)ModSoundEvents.SORCERER_HURT_SOUND_EVENT.value();
   }

   protected SoundEvent getCastingSoundEvent() {
      return (SoundEvent)ModSoundEvents.SORCERER_COMPLETE_CAST_SOUND_EVENT.value();
   }

   public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
   }

   public IllagerArmPose getArmPose() {
      return this.isCastingSpell() ? IllagerArmPose.SPELLCASTING : IllagerArmPose.CROSSED;
   }

   public class CastTeleportGoal extends SpellcasterUseSpellGoal {
      public CastTeleportGoal() {
         super(Sorcerer.this);
      }

      public boolean canUse() {
         if (Sorcerer.this.getTarget() == null) {
            return false;
         } else {
            return Sorcerer.this.isCastingSpell() ? false : Sorcerer.this.castTeleportCooldown < 0 && !this.getTargets().isEmpty();
         }
      }

      private List<LivingEntity> getTargets() {
         return Sorcerer.this.level()
            .getEntitiesOfClass(
               LivingEntity.class, Sorcerer.this.getBoundingBox().inflate(8.0), entity -> entity instanceof Player || entity instanceof IronGolem
            );
      }

      public boolean canContinueToUse() {
         return !this.getTargets().isEmpty();
      }

      protected void performSpellCasting() {
         Sorcerer.this.castTeleportCooldown = 220;
         double x = Sorcerer.this.getX();
         double y = Sorcerer.this.getY() + 1.0;
         double z = Sorcerer.this.getZ();
         if (Sorcerer.this.level() instanceof ServerLevel) {
            ((ServerLevel)Sorcerer.this.level()).sendParticles(ParticleTypes.WITCH, x, y, z, 30, 0.3, 0.5, 0.3, 0.015);
         }

         TeleportUtil.tryRandomTeleport(Sorcerer.this);
      }

      protected int getCastWarmupTime() {
         return 30;
      }

      protected int getCastingTime() {
         return 30;
      }

      protected int getCastingInterval() {
         return 400;
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)ModSoundEvents.SORCERER_CAST_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return ModRegistry.CONJURE_TELEPORT_ILLAGER_SPELL;
      }
   }

   public class ConjureFlamesGoal extends SpellcasterUseSpellGoal {
      public ConjureFlamesGoal() {
         super(Sorcerer.this);
      }

      public boolean canUse() {
         if (Sorcerer.this.getTarget() == null) {
            return false;
         } else {
            return Sorcerer.this.isCastingSpell() ? false : Sorcerer.this.conjureFlamesCooldown < 0;
         }
      }

      protected void performSpellCasting() {
         LivingEntity target = Sorcerer.this.getTarget();
         SetMagicFireUtil.trySetFire(target, Sorcerer.this.level());
         Sorcerer.this.conjureFlamesCooldown = 100;
         target.hurt(Sorcerer.this.damageSources().magic(), 3.0F);
         if (Sorcerer.this.level() instanceof ServerLevel) {
            ((ServerLevel)Sorcerer.this.level())
               .sendParticles(
                  (SimpleParticleType)ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.value(), target.getX(), target.getY() + 1.0, target.getZ(), 30, 0.3, 0.5, 0.3, 0.08
               );
         }
      }

      protected int getCastWarmupTime() {
         return 60;
      }

      protected int getCastingTime() {
         return 60;
      }

      protected int getCastingInterval() {
         return 140;
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)ModSoundEvents.SORCERER_CAST_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return ModRegistry.CONJURE_FLAMES_ILLAGER_SPELL;
      }
   }

   class LookAtTargetOrWololoTarget extends SpellcasterCastingSpellGoal {
      LookAtTargetOrWololoTarget() {
         super(Sorcerer.this);
      }

      public void tick() {
         if (Sorcerer.this.getTarget() != null) {
            Sorcerer.this.getLookControl().setLookAt(Sorcerer.this.getTarget(), Sorcerer.this.getMaxHeadYRot(), Sorcerer.this.getMaxHeadXRot());
         }
      }
   }
}
