package team.creative.creativecore.common.gui.control.collection;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.control.menu.GuiMenuRoot;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.extension.GuiExtensionCreator;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.type.tree.NamedTree;

public class GuiComboBoxTree<K> extends GuiLabel {
   protected final GuiExtensionCreator<GuiComboBoxTree<K>, GuiMenuRoot<K>> ex = new GuiExtensionCreator<>(this);
   protected final Function<String, Component> title;
   protected NamedTree<K> data;
   private String selectedPath;
   private K selected;
   private boolean searchbar;
   private GuiExtensionCreator.ExtensionDirection direction = GuiExtensionCreator.ExtensionDirection.BELOW_OR_ABOVE;

   public GuiComboBoxTree(String name, K selected, NamedTree<K> data, Function<String, Component> title) {
      this(name, data, title);
      this.select(selected);
   }

   public GuiComboBoxTree(String name, NamedTree<K> data, Function<String, Component> title) {
      super(name);
      this.title = title;
      this.set(data);
   }

   public boolean hasSearchbar() {
      return this.searchbar;
   }

   public GuiComboBoxTree setSearchbar(boolean searchbar) {
      this.searchbar = searchbar;
      return this;
   }

   public GuiComboBoxTree setDirection(GuiExtensionCreator.ExtensionDirection direction) {
      this.direction = direction;
      return this;
   }

   public void set(NamedTree<K> data) {
      this.data = data;
      this.select(data.first());
      this.updateDisplay();
   }

   @Nullable
   public K selected() {
      return this.selected;
   }

   public K selected(K defaultValue) {
      K s = this.selected();
      return s != null ? s : defaultValue;
   }

   public void select(String path, K key) {
      this.selectedPath = path;
      this.selected = key;
      this.updateDisplay();
      this.raiseEvent(new GuiControlChangedEvent<>(this));
   }

   public void select(K key) {
      String path = this.data.findPath(key);
      if (path == null) {
         this.select(null, null);
      } else {
         this.select(path, key);
      }
   }

   protected void updateDisplay() {
      this.text = this.text.sameDimensions();
      if (this.selected != null) {
         this.text.setAlign(Align.CENTER);
         this.text.setText(this.title.apply(this.selectedPath));
      }
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      this.ex.toggle(this::createBox, this.direction);
      playSound(SoundEvents.UI_BUTTON_CLICK);
      return true;
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.CLICKABLE;
   }

   protected GuiMenuRoot<K> createBox(GuiExtensionCreator<GuiComboBoxTree<K>, GuiMenuRoot<K>> creator) {
      return new GuiMenuRoot<>(this.data, creator, this.title, this::select);
   }

   @Override
   public void looseFocus() {
      if (this.ex.checkShouldClose()) {
         this.ex.close();
      }
   }
}
