package cn.leolezury.eternalstarlight.common.entity.living.boss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;

public class ESServerBossEvent extends ServerBossEvent {
   private final ESBoss boss;
   private final Set<ServerPlayer> unseenPlayers = new HashSet<>();
   private UUID id;

   public ESServerBossEvent(ESBoss boss, UUID id, BossBarColor color, boolean darkenScreen) {
      super(boss.getDisplayName(), color, BossBarOverlay.PROGRESS);
      this.setVisible(true);
      this.setId(id);
      this.setDarkenScreen(darkenScreen);
      this.boss = boss;
   }

   public void setId(UUID uuid) {
      this.id = uuid;
   }

   public UUID getId() {
      return this.id;
   }

   public void update() {
      this.setProgress(this.boss.getHealth() / this.boss.getMaxHealth());
      Iterator<ServerPlayer> it = this.unseenPlayers.iterator();

      while (it.hasNext()) {
         ServerPlayer player = it.next();
         if (this.boss.getSensing().hasLineOfSight(player) && this.boss.isActivated() && this.boss.tickCount > 20) {
            super.addPlayer(player);
            it.remove();
         }
      }
   }

   public void addPlayer(ServerPlayer player) {
      if (this.boss.getSensing().hasLineOfSight(player) && this.boss.isActivated() && this.boss.tickCount > 20) {
         super.addPlayer(player);
      } else {
         this.unseenPlayers.add(player);
      }
   }

   public void removePlayer(ServerPlayer player) {
      super.removePlayer(player);
      this.unseenPlayers.remove(player);
   }

   public void allConvertToUnseen() {
      this.unseenPlayers.addAll(this.getPlayers());
      new ArrayList<ServerPlayer>(this.getPlayers()).forEach(x$0 -> super.removePlayer(x$0));
   }
}
