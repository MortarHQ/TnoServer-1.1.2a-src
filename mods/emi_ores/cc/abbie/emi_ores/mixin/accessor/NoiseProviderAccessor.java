package cc.abbie.emi_ores.mixin.accessor;

import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NoiseProvider.class)
public interface NoiseProviderAccessor {
   @Accessor
   List<BlockState> getStates();
}
