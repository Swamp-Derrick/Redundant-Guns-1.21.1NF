package zaeonninezero.redundantguns;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import zaeonninezero.redundantguns.init.ModCreativeTabs;
import zaeonninezero.redundantguns.init.initItems;
import zaeonninezero.redundantguns.init.initSounds;

@Mod(RedundantGuns.MOD_ID)
public final class RedundantGuns {
    public static final String MOD_ID = "redundantguns";

    public RedundantGuns(IEventBus bus) {
        initItems.ITEMS.register(bus);
        initSounds.SOUNDS.register(bus);
        ModCreativeTabs.TABS.register(bus);
    }
}
