package com.blamejared.crafttweaker.api.item.tooltip;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@FunctionalInterface
@Document("vanilla/api/item/tooltip/ITooltipFunction")
@ZenCodeType.Name("crafttweaker.api.item.tooltip.ITooltipFunction")
public interface ITooltipFunction {
   @ZenCodeType.Method
   void apply(IItemStack var1, List<Component> var2, TooltipContext var3, TooltipFlag var4);
}
