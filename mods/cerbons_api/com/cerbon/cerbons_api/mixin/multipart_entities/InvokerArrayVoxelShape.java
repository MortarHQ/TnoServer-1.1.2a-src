package com.cerbon.cerbons_api.mixin.multipart_entities;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.world.phys.shapes.ArrayVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ArrayVoxelShape.class)
public interface InvokerArrayVoxelShape {
   @Invoker("<init>")
   static ArrayVoxelShape init(DiscreteVoxelShape shape, DoubleList xPoints, DoubleList yPoints, DoubleList zPoints) {
      throw new AssertionError();
   }
}
