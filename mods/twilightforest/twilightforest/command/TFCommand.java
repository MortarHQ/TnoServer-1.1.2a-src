package twilightforest.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.Component;

@Component
public class TFCommand {
   @Autowired
   private CenterCommand centerCommand;
   @Autowired
   private ConquerCommand conquerCommand;
   @Autowired
   private GenerateBookCommand generateBookCommand;
   @Autowired
   private InfoCommand infoCommand;
   @Autowired
   private MapBiomesCommand mapBiomesCommand;
   @Autowired
   private MapLocatorCommand mapLocatorCommand;
   @Autowired
   private ShieldCommand shieldCommand;
   @Autowired
   private SinisterSpawnerCommand spawnerCommand;
   @Autowired
   private DisplayPiecesCommand displayPiecesCommand;
   @Autowired
   private CountLootCommand countLootCommand;
   @Autowired
   private CountTemplateCommand countTemplateCommand;
   @Autowired
   private StructureDistanceCommand structureDistanceCommand;
   @Autowired
   private ClearDisplayCommand clearDisplayCommand;
   @Autowired
   private TravellersGearCommand travellersGearCommand;
   @Autowired
   private TFTeleportCommand tfTeleportCommand;

   public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
      LiteralArgumentBuilder<CommandSourceStack> structureBranch = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                        "structure_util"
                     )
                     .then(this.displayPiecesCommand.register()))
                  .then(this.clearDisplayCommand.register()))
               .then(this.countLootCommand.register()))
            .then(this.countTemplateCommand.register()))
         .then(this.structureDistanceCommand.register());
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                             "twilightforest"
                                          )
                                          .executes(this::run))
                                       .then(this.tfTeleportCommand.register()))
                                    .then(this.centerCommand.register()))
                                 .then(this.mapLocatorCommand.register()))
                              .then(this.conquerCommand.register()))
                           .then(this.generateBookCommand.register()))
                        .then(this.infoCommand.register()))
                     .then(this.mapBiomesCommand.register()))
                  .then(this.shieldCommand.register()))
               .then(this.spawnerCommand.register(buildContext)))
            .then(this.travellersGearCommand.register()))
         .then(structureBranch);
      LiteralCommandNode<CommandSourceStack> node = dispatcher.register(builder);
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tf").executes(this::run)).redirect(node));
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tffeature").executes(this::run)).redirect(node));
   }

   private int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      throw new SimpleCommandExceptionType(net.minecraft.network.chat.Component.translatable("commands.tffeature.usage", new Object[]{ctx.getInput()}))
         .create();
   }
}
