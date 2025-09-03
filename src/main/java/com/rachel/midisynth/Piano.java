package com.rachel.midisynth;

import javax.sound.midi.MidiChannel;

public class Piano extends Instrument {
    public Piano(MidiChannel channel) {
        super(channel);
        if (channel != null) {
            channel.programChange(0);
            logger.info("Piano program set to Acoustic Grand Piano (0).");
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
