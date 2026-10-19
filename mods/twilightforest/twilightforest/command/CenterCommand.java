package twilightforest.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import twilightforest.beanification.Component;
import twilightforest.util.landmarks.LegacyLandmarkPlacements;

@Component
public class CenterCommand {
   public LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("center").requires(cs -> cs.hasPermission(2)))
         .executes(this::run);
   }

   private int run(CommandContext<CommandSourceStack> ctx) {
      CommandSourceStack source = (CommandSourceStack)ctx.getSource();
      int dx = Mth.floor(source.getPosition().x());
      int dz = Mth.floor(source.getPosition().z());
      BlockPos cc = LegacyLandmarkPlacements.getNearestCenterXZ(dx >> 4, dz >> 4);
      ResourceLocation closestFeature = LegacyLandmarkPlacements.pickLandmarkAtBlock(cc.getX(), cc.getZ(), source.getLevel()).location();
      boolean fc = LegacyLandmarkPlacements.blockIsInLandmarkCenter(dx, dz);
      String structurename = net.minecraft.network.chat.Component.translatable(closestFeature.toLanguageKey("structure"))
         .withStyle(ChatFormatting.DARK_GREEN)
         .getString();
      source.sendSuccess(() -> net.minecraft.network.chat.Component.translatable("commands.tffeature.nearest", new Object[]{structurename}), false);
      source.sendSuccess(() -> net.minecraft.network.chat.Component.translatable("commands.tffeature.center", new Object[]{cc.toShortString()}), false);
      source.sendSuccess(() -> net.minecraft.network.chat.Component.translatable("commands.tffeature.chunk", new Object[]{fc}), false);
      return 1;
   }
}
