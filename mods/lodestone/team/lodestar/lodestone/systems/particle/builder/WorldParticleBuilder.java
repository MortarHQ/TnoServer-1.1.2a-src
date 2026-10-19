package team.lodestar.lodestone.systems.particle.builder;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;
import team.lodestar.lodestone.helpers.block.BlockPosHelper;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleDataWrapper;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleDataWrapper;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleDataWrapper;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.behaviors.LodestoneParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;
import team.lodestar.lodestone.systems.rendering.buffer.LodestoneRenderLayer;

public class WorldParticleBuilder extends AbstractParticleBuilder<WorldParticleOptions> {
   private static final Random RANDOM = new Random();
   final WorldParticleOptions options;
   boolean forceSpawn = false;
   double zMotion = 0.0;
   double maxZSpeed = 0.0;
   double maxZOffset = 0.0;

   public static WorldParticleBuilder create(Holder<? extends LodestoneWorldParticleType> particle) {
      return create((LodestoneWorldParticleType)particle.value());
   }

   public static WorldParticleBuilder create(Supplier<? extends LodestoneWorldParticleType> particle) {
      return create(particle.get());
   }

   public static WorldParticleBuilder create(LodestoneWorldParticleType particle) {
      return create(new WorldParticleOptions(particle));
   }

   public static WorldParticleBuilder create(WorldParticleOptions options) {
      return new WorldParticleBuilder(options);
   }

   protected WorldParticleBuilder(WorldParticleOptions options) {
      this.options = options;
   }

   public WorldParticleOptions getParticleOptions() {
      return this.options;
   }

   public <T extends LodestoneParticleBehavior> WorldParticleBuilder setBehavior(Class<T> targetClass, Function<T, LodestoneParticleBehavior> behaviorFunction) {
      T behavior = this.getBehavior(targetClass);
      return this.setBehavior(behavior != null ? behaviorFunction.apply(behavior) : null);
   }

   public <T extends LodestoneParticleBehavior> GenericParticleData getBehaviorData(Class<T> targetClass, Function<T, GenericParticleData> dataFunction) {
      T behavior = this.getBehavior(targetClass);
      return behavior != null ? dataFunction.apply(behavior) : null;
   }

   public WorldParticleBuilder setBehavior(LodestoneParticleBehavior behavior) {
      this.getParticleOptions().setBehavior(behavior);
      return this;
   }

   public <T extends LodestoneParticleBehavior> T getBehavior(Class<T> targetClass) {
      return targetClass.isInstance(this.getParticleOptions().behavior) ? targetClass.cast(this.getParticleOptions().behavior) : null;
   }

   @Deprecated(forRemoval = true, since = "1.7.2")
   public WorldParticleBuilder modifyData(GenericParticleData dataType, Consumer<GenericParticleData> dataConsumer) {
      if (dataType != null) {
         dataConsumer.accept(dataType);
      }

      return this;
   }

   @Deprecated(forRemoval = true, since = "1.7.2")
   public WorldParticleBuilder modifyData(Supplier<GenericParticleData> dataType, Consumer<GenericParticleData> dataConsumer) {
      return this.modifyData(dataType.get(), dataConsumer);
   }

   @Deprecated(forRemoval = true, since = "1.7.2")
   public WorldParticleBuilder modifyData(Function<WorldParticleBuilder, GenericParticleData> dataType, Consumer<GenericParticleData> dataConsumer) {
      this.modifyData(dataType.apply(this), dataConsumer);
      return this;
   }

   @Deprecated(forRemoval = true, since = "1.7.2")
   public final WorldParticleBuilder modifyData(Collection<Supplier<GenericParticleData>> dataTypes, Consumer<GenericParticleData> dataConsumer) {
      for (Supplier<GenericParticleData> dataFunction : dataTypes) {
         dataConsumer.accept(dataFunction.get());
      }

      return this;
   }

   public WorldParticleBuilder enableNoClip() {
      return this.setNoClip(true);
   }

   public WorldParticleBuilder disableNoClip() {
      return this.setNoClip(false);
   }

   public WorldParticleBuilder setNoClip(boolean noClip) {
      this.getParticleOptions().noClip = noClip;
      return this;
   }

