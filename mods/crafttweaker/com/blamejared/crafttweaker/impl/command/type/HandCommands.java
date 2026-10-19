package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.visitor.DataToTextComponentVisitor;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.block.ExpandBlockState;
import com.blamejared.crafttweaker.natives.entity.attribute.ExpandAttribute;
import com.blamejared.crafttweaker.natives.entity.equipment.ExpandEquipmentSlot;
import com.blamejared.crafttweaker.platform.Services;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.util.TriConsumer;

public final class HandCommands {
   private HandCommands() {
   }

   public static void registerCommands(ICommandRegistrationHandler handler) {
      handler.registerRootCommand("hand", Component.translatable("crafttweaker.command.description.hand"), builder -> builder.executes(context -> {
         CommandSourceStack source = (CommandSourceStack)context.getSource();
         ServerPlayer player = source.getPlayerOrException();
         ItemStack stack = player.getMainHandItem();
         Item item = stack.getItem();
         sendBasicItemInformation(source, stack);
         if (item instanceof BlockItem) {
            sendBlockInformation(source, (BlockItem)stack.getItem());
         }

         if (item instanceof BucketItem && Services.PLATFORM.getBucketContent((BucketItem)item) != Fluids.EMPTY) {
            sendBucketInformation(source, (BucketItem)stack.getItem());
         }

         for (MutableComponent component : Services.PLATFORM.getFluidsForDump(stack, player, InteractionHand.MAIN_HAND)) {
            sendHand(source, Component.translatable("crafttweaker.command.misc.fluid"), component.getString());
         }

         sendTagsInformation(source, item);
         return 1;
      }));
      handler.registerSubCommand(
         "hand", "registry_name", Component.translatable("crafttweaker.command.description.hand.registryname"), builder -> builder.executes(context -> {
            CommandSourceStack source = (CommandSourceStack)context.getSource();
            ItemStack mainHandItem = source.getPlayerOrException().getMainHandItem();
            sendCopyingHand(source, Component.translatable("crafttweaker.command.misc.item"), BuiltInRegistries.ITEM.getKey(mainHandItem.getItem()).toString());
            return 1;
         })
      );
      handler.registerSubCommand(
         "hand",
         "data",
         Component.translatable("crafttweaker.command.description.hand.data"),
         builder -> builder.executes(
            context -> {
               CommandSourceStack source = (CommandSourceStack)context.getSource();
               ServerPlayer player = source.getPlayerOrException();
               ItemStack stack = player.getMainHandItem();
               if (!stack.has(DataComponents.CUSTOM_DATA)) {
                  CommandUtilities.send(source, Component.translatable("crafttweaker.command.hand.no.data"));
                  return 0;
               } else {
                  sendCopyingHand(
                     source,
                     Component.translatable("crafttweaker.command.misc.data"),
                     new MapData(((CustomData)stack.get(DataComponents.CUSTOM_DATA)).getUnsafe()).accept(new DataToTextComponentVisitor(" ", 0)).getString()
                  );
                  return 1;
               }
            }
         )
      );
      handler.registerSubCommand("hand", "tags", Component.translatable("crafttweaker.command.description.hand.tags"), builder -> builder.executes(context -> {
         CommandSourceStack source = (CommandSourceStack)context.getSource();
         ServerPlayer player = source.getPlayerOrException();
         ItemStack stack = player.getMainHandItem();
         Collection<String> tags = sendTagsInformation(source, stack.getItem());
         if (tags.isEmpty()) {
            CommandUtilities.send(source, Component.translatable("crafttweaker.command.hand.no.tags"));
            return 1;
         } else {
            tags.stream().findFirst().ifPresent(it -> CommandUtilities.copy(source, it));
            return 1;
         }
      }));
      handler.registerSubCommand(
         "hand", "vanilla", Component.translatable("crafttweaker.command.description.hand.vanilla"), builder -> builder.executes(context -> {
            CommandSourceStack source = (CommandSourceStack)context.getSource();
            ServerPlayer player = source.getPlayerOrException();
            ItemStack stack = player.getMainHandItem();
            Item item = stack.getItem();
            sendBasicVanillaItemInformation(source, stack);
            if (stack.has(DataComponents.CUSTOM_DATA)) {
               sendVanillaDataInformation(source, ((CustomData)stack.get(DataComponents.CUSTOM_DATA)).getUnsafe());
            }

            if (item instanceof BucketItem && Services.PLATFORM.getBucketContent((BucketItem)item) != Fluids.EMPTY) {
               sendVanillaBucketInformation(source, (BucketItem)stack.getItem());
            }

            sendVanillaTagsInformation(source, item);
            return 1;
         })
      );
      handler.registerSubCommand(
         "hand",
         "attributes",
         Component.translatable("crafttweaker.command.description.hand.attributes"),
         builder -> builder.executes(
            context -> {
               CommandSourceStack source = (CommandSourceStack)context.getSource();
               ServerPlayer player = source.getPlayerOrException();
               ItemStack stack = player.getMainHandItem();

               for (EquipmentSlot slot : EquipmentSlot.values()) {
                  String commandString = ExpandEquipmentSlot.getCommandString(slot);
                  Map<Attribute, List<AttributeModifier>> modifiers = new HashMap<>();
                  stack.forEachModifier(
                     slot,
                     (attributeHolder, attributeModifier) -> modifiers.computeIfAbsent((Attribute)attributeHolder.value(), attribute -> new ArrayList<>())
                        .add(attributeModifier)
                  );
                  if (!modifiers.isEmpty()) {
                     CommandUtilities.sendCopying(
                        source,
                        Component.translatable("crafttweaker.command.hand.header.attributes")
                           .append(": ")
                           .append(Component.literal(commandString).withStyle(ChatFormatting.GREEN))
                           .withStyle(ChatFormatting.DARK_AQUA),
                        commandString
                     );
                  }

                  modifiers.forEach(
                     (attribute, attributeModifiers) -> {
                        String attributeCS = ExpandAttribute.getCommandString(attribute);
                        CommandUtilities.sendCopying(
                           source,
                           Component.literal("- ").withStyle(ChatFormatting.YELLOW).append(Component.literal(attributeCS).withStyle(ChatFormatting.GREEN)),
                           attributeCS
                        );
                        attributeModifiers.forEach(attributeModifier -> {
                           sendAttributePropertyInformation(source, "ID", attributeModifier.id().toString());
                           sendAttributePropertyInformation(source, "Operation", attributeModifier.operation().name());
                           sendAttributePropertyInformation(source, "Amount", String.valueOf(attributeModifier.amount()));
                           sendAttributePropertyInformation(source, "IData", new MapData(attributeModifier.save()).asString());
                        });
                     }
                  );
               }

               sendCopyingHand(source, Component.translatable("crafttweaker.command.misc.item"), BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
               return 1;
            }
         )
      );
   }

   private static void sendBasicItemInformation(CommandSourceStack source, ItemStack target) {
      String output = ItemStackUtil.getCommandString(target);
      sendCopyingHand(source, Component.translatable("crafttweaker.command.misc.item"), output);
   }

   private static void sendBlockInformation(CommandSourceStack source, BlockItem target) {
      sendBlockInformation(source, target.getBlock());
   }

   private static void sendBlockInformation(CommandSourceStack source, Block target) {
      sendHand(source, Component.translatable("crafttweaker.command.misc.block"), ExpandBlock.getCommandString(target));
      sendHand(source, Component.translatable("crafttweaker.command.misc.blockstate"), ExpandBlockState.getCommandString(target.defaultBlockState()));
   }

   private static void sendBucketInformation(CommandSourceStack source, BucketItem target) {
      if (Services.PLATFORM.getBucketContent(target) != Fluids.EMPTY) {
         sendHand(
            source,
            Component.translatable("crafttweaker.command.misc.fluidblockstate"),
            ExpandBlockState.getCommandString(Services.PLATFORM.getBucketContent(target).defaultFluidState().createLegacyBlock())
         );
      }
   }

   private static Collection<String> sendTagsInformation(CommandSourceStack source, Item item) {
      List<String> tags = new ArrayList<>(sendItemTagsInformation(source, item));
      if (item instanceof BlockItem) {
         tags.addAll(sendBlockTagsInformation(source, (BlockItem)item));
      }

      return tags;
   }

   private static Collection<String> sendItemTagsInformation(CommandSourceStack source, Item item) {
      return sendTagsInformation(
         source, Component.translatable("crafttweaker.command.hand.header.tags.item"), CraftTweakerTagRegistry.INSTANCE.knownTagManager(Registries.ITEM), item
      );
   }

   private static Collection<String> sendBlockTagsInformation(CommandSourceStack source, BlockItem item) {
      return sendTagsInformation(
         source,
         Component.translatable("crafttweaker.command.hand.header.tags.block"),
         CraftTweakerTagRegistry.INSTANCE.knownTagManager(Registries.BLOCK),
         item.getBlock()
      );
   }

   private static <T> Collection<String> sendTagsInformation(CommandSourceStack source, MutableComponent header, KnownTagManager<?> manager, T target) {
      Holder<T> tHolder = Services.REGISTRY.holderOrThrow(manager.resourceKey(), target);
      if (tHolder.tags().findAny().isEmpty()) {
         return List.of();
      }

      CommandUtilities.send(source, header.withStyle(ChatFormatting.DARK_AQUA));
      return tHolder.tags()
         .map(tTagKey -> new KnownTag<>(tTagKey.location(), manager))
         .map(MCTag::getCommandString)
         .peek(it -> sendTagHand(source, it))
         .toList();
   }

   private static void sendBasicVanillaItemInformation(CommandSourceStack source, ItemStack target) {
      String output = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(target.getItem())).toString();
      sendCopyingHand(source, Component.translatable("crafttweaker.command.misc.item"), output);
   }

