package dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.kubejs;

import dev.ftb.mods.ftbquests.events.CustomTaskEvent;
import dev.ftb.mods.ftbquests.quest.task.CustomTask;
import dev.ftb.mods.ftbquests.quest.task.CustomTask.Check;
import dev.latvian.mods.kubejs.event.KubeEvent;

public class CustomTaskKubeEvent implements KubeEvent {
   public final CustomTaskEvent event;

   CustomTaskKubeEvent(CustomTaskEvent e) {
      this.event = e;
   }

   public CustomTask getTask() {
      return this.event.getTask();
   }

   public void setCheck(Check c) {
      this.getTask().setCheck(c);
   }

   public void setCheckTimer(int t) {
      this.getTask().setCheckTimer(t);
   }

   public void setEnableButton(boolean b) {
      this.getTask().setEnableButton(b);
   }

   public void setMaxProgress(long max) {
      this.getTask().setMaxProgress(max);
   }
}
