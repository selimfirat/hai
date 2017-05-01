package com.hai.gui.presentation;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class MrsHai {

    private final AmazonPollyClient polly;

    public MrsHai() {
        // create an Amazon Polly client in a specific region
        polly = new AmazonPollyClient(new DefaultAWSCredentialsProviderChain(),
                new ClientConfiguration());
        polly.setRegion(Region.getRegion(Regions.US_EAST_1));
        // Create describe voices request.
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();

        // Synchronously ask Amazon Polly to describe available TTS voices.
//        DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);

    }

    private InputStream synthesize(String text, OutputFormat format) throws IOException {
        SynthesizeSpeechRequest synthReq =
                new SynthesizeSpeechRequest().withText(text).withVoiceId("Salli") // voice.getId())
                        .withOutputFormat(format);
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }

    public static final Level LEVEL = Level.SEVERE;
    private Queue<String> speechQueue = new LinkedList<String>();


    public void speak(final String speech) {
        speechQueue.add(speech);
        if (speechQueue.size() == 1)
            justSpeak();
    }

    private void justSpeak() {
        if (speechQueue.size() == 0)
            return;

        final String speech = speechQueue.peek();

        //get the audio stream
        InputStream speechStream = null;
        try {
            speechStream = synthesize(speech, OutputFormat.Mp3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create an MP3 player
        AdvancedPlayer player = null;
        try {
            player = new AdvancedPlayer(speechStream,
                    javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("Saying " + speech);
            }


            @Override
            public void playbackFinished(PlaybackEvent evt) {
                speechQueue.remove();
                justSpeak();
            }

        });


        // play it!
        try {
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }
} 