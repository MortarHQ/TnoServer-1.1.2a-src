package net.p3pp3rf1y.sophisticatedbackpacks.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.ClickEvent.Action;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.AccessLogRecord;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackAccessLogger;

public class ListCommand {
   private ListCommand() {
   }

   static ArgumentBuilder<CommandSourceStack, ?> register() {
      return ((LiteralArgumentBuilder)Commands.literal("list")
            .executes(context -> printBackpackList(new ArrayList<>(BackpackAccessLogger.getAllBackpackLogs()), (CommandSourceStack)context.getSource())))
         .then(
            Commands.argument("playerName", BackpackPlayerArgumentType.playerName())
               .executes(
                  context -> printBackpackList(
                     new ArrayList<>(BackpackAccessLogger.getBackpackLogsForPlayer((String)context.getArgument("playerName", String.class))),
                     (CommandSourceStack)context.getSource()
                  )
               )
         );
   }

   private static int printBackpackList(List<AccessLogRecord> allLogs, CommandSourceStack source) {
      SimpleDateFormat dateFormat = new SimpleDateFormat();
      allLogs.sort(Comparator.comparing(AccessLogRecord::getAccessTime).reversed());
      source.sendSuccess(() -> Component.translatable("commands.sophisticatedbackpacks.list.header"), false);
      allLogs.forEach(
         alr -> {
            MutableComponent message = Component.literal("");
            message.append(
               Component.literal(alr.getBackpackName())
                  .withStyle(
                     s -> s.withColor(ChatFormatting.GREEN)
                        .withClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/sophisticatedbackpacks give @p " + alr.getBackpackUuid()))
                        .withHoverEvent(
                           new HoverEvent(
                              net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT,
                              Component.translatable("chat.sophisticatedbackpacks.backpack_uuid.tooltip", new Object[]{alr.getBackpackUuid().toString()})
                           )
                        )
                  )
            );
            message.append(Component.literal(", "));
            MutableComponent clothColor = Component.translatable("commands.sophisticatedbackpacks.list.cloth_color");
            clothColor.withStyle(clothColor.getStyle().withColor(TextColor.fromRgb(alr.getClothColor())));
            message.append(clothColor);
            message.append(Component.literal(" "));
            MutableComponent trimColor = Component.translatable("commands.sophisticatedbackpacks.list.trim_color");
            trimColor.withStyle(trimColor.getStyle().withColor(TextColor.fromRgb(alr.getTrimColor())));
            message.append(trimColor);
            message.append(Component.literal(", "));
            message.append(Component.literal(alr.getPlayerName()));
            message.append(Component.literal(", "));
            message.append(Component.literal(dateFormat.format(new Date(alr.getAccessTime()))));
            source.sendSuccess(() -> message, false);
         }
      );
      return 0;
   }
}
