package com.likya.twitter4j;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class ReadFeeds {

	// String user = "cnn";
	static String user = "serkan_tas";

	public static void getPages(Twitter unauthenticatedTwitter) {

		// // First param of Paging() is the page number, second is the number
		// per
		// // page (this is capped around 200 I think.
		// Paging paging = new Paging(1, 200);
		// List<Status> statuses = unauthenticatedTwitter.getUserTimeline(
		// "google", paging);

		int pageno = 1;

		@SuppressWarnings("rawtypes")
		List statuses = new ArrayList();
		int counter = 0;
		while (true) {

			try {

				int size = statuses.size();
				Paging page = new Paging(pageno++, 1000);

				ResponseList<Status> respList = unauthenticatedTwitter.getUserTimeline(user, page);

				for (Status myStatus : respList) {
					System.out.print(++counter + ": " + myStatus.getCreatedAt());
					System.out.println(myStatus.getText());
				}

				statuses.addAll(respList);
				// if (statuses.size() == size)
				// break;
			} catch (TwitterException e) {

				e.printStackTrace();
			}

			if (false)
				break;
		}

		System.out.println("Total: " + statuses.size());

	}

	public static void getLatest(Twitter twitter) {

		List<Status> statusList = null;

		boolean isTure = true;
		String buffer = "";

		while (isTure) {

			try {
				statusList = twitter.getUserTimeline(user);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (Status status : statusList) {
				// System.out.println(status.toString());
				if (!buffer.equals(status.getText())) {
					buffer = status.getText();
					System.out.println(buffer);
				}
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws TwitterException {

		ConfigurationBuilder cb = CfgBldLoader.load();

		Twitter unauthenticatedTwitter = new TwitterFactory(cb.build()).getInstance();

		getLatest(unauthenticatedTwitter);
		// getPages(unauthenticatedTwitter);

	}

}
