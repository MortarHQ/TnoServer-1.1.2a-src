package org.crypticdev.stextras.mixin.access;

import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SimpleScreen.class, remap = false)
public interface SimpleScreenAccessor {
   @Accessor("guiLeft")
   int stextras$getGuiLeft();

   @Accessor("guiTop")
   int stextras$getGuiTop();
}
