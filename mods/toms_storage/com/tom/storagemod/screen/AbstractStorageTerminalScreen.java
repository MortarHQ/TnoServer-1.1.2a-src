package com.tom.storagemod.screen;

import com.google.common.base.Predicates;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.JsonElement;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.JsonOps;
import com.tom.storagemod.Config;
import com.tom.storagemod.client.ClientUtil;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.inventory.TerminalItemStack;
import com.tom.storagemod.inventory.sorting.SortingTypes;
import com.tom.storagemod.menu.StorageTerminalMenu;
import com.tom.storagemod.screen.widget.EnumCycleButton;
import com.tom.storagemod.screen.widget.TerminalSearchModeButton;
import com.tom.storagemod.screen.widget.ToggleButton;
import com.tom.storagemod.util.ComponentJoiner;
import com.tom.storagemod.util.IAutoFillTerminal;
import com.tom.storagemod.util.IDataReceiver;
import com.tom.storagemod.util.NumberFormatUtil;
import com.tom.storagemod.util.PopupMenuManager;
import com.tom.storagemod.util.TerminalSyncManager;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag.Default;
import org.lwjgl.glfw.GLFW;

public abstract class AbstractStorageTerminalScreen<T extends StorageTerminalMenu> extends PlatformContainerScreen<T> implements IDataReceiver {
   private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.parse("container/creative_inventory/scroller");
   private static final ResourceLocation SCROLLER_DISABLED_SPRITE = ResourceLocation.parse("container/creative_inventory/scroller_disabled");
   private static final ResourceLocation FLOATING_SLOT = ResourceLocation.tryBuild("toms_storage", "widget/floating_slot");
   private static final LoadingCache<StoredItemStack, List<String>> tooltipCache = CacheBuilder.newBuilder()
      .expireAfterAccess(5L, TimeUnit.SECONDS)
      .build(CacheLoader.from(key -> {
         Minecraft mc = Minecraft.getInstance();
         Default flag = mc.options.advancedItemTooltips ? Default.ADVANCED : Default.NORMAL;
         return key.getStack().getTooltipLines(TooltipContext.of(mc.level), mc.player, flag).stream().map(Component::getString).collect(Collectors.toList());
      }));
   private static final LoadingCache<StoredItemStack, String> componentCache = CacheBuilder.newBuilder()
      .expireAfterAccess(5L, TimeUnit.SECONDS)
      .build(CacheLoader.from(key -> {
         RegistryOps<JsonElement> ctx = Minecraft.getInstance().level.registryAccess().createSerializationContext(JsonOps.COMPRESSED);
         return (String)DataComponentPatch.CODEC.encodeStart(ctx, key.getStack().getComponentsPatch()).mapOrElse(JsonElement::toString, e -> "");
      }));
   private static final LoadingCache<StoredItemStack, List<String>> tagCache = CacheBuilder.newBuilder()
      .expireAfterAccess(5L, TimeUnit.SECONDS)
      .build(CacheLoader.from(key -> key.getStack().getTags().map(t -> t.location().toString()).toList()));
   protected float currentScroll;
   protected boolean isScrolling;
   private boolean refreshItemList;
   protected boolean wasClicking;
   protected EditBox searchField;
   protected int slotIDUnderMouse = -1;
   protected int controllMode;
   protected int rowCount;
   private String searchLast = "";
   protected boolean loadedSearch = false;
   protected boolean ghostItems;
   protected boolean tallMode;
   public final int textureSlotCount;
   public final int guiHeight;
   public final int slotStartX;
   public final int slotStartY;
   private SortingTypes sortingType = SortingTypes.AMOUNT;
   private boolean reverseSort = false;
   protected EnumCycleButton<SortingTypes> buttonSortingType;
   protected EnumCycleButton<AbstractStorageTerminalScreen.ControllMode> buttonCtrlMode;
   protected TerminalSearchModeButton buttonSearchType;
   protected ToggleButton buttonDirection;
   protected ToggleButton buttonGhostMode;
   protected ToggleButton buttonTallMode;
   private Comparator<StoredItemStack> sortComp;
   protected PopupMenuManager popup = new PopupMenuManager(this);
   private AbstractStorageTerminalScreen.FakeSlot fakeSlotUnderMouse = new AbstractStorageTerminalScreen.FakeSlot();

