package com.blamejared.crafttweaker.mixin.common.access.block;

import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockStateBase.class)
public interface AccessBlockStateBase {
   @Accessor("lightEmission")
   int crafttweaker$getLightEmission();

   @Mutable
   @Accessor("lightEmission")
   void crafttweaker$setLightEmission(int var1);

   @Accessor("useShapeForLightOcclusion")
   boolean crafttweaker$isUseShapeForLightOcclusion();

   @Mutable
   @Accessor("useShapeForLightOcclusion")
   void crafttweaker$setUseShapeForLightOcclusion(boolean var1);

   @Accessor("isAir")
   boolean crafttweaker$isIsAir();

   @Mutable
   @Accessor("isAir")
   void crafttweaker$setIsAir(boolean var1);

   @Accessor("destroySpeed")
   float crafttweaker$getDestroySpeed();

   @Mutable
   @Accessor("destroySpeed")
   void crafttweaker$setDestroySpeed(float var1);

   @Accessor("requiresCorrectToolForDrops")
   boolean crafttweaker$isRequiresCorrectToolForDrops();

   @Mutable
   @Accessor("requiresCorrectToolForDrops")
   void crafttweaker$setRequiresCorrectToolForDrops(boolean var1);

   @Accessor("canOcclude")
   boolean crafttweaker$isCanOcclude();

   @Mutable
   @Accessor("canOcclude")
   void crafttweaker$setCanOcclude(boolean var1);
}
