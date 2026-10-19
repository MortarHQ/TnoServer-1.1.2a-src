package twilightforest.enums;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public final class HollowLogVariants {
   private HollowLogVariants() {
   }

   public enum Climbable implements StringRepresentable {
      VINE,
      LADDER,
      LADDER_WATERLOGGED;

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   public enum Horizontal implements StringRepresentable {
      EMPTY,
      MOSS,
      MOSS_AND_GRASS,
      SNOW,
      WATERLOGGED;

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
