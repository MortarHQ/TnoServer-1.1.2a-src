package team.lodestar.lodestone.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import team.lodestar.lodestone.attachment.WorldEventAttachment;
import team.lodestar.lodestone.command.arguments.WorldEventInstanceArgument;
import team.lodestar.lodestone.command.arguments.WorldEventTypeArgument;
import team.lodestar.lodestone.registry.common.LodestoneAttachmentTypes;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

public class FreezeActiveWorldEventsCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("freeze")
                  .requires(cs -> cs.hasPermission(2)))
               .then(
                  Commands.literal("all")
                     .executes(
                        ctx -> {
                           CommandSourceStack source = (CommandSourceStack)ctx.getSource();
                           ServerLevel level = source.getLevel();
                           WorldEventAttachment data = (WorldEventAttachment)level.getData(LodestoneAttachmentTypes.WORLD_EVENT_DATA);
                           List<WorldEventInstance> activeWorldEvents = data.activeWorldEvents;
                           List<WorldEventInstance> notCurrentlyFrozen = activeWorldEvents.stream().filter(event -> !event.isFrozen()).toList();
                           if (notCurrentlyFrozen.isEmpty()) {
                              ((CommandSourceStack)ctx.getSource())
                                 .sendFailure(Component.translatable("command.lodestone.worldevent.freeze.all.fail").withStyle(ChatFormatting.RED));
                           } else {
                              notCurrentlyFrozen.forEach(instance -> instance.frozen = true);
                              notCurrentlyFrozen.forEach(WorldEventInstance::setDirty);
                              ((CommandSourceStack)ctx.getSource())
                                 .sendSuccess(
                                    () -> Component.translatable("command.lodestone.worldevent.freeze.all.success", new Object[]{notCurrentlyFrozen.size()})
                                       .withStyle(ChatFormatting.AQUA),
                                    true
                                 );
                           }

                           return 1;
                        }
                     )
               ))
            .then(
               Commands.literal("single")
                  .then(
                     Commands.argument("target", WorldEventInstanceArgument.worldEventInstance())
                        .executes(
                           ctx -> {
                              WorldEventInstance event = WorldEventInstanceArgument.getEventInstance(ctx, "target");
                              if (event.isFrozen()) {
                                 ((CommandSourceStack)ctx.getSource())
                                    .sendFailure(Component.translatable("command.lodestone.worldevent.freeze.target.fail").withStyle(ChatFormatting.RED));
                              } else {
                                 event.frozen = true;
                                 event.setDirty();
                                 ((CommandSourceStack)ctx.getSource())
                                    .sendSuccess(
                                       () -> Component.translatable(
                                             "command.lodestone.worldevent.freeze.target.success", new Object[]{event.type.id.toString()}
                                          )
                                          .withStyle(ChatFormatting.AQUA),
                                       true
                                    );
                              }

                              return 1;
                           }
                        )
                  )
            ))
         .then(
            Commands.literal("type")
               .then(
                  Commands.argument("type", WorldEventTypeArgument.worldEventType())
                     .executes(
                        ctx -> {
                           WorldEventType type = WorldEventTypeArgument.getEventType(ctx, "type");
                           WorldEventAttachment data = (WorldEventAttachment)((CommandSourceStack)ctx.getSource())
                              .getLevel()
                              .getData(LodestoneAttachmentTypes.WORLD_EVENT_DATA);
                           List<WorldEventInstance> activeWorldEvents = data.activeWorldEvents.stream().filter(instance -> instance.type == type).toList();
                           List<WorldEventInstance> notCurrentlyFrozen = activeWorldEvents.stream().filter(event -> !event.isFrozen()).toList();
                           if (notCurrentlyFrozen.isEmpty()) {
                              ((CommandSourceStack)ctx.getSource())
                                 .sendFailure(
                                    Component.translatable("command.lodestone.worldevent.freeze.type.fail", new Object[]{type.id.toString()})
                                       .withStyle(ChatFormatting.RED)
                                 );
                           } else {
                              notCurrentlyFrozen.forEach(instance -> instance.frozen = true);
                              notCurrentlyFrozen.forEach(WorldEventInstance::setDirty);
                              ((CommandSourceStack)ctx.getSource())
                                 .sendSuccess(
                                    () -> Component.translatable(
                                          "command.lodestone.worldevent.freeze.type.success", new Object[]{notCurrentlyFrozen.size(), type.id.toString()}
                                       )
                                       .withStyle(ChatFormatting.AQUA),
                                    true
                                 );
                           }

                           return 1;
                        }
                     )
               )
         );
   }
}
