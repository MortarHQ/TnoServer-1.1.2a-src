package com.shin.tensura_kumodesu.races.other;

import com.shin.tensura_kumodesu.config.race.TaratectConfig;
import com.shin.tensura_kumodesu.races.poison.PoisonTaratectRace;
import com.shin.tensura_kumodesu.registry.attributes.KumodesuAttributes;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import com.shin.tensura_kumodesu.registry.skill.KumoIntrinsicSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.spirit.ISpiritWielder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class OrthocadinahtRace extends PoisonTaratectRace {
   public static final ResourceLocation Orthocadinaht = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "orthocadinaht");
   protected static final TaratectConfig.Orthocadinaht CONFIG = ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).Orthocadinaht;

   public OrthocadinahtRace() {
      this.applyDefaultAttributeModifiers();
      this.addAttributeModifier(TensuraAttributes.WIDTH_MULTIPLIER, DEFAULT_RACE_ID, this.getCustomWidth() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(TensuraAttributes.HEIGHT_MULTIPLIER, DEFAULT_RACE_ID, this.getCustomHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.EYE_HEIGHT, DEFAULT_RACE_ID, this.getCustomEyeHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.SHADOW_SIZE, DEFAULT_RACE_ID, this.getCustomShadow() - 1.0, Operation.ADD_VALUE);
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
   public double getCustomEyeHeight() {
      return CONFIG.getEyeHeight();
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
      list.add((ManasRace)KumodesuRaces.ZOA_ELE.get());
      list.add((ManasRace)KumodesuRaces.POISON_TARATECT.get());
      return list;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of();
   }

   @Override
   public Alignment getAlignment() {
      return Alignment.CHAOS;
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)KumoIntrinsicSkills.DIVINEMAGICRELEASE.get());
      list.add((ManasSkill)ExtraSkills.MANA_MANIPULATION.get());
      list.add((ManasSkill)ExtraSkills.THOUGHT_ACCELERATION.get());
      list.add((ManasSkill)ExtraSkills.SAGE.get());
      list.add((ManasSkill)ExtraSkills.CHANT_ANNULMENT.get());
      return list;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      EvolutionRequirement spiritRequirement = new EvolutionRequirement() {
         public float getProgress(ManasRaceInstance instance, LivingEntity entityx) {
            if (entityx instanceof Player player) {
               ISpiritWielder spirit = TensuraStorages.getSpiritFrom(player);

               for (Element element : Element.values()) {
                  if (spirit.getSpiritLevel(element) == SpiritLevel.LORD) {
                     return 1.0F;
                  }
               }

               return 0.0F;
            } else {
               return 0.0F;
            }
         }

         public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entityx) {
            return Component.translatable(
               "tensura.evolution_menu.acquire_requirement", new Object[]{Component.translatable("tensura_kumodesu.ui.lord_requirement")}
            );
         }
      };
      return Map.of(new EPRequirement(CONFIG.epRequirement), 50.0F, spiritRequirement, 50.0F);
   }
}
