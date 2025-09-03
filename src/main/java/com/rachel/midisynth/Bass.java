package com.rachel.midisynth;

import javax.sound.midi.MidiChannel;

public class Bass extends Instrument {
    public Bass(MidiChannel channel) {
        super(channel);
        if (channel != null) {
            channel.programChange(32);
            logger.info("Bass program set to Acoustic Bass (32).");
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
