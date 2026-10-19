package dev.worldgen.lithostitched.mixin.common;

import java.util.List;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TreeConfiguration.class)
public interface TreeConfigurationAccessor {
   @Accessor("decorators")
   @Mutable
   void lithostitched$setDecorators(List<TreeDecorator> var1);
}
