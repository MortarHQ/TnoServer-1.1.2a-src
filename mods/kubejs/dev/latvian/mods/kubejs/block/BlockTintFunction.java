package dev.latvian.mods.kubejs.block;

import dev.latvian.mods.kubejs.color.KubeColor;
import dev.latvian.mods.kubejs.color.SimpleColor;
import dev.latvian.mods.kubejs.color.SimpleColorWithAlpha;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.ColorWrapper;
import dev.latvian.mods.rhino.BaseFunction;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Scriptable;
import dev.latvian.mods.rhino.Undefined;
import dev.latvian.mods.rhino.type.TypeInfo;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface BlockTintFunction {
   TypeInfo TYPE_INFO = TypeInfo.of(BlockTintFunction.class);
   BlockTintFunction GRASS = (s, l, p, i) -> new SimpleColor(l != null && p != null ? BiomeColors.getAverageGrassColor(l, p) : GrassColor.get(0.5, 1.0));
   KubeColor DEFAULT_FOLIAGE_COLOR = new SimpleColor(FoliageColor.getDefaultColor());
   BlockTintFunction FOLIAGE = (s, l, p, i) -> l != null && p != null ? new SimpleColor(BiomeColors.getAverageFoliageColor(l, p)) : DEFAULT_FOLIAGE_COLOR;
   BlockTintFunction.Fixed EVERGREEN_FOLIAGE = new BlockTintFunction.Fixed(new SimpleColor(FoliageColor.getEvergreenColor()));
   BlockTintFunction.Fixed BIRCH_FOLIAGE = new BlockTintFunction.Fixed(new SimpleColor(FoliageColor.getBirchColor()));
   BlockTintFunction.Fixed MANGROVE_FOLIAGE = new BlockTintFunction.Fixed(new SimpleColor(FoliageColor.getMangroveColor()));
   BlockTintFunction WATER = (s, l, p, i) -> l != null && p != null ? new SimpleColorWithAlpha(BiomeColors.getAverageWaterColor(l, p)) : null;
   KubeColor[] REDSTONE_COLORS = new KubeColor[16];
   BlockTintFunction REDSTONE = (state, level, pos, index) -> {
      if (REDSTONE_COLORS[0] == null) {
         for (int i = 0; i < REDSTONE_COLORS.length; i++) {
            REDSTONE_COLORS[i] = new SimpleColor(RedStoneWireBlock.getColorForPower(i));
         }
      }

      return REDSTONE_COLORS[state.getValue(BlockStateProperties.POWER)];
   };

   KubeColor getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int index);

   @Nullable
   static BlockTintFunction wrap(Context cx, Object o) {
      return switch (o) {
         case null -> null;
         case Undefined undefined -> null;
         case Scriptable s when Undefined.isUndefined(s) -> null;
         case BlockTintFunction f -> f;
         case List<?> list -> {
            BlockTintFunction.Mapped map = new BlockTintFunction.Mapped();

            for (int i = 0; i < list.size(); i++) {
               BlockTintFunction f = wrap(cx, list.get(i));
               if (f != null) {
                  map.map.put(i, f);
               }
            }

            yield map;
         }
         case CharSequence cs -> {
            switch (cs.toString()) {
               case "grass":
                  yield GRASS;
               case "foliage":
                  yield FOLIAGE;
               case "evergreen_foliage":
                  yield EVERGREEN_FOLIAGE;
               case "birch_foliage":
                  yield BIRCH_FOLIAGE;
               case "mangrove_foliage":
                  yield MANGROVE_FOLIAGE;
               case "water":
                  yield WATER;
               case "redstone":
                  yield REDSTONE;
               default:
                  yield new BlockTintFunction.Fixed(ColorWrapper.wrap(o));
            }
         }
         case BaseFunction function -> (BlockTintFunction)cx.createInterfaceAdapter(TYPE_INFO, function);
         default -> new BlockTintFunction.Fixed(ColorWrapper.wrap(o));
      };
   }

   record Fixed(KubeColor color) implements BlockTintFunction {
      @Override
      public KubeColor getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int index) {
         return this.color;
      }
   }

   class Mapped implements BlockTintFunction {
      public final Int2ObjectMap<BlockTintFunction> map = new Int2ObjectArrayMap(1);

      @Override
      public KubeColor getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int index) {
         BlockTintFunction f = (BlockTintFunction)this.map.get(index);
         return f == null ? null : f.getColor(state, level, pos, index);
      }
   }
}
