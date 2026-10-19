package mezz.jei.neoforge.input;

import com.mojang.blaze3d.platform.InputConstants.Key;
import java.util.function.Consumer;
import mezz.jei.common.input.KeyNameUtil;
import mezz.jei.common.input.keys.IJeiKeyMappingInternal;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

public class NeoForgeJeiKeyMapping implements IJeiKeyMappingInternal {
   private final KeyMapping keyMapping;

   public NeoForgeJeiKeyMapping(KeyMapping keyMapping) {
      this.keyMapping = keyMapping;
   }

   @Override
   public boolean isActiveAndMatches(Key key) {
      return this.keyMapping.isActiveAndMatches(key);
   }

   @Override
   public boolean isUnbound() {
      return this.keyMapping.isUnbound();
   }

   @Override
   public Component getTranslatedKeyMessage() {
      Key key = this.keyMapping.getKey();
      return this.keyMapping.getKeyModifier().getCombinedName(key, () -> KeyNameUtil.getKeyDisplayName(key));
   }

   public IJeiKeyMappingInternal register(Consumer<KeyMapping> registerMethod) {
      registerMethod.accept(this.keyMapping);
      return this;
   }
}
