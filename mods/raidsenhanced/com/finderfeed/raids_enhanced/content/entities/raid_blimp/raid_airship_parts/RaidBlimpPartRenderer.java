package com.finderfeed.raids_enhanced.content.entities.raid_blimp.raid_airship_parts;

import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.util.rendering.FDEasings;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import com.finderfeed.raids_enhanced.RaidsEnhanced;
import com.finderfeed.raids_enhanced.init.REModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.HashMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class RaidBlimpPartRenderer extends EntityRenderer<RaidBlimpPart> {
   private static HashMap<Integer, RaidBlimpPartDefinition> PARTS;

   public RaidBlimpPartRenderer(Context p_174008_) {
      super(p_174008_);
   }

   public void render(RaidBlimpPart part, float yaw, float partialTicks, PoseStack matrices, MultiBufferSource src, int light) {
      if (PARTS == null) {
         this.initParts();
      }

      int partType = part.getPartType();
      if (PARTS.containsKey(partType)) {
         RaidBlimpPartDefinition definition = PARTS.get(partType);
         FDModel model = definition.fdModel;
         ResourceLocation texture = definition.texture;
         float rpt = part.landedTime >= 0 ? 0.0F : partialTicks;
         float rotation = part.rotation + rpt;
         Vec3 d = part.lastDeltaMovement;
         FDRenderUtil.applyMovementMatrixRotations(matrices, d);
         matrices.mulPose(Axis.XP.rotationDegrees(rotation * 100.0F));
         float p = FDEasings.reversedEaseOut(Mth.clamp((part.landedTime + Math.abs(rpt - partialTicks)) / 5.0F, 0.0F, 1.0F));
         if (part.landedTime >= 0) {
            matrices.mulPose(Axis.XP.rotationDegrees((float)Math.sin((part.landedTime + Math.abs(rpt - partialTicks)) * 5.0F) * 20.0F * p));
         }

         float alpha = 1.0F;
         int lifetime = (Integer)part.getEntityData().get(RaidBlimpPart.LIFETIME);
         if (part.tickCount >= lifetime - 100) {
            alpha = Mth.clamp(1.0F - (part.tickCount + partialTicks - (lifetime - 100)) / 100.0F, 0.0F, 1.0F);
         }

         model.render(matrices, src.getBuffer(RenderType.entityTranslucent(texture)), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
      }
   }

   private void initParts() {
      PARTS = new HashMap<>();
      PARTS.put(
         1, new RaidBlimpPartDefinition(new FDModel(REModels.RAID_AIRSHIP_PART_1.get()), RaidsEnhanced.location("textures/entities/raid_airship_part_1.png"))
      );
      PARTS.put(
         2, new RaidBlimpPartDefinition(new FDModel(REModels.RAID_AIRSHIP_PART_2.get()), RaidsEnhanced.location("textures/entities/raid_airship_part_2.png"))
      );
      PARTS.put(3, new RaidBlimpPartDefinition(new FDModel(REModels.RAID_AIRSHIP_PART_1.get()), RaidsEnhanced.location("textures/entities/golem_part.png")));
   }

   public ResourceLocation getTextureLocation(RaidBlimpPart part) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
