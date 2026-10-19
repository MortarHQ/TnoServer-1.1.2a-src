package mebahelcreaturesdwarven.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Codec;
import net.minecraft.class_5434;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(class_5434.class)
public abstract class DwarvenJigsawStructureIntRangeWrapMixin {
   private static final int NEW_MAX_DISTANCE = 180;

   @WrapOperation(method = "*", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;intRange(II)Lcom/mojang/serialization/Codec;"))
   private static Codec<Integer> dwarvenMebahel$expandMaxDistanceClamp(int min, int max, Operation<Codec<Integer>> original) {
      return min != 1 || max != 128 && max != 116 ? (Codec)original.call(new Object[]{min, max}) : (Codec)original.call(new Object[]{min, 180});
   }
}
