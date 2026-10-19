package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinPlugin implements IMixinConfigPlugin {
   public void onLoad(String s) {
      if (this.isThisVersion()) {
         Config.runProperties();
         LogManager.getLogger().info("Packet Fixer forge 1.20.5-1.21.X has been applied successfully.");
      }
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String s, String s1) {
      return this.isThisVersion();
   }

   private boolean isThisVersion() {
      Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer("minecraft");
      if (!modContainer.isPresent()) {
         return false;
      }

      String version = modContainer.get().getMetadata().getVersion().getFriendlyString();
      return version.equals("1.20.5") || version.equals("1.20.6") || version.startsWith("1.21");
   }

   public void acceptTargets(Set<String> set, Set<String> set1) {
   }

   public List<String> getMixins() {
      return null;
   }

   public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {
   }

   public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {
   }
}
