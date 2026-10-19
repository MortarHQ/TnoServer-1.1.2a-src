package twilightforest.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Optional;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import twilightforest.beanification.Component;
import twilightforest.util.landmarks.LandmarkUtil;
import twilightforest.world.components.structures.start.TFStructureStart;

@Component
public class ConquerCommand {
   private final SimpleCommandExceptionType NOT_IN_STRUCTURE = new SimpleCommandExceptionType(
      net.minecraft.network.chat.Component.translatable("commands.tffeature.structure.required")
   );

   public LiteralArgumentBuilder<CommandSourceStack> register() {
      LiteralArgumentBuilder<CommandSourceStack> conquer = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("conquer")
            .requires(cs -> cs.hasPermission(2)))
         .executes(ctx -> this.changeStructureActivity((CommandSourceStack)ctx.getSource(), true));
      LiteralArgumentBuilder<CommandSourceStack> reactivate = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal(
               "reactivate"
            )
            .requires(cs -> cs.hasPermission(2)))
         .executes(ctx -> this.changeStructureActivity((CommandSourceStack)ctx.getSource(), false));
      return (LiteralArgumentBuilder<CommandSourceStack>)conquer.then(reactivate);
   }

   private int changeStructureActivity(CommandSourceStack source, boolean flag) throws CommandSyntaxException {
      BlockPos pos = BlockPos.containing(source.getPosition());
      Optional<StructureStart> struct = LandmarkUtil.locateNearestLandmarkStart(
         source.getLevel(), SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ())
      );
      if (struct.isPresent() && struct.get().getBoundingBox().isInside(pos) && struct.get() instanceof TFStructureStart TFStructureStart) {
         source.sendSuccess(
            () -> net.minecraft.network.chat.Component.translatable(
               "commands.tffeature.structure.conquer.update", new Object[]{TFStructureStart.isConquered(), flag}
            ),
            true
         );
         TFStructureStart.setConquered(flag, source.getLevel());
         return 1;
      } else {
         throw this.NOT_IN_STRUCTURE.create();
      }
   }
}
