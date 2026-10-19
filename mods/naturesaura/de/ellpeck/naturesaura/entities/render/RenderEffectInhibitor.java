package de.ellpeck.naturesaura.entities.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.naturesaura.entities.EntityEffectInhibitor;
import de.ellpeck.naturesaura.items.ItemEffectPowder;
import de.ellpeck.naturesaura.items.ModItems;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderEffectInhibitor extends EntityRenderer<EntityEffectInhibitor> {
   private final Map<ResourceLocation, ItemStack> items = new HashMap<>();

   public RenderEffectInhibitor(Context ctx) {
      super(ctx);
   }

   public ResourceLocation getTextureLocation(EntityEffectInhibitor entity) {
      return InventoryMenu.BLOCK_ATLAS;
   }

   public void render(EntityEffectInhibitor entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.pushPose();
      float time = entity.renderTicks + entity.getId() + partialTicks;
      float bob = (float)Math.sin(time / 10.0F) * 0.05F;
      matrixStackIn.translate(0.0F, 0.15F + bob, 0.0F);
      matrixStackIn.mulPose(Axis.YP.rotationDegrees(time * 3.0F % 360.0F));
      ResourceLocation effect = entity.getInhibitedEffect();
      ItemStack stack = this.items.computeIfAbsent(effect, res -> ItemEffectPowder.setEffect(new ItemStack(ModItems.EFFECT_POWDER), effect));
      Minecraft.getInstance()
         .getItemRenderer()
         .renderStatic(stack, ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entity.level(), 0);
      matrixStackIn.popPose();
   }
}
