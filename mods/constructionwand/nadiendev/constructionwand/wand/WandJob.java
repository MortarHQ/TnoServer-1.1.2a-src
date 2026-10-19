package nadiendev.constructionwand.wand;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.api.IWandSupplier;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.ModStats;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.ModItems;
import nadiendev.constructionwand.items.wand.ItemWand;
import nadiendev.constructionwand.wand.supplier.SupplierInventory;
import nadiendev.constructionwand.wand.supplier.SupplierRandom;
import nadiendev.constructionwand.wand.undo.ISnapshot;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;

public class WandJob {
   public final Player player;
   public final Level world;
   public final BlockHitResult rayTraceResult;
   public final WandOptions options;
   public final ItemStack wand;
   public final ItemWand wandItem;
   private final IWandAction wandAction;
   private final IWandSupplier wandSupplier;
   private List<ISnapshot> placeSnapshots;

   public WandJob(Player player, Level world, BlockHitResult rayTraceResult, ItemStack wand) {
      this.player = player;
      this.world = world;
      this.rayTraceResult = rayTraceResult;
      this.placeSnapshots = new ArrayList<>();
      this.wand = wand;
      this.wandItem = (ItemWand)wand.getItem();
      this.options = new WandOptions(wand);
      this.wandSupplier = this.options.random.get() ? new SupplierRandom(player, this.options) : new SupplierInventory(player, this.options);
      this.wandAction = this.options.cores.get().getWandAction();
      this.wandSupplier.getSupply(getTargetItem(world, rayTraceResult));
   }

   @Nullable
   private static BlockItem getTargetItem(Level world, BlockHitResult rayTraceResult) {
      Item tgitem = world.getBlockState(rayTraceResult.getBlockPos()).getBlock().asItem();
      return !(tgitem instanceof BlockItem) ? null : (BlockItem)tgitem;
   }

   public void getSnapshots() {
      int limit;
      if (this.player.isCreative() && this.wandItem == ModItems.WAND_INFINITY.get()) {
         limit = (Integer)ConfigServer.LIMIT_CREATIVE.get();
      } else {
         limit = Math.min(this.wandItem.remainingDurability(this.wand), this.wandAction.getLimit(this.wand));
      }

      if (this.rayTraceResult.getType() == Type.BLOCK) {
         this.placeSnapshots = this.wandAction.getSnapshots(this.world, this.player, this.rayTraceResult, this.wand, this.options, this.wandSupplier, limit);
      } else {
         this.placeSnapshots = this.wandAction
            .getSnapshotsFromAir(this.world, this.player, this.rayTraceResult, this.wand, this.options, this.wandSupplier, limit);
      }
   }

   public Set<BlockPos> getBlockPositions() {
      return this.placeSnapshots.stream().map(ISnapshot::getPos).collect(Collectors.toSet());
   }

   public int blockCount() {
      return this.placeSnapshots.size();
   }

   public boolean doIt() {
      ArrayList<ISnapshot> executed = new ArrayList<>();

      for (ISnapshot snapshot : this.placeSnapshots) {
         if (this.wand.isEmpty() || this.wandItem.remainingDurability(this.wand) == 0) {
            break;
         }

         if (snapshot.execute(this.world, this.player, this.rayTraceResult)) {
            if (this.player.isCreative()) {
               executed.add(snapshot);
            } else if (this.wandSupplier.takeItemStack(snapshot.getRequiredItems()) == 0) {
               executed.add(snapshot);
               this.wand.hurtAndBreak(1, this.player, EquipmentSlot.MAINHAND);
            } else {
               ConstructionWand.LOGGER.info("Item could not be taken. Remove block: {}", snapshot.getBlockState().getBlock().toString());
               snapshot.forceRestore(this.world);
            }

            this.player.awardStat(ModStats.USE_WAND_STAT.get());
         }
      }

      this.placeSnapshots = executed;
      if (!this.placeSnapshots.isEmpty()) {
         SoundType sound = this.placeSnapshots.get(0).getBlockState().getSoundType();
         this.world.playSound(null, this.player.blockPosition(), sound.getPlaceSound(), SoundSource.BLOCKS, sound.volume, sound.pitch);
         ConstructionWand.instance.undoHistory.add(this.player, this.world, this.placeSnapshots);
      }

      return !this.placeSnapshots.isEmpty();
   }
}
