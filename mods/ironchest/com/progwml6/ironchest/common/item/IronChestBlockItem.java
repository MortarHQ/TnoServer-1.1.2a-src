package com.progwml6.ironchest.common.item;

import com.progwml6.ironchest.client.model.inventory.IronChestItemStackRenderer;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.regular.CopperChestBlock;
import com.progwml6.ironchest.common.block.regular.CrystalChestBlock;
import com.progwml6.ironchest.common.block.regular.DiamondChestBlock;
import com.progwml6.ironchest.common.block.regular.DirtChestBlock;
import com.progwml6.ironchest.common.block.regular.GoldChestBlock;
import com.progwml6.ironchest.common.block.regular.IronChestBlock;
import com.progwml6.ironchest.common.block.regular.ObsidianChestBlock;
import com.progwml6.ironchest.common.block.regular.entity.CopperChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.CrystalChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.DiamondChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.DirtChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.GoldChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.IronChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.ObsidianChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.TrappedCopperChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedCrystalChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedDiamondChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedDirtChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedGoldChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedIronChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedObsidianChestBlock;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedCopperChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedCrystalChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedDiamondChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedDirtChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedGoldChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedIronChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedObsidianChestBlockEntity;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class IronChestBlockItem extends BlockItem {
   protected IronChestsTypes type;
   protected Boolean trapped;

   public IronChestBlockItem(Block block, Properties properties, IronChestsTypes type, Boolean trapped) {
      super(block, properties);
      this.type = type;
      this.trapped = trapped;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(
         new IClientItemExtensions() {
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               Supplier<BlockEntity> modelToUse;
               if (IronChestBlockItem.this.trapped) {
                  switch (IronChestBlockItem.this.type) {
                     case GOLD:
                        modelToUse = () -> new TrappedGoldChestBlockEntity(
                           BlockPos.ZERO, ((TrappedGoldChestBlock)IronChestsBlocks.TRAPPED_GOLD_CHEST.get()).defaultBlockState()
                        );
                        break;
                     case DIAMOND:
                        modelToUse = () -> new TrappedDiamondChestBlockEntity(
                           BlockPos.ZERO, ((TrappedDiamondChestBlock)IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get()).defaultBlockState()
                        );
                        break;
                     case COPPER:
                        modelToUse = () -> new TrappedCopperChestBlockEntity(
                           BlockPos.ZERO, ((TrappedCopperChestBlock)IronChestsBlocks.TRAPPED_COPPER_CHEST.get()).defaultBlockState()
                        );
                        break;
                     case CRYSTAL:
                        modelToUse = () -> new TrappedCrystalChestBlockEntity(
                           BlockPos.ZERO, ((TrappedCrystalChestBlock)IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get()).defaultBlockState()
                        );
                        break;
                     case OBSIDIAN:
                        modelToUse = () -> new TrappedObsidianChestBlockEntity(
                           BlockPos.ZERO, ((TrappedObsidianChestBlock)IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get()).defaultBlockState()
                        );
                        break;
                     case DIRT:
                        modelToUse = () -> new TrappedDirtChestBlockEntity(
                           BlockPos.ZERO, ((TrappedDirtChestBlock)IronChestsBlocks.TRAPPED_DIRT_CHEST.get()).defaultBlockState()
                        );
                        break;
                     default:
                        modelToUse = () -> new TrappedIronChestBlockEntity(
                           BlockPos.ZERO, ((TrappedIronChestBlock)IronChestsBlocks.TRAPPED_IRON_CHEST.get()).defaultBlockState()
                        );
                  }
               } else {
                  switch (IronChestBlockItem.this.type) {
                     case GOLD:
                        modelToUse = () -> new GoldChestBlockEntity(BlockPos.ZERO, ((GoldChestBlock)IronChestsBlocks.GOLD_CHEST.get()).defaultBlockState());
                        break;
                     case DIAMOND:
                        modelToUse = () -> new DiamondChestBlockEntity(
                           BlockPos.ZERO, ((DiamondChestBlock)IronChestsBlocks.DIAMOND_CHEST.get()).defaultBlockState()
                        );
                        break;
                     case COPPER:
                        modelToUse = () -> new CopperChestBlockEntity(
                           BlockPos.ZERO, ((CopperChestBlock)IronChestsBlocks.COPPER_CHEST.get()).defaultBlockState()
                        );
                        break;
                     case CRYSTAL:
                        modelToUse = () -> new CrystalChestBlockEntity(
                           BlockPos.ZERO, ((CrystalChestBlock)IronChestsBlocks.CRYSTAL_CHEST.get()).defaultBlockState()
                        );
                        break;
                     case OBSIDIAN:
                        modelToUse = () -> new ObsidianChestBlockEntity(
                           BlockPos.ZERO, ((ObsidianChestBlock)IronChestsBlocks.OBSIDIAN_CHEST.get()).defaultBlockState()
                        );
                        break;
                     case DIRT:
                        modelToUse = () -> new DirtChestBlockEntity(BlockPos.ZERO, ((DirtChestBlock)IronChestsBlocks.DIRT_CHEST.get()).defaultBlockState());
                        break;
                     default:
                        modelToUse = () -> new IronChestBlockEntity(BlockPos.ZERO, ((IronChestBlock)IronChestsBlocks.IRON_CHEST.get()).defaultBlockState());
                  }
               }

               return new IronChestItemStackRenderer<>(
                  Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels(), modelToUse
               );
            }
         }
      );
   }
}
