package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.handler.GeneEditorMobHandler;
import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.magic.spiritual.light.LightMagic;
import io.github.manasmods.tensura.ability.magic.spiritual.light.SolarBeamMagic;
import io.github.manasmods.tensura.ability.magic.spiritual.light.SolarFlareMagic;
import io.github.manasmods.tensura.ability.magic.spiritual.light.SolarRainMagic;
import io.github.manasmods.tensura.ability.magic.spiritual.light.SolarWaveMagic;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.magic.SpiritualMagics;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AvalonKnightEntity extends PathfinderMob implements OwnableEntity, SmartBrainOwner<AvalonKnightEntity> {
   private static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(AvalonKnightEntity.class, EntityDataSerializers.OPTIONAL_UUID);
   private static final EntityDataAccessor<Integer> VIRTUE = SynchedEntityData.defineId(AvalonKnightEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> LIFE = SynchedEntityData.defineId(AvalonKnightEntity.class, EntityDataSerializers.INT);
   private static final String NIGHTMARE_ULTIMATES = "com.github.hvnbael.trnightmare.registry.main.UltimateSkills";
   private static final String ACTION_GATES = "tensuramoreskills:gene_action_gates";
   private static final String GENE_GATE = "tensuramoreskills:gene_editor_autocast";
   private static final int SKILL_CAST_INTERVAL = 40;
   private boolean skillsGranted;

   public AvalonKnightEntity(EntityType<? extends AvalonKnightEntity> type, Level level) {
      super(type, level);
      this.xpReward = 0;
      this.setPersistenceRequired();
   }

   public AvalonKnightEntity(Level level, LivingEntity owner, AvalonKnightEntity.Virtue virtue, int lifetime) {
      this((EntityType<? extends AvalonKnightEntity>)ModEntityTypes.AVALON_KNIGHT.get(), level);
      this.setOwnerUUID(owner.getUUID());
      this.setVirtue(virtue);
      this.setLifetime(lifetime);
      this.setPos(owner.position());
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(OWNER, Optional.empty());
      builder.define(VIRTUE, 0);
      builder.define(LIFE, 2400);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 600.0)
         .add(Attributes.ARMOR, 24.0)
         .add(Attributes.ARMOR_TOUGHNESS, 16.0)
         .add(Attributes.ATTACK_DAMAGE, 32.0)
         .add(Attributes.ATTACK_SPEED, 2.0)
         .add(Attributes.MOVEMENT_SPEED, 0.31)
         .add(Attributes.FOLLOW_RANGE, 80.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
         .add(Attributes.STEP_HEIGHT, 1.25)
         .add(TensuraAttributes.PHYSICAL_BARRIER, 0.35)
         .add(TensuraAttributes.MAGIC_BARRIER, 0.25)
         .add(TensuraAttributes.DODGE_NEGATE_CHANCE, 35.0)
         .add(TensuraAttributes.RESISTANCE_DEGRADATION, 0.2)
         .add(TensuraAttributes.LIGHT_BOOST, 1.5)
         .add(TensuraAttributes.MAX_MAGICULE, 350000.0)
         .add(TensuraAttributes.MAX_AURA, 350000.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
   }

   protected @NotNull Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.tickBrain(this);
   }

   public List<ExtendedSensor<AvalonKnightEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor()});
   }

   public BrainActivityGroup<AvalonKnightEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(new Behavior[]{new LookAtTarget(), new MoveToWalkTarget().cooldownFor(entity -> 0)});
   }

   public BrainActivityGroup<AvalonKnightEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  new TargetOrRetaliate().attackablePredicate(this::isValidEnemy).noTargetSwapping(),
                  new SetPlayerLookTarget(),
                  new SetRandomLookTarget(),
                  new Idle().runFor(entity -> entity.getRandom().nextInt(20, 46))
               }
            )
         }
      );
   }

   public BrainActivityGroup<AvalonKnightEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new InvalidateAttackTarget().invalidateIf((entity, target) -> !entity.isValidEnemy(target) || entity.distanceToSqr(target) > 9216.0),
            new AvalonKnightEntity.KnightCombatBehaviour()
         }
      );
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         int life = this.getLifetime() - 1;
         this.setLifetime(life);
         if (life <= 0) {
            this.discard();
         } else {
            LivingEntity owner = this.getOwner();
            if (owner != null && owner.isAlive() && owner.level() == this.level()) {
               if (!this.skillsGranted || !GeneEditorMobHandler.hasInjectedSkills(this)) {
                  this.installVirtueSkills();
               }

               this.synchronizeOwnerTarget(owner);
               LivingEntity target = this.resolveTarget();
               if (target == null) {
                  this.followOwner(owner);
               }

               this.tickRealSkillRuntime();
               if (this.tickCount % 40 == 0) {
                  this.refillExistence();
               }
            } else {
               this.discard();
            }
         }
      }
   }

   private void synchronizeOwnerTarget(LivingEntity owner) {
      LivingEntity ownerTarget = owner.getLastHurtMob();
      if (ownerTarget == null || !this.isValidEnemy(ownerTarget)) {
         ownerTarget = owner.getLastHurtByMob();
      }

      if (ownerTarget != null && this.isValidEnemy(ownerTarget)) {
         this.setTarget(ownerTarget);
      } else {
         LivingEntity current = this.resolveTarget();
         if (current != null && !this.isValidEnemy(current)) {
            this.setTarget(null);
         }
      }
   }

   private void followOwner(LivingEntity owner) {
      double distance = this.distanceToSqr(owner);
      if (distance > 576.0) {
         Vec3 position = owner.position().add((this.random.nextDouble() - 0.5) * 6.0, 0.0, (this.random.nextDouble() - 0.5) * 6.0);
         this.teleportTo(position.x, position.y, position.z);
         BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
      } else {
         if (distance > 49.0) {
            BrainUtils.setMemory(this, MemoryModuleType.WALK_TARGET, new WalkTarget(owner.position(), 1.05F, 2));
         }
      }
   }

   private void installVirtueSkills() {
      Set<ResourceLocation> ids = new LinkedHashSet<>();
      AvalonKnightEntity.Virtue virtue = this.getVirtue();
      addSkill(ids, this.lightSkill(virtue));
      switch (virtue) {
         case JUSTICE:
         case FAITH:
         case VALOR:
            addSkill(ids, (ManasSkill)UniqueSkills.MURDERER.get());
            break;
         case WISDOM:
         case HOPE:
         case CHARITY:
         case MERCY:
            addSkill(ids, (ManasSkill)UniqueSkills.GREAT_SAGE.get());
            break;
         case TEMPERANCE:
         case FORTITUDE:
         case PURITY:
         case LOYALTY:
            addSkill(ids, (ManasSkill)UniqueSkills.UNYIELDING.get());
            break;
         case PRUDENCE:
            addSkill(ids, (ManasSkill)UniqueSkills.INFINITY_PRISON.get());
      }

      ManasSkill nightmare = this.nightmareSkill(virtue);
      addSkill(ids, nightmare);
      GeneEditorMobHandler.addInjectedSkills(this, new ArrayList<>(ids));
      this.setGeneGate(this.tickCount + 40 + 1);
      this.refillExistence();
      this.skillsGranted = true;
   }

   private static void addSkill(Set<ResourceLocation> ids, ManasSkill skill) {
      if (skill != null && skill.getRegistryName() != null) {
         ids.add(skill.getRegistryName());
      }
   }

   private ManasSkill lightSkill(AvalonKnightEntity.Virtue virtue) {
      return (ManasSkill)(switch (virtue) {
         case JUSTICE, PRUDENCE, PURITY -> (SolarBeamMagic)SpiritualMagics.SOLAR_BEAM.get();
         default -> (LightMagic)SpiritualMagics.LIGHT.get();
         case HOPE, MERCY -> (SolarWaveMagic)SpiritualMagics.SOLAR_WAVE.get();
         case TEMPERANCE, FORTITUDE, VALOR -> (SolarFlareMagic)SpiritualMagics.SOLAR_FLARE.get();
         case CHARITY -> (SolarRainMagic)SpiritualMagics.SOLAR_RAIN.get();
      });
   }

   private ManasSkill nightmareSkill(AvalonKnightEntity.Virtue virtue) {
      String fieldName = switch (virtue) {
         case JUSTICE -> "MICHAEL";
         case WISDOM -> "RAPHAEL_KNOWLEDGE";
         case HOPE -> "SARIEL";
         case TEMPERANCE -> "GABRIEL";
         default -> null;
         case CHARITY -> "RAGUEL";
         case PURITY -> "METATRON";
         case LOYALTY -> "URIEL_LORD_OF_VOW";
      };
      if (fieldName == null) {
         return null;
      }

      try {
         Class<?> registry = Class.forName("com.github.hvnbael.trnightmare.registry.main.UltimateSkills", false, this.getClass().getClassLoader());
         Object holder = registry.getField(fieldName).get(null);
         return unwrap(holder) instanceof ManasSkill skill ? skill : null;
      } catch (Throwable ignored) {
         return null;
      }
   }

   private static Object unwrap(Object holder) throws ReflectiveOperationException {
      if (holder != null && !(holder instanceof ManasSkill)) {
         Method method = holder.getClass().getMethod("get");
         return method.invoke(holder);
      } else {
         return holder;
      }
   }

   private void tickRealSkillRuntime() {
      int offset = Math.floorMod(this.getVirtue().ordinal() * 3, 40);
      if (Math.floorMod(this.tickCount, 40) == offset) {
         this.setGeneGate(this.tickCount);
         GeneEditorMobHandler.tickMob(this);
         this.setGeneGate(this.tickCount + 40 + 1);
      } else {
         CompoundTag gates = this.getPersistentData().getCompound("tensuramoreskills:gene_action_gates");
         int current = gates.getInt("tensuramoreskills:gene_editor_autocast");
         if (current <= this.tickCount) {
            this.setGeneGate(this.tickCount + 2);
         }
      }
   }

   private void setGeneGate(int readyTick) {
      CompoundTag data = this.getPersistentData();
      CompoundTag gates = data.getCompound("tensuramoreskills:gene_action_gates");
      gates.putInt("tensuramoreskills:gene_editor_autocast", readyTick);
      data.put("tensuramoreskills:gene_action_gates", gates);
   }

   private void refillExistence() {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(this);
         double maxMagicule = this.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
         double maxAura = this.getAttributeValue(TensuraAttributes.MAX_AURA);
         existence.setMagicule(Math.max(existence.getMagicule(), maxMagicule));
         existence.setAura(Math.max(existence.getAura(), maxAura));
         existence.markDirty();
      } catch (Throwable var6) {
      }
   }

   public LivingEntity resolveTarget() {
      LivingEntity brainTarget = BrainUtils.getTargetOfEntity(this);
      if (brainTarget != null && this.isValidEnemy(brainTarget)) {
         super.setTarget(brainTarget);
         return brainTarget;
      } else {
         LivingEntity vanillaTarget = super.getTarget();
         if (vanillaTarget != null && this.isValidEnemy(vanillaTarget)) {
            BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, vanillaTarget);
            return vanillaTarget;
         } else {
            return null;
         }
      }
   }

   public boolean isValidEnemy(Entity entity) {
      if (!(entity instanceof LivingEntity living)) {
         return false;
      } else if (living.isAlive() && living != this) {
         if (!(entity instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            LivingEntity owner = this.getOwner();
            return owner != null && !RoyalSkillSupport.validEnemy(owner, living) ? false : !this.isAlliedTo(entity) && !entity.isAlliedTo(this);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void setTarget(@Nullable LivingEntity target) {
      if (target != null && this.isValidEnemy(target)) {
         super.setTarget(target);
         if (!this.level().isClientSide()) {
            BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, target);
         }
      } else {
         super.setTarget(null);
         if (!this.level().isClientSide()) {
            BrainUtils.clearMemory(this, MemoryModuleType.ATTACK_TARGET);
         }
      }
   }

   public boolean doHurtTarget(Entity target) {
      LivingEntity owner = this.getOwner();
      if (owner != null && this.isValidEnemy(target)) {
         boolean hurt = super.doHurtTarget(target);
         if (hurt && target instanceof LivingEntity living) {
            RoyalSkillSupport.markCombat(owner, living);
         }

         return hurt;
      } else {
         return false;
      }
   }

   public void setVirtue(AvalonKnightEntity.Virtue virtue) {
      this.entityData.set(VIRTUE, virtue.ordinal());
      this.setCustomName(Component.literal(virtue.displayName + " of the Round Table").withStyle(ChatFormatting.GOLD));
      this.setCustomNameVisible(true);
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(virtue.ordinal() % 3 == 0 ? Items.NETHERITE_SWORD : Items.GOLDEN_SWORD));
      if (virtue.ordinal() % 4 == 0) {
         this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
      } else {
         this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
      }
   }

   public AvalonKnightEntity.Virtue getVirtue() {
      return AvalonKnightEntity.Virtue.values()[Math.floorMod((Integer)this.entityData.get(VIRTUE), AvalonKnightEntity.Virtue.values().length)];
   }

   public int getLifetime() {
      return (Integer)this.entityData.get(LIFE);
   }

   public void setLifetime(int lifetime) {
      this.entityData.set(LIFE, lifetime);
   }

   public void setOwnerUUID(UUID uuid) {
      this.entityData.set(OWNER, Optional.ofNullable(uuid));
   }

   @Nullable
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(OWNER)).orElse(null);
   }

   @Nullable
   public LivingEntity getOwner() {
      UUID uuid = this.getOwnerUUID();
      if (uuid != null && this.level() instanceof ServerLevel serverLevel) {
         return serverLevel.getEntity(uuid) instanceof LivingEntity living ? living : null;
      } else {
         return null;
      }
   }

   public boolean isAlliedTo(Entity entity) {
      LivingEntity owner = this.getOwner();
      return entity == owner || owner != null && entity instanceof LivingEntity living && RoyalSkillSupport.isAlly(owner, living) || super.isAlliedTo(entity);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data) {
      this.setVirtue(this.getVirtue());
      return super.finalizeSpawn(level, difficulty, reason, data);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      UUID uuid = this.getOwnerUUID();
      if (uuid != null) {
         tag.putUUID("Owner", uuid);
      }

      tag.putInt("Virtue", this.getVirtue().ordinal());
      tag.putInt("Lifetime", this.getLifetime());
      tag.putBoolean("SkillsGranted", this.skillsGranted);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.hasUUID("Owner")) {
         this.setOwnerUUID(tag.getUUID("Owner"));
      }

      this.setVirtue(AvalonKnightEntity.Virtue.values()[Math.floorMod(tag.getInt("Virtue"), AvalonKnightEntity.Virtue.values().length)]);
      this.setLifetime(tag.getInt("Lifetime"));
      this.skillsGranted = tag.getBoolean("SkillsGranted");
   }

   private static final class KnightCombatBehaviour extends ExtendedBehaviour<AvalonKnightEntity> {
      private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(
         Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
         Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
         Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED)
      );
      private int attackCooldown;

      private KnightCombatBehaviour() {
         this.noTimeout();
      }

      protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
         return MEMORY_REQUIREMENTS;
      }

      protected boolean checkExtraStartConditions(ServerLevel level, AvalonKnightEntity entity) {
         LivingEntity target = BrainUtils.getTargetOfEntity(entity);
         return target != null && entity.isValidEnemy(target);
      }

      protected boolean shouldKeepRunning(AvalonKnightEntity entity) {
         LivingEntity target = BrainUtils.getTargetOfEntity(entity);
         return target != null && entity.isValidEnemy(target);
      }

      protected void start(AvalonKnightEntity entity) {
         this.attackCooldown = 0;
         entity.setAggressive(true);
      }

      protected void tick(AvalonKnightEntity entity) {
         LivingEntity target = BrainUtils.getTargetOfEntity(entity);
         if (target != null && entity.isValidEnemy(target)) {
            entity.getLookControl().setLookAt(target, 80.0F, 80.0F);
            BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
            double reach = entity.getBbWidth() * 2.0 + target.getBbWidth() + 1.5;
            if (entity.distanceToSqr(target) > reach * reach) {
               BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(target.position(), 1.12F, 1));
               if (this.attackCooldown > 0) {
                  this.attackCooldown--;
               }
            } else {
               BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
               entity.getNavigation().stop();
               if (this.attackCooldown > 0) {
                  this.attackCooldown--;
               } else {
                  entity.swing(InteractionHand.MAIN_HAND);
                  entity.doHurtTarget(target);
                  this.attackCooldown = Math.max(10, Mth.ceil(20.0 / Math.max(0.1, entity.getAttributeValue(Attributes.ATTACK_SPEED))));
               }
            }
         }
      }

      protected void stop(AvalonKnightEntity entity) {
         BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
         entity.getNavigation().stop();
         entity.setAggressive(false);
         this.attackCooldown = 0;
      }
   }

   public enum Virtue {
      JUSTICE("Justice"),
      WISDOM("Wisdom"),
      HOPE("Hope"),
      TEMPERANCE("Temperance"),
      FORTITUDE("Fortitude"),
      CHARITY("Charity"),
      FAITH("Faith"),
      PRUDENCE("Prudence"),
      MERCY("Mercy"),
      VALOR("Valor"),
      PURITY("Purity"),
      LOYALTY("Loyalty");

      private final String displayName;

      Virtue(String displayName) {
         this.displayName = displayName;
      }
   }
}
