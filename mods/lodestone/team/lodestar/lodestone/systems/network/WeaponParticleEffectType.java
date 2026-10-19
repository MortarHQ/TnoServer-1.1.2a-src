package team.lodestar.lodestone.systems.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.awt.Color;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectColorData;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectExtraData;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectPayload;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectPositionData;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectType;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleDataWrapper;

public abstract class WeaponParticleEffectType<T extends WeaponParticleEffectType.WeaponParticleEffectData> extends NetworkedParticleEffectType<T> {
   public static WeaponParticleEffectType.WeaponParticleEffectData createData(Vec3 direction, boolean mirror, float angle) {
      return new WeaponParticleEffectType.WeaponParticleEffectData(direction, mirror, angle);
   }

   @Override
   public Optional<StreamCodec<ByteBuf, ? extends NetworkedParticleEffectExtraData>> getExtraCodec() {
      return Optional.of(WeaponParticleEffectType.WeaponParticleEffectData.STREAM_CODEC);
   }

   @Override
   public Optional<? extends NetworkedParticleEffectExtraData> getDefaultExtraData() {
      return Optional.of(WeaponParticleEffectType.WeaponParticleEffectData.DEFAULT);
   }

   public WeaponParticleEffectType(String id) {
      super(id);
   }

   public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> createEffect(BlockPos position) {
      return this.createEffect().at(position);
   }

   public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> createEffect(Vec3 position) {
      return this.createEffect().at(position);
   }

   public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> createEffect(Entity target) {
      return this.createEffect().targets(target);
   }

   public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> createEffect() {
      return new WeaponParticleEffectType.WeaponParticleEffectBuilder<>(this);
   }

