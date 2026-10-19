package com.shin.tensura_kumodesu.races.poison;

import com.shin.tensura_kumodesu.config.race.TaratectConfig;
import com.shin.tensura_kumodesu.races.other.SmallLesserTaratectRace;
import com.shin.tensura_kumodesu.registry.attributes.KumodesuAttributes;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class LesserTaratectRace extends SmallLesserTaratectRace {
   public static final ResourceLocation LesserTaratect = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "lesser_taratect");
   protected static final TaratectConfig.LesserTaratect CONFIG = ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).LesserTaratect;

   public LesserTaratectRace() {
      this.applyDefaultAttributeModifiers();
      this.addAttributeModifier(TensuraAttributes.WIDTH_MULTIPLIER, DEFAULT_RACE_ID, this.getCustomWidth() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(TensuraAttributes.HEIGHT_MULTIPLIER, DEFAULT_RACE_ID, this.getCustomHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.EYE_HEIGHT, DEFAULT_RACE_ID, this.getCustomEyeHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.SHADOW_SIZE, DEFAULT_RACE_ID, this.getCustomShadow() - 1.0, Operation.ADD_VALUE);
   }

   public double getCustomWidth() {
      return CONFIG.getWidth();
   }

   public double getCustomHeight() {
      return CONFIG.getHeight();
   }

   public double getCustomShadow() {
      return CONFIG.getShadowSize();
   }

   public double getCustomEyeHeight() {
      return CONFIG.getEyeHeight();
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(CONFIG.epRequirement), 100.0F);
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)KumodesuRaces.LESSER_POISON_TARATECT.get();
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)KumodesuRaces.POISON_TARATECT.get();
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)KumodesuRaces.LESSER_POISON_TARATECT.get());
      return list;
   }

   @Override
   public Alignment getAlignment() {
      return Alignment.DEFAULT;
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }
}
