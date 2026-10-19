package xaero.map.common.config;

import net.minecraft.network.chat.Component;

public class WorldMapConfigConstants {
   public static final Component INGAME_TOOLTIP = Component.translatable("gui.xaero_wm_option_requires_ingame");
   public static final Component LEGACY_PLUGIN_TOOLTIP = Component.translatable("gui.xaero_world_map_redirect_legacy");
   public static final Component FAIRPLAY_TOOLTIP = Component.translatable("gui.xaero_world_map_config_redirect_fairplay");
   public static final Component EFFECT_TOOLTIP = Component.translatable("gui.xaero_world_map_config_redirect_effect");
   public static final Component MINIMAP_TOOLTIP = Component.translatable("gui.xaero_wm_option_requires_minimap");
   public static Component[] BLOCK_COLORS_NAMES = new Component[]{Component.translatable("gui.xaero_accurate"), Component.translatable("gui.xaero_vanilla")};
   public static Component[] TERRAIN_SLOPES_NAMES = new Component[]{
      Component.translatable("gui.xaero_off"),
      Component.translatable("gui.xaero_wm_slopes_legacy"),
      Component.translatable("gui.xaero_wm_slopes_default_3d"),
      Component.translatable("gui.xaero_wm_slopes_default_2d")
   };
   public static Component SEC = Component.translatable("gui.xaero_wm_unit_s");
   public static Component MILLISEC = Component.translatable("gui.xaero_wm_unit_ms");
   public static Component[] DEFAULT_CAVE_MODE_TYPE_NAMES = new Component[]{
      Component.translatable("gui.xaero_off"),
      Component.translatable("gui.xaero_wm_cave_mode_type_layered"),
      Component.translatable("gui.xaero_wm_cave_mode_type_full")
   };
   public static Component[] ARROW_COLOR_NAMES = new Component[]{
      Component.translatable("gui.xaero_wm_color_minimap"),
      Component.translatable("gui.xaero_wm_team_color"),
      Component.translatable("gui.xaero_wm_red"),
      Component.translatable("gui.xaero_wm_green"),
      Component.translatable("gui.xaero_wm_blue"),
      Component.translatable("gui.xaero_wm_yellow"),
      Component.translatable("gui.xaero_wm_purple"),
      Component.translatable("gui.xaero_wm_white"),
      Component.translatable("gui.xaero_wm_black"),
      Component.translatable("gui.xaero_wm_legacy_color")
   };
   public static float[][] ARROW_COLORS = new float[][]{
      {0.8F, 0.1F, 0.1F, 1.0F},
      {0.09F, 0.57F, 0.0F, 1.0F},
      {0.0F, 0.55F, 1.0F, 1.0F},
      {1.0F, 0.93F, 0.0F, 1.0F},
      {0.73F, 0.33F, 0.83F, 1.0F},
      {1.0F, 1.0F, 1.0F, 1.0F},
      {0.0F, 0.0F, 0.0F, 1.0F},
      {0.4588F, 0.0F, 0.0F, 1.0F}
   };
}
