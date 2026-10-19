package com.tensura_guild.client.screen;

import com.tensura_guild.data.GuildLevelData;
import com.tensura_guild.data.GuildReputationShopService;
import com.tensura_guild.network.BuyGuildShopItemPayload;
import com.tensura_guild.network.CloseDialoguePayload;
import com.tensura_guild.network.GuildShopStatePayload;
import com.tensura_guild.network.RequestGuildShopStatePayload;
import com.tensura_guild.registry.GuildAttachments;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.item.misc.MagicTomeItem;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;

public class GuildShopScreen extends Screen {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/shop_gui.png");
   private static final ResourceLocation RANK_TAB_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/task_rank_tab.png");
   private static final ResourceLocation MAGIC_TOME = ResourceLocation.fromNamespaceAndPath("tensura", "magic_tome");
   private static final int TEXTURE_SIZE = 256;
   private static final int RANK_TAB_TEXTURE_SIZE = 64;
   private static final GuildShopScreen.Rect RANK_TAB_DEFAULT_SRC = new GuildShopScreen.Rect(0, 0, 17, 11);
   private static final GuildShopScreen.Rect RANK_TAB_HOVER_SRC = new GuildShopScreen.Rect(0, 12, 27, 23);
   private static final GuildShopScreen.Rect RANK_TAB_LETTER_DEST = new GuildShopScreen.Rect(9, 2, 14, 10);
   private static final int RANK_TAB_LETTER_SOURCE_X = 2;
   private static final int RANK_TAB_LETTER_SOURCE_Y = 26;
   private static final int RANK_TAB_LETTER_SOURCE_WIDTH = 5;
   private static final int RANK_TAB_LETTER_SOURCE_HEIGHT = 8;
   private static final int RANK_TAB_LETTER_GAP = 2;
   private static final int RANK_TAB_START_Y = 49;
   private static final int RANK_TAB_GAP = 1;
   private static final int DIGIT_BASE_HEIGHT = 9;
   private static final int DIGIT_DRAW_GAP = 1;
   private static final String DIGITS = "1234567890";
   private static final float SHOP_SLOT_BASE_SCALE = 0.9F;
   private static final float SHOP_SLOT_HOVER_SCALE = 0.96F;
   private static final float SHOP_ITEM_NAME_MAX_SCALE = 0.85F;
   private static final int SHOP_SLOT_LIMIT_ANIMATION_TICKS = 5;
   private static final float SHOP_SLOT_LIMIT_TINT_ALPHA = 0.25F;
   private static final GuildShopScreen.Rect[] DIGIT_SOURCES = new GuildShopScreen.Rect[]{
      new GuildShopScreen.Rect(23, 165, 28, 173),
      new GuildShopScreen.Rect(30, 166, 35, 173),
      new GuildShopScreen.Rect(37, 165, 42, 173),
      new GuildShopScreen.Rect(44, 165, 49, 173),
      new GuildShopScreen.Rect(51, 165, 56, 173),
      new GuildShopScreen.Rect(58, 165, 64, 173),
      new GuildShopScreen.Rect(66, 165, 71, 173),
      new GuildShopScreen.Rect(73, 166, 78, 173),
      new GuildShopScreen.Rect(80, 166, 85, 173),
      new GuildShopScreen.Rect(87, 166, 93, 173)
   };
   private static final int IMAGE_WIDTH = 256;
   private static final int IMAGE_HEIGHT = 163;
   private static final float GUI_SCALE = 1.15F;
   private static final GuildShopScreen.Rect COIN_AMOUNT = new GuildShopScreen.Rect(22, 34, 63, 43);
   private static final GuildShopScreen.Rect REFRESH_COUNTDOWN = new GuildShopScreen.Rect(201, 34, 233, 43);
   private static final GuildShopScreen.Rect SHOP_SLOT_SRC = new GuildShopScreen.Rect(2, 175, 76, 216);
   private static final GuildShopScreen.Rect ITEM_ICON_OFFSET = new GuildShopScreen.Rect(4, 4, 20, 20);
   private static final GuildShopScreen.Rect COST_TEXT_OFFSET = new GuildShopScreen.Rect(43, 8, 72, 16);
   private static final GuildShopScreen.Rect NAME_TEXT_OFFSET = new GuildShopScreen.Rect(6, 25, 72, 40);
   private static final GuildShopScreen.Rect[] SHOP_SLOTS = new GuildShopScreen.Rect[]{
      new GuildShopScreen.Rect(10, 49, 84, 90),
      new GuildShopScreen.Rect(85, 49, 159, 90),
      new GuildShopScreen.Rect(160, 49, 234, 90),
      new GuildShopScreen.Rect(10, 102, 84, 143),
      new GuildShopScreen.Rect(85, 102, 159, 143),
      new GuildShopScreen.Rect(160, 102, 234, 143)
   };
   private int leftPos;
   private int topPos;
   private final int[] limitAnimationTicks = new int[SHOP_SLOTS.length];
   private List<GuildReputationShopService.ShopEntry> syncedEntries = List.of();
   private List<Integer> syncedPurchasedCounts = List.of();
   private int syncedRankLevel = -1;
   private int requestedRankLevel = -1;
   private long nextRefreshGameTime = -1L;
   private String selectedRank = GuildLevelData.rankForLevel(1);
   private final int dialogueEntityId;
   private boolean dialogueReleased;

