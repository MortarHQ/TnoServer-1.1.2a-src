package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityRockSling;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib.model.GeoModel;

public class ModelRockSling extends GeoModel<EntityRockSling> {
   static Map<String, ResourceLocation> texMap;
   private static final ResourceLocation TEXTURE_DIRT = ResourceLocation.withDefaultNamespace("textures/block/dirt.png");
   private static final ResourceLocation TEXTURE_STONE = ResourceLocation.withDefaultNamespace("textures/block/stone.png");
   private static final ResourceLocation TEXTURE_SANDSTONE = ResourceLocation.withDefaultNamespace("textures/block/sandstone.png");
   private static final ResourceLocation TEXTURE_CLAY = ResourceLocation.withDefaultNamespace("textures/block/clay.png");

   public ModelRockSling() {
      texMap = new TreeMap<>();
      texMap.put(Blocks.STONE.getDescriptionId(), TEXTURE_STONE);
      texMap.put(Blocks.DIRT.getDescriptionId(), TEXTURE_DIRT);
      texMap.put(Blocks.CLAY.getDescriptionId(), TEXTURE_CLAY);
      texMap.put(Blocks.SANDSTONE.getDescriptionId(), TEXTURE_SANDSTONE);
   }

   public ResourceLocation getAnimationResource(EntityRockSling entity) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/rock_sling.animation.json");
   }

   public ResourceLocation getModelResource(EntityRockSling entity) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/rock_sling.geo.json");
   }

   public ResourceLocation getTextureResource(EntityRockSling entity) {
      if (entity.storedBlock != null) {
         ResourceLocation tex = texMap.get(entity.storedBlock.getBlock().getDescriptionId());
         if (tex != null) {
            return tex;
         }
      }

      return TEXTURE_DIRT;
   }

   public RenderType getRenderType(EntityRockSling animatable, ResourceLocation texture) {
      return RenderType.entityCutout(texture);
   }
}
