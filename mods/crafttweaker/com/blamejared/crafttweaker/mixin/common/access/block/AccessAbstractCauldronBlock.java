package com.blamejared.crafttweaker.mixin.common.access.block;

import net.minecraft.core.cauldron.CauldronInteraction.InteractionMap;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractCauldronBlock.class)
public interface AccessAbstractCauldronBlock {
   @Accessor("interactions")
   InteractionMap crafttweaker$getInteractions();
}
