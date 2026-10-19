package io.github.Memoires.mysticism.race.insect.wasp.army;

import io.github.Memoires.mysticism.config.MysticismGeneralConfig;
import io.github.Memoires.mysticism.config.race.insect.WaspConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ArmyWaspInsectarRace extends ArmyWaspRace {
   protected static final WaspConfig.ArmyWaspInsectar CONFIG = ((WaspConfig)ConfigRegistry.getConfig(WaspConfig.class)).ArmyWaspInsectar;
   protected static final MysticismGeneralConfig.General GENERAL = ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General;

   public ArmyWaspInsectarRace() {
      this.addAttributeModifier(Attributes.FLYING_SPEED, DEFAULT_RACE_ID, CONFIG.getFlightSpeed(), Operation.ADD_VALUE);
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.ARMY_WASP_SAVANT.get();
   }

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.ARMY_WASP_SAVANT.get());
      list.add((ManasRace)MysticismRaces.WIND_SOUL_INSECT.get());
      return list;
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.ARMY_WASP.get());
      return list;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(CONFIG.epRequirement), 100.0F);
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   @Override
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
