package com.blackgear.platform.common.block;

import com.blackgear.platform.common.block.neoforge.WoodTypeRegistryImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class WoodTypeRegistry {
   @ExpectPlatform
   @Transformed
   public static WoodType create(ResourceLocation location, BlockSetType blockSetType) {
      return WoodTypeRegistryImpl.create(location, blockSetType);
   }

   @OnlyIn(Dist.CLIENT)
   @ExpectPlatform
   @Transformed
   public static void registerWoodType(WoodType type) {
      WoodTypeRegistryImpl.registerWoodType(type);
   }
}
