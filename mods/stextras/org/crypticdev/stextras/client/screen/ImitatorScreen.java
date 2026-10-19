package org.crypticdev.stextras.client.screen;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import org.crypticdev.stextras.ability.skill.unique.ImitatorSkill;
import org.crypticdev.stextras.menu.ImitatorMenu;

public class ImitatorScreen extends AbstractContainerScreen<ImitatorMenu> {
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("stextras", "textures/gui/skill_imitator/skill_imitator.png");
   private static final ResourceLocation SKILL_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   private static final ResourceLocation SCROLL_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/scroll_bar.png");
   private float scrollOffset;
   private boolean scrolling;
   private int listStartIndex;
   private List<ResourceLocation> filtered = new ArrayList<>();
   private ResourceLocation selectedEntity = null;
   private EditBox searchField;
   private String nameFilter = "";
   private String cachedType = "";
   private String cachedDescription = "";

   public ImitatorScreen(ImitatorMenu menu, Inventory inv, Component title) {
      super(menu, inv, title);
      this.imageWidth = 233;
      this.imageHeight = 140;
   }

   protected void init() {
      super.init();
      this.scrollOffset = 0.0F;
      this.listStartIndex = 0;
      this.filtered = new ArrayList<>(((ImitatorMenu)this.menu).getEntities());
      this.searchField = new EditBox(this.font, this.leftPos + 19, this.topPos + 27, 79, 9, Component.empty());
      this.searchField.setBordered(false);
      this.searchField.setResponder(s -> {
         this.nameFilter = s.toLowerCase();
         this.scrollOffset = 0.0F;
         this.listStartIndex = 0;
         this.updateFiltered();
      });
      this.addRenderableWidget(this.searchField);
   }

   private void updateFiltered() {
      if (this.nameFilter.isBlank()) {
         this.filtered = new ArrayList<>(((ImitatorMenu)this.menu).getEntities());
      } else {
         this.filtered = ((ImitatorMenu)this.menu)
            .getEntities()
            .stream()
            .filter(
               rl -> {
                  EntityType<?> t = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(rl);
                  return t != null && t.getDescription().getString().toLowerCase().contains(this.nameFilter)
                     ? true
                     : rl.toString().toLowerCase().contains(this.nameFilter);
               }
            )
            .collect(Collectors.toList());
      }
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
      super.render(guiGraphics, mouseX, mouseY, partialTick);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }

   public void renderLabels(GuiGraphics graphics, int pMouseX, int pMouseY) {
      RenderHelper.drawCenteredText(graphics, this.font, this.title, 56, this.titleLabelY, 16777215, false);
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
      int x = (this.width - this.imageWidth) / 2;
      int y = (this.height - this.imageHeight) / 2;
      guiGraphics.blit(BACKGROUND, x, y, 0, 0, this.imageWidth, this.imageHeight);
      int k = (int)(78.0F * this.scrollOffset);
      guiGraphics.blit(SCROLL_BAR, this.leftPos + 98, this.topPos + 43 + k, 0.0F, this.isScrollBarActive() ? 13.0F : 0.0F, 10, 13, 10, 26);
      int lastVisible = Math.min(this.listStartIndex + 7, this.filtered.size());
      this.renderButtons(guiGraphics, mouseX, mouseY, lastVisible);
      if (this.selectedEntity != null) {
         this.renderEntityModel(guiGraphics, mouseX, mouseY);
         this.renderImitatorButton(guiGraphics, mouseX, mouseY);
         this.renderSectionType(guiGraphics);
         this.renderSectionUniques(guiGraphics);
         this.renderSectionDescription(guiGraphics);
      }
   }

   private void renderButtons(GuiGraphics graphics, int mouseX, int mouseY, int lastVisible) {
      for (int i = this.listStartIndex; i < lastVisible && i < this.filtered.size(); i++) {
         int x = this.leftPos + 6;
         int y = this.topPos + 43 + (i - this.listStartIndex) * 13;
         boolean hovering = mouseX >= x && mouseY >= y && mouseX < x + 89 && mouseY < y + 13;
         int offset = hovering ? 13 : 0;
         graphics.blit(SKILL_BAR, x, y, 0.0F, offset, 89, 13, 89, 26);
         ResourceLocation rl = this.filtered.get(i);
         MutableComponent name = this.getEntityName(rl).withStyle(ChatFormatting.WHITE);
         RenderHelper.drawShortenedTextWithTooltip(
            graphics,
            this.font,
            name,
            name,
            x,
            y,
            3,
            3,
            88,
            13,
            mouseX,
            mouseY,
            name.getStyle().getColor() != null ? name.getStyle().getColor().getValue() : 16777215,
            true,
            this
         );
      }
   }

