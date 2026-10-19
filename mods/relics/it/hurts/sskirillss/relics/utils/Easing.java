package it.hurts.sskirillss.relics.utils;

public class Easing {
   public static float linear(float progress) {
      return progress;
   }

   public static float easeInSine(float progress) {
      return (float)(1.0 - Math.cos(progress * Math.PI / 2.0));
   }

   public static float easeOutSine(float progress) {
      return (float)Math.sin(progress * Math.PI / 2.0);
   }

   public static float easeInOutSine(float progress) {
      return (float)(-(Math.cos(Math.PI * progress) - 1.0)) / 2.0F;
   }

   public static float easeInQuad(float progress) {
      return progress * progress;
   }

   public static float easeOutQuad(float progress) {
      return 1.0F - (1.0F - progress) * (1.0F - progress);
   }

   public static float easeInOutQuad(float progress) {
      return progress < 0.5 ? 2.0F * progress * progress : 1.0F - (float)Math.pow(-2.0F * progress + 2.0F, 2.0) / 2.0F;
   }

   public static float easeInCubic(float progress) {
      return progress * progress * progress;
   }

   public static float easeOutCubic(float progress) {
      return 1.0F - (float)Math.pow(1.0F - progress, 3.0);
   }

   public static float easeInOutCubic(float progress) {
      return progress < 0.5 ? 4.0F * progress * progress * progress : 1.0F - (float)Math.pow(-2.0F * progress + 2.0F, 3.0) / 2.0F;
   }

   public static float easeInQuart(float progress) {
      return progress * progress * progress * progress;
   }

   public static float easeOutQuart(float progress) {
      return 1.0F - (float)Math.pow(1.0F - progress, 4.0);
   }

   public static float easeInOutQuart(float progress) {
      return progress < 0.5 ? 8.0F * progress * progress * progress * progress : 1.0F - (float)Math.pow(-2.0F * progress + 2.0F, 4.0) / 2.0F;
   }

   public static float easeInQuint(float progress) {
      return progress * progress * progress * progress * progress;
   }

   public static float easeOutQuint(float progress) {
      return 1.0F - (float)Math.pow(1.0F - progress, 5.0);
   }

   public static float easeInOutQuint(float progress) {
      return progress < 0.5 ? 16.0F * progress * progress * progress * progress * progress : 1.0F - (float)Math.pow(-2.0F * progress + 2.0F, 5.0) / 2.0F;
   }

   public static float easeInExpo(float progress) {
      return progress == 0.0F ? 0.0F : (float)Math.pow(2.0, 10.0F * progress - 10.0F);
   }

   public static float easeOutExpo(float progress) {
      return progress == 1.0F ? 1.0F : 1.0F - (float)Math.pow(2.0, -10.0F * progress);
   }

   public static float easeInOutExpo(float progress) {
      if (progress == 0.0F) {
         return 0.0F;
      } else if (progress == 1.0F) {
         return 1.0F;
      } else {
         return progress < 0.5 ? (float)Math.pow(2.0, 20.0F * progress - 10.0F) / 2.0F : (2.0F - (float)Math.pow(2.0, -20.0F * progress + 10.0F)) / 2.0F;
      }
   }

   public static float easeInCirc(float progress) {
      return 1.0F - (float)Math.sqrt(1.0 - Math.pow(progress, 2.0));
   }

   public static float easeOutCirc(float progress) {
      return (float)Math.sqrt(1.0 - Math.pow(progress - 1.0F, 2.0));
   }

   public static float easeInOutCirc(float progress) {
      return progress < 0.5
         ? (1.0F - (float)Math.sqrt(1.0 - Math.pow(2.0F * progress, 2.0))) / 2.0F
         : ((float)Math.sqrt(1.0 - Math.pow(-2.0F * progress + 2.0F, 2.0)) + 1.0F) / 2.0F;
   }

   public static float easeInBack(float progress) {
      float c1 = 1.70158F;
      float c3 = 2.70158F;
      return 2.70158F * progress * progress * progress - 1.70158F * progress * progress;
   }

   public static float easeOutBack(float progress) {
      float c1 = 1.70158F;
      float c3 = 2.70158F;
      return 1.0F + 2.70158F * (float)Math.pow(progress - 1.0F, 3.0) + 1.70158F * (float)Math.pow(progress - 1.0F, 2.0);
   }

   public static float easeInOutBack(float progress) {
      float c1 = 1.70158F;
      float c2 = 2.5949094F;
      return progress < 0.5
         ? (float)(Math.pow(2.0F * progress, 2.0) * (7.189819F * progress - 2.5949094F)) / 2.0F
         : (float)(Math.pow(2.0F * progress - 2.0F, 2.0) * (3.5949094F * (progress * 2.0F - 2.0F) + 2.5949094F) + 2.0) / 2.0F;
   }

   public static float easeInElastic(float progress) {
      float c4 = (float) (Math.PI * 2.0 / 3.0);
      return progress == 0.0F
         ? 0.0F
         : (
            progress == 1.0F
               ? 1.0F
               : -((float)Math.pow(2.0, 10.0F * progress - 10.0F)) * (float)Math.sin((progress * 10.0F - 10.75) * (float) (Math.PI * 2.0 / 3.0))
         );
   }

   public static float easeOutElastic(float progress) {
      float c4 = (float) (Math.PI * 2.0 / 3.0);
      return progress == 0.0F
         ? 0.0F
         : (
            progress == 1.0F
               ? 1.0F
               : (float)Math.pow(2.0, -10.0F * progress) * (float)Math.sin((progress * 10.0F - 0.75) * (float) (Math.PI * 2.0 / 3.0)) + 1.0F
         );
   }

   public static float easeInOutElastic(float progress) {
      float c5 = (float) Math.PI * 4.0F / 9.0F;
      return progress == 0.0F
         ? 0.0F
         : (
            progress == 1.0F
               ? 1.0F
               : (
                  progress < 0.5
                     ? -((float)(Math.pow(2.0, 20.0F * progress - 10.0F) * Math.sin((20.0F * progress - 11.125) * (float) Math.PI * 4.0F / 9.0F))) / 2.0F
                     : (float)(Math.pow(2.0, -20.0F * progress + 10.0F) * Math.sin((20.0F * progress - 11.125) * (float) Math.PI * 4.0F / 9.0F)) / 2.0F + 1.0F
               )
         );
   }

   public static float easeInBounce(float progress) {
      return 1.0F - easeOutBounce(1.0F - progress);
   }

   public static float easeOutBounce(float progress) {
      float n1 = 7.5625F;
      float d1 = 2.75F;
      if (progress < 0.36363637F) {
         return 7.5625F * progress * progress;
      } else if (progress < 0.72727275F) {
         float var5;
         return 7.5625F * (var5 = progress - 0.54545456F) * var5 + 0.75F;
      } else {
         float var3;
         float var4;
         return progress < 0.9090909090909091
            ? 7.5625F * (var3 = progress - 0.8181818F) * var3 + 0.9375F
            : 7.5625F * (var4 = progress - 0.95454544F) * var4 + 0.984375F;
      }
   }

   public static float easeInOutBounce(float progress) {
      return progress < 0.5 ? (1.0F - easeOutBounce(1.0F - 2.0F * progress)) / 2.0F : (1.0F + easeOutBounce(2.0F * progress - 1.0F)) / 2.0F;
   }
}
