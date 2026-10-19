package net.unusual.block_factorys_bosses.client.particle.bedrock;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BedrockParticleEmitter extends AbstractBedrockParticle {
   public final ParticleType<BedrockEmitterParticleOptions> particleType;
   public boolean emitting = false;
   public float cycleDuration = 0.0F;
   public float elapsedCycleSeconds = 0.0F;
   private final List<BedrockParticle> children = new ArrayList<>();

   public BedrockParticleEmitter(
      ClientLevel level, BedrockParticleEffect particleEffect, ParticleType<BedrockEmitterParticleOptions> particleType, Vec3 position, Vec3 direction
   ) {
      super(level, particleEffect, position, direction);
      this.particleType = particleType;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.NO_RENDER;
   }

   public List<BedrockParticle> getChildren() {
      return this.children;
   }

   @Override
   public void setMolangVariables(float partialTicks) {
      super.setMolangVariables(partialTicks);
      this.setMolangVariable("emitter_age", this.elapsedCycleSeconds + partialTicks / 20.0F);
      this.setMolangVariable("emitter_lifetime", this.cycleDuration);
      this.setMolangVariable("emitter_random_1", this.random1);
      this.setMolangVariable("emitter_random_2", this.random2);
      this.setMolangVariable("emitter_random_3", this.random3);
      this.setMolangVariable("emitter_random_4", this.random4);
   }

   @Override
   public void tick() {
      this.setMolangVariables(0.0F);
      if (this.age == 0) {
         this.particleEffect.lifetimeEvents().triggerCreationEvents(this);
         if (this.particleEffect.particleMotionCollision() != null) {
            this.particleEffect.particleMotionCollision().initialize(this);
         }
      }

      this.particleEffect.lifetime().tick(this);
      if (this.removed) {
         this.particleEffect.lifetimeEvents().triggerExpirationEvents(this);
      } else {
         this.children.removeIf(child -> !child.isAlive());
         this.particleEffect.rate().tick(this);
         this.particleEffect.lifetimeEvents().tick(this);
         this.age++;
      }
   }

   @Nullable
   public AbstractBedrockParticle emitParticle() {
      Vector3f offset = new Vector3f();
      Vector3f direction = new Vector3f();
      this.particleEffect.shape().getSpawnPosition(this, offset, direction);
      float speed = (float)this.particleEffect.particleInitialSpeed().initialSpeed().get();
      Vector3f velocity = new Vector3f(direction).mul(speed);
      BedrockParticle child = (BedrockParticle)this.level
         .levelRenderer
         .addParticleInternal(
            new BedrockEmitterParticleOptions(this.particleType, this, false),
            false,
            this.x + offset.x(),
            this.y + offset.y(),
            this.z + offset.z(),
            velocity.x(),
            velocity.y(),
            velocity.z()
         );
      if (child != null) {
         this.children.add(child);
      }

      return child;
   }

   public void render(VertexConsumer vertexConsumer, Camera camera, float v) {
   }
}
