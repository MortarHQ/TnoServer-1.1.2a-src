package org.popcraft.chunky.platform;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.popcraft.chunky.platform.util.Location;
import org.popcraft.chunky.util.Translator;

public class NeoForgePlayer extends NeoForgeSender implements Player {
   private final ServerPlayer player;

   public NeoForgePlayer(ServerPlayer player) {
      super(player.createCommandSourceStack());
      this.player = player;
   }

   @Override
   public boolean isPlayer() {
      return true;
   }

   @Override
   public String getName() {
      return this.player.getName().toString();
   }

   @Override
   public World getWorld() {
      return new NeoForgeWorld(this.player.serverLevel());
   }

   @Override
   public Location getLocation() {
      return new Location(this.getWorld(), this.player.getX(), this.player.getY(), this.player.getZ(), this.player.getXRot(), this.player.getYRot());
   }

   @Override
   public void sendMessage(String key, boolean prefixed, Object... args) {
      this.player.sendSystemMessage(this.formatColored(Translator.translateKey(key, prefixed, args)));
   }

   @Override
   public UUID getUUID() {
      return this.player.getUUID();
   }

   @Override
   public void teleport(Location location) {
      this.player
         .teleportTo(((NeoForgeWorld)location.getWorld()).getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
   }

   @Override
   public void sendActionBar(String key) {
      this.player.displayClientMessage(this.formatColored(Translator.translateKey(key, false)), true);
   }

   private Component formatColored(String message) {
      return Component.nullToEmpty(message.replaceAll("&(?=[0-9a-fk-orA-FK-OR])", "§"));
   }
}
