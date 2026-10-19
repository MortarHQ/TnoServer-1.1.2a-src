package dev.ftb.mods.ftblibrary.config;

import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import dev.ftb.mods.ftblibrary.snbt.config.IntValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.snbt.config.StringValue;

public interface FTBLibraryStartupConfig {
   String KEY = "ftblibrary-startup";
   SNBTConfig CONFIG = SNBTConfig.create("ftblibrary-startup").comment("Startup configuration for FTB Library. Testing only!");
   SNBTConfig SECT1 = CONFIG.addGroup("section1");
   StringValue TEST1 = SECT1.addString("test1", "hello");
   StringValue TEST2 = SECT1.addString("test2", "world");
   BooleanValue TEST3 = SECT1.addBoolean("test3", true);
   SNBTConfig SECT2 = CONFIG.addGroup("section2");
   BooleanValue TEST4 = SECT2.addBoolean("test4", false);
   IntValue TEST5 = SECT2.addInt("test5", 1, 0, 10);
}
