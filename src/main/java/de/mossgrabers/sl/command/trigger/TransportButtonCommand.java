// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.sl.command.trigger;

import de.mossgrabers.framework.ButtonEvent;
import de.mossgrabers.framework.Model;
import de.mossgrabers.framework.command.core.AbstractTriggerCommand;
import de.mossgrabers.framework.mode.ModeManager;
import de.mossgrabers.sl.SLConfiguration;
import de.mossgrabers.sl.controller.SLControlSurface;
import de.mossgrabers.sl.mode.Modes;


/**
 * Command to handle the Transport button.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class TransportButtonCommand extends AbstractTriggerCommand<SLControlSurface, SLConfiguration>
{
    /**
     * Constructor.
     *
     * @param model The model
     * @param surface The surface
     */
    public TransportButtonCommand (final Model model, final SLControlSurface surface)
    {
        super (model, surface);
    }


    /** {@inheritDoc} */
    @Override
    public void execute (final ButtonEvent event)
    {
        // Note: The Transport button sends DOWN on first press and UP on the next press
        if (event == ButtonEvent.LONG)
            return;

        // Toggle transport
        final ModeManager modeManager = this.surface.getModeManager ();
        if (modeManager.isActiveMode (Modes.MODE_VIEW_SELECT) || event == ButtonEvent.UP)
            modeManager.restoreMode ();
        else
            modeManager.setActiveMode (Modes.MODE_VIEW_SELECT);
    }
}
