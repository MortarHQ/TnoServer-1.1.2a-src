package mebahelcreaturesdwarven.sound;

import mebahelcreaturesdwarven.entity.custom.DwemerSphereEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1101;
import net.minecraft.class_243;
import net.minecraft.class_3414;
import net.minecraft.class_3419;

@Environment(EnvType.CLIENT)
public class DwemerSphereWalkLoopSoundInstance extends class_1101 {
   private final DwemerSphereEntity entity;

   public DwemerSphereWalkLoopSoundInstance(DwemerSphereEntity entity, class_3414 sound) {
      super(sound, class_3419.field_15251, entity.method_59922());
      this.entity = entity;
      this.field_5446 = true;
      this.field_5451 = 0;
      this.field_5442 = 0.14F;
      this.field_5441 = 1.0F;
      this.field_5439 = (float)entity.method_23317();
      this.field_5450 = (float)entity.method_23318();
      this.field_5449 = (float)entity.method_23321();
   }

   public void method_16896() {
      if (!this.entity.method_31481() && this.entity.method_5805()) {
         this.field_5439 = (float)this.entity.method_23317();
         this.field_5450 = (float)this.entity.method_23318();
         this.field_5449 = (float)this.entity.method_23321();
         if (isMovingHorizontally(this.entity) && !this.entity.isTransforming()) {
            double speed = this.entity.method_18798().method_37267();
            this.field_5442 = (float)Math.min(0.22, 0.1 + speed * 0.9);
            this.field_5441 = (float)Math.min(1.15, 0.95 + speed * 0.6);
         } else {
            this.method_24876();
         }
      } else {
         this.method_24876();
      }
   }

   public void requestStop() {
      this.method_24876();
   }

   private static boolean isMovingHorizontally(DwemerSphereEntity e) {
      class_243 v = e.method_18798();
      return v.field_1352 * v.field_1352 + v.field_1350 * v.field_1350 > 4.0E-4;
   }
}
