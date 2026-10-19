package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.phys.Vec3;

public interface IPlatformHelper {
   String getPlatformName();

   boolean isModLoaded(String var1);

   boolean isDevelopmentEnvironment();

   void sendIcicleProjectileShatterS2CPacket(ServerLevel var1, Vec3 var2);

   void syncSandstormDataToClients(SandstormServerData var1);

   void syncSandstormDataToPlayer(SandstormServerData var1, ServerPlayer var2);

   Supplier<Item> getIceCubeSpawnEggItem();

   Supplier<Item> getSandSnapperSpawnEggItem();

   Block getPottedPricklyPeachCactusBlock();

   ResourceKey<DecoratedPotPattern> registerDecoratedPotPattern(String var1, AutoRegisterItem var2);
}
