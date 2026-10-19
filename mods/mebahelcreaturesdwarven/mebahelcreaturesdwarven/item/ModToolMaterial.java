package mebahelcreaturesdwarven.item;

import java.util.function.Supplier;
import net.minecraft.class_1832;
import net.minecraft.class_1856;
import net.minecraft.class_1935;
import net.minecraft.class_2248;
import net.minecraft.class_3481;
import net.minecraft.class_6862;

public enum ModToolMaterial implements class_1832 {
   DWARVEN_1(4, 1380, 7.0F, 0.0F, 20, () -> class_1856.method_8091(new class_1935[]{ModItems.DWEMER_METAL_INGOT})),
   DWARVEN_2(4, 1380, 7.0F, 0.5F, 20, () -> class_1856.method_8091(new class_1935[]{ModItems.DWEMER_METAL_INGOT}));

   private final int miningLevel;
   private final int itemDurability;
   private final float miningSpeed;
   private final float attackDamage;
   private final int enchantability;
   private final Supplier<class_1856> repairIngredient;

   ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<class_1856> repairIngredient) {
      this.miningLevel = miningLevel;
      this.itemDurability = itemDurability;
      this.miningSpeed = miningSpeed;
      this.attackDamage = attackDamage;
      this.enchantability = enchantability;
      this.repairIngredient = repairIngredient;
   }

   public class_6862<class_2248> method_58419() {
      return switch (this) {
         case DWARVEN_1 -> class_3481.field_49927;
         case DWARVEN_2 -> class_3481.field_49927;
      };
   }

   public int method_8025() {
      return this.itemDurability;
   }

   public float method_8027() {
      return this.miningSpeed;
   }

   public float method_8028() {
      return this.attackDamage;
   }

   public int method_8026() {
      return this.enchantability;
   }

   public class_1856 method_8023() {
      return this.repairIngredient.get();
   }
}
