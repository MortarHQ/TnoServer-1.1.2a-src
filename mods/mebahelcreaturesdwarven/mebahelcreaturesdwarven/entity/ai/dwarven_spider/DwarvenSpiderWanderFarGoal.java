package mebahelcreaturesdwarven.entity.ai.dwarven_spider;

import mebahelcreaturesdwarven.entity.custom.DwemerEntity;
import mebahelcreaturesdwarven.entity.custom.DwemerSpiderEntity;
import net.minecraft.class_1379;
import net.minecraft.class_243;
import net.minecraft.class_5534;
import org.jetbrains.annotations.Nullable;

public class DwarvenSpiderWanderFarGoal extends class_1379 {
   protected final float probability;
   public DwemerEntity mob;

   public DwarvenSpiderWanderFarGoal(DwemerEntity mob, double speed, float probability) {
      super(mob, speed);
      this.mob = mob;
      this.probability = probability;
   }

   public boolean method_6266() {
      return !this.mob.method_5942().method_6357() && !this.mob.method_5782() && !(Boolean)this.mob.method_5841().method_12789(DwemerSpiderEntity.IS_MINING);
   }

   @Nullable
   protected class_243 method_6302() {
      if (this.mob.method_5816()) {
         class_243 vec3d = class_5534.method_31527(this.mob, 15, 7);
         return vec3d == null ? super.method_6302() : vec3d;
      } else {
         return this.mob.method_59922().method_43057() >= this.probability ? class_5534.method_31527(this.mob, 10, 7) : super.method_6302();
      }
   }

   public boolean method_6264() {
      if (this.mob.method_5782() || (Boolean)this.mob.method_5841().method_12789(DwemerSpiderEntity.IS_MINING)) {
         return false;
      }

      if (!this.field_6565 && this.mob.method_59922().method_43048(method_38848(this.field_6564)) != 0) {
         return false;
      }

      class_243 vec3d = this.method_6302();
      if (vec3d == null) {
         return false;
      }

      this.field_6563 = vec3d.field_1352;
      this.field_6562 = vec3d.field_1351;
      this.field_6561 = vec3d.field_1350;
      this.field_6565 = false;
      return true;
   }
}
