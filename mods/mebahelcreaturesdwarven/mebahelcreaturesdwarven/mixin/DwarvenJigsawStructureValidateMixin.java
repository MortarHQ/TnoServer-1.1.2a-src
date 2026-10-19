package mebahelcreaturesdwarven.mixin;

import com.mojang.serialization.DataResult;
import java.util.Set;
import mebahelcreaturesdwarven.mixin.accessor.DwarvenJigsawStructureAccessor;
import net.minecraft.class_2960;
import net.minecraft.class_3785;
import net.minecraft.class_5434;
import net.minecraft.class_6880;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(class_5434.class)
public abstract class DwarvenJigsawStructureValidateMixin {
   @Unique
   private static final int NEW_MAX_DISTANCE = 180;
   @Unique
   private static final Set<class_2960> MEBAHEL_START_POOLS = Set.of(
      class_2960.method_60655("mebahelcreaturesdwarven", "dwemer_ruin/blackreach_start_pool"),
      class_2960.method_60655("mebahelcreaturesdwarven", "dwemer_ruin/raldbthar_start_pool")
   );

   @Inject(method = "method_42708(Lnet/minecraft/class_5434;)Lcom/mojang/serialization/DataResult;", at = @At("HEAD"), cancellable = true, require = 0)
   private static void dwarvenMebahel$allowBiggerOnlyForMine(class_5434 structure, CallbackInfoReturnable<DataResult<class_5434>> cir) {
      int max = ((DwarvenJigsawStructureAccessor)structure).dwarvenMebahel$getMaxDistanceFromCenter();
      if (max > 128) {
         if (max <= 180) {
            if (dwarvenMebahel$isMebahelStructure(structure)) {
               cir.setReturnValue(DataResult.success(structure));
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
