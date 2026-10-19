package com.mortal_cultivation.event;

import com.mortal_cultivation.command.DebugGreatSageEvolutionCommand;
import com.mortal_cultivation.command.JiuyouColdPoolLocateCommand;
import com.mortal_cultivation.command.MindscapeCommand;
import com.mortal_cultivation.command.ReloadFuelConfigCommand;
import com.mortal_cultivation.command.ResetGreatSageCooldownCommand;
import com.mortal_cultivation.command.ResetHundredGhostsCooldownCommand;
import com.mortal_cultivation.command.RuneTableDebugCommand;
import com.mortal_cultivation.command.SpiritRootCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class CommandRegistrationHandler {
   @SubscribeEvent
   public static void onRegisterCommands(RegisterCommandsEvent event) {
      ResetHundredGhostsCooldownCommand.register(event.getDispatcher());
      ReloadFuelConfigCommand.register(event.getDispatcher());
      DebugGreatSageEvolutionCommand.register(event.getDispatcher());
      ResetGreatSageCooldownCommand.register(event.getDispatcher());
      SpiritRootCommand.register(event.getDispatcher());
      MindscapeCommand.register(event.getDispatcher());
      RuneTableDebugCommand.register(event.getDispatcher());
      JiuyouColdPoolLocateCommand.register(event.getDispatcher(), event.getBuildContext());
   }
}
