package com.github.wal_bos.moreskills.command;

import com.github.wal_bos.moreskills.ability.skill.ultimate.CaedrosGodOfConquestSkill;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "tensuramoreskills")
public class CaedrosAspectCommand {
   private static final ResourceLocation CAEDROS_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_god_of_conquest");

   @SubscribeEvent
   public static void onRegisterCommands(RegisterCommandsEvent event) {
      register(event.getDispatcher());
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("caedros")
                     .requires(src -> src.getEntity() instanceof ServerPlayer sp && hasCaedros(sp)))
                  .then(Commands.literal("menu").executes(ctx -> {
                     ServerPlayer p = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
                     CaedrosGodOfConquestSkill.openAspectMenu(p);
                     return 1;
                  })))
               .then(Commands.literal("reconstruct").executes(ctx -> {
                  ServerPlayer p = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
                  CaedrosGodOfConquestSkill.openReconstructMenu(p);
                  return 1;
               })))
            .then(
               Commands.literal("aspect")
                  .executes(
                     ctx -> {
                        ServerPlayer p = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
                        CaedrosGodOfConquestSkill.CaedrosAspect cur = CaedrosGodOfConquestSkill.getActiveAspect(p);
                        ((CommandSourceStack)ctx.getSource())
                           .sendSuccess(() -> Component.literal("Active Aspect: " + (cur == null ? "OFF" : cur.name())).withStyle(ChatFormatting.GRAY), false);
                        return 1;
                     }
                  )
            )
      );
   }

   private static boolean hasCaedros(ServerPlayer player) {
      try {
         SkillStorage skills = SkillAPI.getSkillsFrom(player);
         if (skills == null) {
            return false;
         }

         for (ManasSkillInstance inst : List.copyOf(skills.getLearnedSkills())) {
            if (inst != null && inst.getSkill() != null && CAEDROS_ID.equals(inst.getSkillId())) {
               return true;
            }
         }
      } catch (Throwable var4) {
      }

      return false;
   }
}
