package com.mortal_cultivation.block.entity;

import com.mortal_cultivation.gui.menu.AncientScholarTableMenu;
import com.mortal_cultivation.init.BlockEntityRegistry;
import com.mortal_cultivation.item.AncientBookPuzzleData;
import com.mortal_cultivation.registry.item.CultivationItems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class AncientScholarTableBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider {
   public static final int ANALYSIS_INPUT_SLOT = 0;
   public static final int MAIN_OUTPUT_SLOT = 1;
   public static final int DECIPHER_BOOK_SLOT = 2;
   public static final int TALISMAN_INPUT_SLOT_START = 3;
   public static final int TALISMAN_INPUT_SLOT_COUNT = 7;
   public static final int TALISMAN_TOOL_SLOT = 10;
   public static final int SLOT_COUNT = 11;
   public static final int PAGE_PROGRESS_TICKS = 600;
   public static final int MAX_PROGRESS_TICKS = 24000;
   public static final int DECIPHER_GRID_COLUMNS = 6;
   public static final int DECIPHER_GRID_ROWS = 4;
   public static final int DECIPHER_CELL_COUNT = 24;
   public static final int DECIPHER_SOURCE_CELL = 8;
   public static final int DECIPHER_ROTATION_DATA_START = 3;
   public static final int DECIPHER_SOLUTION_MASK_DATA_START = 27;
   public static final int DECIPHER_COMPLETED_DATA_INDEX = 51;
   public static final int DECIPHER_ACTIVE_DATA_INDEX = 52;
   public static final int DECIPHER_STATUS_DATA_INDEX = 53;
   public static final int DECIPHER_DATA_COUNT = 54;
   public static final int DECIPHER_RUNE_COST_MIN = 3;
   public static final int DECIPHER_RUNE_COST_MAX = 8;
   public static final int DECIPHER_RUNE_SEARCH_RADIUS = 8;
   public static final int DECIPHER_COMPLETION_DELAY_TICKS = 24;
   public static final int DECIPHER_STATUS_NONE = 0;
   public static final int DECIPHER_STATUS_INSUFFICIENT_RUNES = 1;
   private static final int DECIPHER_STATUS_DURATION_TICKS = 60;
   private static final int DECIPHER_LAYOUT_GENERATION_ATTEMPTS = 64;
   private static final int DECIPHER_SCRAMBLE_ATTEMPTS = 64;
   private static final int DECIPHER_MIN_SCRAMBLE_CLICKS = 16;
   private static final int[] DECIPHER_DIRECTIONS = new int[]{1, 2, 4, 8};
   public static final int DECIPHER_ROTATE_COUNTERCLOCKWISE_BUTTON_BASE = 16;
   public static final int DECIPHER_ROTATE_CLOCKWISE_BUTTON_BASE = 40;
   private static final int[] LEGACY_DECIPHER_SOLUTION_MASKS = new int[]{4, 6, 8, 4, 2, 12, 7, 11, 14, 11, 14, 13, 7, 8, 7, 8, 5, 1, 3, 8, 3, 8, 3, 8};
   private static final String DECIPHER_SOLUTION_MASKS_TAG = "decipherSolutionMasks";
   private static final String DECIPHER_ROTATIONS_TAG = "decipherRotations";
   private static final String DECIPHER_INITIALIZED_TAG = "decipherInitialized";
   private static final String DECIPHER_COMPLETED_TAG = "decipherCompleted";
   private static final String DECIPHER_ACTIVE_TAG = "decipherActive";
   private static final String DECIPHER_SOLVED_TICKS_TAG = "decipherSolvedTicks";
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final AncientScholarTableBlockEntity.TableItemStackHandler itemHandler = new AncientScholarTableBlockEntity.TableItemStackHandler();
   private final ContainerData data = new ContainerData() {
      public int get(int index) {
         if (index == 0) {
            return AncientScholarTableBlockEntity.this.progressTicks;
         } else if (index == 1) {
            return 24000;
         } else if (index == 2) {
            return AncientScholarTableBlockEntity.this.selectedPage;
         } else if (index >= 3 && index < 27) {
            return AncientScholarTableBlockEntity.this.decipherRotations[index - 3];
         } else if (index >= 27 && index < 51) {
            return AncientScholarTableBlockEntity.this.decipherSolutionMasks[index - 27];
         } else if (index == 51) {
            return AncientScholarTableBlockEntity.this.decipherCompleted ? 1 : 0;
         } else if (index == 52) {
            return AncientScholarTableBlockEntity.this.decipherActive ? 1 : 0;
         } else {
            return index == 53 ? AncientScholarTableBlockEntity.this.decipherStatus : 0;
         }
      }

      public void set(int index, int value) {
         if (index == 0) {
            AncientScholarTableBlockEntity.this.progressTicks = value;
         } else if (index == 2) {
            int page = AncientScholarTableBlockEntity.normalizeSelectedPage(value);
            if (AncientScholarTableBlockEntity.this.selectedPage != page) {
               AncientScholarTableBlockEntity.this.selectedPage = page;
               AncientScholarTableBlockEntity.this.setChanged();
            }
         } else if (index >= 3 && index < 27) {
            AncientScholarTableBlockEntity.this.decipherRotations[index - 3] = value & 3;
         } else if (index >= 27 && index < 51) {
            AncientScholarTableBlockEntity.this.decipherSolutionMasks[index - 27] = value & 15;
         } else if (index == 51) {
            AncientScholarTableBlockEntity.this.decipherCompleted = value != 0;
         } else if (index == 52) {
            AncientScholarTableBlockEntity.this.decipherActive = value != 0;
         } else if (index == 53) {
            AncientScholarTableBlockEntity.this.decipherStatus = value;
         }
      }

      public int getCount() {
         return 54;
      }
   };
   private int progressTicks;
   private int activePageTicks;
   private int selectedPage;
   private final int[] decipherSolutionMasks = new int[24];
   private final int[] decipherRotations = new int[24];
   private boolean decipherInitialized;
   private boolean decipherCompleted;
   private boolean decipherActive;
   private int decipherStatus;
   private int decipherStatusTicks;
   private int decipherSolvedTicks;
   private boolean loadingInventory;
   private boolean decipherDataSavePending;

   public AncientScholarTableBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)BlockEntityRegistry.ANCIENT_SCHOLAR_TABLE.get(), pos, state);
      this.resetDecipherSolutionMasks();
   }

   public ItemStackHandler getItemHandler() {
      return this.itemHandler;
   }

   public ContainerData getContainerData() {
      return this.data;
   }

   public boolean hasAncientBook() {
      return this.itemHandler.getStackInSlot(2).is((Item)CultivationItems.ANCIENT_BOOK.get());
   }

   public boolean isDecipherCompleted() {
      return this.decipherCompleted;
   }

   public boolean isDecipherActive() {
      return this.decipherActive;
   }

   public int getDecipherRotation(int cell) {
      return cell >= 0 && cell < 24 ? this.decipherRotations[cell] & 3 : 0;
   }

   public int getDecipherMask(int cell) {
      return cell >= 0 && cell < 24 ? rotateMask(this.decipherSolutionMasks[cell], this.getDecipherRotation(cell)) : 0;
   }

   public int getDecipherSolutionMask(int cell) {
      return cell >= 0 && cell < 24 ? this.decipherSolutionMasks[cell] : 0;
   }

   public static int getLegacyDecipherSolutionMask(int cell) {
      return cell >= 0 && cell < 24 ? LEGACY_DECIPHER_SOLUTION_MASKS[cell] : 0;
   }

   public static int rotateMask(int mask, int turns) {
      int rotated = mask & 15;

      for (int step = 0; step < (turns & 3); step++) {
         rotated = rotated << 1 & 15 | rotated >> 3 & 1;
      }

      return rotated;
   }

   public static int getConnectedDecipherCellCount(ItemStack ancientBook) {
      AncientBookPuzzleData.PuzzleData puzzle = AncientBookPuzzleData.readPuzzle(ancientBook, 24, LEGACY_DECIPHER_SOLUTION_MASKS);
      if (puzzle == null) {
         return 0;
      }

      int[] solutionMasks = puzzle.solutionMasks();
      int[] rotations = puzzle.rotations();
      int[] visibleMasks = new int[24];

      for (int cell = 0; cell < 24; cell++) {
         visibleMasks[cell] = rotateMask(solutionMasks[cell], rotations[cell]);
      }

      boolean[] visited = new boolean[24];
      int[] queue = new int[24];
      int head = 0;
      int tail = 0;
      visited[8] = true;
      queue[tail++] = 8;

      while (head < tail) {
         int cell = queue[head++];

         for (int direction : DECIPHER_DIRECTIONS) {
            if ((visibleMasks[cell] & direction) != 0) {
               int neighbor = getNeighborCell(cell, direction);
               if (neighbor >= 0 && !visited[neighbor] && (visibleMasks[neighbor] & oppositeDirection(direction)) != 0) {
                  visited[neighbor] = true;
                  queue[tail++] = neighbor;
               }
            }
         }
      }

      return tail;
   }

   public boolean isDecipherSolved() {
      return this.hasAncientBook() && this.decipherInitialized && !this.decipherCompleted && isSolved(this.decipherSolutionMasks, this.decipherRotations);
   }

   public static int decipherButtonForCell(int cell, int direction) {
      int buttonBase = direction < 0 ? 16 : 40;
      return buttonBase + cell;
   }

   public Component getDisplayName() {
      return Component.translatable("container.mortal_cultivation.ancient_scholar_table");
   }

   @Nullable
   public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
      return new AncientScholarTableMenu(id, inventory, this);
   }

   protected void saveAdditional(CompoundTag tag, Provider provider) {
      super.saveAdditional(tag, provider);
      tag.put("inventory", this.itemHandler.serializeNBT(provider));
      tag.putInt("progressTicks", this.progressTicks);
      tag.putInt("activePageTicks", this.activePageTicks);
      tag.putInt("selectedPage", this.selectedPage);
      tag.putIntArray("decipherSolutionMasks", this.decipherSolutionMasks);
      tag.putIntArray("decipherRotations", this.decipherRotations);
      tag.putBoolean("decipherInitialized", this.decipherInitialized);
      tag.putBoolean("decipherCompleted", this.decipherCompleted);
      tag.putBoolean("decipherActive", this.decipherActive);
      tag.putInt("decipherSolvedTicks", this.decipherSolvedTicks);
   }

   protected void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      if (tag.contains("inventory", 10)) {
         this.loadingInventory = true;

         try {
            this.itemHandler.deserializeNBT(provider, tag.getCompound("inventory"));
         } finally {
            this.loadingInventory = false;
         }
      }

      this.itemHandler.ensureSlotCount();
      this.progressTicks = Math.max(0, Math.min(tag.getInt("progressTicks"), 24000));
      this.activePageTicks = Math.max(0, Math.min(tag.getInt("activePageTicks"), 600));
      this.selectedPage = normalizeSelectedPage(tag.getInt("selectedPage"));
      this.resetDecipherSolutionMasks();
      Arrays.fill(this.decipherRotations, 0);
      if (tag.contains("decipherSolutionMasks", 11)) {
         int[] savedSolutionMasks = tag.getIntArray("decipherSolutionMasks");
         if (isValidSolutionLayout(savedSolutionMasks)) {
            System.arraycopy(savedSolutionMasks, 0, this.decipherSolutionMasks, 0, this.decipherSolutionMasks.length);
         }
      }

      boolean validDecipherRotations = false;
      if (tag.contains("decipherRotations", 11)) {
         int[] savedRotations = tag.getIntArray("decipherRotations");
         validDecipherRotations = savedRotations.length == 24;
         if (validDecipherRotations) {
            System.arraycopy(savedRotations, 0, this.decipherRotations, 0, this.decipherRotations.length);
         }
      }

      boolean legacyInitialized = validDecipherRotations && tag.getBoolean("decipherInitialized");
      boolean legacyCompleted = validDecipherRotations && tag.getBoolean("decipherCompleted");
      ItemStack decipherStack = this.itemHandler.getStackInSlot(2);
      AncientBookPuzzleData.PuzzleData storedPuzzle = this.hasAncientBook()
         ? AncientBookPuzzleData.readPuzzle(decipherStack, 24, LEGACY_DECIPHER_SOLUTION_MASKS)
         : null;
      if (storedPuzzle != null && isValidSolutionLayout(storedPuzzle.solutionMasks())) {
         System.arraycopy(storedPuzzle.solutionMasks(), 0, this.decipherSolutionMasks, 0, this.decipherSolutionMasks.length);
         int[] storedRotations = storedPuzzle.rotations();
         System.arraycopy(storedRotations, 0, this.decipherRotations, 0, this.decipherRotations.length);
         if (storedPuzzle.legacyFormat()) {
            AncientBookPuzzleData.writePuzzle(decipherStack, this.decipherSolutionMasks, this.decipherRotations);
            this.decipherDataSavePending = true;
         }

         this.decipherInitialized = true;
         this.decipherCompleted = false;
         this.decipherActive = true;
      } else if (this.hasAncientBook() && legacyInitialized) {
         AncientBookPuzzleData.writePuzzle(decipherStack, this.decipherSolutionMasks, this.decipherRotations);
         this.decipherDataSavePending = true;
         this.decipherInitialized = true;
         this.decipherCompleted = false;
         this.decipherActive = true;
      } else if (this.hasAncientBook()) {
         this.decipherInitialized = false;
         this.decipherCompleted = false;
         this.decipherActive = false;
      } else {
         boolean hasDecipherResult = !decipherStack.isEmpty();
         this.decipherInitialized = hasDecipherResult && legacyInitialized;
         this.decipherCompleted = hasDecipherResult && legacyCompleted;
         this.decipherActive = false;
         if (!hasDecipherResult) {
            this.resetDecipherSolutionMasks();
            Arrays.fill(this.decipherRotations, 0);
         }
      }

      this.decipherStatus = 0;
      this.decipherStatusTicks = 0;
      this.decipherSolvedTicks = this.hasAncientBook() && this.decipherInitialized && isSolved(this.decipherSolutionMasks, this.decipherRotations)
         ? Math.max(0, Math.min(tag.getInt("decipherSolvedTicks"), 24))
         : 0;
   }

   public void dropContents(Level level) {
      for (int slot = 0; slot < this.itemHandler.getSlots(); slot++) {
         ItemStack stack = this.itemHandler.extractItem(slot, this.itemHandler.getStackInSlot(slot).getCount(), false);
         if (!stack.isEmpty()) {
            Containers.dropItemStack(level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, stack);
         }
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState state, AncientScholarTableBlockEntity table) {
      if (!level.isClientSide) {
         boolean changed = false;
         if (table.decipherDataSavePending) {
            table.decipherDataSavePending = false;
            changed = true;
         }

         if (table.decipherStatusTicks > 0 && --table.decipherStatusTicks == 0) {
            table.decipherStatus = 0;
            changed = true;
         }

         if (table.itemHandler.getStackInSlot(2).isEmpty() && (table.decipherInitialized || table.decipherCompleted || table.hasDecipherRotation())) {
            table.resetDecipherPuzzle();
            changed = true;
         }

         if (table.hasAncientBook() && !table.decipherInitialized) {
            table.initializeDecipherPuzzle();
            changed = true;
         }

         if (table.hasAncientBook() && table.isDecipherSolved() && !table.decipherCompleted) {
            if (++table.decipherSolvedTicks >= 24) {
               changed = table.tryCreateDecodedManual() || changed;
            } else {
               changed = true;
            }
         } else if (table.decipherSolvedTicks != 0) {
            table.decipherSolvedTicks = 0;
            changed = true;
         }

         if (table.progressTicks >= 24000) {
            changed = table.tryCreateAncientBook() || changed;
         } else if (table.canOutputAncientBook()) {
            if (table.activePageTicks <= 0 && table.consumeTornPage()) {
               table.activePageTicks = 600;
               changed = true;
            }

            if (table.activePageTicks > 0) {
               table.activePageTicks--;
               table.progressTicks++;
               changed = true;
               if (table.progressTicks >= 24000) {
                  table.progressTicks = 24000;
                  table.activePageTicks = 0;
                  table.tryCreateAncientBook();
               }
            }
         }

         if (changed) {
            table.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
         }
      }
   }

   private boolean consumeTornPage() {
      if (this.itemHandler.getSlots() <= 0) {
         return false;
      }

      ItemStack input = this.itemHandler.getStackInSlot(0);
      if (!input.is((Item)CultivationItems.TORN_PAGE.get())) {
         return false;
      }

      this.itemHandler.extractItem(0, 1, false);
      return true;
   }

   private boolean canOutputAncientBook() {
      return this.itemHandler.getSlots() <= 1 ? false : this.itemHandler.getStackInSlot(1).isEmpty();
   }

   private boolean tryCreateAncientBook() {
      if (!this.canOutputAncientBook()) {
         return false;
      }

      ItemStack ancientBook = new ItemStack((ItemLike)CultivationItems.ANCIENT_BOOK.get());
      int[] solutionMasks = this.createRandomSolutionMasks();
      AncientBookPuzzleData.writePuzzle(ancientBook, solutionMasks, this.createScrambledRotations(solutionMasks));
      this.itemHandler.setStackInSlot(1, ancientBook);
      this.progressTicks = 0;
      this.activePageTicks = 0;
      return true;
   }

   private boolean tryCreateDecodedManual() {
      if (this.hasAncientBook() && this.isDecipherSolved()) {
         ItemStack manual = switch (this.level == null ? 0 : this.level.random.nextInt(6)) {
            case 0 -> new ItemStack((ItemLike)CultivationItems.SWORD_FLIGHT_MANUAL.get());
            case 1 -> new ItemStack((ItemLike)CultivationItems.BREAK_AND_REBUILD_MANUAL.get());
            case 2 -> new ItemStack((ItemLike)CultivationItems.MYRIAD_SWORDS_RETURN_MANUAL.get());
            case 3 -> new ItemStack((ItemLike)CultivationItems.THE_SUPREME_ALCHEMY_CANON.get());
            case 4 -> new ItemStack((ItemLike)CultivationItems.TALISMAN_MANUAL.get());
            default -> new ItemStack((ItemLike)CultivationItems.SIZE_TRANSFORMATION_MANUAL.get());
         };
         this.itemHandler.setStackInSlot(2, manual);
         this.decipherCompleted = true;
         this.decipherActive = false;
         this.decipherSolvedTicks = 0;
         return true;
      } else {
         return false;
      }
   }

   private void initializeDecipherPuzzle() {
      if (this.hasAncientBook() && this.level != null && !this.level.isClientSide) {
         ItemStack ancientBook = this.itemHandler.getStackInSlot(2);
         AncientBookPuzzleData.PuzzleData storedPuzzle = AncientBookPuzzleData.readPuzzle(ancientBook, 24, LEGACY_DECIPHER_SOLUTION_MASKS);
         int[] storedSolutionMasks;
         int[] storedRotations;
         if (storedPuzzle != null && isValidSolutionLayout(storedPuzzle.solutionMasks())) {
            storedSolutionMasks = storedPuzzle.solutionMasks();
            storedRotations = storedPuzzle.rotations();
            if (storedPuzzle.legacyFormat()) {
               AncientBookPuzzleData.writePuzzle(ancientBook, storedSolutionMasks, storedRotations);
            }
         } else {
            storedSolutionMasks = this.createRandomSolutionMasks();
            storedRotations = this.createScrambledRotations(storedSolutionMasks);
            AncientBookPuzzleData.writePuzzle(ancientBook, storedSolutionMasks, storedRotations);
         }

         System.arraycopy(storedSolutionMasks, 0, this.decipherSolutionMasks, 0, this.decipherSolutionMasks.length);
         System.arraycopy(storedRotations, 0, this.decipherRotations, 0, this.decipherRotations.length);
         this.decipherInitialized = true;
         this.decipherCompleted = false;
         this.decipherActive = true;
         this.decipherSolvedTicks = 0;
      }
   }

   private void persistDecipherPuzzle() {
      if (this.hasAncientBook()) {
         AncientBookPuzzleData.writePuzzle(this.itemHandler.getStackInSlot(2), this.decipherSolutionMasks, this.decipherRotations);
      }
   }

   private boolean hasDecipherRotation() {
      for (int rotation : this.decipherRotations) {
         if ((rotation & 3) != 0) {
            return true;
         }
      }

      return false;
   }

   private void resetDecipherPuzzle() {
      this.resetDecipherSolutionMasks();
      Arrays.fill(this.decipherRotations, 0);
      this.decipherInitialized = false;
      this.decipherCompleted = false;
      this.decipherActive = false;
      this.decipherStatus = 0;
      this.decipherStatusTicks = 0;
      this.decipherSolvedTicks = 0;
   }

   public boolean rotateDecipherCell(Player player, int cell, int direction) {
      if (this.level != null
         && !this.level.isClientSide
         && cell >= 0
         && cell < 24
         && (direction == -1 || direction == 1)
         && isDecipherMaskRotatable(this.getDecipherSolutionMask(cell))
         && this.hasAncientBook()
         && !this.decipherCompleted
         && this.decipherActive) {
         this.initializeDecipherPuzzle();
         int cost = 3 + this.level.random.nextInt(6);
         RuneTableBlockEntity runeTable = this.findRuneTable(cost);
         if (runeTable == null) {
            this.decipherStatus = 1;
            this.decipherStatusTicks = 60;
            this.syncToClient();
            return false;
         } else {
            this.decipherStatus = 0;
            this.decipherStatusTicks = 0;
            runeTable.setStoredRuneCount(runeTable.getStoredRuneCount() - cost);
            player.displayClientMessage(Component.translatable("gui.mortal_cultivation.ancient_scholar_table.rune_cost", new Object[]{cost}), true);
            this.decipherRotations[cell] = this.decipherRotations[cell] + direction & 3;
            this.persistDecipherPuzzle();
            this.syncToClient();
            return true;
         }
      } else {
         return false;
      }
   }

   private static int normalizeSelectedPage(int page) {
      return page != 1 && page != 2 ? 0 : page;
   }

   @Nullable
   private RuneTableBlockEntity findRuneTable(int minimumRunes) {
      if (this.level == null) {
         return null;
      }

      RuneTableBlockEntity nearest = null;
      double nearestDistance = Double.MAX_VALUE;
      MutableBlockPos cursor = new MutableBlockPos();

      for (int dx = -8; dx <= 8; dx++) {
         for (int dy = -4; dy <= 4; dy++) {
            for (int dz = -8; dz <= 8; dz++) {
               cursor.set(this.worldPosition.getX() + dx, this.worldPosition.getY() + dy, this.worldPosition.getZ() + dz);
               if (this.level.getBlockEntity(cursor) instanceof RuneTableBlockEntity runeTable && runeTable.getStoredRuneCount() >= minimumRunes) {
                  double distance = cursor.distSqr(this.worldPosition);
                  if (distance < nearestDistance) {
                     nearest = runeTable;
                     nearestDistance = distance;
                  }
               }
            }
         }
      }

      return nearest;
   }

   public static boolean isDecipherMaskRotatable(int mask) {
      return mask != 0 && rotateMask(mask, 1) != mask;
   }

   private int[] createRandomSolutionMasks() {
      RandomSource random = this.level == null ? RandomSource.create() : this.level.random;

      for (int attempt = 0; attempt < 64; attempt++) {
         int[] candidate = createRandomSpanningTree(random);
         if (isValidSolutionLayout(candidate) && countVisibleSolutions(candidate, 2) == 1) {
            return candidate;
         }
      }

      return Arrays.copyOf(LEGACY_DECIPHER_SOLUTION_MASKS, 24);
   }

   private static int[] createRandomSpanningTree(RandomSource random) {
      List<AncientScholarTableBlockEntity.DecipherEdge> edges = new ArrayList<>();

      for (int cell = 0; cell < 24; cell++) {
         int row = cell / 6;
         int column = cell % 6;
         if (column + 1 < 6) {
            edges.add(new AncientScholarTableBlockEntity.DecipherEdge(cell, cell + 1, 2));
         }

         if (row + 1 < 4) {
            edges.add(new AncientScholarTableBlockEntity.DecipherEdge(cell, cell + 6, 4));
         }
      }

      for (int index = edges.size() - 1; index > 0; index--) {
         int swapIndex = random.nextInt(index + 1);
         AncientScholarTableBlockEntity.DecipherEdge edge = edges.get(index);
         edges.set(index, edges.get(swapIndex));
         edges.set(swapIndex, edge);
      }

      int[] parents = new int[24];
      byte[] ranks = new byte[24];
      int cell = 0;

      while (cell < parents.length) {
         parents[cell] = cell++;
      }

      int[] masks = new int[24];
      int acceptedEdges = 0;

      for (AncientScholarTableBlockEntity.DecipherEdge edge : edges) {
         if (unionDecipherCells(parents, ranks, edge.from(), edge.to())) {
            masks[edge.from()] |= edge.direction();
            masks[edge.to()] |= oppositeDirection(edge.direction());
            if (++acceptedEdges == 23) {
               break;
            }
         }
      }

      return masks;
   }

   private int[] createScrambledRotations(int[] solutionMasks) {
      RandomSource random = this.level == null ? RandomSource.create() : this.level.random;
      int[] rotations = new int[24];

      for (int attempt = 0; attempt < 64; attempt++) {
         for (int cell = 0; cell < rotations.length; cell++) {
            int period = getRotationPeriod(solutionMasks[cell]);
            rotations[cell] = period <= 1 ? 0 : random.nextInt(period);
         }

         if (getMinimumRotationClicks(solutionMasks, rotations) >= 16 && !isSolved(solutionMasks, rotations)) {
            return rotations;
         }
      }

      Arrays.fill(rotations, 0);
      int clicks = 0;

      for (int cell = 0; cell < rotations.length && clicks < 16; cell++) {
         int period = getRotationPeriod(solutionMasks[cell]);
         if (period > 1) {
            rotations[cell] = period == 4 ? 2 : 1;
            clicks += period == 4 ? 2 : 1;
         }
      }

      return rotations;
   }

   private static int getMinimumRotationClicks(int[] solutionMasks, int[] rotations) {
      int clicks = 0;

      for (int cell = 0; cell < 24; cell++) {
         int period = getRotationPeriod(solutionMasks[cell]);
         int rotation = period <= 1 ? 0 : Math.floorMod(rotations[cell], period);
         clicks += Math.min(rotation, period - rotation);
      }

      return clicks;
   }

   private static int getRotationPeriod(int mask) {
      if (rotateMask(mask, 1) == (mask & 15)) {
         return 1;
      } else {
         return rotateMask(mask, 2) == (mask & 15) ? 2 : 4;
      }
   }

   private static boolean isSolved(int[] solutionMasks, int[] rotations) {
      if (solutionMasks != null && rotations != null && solutionMasks.length == 24 && rotations.length == 24) {
         int[] visibleMasks = new int[24];

         for (int cell = 0; cell < 24; cell++) {
            visibleMasks[cell] = rotateMask(solutionMasks[cell], rotations[cell]);
         }

         return isValidSolutionLayout(visibleMasks);
      } else {
         return false;
      }
   }

   private static boolean isValidSolutionLayout(int[] masks) {
      if (masks != null && masks.length == 24) {
         int directedConnections = 0;

         for (int cell = 0; cell < 24; cell++) {
            int mask = masks[cell];
            if (mask <= 0 || (mask & -16) != 0 || !isMaskInsideBoard(cell, mask)) {
               return false;
            }

            for (int direction : DECIPHER_DIRECTIONS) {
               if ((mask & direction) != 0) {
                  int neighbor = getNeighborCell(cell, direction);
                  if (neighbor < 0 || (masks[neighbor] & oppositeDirection(direction)) == 0) {
                     return false;
                  }

                  directedConnections++;
               }
            }
         }

         if (directedConnections != 46) {
            return false;
         }

         boolean[] visited = new boolean[24];
         int[] queue = new int[24];
         int head = 0;
         int tail = 0;
         visited[8] = true;
         queue[tail++] = 8;

         while (head < tail) {
            int cell = queue[head++];

            for (int direction : DECIPHER_DIRECTIONS) {
               if ((masks[cell] & direction) != 0) {
                  int neighbor = getNeighborCell(cell, direction);
                  if (!visited[neighbor]) {
                     visited[neighbor] = true;
                     queue[tail++] = neighbor;
                  }
               }
            }
         }

         return tail == 24;
      } else {
         return false;
      }
   }

   private static int countVisibleSolutions(int[] tileMasks, int limit) {
      int[][] domains = new int[24][];

      for (int cell = 0; cell < 24; cell++) {
         int[] candidates = new int[4];
         int candidateCount = 0;

         for (int turns = 0; turns < 4; turns++) {
            int candidate = rotateMask(tileMasks[cell], turns);
            if (isMaskInsideBoard(cell, candidate)) {
               boolean duplicate = false;

               for (int index = 0; index < candidateCount; index++) {
                  if (candidates[index] == candidate) {
                     duplicate = true;
                     break;
                  }
               }

               if (!duplicate) {
                  candidates[candidateCount++] = candidate;
               }
            }
         }

         if (candidateCount == 0) {
            return 0;
         }

         domains[cell] = Arrays.copyOf(candidates, candidateCount);
      }

      int[] assignedMasks = new int[24];
      Arrays.fill(assignedMasks, -1);
      return countVisibleSolutions(domains, assignedMasks, limit);
   }

   private static int countVisibleSolutions(int[][] domains, int[] assignedMasks, int remainingLimit) {
      int selectedCell = -1;
      int selectedOptionCount = Integer.MAX_VALUE;

      for (int cell = 0; cell < 24; cell++) {
         if (assignedMasks[cell] < 0) {
            int optionCount = 0;

            for (int candidate : domains[cell]) {
               if (matchesAssignedNeighbors(cell, candidate, assignedMasks)) {
                  optionCount++;
               }
            }

            if (optionCount == 0) {
               return 0;
            }

            if (optionCount < selectedOptionCount) {
               selectedCell = cell;
               selectedOptionCount = optionCount;
            }
         }
      }

      if (selectedCell < 0) {
         return isValidSolutionLayout(assignedMasks) ? 1 : 0;
      }

      int solutions = 0;

      for (int candidate : domains[selectedCell]) {
         if (matchesAssignedNeighbors(selectedCell, candidate, assignedMasks)) {
            assignedMasks[selectedCell] = candidate;
            solutions += countVisibleSolutions(domains, assignedMasks, remainingLimit - solutions);
            assignedMasks[selectedCell] = -1;
            if (solutions >= remainingLimit) {
               return solutions;
            }
         }
      }

      return solutions;
   }

   private static boolean matchesAssignedNeighbors(int cell, int mask, int[] assignedMasks) {
      for (int direction : DECIPHER_DIRECTIONS) {
         int neighbor = getNeighborCell(cell, direction);
         if (neighbor < 0) {
            if ((mask & direction) != 0) {
               return false;
            }
         } else {
            int neighborMask = assignedMasks[neighbor];
            if (neighborMask >= 0 && (mask & direction) != 0 != ((neighborMask & oppositeDirection(direction)) != 0)) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean isMaskInsideBoard(int cell, int mask) {
      int row = cell / 6;
      int column = cell % 6;
      return ((mask & 1) == 0 || row != 0) && ((mask & 2) == 0 || column != 5) && ((mask & 4) == 0 || row != 3) && ((mask & 8) == 0 || column != 0);
   }

   private static int getNeighborCell(int cell, int direction) {
      int row = cell / 6;
      int column = cell % 6;

      return switch (direction) {
         case 1 -> row > 0 ? cell - 6 : -1;
         case 2 -> column + 1 < 6 ? cell + 1 : -1;
         default -> -1;
         case 4 -> row + 1 < 4 ? cell + 6 : -1;
         case 8 -> column > 0 ? cell - 1 : -1;
      };
   }

   private static int oppositeDirection(int direction) {
      return switch (direction) {
         case 1 -> 4;
         case 2 -> 8;
         default -> 0;
         case 4 -> 1;
         case 8 -> 2;
      };
   }

   private static int findDecipherRoot(int[] parents, int cell) {
      while (parents[cell] != cell) {
         parents[cell] = parents[parents[cell]];
         cell = parents[cell];
      }

      return cell;
   }

   private static boolean unionDecipherCells(int[] parents, byte[] ranks, int first, int second) {
      int firstRoot = findDecipherRoot(parents, first);
      int secondRoot = findDecipherRoot(parents, second);
      if (firstRoot == secondRoot) {
         return false;
      }

      if (ranks[firstRoot] < ranks[secondRoot]) {
         parents[firstRoot] = secondRoot;
      } else if (ranks[firstRoot] > ranks[secondRoot]) {
         parents[secondRoot] = firstRoot;
      } else {
         parents[secondRoot] = firstRoot;
         ranks[firstRoot]++;
      }

      return true;
   }

   private void resetDecipherSolutionMasks() {
      System.arraycopy(LEGACY_DECIPHER_SOLUTION_MASKS, 0, this.decipherSolutionMasks, 0, this.decipherSolutionMasks.length);
   }

   private void syncToClient() {
      this.setChanged();
      if (this.level != null && !this.level.isClientSide) {
         BlockState state = this.getBlockState();
         this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
      }
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   @NotNull
   public CompoundTag getUpdateTag(Provider provider) {
      CompoundTag tag = super.getUpdateTag(provider);
      this.saveAdditional(tag, provider);
      return tag;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   private record DecipherEdge(int from, int to, int direction) {
   }

   private final class TableItemStackHandler extends ItemStackHandler {
      private TableItemStackHandler() {
         super(11);
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         return slot == 0 && stack.is((Item)CultivationItems.TORN_PAGE.get())
            || slot == 2 && stack.is((Item)CultivationItems.ANCIENT_BOOK.get())
            || slot >= 3 && slot < 10
            || slot == 10 && stack.is((Item)CultivationItems.BRUSH_AND_INK.get());
      }

      public int getSlotLimit(int slot) {
         return slot != 2 && slot != 10 ? super.getSlotLimit(slot) : 1;
      }

      protected void onContentsChanged(int slot) {
         if (!AncientScholarTableBlockEntity.this.loadingInventory) {
            if (slot == 2) {
               if (AncientScholarTableBlockEntity.this.hasAncientBook()) {
                  if (AncientScholarTableBlockEntity.this.level != null && !AncientScholarTableBlockEntity.this.level.isClientSide) {
                     AncientScholarTableBlockEntity.this.initializeDecipherPuzzle();
                  }
               } else if (this.getStackInSlot(2).isEmpty()) {
                  AncientScholarTableBlockEntity.this.resetDecipherPuzzle();
               } else {
                  AncientScholarTableBlockEntity.this.decipherActive = false;
               }
            }

            AncientScholarTableBlockEntity.this.setChanged();
         }
      }

      public void deserializeNBT(Provider provider, CompoundTag nbt) {
         CompoundTag migratedNbt = nbt.copy();
         boolean legacyInventory = !migratedNbt.contains("Size", 3) || migratedNbt.getInt("Size") < 11;
         if (legacyInventory) {
            migratedNbt.putInt("Size", 11);
         }

         super.deserializeNBT(provider, migratedNbt);
         this.ensureSlotCount();
         if (legacyInventory && this.getStackInSlot(0).is((Item)CultivationItems.ANCIENT_BOOK.get()) && this.getStackInSlot(2).isEmpty()) {
            ItemStack ancientBook = this.getStackInSlot(0).copy();
            this.setStackInSlot(0, ItemStack.EMPTY);
            this.setStackInSlot(2, ancientBook);
         }
      }

      private void ensureSlotCount() {
         if (this.getSlots() < 11) {
            ItemStack[] oldStacks = new ItemStack[this.getSlots()];

            for (int slot = 0; slot < oldStacks.length; slot++) {
               oldStacks[slot] = this.getStackInSlot(slot).copy();
            }

            this.setSize(11);

            for (int slot = 0; slot < oldStacks.length; slot++) {
               this.setStackInSlot(slot, oldStacks[slot]);
            }
         }
      }
   }
}
