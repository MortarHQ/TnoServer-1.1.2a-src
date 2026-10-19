package zank.mods.efficient_hashing.mixin;

import net.minecraft.core.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import zank.mods.efficient_hashing.PhiMix;

@Mixin(Vec3i.class)
public abstract class MixinVec3i {
   @Shadow
   private int x;
   @Shadow
   private int y;
   @Shadow
   private int z;

   @Overwrite
   @Override
   public int hashCode() {
      return PhiMix.mix(PhiMix.mix(this.x) + this.y) + this.z;
   }
}
