package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.SetFireworkExplosionLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.FireworkExplosion.Shape;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetFireworkExplosionFunction;

public class SetFireworkExplosionFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      List<MutableComponent> texts = new ArrayList<>();
      Optional<Shape> shape = ((SetFireworkExplosionLootFunctionAccessor)function).getShape();
      if (shape.isPresent()) {
         Component s = shape.get().getName();
         texts.add(LText.translatable("emi_loot.function.firework_explosion.shape", s));
      }

      Optional<IntList> colors = ((SetFireworkExplosionLootFunctionAccessor)function).getColors();
      if (colors.isPresent()) {
         List<MutableComponent> cs = colors.get().intStream().mapToObj(i -> LText.literal(Integer.toString(i))).toList();
         texts.add(LText.translatable("emi_loot.function.firework_explosion.colors", ListProcessors.buildAndList(cs)));
      }

      Optional<IntList> fadeColors = ((SetFireworkExplosionLootFunctionAccessor)function).getFadeColors();
      if (fadeColors.isPresent()) {
         List<MutableComponent> cs = fadeColors.get().intStream().mapToObj(i -> LText.literal(Integer.toString(i))).toList();
         texts.add(LText.translatable("emi_loot.function.firework_explosion.fade_colors", ListProcessors.buildAndList(cs)));
      }

      Optional<Boolean> trail = ((SetFireworkExplosionLootFunctionAccessor)function).getTrail();
      if (trail.isPresent()) {
         if (trail.get()) {
            texts.add(LText.translatable("emi_loot.function.firework_explosion.trail"));
         } else {
            texts.add(LText.translatable("emi_loot.function.firework_explosion.no_trail"));
         }
      }

      Optional<Boolean> twinkle = ((SetFireworkExplosionLootFunctionAccessor)function).getTwinkle();
      if (twinkle.isPresent()) {
         if (twinkle.get()) {
            texts.add(LText.translatable("emi_loot.function.firework_explosion.twinkle"));
         } else {
            texts.add(LText.translatable("emi_loot.function.firework_explosion.no_twinkle"));
         }
      }

      if (!stack.isEmpty()) {
         stack.update(
            DataComponents.FIREWORK_EXPLOSION, SetFireworkExplosionFunction.DEFAULT_VALUE, ((SetFireworkExplosionLootFunctionAccessor)function)::callApply
         );
      }

      return new LootTableParser.LootFunctionResult(
         TextKey.of("emi_loot.function.firework_explosion", ListProcessors.buildAndList(texts)), ItemStack.EMPTY, conditionTexts
      );
   }
}