   public AbstractStorageTerminalScreen(
      T screenContainer, Inventory inv, Component titleIn, int textureSlotCount, int guiHeight, int slotStartX, int slotStartY
   ) {
      super(screenContainer, inv, titleIn);
      screenContainer.onPacket = this::onPacket;
      this.textureSlotCount = textureSlotCount;
      this.guiHeight = guiHeight;
      this.slotStartX = slotStartX;
      this.slotStartY = slotStartY;
   }

   protected void onPacket() {
      this.controllMode = (((StorageTerminalMenu)this.menu).modes & 15) % AbstractStorageTerminalScreen.ControllMode.VALUES.length;
      int type = ((StorageTerminalMenu)this.menu).sorting & 0xFF;
      this.sortingType = SortingTypes.VALUES[type];
      this.reverseSort = (((StorageTerminalMenu)this.menu).sorting & 256) > 0;
      int searchType = ((StorageTerminalMenu)this.menu).searchType;
      this.ghostItems = (((StorageTerminalMenu)this.menu).sorting & 512) == 0;
      boolean tallMode = (((StorageTerminalMenu)this.menu).modes & 16) != 0;
      if (tallMode != this.tallMode) {
         this.tallMode = tallMode;
         this.init();
      }

      if (searchType != -1) {
         if (!this.searchField.isFocused() && (searchType & 1) > 0) {
            this.searchField.setFocused(true);
         }

         this.buttonSearchType.setSearchType(searchType);
         if (!this.loadedSearch && ((StorageTerminalMenu)this.menu).search != null) {
            this.loadedSearch = true;
            if ((searchType & 2) > 0) {
               this.searchField.setValue(((StorageTerminalMenu)this.menu).search);
            }
         }
      }

      this.buttonSortingType.setState(this.sortingType);
      this.buttonDirection.setState(this.reverseSort);
      this.buttonCtrlMode.setState(AbstractStorageTerminalScreen.ControllMode.VALUES[this.controllMode]);
      this.buttonGhostMode.setState(this.ghostItems);
      this.buttonTallMode.setState(tallMode);
   }

   protected void sendUpdate() {
      CompoundTag c = new CompoundTag();
      int sort = 0;
      sort |= this.sortingType.ordinal() & 0xFF;
      sort |= this.reverseSort ? 256 : 0;
      sort |= this.ghostItems ? 0 : 512;
      c.putInt("s", sort);
      c.putInt("st", this.buttonSearchType.getSearchType());
      c.putInt("m", this.writeModes());
      CompoundTag msg = new CompoundTag();
      msg.put("c", c);
      ((StorageTerminalMenu)this.menu).sendMessage(msg);
   }

   protected int writeModes() {
      int d = 0;
      d |= this.controllMode & 15;
      return d | (this.tallMode ? 16 : 0);
   }

