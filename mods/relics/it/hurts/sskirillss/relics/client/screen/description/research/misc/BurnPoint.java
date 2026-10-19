package it.hurts.sskirillss.relics.client.screen.description.research.misc;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;

public class BurnPoint {
   @Nullable
   private Pair<Integer, Integer> link;
   private float x;
   private float y;
   private int maxLifeTime;
   private int lifeTime;
   private float maxScale;
   private float scale;
   private float scaleO;
   private Consumer<BurnPoint> ticker;

   public static BurnPoint.BurnPointBuilder builder(int x, int y, float scale) {
      BurnPoint.BurnPointBuilder builder = new BurnPoint.BurnPointBuilder();
      builder.x(x);
      builder.y(y);
      builder.maxScale(scale);
      builder.scale(scale);
      builder.link(null);
      return builder;
   }

   public final void tick() {
      this.getTicker().accept(this);
   }

   public BurnPoint set(BurnPoint other) {
      this.setLink(other.getLink());
      this.setX(other.getX());
      this.setY(other.getY());
      this.setMaxLifeTime(other.getMaxLifeTime());
      this.setLifeTime(other.getLifeTime());
      this.setMaxScale(other.getMaxScale());
      this.setScaleO(other.getScale());
      this.setScale(other.getScale());
      this.setTicker(other.getTicker());
      return this;
   }

   private static Consumer<BurnPoint> $default$ticker() {
      return point -> {};
   }

   BurnPoint(
      @Nullable Pair<Integer, Integer> link,
      float x,
      float y,
      int maxLifeTime,
      int lifeTime,
      float maxScale,
      float scale,
      float scaleO,
      Consumer<BurnPoint> ticker
   ) {
      this.link = link;
      this.x = x;
      this.y = y;
      this.maxLifeTime = maxLifeTime;
      this.lifeTime = lifeTime;
      this.maxScale = maxScale;
      this.scale = scale;
      this.scaleO = scaleO;
      this.ticker = ticker;
   }

