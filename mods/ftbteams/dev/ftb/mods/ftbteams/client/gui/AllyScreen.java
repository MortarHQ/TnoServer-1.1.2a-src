package dev.ftb.mods.ftbteams.client.gui;

import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import dev.ftb.mods.ftbteams.data.ClientTeamManagerImpl;
import dev.ftb.mods.ftbteams.net.PlayerGUIOperationMessage;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.network.chat.Component;

public class AllyScreen extends BaseInvitationScreen {
   private final Set<GameProfile> existingAllies = new HashSet<>();
   private Set<GameProfile> toAdd = new HashSet<>();
   private Set<GameProfile> toRemove = new HashSet<>();

   public AllyScreen() {
      super(Component.translatable("ftbteams.gui.manage_allies"));
      this.available.forEach(knownClientPlayer -> {
         if (ClientTeamManagerImpl.getInstance().selfTeam().isAllyOrBetter(knownClientPlayer.id())) {
            this.existingAllies.add(knownClientPlayer.profile());
            this.invites.add(knownClientPlayer.profile());
         }
      });
   }

   @Override
   protected boolean shouldIncludePlayer(KnownClientPlayer player) {
      return player.online() && !ClientTeamManagerImpl.getInstance().selfTeam().isMember(player.id());
   }

   @Override
   public void setInvited(GameProfile profile, boolean invited) {
      super.setInvited(profile, invited);
      this.toRemove = Sets.difference(this.existingAllies, this.invites);
      this.toAdd = Sets.difference(this.invites, this.existingAllies);
   }

   @Override
   protected BaseInvitationScreen.ExecuteButton makeExecuteButton() {
      return new BaseInvitationScreen.ExecuteButton(Component.translatable("gui.accept"), Icons.ADD, () -> {
         if (!this.toAdd.isEmpty()) {
            NetworkManager.sendToServer(PlayerGUIOperationMessage.forGameProfiles(PlayerGUIOperationMessage.Operation.ADD_ALLY, this.toAdd));
         }

         if (!this.toRemove.isEmpty()) {
            NetworkManager.sendToServer(PlayerGUIOperationMessage.forGameProfiles(PlayerGUIOperationMessage.Operation.REMOVE_ALLY, this.toRemove));
         }

         this.closeGui();
      }) {
         public boolean isEnabled() {
            return !AllyScreen.this.toAdd.isEmpty() || !AllyScreen.this.toRemove.isEmpty();
         }
      };
   }
}
