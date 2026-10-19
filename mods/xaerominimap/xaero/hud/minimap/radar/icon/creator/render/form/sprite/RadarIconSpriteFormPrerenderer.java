package xaero.hud.minimap.radar.icon.creator.render.form.sprite;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.lwjgl.opengl.GL11;
import xaero.hud.minimap.radar.icon.creator.RadarIconCreator;
import xaero.hud.minimap.radar.icon.creator.render.form.IRadarIconFormPrerenderer;
import xaero.hud.minimap.radar.icon.creator.render.trace.ModelRenderTrace;
import xaero.hud.minimap.radar.icon.definition.form.sprite.RadarIconSpriteForm;
import xaero.lib.client.graphics.util.ImmediateRenderUtil;

public class RadarIconSpriteFormPrerenderer implements IRadarIconFormPrerenderer {
   private final boolean flipped;
   private final boolean outlined;

   public RadarIconSpriteFormPrerenderer(boolean flipped, boolean outlined) {
      this.flipped = flipped;
      this.outlined = outlined;
   }

   @Override
   public boolean requiresEntityModel() {
      return false;
   }

   @Override
   public boolean isFlipped() {
      return this.flipped;
   }

   @Override
   public boolean isOutlined() {
      return this.outlined;
   }

   @Override
   public <T extends Entity> boolean prerender(
      GuiGraphics guiGraphics,
      EntityRenderer<? super T> entityRenderer,
      @Nullable EntityModel<T> entityModel,
      T entity,
      @Nullable List<ModelRenderTrace> traceResult,
      RadarIconCreator.Parameters parameters
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      RadarIconSpriteForm spriteForm = (RadarIconSpriteForm)parameters.form;
      ResourceLocation sprite = spriteForm.getSpriteLocation();
      GlStateManager._disableBlend();
      GlStateManager._enableBlend();
      GlStateManager._disableCull();
      Lighting.setupForFlatItems();
      Minecraft.getInstance().getTextureManager().bindForSetup(sprite);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10242, 33071);
      GL11.glTexParameteri(3553, 10243, 33071);
      int halfIcon = 32;
      matrixStack.translate(halfIcon, halfIcon, 1.0F);
      float scale = parameters.scale;
      if (scale < 1.0F) {
         matrixStack.scale(scale, scale, 1.0F);
      }

      RenderSystem.setShaderTexture(0, sprite);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      ImmediateRenderUtil.texturedRect(matrixStack, -halfIcon, -halfIcon, 0, 64, 64.0F, 64.0F, -64.0F, 64.0F);
      GlStateManager._enableCull();
      return true;
   }
}
