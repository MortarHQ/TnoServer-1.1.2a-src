package com.blackgear.vanillabackport.client.level.entities.renderer;

import com.blackgear.vanillabackport.client.level.entities.layer.GhastHarnessHandler;
import com.blackgear.vanillabackport.client.level.entities.layer.RopesLayer;
import com.blackgear.vanillabackport.client.level.entities.layer.SimpleEquipmentLayer;
import com.blackgear.vanillabackport.client.level.entities.model.HappyGhastHarnessModel;
import com.blackgear.vanillabackport.client.level.entities.model.HappyGhastModel;
import com.blackgear.vanillabackport.client.registries.ModModelLayers;
import com.blackgear.vanillabackport.common.level.entities.happyghast.HappyGhast;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HappyGhastRenderer extends MobRenderer<HappyGhast, HappyGhastModel<HappyGhast>> {
   private static final ResourceLocation GHAST_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/ghast/happy_ghast.png");
   private static final ResourceLocation GHAST_BABY_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/ghast/happy_ghast_baby.png");
   private static final ResourceLocation GHAST_ROPES = ResourceLocation.withDefaultNamespace("textures/entity/ghast/happy_ghast_ropes.png");

   public HappyGhastRenderer(Context context) {
      super(context, new HappyGhastModel(context.bakeLayer(ModModelLayers.HAPPY_GHAST)), 1.5F);
      this.addLayer(
         new SimpleEquipmentLayer(
            this,
            GhastHarnessHandler.HARNESS_EQUIPMENT,
            EquipmentSlot.CHEST,
            HappyGhast::isHarnessed,
            new HappyGhastHarnessModel(context.bakeLayer(ModModelLayers.HAPPY_GHAST_HARNESS)),
            null
         )
      );
      this.addLayer(new RopesLayer(this, context.getModelSet(), GHAST_ROPES));
   }

   public ResourceLocation getTextureLocation(HappyGhast entity) {
      return entity.isBaby() ? GHAST_BABY_LOCATION : GHAST_LOCATION;
   }

   protected void scale(HappyGhast entity, PoseStack matrices, float partialTicks) {
      float scale = entity.isBaby() ? 0.95F : 4.0F;
      matrices.scale(scale, scale, scale);
   }
}
