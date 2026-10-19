package cc.sighs.oelib.config.api;

import cc.sighs.oelib.config.ConfigUnit;

public record ConfigChangedEvent<T>(ConfigUnit<T> unit, T oldValue, T newValue) {
}
