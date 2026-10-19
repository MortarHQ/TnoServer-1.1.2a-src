package io.github.Memoires.mysticism.entity.skill;

import io.github.Memoires.mysticism.ability.skill.unique.EngineerSkill;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DispenserEntity extends TensuraTamableEntity implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private int levelUpAnimationTicks = 0;
   private boolean clientWasLevelingUp = false;
   private static final HashMap<UUID, ArrayList<DispenserEntity>> playerDispensers = new HashMap<>();
   private static final EntityDataAccessor<Integer> DISPENSER_LEVEL = SynchedEntityData.defineId(DispenserEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> PLAYER_PROXIMITY_TIME = SynchedEntityData.defineId(DispenserEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> IS_BEING_PLACED = SynchedEntityData.defineId(DispenserEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_LEVELING_UP = SynchedEntityData.defineId(DispenserEntity.class, EntityDataSerializers.BOOLEAN);
   public static final int LEVEL_2_PROXIMITY_TIME = EngineerSkill.CONFIG.sentryLevel2LevelUpTime;
   public static final int LEVEL_3_PROXIMITY_TIME = EngineerSkill.CONFIG.sentryLevel3LevelUpTime;
   public static final float LEVEL_1_EP_REGEN = EngineerSkill.CONFIG.percentageDispenserEPRegenLevel1 / 100.0F;
   public static final float LEVEL_2_EP_REGEN = EngineerSkill.CONFIG.percentageDispenserEPRegenLevel2 / 100.0F;
   public static final float LEVEL_3_EP_REGEN = EngineerSkill.CONFIG.percentageDispenserEPRegenLevel3 / 100.0F;
   public static final int LEVEL_2_HP_REGEN = EngineerSkill.CONFIG.healthDispenserRegenLevel2;
   public static final float LEVEL_3_HP_REGEN = EngineerSkill.CONFIG.healthDispenserRegenLevel3 / 100.0F;
   public static final int LEVEL_1_FOOD = EngineerSkill.CONFIG.foodDispenserRegenLevel1;
   public static final int LEVEL_2_FOOD = EngineerSkill.CONFIG.foodDispenserRegenLevel2;
   public static final int LEVEL_3_FOOD = EngineerSkill.CONFIG.foodDispenserRegenLevel3;
   private long creationTime = System.currentTimeMillis();

   public DispenserEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
      super(entityType, level);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "main_controller", 0, state -> {
         DispenserEntity entity = (DispenserEntity)state.getAnimatable();
         if (entity.isLevelingUp() && !entity.clientWasLevelingUp) {
            state.getController().forceAnimationReset();
         }

         entity.clientWasLevelingUp = entity.isLevelingUp();
         return !entity.isBeingPlaced() && !entity.isLevelingUp() ? PlayState.STOP : state.setAndContinue(RawAnimation.begin().thenPlay("deploy"));
      }).triggerableAnim("trigger_idle", RawAnimation.begin().thenPlay("idle")));
      controllers.add(
         new AnimationController(this, "dispense_controller", 5, state -> PlayState.STOP)
            .triggerableAnim("dispense", RawAnimation.begin().thenPlay("dispense"))
      );
   }

   public static Builder setAttributes() {
      return TensuraTamableEntity.setAttributes()
         .add(Attributes.ATTACK_DAMAGE, 1.0)
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.FOLLOW_RANGE, 150.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.1F)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 100.0)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 25.0)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 25.0);
   }

   private void copyAttributeFromOwner(LivingEntity owner, Supplier<Holder<Attribute>> attributeSupplier) {
      if (owner != null) {
         Holder<Attribute> attribute = attributeSupplier.get();
         AttributeInstance ownerAttribute = owner.getAttribute(attribute);
         AttributeInstance selfAttribute = this.getAttribute(attribute);
         if (ownerAttribute != null && selfAttribute != null) {
            selfAttribute.setBaseValue(ownerAttribute.getValue());
         }
      }
   }

   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      LivingEntity owner = this.getOwner();
      this.copyAttributeFromOwner(owner, () -> Attributes.MAX_HEALTH);
      this.copyAttributeFromOwner(owner, () -> Attributes.ATTACK_DAMAGE);
      this.copyAttributeFromOwner(owner, () -> Attributes.ARMOR);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.MAX_MAGICULE);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.MAX_AURA);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.PHYSICAL_BARRIER);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.MULTILAYER_BARRIER);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      this.setHealth((float)this.getAttributes().getValue(Attributes.MAX_HEALTH));
      TensuraStorages.getExistenceFrom(this).setSpiritualHealth(this.getAttributes().getValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH));
      return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
   }

   public void setDispenserLevel() {
      int proximityTime = this.getPlayerProximityTime();
      int newLevel;
      if (proximityTime >= LEVEL_3_PROXIMITY_TIME) {
         newLevel = 3;
      } else if (proximityTime >= LEVEL_2_PROXIMITY_TIME) {
         newLevel = 2;
      } else {
         newLevel = 1;
      }

      if (!this.level().isClientSide && newLevel != this.getDispenserLevel()) {
         this.setLevelingUp(true);
         this.levelUpAnimationTicks = 83;
         this.level().playSound(null, this.blockPosition(), SoundEvents.PLAYER_LEVELUP, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         if (this.getOwner() instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("mysticism.skill.engineer.dispenser_evolved", new Object[]{newLevel})
                  .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
               false
            );
         }

         this.entityData.set(DISPENSER_LEVEL, newLevel);
      }
   }

   public boolean canBeAffected(MobEffectInstance pEffectInstance) {
      return false;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DISPENSER_LEVEL, 1);
      builder.define(PLAYER_PROXIMITY_TIME, 0);
      builder.define(IS_BEING_PLACED, true);
      builder.define(IS_LEVELING_UP, false);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setDispenserLevel(compound.getInt("SentryLevel"));
      this.setPlayerProximityTime(compound.getInt("PlayerProximityTime"));
      this.setBeingPlaced(compound.getBoolean("BeingPlaced"));
      this.setLevelingUp(compound.getBoolean("IsLevelingUp"));
      if (compound.contains("CreationTime")) {
         this.creationTime = compound.getLong("CreationTime");
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("SentryLevel", this.getDispenserLevel());
      compound.putInt("PlayerProximityTime", this.getPlayerProximityTime());
      compound.putBoolean("BeingPlaced", this.isBeingPlaced());
      compound.putBoolean("IsLevelingUp", this.isLevelingUp());
      compound.putLong("CreationTime", this.creationTime);
   }

   public boolean isBeingPlaced() {
      return (Boolean)this.entityData.get(IS_BEING_PLACED);
   }

   public boolean isLevelingUp() {
      return (Boolean)this.entityData.get(IS_LEVELING_UP);
   }

   public long getCreationTime() {
      return this.creationTime;
   }

   public int getPlayerProximityTime() {
      return (Integer)this.entityData.get(PLAYER_PROXIMITY_TIME);
   }

   public int getDispenserLevel() {
      return (Integer)this.entityData.get(DISPENSER_LEVEL);
   }

   public void setPlayerProximityTime(int time) {
      this.entityData.set(PLAYER_PROXIMITY_TIME, time);
   }

   public void setDispenserLevel(int level) {
      this.entityData.set(DISPENSER_LEVEL, level);
   }

   public void setBeingPlaced(boolean placed) {
      this.entityData.set(IS_BEING_PLACED, placed);
   }

   public void setLevelingUp(boolean levelingUp) {
      this.entityData.set(IS_LEVELING_UP, levelingUp);
   }

   public boolean isPushable() {
      return false;
   }

   public void remove(RemovalReason reason) {
      if (this.getOwner() != null) {
         UUID ownerUUID = this.getOwner().getUUID();
         if (playerDispensers.containsKey(ownerUUID)) {
            playerDispensers.get(ownerUUID).remove(this);
         }
      }

      super.remove(reason);
   }

   public void tick() {
      super.tick();
      if (this.isBeingPlaced() && this.tickCount > 83) {
         this.setBeingPlaced(false);
      }

      if (!this.level().isClientSide && this.levelUpAnimationTicks > 0) {
         this.levelUpAnimationTicks--;
         if (this.levelUpAnimationTicks == 0) {
            this.setLevelingUp(false);
         }
      }

      if (this.getOwner() != null) {
         UUID ownerUUID = this.getOwner().getUUID();
         playerDispensers.computeIfAbsent(ownerUUID, k -> new ArrayList<>());
         if (!playerDispensers.get(ownerUUID).contains(this)) {
            playerDispensers.get(ownerUUID).add(this);
         }

         if (playerDispensers.get(ownerUUID).size() > 2) {
            playerDispensers.get(ownerUUID).sort(Comparator.comparingLong(DispenserEntity::getCreationTime));
            DispenserEntity oldestDispenser = playerDispensers.get(ownerUUID).getFirst();
            double x = oldestDispenser.getX();
            double y = oldestDispenser.getY();
            double z = oldestDispenser.getZ();
            if (oldestDispenser.level() != null) {
               oldestDispenser.level().addParticle(ParticleTypes.EXPLOSION, x, y, z, 1.0, 1.0, 1.0);
            }

            playerDispensers.get(ownerUUID).remove(0);
            oldestDispenser.remove(RemovalReason.DISCARDED);
         }

         double distanceToOwner = this.distanceToSqr(this.getOwner().getX(), this.getOwner().getY(), this.getOwner().getZ());
         double maxDistance = 10.0;
         int proximityTime = this.getPlayerProximityTime();
         if (distanceToOwner <= maxDistance * maxDistance) {
            this.setPlayerProximityTime(proximityTime + 1);
         } else if (proximityTime > 0) {
            this.setPlayerProximityTime(proximityTime);
         }

         this.setDispenserLevel();
      }

      this.dispenseEffects();
   }

   private void dispenseEffects() {
      if (!this.level().isClientSide) {
         int level = this.getDispenserLevel();
         int tickInterval = level == 3 ? 60 : 100;
         if (this.tickCount % tickInterval == 0) {
            this.level().playSound(null, this.blockPosition(), (SoundEvent)MysticismSoundEvents.DISPENSER_HEAL.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.8F);
            double maxDistance = 10.0;
            AABB area = this.getBoundingBox().inflate(maxDistance);
            List<LivingEntity> allies = this.level().getEntitiesOfClass(LivingEntity.class, area);
            LivingEntity owner = this.getOwner();

            for (LivingEntity livingEntity : allies) {
               if (owner != null && (livingEntity == owner || livingEntity.isAlliedTo(owner))) {
                  this.applyDispenserRegen(livingEntity, level);
               }
            }
         }
      }
   }

   private void applyDispenserRegen(LivingEntity livingEntity, int level) {
      IExistence storage = TensuraStorages.getExistenceFrom(livingEntity);
      if (storage != null) {
         double maxMagicule = livingEntity.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
         double maxAura = livingEntity.getAttributeValue(TensuraAttributes.MAX_AURA);
         this.triggerAnim("dispense_controller", "dispense");
         double currentMagicule = storage.getMagicule();
         double currentAura = storage.getAura();
         switch (level) {
            case 1:
               if (livingEntity instanceof Player player) {
                  player.getFoodData().setFoodLevel(Math.min(player.getFoodData().getFoodLevel() + LEVEL_1_FOOD, 20));
               }

               storage.setMagicule(Math.min(currentMagicule + maxMagicule * LEVEL_1_EP_REGEN, maxMagicule));
               storage.setAura(Math.min(currentAura + maxAura * LEVEL_1_EP_REGEN, maxAura));
               break;
            case 2:
               if (livingEntity instanceof Player player) {
                  player.getFoodData().setFoodLevel(Math.min(player.getFoodData().getFoodLevel() + LEVEL_2_FOOD, 20));
               }

               livingEntity.heal(LEVEL_2_HP_REGEN);
               storage.setMagicule(Math.min(currentMagicule + maxMagicule * LEVEL_2_EP_REGEN, maxMagicule));
               storage.setAura(Math.min(currentAura + maxAura * LEVEL_2_EP_REGEN, maxAura));
               break;
            case 3:
               if (livingEntity instanceof Player player) {
                  player.getFoodData().setFoodLevel(Math.min(player.getFoodData().getFoodLevel() + LEVEL_3_FOOD, 20));
               }

               float maxHp = livingEntity.getMaxHealth();
               livingEntity.heal(maxHp * LEVEL_3_HP_REGEN);
               storage.setMagicule(Math.min(currentMagicule + maxMagicule * LEVEL_3_EP_REGEN, maxMagicule));
               storage.setAura(Math.min(currentAura + maxAura * LEVEL_3_EP_REGEN, maxAura));
         }

         storage.markDirty();
      }
   }
}
