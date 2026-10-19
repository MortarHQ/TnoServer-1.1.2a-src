package com.mortal_cultivation.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.time.Duration;
import java.util.Optional;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.commands.arguments.ResourceOrTagArgument.Result;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public final class JiuyouColdPoolLocateCommand {
   private static final String BIOME_ID = "mortal_cultivation:jiuyou_cold_pool";
   private static final DynamicCommandExceptionType NOT_FOUND = new DynamicCommandExceptionType(
      id -> Component.translatable("commands.locate.biome.not_found", new Object[]{id})
   );

   private JiuyouColdPoolLocateCommand() {
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
      ResourceOrTagArgument<Biome> biomeArgument = ResourceOrTagArgument.resourceOrTag(buildContext, Registries.BIOME);
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("locate").requires(source -> source.hasPermission(2)))
            .then(
               Commands.literal("biome")
                  .then(
                     Commands.literal("mortal_cultivation:jiuyou_cold_pool")
                        .executes(context -> locate((CommandSourceStack)context.getSource(), biomeArgument))
                  )
            )
      );
   }

   private static int locate(CommandSourceStack source, ResourceOrTagArgument<Biome> biomeArgument) throws CommandSyntaxException {
      Result<Biome> biome = biomeArgument.parse(new StringReader("mortal_cultivation:jiuyou_cold_pool"));
      ServerLevel level = source.getLevel();
      Optional<Reference<Structure>> structure = level.registryAccess()
         .registryOrThrow(Registries.STRUCTURE)
         .getHolder(ResourceKey.create(Registries.STRUCTURE, WorldgenRegistry.JIUYOU_COLD_POOL_BIOME.location()));
      if (structure.isEmpty()) {
         throw NOT_FOUND.create("mortal_cultivation:jiuyou_cold_pool");
      } else {
         BlockPos origin = BlockPos.containing(source.getPosition());
         long started = System.nanoTime();
         Pair<BlockPos, Holder<Structure>> found = level.getChunkSource()
            .getGenerator()
            .findNearestMapStructure(level, HolderSet.direct(new Holder[]{(Holder)structure.get()}), origin, 100, false);
         if (found == null) {
            throw NOT_FOUND.create("mortal_cultivation:jiuyou_cold_pool");
         } else {
            ChunkPos locatedChunk = new ChunkPos((BlockPos)found.getFirst());
            ChunkAccess chunk = level.getChunk(locatedChunk.x, locatedChunk.z, ChunkStatus.STRUCTURE_STARTS);
            StructureStart start = level.structureManager()
               .getStartForStructure(SectionPos.bottomOf(chunk), (Structure)((Holder)found.getSecond()).value(), chunk);
            if (start != null && start.isValid() && !start.getPieces().isEmpty()) {
               BlockPos cavernPosition = ((StructurePiece)start.getPieces().getFirst()).getLocatorPosition();
               return LocateCommand.showLocateResult(
                  source,
                  biome,
                  origin,
                  Pair.of(cavernPosition, (Reference)biome.unwrap().left().orElseThrow()),
                  "commands.locate.biome.success",
                  true,
                  Duration.ofNanos(System.nanoTime() - started)
               );
            } else {
               throw NOT_FOUND.create("mortal_cultivation:jiuyou_cold_pool");
            }
         }
      }
   }
}
