import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static imagegola.OfyService.ofy
import imagegola.GolaImage

import com.google.appengine.api.datastore.PreparedQuery
import com.google.appengine.api.datastore.Query

 

def lis = ofy().load().type(GolaImage.class)

if(request.getParameter('aspectRatio') != null && !request.getParameter('aspectRatio').equalsIgnoreCase("All")){
	lis = lis.filter('aspectRatio',request.getParameter('aspectRatio'))
}
if(session.keyword != null){
	lis = lis.filter("searchKey", session.keyword)
}

def images = lis.list()

request.imgs = images

forward '/WEB-INF/pages/list.gtpl'