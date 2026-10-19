package mebahelcreaturesdwarven.entity.ai;

import net.minecraft.class_1309;
import net.minecraft.class_1314;
import net.minecraft.class_1399;

public class CustomRevengeGoal extends class_1399 {
   private final Class<?> classToIgnore;

   public CustomRevengeGoal(class_1314 mob, Class<?> classToIgnore) {
      super(mob, new Class[0]);
      this.classToIgnore = classToIgnore;
   }

   public boolean method_6264() {
      class_1309 revengeTarget = this.field_6660.method_6065();
      return revengeTarget != null && this.classToIgnore.isInstance(revengeTarget) ? false : super.method_6264();
   }

   public void method_6269() {
      class_1309 revengeTarget = this.field_6660.method_6065();
      if (revengeTarget != null && !this.classToIgnore.isInstance(revengeTarget)) {
         this.field_6660.method_5980(revengeTarget);
         super.method_6269();
      }
   }
}