   public static class WeaponParticleEffectBuilder<T extends WeaponParticleEffectType.WeaponParticleEffectData>
      extends NetworkedParticleEffectType.ParticleEffectBuilder<T> {
      protected Vec3 direction;
      protected Entity source;
      protected Entity target;
      protected boolean tiedToTarget;
      protected boolean isMirrored;
      protected float slashRotation;
      protected Vec3 absoluteOffset = Vec3.ZERO;
      protected float horizontalOffset;
      protected float forwardOffset;
      protected float upwardOffset;
      protected float horizontalDeviation;
      protected float verticalDeviation;
      protected float deviationAngle;
      protected boolean exactDeviationAngle;

      public WeaponParticleEffectBuilder(WeaponParticleEffectType<T> type) {
         super(type);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> originatesFrom(Entity source) {
         this.source = source;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> targets(Entity target) {
         this.target = target;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> aimedAt(Vec3 direction) {
         this.direction = direction;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> tiedToTarget() {
         this.tiedToTarget = true;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> mirroredRandomly(RandomSource random) {
         return this.mirrored(random.nextBoolean());
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> mirrored() {
         return this.mirrored(true);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> mirrored(boolean isMirrored) {
         this.isMirrored = isMirrored;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> randomSlashRotation(RandomSource random) {
         return this.slashRotation(random.nextFloat() * 6.28F);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> verticalSlashRotation() {
         return this.slashRotation(1.57F);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> slashRotation(float slashRotation) {
         this.slashRotation = slashRotation;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> setOffsetsFromHand(InteractionHand hand) {
         return this.horizontalOffset(hand.equals(InteractionHand.MAIN_HAND) ? 0.4F : -0.4F);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> horizontalOffset(float horizontalOffset) {
         this.horizontalOffset = horizontalOffset;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> forwardOffset(float forwardOffset) {
         this.forwardOffset = forwardOffset;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> upwardOffset(float upwardOffset) {
         this.upwardOffset = upwardOffset;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> randomOffset(RandomSource random, float min, float max) {
         return this.absoluteOffset(
            new Vec3(random.nextFloat() * (max - min) + min, random.nextFloat() * (max - min) + min, random.nextFloat() * (max - min) + min)
         );
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> absoluteOffset(Vec3 absoluteOffset) {
         this.absoluteOffset = absoluteOffset;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> deviation(float horizontalDeviation, float verticalDeviation, float deviationAngle) {
         return this.deviation(horizontalDeviation, verticalDeviation).deviationAngle(deviationAngle);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> deviation(float horizontalDeviation, float verticalDeviation) {
         return this.horizontalDeviation(horizontalDeviation).verticalDeviation(verticalDeviation);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> deviation(float deviation) {
         return this.horizontalDeviation(deviation).verticalDeviation(deviation);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> horizontalDeviation(float horizontalDeviation) {
         this.horizontalDeviation = horizontalDeviation;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> verticalDeviation(float verticalDeviation) {
         this.verticalDeviation = verticalDeviation;
         return this;
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> randomDeviationAngle(RandomSource random) {
         return this.deviationAngle(random.nextFloat() * 6.28F);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> deviationAngle(float deviationAngle) {
         this.deviationAngle = deviationAngle;
         this.exactDeviationAngle = true;
         return this;
      }

      protected Vec3 getEffectPosition(Vec3 direction) {
         Vec3 positionToUse = null;
         if (this.position != null) {
            positionToUse = this.position.getAsVector();
         } else {
            Entity anchor = null;
            if (this.tiedToTarget && this.target != null) {
               anchor = this.target;
            } else if (this.source != null) {
               anchor = this.source;
            }

            if (anchor != null) {
               positionToUse = anchor.position().add(0.0, anchor.getBbHeight() / 2.0F, 0.0);
            }
         }

         if (positionToUse == null) {
            throw new IllegalArgumentException("Networked Weapon Particle Effect failed to provide a valid position to spawn at. This is a bug.");
         }

         float yRot = (float)(Mth.atan2(direction.x, direction.z) * 180.0F / (float)Math.PI);
         float yaw = (float)Math.toRadians(yRot);
         Vec3 left = new Vec3(-Math.cos(yaw), 0.0, Math.sin(yaw));
         Vec3 up = left.cross(direction);
         Vec3 offset = this.absoluteOffset.add(direction.scale(this.forwardOffset)).add(up.scale(this.upwardOffset)).add(left.scale(this.horizontalOffset));
         return positionToUse.add(offset);
      }

      protected Vec3 getEffectDirection(RandomSource random) {
         Vec3 directionToUse = this.direction;
         if (directionToUse == null) {
            if (this.target != null && this.source != null) {
               directionToUse = this.target.position().subtract(this.source.position()).normalize();
            } else if (this.source != null) {
               directionToUse = this.source.getLookAngle();
            } else {
               double theta = random.nextDouble() * 2.0 * Math.PI;
               double phi = Math.acos(2.0 * random.nextDouble() - 1.0);
               double x = Math.sin(phi) * Math.cos(theta);
               double y = Math.sin(phi) * Math.sin(theta);
               double z = Math.cos(phi);
               directionToUse = new Vec3(x, y, z);
            }
         }

         if (this.horizontalDeviation == 0.0F && this.verticalDeviation == 0.0F) {
            return directionToUse;
         }

         float yRot = (float)(Mth.atan2(directionToUse.x, directionToUse.z) * 180.0F / (float)Math.PI);
         float yaw = (float)Math.toRadians(yRot);
         Vec3 left = new Vec3(-Math.cos(yaw), 0.0, Math.sin(yaw));
         Vec3 up = left.cross(directionToUse);
         float leftOffset = this.horizontalDeviation;
         float upOffset = this.verticalDeviation;
         if (this.exactDeviationAngle) {
            leftOffset *= Mth.sin(this.deviationAngle);
            upOffset *= Mth.cos(this.deviationAngle);
         }

         return directionToUse.add(left.scale(leftOffset)).add(up.scale(upOffset)).normalize();
      }

      public float getSlashRotation() {
         return this.slashRotation + (this.isMirrored ? 3.14F : 0.0F);
      }

      protected void updateData(ServerLevel level) {
         Vec3 effectDirection = this.getEffectDirection(level.getRandom());
         Vec3 effectPosition = this.getEffectPosition(effectDirection);
         this.at(effectPosition);
         if (this.color == null) {
            this.color(ColorParticleData.createGrayParticleColor(level.getRandom()));
         }

         this.customData((T)this.getCustomData().withDirection(effectDirection).withMirror(this.isMirrored).withRotation(this.slashRotation));
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> spawn(ServerLevel level) {
         this.updateData(level);
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.spawn(level);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> at(BlockPos position) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.at(position);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> at(Vec3 position) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.at(position);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> at(Entity target) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.at(target);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> at(NetworkedParticleEffectPositionData position) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.at(position);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> color(Color color) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.color(color);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> color(ColorParticleDataWrapper color) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.color(color);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> color(List<? extends ColorParticleDataWrapper> colors) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.color(colors);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> color(NetworkedParticleEffectColorData color) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.color(color);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> customData(T extra) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.customData(extra);
      }

      public WeaponParticleEffectType.WeaponParticleEffectBuilder<T> spawn(Consumer<NetworkedParticleEffectPayload> sender) {
         return (WeaponParticleEffectType.WeaponParticleEffectBuilder<T>)super.spawn(sender);
      }
   }

   public static class WeaponParticleEffectData implements NetworkedParticleEffectExtraData {
      public static final Codec<WeaponParticleEffectType.WeaponParticleEffectData> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Vec3.CODEC.fieldOf("direction").forGetter(WeaponParticleEffectType.WeaponParticleEffectData::getDirection),
               Codec.BOOL.fieldOf("mirror").forGetter(WeaponParticleEffectType.WeaponParticleEffectData::isMirrored),
               Codec.FLOAT.fieldOf("slashRotation").forGetter(WeaponParticleEffectType.WeaponParticleEffectData::getSlashRotation)
            )
            .apply(instance, WeaponParticleEffectType.WeaponParticleEffectData::new)
      );
      public static final StreamCodec<ByteBuf, WeaponParticleEffectType.WeaponParticleEffectData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
      public static final WeaponParticleEffectType.WeaponParticleEffectData DEFAULT = new WeaponParticleEffectType.WeaponParticleEffectData(
         Vec3.ZERO, false, 0.0F
      );
      private final Vec3 direction;
      private final boolean isMirrored;
      private final float slashRotation;

      public WeaponParticleEffectData(Vec3 direction, boolean isMirrored, float slashRotation) {
         this.direction = direction;
         this.isMirrored = isMirrored;
         this.slashRotation = slashRotation;
      }

      public WeaponParticleEffectType.WeaponParticleEffectData withDirection(Vec3 direction) {
         return this.modify(direction, this.isMirrored, this.slashRotation);
      }

      public WeaponParticleEffectType.WeaponParticleEffectData withMirror(boolean mirror) {
         return this.modify(this.direction, mirror, this.slashRotation);
      }

      public WeaponParticleEffectType.WeaponParticleEffectData withRotation(float slashRotation) {
         return this.modify(this.direction, this.isMirrored, slashRotation);
      }

      public WeaponParticleEffectType.WeaponParticleEffectData modify(Vec3 direction, boolean isMirrored, float slashRotation) {
         return new WeaponParticleEffectType.WeaponParticleEffectData(direction, isMirrored, slashRotation);
      }

      public Vec3 getDirection() {
         return this.direction;
      }

      public boolean isMirrored() {
         return this.isMirrored;
      }

      public float getSlashRotation() {
         return this.slashRotation;
      }
   }
}
