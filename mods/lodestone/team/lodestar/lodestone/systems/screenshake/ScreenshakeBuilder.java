package team.lodestar.lodestone.systems.screenshake;

import java.util.Optional;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.easing.Easing;

public class ScreenshakeBuilder {
   protected int duration = 20;
   protected float startingStrength = 1.0F;
   protected float middleStrength = 0.0F;
   protected float endingStrength = 0.0F;
   protected Easing startingCurve = Easing.LINEAR;
   protected Easing endingCurve = Easing.LINEAR;
   protected float coefficient = 1.0F;
   protected ScreenshakeInstance.ScreenshakePositionData positionData;

   public static ScreenshakeBuilder create() {
      return new ScreenshakeBuilder();
   }

   public ScreenshakeBuilder setDuration(int duration) {
      this.duration = duration;
      return this;
   }

   public ScreenshakeBuilder setStrength(float strength) {
      return this.setStrength(strength, strength);
   }

   public ScreenshakeBuilder setStrength(float startingStrength, float endingStrength) {
      return this.setStrength(startingStrength, endingStrength, endingStrength);
   }

   public ScreenshakeBuilder setStrength(float startingStrength, float middleStrength, float endingStrength) {
      return this.setStartingStrength(startingStrength).setMiddleStrength(middleStrength).setEndingStrength(endingStrength);
   }

   protected ScreenshakeBuilder setStartingStrength(float startingStrength) {
      this.startingStrength = startingStrength;
      return this;
   }

   protected ScreenshakeBuilder setMiddleStrength(float middleStrength) {
      this.middleStrength = middleStrength;
      return this;
   }

   protected ScreenshakeBuilder setEndingStrength(float endingStrength) {
      this.endingStrength = endingStrength;
      return this;
   }

   public ScreenshakeBuilder setEasing(Easing curve) {
      return this.setEasing(curve, curve);
   }

   public ScreenshakeBuilder setEasing(Easing startingCurve, Easing endingCurve) {
      return this.setStartingEasing(startingCurve).setEndingEasing(endingCurve);
   }

   protected ScreenshakeBuilder setStartingEasing(Easing startingCurve) {
      this.startingCurve = startingCurve;
      return this;
   }

   protected ScreenshakeBuilder setEndingEasing(Easing endingCurve) {
      this.endingCurve = endingCurve;
      return this;
   }

   public ScreenshakeBuilder setCoefficient(float coefficient) {
      this.coefficient = coefficient;
      return this;
   }

   public ScreenshakeBuilder placedAt(Vec3 center, float falloffDistance) {
      return this.placedAt(center, falloffDistance, Easing.LINEAR);
   }

   public ScreenshakeBuilder placedAt(Vec3 center, float falloffDistance, Easing falloffCurve) {
      return this.placedAt(new ScreenshakeInstance.ScreenshakePositionData(center, falloffDistance, falloffCurve));
   }

   public ScreenshakeBuilder placedAt(ScreenshakeInstance.ScreenshakePositionData positionData) {
      this.positionData = positionData;
      return this;
   }

   public ScreenshakeInstance build() {
      return new ScreenshakeInstance(
         this.duration,
         this.startingStrength,
         this.middleStrength,
         this.endingStrength,
         this.startingCurve,
         this.endingCurve,
         this.coefficient,
         Optional.ofNullable(this.positionData)
      );
   }
}
