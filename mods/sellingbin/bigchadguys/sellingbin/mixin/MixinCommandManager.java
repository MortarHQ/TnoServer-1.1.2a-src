package bigchadguys.sellingbin.mixin;

import bigchadguys.sellingbin.init.ModCommands;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public class MixinCommandManager {
   @Shadow
   @Final
   private CommandDispatcher<CommandSourceStack> dispatcher;

   @Inject(method = "<init>(Lnet/minecraft/commands/Commands$CommandSelection;Lnet/minecraft/commands/CommandBuildContext;)V", at = @At("RETURN"))
   public void init(CommandSelection environment, CommandBuildContext commandRegistryAccess, CallbackInfo ci) {
      ModCommands.register(this.dispatcher, commandRegistryAccess, environment);
   }
}
