package com.blamejared.controlling.api.events;

import com.mojang.blaze3d.platform.InputConstants.Key;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;

public interface ISetKeyEvent {
   Options options();

   KeyMapping mapping();

   Key key();
}
