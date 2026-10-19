package com.natamus.collective_common_fabric.functions;

import java.util.Objects;
import net.minecraft.class_128;
import net.minecraft.class_129;
import net.minecraft.class_148;
import net.minecraft.class_1918;
import net.minecraft.class_1937;
import net.minecraft.class_2168;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3544;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandFunctions {
   public static String getRawCommandOutput(class_3218 serverLevel, @Nullable class_243 vec, String command) {
      class_1918 bcb = new class_1918() {
         @NotNull
         public class_3218 method_8293() {
            return serverLevel;
         }

         public void method_8295() {
         }

         @NotNull
         public class_243 method_8300() {
            return Objects.requireNonNullElseGet(vec, () -> new class_243(0.0, 0.0, 0.0));
         }

         @NotNull
         public class_2168 method_8303() {
            return new class_2168(
               this, this.method_8300(), class_241.field_1340, serverLevel, 2, "dev", class_2561.method_43470("dev"), serverLevel.method_8503(), null
            );
         }

         public boolean method_52175() {
            return true;
         }

         public boolean method_8301(class_1937 pLevel) {
            if (!pLevel.field_9236) {
               if ("Searge".equalsIgnoreCase(this.method_8289())) {
                  this.method_8291(class_2561.method_43470("#itzlipofutzli"));
                  this.method_8298(1);
               } else {
                  this.method_8298(0);
                  MinecraftServer minecraftserver = this.method_8293().method_8503();
                  if (!class_3544.method_15438(this.method_8289())) {
                     try {
                        this.method_8291(null);
                        class_2168 commandsourcestack = this.method_8303().method_9231(($$0x, $$1x) -> {
                           if ($$0x) {
                              this.method_8298(this.method_8304() + 1);
                           }
                        });
                        minecraftserver.method_3734().method_44252(commandsourcestack, this.method_8289());
                     } catch (Throwable throwable) {
                        class_128 crashreport = class_128.method_560(throwable, "Executing command block");
                        class_129 crashreportcategory = crashreport.method_562("Command to be executed");
                        crashreportcategory.method_577("Command", this::method_8289);
                        crashreportcategory.method_577("Name", () -> this.method_8299().getString());
                        throw new class_148(crashreport);
                     }
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      };
      bcb.method_8286(command);
      bcb.method_8287(true);
      bcb.method_8301(serverLevel);
      return bcb.method_8292().getString();
   }
}
