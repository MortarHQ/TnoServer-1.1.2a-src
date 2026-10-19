package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.entity.INameTagFunction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.platform.Services;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;

public interface IClientHelper {
   Map<IIngredient, LinkedList<ITooltipFunction>> TOOLTIPS = new HashMap<>();
   Map<Predicate<Entity>, INameTagFunction> NAMETAGS = new HashMap<>();

   default boolean isSingleplayer() {
      return Minecraft.getInstance().hasSingleplayerServer();
   }

   boolean isKeyDown(KeyMapping var1);

   boolean isKeyDownExtra(KeyMapping var1);

   default Map<IIngredient, LinkedList<ITooltipFunction>> getTooltips() {
      return TOOLTIPS;
   }

   default void applyTooltips(ItemStack stack, TooltipContext context, TooltipFlag flag, List<Component> lines) {
      IItemStack ctStack = IItemStack.of(stack);
      Services.CLIENT
         .getTooltips()
         .forEach(
            (ingredient, functions) -> {
               if (ingredient.matches(ctStack)) {
                  functions.forEach(
                     function -> {
                        try {
                           function.apply(ctStack, lines, context, flag);
                        } catch (Exception exception) {
                           CommonLoggers.api()
                              .error(
                                 "Unable to run one of the tooltip functions for {} on {} due to an error (for experts, refer to {})",
                                 ingredient.getCommandString(),
                                 ctStack.getCommandString(),
                                 function.getClass().getName(),
                                 exception
                              );
                        }
                     }
                  );
               }
            }
         );
   }
}