   public GuildShopScreen() {
      this(-1);
   }

   public GuildShopScreen(int dialogueEntityId) {
      super(Component.empty());
      this.dialogueEntityId = dialogueEntityId;
   }

   protected void init() {
      this.leftPos = Math.round((this.width / 1.15F - 256.0F) / 2.0F);
      this.topPos = Math.round((this.height / 1.15F - 163.0F) / 2.0F);
      this.requestSelectedShopState();
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.ensureSelectedRankUnlocked();
      int logicalMouseX = this.toLogicalMouse(mouseX);
      int logicalMouseY = this.toLogicalMouse(mouseY);
      graphics.pose().pushPose();

      try {
         graphics.pose().scale(1.15F, 1.15F, 1.0F);
         graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0.0F, 0.0F, 256, 163, 256, 256);
         this.renderRankTabs(graphics, logicalMouseX, logicalMouseY);
         this.renderCoinAmount(graphics);
         this.renderRefreshCountdown(graphics);
         this.renderShopEntries(graphics, logicalMouseX, logicalMouseY);
      } finally {
         graphics.pose().popPose();
      }

      super.render(graphics, mouseX, mouseY, partialTick);
   }

   private void renderRankTabs(GuiGraphics graphics, int mouseX, int mouseY) {
      List<String> ranks = this.visibleRankTabs();
      int localX = mouseX - this.leftPos;
      int localY = mouseY - this.topPos;

      for (int i = 0; i < ranks.size(); i++) {
         String rank = ranks.get(i);
         boolean hovered = this.rankTabBounds(i).contains(localX, localY);
         boolean selected = rank.equals(this.selectedRank);
         GuildShopScreen.Rect source = !hovered && !selected ? RANK_TAB_DEFAULT_SRC : RANK_TAB_HOVER_SRC;
         int drawX = 1 - source.width();
         int drawY = this.rankTabY(i);
         graphics.blit(RANK_TAB_TEXTURE, this.leftPos + drawX, this.topPos + drawY, source.left(), source.top(), source.width(), source.height(), 64, 64);
         this.renderRankLetter(graphics, rank, this.leftPos + drawX, this.topPos + drawY);
      }
   }

   private void renderRankLetter(GuiGraphics graphics, String rank, int tabX, int tabY) {
      int rankIndex = GuildLevelData.levelIndexForRank(rank) - 1;
      int sourceX = 2 + rankIndex * 7;
      graphics.blit(
         RANK_TAB_TEXTURE,
         tabX + RANK_TAB_LETTER_DEST.left(),
         tabY + RANK_TAB_LETTER_DEST.top(),
         RANK_TAB_LETTER_DEST.width(),
         RANK_TAB_LETTER_DEST.height(),
         sourceX,
         26.0F,
         5,
         8,
         64,
         64
      );
   }

   private void renderCoinAmount(GuiGraphics graphics) {
      int coins = this.minecraft != null && this.minecraft.player != null
         ? Math.max(0, (Integer)this.minecraft.player.getData(GuildAttachments.REPUTATION_COINS))
         : 0;
      this.renderDigitNumber(graphics, coins, COIN_AMOUNT, false);
   }

   private void renderRefreshCountdown(GuiGraphics graphics) {
      int daysRemaining = this.syncedRefreshDaysRemaining();
      this.renderFittedText(
         graphics,
         Component.translatable("screen.tensura_guild.shop.refresh_countdown", new Object[]{daysRemaining}).getString(),
         REFRESH_COUNTDOWN,
         1.0F,
         -13950696
      );
   }

   private void renderShopEntries(GuiGraphics graphics, int mouseX, int mouseY) {
      List<GuildReputationShopService.ShopEntry> entries = this.currentShopEntries();

      for (int i = 0; i < SHOP_SLOTS.length; i++) {
         GuildShopScreen.Rect slot = SHOP_SLOTS[i];
         boolean hovered = this.isMouseOver(mouseX, mouseY, slot);
         float scale = hovered ? 0.96F : 0.9F;
         this.renderScaledShopSlot(graphics, slot, i, i < entries.size() ? entries.get(i) : null, scale, this.limitAnimationTicks[i]);
      }
   }

   private void renderScaledShopSlot(
      GuiGraphics graphics, GuildShopScreen.Rect slot, int slotIndex, GuildReputationShopService.ShopEntry entry, float scale, int limitTicks
   ) {
      float centerX = this.leftPos + slot.left() + slot.width() / 2.0F;
      float centerY = this.topPos + slot.top() + slot.height() / 2.0F;
      int shakeX = this.limitShakeOffset(limitTicks);
      graphics.pose().pushPose();

      try {
         graphics.pose().translate(centerX, centerY, 0.0F);
         graphics.pose().scale(scale, scale, 1.0F);
         graphics.pose().translate(-centerX + shakeX, -centerY, 0.0F);
         this.renderShopSlot(graphics, slot, slotIndex, entry, limitTicks);
      } finally {
         graphics.pose().popPose();
      }
   }

   private int limitShakeOffset(int ticks) {
      if (ticks <= 0) {
         return 0;
      }

      int elapsed = 5 - ticks;

      return switch (elapsed % 4) {
         case 0 -> -1;
         case 1 -> 1;
         default -> 0;
      };
   }

   private void renderLimitOverlay(GuiGraphics graphics, GuildShopScreen.Rect slot) {
      graphics.setColor(1.0F, 0.45F, 0.45F, 0.25F);

      try {
         graphics.blit(
            TEXTURE, this.leftPos + slot.left(), this.topPos + slot.top(), SHOP_SLOT_SRC.left(), SHOP_SLOT_SRC.top(), slot.width(), slot.height(), 256, 256
         );
      } finally {
         graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private void renderShopSlot(GuiGraphics graphics, GuildShopScreen.Rect slot, int slotIndex, GuildReputationShopService.ShopEntry entry, int limitTicks) {
      graphics.blit(
         TEXTURE, this.leftPos + slot.left(), this.topPos + slot.top(), SHOP_SLOT_SRC.left(), SHOP_SLOT_SRC.top(), slot.width(), slot.height(), 256, 256
      );
      if (limitTicks > 0) {
         this.renderLimitOverlay(graphics, slot);
      }

      if (entry != null) {
         int multiplier = this.displayMultiplier(slotIndex, entry);
         int displayCount = entry.count() * multiplier;
         int displayCost = entry.cost() * multiplier;
         Item item = (Item)BuiltInRegistries.ITEM.get(entry.item());
         if (item != Items.AIR) {
            ItemStack stack = this.displayStack(entry, item);
            int iconX = this.leftPos + slot.left() + ITEM_ICON_OFFSET.left();
            int iconY = this.topPos + slot.top() + ITEM_ICON_OFFSET.top();
            graphics.renderItem(stack, iconX, iconY);
            if (this.isSoldOut(slotIndex, entry)) {
               this.renderSoldOutName(graphics, slot);
            } else {
               this.renderItemName(graphics, entry, stack, displayCount, slot);
            }
         } else if (this.isSoldOut(slotIndex, entry)) {
            this.renderSoldOutName(graphics, slot);
         } else {
            this.renderMissingItemName(graphics, entry.item().toString(), displayCount, slot);
         }

         this.renderDigitNumber(graphics, displayCost, slot.offset(COST_TEXT_OFFSET), false);
      }
   }

   private boolean isSoldOut(int slotIndex, GuildReputationShopService.ShopEntry entry) {
      return entry != null && entry.maxCount() > 0 && this.purchasedCount(slotIndex) >= entry.maxCount();
   }

   private void renderItemName(GuiGraphics graphics, GuildReputationShopService.ShopEntry entry, ItemStack stack, int count, GuildShopScreen.Rect slot) {
      if (!entry.displayName().isEmpty() && entry.magicTomeSkill().isEmpty()) {
         this.renderFittedName(graphics, Component.translatable(entry.displayName(), new Object[]{Math.max(0, count)}).getString(), slot);
      } else {
         this.renderFittedName(
            graphics,
            Component.translatable("screen.tensura_guild.shop.item_description", new Object[]{this.displayStackName(entry, stack), Math.max(0, count)})
               .getString(),
            slot
         );
      }
   }

   private ItemStack displayStack(GuildReputationShopService.ShopEntry entry, Item item) {
      if (entry.item().equals(MAGIC_TOME) && entry.magicTomeSkill().isPresent()) {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(entry.magicTomeSkill().get());
         if (skill != null) {
            return MagicTomeItem.createForMagic(skill);
         }
      }

      return new ItemStack(item, entry.count());
   }

   private Component displayStackName(GuildReputationShopService.ShopEntry entry, ItemStack stack) {
      if (entry.item().equals(MAGIC_TOME) && entry.magicTomeSkill().isPresent()) {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(entry.magicTomeSkill().get());
         if (skill != null) {
            return skill.getChatDisplayName(false);
         }
      }

      return stack.getHoverName();
   }

   private void renderMissingItemName(GuiGraphics graphics, String itemId, int count, GuildShopScreen.Rect slot) {
      this.renderFittedName(
         graphics, Component.translatable("screen.tensura_guild.shop.item_description", new Object[]{itemId, Math.max(0, count)}).getString(), slot
      );
   }

   private void renderSoldOutName(GuiGraphics graphics, GuildShopScreen.Rect slot) {
      GuildShopScreen.Rect area = slot.offset(NAME_TEXT_OFFSET);
      this.renderCenteredFittedText(graphics, Component.translatable("screen.tensura_guild.shop.sold_out").getString(), area, 0.85F, -1754827);
   }

   private void renderFittedName(GuiGraphics graphics, String value, GuildShopScreen.Rect slot) {
      GuildShopScreen.Rect area = slot.offset(NAME_TEXT_OFFSET);
      this.renderFittedText(graphics, value, area, 0.85F, -13950696);
   }

   private void renderFittedText(GuiGraphics graphics, String text, GuildShopScreen.Rect area, float maxScale, int color) {
      int textWidth = this.font.width(text);
      if (textWidth > 0 && area.width() > 0 && area.height() > 0) {
         int x = this.leftPos + area.left();
         int y = this.topPos + area.top();
         float scale = Math.min(maxScale, Math.min((float)area.width() / textWidth, area.height() / 9.0F));
         this.enableScaledScissor(
            graphics, this.leftPos + area.left(), this.topPos + area.top(), this.leftPos + area.right() + 1, this.topPos + area.bottom() + 1
         );
         graphics.pose().pushPose();

         try {
            graphics.pose().translate(x, y, 0.0F);
            graphics.pose().scale(scale, scale, 1.0F);
            graphics.drawString(this.font, text, 0, 0, color, false);
         } finally {
            graphics.pose().popPose();
            graphics.disableScissor();
         }
      }
   }

   private void renderCenteredFittedText(GuiGraphics graphics, String text, GuildShopScreen.Rect area, float maxScale, int color) {
      int textWidth = this.font.width(text);
      if (textWidth > 0 && area.width() > 0 && area.height() > 0) {
         float scale = Math.min(maxScale, Math.min((float)area.width() / textWidth, area.height() / 9.0F));
         int scaledWidth = (int)Math.ceil(textWidth * scale);
         int scaledHeight = (int)Math.ceil(9.0F * scale);
         int x = this.leftPos + area.left() + Math.max(0, (area.width() - scaledWidth) / 2);
         int y = this.topPos + area.top() + Math.max(0, (area.height() - scaledHeight) / 2);
         this.enableScaledScissor(
            graphics, this.leftPos + area.left(), this.topPos + area.top(), this.leftPos + area.right() + 1, this.topPos + area.bottom() + 1
         );
         graphics.pose().pushPose();

         try {
            graphics.pose().translate(x, y, 0.0F);
            graphics.pose().scale(scale, scale, 1.0F);
            graphics.drawString(this.font, text, 0, 0, color, false);
         } finally {
            graphics.pose().popPose();
            graphics.disableScissor();
         }
      }
   }

   private void renderDigitNumber(GuiGraphics graphics, int amount, GuildShopScreen.Rect area, boolean rightAligned) {
      String text = Integer.toString(Math.max(0, amount));
      int textWidth = this.digitTextWidth(text);
      if (textWidth > 0 && area.width() > 0 && area.height() > 0) {
         float scale = Math.min(1.0F, Math.min((float)area.width() / textWidth, area.height() / 9.0F));
         int scaledWidth = (int)Math.ceil(textWidth * scale);
         int scaledHeight = (int)Math.ceil(9.0F * scale);
         int x = rightAligned ? this.leftPos + area.left() + Math.max(0, area.width() - scaledWidth) : this.leftPos + area.left();
         int y = this.topPos + area.top() + Math.max(0, (area.height() - scaledHeight) / 2);
         this.enableScaledScissor(
            graphics, this.leftPos + area.left(), this.topPos + area.top(), this.leftPos + area.right() + 1, this.topPos + area.bottom() + 1
         );
         graphics.pose().pushPose();

         try {
            graphics.pose().translate(x, y, 0.0F);
            graphics.pose().scale(scale, scale, 1.0F);
            int localX = 0;

            for (int i = 0; i < text.length(); i++) {
               int digitIndex = "1234567890".indexOf(text.charAt(i));
               if (digitIndex >= 0) {
                  GuildShopScreen.Rect source = DIGIT_SOURCES[digitIndex];
                  graphics.blit(TEXTURE, localX, 9 - source.height(), source.left(), source.top(), source.width(), source.height(), 256, 256);
                  localX += source.width() + 1;
               }
            }
         } finally {
            graphics.pose().popPose();
            graphics.disableScissor();
         }
      }
   }

   private int digitTextWidth(String text) {
      int width = 0;

      for (int i = 0; i < text.length(); i++) {
         int digitIndex = "1234567890".indexOf(text.charAt(i));
         if (digitIndex >= 0) {
            if (width > 0) {
               width++;
            }

            width += DIGIT_SOURCES[digitIndex].width();
         }
      }

      return width;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      double logicalMouseX = this.toLogicalMouse(mouseX);
      double logicalMouseY = this.toLogicalMouse(mouseY);
      int localX = (int)logicalMouseX - this.leftPos;
      int localY = (int)logicalMouseY - this.topPos;
      if (this.handleRankTabClick(localX, localY)) {
         ScreenHelper.clicked();
         return true;
      }

      for (int i = 0; i < SHOP_SLOTS.length; i++) {
         if (this.isMouseOver(logicalMouseX, logicalMouseY, SHOP_SLOTS[i])) {
            List<GuildReputationShopService.ShopEntry> entries = this.currentShopEntries();
            if (i >= entries.size()) {
               return true;
            }

            PacketDistributor.sendToServer(
               new BuyGuildShopItemPayload(GuildLevelData.levelIndexForRank(this.selectedRank), i, this.displayMultiplier(i, entries.get(i))),
               new CustomPacketPayload[0]
            );
            ScreenHelper.clicked();
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public void tick() {
      super.tick();

      for (int i = 0; i < this.limitAnimationTicks.length; i++) {
         if (this.limitAnimationTicks[i] > 0) {
            this.limitAnimationTicks[i]--;
         }
      }

      if (this.nextRefreshGameTime > 0L && this.currentGameTime() >= this.nextRefreshGameTime) {
         this.nextRefreshGameTime = -1L;
         this.requestSelectedShopState();
      }
   }

   public void handleLimitReached(int rankLevel, int slot) {
      if (slot >= 0 && slot < this.limitAnimationTicks.length) {
         if (GuildLevelData.levelIndexForRank(this.selectedRank) == rankLevel) {
            this.limitAnimationTicks[slot] = 5;
         }
      }
   }

   public void handleShopState(GuildShopStatePayload payload) {
      if (payload.rankLevel() == GuildLevelData.levelIndexForRank(this.selectedRank)) {
         this.syncedRankLevel = payload.rankLevel();
         this.requestedRankLevel = -1;
         this.nextRefreshGameTime = payload.nextRefreshGameTime();
         this.syncedEntries = List.copyOf(payload.entries());
         this.syncedPurchasedCounts = List.copyOf(payload.purchasedCounts());
      }
   }

   public void handleShopRefresh() {
      this.syncedEntries = List.of();
      this.syncedPurchasedCounts = List.of();
      this.syncedRankLevel = -1;
      this.requestedRankLevel = -1;
      this.nextRefreshGameTime = -1L;
      this.requestSelectedShopState();
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
   }

   protected void renderBlurredBackground(float partialTick) {
   }

   public boolean isPauseScreen() {
      return false;
   }

   public void onClose() {
      this.releaseDialogueLock();
      super.onClose();
   }

   private void releaseDialogueLock() {
      if (!this.dialogueReleased && this.dialogueEntityId >= 0) {
         this.dialogueReleased = true;
         PacketDistributor.sendToServer(new CloseDialoguePayload(this.dialogueEntityId), new CustomPacketPayload[0]);
      }
   }

   private int toLogicalMouse(int coordinate) {
      return Math.round(coordinate / 1.15F);
   }

   private double toLogicalMouse(double coordinate) {
      return coordinate / 1.15F;
   }

   private void enableScaledScissor(GuiGraphics graphics, int left, int top, int right, int bottom) {
      graphics.enableScissor((int)Math.floor(left * 1.15F), (int)Math.floor(top * 1.15F), (int)Math.ceil(right * 1.15F), (int)Math.ceil(bottom * 1.15F));
   }

   private boolean isMouseOver(double mouseX, double mouseY, GuildShopScreen.Rect rect) {
      return mouseX >= this.leftPos + rect.left()
         && mouseX <= this.leftPos + rect.right()
         && mouseY >= this.topPos + rect.top()
         && mouseY <= this.topPos + rect.bottom();
   }

   private boolean handleRankTabClick(int localX, int localY) {
      List<String> ranks = this.visibleRankTabs();

      for (int i = 0; i < ranks.size(); i++) {
         if (this.rankTabBounds(i).contains(localX, localY)) {
            this.selectedRank = ranks.get(i);
            this.syncedEntries = List.of();
            this.syncedPurchasedCounts = List.of();
            this.syncedRankLevel = -1;
            this.nextRefreshGameTime = -1L;
            this.requestSelectedShopState();
            return true;
         }
      }

      return false;
   }

   private GuildShopScreen.Rect rankTabBounds(int index) {
      int top = this.rankTabY(index);
      return new GuildShopScreen.Rect(1 - RANK_TAB_HOVER_SRC.width(), top, 0, top + RANK_TAB_HOVER_SRC.height() - 1);
   }

   private int rankTabY(int index) {
      return 49 + index * (RANK_TAB_HOVER_SRC.height() + 1);
   }

   private void ensureSelectedRankUnlocked() {
      String normalizedRank = GuildLevelData.normalizeRank(this.selectedRank);
      List<String> ranks = this.visibleRankTabs();
      this.selectedRank = ranks.contains(normalizedRank) ? normalizedRank : GuildLevelData.rankForLevel(1);
   }

   private List<String> visibleRankTabs() {
      return this.minecraft != null && this.minecraft.player != null
         ? GuildLevelData.ranksUpToLevel(((GuildLevelData)this.minecraft.player.getData(GuildAttachments.GUILD_LEVEL)).levelIndex())
         : List.of(GuildLevelData.rankForLevel(1));
   }

   private List<GuildReputationShopService.ShopEntry> currentShopEntries() {
      int selectedRankLevel = GuildLevelData.levelIndexForRank(this.selectedRank);
      if (this.syncedRankLevel == selectedRankLevel) {
         return this.syncedEntries;
      }

      this.requestSelectedShopState();
      return List.of();
   }

   private void requestSelectedShopState() {
      int rankLevel = GuildLevelData.levelIndexForRank(this.selectedRank);
      if (this.minecraft != null && this.minecraft.player != null && this.requestedRankLevel != rankLevel) {
         this.requestedRankLevel = rankLevel;
         PacketDistributor.sendToServer(new RequestGuildShopStatePayload(rankLevel), new CustomPacketPayload[0]);
      }
   }

   private int syncedRefreshDaysRemaining() {
      if (this.nextRefreshGameTime <= 0L) {
         return GuildReputationShopService.refreshDaysRemaining(this.currentGameTime());
      }

      long ticksRemaining = Math.max(1L, this.nextRefreshGameTime - this.currentGameTime());
      return (int)Math.max(1L, (ticksRemaining + 24000L - 1L) / 24000L);
   }

   private int displayMultiplier(int slotIndex, GuildReputationShopService.ShopEntry entry) {
      if (!hasShiftDown()) {
         return 1;
      }

      int remainingLimit = Math.max(0, entry.maxCount() - this.purchasedCount(slotIndex));
      int maxByLimit = remainingLimit / Math.max(1, entry.count());
      int maxByCoins = entry.cost() > 0 ? this.currentCoins() / entry.cost() : 0;
      int maxPurchases = Math.min(10, Math.min(maxByLimit, maxByCoins));
      return Math.max(1, maxPurchases);
   }

   private int currentCoins() {
      return this.minecraft != null && this.minecraft.player != null
         ? Math.max(0, (Integer)this.minecraft.player.getData(GuildAttachments.REPUTATION_COINS))
         : 0;
   }

   private int purchasedCount(int slotIndex) {
      return slotIndex >= 0 && slotIndex < this.syncedPurchasedCounts.size() ? Math.max(0, this.syncedPurchasedCounts.get(slotIndex)) : 0;
   }

   private long currentGameTime() {
      return this.minecraft != null && this.minecraft.level != null ? this.minecraft.level.getGameTime() : 0L;
   }

   private record Rect(int left, int top, int right, int bottom) {
      private int width() {
         return this.right - this.left + 1;
      }

      private int height() {
         return this.bottom - this.top + 1;
      }

      private boolean contains(int x, int y) {
         return x >= this.left && x <= this.right && y >= this.top && y <= this.bottom;
      }

      private GuildShopScreen.Rect offset(GuildShopScreen.Rect offset) {
         return new GuildShopScreen.Rect(this.left + offset.left(), this.top + offset.top(), this.left + offset.right(), this.top + offset.bottom());
      }
   }
}
