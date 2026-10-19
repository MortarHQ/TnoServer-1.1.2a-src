package net.unusual.block_factorys_bosses.client.particle.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.BRParticleRenderType;
import net.unusual.block_factorys_bosses.entity.IParticleAttachment;
import net.unusual.block_factorys_bosses.geckolib.util.ParticleLocator;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class SubParticleEmitter extends TextureSheetParticle {
   protected final List<SubParticle> proxies = new ArrayList<>();
   protected final SpriteSet set;
   protected final float randomValue;
   @Nullable
   public SubParticleEmitter.BoneAttachment<?> attachment = null;

   protected SubParticleEmitter(ClientLevel level, SpriteSet spriteSet, Vec3 vec3) {
      super(level, vec3.x, vec3.y, vec3.z);
      this.lifetime = 1;
      this.pickSprite(spriteSet);
      this.set = spriteSet;
      this.randomValue = Mth.randomBetween(this.random, 0.0F, 1.0F);
   }

   public boolean shouldAttach() {
      return false;
   }

   protected abstract int getSubParticleLifeTime();

   public AABB getRenderBoundingBox(float partialTicks) {
      return AABB.INFINITE;
   }

   public List<SubParticle> getProxies() {
      return this.proxies;
   }

   public ClientLevel getLevel() {
      return this.level;
   }

   public int getAge() {
      return this.age;
   }

   public void render(VertexConsumer buffer, Camera camera, float partialTick) {
      Vec3 vec3 = camera.getPosition();
      Vec3 pos = this.getCurrentPos(partialTick);

      for (SubParticle subParticle : this.proxies) {
         subParticle.onRender(partialTick);
         float ageInSeconds = (subParticle.age + partialTick) / 20.0F;
         Vec3 sub = this.getRelativeSubPos(subParticle, partialTick, ageInSeconds);
         Quaternionf quaternion = this.getQuaternion(subParticle, camera, partialTick, ageInSeconds);
         float x = (float)(pos.x - vec3.x() + sub.x);
         float y = (float)(pos.y - vec3.y() + sub.y);
         float z = (float)(pos.z - vec3.z() + sub.z);
         Vector3f size = this.getSubSizeVector(subParticle, partialTick, ageInSeconds);
         float u0 = subParticle.getU0();
         float u1 = subParticle.getU1();
         float v0 = subParticle.getV0();
         float v1 = subParticle.getV1();
         int lightColor = this.getLightColor(partialTick);
         ColorCurve.FloatRGBA color = this.getSubColor(subParticle, partialTick, ageInSeconds);
         this.renderProxyVertex(buffer, quaternion, x, y, z, 1.0F, -1.0F, size, u1, v1, lightColor, color);
         this.renderProxyVertex(buffer, quaternion, x, y, z, 1.0F, 1.0F, size, u1, v0, lightColor, color);
         this.renderProxyVertex(buffer, quaternion, x, y, z, -1.0F, 1.0F, size, u0, v0, lightColor, color);
         this.renderProxyVertex(buffer, quaternion, x, y, z, -1.0F, -1.0F, size, u0, v1, lightColor, color);
      }
   }

   public Vec3 getActualSubPos(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return this.getCurrentPos(partialTick).add(this.getRelativeSubPos(subParticle, partialTick, ageInSeconds));
   }

   public Vec3 getRelativeSubPos(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return new Vec3(
         this.getSubX(subParticle, partialTick, ageInSeconds),
         this.getSubY(subParticle, partialTick, ageInSeconds),
         this.getSubZ(subParticle, partialTick, ageInSeconds)
      );
   }

   public Vec3 getCurrentPos(float partialTick) {
      if (!this.shouldAttach() || this.attachment == null || this.attachment.entity == null) {
         return new Vec3(Mth.lerp(partialTick, this.xo, this.x), Mth.lerp(partialTick, this.yo, this.y), Mth.lerp(partialTick, this.zo, this.z));
      } else {
         return this.attachment.locator != null
            ? this.attachment
               .entity
               .position()
               .add(
                  this.attachment.locator.bone().getLocalPosition().x,
                  this.attachment.locator.bone().getLocalPosition().y,
                  this.attachment.locator.bone().getLocalPosition().z
               )
            : this.attachment.entity().getPosition(partialTick).add(0.0, this.attachment.entity.getBbHeight() * 0.5, 0.0);
      }
   }

   protected double getSubX(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return Mth.lerp(partialTick, subParticle.pos0.x, subParticle.pos.x);
   }

   protected double getSubY(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return Mth.lerp(partialTick, subParticle.pos0.y, subParticle.pos.y);
   }

   protected double getSubZ(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return Mth.lerp(partialTick, subParticle.pos0.z, subParticle.pos.z);
   }

   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return 1.0F;
   }

   protected Vector3f getSubSizeVector(SubParticle subParticle, float partialTick, float ageInSeconds) {
      float subSize = this.getSubSize(subParticle, partialTick, ageInSeconds);
      return new Vector3f(subSize, subSize, subSize);
   }

   protected abstract ColorCurve.FloatRGBA getSubColor(SubParticle var1, float var2, float var3);

   protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
      Quaternionf quaternion = new Quaternionf();
      this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
      return quaternion;
   }

   public void renderProxyVertex(
      VertexConsumer buffer,
      Quaternionf quaternion,
      float x,
      float y,
      float z,
      float xOffset,
      float yOffset,
      Vector3f quadSize,
      float u,
      float v,
      int packedLight,
      ColorCurve.FloatRGBA color
   ) {
      Vector3f vector3f = new Vector3f(xOffset, yOffset, 0.0F).mul(quadSize).rotate(quaternion).add(x, y, z);
      buffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z()).setUv(u, v).setColor(color.r(), color.g(), color.b(), color.a()).setLight(packedLight);
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public void tick() {
      boolean shouldRemove = false;
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         shouldRemove = true;
      } else {
         this.yd = this.yd - 0.04 * this.gravity;
         this.move(this.xd, this.yd, this.zd);
         if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
            this.xd *= 1.1;
            this.zd *= 1.1;
         }

         this.xd = this.xd * this.friction;
         this.yd = this.yd * this.friction;
         this.zd = this.zd * this.friction;
         if (this.onGround) {
            this.xd *= 0.7F;
            this.zd *= 0.7F;
         }
      }

      this.proxies.removeIf(subParticle -> {
         subParticle.age++;
         if (subParticle.age <= subParticle.lifeTime) {
            subParticle.tick();
            return false;
         } else {
            return true;
         }
      });
      if (!shouldRemove) {
         this.spawnParticles();
      } else if (this.proxies.isEmpty()) {
         this.remove();
      }
   }

   protected abstract void spawnParticles();

   public SubParticle spawnSubParticle() {
      SubParticle subParticle = this.makeParticle();
      this.proxies.add(subParticle);
      return subParticle;
   }

   protected SubParticle makeParticle() {
      return new SubParticle(this, this.set, this.getSubParticleLifeTime(), this.random);
   }

   public static void lookAtMovement(SubParticle subParticle, Quaternionf quaternion, float z) {
      Vec3 vec3 = subParticle.getDirection();
      double sqrt = Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z);
      double xRot = Mth.wrapDegrees((float)(-(Mth.atan2(vec3.y, sqrt) * 180.0F / (float)Math.PI)) + subParticle.random3 * 360.0F);
      double yRot = Mth.wrapDegrees((float)(Mth.atan2(vec3.z, vec3.x) * 180.0F / (float)Math.PI));
      quaternion.rotationYXZ((float)(Math.PI - yRot * (Math.PI / 180.0)), (float)(-xRot * (Math.PI / 180.0)), (float)(z * (Math.PI / 180.0)));
   }

   @Nullable
   public static <T extends Entity & IParticleAttachment> SubParticleEmitter attachNewParticle(
      T entity, ParticleOptions particle, @Nullable ParticleLocator locator, float xS, float yS, float zS
   ) {
      Vec3 position = locator != null ? entity.position().add(locator.getLocalPosition()) : entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0);
      if (Minecraft.getInstance().particleEngine.createParticle(particle, position.x, position.y, position.z, xS, yS, zS) instanceof SubParticleEmitter emitter
         )
       {
         if (emitter.shouldAttach()) {
            emitter.attachment = new SubParticleEmitter.BoneAttachment(entity, locator);
            SubParticleEmitter.BoneAttachment.ATTACHMENTS.put(emitter, emitter.attachment.entity);
         }

         return emitter;
      } else {
         return null;
      }
   }

   @Nullable
   public static <T extends Entity & IParticleAttachment> SubParticleEmitter attachNewParticle(
      T entity, ParticleOptions particle, @Nullable ParticleLocator locator
   ) {
      return attachNewParticle(entity, particle, locator, 0.0F, 0.0F, 0.0F);
   }

   public record BoneAttachment<T extends Entity>(T entity, @Nullable ParticleLocator locator) {
      public static final Map<SubParticleEmitter, Entity> ATTACHMENTS = new HashMap<>();
   }
}
