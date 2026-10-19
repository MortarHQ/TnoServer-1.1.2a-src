package net.p3pp3rf1y.sophisticatedbackpacks.command;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackTemplates;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RandHelper;

public class DynamicCommand {
   static final Cache<String, DynamicCommand.DynamicTemplate> DYNAMIC_CACHE = CacheBuilder.newBuilder().expireAfterAccess(5L, TimeUnit.MINUTES).build();

   static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext commandBuildContext) {
      return ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("dynamic")
                  .then(
                     Commands.literal("begin")
                        .then(
                           ((RequiredArgumentBuilder)Commands.argument("templateName", StringArgumentType.word())
                                 .then(
                                    Commands.argument("backpackItem", BackpackItemArgumentType.item(commandBuildContext))
                                       .executes(
                                          context -> beginNewDynamic(
                                             (CommandSourceStack)context.getSource(),
                                             (String)context.getArgument("templateName", String.class),
                                             BackpackItemArgumentType.getItem(context, "backpackItem")
                                          )
                                       )
                                 ))
                              .then(
                                 Commands.argument("baseTemplateName", BackpackTemplateArgumentType.templateName())
                                    .executes(
                                       context -> beginBasedDynamic(
                                          (CommandSourceStack)context.getSource(),
                                          (String)context.getArgument("templateName", String.class),
                                          BackpackTemplateArgumentType.getId(context, "baseTemplateName")
                                       )
                                    )
                              )
                        )
                  ))
               .then(
                  Commands.literal("addItem")
                     .then(
                        ((RequiredArgumentBuilder)Commands.argument("templateName", StringArgumentType.word())
                              .then(
                                 Commands.argument("slot", IntegerArgumentType.integer())
                                    .then(
                                       ((RequiredArgumentBuilder)Commands.argument("item", ItemArgument.item(commandBuildContext))
                                             .executes(
                                                context -> addDynamic(
                                                   (CommandSourceStack)context.getSource(),
                                                   (String)context.getArgument("templateName", String.class),
                                                   IntegerArgumentType.getInteger(context, "slot"),
                                                   ItemArgument.getItem(context, "item"),
                                                   1,
                                                   false
                                                )
                                             ))
                                          .then(
                                             Commands.argument("count", IntegerArgumentType.integer(1))
                                                .executes(
                                                   context -> addDynamic(
                                                      (CommandSourceStack)context.getSource(),
                                                      (String)context.getArgument("templateName", String.class),
                                                      IntegerArgumentType.getInteger(context, "slot"),
                                                      ItemArgument.getItem(context, "item"),
                                                      IntegerArgumentType.getInteger(context, "count"),
                                                      false
                                                   )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              ((RequiredArgumentBuilder)Commands.argument("item", ItemArgument.item(commandBuildContext))
                                    .executes(
                                       context -> addDynamic(
                                          (CommandSourceStack)context.getSource(),
                                          (String)context.getArgument("templateName", String.class),
                                          -1,
                                          ItemArgument.getItem(context, "item"),
                                          1,
                                          false
                                       )
                                    ))
                                 .then(
                                    Commands.argument("count", IntegerArgumentType.integer(1))
                                       .executes(
                                          context -> addDynamic(
                                             (CommandSourceStack)context.getSource(),
                                             (String)context.getArgument("templateName", String.class),
                                             -1,
                                             ItemArgument.getItem(context, "item"),
                                             IntegerArgumentType.getInteger(context, "count"),
                                             false
                                          )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               Commands.literal("addUpgrade")
                  .then(
                     Commands.argument("templateName", StringArgumentType.word())
                        .then(
                           Commands.argument("item", BackpackUpgradeItemArgumentType.item(commandBuildContext))
                              .executes(
                                 context -> addDynamic(
                                    (CommandSourceStack)context.getSource(),
                                    (String)context.getArgument("templateName", String.class),
                                    -1,
                                    ItemArgument.getItem(context, "item"),
                                    1,
                                    true
                                 )
                              )
                        )
                  )
            ))
         .then(
            Commands.literal("end")
               .then(
                  Commands.argument("templateName", StringArgumentType.word())
                     .executes(context -> endDynamic((CommandSourceStack)context.getSource(), (String)context.getArgument("templateName", String.class)))
               )
         );
   }

   private static int beginNewDynamic(CommandSourceStack source, String templateName, ItemInput backpackItem) throws CommandSyntaxException {
      if (DYNAMIC_CACHE.asMap().containsKey(templateName)) {
         source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.dynamic.templateInUse", new Object[]{templateName}));
         return 1;
      } else {
         ItemStack backpack = backpackItem.createItemStack(1, false);
         DYNAMIC_CACHE.put(templateName, new DynamicCommand.DynamicTemplate(backpack, new ArrayList<>(), new ArrayList<>()));
         source.sendSuccess(() -> Component.translatable("commands.sophisticatedbackpacks.dynamic.begin.success", new Object[]{templateName}), false);
         return 0;
      }
   }

   private static int beginBasedDynamic(CommandSourceStack source, String templateName, ResourceLocation baseTemplateName) {
      if (DYNAMIC_CACHE.getIfPresent(templateName) != null) {
         source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.dynamic.templateInUse", new Object[]{templateName}));
         return 1;
      } else {
         Optional<CompoundTag> templateData = BackpackTemplates.getBackpackTemplate(baseTemplateName);
         if (templateData.isEmpty()) {
            source.sendFailure(
               Component.translatable("commands.sophisticatedbackpacks.dynamic.begin.noBaseTemplate", new Object[]{baseTemplateName.toString()})
            );
            return 1;
         } else {
            ItemStack backpack = new ItemStack(
               (ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.parse(templateData.get().getString("backpackItemRegistryName")))
            );
            IBackpackWrapper wrapper = BackpackWrapper.fromStack(backpack);
            wrapper.setTemplate(baseTemplateName);
            wrapper.fillFromTemplate();
            DYNAMIC_CACHE.put(templateName, new DynamicCommand.DynamicTemplate(backpack, new ArrayList<>(), new ArrayList<>()));
            source.sendSuccess(() -> Component.translatable("commands.sophisticatedbackpacks.dynamic.begin.success", new Object[]{templateName}), false);
            return 0;
         }
      }
   }

   private static int addDynamic(CommandSourceStack source, String templateName, int slot, ItemInput item, int count, boolean upgrade) throws CommandSyntaxException {
      DynamicCommand.DynamicTemplate template = (DynamicCommand.DynamicTemplate)DYNAMIC_CACHE.getIfPresent(templateName);
      if (template == null) {
         source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.dynamic.templateNotInUse", new Object[]{templateName}));
         return 1;
      }

      ItemStack stack = item.createItemStack(count, true);
      if (slot == -1) {
         if (upgrade) {
            template.itemsForUpgradeHandler.add(stack);
         } else {
            template.itemsForInventoryHandler.add(stack);
         }
      } else {
         IBackpackWrapper wrapper = BackpackWrapper.fromStack(template.backpack);
         ItemStackHandler inventory = (ItemStackHandler)(upgrade ? wrapper.getUpgradeHandler() : wrapper.getInventoryHandler());
         if (inventory.getStackInSlot(slot).isEmpty()) {
            inventory.setStackInSlot(slot, stack);
            source.sendSuccess(
               () -> Component.translatable("commands.sophisticatedbackpacks.dynamic.add.success", new Object[]{templateName, stack.getDisplayName()}), false
            );
            return 0;
         }

         template.itemsForInventoryHandler.add(stack);
      }

      source.sendSuccess(
         () -> Component.translatable("commands.sophisticatedbackpacks.dynamic.add.delayed", new Object[]{templateName, stack.getDisplayName()}), false
      );
      return 0;
   }

   private static int endDynamic(CommandSourceStack source, String templateName) {
      DynamicCommand.DynamicTemplate template = (DynamicCommand.DynamicTemplate)DYNAMIC_CACHE.getIfPresent(templateName);
      if (template == null) {
         source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.dynamic.templateNotInUse", new Object[]{templateName}));
         return 1;
      }

      IBackpackWrapper wrapper = BackpackWrapper.fromStack(template.backpack);
      List<ItemStack> remainings = new ArrayList<>();
      remainings.addAll(InventoryHelper.insertIntoInventory(template.itemsForInventoryHandler, wrapper.getInventoryHandler(), false));
      remainings.addAll(InventoryHelper.insertIntoInventory(template.itemsForUpgradeHandler, wrapper.getUpgradeHandler(), false));
      if (!remainings.isEmpty()) {
         for (ItemStack remaining : remainings) {
            source.sendFailure(
               Component.translatable(
                  "commands.sophisticatedbackpacks.dynamic.end.addRemainingItemsFailed", new Object[]{remaining.getCount(), remaining.getDisplayName()}
               )
            );
         }
      }

      Optional<UUID> backpackUuid = wrapper.getContentsUuid();
      if (!backpackUuid.isEmpty() && (!InventoryHelper.isEmpty(wrapper.getInventoryHandler()) || !InventoryHelper.isEmpty(wrapper.getUpgradeHandler()))) {
         if (source.isPlayer()) {
            giveBackpackToPlayer(template.backpack(), source.getPlayer());
         } else if (source.getEntity() != null) {
            dropBackpackToPosition(template.backpack(), source.getLevel(), source.getEntity().position());
         } else {
            dropBackpackToPosition(template.backpack(), source.getLevel(), source.getPosition());
         }

         DYNAMIC_CACHE.invalidate(templateName);
         source.sendSuccess(() -> Component.translatable("commands.sophisticatedbackpacks.dynamic.end.success", new Object[]{templateName}), false);
         return 0;
      } else {
         source.sendFailure(Component.translatable("commands.sophisticatedbackpacks.template.backpackempty"));
         return 3;
      }
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

   private static void dropBackpackToPosition(ItemStack backpack, ServerLevel level, Vec3 pos) {
      ItemEntity itemEntity = new ItemEntity(level, pos.x, pos.y, pos.z, backpack, 0.0, 0.0, 0.0);
      level.addFreshEntity(itemEntity);
   }

   private record DynamicTemplate(ItemStack backpack, List<ItemStack> itemsForInventoryHandler, List<ItemStack> itemsForUpgradeHandler) {
   }
}
