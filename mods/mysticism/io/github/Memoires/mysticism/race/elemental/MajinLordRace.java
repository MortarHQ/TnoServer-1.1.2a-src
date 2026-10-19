package io.github.Memoires.mysticism.race.elemental;

import io.github.Memoires.mysticism.ability.magic.spiritual.earth.GravitationalVoidMagic;
import io.github.Memoires.mysticism.config.race.ElementalConfig;
import io.github.Memoires.mysticism.race.template.MysticismEvolutionRequirement;
import io.github.Memoires.mysticism.registry.magic.SpiritualMysticismMagics;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.element.ElementUtils;
import io.github.Memoires.mysticism.storage.element.IElement;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.PhysicalBodyRequirement;
import io.github.manasmods.tensura.registry.magic.SummoningMagics;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.spirit.ISpiritWielder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class MajinLordRace extends GreaterElementalRace {
   protected static final ElementalConfig.MajinLord CONFIG = ((ElementalConfig)ConfigRegistry.getConfig(ElementalConfig.class)).MajinLord;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Override
   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.DIVINE_MAJIN_ELEMENTAL.get();
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.DIVINE_MAJIN_ELEMENTAL.get());
      return list;
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.GREATER_ELEMENTAL.get());
      return list;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = super.getIntrinsicSkills(instance, entity);
      list.add((ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_RESISTANCE.get());
      return list;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(
         new EPRequirement(CONFIG.epRequirement),
         25.0F,
         new PhysicalBodyRequirement(),
         25.0F,
         new MysticismEvolutionRequirement.AlignmentRequirement(MysticismEvolutionRequirement.AlignmentRequirement.Expected.MAJIN),
         50.0F
      );
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
         ISpiritWielder spiritStorage = TensuraStorages.getSpiritFrom(living);
         spiritStorage.setSpiritLevel(newElement, SpiritLevel.GREATER);
         spiritStorage.markDirty();
         SkillHelper.learnSkill(player, (ManasSkill)SummoningMagics.SUMMON_GREATER_ELEMENTAL.get());
         ManasSkillInstance spiritLordMagic = null;
         switch (newElement) {
            case EARTH:
               spiritLordMagic = ((GravitationalVoidMagic)SpiritualMysticismMagics.GRAVITATIONAL_VOID.get()).createDefaultInstance();
            default:
               if (spiritLordMagic != null) {
                  SkillHelper.learnSkill(player, spiritLordMagic);
                  Skills storage = SkillAPI.getSkillsFrom(living);
                  Optional<ManasSkillInstance> optional = storage.getSkill((ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get());
                  if (!optional.isEmpty()) {
                     ManasSkillInstance skillInstance = optional.get();
                     if (skillInstance.isTemporarySkill()) {
                        instance.addIntrinsicSkill((ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get());
                        SkillHelper.learnSkill(living, (ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get());
                     }
                  }
               }
         }
      }
   }
}
