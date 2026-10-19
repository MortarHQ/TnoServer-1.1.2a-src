package team.creative.creativecore.common.gui.control.collection;

import java.util.ArrayList;
import java.util.Map.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.control.inventory.GuiInventoryGridPreview;
import team.creative.creativecore.common.gui.control.inventory.GuiSlotViewer;
import team.creative.creativecore.common.gui.control.parent.GuiScrollY;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.control.simple.GuiTextfield;
import team.creative.creativecore.common.gui.extension.GuiExtensionCreator;
import team.creative.creativecore.common.gui.flow.GuiSizeRule;
import team.creative.creativecore.common.util.type.map.HashMapList;

public class GuiStackSelectorExtension extends GuiScrollY {
   public GuiExtensionCreator<GuiStackSelector, GuiStackSelectorExtension> creator;
   private String search = "";
   protected int cachedWidth;

   public GuiStackSelectorExtension(String name, Player player, GuiExtensionCreator<GuiStackSelector, GuiStackSelectorExtension> creator) {
      super(name);
      this.creator = creator;
      this.registerEventChanged(event -> {
         if (event.control.is("searchBar")) {
            this.search = ((GuiTextfield)event.control).getText();
            this.reloadControls();
         }
      });
      this.registerEventClick(event -> {
         if (event.control instanceof GuiSlotViewer && event.control.isParent(this)) {
            creator.parent.setSelected(((GuiSlotViewer)event.control).getStack());
            playSound(SoundEvents.UI_BUTTON_CLICK);
            creator.close();
         }
      });
      this.align = Align.STRETCH;
      this.reloadControls();
      this.setDim(new GuiSizeRule.GuiSizeRules().maxHeight(100));
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (super.mouseClicked(x, y, button)) {
         this.creator.markKeptFocus();
      }

      return true;
   }

   @Override
   public void flowX(int width, int preferred) {
      this.cachedWidth = width;
      super.flowX(width, preferred);
   }

   public void reflowInternal() {
      this.flowX(this.cachedWidth, this.preferredWidth(this.cachedWidth));
      this.flowY(this.cachedWidth, this.cachedHeight, this.preferredHeight(this.cachedWidth, this.cachedHeight));
   }

   public void reloadControls() {
      if (this.creator != null) {
         GuiStackSelector comboBox = this.creator.parent;
         HashMapList<String, ItemStack> stacks = this.search != null && !this.search.isEmpty() ? new HashMapList<>() : comboBox.getStacks();
         if (this.search != null && !this.search.isEmpty()) {
            for (Entry<String, ArrayList<ItemStack>> entry : comboBox.getStacks().entrySet()) {
               for (ItemStack stack : entry.getValue()) {
                  if (GuiStackSelector.contains(this.search, stack)) {
                     stacks.add(entry.getKey(), stack);
                  }
               }
            }
         }

         GuiTextfield textfield = this.get("searchBar");
         this.clear();
         if (comboBox.hasSearchbar()) {
            if (textfield == null) {
               textfield = new GuiTextfield("searchBar", this.search == null ? "" : this.search);
            }

            this.add(textfield);
            textfield.focus();
         }

         for (Entry<String, ArrayList<ItemStack>> entry : stacks.entrySet()) {
            this.add(new GuiLabel("title").setTitle(Component.translatable(entry.getKey())));
            SimpleContainer container = new SimpleContainer(entry.getValue().size());
            int i = 0;

            for (ItemStack stack : entry.getValue()) {
               container.setItem(i, stack);
               i++;
            }

            this.add(new GuiInventoryGridPreview(entry.getKey(), container));
         }

         if (this.hasGui()) {
            this.reflowInternal();
         }
      }
   }

   @Override
   public void looseFocus() {
      this.creator.markLostFocus();
   }
}
