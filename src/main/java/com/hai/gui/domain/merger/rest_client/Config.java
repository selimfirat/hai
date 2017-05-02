package com.hai.gui.domain.merger.rest_client;

/**
 * Created by deniz on 26/04/17.
 */

public class Config
{
    public static final String SERVER_URL = "http://localhost:5000";
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/42.0";

    public static final String GET_N_LENGTH_STRING = "/dictionary/";

    public static final String GET_TF_IDF_SCORES_STRING = "/document_analyzer/get_tfidf_scores"; // unimplemented
    public static final String ANALYZE_SEARCH_RESULT_STRING = "/document_analyzer/analyze_search_result";

    public static final String SEARCH_DATAMUSE_ANSWER_LIST_STRING = "/datamuse/datamuse_answer_list";
    public static final String SEARCH_DATAMUSE_WORDENP_STRING = "/datamuse/search_datamuse_wordenp";
    public static final String WIKI_SEARCH_STRING = "/datamuse/wiki_search";

    public static final String FIND_ALL_SYNONYMS_STRING = "/syn_ant/find_all_synonyms";
    public static final String FIND_ALL_ANTONYMS_STRING = "/syn_ant/find_all_antonyms";
}
