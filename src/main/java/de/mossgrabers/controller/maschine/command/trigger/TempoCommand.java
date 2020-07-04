// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2020
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.maschine.command.trigger;

import de.mossgrabers.controller.maschine.MaschineConfiguration;
import de.mossgrabers.controller.maschine.controller.MaschineControlSurface;
import de.mossgrabers.controller.maschine.mode.EditNoteMode;
import de.mossgrabers.framework.command.trigger.mode.ModeSelectCommand;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.mode.ModeManager;
import de.mossgrabers.framework.mode.Modes;
import de.mossgrabers.framework.utils.ButtonEvent;
import de.mossgrabers.framework.view.Views;


/**
 * Command to edit the tempo. Switch between Transpose and Pressure in note edit mode.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class TempoCommand extends ModeSelectCommand<MaschineControlSurface, MaschineConfiguration>
{
    /**
     * Constructor.
     *
     * @param model The model
     * @param surface The surface
     */
    public TempoCommand (final IModel model, final MaschineControlSurface surface)
    {
        super (model, surface, Modes.TEMPO);
    }


    /** {@inheritDoc} */
    @Override
    public void execute (final ButtonEvent event, final int velocity)
    {
        final ModeManager modeManager = this.surface.getModeManager ();

        // Switch Transpose/Pressure note edit parameters
        if (modeManager.isActiveOrTempMode (Modes.NOTE) && this.surface.getViewManager ().isActiveView (Views.DRUM, Views.PLAY))
        {
            if (event == ButtonEvent.DOWN)
            {
                final EditNoteMode mode = (EditNoteMode) modeManager.getMode (Modes.NOTE);
                final boolean isTranspose = mode.getSelectedItem () == EditNoteMode.TRANSPOSE;
                mode.selectItem (isTranspose ? EditNoteMode.PRESSURE : EditNoteMode.TRANSPOSE);
                this.surface.getDisplay ().notify (isTranspose ? "Pressure" : "Pitch");
            }
            return;
        }

        super.execute (event, velocity);
    }
}
