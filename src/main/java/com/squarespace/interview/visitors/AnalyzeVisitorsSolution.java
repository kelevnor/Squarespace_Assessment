package com.squarespace.interview.visitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class AnalyzeVisitorsSolution {

  static long SESSION_SLIDE_WINDOW = 30*60;

  /**
   * Given a {@link Iterable} of website hit data modeled as {@link WebsiteVisit} analyze it and return the
   * resulting page views, unique visitors, and sessions.
   *
   * @param websiteVisits Input hit data.
   * @return A 3-element array of long corresponding to 0: page views, 1: unique visitors, 2: view sessions
   *
   * Marios Sifalakis -
   */
  @SuppressWarnings("UnusedParameters")
  static long[] processPageViews(Iterable<WebsiteVisit> websiteVisits) {
    long pageViews = getCount(websiteVisits);
    long uniqueVisitors = getUniqueVisitors(websiteVisits);
    long viewSessions = getSessions(websiteVisits);
    return new long[]{pageViews, uniqueVisitors, viewSessions};
  }


  //Method to count the page views from the Iterable provided
  static long getCount(Iterable<WebsiteVisit> websiteVisits){
    Iterator<WebsiteVisit> iter = websiteVisits.iterator();
    long counter = 0;
    while (iter.hasNext()) {
      counter++;
    }
    return counter;
  }

  //Method to count unique user visits from the Iterable provided
  static long getUniqueVisitors(Iterable<WebsiteVisit> websiteVisits){
    Iterator<WebsiteVisit> iter = websiteVisits.iterator();
    List<String> uniqueVisitorsList = new ArrayList<>();
    while (iter.hasNext()) {
      WebsiteVisit temp = iter.next();
      if(!uniqueVisitorsList.contains(temp.getVisitorId())){
        uniqueVisitorsList.add(temp.getVisitorId());
      }
    }
    return (long)uniqueVisitorsList.size();
  }

  //Method to get unique sessions of the users returned in the Iterable provided
  static long getSessions(Iterable<WebsiteVisit> websiteVisits){
    List<String> nameIndex = new ArrayList<>();
    List<WebsiteVisit> visitorsList = new ArrayList<>();
    List<WebsiteVisit> newSessions = new ArrayList<>();
    if(websiteVisits != null) {
      for(WebsiteVisit e: websiteVisits) {
        if(nameIndex.contains(e.getVisitorId())){
          WebsiteVisit temp = visitorsList.get(getIndexByName(nameIndex, e.getVisitorId()));
          if(!compareLongsToSlideWindow(e.getTimestamp(), temp.getTimestamp())){
            newSessions.add(e);
          }
        }
        else{
          newSessions.add(e);
        }
        nameIndex.add(e.getVisitorId());
        visitorsList.add(e);
      }
    }

    return (long)newSessions.size();
  }

  //iterate list backwards
  static int getIndexByName(List<String> temp, String name) {
    int counter = temp.size()-1;
    while(counter!=-1){
      if(name.equals(temp.get(counter))){
        return counter;
      }
      counter= counter-1;
    }
    return 0;
  }

  static Boolean compareLongsToSlideWindow(long temp1, long temp2) {
    return temp2-temp1<SESSION_SLIDE_WINDOW;
  }

}
