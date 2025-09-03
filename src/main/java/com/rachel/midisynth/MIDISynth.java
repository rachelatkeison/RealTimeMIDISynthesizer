package com.rachel.midisynth;

import javax.sound.midi.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MIDISynth {
    private static final Logger LOGGER = Logger.getLogger(MIDISynth.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            playOrchestrationDemo();
        } else {
            playMidiFile(args[0]);
        }
    }
    private static void playOrchestrationDemo() {
        try (Synthesizer synth = MidiSystem.getSynthesizer()) {
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            List<Instrument> instruments = new ArrayList<>();

            instruments.add(new Piano(channels[0]));
            instruments.add(new Bass(channels[1]));
            instruments.add(new Strings(channels[2]));

            int[] notes = {60, 48, 67};
            int velocity = 100;

            LOGGER.info("Starting orchestration demo...");

            for (int i = 0; i < instruments.size(); i++) {
                instruments.get(i).play(notes[i], velocity);
            }
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (int i = 0; i < instruments.size(); i++) {
                        instruments.get(i).stop(notes[i]);
                    }
                    LOGGER.info("Orchestration demo finished.");
                    timer.cancel();
                }
            }, 1500);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in orchestration demo", e);
        }
    }
    private static void playMidiFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            LOGGER.severe("MIDI file not found: " + path);
            return;
        }

        try (Sequencer sequencer = MidiSystem.getSequencer(false);
             Synthesizer synth = MidiSystem.getSynthesizer()) {

            sequencer.open();
            synth.open();
            Transmitter tx = sequencer.getTransmitter();
            Receiver rx = synth.getReceiver();
            tx.setReceiver(rx);
            sequencer.setSequence(MidiSystem.getSequence(file));
            MidiChannel[] channels = synth.getChannels();
            new Piano(channels[0]);
            new Bass(channels[1]);
            new Strings(channels[2]);

            CountDownLatch latch = new CountDownLatch(1);
            sequencer.addMetaEventListener(meta -> {
                if (meta.getType() == 47) {
                    LOGGER.info("MIDI playback completed.");
                    sequencer.stop();
                    latch.countDown();
                }
            });

            sequencer.start();
            LOGGER.info("Playing MIDI file: " + path);
            latch.await();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while playing MIDI file", e);
        }
    }
}
