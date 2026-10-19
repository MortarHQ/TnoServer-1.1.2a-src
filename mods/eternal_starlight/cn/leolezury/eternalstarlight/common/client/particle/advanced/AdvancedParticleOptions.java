package cn.leolezury.eternalstarlight.common.client.particle.advanced;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class AdvancedParticleOptions {
   public static final RandomSource RANDOM = RandomSource.create();
   private RenderType renderType = ESRenderType.PARTICLE_ADDITIVE_GLOW;
   private SmoothSegmentedValue xSpeed = SmoothSegmentedValue.constant(0.0F);
   private SmoothSegmentedValue ySpeed = SmoothSegmentedValue.constant(0.0F);
   private SmoothSegmentedValue zSpeed = SmoothSegmentedValue.constant(0.0F);
   private SmoothSegmentedValue spinSpeed = SmoothSegmentedValue.constant((float) (Math.PI / 10));
   private SmoothSegmentedValue quadSize = SmoothSegmentedValue.constant(1.0F);
   private int lifetime = 50;
   private SmoothSegmentedValue red = SmoothSegmentedValue.constant(1.0F);
   private SmoothSegmentedValue green = SmoothSegmentedValue.constant(1.0F);
   private SmoothSegmentedValue blue = SmoothSegmentedValue.constant(1.0F);
   private SmoothSegmentedValue alpha = SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.0F, 1.0F, 0.5F).add(Easing.IN_OUT_SINE, 1.0F, 0.0F, 0.5F);
   private final List<Consumer<ParticleOperator>> spawnOperators = new ArrayList<>();
   private final List<Consumer<ParticleOperator>> tickOperators = new ArrayList<>();
   private final List<Consumer<ParticleOperator>> renderOperators = new ArrayList<>();
   private final List<Consumer<ParticleOperator>> removeOperators = new ArrayList<>();

   public AdvancedParticleOptions renderType(RenderType type) {
      this.renderType = type;
      return this;
   }

   public RenderType getRenderType() {
      return this.renderType;
   }

   public AdvancedParticleOptions speed(SmoothSegmentedValue x, SmoothSegmentedValue y, SmoothSegmentedValue z) {
      this.xSpeed = x;
      this.ySpeed = y;
      this.zSpeed = z;
      return this;
   }

   public Vec3 getSpeed(float progress) {
      return new Vec3(this.xSpeed.calculate(progress), this.ySpeed.calculate(progress), this.zSpeed.calculate(progress));
   }

   public AdvancedParticleOptions spinSpeed(SmoothSegmentedValue spinSpeed) {
      this.spinSpeed = spinSpeed;
      return this;
   }

   public float getSpinSpeed(float progress) {
      return this.spinSpeed.calculate(progress);
   }

   public AdvancedParticleOptions quadSize(SmoothSegmentedValue quadSize) {
      this.quadSize = quadSize;
      return this;
   }

   public float getQuadSize(float progress) {
      return this.quadSize.calculate(progress);
   }

   public AdvancedParticleOptions lifetime(int lifetime) {
      this.lifetime = lifetime;
      return this;
   }

   public AdvancedParticleOptions color(SmoothSegmentedValue r, SmoothSegmentedValue g, SmoothSegmentedValue b) {
      this.red = r;
      this.green = g;
      this.blue = b;
      return this;
   }

   public AdvancedParticleOptions alpha(SmoothSegmentedValue alpha) {
      this.alpha = alpha;
      return this;
   }

   public AdvancedParticleOptions color(SmoothSegmentedValue r, SmoothSegmentedValue g, SmoothSegmentedValue b, SmoothSegmentedValue a) {
      this.color(r, g, b);
      this.alpha(a);
      return this;
   }

   public Vector4f getColor(float progress) {
      return new Vector4f(this.red.calculate(progress), this.green.calculate(progress), this.blue.calculate(progress), this.alpha.calculate(progress));
   }

   public AdvancedParticleOptions spawnOperator(Consumer<ParticleOperator> operator) {
      this.spawnOperators.add(operator);
      return this;
   }

   public AdvancedParticleOptions tickOperator(Consumer<ParticleOperator> operator) {
      this.tickOperators.add(operator);
      return this;
   }

   public AdvancedParticleOptions renderOperator(Consumer<ParticleOperator> operator) {
      this.renderOperators.add(operator);
      return this;
   }

   public AdvancedParticleOptions removeOperator(Consumer<ParticleOperator> operator) {
      this.removeOperators.add(operator);
      return this;
   }

   public AdvancedParticleOptions defaultOperators() {
      this.spawnOperator(o -> o.setSpeed(this.getSpeed(0.0F)));
      this.spawnOperator(o -> o.setQuadSize(this.getQuadSize(0.0F)));
      this.spawnOperator(o -> o.setColor(this.getColor(0.0F)));
      this.spawnOperator(o -> o.setLifetime(this.lifetime));
      this.tickOperator(o -> o.setSpeed(this.getSpeed(o.getAge() / o.getLifetime())));
      this.tickOperator(o -> {
         o.setOldRoll(o.getRoll());
         o.setRoll(o.getRoll() + this.getSpinSpeed(o.getAge() / o.getLifetime()));
      });
      this.renderOperator(o -> o.setQuadSize(this.getQuadSize(o.getAge() / o.getLifetime())));
      this.renderOperator(o -> o.setColor(this.getColor(o.getAge() / o.getLifetime())));
      return this;
   }

   public void spawn(ResourceLocation location, float x, float y, float z) {
      this.spawn(AdvancedParticle::new, location, x, y, z);
   }

   public void spawn(ParticleSpawner spawner, ResourceLocation location, float x, float y, float z) {
      Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
      ParticleStatus particleStatus = (ParticleStatus)Minecraft.getInstance().options.particles().get();
      if (particleStatus == ParticleStatus.DECREASED && RANDOM.nextInt(3) == 0) {
         particleStatus = ParticleStatus.MINIMAL;
      }

      if (camera.getPosition().distanceToSqr(x, y, z) <= 1024.0 && particleStatus != ParticleStatus.MINIMAL) {
         Particle particle = spawner.spawn(
            Minecraft.getInstance().level, x, y, z, (SpriteSet)Minecraft.getInstance().particleEngine.spriteSets.get(location), this
         );
         Minecraft.getInstance().particleEngine.add(particle);
      }
   }

   public void operateSpawn(ParticleOperator operator) {
      for (Consumer<ParticleOperator> consumer : this.spawnOperators) {
         consumer.accept(operator);
      }
   }

   public void operateTick(ParticleOperator operator) {
      for (Consumer<ParticleOperator> consumer : this.tickOperators) {
         consumer.accept(operator);
      }
   }

   public void operateRender(ParticleOperator operator) {
      for (Consumer<ParticleOperator> consumer : this.renderOperators) {
         consumer.accept(operator);
      }
   }

   public void operateRemove(ParticleOperator operator) {
      for (Consumer<ParticleOperator> consumer : this.removeOperators) {
         consumer.accept(operator);
      }
   }
}
