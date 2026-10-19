package com.shin.tensura_kumodesu.races.other;

import com.shin.tensura_kumodesu.config.race.TaratectConfig;
import com.shin.tensura_kumodesu.registry.attributes.KumodesuAttributes;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import com.shin.tensura_kumodesu.registry.skill.KumoIntrinsicSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class SmallLesserTaratectRace extends DefaultRace {
   public SmallLesserTaratectRace() {
      super(Difficulty.EXTREME);
      this.applyDefaultAttributeModifiers();
      TaratectConfig.SmallLesserTaratect config = ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallLesserTaratect;
      this.addAttributeModifier(TensuraAttributes.WIDTH_MULTIPLIER, DEFAULT_RACE_ID, config.getWidth() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(TensuraAttributes.HEIGHT_MULTIPLIER, DEFAULT_RACE_ID, config.getHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.EYE_HEIGHT, DEFAULT_RACE_ID, config.getEyeHeight() - 1.0, Operation.ADD_VALUE);
      this.addAttributeModifier(KumodesuAttributes.SHADOW_SIZE, DEFAULT_RACE_ID, config.getShadowSize() - 1.0, Operation.ADD_VALUE);
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)KumodesuRaces.SMALL_TARATECT.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)KumodesuRaces.ARCH_TARATECT.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)KumodesuRaces.SMALL_TARATECT.get());
      list.add((ManasRace)KumodesuRaces.LESSER_TARATECT.get());
      return list;
   }

   public Alignment getAlignment() {
      return Alignment.DEFAULT;
   }

   public Default getDefaultConfig() {
      return ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallLesserTaratect;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)ExtraSkills.STICKY_STEEL_THREAD.get());
      list.add((ManasSkill)CommonSkills.POISON.get());
      list.add((ManasSkill)KumoIntrinsicSkills.SPIDERMOVEMENT.get());
      return list;
   }

   public void onTick(ManasRaceInstance instance, LivingEntity living) {
      super.onTick(instance, living);
      living.refreshDimensions();
   }

   public boolean onHurt(ManasRaceInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      if (source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.ON_FIRE) || TensuraDamageHelper.isFireDamage(source)) {
         amount.set((Float)amount.get() * 2.0F);
      }

      return true;
   }
}
