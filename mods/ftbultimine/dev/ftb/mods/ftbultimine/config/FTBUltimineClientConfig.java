package dev.ftb.mods.ftbultimine.config;

import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import dev.ftb.mods.ftblibrary.snbt.config.DoubleValue;
import dev.ftb.mods.ftblibrary.snbt.config.EnumValue;
import dev.ftb.mods.ftblibrary.snbt.config.IntValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.util.PanelPositioning;

public interface FTBUltimineClientConfig {
   String KEY = "ftbultimine-client";
   SNBTConfig CONFIG = (SNBTConfig)SNBTConfig.create("ftbultimine-client")
      .comment(
         new String[]{
            "Client-specific configuration for FTB Ultimine",
            "Modpack defaults should be defined in <instance>/config/ftbultimine-client.snbt",
            "  (may be overwritten on modpack update)",
            "Players may locally override this by copying into <instance>/local/ftbultimine-client.snbt",
            "  (will NOT be overwritten on modpack update)"
         }
      );
   SNBTConfig GENERAL = CONFIG.addGroup("general");
   BooleanValue REQUIRE_ULTIMINE_KEY_FOR_CYCLING = (BooleanValue)GENERAL.addBoolean("require_ultimine_key_for_cycling", true)
      .comment(new String[]{"Does the player need to be holding the Ultimine key to cycle through shapes with the keyboard?"});
   BooleanValue SHAPE_FEEDBACK_MESSAGE = (BooleanValue)GENERAL.addBoolean("shape_feedback_hotbar_message", false)
      .comment(new String[]{"When true, display the newly-selected shape as a hotbar message after an Ultimine shape change"});
   SNBTConfig RENDERING = CONFIG.addGroup("rendering");
   IntValue RENDER_OUTLINE = (IntValue)RENDERING.addInt("render_outline", 256)
      .range(0, Integer.MAX_VALUE)
      .comment(
         new String[]{
            "Maximum number of blocks the white outline should be rendered for", "Keep in mind this may get *very* laggy for large amounts of blocks!"
         }
      );
   IntValue PREVIEW_LINE_ALPHA = (IntValue)RENDERING.addInt("preview_line_alpha", 45, 0, 255)
      .comment(new String[]{"Alpha value (0-255) for dig preview lines which are 'inside' blocks"});
   SNBTConfig OVERLAY = CONFIG.addGroup("overlay");
   IntValue SHAPE_MENU_CONTEXT_LINES = (IntValue)OVERLAY.addInt("shape_menu_context_lines", 2)
      .range(1, 5)
      .comment(
         new String[]{
            "When displaying the shape selection menu by holding the Ultimine key",
            "and sneaking at the same time, the number of shape names to display",
            "above and below the selected shape"
         }
      );
   BooleanValue REQUIRE_SNEAK_FOR_MENU = (BooleanValue)OVERLAY.addBoolean("require_sneak_for_menu", true)
      .comment(new String[]{"When holding the Ultimine key, must the player also be sneaking to show the shapes menu?"});
   EnumValue<PanelPositioning> OVERLAY_POS = OVERLAY.addEnum("overlay_pos", PanelPositioning.NAME_MAP, PanelPositioning.TOP_LEFT);
   IntValue OVERLAY_INSET_X = OVERLAY.addInt("overlay_inset_x", 2);
   IntValue OVERLAY_INSET_Y = OVERLAY.addInt("overlay_inset_y", 2);
   DoubleValue OVERLAY_SCALE = OVERLAY.addDouble("overlay_scale", 1.0);
}
