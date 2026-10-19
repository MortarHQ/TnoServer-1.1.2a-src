package com.natamus.collective_common_neoforge.functions;

import java.util.Objects;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandFunctions {
   public static String getRawCommandOutput(final ServerLevel serverLevel, @Nullable final Vec3 vec, String command) {
      BaseCommandBlock bcb = new BaseCommandBlock() {
         @NotNull
         public ServerLevel getLevel() {
            return serverLevel;
         }

         public void onUpdated() {
         }

         @NotNull
         public Vec3 getPosition() {
            return Objects.requireNonNullElseGet(vec, () -> new Vec3(0.0, 0.0, 0.0));
         }

         @NotNull
         public CommandSourceStack createCommandSourceStack() {
            return new CommandSourceStack(this, this.getPosition(), Vec2.ZERO, serverLevel, 2, "dev", Component.literal("dev"), serverLevel.getServer(), null);
         }

         public boolean isValid() {
            return true;
         }

         public boolean performCommand(Level pLevel) {
            if (!pLevel.isClientSide) {
               if ("Searge".equalsIgnoreCase(this.getCommand())) {
                  this.setLastOutput(Component.literal("#itzlipofutzli"));
                  this.setSuccessCount(1);
               } else {
                  this.setSuccessCount(0);
                  MinecraftServer minecraftserver = this.getLevel().getServer();
                  if (!StringUtil.isNullOrEmpty(this.getCommand())) {
                     try {
                        this.setLastOutput(null);
                        CommandSourceStack commandsourcestack = this.createCommandSourceStack().withCallback(($$0x, $$1x) -> {
                           if ($$0x) {
                              this.setSuccessCount(this.getSuccessCount() + 1);
                           }
                        });
                        minecraftserver.getCommands().performPrefixedCommand(commandsourcestack, this.getCommand());
                     } catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.forThrowable(throwable, "Executing command block");
                        CrashReportCategory crashreportcategory = crashreport.addCategory("Command to be executed");
                        crashreportcategory.setDetail("Command", this::getCommand);
                        crashreportcategory.setDetail("Name", () -> this.getName().getString());
                        throw new ReportedException(crashreport);
                     }
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      };
      bcb.setCommand(command);
      bcb.setTrackOutput(true);
      bcb.performCommand(serverLevel);
      return bcb.getLastOutput().getString();
   }
}
