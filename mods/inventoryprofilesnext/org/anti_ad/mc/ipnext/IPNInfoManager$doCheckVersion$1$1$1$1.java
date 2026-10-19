package org.anti_ad.mc.ipnext;

import net.minecraft.network.chat.MutableComponent;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.moreinfo.SemVer;
import org.anti_ad.mc.ipnext.event.ClientEventHandler;

final class IPNInfoManager$doCheckVersion$1$1$1$1 implements Runnable {
   IPNInfoManager$doCheckVersion$1$1$1$1(SemVer var1) {
      this.$new = var1;
   }

   @Override
   public final void run() {
      MutableComponent var1 = ClientEventHandler.INSTANCE.createChatMessage(this.$new);
      TellPlayer.INSTANCE.chat(var1);
   }
}
