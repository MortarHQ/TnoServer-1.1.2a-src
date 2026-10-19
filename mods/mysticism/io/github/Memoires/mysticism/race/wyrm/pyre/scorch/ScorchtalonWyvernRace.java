package io.github.Memoires.mysticism.race.wyrm.pyre.scorch;

import io.github.Memoires.mysticism.config.race.WyrmConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.AbilityRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ScorchtalonWyvernRace extends ScorchtailSalamanderRace {
   protected static final WyrmConfig.ScorchtalonWyvern CONFIG = ((WyrmConfig)ConfigRegistry.getConfig(WyrmConfig.class)).ScorchtalonWyvern;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of();
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)MysticismRaces.SCORCHTAIL_SALAMANDER.get());
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(
         new EPRequirement(CONFIG.epRequirement),
         33.4F,
         new AbilityRequirement(
            (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(CONFIG.abilityRequirement)), CONFIG.abilityMasteryRequirement
         ),
         33.3F,
         new AbilityRequirement(
            (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(CONFIG.ability2Requirement)), CONFIG.ability2MasteryRequirement
         ),
         33.3F
      );
   }

   public void onActivateAbility(ManasRaceInstance instance, LivingEntity entity) {
      if (!entity.isPassenger() && entity instanceof Player player && (player.getFoodData().getFoodLevel() > 0 || player.getAbilities().invulnerable)) {
         if (!player.getAbilities().invulnerable) {
            player.getFoodData().addExhaustion(2.0F);
         }

         if (player.onGround()) {
            Vec3 delta = player.getDeltaMovement();
            double dy = delta.y <= 0.0 ? 0.5 : delta.y + 0.5;
            player.setDeltaMovement(new Vec3(delta.x(), dy, delta.z()));
         }

         SkillHelper.riptidePush(player, this.getFlightBoost());
         instance.setCooldown(this.getFlightBoostCooldown());
         player.hurtMarked = true;
         player.hasImpulse = true;
         player.startFallFlying();
      }
   }

   protected float getFlightBoost() {
      return CONFIG.flightBoost;
   }

   protected int getFlightBoostCooldown() {
      return CONFIG.flightCooldown;
   }
}
