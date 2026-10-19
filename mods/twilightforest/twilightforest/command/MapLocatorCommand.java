package twilightforest.command;

import com.google.common.base.Stopwatch;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument.Result;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import twilightforest.beanification.Component;

@Component
public class MapLocatorCommand {
   private final DynamicCommandExceptionType ERROR_STRUCTURE_INVALID = new DynamicCommandExceptionType(
      info -> net.minecraft.network.chat.Component.translatableEscape("commands.locate.structure.invalid", new Object[]{info})
   );
   private final DynamicCommandExceptionType ERROR_STRUCTURE_NOT_FOUND = new DynamicCommandExceptionType(
      info -> net.minecraft.network.chat.Component.translatableEscape("commands.locate.structure.not_found", new Object[]{info})
   );

   public LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("map_locator").requires(cs -> cs.hasPermission(2)))
         .then(
            ((RequiredArgumentBuilder)Commands.argument("structure", ResourceOrTagKeyArgument.resourceOrTagKey(Registries.STRUCTURE))
                  .executes(context -> this.run(context, Collections.singleton(((CommandSourceStack)context.getSource()).getPlayerOrException()), false)))
               .then(
                  ((RequiredArgumentBuilder)Commands.argument("player", EntityArgument.players())
                        .executes(context -> this.run(context, EntityArgument.getPlayers(context, "player"), false)))
                     .then(
                        Commands.argument("skip_known_structures", BoolArgumentType.bool())
                           .executes(
                              context -> this.run(
                                 context, EntityArgument.getPlayers(context, "player"), BoolArgumentType.getBool(context, "skip_known_structures")
                              )
                           )
                     )
               )
         );
   }

   private int run(CommandContext<CommandSourceStack> context, Collection<ServerPlayer> players, boolean skipKnownStructures) throws CommandSyntaxException {
      Result<Structure> structure = ResourceOrTagKeyArgument.getResourceOrTagKey(context, "structure", Registries.STRUCTURE, this.ERROR_STRUCTURE_INVALID);
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      Registry<Structure> registry = source.getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE);
      HolderSet<Structure> holderset = (HolderSet<Structure>)LocateCommand.getHolders(structure, registry)
         .orElseThrow(() -> this.ERROR_STRUCTURE_INVALID.create(structure.asPrintable()));
      BlockPos blockpos = BlockPos.containing(source.getPosition());
      ServerLevel serverlevel = source.getLevel();
      Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
      Pair<BlockPos, Holder<Structure>> pair = serverlevel.getChunkSource()
         .getGenerator()
         .findNearestMapStructure(serverlevel, holderset, blockpos, 100, skipKnownStructures);
      stopwatch.stop();
      if (pair == null) {
         throw this.ERROR_STRUCTURE_NOT_FOUND.create(structure.asPrintable());
      }

      BlockPos foundPos = (BlockPos)pair.getFirst();
      ItemStack itemstack = MapItem.create(serverlevel, foundPos.getX(), foundPos.getZ(), (byte)4, true, true);
      MapItemSavedData.addTargetDecoration(itemstack, foundPos, "+", MapDecorationTypes.RED_X);

      for (ServerPlayer player : players) {
         player.getInventory().add(itemstack.copy());
      }

      return LocateCommand.showLocateResult(source, structure, foundPos, pair, "commands.locate.structure.success", false, stopwatch.elapsed());
   }
}
