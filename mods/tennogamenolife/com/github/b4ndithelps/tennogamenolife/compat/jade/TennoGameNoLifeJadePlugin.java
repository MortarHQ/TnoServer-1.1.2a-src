package com.github.b4ndithelps.tennogamenolife.compat.jade;

import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import net.minecraft.world.entity.EntityType;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public final class TennoGameNoLifeJadePlugin implements IWailaPlugin {
   public void registerClient(IWailaClientRegistration registration) {
      registration.hideTarget((EntityType)TenreincarnationEntities.DRAGON_ROAR_CIRCLE.get());
   }
}
