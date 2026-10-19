package it.hurts.octostudios.immersiveui.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;

public class VariableStorage {
   public static final long TARGET_INTERVAL_MS = 350L;
   public static long lastExecutedTime = System.currentTimeMillis();
   public static long currentTime;
   public static long elapsedTime;
   public static final double SCREEN_ZORDER = 50.0;
   public static List<Screen> shakeScreen = new ArrayList<>();
}
