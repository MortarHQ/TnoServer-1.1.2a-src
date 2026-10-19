package net.blay09.mods.waystones.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.mixin.ScreenAccessor;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.client.gui.widget.ITooltipProvider;
import net.blay09.mods.waystones.client.gui.widget.RemoveWaystoneButton;
import net.blay09.mods.waystones.client.gui.widget.SortWaystoneButton;
import net.blay09.mods.waystones.client.gui.widget.WaystoneButton;
import net.blay09.mods.waystones.comparator.UserSortingComparator;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.blay09.mods.waystones.network.message.RemoveWaystoneMessage;
import net.blay09.mods.waystones.network.message.RequestEditWaystoneMessage;
import net.blay09.mods.waystones.network.message.SelectWaystoneMessage;
import net.blay09.mods.waystones.network.message.SortWaystoneMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public abstract class WaystoneSelectionScreenBase extends AbstractContainerScreen<WaystoneSelectionMenu> {
   private final Collection<Waystone> waystones;
   private List<Waystone> filteredWaystones;
   private final List<ITooltipProvider> tooltipProviders = new ArrayList<>();
   private String searchText = "";
   private Button btnPrevPage;
   private Button btnNextPage;
   private EditBox searchBox;
   private int pageOffset;
   private int headerY;
   private boolean isLocationHeaderHovered;
   private int buttonsPerPage;
   private static final int headerHeight = 64;
   private static final int footerHeight = 25;
   private static final int entryHeight = 25;

   public WaystoneSelectionScreenBase(WaystoneSelectionMenu container, Inventory playerInventory, Component title) {
      super(container, playerInventory, title);
      this.waystones = container.getWaystones();
      PlayerWaystoneManager.ensureSortingIndex(Minecraft.getInstance().player, this.waystones);
      this.filteredWaystones = new ArrayList<>(this.waystones);
      Comparator<Waystone> sorting = this.getSorting();
      if (sorting != null) {
         this.filteredWaystones.sort(this.getSorting());
      }

      this.imageWidth = 270;
      this.imageHeight = 200;
   }

   public void init() {
      int maxContentHeight = (int)(this.height * 0.6F);
      int maxButtonsPerPage = (maxContentHeight - 64 - 25) / 25;
      this.buttonsPerPage = Math.max(4, Math.min(maxButtonsPerPage, this.waystones.size()));
      int contentHeight = 64 + this.buttonsPerPage * 25 + 25;
      this.imageWidth = this.width;
      this.imageHeight = contentHeight;
      super.init();
      this.tooltipProviders.clear();
      this.btnPrevPage = Button.builder(Component.translatable("gui.waystones.waystone_selection.previous_page"), button -> {
         this.pageOffset = Screen.hasShiftDown() ? 0 : this.pageOffset - 1;
         this.updateList();
      }).pos(this.width / 2 - 100, this.height / 2 + 40).size(95, 20).build();
      this.addRenderableWidget(this.btnPrevPage);
      this.btnNextPage = Button.builder(Component.translatable("gui.waystones.waystone_selection.next_page"), button -> {
         this.pageOffset = Screen.hasShiftDown() ? (this.waystones.size() - 1) / this.buttonsPerPage : this.pageOffset + 1;
         this.updateList();
      }).pos(this.width / 2 + 5, this.height / 2 + 40).size(95, 20).build();
      this.addRenderableWidget(this.btnNextPage);
      this.updateList();
      this.searchBox = new EditBox(this.font, this.width / 2 - 99, this.topPos + 64 - 24, 198, 20, Component.empty());
      this.searchBox.setResponder(text -> {
         this.pageOffset = 0;
         this.searchText = text;
         this.updateList();
      });
      this.addRenderableWidget(this.searchBox);
   }

   protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget) {
      if (widget instanceof ITooltipProvider) {
         this.tooltipProviders.add((ITooltipProvider)widget);
      }

      return (T)super.addRenderableWidget(widget);
   }

   private void updateList() {
      List<Waystone> list = new ArrayList<>();

      for (Waystone waystone : this.waystones) {
         if (waystone.getName().getString().toLowerCase().contains(this.searchText.toLowerCase())) {
            list.add(waystone);
         }
      }

      Comparator<Waystone> sorting = this.getSorting();
      if (sorting != null) {
         list.sort(sorting);
      }

      this.filteredWaystones = list;
      this.headerY = 0;
      this.btnPrevPage.active = this.pageOffset > 0;
      this.btnNextPage.active = this.pageOffset < (this.filteredWaystones.size() - 1) / this.buttonsPerPage;
      this.tooltipProviders.clear();
      Predicate<Object> removePredicate = button -> button instanceof WaystoneButton
         || button instanceof SortWaystoneButton
         || button instanceof RemoveWaystoneButton;
      ((ScreenAccessor)this).balm_getChildren().removeIf(removePredicate);
      ((ScreenAccessor)this).balm_getNarratables().removeIf(removePredicate);
      ((ScreenAccessor)this).balm_getRenderables().removeIf(removePredicate);
      int y = this.topPos + 64 + this.headerY;

      for (int i = 0; i < this.buttonsPerPage; i++) {
         int entryIndex = this.pageOffset * this.buttonsPerPage + i;
         if (entryIndex >= 0 && entryIndex < this.filteredWaystones.size()) {
            Waystone waystone = this.filteredWaystones.get(entryIndex);
            this.addRenderableWidget(this.createWaystoneButton(y, waystone));
            if (this.allowSorting()) {
               SortWaystoneButton sortUpButton = new SortWaystoneButton(this.width / 2 + 108, y + 2, -1, y, 20, it -> this.sortWaystone(waystone, -1));
               if (entryIndex == 0) {
                  sortUpButton.active = false;
               }

               this.addRenderableWidget(sortUpButton);
               SortWaystoneButton sortDownButton = new SortWaystoneButton(this.width / 2 + 108, y + 13, 1, y, 20, it -> this.sortWaystone(waystone, 1));
               if (entryIndex == this.filteredWaystones.size() - 1) {
                  sortDownButton.active = false;
               }

               this.addRenderableWidget(sortDownButton);
            }

            if (this.allowDeletion(waystone)) {
               RemoveWaystoneButton removeButton = new RemoveWaystoneButton(this.width / 2 + 122, y + 4, y, 20, waystone, button -> {
                  Player player = Minecraft.getInstance().player;
                  PlayerWaystoneManager.deactivateWaystone(Objects.requireNonNull(player), waystone);
                  this.waystones.remove(waystone);
                  Balm.getNetworking().sendToServer(new RemoveWaystoneMessage(waystone.getWaystoneUid()));
                  this.updateList();
               });
               this.addRenderableWidget(removeButton);
            }

            y += 22;
         }
      }

      this.btnPrevPage.setY(this.topPos + this.headerY + 64 + this.buttonsPerPage * 22 + (this.filteredWaystones.size() > 0 ? 10 : 0));
      this.btnNextPage.setY(this.topPos + this.headerY + 64 + this.buttonsPerPage * 22 + (this.filteredWaystones.size() > 0 ? 10 : 0));
   }

   private boolean allowDeletion(Waystone waystone) {
      boolean isCreative = Minecraft.getInstance().player.getAbilities().instabuild;
      if (waystone.getVisibility() == WaystoneVisibility.GLOBAL && !isCreative) {
         return false;
      }

      if (WaystoneTypes.isSharestone(waystone.getWaystoneType())) {
         if (!isCreative) {
            return false;
         }
      } else if (!waystone.getWaystoneType().equals(WaystoneTypes.WAYSTONE)) {
         return false;
      }

      return this.allowDeletion();
   }

   private WaystoneButton createWaystoneButton(int y, Waystone waystone) {
      Waystone waystoneFrom = ((WaystoneSelectionMenu)this.menu).getWaystoneFrom();
      Set<ResourceLocation> flags = ((WaystoneSelectionMenu)this.menu).getFlags();
      LocalPlayer player = Minecraft.getInstance().player;
      WaystoneTeleportContext context = WaystonesAPI.createUnboundTeleportContext(player, waystone)
         .setFromWaystone(waystoneFrom)
         .setWarpItem(((WaystoneSelectionMenu)this.menu).getWarpItem())
         .addFlags(flags);
      WarpRequirement requirements = WaystonesAPI.resolveRequirements(context);
      WaystoneButton btnWaystone = new WaystoneButton(this.width / 2 - 100, y, waystone, requirements, button -> this.onWaystoneSelected(waystone));
      if (waystoneFrom != null && waystone.getWaystoneUid().equals(waystoneFrom.getWaystoneUid())) {
         btnWaystone.active = false;
      }

      return btnWaystone;
   }

   protected void onWaystoneSelected(Waystone waystone) {
      Balm.getNetworking().sendToServer(new SelectWaystoneMessage(waystone.getWaystoneUid()));
   }

   private void sortWaystone(Waystone waystone, int sortDir) {
      UUID waystoneUid = waystone.getWaystoneUid();
      if (Screen.hasShiftDown()) {
         if (sortDir == -1) {
            PlayerWaystoneManager.sortWaystoneAsFirst(Minecraft.getInstance().player, waystoneUid);
            Balm.getNetworking().sendToServer(new SortWaystoneMessage(waystoneUid, SortWaystoneMessage.SORT_FIRST));
         } else if (sortDir == 1) {
            PlayerWaystoneManager.sortWaystoneAsLast(Minecraft.getInstance().player, waystoneUid);
            Balm.getNetworking().sendToServer(new SortWaystoneMessage(waystoneUid, SortWaystoneMessage.SORT_LAST));
         }
      } else {
         int index = this.filteredWaystones.indexOf(waystone);
         int otherIndex = index + sortDir;
         if (index == -1 || otherIndex < 0 || otherIndex >= this.waystones.size()) {
            return;
         }

         Waystone otherWaystone = this.filteredWaystones.get(otherIndex);
         UUID otherWaystoneUid = otherWaystone.getWaystoneUid();
         PlayerWaystoneManager.sortWaystoneSwap(Minecraft.getInstance().player, waystoneUid, otherWaystoneUid);
         Balm.getNetworking().sendToServer(new SortWaystoneMessage(waystoneUid, otherWaystoneUid));
      }

      this.updateList();
   }

   public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
      if (this.isLocationHeaderHovered && ((WaystoneSelectionMenu)this.menu).getWaystoneFrom() != null) {
         Balm.getNetworking().sendToServer(new RequestEditWaystoneMessage(((WaystoneSelectionMenu)this.menu).getWaystoneFrom().getPos()));
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, mouseButton);
      }
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);

      for (ITooltipProvider tooltipProvider : this.tooltipProviders) {
         if (tooltipProvider.shouldShowTooltip()) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, tooltipProvider.getTooltipComponents(), Optional.empty(), mouseX, mouseY);
         }
      }
   }

   protected void renderBg(GuiGraphics guiGraphics, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      Font font = Minecraft.getInstance().font;
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      Waystone fromWaystone = ((WaystoneSelectionMenu)this.menu).getWaystoneFrom();
      guiGraphics.drawCenteredString(font, this.getTitle(), this.imageWidth / 2, this.headerY + (fromWaystone != null ? 20 : 0), 16777215);
      if (fromWaystone != null) {
         this.drawLocationHeader(guiGraphics, fromWaystone, mouseX, mouseY, this.imageWidth / 2, this.headerY);
      }

      if (this.waystones.size() == 0) {
         guiGraphics.drawCenteredString(
            font,
            ChatFormatting.RED + I18n.get("gui.waystones.waystone_selection.no_waystones_activated", new Object[0]),
            this.imageWidth / 2,
            this.imageHeight / 2 - 20,
            16777215
         );
      }
   }

   private void drawLocationHeader(GuiGraphics guiGraphics, Waystone waystone, int mouseX, int mouseY, int x, int y) {
      Font font = Minecraft.getInstance().font;
      int locationPrefixWidth = font.width(Component.translatable("gui.waystones.waystone_selection.current_location", new Object[]{""}));
      MutableComponent effectiveName = waystone.getName().copy();
      if (effectiveName.getString().isEmpty()) {
         effectiveName = Component.translatable("gui.waystones.waystone_selection.unnamed_waystone");
      }

      int locationWidth = font.width(effectiveName);
      int fullWidth = locationPrefixWidth + locationWidth;
      int startX = x - fullWidth / 2 + locationPrefixWidth;
      int startY = y + this.topPos;
      this.isLocationHeaderHovered = mouseX >= startX && mouseX < startX + locationWidth + 16 && mouseY >= startY && mouseY < startY + 9;
      if (this.isLocationHeaderHovered) {
         effectiveName.withStyle(ChatFormatting.UNDERLINE);
      }

      MutableComponent fullText = Component.translatable(
            "gui.waystones.waystone_selection.current_location", new Object[]{effectiveName.withStyle(ChatFormatting.WHITE)}
         )
         .withStyle(ChatFormatting.YELLOW);
      guiGraphics.drawString(font, fullText, x - fullWidth / 2, y, 16777215);
      if (this.isLocationHeaderHovered) {
         PoseStack poseStack = guiGraphics.pose();
         poseStack.pushPose();
         float scale = 0.5F;
         poseStack.translate(x + fullWidth / 2.0F + 4.0F, y, 0.0F);
         poseStack.scale(scale, scale, scale);
         guiGraphics.renderItem(new ItemStack(Items.WRITABLE_BOOK), 0, 0);
         poseStack.popPose();
      }
   }

   protected boolean allowSorting() {
      return true;
   }

   protected boolean allowDeletion() {
      return true;
   }

   public boolean keyPressed(int key, int scanCode, int modifiers) {
      if (this.searchBox == null) {
         return super.keyPressed(key, scanCode, modifiers);
      } else {
         return this.searchBox.isFocused() && (key != 256 || !this.shouldCloseOnEsc())
            ? this.searchBox.keyPressed(key, scanCode, modifiers)
            : super.keyPressed(key, scanCode, modifiers);
      }
   }

   public Comparator<Waystone> getSorting() {
      LocalPlayer player = Minecraft.getInstance().player;
      List<UUID> sortingIndex = PlayerWaystoneManager.getSortingIndex(player);
      return new UserSortingComparator(sortingIndex);
   }
}
