package com.github.b4ndithelps.tennogamenolife.race.ExMachina;

import com.github.b4ndithelps.tennogamenolife.config.race.ExMachinaRaceConfig;
import com.github.b4ndithelps.tennogamenolife.registry.race.Tenreincarnationraces;
import com.github.b4ndithelps.tennogamenolife.registry.skill.TenreincarnationSkill;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.RaceUtils;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MachineRace extends DefaultRace implements IExMachinaDecorativeRace {
   private static final ExMachinaRaceConfig.Machine CONFIG = new ExMachinaRaceConfig.Machine();

   public MachineRace() {
      super(Difficulty.HARD);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)TenreincarnationSkill.BARRIER.get());
      list.add((ManasSkill)ExtraSkills.MAGIC_SENSE.get());
      return list;
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)Tenreincarnationraces.BEFEHLER.get());
      list.add((ManasRace)Tenreincarnationraces.KAMPF.get());
      list.add((ManasRace)Tenreincarnationraces.ZEICHEN.get());
      list.add((ManasRace)Tenreincarnationraces.PRUEFER.get());
      list.add((ManasRace)Tenreincarnationraces.SEHER.get());
      return list;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)Tenreincarnationraces.BEFEHLER.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of();
   }

   public void onActivateAbility(ManasRaceInstance instance, LivingEntity entity) {
      if (entity instanceof Player player && !player.isSpectator() && !player.isCreative()) {
         Level level = player.level();
         if (player.getAbilities().mayfly) {
            if (RaceUtils.canStillFly(player, true, true, false)) {
               return;
            }

            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
         } else {
            player.getAbilities().mayfly = true;
            player.getAbilities().flying = true;
         }

         player.onUpdateAbilities();
         level.playSound(
            (Player)null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F
         );
      }
   }
}
