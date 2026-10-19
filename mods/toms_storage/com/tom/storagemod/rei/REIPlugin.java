package com.tom.storagemod.rei;

import com.tom.storagemod.Content;
import com.tom.storagemod.screen.AbstractStorageTerminalScreen;
import com.tom.storagemod.screen.IScreen;
import com.tom.storagemod.util.IAutoFillTerminal;
import dev.architectury.event.CompoundEventResult;
import java.util.ArrayList;
import java.util.List;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.ItemLike;

public class REIPlugin implements REIClientPlugin {
   public void registerTransferHandlers(TransferHandlerRegistry recipeHelper) {
      recipeHelper.register(new ReiTransferHandler());
   }

   public static void setReiSearchText(String text) {
      REIRuntime.getInstance().getSearchTextField().setText(text);
   }

   public void registerCategories(CategoryRegistry registry) {
      registry.addWorkstations(BuiltinPlugin.CRAFTING, new EntryStack[]{EntryStacks.of((ItemLike)Content.craftingTerminal.get())});
   }

   public void registerScreens(ScreenRegistry registry) {
      registry.registerFocusedStack((scr, point) -> {
         if (scr instanceof AbstractStorageTerminalScreen<?> t) {
            Slot sl = t.getSlotUnderMouse();
            if (sl != null) {
               return CompoundEventResult.interruptTrue(EntryStack.of(VanillaEntryTypes.ITEM, sl.getItem()));
            }
         }

         return CompoundEventResult.pass();
      });
      registry.registerDraggableStackVisitor(new ReiGhostIngredientHandler());
      registry.exclusionZones().register(IScreen.class, s -> {
         List<Rectangle> rects = new ArrayList<>();
         s.getExclusionAreas(b -> rects.add(new Rectangle(b.x(), b.y(), b.width(), b.height())));
         return rects;
      });
   }

   static {
      IAutoFillTerminal.updateSearch.add(new IAutoFillTerminal.ISearchHandler() {
         @Override
         public void setSearch(String text) {
            REIRuntime.getInstance().getSearchTextField().setText(text);
         }

         @Override
         public String getSearch() {
            return REIRuntime.getInstance().getSearchTextField().getText();
         }

         @Override
         public String getName() {
            return "REI";
         }
      });
   }
}
