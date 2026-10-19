package mebahelcreaturesdwarven.mixin;

import java.util.Optional;
import java.util.Set;
import mebahelcreaturesdwarven.mixin.accessor.DwarvenJigsawStructureAccessor;
import net.minecraft.class_2338;
import net.minecraft.class_2960;
import net.minecraft.class_3785;
import net.minecraft.class_5434;
import net.minecraft.class_6880;
import net.minecraft.class_3195.class_7149;
import net.minecraft.class_3195.class_7150;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(class_5434.class)
public abstract class DwarvenJigsawStructureHeightLimitMixin {
   @Unique
   private static final int NEW_MAX_HEIGHT = -36;
   @Unique
   private static final int MIN_OFFSET_FROM_BOTTOM = 20;
   @Unique
   private static final Set<class_2960> MEBAHEL_START_POOLS = Set.of(
      class_2960.method_60655("mebahelcreaturesdwarven", "dwemer_ruin/blackreach_start_pool"),
      class_2960.method_60655("mebahelcreaturesdwarven", "dwemer_ruin/raldbthar_start_pool")
   );

   @Inject(method = "method_38676", at = @At("RETURN"), cancellable = true)
   private void dwarvenMebahel$cancelIfOutsideAllowedHeight(class_7149 context, CallbackInfoReturnable<Optional<class_7150>> cir) {
      class_5434 self = (class_5434)this;
      if (dwarvenMebahel$isMebahelStructure(self)) {
         Optional<class_7150> result = (Optional<class_7150>)cir.getReturnValue();
         if (!result.isEmpty()) {
            class_2338 pos = result.get().comp_571();
            int minAllowedY = context.comp_569().method_31607() + 20;
            if (pos.method_10264() > -36 || pos.method_10264() < minAllowedY) {
               cir.setReturnValue(Optional.empty());
            }
         }
      }
   }

   @Unique
   private static boolean dwarvenMebahel$isMebahelStructure(class_5434 structure) {
      class_6880<class_3785> startPool = ((DwarvenJigsawStructureAccessor)structure).dwarvenMebahel$getStartPool();
      return startPool.method_40230().map(key -> MEBAHEL_START_POOLS.contains(key.method_29177())).orElse(false);
   }
}
