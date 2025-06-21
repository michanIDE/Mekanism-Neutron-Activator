package net.michanide.mekanismneutronactivator.common;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public enum MNALang implements ILangEntry{
    MEKANISM_NEUTRON_ACTIVATOR("constants", "mod_name"),

    DESCRIPTION_FUSION_NEUTRON_ACTIVATOR("description", "fusion_neutron_activator"),
    DESCRIPTION_FISSION_NEUTRON_ACTIVATOR("description", "fission_neutron_activator"),
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
