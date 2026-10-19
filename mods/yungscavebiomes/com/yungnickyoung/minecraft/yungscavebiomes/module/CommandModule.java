package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterCommand;
import com.yungnickyoung.minecraft.yungscavebiomes.command.DebugSpawnArmorTrimsCommand;
import com.yungnickyoung.minecraft.yungscavebiomes.command.SandstormCommand;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.server.commands.SpawnArmorTrimsCommand;

@AutoRegister("yungscavebiomes")
public class CommandModule {
   @AutoRegister("sandstorm")
   public static final AutoRegisterCommand SANDSTORM_COMMAND = AutoRegisterCommand.of(SandstormCommand::register);
   @AutoRegister("ycb_armor_trims")
   public static final AutoRegisterCommand DEBUG_ARMOR_TRIM_COMMAND = AutoRegisterCommand.of((dispatcher, ctx, handler) -> {
      if (Services.PLATFORM.isDevelopmentEnvironment()) {
         DebugSpawnArmorTrimsCommand.register(dispatcher, ctx, handler);
      }
   });
   @AutoRegister("spawn_armor_trims")
   public static final AutoRegisterCommand DEBUG_ALL_ARMOR_TRIM_COMMAND = AutoRegisterCommand.of((dispatcher, ctx, handler) -> {
      if (Services.PLATFORM.isDevelopmentEnvironment()) {
         SpawnArmorTrimsCommand.register(dispatcher);
      }
   });
}
