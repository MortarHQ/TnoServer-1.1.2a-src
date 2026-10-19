package cn.leolezury.eternalstarlight.common.util;

import java.util.ArrayList;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class TrailEffect {
   private static final int MAX_CAPACITY = 64;
   private final ArrayList<TrailEffect.TrailPoint> points = new ArrayList<>();
   public final ArrayList<TrailEffect.TrailPoint> renderPoints = new ArrayList<>();
   private final float width;
   private float oldLength;
   private float length;

   public float getWidth() {
      return this.width;
   }

   public float getLength() {
      return this.length;
   }

   public void setLength(float length) {
      this.length = length;
   }

   public TrailEffect(float width, float length) {
      this.width = width;
      this.length = length;
   }

   public void update(TrailEffect.TrailPoint point) {
      if (this.points.isEmpty() || this.points.getFirst().pos().distanceTo(point.pos()) > 0.01) {
         this.points.addFirst(point);
      }

      if (this.points.size() > 64) {
         this.points.removeLast();
      }
   }

   public void update(Vec3 pos) {
      this.oldLength = this.length;
      this.update(new TrailEffect.TrailPoint(pos, 1.0F));
   }

   public void prepareRender(Vec3 pos, float partialTicks) {
      this.renderPoints.clear();
      this.renderPoints.addAll(this.points);
      this.prepare(new TrailEffect.TrailPoint(pos), partialTicks);
   }

   private void prepare(TrailEffect.TrailPoint point, float partialTicks) {
      ArrayList<TrailEffect.TrailPoint> modified = new ArrayList<>();
      this.renderPoints.addFirst(point);
      float totalLength = 0.0F;
      float renderLength = Mth.lerp(partialTicks, this.oldLength, this.length);

      for (int i = 0; i < this.renderPoints.size() - 1; i++) {
         TrailEffect.TrailPoint from = this.renderPoints.get(i);
         TrailEffect.TrailPoint to = this.renderPoints.get(i + 1);
         float distance = (float)from.pos().distanceTo(to.pos());
         totalLength += distance;
         if (totalLength > renderLength) {
            this.renderPoints.set(i + 1, this.interpolateTrailPoint((totalLength - renderLength) / distance, to, from));
            modified.addAll(this.renderPoints.subList(0, i + 2));
            totalLength = renderLength;
            break;
         }
      }

      if (!modified.isEmpty()) {
         this.renderPoints.clear();
         this.renderPoints.addAll(modified);
      }

      float currentLength = 0.0F;

      for (int i = 0; i < this.renderPoints.size() - 1; i++) {
         TrailEffect.TrailPoint from = this.renderPoints.get(i);
         TrailEffect.TrailPoint to = this.renderPoints.get(i + 1);
         float distance = (float)from.pos().distanceTo(to.pos());
         this.renderPoints.set(i, this.renderPoints.get(i).withProgressFactor((totalLength - currentLength) / renderLength));
         currentLength += distance;
      }

      if (this.renderPoints.size() > 1) {
         this.renderPoints.set(this.renderPoints.size() - 1, this.renderPoints.getLast().withProgressFactor(0.0F));
      }
   }

   private TrailEffect.TrailPoint interpolateTrailPoint(float progress, TrailEffect.TrailPoint first, TrailEffect.TrailPoint second) {
      return new TrailEffect.TrailPoint(ESMathUtil.lerpVec(progress, first.pos(), second.pos()));
   }

   @FunctionalInterface
   public interface TrailOffsetFunction {
      TrailEffect.TrailOffsetFunction FACE_CAMERA = (look, camXRot, camYRot, tangent) -> tangent.cross(look);
      TrailEffect.TrailOffsetFunction Z_ROT = (look, camXRot, camYRot, tangent) -> new Vec3(0.0, 1.0, 0.0).zRot(camXRot * (float) (-Math.PI / 180.0));

      Vec3 calculateTrailOffset(Vec3 var1, float var2, float var3, Vec3 var4);
   }

   public record TrailPoint(Vec3 pos, float progressFactor) {
      public TrailPoint(Vec3 pos) {
         this(pos, 1.0F);
      }

      public TrailEffect.TrailPoint withProgressFactor(float progress) {
         return new TrailEffect.TrailPoint(this.pos(), progress);
      }
   }
}
