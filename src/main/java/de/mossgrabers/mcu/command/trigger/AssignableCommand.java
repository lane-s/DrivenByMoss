// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.mcu.command.trigger;

import de.mossgrabers.framework.ButtonEvent;
import de.mossgrabers.framework.Model;
import de.mossgrabers.framework.command.continuous.FootswitchCommand;
import de.mossgrabers.mcu.MCUConfiguration;
import de.mossgrabers.mcu.controller.MCUControlSurface;


/**
 * Command for assignable functions.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class AssignableCommand extends FootswitchCommand<MCUControlSurface, MCUConfiguration>
{
    private final int          index;
    private final ModeSwitcher switcher;


    /**
     * Constructor.
     *
     * @param index The index of the assignable button
     * @param model The model
     * @param surface The surface
     */
    public AssignableCommand (final int index, final Model model, final MCUControlSurface surface)
    {
        super (model, surface);
        this.index = index;
        this.switcher = new ModeSwitcher (surface);
    }


    /** {@inheritDoc} */
    @Override
    public void execute (final ButtonEvent event)
    {
        switch (this.getSetting ())
        {
            case MCUConfiguration.FOOTSWITCH_2_PREV_MODE:
                if (event == ButtonEvent.DOWN)
                    this.switcher.scrollDown ();
                break;

            case MCUConfiguration.FOOTSWITCH_2_NEXT_MODE:
                if (event == ButtonEvent.DOWN)
                    this.switcher.scrollUp ();
                break;

            default:
                super.execute (event);
                break;
        }
    }


    /** {@inheritDoc} */
    @Override
    protected int getSetting ()
    {
        return this.surface.getConfiguration ().getAssignable (this.index);
    }
}
