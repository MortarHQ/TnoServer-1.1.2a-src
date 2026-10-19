package mebahelcreaturesdwarven.item.weapon;

import java.util.List;
import mebahelcreaturesdwarven.entity.ModEntities;
import mebahelcreaturesdwarven.entity.projectile.DwarvenArrowEntity;
import net.minecraft.class_124;
import net.minecraft.class_1309;
import net.minecraft.class_1665;
import net.minecraft.class_1676;
import net.minecraft.class_1744;
import net.minecraft.class_1799;
import net.minecraft.class_1836;
import net.minecraft.class_1937;
import net.minecraft.class_2350;
import net.minecraft.class_2374;
import net.minecraft.class_2561;
import net.minecraft.class_1665.class_1666;
import net.minecraft.class_1792.class_1793;
import net.minecraft.class_1792.class_9635;
import org.jetbrains.annotations.Nullable;

public class DwarvenArrowItem extends class_1744 {
   public DwarvenArrowItem(class_1793 settings) {
      super(settings);
   }

   public class_1665 method_7702(class_1937 world, class_1799 stack, class_1309 shooter, @Nullable class_1799 shotFrom) {
      return new DwarvenArrowEntity(ModEntities.DWARVEN_ARROW, world, shooter);
   }

   public class_1676 method_58648(class_1937 world, class_2374 pos, class_1799 stack, class_2350 direction) {
      DwarvenArrowEntity arrow = new DwarvenArrowEntity(ModEntities.DWARVEN_ARROW, world);
      arrow.method_5814(pos.method_10216(), pos.method_10214(), pos.method_10215());
      arrow.field_7572 = class_1666.field_7593;
      return arrow;
   }

   public void method_7851(class_1799 stack, class_9635 context, List<class_2561> tooltip, class_1836 type) {
      tooltip.add(class_2561.method_43471("item.mebahelcreaturesdwarven.dwarven_arrow.tooltip1").method_27692(class_124.field_1056));
   }
}
