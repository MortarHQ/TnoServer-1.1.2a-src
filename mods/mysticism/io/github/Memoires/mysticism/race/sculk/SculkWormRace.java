package io.github.Memoires.mysticism.race.sculk;

import io.github.Memoires.mysticism.config.race.SculkConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class SculkWormRace extends DefaultRace {
   protected static final SculkConfig.SculkWorm CONFIG = ((SculkConfig)ConfigRegistry.getConfig(SculkConfig.class)).SculkWorm;

   public SculkWormRace() {
      super(Difficulty.EXTREME);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.SOUL_SHRIEKER.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.SOUL_ABERRATION.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.SOUL_SHRIEKER.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.SOUL_SHRIEKER.get());
      list.add((ManasRace)MysticismRaces.MOLTEN_PERFORATOR.get());
      list.add((ManasRace)MysticismRaces.CHARGED_PERFORATOR.get());
      return list;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   public boolean canTick(ManasRaceInstance instance, LivingEntity entity) {
      return !entity.hasInfiniteMaterials() && !entity.isSpectator() ? entity.getAttributeValue(TensuraAttributes.PRESENCE_SENSE) <= 0.0 : false;
   }

   public void onTick(ManasRaceInstance instance, LivingEntity entity) {
      entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS), 40, 0, false, false, false));
      entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, false, false));
   }

   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }
}
