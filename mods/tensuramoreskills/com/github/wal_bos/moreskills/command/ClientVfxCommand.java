package com.github.wal_bos.moreskills.command;

import com.github.wal_bos.moreskills.client.fx.ClientVfxBootstrap;
import com.github.wal_bos.moreskills.client.fx.ClientVfxIds;
import com.github.wal_bos.moreskills.client.fx.ClientVfxLocalOverrides;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@EventBusSubscriber(modid = "tensuramoreskills", value = Dist.CLIENT)
public final class ClientVfxCommand {
   private ClientVfxCommand() {
   }

   @SubscribeEvent
   public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
      registerCommands(event.getDispatcher());
   }

   private static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
      if (dispatcher.getRoot().getChild("msvfx") == null) {
         LiteralCommandNode<CommandSourceStack> root = dispatcher.register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("msvfx")
                        .then(Commands.literal("list").executes(ctx -> list())))
                     .then(Commands.literal("status").executes(ctx -> status())))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("all")
                           .then(Commands.argument("enabled", BoolArgumentType.bool()).executes(ctx -> setAll(BoolArgumentType.getBool(ctx, "enabled")))))
                        .then(Commands.literal("default").executes(ctx -> setAllDefault()))
                  ))
               .then(
                  Commands.literal("effect")
                     .then(
                        ((RequiredArgumentBuilder)Commands.argument("id", StringArgumentType.word())
                              .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(ClientVfxIds.ALL, builder))
                              .then(
                                 Commands.argument("enabled", BoolArgumentType.bool())
                                    .executes(ctx -> setEffect(StringArgumentType.getString(ctx, "id"), BoolArgumentType.getBool(ctx, "enabled")))
                              ))
                           .then(Commands.literal("default").executes(ctx -> setEffectDefault(StringArgumentType.getString(ctx, "id"))))
                     )
               )
         );
         dispatcher.register((LiteralArgumentBuilder)Commands.literal("moreskillsvfx").redirect(root));
      }
   }

   private static int list() {
      send(Component.literal(String.join(", ", ClientVfxIds.ALL)).withStyle(ChatFormatting.GRAY));
      return ClientVfxIds.ALL.size();
   }

   private static int status() {
      Boolean all = ClientVfxLocalOverrides.getAll();
      Map<String, Boolean> effects = ClientVfxLocalOverrides.copyEffects();
      send(Component.literal("Local all override: " + valueName(all)).withStyle(ChatFormatting.AQUA));
      if (effects.isEmpty()) {
         send(Component.literal("No local per-effect overrides.").withStyle(ChatFormatting.GRAY));
      } else {
         for (Entry<String, Boolean> entry : effects.entrySet()) {
            send(Component.literal(entry.getKey() + ": " + valueName(entry.getValue())).withStyle(entry.getValue() ? ChatFormatting.GREEN : ChatFormatting.RED));
         }
      }

      return 1;
   }

   private static int setAll(boolean value) {
      ClientVfxLocalOverrides.setAll(value);
      ClientVfxBootstrap.refreshAllAllowed();
      send(Component.literal("Local VFX allEffects set to " + valueName(value)).withStyle(value ? ChatFormatting.GREEN : ChatFormatting.RED));
      return 1;
   }

   private static int setAllDefault() {
      ClientVfxLocalOverrides.setAll(null);
      ClientVfxBootstrap.refreshAllAllowed();
      send(Component.literal("Local VFX allEffects override cleared.").withStyle(ChatFormatting.YELLOW));
      return 1;
   }

   private static int setEffect(String rawId, boolean value) {
      String id = ClientVfxIds.normalize(rawId);
      if (id == null) {
         send(Component.literal("Unknown VFX id: " + rawId).withStyle(ChatFormatting.RED));
         return 0;
      } else {
         ClientVfxLocalOverrides.setEffect(id, value);
         ClientVfxBootstrap.startAllowedAfterChange(id);
         send(Component.literal("Local VFX " + id + " set to " + valueName(value)).withStyle(value ? ChatFormatting.GREEN : ChatFormatting.RED));
         return 1;
      }
   }

   private static int setEffectDefault(String rawId) {
      String id = ClientVfxIds.normalize(rawId);
      if (id == null) {
         send(Component.literal("Unknown VFX id: " + rawId).withStyle(ChatFormatting.RED));
         return 0;
      } else {
         ClientVfxLocalOverrides.setEffect(id, null);
         ClientVfxBootstrap.startAllowedAfterChange(id);
         send(Component.literal("Local VFX " + id + " override cleared.").withStyle(ChatFormatting.YELLOW));
         return 1;
      }
   }

   private static String valueName(Boolean value) {
      if (value == null) {
         return "DEFAULT";
      } else {
         return value ? "ON" : "OFF";
      }
   }

   private static void send(Component component) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null) {
         minecraft.player.displayClientMessage(component, false);
      }
   }
}
