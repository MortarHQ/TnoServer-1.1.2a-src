package io.github.Memoires.mysticism.command.edit;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.Memoires.mysticism.command.MysticismCommands;
import io.github.Memoires.mysticism.handler.AttributeHandler;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.soulEnergy.ISoulEnergy;
import io.github.manasmods.manascore.command.api.Command;
import io.github.manasmods.manascore.command.api.Execute;
import io.github.manasmods.manascore.command.api.Permission;
import io.github.manasmods.manascore.command.api.Permission.PermissionLevel;
import io.github.manasmods.manascore.command.api.parameter.EntityArg;
import io.github.manasmods.manascore.command.api.parameter.SenderArg;
import io.github.manasmods.manascore.command.api.parameter.EntityArg.Type;
import io.github.manasmods.manascore.command.api.parameter.primitive.LiteralArg;
import java.util.Objects;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

@Command("reset")
@Permission(value = "mystic.command.reset", permissionLevel = PermissionLevel.GAMEMASTER)
public class MysticResetCommand {
   @Execute
   public boolean resetSoulEnergy(@SenderArg CommandSourceStack stack, @EntityArg(Type.PLAYERS) EntitySelector selector, @LiteralArg("soulEnergy") String dl) throws CommandSyntaxException {
      for (ServerPlayer player : selector.findPlayers(stack)) {
         ISoulEnergy soulEnergyStorage = MysticismStorages.getSoulEnergyFrom(player);
         int newSoulEnergy = AttributeHandler.getWeightedSoulEnergy();
         Objects.requireNonNull(player.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY)).setBaseValue(newSoulEnergy);
         soulEnergyStorage.setSoulEnergy(newSoulEnergy);
         soulEnergyStorage.markDirty();
         player.sendSystemMessage(Component.literal("Soul Energy: " + newSoulEnergy));
         MysticismCommands.sendSuccess(
            stack,
            player,
            Component.translatable("mysticism.command.soul_energy.reset", new Object[]{player.getName(), MysticismCommands.getShortenedNumber(newSoulEnergy)})
         );
      }

      return true;
   }
}
