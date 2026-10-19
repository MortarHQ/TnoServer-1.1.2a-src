package net.p3pp3rf1y.sophisticatedcore.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.CompositeWidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.TextBox;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.WidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.common.gui.TemplatePersistanceContainer;

public class TemplatePersistanceControl extends CompositeWidgetBase<WidgetBase> {
   private static final int BUTTON_GAP = 0;
   private static final TextureBlitData SAVE_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(240, 16), Dimension.SQUARE_16
   );
   public static final ButtonDefinition SAVE_TEMPLATE = new ButtonDefinition(
      Dimension.SQUARE_16,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      SAVE_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("save_template"))
   );
   private static final TextureBlitData LOAD_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(240, 32), Dimension.SQUARE_16
   );
   public static final ButtonDefinition LOAD_TEMPLATE = new ButtonDefinition(
      Dimension.SQUARE_16,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      LOAD_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("load_template"))
   );
   private static final TextureBlitData EXPORT_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(240, 48), Dimension.SQUARE_16
   );
   public static final ButtonDefinition EXPORT_TEMPLATE = new ButtonDefinition(
      Dimension.SQUARE_16,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      EXPORT_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("export_template"))
   );
   private final Button loadTemplateButton;
   private final Button saveTemplateButton;
   private final Button exportTemplateButton;
   private final TextBox saveInput;
   private final TemplatePersistanceContainer container;
   private final TextBox exportInput;

   protected TemplatePersistanceControl(Position position, final TemplatePersistanceContainer container) {
      super(position, new Dimension(18, 54));
      this.container = container;
      container.setOnSlotsRefreshed(() -> {
         this.setSaveTooltip();
         this.setLoadTooltip();
      });
      this.saveInput = new TextBox(new Position(this.x + 20, this.y), new Dimension(192, 18)) {
         @Override
         protected void onEnterPressed() {
            container.saveTemplate(this.getValue());
            this.setValue("");
            TemplatePersistanceControl.this.setSaveTooltip();
         }
      };
      this.saveInput.setVisible(false);
      this.addChild(this.saveInput);
      this.saveTemplateButton = new Button(new Position(this.x, this.y), SAVE_TEMPLATE, button -> {
         container.saveTemplate(this.saveInput.getValue());
         this.saveInput.setValue("");
         this.setSaveTooltip();
      }) {
         public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
            container.scrollSaveSlot(scrollY > 0.0);
            TemplatePersistanceControl.this.setSaveTooltip();
            return true;
         }

         @Override
         public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
            boolean mouseOver = this.isMouseOver(mouseX, mouseY);
            boolean showTextBox = container.showsTextbox() && mouseOver;
            if (mouseOver) {
               guiGraphics.renderTooltip(
                  screen.font,
                  this.getTooltip(),
                  Optional.empty(),
                  TemplatePersistanceControl.this.saveTemplateButton.getX() + 10,
                  TemplatePersistanceControl.this.saveTemplateButton.getY() + (showTextBox ? -13 : 6)
               );
            }

            TemplatePersistanceControl.this.saveInput.setVisible(showTextBox);
            TemplatePersistanceControl.this.saveInput.setFocused(showTextBox);
            if (mouseOver && screen.getFocused() != TemplatePersistanceControl.this.saveInput) {
               screen.setFocused(TemplatePersistanceControl.this.saveInput);
            } else if (!mouseOver && screen.getFocused() == TemplatePersistanceControl.this.saveInput) {
               screen.setFocused(null);
            }
         }
      };
      this.setSaveTooltip();
      this.addChild(this.saveTemplateButton);
      this.loadTemplateButton = new Button(new Position(this.x, this.y + 18 + 0), LOAD_TEMPLATE, button -> container.loadTemplate()) {
         public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
            container.scrollLoadSlot(scrollY > 0.0);
            TemplatePersistanceControl.this.setLoadTooltip();
            return true;
         }

         @Override
         public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
            boolean mouseOver = this.isMouseOver(mouseX, mouseY);
            if (mouseOver) {
               guiGraphics.renderTooltip(
                  screen.font,
                  this.getTooltip(),
                  Optional.empty(),
                  TemplatePersistanceControl.this.loadTemplateButton.getX() + 10,
                  TemplatePersistanceControl.this.loadTemplateButton.getY() + 6
               );
            }
         }
      };
      this.setLoadTooltip();
      this.addChild(this.loadTemplateButton);
      this.exportInput = new TextBox(new Position(this.x + 20, this.y + 36), new Dimension(171, 18)) {
         @Override
         protected void onEnterPressed() {
            container.exportTemplate(this.getValue());
            this.setValue("");
         }
      };
      this.exportInput.setVisible(false);
      this.addChild(this.exportInput);
      this.exportTemplateButton = new Button(
         new Position(this.x, this.y + 36), EXPORT_TEMPLATE, button -> container.exportTemplate(this.exportInput.getValue())
      ) {
         @Override
         public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
            boolean mouseOver = this.isMouseOver(mouseX, mouseY);
            if (mouseOver) {
               guiGraphics.renderTooltip(
                  screen.font,
                  this.getTooltip(),
                  Optional.empty(),
                  TemplatePersistanceControl.this.exportTemplateButton.getX() + 10,
                  TemplatePersistanceControl.this.exportTemplateButton.getY() - 13
               );
            }

            TemplatePersistanceControl.this.exportInput.setVisible(mouseOver);
            TemplatePersistanceControl.this.exportInput.setFocused(mouseOver);
            if (mouseOver && screen.getFocused() != TemplatePersistanceControl.this.exportInput) {
               screen.setFocused(TemplatePersistanceControl.this.exportInput);
            } else if (!mouseOver && screen.getFocused() == TemplatePersistanceControl.this.exportInput) {
               screen.setFocused(null);
            }
         }
      };
      this.exportTemplateButton
         .setTooltip(
            List.of(
               Component.translatable(
                  TranslationHelper.INSTANCE.translSettingsButton("export_template"),
                  new Object[]{
                     Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("export_template.enter_file_name")).withStyle(ChatFormatting.GREEN)
                  }
               ),
               Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("export_template.additional_info"))
                  .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
            )
         );
      this.addChild(this.exportTemplateButton);
   }

   private void setLoadTooltip() {
      if (this.container.getLoadSlot() == -1) {
         this.loadTemplateButton
            .setTooltip(List.of(Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("load_template.no_save")).withStyle(ChatFormatting.RED)));
      } else {
         List<Component> tooltip = new ArrayList<>();
         tooltip.add(
            Component.translatable(
               TranslationHelper.INSTANCE.translSettingsButton("load_template"),
               new Object[]{this.container.getLoadSlotTooltipName().withStyle(ChatFormatting.GREEN)}
            )
         );
         this.container
            .getLoadSlotSource()
            .ifPresent(
               source -> tooltip.add(
                  Component.translatable(
                        TranslationHelper.INSTANCE.translSettingsButton("load_template.source"),
                        new Object[]{Component.literal(source).withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY})}
                     )
                     .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
               )
            );
         tooltip.add(
            Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("load_template.controls"))
               .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
         );
         if (this.container.templateHasTooManySlots()) {
            tooltip.add(
               Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("load_template.too_many_setting_slots")).withStyle(ChatFormatting.RED)
            );
         }

         this.loadTemplateButton.setTooltip(tooltip);
      }
   }

   private void setSaveTooltip() {
      this.saveTemplateButton
         .setTooltip(
            List.of(
               Component.translatable(
                  TranslationHelper.INSTANCE.translSettingsButton("save_template"),
                  new Object[]{this.container.getSaveSlotTooltipName().withStyle(ChatFormatting.GREEN)}
               ),
               Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("save_template.controls"))
                  .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
            )
         );
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }

   public boolean isTemplateLoadHovered() {
      return this.loadTemplateButton.isHovered();
   }
}
