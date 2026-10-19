package dev.ftb.mods.ftblibrary.config;

import dev.ftb.mods.ftblibrary.config.manager.ConfigManager;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import dev.ftb.mods.ftblibrary.snbt.config.EnumValue;
import dev.ftb.mods.ftblibrary.snbt.config.IntArrayValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.snbt.config.StringSidebarMapValue;
import java.util.HashMap;

public interface FTBLibraryClientConfig {
   String KEY = "ftblibrary-client";
   SNBTConfig CONFIG = SNBTConfig.create("ftblibrary-client").comment("Client-specific configuration for FTB Library");
   SNBTConfig TOOLTIPS = CONFIG.addGroup("tooltips");
   BooleanValue ITEM_MODNAME = TOOLTIPS.addBoolean("item_modname", false)
      .comment(
         "Add the name of the mod that items belong to in the item selection GUI.\nNote that several common mods also do this (modnametooltip,WTHIT,EMI...) so this is false by default"
      );
   BooleanValue FLUID_MODNAME = TOOLTIPS.addBoolean("fluid_modname", true).comment("Add the name of the mod that fluids belong to in the fluid selection GUI.");
   BooleanValue IMAGE_MODNAME = TOOLTIPS.addBoolean("image_modname", true).comment("Add the name of the mod that images belong to in the image selection GUI.");
   BooleanValue ENTITY_MODNAME = TOOLTIPS.addBoolean("entity_modname", true)
      .comment("Add the name of the mod that entities belong to in the entity face selection GUI.");
   SNBTConfig COLOR = CONFIG.addGroup("colorselector");
   IntArrayValue RECENT = COLOR.addIntArray("recents", new int[0]).comment("Colors recently selected in the color selector");
   SNBTConfig SIDEBAR = CONFIG.addGroup("sidebar");
   BooleanValue SIDEBAR_ENABLED = SIDEBAR.addBoolean("enabled", true).comment("Enable the sidebar");
   EnumValue<FTBLibraryClientConfig.SidebarPosition> SIDEBAR_POSITION = SIDEBAR.addEnum(
         "position", FTBLibraryClientConfig.SidebarPosition.NAME_MAP, FTBLibraryClientConfig.SidebarPosition.TOP_LEFT
      )
      .comment("Position of the sidebar");
   StringSidebarMapValue SIDEBAR_BUTTONS = SIDEBAR.add(new StringSidebarMapValue(SIDEBAR, "buttons", new HashMap<>()));

   static void save() {
      ConfigManager.getInstance().save("ftblibrary-client");
   }

   enum SidebarPosition {
      TOP_LEFT(false, false),
      TOP_RIGHT(false, true),
      BOTTOM_LEFT(true, false),
      BOTTOM_RIGHT(true, true);

      public static final NameMap<FTBLibraryClientConfig.SidebarPosition> NAME_MAP = NameMap.of(TOP_LEFT, values())
         .baseNameKey("ftblibrary.panel.position")
         .create();
      private final boolean isBottom;
      private final boolean isRight;

      SidebarPosition(boolean isBottom, boolean isRight) {
         this.isBottom = isBottom;
         this.isRight = isRight;
      }

      public boolean isBottom() {
         return this.isBottom;
      }

      public boolean isRight() {
         return this.isRight;
      }
   }
}
