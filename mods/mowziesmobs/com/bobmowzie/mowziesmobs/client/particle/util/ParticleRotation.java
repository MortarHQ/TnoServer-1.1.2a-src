package com.bobmowzie.mowziesmobs.client.particle.util;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class ParticleRotation {
   public static final Codec<ParticleRotation> CODEC = Codec.either(
         ParticleRotation.FaceCamera.CODEC, Codec.either(ParticleRotation.EulerAngles.CODEC, ParticleRotation.OrientVector.CODEC)
      )
      .flatXmap(
         either -> (DataResult)either.map(DataResult::success, other -> (DataResult)other.map(DataResult::success, DataResult::success)),
         rotation -> {
            if (rotation instanceof ParticleRotation.FaceCamera faceCamera) {
               return DataResult.success(Either.left(faceCamera));
            } else if (rotation instanceof ParticleRotation.EulerAngles eulerAngles) {
               return DataResult.success(Either.right(Either.left(eulerAngles)));
            } else {
               return rotation instanceof ParticleRotation.OrientVector orientVector
                  ? DataResult.success(Either.right(Either.right(orientVector)))
                  : DataResult.error(() -> "Invalid rotation type: [" + rotation.getClass().getName() + "]");
            }
         }
      );

   public abstract void setPrevValues();

   public abstract ParticleRotation.Type type();

   public static class EulerAngles extends ParticleRotation {
      public static final Codec<ParticleRotation.EulerAngles> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.FLOAT.fieldOf("yaw").forGetter(value -> value.yaw),
               Codec.FLOAT.fieldOf("pitch").forGetter(value -> value.pitch),
               Codec.FLOAT.fieldOf("roll").forGetter(value -> value.roll)
            )
            .apply(instance, ParticleRotation.EulerAngles::new)
      );
      public static final ParticleRotation.Type TYPE = ParticleRotation.Type.EULER_ANGLES;
      public float yaw;
      public float pitch;
      public float roll;
      public float prevYaw;
      public float prevPitch;
      public float prevRoll;

      public EulerAngles(float yaw, float pitch, float roll) {
         this.yaw = this.prevYaw = yaw;
         this.pitch = this.prevPitch = pitch;
         this.roll = this.prevRoll = roll;
      }

      @Override
      public void setPrevValues() {
         this.prevYaw = this.yaw;
         this.prevPitch = this.pitch;
         this.prevRoll = this.roll;
      }

      @Override
      public ParticleRotation.Type type() {
         return TYPE;
      }
   }

   public static class FaceCamera extends ParticleRotation {
      public static final Codec<ParticleRotation.FaceCamera> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(Codec.FLOAT.fieldOf("angle").forGetter(value -> value.angle)).apply(instance, ParticleRotation.FaceCamera::new)
      );
      public static final ParticleRotation.Type TYPE = ParticleRotation.Type.FACE_CAMERA;
      public float angle;
      public float prevAngle;

      public FaceCamera(float angle) {
         this.angle = angle;
      }

      @Override
      public void setPrevValues() {
         this.prevAngle = this.angle;
      }

      @Override
      public ParticleRotation.Type type() {
         return TYPE;
      }
   }

   public static class OrientVector extends ParticleRotation {
      public static final Codec<ParticleRotation.OrientVector> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(Vec3.CODEC.fieldOf("orientation").forGetter(value -> value.orientation))
            .apply(instance, ParticleRotation.OrientVector::new)
      );
      public static final ParticleRotation.Type TYPE = ParticleRotation.Type.ORIENT_VECTOR;
      public Vec3 orientation;
      public Vec3 prevOrientation;

      public OrientVector(Vec3 orientation) {
         this.orientation = this.prevOrientation = orientation;
      }

      @Override
      public void setPrevValues() {
         this.prevOrientation = this.orientation;
      }

      @Override
      public ParticleRotation.Type type() {
         return TYPE;
      }
   }

   public enum Type implements StringRepresentable {
      FACE_CAMERA("face_camera"),
      EULER_ANGLES("euler_angles"),
      ORIENT_VECTOR("orient_vector");

      private final String id;

      Type(final String id) {
         this.id = id;
      }

      @NotNull
      public String getSerializedName() {
         return this.id;
      }
   }
}
