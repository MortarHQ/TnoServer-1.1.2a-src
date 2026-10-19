package team.creative.creativecore.common.gui.control.menu;

import java.util.Map.Entry;
import net.minecraft.sounds.SoundEvents;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.control.parent.GuiScrollY;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.extension.GuiExtensionCreator;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.type.tree.NamedTree;

public abstract class GuiMenu<T> extends GuiScrollY {
   protected final GuiExtensionCreator<GuiMenu, GuiMenu> submenu = new GuiExtensionCreator<GuiMenu, GuiMenu>(this) {
      @Override
      public void markKeptFocus() {
         super.markKeptFocus();
         GuiMenu.this.parentCreator().markKeptFocus();
      }
   };
   protected final NamedTree<T> tree;

   public GuiMenu(NamedTree<T> tree) {
      this.tree = tree;
      this.hoveredScroll = true;
      this.align = Align.STRETCH;
      this.spacing = -1;
   }

   protected void buildTree() {
      String path = this.tree.path();
      if (!path.isBlank()) {
         path = path + ".";
      }

      for (Entry<String, NamedTree<T>> entry : this.tree.entries()) {
         this.add(new GuiMenu.GuiMenuEntry(path + entry.getKey(), entry.getValue()));
      }
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (super.mouseClicked(x, y, button)) {
         this.parentCreator().markKeptFocus();
         return true;
      } else {
         return false;
      }
   }

   public abstract boolean isRoot();

   public abstract GuiMenuRoot<T> root();

   public abstract GuiExtensionCreator<? extends GuiControl, ? extends GuiMenu> parentCreator();

   @Override
   public void closed() {
      if (this.submenu.hasExtension()) {
         this.submenu.close();
      }
   }

   public class GuiMenuEntry extends GuiLabel {
      public NamedTree<T> folder;

      public GuiMenuEntry(String name, NamedTree<T> folder) {
         super(name);
         this.folder = folder;
         this.setTitle(GuiMenu.this.root().translate(name, folder.value != null));
      }

      @Override
      public void mouseMoved(double x, double y) {
         if (this.rect.insideLocalPos(x, y) && this.folder.hasChildren()) {
            if (GuiMenu.this.submenu.hasExtension()) {
               GuiMenu.this.submenu.close();
            }

            GuiMenu.this.submenu
               .open(new GuiMenuSub<>(GuiMenu.this.root(), this.folder, GuiMenu.this.submenu), this, GuiExtensionCreator.ExtensionDirection.RIGHT);
         }
      }

      @Override
      public boolean mouseClicked(double x, double y, int button) {
         if (this.folder.value != null) {
            GuiMenu.this.root().select(this.folder.path(), this.folder.value);
            playSound(SoundEvents.UI_BUTTON_CLICK);
         }

         return true;
      }

      @Override
      public ControlFormatting getControlFormatting() {
         return ControlFormatting.CLICKABLE;
      }
   }
}
