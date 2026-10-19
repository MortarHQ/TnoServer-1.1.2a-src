package twilightforest.config;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class TFClientConfig {
   final BooleanValue silentCicadas;
   final BooleanValue silentCicadasOnHead;
   final BooleanValue firstPersonEffects;
   final BooleanValue rotateTrophyHeadsGui;
   final BooleanValue disableOptifineNagScreen;
   final BooleanValue disableLockedBiomeToasts;
   final BooleanValue showQuestRamCrosshairIndicator;
   final BooleanValue showFortificationShieldIndicator;
   final BooleanValue showFortificationShieldIndicatorInCreative;
   final IntValue cloudBlockPrecipitationDistance;
   final ConfigValue<List<? extends String>> giantSkinUUIDs;
   final ConfigValue<List<? extends String>> auroraBiomes;
   final BooleanValue prettifyOreMeterGui;
   final BooleanValue spawnCharmAnimationAsTotem;
   final BooleanValue manualTravellersWingsGradualGlide;
   final BooleanValue firstPersonGloveOverlay;
   final TFClientConfig.ItemDisplay ITEM_DISPLAY = new TFClientConfig.ItemDisplay();

   public TFClientConfig(Builder builder) {
      this.silentCicadas = builder.translation("config.twilightforest.silent_cicadas")
         .comment("Makes Cicadas silent for those having sound library problems or finding them annoying.")
         .define("silentCicadas", false);
      this.silentCicadasOnHead = builder.translation("config.twilightforest.silent_cicadas_on_head")
         .comment("Makes Cicadas silent when one is on your head. If the above option is set to true, this won't have any effect.")
         .define("silentCicadasOnHead", false);
      this.firstPersonEffects = builder.translation("config.twilightforest.screen_shake")
         .comment("Controls whether the screen shakes when a Magic Bean is in the process of growing.")
         .define("screenShakingEffect", true);
      this.rotateTrophyHeadsGui = builder.translation("config.twilightforest.animate_trophies")
         .comment("Rotate Trophy heads on item model. Has no performance impact at all. For those who don't like fun.")
         .define("rotateTrophyHeadsGui", true);
      this.disableOptifineNagScreen = builder.translation("config.twilightforest.optifine")
         .comment("Disable the nag screen when OptiFine is installed.")
         .define("disableOptifineNagScreen", false);
      this.disableLockedBiomeToasts = builder.translation("config.twilightforest.locked_toasts")
         .comment("Disables the toasts that appear when entering a locked biome. Not recommended if you're unfamiliar with progression.")
         .define("disableLockedBiomeToasts", false);
      this.showQuestRamCrosshairIndicator = builder.translation("config.twilightforest.ram_indicator")
         .comment(
            "Renders a check mark or X above your crosshair while holding wool when hovering over the Questing Ram depending on if that color of wool has been fed to it already."
         )
         .define("questRamWoolIndicator", true);
      this.showFortificationShieldIndicator = builder.translation("config.twilightforest.shield_indicator")
         .comment(
            "Renders how many fortification shields are currently active on your player above your armor bar.\n\"Turn this off if other mods render over/under it."
         )
         .define("fortificationShieldIndicator", true);
      this.showFortificationShieldIndicatorInCreative = builder.translation("config.twilightforest.shield_indicator_creative")
         .comment("Enables the fortification shield indicator in creative for debugging.")
         .define("fortificationShieldIndicatorInCreative", false);
      this.cloudBlockPrecipitationDistance = builder.translation("config.twilightforest.cloud_precipitation")
         .comment(
            "Renders precipitation underneath cloud blocks. -1 sets it to be synced with the common config.\nSet this to a lower number if experiencing poor performance, set to 0 to turn it off"
         )
         .defineInRange("cloudBlockPrecipitationDistance", -1, -1, Integer.MAX_VALUE);
      this.giantSkinUUIDs = builder.translation("config.twilightforest.giant_skin_uuid_list")
         .comment("List of player UUIDs whose skins the Giants should use. Leave the list empty to use the skin of the player viewing them.")
         .defineListAllowEmpty("giantSkinUUIDs", new ArrayList(), () -> "", s -> s instanceof String string && string.split("-").length == 5);
      this.auroraBiomes = builder.translation("config.twilightforest.aurora_biomes")
         .comment("Defines which biomes the aurora shader effect will appear in. Leave the list empty to disable the effect.")
         .defineListAllowEmpty(
            "auroraBiomes", List.of("twilightforest:glacier"), () -> "", s -> s instanceof String string && ResourceLocation.tryParse(string) != null
         );
      this.prettifyOreMeterGui = builder.translation("config.twilightforest.prettify_ore_meter_gui")
         .comment("Lines up the dashes and percentages in the Ore Meter GUI.")
         .define("prettifyOreMeterGui", true);
      this.spawnCharmAnimationAsTotem = builder.translation("config.twilightforest.totem_charm_animation")
         .comment("Makes Charms when triggered display like the Totem of Undying instead of our own effects.")
         .define("totemCharmAnimation", false);
      this.manualTravellersWingsGradualGlide = builder.translation("config.twilightforest.manual_travellers_wings_gradual_glide")
         .comment(
            "When this option is off, slow falling is the default. Holding the sneak key makes you fall at normal speed. When this option is on, normal falling is the default. Holding the sneak key activates slow falling."
         )
         .define("travellersWingsGradualGlide", true);
      this.firstPersonGloveOverlay = builder.translation("config.twilightforest.first_person_glove_overlay")
         .comment("Allows Traveller's Gloves to render on your hand when in 1st person view.")
         .define("firstPersonGloveOverlay", true);
      builder.comment("Controls where various elements render when using the Item Display Modifier on Traveller's Gear.")
         .translation("config.twilightforest.item_display")
         .push("Item Display Modifier Settings");
      this.ITEM_DISPLAY.screenOffsetX = builder.translation("config.twilightforest.screen_offset_x")
         .comment("Defines the starting X offset for all display modifiers on the screen.")
         .defineInRange("screenOffsetX", 4, Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.ITEM_DISPLAY.screenOffsetY = builder.translation("config.twilightforest.screen_offset_y")
         .comment("Defines the starting Y offset for all display modifiers on the screen.")
         .defineInRange("screenOffsetY", 4, Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.ITEM_DISPLAY.screenScale = builder.translation("config.twilightforest.screen_scale")
         .comment("Defines the scale of all display modifiers on the screen.")
         .defineInRange("screenScale", 1.0, 0.1, 10.0);
      this.ITEM_DISPLAY.twentyFourHourFormat = builder.translation("config.twilightforest.twenty_four_hour_format")
         .comment("If ON, the clock upgrade displays time in 24-hour format instead of 12-hour format.")
         .define("twentyFourHourFormat", TFConfig.use24HourTimeDefault());
      builder.pop();
   }

   static class ItemDisplay {
      IntValue screenOffsetX;
      IntValue screenOffsetY;
      DoubleValue screenScale;
      BooleanValue twentyFourHourFormat;
   }
}
