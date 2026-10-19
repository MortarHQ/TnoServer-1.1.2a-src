package com.mafuyu404.oneenoughitem;

import cc.sighs.oelib.data.DataRegistry;
import com.mafuyu404.oneenoughitem.api.DomainRegistry;
import com.mafuyu404.oneenoughitem.api.adapter.ItemDomainAdapter;
import com.mafuyu404.oneenoughitem.data.ItemReplacementValidator;
import com.mafuyu404.oneenoughitem.data.Replacements;
import com.mafuyu404.oneenoughitem.init.OEIReplacementStrategy;
import com.mafuyu404.oneenoughitem.init.config.OEIConfig;
import com.mafuyu404.oneenoughitem.util.MixinUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("oneenoughitem")
public class Oneenoughitem {
   public static final String MODID = "oneenoughitem";
   public static final Logger LOGGER = LogManager.getLogger();

   public Oneenoughitem(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
      MixinUtils.setStrategy(new OEIReplacementStrategy());
      OEIConfig.register();
      DomainRegistry.register(new ItemDomainAdapter());
      DataRegistry.registerWithNamespaces(Replacements.class, Replacements.CODEC, new String[]{"oei"});
      DataRegistry.registerNamespaceValidator(Replacements.class, "oei", ItemReplacementValidator.class);
   }
}
