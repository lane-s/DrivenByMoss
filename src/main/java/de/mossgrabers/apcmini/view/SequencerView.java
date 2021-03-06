// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.apcmini.view;

import de.mossgrabers.apcmini.APCminiConfiguration;
import de.mossgrabers.apcmini.controller.APCminiControlSurface;
import de.mossgrabers.framework.ButtonEvent;
import de.mossgrabers.framework.Model;
import de.mossgrabers.framework.daw.CursorClipProxy;
import de.mossgrabers.framework.scale.Scales;
import de.mossgrabers.framework.view.AbstractNoteSequencerView;
import de.mossgrabers.framework.view.AbstractSequencerView;
import de.mossgrabers.framework.view.View;


/**
 * The Sequencer view.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class SequencerView extends AbstractNoteSequencerView<APCminiControlSurface, APCminiConfiguration> implements APCminiView
{
    /**
     * Constructor.
     *
     * @param surface The controller
     * @param model The model
     */
    public SequencerView (final APCminiControlSurface surface, final Model model)
    {
        super ("Sequencer", surface, model, false);
    }


    /** {@inheritDoc} */
    @Override
    public void onSelectTrack (final int index, final ButtonEvent event)
    {
        if (event != ButtonEvent.DOWN)
            return;

        switch (index)
        {
            case 0:
                this.onOctaveUp (event);
                break;
            case 1:
                this.onOctaveDown (event);
                break;
            case 2:
                this.onLeft (event);
                break;
            case 3:
                this.onRight (event);
                break;
        }
        this.updateScale ();
    }


    /** {@inheritDoc} */
    @Override
    public void updateSceneButtons ()
    {
        final boolean isKeyboardEnabled = this.model.canSelectedTrackHoldNotes ();
        for (int i = 0; i < 8; i++)
            this.surface.updateButton (APCminiControlSurface.APC_BUTTON_SCENE_BUTTON1 + i, isKeyboardEnabled && i == 7 - this.selectedIndex ? APCminiControlSurface.APC_BUTTON_STATE_ON : APCminiControlSurface.APC_BUTTON_STATE_OFF);

        final int octave = this.scales.getOctave ();
        this.surface.updateButton (APCminiControlSurface.APC_BUTTON_TRACK_BUTTON1, octave < Scales.OCTAVE_RANGE ? APCminiControlSurface.APC_BUTTON_STATE_ON : APCminiControlSurface.APC_BUTTON_STATE_OFF);
        this.surface.updateButton (APCminiControlSurface.APC_BUTTON_TRACK_BUTTON2, octave > -Scales.OCTAVE_RANGE ? APCminiControlSurface.APC_BUTTON_STATE_ON : APCminiControlSurface.APC_BUTTON_STATE_OFF);

        final View activeView = this.surface.getViewManager ().getActiveView ();
        final CursorClipProxy clip = activeView instanceof AbstractSequencerView ? ((AbstractSequencerView<?, ?>) activeView).getClip () : null;
        this.surface.updateButton (APCminiControlSurface.APC_BUTTON_TRACK_BUTTON3, clip != null && clip.canScrollStepsBackwards () ? APCminiControlSurface.APC_BUTTON_STATE_ON : APCminiControlSurface.APC_BUTTON_STATE_OFF);
        this.surface.updateButton (APCminiControlSurface.APC_BUTTON_TRACK_BUTTON4, clip != null && clip.canScrollStepsForwards () ? APCminiControlSurface.APC_BUTTON_STATE_ON : APCminiControlSurface.APC_BUTTON_STATE_OFF);
        for (int i = 0; i < 4; i++)
            this.surface.updateButton (APCminiControlSurface.APC_BUTTON_TRACK_BUTTON5 + i, APCminiControlSurface.APC_BUTTON_STATE_OFF);
    }


    /** {@inheritDoc} */
    @Override
    public void onGridNote (final int note, final int velocity)
    {
        super.onGridNote (note + 36, velocity);
    }
}