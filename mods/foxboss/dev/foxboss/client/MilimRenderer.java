package dev.foxboss.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.foxboss.entity.BurstTimeline;
import dev.foxboss.entity.MilimEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.Color;

public final class MilimRenderer extends GeoEntityRenderer<MilimEntity> {
   public MilimRenderer(Context context) {
      super(context, new MilimModel());
      this.addRenderLayer(new MilimHeldItemLayer(this));
      this.addRenderLayer(new MilimThrownBlockLayer(this));
      this.shadowRadius = 0.35F;
   }

   public RenderType getRenderType(MilimEntity entity, ResourceLocation texture, MultiBufferSource source, float partial) {
      return demoAlpha(entity, partial) < 0.999F ? RenderType.entityTranslucentCull(texture) : RenderType.entityCutout(texture);
   }

   public Color getRenderColor(MilimEntity entity, float partial, int light) {
      return Color.ofRGBA(1.0F, 1.0F, 1.0F, demoAlpha(entity, partial));
   }

   private static float demoAlpha(MilimEntity entity, float partial) {
      float alpha = 1.0F;
      if (entity.isDemo()) {
         alpha = BurstTimeline.smooth((entity.tickCount + partial) / 6.0F);
         if (entity.action() == 3) {
            alpha *= 1.0F - BurstTimeline.smooth((entity.actionTicks(partial) - 242.0F) / 8.0F);
         }
      }

      return alpha;
   }

   protected float getDeathMaxRotation(MilimEntity entity) {
      return 0.0F;
   }

   public boolean shouldRender(MilimEntity entity, Frustum frustum, double x, double y, double z) {
      return super.shouldRender(entity, frustum, x, y, z) || entity.action() == 3 && frustum.isVisible(entity.getBoundingBox().inflate(4.0));
   }

   public void render(MilimEntity entity, float yaw, float partial, PoseStack pose, MultiBufferSource source, int light) {
      super.render(entity, yaw, partial, pose, source, light);
      if (entity.action() == 3 && entity.isAlive()) {
         float t = entity.actionTicks(partial);
         if (!(t < 24.0F) && !(t > 234.0F)) {
            Vec3 forward = Vec3.directionFromRotation(0.0F, Mth.rotLerp(partial, entity.yRotO, entity.getYRot()));
            Vec3 overhead = new Vec3(0.0, 2.22, 0.0).add(forward.scale(0.22));
            Vec3 hand = new Vec3(0.0, 1.45, 0.0).add(forward.scale(0.75));
            float lower = BurstTimeline.smooth((t - 83.6F) / 5.0F);
            Vec3 center = overhead.lerp(hand, lower);
            float power = BurstTimeline.charge(t);
            float fade = 1.0F - BurstTimeline.smooth((t - 216.0F) / 18.0F);
            float pulse = 1.0F + 0.05F * Mth.sin(t * 1.4F);
            float radius = (t < 88.6F ? 0.035F + 0.28F * power : 0.23F) * pulse;
            float alpha = Math.min(1.0F, (t - 24.0F) / 5.0F) * fade;
            VfxGeometry.orb(pose, source, center, radius, alpha);
            VfxGeometry.star(pose, source, center, this.entityRenderDispatcher.cameraOrientation(), radius * 2.2F, alpha * 0.8F);
            if (t < 88.6F) {
               for (int i = 0; i < 24; i++) {
                  float phase = (t * 0.032F + i / 24.0F) % 1.0F;
                  double angle = i * 2.399963 + t * 0.045;
                  float r = (1.0F - phase) * (1.3F + power * 0.55F);
                  Vec3 offset = new Vec3(Math.cos(angle) * r, Math.sin(angle * 1.7) * r * 0.8, Math.sin(angle) * r);
                  Vec3 a = center.add(offset);
                  Vec3 b = center.add(offset.scale(0.78));
                  float strength = Mth.sin(phase * (float) Math.PI) * alpha;
                  VfxGeometry.glowSpike(pose, source, a, b, 0.018F + power * 0.012F, strength);
               }

               VfxGeometry.ring(pose, source, center, new Vec3(0.3, 1.0, 0.2).normalize(), radius * 1.8F, 0.01F, t * 0.06F, alpha * 0.35F);
            } else {
               float since = t - 88.6F;
               float flash = 1.0F - Mth.clamp(since / 8.0F, 0.0F, 1.0F);
               if (flash > 0.0F) {
                  VfxGeometry.star(pose, source, center, this.entityRenderDispatcher.cameraOrientation(), 0.8F + (1.0F - flash) * 2.0F, flash);
                  VfxGeometry.ring(pose, source, center, forward, (1.0F - flash) * 3.0F + 0.2F, 0.045F, 0.0F, flash * 0.6F);
               }

               VfxGeometry.ring(pose, source, center, forward, 0.42F + 0.07F * Mth.sin(t * 0.5F), 0.015F, t * 0.08F, alpha * 0.5F);
            }
         }
      }
   }
}
