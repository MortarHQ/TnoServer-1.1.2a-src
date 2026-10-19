package it.hurts.octostudios.octolib.module.particle.trail;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.hurts.octostudios.octolib.module.particle.OctoRenderManager;
import it.hurts.octostudios.octolib.module.particle.RenderProvider;
import it.hurts.octostudios.octolib.util.ColorUtils;
import it.hurts.octostudios.octolib.util.TesselatorUtils;
import it.hurts.octostudios.octolib.util.VectorUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public abstract class TrailProvider implements RenderProvider<TrailProvider, TrailBuffer> {
   public TrailBuffer createBuffer() {
      return new DefaultTrailBuffer(this.getTrailMaxLength());
   }

   public boolean shouldRender(TrailBuffer buffer) {
      return this.isTrailAlive() || !this.disappearAfterDeath() && buffer.size() != 0;
   }

   @Override
   public Vec3 getRenderPosition(float partialTick) {
      return this.getTrailPosition(partialTick);
   }

   public abstract Vec3 getTrailPosition(float var1);

   @Deprecated
   @Override
   public double getRenderDistance() {
      return this.getTrailRenderDistance();
   }

   public double getTrailRenderDistance() {
      return 64.0;
   }

   @Deprecated
   @Override
   public int getUpdateFrequency() {
      return this.getTrailUpdateFrequency();
   }

   public abstract int getTrailUpdateFrequency();

   public abstract boolean isTrailAlive();

   public boolean isTrailGrowing() {
      return true;
   }

   public boolean disappearAfterDeath() {
      return false;
   }

   public abstract int getTrailMaxLength();

   public abstract int getTrailFadeInColor();

   public abstract int getTrailFadeOutColor();

   public abstract double getTrailScale();

   public int getTrailInterpolationPoints() {
      return 1;
   }

   public List<Vec3> getTrailRenderPositions(List<Vec3> points, float pTicks) {
      return points;
   }

   @Deprecated
   @Override
   public void render(float pTicks, PoseStack poseStack, MultiBufferSource bufferSourceList) {
      ClientLevel world = Minecraft.getInstance().level;
      if (world != null) {
         Vec3 matrixTranslation = this.getRenderPosition(pTicks);
         long time = world.getGameTime();
         int segments = this.getTrailMaxLength();
         if (segments > 0 && this.getTrailUpdateFrequency() > 0) {
            List<Vec3> partialPoses = new ArrayList<>();
            float partial = (float)(time % this.getTrailUpdateFrequency()) + pTicks;
            TrailBuffer buffer = OctoRenderManager.getOrCreateBuffer(this);
            if (buffer != null) {
               List<Vec3> points = new ArrayList<>();
               if (this.isTrailAlive()) {
                  points.add(new Vec3(0.0, 0.0, 0.0));
               }

               for (Vec3 vec3 : buffer) {
                  points.add(vec3.subtract(matrixTranslation));
               }

               points = this.getTrailRenderPositions(points, pTicks);
               if (points.size() > 2) {
                  for (int i = 0; i < points.size() - 1; i++) {
                     Vec3 p0 = i == 0 ? points.getFirst() : points.get(i - 1);
                     Vec3 p1 = points.get(i);
                     Vec3 p2 = points.get(i + 1);
                     Vec3 p3 = i == points.size() - 2 ? points.getLast() : points.get(i + 2);
                     partialPoses.add(p1);
                     int interpolationPoints = Math.max(1, this.getTrailInterpolationPoints() + 1);

                     for (float f = 1.0F / interpolationPoints; f < 1.0F; f += 1.0F / interpolationPoints) {
                        partialPoses.add(VectorUtils.catmullromVec(f, p0, p1, p2, p3));
                     }
                  }

                  partialPoses.add(points.getLast());
               } else {
                  partialPoses.addAll(points);
               }

               if (points.size() > 1 && this.getTrailMaxLength() + 1 == points.size()) {
                  int i = partialPoses.size() - 1;
                  Vec3 adjustment = partialPoses.get(i - 1)
                     .subtract(partialPoses.get(i))
                     .scale((double)partial / this.getTrailUpdateFrequency() * this.getTrailInterpolationPoints());
                  partialPoses.set(i, partialPoses.get(i).add(adjustment));
               }

               if (partialPoses.size() >= 2) {
                  partialPoses = partialPoses.stream().filter(Objects::nonNull).toList();
                  if (partialPoses.size() >= 2) {
                     Vec3[][] crossVecs = new Vec3[partialPoses.size()][3];

                     for (int i = 1; i < partialPoses.size(); i++) {
                        Vec3 pos1 = partialPoses.get(i - 1);
                        Vec3 pos2 = partialPoses.get(i);
                        if (pos1 != null && pos2 != null) {
                           Vec3 vec1 = pos2.subtract(pos1);
                           Vec3 vec1n = vec1.normalize();
                           Vec3 perpendicular1 = vec1n.cross(VectorUtils.Y_VEC).normalize();
                           double scale = this.getTrailScale() * (1.0 - (double)i / (partialPoses.size() - 1)) + 0.005;
                           crossVecs[i - 1][0] = perpendicular1.scale(scale);
                           crossVecs[i - 1][1] = VectorUtils.rotate(crossVecs[i - 1][0], vec1n, 120.0);
                           crossVecs[i - 1][2] = VectorUtils.rotate(crossVecs[i - 1][0], vec1n, -120.0);
                        }
                     }

                     Color color1 = new Color(this.getTrailFadeInColor(), true);
                     Color color2 = new Color(this.getTrailFadeOutColor(), true);
                     poseStack.pushPose();
                     Matrix4f matrix4f = poseStack.last().pose();
                     VertexConsumer tes = bufferSourceList.getBuffer(TesselatorUtils.TRAIL_RENDER_TYPE);

                     for (int i = 0; i < partialPoses.size() - 1; i++) {
                        Vec3 pos1 = partialPoses.get(i);
                        Vec3 pos2 = partialPoses.get(i + 1);
                        if (pos1 != null
                           && pos2 != null
                           && crossVecs[i][0] != null
                           && crossVecs[i][1] != null
                           && crossVecs[i + 1][0] != null
                           && crossVecs[i + 1][1] != null) {
                           Vec3 tip1 = pos1.add(crossVecs[i][0]);
                           Vec3 base1Left = pos1.add(crossVecs[i][2]);
                           Vec3 base1Right = pos1.add(crossVecs[i][1]);
                           Vec3 tip2 = pos2.add(crossVecs[i + 1][0]);
                           Vec3 base2Left = pos2.add(crossVecs[i + 1][2]);
                           Vec3 base2Right = pos2.add(crossVecs[i + 1][1]);
                           Color c1 = ColorUtils.blend(color1, color2, (float)i / (partialPoses.size() - 1));
                           Color c2 = ColorUtils.blend(color1, color2, (float)(i + 1) / (partialPoses.size() - 1));
                           TesselatorUtils.drawQuadGradient(
                              tes,
                              matrix4f,
                              (float)base2Left.x,
                              (float)base2Left.y,
                              (float)base2Left.z,
                              (float)base1Left.x,
                              (float)base1Left.y,
                              (float)base1Left.z,
                              (float)tip1.x,
                              (float)tip1.y,
                              (float)tip1.z,
                              (float)tip2.x,
                              (float)tip2.y,
                              (float)tip2.z,
                              c2,
                              c1
                           );
                           TesselatorUtils.drawQuadGradient(
                              tes,
                              matrix4f,
                              (float)tip2.x,
                              (float)tip2.y,
                              (float)tip2.z,
                              (float)tip1.x,
                              (float)tip1.y,
                              (float)tip1.z,
                              (float)base1Right.x,
                              (float)base1Right.y,
                              (float)base1Right.z,
                              (float)base2Right.x,
                              (float)base2Right.y,
                              (float)base2Right.z,
                              c2,
                              c1
                           );
                           TesselatorUtils.drawQuadGradient(
                              tes,
                              matrix4f,
                              (float)base2Right.x,
                              (float)base2Right.y,
                              (float)base2Right.z,
                              (float)base1Right.x,
                              (float)base1Right.y,
                              (float)base1Right.z,
                              (float)base1Left.x,
                              (float)base1Left.y,
                              (float)base1Left.z,
                              (float)base2Left.x,
                              (float)base2Left.y,
                              (float)base2Left.z,
                              c2,
                              c1
                           );
                        }
                     }

                     poseStack.popPose();
                  }
               }
            }
         }
      }
   }
}
