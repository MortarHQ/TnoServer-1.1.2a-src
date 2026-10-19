package com.github.wal_bos.moreskills.handler.arthur;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;

public final class ArthurActions {
   private static final List<ArthurModeHandler> MODES = List.of(
      new ArthurTrueExcaliburHandler(),
      new ArthurHolyOrderHandler(),
      new ArthurLightDominationHandler(),
      new ArthurHolyDisintegrationHandler(),
      new ArthurHolyAuraHandler(),
      new ArthurKingsBattleHandler(),
      new ArthurExcaliburPromisedVictoryHandler()
   );

   private ArthurActions() {
   }

   public static void init() {
   }

   public static int getModes(ManasSkillInstance instance) {
      return MODES.size();
   }

   public static int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? MODES.size() - 1 : mode - 1;
      } else {
         return mode >= MODES.size() - 1 ? 0 : mode + 1;
      }
   }

   public static String getModeId(ManasSkillInstance instance, int mode, String fallback) {
      ArthurModeHandler handler = get(mode);
      return handler == null ? fallback : handler.modeId();
   }

   public static double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      ArthurModeHandler handler = get(mode);
      return handler == null ? 0.0 : handler.magiculeCost(entity, instance);
   }

   public static void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      ArthurModeHandler handler = get(mode);
      if (handler != null) {
         handler.activate(instance, entity, keyNumber, mode);
      }
   }

   public static boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      ArthurModeHandler handler = get(mode);
      return handler != null && handler.onHeld(instance, entity, heldTicks, mode);
   }

   public static void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      ArthurModeHandler handler = get(mode);
      if (handler != null) {
         handler.onRelease(instance, entity, heldTicks, keyNumber, mode);
      }
   }

   public static boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      ArthurModeHandler handler = get(mode);
      return handler != null && handler.shouldTriggerReleaseOnHeldInterrupt(instance, entity, keyNumber, mode);
   }

   public static void tick(ManasSkillInstance instance, LivingEntity entity) {
      for (ArthurModeHandler handler : MODES) {
         handler.tick(instance, entity);
      }
   }

   public static void cleanup(LivingEntity entity) {
      for (ArthurModeHandler handler : MODES) {
         handler.cleanup(entity);
      }
   }

   private static ArthurModeHandler get(int mode) {
      return mode >= 0 && mode < MODES.size() ? MODES.get(mode) : null;
   }
}
