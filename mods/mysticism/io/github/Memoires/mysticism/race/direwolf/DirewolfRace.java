package io.github.Memoires.mysticism.race.direwolf;

import io.github.Memoires.mysticism.config.race.direwolf.DirewolfConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.entity.monster.DirewolfEntity;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DirewolfRace extends DefaultRace {
   protected static final DirewolfConfig.Direwolf CONFIG = ((DirewolfConfig)ConfigRegistry.getConfig(DirewolfConfig.class)).Direwolf;

   public DirewolfRace() {
      super(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
      this.addAttributeModifier(Attributes.STEP_HEIGHT, DEFAULT_RACE_ID, CONFIG.stepHeight, Operation.ADD_VALUE);
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.BLACK_FANG.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.MAGIC_SPIRIT_WOLF.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.BLACK_FANG.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.BLACK_FANG.get());
      list.add((ManasRace)MysticismRaces.BLUE_FANG.get());
      list.add((ManasRace)MysticismRaces.BROWN_FANG.get());
      list.add((ManasRace)MysticismRaces.DARKNESS_FANG.get());
      list.add((ManasRace)MysticismRaces.FROST_WOLF.get());
      list.add((ManasRace)MysticismRaces.GREEN_FANG.get());
      list.add((ManasRace)MysticismRaces.GUITAR_WOLF.get());
      list.add((ManasRace)MysticismRaces.LIGHT_FANG.get());
      list.add((ManasRace)MysticismRaces.PURPLE_FANG.get());
      list.add((ManasRace)MysticismRaces.RED_FANG.get());
      list.add((ManasRace)MysticismRaces.SCORCH_WOLF.get());
      list.add((ManasRace)MysticismRaces.STAR_WOLF.get());
      return list;
   }

   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   public boolean canActivateAbility(ManasRaceInstance instance, LivingEntity entity) {
      return entity.onGround();
   }

   public void onActivateAbility(ManasRaceInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer serverPlayer) {
         if (!isInPlayerStack(serverPlayer)) {
            if (ObjectSelectionHelper.getTargetingEntity(serverPlayer, 5.0, false) instanceof ServerPlayer targetSp) {
               if (targetSp != serverPlayer) {
                  if (!chainContains(serverPlayer, targetSp)) {
                     int depth = playerStackDepth(serverPlayer);
                     if (depth < 2) {
                        if (targetSp.getFirstPassenger() == null) {
                           ServerPlayer top = topMostServerPlayer(serverPlayer);
                           if (top != null) {
                              if (!targetSp.isPassenger()) {
                                 targetSp.startRiding(top);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isInPlayerStack(ServerPlayer player) {
      return player.getVehicle() instanceof ServerPlayer ? true : player.getFirstPassenger() instanceof ServerPlayer;
   }

   private static int playerStackDepth(ServerPlayer base) {
      int depth = 0;
      Entity cur = base;

      while (cur.getFirstPassenger() instanceof ServerPlayer next) {
         depth++;
         cur = next;
      }

      return depth;
   }

   @NotNull
   private static ServerPlayer topMostServerPlayer(ServerPlayer base) {
      ServerPlayer cur = base;

      while (cur.getFirstPassenger() instanceof ServerPlayer next) {
         cur = next;
      }

      return cur;
   }

   private static boolean chainContains(ServerPlayer base, ServerPlayer target) {
      if (base == target) {
         return true;
      }

      for (Entity cur = base; cur.getFirstPassenger() != null; cur = cur.getFirstPassenger()) {
         if (cur.getFirstPassenger() == target) {
            return true;
         }
      }

      return false;
   }

   public boolean onBeingTargeted(ManasRaceInstance instance, Changeable<LivingEntity> target, LivingEntity owner) {
      return !(owner instanceof DirewolfEntity);
   }
}
