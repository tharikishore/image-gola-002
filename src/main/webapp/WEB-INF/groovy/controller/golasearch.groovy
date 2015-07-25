import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static imagegola.OfyService.ofy
import imagegola.GolaImage
import imagegola.Google

 

if(request.getParameter('keyword') != null){
	Google google = new Google(request.getParameter('keyword'), 64);
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

	entityList.each{
		println it.properties
		it.imgSrc='google'
		it.searchKey=request.getParameter('keyword')
		it.imgSize=Integer.parseInt(it.height) * Integer.parseInt(it.width)
		if(Double.parseDouble(it.aspectRatio) > 1.0)
			it.aspectRatio = 'Portrait'
		else
			it.aspectRatio = 'Landscape'
		ofy().save().entity(it).now()
	}
	session.keyword = request.getParameter('keyword')
}

def lis = ofy().load().type(GolaImage.class)

lis = lis.filter("searchKey", session.keyword)

def images = lis.list()

request.imgs = images

forward '/WEB-INF/pages/list.gtpl'