   public WorldParticleBuilder setRenderType(ParticleRenderType renderType) {
      this.getParticleOptions().renderType = renderType;
      return this;
   }

   public WorldParticleBuilder setRenderTarget(LodestoneRenderLayer renderLayer) {
      this.getParticleOptions().renderLayer = renderLayer;
      return this;
   }

   public WorldParticleBuilder enableForcedSpawn() {
      return this.setForceSpawn(true);
   }

   public WorldParticleBuilder disableForcedSpawn() {
      return this.setForceSpawn(false);
   }

   public WorldParticleBuilder setForceSpawn(boolean forceSpawn) {
      this.forceSpawn = forceSpawn;
      return this;
   }

   public WorldParticleBuilder setRandomMotion(double maxSpeed) {
      return this.setRandomMotion(maxSpeed, maxSpeed, maxSpeed);
   }

   public WorldParticleBuilder setRandomMotion(double maxHSpeed, double maxVSpeed) {
      return this.setRandomMotion(maxHSpeed, maxVSpeed, maxHSpeed);
   }

   public WorldParticleBuilder setRandomMotion(double maxXSpeed, double maxYSpeed, double maxZSpeed) {
      this.maxXSpeed = maxXSpeed;
      this.maxYSpeed = maxYSpeed;
      this.maxZSpeed = maxZSpeed;
      return this;
   }

   public WorldParticleBuilder addMotion(Vector3f motion) {
      return this.addMotion(motion.x(), motion.y(), motion.z());
   }

   public WorldParticleBuilder addMotion(Vec3 motion) {
      return this.addMotion(motion.x, motion.y, motion.z);
   }

   public WorldParticleBuilder addMotion(double vx, double vy, double vz) {
      this.xMotion += vx;
      this.yMotion += vy;
      this.zMotion += vz;
      return this;
   }

   public WorldParticleBuilder setMotion(Vector3f motion) {
      return this.setMotion(motion.x(), motion.y(), motion.z());
   }

   public WorldParticleBuilder setMotion(Vec3 motion) {
      return this.setMotion(motion.x, motion.y, motion.z);
   }

   public WorldParticleBuilder setMotion(double vx, double vy, double vz) {
      this.xMotion = vx;
      this.yMotion = vy;
      this.zMotion = vz;
      return this;
   }

   public WorldParticleBuilder setRandomOffset(double maxDistance) {
      return this.setRandomOffset(maxDistance, maxDistance, maxDistance);
   }

   public WorldParticleBuilder setRandomOffset(double maxHDist, double maxVDist) {
      return this.setRandomOffset(maxHDist, maxVDist, maxHDist);
   }

   public WorldParticleBuilder setRandomOffset(double maxXDist, double maxYDist, double maxZDist) {
      this.maxXOffset = maxXDist;
      this.maxYOffset = maxYDist;
      this.maxZOffset = maxZDist;
      return this;
   }

   public WorldParticleBuilder act(Consumer<WorldParticleBuilder> particleBuilderConsumer) {
      particleBuilderConsumer.accept(this);
      return this;
   }

   public WorldParticleBuilder addTickActor(Consumer<LodestoneWorldParticle> particleActor) {
      this.getParticleOptions().tickActors.add(particleActor);
      return this;
   }

   public WorldParticleBuilder addSpawnActor(Consumer<LodestoneWorldParticle> particleActor) {
      this.getParticleOptions().spawnActors.add(particleActor);
      return this;
   }

   public WorldParticleBuilder addRenderActor(Consumer<LodestoneWorldParticle> particleActor) {
      this.getParticleOptions().renderActors.add(particleActor);
      return this;
   }

   public WorldParticleBuilder clearActors() {
      return this.clearTickActors().clearSpawnActors().clearRenderActors();
   }

   public WorldParticleBuilder clearTickActors() {
      this.getParticleOptions().tickActors.clear();
      return this;
   }

   public WorldParticleBuilder clearSpawnActors() {
      this.getParticleOptions().spawnActors.clear();
      return this;
   }

   public WorldParticleBuilder clearRenderActors() {
      this.getParticleOptions().renderActors.clear();
      return this;
   }

   public WorldParticleBuilder setNaturalLighting() {
      return this.setLightLevel(-1);
   }

   public WorldParticleBuilder setFullBrightLighting() {
      return this.setLightLevel(15728880);
   }

