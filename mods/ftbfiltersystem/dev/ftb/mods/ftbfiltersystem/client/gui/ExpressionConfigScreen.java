package dev.ftb.mods.ftbfiltersystem.client.gui;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterScreen;
import dev.ftb.mods.ftbfiltersystem.client.FTBFilterSystemClient;
import dev.ftb.mods.ftbfiltersystem.client.gui.widget.CustomStringWidget;
import dev.ftb.mods.ftbfiltersystem.filter.ExpressionFilter;
import dev.ftb.mods.ftbfiltersystem.registry.item.SmartFilterItem;
import dev.ftb.mods.ftbfiltersystem.util.FilterParser;
import java.util.function.Predicate;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ExpressionConfigScreen extends AbstractItemEditorConfigScreen<ExpressionFilter> {
   private EditBox customEditBox;

   public ExpressionConfigScreen(ExpressionFilter filter, AbstractFilterScreen parentScreen) {
      super(filter, parentScreen, 320, 176);
   }

   @Override
   protected void init() {
      super.init();
      this.editBox.setValue(this.filter.getExpression());
      Component text = Component.translatable("ftbfiltersystem.gui.custom_name");
      this.addRenderableWidget(CustomStringWidget.withText(this.leftPos + 180, this.topPos + 110, text, this.font).setColor(4210752));
      this.customEditBox = (EditBox)this.addRenderableWidget(
         new EditBox(this.font, this.leftPos + 180, this.topPos + 110 + 9 + 4, this.guiWidth - 190, 9 + 6, Component.empty())
      );
      this.customEditBox.setValue(this.filter.getCustomName());
   }

   @Nullable
   protected ExpressionFilter makeNewFilter() {
      try {
         String str = this.customEditBox.getValue().isEmpty() ? this.editBox.getValue() : this.editBox.getValue() + "/" + this.customEditBox.getValue();
         return new ExpressionFilter(this.filter.getParent(), str, FTBFilterSystemClient.registryAccess());
      } catch (FilterException e) {
         return null;
      }
   }

   @Override
   protected void onItemWidgetClicked() {
      this.customEditBox.setValue(this.customHoverName == null ? "" : this.customHoverName.getString());
   }

   @Override
   protected Predicate<ItemStack> inventoryChecker() {
      return stack -> FTBFilterSystemAPI.api().isFilterItem(stack) && !FTBFilterSystemClient.isPlayerHolding(stack);
   }

   @Override
   protected String serialize(ItemStack stack) {
      return SmartFilterItem.getFilterString(stack);
   }

   @Override
   protected void doScheduledUpdate() {
      if (this.editBox.getValue().isEmpty()) {
         this.setStatus(true, Component.empty(), null);
      } else {
         try {
            FilterParser.parse(this.editBox.getValue(), FTBFilterSystemClient.registryAccess());
            this.setStatus(true, Component.translatable("ftbfiltersystem.gui.filter_ok"), null);
         } catch (FilterException e) {
            this.setStatus(false, Component.translatable("ftbfiltersystem.gui.filter_bad"), e.getMessage());
         }
      }
   }
}
