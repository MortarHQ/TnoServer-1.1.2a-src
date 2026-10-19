package dev.ftb.mods.ftbchunks.client;

import dev.ftb.mods.ftblibrary.config.NameMap;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public enum MinimapPosition {
   BOTTOM_LEFT(0, 2),
   LEFT(0, 1),
   TOP_LEFT(0, 0),
   TOP_RIGHT(1, 0),
   RIGHT(1, 1),
   BOTTOM_RIGHT(1, 2);

   public static final NameMap<MinimapPosition> NAME_MAP = NameMap.of(TOP_RIGHT, values()).baseNameKey("ftbchunks.minimap.position").create();
   public final int posX;
   public final int posY;

   MinimapPosition(int x, int y) {
      this.posX = x;
      this.posY = y;
   }

   public int getX(int w, int s) {
      return this.posX == 0 ? 5 : w - s - 5;
   }

   public int getY(int h, int s) {
      if (this.posY == 0) {
         return 5;
      } else {
         return this.posY == 1 ? (h - s) / 2 : h - s - 5;
      }
   }

   public enum MinimapOffsetConditional implements Predicate<MinimapPosition> {
      BOTTOM_LEFT(MinimapPosition.BOTTOM_LEFT),
      LEFT(MinimapPosition.LEFT),
      TOP_LEFT(MinimapPosition.TOP_LEFT),
      TOP_RIGHT(MinimapPosition.TOP_RIGHT),
      RIGHT(MinimapPosition.RIGHT),
      BOTTOM_RIGHT(MinimapPosition.BOTTOM_RIGHT),
      NONE(null);

      public static final NameMap<MinimapPosition.MinimapOffsetConditional> NAME_MAP = NameMap.of(NONE, values())
         .baseNameKey("ftbchunks.minimap.position")
         .create();
      @Nullable
      private final MinimapPosition position;

      MinimapOffsetConditional(@Nullable MinimapPosition position) {
         this.position = position;
      }

      public boolean test(MinimapPosition minimapPosition) {
         return this == NONE || this.position == minimapPosition;
      }
   }
}
