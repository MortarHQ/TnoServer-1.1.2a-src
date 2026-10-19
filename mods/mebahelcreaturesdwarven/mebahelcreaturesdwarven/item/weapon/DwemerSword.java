package mebahelcreaturesdwarven.item.weapon;

import mebahelcreaturesdwarven.item.ModItems;
import net.minecraft.class_1322;
import net.minecraft.class_1799;
import net.minecraft.class_1829;
import net.minecraft.class_1832;
import net.minecraft.class_1839;
import net.minecraft.class_2960;
import net.minecraft.class_5134;
import net.minecraft.class_9274;
import net.minecraft.class_9285;
import net.minecraft.class_1322.class_1323;
import net.minecraft.class_1792.class_1793;

public class DwemerSword extends class_1829 {
   private static final class_9285 ANCIENT_NORD_ATTRIBUTE = class_9285.method_57480()
      .method_57487(
         class_5134.field_23721, new class_1322(class_2960.method_60655("minecraft", "base_attack_damage"), 6.0, class_1323.field_6328), class_9274.field_49217
      )
      .method_57487(
         class_5134.field_23723, new class_1322(class_2960.method_60655("minecraft", "base_attack_speed"), -2.4, class_1323.field_6328), class_9274.field_49217
      )
      .method_57486();

   public DwemerSword(class_1832 toolMaterial, class_1793 settings) {
      super(toolMaterial, settings.method_57348(ANCIENT_NORD_ATTRIBUTE));
   }

   public class_1839 method_7853(class_1799 stack) {
      return class_1839.field_8953;
   }

   public boolean method_7878(class_1799 stack, class_1799 ingredient) {
      return ingredient.method_31574(ModItems.DWEMER_METAL_INGOT);
   }
}
