package com.rs.ms.twitter;

import com.rs.ms.twitter.models.TwitterData;

/**
 * Created by babluj on 5/9/16.
 */
public interface User {

    public TwitterData getTimeLine(TwitterConfig config) throws Exception;
}
