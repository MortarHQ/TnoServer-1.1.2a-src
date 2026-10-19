package team.lodestar.lodestone.systems.particle;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.awt.Color;
import java.util.function.Supplier;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

public abstract class SimpleParticleOptions {
   public static final ColorParticleData DEFAULT_COLOR = ColorParticleData.create(Color.WHITE, Color.WHITE).build();
   public static final SpinParticleData DEFAULT_SPIN = SpinParticleData.create(0.0F).build().lock();
   public static final GenericParticleData DEFAULT_GENERIC = GenericParticleData.create(1.0F, 0.0F).build().lock();
   public SimpleParticleOptions.ParticleSpritePicker spritePicker = SimpleParticleOptions.ParticleSpritePicker.WITH_AGE;
   public ColorParticleData colorData = DEFAULT_COLOR;
   public GenericParticleData transparencyData = DEFAULT_GENERIC;
   public GenericParticleData scaleData = DEFAULT_GENERIC;
   public GenericParticleData lengthData = DEFAULT_GENERIC;
   public SpinParticleData spinData = DEFAULT_SPIN;
   public Supplier<Integer> lifetimeSupplier = () -> 20;
   public Supplier<Integer> lifeDelaySupplier = () -> 0;
   public Supplier<Float> gravitySupplier = () -> 0.0F;
   public Supplier<Float> frictionSupplier = () -> 1.0F;
   public Int2IntFunction lifetimeModifier = i -> i;
   public Int2IntFunction lifeDelayModifier = i -> i;
   public Float2FloatFunction gravityModifier = f -> f;
   public Float2FloatFunction frictionModifier = f -> f;

   public int getLifetime() {
      return (Integer)this.lifetimeModifier.apply(this.lifetimeSupplier.get());
   }

   public int getLifeDelay() {
      return (Integer)this.lifeDelayModifier.apply(this.lifeDelaySupplier.get());
   }

   public float getGravity() {
      return (Float)this.gravityModifier.apply(this.gravitySupplier.get());
   }

   public float getFriction() {
      return (Float)this.frictionModifier.apply(this.frictionSupplier.get());
   }

   public enum ParticleSpritePicker {
      FIRST_INDEX,
      LAST_INDEX,
      WITH_AGE,
      WITH_AGE_INVERSE,
      RANDOM_SPRITE;
   }
}
