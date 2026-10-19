package me.decce.gnetum.mixins;

import java.util.List;
import net.neoforged.neoforge.client.gui.GuiLayerManager;
import net.neoforged.neoforge.client.gui.GuiLayerManager.NamedLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GuiLayerManager.class, remap = false)
public interface GuiLayerManagerAccessor {
   @Accessor
   List<NamedLayer> getLayers();
}
