package com.github.wal_bos.moreskills.command;

import com.github.wal_bos.moreskills.ai.TensuraAiClient;
import com.github.wal_bos.moreskills.ai.TensuraAiConfig;
import com.github.wal_bos.moreskills.ai.TensuraAiKnowledge;
import com.github.wal_bos.moreskills.ai.TensuraWikiKnowledge;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
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
public final class RaphaelCommand {
   private static final Map<UUID, Long> COOLDOWNS = new ConcurrentHashMap<>();
   private static final AtomicInteger ACTIVE_REQUESTS = new AtomicInteger();

   private RaphaelCommand() {
   }

   @SubscribeEvent
   public static void onRegisterCommands(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("raphael").requires(source -> source.hasPermission(0)))
                  .then(
                     Commands.argument("question", StringArgumentType.greedyString())
                        .executes(context -> ask((CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "question")))
                  ))
               .executes(context -> {
                  ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("Usage: /raphael <question>"));
                  return 0;
               })
         );
   }

   private static int ask(CommandSourceStack source, String question) {
      ServerPlayer player;
      try {
         player = source.getPlayerOrException();
      } catch (Exception exception) {
         source.sendFailure(Component.literal("Raphael can only be used by players."));
         return 0;
      }

      String cleanQuestion = question == null ? "" : question.trim();
      if (cleanQuestion.isEmpty()) {
         player.sendSystemMessage(Component.literal("Raphael: Ask a question first.").withStyle(ChatFormatting.RED));
         return 0;
      }

      if (cleanQuestion.length() > 300) {
         player.sendSystemMessage(Component.literal("Raphael: Your question is too long.").withStyle(ChatFormatting.RED));
         return 0;
      }

      long now = System.currentTimeMillis();
      long nextAllowed = COOLDOWNS.getOrDefault(player.getUUID(), 0L);
      if (now < nextAllowed) {
         long seconds = Math.max(1L, (nextAllowed - now + 999L) / 1000L);
         player.sendSystemMessage(Component.literal("Raphael: Thought circuit cooling down. Wait " + seconds + "s.").withStyle(ChatFormatting.YELLOW));
         return 0;
      }

      String resolvedKey = TensuraAiConfig.getResolvedApiKey();
      if (resolvedKey != null && !resolvedKey.isBlank() && !resolvedKey.contains("PUT_YOUR")) {
         String localAnswer = TensuraAiKnowledge.getLocalAnswer(cleanQuestion);
         if (localAnswer != null && !shouldPreferWiki(cleanQuestion)) {
            COOLDOWNS.put(player.getUUID(), now + 20000L);
            player.sendSystemMessage(Component.literal(localAnswer).withStyle(ChatFormatting.AQUA));
            return 1;
         } else if (ACTIVE_REQUESTS.get() >= 3) {
            player.sendSystemMessage(Component.literal("Raphael: Too many thought circuits are active. Try again soon.").withStyle(ChatFormatting.YELLOW));
            return 0;
         } else {
            COOLDOWNS.put(player.getUUID(), now + 20000L);
            ACTIVE_REQUESTS.incrementAndGet();
            player.sendSystemMessage(Component.literal("Raphael: Searching Tensura records...").withStyle(ChatFormatting.DARK_AQUA));
            CompletableFuture.<String>supplyAsync(() -> {
               try {
                  System.out.println("[RaphaelAI] Question: " + cleanQuestion);
                  String wikiContext = TensuraWikiKnowledge.buildContext(cleanQuestion);
                  if (wikiContext != null && !wikiContext.isBlank()) {
                     System.out.println("[RaphaelAI] Calling external thought circuit. Context length: " + wikiContext.length());
                     String answer = TensuraAiClient.askGroq(cleanQuestion, wikiContext);
                     System.out.println("[RaphaelAI] External answer: " + preview(answer, 500));
                     if (answer != null && !answer.isBlank()) {
                        return answer;
                     } else {
                        return localAnswer != null ? localAnswer : "Raphael: No valid answer was returned.";
                     }
                  } else {
                     System.out.println("[RaphaelAI] No wiki context found for question: " + cleanQuestion);
                     return localAnswer != null ? localAnswer : "Raphael: I do not have enough Tensura records to answer that.";
                  }
               } catch (Throwable throwable) {
                  System.out.println("[RaphaelAI] External thought circuit throwable for question: " + cleanQuestion);
                  throwable.printStackTrace();
                  if (localAnswer != null) {
                     return localAnswer;
                  }

                  String message = throwable.getMessage();
                  if (message == null || message.isBlank()) {
                     message = "no message";
                  }

                  return "Raphael: External thought circuit failed. " + throwable.getClass().getSimpleName() + ": " + message;
               }
            }).whenComplete((answer, throwable) -> {
               ACTIVE_REQUESTS.decrementAndGet();
               if (player.getServer() != null) {
                  player.getServer().execute(() -> {
                     if (!player.hasDisconnected()) {
                        String finalAnswer = answer;
                        if (throwable != null) {
                           Throwable real = unwrapThrowable(throwable);
                           System.out.println("[RaphaelAI] Async throwable while answering: " + cleanQuestion);
                           real.printStackTrace();
                           finalAnswer = "Raphael: External thought circuit failed. " + real.getClass().getSimpleName();
                        }

                        if (finalAnswer == null || finalAnswer.isBlank()) {
                           finalAnswer = "Raphael: External thought circuit failed. Empty answer.";
                        }

                        if (finalAnswer.length() > 700) {
                           finalAnswer = finalAnswer.substring(0, 700).trim();
                        }

                        player.sendSystemMessage(Component.literal(finalAnswer).withStyle(ChatFormatting.AQUA));
                     }
                  });
               }
            });
            return 1;
         }
      } else {
         player.sendSystemMessage(Component.literal("Raphael: API key is not configured.").withStyle(ChatFormatting.RED));
         return 0;
      }
   }

   private static Throwable unwrapThrowable(Throwable throwable) {
      Throwable real = throwable;

      while ((real instanceof CompletionException || real instanceof ExecutionException) && real.getCause() != null) {
         real = real.getCause();
      }

      return real;
   }

   private static boolean shouldPreferWiki(String question) {
      String q = question == null ? "" : question.toLowerCase();
      return q.contains("spatial motion")
         || q.contains("belphegor")
         || q.contains("mammon")
         || q.contains("lord of sloth")
         || q.contains("lord of greed")
         || q.contains("exact")
         || q.contains("cost")
         || q.contains("cooldown")
         || q.contains("passive")
         || q.contains("active")
         || q.contains("how much")
         || q.contains("what does")
         || q.contains("where")
         || q.contains("obtain")
         || q.contains("requirement");
   }

   private static boolean hasRequiredSkill(ServerPlayer player) {
      try {
         ResourceLocation location = ResourceLocation.parse("tensuramoreskills:raphael");
         return SkillAPI.getSkillsFrom(player).getSkill(location).isPresent();
      } catch (Throwable throwable) {
         System.out.println("[RaphaelAI] Skill check failed for " + player.getGameProfile().getName());
         throwable.printStackTrace();
         return false;
      }
   }

   private static String preview(String text, int maxLength) {
      if (text == null) {
         return "null";
      }

      String clean = text.replace("\n", "\\n").replace("\r", "\\r");
      return clean.length() <= maxLength ? clean : clean.substring(0, Math.max(0, maxLength)) + "...";
   }
}
