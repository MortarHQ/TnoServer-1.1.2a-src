package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.ModelCrate1;
import net.unusual.block_factorys_bosses.item.CrateEntityItem;

public class CrateItemRenderer extends BlockEntityWithoutLevelRenderer {
   private static final ResourceLocation TEXTURE = BossesRise.prefix("textures/entities/crate.png");
   private final EntityModel<?> model;

   public CrateItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
      super(dispatcher, modelSet);
      this.model = new ModelCrate1(modelSet.bakeLayer(ModelCrate1.LAYER_LOCATION));
   }

   public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
      if (stack.getItem() instanceof CrateEntityItem) {
         VertexConsumer vb = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
         pose.pushPose();
         pose.translate(0.5F, 1.5F, 0.5F);
         pose.mulPose(Axis.XP.rotationDegrees(180.0F));
         this.model.renderToBuffer(pose, vb, light, overlay);
         pose.popPose();
      }
   }
}
