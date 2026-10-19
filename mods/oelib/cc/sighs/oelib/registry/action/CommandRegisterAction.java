package cc.sighs.oelib.registry.action;

import com.mojang.brigadier.CommandDispatcher;
import java.util.Objects;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;

public record CommandRegisterAction(CommandRegisterAction.CommandRegistrar registrar, boolean clientOnly) implements RegistrationAction {
   public CommandRegisterAction {
      Objects.requireNonNull(registrar, "registrar");
   }

   @FunctionalInterface
   public interface CommandRegistrar {
      void register(CommandDispatcher<CommandSourceStack> var1, CommandBuildContext var2, CommandSelection var3);
   }
}