   @Nullable
   public Pair<Integer, Integer> getLink() {
      return this.link;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public int getMaxLifeTime() {
      return this.maxLifeTime;
   }

   public int getLifeTime() {
      return this.lifeTime;
   }

   public float getMaxScale() {
      return this.maxScale;
   }

   public float getScale() {
      return this.scale;
   }

   public float getScaleO() {
      return this.scaleO;
   }

   public Consumer<BurnPoint> getTicker() {
      return this.ticker;
   }

   public void setLink(@Nullable Pair<Integer, Integer> link) {
      this.link = link;
   }

   public void setX(float x) {
      this.x = x;
   }

   public void setY(float y) {
      this.y = y;
   }

   public void setMaxLifeTime(int maxLifeTime) {
      this.maxLifeTime = maxLifeTime;
   }

   public void setLifeTime(int lifeTime) {
      this.lifeTime = lifeTime;
   }

   public void setMaxScale(float maxScale) {
      this.maxScale = maxScale;
   }

   public void setScale(float scale) {
      this.scale = scale;
   }

   public void setScaleO(float scaleO) {
      this.scaleO = scaleO;
   }

   public void setTicker(Consumer<BurnPoint> ticker) {
      this.ticker = ticker;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BurnPoint other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (Float.compare(this.getX(), other.getX()) != 0) {
         return false;
      } else if (Float.compare(this.getY(), other.getY()) != 0) {
         return false;
      } else if (this.getMaxLifeTime() != other.getMaxLifeTime()) {
         return false;
      } else if (this.getLifeTime() != other.getLifeTime()) {
         return false;
      } else if (Float.compare(this.getMaxScale(), other.getMaxScale()) != 0) {
         return false;
      } else if (Float.compare(this.getScale(), other.getScale()) != 0) {
         return false;
      } else if (Float.compare(this.getScaleO(), other.getScaleO()) != 0) {
         return false;
      } else {
         Object this$link = this.getLink();
         Object other$link = other.getLink();
         if (this$link == null ? other$link == null : this$link.equals(other$link)) {
            Object this$ticker = this.getTicker();
            Object other$ticker = other.getTicker();
            return this$ticker == null ? other$ticker == null : this$ticker.equals(other$ticker);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof BurnPoint;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + Float.floatToIntBits(this.getX());
      result = result * 59 + Float.floatToIntBits(this.getY());
      result = result * 59 + this.getMaxLifeTime();
      result = result * 59 + this.getLifeTime();
      result = result * 59 + Float.floatToIntBits(this.getMaxScale());
      result = result * 59 + Float.floatToIntBits(this.getScale());
      result = result * 59 + Float.floatToIntBits(this.getScaleO());
      Object $link = this.getLink();
      result = result * 59 + ($link == null ? 43 : $link.hashCode());
      Object $ticker = this.getTicker();
      return result * 59 + ($ticker == null ? 43 : $ticker.hashCode());
   }

   @Override
   public String toString() {
      return "BurnPoint(link="
         + this.getLink()
         + ", x="
         + this.getX()
         + ", y="
         + this.getY()
         + ", maxLifeTime="
         + this.getMaxLifeTime()
         + ", lifeTime="
         + this.getLifeTime()
         + ", maxScale="
         + this.getMaxScale()
         + ", scale="
         + this.getScale()
         + ", scaleO="
         + this.getScaleO()
         + ", ticker="
         + this.getTicker()
         + ")";
   }

   public static class BurnPointBuilder {
      private Pair<Integer, Integer> link;
      private float x;
      private float y;
      private int maxLifeTime;
      private int lifeTime;
      private float maxScale;
      private float scale;
      private float scaleO;
      private boolean ticker$set;
      private Consumer<BurnPoint> ticker$value;

      public BurnPoint.BurnPointBuilder scale(float scale) {
         this.scaleO = scale;
         this.scale = scale;
         return this;
      }

      BurnPointBuilder() {
      }

      public BurnPoint.BurnPointBuilder link(@Nullable Pair<Integer, Integer> link) {
         this.link = link;
         return this;
      }

      public BurnPoint.BurnPointBuilder x(float x) {
         this.x = x;
         return this;
      }

      public BurnPoint.BurnPointBuilder y(float y) {
         this.y = y;
         return this;
      }

      public BurnPoint.BurnPointBuilder maxLifeTime(int maxLifeTime) {
         this.maxLifeTime = maxLifeTime;
         return this;
      }

      public BurnPoint.BurnPointBuilder lifeTime(int lifeTime) {
         this.lifeTime = lifeTime;
         return this;
      }

      public BurnPoint.BurnPointBuilder maxScale(float maxScale) {
         this.maxScale = maxScale;
         return this;
      }

      public BurnPoint.BurnPointBuilder scaleO(float scaleO) {
         this.scaleO = scaleO;
         return this;
      }

      public BurnPoint.BurnPointBuilder ticker(Consumer<BurnPoint> ticker) {
         this.ticker$value = ticker;
         this.ticker$set = true;
         return this;
      }

      public BurnPoint build() {
         Consumer<BurnPoint> ticker$value = this.ticker$value;
         if (!this.ticker$set) {
            ticker$value = BurnPoint.$default$ticker();
         }

         return new BurnPoint(this.link, this.x, this.y, this.maxLifeTime, this.lifeTime, this.maxScale, this.scale, this.scaleO, ticker$value);
      }

      @Override
      public String toString() {
         return "BurnPoint.BurnPointBuilder(link="
            + this.link
            + ", x="
            + this.x
            + ", y="
            + this.y
            + ", maxLifeTime="
            + this.maxLifeTime
            + ", lifeTime="
            + this.lifeTime
            + ", maxScale="
            + this.maxScale
            + ", scale="
            + this.scale
            + ", scaleO="
            + this.scaleO
            + ", ticker$value="
            + this.ticker$value
            + ")";
      }
   }
}
