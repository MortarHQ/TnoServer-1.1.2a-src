package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelBoulder;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.BlockLayer;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderSculptor;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import org.joml.Quaternionf;

public class RenderBoulder extends EntityRenderer<EntityBoulderBase> {
   private static final ResourceLocation TEXTURE_DIRT = ResourceLocation.withDefaultNamespace("textures/block/dirt.png");
   private static final ResourceLocation TEXTURE_STONE = ResourceLocation.withDefaultNamespace("textures/block/stone.png");
   private static final ResourceLocation TEXTURE_SANDSTONE = ResourceLocation.withDefaultNamespace("textures/block/sandstone.png");
   private static final ResourceLocation TEXTURE_CLAY = ResourceLocation.withDefaultNamespace("textures/block/clay.png");
   Map<String, ResourceLocation> texMap;
   ModelBoulder model = new ModelBoulder();

   public RenderBoulder(Context mgr) {
      super(mgr);
      this.texMap = new TreeMap<>();
      this.texMap.put(Blocks.STONE.getDescriptionId(), TEXTURE_STONE);
      this.texMap.put(Blocks.DIRT.getDescriptionId(), TEXTURE_DIRT);
      this.texMap.put(Blocks.CLAY.getDescriptionId(), TEXTURE_CLAY);
      this.texMap.put(Blocks.SANDSTONE.getDescriptionId(), TEXTURE_SANDSTONE);
   }

   public ResourceLocation getTextureLocation(EntityBoulderBase entity) {
      if (entity.storedBlock != null) {
         ResourceLocation tex = this.texMap.get(entity.storedBlock.getBlock().getDescriptionId());
         if (tex != null) {
            return tex;
         }
      }

      return TEXTURE_DIRT;
   }

   public void render(EntityBoulderBase entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      if (entityIn.isActive()) {
         float frame = entityIn.risingTick + partialTicks;
         matrixStackIn.pushPose();
         this.model.setupAnim(entityIn, 0.0F, 0.0F, frame, 0.0F, 0.0F);
         if (entityIn instanceof EntityBoulderSculptor.EntityBoulderSculptorCrumbling crumbling && crumbling.isCrumbling()) {
            matrixStackIn.mulPose(
               new Quaternionf()
                  .rotationXYZ(
                     0.03F * (float)Math.cos(frame * 4.0F + 234.0F),
                     0.03F * (float)Math.cos(frame * 2.2F + 45.0F),
                     0.03F * (float)Math.cos(frame * 3.5F + 409.0F)
                  )
            );
         }

         BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
         AdvancedModelRenderer root;
         if (entityIn.boulderSize == EntityGeomancyBase.GeomancyTier.SMALL) {
            root = this.model.boulder0block1;
         } else if (entityIn.boulderSize == EntityGeomancyBase.GeomancyTier.MEDIUM) {
            root = this.model.boulder1;
         } else if (entityIn.boulderSize == EntityGeomancyBase.GeomancyTier.LARGE) {
            root = this.model.boulder2;
         } else {
            root = this.model.boulder3;
         }

         matrixStackIn.translate(-0.5F, 0.5F, -0.5F);
         BlockLayer.processModelRenderer(
            root, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, blockrendererdispatcher
         );
         matrixStackIn.popPose();
      }
   }
}
