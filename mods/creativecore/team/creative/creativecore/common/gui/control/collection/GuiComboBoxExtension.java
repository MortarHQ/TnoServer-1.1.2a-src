package team.creative.creativecore.common.gui.control.collection;

import java.util.ArrayList;
import java.util.List;
import team.creative.creativecore.client.render.text.CompiledText;
import team.creative.creativecore.common.gui.control.simple.GuiRowLabel;
import team.creative.creativecore.common.gui.control.simple.GuiTextfield;
import team.creative.creativecore.common.gui.extension.GuiExtensionCreator;

public class GuiComboBoxExtension extends GuiListBoxBase<GuiRowLabel> {
   public GuiExtensionCreator<? extends GuiComboBox<?>, ? extends GuiComboBoxExtension> creator;
   public String search = "";

   public GuiComboBoxExtension(String name, GuiExtensionCreator<? extends GuiComboBox<?>, ? extends GuiComboBoxExtension> creator) {
      super(name, false, new ArrayList<>());
      this.creator = creator;
      this.registerEventChanged(event -> {
         if (event.control.is("searchBar")) {
            this.search = ((GuiTextfield)event.control).getText();
            this.reloadControls();
         }
      });
      this.reloadControls();
   }

   @Override
   public void looseFocus() {
      this.creator.markLostFocus();
   }

   public void reloadControls() {
      if (this.creator != null) {
         GuiTextfield textfield = this.get("searchBar");
         this.clearItems();
         if (this.search != null && this.search.isBlank()) {
            this.search = null;
         }

         GuiComboBox<?> box = (GuiComboBox<?>)this.creator.parent;
         if (box.hasSearchbar()) {
            if (textfield == null) {
               textfield = new GuiTextfield("searchBar", this.search == null ? "" : this.search);
               this.addCustomControl(textfield.setExpandableX());
            }

            textfield.focus();
         }

         List<GuiRowLabel> entries = new ArrayList<>();
         int i = 0;

         for (CompiledText text : box.lines()) {
            if (this.search == null || text.contains(this.search)) {
               int index = i;
               entries.add(new GuiRowLabel(i + "", i, i == box.selectedIndex(), x -> {
                  this.creator.parent.select(index);
                  this.creator.close();
               }).set(text.copy()));
            }

            i++;
         }

         this.addAllItems(entries);
         if (this.hasGui()) {
            this.reflowInternal();
         }
      }
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (super.mouseClicked(x, y, button)) {
         this.creator.markKeptFocus();
         return true;
      } else {
         return false;
      }
   }

   @Override
   protected int maxHeight(int width, int availableWidth) {
      return 100;
   }
}
