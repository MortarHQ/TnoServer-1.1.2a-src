package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.blocks.multi.Multiblocks;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import de.ellpeck.naturesaura.recipes.ModRecipes;
import de.ellpeck.naturesaura.recipes.OfferingRecipe;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;

public class BlockEntityOfferingTable extends BlockEntityImpl implements ITickableBlockEntity {
   public final ItemStackHandler items = new ItemStackHandlerNA(1, this, true) {
      public int getSlotLimit(int slot) {
         return 16;
      }
   };
   private final Queue<ItemStack> itemsToSpawn = new ArrayDeque<>();

   public BlockEntityOfferingTable(BlockPos pos, BlockState state) {
      super(ModBlockEntities.OFFERING_TABLE, pos, state);
   }

   private OfferingRecipe getRecipe(ItemStack input) {
      for (RecipeHolder<OfferingRecipe> recipe : this.level.getRecipeManager().getRecipesFor(ModRecipes.OFFERING_TYPE, null, this.level)) {
         if (((OfferingRecipe)recipe.value()).input.test(input)) {
            return (OfferingRecipe)recipe.value();
         }
      }

      return null;
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         if (this.level.getGameTime() % 20L == 0L) {
            if (!Multiblocks.OFFERING_TABLE.isComplete(this.level, this.worldPosition)) {
               return;
            }

            ItemStack stack = this.items.getStackInSlot(0);
            if (stack.isEmpty()) {
               return;
            }

            List<ItemEntity> items = this.level.getEntitiesOfClass(ItemEntity.class, new AABB(this.worldPosition).inflate(1.0));
            if (items.isEmpty()) {
               return;
            }

            OfferingRecipe recipe = this.getRecipe(stack);
            if (recipe == null) {
               return;
            }

            for (ItemEntity item : items) {
               if (item.isAlive() && !item.hasPickUpDelay()) {
                  ItemStack itemStack = item.getItem();
                  if (!itemStack.isEmpty() && itemStack.getCount() == 1 && recipe.startItem.test(itemStack)) {
                     int amount = Helper.getIngredientAmount(recipe.input);
                     int recipeCount = stack.getCount() / amount;
                     stack.shrink(recipeCount * amount);
                     item.kill();
                     this.sendToClients();
                     this.setChanged();

                     for (int i = 0; i < recipeCount; i++) {
                        this.itemsToSpawn.add(recipe.output.copy());
                     }

                     if (Multiblocks.OFFERING_TABLE
                        .forEach(this.worldPosition, 'R', (pos, m) -> this.level.getBlockState(pos).getBlock() == Blocks.WITHER_ROSE)) {
                        for (int i = this.level.random.nextInt(5) + 3; i >= 0; i--) {
                           this.itemsToSpawn.add(new ItemStack(Items.BLACK_DYE));
                        }
                     }

                     LightningBolt lightningboltentity = (LightningBolt)EntityType.LIGHTNING_BOLT.create(this.level);
                     lightningboltentity.setVisualOnly(true);
                     lightningboltentity.moveTo(Vec3.atCenterOf(this.worldPosition));
                     this.level.addFreshEntity(lightningboltentity);
                     PacketHandler.sendToAllAround(
                        this.level,
                        this.worldPosition,
                        32,
                        new PacketParticles(
                           (float)item.getX(),
                           (float)item.getY(),
                           (float)item.getZ(),
                           PacketParticles.Type.OFFERING_TABLE,
                           this.worldPosition.getX(),
                           this.worldPosition.getY(),
                           this.worldPosition.getZ()
                        )
                     );
                     break;
                  }
               }
            }
         } else if (this.level.getGameTime() % 3L == 0L && !this.itemsToSpawn.isEmpty()) {
            this.level
               .addFreshEntity(
                  new ItemEntity(
                     this.level, this.worldPosition.getX() + 0.5F, this.level.getMaxBuildHeight(), this.worldPosition.getZ() + 0.5F, this.itemsToSpawn.remove()
                  )
               );
         }
      }
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         compound.put("items", this.items.serializeNBT(registries));
         if (type != BlockEntityImpl.SaveType.SYNC) {
            ListTag list = new ListTag();

            for (ItemStack stack : this.itemsToSpawn) {
               list.add(stack.save(registries, new CompoundTag()));
            }

            compound.put("items_to_spawn", list);
         }
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.items.deserializeNBT(registries, compound.getCompound("items"));
         if (type != BlockEntityImpl.SaveType.SYNC) {
            this.itemsToSpawn.clear();

            for (Tag base : compound.getList("items_to_spawn", 10)) {
               this.itemsToSpawn.add(ItemStack.parseOptional(registries, (CompoundTag)base));
            }
         }
      }
   }
}
