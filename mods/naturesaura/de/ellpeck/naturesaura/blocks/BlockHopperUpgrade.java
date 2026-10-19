package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.api.render.IVisualizable;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityHopperUpgrade;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockHopperUpgrade extends BlockContainerImpl implements IVisualizable, IPickaxeBreakable {
   public BlockHopperUpgrade() {
      super("hopper_upgrade", BlockEntityHopperUpgrade.class, Properties.of().strength(2.5F).sound(SoundType.METAL));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public AABB getVisualizationBounds(Level level, BlockPos pos) {
      return new AABB(pos).inflate(7.0);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public int getVisualizationColor(Level level, BlockPos pos) {
      return 4411199;
   }
}
