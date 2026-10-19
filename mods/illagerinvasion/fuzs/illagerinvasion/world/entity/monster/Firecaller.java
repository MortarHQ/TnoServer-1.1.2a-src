package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.illagerinvasion.world.entity.projectile.FlyingMagma;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Ravager;
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
import net.minecraft.world.level.block.Blocks;

public class Firecaller extends SpellcasterIllager {
   private int conjureSkullCooldown = 160;
   private int areaDamageCooldown = 300;

   public Firecaller(EntityType<? extends Firecaller> entityType, Level world) {
      super(entityType, world);
      this.xpReward = 15;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Firecaller.LookAtTargetOrWololoTarget());
      this.goalSelector.addGoal(4, new Firecaller.ConjureSkullGoal());
      this.goalSelector.addGoal(3, new Firecaller.AreaDamageGoal());
      this.goalSelector.addGoal(5, new AvoidEntityGoal(this, Player.class, 8.0F, 0.6, 1.0));
      this.goalSelector.addGoal(5, new AvoidEntityGoal(this, IronGolem.class, 12.0F, 0.6, 1.0));
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
   }

   public SoundEvent getCelebrateSound() {
      return SoundEvents.ILLUSIONER_AMBIENT;
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.conjureSkullCooldown--;
      this.areaDamageCooldown--;
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
      return (SoundEvent)ModSoundEvents.FIRECALLER_AMBIENT_SOUND_EVENT.value();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSoundEvents.FIRECALLER_DEATH_SOUND_EVENT.value();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)ModSoundEvents.FIRECALLER_HURT_SOUND_EVENT.value();
   }

   protected SoundEvent getCastingSoundEvent() {
      return SoundEvents.FIRECHARGE_USE;
   }

   public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
   }

   public IllagerArmPose getArmPose() {
      return this.isCastingSpell() ? IllagerArmPose.SPELLCASTING : IllagerArmPose.CROSSED;
   }

   public class AreaDamageGoal extends SpellcasterUseSpellGoal {
      public AreaDamageGoal() {
         super(Firecaller.this);
      }

      public boolean canUse() {
         if (Firecaller.this.getTarget() == null) {
            return false;
         } else {
            return Firecaller.this.isCastingSpell() ? false : Firecaller.this.areaDamageCooldown <= 0;
         }
      }

      private List<LivingEntity> getTargets() {
         return Firecaller.this.level()
            .getEntitiesOfClass(
               LivingEntity.class,
               Firecaller.this.getBoundingBox().inflate(6.0),
               entity -> !(entity instanceof AbstractIllager) && !(entity instanceof Surrendered) && !(entity instanceof Ravager)
            );
      }

      private void buff(LivingEntity entity) {
         entity.push(0.0, 1.2F, 0.0);
         entity.hurt(Firecaller.this.damageSources().magic(), 6.0F);
         entity.setRemainingFireTicks(120);
         double x = entity.getX();
         double y = entity.getY() + 1.0;
         double z = entity.getZ();
         ((ServerLevel)Firecaller.this.level()).sendParticles(ParticleTypes.SMOKE, x, y + 1.0, z, 10, 0.2, 0.2, 0.2, 0.015);
         BlockPos blockPos = entity.blockPosition();
         Firecaller.this.level().setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
      }

      protected void performSpellCasting() {
         this.getTargets().forEach(this::buff);
         Firecaller.this.areaDamageCooldown = 300;
      }

      protected int getCastWarmupTime() {
         return 50;
      }

      protected int getCastingTime() {
         return 50;
      }

      protected int getCastingInterval() {
         return 400;
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)ModSoundEvents.FIRECALLER_CAST_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return ModRegistry.PROVOKE_ILLAGER_SPELL;
      }
   }

   public class ConjureSkullGoal extends SpellcasterUseSpellGoal {
      public ConjureSkullGoal() {
         super(Firecaller.this);
      }

      private List<LivingEntity> getTargets() {
         return Firecaller.this.level()
            .getEntitiesOfClass(
               LivingEntity.class, Firecaller.this.getBoundingBox().inflate(5.0), entity -> entity instanceof Player || entity instanceof IronGolem
            );
      }

      public boolean canUse() {
         if (Firecaller.this.getTarget() == null) {
            return false;
         } else {
            return Firecaller.this.conjureSkullCooldown > 0
               ? false
               : Firecaller.this.conjureSkullCooldown < 0 && !Firecaller.this.isCastingSpell() && this.getTargets().isEmpty();
         }
      }

      public void tick() {
         if (Firecaller.this.level() instanceof ServerLevel) {
            ((ServerLevel)Firecaller.this.level())
               .sendParticles(ParticleTypes.FLAME, Firecaller.this.getX(), Firecaller.this.getY() + 2.5, Firecaller.this.getZ(), 2, 0.2, 0.2, 0.2, 0.05);
            ((ServerLevel)Firecaller.this.level())
               .sendParticles(ParticleTypes.LARGE_SMOKE, Firecaller.this.getX(), Firecaller.this.getY() + 2.5, Firecaller.this.getZ(), 2, 0.2, 0.2, 0.2, 0.05);
         }

         super.tick();
      }

      private void shootSkullAt(LivingEntity target) {
         this.shootSkullAt(target.getX(), target.getY() + target.getEyeHeight() * 0.5, target.getZ());
      }

      private void shootSkullAt(double targetX, double targetY, double targetZ) {
         double d = Firecaller.this.getX();
         double e = Firecaller.this.getY() + 2.5;
         double f = Firecaller.this.getZ();
         double g = targetX - d;
         double h = targetY - e;
         double i = targetZ - f;
         FlyingMagma Magma = new FlyingMagma(Firecaller.this.level(), Firecaller.this, g, h, i);
         Magma.setOwner(Firecaller.this);
         Magma.setPosRaw(d, e, f);
         Firecaller.this.level().addFreshEntity(Magma);
      }

      protected void performSpellCasting() {
         this.shootSkullAt(Firecaller.this.getTarget());
         if (Firecaller.this.level() instanceof ServerLevel) {
            double x = Firecaller.this.getX();
            double y = Firecaller.this.getY() + 2.5;
            double z = Firecaller.this.getZ();
            ((ServerLevel)Firecaller.this.level()).sendParticles(ParticleTypes.SMOKE, x, y, z, 40, 0.4, 0.4, 0.4, 0.15);
         }

         Firecaller.this.conjureSkullCooldown = 160;
      }

      protected int getCastWarmupTime() {
         return 60;
      }

      protected int getCastingTime() {
         return 60;
      }

      protected int getCastingInterval() {
         return 400;
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)ModSoundEvents.FIRECALLER_CAST_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.WOLOLO;
      }
   }

   class LookAtTargetOrWololoTarget extends SpellcasterCastingSpellGoal {
      LookAtTargetOrWololoTarget() {
         super(Firecaller.this);
      }

      public void tick() {
         if (Firecaller.this.getTarget() != null) {
            Firecaller.this.getLookControl().setLookAt(Firecaller.this.getTarget(), Firecaller.this.getMaxHeadYRot(), Firecaller.this.getMaxHeadXRot());
         }
      }
   }
}
