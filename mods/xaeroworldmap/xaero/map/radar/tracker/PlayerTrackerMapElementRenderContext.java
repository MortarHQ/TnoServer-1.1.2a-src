package xaero.map.radar.tracker;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;

public class PlayerTrackerMapElementRenderContext {
   public VertexConsumer textBGConsumer;
   public MultiTextureRenderTypeRenderer uniqueTextureUIObjectRenderer;
   public ResourceKey<Level> mapDimId;
   public double mapDimDiv;
}
