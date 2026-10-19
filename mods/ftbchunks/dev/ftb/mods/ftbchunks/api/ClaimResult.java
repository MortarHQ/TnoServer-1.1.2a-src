package dev.ftb.mods.ftbchunks.api;

import dev.ftb.mods.ftblibrary.config.NameMap;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface ClaimResult {
   String getResultId();

   default boolean isSuccess() {
      return false;
   }

   default MutableComponent getMessage() {
      return Component.translatable(this.getResultId());
   }

   static ClaimResult customProblem(String translationKey) {
      return new ClaimResult.CustomProblem(translationKey);
   }

   static ClaimResult success() {
      return null;
   }

   class CustomProblem implements ClaimResult {
      private final String translationKey;

      private CustomProblem(String translationKey) {
         this.translationKey = translationKey;
      }

      @Override
      public String getResultId() {
         return this.translationKey;
      }
   }

   enum StandardProblem implements ClaimResult {
      NOT_OWNER("not_owner"),
      NOT_ENOUGH_POWER("not_enough_power"),
      ALREADY_CLAIMED("already_claimed"),
      DIMENSION_FORBIDDEN("dimension_forbidden"),
      NOT_CLAIMED("not_claimed"),
      ALREADY_LOADED("already_loaded"),
      NOT_LOADED("not_loaded");

      public static final NameMap<ClaimResult.StandardProblem> NAME_MAP = NameMap.of(NOT_OWNER, values()).baseNameKey("ftbchunks.standard_problem").create();
      private final String resultId;

      StandardProblem(String resultId) {
         this.resultId = "ftbchunks.claim_result." + resultId;
      }

      public static Optional<ClaimResult.StandardProblem> forName(String name) {
         return Optional.ofNullable((ClaimResult.StandardProblem)NAME_MAP.get(name));
      }

      @Override
      public String getResultId() {
         return this.resultId;
      }
   }
}
