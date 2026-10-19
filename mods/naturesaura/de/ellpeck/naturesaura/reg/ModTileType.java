package de.ellpeck.naturesaura.reg;

import java.util.Arrays;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

public class ModTileType<T extends BlockEntity> implements IModItem {
   public final BlockEntityType<T> type;
   public final String name;

   public ModTileType(BlockEntitySupplier<T> supplier, IModItem item) {
      this(supplier, item.getBaseName(), item);
   }

   public ModTileType(BlockEntitySupplier<T> supplier, String name, IModItem... items) {
      this.name = name;
      Block[] blocks = Arrays.stream(items).map(i -> (Block)i).toArray(Block[]::new);
      this.type = Builder.of(supplier, blocks).build(null);
   }

   @Override
   public String getBaseName() {
      return this.name;
   }
}
