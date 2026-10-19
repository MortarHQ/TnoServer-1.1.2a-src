package team.creative.creativecore.common.gui.style;

public class ControlFormatting {
   public static final ControlFormatting PROGRESSBAR = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 0, ControlFormatting.ControlStyleFace.BAR
   );
   public static final ControlFormatting CLICKABLE = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 2, ControlFormatting.ControlStyleFace.CLICKABLE
   );
   public static final ControlFormatting CLICKABLE_SMALL_PADDING = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 1, ControlFormatting.ControlStyleFace.CLICKABLE
   );
   public static final ControlFormatting CLICKABLE_NO_PADDING = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 0, ControlFormatting.ControlStyleFace.CLICKABLE
   );
   public static final ControlFormatting CLICKABLE_INACTIVE = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 2, ControlFormatting.ControlStyleFace.CLICKABLE_INACTIVE
   );
   public static final ControlFormatting CLICKABLE_INACTIVE_SMALL_PADDING = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 1, ControlFormatting.ControlStyleFace.CLICKABLE_INACTIVE
   );
   public static final ControlFormatting CLICKABLE_INACTIVE_NO_PADDING = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 0, ControlFormatting.ControlStyleFace.CLICKABLE_INACTIVE
   );
   public static final ControlFormatting CLICKABLE_NO_BORDER = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.NONE, 0, ControlFormatting.ControlStyleFace.CLICKABLE
   );
   public static final ControlFormatting HEADER = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 2, ControlFormatting.ControlStyleFace.HEADER_BACKGROUND
   );
   public static final ControlFormatting NESTED = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 2, ControlFormatting.ControlStyleFace.NESTED_BACKGROUND
   );
   public static final ControlFormatting NESTED_NO_PADDING = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 0, ControlFormatting.ControlStyleFace.NESTED_BACKGROUND
   );
   public static final ControlFormatting GUI = new ControlFormatting(ControlFormatting.ControlStyleBorder.BIG, 5, ControlFormatting.ControlStyleFace.BACKGROUND);
   public static final ControlFormatting TRANSPARENT = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.NONE, 0, ControlFormatting.ControlStyleFace.NONE
   );
   public static final ControlFormatting TRANSPARENT_NO_DISABLE = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.NONE, 0, ControlFormatting.ControlStyleFace.NONE, false
   );
   public static final ControlFormatting SLOT = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.NONE, 0, ControlFormatting.ControlStyleFace.SLOT, false
   );
   public static final ControlFormatting OUTLINE = new ControlFormatting(ControlFormatting.ControlStyleBorder.SMALL, 1, ControlFormatting.ControlStyleFace.NONE);
   public final ControlFormatting.ControlStyleBorder border;
   public final int padding;
   public final ControlFormatting.ControlStyleFace face;
   public final boolean hasDisabledEffect;

   public ControlFormatting(ControlFormatting.ControlStyleBorder border, int padding, ControlFormatting.ControlStyleFace face) {
      this.border = border;
      this.padding = padding;
      this.face = face;
      this.hasDisabledEffect = true;
   }

   public ControlFormatting(ControlFormatting.ControlStyleBorder border, int padding, ControlFormatting.ControlStyleFace face, boolean hasDisabledEffect) {
      this.border = border;
      this.padding = padding;
      this.face = face;
      this.hasDisabledEffect = hasDisabledEffect;
   }

   public enum ControlStyleBorder {
      BIG,
      SMALL,
      NONE;
   }

   public enum ControlStyleFace {
      BAR,
      CLICKABLE,
      CLICKABLE_INACTIVE,
      HEADER_BACKGROUND,
      NESTED_BACKGROUND,
      BACKGROUND,
      SLOT,
      NONE,
      DISABLED;
   }
}
