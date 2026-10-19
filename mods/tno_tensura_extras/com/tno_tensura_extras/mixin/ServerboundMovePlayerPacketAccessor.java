package com.tno_tensura_extras.mixin;

import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerboundMovePlayerPacket.class)
public interface ServerboundMovePlayerPacketAccessor {
   @Mutable
   @Accessor("x")
   void tno_tensura_extras$setX(double var1);

   @Mutable
   @Accessor("y")
   void tno_tensura_extras$setY(double var1);

   @Mutable
   @Accessor("z")
   void tno_tensura_extras$setZ(double var1);
}
