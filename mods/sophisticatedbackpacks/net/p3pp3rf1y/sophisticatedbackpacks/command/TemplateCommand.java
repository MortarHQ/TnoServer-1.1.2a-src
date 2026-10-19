package net.p3pp3rf1y.sophisticatedbackpacks.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackTemplates;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.DatapackBackpackTemplateManager;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RandHelper;

public class TemplateCommand {
   private TemplateCommand() {
   }

   static ArgumentBuilder<CommandSourceStack, ?> register(RegisterCommandsEvent event) {
      return ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                           "template"
                        )
                        .then(DynamicCommand.register(event.getBuildContext())))
                     .then(Commands.literal("list").executes(context -> templateDetailComponent((CommandSourceStack)context.getSource()))))
                  .then(
                     Commands.literal("create")
                        .then(
                           ((RequiredArgumentBuilder)Commands.argument("templateName", BackpackTemplateArgumentType.templateName())
                                 .executes(
                                    context -> createTemplate(
                                       (CommandSourceStack)context.getSource(), BackpackTemplateArgumentType.getId(context, "templateName"), false
                                    )
                                 ))
                              .then(
                                 Commands.argument("override", BoolArgumentType.bool())
                                    .executes(
                                       context -> createTemplate(
                                          (CommandSourceStack)context.getSource(),
                                          BackpackTemplateArgumentType.getId(context, "templateName"),
                                          BoolArgumentType.getBool(context, "override")
                                       )
                                    )
                              )
                        )
                  ))
               .then(
                  Commands.literal("delete")
                     .then(
                        Commands.argument("templateName", BackpackTemplateArgumentType.templateName(false))
                           .executes(
                              context -> deleteTemplate((CommandSourceStack)context.getSource(), BackpackTemplateArgumentType.getId(context, "templateName"))
                           )
                     )
               ))
            .then(
               Commands.literal("give")
                  .then(
                     ((RequiredArgumentBuilder)Commands.argument("templateName", BackpackTemplateArgumentType.templateName())
                           .executes(
                              context -> giveBackpackFromTemplate(
                                 (CommandSourceStack)context.getSource(),
                                 BackpackTemplateArgumentType.getId(context, "templateName"),
                                 List.of(((CommandSourceStack)context.getSource()).getPlayer())
                              )
                           ))
                        .then(
                           Commands.argument("targets", EntityArgument.players())
                              .executes(
                                 context -> giveBackpackFromTemplate(
                                    (CommandSourceStack)context.getSource(),
                                    BackpackTemplateArgumentType.getId(context, "templateName"),
                                    EntityArgument.getPlayers(context, "targets")
                                 )
                              )
                        )
                  )
            ))
         .then(
            Commands.literal("export")
               .then(
                  ((RequiredArgumentBuilder)Commands.argument("templateName", BackpackTemplateArgumentType.templateName())
                        .executes(
                           context -> exportTemplate(
                              (CommandSourceStack)context.getSource(), BackpackTemplateArgumentType.getId(context, "templateName"), false
                           )
                        ))
                     .then(
                        Commands.argument("delete", BoolArgumentType.bool())
                           .executes(
                              context -> exportTemplate(
                                 (CommandSourceStack)context.getSource(), BackpackTemplateArgumentType.getId(context, "templateName"), true
                              )
                           )
                     )
               )
         );
   }

   private static int templateDetailComponent(CommandSourceStack source) {
      source.sendSuccess(() -> Component.translatable("commands.sophisticatedbackpacks.template.list.header"), false);
      source.sendSuccess(() -> Component.literal("Datapack"), false);
      DatapackBackpackTemplateManager.getBackpackTemplates()
         .keySet()
         .forEach(templateName -> source.sendSuccess(() -> templateDetailComponent(templateName, false), false));
      source.sendSuccess(() -> Component.literal("Local"), false);
      BackpackTemplates.getTemplateNames(false).forEach(templateName -> source.sendSuccess(() -> templateDetailComponent(templateName, true), false));
      return 0;
   }

   private static MutableComponent templateDetailComponent(ResourceLocation templateName, boolean includeNonDatapackMessages) {
      MutableComponent message = Component.literal(templateName.toString());
      message.append(Component.literal(", "));
      message.append(
         Component.translatable("commands.sophisticatedbackpacks.template.list.give")
            .withStyle(
               s -> s.withColor(ChatFormatting.GREEN)
                  .withClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/sophisticatedbackpacks template give " + templateName))
                  .withHoverEvent(
                     new HoverEvent(
                        net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT,
                        Component.translatable("commands.sophisticatedbackpacks.template.list.give.tooltip", new Object[]{templateName.toString()})
                     )
                  )
            )
      );
      if (includeNonDatapackMessages) {
         message.append(Component.literal(", "));
         message.append(
            Component.translatable("commands.sophisticatedbackpacks.template.list.export")
               .withStyle(
                  s -> s.withColor(ChatFormatting.AQUA)
                     .withClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/sophisticatedbackpacks template export " + templateName))
                     .withHoverEvent(
                        new HoverEvent(
                           net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT,
                           Component.translatable("commands.sophisticatedbackpacks.template.list.export.tooltip", new Object[]{templateName.toString()})
                        )
                     )
               )
         );
         message.append(Component.literal(", "));
         message.append(
            Component.translatable("commands.sophisticatedbackpacks.template.list.delete")
               .withStyle(
                  s -> s.withColor(ChatFormatting.RED)
                     .withClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/sophisticatedbackpacks template delete " + templateName))
                     .withHoverEvent(
                        new HoverEvent(
                           net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT,
                           Component.translatable("commands.sophisticatedbackpacks.template.list.delete.tooltip", new Object[]{templateName.toString()})
                        )
                     )
               )
         );
      }

      return message;
   }

   private static int createTemplate(CommandSourceStack source, ResourceLocation templateName, boolean override) {
      if (!source.isPlayer()) {
         return 1;
      }

      ServerPlayer player = source.getPlayer();
      ItemStack backpack = player.getMainHandItem();
      if (!(backpack.getItem() instanceof BackpackItem)) {
         source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.template.create.nobackpack"));
         return 2;
      }

      IBackpackWrapper backpackWrapper = BackpackWrapper.fromStack(backpack);
      Optional<UUID> backpackUuid = backpackWrapper.getContentsUuid();
      if (!backpackUuid.isEmpty()
         && (!InventoryHelper.isEmpty(backpackWrapper.getInventoryHandler()) || !InventoryHelper.isEmpty(backpackWrapper.getUpgradeHandler()))) {
         if (BackpackTemplates.getTemplateNames().contains(templateName) && !override) {
            source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.template.create.alreadyexists", new Object[]{templateName.toString()}));
            return 4;
         } else {
            BackpackTemplates.setBackpackTemplate(templateName, backpackWrapper);
            source.sendSuccess(
               () -> Component.translatable("commands.sophisticatedbackpacks.template.create.success", new Object[]{templateName.toString()}), true
            );
            return 0;
         }
      } else {
         source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.template.backpackempty"));
         return 3;
      }
   }

   private static int deleteTemplate(CommandSourceStack source, ResourceLocation templateName) {
      BackpackTemplates.removeBackpackTemplate(templateName);
      source.sendSuccess(() -> Component.translatable("commands.sophisticatedbackpacks.template.delete.success", new Object[]{templateName.toString()}), true);
      return 0;
   }

   private static int giveBackpackFromTemplate(CommandSourceStack source, ResourceLocation templateName, Collection<ServerPlayer> players) {
      Optional<CompoundTag> templateData = BackpackTemplates.getBackpackTemplate(templateName);
      if (templateData.isEmpty()) {
         source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.template.give.failure.notemplate", new Object[]{templateName.toString()}));
         return 1;
      }

      ItemStack backpack = new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.parse(templateData.get().getString("backpackItemRegistryName"))));
      IBackpackWrapper wrapper = BackpackWrapper.fromStack(backpack);
      wrapper.setTemplate(templateName);
      players.forEach(p -> giveBackpackToPlayer(backpack.copy(), p));
      if (players.size() == 1) {
         source.sendSuccess(
            () -> Component.translatable(
               "commands.sophisticatedbackpacks.template.give.backpack.success", new Object[]{players.iterator().next().getDisplayName()}
            ),
            true
         );
      } else {
         source.sendSuccess(() -> Component.translatable("commands.sophisticatedbackpacks.template.give.backpack.success", new Object[]{players.size()}), true);
      }

      return 0;
   }

   private static void giveBackpackToPlayer(ItemStack backpack, ServerPlayer p) {
      boolean flag = p.getInventory().add(backpack);
      if (flag && backpack.isEmpty()) {
         backpack.setCount(1);
         ItemEntity itemEntity = p.drop(backpack, false);
         if (itemEntity != null) {
            itemEntity.makeFakeItem();
         }

         p.level()
            .playSound(
               null,
               p.getX(),
               p.getY(),
               p.getZ(),
               SoundEvents.ITEM_PICKUP,
               SoundSource.PLAYERS,
               0.2F,
               (RandHelper.getRandomMinusOneToOne(p.getRandom()) * 0.7F + 1.0F) * 2.0F
            );
         p.inventoryMenu.broadcastChanges();
      } else {
         ItemEntity itementity = p.drop(backpack, false);
         if (itementity != null) {
            itementity.setNoPickUpDelay();
            itementity.setThrower(p);
         }
      }

      ItemEntity itemEntity = p.drop(backpack, false);
      if (itemEntity != null) {
         itemEntity.makeFakeItem();
      }
   }

   private static int exportTemplate(CommandSourceStack source, ResourceLocation templateName, boolean deleteTemplate) {
      Optional<CompoundTag> templateData = BackpackTemplates.getBackpackTemplateNoDatapack(templateName);
      if (templateData.isEmpty()) {
         source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.template.export.failure.notemplate", new Object[]{templateName.toString()}));
         return 1;
      }

      BackpackTemplates.exportTemplate(source.getPlayer(), templateName, templateData.get());
      if (deleteTemplate) {
         BackpackTemplates.removeBackpackTemplate(templateName);
      }

      return 0;
   }
}
