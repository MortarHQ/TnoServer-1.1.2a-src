package nadiendev.constructionwand.wand.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.api.IWandSupplier;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.wand.undo.DestroySnapshot;
import nadiendev.constructionwand.wand.undo.ISnapshot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ActionDestruction implements IWandAction {
   public static final ThreadLocal<ActionDestruction.VoidSackDropContext> ACTIVE_CONTEXT = new ThreadLocal<>();

   @Override
   public int getLimit(ItemStack wand) {
      return ConfigServer.getWandProperties(wand.getItem()).getDestruction();
   }

   @Nonnull
   @Override
   public List<ISnapshot> getSnapshots(
      Level world, Player player, BlockHitResult rayTraceResult, ItemStack wand, WandOptions options, IWandSupplier supplier, int limit
   ) {
      LinkedList<ISnapshot> destroySnapshots = new LinkedList<>();
      LinkedList<BlockPos> candidates = new LinkedList<>();
      HashSet<BlockPos> allCandidates = new HashSet<>();
      Direction breakFace = rayTraceResult.getDirection();
      BlockPos startingPoint = rayTraceResult.getBlockPos();
      BlockState targetBlock = world.getBlockState(rayTraceResult.getBlockPos());
      if (breakFace != Direction.UP && breakFace != Direction.DOWN) {
         if (options.testLock(WandOptions.LOCK.HORIZONTAL) || options.testLock(WandOptions.LOCK.VERTICAL)) {
            candidates.add(startingPoint);
         }
      } else if (options.testLock(WandOptions.LOCK.NORTHSOUTH) || options.testLock(WandOptions.LOCK.EASTWEST)) {
         candidates.add(startingPoint);
      }

      while (!candidates.isEmpty() && destroySnapshots.size() < limit) {
         BlockPos currentCandidate = candidates.removeFirst();
         if (WandUtil.isBlockPermeable(world, currentCandidate.offset(breakFace.getNormal()))) {
            try {
               BlockState candidateBlock = world.getBlockState(currentCandidate);
               if (options.matchBlocks(targetBlock.getBlock(), candidateBlock.getBlock()) && allCandidates.add(currentCandidate)) {
                  DestroySnapshot snapshot = DestroySnapshot.get(world, player, currentCandidate);
                  if (snapshot != null) {
                     destroySnapshots.add(snapshot);
                     switch (breakFace) {
                        case DOWN:
                        case UP:
                           if (options.testLock(WandOptions.LOCK.NORTHSOUTH)) {
                              candidates.add(currentCandidate.offset(Direction.NORTH.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.SOUTH.getNormal()));
                           }

                           if (options.testLock(WandOptions.LOCK.EASTWEST)) {
                              candidates.add(currentCandidate.offset(Direction.EAST.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.WEST.getNormal()));
                           }

                           if (options.testLock(WandOptions.LOCK.NORTHSOUTH) && options.testLock(WandOptions.LOCK.EASTWEST)) {
                              candidates.add(currentCandidate.offset(Direction.NORTH.getNormal()).offset(Direction.EAST.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.NORTH.getNormal()).offset(Direction.WEST.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.SOUTH.getNormal()).offset(Direction.EAST.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.SOUTH.getNormal()).offset(Direction.WEST.getNormal()));
                           }
                           break;
                        case NORTH:
                        case SOUTH:
                           if (options.testLock(WandOptions.LOCK.HORIZONTAL)) {
                              candidates.add(currentCandidate.offset(Direction.EAST.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.WEST.getNormal()));
                           }

                           if (options.testLock(WandOptions.LOCK.VERTICAL)) {
                              candidates.add(currentCandidate.offset(Direction.UP.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.DOWN.getNormal()));
                           }

                           if (options.testLock(WandOptions.LOCK.HORIZONTAL) && options.testLock(WandOptions.LOCK.VERTICAL)) {
                              candidates.add(currentCandidate.offset(Direction.UP.getNormal()).offset(Direction.EAST.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.UP.getNormal()).offset(Direction.WEST.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.DOWN.getNormal()).offset(Direction.EAST.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.DOWN.getNormal()).offset(Direction.WEST.getNormal()));
                           }
                           break;
                        case EAST:
                        case WEST:
                           if (options.testLock(WandOptions.LOCK.HORIZONTAL)) {
                              candidates.add(currentCandidate.offset(Direction.NORTH.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.SOUTH.getNormal()));
                           }

                           if (options.testLock(WandOptions.LOCK.VERTICAL)) {
                              candidates.add(currentCandidate.offset(Direction.UP.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.DOWN.getNormal()));
                           }

                           if (options.testLock(WandOptions.LOCK.HORIZONTAL) && options.testLock(WandOptions.LOCK.VERTICAL)) {
                              candidates.add(currentCandidate.offset(Direction.UP.getNormal()).offset(Direction.NORTH.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.UP.getNormal()).offset(Direction.SOUTH.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.DOWN.getNormal()).offset(Direction.NORTH.getNormal()));
                              candidates.add(currentCandidate.offset(Direction.DOWN.getNormal()).offset(Direction.SOUTH.getNormal()));
                           }
                     }
                  }
               }
            } catch (Exception var19) {
            }
         }
      }

      if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
         ItemStack voidSack = findSack(serverPlayer);
         if (!voidSack.isEmpty()) {
            List<ISnapshot> wrapped = new ArrayList<>(destroySnapshots.size());

            for (ISnapshot s : destroySnapshots) {
               wrapped.add(new ActionDestruction.VoidSackCapturingSnapshot(s, (ServerLevel)world, voidSack));
            }

            return wrapped;
         }
      }

      return destroySnapshots;
   }

   public static ItemStack findSack(Player player) {
      ItemStack main = player.getMainHandItem();
      if (main.getItem() instanceof ItemVoidSack) {
         return main;
      }

      ItemStack off = player.getOffhandItem();
      if (off.getItem() instanceof ItemVoidSack) {
         return off;
      }

      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack s = player.getInventory().getItem(i);
         if (s.getItem() instanceof ItemVoidSack) {
            return s;
         }
      }

      return ItemStack.EMPTY;
   }

   @Nonnull
   @Override
   public List<ISnapshot> getSnapshotsFromAir(
      Level world, Player player, BlockHitResult rayTraceResult, ItemStack wand, WandOptions options, IWandSupplier supplier, int limit
   ) {
      return new ArrayList<>();
   }

   private static class VoidSackCapturingSnapshot implements ISnapshot {
      private final ISnapshot delegate;
      private final ServerLevel level;
      private final ItemStack sack;

      VoidSackCapturingSnapshot(ISnapshot delegate, ServerLevel level, ItemStack sack) {
         this.delegate = delegate;
         this.level = level;
         this.sack = sack;
      }

      @Override
      public BlockPos getPos() {
         return this.delegate.getPos();
      }

      @Override
      public BlockState getBlockState() {
         return this.delegate.getBlockState();
      }

      @Override
      public ItemStack getRequiredItems() {
         return this.delegate.getRequiredItems();
      }

      @Override
      public boolean canRestore(Level w, Player p) {
         return this.delegate.canRestore(w, p);
      }

      @Override
      public void forceRestore(Level w) {
         this.delegate.forceRestore(w);
      }

      @Override
      public boolean restore(Level w, Player p) {
         return this.delegate.restore(w, p);
      }

      @Override
      public boolean execute(Level world, Player player, BlockHitResult ray) {
         ActionDestruction.ACTIVE_CONTEXT.set(new ActionDestruction.VoidSackDropContext(this.delegate.getPos(), this.level, this.sack));

         try {
            return this.delegate.execute(world, player, ray);
         } finally {
            ActionDestruction.ACTIVE_CONTEXT.remove();
         }
      }
   }

   public record VoidSackDropContext(BlockPos pos, ServerLevel level, ItemStack sack) {
   }
}
