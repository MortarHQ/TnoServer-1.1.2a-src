package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.SpellcasterIllager.IllagerSpell;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterCastingSpellGoal;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterUseSpellGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

public final class IceologerEntity extends SpellcasterIllager {
   public IceologerEntity(EntityType<? extends IceologerEntity> entityType, Level world) {
      super(entityType, world);
      if (!FriendsAndFoes.getConfig().enableIceologer && !this.level().isClientSide()) {
         this.discard();
      }

      this.xpReward = 10;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new SpellcasterCastingSpellGoal(this));
      this.goalSelector.addGoal(2, new AvoidEntityGoal(this, Player.class, 8.0F, 0.6, 1.0));
      if (FriendsAndFoes.getConfig().fleeAwayFromIronGolems) {
         this.goalSelector.addGoal(2, new AvoidEntityGoal(this, IronGolem.class, 8.0F, 0.6, 1.0));
      }

      this.goalSelector.addGoal(3, new IceologerEntity.SummonIceChunkGoal());
      this.goalSelector.addGoal(4, new IceologerEntity.SlowTargetGoal());
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, GlowSquid.class, false).setUnseenMemoryTicks(300));
   }

   public static Builder createIceologerAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FOLLOW_RANGE, 18.0).add(Attributes.MAX_HEALTH, 24.0);
   }

   public SoundEvent getCelebrateSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_AMBIENT.get();
   }

   public void applyRaidBuffs(ServerLevel world, int wave, boolean unused) {
   }

   public void tick() {
      if (!FriendsAndFoes.getConfig().enableIceologer) {
         this.discard();
      }

      super.tick();
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_AMBIENT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_DEATH.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_HURT.get();
   }

   protected SoundEvent getCastingSoundEvent() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_CAST_SPELL.get();
   }

   public void addBonusForWave(int wave, boolean unused) {
   }

   public final class SlowTargetGoal extends SpellcasterUseSpellGoal {
      public SlowTargetGoal() {
         super(IceologerEntity.this);
      }

      public boolean canUse() {
         return super.canUse() && IceologerEntity.this.getTarget() != null;
      }

      protected int getCastingTime() {
         return 20;
      }

      protected int getCastingInterval() {
         return 220;
      }

      protected void performSpellCasting() {
         LivingEntity target = IceologerEntity.this.getTarget();
         if (target != null && target.isAlive() && target.canFreeze()) {
            target.setTicksFrozen(400);
         }
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_PREPARE_SLOWNESS.get();
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.BLINDNESS;
      }
   }

   private class SummonIceChunkGoal extends SpellcasterUseSpellGoal {
      private SummonIceChunkGoal() {
         super(IceologerEntity.this);
      }

      protected void performSpellCasting() {
         LivingEntity target = IceologerEntity.this.getTarget();
         this.summonIceChunk(target);
      }

      protected int getCastingTime() {
         return 30;
      }

      protected int getCastingInterval() {
         return 160;
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_PREPARE_SUMMON.get();
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.FANGS;
      }

      private void summonIceChunk(LivingEntity target) {
         Level world = IceologerEntity.this.level();
         IceologerIceChunkEntity iceChunk = IceologerIceChunkEntity.createWithOwnerAndTarget(world, IceologerEntity.this, target);
         world.addFreshEntity(iceChunk);
      }
   }
}
