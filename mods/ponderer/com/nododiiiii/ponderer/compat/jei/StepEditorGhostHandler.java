package com.nododiiiii.ponderer.compat.jei;

import com.nododiiiii.ponderer.ui.AbstractStepEditorScreen;
import com.nododiiiii.ponderer.ui.IdFieldMode;
import java.util.List;
import java.util.Optional;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler.Target;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

public class StepEditorGhostHandler implements IGhostIngredientHandler<AbstractStepEditorScreen> {
   public <I> List<Target<I>> getTargetsTyped(AbstractStepEditorScreen gui, ITypedIngredient<I> ingredient, boolean doStart) {
      final IdFieldMode mode = PondererJeiPlugin.getActiveMode();
      if (mode == null) {
         return List.of();
      }

      final HintableTextFieldWidget targetField = gui.getJeiTargetField();
      if (targetField == null) {
         return List.of();
      }

      Optional<ItemStack> stackOpt = ingredient.getItemStack();
      if (stackOpt.isEmpty()) {
         return List.of();
      }

      if (!isCompatible(stackOpt.get(), mode)) {
         return List.of();
      }

      final Rect2i area = new Rect2i(targetField.getX(), targetField.getY(), targetField.getWidth(), targetField.getHeight());
      return List.of(new Target<I>() {
         public Rect2i getArea() {
            return area;
         }

         public void accept(I ing) {
            if (ing instanceof ItemStack stack) {
               String id = StepEditorGhostHandler.resolveId(stack, mode);
               if (id != null) {
                  targetField.setValue(id);
               }
            }
         }
      });
   }

   public void onComplete() {
   }

   static String resolveId(ItemStack stack, IdFieldMode mode) {
      return switch (mode) {
         case ITEM, INGREDIENT -> {
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(stack.getItem());
            yield key.toString();
         }
         case BLOCK -> stack.getItem() instanceof BlockItem bi ? BuiltInRegistries.BLOCK.getKey(bi.getBlock()).toString() : null;
         case ENTITY -> {
            if (stack.getItem() instanceof SpawnEggItem egg) {
               EntityType<?> type = egg.getType(stack);
               yield BuiltInRegistries.ENTITY_TYPE.getKey(type).toString();
            } else if (stack.getItem() instanceof BoatItem) {
               ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(stack.getItem());
               String entityName = itemKey.getPath().contains("chest") ? "chest_boat" : "boat";
               yield ResourceLocation.fromNamespaceAndPath(itemKey.getNamespace(), entityName).toString();
            } else {
               ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(stack.getItem());
               yield BuiltInRegistries.ENTITY_TYPE.containsKey(itemKey) ? itemKey.toString() : null;
            }
         }
      };
   }

   static boolean isCompatible(ItemStack stack, IdFieldMode mode) {
      return resolveId(stack, mode) != null;
   }
}
