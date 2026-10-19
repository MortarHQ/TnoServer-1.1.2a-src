package dev.ftb.mods.ftblibrary;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrarManager;
import dev.ftb.mods.ftblibrary.nbtedit.NBTEditResponseHandlers;
import dev.ftb.mods.ftblibrary.net.EditConfigPacket;
import dev.ftb.mods.ftblibrary.ui.misc.UITesting;
import dev.ftb.mods.ftblibrary.util.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FTBLibraryCommands {
   public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext ignoredCtx, CommandSelection ignoredType) {
      LiteralArgumentBuilder<CommandSourceStack> command = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                           "ftblibrary"
                        )
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("gamemode").requires(commandSource -> commandSource.hasPermission(2)))
                              .executes(context -> {
                                 if (!((CommandSourceStack)context.getSource()).getPlayerOrException().isCreative()) {
                                    ((CommandSourceStack)context.getSource()).getPlayerOrException().setGameMode(GameType.CREATIVE);
                                 } else {
                                    ((CommandSourceStack)context.getSource()).getPlayerOrException().setGameMode(GameType.SURVIVAL);
                                 }

                                 return 1;
                              })
                        ))
                     .then(((LiteralArgumentBuilder)Commands.literal("rain").requires(commandSource -> commandSource.hasPermission(2))).executes(context -> {
                        if (((CommandSourceStack)context.getSource()).getServer().overworld().isRaining()) {
                           ((CommandSourceStack)context.getSource()).getServer().overworld().setWeatherParameters(6000, 0, false, false);
                        } else {
                           ((CommandSourceStack)context.getSource()).getServer().overworld().setWeatherParameters(0, 6000, true, false);
                        }

                        return 1;
                     })))
                  .then(((LiteralArgumentBuilder)Commands.literal("day").requires(commandSource -> commandSource.hasPermission(2))).executes(context -> {
                     for (ServerLevel world : ((CommandSourceStack)context.getSource()).getServer().getAllLevels()) {
                        world.setDayTime(6000L);
                     }

                     ((CommandSourceStack)context.getSource()).getServer().forceTimeSynchronization();
                     return 1;
                  })))
               .then(((LiteralArgumentBuilder)Commands.literal("night").requires(commandSource -> commandSource.hasPermission(2))).executes(context -> {
                  for (ServerLevel world : ((CommandSourceStack)context.getSource()).getServer().getAllLevels()) {
                     world.setDayTime(18000L);
                  }

                  ((CommandSourceStack)context.getSource()).getServer().forceTimeSynchronization();
                  return 1;
               })))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("nbtedit")
                              .requires(commandSource -> commandSource.hasPermission(2)))
                           .then(
                              Commands.literal("block")
                                 .then(
                                    Commands.argument("pos", BlockPosArgument.blockPos())
                                       .executes(context -> editNBT(context, (info, tag) -> editBlockNBT(context, info, tag)))
                                 )
                           ))
                        .then(
                           Commands.literal("entity")
                              .then(
                                 Commands.argument("entity", EntityArgument.entity())
                                    .executes(context -> editNBT(context, (info, tag) -> editEntityNBT(context, info, tag)))
                              )
                        ))
                     .then(
                        Commands.literal("player")
                           .then(
                              Commands.argument("player", EntityArgument.player())
                                 .executes(context -> editNBT(context, (info, tag) -> editPlayerNBT(context, info, tag)))
                           )
                     ))
                  .then(Commands.literal("item").executes(context -> editNBT(context, (info, tag) -> editItemNBT(context, info, tag))))
            ))
         .then(((LiteralArgumentBuilder)Commands.literal("clientconfig").requires(CommandSourceStack::isPlayer)).executes(context -> {
            NetworkManager.sendToPlayer(((CommandSourceStack)context.getSource()).getPlayerOrException(), new EditConfigPacket("ftblibrary-client"));
            return 1;
         }));
      if (ModUtils.isDevMode()) {
         command.then(Commands.literal("test_screen").executes(context -> {
            if (((CommandSourceStack)context.getSource()).getServer().isDedicatedServer()) {
               ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("Can't do this on dedicated server!").withStyle(ChatFormatting.RED));
            } else {
               UITesting.openTestScreen();
            }

            return 1;
         }));
         command.then(((LiteralArgumentBuilder)Commands.literal("serverconfig").requires(cs -> cs.hasPermission(2))).executes(context -> {
            NetworkManager.sendToPlayer(((CommandSourceStack)context.getSource()).getPlayerOrException(), new EditConfigPacket("ftblibrary-server"));
            return 1;
         }));
      }

      dispatcher.register(command);
   }

   private static int editNBT(CommandContext<CommandSourceStack> context, FTBLibraryCommands.NBTEditCallback data) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      CompoundTag info = new CompoundTag();
      CompoundTag tag = new CompoundTag();
      data.accept(info, tag);
      return NBTEditResponseHandlers.INSTANCE.sendRequestPacket(player, info, tag);
   }

   private static void editItemNBT(CommandContext<CommandSourceStack> context, CompoundTag info, CompoundTag tag) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      if (!player.getMainHandItem().isEmpty()) {
         info.putString("type", "item");
         ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
         ResourceLocation key = RegistrarManager.getId(stack.getItem(), Registries.ITEM);
         info.put(
            "text",
            FTBLibraryCommands.InfoBuilder.create(context)
               .add("Class", Component.literal(stack.getItem().getClass().getName()))
               .add("ID", Component.literal(key == null ? "null" : key.toString()))
               .add("Mod", Component.literal(key == null ? "null" : Platform.getOptionalMod(key.getNamespace()).<String>map(Mod::getName).orElse("Unknown")))
               .build()
         );
         if (stack.save(player.level().registryAccess(), tag) instanceof CompoundTag t) {
            tag.merge(t);
         }
      }
   }

   private static void editPlayerNBT(CommandContext<CommandSourceStack> context, CompoundTag info, CompoundTag tag) throws CommandSyntaxException {
      ServerPlayer player = EntityArgument.getPlayer(context, "player");
      info.putString("type", "player");
      info.putUUID("id", player.getUUID());
      player.saveWithoutId(tag);
      tag.remove("id");
      info.put(
         "text",
         FTBLibraryCommands.InfoBuilder.create(context)
            .add("Name", player.getName())
            .add("Display Name", player.getDisplayName())
            .add("UUID", Component.literal(player.getUUID().toString()))
            .build()
      );
      info.putString("title", Serializer.toJson(player.getDisplayName(), player.level().registryAccess()));
   }

   private static void editEntityNBT(CommandContext<CommandSourceStack> context, CompoundTag info, CompoundTag tag) throws CommandSyntaxException {
      Entity entity = EntityArgument.getEntity(context, "entity");
      if (!(entity instanceof Player)) {
         info.putString("type", "entity");
         info.putInt("id", entity.getId());
         entity.save(tag);
         ResourceLocation key = RegistrarManager.getId(entity.getType(), Registries.ENTITY_TYPE);
         info.put(
            "text",
            FTBLibraryCommands.InfoBuilder.create(context)
               .add("Class", Component.literal(entity.getClass().getName()))
               .add("ID", Component.literal(key == null ? "null" : key.toString()))
               .add("Mod", Component.literal(key == null ? "null" : Platform.getOptionalMod(key.getNamespace()).<String>map(Mod::getName).orElse("Unknown")))
               .build()
         );
         info.putString("title", Serializer.toJson(entity.getDisplayName(), entity.level().registryAccess()));
      }
   }

   private static void editBlockNBT(CommandContext<CommandSourceStack> context, CompoundTag info, CompoundTag tag) throws CommandSyntaxException {
      BlockPos pos = BlockPosArgument.getSpawnablePos(context, "pos");
      BlockState blockState = ((CommandSourceStack)context.getSource()).getLevel().getBlockState(pos);
      BlockEntity blockEntity = ((CommandSourceStack)context.getSource()).getLevel().getBlockEntity(pos);
      if (blockEntity == null) {
         ((CommandSourceStack)context.getSource())
            .sendFailure(Component.literal("Not a block entity: ").append(blockState.getBlock().toString()).withStyle(ChatFormatting.RED));
      } else {
         info.putString("type", "block");
         info.put("pos", NbtUtils.writeBlockPos(pos));
         tag.merge(blockEntity.saveWithFullMetadata(((CommandSourceStack)context.getSource()).getLevel().registryAccess()));
         tag.remove("x");
         tag.remove("y");
         tag.remove("z");
         info.putString("id", tag.getString("id"));
         tag.remove("id");
         ResourceLocation key = RegistrarManager.getId(blockEntity.getType(), Registries.BLOCK_ENTITY_TYPE);
         info.put(
            "text",
            FTBLibraryCommands.InfoBuilder.create(context)
               .add("Class", Component.literal(blockEntity.getClass().getName()))
               .add("ID", Component.literal(key == null ? "null" : key.toString()))
               .add("Block", Component.literal(String.valueOf(RegistrarManager.getId(blockEntity.getBlockState().getBlock(), Registries.BLOCK))))
               .add("Block Class", Component.literal(blockEntity.getBlockState().getBlock().getClass().getName()))
               .add("Position", Component.literal("[" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "]"))
               .add("Mod", Component.literal(key == null ? "null" : Platform.getOptionalMod(key.getNamespace()).<String>map(Mod::getName).orElse("Unknown")))
               .add("Ticking", Component.literal(isTicking(blockEntity) ? "true" : "false"))
               .build()
         );
         Component title = (Component)(blockEntity instanceof Nameable n ? n.getDisplayName() : Component.literal(blockEntity.getClass().getSimpleName()));
         info.putString("title", Serializer.toJson(title, ((CommandSourceStack)context.getSource()).registryAccess()));
      }
   }

   private static boolean isTicking(BlockEntity be) {
      return be.getBlockState().getBlock() instanceof EntityBlock eb && eb.getTicker(be.getLevel(), be.getBlockState(), be.getType()) != null;
   }

   public record InfoBuilder(ListTag list, Provider provider) {
      public static FTBLibraryCommands.InfoBuilder create(CommandContext<CommandSourceStack> context) {
         return new FTBLibraryCommands.InfoBuilder(new ListTag(), ((CommandSourceStack)context.getSource()).registryAccess());
      }

      public FTBLibraryCommands.InfoBuilder add(String key, Component value) {
         return this.add(Component.literal(key), value);
      }

      public FTBLibraryCommands.InfoBuilder add(Component key, Component value) {
         this.list
            .add(
               StringTag.valueOf(
                  Serializer.toJson(key.copy().withStyle(ChatFormatting.BLUE).append(": ").append(value.copy().withStyle(ChatFormatting.GOLD)), this.provider)
               )
            );
         return this;
      }

      public ListTag build() {
         return this.list;
      }
   }

   private interface NBTEditCallback {
      void accept(CompoundTag var1, CompoundTag var2) throws CommandSyntaxException;
   }
}
