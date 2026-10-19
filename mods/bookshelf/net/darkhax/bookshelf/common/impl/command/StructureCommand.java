package net.darkhax.bookshelf.common.impl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.darkhax.bookshelf.common.api.commands.PermissionLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> build() {
      LiteralArgumentBuilder<CommandSourceStack> root = (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("structure")
         .requires(PermissionLevel.GAMEMASTER);
      root.executes(withPosition(StructureCommand::structureAt, ctx -> ((CommandSourceStack)ctx.getSource()).getEntity().blockPosition()));
      root.then(
         Commands.argument("position", BlockPosArgument.blockPos())
            .executes(withPosition(StructureCommand::structureAt, ctx -> BlockPosArgument.getBlockPos(ctx, "position")))
      );
      return root;
   }

   private static Set<ResourceLocation> getUniqueStructuresAt(CommandContext<CommandSourceStack> context, BlockPos pos) {
      ServerLevel level = ((CommandSourceStack)context.getSource()).getLevel();
      Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
      return level.structureManager()
         .startsForStructure(new ChunkPos(pos), s -> true)
         .stream()
         .filter(s -> s.getBoundingBox().isInside(pos))
         .map(s -> registry.getKey(s.getStructure()))
         .collect(Collectors.toSet());
   }

   private static int structureAt(CommandContext<CommandSourceStack> context, BlockPos pos) {
      Set<ResourceLocation> structures = getUniqueStructuresAt(context, pos);
      if (structures.isEmpty()) {
         ((CommandSourceStack)context.getSource()).sendFailure(Component.translatable("commands.bookshelf.structure.error.no_structures"));
      } else {
         ((CommandSourceStack)context.getSource())
            .sendSuccess(
               () -> Component.translatable("commands.bookshelf.structure.found", new Object[]{structures.size()})
                  .append(Component.literal("\n  " + structures.stream().<CharSequence>map(ResourceLocation::toString).collect(Collectors.joining("\n  ")))),
               false
            );
      }

      return structures.size();
   }

   private static Command<CommandSourceStack> withPosition(
      BiFunction<CommandContext<CommandSourceStack>, BlockPos, Integer> cmd, Function<CommandContext<CommandSourceStack>, BlockPos> provider
   ) {
      return ctx -> cmd.apply(ctx, provider.apply(ctx));
   }
}
