package de.ellpeck.naturesaura.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collections;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ParticleMagic extends Particle {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/particles/magic_round.png");
   private final float desiredScale;
   private final boolean fade;
   private final boolean depth;
   private float particleScale;

   public ParticleMagic(
      ClientLevel level,
      double posX,
      double posY,
      double posZ,
      double motionX,
      double motionY,
      double motionZ,
      int color,
      float scale,
      int maxAge,
      float gravity,
      boolean collision,
      boolean fade,
      boolean depth
   ) {
      super(level, posX, posY, posZ);
      this.desiredScale = scale;
      this.lifetime = maxAge;
      this.hasPhysics = collision;
      this.gravity = gravity;
      this.fade = fade;
      this.depth = depth;
      this.xd = motionX;
      this.yd = motionY;
      this.zd = motionZ;
      float r = (color >> 16 & 0xFF) / 255.0F * (1.0F - this.random.nextFloat() * 0.25F);
      float g = (color >> 8 & 0xFF) / 255.0F * (1.0F - this.random.nextFloat() * 0.25F);
      float b = (color & 0xFF) / 255.0F * (1.0F - this.random.nextFloat() * 0.25F);
      this.setColor(r, g, b);
      this.alpha = 1.0F;
      this.particleScale = 0.0F;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.age++;
      if (this.age > this.lifetime) {
         this.remove();
      } else {
         this.yd = this.yd - 0.04 * this.gravity;
         this.move(this.xd, this.yd, this.zd);
         float lifeRatio = (float)this.age / this.lifetime;
         if (this.fade && lifeRatio > 0.75F) {
            this.alpha = 1.0F - (lifeRatio - 0.75F) / 0.25F;
         }

         if (lifeRatio <= 0.25F) {
            this.particleScale = this.desiredScale * (lifeRatio / 0.25F);
         } else if (this.fade) {
            this.particleScale = this.desiredScale * (1.0F - (lifeRatio - 0.25F) / 0.75F);
         }
      }
   }

   public void move(double x, double y, double z) {
      double lastY = y;
      if (this.hasPhysics && (x != 0.0 || y != 0.0 || z != 0.0)) {
         Vec3 motion = Entity.collideBoundingBox(null, new Vec3(x, y, z), this.getBoundingBox(), this.level, Collections.emptyList());
         x = motion.x;
         y = motion.y;
         z = motion.z;
      }

      if (x != 0.0 || y != 0.0 || z != 0.0) {
         this.setBoundingBox(this.getBoundingBox().move(x, y, z));
         this.setLocationFromBoundingbox();
      }

      this.onGround = lastY != y && lastY < 0.0;
      if (this.onGround) {
         this.xd = 0.0;
         this.zd = 0.0;
      }
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      Vec3 vec3d = renderInfo.getPosition();
      float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vec3d.x);
      float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vec3d.y);
      float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vec3d.z);
      Quaternionf quaternion = renderInfo.rotation();
      Vector3f[] avector3f = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float f4 = 0.1F * this.particleScale;

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         vector3f.rotate(quaternion);
         vector3f.mul(f4);
         vector3f.add(f, f1, f2);
      }

      int j = this.getLightColor(partialTicks);
      buffer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z())
         .setUv(0.0F, 1.0F)
         .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z())
         .setUv(1.0F, 1.0F)
         .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z())
         .setUv(1.0F, 0.0F)
         .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z())
         .setUv(0.0F, 0.0F)
         .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
         .setLight(j);
   }

   public ParticleRenderType getRenderType() {
      return this.depth ? ParticleHandler.MAGIC : ParticleHandler.MAGIC_NO_DEPTH;
   }

   protected int getLightColor(float p_107249_) {
      return 15728880;
   }
}
