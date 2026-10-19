package com.github.dominickwd04.traddon.ability.skill.unique;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.intrinsic.PossessionSkill;
import io.github.manasmods.tensura.ability.subclass.ICloning;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.race.TensuraRace;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class DeviatorSkill extends PossessionSkill {
   public static final TrAddonSkillConfig.Deviator CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).deviator;
   private final SkillType type = SkillType.UNIQUE;

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura", "textures/skill/unique/predator.png");
   }

   public double getObtainingEpCost() {
      return 75000.0;
   }

   public double learningCost() {
      return 1000.0;
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      MutableComponent modeName = Component.translatable("tensura.skill.mode." + this.getModeId(instance, mode));
      return modeName.withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if ((this.isInSlot(owner) || instance.isToggled()) && source.getEntity() == owner && TensuraDamageHelper.isPhysicalAttack(source)) {
         double m = CONFIG.spiritualDamageMultiplier;
         float multiplier = (float)m;
         double mm = CONFIG.masterDamageMultiplier;
         float masterMultiplier = (float)mm;
         TensuraDamageHelper.directSpiritualHurt(target, owner, (Float)amount.get() * (instance.isMastered(owner) ? masterMultiplier : multiplier), 0.0F);
         this.addMasteryPoint(instance, owner);
         return true;
      } else {
         return false;
      }
   }

   public static void copyStatsAndSkills(LivingEntity target, Player owner, double maxAttack, double maxHealth) {
      if (target.getType().equals(HumanEntityTypes.BONE_GOLEM.get()) || target.getType().equals(HumanEntityTypes.CLONE.get())) {
         AttributeInstance attribute = target.getAttribute(Attributes.ARMOR);
         double value;
         if (attribute == null) {
            value = 0.0;
         } else {
            value = attribute.getBaseValue();
            AttributeModifier raceStat = attribute.getModifier(TensuraRace.DEFAULT_RACE_ID);
            if (raceStat != null) {
               value += raceStat.amount();
            }
         }

         AttributeHelper.addPermanentAttribute(
            owner, Attributes.ARMOR, TensuraRace.DEFAULT_RACE_ID, value - owner.getAttributeBaseValue(Attributes.ARMOR), Operation.ADD_VALUE
         );
         if (target.getType().equals(HumanEntityTypes.BONE_GOLEM.get())) {
            RaceAPI.getRaceFrom(owner).getRace().ifPresent(instancex -> instancex.getOrCreateTag().putBoolean("BoneGolem", true));
            AttributeInstance maxHP = target.getAttribute(Attributes.MAX_HEALTH);
            if (maxHP != null) {
               double health = maxHP.getBaseValue();
               AttributeModifier raceStat = maxHP.getModifier(TensuraRace.DEFAULT_RACE_ID);
               if (raceStat != null) {
                  health += raceStat.amount();
               }

               maxHealth = Math.max(maxHealth, health);
            }
         }
      }

      for (Entry<Holder<Attribute>, Double> map : getStatList().entrySet()) {
         AttributeInstance attribute = target.getAttribute(map.getKey());
         double value;
         if (attribute == null) {
            value = map.getValue();
         } else {
            value = attribute.getBaseValue();
            AttributeModifier raceStat = attribute.getModifier(TensuraRace.DEFAULT_RACE_ID);
            if (raceStat != null) {
               value += raceStat.amount();
            }
         }

         if (map.getKey().equals(Attributes.MOVEMENT_SPEED) && !(target instanceof Player)) {
            value = ICloning.getConvertedMovementSpeed(value, false);
         } else if (map.getKey().equals(Attributes.ATTACK_DAMAGE) && isCloneNotOwned(owner, target)) {
            value = Math.min(value, maxAttack);
         } else if (map.getKey().equals(Attributes.MAX_HEALTH) && isCloneNotOwned(owner, target)) {
            value = Math.min(value, maxHealth);
         } else if (map.getKey().equals(Attributes.JUMP_STRENGTH) && !(target instanceof CloneEntity)) {
            value = ICloning.getConvertedJumpStrength(value, false);
         }

         AttributeHelper.addPermanentAttribute(
            owner, map.getKey(), TensuraRace.DEFAULT_RACE_ID, value - owner.getAttributeBaseValue(map.getKey()), Operation.ADD_VALUE
         );
      }

      owner.setHealth(Math.max(target.getHealth(), 0.0F));

      for (MobEffectInstance instance : target.getActiveEffects()) {
         owner.addEffect(new MobEffectInstance(instance));
      }

      boolean clone = target instanceof CloneEntity;

      for (ManasSkillInstance instance : List.copyOf(SkillAPI.getSkillsFrom(target).getLearnedSkills())) {
         if (canCopySkill(instance, target, clone) && (!(instance.getMastery() < 0.0) || instance.getMastery() == -100.0)) {
            ManasSkillInstance copy = TensuraSkillInstance.fromNBT(instance.toNBT());
            if (!copy.isTemporarySkill()) {
               copy.setRemoveTime(-1);
            }

            boolean unique = false;
            if (!copy.isTemporarySkill()
               && ((Skill)copy.getSkill()).getType() == SkillType.UNIQUE
               && Math.random() > (instance.isMastered(target) ? CONFIG.masterUniqueSkillChance : CONFIG.uniqueSkillChance)) {
               copy.setRemoveTime(-2);
               unique = true;
            }

            SkillHelper.learnSkill(owner, copy, !copy.isTemporarySkill() && !unique ? -1 : copy.getRemoveTime());
         }
      }
   }

   public static boolean canCopySkill(ManasSkillInstance instance, LivingEntity target, boolean clone) {
      if (clone) {
         return instance.isTemporarySkill();
      }

      if (target.getType().is(TensuraEntityTags.NO_SKILL_PLUNDER)) {
         return false;
      }

      if (target instanceof Player player) {
         if (instance.isTemporarySkill()) {
            return true;
         }

         Optional<ManasRaceInstance> optional = RaceAPI.getRaceFrom(player).getRace();
         return optional.isPresent() && optional.get().getObtainedIntrinsicSkills().contains(instance.getSkill());
      } else {
         return true;
      }
   }

   private static boolean isCloneNotOwned(LivingEntity owner, LivingEntity target) {
      return !(target instanceof CloneEntity clone && clone.isOwnedBy(owner));
   }

   private static Map<Holder<Attribute>, Double> getStatList() {
      return Map.of(
         Attributes.MAX_HEALTH,
         1.0,
         Attributes.ATTACK_DAMAGE,
         0.1,
         Attributes.KNOCKBACK_RESISTANCE,
         0.0,
         Attributes.MOVEMENT_SPEED,
         0.1,
         Attributes.JUMP_STRENGTH,
         0.42,
         Attributes.SAFE_FALL_DISTANCE,
         3.0,
         ManasCoreAttributes.SWIM_SPEED_MULTIPLIER,
         1.0
      );
   }
}
