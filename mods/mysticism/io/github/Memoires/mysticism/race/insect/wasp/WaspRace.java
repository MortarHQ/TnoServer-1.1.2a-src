package io.github.Memoires.mysticism.race.insect.wasp;

import io.github.Memoires.mysticism.config.MysticismGeneralConfig;
import io.github.Memoires.mysticism.config.race.insect.WaspConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.insect.InsectRace;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class WaspRace extends InsectRace {
   protected static final WaspConfig.Wasp CONFIG = ((WaspConfig)ConfigRegistry.getConfig(WaspConfig.class)).Wasp;
   protected static final MysticismGeneralConfig.General GENERAL = ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.ARMY_WASP.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.WIND_SOUL_INSECT.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.ARMY_WASP.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.ARMY_WASP.get());
      list.add((ManasRace)MysticismRaces.QUEEN_WASP.get());
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

   public void onActivateAbility(ManasRaceInstance instance, LivingEntity entity) {
      if (entity instanceof Player player && !player.isSpectator() && !player.isCreative()) {
         Level level = player.level();
         if (player.getAbilities().mayfly) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            this.setFlyingSpeed(player, true);
         } else {
            player.getAbilities().mayfly = true;
            player.getAbilities().flying = true;
            this.setFlyingSpeed(player, false);
         }

         player.onUpdateAbilities();
         level.playSound(null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      }
   }

   private void setFlyingSpeed(Player player, boolean reset) {
      float flightSpeed = (float)(reset ? GENERAL.flightSpeed : GENERAL.flightSpeed + CONFIG.getFlightSpeed());
      player.getAbilities().setFlyingSpeed(flightSpeed);
      player.onUpdateAbilities();
   }
}
