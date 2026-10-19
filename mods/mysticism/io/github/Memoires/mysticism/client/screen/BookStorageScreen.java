package io.github.Memoires.mysticism.client.screen;

import dev.architectury.networking.NetworkManager;
import io.github.Memoires.mysticism.ability.subclass.IBookStorage;
import io.github.Memoires.mysticism.menu.BookStorageMenu;
import io.github.Memoires.mysticism.menu.slot.BookSlot;
import io.github.Memoires.mysticism.network.c2s.RequestChangeEnchantmentLevelPacket;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.awt.Color;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class BookStorageScreen extends AbstractContainerScreen<BookStorageMenu> {
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("mysticism", "textures/gui/scholar.png");
   private final Player player;
   private final int maxSize;
   private final int page;
   private int selectedBookSlot = -1;
   private int selectedMenuSlot = -1;
   private int overlayLevelDelta = 0;

   public BookStorageScreen(BookStorageMenu pMenu, Inventory pPlayerInventory, int maxSize, int page) {
      super(pMenu, pPlayerInventory, pMenu.getSkill().getName());
      this.player = pPlayerInventory.player;
      this.maxSize = maxSize;
      this.page = page;
      this.imageWidth = 201;
      this.imageHeight = 211;
   }

   protected void init() {
      super.init();
   }

   public void render(GuiGraphics graphics, int x, int y, float f) {
      super.render(graphics, x, y, f);
      this.renderBookOverlay(graphics, x, y);
      this.renderTooltip(graphics, x, y);
   }

   protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
      RenderHelper.drawCenteredText(guiGraphics, this.font, this.title, this.imageWidth / 2, this.titleLabelY + 110, Color.WHITE.getRGB(), false);
   }

   protected void renderBg(GuiGraphics graphics, float f, int x, int y) {
      int width = (this.width - this.imageWidth) / 2;
      int height = (this.height - this.imageHeight) / 2;
      graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      if (this.page > 0) {
         boolean hovering = x >= width + 45 && x < width + 57 && y >= height + 113 && y < height + 125;
         if (hovering) {
            graphics.blit(BACKGROUND, width + 45, height + 113, 0, 213, 13, 13);
         }
      }

      if (this.page < (this.maxSize - 1) / 27) {
         boolean hovering = x >= width + 144 && x < width + 156 && y >= height + 113 && y < height + 125;
         if (hovering) {
            graphics.blit(BACKGROUND, width + 144, height + 113, 14, 213, 13, 13);
         }
      }
   }

   private void renderBookOverlay(GuiGraphics graphics, int x, int y) {
      if (this.selectedBookSlot != -1) {
         int width = (this.width - this.imageWidth) / 2;
         int height = (this.height - this.imageHeight) / 2;
         int row = this.selectedBookSlot / 9;
         int col = this.selectedBookSlot % 9;
         int slotX = 19 + col * 18;
         int slotY = 28 + row * 18;
         graphics.blit(BACKGROUND, width + slotX, height + slotY, 28, 213, 19, 19);
         graphics.pose().pushPose();
         graphics.pose().translate(0.0F, 0.0F, 300.0F);
         graphics.blit(BACKGROUND, width + slotX + 19, height + slotY, 47, 213, 72, 19);
         int btnX = width + slotX + 19;
         int btnY = height + slotY;
         boolean hoveringUpgrade = x >= btnX && x < btnX + 18 && y >= btnY && y < btnY + 19;
         if (hoveringUpgrade) {
            graphics.blit(BACKGROUND, btnX, btnY, 120, 212, 18, 19);
         }

         ItemStack book = ((BookStorageMenu)this.menu).getSlot(this.selectedMenuSlot).getItem();
         Entry<Holder<Enchantment>, Integer> entry = this.getFirstEnchantment(book);
         if (entry != null) {
            int currentLevel = entry.getValue();
            ManasSkillInstance instance = ((BookStorageMenu)this.menu).getSkillInstance(this.player);
            if (instance != null && instance.getSkill() instanceof IBookStorage bookStorage) {
               int maxLevel = ((BookStorageMenu)this.menu).getMaxAllowedLevel(bookStorage, entry.getKey(), entry.getKey().toString());
               int previewLevel = Math.max(1, Math.min(maxLevel, currentLevel + this.overlayLevelDelta));
               graphics.drawCenteredString(this.font, String.valueOf(previewLevel), btnX + 18 + 9, btnY + 6, 16777215);
            }
         }

         boolean hoveringDowngrade = x >= btnX + 36 && x < btnX + 54 && y >= btnY && y < btnY + 19;
         if (hoveringDowngrade) {
            graphics.blit(BACKGROUND, btnX + 36, btnY, 138, 212, 18, 19);
         }

         boolean hoveringConfirm = x >= btnX + 54 && x < btnX + 72 && y >= btnY && y < btnY + 19;
         if (hoveringConfirm) {
            graphics.blit(BACKGROUND, btnX + 54, btnY, 156, 212, 18, 19);
         }

         graphics.pose().popPose();
      }
   }

   protected void renderTooltip(GuiGraphics graphics, int x, int y) {
      if (!this.isMouseOverBookOverlay(x, y)) {
         super.renderTooltip(graphics, x, y);
      }

      int width = (this.width - this.imageWidth) / 2;
      int height = (this.height - this.imageHeight) / 2;
      if (x >= width + 45 && x < width + 57 && y >= height + 113 && y < height + 125 && this.page > 0) {
         graphics.renderTooltip(this.font, Component.translatable("tooltip.tensura.kiln.mixing_left"), x, y);
      } else if (x >= width + 144 && x < width + 156 && y >= height + 113 && y < height + 125 && this.page < (this.maxSize - 1) / 27) {
         graphics.renderTooltip(this.font, Component.translatable("tooltip.tensura.kiln.mixing_right"), x, y);
      }
   }

   private boolean isMouseOverBookOverlay(double mouseX, double mouseY) {
      if (this.selectedBookSlot == -1) {
         return false;
      }

      int row = this.selectedBookSlot / 9;
      int col = this.selectedBookSlot % 9;
      int slotX = this.leftPos + 19 + col * 18;
      int slotY = this.topPos + 28 + row * 18;
      return mouseX >= slotX + 19 && mouseX < slotX + 19 + 72 && mouseY >= slotY && mouseY < slotY + 19;
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (this.minecraft == null) {
         return false;
      }

      if (this.selectedBookSlot != -1) {
         if (this.isMouseOverBookOverlay(x, y)) {
            int row = this.selectedBookSlot / 9;
            int col = this.selectedBookSlot % 9;
            int slotX = this.leftPos + 19 + col * 18;
            int slotY = this.topPos + 28 + row * 18;
            int btnX = slotX + 19;
            if (x >= btnX && x < btnX + 18) {
               Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
               ItemStack book = ((BookStorageMenu)this.menu).getSlot(this.selectedMenuSlot).getItem();
               Entry<Holder<Enchantment>, Integer> entry = this.getFirstEnchantment(book);
               if (entry != null) {
                  int currentLevel = entry.getValue();
                  ManasSkillInstance instance = ((BookStorageMenu)this.menu).getSkillInstance(this.player);
                  if (instance == null) {
                     return false;
                  }

                  if (!(instance.getSkill() instanceof IBookStorage bookStorage)) {
                     return false;
                  }

                  int var26 = ((BookStorageMenu)this.menu).getMaxAllowedLevel(bookStorage, entry.getKey(), entry.getKey().toString());
                  if (currentLevel + this.overlayLevelDelta < var26) {
                     this.overlayLevelDelta++;
                  }
               }
            } else if (x >= btnX + 36 && x < btnX + 54) {
               Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
               ItemStack book = ((BookStorageMenu)this.menu).getSlot(this.selectedMenuSlot).getItem();
               Entry<Holder<Enchantment>, Integer> entry = this.getFirstEnchantment(book);
               if (entry != null) {
                  int currentLevel = entry.getValue();
                  if (currentLevel + this.overlayLevelDelta > 1) {
                     this.overlayLevelDelta--;
                  }
               }
            } else if (x >= btnX + 54 && x < btnX + 72) {
               Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
               NetworkManager.sendToServer(
                  new RequestChangeEnchantmentLevelPacket(((BookStorageMenu)this.menu).containerId, this.selectedMenuSlot, this.overlayLevelDelta)
               );
               System.out.println("Packet sent");
               this.overlayLevelDelta = 0;
               this.selectedBookSlot = -1;
            }

            return true;
         }

         if (button == 0) {
            this.selectedBookSlot = -1;
            this.selectedMenuSlot = -1;
            this.overlayLevelDelta = 0;
            return super.mouseClicked(x, y, button);
         }
      }

      if (button == 1) {
         if (this.hoveredSlot instanceof BookSlot bookSlot && bookSlot.hasItem() && bookSlot.getItem().getItem() instanceof EnchantedBookItem) {
            int slot = bookSlot.getContainerSlot();
            if (slot >= 0 && slot < 27) {
               this.selectedBookSlot = slot;
               this.selectedMenuSlot = bookSlot.index;
               return true;
            }
         }

         this.selectedBookSlot = -1;
         this.selectedMenuSlot = -1;
         this.overlayLevelDelta = 0;
      }

      int width = (this.width - this.imageWidth) / 2;
      int height = (this.height - this.imageHeight) / 2;
      if (x >= width + 45
         && x < width + 57
         && y >= height + 113
         && y < height + 125
         && this.page > 0
         && ((BookStorageMenu)this.menu).clickMenuButton(this.minecraft.player, 0)) {
         if (this.minecraft.gameMode == null) {
            return false;
         }

         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         this.minecraft.gameMode.handleInventoryButtonClick(((BookStorageMenu)this.menu).containerId, 0);
         return true;
      } else {
         if (!(x >= width + 144)
            || !(x < width + 156)
            || !(y >= height + 113)
            || !(y < height + 125)
            || this.page >= (this.maxSize - 1) / 27
            || !((BookStorageMenu)this.menu).clickMenuButton(this.minecraft.player, 1)) {
            return super.mouseClicked(x, y, button);
         }

         if (this.minecraft.gameMode == null) {
            return false;
         }

         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         this.minecraft.gameMode.handleInventoryButtonClick(((BookStorageMenu)this.menu).containerId, 1);
         return true;
      }
   }

   protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
      if (this.selectedBookSlot != -1) {
         this.selectedBookSlot = -1;
         this.selectedMenuSlot = -1;
      }

      super.slotClicked(slot, slotId, mouseButton, type);
   }

   public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
      if (this.minecraft != null) {
         if (this.minecraft.options.keyInventory.matches(pKeyCode, pScanCode)) {
            return true;
         }

         if (this.minecraft.options.keySwapOffhand.matches(pKeyCode, pScanCode)) {
            return true;
         }
      }

      return super.keyPressed(pKeyCode, pScanCode, pModifiers);
   }

   private Entry<Holder<Enchantment>, Integer> getFirstEnchantment(ItemStack book) {
      ItemEnchantments enchantments = (ItemEnchantments)book.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
      Iterator var3 = enchantments.entrySet().iterator();
      if (var3.hasNext()) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Holder<Enchantment>> entry = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Holder<Enchantment>>)var3.next();
         return Map.entry((Holder<Enchantment>)entry.getKey(), entry.getIntValue());
      } else {
         return null;
      }
   }
}
