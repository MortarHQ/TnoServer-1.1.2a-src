package com.teamresourceful.resourcefullib.common.fluid.data;

import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.ApiStatus.Internal;

public interface FluidData {
   ResourceLocation id();

   FluidProperties properties();

   Supplier<? extends FlowingFluid> still();

   Supplier<? extends FlowingFluid> flowing();

   Supplier<? extends Item> bucket();

   Supplier<? extends LiquidBlock> block();

   @Internal
   <T> T data();

   @Internal
   void setStill(Supplier<? extends FlowingFluid> var1);

   @Internal
   void setFlowing(Supplier<? extends FlowingFluid> var1);

   @Internal
   void setBucket(Supplier<? extends Item> var1);

   @Internal
   void setBlock(Supplier<? extends LiquidBlock> var1);
}
