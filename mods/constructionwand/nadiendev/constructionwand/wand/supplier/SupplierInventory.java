package nadiendev.constructionwand.wand.supplier;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandSupplier;
import nadiendev.constructionwand.basics.ReplacementRegistry;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.basics.pool.IPool;
import nadiendev.constructionwand.basics.pool.OrderedPool;
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.containers.ContainerTrace;
import nadiendev.constructionwand.integrations.curios.CuriosIntegration;
import nadiendev.constructionwand.wand.undo.PlaceSnapshot;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SupplierInventory implements IWandSupplier {
   protected final Player player;
   protected final WandOptions options;
   protected HashMap<BlockItem, Integer> itemCounts;
   protected IPool<BlockItem> itemPool;

   public SupplierInventory(Player player, WandOptions options) {
      this.player = player;
      this.options = options;
   }

   @Override
   public void getSupply(@Nullable BlockItem target) {
      this.itemCounts = new LinkedHashMap<>();
      ItemStack offhandStack = this.player.getItemInHand(InteractionHand.OFF_HAND);
      this.itemPool = new OrderedPool<>();
      if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof BlockItem blockItem) {
         this.addBlockItem(blockItem);
      } else if (target != null && target != Items.AIR) {
         this.addBlockItem(target);
         if (this.options.match.get() != WandOptions.MATCH.EXACT) {
            for (Item it : ReplacementRegistry.getMatchingSet(target)) {
               if (it instanceof BlockItem blockItem) {
                  this.addBlockItem(blockItem);
               }
            }
         }
      }
   }

   protected void addBlockItem(BlockItem item) {
      int count = WandUtil.countItem(this.player, item);
      if (this.hasContainerWithItem(item)) {
         count = Integer.MAX_VALUE;
      } else {
         count += this.countItemInContainers(item);
      }

      if (count > 0) {
         this.itemCounts.put(item, count);
         this.itemPool.add(item);
      }
   }

   private List<ItemStack> getCuriosInv(Player player) {
      return CuriosIntegration.getCurioStacks(player);
   }

   private boolean hasContainerWithItem(BlockItem item) {
      ContainerManager containerManager = ConstructionWand.instance.containerManager;
      ItemStack itemStack = new ItemStack(item);

      for (ItemStack inv : WandUtil.getHotbarWithOffhand(this.player)) {
         if (containerManager.hasHandler(this.player, itemStack, inv)) {
            return true;
         }
      }

      for (ItemStack inv : WandUtil.getMainInv(this.player)) {
         if (containerManager.hasHandler(this.player, itemStack, inv)) {
            return true;
         }
      }

      for (ItemStack inv : this.getCuriosInv(this.player)) {
         if (containerManager.hasHandler(this.player, itemStack, inv)) {
            return true;
         }
      }

      return false;
   }

   private int countItemInContainers(BlockItem item) {
      if (!(this.player instanceof ServerPlayer sp)) {
         return 0;
      } else {
         ContainerManager var9 = ConstructionWand.instance.containerManager;
         ContainerTrace trace = new ContainerTrace(sp);
         ItemStack itemStack = new ItemStack(item);
         int total = 0;

         for (ItemStack inv : WandUtil.getHotbarWithOffhand(this.player)) {
            total += var9.countItems(this.player, trace, itemStack, inv);
         }

         for (ItemStack inv : WandUtil.getMainInv(this.player)) {
            total += var9.countItems(this.player, trace, itemStack, inv);
         }

         for (ItemStack inv : this.getCuriosInv(this.player)) {
            total += var9.countItems(this.player, trace, itemStack, inv);
         }

         return total;
      }
   }

   @Nullable
   @Override
   public PlaceSnapshot getPlaceSnapshot(Level world, BlockPos pos, BlockHitResult rayTraceResult, @Nullable BlockState supportingBlock) {
      if (!WandUtil.isPositionPlaceable(world, this.player, pos, this.options.replace.get())) {
         return null;
      }

      this.itemPool.reset();

      while (true) {
         BlockItem item = this.itemPool.draw();
         if (item == null) {
            return null;
         }

         int count = this.itemCounts.get(item);
         if (count != 0) {
            PlaceSnapshot placeSnapshot = PlaceSnapshot.get(world, this.player, rayTraceResult, pos, item, supportingBlock, this.options);
            if (placeSnapshot != null) {
               int newCount = count == Integer.MAX_VALUE ? Integer.MAX_VALUE : count - 1;
               this.itemCounts.put(item, newCount);
               if (newCount == 0) {
                  this.itemPool.remove(item);
               }

               return placeSnapshot;
            }
         }
      }
   }

   @Override
   public int takeItemStack(ItemStack stack) {
      int count = stack.getCount();
      Item item = stack.getItem();
      if (this.player.getInventory().items == null) {
         return count;
      }

      if (this.player.isCreative()) {
         return 0;
      }

      List<ItemStack> hotbar = WandUtil.getHotbarWithOffhand(this.player);
      List<ItemStack> mainInv = WandUtil.getMainInv(this.player);
      List<ItemStack> curios = this.getCuriosInv(this.player);
      count = this.takeItemsInvList(count, item, mainInv, false);
      count = this.takeItemsInvList(count, item, mainInv, true);
      count = this.takeItemsInvList(count, item, hotbar, true);
      count = this.takeItemsInvList(count, item, hotbar, false);
      return this.takeItemsInvList(count, item, curios, true);
   }

   private int takeItemsInvList(int count, Item item, List<ItemStack> inv, boolean container) {
      if (!(this.player instanceof ServerPlayer sp)) {
         return count;
      } else {
         ContainerManager var11 = ConstructionWand.instance.containerManager;
         ContainerTrace trace = new ContainerTrace(sp);

         for (ItemStack stack : inv) {
            if (count == 0) {
               break;
            }

            if (container) {
               int prevCount = count;
               count = var11.useItems(this.player, trace, new ItemStack(item), stack, count);
               if (count < prevCount) {
                  this.player.getInventory().setChanged();
               }
            }

            if (!container && WandUtil.stackEquals(stack, item)) {
               int toTake = Math.min(count, stack.getCount());
               stack.shrink(toTake);
               count -= toTake;
               this.player.getInventory().setChanged();
            }
         }

         return count;
      }
   }
}
