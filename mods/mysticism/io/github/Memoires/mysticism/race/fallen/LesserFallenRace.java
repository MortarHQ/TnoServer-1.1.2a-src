package io.github.Memoires.mysticism.race.fallen;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.config.race.AngelConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LesserFallenRace extends DefaultRace {
   protected static final AngelConfig.LesserFallen CONFIG = ((AngelConfig)ConfigRegistry.getConfig(AngelConfig.class)).LesserFallen;

   public LesserFallenRace() {
      super(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.GREATER_FALLEN.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.FALLEN_LORD.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.GREATER_FALLEN.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.GREATER_FALLEN.get());
      return list;
   }

   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   public Pair<ResourceKey<Level>, BlockState> getRespawnDimension(ManasRaceInstance instance, LivingEntity owner) {
      return Pair.of(Level.OVERWORLD, Blocks.STONE.defaultBlockState());
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   public void onActivateAbility(ManasRaceInstance instance, LivingEntity entity) {
      if (entity instanceof Player player && !player.isSpectator() && !player.isCreative()) {
         Level level = player.level();
         if (player.getAbilities().mayfly) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
         } else {
            player.getAbilities().mayfly = true;
            player.getAbilities().flying = true;
         }

         player.onUpdateAbilities();
         level.playSound(null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      }
   }

   public void triggerEvolutionRewards(ManasRaceInstance instance, LivingEntity entity) {
      AttributeInstance aura = entity.getAttribute(TensuraAttributes.MAX_AURA);
      if (aura != null && aura.getBaseValue() < this.getMinBaseAura()) {
         aura.setBaseValue(this.getMinBaseAura());
      }

      AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAX_MAGICULE);
      if (magicule != null && magicule.getBaseValue() < this.getMinBaseMagicule()) {
         magicule.setBaseValue(this.getMinBaseMagicule());
      }

      List<TensuraSkill> list = new ArrayList<>();
      list.add((TensuraSkill)ResistanceSkills.HOLY_ATTACK_RESISTANCE.get());
      list.add((TensuraSkill)IntrinsicSkills.POSSESSION.get());
      list.add((TensuraSkill)IntrinsicSkills.LIGHT_TRANSFORM.get());
      list.add((TensuraSkill)ExtraSkills.MAGIC_LIGHT_TRANSFORM.get());
      list.add((TensuraSkill)ExtraMysticismSkills.LIGHT_MANIPULATION.get());
      list.add((TensuraSkill)ExtraMysticismSkills.LIGHT_DOMINATION.get());
      list.forEach(skill -> SkillAPI.getSkillsFrom(entity).forgetSkill(skill));
   }
}
