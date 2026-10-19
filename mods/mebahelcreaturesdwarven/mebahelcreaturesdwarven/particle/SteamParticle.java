package mebahelcreaturesdwarven.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2400;
import net.minecraft.class_3999;
import net.minecraft.class_4002;
import net.minecraft.class_4003;
import net.minecraft.class_638;
import net.minecraft.class_703;
import net.minecraft.class_707;

@Environment(EnvType.CLIENT)
public class SteamParticle extends class_4003 {
   protected SteamParticle(class_638 world, double x, double y, double z, double xd, double yd, double zd) {
      super(world, x, y, z, xd, yd, zd);
      this.field_17867 = 0.15F + this.field_3840.method_43057() * 0.13F;
      this.field_3847 = 10 + this.field_3840.method_43048(8);
      this.field_28786 = 0.85F;
      double spread = 0.02;
      this.field_3852 = (this.field_3840.method_43058() - 0.5) * spread + xd * 0.25;
      this.field_3850 = (this.field_3840.method_43058() - 0.5) * spread + zd * 0.25;
      this.field_3869 = 0.02 + this.field_3840.method_43058() * 0.02 + yd * 0.1;
      float base = 0.85F + this.field_3840.method_43057() * 0.15F;
      this.field_3861 = base;
      this.field_3842 = base;
      this.field_3859 = base;
      this.field_3841 = 0.85F;
      this.field_3844 = 0.0F;
   }

   public int method_3068(float tickDelta) {
      return 180;
   }

   public void method_3070() {
      super.method_3070();
      this.field_17867 *= 0.96F;
      if (this.field_3840.method_43057() < 0.08F) {
         this.field_3852 = this.field_3852 + (this.field_3840.method_43057() * 2.0F - 1.0F) * 0.0015F;
         this.field_3850 = this.field_3850 + (this.field_3840.method_43057() * 2.0F - 1.0F) * 0.0015F;
      }

      float life = (float)this.field_3866 / this.field_3847;
      this.field_3841 = Math.max(0.0F, 0.85F * (1.0F - life));
   }

   public class_3999 method_18122() {
      return class_3999.field_17829;
   }

   @Environment(EnvType.CLIENT)
   public static class Factory implements class_707<class_2400> {
      private final class_4002 sprites;

      public Factory(class_4002 sprites) {
         this.sprites = sprites;
      }

      public class_703 createParticle(class_2400 type, class_638 world, double x, double y, double z, double dx, double dy, double dz) {
         SteamParticle p = new SteamParticle(world, x, y, z, dx, dy, dz);
         p.method_18140(this.sprites);
         return p;
      }
   }
}
