package de.ellpeck.naturesaura.items.tools;

import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

public class ItemHoe extends HoeItem implements IModItem, ICustomItemModel {
   private final String baseName;

   public ItemHoe(String baseName, Tier material, int speed) {
      super(material, new Properties().attributes(HoeItem.createAttributes(material, 0.0F, speed)));
      this.baseName = baseName;
      ModRegistry.ALL_ITEMS.add(this);
   }

   public InteractionResult useOn(UseOnContext context) {
      if (this == ModItems.INFUSED_IRON_HOE) {
         Level level = context.getLevel();
         InteractionResult result = super.useOn(context);
         if (!level.isClientSide && result.consumesAction()) {
            ItemStack seed = ItemStack.EMPTY;
            RandomSource random = level.getRandom();
            BlockPos pos = context.getClickedPos();
            if (random.nextInt(5) == 0) {
               seed = new ItemStack(Items.WHEAT_SEEDS);
            } else if (random.nextInt(10) == 0) {
               int rand = random.nextInt(3);
               if (rand == 0) {
                  seed = new ItemStack(Items.MELON_SEEDS);
               } else if (rand == 1) {
                  seed = new ItemStack(Items.PUMPKIN_SEEDS);
               } else if (rand == 2) {
                  seed = new ItemStack(Items.BEETROOT_SEEDS);
               }
            }

            if (!seed.isEmpty()) {
               ItemEntity item = new ItemEntity(level, pos.getX() + random.nextFloat(), pos.getY() + 1.0F, pos.getZ() + random.nextFloat(), seed);
               level.addFreshEntity(item);
            }
         }

         return result;
      } else {
         if (this != ModItems.SKY_HOE) {
            return super.useOn(context);
         }

         boolean success = false;

         for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
               BlockPos offset = context.getClickedPos().offset(x, 0, z);
               BlockHitResult newResult = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), offset, context.isInside());
               UseOnContext newContext = new UseOnContext(context.getPlayer(), context.getHand(), newResult);
               success |= super.useOn(newContext).consumesAction();
            }
         }

         return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
      }
   }

   public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
      if (!miningEntity.isShiftKeyDown() && (stack.getItem() == ModItems.SKY_HOE || stack.getItem() == ModItems.DEPTH_HOE)) {
         Block block = level.getBlockState(pos).getBlock();
         if ((block instanceof BushBlock || stack.getItem() == ModItems.DEPTH_HOE && block instanceof LeavesBlock) && !level.isClientSide) {
            int range = 3;

            for (int x = -range; x <= range; x++) {
               for (int y = -range; y <= range; y++) {
                  for (int z = -range; z <= range; z++) {
                     if (x != 0 || y != 0 || z != 0) {
                        BlockPos offset = pos.offset(x, y, z);
                        BlockState offState = level.getBlockState(offset);
                        if (offState.getBlock() instanceof BushBlock || stack.getItem() == ModItems.DEPTH_HOE && offState.getBlock() instanceof LeavesBlock) {
                           BlockEntity entity = offState.hasBlockEntity() ? level.getBlockEntity(offset) : null;
                           Block.dropResources(offState, level, offset, entity, null, stack);
                           level.setBlock(offset, Blocks.AIR.defaultBlockState(), 3);
                        }
                     }
                  }
               }
            }
         }
      }

      return super.mineBlock(stack, level, state, pos, miningEntity);
   }

   @Override
   public String getBaseName() {
      return this.baseName;
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/handheld")).texture("layer0", "item/" + this.getBaseName());
   }
}
