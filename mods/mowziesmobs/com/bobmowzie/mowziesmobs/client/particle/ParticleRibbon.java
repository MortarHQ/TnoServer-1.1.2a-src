package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.particle.types.AdvancedParticleType;
import com.bobmowzie.mowziesmobs.client.particle.types.RibbonParticleType;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleRotation;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonComponent;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class ParticleRibbon extends AdvancedParticleBase {
   public Vec3[] positions;
   public Vec3[] prevPositions;
   public float texPanOffset;

   protected ParticleRibbon(
      ClientLevel worldIn,
      double xCoordIn,
      double yCoordIn,
      double zCoordIn,
      double motionX,
      double motionY,
      double motionZ,
      ParticleRotation rotation,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive,
      int length,
      ParticleComponent[] components
   ) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, motionX, motionY, motionZ, rotation, scale, r, g, b, a, drag, duration, emissive, false, components);
      this.positions = new Vec3[length];
      this.prevPositions = new Vec3[length];
      if (this.positions.length >= 1) {
         this.positions[0] = new Vec3(this.getPosX(), this.getPosY(), this.getPosZ());
      }

      if (this.prevPositions.length >= 1) {
         this.prevPositions[0] = this.getPrevPos();
      }
   }

   @Override
   protected void updatePosition() {
      super.updatePosition();
   }

   @Override
   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      this.alpha = this.prevAlpha + (this.alpha - this.prevAlpha) * partialTicks;
      if (this.alpha < 0.01) {
         this.alpha = 0.01F;
      }

      this.rCol = this.prevRed + (this.red - this.prevRed) * partialTicks;
      this.gCol = this.prevGreen + (this.green - this.prevGreen) * partialTicks;
      this.bCol = this.prevBlue + (this.blue - this.prevBlue) * partialTicks;
      this.particleScale = this.prevScale + (this.scale - this.prevScale) * partialTicks;

      for (ParticleComponent component : this.components) {
         component.preRender(this, partialTicks);
      }

      int j = this.getLightColor(partialTicks);
      float r = this.rCol;
      float g = this.gCol;
      float b = this.bCol;
      float a = this.alpha;
      float scale = this.particleScale;
      float prevR = r;
      float prevG = g;
      float prevB = b;
      float prevA = a;
      float prevScale = scale;

      for (ParticleComponent component : this.components) {
         if (component instanceof RibbonComponent.PropertyOverLength pOverLength) {
            float value = pOverLength.evaluate(0.0F);
            if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE) {
               prevScale *= value;
            } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.RED) {
               prevR *= value;
            } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.GREEN) {
               prevG *= value;
            } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.BLUE) {
               prevB *= value;
            } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.ALPHA) {
               prevA *= value;
            }
         }
      }

      Vec3 offsetDir = new Vec3(0.0, 0.0, 0.0);

      for (int index = 0; index < this.positions.length - 1; index++) {
         if (this.positions[index] != null && this.positions[index + 1] != null) {
            r = this.rCol;
            g = this.gCol;
            b = this.bCol;
            scale = this.particleScale;
            float t = (index + 1.0F) / (this.positions.length - 1.0F);
            float tPrev = index / (this.positions.length - 1.0F);

            for (ParticleComponent component : this.components) {
               if (component instanceof RibbonComponent.PropertyOverLength pOverLength) {
                  float value = pOverLength.evaluate(t);
                  if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE) {
                     scale *= value;
                  } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.RED) {
                     r *= value;
                  } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.GREEN) {
                     g *= value;
                  } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.BLUE) {
                     b *= value;
                  } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.ALPHA) {
                     a *= value;
                  }
               }
            }

            Vec3 Vector3d = renderInfo.getPosition();
            Vec3 p1 = this.prevPositions[index].add(this.positions[index].subtract(this.prevPositions[index]).scale(partialTicks)).subtract(Vector3d);
            Vec3 p2 = this.prevPositions[index + 1]
               .add(this.positions[index + 1].subtract(this.prevPositions[index + 1]).scale(partialTicks))
               .subtract(Vector3d);
            if (index == 0) {
               Vec3 moveDir = p2.subtract(p1).normalize();
               if (this.rotation instanceof ParticleRotation.FaceCamera) {
                  Vec3 viewVec = new Vec3(renderInfo.getLookVector());
                  offsetDir = moveDir.cross(viewVec).normalize();
               } else {
                  offsetDir = moveDir.cross(new Vec3(0.0, 1.0, 0.0)).normalize();
               }

               offsetDir = offsetDir.scale(prevScale);
            }

            Vec3[] aVector3d2 = new Vec3[]{offsetDir.scale(-1.0), offsetDir, null, null};
            Vec3 moveDir = p2.subtract(p1).normalize();
            if (this.rotation instanceof ParticleRotation.FaceCamera) {
               Vec3 viewVec = new Vec3(renderInfo.getLookVector());
               offsetDir = moveDir.cross(viewVec).normalize();
            } else {
               offsetDir = moveDir.cross(new Vec3(0.0, 1.0, 0.0)).normalize();
            }

            offsetDir = offsetDir.scale(scale);
            aVector3d2[2] = offsetDir;
            aVector3d2[3] = offsetDir.scale(-1.0);
            Vector4f[] vertices2 = new Vector4f[]{
               new Vector4f((float)aVector3d2[0].x, (float)aVector3d2[0].y, (float)aVector3d2[0].z, 1.0F),
               new Vector4f((float)aVector3d2[1].x, (float)aVector3d2[1].y, (float)aVector3d2[1].z, 1.0F),
               new Vector4f((float)aVector3d2[2].x, (float)aVector3d2[2].y, (float)aVector3d2[2].z, 1.0F),
               new Vector4f((float)aVector3d2[3].x, (float)aVector3d2[3].y, (float)aVector3d2[3].z, 1.0F)
            };
            Matrix4f boxTranslate = new Matrix4f().translate((float)p1.x, (float)p1.y, (float)p1.z);
            vertices2[0].mul(boxTranslate);
            vertices2[1].mul(boxTranslate);
            boxTranslate = new Matrix4f().translate((float)p2.x, (float)p2.y, (float)p2.z);
            vertices2[2].mul(boxTranslate);
            vertices2[3].mul(boxTranslate);
            float halfU = (this.getU1() - this.getU0()) / 2.0F + this.getU0();
            float f = this.getU0() + this.texPanOffset;
            float f1 = halfU + this.texPanOffset;
            float f2 = this.getV0();
            float f3 = this.getV1();
            buffer.addVertex(vertices2[0].x(), vertices2[0].y(), vertices2[0].z()).setUv(f1, f3).setColor(prevR, prevG, prevB, prevA).setLight(j);
            buffer.addVertex(vertices2[1].x(), vertices2[1].y(), vertices2[1].z()).setUv(f1, f2).setColor(prevR, prevG, prevB, prevA).setLight(j);
            buffer.addVertex(vertices2[2].x(), vertices2[2].y(), vertices2[2].z()).setUv(f, f2).setColor(r, g, b, a).setLight(j);
            buffer.addVertex(vertices2[3].x(), vertices2[3].y(), vertices2[3].z()).setUv(f, f3).setColor(r, g, b, a).setLight(j);
            prevR = r;
            prevG = g;
            prevB = b;
            prevA = a;
         }
      }

      for (ParticleComponent component : this.components) {
         component.postRender(this, buffer, renderInfo, partialTicks, j);
      }
   }

   public AABB getBoundingBox() {
      if (this.positions != null && this.positions.length > 0 && this.positions[0] != null) {
         double minX = this.positions[0].x() - 0.1;
         double minY = this.positions[0].y() - 0.1;
         double minZ = this.positions[0].z() - 0.1;
         double maxX = this.positions[0].x() + 0.1;
         double maxY = this.positions[0].y() + 0.1;
         double maxZ = this.positions[0].z() + 0.1;

         for (Vec3 pos : this.positions) {
            if (pos != null) {
               minX = Math.min(minX, pos.x());
               minY = Math.min(minY, pos.y());
               minZ = Math.min(minZ, pos.z());
               maxX = Math.max(maxX, pos.x());
               maxY = Math.max(maxY, pos.y());
               maxZ = Math.max(maxZ, pos.z());
            }
         }

         return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
      } else {
         return super.getBoundingBox();
      }
   }

   public float getMinUPublic() {
      return this.getU0();
   }

   public float getMaxUPublic() {
      return this.getU1();
   }

   public float getMinVPublic() {
      return this.getV0();
   }

   public float getMaxVPublic() {
      return this.getV1();
   }

   public static void spawnRibbon(
      Level world,
      Holder<ParticleType<?>> particle,
      int length,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      boolean faceCamera,
      double yaw,
      double pitch,
      double roll,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive
   ) {
      spawnRibbon(
         world,
         particle,
         length,
         x,
         y,
         z,
         motionX,
         motionY,
         motionZ,
         faceCamera,
         yaw,
         pitch,
         roll,
         scale,
         r,
         g,
         b,
         a,
         drag,
         duration,
         emissive,
         new ParticleComponent[0]
      );
   }

   public static void spawnRibbon(
      Level world,
      Holder<ParticleType<?>> particle,
      int length,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      boolean faceCamera,
      double yaw,
      double pitch,
      double roll,
      double scale,
      double red,
      double green,
      double blue,
      double alpha,
      double airDrag,
      double duration,
      boolean emissive,
      ParticleComponent[] components
   ) {
      ParticleRotation rotation = faceCamera ? new ParticleRotation.FaceCamera(0.0F) : new ParticleRotation.EulerAngles((float)yaw, (float)pitch, (float)roll);
      AdvancedParticleType base = new AdvancedParticleType(
         particle, rotation, components, (float)red, (float)green, (float)blue, (float)alpha, (float)scale, (float)duration, (float)airDrag, emissive, false
      );
      world.addParticle(new RibbonParticleType(base, length), x, y, z, motionX, motionY, motionZ);
   }

   public static final class Provider implements ParticleProvider<RibbonParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(RibbonParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ParticleRibbon particle = new ParticleRibbon(
            worldIn,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            typeIn.rotation(),
            typeIn.scale(),
            typeIn.red(),
            typeIn.green(),
            typeIn.blue(),
            typeIn.alpha(),
            typeIn.airDrag(),
            typeIn.duration(),
            typeIn.emissive(),
            typeIn.length(),
            typeIn.components()
         );
         particle.setSpriteFromAge(this.spriteSet);
         return particle;
      }
   }
}
