package mebahelcreaturesdwarven.mixin.accessor;

import net.minecraft.class_3785;
import net.minecraft.class_5434;
import net.minecraft.class_6880;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(class_5434.class)
public interface DwarvenJigsawStructureAccessor {
   @Accessor("field_38268")
   int dwarvenMebahel$getMaxDistanceFromCenter();

   @Accessor("field_37795")
   class_6880<class_3785> dwarvenMebahel$getStartPool();
}
