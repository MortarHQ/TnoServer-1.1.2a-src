package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.Cataclysm_Skull_BlockEntity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class Cataclysm_Skull_Block extends SkullBlock {
   public Cataclysm_Skull_Block(Type p_56318_, Properties p_56319_) {
      super(p_56318_, p_56319_);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new Cataclysm_Skull_BlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_151992_, BlockState p_151993_, BlockEntityType<T> p_151994_) {
      if (p_151992_.isClientSide) {
         boolean flag = p_151993_.is((Block)ModBlocks.APTRGANGR_HEAD.get())
            || p_151993_.is((Block)ModBlocks.APTRGANGR_WALL_HEAD.get())
            || p_151993_.is((Block)ModBlocks.KOBOLEDIATOR_SKULL.get())
            || p_151993_.is((Block)ModBlocks.KOBOLEDIATOR_WALL_SKULL.get());
         if (flag) {
            return createTickerHelper(p_151994_, (BlockEntityType)ModTileentites.CATACLYSM_SKULL.get(), SkullBlockEntity::animation);
         }
      }

      return null;
   }

   public enum Types implements Type {
      KOBOLEDIATOR("kobolediator"),
      APTRGANGR("aptrgangr"),
      DRAUGR("draugr");

      private final String name;

      Types(String pName) {
         this.name = pName;
         TYPES.put(pName, this);
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
