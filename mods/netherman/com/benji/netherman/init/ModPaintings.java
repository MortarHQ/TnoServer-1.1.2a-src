package com.benji.netherman.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class ModPaintings {
   public static final ResourceKey<PaintingVariant> ANGEL_PAINTING = createKey("angel_painting");
   public static final ResourceKey<PaintingVariant> FACE_PAINTING = createKey("face_painting");
   public static final ResourceKey<PaintingVariant> VILLAGE_PAINTING = createKey("village_painting");
   public static final ResourceKey<PaintingVariant> KING_PAINTING = createKey("king_painting");
   public static final ResourceKey<PaintingVariant> THRONE_PAINTING = createKey("throne_painting");

   private static ResourceKey<PaintingVariant> createKey(String name) {
      return ResourceKey.create(Registries.PAINTING_VARIANT, ResourceLocation.fromNamespaceAndPath("netherman", name));
   }
}
