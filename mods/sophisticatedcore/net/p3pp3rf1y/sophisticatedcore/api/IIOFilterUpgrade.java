package net.p3pp3rf1y.sophisticatedcore.api;

import java.util.Optional;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;

public interface IIOFilterUpgrade {
   Optional<FilterLogic> getInputFilter();

   Optional<FilterLogic> getOutputFilter();
}
