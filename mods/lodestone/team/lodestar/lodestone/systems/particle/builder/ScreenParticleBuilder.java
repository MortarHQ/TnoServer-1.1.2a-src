package team.lodestar.lodestone.systems.particle.builder;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.Collection;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleDataWrapper;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleDataWrapper;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleDataWrapper;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneScreenParticleRenderType;
import team.lodestar.lodestone.systems.particle.screen.LodestoneScreenParticle;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleOptions;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleType;

public class ScreenParticleBuilder extends AbstractParticleBuilder<ScreenParticleOptions> {
   private static final Random RANDOM = new Random();
   final ScreenParticleType<?> type;
   final ScreenParticleOptions options;
   final ScreenParticleHolder target;

   public static ScreenParticleBuilder create(ScreenParticleType<?> type, ScreenParticleHolder target) {
      return new ScreenParticleBuilder(type, target);
   }

   protected ScreenParticleBuilder(ScreenParticleType<?> type, ScreenParticleHolder target) {
      this.type = type;
      this.options = new ScreenParticleOptions(type);
      this.target = target;
   }

   public ScreenParticleOptions getParticleOptions() {
      return this.options;
   }

   @Deprecated(forRemoval = true, since = "1.7.2")
   public ScreenParticleBuilder modifyData(Supplier<GenericParticleData> dataType, Consumer<GenericParticleData> dataConsumer) {
      dataConsumer.accept(dataType.get());
      return this;
   }

   @Deprecated(forRemoval = true, since = "1.7.2")
   public ScreenParticleBuilder modifyData(Function<ScreenParticleBuilder, GenericParticleData> dataType, Consumer<GenericParticleData> dataConsumer) {
      dataConsumer.accept(dataType.apply(this));
      return this;
   }

   @Deprecated(forRemoval = true, since = "1.7.2")
   public final ScreenParticleBuilder modifyData(Collection<Supplier<GenericParticleData>> dataTypes, Consumer<GenericParticleData> dataConsumer) {
      for (Supplier<GenericParticleData> dataFunction : dataTypes) {
         dataConsumer.accept(dataFunction.get());
      }

      return this;
   }

   public ScreenParticleBuilder setRenderType(LodestoneScreenParticleRenderType renderType) {
      this.options.renderType = renderType;
      return this;
   }

   public ScreenParticleBuilder setRandomMotion(double maxSpeed) {
      return this.setRandomMotion(maxSpeed, maxSpeed);
   }

   public ScreenParticleBuilder setRandomMotion(double maxXSpeed, double maxYSpeed) {
      this.maxXSpeed = maxXSpeed;
      this.maxYSpeed = maxYSpeed;
      return this;
   }

   public ScreenParticleBuilder addMotion(double vx, double vy) {
      this.xMotion += vx;
      this.yMotion += vy;
      return this;
   }

   public ScreenParticleBuilder setMotion(double vx, double vy) {
      this.xMotion = vx;
      this.yMotion = vy;
      return this;
   }

   public ScreenParticleBuilder setRandomOffset(double maxDistance) {
      return this.setRandomOffset(maxDistance, maxDistance);
   }

   public ScreenParticleBuilder setRandomOffset(double maxXDist, double maxYDist) {
      this.maxXOffset = maxXDist;
      this.maxYOffset = maxYDist;
      return this;
   }

   public ScreenParticleBuilder act(Consumer<ScreenParticleBuilder> particleBuilderConsumer) {
      particleBuilderConsumer.accept(this);
      return this;
   }

   public ScreenParticleBuilder addTickActor(Consumer<LodestoneScreenParticle> particleActor) {
      this.getParticleOptions().tickActors.add(particleActor);
      return this;
   }

   public ScreenParticleBuilder addSpawnActor(Consumer<LodestoneScreenParticle> particleActor) {
      this.getParticleOptions().spawnActors.add(particleActor);
      return this;
   }

   public ScreenParticleBuilder addRenderActor(Consumer<LodestoneScreenParticle> particleActor) {
      this.getParticleOptions().renderActors.add(particleActor);
      return this;
   }

   public ScreenParticleBuilder clearActors() {
      return this.clearTickActor().clearSpawnActors().clearRenderActors();
   }

