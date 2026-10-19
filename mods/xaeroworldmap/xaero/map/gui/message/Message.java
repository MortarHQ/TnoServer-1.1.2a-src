package xaero.map.gui.message;

import net.minecraft.network.chat.Component;

public class Message {
   private final Component text;
   private final long additionTime;

   public Message(Component text, long additionTime) {
      this.text = text;
      this.additionTime = additionTime;
   }

   public Component getText() {
      return this.text;
   }

   public long getAdditionTime() {
      return this.additionTime;
   }
}
