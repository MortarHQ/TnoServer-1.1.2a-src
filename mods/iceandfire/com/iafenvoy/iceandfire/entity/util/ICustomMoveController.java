package com.iafenvoy.iceandfire.entity.util;

public interface ICustomMoveController {
   void up(boolean var1);

   void down(boolean var1);

   void attack(boolean var1);

   void strike(boolean var1);

   void dismount(boolean var1);

   byte getControlState();

   void setControlState(byte var1);
}
