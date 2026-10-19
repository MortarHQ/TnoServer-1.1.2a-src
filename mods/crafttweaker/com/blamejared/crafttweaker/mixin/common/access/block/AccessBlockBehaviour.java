package com.blamejared.crafttweaker.mixin.common.access.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.class)
public interface AccessBlockBehaviour {
   @Accessor("friction")
   float crafttweaker$getFriction();

   @Mutable
   @Accessor("friction")
   void crafttweaker$setFriction(float var1);

   @Accessor("speedFactor")
   float crafttweaker$getSpeedFactor();

   @Mutable
   @Accessor("speedFactor")
   void crafttweaker$setSpeedFactor(float var1);

   @Accessor("jumpFactor")
   float crafttweaker$getJumpFactor();

   @Mutable
   @Accessor("jumpFactor")
   void crafttweaker$setJumpFactor(float var1);

   @Accessor("hasCollision")
   boolean crafttweaker$getHasCollision();

   @Mutable
   @Accessor("hasCollision")
   void crafttweaker$setHasCollision(boolean var1);

   @Accessor("explosionResistance")
   float crafttweaker$getExplosionResistance();

   @Mutable
   @Accessor("explosionResistance")
   void crafttweaker$setExplosionResistance(float var1);
}
