package xaero.map.controls;

import net.minecraft.client.KeyMapping;

public class KeyEvent {
   private KeyMapping kb;
   private boolean tickEnd;
   private boolean isRepeat;
   private boolean keyDown;

   public KeyEvent(KeyMapping kb, boolean tickEnd, boolean isRepeat, boolean keyDown) {
      this.kb = kb;
      this.tickEnd = tickEnd;
      this.isRepeat = isRepeat;
      this.keyDown = keyDown;
   }

   public KeyMapping getKb() {
      return this.kb;
   }

   public boolean isTickEnd() {
      return this.tickEnd;
   }

   public boolean isRepeat() {
      return this.isRepeat;
   }

   public boolean isKeyDown() {
      return this.keyDown;
   }
}
