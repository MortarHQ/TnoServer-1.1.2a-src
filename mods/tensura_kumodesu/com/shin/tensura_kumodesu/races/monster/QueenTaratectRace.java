package com.shin.tensura_kumodesu.races.monster;

import com.shin.tensura_kumodesu.config.race.TaratectConfig;
import com.shin.tensura_kumodesu.registry.attributes.KumodesuAttributes;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import com.shin.tensura_kumodesu.registry.skill.KumoExtraSkills;
import com.shin.tensura_kumodesu.registry.skill.KumoIntrinsicSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class QueenTaratectRace extends ArchTaratectRace {
   public static final ResourceLocation QueenTaratect = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "queen_taratect");
   protected static final TaratectConfig.QueenTaratect CONFIG = ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).QueenTaratect;

   public QueenTaratectRace() {
      this.applyDefaultAttributeModifiers();
      this.addAttributeModifier(TensuraAttributes.WIDTH_MULTIPLIER, DEFAULT_RACE_ID, this.getCustomWidth() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(TensuraAttributes.HEIGHT_MULTIPLIER, DEFAULT_RACE_ID, this.getCustomHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.EYE_HEIGHT, DEFAULT_RACE_ID, this.getCustomEyeHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.SHADOW_SIZE, DEFAULT_RACE_ID, this.getCustomShadow() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, DEFAULT_RACE_ID, this.getEntityRange() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, DEFAULT_RACE_ID, this.getBlockRange() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.STEP_HEIGHT, DEFAULT_RACE_ID, this.getStepHeight() - 1.0, Operation.ADD_VALUE);
   }

   @Override
   public double getModelOffset() {
      return CONFIG.getYOffset();
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
   public double getEntityRange() {
      return CONFIG.getEntityRange();
   }

   @Override
   public double getBlockRange() {
      return CONFIG.getBlockRange();
   }

   @Override
   public double getStepHeight() {
      return CONFIG.getStepHeight();
   }

   @Override
   public double getCustomEyeHeight() {
      return CONFIG.getEyeHeight();
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(CONFIG.epRequirement), 100.0F);
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
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)KumodesuRaces.ARCH_TARATECT.get());
      list.add((ManasRace)KumodesuRaces.EDE_SAINE.get());
      return list;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of();
   }

   @Override
   public Alignment getAlignment() {
      return Alignment.HOLY;
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)IntrinsicSkills.DIVINE_KI_RELEASE.get());
      list.add((ManasSkill)ExtraSkills.MANA_MANIPULATION.get());
      list.add((ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get());
      list.add((ManasSkill)KumoIntrinsicSkills.EGGLAYING.get());
      list.add((ManasSkill)KumoExtraSkills.DEADLYPOISON.get());
      return list;
   }
}
