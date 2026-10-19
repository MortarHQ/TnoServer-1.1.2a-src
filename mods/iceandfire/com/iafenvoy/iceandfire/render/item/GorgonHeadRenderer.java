package com.iafenvoy.iceandfire.render.item;

import com.iafenvoy.iceandfire.registry.IafDataComponents;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.render.model.GorgonHeadActiveModel;
import com.iafenvoy.iceandfire.render.model.GorgonHeadModel;
import com.iafenvoy.uranus.client.model.AdvancedEntityModel;
import com.iafenvoy.uranus.client.render.DynamicItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GorgonHeadRenderer implements DynamicItemRenderer {
   private static final RenderType ACTIVE_TEXTURE = RenderType.entityCutoutNoCull(
      ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/gorgon/head_active.png"), false
   );
   private static final RenderType INACTIVE_TEXTURE = RenderType.entityCutoutNoCull(
      ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/gorgon/head_inactive.png"), false
   );
   private static final AdvancedEntityModel<Entity> ACTIVE_MODEL = new GorgonHeadActiveModel();
   private static final AdvancedEntityModel<Entity> INACTIVE_MODEL = new GorgonHeadModel();

   public void render(ItemStack stack, ItemDisplayContext type, PoseStack stackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      boolean active = stack.getItem() == IafItems.GORGON_HEAD.get() && stack.has((DataComponentType)IafDataComponents.ACTIVE.get());
      AdvancedEntityModel<Entity> model = active ? ACTIVE_MODEL : INACTIVE_MODEL;
      stackIn.pushPose();
      stackIn.translate(0.5F, active ? 1.5F : 1.25F, 0.5F);
      VertexConsumer ivertexbuilder = bufferIn.getBuffer(active ? ACTIVE_TEXTURE : INACTIVE_TEXTURE);
      model.renderToBuffer(stackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, -1);
      stackIn.popPose();
   }
}
