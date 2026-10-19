package team.creative.creativecore.common.util.mc;

import net.minecraft.util.Mth;
import org.joml.Vector3d;
import team.creative.creativecore.common.util.type.Color;

public class ColorUtils {
   public static final int WHITE = -1;
   public static final int RED = -65536;
   public static final int GREEN = -16711936;
   public static final int BLUE = -16776961;
   public static final int LIGHT_BLUE = -16740609;
   public static final int ORANGE = -23296;
   public static final int YELLOW = -256;
   public static final int CYAN = 16711681;
   public static final int MAGENTA = -65281;
   public static final int BLACK = -16777216;
   public static final int GRAY = -5592406;
   public static final int DARK_GRAY = -11184811;

   public static int alpha(int color) {
      return color >> 24 & 0xFF;
   }

   public static int red(int color) {
      return color >> 16 & 0xFF;
   }

   public static int green(int color) {
      return color >> 8 & 0xFF;
   }

   public static int blue(int color) {
      return color & 0xFF;
   }

   public static float alphaF(int color) {
      return (color >> 24 & 0xFF) / 255.0F;
   }

   public static float redF(int color) {
      return (color >> 16 & 0xFF) / 255.0F;
   }

   public static float greenF(int color) {
      return (color >> 8 & 0xFF) / 255.0F;
   }

   public static float blueF(int color) {
      return (color & 0xFF) / 255.0F;
   }

   public static int setAlpha(int color, int alpha) {
      return (alpha & 0xFF) << 24 | color & ~ColorUtils.ColorPart.ALPHA.code;
   }

   public static int rgba(int red, int green, int blue, int alpha) {
      return (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
   }

   public static int rgba(float red, float green, float blue, float alpha) {
      return ((int)(alpha * 255.0F) & 0xFF) << 24 | ((int)(red * 255.0F) & 0xFF) << 16 | ((int)(green * 255.0F) & 0xFF) << 8 | (int)(blue * 255.0F) & 0xFF;
   }

   public static int rgb(int red, int green, int blue) {
      return 0xFF000000 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
   }

   public static int rgb(float red, float green, float blue) {
      return 0xFF000000 | ((int)(red * 255.0F) & 0xFF) << 16 | ((int)(green * 255.0F) & 0xFF) << 8 | (int)(blue * 255.0F) & 0xFF;
   }

   public static Vector3d toVec(int color) {
      float r = color >> 16 & 0xFF;
      float g = color >> 8 & 0xFF;
      float b = color & 0xFF;
      return new Vector3d(r / 255.0F, g / 255.0F, b / 255.0F);
   }

   public static boolean isDefault(int color) {
      return color == -1;
   }

   public static boolean isTransparent(int color) {
      return (color >> 24 & 0xFF) < 255;
   }

   public static boolean isInvisible(int color) {
      return (color >> 24 & 0xFF) == 0;
   }

   public static int substract(int source, int value) {
      int a1 = source >> 24 & 0xFF;
      int r1 = (source & 0xFF0000) >> 16;
      int g1 = (source & 0xFF00) >> 8;
      int b1 = source & 0xFF;
      int a2 = value >> 24 & 0xFF;
      int r2 = (value & 0xFF0000) >> 16;
      int g2 = (value & 0xFF00) >> 8;
      int b2 = value & 0xFF;
      return rgba(Mth.clamp(r1 - r2, 0, 255), Mth.clamp(g1 - g2, 0, 255), Mth.clamp(b1 - b2, 0, 255), Mth.clamp(a1 - a2, 0, 255));
   }

   public static int blend(int i1, int i2) {
      return blend(i1, i2, 0.5F);
   }

   public static int blend(int i1, int i2, float ratio) {
      if (ratio > 1.0F) {
         ratio = 1.0F;
      } else if (ratio < 0.0F) {
         ratio = 0.0F;
      }

      float iRatio = 1.0F - ratio;
      int a1 = i1 >> 24 & 0xFF;
      int r1 = (i1 & 0xFF0000) >> 16;
      int g1 = (i1 & 0xFF00) >> 8;
      int b1 = i1 & 0xFF;
      int a2 = i2 >> 24 & 0xFF;
      int r2 = (i2 & 0xFF0000) >> 16;
      int g2 = (i2 & 0xFF00) >> 8;
      int b2 = i2 & 0xFF;
      int a = (int)(a1 * iRatio + a2 * ratio);
      int r = (int)(r1 * iRatio + r2 * ratio);
      int g = (int)(g1 * iRatio + g2 * ratio);
      int b = (int)(b1 * iRatio + b2 * ratio);
      return a << 24 | r << 16 | g << 8 | b;
   }

   public enum ColorPart {
      RED(16711680) {
         @Override
         public int get(Color color) {
            return color.getRed();
         }

         @Override
         public void set(Color color, int value) {
            color.setRed(value);
         }
      },
      GREEN(65280) {
         @Override
         public int get(Color color) {
            return color.getGreen();
         }

         @Override
         public void set(Color color, int value) {
            color.setGreen(value);
         }
      },
      BLUE(255) {
         @Override
         public int get(Color color) {
            return color.getBlue();
         }

         @Override
         public void set(Color color, int value) {
            color.setBlue(value);
         }
      },
      ALPHA(-16777216) {
         @Override
         public int get(Color color) {
            return color.getAlpha();
         }

         @Override
         public void set(Color color, int value) {
            color.setAlpha(value);
         }
      };

      public final int code;

      ColorPart(int code) {
         this.code = code;
      }

      public abstract int get(Color var1);

      public abstract void set(Color var1, int var2);
   }
}
