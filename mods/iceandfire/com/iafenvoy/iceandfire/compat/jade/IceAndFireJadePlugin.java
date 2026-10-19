package com.iafenvoy.iceandfire.compat.jade;

import com.iafenvoy.iceandfire.entity.DragonEggEntity;
import com.iafenvoy.iceandfire.entity.FireDragonEntity;
import com.iafenvoy.iceandfire.entity.IceDragonEntity;
import com.iafenvoy.iceandfire.entity.LightningDragonEntity;
import com.iafenvoy.iceandfire.entity.MultipartPartEntity;
import net.minecraft.world.level.block.DragonEggBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class IceAndFireJadePlugin implements IWailaPlugin {
   public void registerClient(IWailaClientRegistration registration) {
      registration.registerBlockComponent(DragonEggBlockComponentProvider.INSTANCE, DragonEggBlock.class);
      registration.registerEntityComponent(DragonEggEntityComponentProvider.INSTANCE, DragonEggEntity.class);
      registration.registerEntityComponent(DragonComponentProvider.INSTANCE, FireDragonEntity.class);
      registration.registerEntityComponent(DragonComponentProvider.INSTANCE, IceDragonEntity.class);
      registration.registerEntityComponent(DragonComponentProvider.INSTANCE, LightningDragonEntity.class);
      registration.registerEntityComponent(MultipartComponentProvider.INSTANCE, MultipartPartEntity.class);
   }
}
