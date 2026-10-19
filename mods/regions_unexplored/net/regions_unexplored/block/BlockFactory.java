package net.regions_unexplored.block;

import java.util.function.Function;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public interface BlockFactory<T extends Block> extends Function<Properties, T> {
}
