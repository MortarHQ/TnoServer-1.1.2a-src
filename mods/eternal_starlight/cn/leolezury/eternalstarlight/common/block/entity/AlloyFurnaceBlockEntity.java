package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceBlock;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.item.menu.AlloyFurnaceMenu;
import cn.leolezury.eternalstarlight.common.item.recipe.AlloyRecipe;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.CraftingInput.Positioned;
import net.minecraft.world.item.crafting.RecipeManager.CachedCheck;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AlloyFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
   private static final String TAG_LIT_TICKS = "lit_ticks";
   private static final String TAG_TOTAL_LIT_TICKS = "total_lit_ticks";
   private static final String TAG_BURN_TICKS = "burn_ticks";
   private static final String TAG_TOTAL_BURN_TICKS = "total_burn_ticks";
   private static final String TAG_OVERHEAT_TICKS = "overheat_ticks";
   private static final String TAG_COOLING_TICKS = "cooling_ticks";
   private static final String TAG_TOTAL_COOLING_TICKS = "total_cooling_ticks";
   private static final String TAG_COOLING_EFFICIENCY = "cooling_efficiency";
   private static final int[] SLOTS_FOR_DOWN = new int[]{11, 12, 13};
   private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
   private static final int[] SLOTS_FOR_FRONT_AND_BACK = new int[]{9};
   private static final int[] SLOTS_FOR_SIDES = new int[]{10};
   protected final ContainerData dataAccess = new ContainerData() {
      public int get(int id) {
         return switch (id) {
            case 0 -> AlloyFurnaceBlockEntity.this.totalLitTicks > 32767
               ? Mth.floor((double)AlloyFurnaceBlockEntity.this.litTicks / AlloyFurnaceBlockEntity.this.totalLitTicks * 32767.0)
               : AlloyFurnaceBlockEntity.this.litTicks;
            case 1 -> Math.min(AlloyFurnaceBlockEntity.this.totalLitTicks, 32767);
            case 2 -> AlloyFurnaceBlockEntity.this.burnTicks;
            case 3 -> AlloyFurnaceBlockEntity.this.totalBurnTicks;
            case 4 -> AlloyFurnaceBlockEntity.this.overheatTicks;
            case 5 -> AlloyFurnaceBlockEntity.this.coolingTicks;
            case 6 -> AlloyFurnaceBlockEntity.this.totalCoolingTicks;
            default -> 0;
         };
      }

      public void set(int id, int value) {
         switch (id) {
            case 0:
               AlloyFurnaceBlockEntity.this.litTicks = value;
               break;
            case 1:
               AlloyFurnaceBlockEntity.this.totalLitTicks = value;
               break;
            case 2:
               AlloyFurnaceBlockEntity.this.burnTicks = value;
               break;
            case 3:
               AlloyFurnaceBlockEntity.this.totalBurnTicks = value;
            case 4:
               AlloyFurnaceBlockEntity.this.overheatTicks = value;
            case 5:
               AlloyFurnaceBlockEntity.this.coolingTicks = value;
            case 6:
               AlloyFurnaceBlockEntity.this.totalCoolingTicks = value;
         }
      }

      public int getCount() {
         return 7;
      }
   };
   private final CachedCheck<CraftingInput, AlloyRecipe> quickCheck;
   private NonNullList<ItemStack> items = NonNullList.withSize(14, ItemStack.EMPTY);
   private int litTicks;
   private int totalLitTicks;
   private int burnTicks;
   private int totalBurnTicks;
   private int overheatTicks;
   private int coolingTicks;
   private int totalCoolingTicks;
   private int coolingEfficiency;
   private int checkStructureTicks;
   public float oldClientOverheatAmplitude;
   public float clientOverheatAmplitude;
   public int oldClientAnimationTicks;
   public int clientAnimationTicks;

   public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state) {
      super(ESBlockEntities.ALLOY_FURNACE.get(), pos, state);
      this.quickCheck = RecipeManager.createCheck(ESRecipes.ALLOY.get());
   }

   public static int getTotalOverheatTicks() {
      return ESConfig.INSTANCE.itemsConfig.alloyFurnace.totalOverheatTicks();
   }

   public static int getOverheatAnimationThreshold() {
      return getTotalOverheatTicks() * 5 / 6;
   }

   public boolean isLit() {
      return this.litTicks > 0;
   }

   public boolean isCooling() {
      return this.coolingTicks > 0;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, AlloyFurnaceBlockEntity entity) {
      if (!level.isClientSide) {
         boolean oldLit = entity.litTicks > 0;
         boolean oldCooling = entity.coolingTicks > 0;
         boolean oldShowOverheatAnimation = entity.overheatTicks >= getOverheatAnimationThreshold();
         boolean changed = false;
         if (entity.litTicks > 0) {
            entity.litTicks--;
         }

         if (entity.coolingTicks > 0) {
            entity.coolingTicks--;
         }

         if (entity.litTicks <= 0) {
            entity.totalLitTicks = 0;
         }

         if (entity.coolingTicks <= 0) {
            entity.totalCoolingTicks = 0;
            entity.coolingEfficiency = 0;
         }

         List<ItemStack> ingredients = entity.getIngredientItems();
         RecipeHolder<AlloyRecipe> recipeHolder = (RecipeHolder<AlloyRecipe>)entity.quickCheck
            .getRecipeFor(CraftingInput.of(3, 3, ingredients), level)
            .orElse(null);
         if (entity.canBurn() && entity.litTicks <= 0) {
            ItemStack fuel = entity.getItem(9);
            if (!fuel.isEmpty() && isFuel(fuel)) {
               entity.totalLitTicks = ESPlatform.INSTANCE.getBurnTime(fuel, ESRecipes.ALLOY.get());
               entity.litTicks = entity.totalLitTicks;
               ItemStack originalFuel = fuel.copy();
               fuel.shrink(1);
               if (ESPlatform.INSTANCE.hasCraftingRemainingItem(originalFuel)) {
                  ESPlatform.INSTANCE.getCraftingRemainingItem(originalFuel).ifPresent(remainingx -> {
                     if (!fuel.isEmpty() && !ItemStack.isSameItemSameComponents(fuel, remainingx)) {
                        Block.popResource(level, pos, remainingx.copy());
                     } else {
                        int remainingCountx = fuel.getCount() + remainingx.getCount() - remainingx.getMaxStackSize();
                        entity.setItem(9, remainingx.copyWithCount(Math.min(fuel.getCount() + remainingx.getCount(), remainingx.getMaxStackSize())));
                        if (remainingCountx > 0) {
                           Block.popResource(level, pos, remainingx.copyWithCount(remainingCountx));
                        }
                     }
                  });
               }

               changed = true;
            }
         }

         if (entity.overheatTicks > 0 && entity.coolingTicks <= 0) {
            ItemStack coolingItem = entity.getItem(10);
            if (!coolingItem.isEmpty() && isCoolingItem(coolingItem)) {
               entity.totalCoolingTicks = AlloyFurnaceBlock.getCoolDuration(coolingItem.getItem());
               entity.coolingTicks = entity.totalCoolingTicks;
               entity.coolingEfficiency = AlloyFurnaceBlock.getCoolEfficiency(coolingItem.getItem());
               coolingItem.shrink(1);
               if (ESPlatform.INSTANCE.hasCraftingRemainingItem(coolingItem)) {
                  ESPlatform.INSTANCE.getCraftingRemainingItem(coolingItem).ifPresent(remainingx -> {
                     if (!coolingItem.isEmpty() && !ItemStack.isSameItemSameComponents(coolingItem, remainingx)) {
                        Block.popResource(level, pos, remainingx.copy());
                     } else {
                        int remainingCountx = coolingItem.getCount() + remainingx.getCount() - remainingx.getMaxStackSize();
                        entity.setItem(10, remainingx.copyWithCount(Math.min(coolingItem.getCount() + remainingx.getCount(), remainingx.getMaxStackSize())));
                        if (remainingCountx > 0) {
                           Block.popResource(level, pos, remainingx.copyWithCount(remainingCountx));
                        }
                     }
                  });
               }

               changed = true;
            }
         }

         entity.burnTicks = entity.burnTicks + (entity.litTicks > 0 && entity.canBurn() ? 1 : -1);
         entity.burnTicks = Mth.clamp(entity.burnTicks, 0, entity.totalBurnTicks);
         if (entity.litTicks > 0 && (entity.coolingTicks <= 0 || entity.litTicks % entity.coolingEfficiency == 0)) {
            entity.overheatTicks++;
         }

         if (entity.litTicks <= 0) {
            entity.overheatTicks = entity.overheatTicks - (1 + (entity.coolingTicks > 0 ? entity.coolingEfficiency : 0));
         }

         entity.overheatTicks = Mth.clamp(entity.overheatTicks, 0, getTotalOverheatTicks());
         if (entity.canBurn() && entity.burnTicks == entity.totalBurnTicks && recipeHolder != null) {
            AlloyRecipe recipe = (AlloyRecipe)recipeHolder.value();

            for (int i = 0; i < Math.min(recipe.results().size(), 3); i++) {
               AlloyRecipe.Result result = (AlloyRecipe.Result)recipe.results().get(i);
               ItemStack stack = result.getResultItem(level.getRandom());
               if (stack.isEmpty()) {
                  break;
               }

               ItemStack existingResult = entity.getItem(11 + i);
               entity.setItem(11 + i, stack.copyWithCount(stack.getCount() + existingResult.getCount()));
            }

            Positioned craftingInput = CraftingInput.ofPositioned(3, 3, ingredients);
            NonNullList<ItemStack> remaining = ((AlloyRecipe)recipeHolder.value()).getRemainingItems(craftingInput.input());

            for (int y = 0; y < craftingInput.input().height(); y++) {
               for (int x = 0; x < craftingInput.input().width(); x++) {
                  int index = x + craftingInput.left() + (y + craftingInput.top()) * 3;
                  ItemStack craftItem = entity.getItem(0 + index);
                  ItemStack remainingItem = (ItemStack)remaining.get(x + y * craftingInput.input().width());
                  if (!craftItem.isEmpty()) {
                     craftItem.shrink(1);
                     entity.setItem(0 + index, craftItem);
                  }

                  if (!remainingItem.isEmpty()) {
                     if (!craftItem.isEmpty() && !ItemStack.isSameItemSameComponents(craftItem, remainingItem)) {
                        Block.popResource(level, pos, remainingItem.copy());
                     } else {
                        int remainingCount = craftItem.getCount() + remainingItem.getCount() - remainingItem.getMaxStackSize();
                        entity.setItem(
                           0 + index, remainingItem.copyWithCount(Math.min(craftItem.getCount() + remainingItem.getCount(), remainingItem.getMaxStackSize()))
                        );
                        if (remainingCount > 0) {
                           Block.popResource(level, pos, remainingItem.copyWithCount(remainingCount));
                        }
                     }
                  }
               }
            }

            entity.burnTicks = 0;
            changed = true;
         }

         if (entity.litTicks > 0 != oldLit
            || entity.coolingTicks > 0 != oldCooling
            || entity.overheatTicks >= getOverheatAnimationThreshold() != oldShowOverheatAnimation) {
            level.sendBlockUpdated(pos, state, state, 3);
            changed = true;
         }

         if (changed) {
            entity.setChanged();
         }

         entity.checkStructureTicks++;
         if (entity.checkStructureTicks > 20 && state.getBlock() instanceof AlloyFurnaceBlock block) {
            block.checkStructure(level, pos);
            entity.checkStructureTicks = 0;
         }

         if (entity.overheatTicks >= getTotalOverheatTicks()) {
            level.destroyBlock(pos, false);
            if (level instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(
                  ESExplosionParticleOptions.LAVA,
                  pos.getX() + level.getRandom().nextFloat(),
                  pos.getY() + level.getRandom().nextFloat() + 1.5,
                  pos.getZ() + level.getRandom().nextFloat(),
                  20,
                  1.5,
                  1.5,
                  1.5,
                  0.0
               );
               serverLevel.sendParticles(
                  (SimpleParticleType)ESParticles.SMOKE_TRAIL.get(),
                  pos.getX() + level.getRandom().nextFloat(),
                  pos.getY() + level.getRandom().nextFloat(),
                  pos.getZ() + level.getRandom().nextFloat(),
                  20,
                  0.0,
                  0.0,
                  0.0,
                  0.5
               );
            }

            level.explode(
               null,
               null,
               null,
               pos.getX() + 0.5,
               pos.getY() + 0.5,
               pos.getZ() + 0.5,
               ESConfig.INSTANCE.itemsConfig.alloyFurnace.explosionRadius(),
               true,
               ExplosionInteraction.BLOCK,
               ESExplosionParticleOptions.LAVA,
               ESExplosionParticleOptions.LAVA,
               SoundEvents.GENERIC_EXPLODE
            );
         }
      } else {
         entity.oldClientOverheatAmplitude = entity.clientOverheatAmplitude;
         entity.clientOverheatAmplitude = entity.clientOverheatAmplitude + (entity.overheatTicks >= getOverheatAnimationThreshold() ? 0.003F : -0.003F);
         entity.clientOverheatAmplitude = Mth.clamp(entity.clientOverheatAmplitude, 0.0F, 1.0F);
         entity.oldClientAnimationTicks = entity.clientAnimationTicks++;
         if (level.getRandom().nextDouble() < 0.1) {
            level.playLocalSound(pos.getX() + 0.5, pos.getY(), pos.getX() + 0.5, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         }
      }
   }

   private List<ItemStack> getIngredientItems() {
      return this.getItems().subList(0, 9);
   }

   private boolean canBurn() {
      if (this.level == null) {
         return false;
      }

      Optional<RecipeHolder<AlloyRecipe>> recipeHolder = this.quickCheck.getRecipeFor(CraftingInput.of(3, 3, this.getIngredientItems()), this.level);
      if (this.getIngredientItems().stream().anyMatch(stackx -> !stackx.isEmpty()) && recipeHolder.isPresent()) {
         AlloyRecipe recipe = (AlloyRecipe)recipeHolder.get().value();

         for (int i = 0; i < Math.min(recipe.results().size(), 3); i++) {
            AlloyRecipe.Result result = (AlloyRecipe.Result)recipe.results().get(i);
            ItemStack stack = result.getMaxResultItem();
            if (stack.isEmpty()) {
               return false;
            }

            ItemStack existingResult = this.getItem(11 + i);
            if (!existingResult.isEmpty() && !ItemStack.isSameItemSameComponents(existingResult, stack)) {
               return false;
            }

            if (existingResult.getCount() + stack.getCount() > stack.getMaxStackSize()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isFuel(ItemStack stack) {
      return FurnaceBlockEntity.isFuel(stack);
   }

   public static boolean isCoolingItem(ItemStack stack) {
      return AlloyFurnaceBlock.getCoolingItem(stack.getItem()) != null;
   }

   public void setItem(int index, ItemStack stack) {
      ItemStack original = (ItemStack)this.items.get(index);
      boolean unchanged = !stack.isEmpty() && ItemStack.isSameItemSameComponents(original, stack);
      if (this.level != null && index >= 0 && index < 9 && !unchanged) {
         List<ItemStack> ingredients = this.getItems().subList(0, 9);
         ingredients.set(index - 0, stack);
         this.quickCheck
            .getRecipeFor(CraftingInput.of(3, 3, ingredients), this.level)
            .ifPresentOrElse(holder -> this.totalBurnTicks = ((AlloyRecipe)holder.value()).burnTime(), () -> this.totalBurnTicks = 0);
         this.burnTicks = 0;
         this.setChanged();
      }

      super.setItem(index, stack);
   }

   public boolean canPlaceItem(int index, ItemStack stack) {
      if (index == 9 && !isFuel(stack)) {
         return false;
      } else {
         return index == 10 && !isCoolingItem(stack) ? false : super.canPlaceItem(index, stack);
      }
   }

   public int getContainerSize() {
      return 14;
   }

   public void loadAdditional(CompoundTag compoundTag, Provider provider) {
      super.loadAdditional(compoundTag, provider);
      ContainerHelper.loadAllItems(compoundTag, this.items, provider);
      this.litTicks = compoundTag.getShort("lit_ticks");
      this.totalLitTicks = compoundTag.getShort("total_lit_ticks");
      this.burnTicks = compoundTag.getShort("burn_ticks");
      this.totalBurnTicks = compoundTag.getShort("total_burn_ticks");
      this.overheatTicks = compoundTag.getShort("overheat_ticks");
      this.coolingTicks = compoundTag.getShort("cooling_ticks");
      this.totalCoolingTicks = compoundTag.getShort("total_cooling_ticks");
      this.coolingEfficiency = compoundTag.getShort("cooling_efficiency");
   }

   protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      ContainerHelper.saveAllItems(compoundTag, this.items, provider);
      compoundTag.putShort("lit_ticks", (short)this.litTicks);
      compoundTag.putShort("total_lit_ticks", (short)this.totalLitTicks);
      compoundTag.putShort("burn_ticks", (short)this.burnTicks);
      compoundTag.putShort("total_burn_ticks", (short)this.totalBurnTicks);
      compoundTag.putShort("overheat_ticks", (short)this.overheatTicks);
      compoundTag.putShort("cooling_ticks", (short)this.coolingTicks);
      compoundTag.putShort("total_cooling_ticks", (short)this.totalCoolingTicks);
      compoundTag.putShort("cooling_efficiency", (short)this.coolingEfficiency);
   }

   @Nullable
   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider provider) {
      return this.saveCustomOnly(provider);
   }

   protected Component getDefaultName() {
      return Component.translatable("container.eternal_starlight.alloy_furnace");
   }

   protected NonNullList<ItemStack> getItems() {
      return this.items;
   }

   protected void setItems(NonNullList<ItemStack> items) {
      this.items = items;
   }

   protected AbstractContainerMenu createMenu(int id, Inventory player) {
      return new AlloyFurnaceMenu(id, player.player.getInventory(), this, this.dataAccess);
   }

   public int[] getSlotsForFace(Direction side) {
      if (side == Direction.DOWN) {
         return SLOTS_FOR_DOWN;
      } else if (side == Direction.UP) {
         return SLOTS_FOR_UP;
      } else {
         return side.getAxis() == ((Direction)this.getBlockState().getValue(AlloyFurnaceBlock.FACING)).getAxis() ? SLOTS_FOR_FRONT_AND_BACK : SLOTS_FOR_SIDES;
      }
   }

   public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
      if (index != 9 && index != 10 && this.level != null) {
         Optional<RecipeHolder<AlloyRecipe>> recipeHolder = this.quickCheck.getRecipeFor(CraftingInput.of(3, 3, this.getIngredientItems()), this.level);
         return !recipeHolder.isPresent() ? this.canPlaceItem(index, stack) : !this.getItem(index).isEmpty() && this.canPlaceItem(index, stack);
      } else {
         return this.canPlaceItem(index, stack);
      }
   }

   public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
      return true;
   }
}
