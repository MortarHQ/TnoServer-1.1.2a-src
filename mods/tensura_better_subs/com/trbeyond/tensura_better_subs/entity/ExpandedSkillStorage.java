package com.trbeyond.tensura_better_subs.entity;

import com.trbeyond.tensura_better_subs.BetterSubs;
import com.trbeyond.tensura_better_subs.config.ExpandedSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageEvents;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.manascore.storage.api.StorageEvents.RegisterStorage;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.registry.block.TensuraBlocks;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class ExpandedSkillStorage extends Storage implements IExpandedSkillAcquisition {
   public static final ExpandedSkillConfig config = (ExpandedSkillConfig)ConfigRegistry.getConfig(ExpandedSkillConfig.class);
   private static StorageKey<ExpandedSkillStorage> key = null;
   private int spiderEyesEaten = 0;
   private int spiderKills = 0;
   private int slimeKills = 0;
   private int centipedeKills = 0;
   private int totalKills = 0;
   private float damageTaken = 0.0F;
   private boolean existenceUpdated;

   public static void init() {
      StorageEvents.REGISTER_ENTITY_STORAGE
         .register(
            (RegisterStorage)registry -> {
               ResourceLocation resourceLocation = BetterSubs.rl("expanded_skill_storage");
               Objects.requireNonNull(LivingEntity.class);
               key = registry.register(
                  resourceLocation,
                  ExpandedSkillStorage.class,
                  entity -> entity instanceof OwnableEntity
                     && entity instanceof LivingEntity livingEntity
                     && !livingEntity.getType().is(TensuraEntityTags.CLONES),
                  target -> new ExpandedSkillStorage((LivingEntity)target)
               );
            }
         );
   }

   public static StorageKey<ExpandedSkillStorage> getKey() {
      return key;
   }

   protected ExpandedSkillStorage(StorageHolder holder) {
      super(holder);
   }

   public void save(CompoundTag compoundTag) {
      compoundTag.putInt("spiderEyesEaten", this.spiderEyesEaten);
      compoundTag.putInt("spiderKills", this.spiderKills);
      compoundTag.putInt("slimeKills", this.slimeKills);
      compoundTag.putInt("centipedeKills", this.centipedeKills);
      compoundTag.putInt("totalKills", this.totalKills);
      compoundTag.putFloat("damageTaken", this.damageTaken);
   }

   public void load(CompoundTag compoundTag) {
      this.spiderEyesEaten = compoundTag.getInt("spiderEyesEaten");
      this.spiderKills = compoundTag.getInt("spiderKills");
      this.slimeKills = compoundTag.getInt("slimeKills");
      this.centipedeKills = compoundTag.getInt("centipedeKills");
      this.totalKills = compoundTag.getInt("totalKills");
      this.damageTaken = compoundTag.getFloat("damageTaken");
   }

   @Override
   public boolean usedSkillItem(Item item, LivingEntity entity) {
      Skills storage = SkillAPI.getSkillsFrom(entity);
      if (item == ((Block)TensuraBlocks.CHARYBDIS_CORE.get()).asItem()) {
         boolean acquiredSkill = false;
         if (IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)ExtraSkills.MAGIC_JAMMING.get())) {
            acquiredSkill = true;
         }

         if (IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get())) {
            acquiredSkill = true;
         }

         if (IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)ExtraSkills.GRAVITY_MANIPULATION.get())) {
            acquiredSkill = true;
         }

         return acquiredSkill;
      } else {
         if (item == Items.SPIDER_EYE && this.spiderEyesEaten < config.spiderEyesEatenForPoison) {
            Optional<ManasSkillInstance> optional = storage.getSkill((ManasSkill)CommonSkills.POISON.get());
            if (optional.isEmpty() || optional.get().getMastery() < 0.0) {
               this.spiderEyesEaten++;
               if (this.spiderEyesEaten >= config.spiderEyesEatenForPoison && this.spiderKills >= config.spiderKillsForPoison) {
                  IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)CommonSkills.POISON.get(), true);
               }

               return true;
            }
         }

         return false;
      }
   }

   @Override
   public void attemptTallyKill(Entity killedEntity, LivingEntity entity) {
      LivingEntity owner = SubordinateHelper.getSubordinateOwner(entity);
      this.totalKills++;
      double currentEP = EnergyHelper.getMaxEP(entity);
      if (this.totalKills >= config.killsForStrength && currentEP >= config.epForStrength) {
         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)CommonSkills.STRENGTH.get(), true);
      }

      if (this.totalKills >= config.killsForSteelStrength && currentEP >= config.epForSteelStrength) {
         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)ExtraSkills.STEEL_STRENGTH.get(), true);
      }

      EntityType<?> killedType = killedEntity.getType();
      if (killedType == MonsterEntityTypes.SLIME.get()
         || killedType == MonsterEntityTypes.METAL_SLIME.get()
         || killedType == MonsterEntityTypes.SUPERMASSIVE_SLIME.get()) {
         this.slimeKills++;
         if (this.slimeKills >= config.slimeKillsForRegen) {
            IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)CommonSkills.SELF_REGENERATION.get());
         }
      }

      if (killedType == MonsterEntityTypes.EVIL_CENTIPEDE.get()) {
         this.centipedeKills++;
         if (this.centipedeKills >= config.centipedeKillsForParalysis) {
            IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)CommonSkills.PARALYSIS.get(), true);
         }
      }

      if (killedType == MonsterEntityTypes.BLACK_SPIDER.get()) {
         this.spiderKills++;
         if (this.spiderKills >= config.spiderKillsForPoison) {
            IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)CommonSkills.POISON.get(), true);
         }
      }

      if (killedType == MonsterEntityTypes.ORC_DISASTER.get()) {
         for (LivingEntity subordinate : killedEntity.level()
            .getEntitiesOfClass(
               LivingEntity.class,
               killedEntity.getBoundingBox().inflate(30.0),
               selectedEntity -> Objects.equals(SubordinateHelper.getSubordinateOwnerUUID(selectedEntity), SubordinateHelper.getSubordinateOwnerUUID(entity))
                  && BetterSubs.getSkillExpansionFrom(selectedEntity) != null
                  && selectedEntity.getLastHurtMob() == killedEntity
            )) {
            IExpandedSkillAcquisition.grantSkill(subordinate, (ManasSkill)CommonSkills.CORROSION.get(), true);
         }

         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)CommonSkills.CORROSION.get(), true);
      }
   }

   @Override
   public void attemptTallyDamage(LivingEntity entity, float amount) {
      this.damageTaken += amount;
      if (this.damageTaken >= config.damageTakenForStrengthenBody && EnergyHelper.getMaxEP(entity) >= config.epForStrengthenBody) {
         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)ExtraSkills.STRENGTHEN_BODY.get());
      }
   }

   @Override
   public void entityEPUpdate(LivingEntity entity) {
      double currentEP = EnergyHelper.getMaxEP(entity);
      if (currentEP >= config.epForStrengthenBody && this.damageTaken >= config.damageTakenForStrengthenBody) {
         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)ExtraSkills.STRENGTHEN_BODY.get());
      }

      if (currentEP >= config.epForSteelStrength && this.totalKills >= config.killsForSteelStrength) {
         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)ExtraSkills.STEEL_STRENGTH.get(), true);
      }

      if (currentEP >= config.epForStrength && this.totalKills >= config.killsForStrength) {
         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)CommonSkills.STRENGTH.get(), true);
      }

      if (currentEP >= config.epForWaterCurrentControl) {
         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)CommonSkills.WATER_CURRENT_CONTROL.get());
      }

      if (currentEP >= config.epForHaki) {
         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)ExtraSkills.HAKI.get());
      }

      if (currentEP >= config.epForInfiniteRegeneration && SkillUtils.isSkillMastered(entity, (ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get())) {
         IExpandedSkillAcquisition.grantSkill(entity, (ManasSkill)ExtraSkills.INFINITE_REGENERATION.get());
      }
   }

   @Override
   public void markUpdateExistence() {
      this.existenceUpdated = true;
   }

   @Override
   public void tickStorage(LivingEntity entity) {
      if (this.existenceUpdated) {
         this.entityEPUpdate(entity);
         this.existenceUpdated = false;
      }
   }
}
