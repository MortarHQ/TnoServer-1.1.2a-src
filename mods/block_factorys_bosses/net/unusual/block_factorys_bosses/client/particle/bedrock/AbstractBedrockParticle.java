package net.unusual.block_factorys_bosses.client.particle.bedrock;

import java.util.Map.Entry;
import java.util.function.DoubleSupplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.ParticleProvider.Sprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.ParticleCurve;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.ParticleEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;
import software.bernie.geckolib.loading.math.MathParser;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class AbstractBedrockParticle extends TextureSheetParticle {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final float TICK_DELTA = 0.05F;
   public final BedrockParticleEffect particleEffect;
   protected final float random1;
   protected final float random2;
   protected final float random3;
   protected final float random4;

   public static Sprite<BedrockEmitterParticleOptions> provider(ResourceLocation bedrockIdentifier) {
      return new AbstractBedrockParticle.Provider(bedrockIdentifier);
   }

   public AbstractBedrockParticle(ClientLevel level, BedrockParticleEffect particleEffect, Vec3 position, Vec3 direction) {
      super(level, position.x(), position.y(), position.z());
      this.particleEffect = particleEffect;
      this.setVelocity(direction.toVector3f());
      this.random1 = level.getRandom().nextFloat();
      this.random2 = level.getRandom().nextFloat();
      this.random3 = level.getRandom().nextFloat();
      this.random4 = level.getRandom().nextFloat();
   }

   public ClientLevel getLevel() {
      return this.level;
   }

   public int getAge() {
      return this.age;
   }

   public Vec3 getPos(float partialTicks) {
      return new Vec3(Mth.lerp(partialTicks, this.xo, this.x), Mth.lerp(partialTicks, this.yo, this.y), Mth.lerp(partialTicks, this.zo, this.z));
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public void setSize(float width, float height) {
      super.setSize(width, height);
   }

   public Vector3f getVelocity() {
      return new Vector3f((float)this.xd, (float)this.yd, (float)this.zd);
   }

   public void setVelocity(Vector3f velocity) {
      this.xd = velocity.x();
      this.yd = velocity.y();
      this.zd = velocity.z();
   }

   public float getRoll() {
      return this.roll;
   }

   public void setRoll(float roll) {
      this.roll = roll;
   }

   protected void setMolangVariable(String name, double value) {
      this.setMolangVariable(name, () -> value);
   }

   protected void setMolangVariable(String name, DoubleSupplier supplier) {
      MathParser.setVariable("variable." + name, supplier);
      MathParser.setVariable("v." + name, supplier);
   }

   public void setMolangVariables(float partialTicks) {
      for (Entry<String, ParticleCurve> entry : this.particleEffect.curves().entrySet()) {
         String name = entry.getKey();
         ParticleCurve curve = entry.getValue();
         this.setMolangVariable(name, curve::getValue);
      }
   }

   public void triggerParticleEvent(String name) {
      ParticleEvent event = this.particleEffect.events().get(name);
      if (event == null) {
         LOGGER.warn("Bedrock particle effect {} triggered unknown event: {}", this.particleEffect, name);
      } else {
         try {
            event.trigger(this);
         } catch (Exception exception) {
            throw new IllegalStateException("Exception while triggering particle event " + name + " on Bedrock particle " + this.particleEffect);
         }
      }
   }

   public abstract void tick();

   public void physicsStep() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.oRoll = this.roll;
      Vec3 motion = new Vec3(this.xd * 0.05F, this.yd * 0.05F, this.zd * 0.05F);
      if (this.particleEffect.particleMotionCollision() != null) {
         motion = this.particleEffect.particleMotionCollision().move(this, motion);
      }

      this.setPos(this.x + motion.x(), this.y + motion.y(), this.z + motion.z());
   }

   public static class Provider implements Sprite<BedrockEmitterParticleOptions> {
      private final ResourceLocation bedrockIdentifier;
      @Nullable
      private BedrockParticleEffect particleEffect;

      public Provider(ResourceLocation bedrockIdentifier) {
         this.bedrockIdentifier = bedrockIdentifier;
      }

      private BedrockParticleEffect findParticleEffect() {
         if (this.particleEffect != null) {
            return this.particleEffect;
         }

         ResourceLocation resourcePath = this.bedrockIdentifier.withPrefix("bedrock_particles/").withSuffix(".particle.json");
         BedrockParticleEffect particleEffect = BedrockParticleEffectCache.getBedrockParticleEffects().get(resourcePath);
         if (particleEffect == null) {
            throw new IllegalStateException("Particle provider for Bedrock particle emitter " + this.bedrockIdentifier + " has no definition file.");
         }

         this.particleEffect = particleEffect;
         return particleEffect;
      }

      public AbstractBedrockParticle createParticle(
         BedrockEmitterParticleOptions options, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         BedrockParticleEffect particleEffect = this.findParticleEffect();
         Vec3 position = new Vec3(x, y, z);
         Vec3 velocity = new Vec3(xSpeed, ySpeed, zSpeed);
         return options.isEmitter()
            ? new BedrockParticleEmitter(world, particleEffect, options.type(), position, velocity)
            : new BedrockParticle(world, particleEffect, options.parent(), position, velocity);
      }
   }
}