   private static void sendVanillaDataInformation(CommandSourceStack source, Tag nbt) {
      sendHand(source, Component.translatable("crafttweaker.command.misc.data"), nbt.toString());
   }

   private static void sendVanillaBucketInformation(CommandSourceStack source, BucketItem target) {
      if (Services.PLATFORM.getBucketContent(target) != Fluids.EMPTY) {
         sendHand(
            source,
            Component.translatable("crafttweaker.command.misc.fluidblockstate"),
            BuiltInRegistries.FLUID.getKey(Services.PLATFORM.getBucketContent(target)).toString()
         );
      }
   }

   private static void sendVanillaTagsInformation(CommandSourceStack source, Item item) {
      sendVanillaItemTagsInformation(source, item);
      if (item instanceof BlockItem) {
         sendVanillaBlockTagsInformation(source, (BlockItem)item);
      }
   }

   private static void sendVanillaItemTagsInformation(CommandSourceStack source, Item item) {
      sendVanillaTagsInformation(
         source, Component.translatable("crafttweaker.command.hand.header.tags.item"), CraftTweakerTagRegistry.INSTANCE.tagManager(Registries.ITEM), item
      );
   }

   private static void sendVanillaBlockTagsInformation(CommandSourceStack source, BlockItem item) {
      sendVanillaTagsInformation(
         source,
         Component.translatable("crafttweaker.command.hand.header.tags.block"),
         CraftTweakerTagRegistry.INSTANCE.tagManager(Registries.BLOCK),
         item.getBlock()
      );
   }

