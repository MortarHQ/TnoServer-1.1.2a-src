package com.shin.tensura_kumodesu.races.spec;

import com.shin.tensura_kumodesu.config.race.TaratectConfig;
import com.shin.tensura_kumodesu.registry.attributes.KumodesuAttributes;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class ZanaHorowaRace extends ZoaEleRace {
   public static final ResourceLocation ZanaHorowa = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "zana_horowa");
   protected static final TaratectConfig.ZanaHorowa CONFIG = ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).ZanaHorowa;

   public ZanaHorowaRace() {
      this.applyDefaultAttributeModifiers();
      this.addAttributeModifier(TensuraAttributes.WIDTH_MULTIPLIER, DEFAULT_RACE_ID, this.getCustomWidth() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(TensuraAttributes.HEIGHT_MULTIPLIER, DEFAULT_RACE_ID, this.getCustomHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.EYE_HEIGHT, DEFAULT_RACE_ID, this.getCustomEyeHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.SHADOW_SIZE, DEFAULT_RACE_ID, this.getCustomShadow() - 1.0, Operation.ADD_VALUE);
   }

   @Override
   public double getCustomWidth() {
      return CONFIG.getWidth();
   }

   @Override
   public double getCustomHeight() {
      return CONFIG.getHeight();
   }

   @Override
   public double getCustomShadow() {
      return CONFIG.getShadowSize();
   }

   @Override
   public double getCustomEyeHeight() {
      return CONFIG.getEyeHeight();
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      final List<ManasSkill> sins = List.of(
         Objects.requireNonNull((ManasSkill)SkillAPI.getSkillRegistry().get(UniqueSkills.PRIDE.getId())),
         Objects.requireNonNull((ManasSkill)SkillAPI.getSkillRegistry().get(UniqueSkills.GREED.getId())),
         Objects.requireNonNull((ManasSkill)SkillAPI.getSkillRegistry().get(UniqueSkills.ENVY.getId())),
         Objects.requireNonNull((ManasSkill)SkillAPI.getSkillRegistry().get(UniqueSkills.LUST.getId())),
         Objects.requireNonNull((ManasSkill)SkillAPI.getSkillRegistry().get(UniqueSkills.WRATH.getId())),
         Objects.requireNonNull((ManasSkill)SkillAPI.getSkillRegistry().get(UniqueSkills.SLOTH.getId())),
         Objects.requireNonNull((ManasSkill)SkillAPI.getSkillRegistry().get(UniqueSkills.GLUTTONY.getId()))
      );
      EvolutionRequirement sinRequirement = new EvolutionRequirement() {
         public float getProgress(ManasRaceInstance instance, LivingEntity entityx) {
            return sins.stream().anyMatch(skill -> SkillUtils.hasSkill(entity, skill)) ? 1.0F : 0.0F;
         }

         public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entityx) {
            return Component.translatable(
               "tensura.evolution_menu.acquire_requirement", new Object[]{Component.translatable("tensura_kumodesu.ui.any_sin_requirement")}
            );
         }
      };
      EvolutionRequirement epRequirement = new EPRequirement(CONFIG.epRequirement);
      return Map.of(epRequirement, 50.0F, sinRequirement, 50.0F);
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)KumodesuRaces.ZOA_ELE.get());
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)KumodesuRaces.ARACHNE.get());
      return list;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)ExtraSkills.INFINITE_REGENERATION.get());
      return list;
   }

   @Override
   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }
}
