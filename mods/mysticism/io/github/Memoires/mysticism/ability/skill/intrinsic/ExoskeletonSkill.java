package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.item.misc.TensuraArmorMaterials;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;

public class ExoskeletonSkill extends Skill {
   private static final IntrinsicMysticismSkillConfig.Exoskeleton CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .Exoskeleton;
   protected static final ResourceLocation EXOSKELETON = ResourceLocation.fromNamespaceAndPath("mysticism", "exoskeleton");

   public ExoskeletonSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/exoskeleton.png");
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (this.dragonSkinToggled(entity) && instance.isToggled()) {
         entity.sendSystemMessage(
            Component.translatable("tensura.skill.lack_requirement.toggled_off", new Object[]{instance.getChatDisplayName(true)})
               .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
         );
         instance.setToggled(false);
         instance.onToggleOff(entity);
         instance.markDirty();
      }

      if (!instance.isToggled() && !TickingSkill.isTickingSkill(entity, this) && instance.getOrCreateTag().getBoolean("IsHeld")) {
         instance.getOrCreateTag().putBoolean("IsHeld", false);
         AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
         if (armor != null) {
            armor.removeModifier(EXOSKELETON);
         }

         AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
         if (toughness != null) {
            toughness.removeModifier(EXOSKELETON);
         }

         entity.level()
            .playSound(
               null,
               entity.getX(),
               entity.getY(),
               entity.getZ(),
               (SoundEvent)TensuraSoundEvents.DEFENCE_DEACTIVATE.get(),
               TensuraSkill.ABILITY_SOUND,
               1.0F,
               1.0F
            );
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      if (armor != null && armor.getModifier(EXOSKELETON) == null) {
         armor.addOrReplacePermanentModifier(new AttributeModifier(EXOSKELETON, this.calculateArmor(entity), Operation.ADD_VALUE));
      }

      AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
      if (toughness != null && toughness.getModifier(EXOSKELETON) == null) {
         toughness.addOrReplacePermanentModifier(new AttributeModifier(EXOSKELETON, this.calculateToughness(entity), Operation.ADD_VALUE));
      }

      entity.level()
         .playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.DEFENCE_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
         );
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      if (armor != null) {
         armor.removeModifier(EXOSKELETON);
      }

      AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
      if (toughness != null) {
         toughness.removeModifier(EXOSKELETON);
      }

      entity.level()
         .playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.DEFENCE_DEACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
         );
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (instance.isMastered(entity) && this.dragonSkinToggled(entity)) {
         return false;
      }

      instance.getOrCreateTag().putBoolean("IsHeld", true);
      if (heldTicks == 0) {
         entity.level()
            .playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.DEFENCE_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
            );
      }

      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      if (armor != null && armor.getModifier(EXOSKELETON) == null) {
         armor.addOrReplacePermanentModifier(new AttributeModifier(EXOSKELETON, this.calculateArmor(entity), Operation.ADD_VALUE));
      }

      AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
      if (toughness != null && toughness.getModifier(EXOSKELETON) == null) {
         toughness.addOrReplacePermanentModifier(new AttributeModifier(EXOSKELETON, this.calculateToughness(entity), Operation.ADD_VALUE));
      }

      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(entity);
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (!instance.isMastered(entity) || !this.dragonSkinToggled(entity)) {
         instance.getOrCreateTag().putBoolean("IsHeld", false);
         entity.level()
            .playSound(
               null,
               entity.getX(),
               entity.getY(),
               entity.getZ(),
               (SoundEvent)TensuraSoundEvents.DEFENCE_DEACTIVATE.get(),
               TensuraSkill.ABILITY_SOUND,
               1.0F,
               1.0F
            );
         AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
         if (armor != null) {
            armor.removeModifier(EXOSKELETON);
         }

         AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
         if (toughness != null) {
            toughness.removeModifier(EXOSKELETON);
         }

         instance.setCoolDown(3, 0);
      }
   }

   protected int calculateArmor(LivingEntity entity) {
      int armor = 0;
      Map<Type, Integer> map = ((ArmorMaterial)this.getMaterial(entity).value()).defense();

      for (Type type : Type.values()) {
         armor += map.getOrDefault(type, 0);
      }

      return armor / 2;
   }

   protected float calculateToughness(LivingEntity entity) {
      return ((ArmorMaterial)this.getMaterial(entity).value()).toughness() * 2.0F;
   }

   protected Holder<ArmorMaterial> getMaterial(LivingEntity entity) {
      double EP = EnergyHelper.getMaxEP(entity);
      if (EP >= CONFIG.hihiirokaneEP) {
         return TensuraArmorMaterials.HIHIIROKANE;
      } else if (EP >= CONFIG.mlSAEP) {
         return TensuraArmorMaterials.MONSTER_LEATHER_SPECIAL_A;
      } else if (EP >= CONFIG.mlAEP) {
         return TensuraArmorMaterials.MONSTER_LEATHER_A;
      } else {
         return EP >= CONFIG.mlBEP ? TensuraArmorMaterials.MONSTER_LEATHER_B : TensuraArmorMaterials.MONSTER_LEATHER_C;
      }
   }

   private boolean dragonSkinToggled(LivingEntity entity) {
      return SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)IntrinsicSkills.DRAGON_SKIN.get()).<Boolean>map(ManasSkillInstance::isToggled).orElse(false);
   }
}
