package org.crypticdev.stextras.command.system;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.ClickEvent.Action;
import org.crypticdev.stextras.command.support.CommandSupport;
import org.crypticdev.stextras.command.support.CommandText;

public final class WikiCommands {
   private static final String WIKI_URL = "https://slimethrone.net/help/topics/slimethrone-extras-faq/";

   public static LiteralArgumentBuilder<CommandSourceStack> build() {
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("wiki")
         .executes(
            ctx -> {
               CommandSupport.info(
                  ctx,
                  Component.empty()
                     .append(CommandText.label("wiki"))
                     .append(Component.literal(": ").withStyle(ChatFormatting.DARK_GRAY))
                     .append(
                        Component.literal("Open SlimeThrone Extras FAQ")
                           .withStyle(
                              style -> style.withColor(ChatFormatting.AQUA)
                                 .withUnderlined(true)
                                 .withClickEvent(new ClickEvent(Action.OPEN_URL, "https://slimethrone.net/help/topics/slimethrone-extras-faq/"))
                                 .withHoverEvent(
                                    new HoverEvent(
                                       net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT,
                                       Component.literal("Click to open the wiki").withStyle(ChatFormatting.YELLOW)
                                    )
                                 )
                           )
                     ),
                  false
               );
               return 1;
            }
         );
   }

   private WikiCommands() {
   }
}
