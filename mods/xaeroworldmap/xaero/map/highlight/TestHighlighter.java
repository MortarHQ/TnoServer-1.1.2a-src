package xaero.map.highlight;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class TestHighlighter extends ChunkHighlighter {
   public TestHighlighter() {
      super(true);
   }

   @Override
   public boolean regionHasHighlights(ResourceKey<Level> dimension, int regionX, int regionZ) {
      return true;
   }

   @Override
   protected int[] getColors(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      if (!this.chunkIsHighlit(dimension, chunkX, chunkZ)) {
         return null;
      }

      int centerColor = 1442796919;
      int sideColor = 1442797004;
      this.resultStore[0] = centerColor;
      this.resultStore[1] = (chunkZ & 3) == 0 ? sideColor : centerColor;
      this.resultStore[2] = (chunkX & 3) == 3 ? sideColor : centerColor;
      this.resultStore[3] = (chunkZ & 3) == 3 ? sideColor : centerColor;
      this.resultStore[4] = (chunkX & 3) == 0 ? sideColor : centerColor;
      return this.resultStore;
   }

   @Override
   public int calculateRegionHash(ResourceKey<Level> dimension, int regionX, int regionZ) {
      return 51;
   }

   @Override
   public boolean chunkIsHighlit(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      return (chunkX >> 2 & 1) == (chunkZ >> 2 & 1);
   }

   @Override
   public Component getChunkHighlightSubtleTooltip(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      return Component.literal("subtle!");
   }

   @Override
   public Component getChunkHighlightBluntTooltip(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      return Component.literal("blunt!");
   }

   @Override
   public void addMinimapBlockHighlightTooltips(List<Component> list, ResourceKey<Level> dimension, int blockX, int blockZ, int width) {
      list.add(Component.literal("minimap tooltip!"));
   }
}
