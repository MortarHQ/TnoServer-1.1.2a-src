package dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.kubejs;

import dev.ftb.mods.ftbquests.events.CustomRewardEvent;
import dev.ftb.mods.ftbquests.quest.reward.CustomReward;
import dev.latvian.mods.kubejs.player.KubePlayerEvent;
import net.minecraft.world.entity.player.Player;

public class CustomRewardKubeEvent implements KubePlayerEvent {
   public final transient CustomRewardEvent event;

   public CustomRewardKubeEvent(CustomRewardEvent e) {
      this.event = e;
   }

   public Player getEntity() {
      return this.event.getPlayer();
   }

   public CustomReward getReward() {
      return this.event.getReward();
   }

   public boolean getNotify() {
      return this.event.getNotify();
   }
}
