package dev.ftb.mods.ftbteams.client.gui;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ColorConfig;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import dev.ftb.mods.ftblibrary.config.ui.EditConfigScreen;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ColorSelectorPanel;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.ScreenWrapper;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.TextBox;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.VerticalSpaceWidget;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.WidgetLayout.Vertical;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.misc.NordColors;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamMessage;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.client.ClientTeamManager;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import dev.ftb.mods.ftbteams.api.property.TeamProperties;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;
import dev.ftb.mods.ftbteams.client.FTBTeamsClient;
import dev.ftb.mods.ftbteams.config.ServerConfig;
import dev.ftb.mods.ftbteams.data.ClientTeamManagerImpl;
import dev.ftb.mods.ftbteams.data.PlayerPermissions;
import dev.ftb.mods.ftbteams.data.TeamPropertyCollectionImpl;
import dev.ftb.mods.ftbteams.data.TeamType;
import dev.ftb.mods.ftbteams.net.SendMessageMessage;
import dev.ftb.mods.ftbteams.net.ToggleChatRedirectionMessage;
import dev.ftb.mods.ftbteams.net.UpdatePropertiesRequestMessage;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class MyTeamScreen extends BaseScreen implements NordColors {
   private final TeamPropertyCollection properties;
   private final PlayerPermissions permissions;
   private final UUID teamID;
   private Button settingsButton;
   private Button infoButton;
   private Button livesButton;
   private Button missingDataButton;
   private Button colorButton;
   private Button toggleChatButton;
   private Button inviteButton;
   private Button allyButton;
   private Panel memberPanel;
   private Panel chatPanel;
   private TextBox chatBox;
   private static final List<TeamRank> PARTY_RANKS = List.of(TeamRank.OWNER, TeamRank.OFFICER, TeamRank.MEMBER, TeamRank.ALLY);
   private static final int MIN_MEMBER_PANEL_WIDTH = 80;

   public MyTeamScreen(TeamPropertyCollection properties, PlayerPermissions permissions) {
      this.properties = properties;
      this.permissions = permissions;
      this.teamID = this.getManager().selfTeam().getId();
   }

   public static void refreshIfOpen() {
      if (Minecraft.getInstance().screen instanceof ScreenWrapper w && w.getGui() instanceof MyTeamScreen mts) {
         if (mts.getManager().selfTeam().getId().equals(mts.teamID)) {
            mts.refreshWidgets();
         } else {
            mts.closeGui(false);
         }
      }
   }

   private ClientTeamManager getManager() {
      return FTBTeamsAPI.api().getClientManager();
   }

   public boolean onInit() {
      this.setWidth(this.getScreen().getGuiScaledWidth() * 4 / 5);
      this.setHeight(this.getScreen().getGuiScaledHeight() * 4 / 5);
      return true;
   }

   public void addWidgets() {
      this.add(this.settingsButton = new MyTeamScreen.SettingsButton());
      this.add(this.infoButton = new SimpleButton(this, Component.empty(), Icons.INFO, (w, mb) -> {}) {
         public void addMouseOverText(TooltipList list) {
            MyTeamScreen.this.addTeamInfo(list);
         }

         public void playClickSound() {
         }
      });
      if (ServerConfig.limitedLives().isPresent() && this.getManager().selfTeam().isPartyTeam()) {
         this.add(this.livesButton = new MyTeamScreen.LivesButton());
      }

      if (ClientTeamManagerImpl.getInstance().self() == null) {
         this.add(this.missingDataButton = new SimpleButton(this, Component.empty(), Icons.CANCEL, (w, mb) -> {}) {
            public void addMouseOverText(TooltipList list) {
               list.add(Component.translatable("ftbteams.missing_data").withStyle(ChatFormatting.RED));
            }

            public void playClickSound() {
            }
         });
      }

      this.add(
         this.colorButton = new SimpleButton(
            this,
            Component.translatable("gui.color"),
            this.properties.get(TeamProperties.COLOR).withBorder(POLAR_NIGHT_0, false),
            (simpleButton, mouseButton) -> {
               ColorConfig config = new ColorConfig();
               config.setValue(this.properties.get(TeamProperties.COLOR));
               ColorSelectorPanel.popupAtMouse(this.getGui(), config, accepted -> {
                  if (accepted) {
                     Color4I c = (Color4I)config.getValue();
                     this.properties.set(TeamProperties.COLOR, c);
                     simpleButton.setIcon(c.withBorder(POLAR_NIGHT_0, false));
                     TeamPropertyCollection properties = new TeamPropertyCollectionImpl();
                     properties.set(TeamProperties.COLOR, c);
                     NetworkManager.sendToServer(new UpdatePropertiesRequestMessage(properties));
                  }
               });
            }
         ) {
            public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
               this.icon.draw(graphics, x, y, w, h);
            }
         }
      );
      this.add(this.toggleChatButton = new MyTeamScreen.ToggleChatButton(this));
      this.add(this.inviteButton = new MyTeamScreen.InviteButton(this));
      this.add(this.allyButton = new MyTeamScreen.AllyButton(this));
      this.add(this.memberPanel = new MyTeamScreen.MemberPanel());
      this.add(this.chatPanel = new MyTeamScreen.ChatPanel());
      this.add(this.chatBox = new MyTeamScreen.ChatBox());
   }

   public void alignWidgets() {
      super.alignWidgets();
      this.colorButton.setPosAndSize(5, 5, 12, 12);
      this.infoButton.setPosAndSize(20, 3, 16, 16);
      if (this.livesButton != null) {
         this.livesButton.setPosAndSize(40, 3, 16, 16);
      }

      if (this.missingDataButton != null) {
         this.missingDataButton.setPosAndSize(60, 3, 16, 16);
      }

      this.settingsButton.setPosAndSize(this.width - 19, 3, 16, 16);
      this.inviteButton.setPosAndSize(this.width - 37, 3, 16, 16);
      this.allyButton.setPosAndSize(this.width - 55, 3, 16, 16);
      this.toggleChatButton.setPosAndSize(this.width - 73, 3, 16, 16);
      this.memberPanel.setPosAndSize(1, 22, Math.max(this.memberPanel.width, 80), this.height - 23);
   }

   private void addTeamInfo(TooltipList list) {
      ClientTeamManager manager = this.getManager();
      if (manager != null) {
         Team team = this.getManager().selfTeam();
         list.add(Component.translatable(team.getTypeTranslationKey()).withStyle(ChatFormatting.AQUA));
         list.add(Component.translatable("ftbteams.info.id", new Object[]{Component.literal(team.getId().toString()).withStyle(ChatFormatting.YELLOW)}));
         list.add(Component.translatable("ftbteams.info.short_id", new Object[]{Component.literal(team.getShortName()).withStyle(ChatFormatting.YELLOW)}));
         if (!team.getOwner().equals(Util.NIL_UUID)) {
            list.add(Component.translatable("ftbteams.info.owner", new Object[]{this.getManager().formatName(team.getOwner())}));
         }
      }
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      super.drawBackground(graphics, theme, x, y, w, h);
      POLAR_NIGHT_0.draw(graphics, x, y + 21, w, 1);
      POLAR_NIGHT_0.draw(graphics, x + this.memberPanel.width + 1, y + this.memberPanel.posY, 1, this.memberPanel.height + 1);
   }

   public void drawForeground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      super.drawForeground(graphics, theme, x, y, w, h);
      theme.drawString(graphics, this.properties.get(TeamProperties.DISPLAY_NAME), x + w / 2, y + 7, SNOW_STORM_1, 4);
   }

   public boolean keyPressed(Key key) {
      if (key.is(258)) {
         this.chatBox.setFocused(true);
         return true;
      } else {
         return super.keyPressed(key);
      }
   }

   public void refreshChat() {
      this.chatPanel.refreshWidgets();
   }

   private class AllyButton extends SimpleButton {
      public AllyButton(Panel panel) {
         super(panel, Component.translatable("ftbteams.gui.manage_allies"), Icons.FRIENDS, (w, mb) -> new AllyScreen().openGui());
      }

      public boolean isEnabled() {
         if (ClientTeamManagerImpl.getInstance().selfTeam().getType() == TeamType.PARTY && MyTeamScreen.this.permissions.addAlly()) {
            KnownClientPlayer knownPlayer = ClientTeamManagerImpl.getInstance().self();
            return knownPlayer != null && ClientTeamManagerImpl.getInstance().selfTeam().isOfficerOrBetter(knownPlayer.id());
         } else {
            return false;
         }
      }

      public boolean shouldDraw() {
         return this.isEnabled();
      }
   }

   private class ChatBox extends TextBox {
      public ChatBox() {
         super(MyTeamScreen.this);
      }

      public void drawTextBox(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         NordColors.POLAR_NIGHT_3.draw(graphics, x, y, w, h);
      }

      public void onEnterPressed() {
         NetworkManager.sendToServer(new SendMessageMessage(this.getText()));
         this.setText("");
         this.setFocused(true);
      }
   }

   private class ChatPanel extends Panel {
      public ChatPanel() {
         super(MyTeamScreen.this);
      }

      public void addWidgets() {
         UUID prev = null;
         ClientTeamManager manager = MyTeamScreen.this.getManager();
         if (manager != null) {
            for (final TeamMessage message : manager.selfTeam().getMessageHistory()) {
               if (!message.sender().equals(prev)) {
                  this.add(new VerticalSpaceWidget(this, 2));
                  Component name = manager.formatName(message.sender()).copy().append(":");
                  this.add(new TextField(this).setMaxWidth(this.width).setText(name));
                  prev = message.sender();
               }

               this.add((new TextField(this) {
                  public void addMouseOverText(TooltipList list) {
                     list.add(Component.literal(DateFormat.getInstance().format(new Date(message.date()))));
                  }
               }).setMaxWidth(this.width).setText(Component.literal("  ").append(message.text())));
            }

            if (!this.widgets.isEmpty()) {
               this.add(new TextField(this).setMaxWidth(this.width).setText(Component.empty()));
            }
         }
      }

      public void alignWidgets() {
         this.align(new Vertical(2, 1, 1));
         this.movePanelScroll(0.0, this.getContentHeight());
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         NordColors.POLAR_NIGHT_2.draw(graphics, x, y, w, h);
      }
   }

   private class InviteButton extends SimpleButton {
      public InviteButton(Panel panel) {
         super(panel, Component.translatable("ftbteams.gui.invite"), Icons.ADD, (w, mb) -> new InviteScreen().openGui());
      }

      public boolean isEnabled() {
         if (ClientTeamManagerImpl.getInstance().selfTeam().getType() == TeamType.PARTY && MyTeamScreen.this.permissions.invitePlayer()) {
            KnownClientPlayer knownPlayer = ClientTeamManagerImpl.getInstance().self();
            return knownPlayer != null && ClientTeamManagerImpl.getInstance().selfTeam().isOfficerOrBetter(knownPlayer.id());
         } else {
            return false;
         }
      }

      public boolean shouldDraw() {
         return this.isEnabled();
      }
   }

   private class LivesButton extends SimpleButton {
      public LivesButton() {
         super(MyTeamScreen.this, Component.empty(), Icons.HEART, (btn, mb) -> {});
      }

      public void addMouseOverText(TooltipList list) {
         FTBTeamsClient.addLivesIconTooltip().forEach(list::add);
      }

      public void drawIcon(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         super.drawIcon(graphics, theme, x, y, w, h);
         graphics.pose().pushPose();
         graphics.pose().translate(x, y, 0.0F);
         FTBTeamsClient.renderLivesIconOverlay(graphics, theme.getFont(), w);
         graphics.pose().popPose();
      }
   }

   private class MemberPanel extends Panel {
      public MemberPanel() {
         super(MyTeamScreen.this);
      }

      public void addWidgets() {
         ClientTeamManager manager = MyTeamScreen.this.getManager();
         if (manager != null && manager.isValid()) {
            Map<TeamRank, List<KnownClientPlayer>> byRank = new EnumMap<>(TeamRank.class);
            manager.selfTeam()
               .getPlayersByRank(TeamRank.NONE)
               .forEach((id, rankx) -> manager.getKnownPlayer(id).ifPresent(kcp -> byRank.computeIfAbsent(rankx, k -> new ArrayList<>()).add(kcp)));

            for (TeamRank rank : MyTeamScreen.PARTY_RANKS) {
               byRank.getOrDefault(rank, List.of())
                  .stream()
                  .sorted(Comparator.comparing(KnownClientPlayer::name))
                  .map(kcp -> new MemberButton(this, kcp))
                  .forEach(this::add);
            }

            if (manager.selfTeam().isPlayerTeam()) {
               this.add(new CreatePartyButton(this, MyTeamScreen.this.permissions.createParty()));
            }
         }
      }

      public void alignWidgets() {
         this.align(new Vertical(1, 2, 1));
         this.width = 80;

         for (Widget widget : this.widgets) {
            this.width = Math.max(this.width, widget.width);
         }

         for (Widget widget : this.widgets) {
            widget.setX(1);
            widget.setWidth(this.width - 2);
         }

         MyTeamScreen.this.chatPanel
            .setPosAndSize(this.width + 3, 23, MyTeamScreen.this.width - MyTeamScreen.this.memberPanel.width - 5, MyTeamScreen.this.height - 40);
         MyTeamScreen.this.chatBox.setPosAndSize(MyTeamScreen.this.chatPanel.posX, MyTeamScreen.this.height - 15, MyTeamScreen.this.chatPanel.width, 13);
      }
   }

   private class SettingsButton extends SimpleButton {
      public SettingsButton() {
         super(MyTeamScreen.this, Component.translatable("gui.settings"), Icons.SETTINGS.withTint(NordColors.SNOW_STORM_2), (simpleButton, mouseButton) -> {
            ConfigGroup config = new ConfigGroup("ftbteamsconfig", accepted -> {
               if (accepted) {
                  NetworkManager.sendToServer(new UpdatePropertiesRequestMessage(MyTeamScreen.this.properties));
               }

               MyTeamScreen.this.openGui();
            });
            Map<String, ConfigGroup> subGroups = new HashMap<>();
            MyTeamScreen.this.properties.forEach((key, value) -> {
               if (!key.isHidden()) {
                  String groupName = key.getId().getNamespace();
                  ConfigGroup cfg = subGroups.computeIfAbsent(groupName, k -> config.getOrCreateSubgroup(groupName));
                  ConfigValue<?> val = key.config(cfg, value);
                  if (val != null && !key.isPlayerEditable()) {
                     val.setCanEdit(false);
                  }
               }
            });
            new EditConfigScreen(config).openGui();
         });
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         this.drawIcon(graphics, theme, x, y, w, h);
      }
   }

   private static class ToggleChatButton extends SimpleButton {
      public ToggleChatButton(Panel panel) {
         super(
            panel,
            Component.translatable("ftbteams.gui.toggle_chat"),
            Icons.CHAT,
            (b, mb) -> NetworkManager.sendToServer(ToggleChatRedirectionMessage.INSTANCE)
         );
      }

      public void addMouseOverText(TooltipList list) {
         list.add(Component.translatable("ftbteams.message.chat_redirected." + (FTBTeamsClient.isChatRedirected() ? "on" : "off")));
         list.add(Component.translatable("ftbteams.gui.toggle_chat").withStyle(ChatFormatting.GRAY));
      }

      public void tick() {
         this.setIcon(FTBTeamsClient.isChatRedirected() ? Icons.CHAT.withTint(Color4I.rgb(16752736)) : Icons.CHAT);
      }

      public boolean isEnabled() {
         return ClientTeamManagerImpl.getInstance().selfTeam().getType() == TeamType.PARTY;
      }

      public boolean shouldDraw() {
         return this.isEnabled();
      }
   }
}
