package team.creative.creativecore.common.util.type;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Vec3i;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3d;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public class Color {
   public static final Color NONE = new Color(0) {
      @Override
      public void glColor() {
      }
   };
   public static final Color WHITE = new Color(-1);
   public static final Color RED = new Color(-65536);
   public static final Color GREEN = new Color(-16711936);
   public static final Color BLUE = new Color(-16776961);
   public static final Color LIGHT_BLUE = new Color(-16740609);
   public static final Color ORANGE = new Color(-23296);
   public static final Color YELLOW = new Color(-256);
   public static final Color CYAN = new Color(16711681);
   public static final Color MAGENTA = new Color(-65281);
   public static final Color BLACK = new Color(-16777216);
   protected short red;
   protected short green;
   protected short blue;
   protected short alpha;

   public Color() {
      this((short)255, (short)255, (short)255, (short)255);
   }

   public Color(Color color) {
      this.set(color);
   }

   public Color(int color) {
      this.alpha = (short)(color >> 24 & 0xFF);
      this.red = (short)(color >> 16 & 0xFF);
      this.green = (short)(color >> 8 & 0xFF);
      this.blue = (short)(color & 0xFF);
   }

   public Color(float r, float g, float b) {
      this((int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F), 255);
   }

   public Color(int r, int g, int b) {
      this(r, g, b, 255);
   }

   public Color(short r, short g, short b) {
      this(r, g, b, (short)255);
   }

   public Color(float r, float g, float b, float a) {
      this((int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F), (int)(a * 255.0F));
   }

   public Color(int r, int g, int b, int a) {
      this.red = (short)r;
      this.green = (short)g;
      this.blue = (short)b;
      this.alpha = (short)a;
   }

   public Color(short r, short g, short b, short a) {
      this.red = r;
      this.green = g;
      this.blue = b;
      this.alpha = a;
   }

   public Color(Vec3i vec) {
      this.red = (short)vec.getX();
      this.green = (short)vec.getY();
      this.blue = (short)vec.getZ();
      this.alpha = 255;
   }

   public Color(Vector3d vec) {
      this.red = (short)(vec.x * 255.0);
      this.green = (short)(vec.y * 255.0);
      this.blue = (short)(vec.z * 255.0);
      this.alpha = 255;
   }

   public void set(Color color) {
      this.red = color.getRed();
      this.green = color.getGreen();
      this.blue = color.getBlue();
      this.alpha = color.getAlpha();
   }

   public void set(ColorType type, int value) {
      this.set(type, (short)value);
   }

   public void set(ColorType type, short value) {
      switch (type) {
         case ALPHA:
            this.alpha = value;
            break;
         case RED:
            this.red = value;
            break;
         case GREEN:
            this.green = value;
            break;
         case BLUE:
            this.blue = value;
      }
   }

   public void setAlpha(int a) {
      this.alpha = (short)a;
   }

   public void setAlpha(short a) {
      this.alpha = a;
   }

   public void setRed(int r) {
      this.red = (short)r;
   }

   public void setRed(short r) {
      this.red = r;
   }

   public void setGreen(int g) {
      this.green = (short)g;
   }

   public void setGreen(short g) {
      this.green = g;
   }

   public void setBlue(int b) {
      this.blue = (short)b;
   }

   public void setBlue(short b) {
      this.blue = b;
   }

   public short get(ColorType type) {
      return switch (type) {
         case ALPHA -> this.alpha;
         case RED -> this.red;
         case GREEN -> this.green;
         case BLUE -> this.blue;
         default -> 0;
      };
   }

   public short getAlpha() {
      return this.alpha;
   }

   public short getRed() {
      return this.red;
   }

   public short getGreen() {
      return this.green;
   }

   public short getBlue() {
      return this.blue;
   }

   public float getDecimal(ColorType type) {
      return switch (type) {
         case ALPHA -> this.alpha / 255.0F;
         case RED -> this.red / 255.0F;
         case GREEN -> this.green / 255.0F;
         case BLUE -> this.blue / 255.0F;
         default -> 0.0F;
      };
   }

   public float getAlphaDecimal() {
      return this.alpha / 255.0F;
   }

   public float getRedDecimal() {
      return this.red / 255.0F;
   }

   public float getGreenDecimal() {
      return this.green / 255.0F;
   }

   public float getBlueDecimal() {
      return this.blue / 255.0F;
   }

   public int toInt() {
      return (this.alpha & 0xFF) << 24 | (this.red & 0xFF) << 16 | (this.green & 0xFF) << 8 | this.blue & 0xFF;
   }

   public boolean isDefault() {
      return this.red == 255 && this.green == 255 && this.blue == 255 && this.alpha == 255;
   }

   public boolean isWhite() {
      return this.red == 255 && this.green == 255 && this.blue == 255;
   }

   public boolean isTransparent() {
      return this.alpha < 255;
   }

   public boolean isInvisible() {
      return this.alpha == 0;
   }

   public void blend(Color color) {
      this.blend(color, 0.5F);
   }

   public void blend(Color color, float ratio) {
      if (ratio > 1.0F) {
         ratio = 1.0F;
      } else if (ratio < 0.0F) {
         ratio = 0.0F;
      }

      float iRatio = 1.0F - ratio;
      this.alpha = (short)(this.alpha * iRatio + color.alpha * ratio);
      this.red = (short)(this.red * iRatio + color.red * ratio);
      this.green = (short)(this.green * iRatio + color.green * ratio);
      this.blue = (short)(this.blue * iRatio + color.blue * ratio);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void glColor() {
      RenderSystem.setShaderColor(this.red / 255.0F, this.green / 255.0F, this.blue / 255.0F, this.alpha / 255.0F);
   }

   public Vec3d toVec() {
      return new Vec3d(this.red / 255.0, this.green / 255.0, this.blue / 255.0);
   }

   public static boolean isWhite(int color) {
      int r = color >> 16 & 0xFF;
      int g = color >> 8 & 0xFF;
      int b = color & 0xFF;
      return r == 255 && g == 255 && b == 255;
   }

   public static boolean isTransparent(int color) {
      int a = color >> 24 & 0xFF;
      return a < 255;
   }

   public static boolean isInvisible(int color) {
      int a = color >> 24 & 0xFF;
      return a == 0;
   }

   public static Color blend(Color color1, Color color2) {
      return blend(color1, color2, 0.5F);
   }

   public static Color blend(Color color1, Color color2, float ratio) {
      if (ratio > 1.0F) {
         ratio = 1.0F;
      } else if (ratio < 0.0F) {
         ratio = 0.0F;
      }

      float iRatio = 1.0F - ratio;
      short a = (short)(color1.alpha * iRatio + color2.alpha * ratio);
      short r = (short)(color1.red * iRatio + color2.red * ratio);
      short g = (short)(color1.green * iRatio + color2.green * ratio);
      short b = (short)(color1.blue * iRatio + color2.blue * ratio);
      return new Color(r, g, b, a);
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
}
