package org.tn5250jlpr.event;

import java.util.EventListener;

public interface BootListener extends EventListener {

    public abstract void bootOptionsReceived(BootEvent bootevent);

}