   public ScreenParticleBuilder clearTickActor() {
      this.getParticleOptions().tickActors.clear();
      return this;
   }

   public ScreenParticleBuilder clearSpawnActors() {
      this.getParticleOptions().spawnActors.clear();
      return this;
   }

   public ScreenParticleBuilder clearRenderActors() {
      this.getParticleOptions().renderActors.clear();
      return this;
   }

   public ScreenParticleBuilder spawn(double x, double y) {
      double yaw = RANDOM.nextFloat() * Math.PI * 2.0;
      double pitch = RANDOM.nextFloat() * Math.PI - (Math.PI / 2);
      double xSpeed = RANDOM.nextFloat() * this.maxXSpeed;
      double ySpeed = RANDOM.nextFloat() * this.maxYSpeed;
      this.xMotion = this.xMotion + Math.sin(yaw) * Math.cos(pitch) * xSpeed;
      this.yMotion = this.yMotion + Math.sin(pitch) * ySpeed;
      double yaw2 = RANDOM.nextFloat() * Math.PI * 2.0;
      double pitch2 = RANDOM.nextFloat() * Math.PI - (Math.PI / 2);
      double xDist = RANDOM.nextFloat() * this.maxXOffset;
      double yDist = RANDOM.nextFloat() * this.maxYOffset;
      double xPos = Math.sin(yaw2) * Math.cos(pitch2) * xDist;
      double yPos = Math.sin(pitch2) * yDist;
      ScreenParticleHandler.addParticle(this.target, this.options, x + xPos, y + yPos, this.xMotion, this.yMotion);
      return this;
   }

   public ScreenParticleBuilder repeat(double x, double y, int n) {
      for (int i = 0; i < n; i++) {
         this.spawn(x, y);
      }

      return this;
   }

   public ScreenParticleBuilder spawnOnStack(double xOffset, double yOffset) {
      this.options.tracksStack = true;
      this.options.stackTrackXOffset = xOffset;
      this.options.stackTrackYOffset = yOffset;
      this.spawn(ScreenParticleHandler.currentItemX + xOffset, ScreenParticleHandler.currentItemY + yOffset);
      return this;
   }

   public ScreenParticleBuilder repeatOnStack(double xOffset, double yOffset, int n) {
      for (int i = 0; i < n; i++) {
         this.spawnOnStack(xOffset, yOffset);
      }

      return this;
   }

   public ScreenParticleBuilder modifyColorData(Consumer<ColorParticleData> dataConsumer) {
      return (ScreenParticleBuilder)super.modifyColorData(dataConsumer);
   }

   public ScreenParticleBuilder setColorData(ColorParticleDataWrapper colorData) {
      return (ScreenParticleBuilder)super.setColorData(colorData);
   }

   public ScreenParticleBuilder modifyScaleData(Consumer<GenericParticleData> dataConsumer) {
      return (ScreenParticleBuilder)super.modifyScaleData(dataConsumer);
   }

   public ScreenParticleBuilder setScaleData(GenericParticleDataWrapper scaleData) {
      return (ScreenParticleBuilder)super.setScaleData(scaleData);
   }

   public ScreenParticleBuilder modifyLengthData(Consumer<GenericParticleData> dataConsumer) {
      return (ScreenParticleBuilder)super.modifyLengthData(dataConsumer);
   }

   public ScreenParticleBuilder setLengthData(GenericParticleDataWrapper lengthData) {
      return (ScreenParticleBuilder)super.setLengthData(lengthData);
   }

   public ScreenParticleBuilder setLifeDelay(int lifeDelay) {
      return (ScreenParticleBuilder)super.setLifeDelay(lifeDelay);
   }

   public ScreenParticleBuilder setLifeDelay(Supplier<Integer> supplier) {
      return (ScreenParticleBuilder)super.setLifeDelay(supplier);
   }

   public ScreenParticleBuilder setLifetime(int lifetime) {
      return (ScreenParticleBuilder)super.setLifetime(lifetime);
   }

   public ScreenParticleBuilder setLifetime(Supplier<Integer> supplier) {
      return (ScreenParticleBuilder)super.setLifetime(supplier);
   }

   public ScreenParticleBuilder setGravity(float gravity) {
      return (ScreenParticleBuilder)super.setGravity(gravity);
   }

