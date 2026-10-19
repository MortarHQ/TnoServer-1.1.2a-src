package dev.ftb.mods.ftbxmodcompat.config;

import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import dev.ftb.mods.ftblibrary.snbt.config.EnumValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftbxmodcompat.FTBXModCompat;
import java.util.function.BooleanSupplier;

public interface FTBXModConfig {
   SNBTConfig CONFIG = SNBTConfig.create("ftbxmodcompat");
   EnumValue<FTBXModConfig.StageSelector> STAGE_SELECTOR = (EnumValue<FTBXModConfig.StageSelector>)CONFIG.addEnum(
         "stage_selector", NameMap.of(FTBXModConfig.StageSelector.DEFAULT, FTBXModConfig.StageSelector.values()).create()
      )
      .comment(
         new String[]{
            "Select the game stages implementation to use", "DEFAULT: use KubeJS, Game Stages, vanilla in preference order, depending on mod availability"
         }
      );
   EnumValue<FTBXModConfig.PermSelector> PERMISSION_SELECTOR = (EnumValue<FTBXModConfig.PermSelector>)CONFIG.addEnum(
         "permission_selector", NameMap.of(FTBXModConfig.PermSelector.DEFAULT, FTBXModConfig.PermSelector.values()).create()
      )
      .comment(
         new String[]{
            "Select the permissions implementation to use", "DEFAULT: use FTB Ranks then Luckperms in preference order, depending on mod availability"
         }
      );
   EnumValue<FTBXModConfig.CurrencySelector> CURRENCY_SELECTOR = (EnumValue<FTBXModConfig.CurrencySelector>)CONFIG.addEnum(
         "currency_selector", NameMap.of(FTBXModConfig.CurrencySelector.DEFAULT, FTBXModConfig.CurrencySelector.values()).create()
      )
      .comment(
         new String[]{
            "Select the currency implementation to use", "Note: currently Neoforge-only", "DEFAULT: use SG Economy if installed, otherwise no currency plugin"
         }
      );
   BooleanValue ONLY_SHOW_KNOWN_WAYSTONES = (BooleanValue)CONFIG.addBoolean("only_show_known_waystones", true)
      .comment(new String[]{"Only show waystones that have been discovered"});

   enum CurrencySelector {
      DEFAULT(() -> true),
      SG_ECONOMY_API(() -> FTBXModCompat.isSGEconomyLoaded);

      private final BooleanSupplier usable;

      CurrencySelector(BooleanSupplier usable) {
         this.usable = usable;
      }

      public boolean isUsable() {
         return this.usable.getAsBoolean();
      }
   }

   enum PermSelector {
      DEFAULT(() -> true),
      FTB_RANKS(() -> FTBXModCompat.isFTBRanksLoaded),
      LUCKPERMS(() -> FTBXModCompat.isLuckPermsLoaded);

      private final BooleanSupplier usable;

      PermSelector(BooleanSupplier usable) {
         this.usable = usable;
      }

      public boolean isUsable() {
         return this.usable.getAsBoolean();
      }
   }

   enum StageSelector {
      DEFAULT(() -> true),
      VANILLA(() -> true),
      KUBEJS(() -> FTBXModCompat.isKubeJSLoaded),
      GAMESTAGES(() -> FTBXModCompat.isGameStagesLoaded);

      private final BooleanSupplier usable;

      StageSelector(BooleanSupplier usable) {
         this.usable = usable;
      }

      public boolean isUsable() {
         return this.usable.getAsBoolean();
      }
   }
}
