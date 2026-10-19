package io.github.Memoires.mysticism.race.daemon_doll;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.config.race.DaemonDollConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.template.MysticismEvolutionRequirement;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.demon.GreaterDaemonRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GreaterDollRace extends GreaterDaemonRace {
   protected static final DaemonDollConfig.GreaterDoll CONFIG = ((DaemonDollConfig)ConfigRegistry.getConfig(DaemonDollConfig.class)).GreaterDoll;

   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.ARCH_DOLL.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.DAEMON_DOLL.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.ARCH_DOLL.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.ARCH_DOLL.get());
      return list;
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)TensuraRaces.GREATER_DAEMON.get());
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   public Pair<ResourceKey<Level>, BlockState> getRespawnDimension(ManasRaceInstance instance, LivingEntity owner) {
      return Pair.of(Level.OVERWORLD, Blocks.STONE.defaultBlockState());
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new MysticismEvolutionRequirement.PossessGolemRequirement(), 100.0F);
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

      EnergyHelper.removeSpiritualEPLimit(entity);
      List<TensuraSkill> list = new ArrayList<>();
      list.add((TensuraSkill)IntrinsicSkills.POSSESSION.get());
      list.forEach(skill -> SkillAPI.getSkillsFrom(entity).forgetSkill(skill));
      if (instance.getOrCreateTag() != null && instance.getOrCreateTag().getBoolean("BoneGolem")) {
         instance.getOrCreateTag().remove("BoneGolem");
         instance.markDirty();
      }
   }
}
