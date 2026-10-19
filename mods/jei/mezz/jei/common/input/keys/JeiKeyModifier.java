package mezz.jei.common.input.keys;

import com.mojang.blaze3d.platform.InputConstants.Key;
import mezz.jei.common.input.KeyNameUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public enum JeiKeyModifier {
   CONTROL_OR_COMMAND {
      @Override
      public boolean isActive(JeiKeyConflictContext context) {
         return Screen.hasControlDown();
      }

      @Override
      public Component getCombinedName(Key key) {
         return Minecraft.ON_OSX
            ? Component.translatable("jei.key.combo.command", new Object[]{KeyNameUtil.getKeyDisplayName(key)})
            : Component.translatable("jei.key.combo.control", new Object[]{KeyNameUtil.getKeyDisplayName(key)});
      }
   },
   SHIFT {
      @Override
      public boolean isActive(JeiKeyConflictContext context) {
         return Screen.hasShiftDown();
      }

      @Override
      public Component getCombinedName(Key key) {
         return Component.translatable("jei.key.combo.shift", new Object[]{KeyNameUtil.getKeyDisplayName(key)});
      }
   },
   ALT {
      @Override
      public boolean isActive(JeiKeyConflictContext context) {
         return Screen.hasAltDown();
      }

      @Override
      public Component getCombinedName(Key key) {
         return Component.translatable("jei.key.combo.alt", new Object[]{KeyNameUtil.getKeyDisplayName(key)});
      }
   },
   NONE {
      @Override
      public boolean isActive(JeiKeyConflictContext context) {
         return context.conflicts(JeiKeyConflictContext.IN_GAME)
            ? true
            : !CONTROL_OR_COMMAND.isActive(context) && !SHIFT.isActive(context) && !ALT.isActive(context);
      }

      @Override
      public Component getCombinedName(Key key) {
         return KeyNameUtil.getKeyDisplayName(key);
      }
   };

   public abstract boolean isActive(JeiKeyConflictContext var1);

   public abstract Component getCombinedName(Key var1);
}
