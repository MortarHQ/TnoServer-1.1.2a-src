package team.creative.creativecore.common.util.math.geo;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiControlRect;
import team.creative.creativecore.common.util.math.base.Axis;

public class Rect {
   public double minX;
   public double minY;
   public double maxX;
   public double maxY;

   public Rect(double x, double y, double x2, double y2) {
      this.minX = x;
      this.minY = y;
      this.maxX = x2;
      this.maxY = y2;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public static Rect getScreenRect() {
      Minecraft mc = Minecraft.getInstance();
      return new Rect(0.0, 0.0, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
   }

   public boolean intersects(Rect other) {
      return this.minX < other.maxX && this.maxX > other.minX && this.minY < other.maxY && this.maxY > other.minY;
   }

   public Rect intersection(Rect other) {
      if (this.intersects(other)) {
         double x = Math.max(this.minX, other.minX);
         double y = Math.max(this.minY, other.minY);
         double x2 = Math.min(this.maxX, other.maxX);
         double y2 = Math.min(this.maxY, other.maxY);
         return new Rect(x, y, x2, y2);
      } else {
         return null;
      }
   }

   public void move(double x, double y) {
      this.minX += x;
      this.maxX += x;
      this.minY += y;
      this.maxY += y;
   }

   public void shrink(double shrink) {
      this.minX += shrink;
      this.minY += shrink;
      this.maxX -= shrink;
      this.maxY -= shrink;
   }

   public void grow(double grow) {
      this.minX -= grow;
      this.minY -= grow;
      this.maxX += grow;
      this.maxY += grow;
   }

   public double getWidth() {
      return this.maxX - this.minX;
   }

   public double getHeight() {
      return this.maxY - this.minY;
   }

   public double getSize(Axis axis) {
      return switch (axis) {
         case X -> this.getWidth();
         case Y -> this.getHeight();
         default -> 0.0;
      };
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void scissor() {
      Window window = Minecraft.getInstance().getWindow();
      double realMinX = this.minX * window.getGuiScale();
      double realMinY = window.getHeight() - (this.minY + this.getHeight()) * window.getGuiScale();
      double realMaxX = this.getWidth() * window.getGuiScale();
      double realMaxY = this.getHeight() * window.getGuiScale();
      RenderSystem.enableScissor((int)Math.floor(realMinX), (int)Math.floor(realMinY), (int)Math.ceil(realMaxX), (int)Math.ceil(realMaxY) + 1);
   }

   public Rect copy() {
      return new Rect(this.minX, this.minY, this.maxX, this.maxY);
   }

   public Rect child(double x, double y, double width, double height) {
      return new Rect(this.minX + x, this.minY + y, this.minX + x + width, this.minY + y + height);
   }

   public Rect child(Rect rect, double scale, double xOffset, double yOffset) {
      return new Rect(
         this.minX + (rect.minX + xOffset) * scale,
         this.minY + (rect.minY + yOffset) * scale,
         this.minX + (rect.maxX + xOffset) * scale,
         this.minY + (rect.maxY + yOffset) * scale
      );
   }

   public Rect child(GuiControlRect rect, double scale, double xOffset, double yOffset) {
      return new Rect(
         this.minX + (rect.getX() + xOffset) * scale,
         this.minY + (rect.getY() + yOffset) * scale,
         this.minX + (rect.getRight() + xOffset) * scale,
         this.minY + (rect.getBottom() + yOffset) * scale
      );
   }

   public boolean inside(double x, double y) {
      return x >= this.minX && x < this.maxX && y >= this.minY && y < this.maxY;
   }

   @Override
   public String toString() {
      return "[" + this.minX + "," + this.minY + "," + this.maxX + "," + this.maxY + "]";
   }

   public void scale(double scale) {
      this.minX *= scale;
      this.minY *= scale;
      this.maxX *= scale;
      this.maxY *= scale;
   }
}
