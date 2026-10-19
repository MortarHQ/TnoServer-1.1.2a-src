package dev.ftb.mods.ftbteams.client.gui;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import dev.ftb.mods.ftbteams.net.PlayerGUIOperationMessage;
import net.minecraft.network.chat.Component;

public class InviteScreen extends BaseInvitationScreen {
   public InviteScreen() {
      super(Component.translatable("ftbteams.gui.invite"));
   }

   @Override
   protected boolean shouldIncludePlayer(KnownClientPlayer player) {
      return player.isOnlineAndNotInParty() && !player.equals(FTBTeamsAPI.api().getClientManager().self());
   }

   @Override
   protected BaseInvitationScreen.ExecuteButton makeExecuteButton() {
      return new BaseInvitationScreen.ExecuteButton(Component.translatable("ftbteams.gui.send_invite"), Icons.ADD, () -> {
         NetworkManager.sendToServer(PlayerGUIOperationMessage.forGameProfiles(PlayerGUIOperationMessage.Operation.INVITE, this.invites));
         this.closeGui();
      }) {
         public boolean isEnabled() {
            return !InviteScreen.this.invites.isEmpty();
         }
      };
   }
}
