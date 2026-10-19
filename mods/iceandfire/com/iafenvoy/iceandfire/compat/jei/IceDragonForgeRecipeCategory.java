package com.iafenvoy.iceandfire.compat.jei;

import com.iafenvoy.iceandfire.registry.IafBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class IceDragonForgeRecipeCategory extends DragonForgeRecipeCategory {
   public IceDragonForgeRecipeCategory(IGuiHelper guiHelper) {
      super(
         guiHelper,
         IceAndFireJeiPlugin.ICE,
         "ice",
         guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)IafBlocks.DRAGONFORGE_ICE_CORE.get()))
      );
   }
}
