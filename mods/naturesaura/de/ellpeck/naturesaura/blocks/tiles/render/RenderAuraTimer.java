package de.ellpeck.naturesaura.blocks.tiles.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAuraTimer;
import de.ellpeck.naturesaura.items.ItemAuraBottle;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

public class RenderAuraTimer implements BlockEntityRenderer<BlockEntityAuraTimer> {
   private static final ResourceLocation RES = ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/models/aura_timer_aura.png");
   private final RenderAuraTimer.AuraModel model = new RenderAuraTimer.AuraModel();

   public RenderAuraTimer(Context context) {
   }

   public void render(BlockEntityAuraTimer tile, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
      ItemStack bottle = ((IItemHandler)tile.getLevel().getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, null))
         .getStackInSlot(0);
      if (!bottle.isEmpty()) {
         stack.pushPose();
         stack.translate(0.25F, 0.1250625F, 0.25F);
         float percentage = 1.0F - tile.getTimerPercentage();
         stack.scale(0.5F, 0.40625F * percentage, 0.5F);
         IAuraType type = ItemAuraBottle.getType(bottle);
         float r = (type.getColor() >> 16 & 0xFF) / 255.0F;
         float g = (type.getColor() >> 8 & 0xFF) / 255.0F;
         float b = (type.getColor() & 0xFF) / 255.0F;
         this.model
            .renderToBuffer(stack, buffer.getBuffer(this.model.renderType(RES)), combinedLightIn, combinedOverlayIn, ARGB32.colorFromFloat(0.75F, r, g, b));
         stack.popPose();
      }
   }

   private static class AuraModel extends Model {
      private final ModelPart model;

      public AuraModel() {
         super(RenderType::entityTranslucent);
         MeshDefinition mesh = new MeshDefinition();
         PartDefinition part = mesh.getRoot();
         part.addOrReplaceChild("main", new CubeListBuilder().addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F), PartPose.ZERO);
         this.model = LayerDefinition.create(mesh, 64, 64).bakeRoot();
      }

      public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
         this.model.render(poseStack, buffer, packedLight, packedOverlay, color);
      }
   }
}
