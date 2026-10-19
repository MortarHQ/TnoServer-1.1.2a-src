package com.blamejared.crafttweaker.mixin.common.access.brewing;

import java.util.List;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionBrewing.Mix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PotionBrewing.class)
public interface AccessPotionBrewing {
   @Accessor("potionMixes")
   List<Mix<Potion>> crafttweaker$getPotionMixes();

   @Mutable
   @Accessor("potionMixes")
   void crafttweaker$setPotionMixes(List<Mix<Potion>> var1);
}
