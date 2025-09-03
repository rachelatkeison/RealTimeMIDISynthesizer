package com.rachel.midisynth;

import javax.sound.midi.MidiChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Instrument {
    protected final MidiChannel channel;
    protected final Logger logger;

    public Instrument(MidiChannel channel) {
        this.channel = channel;
        this.logger = Logger.getLogger(this.getClass().getName());

        if (channel == null) {
            logger.warning("Instrument initialized with null MIDI channel. Playback may fail.");
        } else {
            logger.info(this.getClass().getSimpleName() + " instrument initialized on a valid MIDI channel.");
        }
    }
    public abstract void play(int note, int velocity);

    public abstract void stop(int note);

    protected void noteOn(int note, int velocity) {
        if (channel != null) {
            channel.noteOn(note, velocity);
            logger.log(Level.INFO, "{0}: Note ON {1}, velocity {2}",
                    new Object[]{this.getClass().getSimpleName(), note, velocity});
        } else {
            logger.warning("Note ON ignored: no MIDI channel.");
        }
    }
    protected void noteOff(int note) {
        if (channel != null) {
            channel.noteOff(note);
            logger.log(Level.INFO, "{0}: Note OFF {1}",
                    new Object[]{this.getClass().getSimpleName(), note});
        } else {
            logger.warning("Note OFF ignored: no MIDI channel.");
        }
    }
}
