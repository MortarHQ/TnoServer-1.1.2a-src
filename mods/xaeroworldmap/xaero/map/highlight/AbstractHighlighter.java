package xaero.map.highlight;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public abstract class AbstractHighlighter {
   protected final boolean coveringOutsideDiscovered;
   protected final int[] resultStore = new int[256];

   protected AbstractHighlighter(boolean coveringOutsideDiscovered) {
      this.coveringOutsideDiscovered = coveringOutsideDiscovered;
   }

   public abstract int calculateRegionHash(ResourceKey<Level> var1, int var2, int var3);

   public abstract boolean regionHasHighlights(ResourceKey<Level> var1, int var2, int var3);

   public abstract boolean chunkIsHighlit(ResourceKey<Level> var1, int var2, int var3);

   public abstract int[] getChunkHighlitColor(ResourceKey<Level> var1, int var2, int var3);

   public abstract Component getBlockHighlightSubtleTooltip(ResourceKey<Level> var1, int var2, int var3);

   public abstract Component getBlockHighlightBluntTooltip(ResourceKey<Level> var1, int var2, int var3);

   public abstract void addMinimapBlockHighlightTooltips(List<Component> var1, ResourceKey<Level> var2, int var3, int var4, int var5);

   protected void setResult(int x, int z, int color) {
      this.resultStore[z << 4 | x] = color;
   }

   protected int getBlend(int color1, int color2) {
      if (color1 == color2) {
         return color1;
      }

      int red1 = color1 >> 8 & 0xFF;
      int green1 = color1 >> 16 & 0xFF;
      int blue1 = color1 >> 24 & 0xFF;
      int alpha1 = color1 & 0xFF;
      int red2 = color2 >> 8 & 0xFF;
      int green2 = color2 >> 16 & 0xFF;
      int blue2 = color2 >> 24 & 0xFF;
      int alpha2 = color2 & 0xFF;
      int red = red1 + red2 >> 1;
      int green = green1 + green2 >> 1;
      int blue = blue1 + blue2 >> 1;
      int alpha = alpha1 + alpha2 >> 1;
      return blue << 24 | green << 16 | red << 8 | alpha;
   }

   public boolean isCoveringOutsideDiscovered() {
      return this.coveringOutsideDiscovered;
   }
}