   public WorldParticleBuilder setLightLevel(int particleLight) {
      this.options.particleLight = particleLight;
      return this;
   }

   public WorldParticleBuilder spawn(Level level, Vec3 pos) {
      return this.spawn(level, pos.x, pos.y, pos.z);
   }

   public WorldParticleBuilder spawn(Level level, double x, double y, double z) {
      double yaw = RANDOM.nextFloat() * Math.PI * 2.0;
      double pitch = RANDOM.nextFloat() * Math.PI - (Math.PI / 2);
      double xSpeed = RANDOM.nextFloat() * this.maxXSpeed;
      double ySpeed = RANDOM.nextFloat() * this.maxYSpeed;
      double zSpeed = RANDOM.nextFloat() * this.maxZSpeed;
      this.xMotion = this.xMotion + Math.sin(yaw) * Math.cos(pitch) * xSpeed;
      this.yMotion = this.yMotion + Math.sin(pitch) * ySpeed;
      this.zMotion = this.zMotion + Math.cos(yaw) * Math.cos(pitch) * zSpeed;
      double yaw2 = RANDOM.nextFloat() * Math.PI * 2.0;
      double pitch2 = RANDOM.nextFloat() * Math.PI - (Math.PI / 2);
      double xDist = RANDOM.nextFloat() * this.maxXOffset;
      double yDist = RANDOM.nextFloat() * this.maxYOffset;
      double zDist = RANDOM.nextFloat() * this.maxZOffset;
      double xPos = Math.sin(yaw2) * Math.cos(pitch2) * xDist;
      double yPos = Math.sin(pitch2) * yDist;
      double zPos = Math.cos(yaw2) * Math.cos(pitch2) * zDist;
      level.addParticle(this.getParticleOptions(), this.forceSpawn, x + xPos, y + yPos, z + zPos, this.xMotion, this.yMotion, this.zMotion);
      return this;
   }

   public WorldParticleBuilder repeat(Level level, Vec3 pos, int n) {
      return this.repeat(level, pos.x, pos.y, pos.z, n);
   }

   public WorldParticleBuilder repeat(Level level, double x, double y, double z, int n) {
      for (int i = 0; i < n; i++) {
         this.spawn(level, x, y, z);
      }

      return this;
   }

   public WorldParticleBuilder surroundBlock(Level level, BlockPos pos, Direction... directions) {
      if (directions.length == 0) {
         directions = Direction.values();
      }

      for (Direction direction : directions) {
         double yaw = RANDOM.nextFloat() * Math.PI * 2.0;
         double pitch = RANDOM.nextFloat() * Math.PI - (Math.PI / 2);
         double xSpeed = RANDOM.nextFloat() * this.maxXSpeed;
         double ySpeed = RANDOM.nextFloat() * this.maxYSpeed;
         double zSpeed = RANDOM.nextFloat() * this.maxZSpeed;
         this.xMotion = this.xMotion + Math.sin(yaw) * Math.cos(pitch) * xSpeed;
         this.yMotion = this.yMotion + Math.sin(pitch) * ySpeed;
         this.zMotion = this.zMotion + Math.cos(yaw) * Math.cos(pitch) * zSpeed;
         Axis direction$axis = direction.getAxis();
         double d0 = 0.5625;
         double xPos = direction$axis == Axis.X ? 0.5 + d0 * direction.getStepX() : RANDOM.nextDouble();
         double yPos = direction$axis == Axis.Y ? 0.5 + d0 * direction.getStepY() : RANDOM.nextDouble();
         double zPos = direction$axis == Axis.Z ? 0.5 + d0 * direction.getStepZ() : RANDOM.nextDouble();
         level.addParticle(
            this.getParticleOptions(), this.forceSpawn, pos.getX() + xPos, pos.getY() + yPos, pos.getZ() + zPos, this.xMotion, this.yMotion, this.zMotion
         );
      }

      return this;
   }

   public WorldParticleBuilder repeatSurroundBlock(Level level, BlockPos pos, int n) {
      for (int i = 0; i < n; i++) {
         this.surroundBlock(level, pos);
      }

      return this;
   }

