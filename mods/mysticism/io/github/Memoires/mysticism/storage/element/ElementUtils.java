package io.github.Memoires.mysticism.storage.element;

import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.ability.skill.extra.EarthManipulationSkill;
import io.github.manasmods.tensura.ability.skill.extra.FlameManipulationSkill;
import io.github.manasmods.tensura.ability.skill.extra.SpatialManipulationSkill;
import io.github.manasmods.tensura.ability.skill.extra.WaterManipulationSkill;
import io.github.manasmods.tensura.ability.skill.extra.WindManipulationSkill;
import io.github.manasmods.tensura.ability.skill.resist.DarknessAttackResistance;
import io.github.manasmods.tensura.ability.skill.resist.EarthAttackResistance;
import io.github.manasmods.tensura.ability.skill.resist.FireAttackResistance;
import io.github.manasmods.tensura.ability.skill.resist.LightAttackResistance;
import io.github.manasmods.tensura.ability.skill.resist.SpatialAttackResistance;
import io.github.manasmods.tensura.ability.skill.resist.WaterAttackResistance;
import io.github.manasmods.tensura.ability.skill.resist.WindAttackResistance;
import io.github.manasmods.tensura.block.entity.PrayingPathBlockEntity;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Unique;

public class ElementUtils {
   public static void grantMagicAndResistance(Player player, Element element) {
      switch (element) {
         case DARKNESS: {
            grantManipulation(player, element);
            PrayingPathBlockEntity.grantSpiritMagic(player, element, SpiritLevel.LESSER);
            ManasSkillInstance newResInstance = ((DarknessAttackResistance)ResistanceSkills.DARKNESS_ATTACK_RESISTANCE.get()).createDefaultInstance();
            newResInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newResInstance);
            break;
         }
         case EARTH: {
            ManasSkillInstance newManiInstance = ((EarthManipulationSkill)ExtraSkills.EARTH_MANIPULATION.get()).createDefaultInstance();
            newManiInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newManiInstance);
            PrayingPathBlockEntity.grantSpiritMagic(player, element, SpiritLevel.LESSER);
            PrayingPathBlockEntity.grantManipulation(player, element);
            ManasSkillInstance newResInstance = ((EarthAttackResistance)ResistanceSkills.EARTH_ATTACK_RESISTANCE.get()).createDefaultInstance();
            newResInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newResInstance);
            break;
         }
         case FLAME: {
            ManasSkillInstance newManiInstance = ((FlameManipulationSkill)ExtraSkills.FLAME_MANIPULATION.get()).createDefaultInstance();
            newManiInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newManiInstance);
            PrayingPathBlockEntity.grantSpiritMagic(player, element, SpiritLevel.LESSER);
            ManasSkillInstance newResInstance = ((FireAttackResistance)ResistanceSkills.FLAME_ATTACK_RESISTANCE.get()).createDefaultInstance();
            newResInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newResInstance);
            break;
         }
         case LIGHT: {
            grantManipulation(player, element);
            PrayingPathBlockEntity.grantSpiritMagic(player, element, SpiritLevel.LESSER);
            ManasSkillInstance newResInstance = ((LightAttackResistance)ResistanceSkills.LIGHT_ATTACK_RESISTANCE.get()).createDefaultInstance();
            newResInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newResInstance);
            break;
         }
         case SPACE: {
            ManasSkillInstance newManiInstance = ((SpatialManipulationSkill)ExtraSkills.SPATIAL_MANIPULATION.get()).createDefaultInstance();
            newManiInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newManiInstance);
            PrayingPathBlockEntity.grantSpiritMagic(player, element, SpiritLevel.LESSER);
            ManasSkillInstance newResInstance = ((SpatialAttackResistance)ResistanceSkills.SPATIAL_ATTACK_RESISTANCE.get()).createDefaultInstance();
            newResInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newResInstance);
            break;
         }
         case WATER: {
            ManasSkillInstance newManiInstance = ((WaterManipulationSkill)ExtraSkills.WATER_MANIPULATION.get()).createDefaultInstance();
            newManiInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newManiInstance);
            PrayingPathBlockEntity.grantSpiritMagic(player, element, SpiritLevel.LESSER);
            ManasSkillInstance newResInstance = ((WaterAttackResistance)ResistanceSkills.WATER_ATTACK_RESISTANCE.get()).createDefaultInstance();
            newResInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newResInstance);
            break;
         }
         case WIND: {
            ManasSkillInstance newManiInstance = ((WindManipulationSkill)ExtraSkills.WIND_MANIPULATION.get()).createDefaultInstance();
            newManiInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newManiInstance);
            PrayingPathBlockEntity.grantSpiritMagic(player, element, SpiritLevel.LESSER);
            ManasSkillInstance newResInstance = ((WindAttackResistance)ResistanceSkills.WIND_ATTACK_RESISTANCE.get()).createDefaultInstance();
            newResInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(player, newResInstance);
         }
      }
   }

   @Unique
   private static void grantManipulation(Player player, Element element) {
      if (element == Element.DARKNESS) {
         ManasSkillInstance manipulation = new TensuraSkillInstance((ManasSkill)ExtraMysticismSkills.DARKNESS_MANIPULATION.get());
         manipulation.getOrCreateTag().putBoolean("NoMagiculeCost", true);
         SkillHelper.learnSkill(player, manipulation);
      } else if (element == Element.LIGHT) {
         ManasSkillInstance manipulation = new TensuraSkillInstance((ManasSkill)ExtraMysticismSkills.LIGHT_MANIPULATION.get());
         manipulation.getOrCreateTag().putBoolean("NoMagiculeCost", true);
         SkillHelper.learnSkill(player, manipulation);
      }
   }
}
