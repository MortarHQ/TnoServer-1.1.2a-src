package com.github.wal_bos.moreutils.command;

import com.github.wal_bos.moreutils.data.MoreUtilsLeaderboardData;
import com.github.wal_bos.moreutils.registry.MoreUtilsGameRules;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.ResolvableProfile;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class MoreUtilsLeaderboardCommand {
   private static final DecimalFormat EP_FORMAT = new DecimalFormat("#,###");
   private static final int ROWS = 6;
   private static final int SIZE = 54;
   private static final int ENTRIES_PER_PAGE = 45;
   private static final int SLOT_SORT_EP = 45;
   private static final int SLOT_SORT_UNIQUE = 46;
   private static final int SLOT_SORT_ULTIMATE = 47;
   private static final int SLOT_PREVIOUS = 48;
   private static final int SLOT_INFO = 49;
   private static final int SLOT_NEXT = 50;
   private static final int SLOT_CLOSE = 53;

   private MoreUtilsLeaderboardCommand() {
   }

   public static void register(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register((LiteralArgumentBuilder)Commands.literal("tmuleaderboard").executes(context -> open((CommandSourceStack)context.getSource())));
   }

   private static int open(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer viewer = source.getPlayerOrException();
      if (!viewer.level().getGameRules().getBoolean(MoreUtilsGameRules.ALLOW_NON_ADMIN_LEADERBOARD) && !source.hasPermission(2)) {
         viewer.sendSystemMessage(Component.literal("The TMU leaderboard is disabled for non-admin players.").withStyle(ChatFormatting.RED));
         return 0;
      }

      MoreUtilsLeaderboardData data = MoreUtilsLeaderboardData.get(viewer.server);
      data.updateOnlinePlayers(viewer.server);
      Set<UUID> online = new HashSet<>();

      for (ServerPlayer player : viewer.server.getPlayerList().getPlayers()) {
         online.add(player.getUUID());
      }

      List<MoreUtilsLeaderboardCommand.Entry> entries = new ArrayList<>();

      for (MoreUtilsLeaderboardData.Snapshot snapshot : data.getSnapshots()) {
         entries.add(MoreUtilsLeaderboardCommand.Entry.from(snapshot, online.contains(snapshot.uuid())));
      }

      viewer.openMenu(
         new SimpleMenuProvider(
            (id, inventory, playerx) -> new MoreUtilsLeaderboardCommand.LeaderboardMenu(id, inventory, entries),
            Component.literal("TMU Leaderboard").withStyle(ChatFormatting.GOLD)
         )
      );
      return 1;
   }

   private static ItemStack createHead(int rank, MoreUtilsLeaderboardCommand.Entry entry, MoreUtilsLeaderboardCommand.SortMode mode) {
      ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
      GameProfile profile = new GameProfile(entry.uuid(), entry.name());
      stack.set(DataComponents.PROFILE, new ResolvableProfile(profile));
      stack.set(DataComponents.CUSTOM_NAME, Component.literal("#" + rank + " " + entry.name()).withStyle(ChatFormatting.GOLD));
      stack.set(
         DataComponents.LORE,
         new ItemLore(
            List.of(
               Component.literal("Sorted By: ").withStyle(ChatFormatting.DARK_GRAY).append(Component.literal(mode.display()).withStyle(ChatFormatting.YELLOW)),
               Component.literal("Status: ")
                  .withStyle(ChatFormatting.GRAY)
                  .append(Component.literal(entry.online() ? "Online" : "Offline").withStyle(entry.online() ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY)),
               Component.literal("EP: ").withStyle(ChatFormatting.GRAY).append(Component.literal(EP_FORMAT.format(entry.ep())).withStyle(ChatFormatting.AQUA)),
               Component.literal("Unique Skills: ")
                  .withStyle(ChatFormatting.GRAY)
                  .append(Component.literal(String.valueOf(entry.uniqueSkills())).withStyle(ChatFormatting.LIGHT_PURPLE)),
               Component.literal("Ultimate Skills: ")
                  .withStyle(ChatFormatting.GRAY)
                  .append(Component.literal(String.valueOf(entry.ultimateSkills())).withStyle(ChatFormatting.RED))
            )
         )
      );
      return stack;
   }

   private static ItemStack button(ItemStack stack, Component name, List<Component> lore) {
      stack.set(DataComponents.CUSTOM_NAME, name);
      stack.set(DataComponents.LORE, new ItemLore(lore));
      return stack;
   }

   private static ItemStack filler() {
      ItemStack stack = new ItemStack(Items.GRAY_STAINED_GLASS_PANE);
      stack.set(DataComponents.CUSTOM_NAME, Component.empty());
      return stack;
   }

   private record Entry(UUID uuid, String name, double ep, int uniqueSkills, int ultimateSkills, boolean online) {
      private static MoreUtilsLeaderboardCommand.Entry from(MoreUtilsLeaderboardData.Snapshot snapshot, boolean online) {
         return new MoreUtilsLeaderboardCommand.Entry(
            snapshot.uuid(), snapshot.name(), snapshot.ep(), snapshot.uniqueSkills(), snapshot.ultimateSkills(), online
         );
      }
   }

   private static final class LeaderboardMenu extends ChestMenu {
      private final SimpleContainer container;
      private final List<MoreUtilsLeaderboardCommand.Entry> entries;
      private MoreUtilsLeaderboardCommand.SortMode sortMode = MoreUtilsLeaderboardCommand.SortMode.EP;
      private int page = 0;

      private LeaderboardMenu(int containerId, Inventory inventory, List<MoreUtilsLeaderboardCommand.Entry> entries) {
         this(containerId, inventory, new SimpleContainer(54), entries);
      }

      private LeaderboardMenu(int containerId, Inventory inventory, SimpleContainer container, List<MoreUtilsLeaderboardCommand.Entry> entries) {
         super(MenuType.GENERIC_9x6, containerId, inventory, container, 6);
         this.container = container;
         this.entries = new ArrayList<>(entries);
         this.redraw();
      }

      private void redraw() {
         this.entries.sort(this.sortMode.comparator());

         for (int i = 0; i < 54; i++) {
            this.container.setItem(i, ItemStack.EMPTY);
         }

         int maxPage = this.maxPage();
         if (this.page > maxPage) {
            this.page = maxPage;
         }

         int start = this.page * 45;
         int end = Math.min(start + 45, this.entries.size());

         for (int i = start; i < end; i++) {
            MoreUtilsLeaderboardCommand.Entry entry = this.entries.get(i);
            this.container.setItem(i - start, MoreUtilsLeaderboardCommand.createHead(i + 1, entry, this.sortMode));
         }

         for (int i = 45; i < 54; i++) {
            this.container.setItem(i, MoreUtilsLeaderboardCommand.filler());
         }

         this.container
            .setItem(
               45,
               MoreUtilsLeaderboardCommand.button(
                  new ItemStack(this.sortMode == MoreUtilsLeaderboardCommand.SortMode.EP ? Items.LIME_DYE : Items.PAPER),
                  Component.literal("Sort by EP")
                     .withStyle(this.sortMode == MoreUtilsLeaderboardCommand.SortMode.EP ? ChatFormatting.GREEN : ChatFormatting.YELLOW),
                  List.of(Component.literal("Click to rank players by highest EP.").withStyle(ChatFormatting.GRAY))
               )
            );
         this.container
            .setItem(
               46,
               MoreUtilsLeaderboardCommand.button(
                  new ItemStack(this.sortMode == MoreUtilsLeaderboardCommand.SortMode.UNIQUE ? Items.LIME_DYE : Items.BOOK),
                  Component.literal("Sort by Unique Skills")
                     .withStyle(this.sortMode == MoreUtilsLeaderboardCommand.SortMode.UNIQUE ? ChatFormatting.GREEN : ChatFormatting.LIGHT_PURPLE),
                  List.of(Component.literal("Click to rank players by most Unique Skills.").withStyle(ChatFormatting.GRAY))
               )
            );
         this.container
            .setItem(
               47,
               MoreUtilsLeaderboardCommand.button(
                  new ItemStack(this.sortMode == MoreUtilsLeaderboardCommand.SortMode.ULTIMATE ? Items.LIME_DYE : Items.NETHER_STAR),
                  Component.literal("Sort by Ultimate Skills")
                     .withStyle(this.sortMode == MoreUtilsLeaderboardCommand.SortMode.ULTIMATE ? ChatFormatting.GREEN : ChatFormatting.RED),
                  List.of(Component.literal("Click to rank players by most Ultimate Skills.").withStyle(ChatFormatting.GRAY))
               )
            );
         this.container
            .setItem(
               48,
               MoreUtilsLeaderboardCommand.button(
                  new ItemStack(Items.ARROW),
                  Component.literal("Previous Page").withStyle(ChatFormatting.AQUA),
                  List.of(Component.literal("Page " + (this.page + 1) + "/" + (maxPage + 1)).withStyle(ChatFormatting.GRAY))
               )
            );
         int onlineCount = 0;

         for (MoreUtilsLeaderboardCommand.Entry entry : this.entries) {
            if (entry.online()) {
               onlineCount++;
            }
         }

         this.container
            .setItem(
               49,
               MoreUtilsLeaderboardCommand.button(
                  new ItemStack(Items.COMPASS),
                  Component.literal("TMU Leaderboard").withStyle(ChatFormatting.GOLD),
                  List.of(
                     Component.literal("Saved players: " + this.entries.size()).withStyle(ChatFormatting.GRAY),
                     Component.literal("Online players: " + onlineCount).withStyle(ChatFormatting.GREEN),
                     Component.literal("Current sort: " + this.sortMode.display()).withStyle(ChatFormatting.YELLOW),
                     Component.literal("Page: " + (this.page + 1) + "/" + (maxPage + 1)).withStyle(ChatFormatting.GRAY)
                  )
               )
            );
         this.container
            .setItem(
               50,
               MoreUtilsLeaderboardCommand.button(
                  new ItemStack(Items.ARROW),
                  Component.literal("Next Page").withStyle(ChatFormatting.AQUA),
                  List.of(Component.literal("Page " + (this.page + 1) + "/" + (maxPage + 1)).withStyle(ChatFormatting.GRAY))
               )
            );
         this.container
            .setItem(
               53,
               MoreUtilsLeaderboardCommand.button(
                  new ItemStack(Items.BARRIER),
                  Component.literal("Close").withStyle(ChatFormatting.RED),
                  List.of(Component.literal("Click to close the leaderboard.").withStyle(ChatFormatting.GRAY))
               )
            );
         this.broadcastChanges();
      }

      private int maxPage() {
         return this.entries.isEmpty() ? 0 : (this.entries.size() - 1) / 45;
      }

      public ItemStack quickMoveStack(Player player, int index) {
         return ItemStack.EMPTY;
      }

      public void clicked(int slotId, int button, ClickType clickType, Player player) {
         if (player instanceof ServerPlayer serverPlayer) {
            if (slotId == 45) {
               this.sortMode = MoreUtilsLeaderboardCommand.SortMode.EP;
               this.page = 0;
               this.redraw();
            } else if (slotId == 46) {
               this.sortMode = MoreUtilsLeaderboardCommand.SortMode.UNIQUE;
               this.page = 0;
               this.redraw();
            } else if (slotId == 47) {
               this.sortMode = MoreUtilsLeaderboardCommand.SortMode.ULTIMATE;
               this.page = 0;
               this.redraw();
            } else if (slotId == 48) {
               if (this.page > 0) {
                  this.page--;
                  this.redraw();
               }
            } else if (slotId == 50) {
               if (this.page < this.maxPage()) {
                  this.page++;
                  this.redraw();
               }
            } else {
               if (slotId == 53) {
                  serverPlayer.closeContainer();
               }
            }
         }
      }

      public boolean stillValid(Player player) {
         return true;
      }
   }

   private enum SortMode {
      EP,
      UNIQUE,
      ULTIMATE;

      private Comparator<MoreUtilsLeaderboardCommand.Entry> comparator() {
         return switch (this) {
            case EP -> Comparator.comparingDouble(MoreUtilsLeaderboardCommand.Entry::ep)
               .reversed()
               .thenComparing(Comparator.comparingInt(MoreUtilsLeaderboardCommand.Entry::ultimateSkills).reversed())
               .thenComparing(Comparator.comparingInt(MoreUtilsLeaderboardCommand.Entry::uniqueSkills).reversed())
               .thenComparing(MoreUtilsLeaderboardCommand.Entry::name);
            case UNIQUE -> Comparator.comparingInt(MoreUtilsLeaderboardCommand.Entry::uniqueSkills)
               .reversed()
               .thenComparing(Comparator.comparingDouble(MoreUtilsLeaderboardCommand.Entry::ep).reversed())
               .thenComparing(Comparator.comparingInt(MoreUtilsLeaderboardCommand.Entry::ultimateSkills).reversed())
               .thenComparing(MoreUtilsLeaderboardCommand.Entry::name);
            case ULTIMATE -> Comparator.comparingInt(MoreUtilsLeaderboardCommand.Entry::ultimateSkills)
               .reversed()
               .thenComparing(Comparator.comparingDouble(MoreUtilsLeaderboardCommand.Entry::ep).reversed())
               .thenComparing(Comparator.comparingInt(MoreUtilsLeaderboardCommand.Entry::uniqueSkills).reversed())
               .thenComparing(MoreUtilsLeaderboardCommand.Entry::name);
         };
      }

      private String display() {
         return switch (this) {
            case EP -> "EP";
            case UNIQUE -> "Unique Skills";
            case ULTIMATE -> "Ultimate Skills";
         };
      }
   }
}
