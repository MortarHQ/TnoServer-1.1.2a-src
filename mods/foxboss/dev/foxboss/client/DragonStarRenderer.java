package dev.foxboss.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.foxboss.entity.DragonStarEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class DragonStarRenderer extends EntityRenderer<DragonStarEntity> {
   public DragonStarRenderer(Context context) {
      super(context);
   }

   public boolean shouldRender(DragonStarEntity star, Frustum frustum, double x, double y, double z) {
      return star.distanceToSqr(x, y, z) < 65536.0;
   }

   public void render(DragonStarEntity star, float yaw, float partial, PoseStack pose, MultiBufferSource source, int light) {
      float age = star.age(partial);
      if (!(age < 0.0F) && !(age > star.travelDuration() + 10)) {
         float progress = Mth.clamp(age / star.travelDuration(), 0.0F, 1.0F);
         float endFade = 1.0F - Mth.clamp((age - star.travelDuration()) / 7.0F, 0.0F, 1.0F);
         Vec3 origin = star.position();
         if (endFade > 0.0F) {
            float tail = Math.max(0.0F, progress - 0.85F);
            Vec3 head = star.point(progress, partial).subtract(origin);
            Vec3 last = star.point(tail, partial).subtract(origin);
            float lastWidth = 0.0F;

            for (int i = 1; i <= 36; i++) {
               float p = Mth.lerp(i / 36.0F, tail, progress);
               Vec3 next = star.point(p, partial).subtract(origin);
               float taper = (float)Math.sin(i / 36.0F * Math.PI * 0.65);
               float tipTaper = i == 36 ? 0.0F : Mth.clamp((float)next.distanceTo(head) / 0.9F, 0.0F, 1.0F);
               float width = 0.027F * taper * tipTaper;
               VfxGeometry.glowBeam(pose, source, last, next, lastWidth, width, taper * endFade);
               last = next;
               lastWidth = width;
            }
         }
      }
   }

   public ResourceLocation getTextureLocation(DragonStarEntity entity) {
      return ResourceLocation.withDefaultNamespace("textures/misc/white.png");
   }
}
