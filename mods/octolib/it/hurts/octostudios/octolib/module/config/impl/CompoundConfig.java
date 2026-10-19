package it.hurts.octostudios.octolib.module.config.impl;

import it.hurts.octostudios.octolib.module.config.cfgbuilder.CompoundEntry;
import it.hurts.octostudios.octolib.module.config.loader.IConfigFileLoader;

public abstract class CompoundConfig implements OctoConfig {
   @Override
   public Object prepareData() {
      CompoundEntry compoundConfig = new CompoundEntry();
      this.write(compoundConfig);
      return compoundConfig;
   }

   @Override
   public void onLoadObject(Object object) {
      this.read((CompoundEntry)object);
   }

   @Override
   public IConfigFileLoader<?, ?> getLoader() {
      return IConfigFileLoader.SOLID;
   }

   public abstract void write(CompoundEntry var1);

   public abstract void read(CompoundEntry var1);
}
