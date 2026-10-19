package lain.mods.cos.impl;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class ModConfigs {
   public static BooleanValue CosArmorGuiButton_Hidden;
   public static IntValue CosArmorGuiButton_Left;
   public static IntValue CosArmorGuiButton_Top;
   public static BooleanValue CosArmorToggleButton_Hidden;
   public static IntValue CosArmorToggleButton_Left;
   public static IntValue CosArmorToggleButton_Top;
   public static BooleanValue CosArmorCreativeGuiButton_Hidden;
   public static IntValue CosArmorCreativeGuiButton_Left;
   public static IntValue CosArmorCreativeGuiButton_Top;
   public static BooleanValue CosArmorKeepThroughDeath;
   public static BooleanValue CosArmorDisableRecipeBook;
   public static BooleanValue CosArmorDisableCosHatCommand;

   public static void registerConfigs() {
      ModLoadingContext.get()
         .getActiveContainer()
         .registerConfig(
            Type.CLIENT,
            (new Builder() {
                  {
                     this.comment("These settings only affects client").push("Client");
                     ModConfigs.CosArmorGuiButton_Hidden = this.comment("Whether or not to hide the button for opening CosmeticArmorInventory")
                        .translation("cos.config.CosArmorGuiButton_Hidden")
                        .define("CosArmorGuiButton_Hidden", false);
                     ModConfigs.CosArmorGuiButton_Left = this.comment("The horizontal pixel distance from the origin point of player inventory gui")
                        .translation("cos.config.CosArmorGuiButton_Left")
                        .defineInRange("CosArmorGuiButton_Left", 65, Integer.MIN_VALUE, Integer.MAX_VALUE);
                     ModConfigs.CosArmorGuiButton_Top = this.comment("The vertical pixel distance from the origin point of player inventoy gui")
                        .translation("cos.config.CosArmorGuiButton_Top")
                        .defineInRange("CosArmorGuiButton_Top", 67, Integer.MIN_VALUE, Integer.MAX_VALUE);
                     ModConfigs.CosArmorToggleButton_Hidden = this.comment("Whether or not to hide the button for toggling the mod temporarily on client side")
                        .translation("cos.config.CosArmorToggleButton_Hidden")
                        .define("CosArmorToggleButton_Hidden", false);
                     ModConfigs.CosArmorToggleButton_Left = this.comment("The horizontal pixel distance from the origin point of player inventory gui")
                        .translation("cos.config.CosArmorToggleButton_Left")
                        .defineInRange("CosArmorToggleButton_Left", 59, Integer.MIN_VALUE, Integer.MAX_VALUE);
                     ModConfigs.CosArmorToggleButton_Top = this.comment("The vertical pixel distance from the origin point of player inventory gui")
                        .translation("cos.config.CosArmorToggleButton_Top")
                        .defineInRange("CosArmorToggleButton_Top", 72, Integer.MIN_VALUE, Integer.MAX_VALUE);
                     ModConfigs.CosArmorCreativeGuiButton_Hidden = this.comment(
                           "Whether or not to hide the button for opening CosmeticArmorInventory in CreativeInventory"
                        )
                        .translation("cos.config.CosArmorCreativeGuiButton_Hidden")
                        .define("CosArmorCreativeGuiButton_Hidden", false);
                     ModConfigs.CosArmorCreativeGuiButton_Left = this.comment("The horizontal pixel distance from the origin point of creative inventory gui")
                        .translation("cos.config.CosArmorCreativeGuiButton_Left")
                        .defineInRange("CosArmorCreativeGuiButton_Left", 95, Integer.MIN_VALUE, Integer.MAX_VALUE);
                     ModConfigs.CosArmorCreativeGuiButton_Top = this.comment("The vertical pixel distance from the origin point of creative inventoy gui")
                        .translation("cos.config.CosArmorCreativeGuiButton_Top")
                        .defineInRange("CosArmorCreativeGuiButton_Top", 38, Integer.MIN_VALUE, Integer.MAX_VALUE);
                     this.pop();
                  }
               })
               .build()
         );
      ModLoadingContext.get()
         .getActiveContainer()
         .registerConfig(
            Type.COMMON,
            (new Builder() {
                  {
                     this.comment("These settings affects both server and client").push("Common");
                     ModConfigs.CosArmorKeepThroughDeath = this.comment("Whether or not to keep items in cosmetic armor slots in the event of player death")
                        .translation("cos.config.CosArmorKeepThroughDeath")
                        .define("CosArmorKeepThroughDeath", false);
                     ModConfigs.CosArmorDisableRecipeBook = this.comment("Whether or not to disable the RecipeBook in the CosmeticArmorInventory")
                        .translation("cos.config.CosArmorDisableRecipeBook")
                        .define("CosArmorDisableRecipeBook", false);
                     ModConfigs.CosArmorDisableCosHatCommand = this.comment("Whether or not to disable the coshat command")
                        .translation("cos.config.CosArmorDisableCosHatCommand")
                        .define("CosArmorDisableCosHatCommand", false);
                     this.pop();
                  }
               })
               .build()
         );
   }
}
