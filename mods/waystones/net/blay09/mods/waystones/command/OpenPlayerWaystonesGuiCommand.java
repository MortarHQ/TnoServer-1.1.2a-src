package net.blay09.mods.waystones.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.menu.BalmMenuProvider;
import net.blay09.mods.waystones.api.TeleportFlags;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.comparator.WaystoneComparators;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneImpl;
import net.blay09.mods.waystones.menu.ModMenus;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class OpenPlayerWaystonesGuiCommand implements Command<CommandSourceStack> {
   public int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      final ServerPlayer target = ((EntitySelector)ctx.getArgument("player", EntitySelector.class)).findSinglePlayer((CommandSourceStack)ctx.getSource());
      ServerPlayer op = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
      final List<Waystone> waystones = PlayerWaystoneManager.getActivatedWaystones(target)
         .stream()
         .sorted(WaystoneComparators.forAdminInspection(op, target))
         .toList();
      var menuProvider = new BalmMenuProvider<Collection<Waystone>>() {
         public Component getDisplayName() {
            return Component.translatable("container.waystones.waystone_admin_selection", new Object[]{target.getScoreboardName()});
         }

         public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
            return new WaystoneSelectionMenu(
               (MenuType<WaystoneSelectionMenu>)ModMenus.adminSelection.get(), null, windowId, waystones, Set.of(TeleportFlags.ADMIN)
            );
         }

         public Collection<Waystone> getScreenOpeningData(ServerPlayer serverPlayer) {
            return waystones;
         }

         public StreamCodec<RegistryFriendlyByteBuf, Collection<Waystone>> getScreenStreamCodec() {
            return WaystoneImpl.LIST_STREAM_CODEC;
         }
      };
      Balm.getNetworking().openGui(op, menuProvider);
      return 0;
   }
}
