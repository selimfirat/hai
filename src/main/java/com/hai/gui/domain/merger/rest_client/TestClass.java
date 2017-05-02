package com.hai.gui.domain.merger.rest_client;


import java.io.IOException;

/**
 * Created by deniz on 29/04/17.
 */
public class TestClass
{
    public static void main(String[] args) throws IOException
    {
        RestClient rc = new RestClient();

        //System.out.println(rc.getNLength(3));
        System.out.println(rc.analyzeSearchResults("erdoğan", 7, 6));
        //System.out.println(rc.dataMuseAnswerList("what is round, purple and commutes to work", 10));
        //System.out.println(rc.searchDatamuseWordenp("what is round, purple and commutes to work"));
        //System.out.println(rc.wikiSearch("what is round, purple and commutes to work", 5));
        System.out.println(rc.findAllSynonyms("selim", 5));
        System.out.println(rc.findAllAntonyms("selim", 5));
    }
}
