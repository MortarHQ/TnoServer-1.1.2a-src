package team.lodestar.lodestone.systems.screenshake;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.client.Camera;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.easing.Easing;

public class ScreenshakeInstance {
   public static final Codec<ScreenshakeInstance> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.fieldOf("duration").forGetter(data -> data.duration),
            Codec.FLOAT.fieldOf("startingStrength").forGetter(data -> data.startingStrength),
            Codec.FLOAT.fieldOf("middleStrength").forGetter(data -> data.middleStrength),
            Codec.FLOAT.fieldOf("endingStrength").forGetter(data -> data.endingStrength),
            Easing.CODEC.fieldOf("startingCurve").forGetter(data -> data.startingCurve),
            Easing.CODEC.fieldOf("endingCurve").forGetter(data -> data.endingCurve),
            Codec.FLOAT.fieldOf("coefficient").forGetter(data -> data.coefficient),
            ScreenshakeInstance.ScreenshakePositionData.CODEC.optionalFieldOf("positionData").forGetter(data -> Optional.of(data.positionData))
         )
         .apply(instance, ScreenshakeInstance::new)
   );
   public static StreamCodec<ByteBuf, ScreenshakeInstance> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
   protected final int duration;
   protected final float startingStrength;
   protected final float middleStrength;
   protected final float endingStrength;
   protected final Easing startingCurve;
   protected final Easing endingCurve;
   protected final float coefficient;
   protected final ScreenshakeInstance.ScreenshakePositionData positionData;
   protected int progress;
   protected boolean expired;

   public ScreenshakeInstance(
      int duration,
      float startingStrength,
      float middleStrength,
      float endingStrength,
      Easing startingCurve,
      Easing endingCurve,
      float coefficient,
      Optional<ScreenshakeInstance.ScreenshakePositionData> positionData
   ) {
      this.duration = duration;
      this.startingStrength = startingStrength;
      this.middleStrength = middleStrength;
      this.endingStrength = endingStrength;
      this.startingCurve = startingCurve;
      this.endingCurve = endingCurve;
      this.coefficient = coefficient;
      this.positionData = positionData.orElse(null);
   }

   public void tick() {
      if (this.progress < this.duration) {
         this.progress++;
         if (this.progress == this.duration) {
            this.expired = true;
         }
      }
   }

   public float getStrength(Camera camera) {
      float strength = this.getStrength();
      if (this.positionData == null) {
         return strength;
      } else {
         Vec3 cameraPos = camera.getPosition();
         Vec3 center = this.positionData.center();
         float distance = (float)cameraPos.distanceTo(center);
         float falloffDistance = this.positionData.falloffDistance();
         if (!(distance > falloffDistance) && falloffDistance != 0.0F) {
            float falloff = 1.0F - this.positionData.falloffCurve().clamped(distance / falloffDistance, 0.0F, 1.0F);
            return strength * falloff;
         } else {
            return 0.0F;
         }
      }
   }

   protected float getStrength() {
      if (this.expired) {
         return 0.0F;
      }

      float percentage = this.progress * this.coefficient / this.duration;
      if (this.isTrinary()) {
         if (percentage >= 0.5F) {
            float delta = this.endingCurve.clamped((percentage - 0.5F) * 2.0F, 0.0F, 1.0F);
            return Mth.lerp(delta, this.middleStrength, this.endingStrength);
         } else {
            float delta = this.startingCurve.clamped(percentage * 2.0F, 0.0F, 1.0F);
            return Mth.lerp(delta, this.startingStrength, this.middleStrength);
         }
      } else {
         float delta = this.startingCurve.clamped(percentage, 0.0F, 1.0F);
         return Mth.lerp(delta, this.startingStrength, this.middleStrength);
      }
   }

   public boolean isExpired() {
      return this.expired;
   }

   public boolean isTrinary() {
      return this.endingStrength != this.middleStrength;
   }

   public record ScreenshakePositionData(Vec3 center, float falloffDistance, Easing falloffCurve) {
      public static final Codec<ScreenshakeInstance.ScreenshakePositionData> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Vec3.CODEC.fieldOf("center").forGetter(data -> data.center),
               Codec.FLOAT.fieldOf("falloffDistance").forGetter(data -> data.falloffDistance),
               Easing.CODEC.fieldOf("falloffCurve").forGetter(data -> data.falloffCurve)
            )
            .apply(instance, ScreenshakeInstance.ScreenshakePositionData::new)
      );
      public static StreamCodec<ByteBuf, ScreenshakeInstance.ScreenshakePositionData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
   }
}
