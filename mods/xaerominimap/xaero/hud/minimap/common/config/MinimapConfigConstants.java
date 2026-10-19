package xaero.hud.minimap.common.config;

import net.minecraft.network.chat.Component;

public class MinimapConfigConstants {
   public static final Component[] DOTS_STYLES = new Component[]{
      Component.translatable("gui.xaero_dots_style_default"), Component.translatable("gui.xaero_dots_style_legacy")
   };
   public static final Component[] MAIN_ENTITY_TYPES = new Component[]{
      Component.translatable("gui.xaero_crosshair"), Component.translatable("gui.xaero_dot"), Component.translatable("gui.xaero_arrow")
   };
   public static Component[] ARROW_COLOR_NAMES = new Component[]{
      Component.translatable("gui.xaero_red"),
      Component.translatable("gui.xaero_green"),
      Component.translatable("gui.xaero_blue"),
      Component.translatable("gui.xaero_yellow"),
      Component.translatable("gui.xaero_purple"),
      Component.translatable("gui.xaero_white"),
      Component.translatable("gui.xaero_black"),
      Component.translatable("gui.xaero_legacy_color")
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
   public static final Component AUTO_SCALE_COMPONENT = Component.translatable("gui.xaero_ui_scale_auto");
   public static final Component MINECRAFT_SCALE_COMPONENT = Component.translatable("gui.xaero_ui_scale_mc");
   public static final float DEFAULT_WORLD_WAYPOINT_SCALE = 0.8F;
   public static final float WORLD_WAYPOINT_MINECRAFT_SCALE = 0.02666667F;
   public static final double WAYPOINT_ICON_WORLD_SCALE = 0.021333335F;
   public static final Component[] MULTIPLE_WAYPOINTS_INFO_NAMES = new Component[]{
      Component.translatable("gui.xaero_off"),
      Component.translatable("gui.xaero_while_sneaking"),
      Component.translatable("gui.xaero_multiple_waypoints_always")
   };
   public static final Component[] WAYPOINT_DISTANCE_NAMES = new Component[]{
      Component.translatable("gui.xaero_off"), Component.translatable("gui.xaero_looking_at"), Component.translatable("gui.xaero_all")
   };
   public static final Component UNLIMITED_COMPONENT = Component.translatable("gui.xaero_unlimited");
   public static final Component CONVERT_DISTANCE_NEVER_COMPONENT = Component.translatable("gui.xaero_auto_convert_wp_distance_km_never");
   public static final Component[] HIDE_WORLD_NAMES_NAMES = new Component[]{
      Component.translatable("gui.xaero_off"), Component.translatable("gui.xaero_partial"), Component.translatable("gui.xaero_full")
   };
   public static final Component[] COMPASS_LOCATION_NAMES = new Component[]{
      Component.translatable("gui.xaero_off"),
      Component.translatable("gui.xaero_minimap_compass_inside_frame"),
      Component.translatable("gui.xaero_minimap_compass_on_frame")
   };
   public static final Component[] COLOR_NAMES = new Component[]{
      Component.translatable("gui.xaero_black"),
      Component.translatable("gui.xaero_dark_blue"),
      Component.translatable("gui.xaero_dark_green"),
      Component.translatable("gui.xaero_dark_aqua"),
      Component.translatable("gui.xaero_dark_red"),
      Component.translatable("gui.xaero_dark_purple"),
      Component.translatable("gui.xaero_gold"),
      Component.translatable("gui.xaero_gray"),
      Component.translatable("gui.xaero_dark_gray"),
      Component.translatable("gui.xaero_blue"),
      Component.translatable("gui.xaero_green"),
      Component.translatable("gui.xaero_aqua"),
      Component.translatable("gui.xaero_red"),
      Component.translatable("gui.xaero_purple"),
      Component.translatable("gui.xaero_yellow"),
      Component.translatable("gui.xaero_white")
   };
   public static final int[] COLORS = new int[]{
      -16777216, -16777046, -16733696, -16733526, -5636096, -5635926, -22016, -5592406, -11184811, -11184641, -11141291, -11141121, -65536, -43521, -171, -1
   };
   public static final String[] COLOR_CODES = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
   public static final Component[] BLOCK_COLOR_NAMES = new Component[]{
      Component.translatable("gui.xaero_accurate"), Component.translatable("gui.xaero_vanilla")
   };
   public static final Component[] TERRAIN_SLOPES_NAMES = new Component[]{
      Component.translatable("gui.xaero_off"),
      Component.translatable("gui.xaero_slopes_legacy"),
      Component.translatable("gui.xaero_slopes_default_3d"),
      Component.translatable("gui.xaero_slopes_default_2d")
   };
   public static final Component[] MINIMAP_SHAPE_NAMES = new Component[]{
      Component.translatable("gui.xaero_minimap_shape_square"), Component.translatable("gui.xaero_minimap_shape_circle")
   };
   public static final int MAX_ZOOM = 5;
   public static final Component[] FRAME_NAMES = new Component[]{
      Component.translatable("gui.xaero_minimap_frame_default"),
      Component.translatable("gui.xaero_minimap_frame_colored_thick"),
      Component.translatable("gui.xaero_minimap_frame_colored_thin"),
      Component.translatable("gui.xaero_off")
   };
   public static final Component[] PUSH_BOX_NAMES = new Component[]{
      Component.translatable("gui.xaero_off"), Component.translatable("gui.xaero_pushbox_normal"), Component.translatable("gui.xaero_pushbox_screen_height")
   };
   public static final Component[] LIGHT_NAMES = new Component[]{
      Component.translatable("gui.xaero_off"),
      Component.translatable("gui.xaero_light_block"),
      Component.translatable("gui.xaero_light_sky"),
      Component.translatable("gui.xaero_light_all"),
      Component.translatable("gui.xaero_light_both2")
   };
   public static final Component[] INFO_DISPLAY_ALIGNMENT_NAMES = new Component[]{
      Component.translatable("gui.xaero_center"), Component.translatable("gui.xaero_left"), Component.translatable("gui.xaero_right")
   };
}
