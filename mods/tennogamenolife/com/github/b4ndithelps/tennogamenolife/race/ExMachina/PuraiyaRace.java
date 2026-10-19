package com.github.b4ndithelps.tennogamenolife.race.ExMachina;

import com.github.b4ndithelps.tennogamenolife.config.race.ExMachinaRaceConfig;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.RaceUtils;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.AwakenRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
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

public class PuraiyaRace extends HubieDoraRace {
   private static final ExMachinaRaceConfig.Puraiya CONFIG = new ExMachinaRaceConfig.Puraiya();

   public PuraiyaRace() {
      this.applyDefaultAttributeModifiers();
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return new ArrayList<>();
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new AwakenRequirement(), 50.0F, new EPRequirement(1000000.0), 50.0F);
   }

   @Override
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
