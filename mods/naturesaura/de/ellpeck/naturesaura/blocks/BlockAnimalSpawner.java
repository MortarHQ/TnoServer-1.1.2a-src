package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAnimalSpawner;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockAnimalSpawner extends BlockContainerImpl implements IPickaxeBreakable {
   public BlockAnimalSpawner() {
      super("animal_spawner", BlockEntityAnimalSpawner.class, Properties.of().strength(2.0F).sound(SoundType.STONE));
   }
}
