package io.github.Memoires.mysticism.race.tengu;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.config.race.AngelConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.angel.GreaterAngelRace;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TenguRace extends GreaterAngelRace {
   protected static final AngelConfig.Tengu CONFIG = ((AngelConfig)ConfigRegistry.getConfig(AngelConfig.class)).Tengu;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.TENGU_SAINT.get();
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.TENGU_SAINT.get();
   }

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.TENGU_SAINT.get());
      return list;
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.GREATER_ANGEL.get());
      list.add((ManasRace)MysticismRaces.ARCH_ANGEL.get());
      return list;
   }

   @Override
   public Pair<ResourceKey<Level>, BlockState> getRespawnDimension(ManasRaceInstance instance, LivingEntity owner) {
      return Pair.of(Level.OVERWORLD, Blocks.STONE.defaultBlockState());
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   public void triggerEvolutionRewards(ManasRaceInstance instance, LivingEntity entity) {
      AttributeInstance aura = entity.getAttribute(TensuraAttributes.MAX_AURA);
      if (aura != null && aura.getBaseValue() < this.getMinBaseAura()) {
         aura.setBaseValue(this.getMinBaseAura());
      }

      AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAX_MAGICULE);
      if (magicule != null && magicule.getBaseValue() < this.getMinBaseMagicule()) {
         magicule.setBaseValue(this.getMinBaseMagicule());
      }

      List<TensuraSkill> list = new ArrayList<>();
      list.add((TensuraSkill)IntrinsicSkills.POSSESSION.get());
      list.add((TensuraSkill)IntrinsicSkills.LIGHT_TRANSFORM.get());
      list.forEach(skill -> SkillAPI.getSkillsFrom(entity).forgetSkill(skill));
   }
}
