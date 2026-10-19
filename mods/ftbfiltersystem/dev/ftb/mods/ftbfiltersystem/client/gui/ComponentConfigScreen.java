package dev.ftb.mods.ftbfiltersystem.client.gui;

import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterScreen;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.widget.CustomCheckbox;
import dev.ftb.mods.ftbfiltersystem.client.FTBFilterSystemClient;
import dev.ftb.mods.ftbfiltersystem.filter.ComponentFilter;
import dev.ftb.mods.ftbfiltersystem.util.PlatformUtil;
import java.util.function.Predicate;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ComponentConfigScreen extends AbstractItemEditorConfigScreen<ComponentFilter> implements GhostDropReceiver {
   private CustomCheckbox fuzzyCB;

   public ComponentConfigScreen(ComponentFilter filter, AbstractFilterScreen parentScreen) {
      super(filter, parentScreen, 320, 176);
   }

   @Override
   protected void init() {
      super.init();
      Component str = Component.translatable("ftbfiltersystem.gui.fuzzy_match");
      this.fuzzyCB = (CustomCheckbox)this.addRenderableWidget(
         new CustomCheckbox(this.leftPos + 180, this.topPos + 110, this.font.width(str), 20, str, this.filter.isFuzzyMatch())
      );

      try {
         this.editBox.setValue(this.filter.getStringArgWithoutPrefix(FTBFilterSystemClient.registryAccess()));
      } catch (IllegalStateException var3) {
      }
   }

   @Override
   protected void doScheduledUpdate() {
      if (this.editBox.getValue().isEmpty()) {
         this.setStatus(true, Component.empty(), null);
      } else {
         try {
            ComponentFilter.fromString(this.filter.getParent(), this.editBox.getValue(), FTBFilterSystemClient.registryAccess());
            this.setStatus(true, Component.translatable("ftbfiltersystem.gui.nbt_ok"), null);
         } catch (FilterException e) {
            this.setStatus(false, Component.translatable("ftbfiltersystem.gui.nbt_bad"), e.getMessage());
         }
      }
   }

   @Nullable
   protected ComponentFilter makeNewFilter() {
      try {
         String str = ComponentFilter.getPrefixStr(this.fuzzyCB.selected()) + this.editBox.getValue();
         return ComponentFilter.fromString(this.filter.getParent(), str, FTBFilterSystemClient.registryAccess());
      } catch (FilterException e) {
         return null;
      }
   }

   @Override
   protected Predicate<ItemStack> inventoryChecker() {
      return PlatformUtil::hasComponentPatch;
   }

   @Override
   protected String serialize(ItemStack stack) {
      return stack.save(FTBFilterSystemClient.registryAccess(), new CompoundTag()) instanceof CompoundTag c && c.contains("components")
         ? c.get("components").toString()
         : "";
   }

   @Override
   public Rect2i getGhostDropRegion() {
      return new Rect2i(this.editBox.getX(), this.editBox.getY(), this.editBox.getWidth(), this.editBox.getHeight());
   }

   @Override
   public void receiveGhostDrop(ItemStack stack) {
      if (PlatformUtil.hasComponentPatch(stack)) {
         this.editBox.setValue(this.serialize(stack));
         this.customHoverName = (Component)stack.getOrDefault(DataComponents.CUSTOM_NAME, null);
         this.setFocused(this.editBox);
      }
   }
}
