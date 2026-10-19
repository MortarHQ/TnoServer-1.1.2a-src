package com.sjyueluo.tensura_skill_books.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.sjyueluo.tensura_skill_books.catalog.SkillCatalog;
import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import com.sjyueluo.tensura_skill_books.client.icon.TensuraSkillIconResolver;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class SkillBooksCommands {
   private SkillBooksCommands() {
   }

   public static void onRegisterCommands(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("skillbooks").requires(source -> source.hasPermission(2)))
               .then(
                  Commands.literal("dump")
                     .executes(
                        context -> {
                           int total = SkillCatalog.all().size();
                           ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("SkillCatalog total: " + total), false);

                           for (SkillCategory category : SkillCategory.values()) {
                              int count = SkillCatalog.byCategory().get(category).size();
                              ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal(category + ": " + count), false);
                           }

                           List<ResourceLocation> missing = TensuraSkillIconResolver.missingIconsSample(30);
                           if (!missing.isEmpty()) {
                              ((CommandSourceStack)context.getSource())
                                 .sendSuccess(() -> Component.literal("Missing icons (first " + missing.size() + "): " + missing), false);
                           }

                           return 1;
                        }
                     )
               )
         );
   }
}
