package xaero.map.element.render;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xaero.map.entity.util.EntityUtil;

public class ElementRenderInfo {
   public final ElementRenderLocation location;
   public final Entity renderEntity;
   public final Vec3 renderEntityPos;
   public final Player player;
   public final Vec3 renderPos;
   public final double mouseX;
   public final double mouseZ;
   public final float brightness;
   public final double scale;
   public final double screenSizeBasedScale;
   public final boolean cave;
   public final float partialTicks;
   public final RenderTarget framebuffer;
   public final double renderEntityDimensionScale;
   public final ResourceKey<Level> renderEntityDimension;
   public final double backgroundCoordinateScale;
   public final ResourceKey<Level> mapDimension;

   public ElementRenderInfo(
      ElementRenderLocation location,
      Entity renderEntity,
      Player player,
      Vec3 renderPos,
      double mouseX,
      double mouseZ,
      double scale,
      boolean cave,
      float partialTicks,
      float brightness,
      double screenSizeBasedScale,
      RenderTarget framebuffer,
      double backgroundCoordinateScale,
      ResourceKey<Level> mapDimension
   ) {
      this.location = location;
      this.renderEntity = renderEntity;
      this.mouseX = mouseX;
      this.mouseZ = mouseZ;
      this.scale = scale;
      this.brightness = brightness;
      this.screenSizeBasedScale = screenSizeBasedScale;
      this.renderEntityPos = EntityUtil.getEntityPos(renderEntity, partialTicks);
      this.player = player;
      this.renderPos = renderPos;
      this.cave = cave;
      this.partialTicks = partialTicks;
      this.framebuffer = framebuffer;
      this.renderEntityDimensionScale = Minecraft.getInstance().level.dimensionType().coordinateScale();
      this.renderEntityDimension = Minecraft.getInstance().level.dimension();
      this.backgroundCoordinateScale = backgroundCoordinateScale;
      this.mapDimension = mapDimension;
   }
}
