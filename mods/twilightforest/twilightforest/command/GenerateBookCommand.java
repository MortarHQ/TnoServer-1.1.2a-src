package twilightforest.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;
import twilightforest.beanification.Component;
import twilightforest.world.components.structures.util.StructureHints;

@Component
public class GenerateBookCommand {
   private final SimpleCommandExceptionType ERROR_NOT_RUN_BY_PLAYER = new SimpleCommandExceptionType(
      net.minecraft.network.chat.Component.translatable("commands.tffeature.not_player")
   );

   public LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("genbook")
               .executes(context -> this.generateBook((CommandSourceStack)context.getSource(), null)))
            .requires(cs -> cs.hasPermission(3)))
         .then(
            Commands.argument("structure", ResourceKeyArgument.key(Registries.STRUCTURE))
               .executes(context -> this.generateBook((CommandSourceStack)context.getSource(), ResourceKeyArgument.getStructure(context, "structure")))
         );
   }

   private int generateBook(CommandSourceStack source, @Nullable Reference<Structure> structureKey) throws CommandSyntaxException {
      if (source.getEntity() instanceof Player player && !(player instanceof FakePlayer)) {
         if (structureKey == null) {
            for (Structure structure : source.getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE).stream().toList()) {
               if (structure instanceof StructureHints hint) {
                  ItemStack book = hint.createHintBook(source.registryAccess());
                  if (!book.isEmpty() && !player.addItem(book)) {
                     player.drop(book, true);
                  }
               }
            }
         } else if (source.getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE).get(structureKey.key()) instanceof StructureHints hint) {
            ItemStack book = hint.createHintBook(source.registryAccess());
            if (!book.isEmpty() && !player.addItem(book)) {
               player.drop(book, true);
            }
         } else {
            ItemStack book = StructureHints.HintConfig.defaultBook();
            if (!player.addItem(book)) {
               player.drop(book, true);
            }
         }

         return 1;
      } else {
         throw this.ERROR_NOT_RUN_BY_PLAYER.create();
      }
   }
}
