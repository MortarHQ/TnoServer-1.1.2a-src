package xaero.common.minimap.write;

public class MinimapWriterHelper {
   void getGlowingColour(int r, int g, int b, int[] result) {
      int total = r + g + b;
      float minBrightness = 407.0F;
      float brightener = Math.max(1.0F, minBrightness / total);
      result[0] = (int)(r * brightener);
      result[1] = (int)(g * brightener);
      result[2] = (int)(b * brightener);
   }
}
