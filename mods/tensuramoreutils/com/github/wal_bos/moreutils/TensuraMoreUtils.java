package com.github.wal_bos.moreutils;

import com.github.wal_bos.moreutils.command.MoreUtilsCapCommands;
import com.github.wal_bos.moreutils.command.MoreUtilsCommands;
import com.github.wal_bos.moreutils.command.MoreUtilsLeaderboardCommand;
import com.github.wal_bos.moreutils.handler.MoreUtilsHooks;
import com.github.wal_bos.moreutils.handler.MoreUtilsLeaderboardHandler;
import com.github.wal_bos.moreutils.registry.MoreUtilsGameRules;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod("tensuramoreutils")
public class TensuraMoreUtils {
   public static final String MODID = "tensuramoreutils";
   public static final Logger LOGGER = LogUtils.getLogger();

   public TensuraMoreUtils(IEventBus modBus) {
      MoreUtilsGameRules.init();
      NeoForge.EVENT_BUS.addListener(MoreUtilsCommands::register);
      NeoForge.EVENT_BUS.addListener(MoreUtilsCapCommands::register);
      NeoForge.EVENT_BUS.addListener(MoreUtilsLeaderboardCommand::register);
      MoreUtilsLeaderboardHandler.init();
      MoreUtilsHooks.init();
      LOGGER.info("TensuraMoreUtils loaded!");
   }
}
