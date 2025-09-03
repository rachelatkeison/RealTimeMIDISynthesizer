package com.rachel.midisynth;

import javax.sound.midi.MidiChannel;

public class Strings extends Instrument {
    public Strings(MidiChannel channel) {
        super(channel);
        if (channel != null) {
            channel.programChange(48);
            logger.info("Strings program set to Strings Ensemble (48).");
        }
    }

    @Override
    public void play(int note, int velocity) {
        noteOn(note, velocity);
    }

    @Override
    public void stop(int note) {
        noteOff(note);
    }
}
