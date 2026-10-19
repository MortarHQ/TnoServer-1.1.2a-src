package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.item.misc.TensuraArmorMaterials;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;

public class MagisteelBodySkill extends Skill {
   private static final IntrinsicMysticismSkillConfig.MagisteelBody CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .MagisteelBody;
   protected static final ResourceLocation MAGISTEEL_BODY = ResourceLocation.fromNamespaceAndPath("mysticism", "magisteel_body");

   public MagisteelBodySkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/magisteel_body.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      if (armor != null && armor.getModifier(MAGISTEEL_BODY) == null) {
         armor.addOrReplacePermanentModifier(new AttributeModifier(MAGISTEEL_BODY, this.calculateArmor(entity), Operation.ADD_VALUE));
      }

      AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
      if (toughness != null && toughness.getModifier(MAGISTEEL_BODY) == null) {
         toughness.addOrReplacePermanentModifier(new AttributeModifier(MAGISTEEL_BODY, this.calculateToughness(entity), Operation.ADD_VALUE));
      }

      entity.level()
         .playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.DEFENCE_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
         );
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      if (armor != null) {
         armor.removeModifier(MAGISTEEL_BODY);
      }

      AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
      if (toughness != null) {
         toughness.removeModifier(MAGISTEEL_BODY);
      }

      entity.level()
         .playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.DEFENCE_DEACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
         );
   }

   protected int calculateArmor(LivingEntity entity) {
      int armor = 0;
      Map<Type, Integer> map = ((ArmorMaterial)this.getMaterial(entity).value()).defense();

      for (Type type : Type.values()) {
         armor += map.getOrDefault(type, 0);
      }

      return armor;
   }

   protected float calculateToughness(LivingEntity entity) {
      return ((ArmorMaterial)this.getMaterial(entity).value()).toughness() * 2.0F;
   }

   protected Holder<ArmorMaterial> getMaterial(LivingEntity entity) {
      double EP = EnergyHelper.getMaxEP(entity);
      if (EP >= CONFIG.hihiirokaneEP) {
         return TensuraArmorMaterials.HIHIIROKANE;
      } else if (EP >= CONFIG.adamantiteEP) {
         return TensuraArmorMaterials.ADAMANTITE;
      } else if (EP >= CONFIG.pureMagisteelEP) {
         return TensuraArmorMaterials.PURE_MAGISTEEL;
      } else {
         return EP >= CONFIG.highMagisteelEP ? TensuraArmorMaterials.HIGH_MAGISTEEL : TensuraArmorMaterials.LOW_MAGISTEEL;
      }
   }
}
