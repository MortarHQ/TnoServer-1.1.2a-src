package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client.accessor;

import java.util.List;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PalettedPermutations.class)
public interface PalettedPermutationsAccessor {
   @Accessor
   List<ResourceLocation> getTextures();

   @Accessor("textures")
   @Mutable
   void setTextures(List<ResourceLocation> var1);

   @Accessor
   ResourceLocation getPaletteKey();
}
