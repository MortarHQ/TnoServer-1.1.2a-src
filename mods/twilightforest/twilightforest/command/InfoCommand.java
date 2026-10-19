package twilightforest.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.neoforged.fml.loading.FMLLoader;
import twilightforest.beanification.Component;
import twilightforest.events.EntityEvents;
import twilightforest.util.landmarks.LandmarkUtil;
import twilightforest.world.components.structures.start.TFStructureStart;
import twilightforest.world.components.structures.util.LandmarkStructure;

@Component
public class InfoCommand {
   public LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("info").requires(cs -> cs.hasPermission(2)))
         .executes(this::run);
   }

   private int run(CommandContext<CommandSourceStack> ctx) {
      CommandSourceStack source = (CommandSourceStack)ctx.getSource();
      ServerLevel level = source.getLevel();
      BlockPos pos = BlockPos.containing(source.getPosition());
      Optional<Registry<Structure>> possibleStructureRegistry = level.registryAccess().registry(Registries.STRUCTURE);
      Optional<StructureStart> possibleNearLandmark = LandmarkUtil.locateNearestLandmarkStart(
         level, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ())
      );
      if (!possibleStructureRegistry.isEmpty()
         && !possibleNearLandmark.isEmpty()
         && possibleNearLandmark.get().getStructure() instanceof LandmarkStructure landmarkStructure) {
         StructureStart var16 = possibleNearLandmark.get();
         ResourceLocation key = possibleStructureRegistry.get().getKey(landmarkStructure);
         if (FMLLoader.isProduction()) {
            source.sendSuccess(
               () -> net.minecraft.network.chat.Component.translatable("commands.tffeature.info.wip")
                  .withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD}),
               false
            );
         }

         String structureName = net.minecraft.network.chat.Component.translatable("structure." + key.getNamespace() + "." + key.getPath()).getString();
         source.sendSuccess(() -> net.minecraft.network.chat.Component.translatable("commands.tffeature.nearest", new Object[]{structureName}), false);
         BoundingBox boundingBox = var16.getBoundingBox();
         StringJoiner boxInfo = new StringJoiner(", ", "[", "]");
         boxInfo.add(boundingBox.minX() + "");
         boxInfo.add(boundingBox.minY() + "");
         boxInfo.add(boundingBox.minZ() + "");
         boxInfo.add(boundingBox.maxX() + "");
         boxInfo.add(boundingBox.maxY() + "");
         boxInfo.add(boundingBox.maxZ() + "");
         source.sendSuccess(() -> net.minecraft.network.chat.Component.translatable("commands.tffeature.structure.boundaries", new Object[]{boxInfo}), false);
         if (boundingBox.isInside(pos)) {
            source.sendSuccess(
               () -> net.minecraft.network.chat.Component.translatable("commands.tffeature.structure.inside")
                  .withStyle(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}),
               false
            );
            if (var16 instanceof TFStructureStart tfStructureStart) {
               source.sendSuccess(
                  () -> net.minecraft.network.chat.Component.translatable(
                        "commands.tffeature.structure.conquer.status", new Object[]{tfStructureStart.isConquered()}
                     )
                     .withStyle(new ChatFormatting[]{ChatFormatting.BOLD, tfStructureStart.isConquered() ? ChatFormatting.GREEN : ChatFormatting.RED}),
                  false
               );
            }

            List<SpawnerData> spawnList = EntityEvents.gatherPotentialSpawns(level.structureManager(), MobCategory.MONSTER, pos);
            source.sendSuccess(
               () -> net.minecraft.network.chat.Component.translatable("commands.tffeature.structure.spawn_list").withStyle(ChatFormatting.UNDERLINE), false
            );
            if (spawnList != null) {
               for (SpawnerData entry : spawnList) {
                  source.sendSuccess(
                     () -> net.minecraft.network.chat.Component.translatable(
                        "commands.tffeature.structure.spawn_info", new Object[]{entry.type.getDescription().getString(), entry.getWeight().asInt()}
                     ),
                     false
                  );
               }
            }
         } else {
            source.sendSuccess(
               () -> net.minecraft.network.chat.Component.translatable("commands.tffeature.structure.outside")
                  .withStyle(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.RED}),
               false
            );
         }

         return 1;
      } else {
         return 0;
      }
   }
}