   private static <T> void sendVanillaTagsInformation(CommandSourceStack source, MutableComponent header, ITagManager<?> manager, T target) {
      Holder<T> tHolder = Services.REGISTRY.holderOrThrow(manager.resourceKey(), target);
      if (!tHolder.tags().findAny().isEmpty()) {
         CommandUtilities.send(source, header.withStyle(ChatFormatting.DARK_AQUA));
         tHolder.tags().map(tTagKey -> "#" + tTagKey.location()).forEach(it -> sendTagHand(source, it));
      }
   }

   private static void sendAttributePropertyInformation(CommandSourceStack source, String propertyName, String value) {
      CommandUtilities.sendCopying(
         source,
         Component.literal("    - ")
            .append(propertyName)
            .append(": ")
            .withStyle(ChatFormatting.YELLOW)
            .append(Component.literal(value).withStyle(ChatFormatting.AQUA)),
         value
      );
   }

   private static void sendHand(CommandSourceStack source, MutableComponent messagePrefix, String target) {
      sendHand(source, messagePrefix, target, CommandUtilities::sendCopying);
   }

   private static void sendCopyingHand(CommandSourceStack source, MutableComponent messagePrefix, String target) {
      sendHand(source, messagePrefix, target, CommandUtilities::sendCopyingAndCopy);
   }

   private static void sendHand(
      CommandSourceStack source, MutableComponent messagePrefix, String target, TriConsumer<CommandSourceStack, MutableComponent, String> consumer
   ) {
      consumer.accept(source, messagePrefix.append(": ").append(Component.literal(target).withStyle(ChatFormatting.GREEN)), target);
   }

   private static void sendTagHand(CommandSourceStack source, String tag) {
      CommandUtilities.sendCopying(
         source,
         Component.literal("    ")
            .append(Component.literal("- ").withStyle(ChatFormatting.YELLOW))
            .append(" ")
            .append(Component.literal(tag).withStyle(ChatFormatting.AQUA)),
         tag
      );
   }
}
