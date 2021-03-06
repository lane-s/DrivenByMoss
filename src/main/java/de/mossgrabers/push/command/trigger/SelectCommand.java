// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.push.command.trigger;

import de.mossgrabers.framework.ButtonEvent;
import de.mossgrabers.framework.Model;
import de.mossgrabers.framework.command.core.AbstractTriggerCommand;
import de.mossgrabers.framework.controller.color.ColorManager;
import de.mossgrabers.framework.mode.ModeManager;
import de.mossgrabers.push.PushConfiguration;
import de.mossgrabers.push.controller.PushControlSurface;
import de.mossgrabers.push.mode.Modes;


/**
 * Command to handle the select button.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class SelectCommand extends AbstractTriggerCommand<PushControlSurface, PushConfiguration>
{
    /**
     * Constructor.
     *
     * @param model The model
     * @param surface The surface
     */
    public SelectCommand (final Model model, final PushControlSurface surface)
    {
        super (model, surface);
    }


    /** {@inheritDoc} */
    @Override
    public void execute (final ButtonEvent event)
    {
        // Update for key combinations
        this.surface.getViewManager ().getActiveView ().updateNoteMapping ();

        final boolean isUp = event == ButtonEvent.UP;
        this.surface.updateButton (PushControlSurface.PUSH_BUTTON_SELECT, isUp ? ColorManager.BUTTON_STATE_ON : ColorManager.BUTTON_STATE_HI);

        final ModeManager modeManager = this.surface.getModeManager ();
        if (event == ButtonEvent.DOWN)
        {
            // Track or layer details?
            if (Modes.isLayerMode (modeManager.getActiveModeId ()))
                modeManager.setActiveMode (Modes.MODE_DEVICE_LAYER_DETAILS);
            else
                modeManager.setActiveMode (Modes.MODE_TRACK_DETAILS);
        }
        else if (isUp && modeManager.isActiveMode (Modes.MODE_TRACK_DETAILS))
            modeManager.restoreMode ();
    }
}
