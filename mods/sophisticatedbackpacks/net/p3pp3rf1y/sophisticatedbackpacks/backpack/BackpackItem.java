package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen.ItemPickerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContainer;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContext;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.everlasting.EverlastingBackpackItemEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.everlasting.EverlastingUpgradeItem;
import net.p3pp3rf1y.sophisticatedbackpacks.util.InventoryInteractionHelper;
import net.p3pp3rf1y.sophisticatedcore.Config;
import net.p3pp3rf1y.sophisticatedcore.api.IStashStorageItem;
import net.p3pp3rf1y.sophisticatedcore.api.IStashStorageItem.StashResult;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.ServerStorageSoundHandler;
import net.p3pp3rf1y.sophisticatedcore.util.ColorHelper;
import net.p3pp3rf1y.sophisticatedcore.util.ItemBase;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public class BackpackItem extends ItemBase implements IStashStorageItem {
   private final IntSupplier numberOfSlots;
   private final IntSupplier numberOfUpgradeSlots;
   private final Supplier<BackpackBlock> blockSupplier;

   public BackpackItem(IntSupplier numberOfSlots, IntSupplier numberOfUpgradeSlots, Supplier<BackpackBlock> blockSupplier) {
      this(numberOfSlots, numberOfUpgradeSlots, blockSupplier, p -> p);
   }

   public BackpackItem(
      IntSupplier numberOfSlots, IntSupplier numberOfUpgradeSlots, Supplier<BackpackBlock> blockSupplier, UnaryOperator<Properties> updateProperties
   ) {
      super(updateProperties.apply(new Properties().stacksTo(1)));
      this.numberOfSlots = numberOfSlots;
      this.numberOfUpgradeSlots = numberOfUpgradeSlots;
      this.blockSupplier = blockSupplier;
   }

   public static void setColors(ItemStack backpackStack, int mainColor, int accentColor) {
      backpackStack.set(ModCoreDataComponents.MAIN_COLOR, mainColor);
      backpackStack.set(ModCoreDataComponents.ACCENT_COLOR, accentColor);
   }

   public void addCreativeTabItems(Consumer<ItemStack> itemConsumer) {
      super.addCreativeTabItems(itemConsumer);
      if (this == ModItems.BACKPACK.get() && Config.COMMON.enabledItems.isItemEnabled(this)) {
         for (DyeColor color : DyeColor.values()) {
            ItemStack stack = new ItemStack(this);
            setColors(stack, color.getTextureDiffuseColor(), color.getTextureDiffuseColor());
            itemConsumer.accept(stack);
         }

         int mainColor = ColorHelper.calculateColor(-3382982, -3382982, List.of(DyeColor.YELLOW, DyeColor.LIME));
         int accentColor = ColorHelper.calculateColor(-10342886, -10342886, List.of(DyeColor.BLUE, DyeColor.BLACK));
         ItemStack stack = new ItemStack(this);
         setColors(stack, mainColor, accentColor);
         itemConsumer.accept(stack);
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, tooltip, tooltipFlag);
      if (tooltipFlag.isAdvanced()) {
         BackpackWrapper.fromStack(stack)
            .getContentsUuid()
            .ifPresent(uuid -> tooltip.add(Component.literal("UUID: " + uuid).withStyle(ChatFormatting.DARK_GRAY)));
      }

      if (!Screen.hasShiftDown()) {
         tooltip.add(
            Component.translatable(
                  TranslationHelper.INSTANCE.translItemTooltip("storage") + ".press_for_contents",
                  new Object[]{Component.translatable(TranslationHelper.INSTANCE.translItemTooltip("storage") + ".shift").withStyle(ChatFormatting.AQUA)}
               )
               .withStyle(ChatFormatting.GRAY)
         );
      }
   }

   public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
      return FMLEnvironment.dist.isClient() ? Optional.ofNullable(BackpackItemClient.getTooltipImage(stack)) : Optional.empty();
   }

   public boolean hasCustomEntity(ItemStack stack) {
      return true;
   }

   private boolean hasEverlastingUpgrade(ItemStack stack) {
      return !BackpackWrapper.fromStack(stack).getUpgradeHandler().getTypeWrappers(EverlastingUpgradeItem.TYPE).isEmpty();
   }

   @Nullable
   public Entity createEntity(Level level, Entity entity, ItemStack itemstack) {
      if (entity instanceof ItemEntity itemEntity) {
         UUIDDeduplicator.dedupeBackpackItemEntityInArea(itemEntity);
         return this.hasEverlastingUpgrade(itemstack) ? this.createEverlastingBackpack(level, (ItemEntity)entity, itemstack) : null;
      } else {
         return null;
      }
   }

   @Nullable
   private EverlastingBackpackItemEntity createEverlastingBackpack(Level level, ItemEntity itemEntity, ItemStack itemstack) {
      EverlastingBackpackItemEntity backpackItemEntity = (EverlastingBackpackItemEntity)ModItems.EVERLASTING_BACKPACK_ITEM_ENTITY.get().create(level);
      if (backpackItemEntity != null) {
         backpackItemEntity.setPos(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
         backpackItemEntity.setItem(itemstack);
         backpackItemEntity.setPickUpDelay(itemEntity.pickupDelay);
         if (itemEntity.getOwner() != null) {
            backpackItemEntity.setThrower(itemEntity.getOwner());
         }

         backpackItemEntity.setDeltaMovement(itemEntity.getDeltaMovement());
      }

      return backpackItemEntity;
   }

   public InteractionResult useOn(UseOnContext context) {
      Player player = context.getPlayer();
      if (player == null || !player.isShiftKeyDown()) {
         return InteractionResult.PASS;
      }

      if (InventoryInteractionHelper.tryInventoryInteraction(context)) {
         return InteractionResult.SUCCESS;
      }

      Direction direction = player.getDirection().getOpposite();
      BlockPlaceContext blockItemUseContext = new BlockPlaceContext(context);
      InteractionResult result = this.tryPlace(player, direction, blockItemUseContext);
      return result == InteractionResult.PASS ? super.useOn(context) : result;
   }

   public InteractionResult tryPlace(@Nullable Player player, Direction direction, BlockPlaceContext blockItemUseContext) {
      if (!blockItemUseContext.canPlace()) {
         return InteractionResult.FAIL;
      }

      Level level = blockItemUseContext.getLevel();
      BlockPos pos = blockItemUseContext.getClickedPos();
      FluidState fluidstate = blockItemUseContext.getLevel().getFluidState(pos);
      BlockState placementState = (BlockState)((BlockState)this.blockSupplier.get().defaultBlockState().setValue(BackpackBlock.FACING, direction))
         .setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
      if (!this.canPlace(blockItemUseContext, placementState)) {
         return InteractionResult.FAIL;
      }

      if (level.setBlockAndUpdate(pos, placementState)) {
         ItemStack backpack = blockItemUseContext.getItemInHand();
         WorldHelper.getBlockEntity(level, pos, BackpackBlockEntity.class).ifPresent(be -> {
            be.setBackpack(this.getBackpackCopy(player, backpack));
            be.refreshRenderState();
            be.tryToAddToController();
         });
         if (!level.isClientSide) {
            stopBackpackSounds(backpack, level, pos);
         }

         SoundType soundtype = placementState.getSoundType(level, pos, player);
         level.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
         if (player == null || !player.isCreative()) {
            backpack.shrink(1);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   private static void stopBackpackSounds(ItemStack backpack, Level level, BlockPos pos) {
      BackpackWrapper.fromStack(backpack).getContentsUuid().ifPresent(uuid -> ServerStorageSoundHandler.stopPlayingDisc(level, Vec3.atCenterOf(pos), uuid));
   }

   private ItemStack getBackpackCopy(@Nullable Player player, ItemStack backpack) {
      return player != null && player.isCreative() ? BackpackWrapper.fromStack(backpack).cloneBackpack() : backpack.copy();
   }

   protected boolean canPlace(BlockPlaceContext context, BlockState state) {
      Player playerentity = context.getPlayer();
      CollisionContext iselectioncontext = playerentity == null ? CollisionContext.empty() : CollisionContext.of(playerentity);
      return state.canSurvive(context.getLevel(), context.getClickedPos())
         && context.getLevel().isUnobstructed(state, context.getClickedPos(), iselectioncontext);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!level.isClientSide) {
         String handlerName = hand == InteractionHand.MAIN_HAND ? "main" : "offhand";
         int slot = hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : 0;
         BackpackContext.Item context = new BackpackContext.Item(handlerName, slot);
         player.openMenu(new SimpleMenuProvider((w, p, pl) -> new BackpackContainer(w, pl, context), stack.getHoverName()), context::toBuffer);
      }

      return InteractionResultHolder.success(stack);
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
      if (!level.isClientSide
         && entity instanceof Player player
         && !player.isSpectator()
         && !player.isDeadOrDying()
         && (!(Boolean)net.p3pp3rf1y.sophisticatedbackpacks.Config.SERVER.nerfsConfig.onlyWornBackpackTriggersUpgrades.get() || itemSlot <= -1)) {
         BackpackWrapper.fromStack(stack)
            .getUpgradeHandler()
            .getWrappersThatImplement(ITickableUpgrade.class)
            .forEach(upgrade -> upgrade.tick(player, player.level(), player.blockPosition()));
         super.inventoryTick(stack, level, entity, itemSlot, isSelected);
      }
   }

   public int getNumberOfSlots() {
      return this.numberOfSlots.getAsInt();
   }

   public int getNumberOfUpgradeSlots() {
      return this.numberOfUpgradeSlots.getAsInt();
   }

   public boolean onDroppedByPlayer(ItemStack item, Player player) {
      return !(
         player.containerMenu instanceof BackpackContainer backpackContainer
            && backpackContainer.getVisibleStorageItem().map(visibleStorageItem -> visibleStorageItem == item).orElse(false)
      );
   }

   @Nullable
   public EquipmentSlot getEquipmentSlot(ItemStack stack) {
      return EquipmentSlot.CHEST;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
      return stack.getItem() == ModItems.GOLD_BACKPACK.get();
   }

   public Optional<TooltipComponent> getInventoryTooltip(ItemStack stack) {
      return Optional.of(new BackpackItem.BackpackContentsTooltip(stack));
   }

   public ItemStack stash(ItemStack storageStack, ItemStack stack, boolean simulate) {
      return BackpackWrapper.fromStack(storageStack).getInventoryForUpgradeProcessing().insertItem(stack, simulate);
   }

   public StashResult getItemStashable(Provider registries, ItemStack storageStack, ItemStack stack) {
      IBackpackWrapper wrapper = BackpackWrapper.fromStack(storageStack);
      if (wrapper.getContentsUuid().isEmpty()) {
         return StashResult.SPACE;
      } else if (wrapper.getInventoryForUpgradeProcessing().insertItem(stack, true).getCount() == stack.getCount()) {
         return StashResult.NO_SPACE;
      } else {
         return !wrapper.getInventoryHandler().getSlotTracker().getItems().contains(stack.getItem())
               && !((MemorySettingsCategory)wrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class)).matchesFilter(stack)
            ? StashResult.SPACE
            : StashResult.MATCH_AND_SPACE;
      }
   }

   public boolean overrideStackedOnOther(ItemStack storageStack, Slot slot, ClickAction action, Player player) {
      if (!this.hasCreativeScreenContainerOpen(player) && storageStack.getCount() <= 1 && slot.mayPickup(player) && action == ClickAction.SECONDARY) {
         ItemStack stackToStash = slot.getItem();
         ItemStack stashResult = this.stash(storageStack, stackToStash, true);
         if (stashResult.getCount() >= stackToStash.getCount()) {
            return super.overrideStackedOnOther(storageStack, slot, action, player);
         }

         int countToTake = stackToStash.getCount() - stashResult.getCount();

         while (countToTake > 0) {
            ItemStack takeResult = slot.safeTake(countToTake, countToTake, player);
            if (takeResult.isEmpty()) {
               break;
            }

            this.stash(storageStack, takeResult, false);
            countToTake -= takeResult.getCount();
         }

         return true;
      } else {
         return super.overrideStackedOnOther(storageStack, slot, action, player);
      }
   }

   public boolean overrideOtherStackedOnMe(ItemStack storageStack, ItemStack otherStack, Slot slot, ClickAction action, Player player, SlotAccess carriedAccess) {
      if (!this.hasCreativeScreenContainerOpen(player) && storageStack.getCount() <= 1 && slot.mayPlace(storageStack) && action == ClickAction.SECONDARY) {
         ItemStack result = this.stash(storageStack, otherStack, false);
         if (result.getCount() != otherStack.getCount()) {
            carriedAccess.set(result);
            slot.set(storageStack);
            return true;
         } else {
            return super.overrideOtherStackedOnMe(storageStack, otherStack, slot, action, player, carriedAccess);
         }
      } else {
         return super.overrideOtherStackedOnMe(storageStack, otherStack, slot, action, player, carriedAccess);
      }
   }

   private boolean hasCreativeScreenContainerOpen(Player player) {
      return player.level().isClientSide() && player.containerMenu instanceof ItemPickerMenu;
   }

   public boolean canFitInsideContainerItems() {
      return (Boolean)net.p3pp3rf1y.sophisticatedbackpacks.Config.SERVER.canBePlacedInContainerItems.get();
   }

   public record BackpackContentsTooltip(ItemStack backpack) implements TooltipComponent {
      public ItemStack getBackpack() {
         return this.backpack;
      }
   }
}
