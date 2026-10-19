package me.wesley1808.servercore.mixin.optimizations.command_blocks;

import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.BaseCommandBlock;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BaseCommandBlock.class, priority = 2000)
public class BaseCommandBlockMixin {
   @Unique
   @Nullable
   private ParseResults<CommandSourceStack> servercore$parsed;

   @Redirect(
      method = "performCommand(Lnet/minecraft/world/level/Level;)Z",
      require = 0,
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/commands/Commands;performPrefixedCommand(Lnet/minecraft/commands/CommandSourceStack;Ljava/lang/String;)V"
      )
   )
   private void servercore$useCachedParseResults(Commands commands, CommandSourceStack source, String command) {
      String cmd = command.startsWith("/") ? command.substring(1) : command;
      if (this.servercore$parsed == null) {
         this.servercore$parsed = commands.getDispatcher().parse(cmd, source);
         commands.performCommand(this.servercore$parsed, cmd);
      } else {
         commands.performCommand(Commands.mapSource(this.servercore$parsed, s -> source), cmd);
      }
   }

   @Inject(method = "load(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;)V", at = @At("RETURN"))
   private void servercore$resetCache(CompoundTag tag, Provider provider, CallbackInfo ci) {
      this.servercore$parsed = null;
   }

   @Inject(method = "setCommand(Ljava/lang/String;)V", at = @At("RETURN"))
   private void servercore$resetCache(String command, CallbackInfo ci) {
      this.servercore$parsed = null;
   }
}
