package twilightforest.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Comparator;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import twilightforest.beanification.Component;

@Component
public class CountTemplateCommand {
   public LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("count_template").requires(cs -> cs.hasPermission(2)))
         .then(Commands.argument("filter_structure", ResourceKeyArgument.key(Registries.STRUCTURE)).executes(this::countTemplates));
   }

   private int countTemplates(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      Reference<Structure> structure = ResourceKeyArgument.getStructure(context, "filter_structure");
      if (!structure.isBound()) {
         return 0;
      }

      CommandSourceStack source = (CommandSourceStack)context.getSource();
      ServerLevel level = source.getLevel();
      BlockPos commandPos = BlockPos.containing(source.getPosition());
      StructureStart structureAt = level.structureManager().getStructureAt(commandPos, (Structure)structure.value());
      Object2IntMap<ResourceLocation> templateCounts = new Object2IntOpenHashMap();

      for (StructurePiece piece : structureAt.getPieces()) {
         if (piece instanceof TemplateStructurePiece templatePiece) {
            ResourceLocation resourceLocation = templatePiece.makeTemplateLocation();
            templateCounts.put(resourceLocation, templateCounts.getOrDefault(resourceLocation, 0) + 1);
         }
      }

      for (Entry<ResourceLocation> countedTemplate : templateCounts.object2IntEntrySet()
         .stream()
         .sorted(Comparator.comparing(java.util.Map.Entry::getKey))
         .sorted(Comparator.comparing(Entry::getIntValue))
         .toList()) {
         MutableComponent text = net.minecraft.network.chat.Component.literal(countedTemplate.getKey() + "    " + countedTemplate.getIntValue());
         ((CommandSourceStack)context.getSource()).sendSystemMessage(text);
      }

      return templateCounts.size();
   }
}
