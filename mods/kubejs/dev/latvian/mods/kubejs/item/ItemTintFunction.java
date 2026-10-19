package dev.latvian.mods.kubejs.item;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.color.KubeColor;
import dev.latvian.mods.kubejs.color.SimpleColor;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.ColorWrapper;
import dev.latvian.mods.rhino.BaseFunction;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Scriptable;
import dev.latvian.mods.rhino.Undefined;
import dev.latvian.mods.rhino.type.TypeInfo;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.MapItemColor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ItemTintFunction {
   TypeInfo TYPE_INFO = TypeInfo.of(ItemTintFunction.class);
   ItemTintFunction BLOCK = (stack, index) -> {
      if (stack.getItem() instanceof BlockItem block) {
         BlockState s = block.getBlock().defaultBlockState();
         BlockBuilder internal = s.getBlock().kjs$getBlockBuilder();
         if (internal != null && internal.tint != null) {
            return internal.tint.getColor(s, null, null, index);
         }
      }

      return null;
   };
   ItemTintFunction POTION = (stack, index) -> {
      PotionContents potion = (PotionContents)stack.get(DataComponents.POTION_CONTENTS);
      return potion != null ? new SimpleColor(potion.getColor()) : null;
   };
   ItemTintFunction MAP = (stack, index) -> {
      MapItemColor map = (MapItemColor)stack.get(DataComponents.MAP_COLOR);
      return map != null ? new SimpleColor(map.rgb()) : null;
   };
   ItemTintFunction DISPLAY_COLOR_NBT = (stack, index) -> {
      DyedItemColor color = (DyedItemColor)stack.get(DataComponents.DYED_COLOR);
      return color != null ? new SimpleColor(color.rgb()) : null;
   };

   KubeColor getColor(ItemStack stack, int index);

   @Nullable
   static ItemTintFunction wrap(Context cx, Object o) {
      return switch (o) {
         case null -> null;
         case Undefined undefined -> null;
         case Scriptable s when Undefined.isUndefined(s) -> null;
         case ItemTintFunction f -> f;
         case List<?> list -> {
            ItemTintFunction.Mapped map = new ItemTintFunction.Mapped();

            for (int i = 0; i < list.size(); i++) {
               ItemTintFunction f = wrap(cx, list.get(i));
               if (f != null) {
                  map.map.put(i, f);
               }
            }

            yield map;
         }
         case CharSequence cs -> {
            switch (cs.toString()) {
               case "block":
                  yield BLOCK;
               case "potion":
                  yield POTION;
               case "map":
                  yield MAP;
               case "display_color_nbt":
                  yield DISPLAY_COLOR_NBT;
               default:
                  yield new ItemTintFunction.Fixed(ColorWrapper.wrap(o));
            }
         }
         case BaseFunction function -> (ItemTintFunction)cx.createInterfaceAdapter(TYPE_INFO, function);
         default -> new ItemTintFunction.Fixed(ColorWrapper.wrap(o));
      };
   }

   record Fixed(KubeColor color) implements ItemTintFunction {
      @Override
      public KubeColor getColor(ItemStack stack, int index) {
         return this.color;
      }
   }

   class Mapped implements ItemTintFunction {
      public final Int2ObjectMap<ItemTintFunction> map = new Int2ObjectArrayMap(1);

      @Override
      public KubeColor getColor(ItemStack stack, int index) {
         ItemTintFunction f = (ItemTintFunction)this.map.get(index);
         return f == null ? null : f.getColor(stack, index);
      }
   }
}
