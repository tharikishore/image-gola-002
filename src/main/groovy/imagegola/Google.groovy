package imagegola;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Google {
	private final String API_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&"\
			+ "q=###q###&userip=192.168.16.1&rsz=###num###";
	private final int NUM_PER_PAGE = 8;
	private String searchTerm;
	private int numOfItems;
	private boolean processInProgress = false;
	private boolean processdone = false;
	private List entityList = new ArrayList();
	private List searchThreads = new ArrayList();

	public Google(String searchTerm, int pageLimit) {
		this.searchTerm = searchTerm;
		this.numOfItems = pageLimit;
	}

	public boolean startSearch() {
		boolean result = false;
		if (!processInProgress) {
			result = startTheProcess();
		}
		return result;
	}

	private boolean startTheProcess() {
		boolean result = false;
		try {
			String urlString = new String(API_URL);
			urlString = urlString.replaceAll("###q###",
					URLEncoder.encode(searchTerm));
			urlString = urlString.replaceAll("###num###",
					String.valueOf(NUM_PER_PAGE));
			GoogleThread googleThread = new GoogleThread(urlString, entityList);
			googleThread.run();
			result = true;
			// start the threads to process the balance pages based on pageLimit
			int numOfThreads = numOfItems / NUM_PER_PAGE;
/*			ThreadGroup tg = new ThreadGroup("Collectors");
			Thread t = null;
			for (int i = 1; i < numOfThreads; i++) {
				String curUrlString = new String(urlString) + "&start="
						+ NUM_PER_PAGE * i;
				t = new Thread(tg, new GoogleThread(curUrlString, entityList));
				t.start();
				searchThreads.add(t);
			}

			Iterator it = searchThreads.iterator();
			while (it.hasNext()) {
				t = (Thread) it.next();
				t.join();
			}*/

			processdone = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean isProcessDone() {
		return processdone;
	}

	public static void main(String[] args) {
		Google google = new Google("taj mahal", 64);
		google.startSearch();
		while (!google.isProcessDone()) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List entityList = google.entityList;
		Iterator it = entityList.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

}

class GoogleThread implements Runnable {
	private String urlString;
	private List entityList;

	GoogleThread(String urlString, List entityList) {
		this.urlString = urlString;
		this.entityList = entityList;
	}

	private void collectData() {
		URL url;
		try {
			url = new URL(urlString);
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("Referer", "");

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			System.out.println(urlString + "\n" + json);
			if(json.get("responseData") && !json.isNull('responseData')){
				JSONArray resultsArray = ((JSONObject) json.get("responseData")).getJSONArray("results");
				Iterator it = resultsArray.iterator();
				JSONObject curResult = null;
				while (it.hasNext()) {
					curResult = (JSONObject) it.next();
					GolaImage curImage = new GolaImage();
					curImage.setImgUrl(curResult.getString("url").toString());
					curImage.setHeight(curResult.getString("height").toString());
					curImage.setWidth(curResult.getString("width").toString());
					try {
						curImage.setAspectRatio(String.valueOf(Float
								.parseFloat(curImage.getHeight())
								/ Float.parseFloat(curImage.getWidth())));
						;
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					synchronized (entityList) {
						entityList.add(curImage);
					}
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void run() {
		collectData();
	}

}