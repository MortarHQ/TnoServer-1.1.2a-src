package net.unusual.block_factorys_bosses.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.unusual.block_factorys_bosses.procedures.PosDebugPrProcedure;

@EventBusSubscriber
public class PosDebugCommand {
   @SubscribeEvent
   public static void registerCommand(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)Commands.literal("posdebug")
               .then(
                  Commands.argument("x", DoubleArgumentType.doubleArg())
                     .then(
                        Commands.argument("y", DoubleArgumentType.doubleArg())
                           .then(Commands.argument("z", DoubleArgumentType.doubleArg()).executes(arguments -> {
                              Level world = ((CommandSourceStack)arguments.getSource()).getUnsidedLevel();
                              double x = ((CommandSourceStack)arguments.getSource()).getPosition().x();
                              double y = ((CommandSourceStack)arguments.getSource()).getPosition().y();
                              double z = ((CommandSourceStack)arguments.getSource()).getPosition().z();
                              Entity entity = ((CommandSourceStack)arguments.getSource()).getEntity();
                              if (entity == null && world instanceof ServerLevel _servLevel) {
                                 entity = FakePlayerFactory.getMinecraft(_servLevel);
                              }

                              Direction direction = Direction.DOWN;
                              if (entity != null) {
                                 direction = entity.getDirection();
                              }

                              PosDebugPrProcedure.execute(world, arguments);
                              return 0;
                           }))
                     )
               )
         );
   }
}
