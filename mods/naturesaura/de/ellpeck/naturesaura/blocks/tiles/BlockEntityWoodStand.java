package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.blocks.multi.Multiblocks;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticleStream;
import de.ellpeck.naturesaura.packet.PacketParticles;
import de.ellpeck.naturesaura.recipes.TreeRitualRecipe;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class BlockEntityWoodStand extends BlockEntityImpl implements ITickableBlockEntity {
   public final ItemStackHandler items = new ItemStackHandlerNA(1, this, true) {
      public int getSlotLimit(int slot) {
         return 1;
      }
   };
   private RecipeHolder<TreeRitualRecipe> recipe;
   private BlockPos ritualPos;
   private int timer;

   public BlockEntityWoodStand(BlockPos pos, BlockState state) {
      super(ModBlockEntities.WOOD_STAND, pos, state);
   }

   public void setRitual(BlockPos pos, RecipeHolder<TreeRitualRecipe> recipe) {
      this.ritualPos = pos;
      this.recipe = recipe;
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.ritualPos != null && this.recipe != null && this.level.getGameTime() % 5L == 0L) {
         if (this.isRitualOkay()) {
            boolean wasOverHalf = this.timer >= ((TreeRitualRecipe)this.recipe.value()).time / 2;
            this.timer += 5;
            boolean isOverHalf = this.timer >= ((TreeRitualRecipe)this.recipe.value()).time / 2;
            if (!isOverHalf) {
               Multiblocks.TREE_RITUAL
                  .forEach(
                     this.ritualPos,
                     'W',
                     (pos, matcher) -> {
                        BlockEntity tile = this.level.getBlockEntity(pos);
                        if (tile instanceof BlockEntityWoodStand && !((BlockEntityWoodStand)tile).items.getStackInSlot(0).isEmpty()) {
                           PacketHandler.sendToAllAround(
                              this.level,
                              this.worldPosition,
                              32,
                              new PacketParticleStream(
                                 pos.getX() + 0.2F + this.level.random.nextFloat() * 0.6F,
                                 pos.getY() + 0.85F,
                                 pos.getZ() + 0.2F + this.level.random.nextFloat() * 0.6F,
                                 this.ritualPos.getX() + 0.5F,
                                 this.ritualPos.getY() + this.level.random.nextFloat() * 3.0F + 2.0F,
                                 this.ritualPos.getZ() + 0.5F,
                                 this.level.random.nextFloat() * 0.04F + 0.04F,
                                 9030711,
                                 this.level.random.nextFloat() + 1.0F
                              )
                           );
                        }

                        return true;
                     }
                  );
            }

            PacketHandler.sendToAllAround(
               this.level,
               this.ritualPos,
               32,
               new PacketParticles(this.ritualPos.getX(), this.ritualPos.getY(), this.ritualPos.getZ(), PacketParticles.Type.TR_GOLD_POWDER)
            );
            if (this.timer >= ((TreeRitualRecipe)this.recipe.value()).time) {
               Multiblocks.TREE_RITUAL.forEach(this.ritualPos, 'G', (pos, matcher) -> {
                  this.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                  return true;
               });
               Helper.mineRecursively(
                  this.level, this.ritualPos, this.ritualPos, ItemStack.EMPTY, 6, 32, s -> s.is(BlockTags.LOGS) || s.getBlock() instanceof LeavesBlock
               );
               ItemEntity item = new ItemEntity(
                  this.level,
                  this.ritualPos.getX() + 0.5,
                  this.ritualPos.getY() + 4.5,
                  this.ritualPos.getZ() + 0.5,
                  ((TreeRitualRecipe)this.recipe.value()).output.copy()
               );
               item.setExtendedLifetime();
               this.level.addFreshEntity(item);
               PacketHandler.sendToAllAround(
                  this.level,
                  this.worldPosition,
                  32,
                  new PacketParticles((float)item.getX(), (float)item.getY(), (float)item.getZ(), PacketParticles.Type.TR_SPAWN_RESULT)
               );
               this.level
                  .playSound(
                     null,
                     this.worldPosition.getX() + 0.5,
                     this.worldPosition.getY() + 0.5,
                     this.worldPosition.getZ() + 0.5,
                     SoundEvents.ENDERMAN_TELEPORT,
                     SoundSource.BLOCKS,
                     0.65F,
                     1.0F
                  );
               this.ritualPos = null;
               this.recipe = null;
               this.timer = 0;
            } else if (isOverHalf && !wasOverHalf) {
               Multiblocks.TREE_RITUAL
                  .forEach(
                     this.ritualPos,
                     'W',
                     (pos, matcher) -> {
                        if (this.level.getBlockEntity(pos) instanceof BlockEntityWoodStand stand && !stand.items.getStackInSlot(0).isEmpty()) {
                           PacketHandler.sendToAllAround(
                              this.level,
                              this.worldPosition,
                              32,
                              new PacketParticles(
                                 stand.worldPosition.getX(), stand.worldPosition.getY(), stand.worldPosition.getZ(), PacketParticles.Type.TR_CONSUME_ITEM
                              )
                           );
                           this.level
                              .playSound(
                                 null,
                                 stand.worldPosition.getX() + 0.5,
                                 stand.worldPosition.getY() + 0.5,
                                 stand.worldPosition.getZ() + 0.5,
                                 SoundEvents.WOOD_STEP,
                                 SoundSource.BLOCKS,
                                 0.5F,
                                 1.0F
                              );
                           stand.items.setStackInSlot(0, ItemStack.EMPTY);
                           stand.sendToClients();
                        }

                        return true;
                     }
                  );
            }
         } else {
            this.ritualPos = null;
            this.recipe = null;
            this.timer = 0;
         }
      }
   }

   private boolean isRitualOkay() {
      if (!Multiblocks.TREE_RITUAL.isComplete(this.level, this.ritualPos)) {
         return false;
      }

      for (int i = 0; i < 2; i++) {
         BlockState state = this.level.getBlockState(this.ritualPos.above(i));
         if (!state.is(BlockTags.LOGS)) {
            return false;
         }
      }

      if (this.timer >= ((TreeRitualRecipe)this.recipe.value()).time / 2) {
         return true;
      }

      List<Ingredient> required = new ArrayList<>(((TreeRitualRecipe)this.recipe.value()).ingredients);
      boolean fine = Multiblocks.TREE_RITUAL.forEach(this.ritualPos, 'W', (pos, matcher) -> {
         BlockEntity tile = this.level.getBlockEntity(pos);
         if (tile instanceof BlockEntityWoodStand) {
            ItemStack stack = ((BlockEntityWoodStand)tile).items.getStackInSlot(0);
            if (!stack.isEmpty()) {
               for (int i = required.size() - 1; i >= 0; i--) {
                  Ingredient req = required.get(i);
                  if (req.test(stack)) {
                     required.remove(i);
                     return true;
                  }
               }

               return false;
            }
         }

         return true;
      });
      return fine && required.isEmpty();
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         compound.put("items", this.items.serializeNBT(registries));
      }

      if (type == BlockEntityImpl.SaveType.TILE && this.ritualPos != null && this.recipe != null) {
         compound.putLong("ritual_pos", this.ritualPos.asLong());
         compound.putInt("timer", this.timer);
         compound.putString("recipe", this.recipe.toString());
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.items.deserializeNBT(registries, compound.getCompound("items"));
      }

      if (type == BlockEntityImpl.SaveType.TILE && compound.contains("recipe")) {
         this.ritualPos = BlockPos.of(compound.getLong("ritual_pos"));
         this.timer = compound.getInt("timer");
         if (this.hasLevel()) {
            this.recipe = (RecipeHolder<TreeRitualRecipe>)this.level
               .getRecipeManager()
               .byKey(ResourceLocation.parse(compound.getString("recipe")))
               .orElse(null);
         }
      }
   }
}
