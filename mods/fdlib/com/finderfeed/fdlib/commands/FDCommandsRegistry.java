package com.finderfeed.fdlib.commands;

import com.finderfeed.fdlib.FDLibCalls;
import com.finderfeed.fdlib.init.FDModEvents;
import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.bedrock.TriggerAnimationReloadPacket;
import com.finderfeed.fdlib.systems.bedrock.TriggerModelReloadPacket;
import com.finderfeed.fdlib.systems.bedrock.animations.Animation;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationTicker;
import com.finderfeed.fdlib.systems.config.JsonConfig;
import com.finderfeed.fdlib.systems.config.packets.JsonConfigSyncPacket;
import com.finderfeed.fdlib.systems.config.packets.TriggerClientsideConfigReloadPacket;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "fdlib", bus = Bus.GAME)
public class FDCommandsRegistry {
   @SubscribeEvent
   public static void registerCommands(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("fdlib")
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("animation_test").requires(stack -> stack.hasPermission(2)))
                           .then(
                              Commands.argument("animation", new AnimationArgument())
                                 .then(
                                    ((RequiredArgumentBuilder)Commands.argument("layer", StringArgumentType.string())
                                          .then(
                                             Commands.literal("entity")
                                                .then(
                                                   Commands.argument("target", EntityArgument.entity())
                                                      .executes(
                                                         context -> {
                                                            executeEntityAnimation(
                                                               context,
                                                               (String)context.getArgument("animation", String.class),
                                                               (String)context.getArgument("layer", String.class),
                                                               EntityArgument.getEntity(context, "target")
                                                            );
                                                            return 1;
                                                         }
                                                      )
                                                )
                                          ))
                                       .then(
                                          Commands.literal("tile")
                                             .then(
                                                Commands.argument("target", BlockPosArgument.blockPos())
                                                   .executes(
                                                      context -> {
                                                         executeTileEntityAnimation(
                                                            context,
                                                            (String)context.getArgument("animation", String.class),
                                                            (String)context.getArgument("layer", String.class),
                                                            BlockPosArgument.getBlockPos(context, "target")
                                                         );
                                                         return 1;
                                                      }
                                                   )
                                             )
                                       )
                                 )
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("reload")
                                 .then(Commands.literal("clientsideConfigs").executes(ctx -> {
                                    reloadClientConfigs(ctx);
                                    return 1;
                                 })))
                              .then(((LiteralArgumentBuilder)Commands.literal("configs").requires(stack -> stack.hasPermission(2))).executes(ctx -> {
                                 reloadConfigs(ctx);
                                 return 1;
                              })))
                           .then(((LiteralArgumentBuilder)Commands.literal("models").requires(stack -> stack.hasPermission(2))).executes(ctx -> {
                              reloadModels(ctx);
                              return 1;
                           })))
                        .then(((LiteralArgumentBuilder)Commands.literal("animations").requires(stack -> stack.hasPermission(2))).executes(ctx -> {
                           reloadAnimations(ctx);
                           return 1;
                        }))
                  ))
               .then(Commands.literal("fix").then(Commands.literal("cutscene").executes(stack -> {
                  emergencyCutsceneEnd(stack);
                  return 1;
               })))
         );
   }

   public static void emergencyCutsceneEnd(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      CommandSourceStack stack = (CommandSourceStack)ctx.getSource();
      ServerPlayer player = stack.getPlayerOrException();
      FDLibCalls.stopCutsceneForPlayer(player);
   }

   public static void reloadAnimations(CommandContext<CommandSourceStack> ctx) {
      CommandSourceStack stack = (CommandSourceStack)ctx.getSource();
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         stack.sendSystemMessage(Component.literal("Reloading animations..."));
         FDModEvents.loadAnimations();
         PacketDistributor.sendToAllPlayers(new TriggerAnimationReloadPacket(), new CustomPacketPayload[0]);
         stack.sendSystemMessage(Component.literal("Reload complete!"));
      } else {
         stack.sendFailure(Component.literal("Can only be used in dev environment."));
      }
   }

   public static void reloadModels(CommandContext<CommandSourceStack> ctx) {
      CommandSourceStack stack = (CommandSourceStack)ctx.getSource();
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         stack.sendSystemMessage(Component.literal("Reloading models..."));
         FDModEvents.loadModels();
         PacketDistributor.sendToAllPlayers(new TriggerModelReloadPacket(), new CustomPacketPayload[0]);
         stack.sendSystemMessage(Component.literal("Reload complete!"));
      } else {
         stack.sendFailure(Component.literal("Can only be used in dev environment."));
      }
   }

   public static void reloadConfigs(CommandContext<CommandSourceStack> ctx) {
      CommandSourceStack stack = (CommandSourceStack)ctx.getSource();

      for (JsonConfig config : FDRegistries.CONFIGS) {
         if (!config.isClientside()) {
            stack.sendSystemMessage(Component.literal("Loading config: " + config.getName()));
            config.loadFromDisk();
            stack.sendSystemMessage(Component.literal("Loaded config: " + config.getName()).withStyle(ChatFormatting.GREEN));
         }
      }

      PacketDistributor.sendToAllPlayers(new JsonConfigSyncPacket(), new CustomPacketPayload[0]);
   }

   public static void reloadClientConfigs(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      PacketDistributor.sendToPlayer(
         ((CommandSourceStack)ctx.getSource()).getPlayerOrException(), new TriggerClientsideConfigReloadPacket(true), new CustomPacketPayload[0]
      );
   }

   public static void executeEntityAnimation(CommandContext<CommandSourceStack> ctx, String animname, String tickerName, Entity entity) throws CommandSyntaxException {
      CommandSourceStack sourceStack = (CommandSourceStack)ctx.getSource();
      Animation animation = (Animation)FDRegistries.ANIMATIONS.get(ResourceLocation.parse(animname));
      if (animation == null) {
         sourceStack.sendFailure(Component.literal("No such animation found: " + animname));
      } else if (entity instanceof AnimatedObject object) {
         sourceStack.sendSystemMessage(Component.literal("Playing animation"));
         object.getAnimationSystem()
            .startAnimation(tickerName, new AnimationTicker.Builder(animation).setLoopMode(Animation.LoopMode.ONCE).setToNullTransitionTime(0).build());
      } else {
         sourceStack.sendFailure(Component.literal("Entity is not an animatable object"));
      }
   }

   public static void executeTileEntityAnimation(CommandContext<CommandSourceStack> ctx, String animname, String tickerName, BlockPos pos) throws CommandSyntaxException {
      CommandSourceStack sourceStack = (CommandSourceStack)ctx.getSource();
      ServerLevel level = ((CommandSourceStack)ctx.getSource()).getLevel();
      Animation animation = (Animation)FDRegistries.ANIMATIONS.get(ResourceLocation.parse(animname));
      if (animation == null) {
         sourceStack.sendFailure(Component.literal("No such animation found: " + animname));
      } else if (level.getBlockEntity(pos) instanceof AnimatedObject object) {
         sourceStack.sendSystemMessage(Component.literal("Playing animation"));
         object.getAnimationSystem()
            .startAnimation(tickerName, new AnimationTicker.Builder(animation).setLoopMode(Animation.LoopMode.ONCE).setToNullTransitionTime(0).build());
      } else {
         sourceStack.sendFailure(Component.literal("Tile entity is not an animatable object"));
      }
   }
}
