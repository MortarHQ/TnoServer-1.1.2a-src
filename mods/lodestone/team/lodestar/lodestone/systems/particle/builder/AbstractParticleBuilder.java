package team.lodestar.lodestone.systems.particle.builder;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleDataWrapper;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleDataWrapper;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleDataWrapper;

public abstract class AbstractParticleBuilder<T extends SimpleParticleOptions> {
   double xMotion = 0.0;
   double yMotion = 0.0;
   double maxXSpeed = 0.0;
   double maxYSpeed = 0.0;
   double maxXOffset = 0.0;
   double maxYOffset = 0.0;

   public AbstractParticleBuilder<T> modifyColorData(Consumer<ColorParticleData> dataConsumer) {
      dataConsumer.accept(this.getColorData());
      return this;
   }

   public AbstractParticleBuilder<T> setColorData(ColorParticleDataWrapper colorData) {
      this.getParticleOptions().colorData = colorData.unwrap();
      return this;
   }

   public ColorParticleData getColorData() {
      return this.getParticleOptions().colorData;
   }

   public AbstractParticleBuilder<T> modifyScaleData(Consumer<GenericParticleData> dataConsumer) {
      dataConsumer.accept(this.getScaleData());
      return this;
   }

   public AbstractParticleBuilder<T> setScaleData(GenericParticleDataWrapper scaleData) {
      this.getParticleOptions().scaleData = scaleData.unwrap();
      return this;
   }

   public GenericParticleData getScaleData() {
      return this.getParticleOptions().scaleData;
   }

   public AbstractParticleBuilder<T> modifyLengthData(Consumer<GenericParticleData> dataConsumer) {
      dataConsumer.accept(this.getLengthData());
      return this;
   }

   public AbstractParticleBuilder<T> setLengthData(GenericParticleDataWrapper lengthData) {
      this.getParticleOptions().lengthData = lengthData.unwrap();
      return this;
   }

   public GenericParticleData getLengthData() {
      return this.getParticleOptions().lengthData;
   }

   public AbstractParticleBuilder<T> modifyTransparencyData(Consumer<GenericParticleData> dataConsumer) {
      dataConsumer.accept(this.getTransparencyData());
      return this;
   }

   public AbstractParticleBuilder<T> setTransparencyData(GenericParticleDataWrapper transparencyData) {
      this.getParticleOptions().transparencyData = transparencyData.unwrap();
      return this;
   }

   public GenericParticleData getTransparencyData() {
      return this.getParticleOptions().transparencyData;
   }

   public AbstractParticleBuilder<T> modifySpinData(Consumer<SpinParticleData> dataConsumer) {
      dataConsumer.accept(this.getSpinData());
      return this;
   }

   public AbstractParticleBuilder<T> setSpinData(SpinParticleDataWrapper spinData) {
      this.getParticleOptions().spinData = spinData.unwrap();
      return this;
   }

   public SpinParticleData getSpinData() {
      return this.getParticleOptions().spinData;
   }

   public AbstractParticleBuilder<T> setLifeDelay(int lifeDelay) {
      return this.setLifeDelay(() -> lifeDelay);
   }

   public AbstractParticleBuilder<T> setLifeDelay(Supplier<Integer> supplier) {
      this.getParticleOptions().lifeDelaySupplier = supplier;
      return this;
   }

   public AbstractParticleBuilder<T> setLifetime(int lifetime) {
      return this.setLifetime(() -> lifetime);
   }

   public AbstractParticleBuilder<T> setLifetime(Supplier<Integer> supplier) {
      this.getParticleOptions().lifetimeSupplier = supplier;
      return this;
   }

   public AbstractParticleBuilder<T> setGravity(float gravity) {
      return this.setGravity(() -> gravity);
   }

   public AbstractParticleBuilder<T> setGravity(Supplier<Float> supplier) {
      this.getParticleOptions().gravitySupplier = supplier;
      return this;
   }

   public AbstractParticleBuilder<T> setFriction(float friction) {
      return this.setFriction(() -> friction);
   }

   public AbstractParticleBuilder<T> setFriction(Supplier<Float> supplier) {
      this.getParticleOptions().frictionSupplier = supplier;
      return this;
   }

   public AbstractParticleBuilder<T> multiplyLifeDelay(float multiplier) {
      return this.modifyLifeDelay(f -> (int)(f * multiplier));
   }

   public AbstractParticleBuilder<T> modifyLifeDelay(Int2IntFunction modifier) {
      return this.setLifeDelayModifier(this.getParticleOptions().lifeDelayModifier.andThenInt(modifier));
   }

   public AbstractParticleBuilder<T> setLifeDelayModifier(float multiplier) {
      return this.setLifeDelayModifier(f -> (int)(f * multiplier));
   }

   public AbstractParticleBuilder<T> setLifeDelayModifier(Int2IntFunction modifier) {
      this.getParticleOptions().lifeDelayModifier = modifier;
      return this;
   }

   public AbstractParticleBuilder<T> multiplyLifetime(float multiplier) {
      return this.modifyLifetime(f -> (int)(f * multiplier));
   }

   public AbstractParticleBuilder<T> modifyLifetime(Int2IntFunction modifier) {
      return this.setLifetimeModifier(this.getParticleOptions().lifetimeModifier.andThenInt(modifier));
   }

   public AbstractParticleBuilder<T> setLifetimeModifier(float multiplier) {
      return this.setLifetimeModifier(f -> (int)(f * multiplier));
   }

   public AbstractParticleBuilder<T> setLifetimeModifier(Int2IntFunction modifier) {
      this.getParticleOptions().lifetimeModifier = modifier;
      return this;
   }

   public AbstractParticleBuilder<T> multiplyGravity(float multiplier) {
      return this.modifyGravity(f -> f * multiplier);
   }

   public AbstractParticleBuilder<T> modifyGravity(Float2FloatFunction modifier) {
      return this.setGravityModifier(this.getParticleOptions().gravityModifier.andThenFloat(modifier));
   }

   public AbstractParticleBuilder<T> setGravityModifier(float multiplier) {
      return this.setGravityModifier(f -> (int)(f * multiplier));
   }

   public AbstractParticleBuilder<T> setGravityModifier(Float2FloatFunction modifier) {
      this.getParticleOptions().gravityModifier = modifier;
      return this;
   }

   public AbstractParticleBuilder<T> multiplyFriction(float multiplier) {
      return this.modifyFriction(f -> f * multiplier);
   }

   public AbstractParticleBuilder<T> modifyFriction(Float2FloatFunction modifier) {
      return this.setFrictionModifier(this.getParticleOptions().frictionModifier.andThenFloat(modifier));
   }

   public AbstractParticleBuilder<T> setFrictionModifier(float multiplier) {
      return this.setFrictionModifier(f -> (int)(f * multiplier));
   }

   public AbstractParticleBuilder<T> setFrictionModifier(Float2FloatFunction modifier) {
      this.getParticleOptions().frictionModifier = modifier;
      return this;
   }

   public AbstractParticleBuilder<T> setSpritePicker(SimpleParticleOptions.ParticleSpritePicker spritePicker) {
      this.getParticleOptions().spritePicker = spritePicker;
      return this;
   }

   public abstract T getParticleOptions();
}
