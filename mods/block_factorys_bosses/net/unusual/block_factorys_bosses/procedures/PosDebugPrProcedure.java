package net.unusual.block_factorys_bosses.procedures;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.LevelAccessor;
import net.unusual.block_factorys_bosses.network.BlockFactorysBossesModVariables;

public class PosDebugPrProcedure {
   public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
      BlockFactorysBossesModVariables.MapVariables.get(world).debugX = DoubleArgumentType.getDouble(arguments, "x");
      BlockFactorysBossesModVariables.MapVariables.get(world).syncData(world);
      BlockFactorysBossesModVariables.MapVariables.get(world).debugY = DoubleArgumentType.getDouble(arguments, "y");
      BlockFactorysBossesModVariables.MapVariables.get(world).syncData(world);
      BlockFactorysBossesModVariables.MapVariables.get(world).debugZ = DoubleArgumentType.getDouble(arguments, "z");
      BlockFactorysBossesModVariables.MapVariables.get(world).syncData(world);
   }
}
