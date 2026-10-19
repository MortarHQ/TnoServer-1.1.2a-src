package com.tensura_tno.entity.spirit;

import com.tensura_tno.ai.SubordinateSkillAiController;
import com.tensura_tno.compat.BetterSubsFoxCompat;
import com.tensura_tno.race.fox_spirit.FoxSpiritSummonBonus;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FoxSpiritEntity extends Fox implements OwnableEntity {
   private static final EntityDataAccessor<Optional<UUID>> DATA_SUMMONER_UUID = SynchedEntityData.defineId(
      FoxSpiritEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   public static final int CMD_FOLLOW = 0;
   public static final int CMD_WANDER = 1;
   public static final int CMD_STAY = 2;
   private static final EntityDataAccessor<Integer> DATA_OWNER_COMMAND = SynchedEntityData.defineId(FoxSpiritEntity.class, EntityDataSerializers.INT);
   private static final int ATTACK_SYNC_INTERVAL = 20;
   public static final String TAG_SUMMONER_UUID = "TnoSummonerUUID";
   public static final String TAG_COMMAND = "TnoCommand";
   public static final String TAG_WANDER_POS = "TnoWanderPos";
   private int attackSyncCounter;
   @Nullable
   private BlockPos wanderCenter;

   public FoxSpiritEntity(EntityType<? extends Fox> type, Level level) {
      super(type, level);
      this.setPersistenceRequired();
      this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
      this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
      this.setPathfindingMalus(PathType.DANGER_OTHER, -1.0F);
      this.setPathfindingMalus(PathType.DAMAGE_OTHER, -1.0F);
   }

   public static Builder createAttributes() {
      return Fox.createAttributes()
         .add(Attributes.ATTACK_DAMAGE, 4.0)
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.32)
         .add(Attributes.FOLLOW_RANGE, 32.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_SUMMONER_UUID, Optional.empty());
      builder.define(DATA_OWNER_COMMAND, 0);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.removeAllGoals(g -> true);
      this.targetSelector.removeAllGoals(g -> true);
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new FoxSpiritEntity.SkillCastingGoal(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3, true));
      this.goalSelector.addGoal(3, new FoxSpiritEntity.FollowSummonerGoal(this, 1.0, 6.0F, 2.5F));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new FoxSpiritEntity.SummonerHurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new FoxSpiritEntity.SummonerHurtTargetGoal(this));
      this.targetSelector.addGoal(3, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
   }

   public Optional<UUID> getSummonerUUID() {
      return (Optional<UUID>)this.entityData.get(DATA_SUMMONER_UUID);
   }

   public void setSummonerUUID(@Nullable UUID uuid) {
      this.entityData.set(DATA_SUMMONER_UUID, Optional.ofNullable(uuid));
      IExistence existence = TensuraStorages.getExistenceFrom(this);
      existence.setSummoner(uuid);
      existence.markDirty();
   }

   @Nullable
   public UUID getOwnerUUID() {
      return this.getSummonerUUID().orElse(null);
   }

   @Nullable
   public LivingEntity getSummoner() {
      return this.getSummonerUUID().map(uuid -> {
         if (this.level() instanceof ServerLevel sl) {
            return sl.getEntity(uuid) instanceof LivingEntity le ? le : null;
         } else {
            return this.level().getPlayerByUUID(uuid);
         }
      }).orElse(null);
   }

   private boolean isAlliedWithSummoner(LivingEntity target) {
      UUID summonerId = this.getSummonerUUID().orElse(null);
      if (summonerId == null) {
         return false;
      }

      if (target.getUUID().equals(summonerId)) {
         return true;
      }

      IExistence existence = TensuraStorages.getExistenceFrom(target);
      return summonerId.equals(existence.getSummoner());
   }

   public int getOwnerCommand() {
      return (Integer)this.entityData.get(DATA_OWNER_COMMAND);
   }

   public void setOwnerCommand(int command) {
      this.entityData.set(DATA_OWNER_COMMAND, command);
   }

   public boolean isFollowMode() {
      return this.getOwnerCommand() == 0;
   }

   public boolean isWanderMode() {
      return this.getOwnerCommand() == 1;
   }

   public boolean isStayMode() {
      return this.getOwnerCommand() == 2;
   }

   @Nullable
   public BlockPos getWanderCenter() {
      return this.wanderCenter;
   }

   public void setWanderCenter(@Nullable BlockPos pos) {
      this.wanderCenter = pos;
   }

   public void cycleCommandFor(Player player) {
      if (!this.level().isClientSide()) {
         int next = (this.getOwnerCommand() + 1) % 3;
         this.setOwnerCommand(next);
         SubordinateSkillAiController.cancel(this);
         if (next == 1) {
            this.wanderCenter = player.getOnPos().above();
            this.getNavigation().stop();
            this.setTarget(null);
         } else if (next == 2) {
            this.getNavigation().stop();
            this.setTarget(null);
         }
         MutableComponent message = switch (next) {
            case 1 -> Component.translatable("tensura.message.pet.wander", new Object[]{this.getDisplayName()});
            case 2 -> Component.translatable("tensura.message.pet.stay", new Object[]{this.getDisplayName()});
            default -> Component.translatable("tensura.message.pet.follow", new Object[]{this.getDisplayName()});
         };
         player.displayClientMessage(message.setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true);
      }
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      UUID summoner = this.getSummonerUUID().orElse(null);
      if (summoner != null && player.getUUID().equals(summoner)) {
         ItemStack stack = player.getItemInHand(hand);
         if (!player.level().isClientSide()
            && player instanceof ServerPlayer serverPlayer
            && player.isShiftKeyDown()
            && stack.isEmpty()
            && player.distanceTo(this) < 8.0F
            && BetterSubsFoxCompat.tryOpenSubordinateMainMenu(serverPlayer, this.getId())) {
            return InteractionResult.sidedSuccess(player.level().isClientSide());
         }

         if (stack.isEmpty()) {
            this.cycleCommandFor(player);
            return InteractionResult.sidedSuccess(player.level().isClientSide());
         }
      }

      return super.mobInteract(player, hand);
   }

   public boolean removeWhenFarAway(double distanceSquared) {
      return false;
   }

   public boolean isAlliedTo(Entity entity) {
      UUID summonerId = this.getSummonerUUID().orElse(null);
      if (summonerId == null) {
         return super.isAlliedTo(entity);
      }

      if (entity.getUUID().equals(summonerId)) {
         return true;
      }

      if (entity instanceof LivingEntity le) {
         IExistence existence = TensuraStorages.getExistenceFrom(le);
         if (summonerId.equals(existence.getSummoner())) {
            return true;
         }
      }

      return super.isAlliedTo(entity);
   }

   public boolean hurt(DamageSource source, float amount) {
      UUID summonerId = this.getSummonerUUID().orElse(null);
      if (summonerId != null) {
         if (causedBySummonerOrSubordinate(source.getEntity(), summonerId)) {
            return false;
         }

         if (causedBySummonerOrSubordinate(source.getDirectEntity(), summonerId)) {
            return false;
         }
      }

      return super.hurt(source, amount);
   }

   private static boolean causedBySummonerOrSubordinate(@Nullable Entity entity, UUID summonerId) {
      if (entity == null) {
         return false;
      } else if (summonerId.equals(entity.getUUID())) {
         return true;
      } else if (entity instanceof LivingEntity le) {
         UUID owner = SubordinateHelper.getSubordinateOwnerUUID(le);
         return summonerId.equals(owner);
      } else {
         return false;
      }
   }

   public boolean canBeAffected(MobEffectInstance effectInstance) {
      return effectInstance.getEffect().equals(TensuraMobEffects.MIND_CONTROL) ? false : super.canBeAffected(effectInstance);
   }

   public void setTarget(@Nullable LivingEntity target) {
      if (target != null) {
         UUID summonerId = this.getSummonerUUID().orElse(null);
         if (summonerId != null) {
            if (target.getUUID().equals(summonerId)) {
               return;
            }

            if (target instanceof LivingEntity) {
               UUID targetOwner = SubordinateHelper.getSubordinateOwnerUUID(target);
               if (summonerId.equals(targetOwner)) {
                  return;
               }
            }

            if (target instanceof Player player && this.isAlliedTo(player)) {
               return;
            }
         }
      }

      super.setTarget(target);
   }

   public boolean canBeLeashed() {
      return false;
   }

   protected int getBaseExperienceReward() {
      return 0;
   }

   protected void tickDeath() {
      super.tickDeath();
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide()) {
         if (++this.attackSyncCounter >= 20) {
            this.attackSyncCounter = 0;
            this.syncAttackFromSummoner();
            this.syncMaxStatsFromSummoner();
         }

         UUID ownerId = this.getSummonerUUID().orElse(null);
         LivingEntity owner = this.getSummoner();
         LivingEntity target = SubordinateSkillAiController.resolveTarget(this);
         boolean combatAllowed = ownerId != null
            && owner != null
            && owner.isAlive()
            && owner.level() == this.level()
            && !this.isVehicle()
            && !this.isStayMode();
         SubordinateSkillAiController.tick(this, target, ownerId, combatAllowed);
         if (this.isStayMode() && this.getTarget() == null && !this.getNavigation().isDone()) {
            this.getNavigation().stop();
         }
      }
   }

   private void syncAttackFromSummoner() {
      LivingEntity summoner = this.getSummoner();
      if (summoner instanceof Player player) {
         AttributeInstance ownAtk = this.getAttribute(Attributes.ATTACK_DAMAGE);
         if (ownAtk != null) {
            float bonus = FoxSpiritSummonBonus.getBonusPercentage(player);
            double targetAtk = summoner.getAttributeValue(Attributes.ATTACK_DAMAGE) * (1.0 + bonus);
            if (Math.abs(ownAtk.getBaseValue() - targetAtk) > 0.001) {
               ownAtk.setBaseValue(targetAtk);
            }
         }
      }
   }

   private void syncMaxStatsFromSummoner() {
      LivingEntity summoner = this.getSummoner();
      if (summoner instanceof Player player) {
         float bonus = FoxSpiritSummonBonus.getBonusPercentage(player);
         AttributeInstance ownHp = this.getAttribute(Attributes.MAX_HEALTH);
         if (ownHp != null) {
            double baseHp = summoner.getMaxHealth() * 0.5;
            double summonerBaseEP = (EnergyHelper.getBaseMaxAura(summoner) + EnergyHelper.getBaseMaxMagicule(summoner)) * 0.5;
            double foxCurrentEP = EnergyHelper.getBaseMaxAura(this) + EnergyHelper.getBaseMaxMagicule(this);
            double extraEP = Math.max(0.0, foxCurrentEP - summonerBaseEP);
            double epHealthBonus = extraEP / 1000.0;
            double targetMaxHp = (baseHp + epHealthBonus) * (1.0 + bonus);
            double oldBase = ownHp.getBaseValue();
            if (Math.abs(oldBase - targetMaxHp) > 0.001) {
               ownHp.setBaseValue(targetMaxHp);
               if (this.getHealth() >= oldBase - 0.5) {
                  this.setHealth(this.getMaxHealth());
               }
            }
         }

         AttributeInstance ownShp = this.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
         AttributeInstance playerShp = summoner.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
         if (ownShp != null && playerShp != null) {
            double targetShp = playerShp.getValue() * 0.5 * (1.0 + bonus);
            double oldShpBase = ownShp.getBaseValue();
            if (Math.abs(oldShpBase - targetShp) > 0.001) {
               ownShp.setBaseValue(targetShp);
               IExistence foxEx = TensuraStorages.getExistenceFrom(this);
               if (foxEx.getSpiritualHealth() >= oldShpBase - 0.5) {
                  foxEx.setSpiritualHealth(targetShp);
                  foxEx.markDirty();
               }
            }
         }

         double targetMaxAura = EnergyHelper.getBaseMaxAura(summoner) * 0.5;
         double oldMaxAura = EnergyHelper.getBaseMaxAura(this);
         if (targetMaxAura > oldMaxAura + 0.001) {
            EnergyHelper.setMaxAura(this, targetMaxAura);
            IExistence foxEx = TensuraStorages.getExistenceFrom(this);
            if (foxEx.getAura() >= oldMaxAura - 0.5) {
               foxEx.setAura(targetMaxAura);
               foxEx.markDirty();
            }
         }

         double targetMaxMagic = EnergyHelper.getBaseMaxMagicule(summoner) * 0.5;
         double oldMaxMagic = EnergyHelper.getBaseMaxMagicule(this);
         if (targetMaxMagic > oldMaxMagic + 0.001) {
            EnergyHelper.setMaxMagicule(this, targetMaxMagic);
            IExistence foxEx = TensuraStorages.getExistenceFrom(this);
            if (foxEx.getMagicule() >= oldMaxMagic - 0.5) {
               foxEx.setMagicule(targetMaxMagic);
               foxEx.markDirty();
            }
         }
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.getSummonerUUID().ifPresent(uuid -> tag.putUUID("TnoSummonerUUID", uuid));
      tag.putInt("TnoCommand", this.getOwnerCommand());
      if (this.wanderCenter != null) {
         tag.putLong("TnoWanderPos", this.wanderCenter.asLong());
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.hasUUID("TnoSummonerUUID")) {
         this.setSummonerUUID(tag.getUUID("TnoSummonerUUID"));
      }

      if (tag.contains("TnoCommand")) {
         int cmd = tag.getInt("TnoCommand");
         this.setOwnerCommand(Math.max(0, Math.min(2, cmd)));
      }

      if (tag.contains("TnoWanderPos")) {
         this.wanderCenter = BlockPos.of(tag.getLong("TnoWanderPos"));
      }
   }

   @Nullable
   public Fox getBreedOffspring(ServerLevel level, AgeableMob mate) {
      return null;
   }

   public boolean canMate(Animal partner) {
      return false;
   }

   public boolean isBaby() {
      return false;
   }

   public static void applyHalfStatsFrom(FoxSpiritEntity fox, LivingEntity summoner) {
      double playerMaxAura = EnergyHelper.getBaseMaxAura(summoner);
      double playerMaxMagic = EnergyHelper.getBaseMaxMagicule(summoner);
      EnergyHelper.setMaxAura(fox, playerMaxAura * 0.5);
      EnergyHelper.setMaxMagicule(fox, playerMaxMagic * 0.5);
      AttributeInstance shp = fox.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      if (shp != null) {
         shp.setBaseValue(summoner.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * 0.5);
      }

      AttributeInstance hp = fox.getAttribute(Attributes.MAX_HEALTH);
      if (hp != null) {
         hp.setBaseValue(summoner.getMaxHealth() * 0.5);
      }

      AttributeInstance atk = fox.getAttribute(Attributes.ATTACK_DAMAGE);
      if (atk != null) {
         atk.setBaseValue(summoner.getAttributeValue(Attributes.ATTACK_DAMAGE));
      }

      IExistence foxExistence = TensuraStorages.getExistenceFrom(fox);
      foxExistence.setAura(EnergyHelper.getBaseMaxAura(summoner) * 0.5);
      foxExistence.setMagicule(EnergyHelper.getBaseMaxMagicule(summoner) * 0.5);
      double maxShpVal = summoner.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      foxExistence.setSpiritualHealth(maxShpVal * 0.5);
      foxExistence.markDirty();
      fox.setHealth(fox.getMaxHealth());
   }

   private static class FollowSummonerGoal extends Goal {
      private final FoxSpiritEntity fox;
      private final double speed;
      private final float startDistance;
      private final float stopDistance;
      private LivingEntity summoner;
      private int teleportCooldown;

      FollowSummonerGoal(FoxSpiritEntity fox, double speed, float startDistance, float stopDistance) {
         this.fox = fox;
         this.speed = speed;
         this.startDistance = startDistance;
         this.stopDistance = stopDistance;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         if (!this.fox.isFollowMode()) {
            return false;
         }

         LivingEntity s = this.fox.getSummoner();
         return s != null && !s.isSpectator() ? this.fox.distanceTo(s) > this.startDistance : false;
      }

      public boolean canContinueToUse() {
         if (!this.fox.isFollowMode()) {
            return false;
         } else if (this.summoner == null) {
            return false;
         } else {
            return this.fox.getNavigation().isDone() ? false : this.fox.distanceTo(this.summoner) > this.stopDistance;
         }
      }

      public void start() {
         this.summoner = this.fox.getSummoner();
         this.teleportCooldown = 0;
      }

      public void stop() {
         this.summoner = null;
         this.fox.getNavigation().stop();
      }

      public void tick() {
         if (this.summoner != null) {
            this.fox.getLookControl().setLookAt(this.summoner, 10.0F, this.fox.getMaxHeadXRot());
            if (--this.teleportCooldown <= 0) {
               this.teleportCooldown = 10;
               if (this.fox.distanceTo(this.summoner) > 16.0F) {
                  this.teleportToSummoner();
               } else {
                  this.fox.getNavigation().moveTo(this.summoner, this.speed);
               }
            }
         }
      }

      private void teleportToSummoner() {
         Vec3 pos = this.summoner.position();
         this.fox.moveTo(pos.x, pos.y, pos.z, this.fox.getYRot(), this.fox.getXRot());
         this.fox.getNavigation().stop();
      }
   }

   private static class SkillCastingGoal extends Goal {
      private final FoxSpiritEntity fox;

      SkillCastingGoal(FoxSpiritEntity fox) {
         this.fox = fox;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return SubordinateSkillAiController.isCasting(this.fox);
      }

      public boolean canContinueToUse() {
         return SubordinateSkillAiController.isCasting(this.fox);
      }

      public void start() {
         this.fox.getNavigation().stop();
      }

      public void tick() {
         this.fox.getNavigation().stop();
         LivingEntity target = SubordinateSkillAiController.getCastTarget(this.fox);
         if (target != null) {
            this.fox.getLookControl().setLookAt(target, 360.0F, 360.0F);
         }
      }
   }

   private static class SummonerHurtByTargetGoal extends Goal {
      private final FoxSpiritEntity fox;
      private LivingEntity attackerCandidate;
      private int timestamp;

      SummonerHurtByTargetGoal(FoxSpiritEntity fox) {
         this.fox = fox;
         this.setFlags(EnumSet.of(Flag.TARGET));
      }

      public boolean canUse() {
         if (this.fox.isStayMode()) {
            return false;
         }

         LivingEntity summoner = this.fox.getSummoner();
         if (summoner == null) {
            return false;
         }

         this.attackerCandidate = summoner.getLastHurtByMob();
         int newTs = summoner.getLastHurtByMobTimestamp();
         return this.attackerCandidate != null
            && newTs != this.timestamp
            && TargetingConditions.DEFAULT.test(this.fox, this.attackerCandidate)
            && !this.fox.isAlliedTo(this.attackerCandidate);
      }

      public void start() {
         this.fox.setTarget(this.attackerCandidate);
         LivingEntity s = this.fox.getSummoner();
         if (s != null) {
            this.timestamp = s.getLastHurtByMobTimestamp();
         }

         super.start();
      }
   }

   private static class SummonerHurtTargetGoal extends Goal {
      private final FoxSpiritEntity fox;
      private LivingEntity target;
      private int timestamp;

      SummonerHurtTargetGoal(FoxSpiritEntity fox) {
         this.fox = fox;
         this.setFlags(EnumSet.of(Flag.TARGET));
      }

      public boolean canUse() {
         if (this.fox.isStayMode()) {
            return false;
         }

         LivingEntity summoner = this.fox.getSummoner();
         if (summoner == null) {
            return false;
         }

         this.target = summoner.getLastHurtMob();
         int newTs = summoner.getLastHurtMobTimestamp();
         return this.target != null && newTs != this.timestamp && TargetingConditions.DEFAULT.test(this.fox, this.target) && !this.fox.isAlliedTo(this.target);
      }

      public void start() {
         this.fox.setTarget(this.target);
         LivingEntity s = this.fox.getSummoner();
         if (s != null) {
            this.timestamp = s.getLastHurtMobTimestamp();
         }

         super.start();
      }
   }
}
