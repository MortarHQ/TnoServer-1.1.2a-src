package team.creative.creativecore.common.gui.control.parent;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.control.simple.GuiTabButton;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiTabs extends GuiParent {
   private final List<GuiTabs.SpecialParent> tabs = new ArrayList<>();
   private int index = -1;
   private GuiParent selected;
   private int lastWidth;
   private int lastHeight = -1;
   private int lastY;
   private GuiTabs.GuiTabBar bar;

   public GuiTabs(String name) {
      super(name);
      this.flow = GuiFlow.STACK_Y;
      this.align = Align.STRETCH;
      this.valign = VAlign.STRETCH;
      this.add(this.bar = new GuiTabs.GuiTabBar("bar"));
   }

   public GuiParent createTab(Component component) {
      this.bar.addTab(component, this.tabs.size());
      GuiTabs.SpecialParent tab = new GuiTabs.SpecialParent();
      tab.setParent(this);
      this.tabs.add(tab);
      return tab;
   }

   public GuiTabButton.GuiBorderlessButton getTabButton(int index) {
      return this.bar.getTab(index);
   }

   public GuiParent getTab(int index) {
      return this.tabs.get(index);
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }

   public void select(int select) {
      if (this.selected != null) {
         this.remove(this.selected);
      }

      this.index = select;
      this.selected = this.tabs.get(select);
      this.bar.highlight(select);
      this.add(this.selected);
      if (this.lastHeight == -1 && this.getParent() != null) {
         this.reflow();
      } else {
         this.selected.rect.setX(0);
         this.selected.rect.setWidth(this.lastWidth, this.lastWidth);
         this.selected.rect.flowX();
         this.selected.rect.setY(this.lastY);
         this.selected.rect.setHeight(this.lastHeight, this.lastHeight);
         this.selected.rect.flowY();
      }

      this.raiseEvent(new GuiControlChangedEvent<>(this));
   }

   public int index() {
      return this.index;
   }

   @Override
   public void flowX(int width, int preferred) {
      super.flowX(width, preferred);
      this.lastWidth = width;
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      super.flowY(width, height, preferred);
      if (this.selected != null) {
         this.lastHeight = this.selected.rect.getHeight();
         this.lastY = this.selected.rect.getY();
      } else {
         this.lastHeight = -1;
      }
   }

   public class GuiTabBar extends GuiParent {
      private GuiTabButton.GuiBorderlessButton highlighted;
      private int count;

      public GuiTabBar(String name) {
         super(name);
         this.flow = GuiFlow.STACK_X;
      }

      public void highlight(int index) {
         GuiTabButton.GuiBorderlessButton newSelected = (GuiTabButton.GuiBorderlessButton)this.controls.get(index);
         if (newSelected != this.highlighted && this.highlighted != null) {
            this.highlighted.active = false;
         }

         newSelected.active = true;
         this.highlighted = newSelected;
      }

      public void addTab(Component component, int index) {
         this.add(new GuiTabButton.GuiBorderlessButton("b" + this.count, x -> GuiTabs.this.select(index), null).setTitle(component));
         this.count++;
      }

      public GuiTabButton.GuiBorderlessButton getTab(int index) {
         return (GuiTabButton.GuiBorderlessButton)this.controls.get(index);
      }

      public void removeTab(int index) {
         this.controls.remove(index);
      }

      @Override
      public ControlFormatting getControlFormatting() {
         return ControlFormatting.TRANSPARENT;
      }
   }

   private class SpecialParent extends GuiParent {
      public SpecialParent() {
      }

      @Override
      public ControlFormatting getControlFormatting() {
         return ControlFormatting.NESTED;
      }

      @Override
      protected int preferredWidth(int availableWidth) {
         int pref = 0;

         for (GuiTabs.SpecialParent p : GuiTabs.this.tabs) {
            pref = Math.max(pref, p.preferredWidthOriginal(availableWidth));
         }

         return pref;
      }

      protected int preferredWidthOriginal(int availableWidth) {
         return super.preferredWidth(availableWidth);
      }

      @Override
      protected int preferredHeight(int width, int availableHeight) {
         int pref = 0;

         for (GuiTabs.SpecialParent p : GuiTabs.this.tabs) {
            pref = Math.max(pref, p.preferredHeightOriginal(width, availableHeight));
         }

         return pref;
      }

      protected int preferredHeightOriginal(int width, int availableHeight) {
         return super.preferredHeight(width, availableHeight);
      }
   }
}
