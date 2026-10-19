package com.github.b4ndithelps.tennogamenolife.mixin;

import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerboundMovePlayerPacket.class)
public interface ServerboundMovePlayerPacketAccessor {
   @Mutable
   @Accessor("x")
   void tennogamenolife$setX(double var1);

   @Mutable
   @Accessor("y")
   void tennogamenolife$setY(double var1);

   @Mutable
   @Accessor("z")
   void tennogamenolife$setZ(double var1);
}
