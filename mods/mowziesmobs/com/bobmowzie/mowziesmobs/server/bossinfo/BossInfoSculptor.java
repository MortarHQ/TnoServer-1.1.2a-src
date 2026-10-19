package com.bobmowzie.mowziesmobs.server.bossinfo;

import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.google.common.base.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.server.level.ServerPlayer;

public class BossInfoSculptor extends MMBossInfoServer {
   private final EntitySculptor sculptor;
   private Component displayName;
   private static final Component TESTING_NAME = Component.translatable("entity.mowziesmobs.sculptor.boss_info.timer.text");

   public BossInfoSculptor(EntitySculptor entity) {
      super(entity);
      this.sculptor = entity;
      this.displayName = this.name;
   }

   @Override
   public void update() {
      if (this.sculptor.isTesting() && !this.sculptor.isTestPassed() && !this.sculptor.isTestFailed()) {
         if (!this.sculptor.isRemoved() && !this.sculptor.isDeadOrDying()) {
            this.setProgress(1.0F - (float)this.sculptor.getTestTimePassed() / this.sculptor.getMaxTestTime());
            if (this.sculptor.getTestingPlayer() instanceof ServerPlayer) {
               this.addPlayer((ServerPlayer)this.sculptor.getTestingPlayer());
            }

            this.setDisplayName(TESTING_NAME);
            this.setVisible(true);
         } else {
            this.setVisible(false);
         }
      } else {
         this.setVisible(this.sculptor.hasBossBar() && this.sculptor.isFighting());
         this.setDisplayName(this.name);
         super.update();
      }
   }

   public Component getName() {
      return this.displayName;
   }

   public void setDisplayName(Component displayName) {
      if (!Objects.equal(displayName, this.displayName)) {
         this.displayName = displayName;
         if (this.isVisible()) {
            ClientboundBossEventPacket clientboundbosseventpacket = ClientboundBossEventPacket.createUpdateNamePacket(this);

            for (ServerPlayer serverplayer : this.getPlayers()) {
               serverplayer.connection.send(clientboundbosseventpacket);
            }
         }
      }
   }
}
