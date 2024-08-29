package net.michanide.mekanismneutronactivator.common;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public enum MNALang implements ILangEntry{
    DESCRIPTION_FUSION_NEUTRON_ACTIVATOR("description", "fusion_neutron_activator"),
    ;

    private final String key;

    MNALang(String type, String path) {
        this(Util.makeDescriptionId(type, MekanismNeutronActivator.rl(path)));
    }

    MNALang(String key) {
        this.key = key;
    }

    @Override
    public String getTranslationKey() {
        return key;
    }
}