   private void renderEntityModel(GuiGraphics graphics, int mouseX, int mouseY) {
      EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(this.selectedEntity);
      if (type != null && this.minecraft.level != null) {
         try {
            if (type.create(this.minecraft.level) instanceof LivingEntity entity) {
               int bx = this.leftPos + 138;
               int by = this.topPos + 116;
               float refWidth = 0.6F;
               float refHeight = 1.95F;
               float width = entity.getBbWidth();
               float height = entity.getBbHeight();
               float size = Math.max(width, height);
               float refSize = Math.max(refWidth, refHeight);
               float scale = 32.0F * (refSize / size);
               scale = Mth.clamp(scale, 12.0F, 48.0F);
               int boxX1 = bx - 50;
               int boxY1 = by - 90;
               int boxX2 = bx + 50;
               int boxY2 = by + 10;
               InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, boxX1, boxY1, boxX2, boxY2, (int)scale, 0.0625F, mouseX, mouseY, entity);
            }
         } catch (Exception var19) {
         }
      }
   }

   private void renderSectionType(GuiGraphics graphics) {
      int x1 = this.leftPos + 164;
      int y1 = this.topPos + 19;
      float width = 64.0F;
      float height = 26.0F;
      RenderHelper.drawScaledTextInArea(
         graphics, this.font, Component.translatable("stextras.skill_imitator.header.type"), x1, y1, width, 8.0F, new Color(14737632), 0.2F
      );
      RenderHelper.drawScaledTextInArea(graphics, this.font, Component.literal(this.cachedType), x1, y1 + 10, width, height - 10.0F, new Color(13676640), 1.0F);
   }

   private void renderSectionUniques(GuiGraphics graphics) {
      int x1 = this.leftPos + 164;
      int y1 = this.topPos + 48;
      float width = 64.0F;
      float height = 27.0F;
      RenderHelper.drawScaledTextInArea(
         graphics, this.font, Component.translatable("stextras.skill_imitator.header.uniques"), x1, y1, width, 8.0F, new Color(14737632), 0.2F
      );
      List<ResourceLocation> skillIds = ImitatorSkill.getSkillsForEntity(this.selectedEntity);
      float lineY = y1 + 10;
      if (skillIds.isEmpty()) {
         RenderHelper.drawScaledTextInArea(
            graphics, this.font, Component.translatable("stextras.skill_imitator.uniques.none"), x1, lineY, width, height - 10.0F, new Color(16744717), 1.0F
         );
      } else {
         for (ResourceLocation sId : skillIds) {
            float remaining = y1 + height - lineY;
            if (remaining <= 0.0F) {
               break;
            }

            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(sId);
            if (skill != null) {
               Component name = Component.literal(titleCase(skill.getName().getString()));
               RenderHelper.drawScaledTextInArea(graphics, this.font, name, x1, lineY, width, 8.0F, new Color(16744717), 0.3F);
               lineY += 9.0F;
            }
         }
      }
   }

   private void renderSectionDescription(GuiGraphics graphics) {
      int x1 = this.leftPos + 164;
      int y1 = this.topPos + 78;
      float width = 64.0F;
      float height = 56.0F;
      RenderHelper.drawScaledTextInArea(
         graphics, this.font, Component.translatable("stextras.skill_imitator.header.description"), x1, y1, width, 8.0F, new Color(14737632), 0.2F
      );
      String desc = this.cachedDescription.isEmpty() ? "No Description" : this.cachedDescription;
      RenderHelper.drawScaledTextInArea(graphics, this.font, Component.literal(desc), x1, y1 + 10, width, height - 10.0F, new Color(11579568), 1.0F);
   }

   private void renderImitatorButton(GuiGraphics graphics, int mouseX, int mouseY) {
      int x = this.leftPos + 116;
      int y = this.topPos + 123;
      int width = 43;
      int height = 11;
      boolean hovering = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
      int textureV = hovering ? 151 : 140;
      graphics.blit(BACKGROUND, x, y, 0, textureV, width, height);
      Component text = Component.translatable("stextras.skill_imitator.entity_apply");
      int textWidth = this.font.width(text);
      int textX = x + width / 2 - textWidth / 2;
      int textY = y + height / 2 - 9 / 2 + 1;
      int color = hovering ? -12176 : -2039584;
      graphics.drawString(this.font, text, textX, textY, color, false);
   }

   private void loadConfigDataForEntity(ResourceLocation entityId) {
      this.cachedType = "Unknown";
      this.cachedDescription = "";

      for (String entry : ImitatorSkill.CONFIG.entries) {
         String[] parts = entry.split("\\|");
         if (parts.length >= 3) {
            ResourceLocation id = ResourceLocation.tryParse(parts[0].trim());
            if (id != null && id.equals(entityId)) {
               this.cachedType = parts[1].trim();
               this.cachedDescription = parts[2].trim();
               break;
            }
         }
      }
   }

   private static String titleCase(String s) {
      if (s.isEmpty()) {
         return s;
      }

      String[] parts = s.split(" ");
      StringBuilder b = new StringBuilder();

      for (int i = 0; i < parts.length; i++) {
         String p = parts[i];
         if (!p.isEmpty()) {
            b.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1));
         }

         if (i < parts.length - 1) {
            b.append(' ');
         }
      }

      return b.toString();
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      int ax = this.leftPos + 116;
      int ay = this.topPos + 123;
      if (mouseX >= ax && mouseY >= ay && mouseX < ax + 43 && mouseY < ay + 11 && this.selectedEntity != null) {
         int id = ((ImitatorMenu)this.menu).getEntities().indexOf(this.selectedEntity);
         if (id != -1 && this.minecraft.gameMode != null) {
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            this.minecraft.gameMode.handleInventoryButtonClick(((ImitatorMenu)this.menu).containerId, id);
            return true;
         }
      }

      this.scrolling = false;
      int last = Math.min(this.listStartIndex + 7, this.filtered.size());

      for (int i = this.listStartIndex; i < last; i++) {
         int x = this.leftPos + 6;
         int y = this.topPos + 43 + (i - this.listStartIndex) * 13;
         if (mouseX >= x && mouseY >= y && mouseX < x + 89 && mouseY < y + 13) {
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            this.selectedEntity = this.filtered.get(i);
            this.loadConfigDataForEntity(this.selectedEntity);
            return true;
         }
      }

      if (mouseX >= this.leftPos + 98 && mouseX < this.leftPos + 109 && mouseY >= this.topPos + 43 && mouseY < this.topPos + 135) {
         this.scrolling = true;
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseDragged(double mx, double my, int button, double dx, double dy) {
      if (this.scrolling && this.isScrollBarActive()) {
         int i = this.topPos + 43;
         int j = i + 91;
         this.scrollOffset = (float)((my - i - 6.5) / (j - i - 13.0));
         this.scrollOffset = Mth.clamp(this.scrollOffset, 0.0F, 1.0F);
         this.listStartIndex = (int)(this.scrollOffset * this.getOffscreenRows() + 0.5);
         return true;
      } else {
         return super.mouseDragged(mx, my, button, dx, dy);
      }
   }

   public boolean mouseScrolled(double mx, double my, double deltaX, double deltaY) {
      if (this.isScrollBarActive()) {
         int rows = this.getOffscreenRows();
         float f = (float)deltaY / rows;
         this.scrollOffset = Mth.clamp(this.scrollOffset - f, 0.0F, 1.0F);
         this.listStartIndex = Math.max(0, Math.min(this.listStartIndex - (int)deltaY, this.filtered.size() - 7));
         return true;
      } else {
         return super.mouseScrolled(mx, my, deltaX, deltaY);
      }
   }

   private MutableComponent getEntityName(ResourceLocation rl) {
      EntityType<?> t = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(rl);
      return t != null ? (MutableComponent)t.getDescription() : Component.literal(rl.toString());
   }

   private boolean isScrollBarActive() {
      return this.filtered.size() > 7;
   }

   private int getOffscreenRows() {
      return this.filtered.size() - 7;
   }
}
