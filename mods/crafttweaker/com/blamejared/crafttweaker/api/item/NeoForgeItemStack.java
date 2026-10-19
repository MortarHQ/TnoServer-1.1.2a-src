package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.item.ActionSetBurnTime;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.NeoForgeItemStack")
@Document("neoforge/api/item/NeoForgeItemStack")
public interface NeoForgeItemStack extends IItemStack {
   @Deprecated(forRemoval = true)
   @ZenCodeType.Method
   default void setBurnTime(int time, IRecipeManager<?> manager) {
      CraftTweakerAPI.apply(new ActionSetBurnTime(this, time, manager.getRecipeType()));
   }

   @Deprecated(forRemoval = true)
   @ZenCodeType.Method
   default int getBurnTime(IRecipeManager<?> manager) {
      return this.getInternal().getBurnTime(manager.getRecipeType());
   }
}
