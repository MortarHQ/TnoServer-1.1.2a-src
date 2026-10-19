package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.container.BasicAuraContainer;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.blocks.multi.Multiblocks;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticleStream;
import de.ellpeck.naturesaura.packet.PacketParticles;
import de.ellpeck.naturesaura.recipes.AltarRecipe;
import de.ellpeck.naturesaura.recipes.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemStackHandler;

public class BlockEntityNatureAltar extends BlockEntityImpl implements ITickableBlockEntity {
   public final BasicAuraContainer container = new BasicAuraContainer(null, 500000) {
      @Override
      public int getAuraColor() {
         return IAuraType.forLevel(BlockEntityNatureAltar.this.level).getColor();
      }
   };
   private final ItemStack[] catalysts = new ItemStack[4];
   public final ItemStackHandler items = new ItemStackHandlerNA(1, this, true) {
      public int getSlotLimit(int slot) {
         return 1;
      }

      @Override
      protected boolean canInsert(ItemStack stack, int slot) {
         return BlockEntityNatureAltar.this.getRecipeForInput(stack) != null
            || stack.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY, null) != null;
      }

      @Override
      protected boolean canExtract(ItemStack stack, int slot, int amount) {
         IAuraContainer cap = (IAuraContainer)stack.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
         return cap != null ? cap.storeAura(1, true) <= 0 : BlockEntityNatureAltar.this.getRecipeForInput(stack) == null;
      }
   };
   @OnlyIn(Dist.CLIENT)
   public int bobTimer;
   public boolean isComplete;
   private RecipeHolder<AltarRecipe> currentRecipe;
   private int timer;
   private int lastAura;
   private boolean firstTick = true;

   public BlockEntityNatureAltar(BlockPos pos, BlockState state) {
      super(ModBlockEntities.NATURE_ALTAR, pos, state);
   }

   @Override
   public void tick() {
      RandomSource rand = this.level.random;
      if (this.level.getGameTime() % 40L == 0L) {
         int index = 0;

         for (int x = -2; x <= 2; x += 4) {
            for (int z = -2; z <= 2; z += 4) {
               BlockPos offset = this.worldPosition.offset(x, 1, z);
               BlockState state = this.level.getBlockState(offset);
               this.catalysts[index] = new ItemStack(state.getBlock());
               index++;
            }
         }
      }

      if (!this.level.isClientSide) {
         if (this.level.getGameTime() % 40L == 0L || this.firstTick) {
            boolean complete = Multiblocks.ALTAR.isComplete(this.level, this.worldPosition);
            if (complete != this.isComplete) {
               this.isComplete = complete;
               this.sendToClients();
            }

            this.firstTick = false;
         }

         if (this.isComplete) {
            IAuraType type = IAuraType.forLevel(this.level);
            int space = this.container.storeAura(300, true);
            if (space > 0 && (type.isSimilar(NaturesAuraAPI.TYPE_OVERWORLD) || type.isSimilar(NaturesAuraAPI.TYPE_NETHER))) {
               int toStore = Math.min(IAuraChunk.getAuraInArea(this.level, this.worldPosition, 20), space);
               if (toStore > 0) {
                  BlockPos spot = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 20, this.worldPosition);
                  IAuraChunk chunk = IAuraChunk.getAuraChunk(this.level, spot);
                  chunk.drainAura(spot, toStore);
                  this.container.storeAura(toStore, false);
                  if (this.level.getGameTime() % 3L == 0L) {
                     PacketHandler.sendToAllAround(
                        this.level,
                        this.worldPosition,
                        32,
                        new PacketParticleStream(
                           this.worldPosition.getX() + (float)rand.nextGaussian() * 10.0F,
                           this.worldPosition.getY() + rand.nextFloat() * 10.0F,
                           this.worldPosition.getZ() + (float)rand.nextGaussian() * 10.0F,
                           this.worldPosition.getX() + 0.5F,
                           this.worldPosition.getY() + 0.5F,
                           this.worldPosition.getZ() + 0.5F,
                           rand.nextFloat() * 0.1F + 0.1F,
                           this.container.getAuraColor(),
                           rand.nextFloat() + 1.0F
                        )
                     );
                  }
               }
            }

            ItemStack stack = this.items.getStackInSlot(0);
            IAuraContainer container = (IAuraContainer)stack.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
            if (!stack.isEmpty() && container != null) {
               int theoreticalDrain = this.container.drainAura(1000, true);
               if (theoreticalDrain > 0) {
                  int stored = container.storeAura(theoreticalDrain, false);
                  if (stored > 0) {
                     this.container.drainAura(stored, false);
                     if (this.level.getGameTime() % 4L == 0L) {
                        PacketHandler.sendToAllAround(
                           this.level,
                           this.worldPosition,
                           32,
                           new PacketParticles(
                              this.worldPosition.getX(),
                              this.worldPosition.getY(),
                              this.worldPosition.getZ(),
                              PacketParticles.Type.ALTAR_CONVERSION,
                              this.container.getAuraColor()
                           )
                        );
                     }
                  }
               }
            } else if (this.currentRecipe == null) {
               if (!stack.isEmpty()) {
                  this.currentRecipe = this.getRecipeForInput(stack);
               }
            } else if (!stack.isEmpty() && ((AltarRecipe)this.currentRecipe.value()).input.test(stack)) {
               int req = Mth.ceil((double)((AltarRecipe)this.currentRecipe.value()).aura / ((AltarRecipe)this.currentRecipe.value()).time);
               if (this.container.getStoredAura() >= req) {
                  this.container.drainAura(req, false);
                  if (this.timer % 4 == 0) {
                     PacketHandler.sendToAllAround(
                        this.level,
                        this.worldPosition,
                        32,
                        new PacketParticles(
                           this.worldPosition.getX(),
                           this.worldPosition.getY(),
                           this.worldPosition.getZ(),
                           PacketParticles.Type.ALTAR_CONVERSION,
                           this.container.getAuraColor()
                        )
                     );
                  }

                  this.timer++;
                  if (this.timer >= ((AltarRecipe)this.currentRecipe.value()).time) {
                     this.items.setStackInSlot(0, ((AltarRecipe)this.currentRecipe.value()).output.copy());
                     this.currentRecipe = null;
                     this.timer = 0;
                     this.level
                        .playSound(
                           null,
                           this.worldPosition.getX() + 0.5,
                           this.worldPosition.getY() + 0.5,
                           this.worldPosition.getZ() + 0.5,
                           SoundEvents.ARROW_HIT_PLAYER,
                           SoundSource.BLOCKS,
                           0.65F,
                           1.0F
                        );
                  }
               }
            } else {
               this.currentRecipe = null;
               this.timer = 0;
            }
         }

         if (this.level.getGameTime() % 10L == 0L && this.lastAura != this.container.getStoredAura()) {
            this.lastAura = this.container.getStoredAura();
            this.sendToClients();
         }
      } else {
         if (this.isComplete && rand.nextFloat() >= 0.7F) {
            int fourths = this.container.getMaxAura() / 4;
            if (this.container.getStoredAura() > 0) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     this.worldPosition.getX() - 4.0F + rand.nextFloat(),
                     this.worldPosition.getY() + 3.0F,
                     this.worldPosition.getZ() + rand.nextFloat(),
                     0.0,
                     0.0,
                     0.0,
                     this.container.getAuraColor(),
                     rand.nextFloat() * 3.0F + 1.0F,
                     rand.nextInt(100) + 50,
                     -0.05F,
                     true,
                     true
                  );
            }

            if (this.container.getStoredAura() >= fourths) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     this.worldPosition.getX() + 4.0F + rand.nextFloat(),
                     this.worldPosition.getY() + 3.0F,
                     this.worldPosition.getZ() + rand.nextFloat(),
                     0.0,
                     0.0,
                     0.0,
                     this.container.getAuraColor(),
                     rand.nextFloat() * 3.0F + 1.0F,
                     rand.nextInt(100) + 50,
                     -0.05F,
                     true,
                     true
                  );
            }

            if (this.container.getStoredAura() >= fourths * 2) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     this.worldPosition.getX() + rand.nextFloat(),
                     this.worldPosition.getY() + 3.0F,
                     this.worldPosition.getZ() - 4.0F + rand.nextFloat(),
                     0.0,
                     0.0,
                     0.0,
                     this.container.getAuraColor(),
                     rand.nextFloat() * 3.0F + 1.0F,
                     rand.nextInt(100) + 50,
                     -0.05F,
                     true,
                     true
                  );
            }

            if (this.container.getStoredAura() >= fourths * 3) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     this.worldPosition.getX() + rand.nextFloat(),
                     this.worldPosition.getY() + 3.0F,
                     this.worldPosition.getZ() + 4.0F + rand.nextFloat(),
                     0.0,
                     0.0,
                     0.0,
                     this.container.getAuraColor(),
                     rand.nextFloat() * 3.0F + 1.0F,
                     rand.nextInt(100) + 50,
                     -0.05F,
                     true,
                     true
                  );
            }
         }

         this.bobTimer++;
      }
   }

   private RecipeHolder<AltarRecipe> getRecipeForInput(ItemStack input) {
      for (RecipeHolder<AltarRecipe> holder : this.level.getRecipeManager().getRecipesFor(ModRecipes.ALTAR_TYPE, null, this.level)) {
         AltarRecipe recipe = (AltarRecipe)holder.value();
         if (recipe.input.test(input)) {
            if (recipe.catalyst == Ingredient.EMPTY) {
               return holder;
            }

            for (ItemStack stack : this.catalysts) {
               if (recipe.catalyst.test(stack)) {
                  return holder;
               }
            }
         }
      }

      return null;
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         compound.put("items", this.items.serializeNBT(registries));
         compound.putBoolean("complete", this.isComplete);
         this.container.writeNBT(compound);
      }

      if (type == BlockEntityImpl.SaveType.TILE && this.currentRecipe != null) {
         compound.putString("recipe", this.currentRecipe.id().toString());
         compound.putInt("timer", this.timer);
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.items.deserializeNBT(registries, compound.getCompound("items"));
         this.isComplete = compound.getBoolean("complete");
         this.container.readNBT(compound);
      }

      if (type == BlockEntityImpl.SaveType.TILE && compound.contains("recipe")) {
         if (this.hasLevel()) {
            this.currentRecipe = (RecipeHolder<AltarRecipe>)this.level
               .getRecipeManager()
               .byKey(ResourceLocation.parse(compound.getString("recipe")))
               .orElse(null);
         }

         this.timer = compound.getInt("timer");
      }
   }
}
