package org.crypticdev.stextras.race.wispfolk;

import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.SpawnPointHelper;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.AbilityRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.block.TensuraBlocks;
import io.github.manasmods.tensura.registry.dimension.TensuraDimensions;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.crypticdev.stextras.config.race.WispfolkConfig;
import org.crypticdev.stextras.registary.effects.STExtrasMobEffects;
import org.crypticdev.stextras.registary.race.STExtrasRaces;

public class WispfolkRace extends DefaultRace {
   private static final double LABYRINTH_SPAWN_X = 16.0;
   private static final double LABYRINTH_SPAWN_Y = 1.0;
   private static final double LABYRINTH_SPAWN_Z = 10.5;

   public WispfolkRace(Difficulty difficulty) {
      super(difficulty);
   }

   public WispfolkRace() {
      this(Difficulty.EXTREME);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return this.getWispfolkConfig().Wispfolk;
   }

   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return List.of(
         (ManasSkill)IntrinsicSkills.WIND_TRANSFORM.get(), (ManasSkill)ResistanceSkills.MAGIC_RESISTANCE.get(), (ManasSkill)IntrinsicSkills.POSSESSION.get()
      );
   }

   public void onActivateAbility(ManasRaceInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         if (player.hasEffect(TensuraMobEffects.getReference(STExtrasMobEffects.WISP_MODE))) {
            player.removeEffect(TensuraMobEffects.getReference(STExtrasMobEffects.WISP_MODE));
         } else {
            player.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(STExtrasMobEffects.WISP_MODE), 1728000, 0, false, false, false));
         }
      }
   }

   public double getElementalSpiritsChance(Element element, SpiritLevel level) {
      if (!Element.WIND.equals(element)) {
         return super.getElementalSpiritsChance(element, level);
      } else {
         return SpiritLevel.MEDIUM.equals(level) ? 100.0 : super.getElementalSpiritsChance(element, level) * 2.0;
      }
   }

   public Pair<ResourceKey<Level>, BlockState> getRespawnDimension(ManasRaceInstance instance, LivingEntity owner) {
      return Pair.of(TensuraDimensions.LABYRINTH, ((Block)TensuraBlocks.LABYRINTH_LIGHT_PATH.get()).defaultBlockState());
   }

   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)STExtrasRaces.GALE_WISP.get();
   }

   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)STExtrasRaces.ZEPHYR_WISP.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)STExtrasRaces.GALE_WISP.get());
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance instance, LivingEntity entity) {
      return Map.of();
   }

   protected WispfolkConfig getWispfolkConfig() {
      return (WispfolkConfig)ConfigRegistry.getConfig(WispfolkConfig.class);
   }

   public static boolean usesLabyrinthStructureSpawn(ManasRaceInstance instance, LivingEntity owner) {
      return instance.getRace() instanceof WispfolkRace && TensuraDimensions.LABYRINTH.equals(instance.getRespawnDimension(owner).getFirst());
   }

   public static void teleportToLabyrinthStructure(ServerPlayer player) {
      SpawnPointHelper.teleportToAcrossDimensions(player, TensuraDimensions.LABYRINTH, 16.0, 1.0, 10.5, player.getXRot(), player.getYRot());
      player.fallDistance = 0.0F;
   }

   protected Map<EvolutionRequirement, Float> epRequirement(double requirement) {
      return requirement <= 0.0 ? Map.of() : Map.of(new EPRequirement(requirement), 100.0F);
   }

   protected Map<EvolutionRequirement, Float> epAndAbilityRequirement(double requirement, ManasSkill skill, boolean mastered) {
      if (requirement <= 0.0 && skill == null) {
         return Map.of();
      } else if (skill == null) {
         return this.epRequirement(requirement);
      } else {
         return requirement <= 0.0
            ? Map.of(new AbilityRequirement(skill, mastered), 100.0F)
            : Map.of(new EPRequirement(requirement), 50.0F, new AbilityRequirement(skill, mastered), 50.0F);
      }
   }
}