   public ScreenParticleBuilder setGravity(Supplier<Float> supplier) {
      return (ScreenParticleBuilder)super.setGravity(supplier);
   }

   public ScreenParticleBuilder setFriction(float friction) {
      return (ScreenParticleBuilder)super.setFriction(friction);
   }

   public ScreenParticleBuilder setFriction(Supplier<Float> supplier) {
      return (ScreenParticleBuilder)super.setFriction(supplier);
   }

   public ScreenParticleBuilder multiplyLifeDelay(float multiplier) {
      return (ScreenParticleBuilder)super.multiplyLifeDelay(multiplier);
   }

   public ScreenParticleBuilder modifyLifeDelay(Int2IntFunction modifier) {
      return (ScreenParticleBuilder)super.modifyLifeDelay(modifier);
   }

   public ScreenParticleBuilder setLifeDelayModifier(float multiplier) {
      return (ScreenParticleBuilder)super.setLifeDelayModifier(multiplier);
   }

   public ScreenParticleBuilder setLifeDelayModifier(Int2IntFunction modifier) {
      return (ScreenParticleBuilder)super.setLifeDelayModifier(modifier);
   }

   public ScreenParticleBuilder multiplyLifetime(float multiplier) {
      return (ScreenParticleBuilder)super.multiplyLifetime(multiplier);
   }

   public ScreenParticleBuilder modifyLifetime(Int2IntFunction modifier) {
      return (ScreenParticleBuilder)super.modifyLifetime(modifier);
   }

   public ScreenParticleBuilder setLifetimeModifier(float multiplier) {
      return (ScreenParticleBuilder)super.setLifetimeModifier(multiplier);
   }

   public ScreenParticleBuilder setLifetimeModifier(Int2IntFunction modifier) {
      return (ScreenParticleBuilder)super.setLifetimeModifier(modifier);
   }

   public ScreenParticleBuilder multiplyGravity(float multiplier) {
      return (ScreenParticleBuilder)super.multiplyGravity(multiplier);
   }

   public ScreenParticleBuilder modifyGravity(Float2FloatFunction modifier) {
      return (ScreenParticleBuilder)super.modifyGravity(modifier);
   }

   public ScreenParticleBuilder setGravityModifier(float multiplier) {
      return (ScreenParticleBuilder)super.setGravityModifier(multiplier);
   }

   public ScreenParticleBuilder setGravityModifier(Float2FloatFunction modifier) {
      return (ScreenParticleBuilder)super.setGravityModifier(modifier);
   }

   public ScreenParticleBuilder multiplyFriction(float multiplier) {
      return (ScreenParticleBuilder)super.multiplyFriction(multiplier);
   }

   public ScreenParticleBuilder modifyFriction(Float2FloatFunction modifier) {
      return (ScreenParticleBuilder)super.modifyFriction(modifier);
   }

   public ScreenParticleBuilder setFrictionModifier(float multiplier) {
      return (ScreenParticleBuilder)super.setFrictionModifier(multiplier);
   }

   public ScreenParticleBuilder setFrictionModifier(Float2FloatFunction modifier) {
      return (ScreenParticleBuilder)super.setFrictionModifier(modifier);
   }

   public ScreenParticleBuilder setSpinData(SpinParticleDataWrapper spinData) {
      return (ScreenParticleBuilder)super.setSpinData(spinData);
   }

   public ScreenParticleBuilder modifySpinData(Consumer<SpinParticleData> dataConsumer) {
      return (ScreenParticleBuilder)super.modifySpinData(dataConsumer);
   }

   public ScreenParticleBuilder setTransparencyData(GenericParticleDataWrapper transparencyData) {
      return (ScreenParticleBuilder)super.setTransparencyData(transparencyData);
   }

   public ScreenParticleBuilder modifyTransparencyData(Consumer<GenericParticleData> dataConsumer) {
      return (ScreenParticleBuilder)super.modifyTransparencyData(dataConsumer);
   }

   public ScreenParticleBuilder setSpritePicker(SimpleParticleOptions.ParticleSpritePicker spritePicker) {
      return (ScreenParticleBuilder)super.setSpritePicker(spritePicker);
   }
}
