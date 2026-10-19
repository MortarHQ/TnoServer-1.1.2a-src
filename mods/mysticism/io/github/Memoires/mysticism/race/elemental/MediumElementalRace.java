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
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.skill.ElementalTransformSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class MediumElementalRace extends LesserElementalRace {
   protected static final ElementalConfig.MediumElemental CONFIG = ((ElementalConfig)ConfigRegistry.getConfig(ElementalConfig.class)).MediumElemental;
   protected static final MysticismGeneralConfig.General GENERAL = ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.GREATER_ELEMENTAL.get();
   }

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.GREATER_ELEMENTAL.get();
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.GREATER_ELEMENTAL.get());
      return list;
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.LESSER_ELEMENTAL.get());
      return list;
   }

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
         ManasSkill elementalTransform = ElementalTransformSkill.getTransformSkill(newElement);
         if (elementalTransform != null) {
            SkillHelper.learnSkill(player, elementalTransform);
         }
      }
   }
}
