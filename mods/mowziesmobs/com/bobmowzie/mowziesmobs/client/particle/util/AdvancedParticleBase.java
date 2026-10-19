package com.bobmowzie.mowziesmobs.client.particle.util;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.client.particle.ParticleRibbon;
import com.bobmowzie.mowziesmobs.client.particle.types.AdvancedParticleType;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class AdvancedParticleBase extends TextureSheetParticle {
   public boolean doRender;
   public float airDrag;
   public float red;
   public float green;
   public float blue;
   public float alpha;
   public float prevRed;
   public float prevGreen;
   public float prevBlue;
   public float prevAlpha;
   public float scale;
   public float prevScale;
   public float particleScale;
   public ParticleRotation rotation;
   public boolean emissive;
   public double prevMotionX;
   public double prevMotionY;
   public double prevMotionZ;
   public ParticleRenderType renderType = MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   public ParticleComponent[] components;
   public ParticleRibbon ribbon;
   public boolean isScreenSpace;
   public float screenX;
   public float screenY;
   public float screenZ;
   public float screenXo;
   public float screenYo;
   public float screenZo;

   protected AdvancedParticleBase(
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
      boolean canCollide,
      ParticleComponent[] components
   ) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      this.xd = motionX;
      this.yd = motionY;
      this.zd = motionZ;
      this.red = (float)r;
      this.green = (float)g;
      this.blue = (float)b;
      this.alpha = (float)a;
      this.scale = (float)scale;
      this.lifetime = (int)duration;
      this.airDrag = (float)drag;
      this.rotation = rotation;
      this.components = components;
      this.emissive = emissive;
      this.ribbon = null;
      this.doRender = true;

      for (ParticleComponent component : components) {
         component.init(this);
      }

      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.prevRed = this.red;
      this.prevGreen = this.green;
      this.prevBlue = this.blue;
      this.prevAlpha = this.alpha;
      this.rotation.setPrevValues();
      this.prevScale = this.scale;
      this.hasPhysics = canCollide;
      this.gravity = 0.0F;
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return this.renderType;
   }

   public void setSprite(TextureAtlasSprite sprite) {
      super.setSprite(sprite);
   }

   public int getLightColor(float partialTick) {
      int i = super.getLightColor(partialTick);
      if (this.emissive) {
         int k = i >> 16 & 0xFF;
         return 240 | k << 16;
      } else {
         return i;
      }
   }

   public ClientLevel getLevel() {
      return this.level;
   }

   public void tick() {
      this.prevRed = this.red;
      this.prevGreen = this.green;
      this.prevBlue = this.blue;
      this.prevAlpha = this.alpha;
      this.prevScale = this.scale;
      this.rotation.setPrevValues();
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.prevMotionX = this.xd;
      this.prevMotionY = this.yd;
      this.prevMotionZ = this.zd;

      for (ParticleComponent component : this.components) {
         component.preUpdate(this);
      }

      if (this.age++ >= this.lifetime) {
         this.remove();
      }

      this.updatePosition();

      for (ParticleComponent component : this.components) {
         component.postUpdate(this);
      }

      if (this.ribbon != null) {
         this.ribbon.setPos(this.x, this.y, this.z);
         this.ribbon.positions[0] = new Vec3(this.x, this.y, this.z);
         this.ribbon.prevPositions[0] = this.getPrevPos();
      }
   }

   protected void updatePosition() {
      this.yd = this.yd - 0.04 * this.gravity;
      this.move(this.xd, this.yd, this.zd);
      if (this.onGround && this.hasPhysics) {
         this.xd *= 0.7F;
         this.zd *= 0.7F;
      }

      this.xd = this.xd * this.airDrag;
      this.yd = this.yd * this.airDrag;
      this.zd = this.zd * this.airDrag;
   }

   public AABB getRenderBoundingBox(float partialTicks) {
      return this.isScreenSpace ? AABB.INFINITE : super.getRenderBoundingBox(partialTicks);
   }

   public void setGravity(float gravity) {
      this.gravity = gravity;
   }

   public float getQuadSize(float scaleFactor) {
      return this.quadSize * this.scale;
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      if (!this.isScreenSpace || Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
         this.alpha = this.prevAlpha + (this.alpha - this.prevAlpha) * partialTicks;
         this.rCol = this.prevRed + (this.red - this.prevRed) * partialTicks;
         this.gCol = this.prevGreen + (this.green - this.prevGreen) * partialTicks;
         this.bCol = this.prevBlue + (this.blue - this.prevBlue) * partialTicks;
         this.particleScale = this.prevScale + (this.scale - this.prevScale) * partialTicks;

         for (ParticleComponent component : this.components) {
            component.preRender(this, partialTicks);
         }

         if (this.alpha < 0.01) {
            this.alpha = 0.0F;
         }

         if (this.doRender) {
            Quaternionf quaternion = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
            if (this.rotation instanceof ParticleRotation.FaceCamera faceCameraRot) {
               if (faceCameraRot.angle == 0.0F && faceCameraRot.prevAngle == 0.0F) {
                  quaternion = renderInfo.rotation();
               } else {
                  quaternion = new Quaternionf(renderInfo.rotation());
                  float f3 = Mth.lerp(partialTicks, faceCameraRot.prevAngle, faceCameraRot.angle);
                  quaternion.mul(Axis.ZP.rotation(f3));
               }
            } else if (this.rotation instanceof ParticleRotation.EulerAngles eulerRot) {
               float rotX = eulerRot.prevPitch + (eulerRot.pitch - eulerRot.prevPitch) * partialTicks;
               float rotY = eulerRot.prevYaw + (eulerRot.yaw - eulerRot.prevYaw) * partialTicks;
               float rotZ = eulerRot.prevRoll + (eulerRot.roll - eulerRot.prevRoll) * partialTicks;
               Quaternionf quatX = MathUtils.quatFromRotationXYZ(rotX, 0.0F, 0.0F, false);
               Quaternionf quatY = MathUtils.quatFromRotationXYZ(0.0F, rotY, 0.0F, false);
               Quaternionf quatZ = MathUtils.quatFromRotationXYZ(0.0F, 0.0F, rotZ, false);
               quaternion.mul(quatZ);
               quaternion.mul(quatY);
               quaternion.mul(quatX);
            }

            if (this.rotation instanceof ParticleRotation.OrientVector orientRot) {
               double x = orientRot.prevOrientation.x + (orientRot.orientation.x - orientRot.prevOrientation.x) * partialTicks;
               double y = orientRot.prevOrientation.y + (orientRot.orientation.y - orientRot.prevOrientation.y) * partialTicks;
               double z = orientRot.prevOrientation.z + (orientRot.orientation.z - orientRot.prevOrientation.z) * partialTicks;
               float pitch = (float)Math.asin(-y);
               float yaw = (float)Mth.atan2(x, z);
               Quaternionf quatX = MathUtils.quatFromRotationXYZ(pitch, 0.0F, 0.0F, false);
               Quaternionf quatY = MathUtils.quatFromRotationXYZ(0.0F, yaw, 0.0F, false);
               quaternion.mul(quatY);
               quaternion.mul(quatX);
            }

            Vector3f[] avector3f = new Vector3f[]{
               new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
            };
            float f4 = this.particleScale * 0.1F;
            if (this.isScreenSpace) {
               this.applyTransformToVerticesScreenSpace(renderInfo, partialTicks, avector3f, quaternion, f4);
            } else {
               this.applyTransformToVertices(renderInfo, partialTicks, avector3f, quaternion, f4);
            }

            float f7 = this.getU0();
            float f8 = this.getU1();
            float f5 = this.getV0();
            float f6 = this.getV1();
            int j = this.getLightColor(partialTicks);
            buffer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z())
               .setUv(f8, f6)
               .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
               .setLight(j);
            buffer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z())
               .setUv(f8, f5)
               .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
               .setLight(j);
            buffer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z())
               .setUv(f7, f5)
               .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
               .setLight(j);
            buffer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z())
               .setUv(f7, f6)
               .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
               .setLight(j);

            for (ParticleComponent component : this.components) {
               component.postRender(this, buffer, renderInfo, partialTicks, j);
            }
         }
      }
   }

   protected void applyTransformToVertices(Camera renderInfo, float partialTicks, Vector3f[] avector3f, Quaternionf quaternion, float scale) {
      Vec3 vector3d = renderInfo.getPosition();
      float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vector3d.x());
      float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vector3d.y());
      float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vector3d.z());

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         quaternion.transform(vector3f);
         vector3f.mul(scale);
         vector3f.add(f, f1, f2);
      }
   }

   protected void applyTransformToVerticesScreenSpace(Camera renderInfo, float partialTicks, Vector3f[] avector3f, Quaternionf quaternion, float scale) {
      PoseStack posestack = new PoseStack();
      posestack.mulPose(renderInfo.rotation());
      posestack.scale(1.0F, 1.0F, -1.0F);
      posestack.translate(0.0F, -1.101F, 1.5F);
      float f = Mth.lerp(partialTicks, this.screenXo, this.screenX);
      float f1 = Mth.lerp(partialTicks, this.screenYo, this.screenY);
      float f2 = Mth.lerp(partialTicks, this.screenZo, this.screenZ);
      posestack.translate(f, f1, f2);

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         vector3f.mul(scale);
         Vector4f vec4 = new Vector4f(vector3f.x, vector3f.y, vector3f.z, 1.0F);
         vec4.mul(posestack.last().pose());
         vector3f.set(vec4.x, vec4.y, vec4.z);
      }
   }

   public float getAge() {
      return this.age;
   }

   public double getPosX() {
      return this.x;
   }

   public void setPosX(double posX) {
      this.setPos(posX, this.y, this.z);
   }

   public double getPosY() {
      return this.y;
   }

   public void setPosY(double posY) {
      this.setPos(this.x, posY, this.z);
   }

   public double getPosZ() {
      return this.z;
   }

   public void setPosZ(double posZ) {
      this.setPos(this.x, this.y, posZ);
   }

   public double getMotionX() {
      return this.xd;
   }

   public void setMotionX(double motionX) {
      this.xd = motionX;
   }

   public double getMotionY() {
      return this.yd;
   }

   public void setMotionY(double motionY) {
      this.yd = motionY;
   }

   public double getMotionZ() {
      return this.zd;
   }

   public void setMotionZ(double motionZ) {
      this.zd = motionZ;
   }

   public Vec3 getPrevPos() {
      return new Vec3(this.xo, this.yo, this.zo);
   }

   public double getPrevPosX() {
      return this.xo;
   }

   public double getPrevPosY() {
      return this.yo;
   }

   public double getPrevPosZ() {
      return this.zo;
   }

   public static void spawnParticle(
      Level world,
      Holder<ParticleType<?>> particle,
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
      double faceCameraAngle,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive,
      boolean canCollide
   ) {
      spawnParticle(
         world,
         particle,
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
         faceCameraAngle,
         scale,
         r,
         g,
         b,
         a,
         drag,
         duration,
         emissive,
         canCollide,
         new ParticleComponent[0]
      );
   }

   public static void spawnParticle(
      Level world,
      Holder<ParticleType<?>> particle,
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
      double faceCameraAngle,
      double scale,
      double red,
      double green,
      double blue,
      double alpha,
      double airDrag,
      double duration,
      boolean emissive,
      boolean canCollide,
      ParticleComponent[] components
   ) {
      ParticleRotation rotation = faceCamera
         ? new ParticleRotation.FaceCamera((float)faceCameraAngle)
         : new ParticleRotation.EulerAngles((float)yaw, (float)pitch, (float)roll);
      world.addParticle(
         new AdvancedParticleType(
            particle,
            rotation,
            components,
            (float)red,
            (float)green,
            (float)blue,
            (float)alpha,
            (float)scale,
            (float)duration,
            (float)airDrag,
            emissive,
            canCollide
         ),
         x,
         y,
         z,
         motionX,
         motionY,
         motionZ
      );
   }

   public static void spawnParticle(
      Level world,
      Holder<ParticleType<?>> particle,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      ParticleRotation rotation,
      double scale,
      double red,
      double green,
      double blue,
      double alpha,
      double airDrag,
      double duration,
      boolean emissive,
      boolean canCollide,
      ParticleComponent[] components
   ) {
      world.addParticle(
         new AdvancedParticleType(
            particle,
            rotation,
            components,
            (float)red,
            (float)green,
            (float)blue,
            (float)alpha,
            (float)scale,
            (float)duration,
            (float)airDrag,
            emissive,
            canCollide
         ),
         x,
         y,
         z,
         motionX,
         motionY,
         motionZ
      );
   }

   public static void spawnAlwaysVisibleParticle(
      Level world,
      Holder<ParticleType<?>> particle,
      double distanceLimit,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      ParticleRotation rotation,
      double scale,
      double red,
      double green,
      double blue,
      double alpha,
      double airDrag,
      double duration,
      boolean emissive,
      boolean canCollide,
      ParticleComponent[] components
   ) {
      Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
      boolean overrideLimiter = camera.getPosition().distanceToSqr(x, y, z) < distanceLimit * distanceLimit;
      world.addAlwaysVisibleParticle(
         new AdvancedParticleType(
            particle,
            rotation,
            components,
            (float)red,
            (float)green,
            (float)blue,
            (float)alpha,
            (float)scale,
            (float)duration,
            (float)airDrag,
            emissive,
            canCollide
         ),
         overrideLimiter,
         x,
         y,
         z,
         motionX,
         motionY,
         motionZ
      );
   }

   public static void spawnAlwaysVisibleParticle(
      Level world,
      Holder<ParticleType<?>> particle,
      double distanceLimit,
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
      double faceCameraAngle,
      double scale,
      double red,
      double green,
      double blue,
      double alpha,
      double airDrag,
      double duration,
      boolean emissive,
      boolean canCollide,
      ParticleComponent[] components
   ) {
      Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
      boolean overrideLimiter = camera.getPosition().distanceToSqr(x, y, z) < distanceLimit * distanceLimit;
      ParticleRotation rotation = faceCamera
         ? new ParticleRotation.FaceCamera((float)faceCameraAngle)
         : new ParticleRotation.EulerAngles((float)yaw, (float)pitch, (float)roll);
      world.addAlwaysVisibleParticle(
         new AdvancedParticleType(
            particle,
            rotation,
            components,
            (float)red,
            (float)green,
            (float)blue,
            (float)alpha,
            (float)scale,
            (float)duration,
            (float)airDrag,
            emissive,
            canCollide
         ),
         overrideLimiter,
         x,
         y,
         z,
         motionX,
         motionY,
         motionZ
      );
   }

   public static class Factory implements ParticleProvider<AdvancedParticleType> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         AdvancedParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         AdvancedParticleBase particle = new AdvancedParticleBase(
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
            typeIn.canCollide(),
            typeIn.components()
         );
         particle.setColor(typeIn.red(), typeIn.green(), typeIn.blue());
         particle.pickSprite(this.spriteSet);
         return particle;
      }
   }
}
