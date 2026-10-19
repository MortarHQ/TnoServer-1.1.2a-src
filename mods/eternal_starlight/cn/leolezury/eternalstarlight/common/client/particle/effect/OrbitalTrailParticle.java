package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.visual.TrailRenderer;
import cn.leolezury.eternalstarlight.common.particle.OrbitalTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class OrbitalTrailParticle extends TextureSheetParticle {
   private final TrailEffect effect = new TrailEffect(0.125F, 4.0F);
   private final int fromColor;
   private final int toColor;
   private final float radius;
   private final float rotSpeed;
   private final Entity owner;
   private double cx;
   private double cy;
   private double cz;
   private float yaw;

   protected OrbitalTrailParticle(
      ClientLevel level,
      double x,
      double y,
      double z,
      double xSpeed,
      double ySpeed,
      double zSpeed,
      int lifetime,
      int owner,
      int fromColor,
      int toColor,
      float radius,
      float rotSpeed,
      float alpha,
      SpriteSet spriteSet
   ) {
      super(level, x, y, z);
      this.lifetime = lifetime;
      this.xd = xSpeed;
      this.yd = ySpeed;
      this.zd = zSpeed;
      this.fromColor = fromColor;
      this.toColor = toColor;
      this.radius = radius;
      this.rotSpeed = rotSpeed;
      this.alpha = alpha;
      this.cx = x;
      this.cy = y;
      this.cz = z;
      this.owner = level.getEntity(owner);
      this.pickSprite(spriteSet);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.cx = this.cx + this.xd;
      this.cy = this.cy + this.yd;
      this.cz = this.cz + this.zd;
      if (this.owner != null) {
         this.cx = this.owner.getX();
         this.cz = this.owner.getZ();
      }

      this.yaw = this.yaw + this.rotSpeed;
      this.yaw = Mth.wrapDegrees(this.yaw);
      Vec3 pos = ESMathUtil.rotationToPosition(new Vec3(this.cx, this.cy, this.cz), this.radius, 0.0F, this.yaw);
      this.x = pos.x();
      this.y = pos.y();
      this.z = pos.z();
      if (this.age > 0) {
         this.effect.update(new Vec3(this.xo, this.yo, this.zo));
      }

      if (this.age++ >= this.lifetime) {
         this.remove();
      }
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
      float progress = Math.min(this.age + partialTicks, this.lifetime) / this.lifetime;
      Color color = Color.rgbi(
         (int)Mth.lerp(progress, Color.rgb(this.fromColor).r(), Color.rgb(this.toColor).r()),
         (int)Mth.lerp(progress, Color.rgb(this.fromColor).g(), Color.rgb(this.toColor).g()),
         (int)Mth.lerp(progress, Color.rgb(this.fromColor).b(), Color.rgb(this.toColor).b())
      );
      float a = Mth.lerp((float)(Math.abs(progress - 0.5) * 2.0), this.alpha, 0.0F);
      PoseStack stack = new PoseStack();
      float x = (float)Mth.lerp(partialTicks, this.xo, this.x);
      float y = (float)Mth.lerp(partialTicks, this.yo, this.y);
      float z = (float)Mth.lerp(partialTicks, this.zo, this.z);
      stack.pushPose();
      stack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
      this.effect.prepareRender(new Vec3(x, y, z), partialTicks);
      TrailRenderer.render(
         this.effect,
         ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_ADDITIVE_GLOW),
         stack,
         TrailEffect.TrailOffsetFunction.FACE_CAMERA,
         false,
         true,
         color.rf(),
         color.gf(),
         color.bf(),
         a * 5.0F,
         this.getU0(),
         this.getU1(),
         this.getV0(),
         this.getV1(),
         15728880
      );
      stack.popPose();
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public static class Provider implements ParticleProvider<OrbitalTrailParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(
         OrbitalTrailParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new OrbitalTrailParticle(
            level,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            options.lifetime(),
            options.owner(),
            Color.rgbd(options.fromColor().x, options.fromColor().y, options.fromColor().z).rgb(),
            Color.rgbd(options.toColor().x, options.toColor().y, options.toColor().z).rgb(),
            options.radius(),
            options.rotSpeed(),
            options.alpha(),
            this.sprites
         );
      }
   }
}
