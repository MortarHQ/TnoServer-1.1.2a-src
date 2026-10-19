package it.hurts.sskirillss.relics.client.screen.description.general.particles.base;

import java.awt.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public abstract class ParticleData {
   private final ResourceLocation texture;
   private final Color color;
   private final float xStart;
   private final float yStart;
   private final int maxLifeTime;
   private float scale;
   private int lifeTime;
   private float roll;
   private float deltaX;
   private float deltaY;
   private float xO;
   private float yO;
   private float x;
   private float y;
   private float z;

   public ParticleData(ResourceLocation texture, Color color, float xStart, float yStart, float scale, int lifeTime) {
      this.texture = texture;
      this.color = color;
      this.xStart = xStart;
      this.yStart = yStart;
      this.scale = scale;
      this.lifeTime = lifeTime;
      this.maxLifeTime = lifeTime;
      this.roll = 0.0F;
      this.deltaX = 0.0F;
      this.deltaY = 0.0F;
      this.xO = xStart;
      this.yO = yStart;
      this.x = xStart;
      this.y = yStart;
      this.z = 0.0F;
   }

   public void tick(Screen screen) {
      this.xO = this.x;
      this.yO = this.y;
   }

   public abstract void render(Screen var1, GuiGraphics var2, int var3, int var4, float var5);

   public ResourceLocation getTexture() {
      return this.texture;
   }

   public Color getColor() {
      return this.color;
   }

   public float getXStart() {
      return this.xStart;
   }

   public float getYStart() {
      return this.yStart;
   }

   public int getMaxLifeTime() {
      return this.maxLifeTime;
   }

   public float getScale() {
      return this.scale;
   }

   public int getLifeTime() {
      return this.lifeTime;
   }

   public float getRoll() {
      return this.roll;
   }

   public float getDeltaX() {
      return this.deltaX;
   }

   public float getDeltaY() {
      return this.deltaY;
   }

   public float getXO() {
      return this.xO;
   }

   public float getYO() {
      return this.yO;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getZ() {
      return this.z;
   }

   public ParticleData setScale(float scale) {
      this.scale = scale;
      return this;
   }

   public ParticleData setLifeTime(int lifeTime) {
      this.lifeTime = lifeTime;
      return this;
   }

   public ParticleData setRoll(float roll) {
      this.roll = roll;
      return this;
   }

   public ParticleData setDeltaX(float deltaX) {
      this.deltaX = deltaX;
      return this;
   }

   public ParticleData setDeltaY(float deltaY) {
      this.deltaY = deltaY;
      return this;
   }

   public ParticleData setXO(float xO) {
      this.xO = xO;
      return this;
   }

   public ParticleData setYO(float yO) {
      this.yO = yO;
      return this;
   }

   public ParticleData setX(float x) {
      this.x = x;
      return this;
   }

   public ParticleData setY(float y) {
      this.y = y;
      return this;
   }

   public ParticleData setZ(float z) {
      this.z = z;
      return this;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ParticleData other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (Float.compare(this.getXStart(), other.getXStart()) != 0) {
         return false;
      } else if (Float.compare(this.getYStart(), other.getYStart()) != 0) {
         return false;
      } else if (this.getMaxLifeTime() != other.getMaxLifeTime()) {
         return false;
      } else if (Float.compare(this.getScale(), other.getScale()) != 0) {
         return false;
      } else if (this.getLifeTime() != other.getLifeTime()) {
         return false;
      } else if (Float.compare(this.getRoll(), other.getRoll()) != 0) {
         return false;
      } else if (Float.compare(this.getDeltaX(), other.getDeltaX()) != 0) {
         return false;
      } else if (Float.compare(this.getDeltaY(), other.getDeltaY()) != 0) {
         return false;
      } else if (Float.compare(this.getXO(), other.getXO()) != 0) {
         return false;
      } else if (Float.compare(this.getYO(), other.getYO()) != 0) {
         return false;
      } else if (Float.compare(this.getX(), other.getX()) != 0) {
         return false;
      } else if (Float.compare(this.getY(), other.getY()) != 0) {
         return false;
      } else if (Float.compare(this.getZ(), other.getZ()) != 0) {
         return false;
      } else {
         Object this$texture = this.getTexture();
         Object other$texture = other.getTexture();
         if (this$texture == null ? other$texture == null : this$texture.equals(other$texture)) {
            Object this$color = this.getColor();
            Object other$color = other.getColor();
            return this$color == null ? other$color == null : this$color.equals(other$color);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof ParticleData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + Float.floatToIntBits(this.getXStart());
      result = result * 59 + Float.floatToIntBits(this.getYStart());
      result = result * 59 + this.getMaxLifeTime();
      result = result * 59 + Float.floatToIntBits(this.getScale());
      result = result * 59 + this.getLifeTime();
      result = result * 59 + Float.floatToIntBits(this.getRoll());
      result = result * 59 + Float.floatToIntBits(this.getDeltaX());
      result = result * 59 + Float.floatToIntBits(this.getDeltaY());
      result = result * 59 + Float.floatToIntBits(this.getXO());
      result = result * 59 + Float.floatToIntBits(this.getYO());
      result = result * 59 + Float.floatToIntBits(this.getX());
      result = result * 59 + Float.floatToIntBits(this.getY());
      result = result * 59 + Float.floatToIntBits(this.getZ());
      Object $texture = this.getTexture();
      result = result * 59 + ($texture == null ? 43 : $texture.hashCode());
      Object $color = this.getColor();
      return result * 59 + ($color == null ? 43 : $color.hashCode());
   }

   @Override
   public String toString() {
      return "ParticleData(texture="
         + this.getTexture()
         + ", color="
         + this.getColor()
         + ", xStart="
         + this.getXStart()
         + ", yStart="
         + this.getYStart()
         + ", maxLifeTime="
         + this.getMaxLifeTime()
         + ", scale="
         + this.getScale()
         + ", lifeTime="
         + this.getLifeTime()
         + ", roll="
         + this.getRoll()
         + ", deltaX="
         + this.getDeltaX()
         + ", deltaY="
         + this.getDeltaY()
         + ", xO="
         + this.getXO()
         + ", yO="
         + this.getYO()
         + ", x="
         + this.getX()
         + ", y="
         + this.getY()
         + ", z="
         + this.getZ()
         + ")";
   }
}