   protected void init() {
      this.clearWidgets();
      if (this.tallMode) {
         int guiSize = this.guiHeight - this.textureSlotCount * 18;
         this.rowCount = (this.height - 30 - guiSize) / 18;
         this.imageHeight = guiSize + this.rowCount * 18;
         ((StorageTerminalMenu)this.menu).setOffset(0, (this.rowCount - this.textureSlotCount) * 18);
         ((StorageTerminalMenu)this.menu).addStorageSlots(this.rowCount, this.slotStartX + 1, this.slotStartY + 1);
      } else {
         this.rowCount = this.textureSlotCount;
         ((StorageTerminalMenu)this.menu).setOffset(0, 0);
         ((StorageTerminalMenu)this.menu).addStorageSlots(this.rowCount, this.slotStartX + 1, this.slotStartY + 1);
      }

      Slot offh = ((StorageTerminalMenu)this.getMenu()).offhand;
      if (this.minecraft.options.mainHand().get() == HumanoidArm.RIGHT) {
         offh.x = -26;
      } else {
         offh.x = 186;
      }

      this.inventoryLabelY = this.imageHeight - 92;
      super.init();
      this.searchField = new EditBox(this.getFont(), this.leftPos + 82, this.topPos + 6, 89, 9, Component.translatable("narrator.toms_storage.terminal_search"));
      this.searchField.setMaxLength(100);
      this.searchField.setBordered(false);
      this.searchField.setVisible(true);
      this.searchField.setTextColor(16777215);
      this.searchField.setValue(this.searchLast);
      this.searchLast = "";
      this.addWidget(this.searchField);
      this.buttonSortingType = (EnumCycleButton<SortingTypes>)this.addRenderableWidget(
         new EnumCycleButton<SortingTypes>(
            this.leftPos - 18, this.topPos + 5, Component.translatable("narrator.toms_storage.terminal_sort"), "sort", SortingTypes.VALUES, n -> {
               this.sortingType = n;
               this.buttonSortingType.setState(n);
               this.sendUpdate();
               this.refreshItemList = true;
            }
         )
      );
      this.buttonDirection = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos - 18, this.topPos + 5 + 18)
            .name(Component.translatable("narrator.toms_storage.terminal_sort_rev"))
            .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/sort_desc"))
            .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/sort_asc"))
            .build(n -> {
               this.reverseSort = n;
               this.buttonDirection.setState(n);
               this.sendUpdate();
               this.refreshItemList = true;
            })
      );
      this.buttonSearchType = (TerminalSearchModeButton)this.addRenderableWidget(
         new TerminalSearchModeButton(
            this.leftPos - 18, this.topPos + 5 + 36, this.popup, IAutoFillTerminal.hasSync() || this instanceof CraftingTerminalScreen, this::sendUpdate
         )
      );
      this.buttonCtrlMode = (EnumCycleButton<AbstractStorageTerminalScreen.ControllMode>)this.addRenderableWidget(
         new EnumCycleButton<AbstractStorageTerminalScreen.ControllMode>(
            this.leftPos - 18,
            this.topPos + 5 + 54,
            Component.translatable("narrator.toms_storage.terminal_control"),
            "control",
            AbstractStorageTerminalScreen.ControllMode.VALUES,
            n -> {
               this.controllMode = n.ordinal();
               this.buttonCtrlMode.setState(n);
               this.sendUpdate();
            }
         )
      );
      this.buttonGhostMode = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos - 18, this.topPos + 5 + 72)
            .name(Component.translatable("narrator.toms_storage.terminal_ghost_items"))
            .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/keep_last_off"))
            .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/keep_last_0"))
            .build(n -> {
               this.ghostItems = n;
               this.buttonGhostMode.setState(n);
               this.sendUpdate();
            })
      );
      this.buttonGhostMode
         .setTooltip(
            Tooltip.create(Component.translatable("tooltip.toms_storage.ghostMode_off")),
            Tooltip.create(Component.translatable("tooltip.toms_storage.ghostMode_on"))
         );
      this.buttonTallMode = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos - 18, this.topPos + 5 + 90)
            .name(Component.translatable("narrator.toms_storage.terminal_tall_mode"))
            .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/tall_terminal_off"))
            .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/tall_terminal_on"))
            .build(n -> {
               this.tallMode = n;
               this.buttonTallMode.setState(n);
               this.sendUpdate();
               this.init();
            })
      );
      this.buttonTallMode
         .setTooltip(
            Tooltip.create(Component.translatable("tooltip.toms_storage.tallMode_off")),
            Tooltip.create(Component.translatable("tooltip.toms_storage.tallMode_on"))
         );
      this.buttonSortingType.tooltipFactory = s -> Tooltip.create(Component.translatable("tooltip.toms_storage.sorting_" + s.name().toLowerCase(Locale.ROOT)));
      this.buttonCtrlMode.tooltipFactory = s -> Tooltip.create(
         Arrays.stream(I18n.get("tooltip.toms_storage.ctrlMode_" + s.name().toLowerCase(Locale.ROOT), new Object[0]).split("\\\\"))
            .<Component>map(Component::literal)
            .collect(ComponentJoiner.joining(Component.empty(), Component.literal("\n")))
      );
      this.updateSearch();
   }

   private static Pattern buildPattern(String s) {
      try {
         return Pattern.compile(s, 2);
      } catch (Throwable ignore) {
         try {
            return Pattern.compile(Pattern.quote(s), 2);
         } catch (Throwable ignored) {
            return null;
         }
      }
   }

   private boolean matchSearch(Pattern m, String value) {
      return m.matcher(value).find();
   }

   protected void updateSearch() {
      String searchString = this.searchField.getValue();
      if (this.refreshItemList || !this.searchLast.equals(searchString)) {
         ((StorageTerminalMenu)this.getMenu()).itemListClientSorted.clear();
         Predicate<TerminalItemStack> pred = null;
         String[] or = searchString.split("\\|");

         for (int i = 0; i < or.length; i++) {
            String part = or[i].trim();
            if (!part.isEmpty()) {
               String[] sp = part.split(" ");
               Predicate<TerminalItemStack> p = Predicates.alwaysTrue();

               for (int j = 0; j < sp.length; j++) {
                  String s = sp[j].toLowerCase();
                  if (s.startsWith("@")) {
                     String fs = s.substring(1);
                     p = p.and(is -> is.getNamespace().contains(fs));
                  } else if (s.startsWith("#")) {
                     String fs = s.substring(1);
                     p = p.and(is -> {
                        try {
                           return ((List)tagCache.get(is)).stream().anyMatch(st -> st.contains(fs));
                        } catch (Exception e) {
                           return false;
                        }
                     });
                  } else if (s.startsWith("$")) {
                     Pattern m = buildPattern(s.substring(1));
                     if (m != null) {
                        p = p.and(is -> {
                           if (is.getStack().getComponentsPatch().isEmpty()) {
                              return false;
                           }

                           try {
                              return this.matchSearch(m, (String)componentCache.get(is));
                           } catch (Exception e) {
                              return false;
                           }
                        });
                     }
                  } else {
                     Pattern m = buildPattern(s);
                     if (m != null) {
                        p = p.and(is -> {
                           try {
                              String dspName = is.getDisplayName();
                              if (this.matchSearch(m, dspName.toLowerCase())) {
                                 return true;
                              }

                              for (String lp : (List)tooltipCache.get(is)) {
                                 if (this.matchSearch(m, lp)) {
                                    return true;
                                 }
                              }
                           } catch (Exception var6x) {
                           }

                           return false;
                        });
                     }
                  }
               }

               if (pred == null) {
                  pred = p;
               } else {
                  pred = pred.or(p);
               }
            }
         }

         if (pred == null) {
            pred = Predicates.alwaysTrue();
         }

         try {
            for (int i = 0; i < ((StorageTerminalMenu)this.getMenu()).itemListClient.size(); i++) {
               TerminalItemStack is = ((StorageTerminalMenu)this.getMenu()).itemListClient.get(i);
               if (is != null && is.getStack() != null && pred.test(is)) {
                  this.addStackToClientList(is);
               }
            }
         } catch (Exception var11) {
         }

         Comparator var10001 = ((StorageTerminalMenu)this.menu).noSort ? this.sortComp : this.sortingType.create(this.reverseSort);
         Collections.sort(((StorageTerminalMenu)this.getMenu()).itemListClientSorted, var10001);
         if (!this.searchLast.equals(searchString)) {
            ((StorageTerminalMenu)this.getMenu()).scrollTo(0.0F);
            this.currentScroll = 0.0F;
            int searchType = this.buttonSearchType.getSearchType();
            if (searchType != -1) {
               if ((searchType & 4) > 0) {
                  IAutoFillTerminal.sync(searchString);
               }

               if ((searchType & 2) > 0) {
                  CompoundTag nbt = new CompoundTag();
                  nbt.putString("s", searchString);
                  ((StorageTerminalMenu)this.menu).sendMessage(nbt);
               }
            }

            this.onUpdateSearch(searchString);
         } else {
            ((StorageTerminalMenu)this.getMenu()).scrollTo(this.currentScroll);
         }

         this.refreshItemList = false;
         this.searchLast = searchString;
      }
   }

   private void addStackToClientList(TerminalItemStack is) {
      ((StorageTerminalMenu)this.getMenu()).itemListClientSorted.add(is);
   }

   protected void containerTick() {
      this.updateSearch();
   }

   public void render(GuiGraphics st, int mouseX, int mouseY, float partialTicks) {
      boolean flag = GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), 0) != 0;
      int i = this.leftPos;
      int j = this.topPos;
      int k = i + 174;
      int l = j + 18;
      int i1 = k + 14;
      int j1 = l + this.rowCount * 18;
      if (((StorageTerminalMenu)this.menu).itemsLoaded && this.ghostItems && hasShiftDown()) {
         if (!((StorageTerminalMenu)this.menu).noSort) {
            List<TerminalItemStack> list = ((StorageTerminalMenu)this.getMenu()).itemListClientSorted;
            Object2IntMap<TerminalItemStack> map = new Object2IntOpenHashMap();
            map.defaultReturnValue(Integer.MAX_VALUE);

            for (int m = 0; m < list.size(); m++) {
               map.put(list.get(m), m);
            }

            this.sortComp = Comparator.comparing(map::getInt);
            ((StorageTerminalMenu)this.menu).noSort = true;
         }
      } else if (((StorageTerminalMenu)this.menu).noSort) {
         this.sortComp = null;
         ((StorageTerminalMenu)this.menu).noSort = false;
         this.refreshItemList = true;
         ((StorageTerminalMenu)this.menu).itemListClient = new ArrayList<>(((StorageTerminalMenu)this.menu).itemList);
      }

      if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1) {
         this.isScrolling = this.needsScrollBars();
      }

      if (!flag) {
         this.isScrolling = false;
      }

      this.wasClicking = flag;
      if (this.isScrolling) {
         this.currentScroll = (mouseY - l - 7.5F) / (j1 - l - 15.0F);
         this.currentScroll = Mth.clamp(this.currentScroll, 0.0F, 1.0F);
         ((StorageTerminalMenu)this.getMenu()).scrollTo(this.currentScroll);
      }

      super.render(st, mouseX, mouseY, partialTicks);
      i = k;
      j = l;
      k = j1;
      st.blitSprite(this.needsScrollBars() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE, i, j + (int)((k - j - 17) * this.currentScroll), 12, 15);
      this.searchField.render(st, mouseX, mouseY, partialTicks);
      if (((StorageTerminalMenu)this.menu).beaconLvl >= 0) {
         int x = 176;
         int y = 24 + this.rowCount * 18;
         st.renderItem(new ItemStack(Items.BEACON), this.leftPos + x, this.topPos + y);
         if (this.isHovering(x, y, 16, 16, mouseX, mouseY)) {
            String info;
            if (Config.get().wirelessTermBeaconLvlCrossDim != -1 && ((StorageTerminalMenu)this.menu).beaconLvl >= Config.get().wirelessTermBeaconLvlCrossDim) {
               info = "\\" + I18n.get("tooltip.toms_storage.terminal_beacon.anywhere", new Object[0]);
            } else if (Config.get().wirelessTermBeaconLvl != -1 && ((StorageTerminalMenu)this.menu).beaconLvl >= Config.get().wirelessTermBeaconLvl) {
               info = "\\" + I18n.get("tooltip.toms_storage.terminal_beacon.sameDim", new Object[0]);
            } else {
               info = "";
            }

            st.renderComponentTooltip(
               this.font,
               Arrays.stream(I18n.get("tooltip.toms_storage.terminal_beacon", new Object[]{((StorageTerminalMenu)this.menu).beaconLvl, info}).split("\\\\"))
                  .map(Component::literal)
                  .collect(Collectors.toList()),
               mouseX,
               mouseY
            );
         }
      }

      if (this.isHovering(176, 4, 16, 10, mouseX, mouseY)) {
         st.renderComponentTooltip(
            this.font,
            List.of(
               Component.translatable(
                  "tooltip.toms_storage.terminal_stat.slots",
                  new Object[]{
                     ((StorageTerminalMenu)this.menu).slotCount == 32767
                        ? Component.translatable("tooltip.toms_storage.terminal_stat.alot")
                        : ((StorageTerminalMenu)this.menu).slotCount
                  }
               ),
               Component.translatable(
                  "tooltip.toms_storage.terminal_stat.free",
                  new Object[]{
                     ((StorageTerminalMenu)this.menu).freeCount == 32767
                        ? Component.translatable("tooltip.toms_storage.terminal_stat.alot")
                        : ((StorageTerminalMenu)this.menu).freeCount,
                     ((StorageTerminalMenu)this.menu).freeCount != 32767 && ((StorageTerminalMenu)this.menu).slotCount != 32767
                        ? Math.round((float)((StorageTerminalMenu)this.menu).freeCount / ((StorageTerminalMenu)this.menu).slotCount * 100.0F)
                        : "?"
                  }
               ),
               Component.translatable("tooltip.toms_storage.terminal_stat.unique", new Object[]{((StorageTerminalMenu)this.menu).itemList.size()})
            ),
            mouseX,
            mouseY
         );
      }

      if (this.popup.render(st, this.font, mouseX, mouseY)) {
         if (((StorageTerminalMenu)this.menu).getCarried().isEmpty() && this.slotIDUnderMouse != -1) {
            StorageTerminalMenu.SlotStorage slot = ((StorageTerminalMenu)this.getMenu()).storageSlotList.get(this.slotIDUnderMouse);
            if (slot.stack != null) {
               if (slot.stack.getQuantity() > 9999L || this.sortingType == SortingTypes.SPACE_EFFICIENCY) {
                  ClientUtil.setTooltip(
                     Component.translatable("tooltip.toms_storage.amount", new Object[]{slot.stack.getQuantity()}),
                     Component.translatable("tooltip.toms_storage.slots_used", new Object[]{slot.stack.getUsedSlotCount()})
                  );
               }

               try {
                  st.renderTooltip(this.font, slot.stack.getQuantity() == 0L ? slot.stack.getStack() : slot.stack.getActualStack(), mouseX, mouseY);
               } catch (IllegalStateException e) {
                  ClientUtil.setTooltip(Component.translatable("tooltip.toms_storage.amount", new Object[]{slot.stack.getQuantity()}));
                  st.renderTooltip(this.font, slot.stack.getStack(), mouseX, mouseY);
               }

               ClientUtil.setTooltip();
            }
         } else {
            this.renderTooltip(st, mouseX, mouseY);
         }
      } else {
         this.clearTooltipForNextRenderPass();
      }
   }

   protected void renderLabels(GuiGraphics st, int mouseX, int mouseY) {
      super.renderLabels(st, mouseX, mouseY);
      st.drawString(this.font, "i", 180, 6, 4210752, false);
      st.pose().pushPose();
      this.slotIDUnderMouse = this.drawSlots(st, mouseX, mouseY);
      st.pose().popPose();
   }

   protected int drawSlots(GuiGraphics st, int mouseX, int mouseY) {
      StorageTerminalMenu term = (StorageTerminalMenu)this.getMenu();
      int slotHover = -1;

      for (int i = 0; i < term.storageSlotList.size(); i++) {
         if (this.drawSlot(st, term.storageSlotList.get(i), mouseX, mouseY)) {
            slotHover = i;
         }
      }

      return slotHover;
   }

   protected boolean drawSlot(GuiGraphics st, StorageTerminalMenu.SlotStorage slot, int mouseX, int mouseY) {
      if (slot.stack != null) {
         try {
            ItemStack stack = slot.stack.getStack().copyWithCount(1);
            int i = slot.xDisplayPosition;
            int j = slot.yDisplayPosition;
            st.renderItem(stack, i, j);
            st.renderItemDecorations(this.font, stack, i, j);
            this.drawStackSize(st, this.getFont(), slot.stack.getQuantity(), i, j);
         } catch (Exception e) {
            CrashReport report = CrashReport.forThrowable(e, "Rendering item stack in terminal");
            report.addCategory("Item details:")
               .setDetail("Item Id", () -> BuiltInRegistries.ITEM.getKey(slot.stack.getStack().getItem()).toString())
               .setDetail("Item Count", slot.stack.getQuantity())
               .setDetail("Item Components", () -> (String)componentCache.get(slot.stack));
            throw new ReportedException(report);
         }
      }

      if (mouseX >= this.getGuiLeft() + slot.xDisplayPosition - 1
         && mouseY >= this.getGuiTop() + slot.yDisplayPosition - 1
         && mouseX < this.getGuiLeft() + slot.xDisplayPosition + 17
         && mouseY < this.getGuiTop() + slot.yDisplayPosition + 17) {
         int l = slot.xDisplayPosition;
         int t = slot.yDisplayPosition;
         renderSlotHighlight(st, l, t, 0);
         return true;
      } else {
         return false;
      }
   }

   private void drawStackSize(GuiGraphics st, Font fr, long size, int x, int y) {
      float scaleFactor = 0.6F;
      RenderSystem.disableDepthTest();
      RenderSystem.disableBlend();
      String stackSize = NumberFormatUtil.formatNumber(size);
      st.pose().pushPose();
      st.pose().scale(scaleFactor, scaleFactor, scaleFactor);
      st.pose().translate(0.0F, 0.0F, 450.0F);
      float inverseScaleFactor = 1.0F / scaleFactor;
      int X = (int)((x + 0.0F + 16.0F - fr.width(stackSize) * scaleFactor) * inverseScaleFactor);
      int Y = (int)((y + 0.0F + 16.0F - 7.0F * scaleFactor) * inverseScaleFactor);
      st.drawString(fr, stackSize, X, Y, size == 0L ? 16776960 : 16777215, true);
      st.pose().popPose();
      RenderSystem.enableDepthTest();
   }

   protected boolean needsScrollBars() {
      return ((StorageTerminalMenu)this.getMenu()).itemListClientSorted.size() > this.rowCount * 9;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
      if (this.popup.mouseClick(mouseX, mouseY, mouseButton)) {
         return true;
      }

      if (this.slotIDUnderMouse > -1) {
         if (this.isPullOne(mouseButton)) {
            if (((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack != null
               && ((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack.getQuantity() > 0L) {
               this.storageSlotClick(
                  ((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack,
                  TerminalSyncManager.SlotAction.PULL_ONE,
                  this.isTransferOne(mouseButton)
               );
               return true;
            }

            return true;
         }

         if (this.pullHalf(mouseButton)) {
            if (!((StorageTerminalMenu)this.menu).getCarried().isEmpty()) {
               this.storageSlotClick(null, hasControlDown() ? TerminalSyncManager.SlotAction.GET_QUARTER : TerminalSyncManager.SlotAction.GET_HALF, false);
            } else if (((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack != null
               && ((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack.getQuantity() > 0L) {
               this.storageSlotClick(
                  ((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack,
                  hasControlDown() ? TerminalSyncManager.SlotAction.GET_QUARTER : TerminalSyncManager.SlotAction.GET_HALF,
                  false
               );
               return true;
            }
         } else if (this.pullNormal(mouseButton)) {
            if (!((StorageTerminalMenu)this.menu).getCarried().isEmpty()) {
               this.storageSlotClick(null, TerminalSyncManager.SlotAction.PULL_OR_PUSH_STACK, false);
            } else if (((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack != null
               && ((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack.getQuantity() > 0L) {
               this.storageSlotClick(
                  ((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack,
                  hasShiftDown() ? TerminalSyncManager.SlotAction.SHIFT_PULL : TerminalSyncManager.SlotAction.PULL_OR_PUSH_STACK,
                  false
               );
               return true;
            }
         }
      } else if (GLFW.glfwGetKey(this.minecraft.getWindow().getWindow(), 32) != 0) {
         this.storageSlotClick(null, TerminalSyncManager.SlotAction.SPACE_CLICK, false);
      } else {
         if (!this.isHovering(this.searchField.getX() - this.leftPos, this.searchField.getY() - this.topPos, 89, 9, mouseX, mouseY)) {
            this.searchField.setFocused(false);
            return super.mouseClicked(mouseX, mouseY, mouseButton);
         }

         if (mouseButton != 1) {
            return super.mouseClicked(mouseX, mouseY, mouseButton);
         }

         this.searchField.setValue("");
      }

      return true;
   }

   protected void storageSlotClick(StoredItemStack slotStack, TerminalSyncManager.SlotAction act, boolean mod) {
      ((StorageTerminalMenu)this.menu).sync.sendInteract(slotStack, act, mod);
   }

   public boolean isPullOne(int mouseButton) {
      switch (this.ctrlm()) {
         case AE:
            return mouseButton == 1 && hasShiftDown();
         case RS:
            return mouseButton == 2;
         case DEF:
            return mouseButton == 1 && !((StorageTerminalMenu)this.menu).getCarried().isEmpty();
         default:
            return false;
      }
   }

   public boolean isTransferOne(int mouseButton) {
      switch (this.ctrlm()) {
         case AE:
            return hasShiftDown() && hasControlDown();
         case RS:
            return hasShiftDown() && mouseButton == 2;
         case DEF:
            return mouseButton == 1 && hasShiftDown();
         default:
            return false;
      }
   }

   public boolean pullHalf(int mouseButton) {
      switch (this.ctrlm()) {
         case AE:
            return mouseButton == 1;
         case RS:
            return mouseButton == 1;
         case DEF:
            return mouseButton == 1 && ((StorageTerminalMenu)this.menu).getCarried().isEmpty();
         default:
            return false;
      }
   }

   public boolean pullNormal(int mouseButton) {
      switch (this.ctrlm()) {
         case AE:
         case RS:
         case DEF:
            return mouseButton == 0;
         default:
            return false;
      }
   }

   private AbstractStorageTerminalScreen.ControllMode ctrlm() {
      return AbstractStorageTerminalScreen.ControllMode.VALUES[this.controllMode];
   }

   public Font getFont() {
      return this.font;
   }

   public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
      if (this.popup.keyPressed(pKeyCode, pScanCode, pModifiers)) {
         return true;
      } else if (pKeyCode == 256) {
         this.onClose();
         return true;
      } else if (pKeyCode == 258) {
         return super.keyPressed(pKeyCode, pScanCode, pModifiers);
      } else {
         return !this.searchField.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.searchField.canConsumeInput()
            ? super.keyPressed(pKeyCode, pScanCode, pModifiers)
            : true;
      }
   }

   public boolean charTyped(char pCodePoint, int pModifiers) {
      if (this.popup.charTyped(pCodePoint, pModifiers)) {
         return true;
      } else {
         return this.searchField.charTyped(pCodePoint, pModifiers) ? true : super.charTyped(pCodePoint, pModifiers);
      }
   }

   public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double xd, double p_mouseScrolled_5_) {
      if (!this.needsScrollBars()) {
         return false;
      }

      int i = (((StorageTerminalMenu)this.menu).itemListClientSorted.size() + 9 - 1) / 9 - 5;
      this.currentScroll = (float)(this.currentScroll - p_mouseScrolled_5_ / i);
      this.currentScroll = Mth.clamp(this.currentScroll, 0.0F, 1.0F);
      ((StorageTerminalMenu)this.menu).scrollTo(this.currentScroll);
      return true;
   }

   public abstract ResourceLocation getGui();

   protected void renderBg(GuiGraphics st, float partialTicks, int mouseX, int mouseY) {
      if (this.tallMode) {
         st.blit(this.getGui(), this.leftPos, this.topPos, 0, 0, this.imageWidth, this.slotStartY);
         int guiStart = this.textureSlotCount * 18 + this.slotStartY;
         int guiRStart = this.rowCount * 18 + this.slotStartY;
         int guiSize = this.guiHeight - this.textureSlotCount * 18 - this.slotStartY;
         st.blit(this.getGui(), this.leftPos, this.topPos + guiRStart, 0, guiStart, this.imageWidth, guiSize);
         int scrollbarW = 25;
         st.blit(this.getGui(), this.leftPos, this.topPos + this.slotStartY, 0, this.slotStartY, this.slotStartX + 162 + scrollbarW, 18);

         for (int i = 1; i < this.rowCount - 1; i++) {
            st.blit(this.getGui(), this.leftPos, this.topPos + this.slotStartY + i * 18, 0, this.slotStartY + 18, this.slotStartX + 162 + scrollbarW, 18);
         }

         st.blit(
            this.getGui(),
            this.leftPos,
            this.topPos + this.slotStartY + (this.rowCount - 1) * 18,
            0,
            this.slotStartY + (this.textureSlotCount - 1) * 18,
            this.slotStartX + 162 + scrollbarW,
            18
         );
      } else {
         st.blit(this.getGui(), this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      }

      Slot offh = ((StorageTerminalMenu)this.getMenu()).offhand;
      st.blitSprite(FLOATING_SLOT, this.leftPos + offh.x - 8, this.topPos + offh.y - 8, 32, 32);
   }

   protected void onUpdateSearch(String text) {
   }

   @Override
   public void receive(CompoundTag tag) {
      ((StorageTerminalMenu)this.menu).receiveClientNBTPacket(tag);
      this.refreshItemList = true;
   }

   public Slot getSlotUnderMouse() {
      Slot s = super.getSlotUnderMouse();
      if (s != null) {
         return s;
      } else if (this.slotIDUnderMouse > -1 && ((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack != null) {
         this.fakeSlotUnderMouse.container.setItem(0, ((StorageTerminalMenu)this.getMenu()).getSlotByID(this.slotIDUnderMouse).stack.getStack());
         return this.fakeSlotUnderMouse;
      } else {
         return null;
      }
   }

   public int getRowCount() {
      return this.rowCount;
   }

   public boolean isSmartItemSearchOn() {
      return (this.buttonSearchType.getSearchType() & 8) == 0;
   }

   @Override
   public void getExclusionAreas(Consumer<IScreen.Box> consumer) {
      consumer.accept(new IScreen.Box(this.leftPos - 30, this.topPos, 30, this.imageHeight));
   }

   public enum ControllMode {
      AE,
      RS,
      DEF;

      public static final AbstractStorageTerminalScreen.ControllMode[] VALUES = values();
   }

   private static class FakeSlot extends Slot {
      private static final Container DUMMY = new SimpleContainer(1);

      public FakeSlot() {
         super(DUMMY, 0, Integer.MIN_VALUE, Integer.MIN_VALUE);
      }

      public boolean allowModification(Player p_150652_) {
         return false;
      }

      public void set(ItemStack p_40240_) {
      }

      public ItemStack remove(int p_40227_) {
         return ItemStack.EMPTY;
      }
   }
}
