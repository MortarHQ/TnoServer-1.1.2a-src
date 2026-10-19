package com.yungnickyoung.minecraft.yungscavebiomes.mixin.debug;

import net.minecraft.world.level.pathfinder.Path;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Path.class)
public interface PathAccessor {
}
