package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.config.ServerConfig;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.illagerinvasion.util.SpellParticleUtil;
import fuzs.illagerinvasion.util.TeleportUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
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
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.entity.monster.SpellcasterIllager.IllagerSpell;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterCastingSpellGoal;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterUseSpellGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Invoker extends SpellcasterIllager implements PowerableMob {
   private static final EntityDataAccessor<Boolean> DATA_IS_SHIELDED = SynchedEntityData.defineId(Invoker.class, EntityDataSerializers.BOOLEAN);
   private final ServerBossEvent bossBar = new ServerBossEvent(this.getDisplayName(), BossBarColor.YELLOW, BossBarOverlay.PROGRESS);
   public int areaDamageCooldown;
   public int teleportCooldown;
   public boolean isAoeCasting = false;
   public int fangaoecooldown;
   @Nullable
   private Sheep wololoTarget;

   public Invoker(EntityType<? extends Invoker> entityType, Level world) {
      super(entityType, world);
      this.xpReward = 80;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Invoker.LookAtTargetOrWololoTarget());
      this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Player.class, 8.0F, 0.6, 1.0));
      this.goalSelector.addGoal(5, new Invoker.AreaDamageGoal());
      this.goalSelector.addGoal(4, new Invoker.CastTeleportGoal());
      this.goalSelector.addGoal(5, new Invoker.SummonVexGoal());
      this.goalSelector.addGoal(5, new Invoker.ConjureAoeFangsGoal());
      this.goalSelector.addGoal(6, new Invoker.ConjureFangsGoal());
      this.goalSelector.addGoal(6, new Invoker.WololoGoal());
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
   }

   public boolean isPowered() {
      return this.getShieldedState();
   }

   public boolean doHurtTarget(Entity target) {
      if (!super.doHurtTarget(target)) {
         return false;
      }

      if (target instanceof LivingEntity) {
         ((LivingEntity)target).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0), this);
      }

      return true;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_IS_SHIELDED, false);
   }

   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.setShieldedState(nbt.getBoolean("Invul"));
      if (this.hasCustomName()) {
         this.bossBar.setName(this.getDisplayName());
      }
   }

   public void setCustomName(@Nullable Component name) {
      super.setCustomName(name);
      this.bossBar.setName(this.getDisplayName());
   }

   public SoundEvent getCelebrateSound() {
      return SoundEvents.EVOKER_CELEBRATE;
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      nbt.putBoolean("Invul", this.getShieldedState());
      super.addAdditionalSaveData(nbt);
   }

   public boolean getShieldedState() {
      return (Boolean)this.entityData.get(DATA_IS_SHIELDED);
   }

   public void setShieldedState(boolean isShielded) {
      this.entityData.set(DATA_IS_SHIELDED, isShielded);
   }

   protected void customServerAiStep() {
      this.teleportCooldown--;
      this.areaDamageCooldown--;
      this.fangaoecooldown--;
      super.customServerAiStep();
      this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
      if (this.isAoeCasting && this.isCastingSpell()) {
         SpellParticleUtil.sendSpellParticles(this, (ServerLevel)this.level(), ParticleTypes.SMOKE, 2, 0.06);
      }

      Vec3 vec3d = this.getDeltaMovement();
      if (!this.onGround() && vec3d.y < 0.0) {
         this.setDeltaMovement(vec3d.multiply(1.0, 0.6, 1.0));
      }

      if (this.level() instanceof ServerLevel) {
         ((ServerLevel)this.level()).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.3, this.getZ(), 1, 0.2, 0.2, 0.2, 0.005);
      }
   }

   public void checkDespawn() {
      if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
         this.discard();
      } else {
         this.noActionTime = 0;
      }
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      if (((ServerConfig)IllagerInvasion.CONFIG.get(ServerConfig.class)).invokerBossBar) {
         this.bossBar.addPlayer(player);
      }
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      if (((ServerConfig)IllagerInvasion.CONFIG.get(ServerConfig.class)).invokerBossBar) {
         this.bossBar.removePlayer(player);
      }
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
   }

   public boolean isAlliedTo(Entity other) {
      if (other == null) {
         return false;
      } else if (other == this) {
         return true;
      } else if (super.isAlliedTo(other)) {
         return true;
      } else if (other instanceof Surrendered) {
         return this.isAlliedTo(((Surrendered)other).getOwner());
      } else {
         return other instanceof LivingEntity livingEntity && livingEntity.getType().is(EntityTypeTags.ILLAGER_FRIENDS)
            ? this.getTeam() == null && other.getTeam() == null
            : false;
      }
   }

   public IllagerArmPose getArmPose() {
      if (this.isAggressive()) {
         return IllagerArmPose.ATTACKING;
      } else if (this.isCastingSpell()) {
         return IllagerArmPose.SPELLCASTING;
      } else {
         return this.isCelebrating() ? IllagerArmPose.CELEBRATING : IllagerArmPose.CROSSED;
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      if (!this.level().isClientSide) {
         if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            if (!this.getShieldedState() && this.random.nextInt(2) == 0) {
               this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.level().random.nextFloat() * 0.4F);
               this.setShieldedState(true);
            }
         } else if (this.getShieldedState() && this.random.nextInt(3) == 0) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.CRIT, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.5, 0.7, 0.5, 0.5);
            this.playSound((SoundEvent)ModSoundEvents.INVOKER_SHIELD_BREAK_SOUND_EVENT.value(), 1.0F, 0.8F + this.level().random.nextFloat() * 0.4F);
            this.setShieldedState(false);
         }
      }

      return super.hurt(source, amount);
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return super.isInvulnerableTo(source)
         || source.is(DamageTypeTags.WITCH_RESISTANT_TO)
         || this.getShieldedState() && source.is(DamageTypeTags.IS_PROJECTILE);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSoundEvents.INVOKER_AMBIENT_SOUND_EVENT.value();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSoundEvents.INVOKER_DEATH_SOUND_EVENT.value();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)ModSoundEvents.INVOKER_HURT_SOUND_EVENT.value();
   }

   @Nullable
   Sheep getWololoTarget() {
      return this.wololoTarget;
   }

   void setWololoTarget(@Nullable Sheep sheep) {
      this.wololoTarget = sheep;
   }

   protected SoundEvent getCastingSoundEvent() {
      return (SoundEvent)ModSoundEvents.INVOKER_COMPLETE_CAST_SOUND_EVENT.value();
   }

   public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
   }

   public class AreaDamageGoal extends SpellcasterUseSpellGoal {
      public AreaDamageGoal() {
         super(Invoker.this);
      }

      public boolean canUse() {
         if (Invoker.this.getTarget() == null) {
            return false;
         } else if (Invoker.this.areaDamageCooldown < 0) {
            Invoker.this.isAoeCasting = true;
            return true;
         } else {
            return false;
         }
      }

      private void knockBack(Entity entity) {
         double d = entity.getX() - Invoker.this.getX();
         double e = entity.getZ() - Invoker.this.getZ();
         double f = Math.max(d * d + e * e, 0.001);
         entity.push(d / f * 6.0, 0.65, e / f * 6.0);
      }

      protected void knockback(LivingEntity target) {
         this.knockBack(target);
         target.hurtMarked = true;
      }

      public void stop() {
         Invoker.this.isAoeCasting = false;
         super.stop();
      }

      private void buff(LivingEntity entity) {
         this.knockback(entity);
         entity.hurt(Invoker.this.damageSources().magic(), 11.0F);
         double x = entity.getX();
         double y = entity.getY() + 1.0;
         double z = entity.getZ();
         ((ServerLevel)Invoker.this.level()).sendParticles(ParticleTypes.SMOKE, x, y + 1.0, z, 10, 0.2, 0.2, 0.2, 0.015);
      }

      protected void performSpellCasting() {
         Invoker.this.areaDamageCooldown = 300;
         Invoker.this.level()
            .getEntitiesOfClass(
               LivingEntity.class,
               Invoker.this.getBoundingBox().inflate(6.0),
               entity -> !(entity instanceof AbstractIllager)
                  && !(entity instanceof Surrendered)
                  && !(entity instanceof Ravager)
                  && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)
            )
            .forEach(this::buff);
         Invoker.this.isAoeCasting = false;
         double posx = Invoker.this.getX();
         double posy = Invoker.this.getY();
         double posz = Invoker.this.getZ();
         ((ServerLevel)Invoker.this.level()).sendParticles(ParticleTypes.LARGE_SMOKE, posx, posy + 1.0, posz, 350, 1.0, 0.8, 1.0, 0.3);
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
         return (SoundEvent)ModSoundEvents.INVOKER_BIG_CAST_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.BLINDNESS;
      }
   }

   public class CastTeleportGoal extends SpellcasterUseSpellGoal {
      public CastTeleportGoal() {
         super(Invoker.this);
      }

      public boolean canUse() {
         if (Invoker.this.getTarget() == null) {
            return false;
         } else {
            return Invoker.this.isCastingSpell() ? false : Invoker.this.teleportCooldown < 0 && !this.getTargets().isEmpty();
         }
      }

      private List<LivingEntity> getTargets() {
         return Invoker.this.level()
            .getEntitiesOfClass(
               LivingEntity.class,
               Invoker.this.getBoundingBox().inflate(6.0),
               entity -> entity instanceof Player && !((Player)entity).getAbilities().instabuild || entity instanceof IronGolem
            );
      }

      public boolean canContinueToUse() {
         return !this.getTargets().isEmpty();
      }

      public void stop() {
         super.stop();
      }

      public void start() {
         super.start();
         Invoker.this.teleportCooldown = 180;
      }

      protected void performSpellCasting() {
         double x = Invoker.this.getX();
         double y = Invoker.this.getY() + 1.0;
         double z = Invoker.this.getZ();
         if (Invoker.this.level() instanceof ServerLevel) {
            ((ServerLevel)Invoker.this.level()).sendParticles(ParticleTypes.SMOKE, x, y, z, 30, 0.3, 0.5, 0.3, 0.015);
         }

         TeleportUtil.tryRandomTeleport(Invoker.this);
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
         return (SoundEvent)ModSoundEvents.INVOKER_TELEPORT_CAST_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.BLINDNESS;
      }
   }

   public class ConjureAoeFangsGoal extends SpellcasterUseSpellGoal {
      public ConjureAoeFangsGoal() {
         super(Invoker.this);
      }

      public boolean canUse() {
         if (Invoker.this.getTarget() == null) {
            return false;
         } else if (this.getTargets().isEmpty()) {
            return false;
         } else {
            return Invoker.this.isCastingSpell() ? false : Invoker.this.fangaoecooldown < 0;
         }
      }

      private List<LivingEntity> getTargets() {
         return Invoker.this.level()
            .getEntitiesOfClass(LivingEntity.class, Invoker.this.getBoundingBox().inflate(18.0), entity -> !(entity instanceof Monster));
      }

      private void conjureFangs(double x, double z, double maxY, double y, float yaw, int warmup) {
         BlockPos blockPos = BlockPos.containing(x, y, z);
         boolean bl = false;
         double d = 0.0;

         do {
            BlockPos blockPos2;
            if (Invoker.this.level().getBlockState(blockPos2 = blockPos.below()).isFaceSturdy(Invoker.this.level(), blockPos2, Direction.UP)) {
               VoxelShape voxelShape;
               if (!Invoker.this.level().isEmptyBlock(blockPos)
                  && !(voxelShape = Invoker.this.level().getBlockState(blockPos).getCollisionShape(Invoker.this.level(), blockPos)).isEmpty()) {
                  d = voxelShape.max(Axis.Y);
               }

               bl = true;
               break;
            }
         } while ((blockPos = blockPos.below()).getY() >= Mth.floor(maxY) - 1);

         if (bl) {
            Invoker.this.level().addFreshEntity(new InvokerFangs(Invoker.this.level(), x, blockPos.getY() + 0.2 + d, z, yaw, warmup + 4, Invoker.this));
         }
      }

      public void stop() {
         super.stop();
      }

      protected void performSpellCasting() {
         for (LivingEntity livingEntity : this.getTargets()) {
            double d = Math.min(livingEntity.getY(), Invoker.this.getY());
            double e = Math.max(livingEntity.getY(), Invoker.this.getY()) + 1.0;
            float f = (float)Mth.atan2(livingEntity.getZ() - Invoker.this.getZ(), livingEntity.getX() - Invoker.this.getX());

            for (int i = 0; i < 5; i++) {
               float g = f + i * (float) Math.PI * 0.4F;
               this.conjureFangs(livingEntity.getX() + Mth.cos(g) * 1.5, livingEntity.getZ() + Mth.sin(g) * 1.5, d, e, g, 0);
            }
         }

         Invoker.this.fangaoecooldown = 100;
      }

      protected int getCastingTime() {
         return 40;
      }

      protected int getCastingInterval() {
         return 100;
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)ModSoundEvents.INVOKER_FANGS_CAST_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.FANGS;
      }
   }

   class ConjureFangsGoal extends SpellcasterUseSpellGoal {
      ConjureFangsGoal() {
         super(Invoker.this);
      }

      protected int getCastingTime() {
         return 40;
      }

      protected int getCastingInterval() {
         return 100;
      }

      protected void performSpellCasting() {
         LivingEntity livingEntity = Invoker.this.getTarget();
         double d = Math.min(livingEntity.getY(), Invoker.this.getY());
         double e = Math.max(livingEntity.getY(), Invoker.this.getY()) + 1.0;
         float f = (float)Mth.atan2(livingEntity.getZ() - Invoker.this.getZ(), livingEntity.getX() - Invoker.this.getX());
         if (Invoker.this.distanceToSqr(livingEntity) < 9.0) {
            for (int i = 0; i < 5; i++) {
               float g = f + i * (float) Math.PI * 0.4F;
               this.conjureFangs(Invoker.this.getX() + Mth.cos(g) * 1.5, Invoker.this.getZ() + Mth.sin(g) * 1.5, d, e, g, 0);
            }

            for (int var15 = 0; var15 < 8; var15++) {
               float g = f + var15 * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
               this.conjureFangs(Invoker.this.getX() + Mth.cos(g) * 2.5, Invoker.this.getZ() + Mth.sin(g) * 2.5, d, e, g, 3);
            }

            for (int var16 = 0; var16 < 8; var16++) {
               float g = f + var16 * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
               this.conjureFangs(Invoker.this.getX() + Mth.cos(g) * 3.5, Invoker.this.getZ() + Mth.sin(g) * 2.5, d, e, g, 3);
            }
         } else {
            for (int i = 0; i < 16; i++) {
               double h = 1.25 * (i + 1);
               this.conjureFangs(Invoker.this.getX() + Mth.cos(f) * h, Invoker.this.getZ() + Mth.sin(f) * h, d, e, f, i);
            }

            for (int i = 0; i < 16; i++) {
               double h = 1.25 * (i + 1);
               this.conjureFangs(Invoker.this.getX() + Mth.cos(f + 0.4F) * h, Invoker.this.getZ() + Mth.sin(f + 0.3F) * h, d, e, f, i);
            }

            for (int i = 0; i < 16; i++) {
               double h = 1.25 * (i + 1);
               this.conjureFangs(Invoker.this.getX() + Mth.cos(f - 0.4F) * h, Invoker.this.getZ() + Mth.sin(f - 0.3F) * h, d, e, f, i);
            }
         }
      }

      private void conjureFangs(double x, double z, double maxY, double y, float yaw, int warmup) {
         BlockPos blockPos = BlockPos.containing(x, y, z);
         boolean bl = false;
         double d = 0.0;

         do {
            BlockPos blockPos2;
            if (Invoker.this.level().getBlockState(blockPos2 = blockPos.below()).isFaceSturdy(Invoker.this.level(), blockPos2, Direction.UP)) {
               VoxelShape voxelShape;
               if (!Invoker.this.level().isEmptyBlock(blockPos)
                  && !(voxelShape = Invoker.this.level().getBlockState(blockPos).getCollisionShape(Invoker.this.level(), blockPos)).isEmpty()) {
                  d = voxelShape.max(Axis.Y);
               }

               bl = true;
               break;
            }
         } while ((blockPos = blockPos.below()).getY() >= Mth.floor(maxY) - 1);

         if (bl) {
            Invoker.this.level().addFreshEntity(new InvokerFangs(Invoker.this.level(), x, blockPos.getY() + 0.2 + d, z, yaw, warmup, Invoker.this));
         }
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)ModSoundEvents.INVOKER_FANGS_CAST_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.FANGS;
      }
   }

   class LookAtTargetOrWololoTarget extends SpellcasterCastingSpellGoal {
      LookAtTargetOrWololoTarget() {
         super(Invoker.this);
      }

      public void tick() {
         if (Invoker.this.getTarget() != null) {
            Invoker.this.getLookControl().setLookAt(Invoker.this.getTarget(), Invoker.this.getMaxHeadYRot(), Invoker.this.getMaxHeadXRot());
         } else if (Invoker.this.getWololoTarget() != null) {
            Invoker.this.getLookControl().setLookAt(Invoker.this.getWololoTarget(), Invoker.this.getMaxHeadYRot(), Invoker.this.getMaxHeadXRot());
         }
      }
   }

   class SummonVexGoal extends SpellcasterUseSpellGoal {
      private static final TargetingConditions CLOSE_VEX_PREDICATE = TargetingConditions.forNonCombat()
         .range(16.0)
         .ignoreLineOfSight()
         .ignoreInvisibilityTesting();

      SummonVexGoal() {
         super(Invoker.this);
      }

      public boolean canUse() {
         if (!super.canUse()) {
            return false;
         }

         List<Surrendered> entities = Invoker.this.level()
            .getNearbyEntities(Surrendered.class, CLOSE_VEX_PREDICATE, Invoker.this, Invoker.this.getBoundingBox().inflate(20.0));
         return entities.size() < 3;
      }

      protected int getCastingTime() {
         return 100;
      }

      protected int getCastingInterval() {
         return 340;
      }

      protected void performSpellCasting() {
         ServerLevel serverWorld = (ServerLevel)Invoker.this.level();

         for (int i = 0; i < 4; i++) {
            BlockPos blockPos = Invoker.this.blockPosition().offset(-2 + Invoker.this.random.nextInt(5), 1, -2 + Invoker.this.random.nextInt(5));
            Surrendered surrendered = (Surrendered)((EntityType)ModEntityTypes.SURRENDERED_ENTITY_TYPE.value()).create(Invoker.this.level());
            surrendered.moveTo(blockPos, 0.0F, 0.0F);
            surrendered.finalizeSpawn(serverWorld, Invoker.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, null);
            surrendered.setOwner(Invoker.this);
            surrendered.setBounds(blockPos);
            surrendered.setLifeTicks(20 * (30 + Invoker.this.random.nextInt(90)));
            serverWorld.addFreshEntityWithPassengers(surrendered);
         }
      }

      protected SoundEvent getSpellPrepareSound() {
         return (SoundEvent)ModSoundEvents.INVOKER_SUMMON_CAST_SOUND_EVENT.value();
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.SUMMON_VEX;
      }
   }

   public class WololoGoal extends SpellcasterUseSpellGoal {
      private final TargetingConditions convertibleSheepPredicate = TargetingConditions.forNonCombat()
         .range(16.0)
         .selector(livingEntity -> ((Sheep)livingEntity).getColor() == DyeColor.BLUE);

      public WololoGoal() {
         super(Invoker.this);
      }

      public boolean canUse() {
         if (Invoker.this.getTarget() != null) {
            return false;
         }

         if (Invoker.this.isCastingSpell()) {
            return false;
         }

         if (Invoker.this.tickCount < this.nextAttackTickCount) {
            return false;
         }

         if (!Invoker.this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return false;
         }

         List<Sheep> list = Invoker.this.level()
            .getNearbyEntities(Sheep.class, this.convertibleSheepPredicate, Invoker.this, Invoker.this.getBoundingBox().inflate(16.0, 4.0, 16.0));
         if (list.isEmpty()) {
            return false;
         }

         Invoker.this.setWololoTarget(list.get(Invoker.this.random.nextInt(list.size())));
         return true;
      }

      public boolean canContinueToUse() {
         return Invoker.this.getWololoTarget() != null && this.attackWarmupDelay > 0;
      }

      public void stop() {
         super.stop();
         Invoker.this.setWololoTarget(null);
      }

      protected void performSpellCasting() {
         Sheep sheepEntity = Invoker.this.getWololoTarget();
         if (sheepEntity != null && sheepEntity.isAlive()) {
            sheepEntity.setColor(DyeColor.RED);
         }
      }

      protected int getCastWarmupTime() {
         return 40;
      }

      protected int getCastingTime() {
         return 140;
      }

      protected int getCastingInterval() {
         return 600;
      }

      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.EVOKER_PREPARE_WOLOLO;
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.WOLOLO;
      }
   }
}
