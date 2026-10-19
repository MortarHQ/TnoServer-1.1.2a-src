package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityRFConverter;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockRFConverter extends BlockContainerImpl implements IPickaxeBreakable {
   public BlockRFConverter() {
      super("rf_converter", BlockEntityRFConverter.class, Properties.of().sound(SoundType.STONE).strength(3.0F));
   }

   public String getDescriptionId() {
      return ModConfig.instance.rfConverter.get() ? super.getDescriptionId() : "block.naturesaura." + this.getBaseName() + ".disabled";
   }
}
