package io.github.Memoires.mysticism.race.elemental;

import io.github.Memoires.mysticism.config.MysticismGeneralConfig;
import io.github.Memoires.mysticism.config.race.ElementalConfig;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.element.ElementUtils;
import io.github.Memoires.mysticism.storage.element.IElement;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.ability.skill.extra.MagicDarknessTransformSkill;
import io.github.manasmods.tensura.ability.skill.extra.MagicEarthTransformSkill;
import io.github.manasmods.tensura.ability.skill.extra.MagicFlameTransformSkill;
import io.github.manasmods.tensura.ability.skill.extra.MagicLightTransformSkill;
import io.github.manasmods.tensura.ability.skill.extra.MagicSpaceTransformSkill;
import io.github.manasmods.tensura.ability.skill.extra.MagicWaterTransformSkill;
import io.github.manasmods.tensura.ability.skill.extra.MagicWindTransformSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.magic.SummoningMagics;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.spirit.ISpiritWielder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class GreaterElementalRace extends MediumElementalRace {
   protected static final ElementalConfig.GreaterElemental CONFIG = ((ElementalConfig)ConfigRegistry.getConfig(ElementalConfig.class)).GreaterElemental;
   protected static final MysticismGeneralConfig.General GENERAL = ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.ELEMENTAL_LORD.get();
   }

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.ELEMENTAL_LORD.get());
      list.add((ManasRace)MysticismRaces.MAJIN_LORD.get());
      return list;
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.MEDIUM_ELEMENTAL.get());
      return list;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(CONFIG.epRequirement), 100.0F);
   }

   @Override
   public void onRaceSet(ManasRaceInstance instance, LivingEntity living) {
      if (living instanceof Player player) {
         IElement elementStorage = MysticismStorages.getElementFrom(player);
         Element element = elementStorage.getElement();
         if (element == null) {
            Element[] elements = Element.values();
            Element randomElement = elements[living.getRandom().nextInt(7)];
            elementStorage.setElement(randomElement);
            elementStorage.markDirty();
            ElementUtils.grantMagicAndResistance(player, randomElement);
         }

         Element newElement = elementStorage.getElement();
         ManasSkillInstance magicTransform = null;
         switch (newElement) {
            case DARKNESS:
               magicTransform = ((MagicDarknessTransformSkill)ExtraSkills.MAGIC_DARKNESS_TRANSFORM.get()).createDefaultInstance();
               break;
            case LIGHT:
               magicTransform = ((MagicLightTransformSkill)ExtraSkills.MAGIC_LIGHT_TRANSFORM.get()).createDefaultInstance();
               break;
            case FLAME:
               magicTransform = ((MagicFlameTransformSkill)ExtraSkills.MAGIC_FLAME_TRANSFORM.get()).createDefaultInstance();
               break;
            case WATER:
               magicTransform = ((MagicWaterTransformSkill)ExtraSkills.MAGIC_WATER_TRANSFORM.get()).createDefaultInstance();
               break;
            case WIND:
               magicTransform = ((MagicWindTransformSkill)ExtraSkills.MAGIC_WIND_TRANSFORM.get()).createDefaultInstance();
               break;
            case EARTH:
               magicTransform = ((MagicEarthTransformSkill)ExtraSkills.MAGIC_EARTH_TRANSFORM.get()).createDefaultInstance();
               break;
            case SPACE:
               magicTransform = ((MagicSpaceTransformSkill)ExtraSkills.MAGIC_SPACE_TRANSFORM.get()).createDefaultInstance();
         }

         if (magicTransform != null) {
            magicTransform.setMastery(-100.0);
            SkillHelper.learnSkill(player, magicTransform);
            SkillHelper.learnSkill(player, (ManasSkill)SummoningMagics.SUMMON_MEDIUM_ELEMENTAL.get());
            ISpiritWielder spiritStorage = TensuraStorages.getSpiritFrom(living);
            spiritStorage.setSpiritLevel(newElement, SpiritLevel.MEDIUM);
            spiritStorage.markDirty();
         }
      }
   }
}
