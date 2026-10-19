package it.hurts.octostudios.immersiveui.compat;

import it.hurts.octostudios.immersiveui.client.MouseInfo;
import it.hurts.octostudios.immersiveui.client.RenderInfo;
import java.util.Map;
import java.util.Random;
import net.minecraft.world.inventory.Slot;

public interface ExtraScreenData {
   MouseInfo getMouseInfo();

   RenderInfo getRenderInfo();

   Random getRandom();

   Map<Slot, Float> getExpandingProgress();
}
