package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.blocks.BlockAutoCrafter;
import de.ellpeck.naturesaura.blocks.multi.Multiblocks;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BlockEntityAutoCrafter extends BlockEntityImpl implements ITickableBlockEntity {
   public final CraftingContainer crafting = new TransientCraftingContainer(new AbstractContainerMenu(null, 0) {
      public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
         return ItemStack.EMPTY;
      }

      public boolean stillValid(Player playerIn) {
         return false;
      }
   }, 3, 3);

   public BlockEntityAutoCrafter(BlockPos pos, BlockState state) {
      super(ModBlockEntities.AUTO_CRAFTER, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         if (this.level.getGameTime() % 60L != 0L) {
            return;
         }

         if (!Multiblocks.AUTO_CRAFTER.isComplete(this.level, this.worldPosition)) {
            return;
         }

         this.crafting.clearContent();
         BlockState state = this.level.getBlockState(this.worldPosition);
         Direction facing = (Direction)state.getValue(BlockAutoCrafter.FACING);
         BlockPos middlePos = this.worldPosition.above();
         BlockPos topPos = middlePos.relative(facing, 2);
         BlockPos bottomPos = middlePos.relative(facing.getOpposite(), 2);
         BlockPos[] poses = new BlockPos[]{
            topPos.relative(facing.getCounterClockWise(), 2),
            topPos,
            topPos.relative(facing.getClockWise(), 2),
            middlePos.relative(facing.getCounterClockWise(), 2),
            middlePos,
            middlePos.relative(facing.getClockWise(), 2),
            bottomPos.relative(facing.getCounterClockWise(), 2),
            bottomPos,
            bottomPos.relative(facing.getClockWise(), 2)
         };
         ItemEntity[] items = new ItemEntity[9];

         for (int i = 0; i < poses.length; i++) {
            List<ItemEntity> entities = this.level.getEntitiesOfClass(ItemEntity.class, new AABB(poses[i]).inflate(0.25), EntitySelector.ENTITY_STILL_ALIVE);
            if (entities.size() > 1) {
               return;
            }

            if (!entities.isEmpty()) {
               ItemEntity entity = entities.getFirst();
               if (entity.hasPickUpDelay()) {
                  return;
               }

               ItemStack stack = entity.getItem();
               if (stack.isEmpty()) {
                  return;
               }

               items[i] = entity;
               this.crafting.setItem(i, stack.copy());
            }
         }

         RecipeHolder<CraftingRecipe> recipe = (RecipeHolder<CraftingRecipe>)this.level
            .getRecipeManager()
            .getRecipeFor(RecipeType.CRAFTING, this.crafting.asCraftInput(), this.level)
            .orElse(null);
         if (recipe == null) {
            return;
         }

         ItemStack result = ((CraftingRecipe)recipe.value()).assemble(this.crafting.asCraftInput(), this.level.registryAccess());
         if (result.isEmpty()) {
            return;
         }

         ItemEntity resultItem = new ItemEntity(
            this.level, this.worldPosition.getX() + 0.5F, this.worldPosition.getY() - 0.35F, this.worldPosition.getZ() + 0.5F, result.copy()
         );
         resultItem.setDeltaMovement(0.0, 0.0, 0.0);
         this.level.addFreshEntity(resultItem);
         NonNullList<ItemStack> remainingItems = ((CraftingRecipe)recipe.value()).getRemainingItems(this.crafting.asCraftInput());

         for (int i = 0; i < items.length; i++) {
            ItemEntity item = items[i];
            if (item != null) {
               ItemStack stack = item.getItem();
               if (stack.getCount() <= 1) {
                  item.discard();
               } else {
                  stack.shrink(1);
                  item.setItem(stack);
               }

               ItemStack remain = (ItemStack)remainingItems.get(i);
               if (!remain.isEmpty()) {
                  ItemEntity remItem = new ItemEntity(this.level, item.getX(), item.getY(), item.getZ(), remain.copy());
                  remItem.setDeltaMovement(0.0, 0.0, 0.0);
                  this.level.addFreshEntity(remItem);
               }

               PacketHandler.sendToAllAround(
                  this.level,
                  this.worldPosition,
                  32,
                  new PacketParticles((float)item.getX(), (float)item.getY(), (float)item.getZ(), PacketParticles.Type.ANIMAL_SPAWNER)
               );
            }
         }
      }
   }
}