   public WorldParticleBuilder repeatSurroundBlock(Level level, BlockPos pos, int n, Direction... directions) {
      for (int i = 0; i < n; i++) {
         this.surroundBlock(level, pos, directions);
      }

      return this;
   }

   public WorldParticleBuilder surroundVoxelShape(Level level, BlockPos pos, VoxelShape voxelShape, int max) {
      int[] c = new int[1];
      int perBoxMax = max / voxelShape.toAabbs().size();
      Supplier<Boolean> r = () -> {
         c[0]++;
         if (c[0] >= perBoxMax) {
            c[0] = 0;
            return true;
         } else {
            return false;
         }
      };
      Vec3 v = BlockPosHelper.fromBlockPos(pos);
      voxelShape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
         Vec3 b = v.add(x1, y1, z1);
         Vec3 e = v.add(x2, y2, z2);
         List<Runnable> runs = new ArrayList<>();
         runs.add(() -> this.spawnLine(level, b, v.add(x2, y1, z1)));
         runs.add(() -> this.spawnLine(level, b, v.add(x1, y2, z1)));
         runs.add(() -> this.spawnLine(level, b, v.add(x1, y1, z2)));
         runs.add(() -> this.spawnLine(level, v.add(x1, y2, z1), v.add(x2, y2, z1)));
         runs.add(() -> this.spawnLine(level, v.add(x1, y2, z1), v.add(x1, y2, z2)));
         runs.add(() -> this.spawnLine(level, e, v.add(x2, y2, z1)));
         runs.add(() -> this.spawnLine(level, e, v.add(x1, y2, z2)));
         runs.add(() -> this.spawnLine(level, e, v.add(x2, y1, z2)));
         runs.add(() -> this.spawnLine(level, v.add(x2, y1, z1), v.add(x2, y1, z2)));
         runs.add(() -> this.spawnLine(level, v.add(x1, y1, z2), v.add(x2, y1, z2)));
         runs.add(() -> this.spawnLine(level, v.add(x2, y1, z1), v.add(x2, y2, z1)));
         runs.add(() -> this.spawnLine(level, v.add(x1, y1, z2), v.add(x1, y2, z2)));
         Collections.shuffle(runs);

         for (Runnable runnable : runs) {
            runnable.run();
            if (r.get()) {
               break;
            }
         }
      });
      return this;
   }

   public WorldParticleBuilder surroundVoxelShape(Level level, BlockPos pos, BlockState state, int max) {
      VoxelShape voxelShape = state.getShape(level, pos);
      if (voxelShape.isEmpty()) {
         voxelShape = Shapes.block();
      }

      return this.surroundVoxelShape(level, pos, voxelShape, max);
   }

   public WorldParticleBuilder spawnAtRandomFace(Level level, BlockPos pos) {
      Direction direction = Direction.values()[RANDOM.nextInt(Direction.values().length)];
      double yaw = RANDOM.nextFloat() * Math.PI * 2.0;
      double pitch = RANDOM.nextFloat() * Math.PI - (Math.PI / 2);
      double xSpeed = RANDOM.nextFloat() * this.maxXSpeed;
      double ySpeed = RANDOM.nextFloat() * this.maxYSpeed;
      double zSpeed = RANDOM.nextFloat() * this.maxZSpeed;
      this.xMotion = this.xMotion + Math.sin(yaw) * Math.cos(pitch) * xSpeed;
      this.yMotion = this.yMotion + Math.sin(pitch) * ySpeed;
      this.zMotion = this.zMotion + Math.cos(yaw) * Math.cos(pitch) * zSpeed;
      Axis direction$axis = direction.getAxis();
      double d0 = 0.5625;
      double xPos = direction$axis == Axis.X ? 0.5 + d0 * direction.getStepX() : RANDOM.nextDouble();
      double yPos = direction$axis == Axis.Y ? 0.5 + d0 * direction.getStepY() : RANDOM.nextDouble();
      double zPos = direction$axis == Axis.Z ? 0.5 + d0 * direction.getStepZ() : RANDOM.nextDouble();
      level.addParticle(
         this.getParticleOptions(), this.forceSpawn, pos.getX() + xPos, pos.getY() + yPos, pos.getZ() + zPos, this.xMotion, this.yMotion, this.zMotion
      );
      return this;
   }

   public WorldParticleBuilder repeatRandomFace(Level level, BlockPos pos, int n) {
      for (int i = 0; i < n; i++) {
         this.spawnAtRandomFace(level, pos);
      }

      return this;
   }

   public WorldParticleBuilder createBlockOutline(Level level, BlockPos pos, BlockState state) {
      VoxelShape voxelShape = state.getShape(level, pos);
      voxelShape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
         Vec3 v = BlockPosHelper.fromBlockPos(pos);
         Vec3 b = v.add(x1, y1, z1);
         Vec3 e = v.add(x2, y2, z2);
         this.spawnLine(level, b, v.add(x2, y1, z1));
         this.spawnLine(level, b, v.add(x1, y2, z1));
         this.spawnLine(level, b, v.add(x1, y1, z2));
         this.spawnLine(level, v.add(x1, y2, z1), v.add(x2, y2, z1));
         this.spawnLine(level, v.add(x1, y2, z1), v.add(x1, y2, z2));
         this.spawnLine(level, e, v.add(x2, y2, z1));
         this.spawnLine(level, e, v.add(x1, y2, z2));
         this.spawnLine(level, e, v.add(x2, y1, z2));
         this.spawnLine(level, v.add(x2, y1, z1), v.add(x2, y1, z2));
         this.spawnLine(level, v.add(x1, y1, z2), v.add(x2, y1, z2));
         this.spawnLine(level, v.add(x2, y1, z1), v.add(x2, y2, z1));
         this.spawnLine(level, v.add(x1, y1, z2), v.add(x1, y2, z2));
      });
      return this;
   }

   public WorldParticleBuilder spawnLine(Level level, Vec3 one, Vec3 two) {
      double yaw = RANDOM.nextFloat() * Math.PI * 2.0;
      double pitch = RANDOM.nextFloat() * Math.PI - (Math.PI / 2);
      double xSpeed = RANDOM.nextFloat() * this.maxXSpeed;
      double ySpeed = RANDOM.nextFloat() * this.maxYSpeed;
      double zSpeed = RANDOM.nextFloat() * this.maxZSpeed;
      this.xMotion = this.xMotion + Math.sin(yaw) * Math.cos(pitch) * xSpeed;
      this.yMotion = this.yMotion + Math.sin(pitch) * ySpeed;
      this.zMotion = this.zMotion + Math.cos(yaw) * Math.cos(pitch) * zSpeed;
      Vec3 pos = one.lerp(two, RANDOM.nextDouble());
      level.addParticle(this.getParticleOptions(), this.forceSpawn, pos.x, pos.y, pos.z, this.xMotion, this.yMotion, this.zMotion);
      return this;
   }

   public WorldParticleBuilder modifyColorData(Consumer<ColorParticleData> dataConsumer) {
      return (WorldParticleBuilder)super.modifyColorData(dataConsumer);
   }

   public WorldParticleBuilder setColorData(ColorParticleDataWrapper colorData) {
      return (WorldParticleBuilder)super.setColorData(colorData);
   }

   public WorldParticleBuilder modifyScaleData(Consumer<GenericParticleData> dataConsumer) {
      return (WorldParticleBuilder)super.modifyScaleData(dataConsumer);
   }

   public WorldParticleBuilder setScaleData(GenericParticleDataWrapper scaleData) {
      return (WorldParticleBuilder)super.setScaleData(scaleData);
   }

   public WorldParticleBuilder modifyLengthData(Consumer<GenericParticleData> dataConsumer) {
      return (WorldParticleBuilder)super.modifyLengthData(dataConsumer);
   }

   public WorldParticleBuilder setLengthData(GenericParticleDataWrapper lengthData) {
      return (WorldParticleBuilder)super.setLengthData(lengthData);
   }

   public WorldParticleBuilder setLifeDelay(int lifeDelay) {
      return (WorldParticleBuilder)super.setLifeDelay(lifeDelay);
   }

   public WorldParticleBuilder setLifeDelay(Supplier<Integer> supplier) {
      return (WorldParticleBuilder)super.setLifeDelay(supplier);
   }

   public WorldParticleBuilder setLifetime(int lifetime) {
      return (WorldParticleBuilder)super.setLifetime(lifetime);
   }

   public WorldParticleBuilder setLifetime(Supplier<Integer> supplier) {
      return (WorldParticleBuilder)super.setLifetime(supplier);
   }

   public WorldParticleBuilder setGravity(float gravity) {
      return (WorldParticleBuilder)super.setGravity(gravity);
   }

   public WorldParticleBuilder setGravity(Supplier<Float> supplier) {
      return (WorldParticleBuilder)super.setGravity(supplier);
   }

   public WorldParticleBuilder setFriction(float friction) {
      return (WorldParticleBuilder)super.setFriction(friction);
   }

   public WorldParticleBuilder setFriction(Supplier<Float> supplier) {
      return (WorldParticleBuilder)super.setFriction(supplier);
   }

   public WorldParticleBuilder multiplyLifeDelay(float multiplier) {
      return (WorldParticleBuilder)super.multiplyLifeDelay(multiplier);
   }

   public WorldParticleBuilder modifyLifeDelay(Int2IntFunction modifier) {
      return (WorldParticleBuilder)super.modifyLifeDelay(modifier);
   }

   public WorldParticleBuilder setLifeDelayModifier(float multiplier) {
      return (WorldParticleBuilder)super.setLifeDelayModifier(multiplier);
   }

   public WorldParticleBuilder setLifeDelayModifier(Int2IntFunction modifier) {
      return (WorldParticleBuilder)super.setLifeDelayModifier(modifier);
   }

   public WorldParticleBuilder multiplyLifetime(float multiplier) {
      return (WorldParticleBuilder)super.multiplyLifetime(multiplier);
   }

   public WorldParticleBuilder modifyLifetime(Int2IntFunction modifier) {
      return (WorldParticleBuilder)super.modifyLifetime(modifier);
   }

   public WorldParticleBuilder setLifetimeModifier(float multiplier) {
      return (WorldParticleBuilder)super.setLifetimeModifier(multiplier);
   }

   public WorldParticleBuilder setLifetimeModifier(Int2IntFunction modifier) {
      return (WorldParticleBuilder)super.setLifetimeModifier(modifier);
   }

   public WorldParticleBuilder multiplyGravity(float multiplier) {
      return (WorldParticleBuilder)super.multiplyGravity(multiplier);
   }

   public WorldParticleBuilder modifyGravity(Float2FloatFunction modifier) {
      return (WorldParticleBuilder)super.modifyGravity(modifier);
   }

   public WorldParticleBuilder setGravityModifier(float multiplier) {
      return (WorldParticleBuilder)super.setGravityModifier(multiplier);
   }

   public WorldParticleBuilder setGravityModifier(Float2FloatFunction modifier) {
      return (WorldParticleBuilder)super.setGravityModifier(modifier);
   }

   public WorldParticleBuilder multiplyFriction(float multiplier) {
      return (WorldParticleBuilder)super.multiplyFriction(multiplier);
   }

   public WorldParticleBuilder modifyFriction(Float2FloatFunction modifier) {
      return (WorldParticleBuilder)super.modifyFriction(modifier);
   }

   public WorldParticleBuilder setFrictionModifier(float multiplier) {
      return (WorldParticleBuilder)super.setFrictionModifier(multiplier);
   }

   public WorldParticleBuilder setFrictionModifier(Float2FloatFunction modifier) {
      return (WorldParticleBuilder)super.setFrictionModifier(modifier);
   }

   public WorldParticleBuilder setSpinData(SpinParticleDataWrapper spinData) {
      return (WorldParticleBuilder)super.setSpinData(spinData);
   }

   public WorldParticleBuilder modifySpinData(Consumer<SpinParticleData> dataConsumer) {
      return (WorldParticleBuilder)super.modifySpinData(dataConsumer);
   }

   public WorldParticleBuilder setTransparencyData(GenericParticleDataWrapper transparencyData) {
      return (WorldParticleBuilder)super.setTransparencyData(transparencyData);
   }

   public WorldParticleBuilder modifyTransparencyData(Consumer<GenericParticleData> dataConsumer) {
      return (WorldParticleBuilder)super.modifyTransparencyData(dataConsumer);
   }

   public WorldParticleBuilder setSpritePicker(SimpleParticleOptions.ParticleSpritePicker spritePicker) {
      return (WorldParticleBuilder)super.setSpritePicker(spritePicker);
   }
}
