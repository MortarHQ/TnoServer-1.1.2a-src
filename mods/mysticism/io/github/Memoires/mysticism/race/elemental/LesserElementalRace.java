package io.github.Memoires.mysticism.race.elemental;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.config.MysticismGeneralConfig;
import io.github.Memoires.mysticism.config.race.ElementalConfig;
import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.element.ElementUtils;
import io.github.Memoires.mysticism.storage.element.IElement;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.spirit.ISpiritWielder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LesserElementalRace extends DefaultRace {
   protected static final ElementalConfig.LesserElemental CONFIG = ((ElementalConfig)ConfigRegistry.getConfig(ElementalConfig.class)).LesserElemental;
   protected static final MysticismGeneralConfig.General GENERAL = ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General;

   public LesserElementalRace() {
      super(Difficulty.HARD);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.MEDIUM_ELEMENTAL.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.ELEMENTAL_LORD.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.MEDIUM_ELEMENTAL.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.MEDIUM_ELEMENTAL.get());
      return list;
   }

   public Alignment getAlignment() {
      return Alignment.DEFAULT;
   }

   public double getElementalSpiritsChance(Element element, SpiritLevel level) {
      return -1.0;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)IntrinsicSkills.POSSESSION.get());
      return list;
   }

   public Pair<ResourceKey<Level>, BlockState> getRespawnDimension(ManasRaceInstance instance, LivingEntity owner) {
      return Pair.of(MysticismDimensions.ELEMENTAL_REALM, Blocks.STONE.defaultBlockState());
   }

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

   public void onRaceSet(ManasRaceInstance instance, LivingEntity living) {
      if (living instanceof Player player) {
         Element[] elements = Element.values();
         Element randomElement = elements[living.getRandom().nextInt(7)];
         IElement element = MysticismStorages.getElementFrom(living);
         element.setElement(randomElement);
         element.markDirty();
         ElementUtils.grantMagicAndResistance(player, randomElement);
         ISpiritWielder spiritStorage = TensuraStorages.getSpiritFrom(living);
         spiritStorage.setSpiritLevel(randomElement, SpiritLevel.LESSER);
         spiritStorage.markDirty();
      }
   }
}
