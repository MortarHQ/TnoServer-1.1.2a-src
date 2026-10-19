package xaero.map.graphics;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.SortedMap;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;

public class CustomVertexConsumers {
   private final SortedMap<RenderType, ByteBufferBuilder> builders = (SortedMap<RenderType, ByteBufferBuilder>)Util.make(
      new Object2ObjectLinkedOpenHashMap(), map -> {
         checkedAddToMap(map, CustomRenderTypes.MAP_COLOR_FILLER, new ByteBufferBuilder(256));
         checkedAddToMap(map, CustomRenderTypes.MAP_FRAME_TEXTURE_OVER_TRANSPARENT, new ByteBufferBuilder(256));
         checkedAddToMap(map, CustomRenderTypes.GUI_NEAREST, new ByteBufferBuilder(256));
         checkedAddToMap(map, CustomRenderTypes.GUI_BILINEAR, new ByteBufferBuilder(256));
         checkedAddToMap(map, CustomRenderTypes.GUI_BILINEAR_PREMULTIPLIED, new ByteBufferBuilder(256));
         checkedAddToMap(map, CustomRenderTypes.MAP_COLOR_OVERLAY, new ByteBufferBuilder(256));
         checkedAddToMap(map, CustomRenderTypes.MAP, new ByteBufferBuilder(256));
         checkedAddToMap(map, CustomRenderTypes.MAP_ELEMENT_TEXT_BG, new ByteBufferBuilder(42));
      }
   );
   private BufferSource renderTypeBuffers = MultiBufferSource.immediateWithBuffers(this.builders, new ByteBufferBuilder(256));

   public BufferSource getRenderTypeBuffers() {
      return this.renderTypeBuffers;
   }

   private static void checkedAddToMap(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map, RenderType layer, ByteBufferBuilder bb) {
      if (map.containsKey(layer)) {
         throw new RuntimeException("Duplicate render layers!");
      }

      map.put(layer, bb);
   }
}
