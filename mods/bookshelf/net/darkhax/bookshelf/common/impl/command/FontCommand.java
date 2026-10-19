package net.darkhax.bookshelf.common.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.UnaryOperator;
import net.darkhax.bookshelf.common.api.commands.PermissionLevel;
import net.darkhax.bookshelf.common.api.commands.args.FontArgument;
import net.darkhax.bookshelf.common.api.util.CommandHelper;
import net.darkhax.bookshelf.common.api.util.TextHelper;
import net.darkhax.bookshelf.common.mixin.access.block.AccessorBannerBlockEntity;
import net.darkhax.bookshelf.common.mixin.access.block.AccessorBaseContainerBlockEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;

public class FontCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> build() {
      LiteralArgumentBuilder<CommandSourceStack> font = (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("font")
         .requires(PermissionLevel.GAMEMASTER);
      LiteralArgumentBuilder<CommandSourceStack> rename = Commands.literal("rename");
      rename.then(FontArgument.argument().executes(FontCommand::renameItemWithFont));
      rename.then(Commands.argument("target", EntityArgument.entity()).then(FontArgument.argument().executes(FontCommand::renameItemWithFont)));
      font.then(rename);
      font.then(
         Commands.literal("block")
            .then(FontArgument.argument().then(Commands.argument("pos", BlockPosArgument.blockPos()).executes(FontCommand::renameBlockWithFont)))
      );
      font.then(
         Commands.literal("say")
            .then(FontArgument.argument().then(Commands.argument("message", MessageArgument.message()).executes(FontCommand::speakWithFont)))
      );
      return font;
   }

   private static int speakWithFont(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ResourceLocation fontId = FontArgument.get(context);
      Component inputMessage = TextHelper.applyFont(MessageArgument.getMessage(context, "message"), fontId);
      Component txtMessage = Component.translatable(
         "chat.type.announcement", new Object[]{((CommandSourceStack)context.getSource()).getDisplayName(), inputMessage}
      );
      ((CommandSourceStack)context.getSource()).getServer().getPlayerList().broadcastSystemMessage(txtMessage, false);
      return 0;
   }

   private static int renameItemWithFont(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ResourceLocation fontId = FontArgument.get(context);
      if (CommandHelper.getEntityOrSender("target", context) instanceof LivingEntity living) {
         ItemStack stack = living.getMainHandItem();
         if (stack.isEmpty()) {
            ((CommandSourceStack)context.getSource()).sendFailure(Component.translatable("commands.bookshelf.hand.error.not_air"));
            return 0;
         } else {
            stack.set(DataComponents.CUSTOM_NAME, TextHelper.applyFont(stack.getHoverName().copy(), fontId));
            return 1;
         }
      } else {
         ((CommandSourceStack)context.getSource()).sendFailure(Component.translatable("commands.bookshelf.font.bad_sender"));
         return 0;
      }
   }

   private static int renameBlockWithFont(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerLevel world = ((CommandSourceStack)context.getSource()).getLevel();
      ResourceLocation fontId = FontArgument.get(context);
      BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, "pos");
      BlockEntity tile = world.getBlockEntity(pos);
      if (tile != null && tile.hasLevel()) {
         switch (tile) {
            case BaseContainerBlockEntity container when tile instanceof AccessorBaseContainerBlockEntity accessor:
               accessor.bookshelf$name(TextHelper.applyFont(container.getName().copy(), fontId));
               break;
            case SignBlockEntity sign:
               sign.updateText(applySignFont(fontId), true);
               sign.updateText(applySignFont(fontId), false);
               sign.getLevel().sendBlockUpdated(sign.getBlockPos(), sign.getBlockState(), sign.getBlockState(), 3);
               break;
            case BannerBlockEntity banner when banner.hasCustomName() && banner instanceof AccessorBannerBlockEntity accessor:
               accessor.setName(TextHelper.applyFont(banner.getCustomName(), fontId));
               break;
            default:
               ((CommandSourceStack)context.getSource())
                  .sendFailure(Component.translatable("commands.bookshelf.font.unsupported_block", new Object[]{tile.getBlockState().getBlock().getName()}));
         }
      }

      return 1;
   }

   private static UnaryOperator<SignText> applySignFont(ResourceLocation fontId) {
      return text -> {
         SignText newText = text;

         for (int i = 0; i < 4; i++) {
            newText = newText.setMessage(i, TextHelper.applyFont(text.getMessage(i, false).copy(), fontId));
         }

         return newText;
      };
   }
}
