package team.creative.creativecore.common.gui.control.timeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.Nullable;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public abstract class GuiTimelineChannel<T> extends GuiParent {
   public final GuiTimeline timeline;
   public GuiControl sidebarTitle;
   private int cachedHeight;
   private List<GuiTimelineKey<T>> keys = new ArrayList<>();
   private GuiTimelineKey<T> dragged;

   public GuiTimelineChannel(GuiTimeline timeline) {
      this.timeline = timeline;
      this.valign = VAlign.CENTER;
   }

   public GuiTimelineChannel<T> setTooltip(List<Component> tooltip) {
      super.setTooltip(tooltip);
      this.sidebarTitle.setTooltip(tooltip);
      return this;
   }

   public GuiTimelineChannel<T> addKeyFixed(int tick, T value) {
      GuiTimelineKey key = this.addKey(tick, value);
      key.modifiable = false;
      return this;
   }

   protected void adjustPosition(GuiTimelineKey<T> key) {
      if (this.hasLayer()) {
         key.rect.setWidth(key.rect.getPreferredWidth(0), 0);
         key.rect.flowX();
         key.rect.setHeight(key.rect.getPreferredHeight(0), 0);
         key.rect.flowY();
         key.rect.setY((int)Math.ceil(this.cachedHeight / 2.0 - key.rect.getHeight() / 2.0));
      }

      this.timeline.adjustKeyPositionX(key);
   }

   public GuiTimelineKey<T> addKey(int tick, T value) {
      GuiTimelineKey<T> key = new GuiTimelineKey<>(this, tick, value);
      this.add(key);
      this.adjustPosition(key);

      for (int i = 0; i < this.keys.size(); i++) {
         GuiTimelineKey<T> other = this.keys.get(i);
         if (other.tick == tick) {
            return null;
         }

         if (other.tick > tick) {
            this.keys.add(i, key);
            return key;
         }
      }

      this.keys.add(key);
      this.timeline.raiseEvent(new GuiControlChangedEvent<>(this.timeline));
      return key;
   }

   @Override
   public void flowX(int width, int preferred) {
      for (GuiControl control : this.controls) {
         control.rect.setWidth(control.rect.getPreferredWidth(0), 0);
         control.rect.flowX();
      }

      this.timeline.adjustKeysPositionX();
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      super.flowY(width, height, preferred);
      this.cachedHeight = height;
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.NESTED;
   }

   @Override
   public double getOffsetX() {
      return -this.timeline.scrolledX();
   }

   public void select(GuiTimelineKey<T> key) {
      this.timeline.selectKey(key);
   }

   public void deslect() {
      this.timeline.deselect();
   }

   public void removeChannel() {
      this.timeline.removeChannel(this);
   }

   public void removeKey(GuiTimelineKey<T> key) {
      this.keys.remove(key);
      this.remove(key);
      this.timeline.raiseEvent(new GuiControlChangedEvent<>(this.timeline));
   }

   public void movedKey(GuiTimelineKey<T> key) {
      Collections.sort(this.keys);
      this.timeline.raiseEvent(new GuiControlChangedEvent<>(this.timeline));
   }

   public void dragKey(GuiTimelineKey<T> key) {
      if (key.modifiable) {
         this.dragged = key;
      }
   }

   @Override
   public void mouseMoved(double x, double y) {
      if (this.dragged != null) {
         int tick = Math.max(0, this.timeline.getTimeAt(x));
         if (this.dragged.channel.isSpaceFor(this.dragged, tick)) {
            this.dragged.tick = tick;
            this.timeline.adjustKeyPositionX(this.dragged);
         }
      }

      super.mouseMoved(x, y);
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      if (this.dragged != null) {
         this.dragged.channel.movedKey(this.dragged);
         this.dragged = null;
      }

      super.mouseReleased(x, y, button);
   }

   @Override
   public boolean mouseScrolled(double x, double y, double delta) {
      this.timeline.scrolled(this.rect.getWidth(), x, delta);
      return true;
   }

   public boolean isSpaceFor(@Nullable GuiTimelineKey<T> key, int tick) {
      if (tick >= 0 && tick <= this.timeline.duration) {
         for (int i = 0; i < this.keys.size(); i++) {
            int otherTick = this.keys.get(i).tick;
            if (otherTick == tick) {
               return false;
            }

            if (otherTick > tick) {
               return true;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected abstract T getValueAt(int var1);

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      boolean result = super.mouseClicked(x, y, button);
      if (!result && button == 1) {
         int time = this.timeline.getTimeAt(x);
         if (this.isSpaceFor(null, time)) {
            GuiTimelineKey<T> key = this.addKey(time, this.getValueAt(time));
            if (key != null) {
               this.select(key);
               playSound(SoundEvents.ITEM_FRAME_ADD_ITEM, 0.1F, 0.6F);
            }
         }

         return true;
      } else {
         return result;
      }
   }

   public boolean isChannelEmpty() {
      return this.keys.isEmpty();
   }

   public Iterable<GuiTimelineKey<T>> keys() {
      return this.keys;
   }

   public GuiTimelineKey<T> getFirst() {
      return this.keys.isEmpty() ? null : this.keys.getFirst();
   }

   public GuiTimelineKey<T> getLast() {
      return this.keys.isEmpty() ? null : this.keys.getLast();
   }
}
