package net.unusual.block_factorys_bosses.client.particle.bedrock;

import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.appearance.ParticleAppearanceBillboard;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BedrockParticle extends AbstractBedrockParticle {
   @Nullable
   public final BedrockParticleEmitter parent;
   private float rollVelocity = 0.0F;

   public BedrockParticle(ClientLevel level, BedrockParticleEffect particleEffect, @Nullable BedrockParticleEmitter parent, Vec3 position, Vec3 direction) {
      super(level, particleEffect, position, direction);
      this.parent = parent;
      this.lifetime = particleEffect.particleLifetime().getLifetime();
   }

   public ParticleRenderType getRenderType() {
      return this.particleEffect.particleRenderType();
   }

   public AABB getRenderBoundingBox(float partialTicks) {
      return this.particleEffect.particleAppearance().getRenderBoundingBox(this, partialTicks);
   }

   public float getRollVelocity() {
      return this.rollVelocity;
   }

   public void setRollVelocity(float rollVelocity) {
      this.rollVelocity = rollVelocity;
   }

   @Override
   public void setMolangVariables(float partialTicks) {
      super.setMolangVariables(partialTicks);
      this.setMolangVariable("particle_age", (this.age + partialTicks) / 20.0F);
      this.setMolangVariable("particle_lifetime", this.lifetime / 20.0F);
      this.setMolangVariable("particle_random_1", this.random1);
      this.setMolangVariable("particle_random_2", this.random2);
      this.setMolangVariable("particle_random_3", this.random3);
      this.setMolangVariable("particle_random_4", this.random4);
      if (this.parent != null) {
         this.parent.setMolangVariables(partialTicks);
      }
   }

   @Override
   public void tick() {
      if (this.age >= this.lifetime) {
         this.remove();
      } else {
         if (this.age == 0) {
            if (this.particleEffect.particleMotionCollision() != null) {
               this.particleEffect.particleMotionCollision().initialize(this);
            }

            this.particleEffect.particleInitialSpin().initialize(this);
         }

         this.setMolangVariables(0.0F);
         this.particleEffect.particleLifetime().tick(this);
         if (this.isAlive()) {
            if (this.particleEffect.particleMotion() != null) {
               this.particleEffect.particleMotion().tick(this);
            }

            this.age++;
         }
      }
   }

   @Override
   public void physicsStep() {
      super.physicsStep();
      this.setRoll(this.roll + this.rollVelocity * 0.05F);
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      this.setMolangVariables(partialTicks);
      Quaternionf quaternion = this.getRotation(renderInfo, partialTicks);
      Vec3 cameraPosition = renderInfo.getPosition();
      Vec3 position = this.getPos(partialTicks).subtract(cameraPosition);
      this.renderRotatedQuad(buffer, quaternion, (float)position.x, (float)position.y, (float)position.z, partialTicks);
   }

   protected Quaternionf getRotation(Camera renderInfo, float partialTicks) {
      Quaternionf quaternion = new Quaternionf();
      this.particleEffect.particleAppearance().facingCameraMode().getFacing(this, quaternion, renderInfo, partialTicks);
      if (this.roll != 0.0F) {
         quaternion.rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));
      }

      return quaternion;
   }

   protected void getUV(Vector2f topLeft, Vector2f bottomRight, float partialTicks) {
      this.particleEffect.particleAppearance().uv().getUV(this, partialTicks, topLeft, bottomRight);
      topLeft.set(this.sprite.getU(topLeft.x()), this.sprite.getV(topLeft.y()));
      bottomRight.set(this.sprite.getU(bottomRight.x()), this.sprite.getV(bottomRight.y()));
   }

   protected void updateColor() {
      int color = this.particleEffect.particleAppearanceTinting().getColor(this);
      this.alpha = ARGB32.alpha(color) / 255.0F;
      this.rCol = ARGB32.red(color) / 255.0F;
      this.gCol = ARGB32.green(color) / 255.0F;
      this.bCol = ARGB32.blue(color) / 255.0F;
   }

   protected void renderRotatedQuad(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float partialTicks) {
      ParticleAppearanceBillboard particleAppearance = this.particleEffect.particleAppearance();
      Vector2f quadHalfSize = particleAppearance.size().getNewVector().mul(0.5F);
      Vector2f uvTopLeft = new Vector2f();
      Vector2f uvBottomRight = new Vector2f();
      this.getUV(uvTopLeft, uvBottomRight, partialTicks);
      this.updateColor();
      int lightColor = this.getLightColor(partialTicks);
      this.renderVertex(buffer, quaternion, x, y, z, quadHalfSize.x(), -quadHalfSize.y(), uvBottomRight.x(), uvBottomRight.y(), lightColor);
      this.renderVertex(buffer, quaternion, x, y, z, quadHalfSize.x(), quadHalfSize.y(), uvBottomRight.x(), uvTopLeft.y(), lightColor);
      this.renderVertex(buffer, quaternion, x, y, z, -quadHalfSize.x(), quadHalfSize.y(), uvTopLeft.x(), uvTopLeft.y(), lightColor);
      this.renderVertex(buffer, quaternion, x, y, z, -quadHalfSize.x(), -quadHalfSize.y(), uvTopLeft.x(), uvBottomRight.y(), lightColor);
   }

   protected void renderVertex(
      VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float quadSizeX, float quadSizeY, float u, float v, int packedLight
   ) {
      Vector3f vector3f = new Vector3f(quadSizeX, quadSizeY, 0.0F).rotate(quaternion).add(x, y, z);
      buffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z()).setUv(u, v).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(packedLight);
   }
}
