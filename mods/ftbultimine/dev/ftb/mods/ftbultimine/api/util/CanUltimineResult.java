package dev.ftb.mods.ftbultimine.api.util;

import org.jetbrains.annotations.ApiStatus.NonExtendable;

@FunctionalInterface
public interface CanUltimineResult {
   CanUltimineResult.Allow ALLOWED = () -> "ftbultimine.info.no_valid_block";
   CanUltimineResult NO_BLOCK_TARGETED = prevent("ftbultimine.info.no_block_targeted");
   CanUltimineResult NO_FOOD = prevent("ftbultimine.info.no_food");
   CanUltimineResult NO_TOOL = prevent("ftbultimine.info.no_tool");
   CanUltimineResult NO_PERMISSION = prevent("ftbultimine.info.no_permission");
   CanUltimineResult BLOCKED_TOOL = prevent("ftbultimine.info.denied_tool");
   CanUltimineResult ON_COOLDOWN = prevent("ftbultimine.info.cooldown");
   CanUltimineResult OTHER_RESTRICTION = prevent("ftbultimine.info.other_restriction");

   static CanUltimineResult prevent(String key) {
      return () -> key;
   }

   @NonExtendable
   default boolean isAllowed() {
      return false;
   }

   String getTranslationKey();

   @FunctionalInterface
   interface Allow extends CanUltimineResult {
      @Override
      default boolean isAllowed() {
         return true;
      }
   }
}
