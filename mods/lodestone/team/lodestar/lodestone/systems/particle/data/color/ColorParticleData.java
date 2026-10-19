package team.lodestar.lodestone.systems.particle.data.color;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.awt.Color;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;

public class ColorParticleData implements ColorParticleDataWrapper {
   public static final Codec<ColorParticleData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.FLOAT.fieldOf("r1").forGetter(data -> data.r1),
            Codec.FLOAT.fieldOf("g1").forGetter(data -> data.g1),
            Codec.FLOAT.fieldOf("b1").forGetter(data -> data.b1),
            Codec.FLOAT.fieldOf("r2").forGetter(data -> data.r2),
            Codec.FLOAT.fieldOf("g2").forGetter(data -> data.g2),
            Codec.FLOAT.fieldOf("b2").forGetter(data -> data.b2),
            Codec.FLOAT.fieldOf("colorCoefficient").forGetter(data -> data.colorCoefficient),
            Easing.CODEC.fieldOf("colorCurveEasing").forGetter(data -> data.colorCurve),
            Codec.FLOAT.fieldOf("coefficientMultiplier").forGetter(data -> data.coefficientMultiplier)
         )
         .apply(instance, ColorParticleData::new)
   );
   protected final float r1;
   protected final float g1;
   protected final float b1;
   protected final float r2;
   protected final float g2;
   protected final float b2;
   protected final Color startingColor;
   protected final Color endingColor;
   protected final float colorCoefficient;
   protected final Easing colorCurve;
   protected float coefficientMultiplier = 1.0F;
   protected boolean locked;

   protected ColorParticleData(
      float r1, float g1, float b1, float r2, float g2, float b2, float colorCoefficient, Easing colorCurve, float coefficientMultiplier
   ) {
      this(r1, g1, b1, r2, g2, b2, colorCoefficient, colorCurve);
      this.coefficientMultiplier = coefficientMultiplier;
   }

   protected ColorParticleData(float r1, float g1, float b1, float r2, float g2, float b2, float colorCoefficient, Easing colorCurve) {
      this.r1 = r1;
      this.g1 = g1;
      this.b1 = b1;
      this.r2 = r2;
      this.g2 = g2;
      this.b2 = b2;
      this.startingColor = new Color(r1, g1, b1);
      this.endingColor = new Color(r2, g2, b2);
      this.colorCoefficient = colorCoefficient;
      this.colorCurve = colorCurve;
   }

   @Override
   public ColorParticleData unwrap() {
      return this;
   }

   public ColorParticleData lock() {
      this.locked = true;
      return this;
   }

   public Color getStartingColor() {
      return this.startingColor;
   }

   public Color getEndingColor() {
      return this.endingColor;
   }

   public float getColorCoefficient() {
      return this.colorCoefficient;
   }

   public Easing getColorCurve() {
      return this.colorCurve;
   }

   public ColorParticleData multiplyCoefficient(float coefficientMultiplier) {
      if (!this.locked) {
         this.coefficientMultiplier *= coefficientMultiplier;
      }

      return this;
   }

   public ColorParticleData overrideCoefficientMultiplier(float coefficientMultiplier) {
      if (!this.locked) {
         this.coefficientMultiplier = coefficientMultiplier;
      }

      return this;
   }

   public void rgbToHsv(float[] hsv1, float[] hsv2) {
      Color.RGBtoHSB((int)(255.0F * Math.min(1.0F, this.r1)), (int)(255.0F * Math.min(1.0F, this.g1)), (int)(255.0F * Math.min(1.0F, this.b1)), hsv1);
      Color.RGBtoHSB((int)(255.0F * Math.min(1.0F, this.r2)), (int)(255.0F * Math.min(1.0F, this.g2)), (int)(255.0F * Math.min(1.0F, this.b2)), hsv2);
   }

   public float getProgress(float age, float lifetime) {
      return Mth.clamp(age * this.colorCoefficient * this.coefficientMultiplier / lifetime, 0.0F, 1.0F);
   }

   public ColorParticleDataBuilder invert() {
      return create(this.r2, this.g2, this.b2, this.r1, this.g1, this.b1).setCoefficient(this.colorCoefficient).setEasing(this.colorCurve);
   }

   public ColorParticleDataBuilder copy() {
      return create(this.r1, this.g1, this.b1, this.r2, this.g2, this.b2).setCoefficient(this.colorCoefficient).setEasing(this.colorCurve);
   }

   public static ColorParticleDataBuilder create(float r1, float g1, float b1, float r2, float g2, float b2) {
      return new ColorParticleDataBuilder(r1, g1, b1, r2, g2, b2);
   }

   public static ColorParticleDataBuilder create(float r, float g, float b) {
      return new ColorParticleDataBuilder(r, g, b, r, g, b);
   }

   public static ColorParticleDataBuilder create(Color start, Color end) {
      return create(
         start.getRed() / 255.0F, start.getGreen() / 255.0F, start.getBlue() / 255.0F, end.getRed() / 255.0F, end.getGreen() / 255.0F, end.getBlue() / 255.0F
      );
   }

   public static ColorParticleDataBuilder create(Color color) {
      return create(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
   }

   public static ColorParticleData createGrayParticleColor(RandomSource random) {
      return createGrayParticleColor(random, 0.6F, 1.0F);
   }

   public static ColorParticleData createGrayParticleColor(RandomSource random, float min, float max) {
      int brightness = (int)(255.0F * RandomHelper.randomBetween(random, min, max));
      Color color = new Color(brightness, brightness, brightness);
      return create(color, color.darker()).setEasing(Easing.SINE_IN_OUT).build();
   }
}
