package io.github.Memoires.mysticism.race.insect.ant;

import io.github.Memoires.mysticism.config.race.insect.AntConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.insect.InsectRace;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class AntRace extends InsectRace {
   protected static final AntConfig.Ant CONFIG = ((AntConfig)ConfigRegistry.getConfig(AntConfig.class)).Ant;

   public AntRace() {
      this.addAttributeModifier(Attributes.ARMOR, DEFAULT_RACE_ID, CONFIG.getArmor(), Operation.ADD_VALUE);
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.HARDSHELL_ANT.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.EARTH_SOUL_INSECT.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.HARDSHELL_ANT.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.FIRE_ANT.get());
      list.add((ManasRace)MysticismRaces.HARDSHELL_ANT.get());
      return list;
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.INSECT.get());
      return list;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   @Override
   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }
}
