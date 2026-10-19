package team.creative.creativecore.common.gui.control.simple;

import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;

public class GuiDuration extends GuiParent {
   public static final int TICKS_PER_SECOND = 20;
   public static final int TICKS_PER_MINUTE = 1200;
   public static final int TICKS_PER_HOUR = 72000;
   public static final int TICKS_PER_DAY = 1728000;
   private GuiCounter d;
   private GuiCounter h;
   private GuiCounter m;
   private GuiCounter s;

   public GuiDuration(String name, int ticks, boolean showDays, boolean showHours, boolean showMinutes, boolean showSeconds) {
      super(name);
      this.setVAlign(VAlign.CENTER);
      if (showDays) {
         this.add(this.d = new GuiCounter("d", 0, 0, Integer.MAX_VALUE));
         this.add(new GuiLabel("dLabel").setTranslate("gui.day"));
      }

      if (showHours) {
         this.add(this.h = new GuiCounter("h", 0, 0, Integer.MAX_VALUE));
         this.add(new GuiLabel("hLabel").setTranslate("gui.hour"));
      }

      if (showMinutes) {
         this.add(this.m = new GuiCounter("m", 0, 0, Integer.MAX_VALUE));
         this.add(new GuiLabel("mLabel").setTranslate("gui.minute"));
      }

      if (showSeconds) {
         this.add(this.s = new GuiCounter("s", 0, 0, Integer.MAX_VALUE));
         this.add(new GuiLabel("sLabel").setTranslate("gui.second"));
      }

      this.setDuration(ticks);
   }

   public void setDuration(int ticks) {
      if (this.d != null) {
         this.d.setValue(ticks / 1728000);
         ticks -= this.d.getValue() * 1728000;
      }

      if (this.h != null) {
         this.h.setValue(ticks / 72000);
         ticks -= this.h.getValue() * 72000;
      }

      if (this.m != null) {
         this.m.setValue(ticks / 1200);
         ticks -= this.m.getValue() * 1200;
      }

      if (this.s != null) {
         this.s.setValue(ticks / 20);
         ticks -= this.s.getValue() * 20;
      }
   }

   public int getDuration() {
      int duration = 0;
      if (this.d != null) {
         duration += this.d.getValue() * 1728000;
      }

      if (this.h != null) {
         duration += this.h.getValue() * 72000;
      }

      if (this.m != null) {
         duration += this.m.getValue() * 1200;
      }

      if (this.s != null) {
         duration += this.s.getValue() * 20;
      }

      return duration;
   }
}
