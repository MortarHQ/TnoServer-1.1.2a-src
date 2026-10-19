package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ModelEvent.ModifyBakingResult;

public class MMModels {
   public static final String[] HAND_MODEL_ITEMS = new String[]{"wrought_axe", "spear", "earthrend_gauntlet", "sculptor_staff"};
   public static final String PREFIX = "item/";

   @SubscribeEvent
   public static void onModelBakeEvent(ModifyBakingResult event) {
      Map<ModelResourceLocation, BakedModel> models = event.getModels();

      for (String item : HAND_MODEL_ITEMS) {
         ModelResourceLocation modelInventory = ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath("mowziesmobs", item));
         ModelResourceLocation modelHand = prefixed(item + "_in_hand");
         final BakedModel bakedModelDefault = models.get(modelInventory);
         final BakedModel bakedModelHand = models.get(modelHand);
         BakedModel modelWrapper = new BakedModel() {
            public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
               return bakedModelDefault.getQuads(state, side, rand);
            }

            public boolean useAmbientOcclusion() {
               return bakedModelDefault.useAmbientOcclusion();
            }

            public boolean isGui3d() {
               return bakedModelDefault.isGui3d();
            }

            public boolean usesBlockLight() {
               return false;
            }

            public boolean isCustomRenderer() {
               return bakedModelDefault.isCustomRenderer();
            }

            public TextureAtlasSprite getParticleIcon() {
               return bakedModelDefault.getParticleIcon();
            }

            public ItemOverrides getOverrides() {
               return bakedModelDefault.getOverrides();
            }

            public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack mat, boolean applyLeftHandTransform) {
               BakedModel modelToUse = bakedModelDefault;
               if (cameraTransformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                  || cameraTransformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                  || cameraTransformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                  || cameraTransformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
                  modelToUse = bakedModelHand;
               }

               return ClientHooks.handleCameraTransforms(mat, modelToUse, cameraTransformType, applyLeftHandTransform);
            }
         };
         models.put(modelInventory, modelWrapper);
      }

      for (MaskType type : MaskType.values()) {
         ModelResourceLocation maskModelInventory = ModelResourceLocation.inventory(
            ResourceLocation.fromNamespaceAndPath("mowziesmobs", "umvuthana_mask_" + type.name)
         );
         ModelResourceLocation maskModelFrame = prefixed("umvuthana_mask_" + type.name + "_frame");
         bakeMask(models, maskModelInventory, maskModelFrame);
      }

      ModelResourceLocation maskModelInventory = ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "sol_visage"));
      ModelResourceLocation maskModelFrame = prefixed("sol_visage_frame");
      bakeMask(models, maskModelInventory, maskModelFrame);
   }

   private static void bakeMask(Map<ModelResourceLocation, BakedModel> map, ModelResourceLocation maskModelInventory, ModelResourceLocation maskModelFrame) {
      final BakedModel maskBakedModelDefault = map.get(maskModelInventory);
      final BakedModel maskBakedModelFrame = map.get(maskModelFrame);
      BakedModel maskModelWrapper = new BakedModel() {
         public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
            return maskBakedModelDefault.getQuads(state, side, rand);
         }

         public boolean useAmbientOcclusion() {
            return maskBakedModelDefault.useAmbientOcclusion();
         }

         public boolean isGui3d() {
            return maskBakedModelDefault.isGui3d();
         }

         public boolean usesBlockLight() {
            return false;
         }

         public boolean isCustomRenderer() {
            return maskBakedModelDefault.isCustomRenderer();
         }

         public TextureAtlasSprite getParticleIcon() {
            return maskBakedModelDefault.getParticleIcon();
         }

         public ItemOverrides getOverrides() {
            return maskBakedModelDefault.getOverrides();
         }

         public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack mat, boolean applyLeftHandTransform) {
            BakedModel modelToUse = maskBakedModelDefault;
            if (cameraTransformType == ItemDisplayContext.FIXED) {
               modelToUse = maskBakedModelFrame;
            }

            return ClientHooks.handleCameraTransforms(mat, modelToUse, cameraTransformType, applyLeftHandTransform);
         }
      };
      map.put(maskModelInventory, maskModelWrapper);
   }

   public static ModelResourceLocation prefixed(String path) {
      return ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "item/" + path));
   }
}
