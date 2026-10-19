package fzzyhmstrs.emi_loot.parser.processor;

import fzzyhmstrs.emi_loot.util.LText;
import java.util.List;
import net.minecraft.network.chat.MutableComponent;

public class ListProcessors {
   public static MutableComponent buildAndList(List<MutableComponent> list) {
      return buildAndList(list, 0);
   }

   public static MutableComponent buildAndList(List<MutableComponent> list, int currentIndex) {
      if (list.size() == 0) {
         return LText.empty();
      } else if (currentIndex == list.size() - 1) {
         return list.get(currentIndex);
      } else {
         return currentIndex == list.size() - 2
            ? LText.translatable("emi_loot.predicate.and_2", list.get(currentIndex), buildAndList(list, currentIndex + 1))
            : LText.translatable("emi_loot.predicate.and_3", list.get(currentIndex), buildAndList(list, currentIndex + 1));
      }
   }

   public static MutableComponent buildOrList(List<MutableComponent> list) {
      return buildOrList(list, 0);
   }

   public static MutableComponent buildOrList(List<MutableComponent> list, int currentIndex) {
      if (list.size() == 0) {
         return LText.empty();
      } else if (currentIndex == list.size() - 1) {
         return list.get(currentIndex);
      } else {
         return currentIndex == list.size() - 2
            ? LText.translatable("emi_loot.predicate.or_2", list.get(currentIndex), buildOrList(list, currentIndex + 1))
            : LText.translatable("emi_loot.predicate.or_3", list.get(currentIndex), buildOrList(list, currentIndex + 1));
      }
   }
}
